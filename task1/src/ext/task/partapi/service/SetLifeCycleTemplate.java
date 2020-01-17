package ext.task.partapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleTemplateReference;
import wt.part.WTPart;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * @author 段鑫扬
 */
public class SetLifeCycleTemplate {

    /**
     * 获取生命周期状态
     *
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static WTPart setLifeCycleTemplate(String oid) throws WTException {
        WTPart wtPart = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            wtPart = (WTPart) refObject;
            //获取容器
            WTContainerRef exchangeRef = WTContainerHelper.service.getExchangeRef();
            //根据容器和生命周期名获取生命周期模板
            LifeCycleTemplateReference lifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference("Default", exchangeRef);
            wtPart = (WTPart) LifeCycleHelper.service.reassign(wtPart, lifeCycleTemplateReference);
        }
        return wtPart;
    }
}