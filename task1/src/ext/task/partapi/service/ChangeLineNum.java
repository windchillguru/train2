package ext.task.partapi.service;

import com.google.gwt.rpc.client.impl.RemoteException;
import wt.fc.PersistenceHelper;
import wt.part.LineNumber;
import wt.part.LineNumberHelper;
import wt.part.WTPartUsageLink;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * @author 段鑫扬
 */
public class ChangeLineNum {
    /**
     * windchill TestPartAPI4 VR:wt.part.WTPart:104314  OR:wt.part.WTPart:104724
     */
    public static WTPartUsageLink changeLineNum(String parentOid, String childOid) throws RemoteException, WTException, WTPropertyVetoException {
        WTPartUsageLink link = FindLink.findLink(parentOid, childOid);
        if (link != null) {
            LineNumber latestLineNumber = LineNumberHelper.service.getLatestLineNumber(link.getUsedBy());
            LineNumber newLineNumber = LineNumber.newLineNumber(latestLineNumber.getValue() + 10);
            link.setLineNumber(newLineNumber);
            // System.out.println("lineNumber = " + lineNumber);
            link = (WTPartUsageLink) PersistenceHelper.manager.save(link);
        }
        return link;
    }

}

