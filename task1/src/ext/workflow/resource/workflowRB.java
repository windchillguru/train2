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
public class workflowRB extends WTListResourceBundle {
    @RBEntry("start workflow ")
    public static final String EXAMPLE_STARTWORKFLOW_TITLE = "example.startWorkflow.title";

    @RBEntry("start workflow")
    public static final String EXAMPLE_STARTWORKFLOWDESC = "example.startWorkflow.description";

    @RBEntry("start workflow ")
    public static final String EXAMPLE_STARTWORKFLOW_TOOLTIP = "example.startWorkflow.tooltip";

    @RBEntry(" START_WORKFLOW_MSG")
    public static final String START_WORKFLOW_MSG = "START_WORKFLOW_MSG";

    @RBEntry("START_WORKFLOW_SUCCESS_MSG")
    public static final String START_WORKFLOW_SUCCESS_MSG = "START_WORKFLOW_SUCCESS_MSG";
}
