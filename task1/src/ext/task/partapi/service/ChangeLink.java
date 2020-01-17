package ext.task.partapi.service;

import com.google.gwt.rpc.client.impl.RemoteException;
import com.ptc.netmarkets.model.NmOid;
import wt.fc.PersistenceHelper;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartUsageLink;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * @author 段鑫扬
 */
public class ChangeLink {
    /**
     * OR%3Awt.part.WTPart%3A104672
     * windchill TestPartAPI4 VR:wt.part.WTPart:104314  OR:wt.part.WTPart:101926  OR:wt.part.WTPart:104860
     */
    public static WTPartUsageLink changeLink(String parentOid, String childOid, String newChildOid) throws RemoteException, WTException, WTPropertyVetoException {
        WTPartUsageLink link = FindLink.findLink(parentOid, childOid);

        WTPart newChildPart = null;
        NmOid newChildNmOid = NmOid.newNmOid(newChildOid);
        Object refObject = newChildNmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            newChildPart = (WTPart) refObject;
        }

        if (link != null) {
            link.setUses(newChildPart.getMaster());
            //保存
            link = (WTPartUsageLink) PersistenceHelper.manager.save(link);

            WTPart roleA = link.getUsedBy();
            WTPartMaster roleB = link.getUses();
            System.out.println("new link :" + link);
            System.out.println("roleA parent :" + roleA.getName());
            System.out.println("roleB child :" + roleB.getName());
        }

        return link;
    }

}

