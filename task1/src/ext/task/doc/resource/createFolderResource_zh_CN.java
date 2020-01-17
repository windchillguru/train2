package ext.task.doc.resource;

import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * create folder in doc  resource
 *
 * @author 段鑫扬
 */
@RBUUID("createFolderResource")
public class createFolderResource_zh_CN extends WTListResourceBundle {
    @RBEntry("创建文件夹 ")
    public static final String TASKDOC_CREATEFOLDERDOC_TITLE = "taskDoc.createFolderDoc.title";

    @RBEntry("创建文件夹 ")
    public static final String TASKDOC_CREATEFOLDERDOC_DESC = "taskDoc.createFolderDoc.description";

    @RBEntry("创建文件夹 ")
    public static final String TASKDOC_CREATEFOLDERDOC_TOOLTIP = "taskDoc.createFolderDoc.tooltip";

    @RBEntry("folder.gif")
    @RBPseudo(false)
    public static final String TASKDOC_CREATEFOLDERDOC_ICON = "taskDoc.createFolderDoc.icon";

    @RBEntry("创建文件夹")
    public static final String CREATE_FOLDER_TITLE = "CREATE_FOLDER_TITLE";

    @RBEntry("文件夹名")
    public static final String FOLDER_NAME = "FOLDER_NAME";

    @RBEntry("创建文件夹{0} 成功")
    public static final String CREATE_FOLDER_SUCCESS_MSG = "CREATE_FOLDER_SUCCESS_MSG";

    @RBEntry("创建文件夹失败,失败信息{0}")
    public static final String CREATE_FOLDER_FAILURE_MSG = "CREATE_FOLDER_FAILURE_MSG";
}
