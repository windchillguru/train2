package ext.task.docapi.service.referencelink;

import com.ptc.netmarkets.model.NmOid;
import wt.doc.WTDocumentMaster;
import wt.fc.*;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTCollection;
import wt.part.WTPart;
import wt.part.WTPartReferenceLink;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;

import java.beans.PropertyVetoException;

/**
 * @author 段鑫扬
 */
public class FindWTPartReferenceLink {
    /**
     * windchill TestDocAPI VR:wt.part.WTPart:104846
     *
     * @param oid
     * @return
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static WTCollection findWTPartReferenceLink(String oid) throws WTException, PropertyVetoException {
        WTCollection wtCollection = new WTArrayList();
        WTPart wtpart = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            wtpart = (WTPart) refObject;
        }
        QuerySpec qs = new QuerySpec(WTPartReferenceLink.class);

        String roleAObjRefKeyId = BinaryLink.ROLE_AOBJECT_REF + "." + ObjectReference.KEY + "." + ObjectIdentifier.ID;
        long objectIdentifier = PersistenceHelper.getObjectIdentifier(wtpart).getId();
        SearchCondition sc = new SearchCondition(WTPartReferenceLink.class, roleAObjRefKeyId
                , SearchCondition.EQUAL, objectIdentifier);
        qs.appendWhere(sc, new int[]{0});

        QueryResult qr = PersistenceHelper.manager.find(qs);
        if (qr != null) {
            while (qr.hasMoreElements()) {
                Object nextElement = qr.nextElement();
                if (nextElement != null && nextElement instanceof WTPartReferenceLink) {
                    WTPartReferenceLink referenceLink = (WTPartReferenceLink) nextElement;
                    WTPart referencedBy = referenceLink.getReferencedBy();
                    System.out.println("referencedBy = " + referencedBy.getName());
                    WTDocumentMaster references = referenceLink.getReferences();
                    System.out.println("references = " + references.getName());
                }
            }
        }
        wtCollection.addAll(qr);

        return wtCollection;
    }
}
