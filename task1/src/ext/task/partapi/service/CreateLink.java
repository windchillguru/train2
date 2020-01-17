package ext.task.partapi.service;

import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.netmarkets.model.NmOid;
import wt.fc.PersistenceHelper;
import wt.part.WTPart;
import wt.part.WTPartUsageLink;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.wip.WorkInProgressHelper;

/**
 * @author 段鑫扬
 */
public class CreateLink {

    /**
     * 创建link
     * windchill TestPartAPI4 VR:wt.part.WTPart:103901  VR:wt.part.WTPart:104014
     *
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static WTPartUsageLink createLink(String parentId, String childId) throws WTException, WTPropertyVetoException {
        WTPartUsageLink partUsageLink = null;
        WTPart parent = null;
        WTPart child = null;
        NmOid parentOId = NmOid.newNmOid(parentId);
        Object parentRefObject = parentOId.getRefObject();
        if (parentRefObject != null && parentRefObject instanceof WTPart) {
            parent = (WTPart) parentRefObject;
        }

        NmOid childOId = NmOid.newNmOid(childId);
        Object childOIdRefObject = childOId.getRefObject();
        if (childOIdRefObject != null && childOIdRefObject instanceof WTPart) {
            child = (WTPart) childOIdRefObject;
        }
        if (parent != null && child != null) {
            if (WorkInProgressHelper.isCheckedOut(parent, SessionHelper.getPrincipal())) {
                //检出状态
                //获取link的持久化对象
                PersistableAdapter adapter = new PersistableAdapter("wt.part.WTPartUsageLink", null, null);
                adapter.load("usedBy", "uses");
                adapter.set("usedBy", parent);
                adapter.set("uses", child.getMaster());

                partUsageLink = (WTPartUsageLink) adapter.apply();
                //持久化link对象
                partUsageLink = (WTPartUsageLink) PersistenceHelper.manager.save(partUsageLink);

            }
        }
        return partUsageLink;
    }
}