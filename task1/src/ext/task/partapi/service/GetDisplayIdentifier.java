package ext.task.partapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.identity.IdentityFactory;
import wt.part.WTPart;
import wt.util.LocalizableMessage;
import wt.util.WTException;

import java.util.Locale;

/**
 * 部件api测试
 *
 * @author 段鑫扬
 */
public class GetDisplayIdentifier {

    /**
     * 获取指定版本
     * windchill TestPartAPI5 VR:wt.part.WTPart:104859
     *
     * @throws WTException
     */
    public static WTPart getDisplayIdentifier(String oid) throws WTException {
        WTPart wtpart = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            wtpart = (WTPart) refObject;
            LocalizableMessage displayIdentifier = IdentityFactory.getDisplayIdentifier(wtpart);
            String localizedMessage = displayIdentifier.getLocalizedMessage(Locale.CHINA);
            System.out.println("localizedMessage = " + localizedMessage);
        }
        return wtpart;
    }
}
