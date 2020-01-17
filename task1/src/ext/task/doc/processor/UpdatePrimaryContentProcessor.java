package ext.task.doc.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.util.FeedbackMessage;
import com.ptc.core.ui.resources.FeedbackType;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.task.doc.resource.docAPIResource;
import org.apache.log4j.Logger;
import wt.content.*;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.log4j.LogR;
import wt.pom.Transaction;
import wt.util.WTException;
import wt.util.WTMessage;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * 更新主内容的Processor
 *
 * @author 段鑫扬
 */
public class UpdatePrimaryContentProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = UpdatePrimaryContentProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = docAPIResource.class.getName();

    /**
     * 更新主内容
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

        FileInputStream inputStream = null;
        File file = null;
        Transaction tx = null;
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
            //获取主数据
            ContentItem primary = findPrimary(doc);
            if (primary != null) {
                //删除原来的主数据
                ContentServerHelper.service.deleteContent(doc, primary);
            }

            HashMap<String, Object> fileUploadMap = (HashMap<String, Object>) commandBean.getMap().get("fileUploadMap");
            LOGGER.debug("fileUploadMap" + fileUploadMap);
            file = (File) fileUploadMap.get("primaryFile");
            String inputFileName = commandBean.getTextParameter("primaryFile");
            LOGGER.debug("inputFileName" + inputFileName);

            inputStream = new FileInputStream(file);
            ApplicationData newApplicationData = ApplicationData.newApplicationData(doc);
            newApplicationData.setFileName(inputFileName);
            newApplicationData.setFileSize(file.length());
            //更新主内容
            ContentServerHelper.service.updatePrimary(doc, newApplicationData, inputStream);
            //更新格式
            primary = findPrimary(doc);
            doc.setFormat(primary.getFormat());
            PersistenceServerHelper.manager.update(doc);

            doc = (WTDocument) PersistenceHelper.manager.refresh(doc, true, true);

            String successMsg = WTMessage.getLocalizedMessage(RESOURCE, docAPIResource.UPDATE_PRIMARY_CONTENT_SUCCESSMSG
                    , new Object[]{}, locale);
            FeedbackMessage feedbackMessage = new FeedbackMessage(FeedbackType.SUCCESS, locale, successMsg, null, new String[]{""});
            formResult.addFeedbackMessage(feedbackMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //删除上传的临时文件
            if (file != null && file.exists()) {
                file.delete();
            }
        }
        return formResult;
    }

    /**
     * 获取主内容
     *
     * @return
     */
    public ContentItem findPrimary(WTDocument document) throws WTException {
        ContentItem primaryContent = null;
        try {
            if (document != null) {
                ContentHolder holder = ContentHelper.service.getContents(document);
                QueryResult qr = ContentHelper.service.getContentsByRole(holder, ContentRoleType.PRIMARY);
                if (qr != null && qr.hasMoreElements()) {
                    primaryContent = (ContentItem) qr.nextElement();
                }
            }
        } catch (PropertyVetoException e) {
            e.printStackTrace();
            throw new WTException(e);
        }
        return primaryContent;
    }
}
