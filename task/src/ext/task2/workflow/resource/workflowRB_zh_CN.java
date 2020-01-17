package ext.task2.workflow.resource;

import wt.util.resource.RBEntry;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * @author 段鑫扬
 * @version 2020/1/10
 * 工作流相关报表的资源类
 */
@RBUUID("ext.task2.workflow.resource.workflowRB")
public class workflowRB_zh_CN extends WTListResourceBundle {

    @RBEntry("结案资料通过率")
    public static final String TASK_WFREPORT_TITLE = "task.wfReport.title";

    @RBEntry("结案资料通过率")
    public static final String TASK_WFREPORT_DESCRIPTION = "task.wfReport.description";

    @RBEntry("结案资料通过率")
    public static final String TASK_WFREPORT_TOOLTIP = "task.wfReport.tooltip";


}
