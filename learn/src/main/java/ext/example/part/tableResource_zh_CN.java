package ext.example.part;


import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * @author 段鑫扬
 */
@RBUUID("ext.example.part.buttonResource")
public final class tableResource_zh_CN extends WTListResourceBundle {
    @RBEntry("部品列表")
    public static final String EXMAPLE_MYBUTTONACTION_TITLE ="example2.myButtonAction.title";

    @RBEntry("部品列表")
    public static final String EXMAPLE_MYBUTTONACTION_DESC ="example2.myButtonAction.description";

    @RBEntry("部品列表")
    public static final String EXMAPLE_MYBUTTONACTION_TOOLTIP ="example2.myButtonAction.tooltip";

    @RBEntry("info.gif")
    @RBPseudo(false)
    public static final String EXMAPLE_MYBUTTONACTION_ICON ="example2.myMenuAction.icon";

    @RBEntry("测试配置表格")
    public static final String TABLE_LABEL ="Part List Table";

    @RBEntry("默认视图")
    public static final String TABLE_NAME ="Default";

    @RBEntry("默认表格视图")
    public static final String TABLE_DESC ="Default Table View";
}
