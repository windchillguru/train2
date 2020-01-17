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
public class pgInfoActionRB extends WTListResourceBundle {
    @RBEntry("create pg info")
    public static final String PG_CREATEPGINFO_TITLE = "pg.createPGInfo.title";

    @RBEntry("create pg info")
    public static final String PG_CREATEPGINFO_DESC = "pg.createPGInfo.description";

    @RBEntry("create pg info")
    public static final String PG_CREATEPGINFO_TOOLTIP = "pg.createPGInfo.tooltip";

    @RBEntry("add16x16.gif")
    public static final String PG_CREATEPGINFO_ICON = "pg.createPGInfo.icon";

    @RBEntry("edit pg info")
    public static final String PG_EDITPGINFO_TITLE = "pg.editPGInfo.title";

    @RBEntry("edit pg info")
    public static final String PG_EDITPGINFO_DESC = "pg.editPGInfo.description";

    @RBEntry("edit pg info")
    public static final String PG_EDITPGINFO_TOOLTIP = "pg.editPGInfo.tooltip";

    @RBEntry("edit.gif")
    public static final String PG_EDITPGINFO_ICON = "pg.editPGInfo.icon";

    @RBEntry("remove pg info")
    public static final String PG_REMOVEPGINFO_TITLE = "pg.removePGInfo.title";

    @RBEntry("remove pg info")
    public static final String PG_REMOVEPGINFO_DESC = "pg.removePGInfo.description";

    @RBEntry("remove pg info")
    public static final String PG_REMOVEPGINFO_TOOLTIP = "pg.removePGInfo.tooltip";

    @RBEntry("remove16x16.gif")
    public static final String PG_REMOVEPGINFO_ICON = "pg.removePGInfo.icon";

    @RBEntry("PGINFO_REMOVE_SUCCESS_MSG")
    public static final String PGINFO_REMOVE_SUCCESS_MSG = "PGINFO_REMOVE_SUCCESS_MSG";

    @RBEntry("PGINFO_REMOVE_MSG")
    public static final String PGINFO_REMOVE_MSG = "PGINFO_REMOVE_MSG";

}
