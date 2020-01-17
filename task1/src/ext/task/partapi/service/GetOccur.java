package ext.task.partapi.service;

import com.google.gwt.rpc.client.impl.RemoteException;
import wt.fc.QueryResult;
import wt.occurrence.OccurrenceHelper;
import wt.part.PartUsesOccurrence;
import wt.part.WTPartUsageLink;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * @author 段鑫扬
 */
public class GetOccur {
    /**
     * windchill TestPartAPI4 VR:wt.part.WTPart:104314  OR:wt.part.WTPart:104724
     */
    public static WTPartUsageLink getOccur(String parentOid, String childOid) throws RemoteException, WTException, WTPropertyVetoException {
        WTPartUsageLink link = FindLink.findLink(parentOid, childOid);
        if (link != null) {
            QueryResult qr = OccurrenceHelper.service.getUsesOccurrences(link);
            while (qr.hasMoreElements()) {
                Object o = qr.nextElement();
                if (o != null && o instanceof PartUsesOccurrence) {
                    PartUsesOccurrence partUsesOccurrence = (PartUsesOccurrence) o;
                    String name = partUsesOccurrence.getName();
                    System.out.println("UsesOccurrence name = " + name);
                }
            }
        }
        return link;
    }

}

