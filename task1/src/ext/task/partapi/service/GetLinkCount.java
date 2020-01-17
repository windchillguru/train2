package ext.task.partapi.service;

import com.google.gwt.rpc.client.impl.RemoteException;
import wt.part.Quantity;
import wt.part.QuantityUnit;
import wt.part.WTPartUsageLink;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * @author 段鑫扬
 */
public class GetLinkCount {
    /**
     * windchill TestPartAPI4 VR:wt.part.WTPart:104314  OR:wt.part.WTPart:104724
     */
    public static WTPartUsageLink getLinkQuantity(String parentOid, String childOid) throws RemoteException, WTException, WTPropertyVetoException {
        WTPartUsageLink link = FindLink.findLink(parentOid, childOid);
        if (link != null) {
            Quantity quantity = link.getQuantity();
            System.out.println("quantity = " + quantity);
            double amount = quantity.getAmount();
            System.out.println("amount = " + amount);
            QuantityUnit unit = quantity.getUnit();
            System.out.println("unit = " + unit);
        }

        return link;
    }

}

