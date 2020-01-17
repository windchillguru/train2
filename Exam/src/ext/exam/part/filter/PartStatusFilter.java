package ext.exam.part.filter;

import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;
import ext.exam.part.IPartIfc;
import ext.pi.core.PIContainerHelper;
import ext.pi.core.PICoreHelper;
import ext.pi.core.PIPartHelper;
import org.apache.log4j.Logger;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.WTReference;
import wt.inf.container.WTContainer;
import wt.inf.team.ContainerTeamManaged;
import wt.log4j.LogR;
import wt.org.WTPrincipal;
import wt.part.WTPart;
import wt.project.Role;
import wt.util.WTException;

/**
 * 设置部件状态的过滤器
 *
 * @author 段鑫扬
 */
public class PartStatusFilter extends DefaultSimpleValidationFilter {
    private static final Logger LOGGER = LogR.getLogger(PartStatusFilter.class.getName());

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
            //是否是制造工程师
            boolean isManufacturing = isManufacturing(uiValidationCriteria);
            if (uiValidationCriteria.isSiteAdmin() || isManufacturing) {
                //是站点管理员或是制造工程师
                //获取上下文对象
                WTReference contextObject = uiValidationCriteria.getContextObject();
                if (contextObject != null && contextObject.getObject() != null) {
                    Persistable persistable = contextObject.getObject();
                    if (persistable instanceof WTPart) {
                        //是部件对象
                        WTPart part = (WTPart) persistable;
                        //是否检出
                        boolean checkout = PICoreHelper.service.isCheckout(part);
                        if (!checkout) { //未检出
                            String viewName = part.getView().getName();
                            if (IPartIfc.DESIGN_VIEW.equals(viewName)) { //设计视图
                                String number = part.getNumber();
                                //最新小版本 部件对象
                                WTPart wtPart = PIPartHelper.service.findWTPart(number, IPartIfc.DESIGN_VIEW);
                                //比较2个持久化对象是否一致
                                boolean equivalent = PersistenceHelper.isEquivalent(wtPart, part);
                                if (equivalent) {
                                    //是最新小版本
                                    enabled = true;
                                } else {
                                    if (LOGGER.isDebugEnabled()) {
                                        LOGGER.debug(">>>> part 不是最新小版本 ");
                                    }
                                }
                            } else {
                                if (LOGGER.isDebugEnabled()) {
                                    LOGGER.debug(">>>> part不是设计视图");
                                }
                            }
                        } else {
                            if (LOGGER.isDebugEnabled()) {
                                LOGGER.debug(">>>>part 已检出 ");
                            }
                        }
                    }
                }
            } else {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(">>>> 当前用户不是站点管理员，并且不是制造工程师");
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
