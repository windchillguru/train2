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
public final class excelInfoResource extends WTListResourceBundle {
    @RBEntry("taskPart excelInfo action")
    public static final String TASKPART_EXCELINFO_TITLE = "taskPart.excelInfo.title";

    @RBEntry("taskPart excelInfo action")
    public static final String TASKPART_EXCELINFO_DESC = "taskPart.excelInfo.description";

    @RBEntry("taskPart excelInfo action")
    public static final String TASKPART_EXCELINFO_TOOLTIP = "taskPart.excelInfo.tooltip";

    @RBEntry("iterate.png")
    @RBPseudo(false)
    public static final String TASKPART_EXCELINFO_ICON = "taskPart.excelInfo.icon";

    @RBEntry("excelInfo title")
    public static final String TASKPART_EXCEL_TOOLTIP = "TASKPART_EXCEL_TITLE";

    @RBEntry("cosNum")
    public static final String TASKPART_EXCELINFO_COSNUM = "TASKPART_EXCELINFO_COSNUM";

    @RBEntry("cosName")
    public static final String TASKPART_EXCELINFO_COSNAME = "TASKPART_EXCELINFO_COSNAME";

    @RBEntry("cosSex")
    public static final String TASKPART_EXCELINFO_COSSEX = "TASKPART_EXCELINFO_COSSEX";

    @RBEntry("cosAge")
    public static final String TASKPART_EXCELINFO_COSAGE = "TASKPART_EXCELINFO_COSAGE";

}
