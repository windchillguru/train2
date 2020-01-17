package ext.exam.part.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.forms.FormResultAction;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.exam.part.resource.partResource;
import ext.exam.part.util.PartCodeUtil;
import ext.lang.PIStringUtils;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.util.WTException;

import java.util.List;

/**
 * 生成正式码的processor 处理类
 *
 * @author 段鑫扬
 */
public class GenerateNumberProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = GenerateNumberProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = partResource.class.getName();

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
        String unitValue = part.getDefaultUnit().toString();
        if (part.getNumber().startsWith(unitValue)) {
            //非临时码
            LOGGER.info("部件：" + part.getNumber() + "已经是正式码，不处理");
            throw new WTException("部件：" + part.getNumber() + "已经是正式码，不处理");
        }
        //生成正式码
        String errMsg = PartCodeUtil.generateNumber(part, false);
        //刷新页面
        formResult.setNextAction(FormResultAction.REFRESH_CURRENT_PAGE);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>errMsg = " + errMsg);
        }
        if (PIStringUtils.hasText(errMsg)) {
            throw new WTException(errMsg);
        }
        return formResult;
    }
}
