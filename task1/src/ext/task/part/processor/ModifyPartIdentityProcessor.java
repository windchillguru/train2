package ext.task.part.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.util.FeedbackMessage;
import com.ptc.core.ui.resources.FeedbackType;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.lang.PIStringUtils;
import ext.task.part.resource.partAPIResource;
import org.apache.log4j.Logger;
import wt.fc.IdentityHelper;
import wt.fc.PersistenceHelper;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartMasterIdentity;
import wt.util.WTException;
import wt.util.WTMessage;
import wt.util.WTPropertyVetoException;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * 修改标识的processor处理类
 *
 * @author 段鑫扬
 */
public class ModifyPartIdentityProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = ModifyPartIdentityProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = partAPIResource.class.getName();

    /**
     * 修改标识
     *
     * @param commandBean
     * @param objectBeans
     * @return
     * @throws WTException
     */
    @Override
    public FormResult doOperation(NmCommandBean commandBean, List<ObjectBean> objectBeans) throws WTException {
        FormResult formResult = super.doOperation(commandBean, objectBeans);

        NmOid actionOid = commandBean.getActionOid();
        Object refObject = actionOid.getRefObject();
        WTPart part = null;
        if (refObject instanceof WTPart) {
            part = (WTPart) refObject;
        }
        if (part == null) {
            throw new WTException("部件为空");
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> doOperation--->" + CLASSNAME + ",part =" + part.getName());
        }

        Locale locale = commandBean.getLocale();
        //获取所有的文本输入框
        HashMap textMap = commandBean.getText();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("textMap=" + textMap);
        }
        String newNum = "";
        String newName = "";

        if (textMap != null && textMap.containsKey("newName")) {
            newName = (String) textMap.get("newName");
        }
        if (textMap != null && textMap.containsKey("newNumber")) {
            newNum = (String) textMap.get("newNumber");
        }

        String oldIdentity = part.getNumber() + "-" + part.getName();
        //js已经验证过修改前后的name和num
        WTPartMaster master = part.getMaster();
        WTPartMasterIdentity masterIdentity = (WTPartMasterIdentity) master.getIdentificationObject();
        try {
            if (!PIStringUtils.equalsIgnoreCase(newName, part.getName())) {
                masterIdentity.setName(newName);
            }

            if (!PIStringUtils.equalsIgnoreCase(newNum, part.getNumber())) {
                masterIdentity.setNumber(newNum);
            }
            //修改标识
            master = (WTPartMaster) IdentityHelper.service.changeIdentity(master, masterIdentity);
            part = (WTPart) PersistenceHelper.manager.refresh(part);

            //添加返回信息
            String newIdentity = part.getNumber() + "-" + part.getName();
            String successMsg = WTMessage.getLocalizedMessage(RESOURCE, partAPIResource.MODIFY_PARTIDENTITY_SUCCESSMSG
                    , new Object[]{oldIdentity, newIdentity}, locale);
            FeedbackMessage feedbackMessage = new FeedbackMessage(FeedbackType.SUCCESS, locale, successMsg, null, new String[]{""});
            formResult.addFeedbackMessage(feedbackMessage);
        } catch (WTPropertyVetoException e) {
            e.printStackTrace();
            throw new WTException(e);
        }
        return formResult;
    }
}
