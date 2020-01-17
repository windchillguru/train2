package ext.task.part.resource;


import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * 部件信息的资源文件
 *
 * @author 段鑫扬
 */
@RBUUID("partInfoResource")
public final class partInfoResource extends WTListResourceBundle {
    @RBEntry("part info action")
    public static final String TASKPART_PARTINFOACTION_TITLE = "taskPart.partInfoAction.title";

    @RBEntry("part info action")
    public static final String TASKPART_PARTINFOACTION_DESC = "taskPart.partInfoAction.description";

    @RBEntry("part info action")
    public static final String TASKPART_PARTINFOACTION_TOOLTIP = "taskPart.partInfoAction.tooltip";

    @RBEntry("iterate.png")
    @RBPseudo(false)
    public static final String TASKPART_PARTINFOACTION_ICON = "taskPart.partInfoAction.icon";

}
