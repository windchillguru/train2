package ext.task.partapi;

import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.meta.type.mgmt.server.impl.TypeDomainHelper;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.workflow.NmWorkflowHelper;
import ext.generic.workflow.util.WorkflowHelper;
import ext.pi.core.PIWorkflowHelper;
import org.apache.log4j.Logger;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.httpgw.GatewayAuthenticator;
import wt.log4j.LogR;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.project.Role;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtility;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.config.LatestConfigSpec;
import wt.vc.views.View;
import wt.vc.views.ViewHelper;
import wt.vc.views.ViewReference;
import wt.workflow.engine.WfProcess;
import wt.workflow.engine.WfRequester;
import wt.workflow.engine.WfRequesterActivity;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WfAssignmentState;
import wt.workflow.work.WorkItem;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;

/**
 * 测试部件相关的API
 * 创建部件 自定义编码
 *
 * @author 段鑫扬
 */
public class TestPartAPI implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = TestPartAPI.class.getName();
    private static final Logger logger = LogR.getLogger(CLASSNAME);

    public static void main(String[] args) {
        if (args.length >= 0) {
            Class[] clsAry = {String[].class};
            Object[] objAry = {args};
            String method = "testPartAPI";//执行的方法
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


    public static void testPartAPI(String[] args) throws WTException, WTPropertyVetoException {
        String exchangeDomain = TypeDomainHelper.getExchangeDomain();
        //结案资料的全内部名称
        String softType = exchangeDomain + ".ClosinginfoDoc";
        QuerySpec qs = new QuerySpec(WTDocument.class);
        qs.setAdvancedQueryEnabled(true);
        //最新小版本
        SearchCondition sc = new SearchCondition(WTDocument.class, WTDocument.LATEST_ITERATION
                , SearchCondition.IS_TRUE);
        qs.appendWhere(sc, new int[]{0});

        qs.appendAnd();
        //获取软类型的数据
        TypeDefinitionReference typeDefinitionReference = TypedUtility.getTypeDefinitionReference(softType);
        sc = new SearchCondition(WTDocument.class, "typeDefinitionReference.key.branchId"
                , SearchCondition.EQUAL, typeDefinitionReference.getKey().getBranchId());
        qs.appendWhere(sc, new int[]{0});

        //获取结案资料对象
        QueryResult qr = PersistenceHelper.manager.find(qs);
        //最新小版本对象
        qr = new LatestConfigSpec().process(qr);
        while (qr.hasMoreElements()) {
            WTDocument document = (WTDocument) qr.nextElement();
            //获取的工作流，由结案资料启动的对象
            NmOid nmoid = NmOid.newNmOid(PersistenceHelper.getObjectIdentifier(document));
            QueryResult processResult = NmWorkflowHelper.service.getRoutingHistoryData(nmoid);
            WfProcess process = null;
            while (processResult.hasMoreElements()) {
                process = (WfProcess) processResult.nextElement();
                // 判断wfRequester,如果wfRequester不为空,且类型为WfRequesterActivity那么process就是子流程
                WfRequester wfRequester = process.getRequester();
                if ((wfRequester instanceof WfRequesterActivity))
                    continue;
                else {
                    break;
                }
            }
            if (process != null) {
                QueryResult wfActivities = PIWorkflowHelper.service.findWfActivities(process, "审核");
                if (wfActivities != null) {
                    if (wfActivities.hasMoreElements()) {
                        //获取审核节点 （流程中只有一个审核节点）
                        Object nextElement = wfActivities.nextElement();
                        if (nextElement instanceof WfAssignedActivity) {
                            WfAssignedActivity activity = (WfAssignedActivity) nextElement;
                            //此流程中所有已完成的审核节点
                            QueryResult workItems = PIWorkflowHelper.service.findWorkItems(activity, new WfAssignmentState[]{WfAssignmentState.COMPLETED});
                            while (workItems.hasMoreElements()) {
                                WorkItem workItem = (WorkItem) workItems.nextElement();
                                Role role = workItem.getRole();
                                //获取完成者
                                String completedBy = workItem.getCompletedBy();
                                String voteEvent = WorkflowHelper.getVoteEvent(workItem);
                                if ("驳回".equals(voteEvent)) {
                                    //驳回时间
                                    Timestamp modifyTimestamp = workItem.getModifyTimestamp();
                                }
                            }

                        }
                    }
                }
            }
        }

    }

    /**
     * 自定义编码创建零部件
     *
     * @throws WTException
     */
    private static void createPart() throws WTException {
        PersistableAdapter adapter = new PersistableAdapter("wt.part.WTPart", null, null);
        adapter.load("number", "name", "view");
        adapter.set("number", "P2019120301");
        adapter.set("name", "test part");
        adapter.set("view", "Design");
        WTPart part = (WTPart) adapter.apply();
        //创建部件对象
        part = (WTPart) PersistenceHelper.manager.save(part);
        if (logger.isDebugEnabled()) {
            logger.debug("Create Part Successfully, part=" + part.getDisplayIdentifier());
        }
    }

    /**
     * 创建部件 使用初始化规则
     *
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    private static void createPartWithORI() throws WTException, WTPropertyVetoException {
        WTPart part = WTPart.newWTPart();
        part.setNumber("P2019120302");
        part.setName("CreatePartWithORI");
        View viewObj = ViewHelper.service.getView("Design");
        part.setView(ViewReference.newViewReference(viewObj));//设置视图
        part = (WTPart) PersistenceHelper.manager.save(part);
        if (logger.isDebugEnabled()) {
            logger.debug("Create Part Successfully, part=" + part.getDisplayIdentifier());
        }
    }

}



