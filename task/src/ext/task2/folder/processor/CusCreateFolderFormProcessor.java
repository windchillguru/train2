package ext.task2.folder.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.util.FeedbackMessage;
import com.ptc.core.ui.resources.FeedbackType;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.folder.forms.CreateFolderFormProcessor;
import ext.pi.core.PIAttributeHelper;
import ext.task2.folder.util.FolderUtil;
import org.apache.log4j.Logger;
import wt.fc.PersistenceHelper;
import wt.folder.Folder;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerRef;
import wt.log4j.LogR;
import wt.util.WTException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * @author 段鑫扬
 * @version 2020/1/7
 * 创建文件夹时，附带创建子文件夹
 */
public class CusCreateFolderFormProcessor extends CreateFolderFormProcessor {
    private static final String CLASSNAME = CusCreateFolderFormProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static String IBA_CREATE_SUBFOLDER = "createSubFolder";


    @Override
    public FormResult doOperation(NmCommandBean commandBean, List<ObjectBean> objectBeans) throws WTException {
        FormResult formResult = super.doOperation(commandBean, objectBeans);

        ObjectBean objectBean = objectBeans.get(0);
        Object object = objectBean.getObject();
        //创建的主文件夹
        SubFolder folder = null;
        if (object instanceof SubFolder) {
            folder = (SubFolder) object;
        }

        Object createSubFolder = PIAttributeHelper.service.getValue(folder, IBA_CREATE_SUBFOLDER);

        if (createSubFolder == null || "否".equals((String) createSubFolder)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> ");
            }
            //不创建子文件夹
            return formResult;
        }
        //子文件夹名称的数组
        String[] subFolders = null;
        try {
            subFolders = FolderUtil.getSubFolder();
        } catch (IOException e) {
            e.printStackTrace();
            throw new WTException(e);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>subFolder name[] :" + subFolders.toString());
        }
        Locale locale = commandBean.getLocale();
        WTContainerRef containerReference = folder.getContainerReference();
        for (String subFolder : subFolders) {
            String folderPath = folder.getFolderPath();
            folderPath = folderPath + "/" + subFolder;
            Folder folder1 = null;
            try {
                folder1 = FolderHelper.service.getFolder(folderPath, containerReference);
                if (folder1 != null) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(">>>> 此文件夹已创建,不需要重新创建：" + subFolder);
                    }
                    continue;
                }
            } catch (WTException e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(">>>> 此文件夹未创建,需要重新创建：" + subFolder);
                }
            }
            SubFolder subFoler1 = SubFolder.newSubFolder(subFolder, folder);
            //创建子文件夹
            PersistenceHelper.manager.store(subFoler1);
        }
        String successMsg = "子文件夹创建成功";
        FeedbackMessage fbm = new FeedbackMessage(FeedbackType.SUCCESS, locale, successMsg, null, new String[]{""});
        formResult.addFeedbackMessage(fbm);
        return formResult;
    }
}
