package ext.task.partapi.service;

import com.google.gwt.rpc.client.impl.RemoteException;
import com.ptc.netmarkets.model.NmOid;
import ext.task.partapi.traverse.BaseLineFirstTraverse;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartUsageLink;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.baseline.Baseline;

import java.util.Map;
import java.util.Set;

/**
 * @author 段鑫扬
 */
public class BaseLineTraverseService {
    /**
     * 遍历bom
     * windchill TestPartAPI4 OR:wt.part.WTPart:102076  OR:wt.vc.baseline.ManagedBaseline:104107
     *
     * @param Oid
     * @throws RemoteException
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static void baseLineTraverse(String Oid, String baselineOid) throws RemoteException, WTException, WTPropertyVetoException {
        Baseline baseline = null;
        NmOid baselineNmOid = NmOid.newNmOid(baselineOid);
        Object baselineObj = baselineNmOid.getRefObject();
        if (baselineObj != null && baselineObj instanceof Baseline) {
            baseline = (Baseline) baselineObj;
        }

        NmOid nmOid = NmOid.newNmOid(Oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            WTPart wtPart = (WTPart) refObject;

            //遍历
            BaseLineFirstTraverse traverse = new BaseLineFirstTraverse(wtPart, baseline);
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
