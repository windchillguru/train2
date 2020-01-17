package ext.task.part.filter;

import com.ptc.core.ui.validation.*;
import ext.task.doc.provider.FilterXmlProvider;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import wt.fc.Persistable;
import wt.fc.WTReference;
import wt.log4j.LogR;
import wt.part.WTPart;

/**
 * 物料信息按钮的过滤器
 *
 * @author 段鑫扬
 */
public class PartInfoActionFilter extends DefaultSimpleValidationFilter {
    private static final Logger LOGGER = LogR.getLogger(PartInfoActionFilter.class.getName());
    private FilterXmlProvider filterXmlProvider = new FilterXmlProvider();

    @Override
    public UIValidationStatus preValidateAction(UIValidationKey uiValidationKey, UIValidationCriteria uiValidationCriteria) {
        UIValidationStatus status = UIValidationStatus.DISABLED; //默认置灰
        //获取按钮的信息
        String actionName = uiValidationKey.getComponentID();
        //当前按钮的配置信息
        Element actionEle = filterXmlProvider.findActionByAttr(actionName);

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
                        //可用
                        status = UIValidationStatus.ENABLED;
                    }

                }
            }
        }
        return status;
    }
}
