package ext.example.part.resource;


import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * @author 段鑫扬
 */
@RBUUID("ext.example.part.buttonResource")
public final class tableResource extends WTListResourceBundle {
    @RBEntry("my table action")
    public static final String EXMAPLE_MYBUTTONACTION_TITLE ="example2.myButtonAction.title";

    @RBEntry("my menu action")
    public static final String EXMAPLE_MYBUTTONACTION_DESC ="example2.myButtonAction.description";

    @RBEntry("my menu action")
    public static final String EXMAPLE_MYBUTTONACTION_TOOLTIP ="example2.myButtonAction.tooltip";

    @RBEntry("info.gif")
    @RBPseudo(false)
    public static final String EXMAPLE_MYBUTTONACTION_ICON ="example2.myMenuAction.icon";

    @RBEntry("Part List Table")
    public static final String TABLE_LABEL ="Part List Table";

    @RBEntry("Default")
    public static final String TABLE_NAME ="Default";

    @RBEntry("Default Table View")
    public static final String TABLE_DESC ="Default Table View";
}
