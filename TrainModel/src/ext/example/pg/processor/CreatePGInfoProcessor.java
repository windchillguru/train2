package ext.example.pg.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.example.pg.bean.PGInformationBean;
import ext.example.pg.service.PGInfoHelper;
import ext.lang.PIStringUtils;
import ext.pi.core.PICoreHelper;
import org.apache.log4j.Logger;
import wt.doc.WTDocument;
import wt.log4j.LogR;
import wt.session.SessionServerHelper;
import wt.util.WTException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 创建PG信息的processor 处理类
 *
 * @author 段鑫扬
 */
public class CreatePGInfoProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = CreatePGInfoProcessor.class.getName();
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
            //工号
            String employeeNo = "";
            if (textMap.containsKey("employeeNo")) {
                employeeNo = (String) textMap.get("employeeNo");
            }

            //姓名
            String employeeName = "";
            if (textMap.containsKey("employeeName")) {
                employeeName = (String) textMap.get("employeeName");
            }

            //用户名
            String employeeUserName = "";
            if (textMap.containsKey("employeeUserName")) {
                employeeUserName = (String) textMap.get("employeeUserName");
            }

            //邮箱
            String employeeEmail = "";
            if (textMap.containsKey("employeeEmail")) {
                employeeEmail = (String) textMap.get("employeeEmail");
            }

            //电话
            String employeePhone = "";
            if (textMap.containsKey("employeePhone")) {
                employeePhone = (String) textMap.get("employeePhone");
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

            HashMap comboMap = commandBean.getComboBox();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> comboMap=" + comboMap);
            }

            //信息来源
            String informationSource = "";
            if (comboMap != null && comboMap.containsKey("informationSource")) {
                informationSource = ((ArrayList<String>) comboMap.get("informationSource")).get(0);
            }

            HashMap checkedMap = commandBean.getChecked();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> checkedMap=" + checkedMap);
            }
            //是否有经验
            boolean isExperienced = false;
            if (checkedMap != null && checkedMap.containsKey("isExperienced")) {
                isExperienced = true;
            }
            //是否是组长
            boolean isLeader = false;
            if (checkedMap != null && checkedMap.containsKey("isLeader")) {
                isLeader = true;
            }
            String resumeInfo = commandBean.getTextParameter("resumeInfo");
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> resumeInfo=" + resumeInfo);
            }
            WTDocument document = null;
            if (PIStringUtils.hasText(resumeInfo)) {
                document = (WTDocument) PICoreHelper.service.getWTObjectByOid(resumeInfo);
            }
            String pgName = employeeName + "(" + employeeUserName + ")";
            PGInformationBean bean = new PGInformationBean(employeeNo, employeeName, employeeUserName
                    , employeeEmail, employeePhone, comments, isExperienced, informationSource, isLeader
                    , document, pgName);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> bean=" + bean);
            }
            PGInfoHelper.service.createPGInformation(bean);
        } finally {
            SessionServerHelper.manager.setAccessEnforced(enforced);
        }

        return formResult;
    }
}
