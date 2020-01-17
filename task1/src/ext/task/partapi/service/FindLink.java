package ext.task.partapi.service;

import com.google.gwt.rpc.client.impl.RemoteException;
import com.ptc.netmarkets.model.NmOid;
import wt.fc.*;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartUsageLink;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * @author 段鑫扬
 */
public class FindLink {
    /**
     * windchill TestPartAPI4 OR:wt.part.WTPart:104644  OR:wt.part.WTPart:103558
     */
    public static WTPartUsageLink findLink(String parentOid, String childOid) throws RemoteException, WTException, WTPropertyVetoException {
        WTPartUsageLink link = null;
        WTPart parentPart = null;
        NmOid parentNmOid = NmOid.newNmOid(parentOid);
        Object parentNmOidRefObject = parentNmOid.getRefObject();
        if (parentNmOidRefObject != null && parentNmOidRefObject instanceof WTPart) {
            parentPart = (WTPart) parentNmOidRefObject;
        }

        WTPart childPart = null;
        NmOid childNmOid = NmOid.newNmOid(childOid);
        Object refObject = childNmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            childPart = (WTPart) refObject;
        }

        QuerySpec qs = new QuerySpec(WTPartUsageLink.class);
        SearchCondition roleAWhere = new SearchCondition(WTPartUsageLink.class, BinaryLink.ROLE_AOBJECT_REF + "." + ObjectReference.KEY + "."
                + ObjectIdentifier.ID, SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(parentPart).getId());
        qs.appendWhere(roleAWhere, new int[]{0});

        qs.appendAnd();

        SearchCondition roleBWhere = new SearchCondition(WTPartUsageLink.class, BinaryLink.ROLE_BOBJECT_REF + "." + ObjectReference.KEY + "."
                + ObjectIdentifier.ID, SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(childPart.getMaster()).getId());
        qs.appendWhere(roleBWhere, new int[]{0});

        QueryResult qr = PersistenceHelper.manager.find(qs);
        if (qr != null && qr.hasMoreElements()) {
            link = (WTPartUsageLink) qr.nextElement();
            WTPart roleA = link.getUsedBy();
            WTPartMaster roleB = link.getUses();
            System.out.println("link" + link);
            System.out.println("roleA parent :" + roleA.getName());
            System.out.println("roleB child :" + roleB.getName());
        }
        return link;
    }

}

