package ext.task.partapi.service;

import com.google.gwt.rpc.client.impl.RemoteException;
import com.ptc.netmarkets.model.NmOid;
import ext.task.partapi.traverse.SingleLevelDifferenceBOMService;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartUsageLink;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import java.util.List;

/**
 * @author 段鑫扬
 */
public class SingleBomTraverseCompare {
    /**
     * 遍历bom
     * windchill TestPartAPI4 OR:wt.part.WTPart:104644  OR:wt.part.WTPart:103902
     */
    public static void compare(String newOid, String oldOid) throws RemoteException, WTException, WTPropertyVetoException {
        WTPart newPart = null;
        NmOid newNmOid = NmOid.newNmOid(newOid);
        Object newNmOidRefObject = newNmOid.getRefObject();
        if (newNmOidRefObject != null && newNmOidRefObject instanceof WTPart) {
            newPart = (WTPart) newNmOidRefObject;
        }

        WTPart oldPart = null;
        NmOid oldNmOid = NmOid.newNmOid(oldOid);
        Object refObject = oldNmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            oldPart = (WTPart) refObject;
        }

        SingleLevelDifferenceBOMService comService = new SingleLevelDifferenceBOMService(newPart, oldPart);
        comService.compare();
        List<WTPartUsageLink> createLinks = comService.getCreateLinks();
        System.out.println("createLinks: ");
        show(createLinks);

        List<WTPartUsageLink> updateLinks = comService.getUpdateLinks();
        System.out.println("updateLinks: ");
        show(updateLinks);

        List<WTPartUsageLink> deleteLinks = comService.getDeleteLinks();
        System.out.println("deleteLinks: ");
        show(deleteLinks);

    }

    /**
     * 展示数据
     *
     * @param links
     */
    public static void show(List<WTPartUsageLink> links) {
        for (WTPartUsageLink link : links) {
            WTPart parentPart = link.getUsedBy();
            System.out.println("parentPart = " + parentPart.getNumber() + " " + parentPart.getName());
            WTPartMaster childPartMaster = link.getUses();
            System.out.println("childPartMaster = " + childPartMaster.getNumber() + " " + childPartMaster.getName());
        }
        System.out.println("======111111111111111===========");
    }
}

