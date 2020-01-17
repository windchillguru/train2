package ext.task.partapi.service;

import com.google.gwt.rpc.client.impl.RemoteException;
import com.ptc.netmarkets.model.NmOid;
import ext.task.partapi.traverse.SingleBomTraverse;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartUsageLink;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import java.util.Map;
import java.util.Set;

/**
 * @author 段鑫扬
 */
public class SingleBomTraverseService {
    /**
     * 遍历bom
     * windchill TestPartAPI4 VR:wt.part.WTPart:103901
     *
     * @param Oid
     * @throws RemoteException
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static void singleBomTraverse(String Oid) throws RemoteException, WTException, WTPropertyVetoException {
        NmOid parentOId = NmOid.newNmOid(Oid);
        Object refObject = parentOId.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            WTPart wtPart = (WTPart) refObject;

            //单层遍历
            SingleBomTraverse traverse = new SingleBomTraverse(wtPart);
            traverse.traverse();
            Map<Long, WTPart> childList = traverse.getChildList();
            System.out.println("childList = " + childList);
            Map<Long, WTPartUsageLink> linkList = traverse.getLinkList();
            Set<Long> linkIds = linkList.keySet();
            for (Long linkId : linkIds) {
                WTPartUsageLink partUsageLink = linkList.get(linkId);
                WTPart parentPart = partUsageLink.getUsedBy();
                System.out.println("linkId = " + linkId + "：parentPart = " + parentPart.getNumber() + " " + parentPart.getName());
                WTPartMaster childPartMaster = partUsageLink.getUses();
                System.out.println("linkId = " + linkId + "：childPartMaster = " + childPartMaster.getNumber() + " " + childPartMaster.getName());
            }
        }
    }
}
