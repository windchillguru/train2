package ext.task.docapi.service.deslink;

import com.ptc.netmarkets.model.NmOid;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTCollection;
import wt.part.WTPart;
import wt.part.WTPartDescribeLink;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;

import java.beans.PropertyVetoException;

/**
 * @author 段鑫扬
 */
public class FindWTPartDescribeLink3 {
    /**
     * windchill TestDocAPI VR:wt.part.WTPart:104846 VR:wt.doc.WTDocument:101741
     *
     * @return
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static WTCollection findWTPartReferenceLink(String partOid, String docOid) throws WTException, PropertyVetoException {
        WTCollection wtCollection = new WTArrayList();

        WTPart wtpart = getWtPart(partOid);

        WTDocument doc = null;
        NmOid docNm = NmOid.newNmOid(docOid);
        Object docRefObj = docNm.getRefObject();
        if (docRefObj != null && docRefObj instanceof WTDocument) {
            doc = (WTDocument) docRefObj;
        }

        QuerySpec qs = new QuerySpec(WTPartDescribeLink.class);
        qs.appendWhere(new SearchCondition(WTPartDescribeLink.class, "roleAObjectRef.key", "=", PersistenceHelper.getObjectIdentifier(wtpart)), new int[]{0});
        qs.appendAnd();
        qs.appendWhere(new SearchCondition(WTPartDescribeLink.class, "roleBObjectRef.key", "=", PersistenceHelper.getObjectIdentifier(doc)), new int[]{0});

        QueryResult qr = PersistenceHelper.manager.find(qs);
        showInfo(qr);
        wtCollection.addAll(qr);

        return wtCollection;
    }

    private static WTPart getWtPart(String partOid) throws WTException {
        WTPart wtpart = null;
        NmOid nmOid = NmOid.newNmOid(partOid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            wtpart = (WTPart) refObject;
        }
        return wtpart;
    }

    public static void showInfo(QueryResult qr) {
        if (qr != null) {
            while (qr.hasMoreElements()) {
                Object nextElement = qr.nextElement();
                if (nextElement != null && nextElement instanceof WTPartDescribeLink) {
                    WTPartDescribeLink describeLink = (WTPartDescribeLink) nextElement;
                    WTPart roleAObject = (WTPart) describeLink.getRoleAObject();
                    System.out.println("roleAObject part = " + roleAObject.getName());
                    WTDocument roleBObject = (WTDocument) describeLink.getRoleBObject();
                    System.out.println("roleBObject doc = " + roleBObject.getName());
                }
            }
        }
    }
}
