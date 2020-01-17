package ext.task.doc.filter;

import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;
import org.apache.log4j.Logger;
import wt.doc.WTDocument;
import wt.fc.Persistable;
import wt.fc.WTReference;
import wt.log4j.LogR;

/**
 * xml信息按钮的过滤器
 *
 * @author 段鑫扬
 */
public class XmlInfoActionFilter extends DefaultSimpleValidationFilter {
    private static final Logger LOGGER = LogR.getLogger(XmlInfoActionFilter.class.getName());

    /**
     * 只有废弃状态并且是库管理员 （xml信息）才可用
     *
     * @param uiValidationKey
     * @param uiValidationCriteria
     * @return
     */
    @Override
    public UIValidationStatus preValidateAction(UIValidationKey uiValidationKey, UIValidationCriteria uiValidationCriteria) {
        UIValidationStatus status = UIValidationStatus.DISABLED; //默认置灰
        //获取按钮的信息
        String actionName = uiValidationKey.getComponentID();
        String objectType = uiValidationKey.getObjectType();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("actionName: " + actionName + " ,objectType: " + objectType);
        }
        boolean enabled = false;
        if (uiValidationCriteria.isContainerAdmin()) {
            //是库管理员
            //获取上下文对象
            WTReference contextObject = uiValidationCriteria.getContextObject();
            if (contextObject != null && contextObject.getObject() != null) {
                Persistable persistable = contextObject.getObject();
                if (persistable instanceof WTDocument) {
                    //是文档对象
                    WTDocument doc = (WTDocument) persistable;
                    //获取生命周期状态
                    String state = doc.getLifeCycleState().toString();
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("doc.display: " + doc.getDisplayIdentifier() + ",state:" + state);
                    }

                    if ("OBSOLESCENCE".equals(state)) {
                        //废弃状态

                        //可用
                        status = UIValidationStatus.ENABLED;
                    }

                }
            }
        }
        return status;
    }
}
