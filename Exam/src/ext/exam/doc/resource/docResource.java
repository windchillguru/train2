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
public final class docResource extends WTListResourceBundle {
    @RBEntry("query Doc")
    public static final String EXAM_QUERYDOC_TITLE = "exam.queryDoc.title";

    @RBEntry("query Doc")
    public static final String EXAM_QUERYDOC_DESC = "exam.queryDoc.description";

    @RBEntry("query Doc")
    public static final String EXAM_QUERYDOC_TOOLTIP = "exam.queryDoc.tooltip";

    @RBEntry("navigation_search.gif")
    @RBPseudo(false)
    public static final String EXAM_QUERYDOC_ICON = "exam.queryDoc.icon";

    @RBEntry("Report management")
    public static final String NAVIGATION_REPORTMANAGEMENT_TITLE = "navigation.reportManagement.title";

    @RBEntry("Report management")
    public static final String NAVIGATION_REPORTMANAGEMENT_DESCRIPTION = "navigation.reportManagement.description";

    @RBEntry("Report management")
    public static final String NAVIGATION_REPORTMANAGEMENT_TOOLTIP = "navigation.reportManagement.tooltip";

    @RBEntry("navigation_search.gif")
    public static final String NAVIGATION_REPORTMANAGEMENT_ICON = "navigation.reportManagement.icon";

    @RBEntry("SEARCH_NAME")
    public static final String SEARCH_NAME = "SEARCH_NAME";

    @RBEntry("SEARCH_NUMBER")
    public static final String SEARCH_NUMBER = "SEARCH_NUMBER";

    @RBEntry("SEARCH_CUSTOMERNAME")
    public static final String SEARCH_CUSTOMERNAME = "SEARCH_CUSTOMERNAME";

    @RBEntry("SEARCH_BUTTON")
    public static final String SEARCH_BUTTON = "SEARCH_BUTTON";

    @RBEntry("SEARCH_RESET_BUTTON")
    public static final String SEARCH_RESET_BUTTON = "SEARCH_RESET_BUTTON";


}
