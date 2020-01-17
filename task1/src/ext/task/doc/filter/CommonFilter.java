package ext.task.doc.filter;

import com.ptc.core.ui.validation.DefaultSimpleValidationFilter;
import com.ptc.core.ui.validation.UIValidationCriteria;
import com.ptc.core.ui.validation.UIValidationKey;
import com.ptc.core.ui.validation.UIValidationStatus;
import ext.task.doc.TagName;
import ext.task.doc.provider.FilterXmlProvider;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import wt.enterprise.RevisionControlled;
import wt.fc.Persistable;
import wt.fc.WTReference;
import wt.log4j.LogR;

import java.util.List;

/**
 * xml配置的过滤器
 *
 * @author 段鑫扬
 */
public class CommonFilter extends DefaultSimpleValidationFilter {
    private static final Logger LOGGER = LogR.getLogger(CommonFilter.class.getName());
    private FilterXmlProvider filterXmlProvider = new FilterXmlProvider();

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
        Element actionEle = filterXmlProvider.findActionByAttr(actionName);
        String objectType = uiValidationKey.getObjectType();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("actionName: " + actionName + " ,objectType: " + objectType);
        }
        boolean enabled = false;
        //xml配置的生命周期state
        List<String> states = filterXmlProvider.findAttrByTag(actionEle, TagName.STATE);
        enabled = checkState(states, uiValidationCriteria);
        if (enabled == true) {
            status = UIValidationStatus.ENABLED;
        }
        return status;
    }

    /**
     * 校验生命周期
     *
     * @param states
     * @param uiValidationCriteria
     * @return
     */
    private boolean checkState(List<String> states, UIValidationCriteria uiValidationCriteria) {
        boolean flag = false;
        if (states.size() > 0) {
            WTReference contextObject = uiValidationCriteria.getContextObject();
            RevisionControlled revisionControlled = null;
            if (contextObject != null && contextObject.getObject() != null) {
                Persistable persistable = contextObject.getObject();
                if (persistable instanceof RevisionControlled) {
                    //是文档对象
                    revisionControlled = (RevisionControlled) persistable;
                }
            }
            String lifeCycleState = revisionControlled.getLifeCycleState().toString();
            for (String state : states) {
                if (state.equals("") || state.equals("ALL")) {
                    //不需要校验
                    return true;
                }
                if (lifeCycleState.equals(state)) {
                    flag = true;
                    break;
                }
            }
        } else {
            return true;
        }
        return flag;
    }
}
