package ext.task.partapi.service;

import com.google.gwt.rpc.client.impl.RemoteException;
import com.ptc.netmarkets.model.NmOid;
import wt.enterprise.CopyFactory;
import wt.part.WTPart;
import wt.part.WTPartUsageLink;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * @author 段鑫扬
 */
public class CopyLink {
    /**
     * OR%3Awt.part.WTPart%3A104672
     * windchill TestPartAPI4 VR:wt.part.WTPart:104314  OR:wt.part.WTPart:104724  OR:wt.part.WTPart:104860
     */
    public static WTPartUsageLink copyLink(String parentOid, String childOid, String newParentOid) throws RemoteException, WTException, WTPropertyVetoException {
        WTPartUsageLink link = FindLink.findLink(parentOid, childOid);
        WTPart newParentPart = null;
        NmOid newParOid = NmOid.newNmOid(newParentOid);
        Object refObject = newParOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            newParentPart = (WTPart) refObject;
        }
        WTPartUsageLink newLink = null;
        if (link != null) {
            newLink = (WTPartUsageLink) CopyFactory.copyRelationship(link.getUsedBy(), newParentPart, link, WTPartUsageLink.USED_BY_ROLE);
        }
        return newLink;
    }

}

