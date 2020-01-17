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
public class FindWTPartDescribeLink {
    /**
     * windchill TestDocAPI VR:wt.part.WTPart:104846
     *
     * @param oid 部件oid
     * @return
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static WTCollection findReferenceDocs(String oid) throws WTException, PropertyVetoException {
        WTCollection wtCollection = new WTArrayList();
        WTPart wtPart = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            wtPart = (WTPart) refObject;
        }
        QuerySpec qs = new QuerySpec(WTPartDescribeLink.class);

        String roleAObjRefKeyId = BinaryLink.ROLE_AOBJECT_REF + "." + ObjectReference.KEY + "." + ObjectIdentifier.ID;
        long objectIdentifier = PersistenceHelper.getObjectIdentifier(wtPart).getId();
        SearchCondition sc = new SearchCondition(WTPartDescribeLink.class, roleAObjRefKeyId
                , SearchCondition.EQUAL, objectIdentifier);
        qs.appendWhere(sc, new int[]{0});

        QueryResult qr = PersistenceHelper.manager.find(qs);
        if (qr != null) {
            while (qr.hasMoreElements()) {
                Object nextElement = qr.nextElement();
                WTPartDescribeLink describeLink = (WTPartDescribeLink) nextElement;
                WTPart roleA = (WTPart) describeLink.getRoleAObject();
                System.out.println("roleA part = " + roleA.getName());
                WTDocument roleB = (WTDocument) describeLink.getRoleBObject();
                System.out.println("roleB doc = " + roleB.getName());
            }
        }
        wtCollection.addAll(qr);
        return wtCollection;
    }
}
