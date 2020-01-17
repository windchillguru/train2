package ext.task.folder.resource;


import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * 所有部件信息的资源文件
 *
 * @author 段鑫扬
 */
@RBUUID("partsInfoTableResource")
public final class partsInfoTableResource extends WTListResourceBundle {
    @RBEntry("part info table action")
    public static final String TASKFOLDER_LLPARTSINFOACTION_TITLE = "taskFolder.allPartsInfoAction.title";

    @RBEntry("part info table action")
    public static final String TASKFOLDER_LLPARTSINFOACTION_DESC = "taskFolder.allPartsInfoAction.description";

    @RBEntry("part info table action")
    public static final String TASKFOLDER_LLPARTSINFOACTION_TOOLTIP = "taskFolder.allPartsInfoAction.tooltip";

    @RBEntry("iterate.png")
    @RBPseudo(false)
    public static final String TASKFOLDER_LLPARTSINFOACTION_ICON = "taskFolder.allPartsInfoAction.icon";

    @RBEntry("all part info")
    public static final String ALL_PARTS_INFO_TABLE = "all parts info table";

    @RBEntry("part table label")
    public static final String PART_TABLE_LABEL = "part table label";

    @RBEntry("Default")
    public static final String PART_TABLE_VIEW_NAME = "Default";

    @RBEntry("Default View Desc")
    public static final String PART_TABLE_VIEW_DESC = "Default View Desc";

}
