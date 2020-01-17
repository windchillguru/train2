package ext.example.part.resource;


import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * @author 段鑫扬
 */
@RBUUID("ext.example.part.resource.xmlTreeResource")
public final class xmlTreeResource extends WTListResourceBundle {
    @RBEntry("my tree action")
    public static final String XMLTREE_XMLTREEBUTTON_TITLE ="xmlTree.xmlTreeButton.title";

    @RBEntry("my tree action")
    public static final String XMLTREE_XMLTREEBUTTON_DESC ="xmlTree.xmlTreeButton.description";

    @RBEntry("my tree action")
    public static final String XMLTREE_XMLTREEBUTTON_TOOLTIP ="xmlTree.xmlTreeButton.tooltip";

    @RBEntry("trees.gif")
    @RBPseudo(false)
    public static final String XMLTREE_XMLTREEBUTTON_ICON ="xmlTree.xmlTreeButton.icon";


}
