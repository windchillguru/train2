package ext.task.docapi.service.deslink;

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
public class FindDescribeParts {
    /**
     * windchill TestDocAPI VR:wt.doc.WTDocument:101741
     *
     * @param oid 文档oid
     * @return
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static WTCollection findReferenceParts(String oid) throws WTException, PropertyVetoException {
        WTCollection wtCollection = new WTArrayList();
        WTDocument wtDocument = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTDocument) {
            wtDocument = (WTDocument) refObject;
        }

        QueryResult qr = PartDocServiceCommand.getAssociatedDescParts(wtDocument);
        if (qr != null) {
            while (qr.hasMoreElements()) {
                Object nextElement = qr.nextElement();
                if (nextElement != null && nextElement instanceof WTPart) {
                    WTPart part = (WTPart) nextElement;
                    System.out.println("desc part = " + part.getName());
                }
            }
        }
        wtCollection.addAll(qr);
        return wtCollection;
    }
}
