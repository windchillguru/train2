package ext.task.part.resource;


import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * excel信息的资源文件
 *
 * @author 段鑫扬
 */
@RBUUID("excelInfoResource")
public final class excelInfoResource_zh_CN extends WTListResourceBundle {
    @RBEntry("Excel信息")
    public static final String TASKPART_EXCELINFO_TITLE = "taskPart.excelInfo.title";

    @RBEntry("Excel信息")
    public static final String TASKPART_EXCELINFO_DESC = "taskPart.excelInfo.description";

    @RBEntry("Excel信息")
    public static final String TASKPART_EXCELINFO_TOOLTIP = "taskPart.excelInfo.tooltip";

    @RBEntry("iterate.png")
    @RBPseudo(false)
    public static final String TASKPART_EXCELINFO_ICON = "taskPart.excelInfo.icon";

    @RBEntry("Excel信息")
    public static final String TASKPART_EXCEL_TOOLTIP = "TASKPART_EXCEL_TITLE";

    @RBEntry("编号")
    public static final String TASKPART_EXCELINFO_COSNUM = "TASKPART_EXCELINFO_COSNUM";

    @RBEntry("姓名")
    public static final String TASKPART_EXCELINFO_COSNAME = "TASKPART_EXCELINFO_COSNAME";

    @RBEntry("性别")
    public static final String TASKPART_EXCELINFO_COSSEX = "TASKPART_EXCELINFO_COSSEX";

    @RBEntry("年龄")
    public static final String TASKPART_EXCELINFO_COSAGE = "TASKPART_EXCELINFO_COSAGE";

}
