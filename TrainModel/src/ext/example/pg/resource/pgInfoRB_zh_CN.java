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
public class pgInfoRB_zh_CN extends WTListResourceBundle {
    @RBEntry("PG信息")
    public static final String PG_PGINFONAV_TITLE = "pg.pgInfoNav.title";

    @RBEntry("PG信息")
    public static final String PG_PGINFONAV_DESC = "pg.pgInfoNav.description";

    @RBEntry("PG信息")
    public static final String PG_PGINFONAV_TOOLTIP = "pg.pgInfoNav.tooltip";

    @RBEntry("PG用户")
    public static final String PGINFO_USER_TABLE = "PGINFO_USER_TABLE";//pg信息表

    @RBEntry("工号")
    public static final String EMPLOYEE_NO = "EMPLOYEE_NO";//工号

    @RBEntry("姓名")
    public static final String EMPLOYEE_NAME = "EMPLOYEE_NAME";//姓名

    @RBEntry("用户名")
    public static final String EMPLOYEE_USERNAME = "EMPLOYEE_USERNAME";//用户名

    @RBEntry("邮箱")
    public static final String EMPLOYEE_EMAIL = "EMPLOYEE_EMAIL";

    @RBEntry("电话")
    public static final String EMPLOYEE_PHONE = "EMPLOYEE_PHONE";

    @RBEntry("备注")
    public static final String COMMENTS = "COMMENTS";

    @RBEntry("有经验")
    public static final String EXPERIENCED = "EXPERIENCED";//是否有经验

    @RBEntry("信息来源")
    public static final String INFORMATION_SOURCE = "INFORMATION_SOURCE";//信息来源

    @RBEntry("简历")
    public static final String RESUMEINFO = "RESUMEINFO";//简历

    @RBEntry("信息编号")
    public static final String INFORNAITIONNO = "IFORPATIONO";//信息编号


    @RBEntry("组长")
    public static final String LEADER = "LEADER";//是否组长

    @RBEntry("PG组表")
    public static final String PGINFO_GROUP_TABLE = "PGINFO_GROUP_TABLE";

    @RBEntry("组名")
    public static final String PGGROUP_NANE = "PGGROUP_NANE";

    @RBEntry("启用")
    public static final String ENABLED = "ENABLED";

    @RBEntry("根组")
    public static final String ROOT = "ROOT";//是否根节点

    @RBEntry("PG树")
    public static final String PGINFO_TREE_TABLE = "PGINFO_TREE_TABLE";

    @RBEntry("PG名称")
    public static final String PG_NAME = "PG_NAME";

    @RBEntry("是否是组")
    public static final String IS_GROUP = "IS_GROUP";

}
