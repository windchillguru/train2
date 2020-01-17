package ext.task.part.resource;


import wt.util.resource.RBEntry;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * 部件的修改标识的资源文件
 *
 * @author 段鑫扬
 */
@RBUUID("partAPIResource")
public final class partAPIResource_zh_CN extends WTListResourceBundle {
    @RBEntry("修改标识")
    public static final String TASKPART_MODIFYPARTIDENTITY_TITLE = "taskPart.modifyPartIdentity.title";

    @RBEntry("修改标识")
    public static final String TASKPART_MODIFYPARTIDENTITY_DESC = "taskPart.modifyPartIdentity.description";

    @RBEntry("修改标识")
    public static final String TASKPART_MODIFYPARTIDENTITY_TOOLTIP = "taskPart.modifyPartIdentity.tooltip";

    @RBEntry("新名称")
    public static final String MODIFY_PARTIDENTITY_NAME = "MODIFY_PARTIDENTITY_NAME";

    @RBEntry("新编号")
    public static final String MODIFY_PARTIDENTITY_NUMBER = "MODIFY_PARTIDENTITY_NUMBER";

    @RBEntry("修改标识")
    public static final String MODIFY_PARTIDENTITY_TITLE = "MODIFY_PARTIDENTITY_TITLE";

    @RBEntry("请修改编号或名称；否则，请点击取消")
    public static final String MODIFY_PARTIDENTITY_ERR1 = "MODIFY_PARTIDENTITY_ERR1";

    @RBEntry("修改标识成功，原标识{0},新标识{1}")
    public static final String MODIFY_PARTIDENTITY_SUCCESSMSG = "MODIFY_PARTIDENTITY_SUCCESSMSG";

    @RBEntry("修改标识失败，失败信息，{0}")
    public static final String MODIFY_PARTIDENTITY_FAILUREMSG = "MODIFY_PARTIDENTITY_FAILUREMSG";

    @RBEntry("关联文档")
    public static final String TASKPART_ASSOCIATEDDOCS_TITLE = "taskPart.associatedDocs.title";

    @RBEntry("关联文档")
    public static final String TASKPART_ASSOCIATEDDOCS_DESC = "taskPart.associatedDocs.description";

    @RBEntry("关联文档")
    public static final String TASKPART_ASSOCIATEDDOCS_TOOLTIP = "taskPart.associatedDocs.tooltip";

}
