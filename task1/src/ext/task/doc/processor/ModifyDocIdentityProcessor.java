package ext.task.doc.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.util.FeedbackMessage;
import com.ptc.core.ui.resources.FeedbackType;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.lang.PIStringUtils;
import ext.task.doc.resource.docAPIResource;
import org.apache.log4j.Logger;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.doc.WTDocumentMasterIdentity;
import wt.fc.IdentityHelper;
import wt.fc.PersistenceHelper;
import wt.log4j.LogR;
import wt.util.WTException;
import wt.util.WTMessage;
import wt.util.WTPropertyVetoException;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * 修改标识的processor处理类  文档
 *
 * @author 段鑫扬
 */
public class ModifyDocIdentityProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = ModifyDocIdentityProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = docAPIResource.class.getName();

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
        WTDocument doc = null;
        if (refObject instanceof WTDocument) {
            doc = (WTDocument) refObject;
        }
        if (doc == null) {
            throw new WTException("文档为空");
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> doOperation--->" + CLASSNAME + ",doc =" + doc.getName());
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

        String oldIdentity = doc.getNumber() + "-" + doc.getName();

        WTDocumentMaster master = (WTDocumentMaster) doc.getMaster();
        WTDocumentMasterIdentity masterIdentity = (WTDocumentMasterIdentity) master.getIdentificationObject();
        try {
            if (!PIStringUtils.equals(newName, doc.getName())) {
                masterIdentity.setName(newName);
            }

            if (!PIStringUtils.equalsIgnoreCase(newNum, doc.getNumber())) {
                masterIdentity.setNumber(newNum);
            }
            //修改标识
            master = (WTDocumentMaster) IdentityHelper.service.changeIdentity(master, masterIdentity);
            doc = (WTDocument) PersistenceHelper.manager.refresh(doc);

            //添加返回信息
            String newIdentity = doc.getNumber() + "-" + doc.getName();
            String successMsg = WTMessage.getLocalizedMessage(RESOURCE, docAPIResource.MODIFY_DOCIDENTITY_SUCCESSMSG
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
