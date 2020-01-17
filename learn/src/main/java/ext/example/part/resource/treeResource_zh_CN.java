package ext.example.part.resource;


import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * @author 段鑫扬
 */
@RBUUID("ext.example.part.resource.treeResource")
public final class treeResource_zh_CN extends WTListResourceBundle {
    @RBEntry("树表格")
    public static final String TREE_TREEBUTTON_TITLE ="tree.treeButton.title";

    @RBEntry("树表格")
    public static final String TREE_TREEBUTTON_DESC ="tree.treeButton.description";

    @RBEntry("树表格")
    public static final String TREE_TREEBUTTON_TOOLTIP ="tree.treeButton.tooltip";

    @RBEntry("trees.gif")
    @RBPseudo(false)
    public static final String TREE_TREEBUTTON_ICON ="tree.treeButton.icon";

    @RBEntry("树表格")
    public static final String TREE_LABEL ="Part Tree Table";

    @RBEntry("默认")
    public static final String TREE_NAME ="Default";

    @RBEntry("默认视图")
    public static final String TREE_DESC ="Default Tree Table View";

    @RBEntry("Xml 树")
    public static final String TREE_XML_LABEL ="Xml Tree";

    @RBEntry("Xml 测试")
    public static final String TREE_XML_NAME ="Xml Test Tree";
}
