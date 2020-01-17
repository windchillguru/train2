package ext.task.partapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.lifecycle.LifeCycleTemplate;
import wt.lifecycle.LifeCycleTemplateReference;
import wt.part.WTPart;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * @author 段鑫扬
 */
public class GetLifeCycleTemplate {

    /**
     * 获取生命周期状态
     *
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static LifeCycleTemplate getLifeCycleTemplate(String oid) throws WTException {
        LifeCycleTemplate lifeCycleTemplate = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            WTPart wtPart = (WTPart) refObject;
            LifeCycleTemplateReference lifeCycleTemplateReference = wtPart.getLifeCycleTemplate();
            lifeCycleTemplate = (LifeCycleTemplate) lifeCycleTemplateReference.getObject();
            String name = lifeCycleTemplate.getName();
            System.out.println("lifeCycleTemplate name = " + name);
        }
        return lifeCycleTemplate;
    }
}