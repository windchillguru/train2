package ext.workflow.resource;

import wt.util.resource.RBEntry;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * 工作流相关资源文件
 *
 * @author 段鑫扬
 */
@RBUUID("ext.workflow.resource.workflowRB")
public class workflowRB_zh_CN extends WTListResourceBundle {
    @RBEntry("启动工作流 ")
    public static final String EXAMPLE_STARTWORKFLOW_TITLE = "example.startWorkflow.title";

    @RBEntry("启动工作流")
    public static final String EXAMPLE_STARTWORKFLOWDESC = "example.startWorkflow.description";

    @RBEntry("启动工作流 ")
    public static final String EXAMPLE_STARTWORKFLOW_TOOLTIP = "example.startWorkflow.tooltip";

    @RBEntry("确定启用工作流吗？")
    public static final String START_WORKFLOW_MSG = "START_WORKFLOW_MSG";

    @RBEntry("启动工作流成功")
    public static final String START_WORKFLOW_SUCCESS_MSG = "START_WORKFLOW_SUCCESS_MSG";
}
