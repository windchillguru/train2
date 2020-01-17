package ext.task.part.validator;

import com.ptc.core.ui.validation.*;
import org.apache.log4j.Logger;
import wt.fc.Persistable;
import wt.fc.WTReference;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.util.WTException;

import java.util.Locale;

/**
 * 物料信息按钮的验证器
 *
 * @author 段鑫扬
 */
public class PartInfoActionValidator extends DefaultUIComponentValidator {

    private static final Logger LOGGER = LogR.getLogger(PartInfoActionValidator.class.getName());

    /**
     * @param uiValidationKey      获取按钮（ui)的信息
     * @param uiValidationCriteria 获取当前用户信息
     * @param locale
     * @return
     * @throws WTException
     */
    @Override
    public UIValidationResultSet performFullPreValidation(UIValidationKey uiValidationKey, UIValidationCriteria uiValidationCriteria, Locale locale) throws WTException {
        UIValidationResultSet uiValidationResultSet = UIValidationResultSet.newInstance();
        //获取按钮的信息
        String actionName = uiValidationKey.getComponentID();
        String objectType = uiValidationKey.getObjectType();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("actionName: " + actionName + " ,objectType: " + objectType);
        }
        boolean enabled = false;
        if (uiValidationCriteria.isSiteAdmin() || uiValidationCriteria.isOrgAdmin()) {
            //是组织或是站点管理员
            //获取上下文对象
            WTReference contextObject = uiValidationCriteria.getContextObject();
            if (contextObject != null && contextObject.getObject() != null) {
                Persistable persistable = contextObject.getObject();
                if (persistable instanceof WTPart) {
                    //是部件对象
                    WTPart part = (WTPart) persistable;
                    //获取生命周期状态
                    String state = part.getLifeCycleState().toString();
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("part.display: " + part.getDisplayIdentifier() + ",state:" + state);
                    }

                    if ("RELEASED".equals(state)) {
                        //已发布状态
                        enabled = true;
                    }

                }
            }
        }

        if (enabled) {
            //显示
            uiValidationResultSet.addResult(UIValidationResult.newInstance(uiValidationKey, UIValidationStatus.ENABLED));
        } else {
            uiValidationResultSet.addResult(UIValidationResult.newInstance(uiValidationKey, UIValidationStatus.DISABLED));
        }
        return uiValidationResultSet;
    }
}
