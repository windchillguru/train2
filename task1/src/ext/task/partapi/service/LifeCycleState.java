package ext.task.partapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * @author 段鑫扬
 */
public class LifeCycleState {

    /**
     * 获取生命周期状态
     *
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static State getLifeCycleState(String oid) throws WTException {
        State state = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            WTPart wtPart = (WTPart) refObject;
            state = wtPart.getLifeCycleState();
            System.out.println("state = " + state);
        }
        return state;
    }
}