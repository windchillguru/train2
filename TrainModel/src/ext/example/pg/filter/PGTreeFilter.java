package ext.example.pg.filter;

import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;
import ext.example.pg.model.PGGroup;
import ext.example.pg.model.PGMemberLink;
import org.apache.log4j.Logger;
import wt.fc.Persistable;
import wt.fc.WTObject;
import wt.log4j.LogR;

/**
 * PG树的过滤器
 *
 * @author 段鑫扬
 */
public class PGTreeFilter extends DefaultSimpleValidationFilter {
    private static final Logger LOGGER = LogR.getLogger(PGTreeFilter.class.getName());

    /**
     * 当前行对象是组时可以看到添加成员操作
     * 当前行对象是PG信息时不能看到添加成员操作
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
                return UIValidationStatus.ENABLED;
            }
            if (contextObject instanceof PGMemberLink) {
                PGMemberLink pgMemberLink = (PGMemberLink) contextObject;
                WTObject pgMember = pgMemberLink.getPgMember();
                if (pgMember instanceof PGGroup) {
                    return UIValidationStatus.ENABLED;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


}
