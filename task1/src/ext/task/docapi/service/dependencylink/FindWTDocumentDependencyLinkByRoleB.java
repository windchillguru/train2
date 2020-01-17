package ext.task.docapi.service.dependencylink;

import com.ptc.netmarkets.model.NmOid;
import wt.doc.WTDocument;
import wt.doc.WTDocumentDependencyLink;
import wt.fc.*;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTCollection;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;

import java.beans.PropertyVetoException;

/**
 * @author 段鑫扬
 */
public class FindWTDocumentDependencyLinkByRoleB {
    /**
     * windchill TestDocAPI VR:wt.doc.WTDocument:105848
     *
     * @param oid 105848
     * @return
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static WTCollection findWTDocumentDependencyLinkByRoleB(String oid) throws WTException, PropertyVetoException {
        WTCollection wtCollection = new WTArrayList();

        WTDocument wtDocument = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTDocument) {
            wtDocument = (WTDocument) refObject;
        }

        QuerySpec qs = new QuerySpec(WTDocumentDependencyLink.class);

        String roleAObjRefKeyId = BinaryLink.ROLE_BOBJECT_REF + "." + ObjectReference.KEY + "." + ObjectIdentifier.ID;
        long objectIdentifierId = PersistenceHelper.getObjectIdentifier(wtDocument).getId();
        SearchCondition sc = new SearchCondition(WTDocumentDependencyLink.class, roleAObjRefKeyId
                , SearchCondition.EQUAL, objectIdentifierId);
        qs.appendWhere(sc, new int[]{0});

        QueryResult qr = PersistenceHelper.manager.find(qs);
        if (qr != null) {
            while (qr.hasMoreElements()) {
                WTDocumentDependencyLink dependencyLink = (WTDocumentDependencyLink) qr.nextElement();
                WTDocument roleAObject = (WTDocument) dependencyLink.getRoleAObject();
                System.out.println("roleAObject = " + roleAObject.getName());
                WTDocument roleBObject = (WTDocument) dependencyLink.getRoleBObject();
                System.out.println("roleBObject = " + roleBObject.getName());
            }
        }
        wtCollection.addAll(qr);

        return wtCollection;
    }
}
