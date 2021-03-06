package ext.task.docapi.service.dependencylink;

import com.ptc.netmarkets.model.NmOid;
import wt.doc.WTDocument;
import wt.doc.WTDocumentHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTCollection;
import wt.util.WTException;

import java.beans.PropertyVetoException;

/**
 * @author 段鑫扬
 */
public class FindDependsOnDocs {
    /**
     * @param oid
     * @return 参考文档
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static WTCollection findDependsOnDocs(String oid) throws WTException, PropertyVetoException {
        WTCollection wtCollection = new WTArrayList();

        WTDocument wtDocument = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTDocument) {
            wtDocument = (WTDocument) refObject;
        }

        QueryResult qr = WTDocumentHelper.service.getDependsOnWTDocuments(wtDocument);
        if (qr != null) {
            while (qr.hasMoreElements()) {
                WTDocument document = (WTDocument) qr.nextElement();
                System.out.println("DependsOn document = " + document.getName());
            }
        }
        wtCollection.addAll(qr);
        return wtCollection;
    }
}
