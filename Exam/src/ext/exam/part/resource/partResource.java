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
public final class partResource extends WTListResourceBundle {
    @RBEntry("part info")
    public static final String OBJECT_PARTCUSTOMINFO_TITLE = "exam.generateNumber.title";

    @RBEntry("part info")
    public static final String OBJECT_PARTCUSTOMINFO_DESC = "exam.generateNumber.description";

    @RBEntry("part info")
    public static final String OBJECT_PARTCUSTOMINFO_TOOLTIP = "exam.generateNumber.tooltip";

    @RBEntry("info.png")
    @RBPseudo(false)
    public static final String OBJECT_PARTCUSTOMINFO_ICON = "exam.generateNumber.icon";

    @RBEntry("part status")
    public static final String EXAM_SETPARTSTATUS_TITLE = "exam.setPartStatus.title";

    @RBEntry("part status")
    public static final String EXAM_SETPARTSTATUS_DESC = "exam.setPartStatus.description";

    @RBEntry("part status")
    public static final String EXAM_SETPARTSTATUS_TOOLTIP = "exam.setPartStatus.tooltip";

    @RBEntry("set part status")
    public static final String SET_PART_STATUS = "set_part_status";

    @RBEntry("set part status title")
    public static final String SET_PART_STATUS_TITLE = "SET_PART_STATUS_TITLE";

    @RBEntry("set part status err")
    public static final String SET_PART_STATUS_ERR = "SET_PART_STATUS_ERR";

    @RBEntry("generate Number")
    public static final String EXAM_GENERATENUMBER_TITLE = "exam.generateNumber.title";

    @RBEntry("generate Number")
    public static final String EXAM_GENERATENUMBER_DESC = "exam.generateNumber.description";

    @RBEntry("generate Number")
    public static final String EXAM_GENERATENUMBER_TOOLTIP = "exam.generateNumber.tooltip";
}
