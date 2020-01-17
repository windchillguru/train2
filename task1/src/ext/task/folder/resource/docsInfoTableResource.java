package ext.task.folder.resource;


import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * 所有文档信息的资源文件
 *
 * @author 段鑫扬
 */
@RBUUID("partsInfoTableResource")
public final class docsInfoTableResource extends WTListResourceBundle {
    @RBEntry("doc info table action")
    public static final String TASKFOLDER_ALLDOCSINFOACTION_TITLE = "taskFolder.allDocsInfoAction.title";

    @RBEntry("dco info table action")
    public static final String TASKFOLDER_ALLDOCSINFOACTION_DESC = "taskFolder.allDocsInfoAction.description";

    @RBEntry("dco info table action")
    public static final String TASKFOLDER_ALLDOCSINFOACTION_TOOLTIP = "taskFolder.allDocsInfoAction.tooltip";

    @RBEntry("iterate.png")
    @RBPseudo(false)
    public static final String TASKFOLDER_ALLDOCSINFOACTION_ICON = "taskFolder.allDocsInfoAction.icon";

    @RBEntry("all doc info")
    public static final String ALL_DOCS_INFO_TABLE = "all docs info table";

    @RBEntry("doc table label")
    public static final String DOC_TABLE_LABEL = "doc table label";

}
