package ext.task.part.util;

import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import wt.part.WTPart;
import wt.util.WTException;

import java.util.HashMap;

/**
 * @author 段鑫扬
 */
public class ModifyPartIdentityUtil {
    public static HashMap initPartParams(NmCommandBean commandBean) throws WTException {
        HashMap hashMap = new HashMap();
        NmOid actionOid = commandBean.getActionOid();
        Object refObject = actionOid.getRefObject();
        if (refObject instanceof WTPart) {
            WTPart part = (WTPart) refObject;
            String name = part.getName();
            String number = part.getNumber();
            hashMap.put("number", number);
            hashMap.put("name", name);
        }
        return hashMap;
    }
}
