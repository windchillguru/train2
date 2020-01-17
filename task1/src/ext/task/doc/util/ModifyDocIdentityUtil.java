package ext.task.doc.util;

import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import wt.doc.WTDocument;
import wt.util.WTException;

import java.util.HashMap;

/**
 * @author 段鑫扬
 */
public class ModifyDocIdentityUtil {
    public static HashMap initPartParams(NmCommandBean commandBean) throws WTException {
        HashMap hashMap = new HashMap();
        NmOid actionOid = commandBean.getActionOid();
        Object refObject = actionOid.getRefObject();
        if (refObject instanceof WTDocument) {
            WTDocument doc = (WTDocument) refObject;
            //文档名
            String name = doc.getName();
            String number = doc.getNumber();
            hashMap.put("number", number);
            hashMap.put("name", name);
        }
        return hashMap;
    }
}
