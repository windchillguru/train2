package ext.example.pg.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.example.pg.model.PGGroup;
import org.apache.log4j.Logger;
import wt.fc.PersistenceHelper;
import wt.log4j.LogR;
import wt.session.SessionServerHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import java.util.HashMap;
import java.util.List;

/**
 * 编辑PG组的processor 处理类
 *
 * @author 段鑫扬
 */
public class EditPGGroupProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = EditPGGroupProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    @Override
    public FormResult doOperation(NmCommandBean commandBean, List<ObjectBean> objectBeans) throws WTException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>doOperation() ");
        }
        FormResult formResult = super.doOperation(commandBean, objectBeans);
        boolean enforced = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            Object actionObj = commandBean.getActionOid().getRefObject();
            PGGroup pgGroup = null;
            if (actionObj != null && actionObj instanceof PGGroup) {
                pgGroup = (PGGroup) actionObj;
            }
            if (pgGroup == null) {
                throw new WTException("当前对象不是PG组，无法操作");
            }

            HashMap textMap = commandBean.getText();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> textMap=" + textMap);
            }
            if (textMap == null) {
                return formResult;
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
            pgGroup.setComments(comments);
            pgGroup.setRoot(isRoot);
            pgGroup.setEnabled(isEnabled);
            //修改
            PersistenceHelper.manager.save(pgGroup);
        } catch (WTPropertyVetoException e) {
            e.printStackTrace();
            throw new WTException(e);
        } finally {
            SessionServerHelper.manager.setAccessEnforced(enforced);
        }

        return formResult;
    }
}
