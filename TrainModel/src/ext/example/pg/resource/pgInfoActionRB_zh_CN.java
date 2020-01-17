package ext.example.pg.resource;

import wt.util.resource.RBEntry;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * pg信息操作的资源类
 *
 * @author 段鑫扬
 * @version 2019/12/20
 */
@RBUUID("ext.example.pg.resource.pgInfoActionRB")
public class pgInfoActionRB_zh_CN extends WTListResourceBundle {
    @RBEntry("创建PG信息")
    public static final String PG_CREATEPGINFO_TITLE = "pg.createPGInfo.title";

    @RBEntry("创建PG信息")
    public static final String PG_CREATEPGINFO_DESC = "pg.createPGInfo.description";

    @RBEntry("创建PG信息")
    public static final String PG_CREATEPGINFO_TOOLTIP = "pg.createPGInfo.tooltip";

    @RBEntry("add16x16.gif")
    public static final String PG_CREATEPGINFO_ICON = "pg.createPGInfo.icon";

    @RBEntry("编辑PG信息")
    public static final String PG_EDITPGINFO_TITLE = "pg.editPGInfo.title";

    @RBEntry("编辑PG信息")
    public static final String PG_EDITPGINFO_DESC = "pg.editPGInfo.description";

    @RBEntry("编辑PG信息")
    public static final String PG_EDITPGINFO_TOOLTIP = "pg.editPGInfo.tooltip";

    @RBEntry("edit.gif")
    public static final String PG_EDITPGINFO_ICON = "pg.editPGInfo.icon";

    @RBEntry("移除PG信息")
    public static final String PG_REMOVEPGINFO_TITLE = "pg.removePGInfo.title";

    @RBEntry("移除PG信息")
    public static final String PG_REMOVEPGINFO_DESC = "pg.removePGInfo.description";

    @RBEntry("移除PG信息")
    public static final String PG_REMOVEPGINFO_TOOLTIP = "pg.removePGInfo.tooltip";

    @RBEntry("remove16x16.gif")
    public static final String PG_REMOVEPGINFO_ICON = "pg.removePGInfo.icon";

    @RBEntry("移除PG信息成功")
    public static final String PGINFO_REMOVE_SUCCESS_MSG = "PGINFO_REMOVE_SUCCESS_MSG";

    @RBEntry("确定要删除吗？")
    public static final String PGINFO_REMOVE_MSG = "PGINFO_REMOVE_MSG";
}
