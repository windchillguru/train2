package ext.example.part;

import wt.util.resource.*;

/**
 * @author 段鑫扬
 */
@RBUUID("PartResource")
public final class PartResource extends WTListResourceBundle {
    @RBEntry("my menu action")
    public static final String EXMAPLE_MYMENUACTION_TITLE ="exmaple.myMenuAction.title";

    @RBEntry("my menu action")
    public static final String EXMAPLE_MYMENUACTION_DESCRIPTION ="exmaple.myMenuAction.description";

    @RBEntry("my menu action")
    public static final String EXMAPLE_MYMENUACTION_TOOLTIP ="exmaple.myMenuAction.tooltip";

    @RBEntry("info.gif")
    @RBPseudo(false)
    @RBComment("")
    public static final String EXMAPLE_MYMENUACTION_ICON ="exmaple.myMenuAction.icon";
}
