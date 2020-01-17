package ext.task.partapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * @author 段鑫扬
 */
public class SetLifeCycleState {

    /**
     * 获取生命周期状态
     *
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static WTPart setSetLifeCycleState(String oid) throws WTException {
        WTPart wtPart = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            wtPart = (WTPart) refObject;
            //设置为已发布
            wtPart = (WTPart) LifeCycleHelper.service.setLifeCycleState(wtPart, State.RELEASED);

        }
        return wtPart;
    }
}