package ext.task.doc.resource;


import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * 文档信息的资源文件
 *
 * @author 段鑫扬
 */
@RBUUID("docInfoResource")
public final class docInfoResource extends WTListResourceBundle {
    @RBEntry("doc info action")
    public static final String TASKDOC_DOCINFOACTION_TITLE = "taskDoc.docInfoAction.title";

    @RBEntry("doc info action")
    public static final String TASKDOC_DOCINFOACTION_DESC = "taskDoc.docInfoAction.description";

    @RBEntry("doc info action")
    public static final String TASKDOC_DOCINFOACTION_TOOLTIP = "taskDoc.docInfoAction.tooltip";

    @RBEntry("iterate.png")
    @RBPseudo(false)
    public static final String TASKDOC_DOCINFOACTION_ICON = "taskDoc.docInfoAction.icon";

}
