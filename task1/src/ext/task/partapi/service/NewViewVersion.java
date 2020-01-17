package ext.task.partapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.fc.PersistenceHelper;
import wt.part.WTPart;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.views.View;
import wt.vc.views.ViewHelper;

/**
 * @author 段鑫扬
 */
public class NewViewVersion {

    /**
     * 新建视图版本
     *
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static WTPart newViewVersion(String oid) throws WTException, WTPropertyVetoException {
        WTPart newViewPart = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            WTPart wtPart = (WTPart) refObject;
            View manufacturing = ViewHelper.service.getView("Manufacturing");

            //新建视图
            newViewPart = (WTPart) ViewHelper.service.newBranchForView(wtPart, manufacturing);
            newViewPart = (WTPart) PersistenceHelper.manager.save(newViewPart);

        }
        return newViewPart;
    }
}