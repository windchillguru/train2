package ext.task.docapi.service.deslink;

import com.ptc.netmarkets.model.NmOid;
import wt.doc.WTDocument;
import wt.fc.*;
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
public class FindWTPartDescribeLinkByDoc {
    /**
     * windchill TestDocAPI VR:wt.doc.WTDocument:101741
     *
     * @param oid
     * @return
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static WTCollection findWTPartReferenceLink(String oid) throws WTException, PropertyVetoException {
        WTCollection wtCollection = new WTArrayList();

        WTDocument wtDocument = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTDocument) {
            wtDocument = (WTDocument) refObject;
        }

        QuerySpec qs = new QuerySpec(WTPartDescribeLink.class);

        String roleBObjRefKeyId = BinaryLink.ROLE_BOBJECT_REF + "." + ObjectReference.KEY + "." + ObjectIdentifier.ID;
        long objectIdentifier = PersistenceHelper.getObjectIdentifier(wtDocument).getId();
        SearchCondition sc = new SearchCondition(WTPartDescribeLink.class, roleBObjRefKeyId
                , SearchCondition.EQUAL, objectIdentifier);
        qs.appendWhere(sc, new int[]{0});

        QueryResult qr = PersistenceHelper.manager.find(qs);
        showInfo(qr);
        wtCollection.addAll(qr);

        return wtCollection;
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
