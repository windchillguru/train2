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
public class reportResource_zh_CN extends WTListResourceBundle {
    @RBEntry("查询顶层物料")
    public static final String REPORT_QUERYTOPPART_TITLE = "report.queryTopPart.title";

    @RBEntry("查询顶层物料")
    public static final String REPORT_QUERYTOPPART_DESC = "report.queryTopPart.description";

    @RBEntry("选择文件：")
    public static final String SELECT_FILE = "SELECT_FILE";

    @RBEntry("无效文件，请选择TXT或者Excel文件！")
    public static final String SELECT_FILE_INVALID_ERR = "SELECT_FILE_INVALID_ERR";

    @RBEntry("请选择TXT或者Excel文件！")
    public static final String SELECT_FILE_NONE_ERR = "SELECT_FILE_NONE_ERR";

    @RBEntry("导出BOM报表")
    public static final String REPORT_BOMPART_TITLE = "report.BOMPart.title";

    @RBEntry("导出BOM报表")
    public static final String REPORT_BOMPART_DESC = "report.BOMPart.description";

}
