package ext.task.partapi.service;

import com.google.gwt.rpc.client.impl.RemoteException;
import wt.fc.collections.WTKeyedHashMap;
import wt.occurrence.OccurrenceHelper;
import wt.part.PartUsesOccurrence;
import wt.part.WTPartUsageLink;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * @author 段鑫扬
 */
public class ChangeOccur {
    /**
     * windchill TestPartAPI4 VR:wt.part.WTPart:104314  OR:wt.part.WTPart:104724
     */
    public static WTPartUsageLink chanegOccur(String parentOid, String childOid) throws RemoteException, WTException, WTPropertyVetoException {
        WTPartUsageLink link = FindLink.findLink(parentOid, childOid);
        if (link != null) {
            PartUsesOccurrence newPartUsesOccurrence = PartUsesOccurrence.newPartUsesOccurrence(link);
            newPartUsesOccurrence.setName("C5");
            WTKeyedHashMap wtKeyedHashMap = new WTKeyedHashMap();
            wtKeyedHashMap.addElement(newPartUsesOccurrence);
            //保存
            OccurrenceHelper.service.saveUsesOccurrenceAndData(wtKeyedHashMap);
        }
        return link;
    }

}

