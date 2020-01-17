package ext.train.report.filter;

import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;
import ext.pi.core.PIContainerHelper;
import org.apache.log4j.Logger;
import wt.inf.container.WTContainer;
import wt.inf.team.ContainerTeamManaged;
import wt.log4j.LogR;
import wt.org.WTPrincipal;
import wt.project.Role;
import wt.util.WTException;

/**
 * 导出BOM报表的过滤器
 *
 * @author 段鑫扬
 */
public class BOMPartFilter extends DefaultSimpleValidationFilter {
    private static final Logger LOGGER = LogR.getLogger(BOMPartFilter.class.getName());

    /**
     * 该按钮只有在部件未检出
     * ,视图为Design,最新小版本，
     * 且用户为站点管理员或者制造工程师的条件下才可见，否则置灰；
     *
     * @param uiValidationKey
     * @param uiValidationCriteria
     * @return
     */
    @Override
    public UIValidationStatus preValidateAction(UIValidationKey uiValidationKey, UIValidationCriteria uiValidationCriteria) {
        UIValidationStatus status = UIValidationStatus.DISABLED; //默认置灰
        try {
            //获取按钮的信息
            String actionName = uiValidationKey.getComponentID();
            String objectType = uiValidationKey.getObjectType();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("actionName: " + actionName + " ,objectType: " + objectType);
            }
            boolean enabled = false;
            //是否是结构工程师
            boolean isStructural = isStructural(uiValidationCriteria);
            if (uiValidationCriteria.isSiteAdmin() || isStructural) {
                //是站点管理员或是结构工程师
                enabled = true;
            } else {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(">>>> 当前用户不是站点管理员，并且不是结构工程师");
                }
            }
            if (enabled) {
                status = UIValidationStatus.ENABLED;//可见
            }
        } catch (WTException e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * 判断是否是结构工程师
     *
     * @param uiValidationCriteria
     * @return
     * @throws WTException
     */
    private boolean isStructural(UIValidationCriteria uiValidationCriteria) throws WTException {
        WTContainer container = uiValidationCriteria.getParentContainer().getReferencedContainer();
        ContainerTeamManaged containerTeamManaged = (ContainerTeamManaged) container;
        //制造工程师
        Role role = Role.toRole("STRUCTURAL ENGINEER");
        //用户
        WTPrincipal principal = uiValidationCriteria.getUser().getPrincipal();
        //判断是否是制造工程师
        boolean rolePrincipal = PIContainerHelper.service.isRolePrincipal(containerTeamManaged, role, principal);
        return rolePrincipal;
    }
}
