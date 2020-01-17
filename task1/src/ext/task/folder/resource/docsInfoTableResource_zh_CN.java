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
@RBUUID("docsInfoTableResource")
public final class docsInfoTableResource_zh_CN extends WTListResourceBundle {
    @RBEntry("所有文档")
    public static final String TASKFOLDER_ALLDOCSINFOACTION_TITLE = "taskFolder.allDocsInfoAction.title";

    @RBEntry("所有文档")
    public static final String TASKFOLDER_ALLDOCSINFOACTION_DESC = "taskFolder.allDocsInfoAction.description";

    @RBEntry("所有文档")
    public static final String TASKFOLDER_ALLDOCSINFOACTION_TOOLTIP = "taskFolder.allDocsInfoAction.tooltip";

    @RBEntry("iterate.png")
    @RBPseudo(false)
    public static final String TASKFOLDER_ALLDOCSINFOACTION_ICON = "taskFolder.allDocsInfoAction.icon";

    @RBEntry("所有文档信息")
    public static final String ALL_DOCS_INFO_TABLE = "all docs info table";

    @RBEntry("文档列表")
    public static final String DOC_TABLE_LABEL = "doc table label";

}
