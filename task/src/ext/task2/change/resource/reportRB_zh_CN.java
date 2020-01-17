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
public class reportRB_zh_CN extends WTListResourceBundle {

    @RBEntry("报表管理")
    public static final String NAVIGATION_REPORTMANAGEMENT_TITLE = "navigation.reportManagement.title";

    @RBEntry("报表管理")
    public static final String NAVIGATION_REPORTMANAGEMENT_DESCRIPTION = "navigation.reportManagement.description";

    @RBEntry("报表管理")
    public static final String NAVIGATION_REPORTMANAGEMENT_TOOLTIP = "navigation.reportManagement.tooltip";

    @RBEntry("reports_24.png")
    @RBPseudo(false)
    public static final String NAVIGATION_REPORTMANAGEMENT_ICON = "navigation.reportManagement.icon";

    @RBEntry("产品问题反馈及时回复率")
    public static final String TASK_CHANGEREPORT_TITLE = "task.changeReport.title";

    @RBEntry("产品问题反馈及时回复率")
    public static final String TASK_CHANGEREPORT_DESCRIPTION = "task.changeReport.description";

    @RBEntry("产品问题反馈及时回复率")
    public static final String TASK_CHANGEREPORT_TOOLTIP = "task.changeReport.tooltip";


}
