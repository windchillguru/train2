package ext.exam.part.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.exam.part.IPartIfc;
import ext.exam.part.resource.partResource;
import ext.pi.core.PICoreHelper;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.util.WTException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * @author 段鑫扬
 */
public class SetPartStatusProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = SetPartStatusProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = partResource.class.getName();

    /**
     * 修改生命周期
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
        //获取下拉框
        HashMap comboBox = commandBean.getComboBox();
        //获取新的生命周期状态
        ArrayList<String> newStatuss = (ArrayList<String>) comboBox.get(IPartIfc.STATUS);
        String newStatus = newStatuss.get(0);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("comboBox=" + comboBox + ",newStatus=" + newStatus);
        }
        //修改生命周期状态
        PICoreHelper.service.setLifeCycleState(part, newStatus);
        return formResult;
    }
}
