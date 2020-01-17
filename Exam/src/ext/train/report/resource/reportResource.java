package ext.train.report.resource;

import wt.util.resource.RBEntry;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * 导出报表资源
 *
 * @author 段鑫扬
 */
@RBUUID("ext.train.report.resource.reportResource")
public class reportResource extends WTListResourceBundle {
    @RBEntry("query top part")
    public static final String REPORT_QUERYTOPPART_TITLE = "report.queryTopPart.title";

    @RBEntry("query top part")
    public static final String REPORT_QUERYTOPPART_DESC = "report.queryTopPart.description";

    @RBEntry("SELECT_FILE")
    public static final String SELECT_FILE = "SELECT_FILE";

    @RBEntry("SELECT_FILE_INVALID_ERR")
    public static final String SELECT_FILE_INVALID_ERR = "SELECT_FILE_INVALID_ERR";

    @RBEntry("SELECT_FILE_NONE_ERR")
    public static final String SELECT_FILE_NONE_ERR = "SELECT_FILE_NONE_ERR";

    @RBEntry("BOM report")
    public static final String REPORT_BOMPART_TITLE = "report.BOMPart.title";

    @RBEntry("BOM report")
    public static final String REPORT_BOMPART_DESC = "report.BOMPart.description";

}
