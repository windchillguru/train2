package ext.task.partapi.service;

import com.google.gwt.rpc.client.impl.RemoteException;
import wt.fc.PersistenceHelper;
import wt.part.Quantity;
import wt.part.QuantityUnit;
import wt.part.WTPartUsageLink;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * @author 段鑫扬
 */
public class ChangeLinkQuantity {
    /**
     * windchill TestPartAPI4 VR:wt.part.WTPart:104314  OR:wt.part.WTPart:104724
     */
    public static WTPartUsageLink changeLinkQuantity(String parentOid, String childOid) throws RemoteException, WTException, WTPropertyVetoException {
        WTPartUsageLink link = FindLink.findLink(parentOid, childOid);
        if (link != null) {
            Quantity quantity = link.getQuantity();
            quantity.setAmount(1.5);
            quantity.setUnit(QuantityUnit.M);

            link = (WTPartUsageLink) PersistenceHelper.manager.save(link);
        }

        return link;
    }

}

