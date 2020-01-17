package ext.task.partapi.service;

import com.google.gwt.rpc.client.impl.RemoteException;
import wt.fc.collections.WTCollection;
import wt.part.*;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import java.util.Iterator;

/**
 * @author 段鑫扬
 */
public class GetSubAlternateLink {
    /**
     * windchill TestPartAPI5 OR:wt.part.WTPart:104906 OR:wt.part.WTPart:101926
     */
    public static WTCollection getSubAlternateLink(String parentOid, String childOid) throws RemoteException, WTException, WTPropertyVetoException {
        WTCollection subAlternateLink = null;
        WTPartUsageLink link = FindLink.findLink(parentOid, childOid);

        if (link != null) {
            subAlternateLink = WTPartHelper.service.getSubstituteLinks(link);
            Iterator iterator = subAlternateLink.persistableIterator();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                if (next != null && next instanceof WTPartSubstituteLink) {
                    WTPartSubstituteLink subLink = (WTPartSubstituteLink) next;
                    WTPartUsageLink roleALink = (WTPartUsageLink) subLink.getRoleAObject();
                    System.out.println("roleALink = " + roleALink);
                    WTPart roleALinkUsedBy = roleALink.getUsedBy();
                    System.out.println("roleALinkUsedBy.getName() = " + roleALinkUsedBy.getName());
                    WTPartMaster roleALinkUses = roleALink.getUses();
                    System.out.println("roleALinkUses.getName() = " + roleALinkUses.getName());
                    WTPartMaster roleBPart = (WTPartMaster) subLink.getRoleBObject();
                    System.out.println("roleBPart = " + roleBPart.getName());
                }
            }
        }

        return subAlternateLink;
    }

}

