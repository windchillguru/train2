package ext.task2.folder.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.util.FeedbackMessage;
import com.ptc.core.ui.resources.FeedbackType;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.folder.forms.EditFolderFormProcessor;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author 段鑫扬
 * @version 2020/1/7
 * 编辑时创建子文件夹
 */
public class CusEditFolderFormProcessor extends EditFolderFormProcessor {
    private static final String CLASSNAME = CusEditFolderFormProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static String IBA_CREATE_SUBFOLDER = "createSubFolder";

    @Override
    public FormResult doOperation(NmCommandBean commandBean, List<ObjectBean> objectBeans) throws WTException {
        FormResult formResult = super.doOperation(commandBean, objectBeans);
        ObjectBean objectBean = objectBeans.get(0);
        Object object = objectBean.getObject();
        //编辑的主文件夹
        SubFolder folder = null;
        if (object instanceof SubFolder) {
            folder = (SubFolder) object;
        }
        //是否需要创建子文件夹
        Object createSubFolder = PIAttributeHelper.service.getValue(folder, IBA_CREATE_SUBFOLDER);
        if (createSubFolder == null || "否".equals((String) createSubFolder)) {
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
        Locale locale = commandBean.getLocale();
        WTContainerRef containerRef = commandBean.getContainerRef();
        List<String> createFolderList = new ArrayList<>(subFolders.length);

        for (String subFolder : subFolders) {
            String folderPath = folder.getFolderPath();
            folderPath = folderPath + "/" + subFolder;
            Folder folder1 = null;
            try {
                folder1 = FolderHelper.service.getFolder(folderPath, containerRef);
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
            createFolderList.add(subFolder);
        }
        if (createFolderList.size() > 0) {
            String msg = "子文件夹：" + createFolderList.toString();
            msg = msg + "，创建成功";
            FeedbackMessage fbm = new FeedbackMessage(FeedbackType.SUCCESS, locale, msg, null, new String[]{""});
            formResult.addFeedbackMessage(fbm);
        }
        return formResult;
    }
}
