package ext.task.doc.resource;


import wt.util.resource.RBEntry;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * 修改文档内容的资源文件
 *
 * @author 段鑫扬
 */
@RBUUID("docAPIResource")
public final class docAPIResource_zh_CN extends WTListResourceBundle {
    @RBEntry("更新主内容")
    public static final String TASKDOC_UPDATEPRIMARYCONTENT_TITLE = "taskDoc.updatePrimaryContent.title";

    @RBEntry("更新主内容")
    public static final String TASKDOC_UPDATEPRIMARYCONTENT_DESC = "taskDoc.updatePrimaryContent.description";

    @RBEntry("更新主内容")
    public static final String TASKDOC_UPDATEPRIMARYCONTENT_TOOLTIP = "taskDoc.updatePrimaryContent.tooltip";

    @RBEntry("更新主内容")
    public static final String UPDATE_PRIMARY_CONTENT_TITLE = "UPDATE_PRIMARY_CONTENT_TITLE";

    @RBEntry("成功更新主内容")
    public static final String UPDATE_PRIMARY_CONTENT_SUCCESSMSG = "UPDATE_PRIMARY_CONTENT_SUCCESSMSG";

    @RBEntry("选择文件")
    public static final String SELECT_FILE = "SELECT_FILE";

    @RBEntry("请选择文件")
    public static final String SELECT_FILE_NONE_ERR = "SELECT_FILE_NONE_ERR";

    @RBEntry("请选择Excel文件")
    public static final String SELECT_FILE_INVALID_ERR = "SELECT_FILE_INVALID_ERR";

    @RBEntry("添加附件")
    public static final String TASKDOC_ADDSECONDARIES_TITLE = "taskDoc.addSecondaries.title";

    @RBEntry("添加附件")
    public static final String TASKDOC_ADDSECONDARIES_DESC = "taskDoc.addSecondaries.description";

    @RBEntry("添加附件")
    public static final String TASKDOC_ADDSECONDARIES_TOOLTIP = "taskDoc.addSecondaries.tooltip";

    @RBEntry("添加附件")
    public static final String ADD_SECONDARY_TITLE = "ADD_SECONDARY_TITLE";

    @RBEntry("添加附件成功")
    public static final String ADD_SECONDARY_SUCCESSMSG = "ADD_SECONDARY_SUCCESSMSG";

    @RBEntry("修改标识")
    public static final String TASKDOC_MODIFYDOCIDENTITY_TITLE = "taskDoc.modifyDocIdentity.title";

    @RBEntry("修改标识")
    public static final String TASKDOC_MODIFYDOCIDENTITY_DESC = "taskDoc.modifyDocIdentity.description";

    @RBEntry("修改标识")
    public static final String TASKDOC_MODIFYDOCIDENTITY_TOOLTIP = "taskDoc.modifyDocIdentity.tooltip";

    @RBEntry("新名称")
    public static final String MODIFY_DOCIDENTITY_NAME = "MODIFY_DOCIDENTITY_NAME";

    @RBEntry("新编号")
    public static final String MODIFY_DOCIDENTITY_NUMBER = "MODIFY_DOCIDENTITY_NUMBER";

    @RBEntry("修改标识")
    public static final String MODIFY_DOCIDENTITY_TITLE = "MODIFY_DOCIDENTITY_TITLE";

    @RBEntry("请修改编号或名称；否则，请点击取消")
    public static final String MODIFY_DOCIDENTITY_ERR1 = "MODIFY_DOCIDENTITY_ERR1";

    @RBEntry("修改标识成功，原标识{0},新标识{1}")
    public static final String MODIFY_DOCIDENTITY_SUCCESSMSG = "MODIFY_DOCIDENTITY_SUCCESSMSG";

    @RBEntry("修改标识失败，失败信息，{0}")
    public static final String MODIFY_DOCIDENTITY_FAILUREMSG = "MODIFY_DOCIDENTITY_FAILUREMSG";

}