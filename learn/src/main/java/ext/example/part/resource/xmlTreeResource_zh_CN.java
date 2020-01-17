package ext.example.part.resource;


import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * @author 段鑫扬
 */
@RBUUID("ext.example.part.resource.xmlTreeResource")
public final class xmlTreeResource_zh_CN extends WTListResourceBundle {
    @RBEntry("xml树")
    public static final String XMLTREE_XMLTREEBUTTON_TITLE ="xmlTree.xmlTreeButton.title";

    @RBEntry("xml树")
    public static final String XMLTREE_XMLTREEBUTTON_DESC ="xmlTree.xmlTreeButton.description";

    @RBEntry("xml树")
    public static final String XMLTREE_XMLTREEBUTTON_TOOLTIP ="xmlTree.xmlTreeButton.tooltip";

    @RBEntry("trees.gif")
    @RBPseudo(false)
    public static final String XMLTREE_XMLTREEBUTTON_ICON ="xmlTree.xmlTreeButton.icon";


}
