package ext.task.folder.resource;

import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * create folder resource
 *
 * @author 段鑫扬
 */
@RBUUID("createFolderResource")
public class createFolderResource extends WTListResourceBundle {
    @RBEntry("Create folder ")
    public static final String TASKFOLDER_CREATEFOLDER_TITLE = "taskFolder.createFolder.title";

    @RBEntry("Create folder ")
    public static final String TASKFOLDER_CREATEFOLDER_DESC = "taskFolder.createFolder.description";

    @RBEntry("Create folder ")
    public static final String TASKFOLDER_CREATEFOLDER_TOOLTIP = "taskFolder.createFolder.tooltip";

    @RBEntry("folder.gif")
    @RBPseudo(false)
    public static final String TASKFOLDER_CREATEFOLDER_ICON = "taskFolder.createFolder.icon";

    @RBEntry("Create Folder")
    public static final String CREATE_FOLDER_TITLE = "CREATE_FOLDER_TITLE";

    @RBEntry("Folder Name")
    public static final String FOLDER_NAME = "FOLDER_NAME";

    @RBEntry("Create Folder {0} Successfully")
    public static final String CREATE_FOLDER_SUCCESS_MSG = "CREATE_FOLDER_SUCCESS_MSG";

    @RBEntry("Create Folder Failure ,info. {0}")
    public static final String CREATE_FOLDER_FAILURE_MSG = "CREATE_FOLDER_FAILURE_MSG";
}
