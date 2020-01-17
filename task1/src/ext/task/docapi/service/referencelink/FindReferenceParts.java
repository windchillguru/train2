package ext.task.docapi.service.referencelink;

import com.ptc.netmarkets.model.NmOid;
import com.ptc.windchill.enterprise.part.commands.PartDocServiceCommand;
import wt.doc.WTDocument;
import wt.fc.ObjectReference;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTCollection;
import wt.part.WTPart;
import wt.util.WTException;

import java.beans.PropertyVetoException;

/**
 * @author 段鑫扬
 */
public class FindReferenceParts {
    /**
     * windchill TestDocAPI VR:wt.doc.WTDocument:105832
     *
     * @param oid 文档oid
     * @return
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static WTCollection findReferenceParts(String oid) throws WTException, PropertyVetoException {
        WTDocument wtDocument = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTDocument) {
            wtDocument = (WTDocument) refObject;
        }

        WTArrayList refParts = PartDocServiceCommand.getAssociatedRefParts(wtDocument);

        for (Object refPart : refParts) {
            ObjectReference objectReference = (ObjectReference) refPart;
            WTPart part = (WTPart) objectReference.getObject();
            System.out.println(" refPart = " + part.getName());
        }
        return refParts;
    }
}
