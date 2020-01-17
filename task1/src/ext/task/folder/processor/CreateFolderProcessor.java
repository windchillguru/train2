package ext.task.folder.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.forms.FormResultAction;
import com.ptc.core.components.util.FeedbackMessage;
import com.ptc.core.ui.resources.FeedbackType;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.util.PartManagementHelper;
import ext.lang.PIStringUtils;
import ext.task.folder.resource.createFolderResource;
import org.apache.log4j.Logger;
import wt.folder.Folder;
import wt.folder.FolderHelper;
import wt.folder.FolderNotFoundException;
import wt.inf.container.WTContainerRef;
import wt.log4j.LogR;
import wt.util.WTException;
import wt.util.WTMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * 创建文件夹的processor处理类
 *
 * @author 段鑫扬
 */
public class CreateFolderProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = CreateFolderProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = createFolderResource.class.getName();

    @Override
    public FormResult doOperation(NmCommandBean commandBean, List<ObjectBean> objectBeans) throws WTException {
        FormResult formResult = super.doOperation(commandBean, objectBeans);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> doOperation--->" + CLASSNAME);
        }

        Locale locale = commandBean.getLocale();
        //获取所有的文本输入框
        HashMap textMap = commandBean.getText();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("textMap=" + textMap);
        }

        String folderName = "";
        //获取文件夹名
        if (textMap != null && textMap.containsKey("folderName")) {
            folderName = (String) textMap.get("folderName");
        }

        LOGGER.debug("folderName=" + folderName);


        if (PIStringUtils.hasText(folderName)) {
            if (!folderName.startsWith("/")) {
                //文件夹名处理
                folderName = "/" + folderName;
            }

            if (!folderName.startsWith("/Default")) {
                //文件夹名（路径）处理
                folderName = "/Default" + folderName;
            }
            LOGGER.debug("folderName= " + folderName);

            //获取上下文
            WTContainerRef containerRef = commandBean.getContainerRef();

            Folder folder = null;
            try {
                folder = FolderHelper.service.getFolder(folderName, containerRef);
                if (folder != null) {
                    throw new WTException("文件夹已存在");
                }
            } catch (FolderNotFoundException e) {
                folder = FolderHelper.service.createSubFolder(folderName, containerRef);
            }
            String successMsg = WTMessage.getLocalizedMessage(RESOURCE, createFolderResource.CREATE_FOLDER_SUCCESS_MSG,
                    new Object[]{folderName}, locale);
            FeedbackMessage fbm = new FeedbackMessage(FeedbackType.SUCCESS, locale, successMsg, null, new String[]{""});
            //反馈信息添加超链接
            fbm.addOidIdentityPair(folder, locale);
            formResult.addFeedbackMessage(fbm);
            //跳转 创建的文件夹里面
            if ((commandBean.getPageOid().getRefObject() instanceof Folder)) {
                String url = PartManagementHelper.getInfoPageURL(folder);
                formResult.setNextAction(FormResultAction.LOAD_OPENER_URL);
                formResult.setURL(url);
            }
        }
        return formResult;
    }
}
