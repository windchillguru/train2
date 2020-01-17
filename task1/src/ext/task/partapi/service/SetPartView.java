package ext.task.partapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.part.WTPart;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.views.View;
import wt.vc.views.ViewHelper;
import wt.vc.views.ViewReference;

/**
 * @author 段鑫扬
 */
public class SetPartView {

    /**
     * 修改部件视图
     *
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static WTPart setPartView(String oid) throws WTException {
        WTPart wtPart = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            wtPart = (WTPart) refObject;
            View manufacturing = ViewHelper.service.getView("Manufacturing");
            ViewReference viewReference = ViewReference.newViewReference(manufacturing);
            ViewHelper.service.reassignView(wtPart.getMaster(), viewReference);
        }
        return wtPart;
    }
}