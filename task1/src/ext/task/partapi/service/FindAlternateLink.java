package ext.task.partapi.service;

import com.google.gwt.rpc.client.impl.RemoteException;
import com.ptc.netmarkets.model.NmOid;
import wt.fc.collections.WTCollection;
import wt.part.WTPart;
import wt.part.WTPartAlternateLink;
import wt.part.WTPartHelper;
import wt.part.WTPartMaster;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import java.util.Iterator;

/**
 * @author 段鑫扬
 */
public class FindAlternateLink {
    /**
     * windchill TestPartAPI5 OR:wt.part.WTPart:104724
     */
    public static WTCollection findAlternateLink(String oId) throws RemoteException, WTException, WTPropertyVetoException {
        WTCollection alternateLink = null;
        WTPart wtPart = null;
        NmOid newParOid = NmOid.newNmOid(oId);
        Object refObject = newParOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            wtPart = (WTPart) refObject;
            alternateLink = WTPartHelper.service.getAlternateLinks(wtPart.getMaster());
            if (alternateLink != null) {
                Iterator iterator = alternateLink.persistableIterator();
                while (iterator.hasNext()) {
                    Object next = iterator.next();
                    if (next != null && next instanceof WTPartAlternateLink) {
                        WTPartAlternateLink link = (WTPartAlternateLink) next;
                        WTPartMaster roleA = (WTPartMaster) link.getRoleAObject();
                        System.out.println("roleA = " + roleA.getName());
                        WTPartMaster roleB = (WTPartMaster) link.getRoleBObject();
                        System.out.println("roleB = " + roleB.getName());
                    }
                }
            }
        }
        return alternateLink;
    }

}

