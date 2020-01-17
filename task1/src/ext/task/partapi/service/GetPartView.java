package ext.task.partapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.part.WTPart;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.views.View;
import wt.vc.views.ViewReference;

/**
 * @author 段鑫扬
 */
public class GetPartView {

    /**
     * 获取部件视图
     * VR:wt.part.WTPart:103520
     *
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static View getPartView(String oid) throws WTException {
        View view = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            WTPart wtPart = (WTPart) refObject;
            ViewReference viewRef = wtPart.getView();
            view = (wt.vc.views.View) viewRef.getObject();
            System.out.println("wtPart view = " + view.getName());
        }
        return view;
    }
}