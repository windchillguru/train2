package ext.task.part.filter;

import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;
import ext.pi.core.PIContainerHelper;
import org.apache.log4j.Logger;
import wt.doc.WTDocument;
import wt.fc.Persistable;
import wt.fc.WTReference;
import wt.inf.container.WTContainer;
import wt.inf.team.ContainerTeamManaged;
import wt.lifecycle.State;
import wt.log4j.LogR;
import wt.org.WTPrincipal;
import wt.project.Role;
import wt.util.WTException;
import wt.vc.VersionControlHelper;

/**
 * 修改标识的过滤器
 *
 * @author 段鑫扬
 */
public class ModifyDocIdentityFilter extends DefaultSimpleValidationFilter {
    private static final Logger LOGGER = LogR.getLogger(ModifyDocIdentityFilter.class.getName());

    /**
     * 正在工作、最新小版本、当前用户是设计工程师角色，才显示
     *
     * @param uiValidationKey
     * @param uiValidationCriteria
     * @return
     */
    @Override
    public UIValidationStatus preValidateAction(UIValidationKey uiValidationKey, UIValidationCriteria uiValidationCriteria) {
        UIValidationStatus status = UIValidationStatus.HIDDEN;//默认隐藏
        try {
            //获取按钮的信息
            WTContainer container = uiValidationCriteria.getParentContainer().getReferencedContainer();
            String actionName = uiValidationKey.getComponentID();
            String objectType = uiValidationKey.getObjectType();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("actionName: " + actionName + " ,objectType: " + objectType);
            }
            //获取上下文对象
            WTReference contextObject = uiValidationCriteria.getContextObject();
            if (contextObject != null && contextObject.getObject() != null) {
                Persistable persistable = contextObject.getObject();
                if (persistable instanceof WTDocument) {
                    //是部件对象
                    WTDocument document = (WTDocument) persistable;
                    //获取生命周期状态
                    String state = document.getLifeCycleState().toString();
                    ContainerTeamManaged containerTeamManaged = (ContainerTeamManaged) container;
                    //设计工程师
                    Role role = Role.toRole("DESIGN ENGINEER");
                    //用户
                    WTPrincipal principal = uiValidationCriteria.getUser().getPrincipal();
                    if (PIContainerHelper.service.isRolePrincipal(containerTeamManaged, role, principal))
                        //当前用户是设计工程师角色
                        if (State.INWORK.toString().equals(state)) {
                            //正在工作状态
                            if (VersionControlHelper.isLatestIteration(document)) {
                                //最新小版本
                                //可用
                                status = UIValidationStatus.ENABLED;
                            }
                        }
                }
            }
        } catch (WTException e) {
            e.printStackTrace();
        }
        return status;
    }
}
