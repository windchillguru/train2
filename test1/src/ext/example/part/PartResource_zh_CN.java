package ext.example.part;

import wt.util.resource.*;

/**
 * @author 段鑫扬
 */
@RBUUID("PartResource_zh_CN")
public final class PartResource_zh_CN extends WTListResourceBundle {
    @RBEntry("菜单测试")
    public static final String EXMAPLE_MYMENUACTION_TITLE ="exmaple.myMenuAction.title";

    @RBEntry("菜单测试")
    public static final String EXMAPLE_MYMENUACTION_DESCRIPTION ="exmaple.myMenuAction.description";

    @RBEntry("菜单测试")
    public static final String EXMAPLE_MYMENUACTION_TOOLTIP ="exmaple.myMenuAction.tooltip";

    @RBEntry("info.gif")
    @RBPseudo(false)
    @RBComment("")
    public static final String EXMAPLE_MYMENUACTION_ICON ="exmaple.myMenuAction.icon";
}
