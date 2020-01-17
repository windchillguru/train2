package ext.task.cadapi;

import com.ptc.netmarkets.model.NmOid;
import com.ptc.windchill.enterprise.part.commands.AssociationLinkObject;
import com.ptc.windchill.enterprise.part.commands.PartDocServiceCommand;
import com.ptc.windchill.uwgm.common.autoassociate.AutoAssociateHelper;
import com.ptc.windchill.uwgm.common.navigate.AssociatedItemsInfo;
import com.ptc.windchill.uwgm.common.navigate.AssociationTracer;
import org.apache.log4j.Logger;
import wt.epm.EPMDocument;
import wt.epm.build.EPMBuildRule;
import wt.epm.retriever.ResultGraph;
import wt.fc.BinaryLink;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTCollection;
import wt.httpgw.GatewayAuthenticator;
import wt.log4j.LogR;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * 测试文档相关的API
 * 创建部件 自定义编码
 *
 * @author 段鑫扬
 */
public class TestCADAPI implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = TestCADAPI.class.getName();
    private static final Logger logger = LogR.getLogger(CLASSNAME);

    public static void main(String[] args) {
        if (args.length >= 0) {
            Class[] clsAry = {String[].class};
            Object[] objAry = {args};
            String method = "testCADAPI";//执行的方法
            try {
                if (!RemoteMethodServer.ServerFlag) {//是否远程调用
                    GatewayAuthenticator auth = new GatewayAuthenticator();
                    auth.setRemoteUser("Administrator");// 直接设置管理员，执行命令时，不会再弹出输入用户名密码框
                    RemoteMethodServer.getDefault().setAuthenticator(auth);
                    /*
                     * method 执行的方法
                     * CLASSNAME 类名
                     * clsAry
                     * objAry args 参数
                     * */
                    RemoteMethodServer.getDefault().invoke(method, CLASSNAME, null, clsAry, objAry);

                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static void testCADAPI(String[] args) throws WTException, PropertyVetoException, IOException {
        if (args.length > 0) {

        }
    }

    public static WTCollection findEPMBuildRules(String oid) throws WTException {
        EPMDocument epmDoc = getEPMDoc(oid);
        WTArrayList wtArrayList = new WTArrayList();
        wtArrayList.add(epmDoc);

        //
        AssociatedItemsInfo associatedItemsInfo = new AssociatedItemsInfo();
        associatedItemsInfo.setTraceModelItems(false);

        WTCollection links = AssociationTracer.getAssociatedLinks(wtArrayList, associatedItemsInfo, AssociationTracer.Type.getAll());
        for (Object link : links) {
            show((EPMBuildRule) link);
        }
        return links;

    }

    public static WTCollection findEPMBuildRulesByPart(String oid) throws WTException {
        WTCollection links = new WTArrayList();
        WTPart part = getPart(oid);
        Collection empsAndLinks = PartDocServiceCommand.getAssociatedCADDocumentsAndLinks(part);
        if (empsAndLinks != null) {
            for (Object empsAndLink : empsAndLinks) {
                AssociationLinkObject associationLinkObject = (AssociationLinkObject) empsAndLink;
                BinaryLink link = associationLinkObject.getLink();
                ((WTArrayList) links).add(link);
            }
        }
        for (Object link : links) {
            show((EPMBuildRule) link);
        }
        return links;
    }

    public static WTCollection findEPMBuildRulesByPartAndEpm(String epmOid, String partOid) throws WTException {
        WTCollection links = new WTArrayList();
        WTPart part = getPart(partOid);
        EPMDocument epmDoc = getEPMDoc(epmOid);

        QuerySpec qs = new QuerySpec(EPMBuildRule.class);
        long roleABranchId = epmDoc.getBranchIdentifier();
        SearchCondition sc = new SearchCondition(EPMBuildRule.class, "roleAObjectRef.key.branchId", "=", roleABranchId);
        qs.appendWhere(sc, new int[]{0});

        qs.appendAnd();
        long roleBBranchId = part.getBranchIdentifier();
        sc = new SearchCondition(EPMBuildRule.class, "roleAObjectRef.key.branchId", "=", roleBBranchId);
        qs.appendWhere(sc, new int[]{0});

        QueryResult qr = PersistenceHelper.manager.find(qs);

        if (qr != null) {
            while (qr.hasMoreElements()) {
                Object nextElement = qr.nextElement();
                show((EPMBuildRule) nextElement);
            }
        }
        links.addAll(qr);

        return links;
    }

    public static WTCollection findAssociatedCAD(String partOid) throws WTException {
        WTCollection epms = new WTArrayList();
        WTPart part = getPart(partOid);
        QueryResult qr = PartDocServiceCommand.getAssociatedCADDocuments(part);
        if (qr != null) {
            while (qr.hasMoreElements()) {
                EPMDocument nextElement = (EPMDocument) qr.nextElement();
                System.out.println("nextElement = " + nextElement.getCADName());
            }
        }
        epms.addAll(qr);
        return epms;
    }

    public static WTCollection findAssociatedParts(String oid) throws WTException {
        WTArrayList wtArrayList = new WTArrayList();
        EPMDocument doc = getEPMDoc(oid);
        wtArrayList.add(doc);

        AssociatedItemsInfo associatedItemsInfo = new AssociatedItemsInfo();
        associatedItemsInfo.setTraceModelItems(false);

        WTCollection links = AssociationTracer.getAssociatedWTParts(wtArrayList, associatedItemsInfo, AssociationTracer.Type.getAll());
        for (Object link : links) {
            show((EPMBuildRule) link);
        }

        return wtArrayList;
    }

    public static WTPart findActiveAssociatedPart(String epmOid) throws WTException {
        WTArrayList wtArrayList = new WTArrayList();
        EPMDocument epm = getEPMDoc(epmOid);
        wtArrayList.add(epm);
        WTPart part = null;

        ResultGraph resultGraph = AutoAssociateHelper.getAssociatedResultGraph(wtArrayList, true, false, null);

        if (resultGraph != null && (epm != null)) {
            ResultGraph.Node node = resultGraph.getNode(epm);
            for (ResultGraph.Link adjacentLink : node.getAdjacentLinks()) {
                BinaryLink linkObject = adjacentLink.getLinkObject();
                if (linkObject instanceof EPMBuildRule) {
                    part = (WTPart) linkObject.getOtherObject(epm);
                    break;
                }
            }
        }
        return part;
    }


    private static WTPart getPart(String oid) throws WTException {
        WTPart part = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            part = (WTPart) refObject;
        }
        return part;
    }

    private static EPMDocument getEPMDoc(String oid) throws WTException {
        EPMDocument doc = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof EPMDocument) {
            doc = (EPMDocument) refObject;
        }
        return doc;
    }

    private static void show(EPMBuildRule epmBuildRule) throws WTException {
        EPMDocument roleAEPM = (EPMDocument) epmBuildRule.getRoleAObject();
        System.out.println("roleAEPM = " + roleAEPM.getCADName());
        WTPart roleBPart = (WTPart) epmBuildRule.getRoleBObject();
        System.out.println("roleBPart = " + roleBPart.getName());
    }
}



