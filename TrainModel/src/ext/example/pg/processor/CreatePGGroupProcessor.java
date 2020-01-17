package ext.example.pg.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.example.pg.bean.PGGroupBean;
import ext.example.pg.service.PGInfoHelper;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.session.SessionServerHelper;
import wt.util.WTException;

import java.util.HashMap;
import java.util.List;

/**
 * 创建PG组的processor 处理类
 *
 * @author 段鑫扬
 */
public class CreatePGGroupProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = CreatePGGroupProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    @Override
    public FormResult doOperation(NmCommandBean commandBean, List<ObjectBean> objectBeans) throws WTException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>doOperation() ");
        }
        FormResult formResult = super.doOperation(commandBean, objectBeans);
        boolean enforced = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            HashMap textMap = commandBean.getText();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> textMap=" + textMap);
            }
            if (textMap == null) {
                return formResult;
            }
            //组名
            String pgGroupName = "";
            if (textMap.containsKey("pgGroupName")) {
                pgGroupName = (String) textMap.get("pgGroupName");
            }

            HashMap textAreaMap = commandBean.getTextArea();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> textAreaMap=" + textAreaMap);
            }

            //备注
            String comments = "";
            if (textAreaMap != null && textAreaMap.containsKey("comments")) {
                comments = (String) textAreaMap.get("comments");
            }

            HashMap checkedMap = commandBean.getChecked();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> checkedMap=" + checkedMap);
            }
            //是否根组
            boolean isRoot = false;
            if (checkedMap != null && checkedMap.containsKey("isRoot")) {
                isRoot = true;
            }
            //是否启用
            boolean isEnabled = false;
            if (checkedMap != null && checkedMap.containsKey("isEnabled")) {
                isEnabled = true;
            }

            PGGroupBean bean = new PGGroupBean(pgGroupName, comments, isEnabled, isRoot);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> bean=" + bean);
            }
            PGInfoHelper.service.createPGGroup(bean);
        } finally {
            SessionServerHelper.manager.setAccessEnforced(enforced);
        }
        return formResult;
    }
}
