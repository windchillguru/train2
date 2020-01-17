package ext.task2.change.resource;

import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * @author 段鑫扬
 * @version 2020/1/10
 */
@RBUUID("ext.task2.change.resource.reportRB")
public class reportRB extends WTListResourceBundle {

    @RBEntry("ReportManagement")
    public static final String NAVIGATION_REPORTMANAGEMENT_TITLE = "navigation.reportManagement.title";

    @RBEntry("ReportManagement")
    public static final String NAVIGATION_REPORTMANAGEMENT_DESCRIPTION = "navigation.reportManagement.description";

    @RBEntry("ReportManagement")
    public static final String NAVIGATION_REPORTMANAGEMENT_TOOLTIP = "navigation.reportManagement.tooltip";

    @RBEntry("reports_24.png")
    @RBPseudo(false)
    public static final String NAVIGATION_REPORTMANAGEMENT_ICON = "navigation.reportManagement.icon";

    @RBEntry("Change Report")
    public static final String TASK_CHANGEREPORT_TITLE = "task.changeReport.title";

    @RBEntry("Change Report")
    public static final String TASK_CHANGEREPORT_DESCRIPTION = "task.changeReport.description";

    @RBEntry("Change Report")
    public static final String TASK_CHANGEREPORT_TOOLTIP = "task.changeReport.tooltip";


}
