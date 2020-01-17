package ext.task.docapi.service.referencelink;

import com.ptc.netmarkets.model.NmOid;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
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
public class FindWTPartReferenceLink3 {
    /**
     * windchill TestDocAPI VR:wt.part.WTPart:104846 VR:wt.doc.WTDocument:105832
     *
     * @return
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static WTCollection findWTPartReferenceLink(String partOid, String docOid) throws WTException, PropertyVetoException {
        WTCollection wtCollection = new WTArrayList();

        WTPart wtpart = null;
        NmOid nmOid = NmOid.newNmOid(partOid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            wtpart = (WTPart) refObject;
        }
        WTDocumentMaster documentMaster = null;
        NmOid docNm = NmOid.newNmOid(docOid);
        Object docRefObj = docNm.getRefObject();
        if (docRefObj != null && docRefObj instanceof WTDocument) {
            documentMaster = (WTDocumentMaster) ((WTDocument) docRefObj).getMaster();
        }

        QuerySpec qs = new QuerySpec(WTPartReferenceLink.class);
        qs.appendWhere(new SearchCondition(WTPartReferenceLink.class, "roleAObjectRef.key", "="
                        , PersistenceHelper.getObjectIdentifier(wtpart))
                , new int[]{0});
        qs.appendAnd();
        qs.appendWhere(new SearchCondition(WTPartReferenceLink.class, "roleBObjectRef.key", "="
                        , PersistenceHelper.getObjectIdentifier(documentMaster))
                , new int[]{0});

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
