package ext.exam.doc.resource;


import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * 文档的资源文件
 *
 * @author 段鑫扬
 */
@RBUUID("ext.exam.doc.resource.docResource")
public final class docResource_zh_CN extends WTListResourceBundle {
    @RBEntry("查询文档")
    public static final String EXAM_QUERYDOC_TITLE = "exam.queryDoc.title";

    @RBEntry("查询文档")
    public static final String EXAM_QUERYDOC_DESC = "exam.queryDoc.description";

    @RBEntry("查询文档")
    public static final String EXAM_QUERYDOC_TOOLTIP = "exam.queryDoc.tooltip";

    @RBEntry("navigation_search.gif")
    @RBPseudo(false)
    public static final String EXAM_QUERYDOC_ICON = "exam.queryDoc.icon";

    @RBEntry("报表管理")
    public static final String NAVIGATION_REPORTMANAGEMENT_TITLE = "navigation.reportManagement.title";

    @RBEntry("报表管理")
    public static final String NAVIGATION_REPORTMANAGEMENT_DESCRIPTION = "navigation.reportManagement.description";

    @RBEntry("报表管理")
    public static final String NAVIGATION_REPORTMANAGEMENT_TOOLTIP = "navigation.reportManagement.tooltip";

    @RBEntry("navigation_search.gif")
    public static final String NAVIGATION_REPORTMANAGEMENT_ICON = "navigation.reportManagement.icon";

    @RBEntry("名称")
    public static final String SEARCH_NAME = "SEARCH_NAME";

    @RBEntry("编号")
    public static final String SEARCH_NUMBER = "SEARCH_NUMBER";

    @RBEntry("客户名称")
    public static final String SEARCH_CUSTOMERNAME = "SEARCH_CUSTOMERNAME";

    @RBEntry("搜索")
    public static final String SEARCH_BUTTON = "SEARCH_BUTTON";

    @RBEntry("重置")
    public static final String SEARCH_RESET_BUTTON = "SEARCH_RESET_BUTTON";

}
