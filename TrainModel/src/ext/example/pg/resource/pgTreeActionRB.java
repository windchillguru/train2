package ext.example.pg.resource;

import wt.util.resource.RBEntry;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * pg树操作的资源类
 *
 * @author 段鑫扬
 * @version 2019/12/20
 */
@RBUUID("ext.example.pg.resource.pgTreeActionRB")
public class pgTreeActionRB extends WTListResourceBundle {
    @RBEntry("add pg member")
    public static final String PG_ADDPGMEMBER_TITLE = "pg.addPGMember.title";

    @RBEntry("add pg member")
    public static final String PG_ADDPGMEMBER_DESC = "pg.addPGMember.description";

    @RBEntry("add pg member")
    public static final String PG_ADDPGMEMBER_TOOLTIP = "pg.addPGMember.tooltip";

    @RBEntry("remove pg member")
    public static final String PG_REMOVEPGGROUP_TITLE = "pg.removePGMember.title";

    @RBEntry("remove pg member")
    public static final String PG_REMOVEPGGROUP_DESC = "pg.removePGMember.description";

    @RBEntry("remove pg member")
    public static final String PG_REMOVEPGGROUP_TOOLTIP = "pg.removePGMember.tooltip";

    @RBEntry("remove16x16.gif")
    public static final String PG_REMOVEPGGROUP_ICON = "pg.removePGMember.icon";

    @RBEntry("PGMEMBER_REMOVE_SUCCESS_MSG")
    public static final String PGMEMBER_REMOVE_SUCCESS_MSG = "PGMEMBER_REMOVE_SUCCESS_MSG";

    @RBEntry("PGMEMBER_REMOVE_MSG")
    public static final String PGMEMBER_REMOVE_MSG = "PGMEMBER_REMOVE_MSG";

    @RBEntry("PG_NAME")
    public static final String PG_NAME = "PG_NAME";

    @RBEntry("SEARCH_BUTTON")
    public static final String SEARCH_BUTTON = "SEARCH_BUTTON";

    @RBEntry("SEARCH_RESET_BUTTON")
    public static final String SEARCH_RESET_BUTTON = "SEARCH_RESET_BUTTON";
}
