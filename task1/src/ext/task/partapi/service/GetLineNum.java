package ext.task.partapi.service;

import com.google.gwt.rpc.client.impl.RemoteException;
import wt.part.LineNumber;
import wt.part.WTPartUsageLink;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * @author 段鑫扬
 */
public class GetLineNum {
    /**
     * windchill TestPartAPI4 VR:wt.part.WTPart:104314  OR:wt.part.WTPart:104724
     */
    public static WTPartUsageLink getLineNum(String parentOid, String childOid) throws RemoteException, WTException, WTPropertyVetoException {
        WTPartUsageLink link = FindLink.findLink(parentOid, childOid);
        if (link != null) {
            LineNumber lineNumber = link.getLineNumber();
            System.out.println("lineNumber = " + lineNumber);
        }
        return link;
    }

}

