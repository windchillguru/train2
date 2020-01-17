package ext.exam.part.resource;


import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * 部件信息的资源文件
 *
 * @author 段鑫扬
 */
@RBUUID("ext.exam.part.resource.partResource")
public final class partResource_zh_CN extends WTListResourceBundle {
    @RBEntry("部件客制信息")
    public static final String OBJECT_PARTCUSTOMINFO_TITLE = "object.partCustomInfo.title";

    @RBEntry("部件客制信息")
    public static final String OBJECT_PARTCUSTOMINFO_DESC = "object.partCustomInfo.description";

    @RBEntry("部件客制信息")
    public static final String OBJECT_PARTCUSTOMINFO_TOOLTIP = "object.partCustomInfo.tooltip";

    @RBEntry("info.png")
    @RBPseudo(false)
    public static final String OBJECT_PARTCUSTOMINFO_ICON = "object.partCustomInfo.icon";

    @RBEntry("设置部件状态")
    public static final String EXAM_SETPARTSTATUS_TITLE = "exam.setPartStatus.title";

    @RBEntry("设置部件状态")
    public static final String EXAM_SETPARTSTATUS_DESC = "exam.setPartStatus.description";

    @RBEntry("设置部件状态")
    public static final String EXAM_SETPARTSTATUS_TOOLTIP = "exam.setPartStatus.tooltip";

    @RBEntry("选择状态")
    public static final String SET_PART_STATUS = "set_part_status";

    @RBEntry("设置部件状态")
    public static final String SET_PART_STATUS_TITLE = "SET_PART_STATUS_TITLE";

    @RBEntry("请修改状态，否则请取消")
    public static final String SET_PART_STATUS_ERR = "SET_PART_STATUS_ERR";

    @RBEntry("生成正式码")
    public static final String EXAM_GENERATENUMBER_TITLE = "exam.generateNumber.title";

    @RBEntry("生成正式码")
    public static final String EXAM_GENERATENUMBER_DESC = "exam.generateNumber.description";

    @RBEntry("生成正式码")
    public static final String EXAM_GENERATENUMBER_TOOLTIP = "exam.generateNumber.tooltip";

}
