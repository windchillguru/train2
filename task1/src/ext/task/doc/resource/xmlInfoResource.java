package ext.task.doc.resource;


import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * xml 的资源文件
 *
 * @author 段鑫扬
 */
@RBUUID("xmlInfoResource")
public final class xmlInfoResource extends WTListResourceBundle {
    @RBEntry("xml info")
    public static final String TASKDOC_XMLINFO_TITLE = "taskDoc.xmlInfo.title";

    @RBEntry("xml info")
    public static final String TASKDOC_XMLINFO_DESC = "taskDoc.xmlInfo.description";

    @RBEntry("xml info")
    public static final String TASKDOC_XMLINFO_TOOLTIP = "taskDoc.xmlInfo.tooltip";

    @RBEntry("iterate.png")
    @RBPseudo(false)
    public static final String TASKDOC_XMLINFO_ICON = "taskDoc.xmlInfo.icon";

}
