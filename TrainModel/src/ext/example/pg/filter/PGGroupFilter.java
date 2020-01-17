package ext.example.pg.filter;

import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;
import ext.example.pg.model.PGGroup;
import org.apache.log4j.Logger;
import wt.fc.Persistable;
import wt.log4j.LogR;

/**
 * PG组的过滤器
 *
 * @author 段鑫扬
 */
public class PGGroupFilter extends DefaultSimpleValidationFilter {
    private static final Logger LOGGER = LogR.getLogger(PGGroupFilter.class.getName());

    /**
     * 在禁用时可以看到启用，不能看到编辑
     * 在启用时可以看到禁用
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
            //获取上下文对象
            Persistable contextObject = uiValidationCriteria.getContextObject().getObject();
            if (contextObject instanceof PGGroup) {
                PGGroup pgGroup = (PGGroup) contextObject;
                if ("enablePGGroup".equals(actionName)) {
                    if (pgGroup.getEnabled()) {
                        return UIValidationStatus.DISABLED;
                    } else {
                        //禁用状态下需要可用
                        return UIValidationStatus.ENABLED;
                    }
                }
                if ("disablePGGroup".equals(actionName)) {//禁用按钮
                    if (pgGroup.getEnabled()) {//启用
                        //启用状态下需要可用
                        return UIValidationStatus.ENABLED;
                    } else {
                        return UIValidationStatus.DISABLED;
                    }
                }
                if ("editPGGroup".equals(actionName)) {//编辑按钮
                    if (pgGroup.getEnabled()) {//启用
                        //启用状态下需要可用
                        return UIValidationStatus.ENABLED;
                    } else {
                        return UIValidationStatus.DISABLED;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


}
