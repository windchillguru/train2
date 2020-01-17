package ext.task.docapi.service.docusagelink;

import com.ptc.netmarkets.model.NmOid;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.doc.WTDocumentUsageLink;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import java.util.Map;
import java.util.Set;

/**
 * @author 段鑫扬
 */
public class Traverse {
    public static void singleTraverse(String oid) throws WTException, WTPropertyVetoException {
        WTDocument wtDocument = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTDocument) {
            wtDocument = (WTDocument) refObject;
        }
        SingleDocUsageLinkService traverse = new SingleDocUsageLinkService(wtDocument);
        traverse.traverse();
        Map<Long, WTDocument> childList = traverse.getChildList();
        System.out.println("childList = " + childList);
        Map<Long, WTDocumentUsageLink> linkList = traverse.getLinkList();
        Set<Long> linkIds = linkList.keySet();
        for (Long linkId : linkIds) {
            WTDocumentUsageLink partUsageLink = linkList.get(linkId);
            WTDocument parentDoc = (WTDocument) partUsageLink.getRoleAObject();
            System.out.println("linkId = " + linkId + "：parentDoc = " + parentDoc.getName());
            WTDocumentMaster childDocMaster = (WTDocumentMaster) partUsageLink.getRoleBObject();
            System.out.println("linkId = " + linkId + "：childDocMaster = " + childDocMaster.getName());
        }
    }
}
