package ext.exam.part.filter;

import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;
import ext.exam.part.IPartIfc;
import ext.pi.core.PIContainerHelper;
import org.apache.log4j.Logger;
import wt.fc.Persistable;
import wt.fc.WTReference;
import wt.inf.container.WTContainer;
import wt.inf.team.ContainerTeamManaged;
import wt.log4j.LogR;
import wt.org.WTPrincipal;
import wt.part.WTPart;
import wt.project.Role;
import wt.util.WTException;

/**
 * 生成正式码的过滤器
 *
 * @author 段鑫扬
 */
public class GenNumberFilter extends DefaultSimpleValidationFilter {
    private static final Logger LOGGER = LogR.getLogger(GenNumberFilter.class.getName());

    /**
     * 该按钮只有在部件不是正式码时可见
     *
     * @param uiValidationKey
     * @param uiValidationCriteria
     * @return
     */
    @Override
    public UIValidationStatus preValidateAction(UIValidationKey uiValidationKey, UIValidationCriteria uiValidationCriteria) {
        UIValidationStatus status = UIValidationStatus.HIDDEN; //默认隐藏
        try {
            //获取按钮的信息
            String actionName = uiValidationKey.getComponentID();
            String objectType = uiValidationKey.getObjectType();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("actionName: " + actionName + " ,objectType: " + objectType);
            }
            boolean enabled = false;
            //获取上下文对象
            WTReference contextObject = uiValidationCriteria.getContextObject();
            if (contextObject != null && contextObject.getObject() != null) {
                Persistable persistable = contextObject.getObject();
                if (persistable instanceof WTPart) {
                    //是部件对象
                    WTPart part = (WTPart) persistable;
                    String number = part.getNumber();
                    String unitValue = part.getDefaultUnit().toString();
                    if (!number.startsWith(unitValue.toUpperCase())) {
                        //不是正式码
                        enabled = true;
                    }
                }
            }
            if (enabled) {
                status = UIValidationStatus.ENABLED;//可见
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * 判断是否是制造工程师
     *
     * @param uiValidationCriteria
     * @return
     * @throws WTException
     */
    private boolean isManufacturing(UIValidationCriteria uiValidationCriteria) throws WTException {
        WTContainer container = uiValidationCriteria.getParentContainer().getReferencedContainer();
        ContainerTeamManaged containerTeamManaged = (ContainerTeamManaged) container;
        //制造工程师
        Role role = Role.toRole(IPartIfc.MANUFACTURING_ENGINEER);
        //用户
        WTPrincipal principal = uiValidationCriteria.getUser().getPrincipal();
        //判断是否是制造工程师
        boolean rolePrincipal = PIContainerHelper.service.isRolePrincipal(containerTeamManaged, role, principal);
        return rolePrincipal;
    }
}
