package ext.example.pg.resource;

import wt.util.resource.RBEntry;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * pg信息的资源类
 *
 * @author 段鑫扬
 * @version 2019/12/20
 */
@RBUUID("ext.example.pg.resource.pgInfoRB")
public class pgInfoRB extends WTListResourceBundle {
    @RBEntry("pgInfoNav")
    public static final String PG_PGINFONAV_TITLE = "pg.pgInfoNav.title";

    @RBEntry("pgInfoNav")
    public static final String PG_PGINFONAV_DESC = "pg.pgInfoNav.description";

    @RBEntry("pgInfoNav")
    public static final String PG_PGINFONAV_TOOLTIP = "pg.pgInfoNav.tooltip";

    @RBEntry("PGINFO_USER_TABLE")
    public static final String PGINFO_USER_TABLE = "PGINFO_USER_TABLE";//pg信息表

    @RBEntry("EMPLOYEE_NO")
    public static final String EMPLOYEE_NO = "EMPLOYEE_NO";//工号

    @RBEntry("EMPLOYEE_NAME")
    public static final String EMPLOYEE_NAME = "EMPLOYEE_NAME";//姓名

    @RBEntry("EMPLOYEE_USERNAME")
    public static final String EMPLOYEE_USERNAME = "EMPLOYEE_USERNAME";//用户名

    @RBEntry("EMPLOYEE_EMAIL")
    public static final String EMPLOYEE_EMAIL = "EMPLOYEE_EMAIL";

    @RBEntry("EMPLOYEE_PHONE")
    public static final String EMPLOYEE_PHONE = "EMPLOYEE_PHONE";

    @RBEntry("COMMENTS")
    public static final String COMMENTS = "COMMENTS";

    @RBEntry("EXPERIENCED")
    public static final String EXPERIENCED = "EXPERIENCED";//是否有经验

    @RBEntry("INFORMATIONSOURCE")
    public static final String INFORMATION_SOURCE = "INFORMATION_SOURCE";//信息来源

    @RBEntry("RESUMEINFO")
    public static final String RESUMEINFO = "RESUMEINFO";//简历

    @RBEntry("IFORPATIONO")
    public static final String INFORNAITIONNO = "IFORPATIONO";//信息编号


    @RBEntry("LEADER")
    public static final String LEADER = "LEADER";

    @RBEntry("PGINFO_GROUP_TABLE")
    public static final String PGINFO_GROUP_TABLE = "PGINFO_GROUP_TABLE";

    @RBEntry("PGGROUP_NANE")
    public static final String PGGROUP_NANE = "PGGROUP_NANE";

    @RBEntry("ENABLED")
    public static final String ENABLED = "ENABLED";

    @RBEntry("ROOT")
    public static final String ROOT = "ROOT";

    @RBEntry("PGINFO_TREE_TABLE")
    public static final String PGINFO_TREE_TABLE = "PGINFO_TREE_TABLE";

    @RBEntry("PG_NAME")
    public static final String PG_NAME = "PG_NAME";

    @RBEntry("IS_GROUP")
    public static final String IS_GROUP = "IS_GROUP";
}
