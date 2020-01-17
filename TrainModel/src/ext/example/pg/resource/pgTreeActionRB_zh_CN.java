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
public class pgTreeActionRB_zh_CN extends WTListResourceBundle {
    @RBEntry("添加成员")
    public static final String PG_ADDPGMEMBER_TITLE = "pg.addPGMember.title";

    @RBEntry("添加成员")
    public static final String PG_ADDPGMEMBER_DESC = "pg.addPGMember.description";

    @RBEntry("添加成员")
    public static final String PG_ADDPGMEMBER_TOOLTIP = "pg.addPGMember.tooltip";

    @RBEntry("移除成员")
    public static final String PG_REMOVEPGGROUP_TITLE = "pg.removePGMember.title";

    @RBEntry("移除成员")
    public static final String PG_REMOVEPGGROUP_DESC = "pg.removePGMember.description";

    @RBEntry("移除成员")
    public static final String PG_REMOVEPGGROUP_TOOLTIP = "pg.removePGMember.tooltip";

    @RBEntry("remove16x16.gif")
    public static final String PG_REMOVEPGGROUP_ICON = "pg.removePGMember.icon";

    @RBEntry("移除成员成功")
    public static final String PGMEMBER_REMOVE_SUCCESS_MSG = "PGMEMBER_REMOVE_SUCCESS_MSG";

    @RBEntry("确定移除成员吗？")
    public static final String PGMEMBER_REMOVE_MSG = "PGMEMBER_REMOVE_MSG";

    @RBEntry("PG名称")
    public static final String PG_NAME = "PG_NAME";

    @RBEntry("搜索")
    public static final String SEARCH_BUTTON = "SEARCH_BUTTON";

    @RBEntry("重置")
    public static final String SEARCH_RESET_BUTTON = "SEARCH_RESET_BUTTON";
}
