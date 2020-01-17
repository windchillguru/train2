package ext.exam.part.util;

import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.pi.core.PICoreHelper;
import wt.fc.Persistable;
import wt.lifecycle.LifeCycleState;
import wt.lifecycle.LifeCycleTemplate;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.util.WTException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author 段鑫扬
 */
public class PartStatusUtil {
    /**
     * 获取当前生命周期状态显示值
     *
     * @param commandBean
     * @return
     * @throws WTException
     */
    public static String getState(NmCommandBean commandBean) throws WTException {
        NmOid actionOid = commandBean.getActionOid();
        Object refObject = actionOid.getRefObject();
        String stateStr = null;
        if (refObject instanceof WTPart) {
            WTPart part = (WTPart) refObject;
            LifeCycleState state = part.getState();
            stateStr = state.toString();
        }
        return stateStr;
    }

    /**
     * 获取当前部件所属生命周期模板的所有状态，以内部值为key，显示值为value存储在map
     *
     * @param commandBean
     * @return
     */
    public static Map<String, String> getAllState(NmCommandBean commandBean) throws WTException {
        NmOid actionOid = commandBean.getActionOid();
        Locale locale = commandBean.getLocale();
        Object refObject = actionOid.getRefObject();
        Map<String, String> stateMap = new HashMap<>();
        if (refObject instanceof WTPart) {
            WTPart part = (WTPart) refObject;
            Persistable object = part.getState().getLifeCycleId().getObject();
            LifeCycleTemplate lifeCycleTemplate = null;
            if (object instanceof LifeCycleTemplate) {
                lifeCycleTemplate = (LifeCycleTemplate) object;
            }
            Set<State> stateSet = PICoreHelper.service.getStateSet(lifeCycleTemplate);
            for (State state : stateSet) {
                String display = state.getDisplay(locale);
                String stateStr = state.toString();
                //以内部值为key，显示值为value存储
                stateMap.put(stateStr, display);
            }
        }
        return stateMap;
    }
}
