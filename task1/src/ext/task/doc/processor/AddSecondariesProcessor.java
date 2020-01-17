package ext.task.doc.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.util.FeedbackMessage;
import com.ptc.core.ui.resources.FeedbackType;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.lang.PIFileUtils;
import ext.task.doc.resource.docAPIResource;
import org.apache.log4j.Logger;
import wt.content.ApplicationData;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.doc.WTDocument;
import wt.fc.Persistable;
import wt.log4j.LogR;
import wt.pom.PersistentObjectManager;
import wt.pom.Transaction;
import wt.util.WTException;
import wt.util.WTMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * 添加附件的Processor
 *
 * @author 段鑫扬
 */
public class AddSecondariesProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = AddSecondariesProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = docAPIResource.class.getName();

    /**
     * 添加附件
     *
     * @param commandBean
     * @param objectBeans
     * @return
     * @throws WTException
     */
    @Override
    public FormResult doOperation(NmCommandBean commandBean, List<ObjectBean> objectBeans) throws WTException {
        FormResult formResult = super.doOperation(commandBean, objectBeans);
        Locale locale = commandBean.getLocale();

        List<File> need2Deletes = new ArrayList<>();
        try {
            WTDocument doc = null;
            NmOid nmOid = commandBean.getActionOid();
            Object refObject = nmOid.getRefObject();
            if (refObject != null && refObject instanceof WTDocument) {
                doc = (WTDocument) refObject;
            }
            if (doc == null) {
                throw new WTException("文档为空");
            }

            HashMap<String, Object> fileUploadMap = (HashMap<String, Object>) commandBean.getMap().get("fileUploadMap");
            LOGGER.debug("fileUploadMap" + fileUploadMap);
            Object fileObjs = fileUploadMap.get("secondFiles");

            Map<File, String> fileMap = new HashMap<>();
            if (fileObjs instanceof File) {
                fileMap.put((File) fileObjs, commandBean.getTextParameter("secondFiles"));
            } else if (fileObjs instanceof File[]) {
                File[] files = (File[]) fileObjs;
                int length = files.length;
                String[] fileNames = (String[]) commandBean.getParameterMap().get("secondFiles");
                if (length != fileNames.length) {
                    throw new WTException("文件名和文件个数不一致");
                }
                for (int i = 0; i < length; i++) {
                    fileMap.put(files[i], fileNames[i]);
                }
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("fileMap =" + fileMap);
            }
            //附件的容器
            Map<Persistable, InputStream> newItems = new HashMap<>();
            for (File file : fileMap.keySet()) {
                //需要删除的文件
                need2Deletes.add(file);
                String fileName = fileMap.get(file);
                ApplicationData applicationData = ApplicationData.newApplicationData(doc);
                //附件
                applicationData.setRole(ContentRoleType.SECONDARY);
                applicationData.setFileName(fileName);
                applicationData.setFileSize(file.length());
                newItems.put(applicationData, new FileInputStream(file));
            }
            addSecondaries(doc, newItems);

            String successMsg = WTMessage.getLocalizedMessage(RESOURCE, docAPIResource.ADD_SECONDARY_SUCCESSMSG
                    , new Object[]{}, locale);
            FeedbackMessage feedbackMessage = new FeedbackMessage(FeedbackType.SUCCESS, locale, successMsg, null, new String[]{""});
            formResult.addFeedbackMessage(feedbackMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        } finally {
            if (!need2Deletes.isEmpty()) {
                for (File file : need2Deletes) {
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }

        }
        return formResult;
    }

    /**
     * 添加附件
     *
     * @param doc
     * @param newItems
     */
    private void addSecondaries(WTDocument doc, Map<Persistable, InputStream> newItems) throws WTException {
        if (doc == null) {
            return;
        }

        Transaction tx = null;
        boolean canCommit = false;
        //输入流，需要关闭
        List<InputStream> isList = new ArrayList<>();
        try {
            if (!PersistentObjectManager.getPom().isTransactionActive()) {
                //开启事务
                tx = new Transaction();
                tx.start();
                canCommit = true;
            }

            if (newItems != null) {
                Iterator<Persistable> iterator = newItems.keySet().iterator();
                while (iterator.hasNext()) {
                    Persistable next = iterator.next();
                    if (next != null && next instanceof ApplicationData) {
                        ApplicationData appData = (ApplicationData) next;
                        InputStream inputStream = newItems.get(appData);
                        isList.add(inputStream);

                        LOGGER.debug("fileName = " + appData.getFileName());
                        ContentServerHelper.service.updateContent(doc, appData, inputStream);
                    }
                }
            }


            if (canCommit) {
                tx.commit();
                tx = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        } finally {
            PIFileUtils.close(isList);
            if (canCommit) {
                if (tx != null) {
                    tx.rollback();
                    tx = null;
                }
            }
        }

    }

}
