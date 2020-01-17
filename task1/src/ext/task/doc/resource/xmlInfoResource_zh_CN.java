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
@RBUUID("xmlInfoResource")
public final class xmlInfoResource_zh_CN extends WTListResourceBundle {
    @RBEntry("XML信息")
    public static final String TASKDOC_XMLINFO_TITLE = "taskDoc.xmlInfo.title";

    @RBEntry("XML信息")
    public static final String TASKDOC_XMLINFO_DESC = "taskDoc.xmlInfo.description";

    @RBEntry("XML信息")
    public static final String TASKDOC_XMLINFO_TOOLTIP = "taskDoc.xmlInfo.tooltip";

    @RBEntry("iterate.png")
    @RBPseudo(false)
    public static final String TASKDOC_XMLINFO_ICON = "taskDoc.xmlInfo.icon";

}
