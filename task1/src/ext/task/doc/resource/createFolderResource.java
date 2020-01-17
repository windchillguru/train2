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
public class createFolderResource extends WTListResourceBundle {
    @RBEntry("Create folder ")
    public static final String TASKDOC_CREATEFOLDERDOC_TITLE = "taskDoc.createFolderDoc.title";

    @RBEntry("Create folder ")
    public static final String TASKDOC_CREATEFOLDERDOC_DESC = "taskDoc.createFolderDoc.description";

    @RBEntry("Create folder ")
    public static final String TASKDOC_CREATEFOLDERDOC_TOOLTIP = "taskDoc.createFolderDoc.tooltip";

    @RBEntry("folder.gif")
    @RBPseudo(false)
    public static final String TASKDOC_CREATEFOLDERDOC_ICON = "taskDoc.createFolderDoc.icon";

    @RBEntry("Create Folder")
    public static final String CREATE_FOLDER_TITLE = "CREATE_FOLDER_TITLE";

    @RBEntry("Folder Name")
    public static final String FOLDER_NAME = "FOLDER_NAME";

    @RBEntry("Create Folder {0} Successfully")
    public static final String CREATE_FOLDER_SUCCESS_MSG = "CREATE_FOLDER_SUCCESS_MSG";

    @RBEntry("Create Folder Failure ,info. {0}")
    public static final String CREATE_FOLDER_FAILURE_MSG = "CREATE_FOLDER_FAILURE_MSG";
}
