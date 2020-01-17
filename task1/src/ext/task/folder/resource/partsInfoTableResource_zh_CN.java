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
public final class partsInfoTableResource_zh_CN extends WTListResourceBundle {
    @RBEntry("所有部件")
    public static final String TASKFOLDER_LLPARTSINFOACTION_TITLE = "taskFolder.allPartsInfoAction.title";

    @RBEntry("所有部件")
    public static final String TASKFOLDER_LLPARTSINFOACTION_DESC = "taskFolder.allPartsInfoAction.description";

    @RBEntry("所有部件")
    public static final String TASKFOLDER_LLPARTSINFOACTION_TOOLTIP = "taskFolder.allPartsInfoAction.tooltip";

    @RBEntry("iterate.png")
    @RBPseudo(false)
    public static final String TASKFOLDER_LLPARTSINFOACTION_ICON = "taskFolder.allPartsInfoAction.icon";

    @RBEntry("所有部件信息")
    public static final String ALL_PARTS_INFO_TABLE = "all parts info table";

    @RBEntry("部件列表")
    public static final String PART_TABLE_LABEL = "part table label";

    @RBEntry("默认视图")
    public static final String PART_TABLE_VIEW_NAME = "Default";

    @RBEntry("默认视图")
    public static final String PART_TABLE_VIEW_DESC = "Default View Desc";
}
