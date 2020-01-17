package ext.task.docapi.service.referencelink;

import com.ptc.netmarkets.model.NmOid;
import com.ptc.windchill.enterprise.part.commands.PartDocServiceCommand;
import wt.doc.WTDocument;
import wt.fc.QueryResult;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTCollection;
import wt.part.WTPart;
import wt.util.WTException;

import java.beans.PropertyVetoException;

/**
 * @author 段鑫扬
 */
public class FindReferenceDocs {
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

        QueryResult qr = PartDocServiceCommand.getAssociatedReferenceDocuments(wtPart);

        if (qr != null) {
            while (qr.hasMoreElements()) {
                Object nextElement = qr.nextElement();
                WTDocument document = (WTDocument) nextElement;
                System.out.println("document = " + document.getName());
            }
        }
        wtCollection.addAll(qr);
        return wtCollection;
    }
}
