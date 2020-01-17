package ext.example.part.resource;


import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * @author 段鑫扬
 */
@RBUUID("ext.example.part.resource.treeResource")
public final class treeResource extends WTListResourceBundle {
    @RBEntry("my tree action")
    public static final String TREE_TREEBUTTON_TITLE ="tree.treeButton.title";

    @RBEntry("my tree action")
    public static final String TREE_TREEBUTTON_DESC ="tree.treeButton.description";

    @RBEntry("my tree action")
    public static final String TREE_TREEBUTTON_TOOLTIP ="tree.treeButton.tooltip";

    @RBEntry("trees.gif")
    @RBPseudo(false)
    public static final String TREE_TREEBUTTON_ICON ="tree.treeButton.icon";

    @RBEntry("Part tree Table")
    public static final String TREE_LABEL ="Part Tree Table";

    @RBEntry("Default")
    public static final String TREE_NAME ="Default";

    @RBEntry("Default Tree Table View")
    public static final String TREE_DESC ="Default Tree Table View";

    @RBEntry("Xml Tree")
    public static final String TREE_XML_LABEL ="Xml Tree";

    @RBEntry("Xml Test Tree")
    public static final String TREE_XML_NAME ="Xml Test Tree";
}
