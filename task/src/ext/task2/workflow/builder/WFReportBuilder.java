package ext.task2.workflow.builder;

import com.ptc.core.meta.type.mgmt.server.impl.TypeDomainHelper;
import com.ptc.mvc.components.*;
import com.ptc.mvc.util.ClientMessageSource;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.workflow.NmWorkflowHelper;
import ext.generic.workflow.util.WorkflowHelper;
import ext.pi.core.PIWorkflowHelper;
import ext.task2.workflow.bean.WFReportBean;
import ext.task2.workflow.resource.workflowRB;
import org.apache.log4j.Logger;
import wt.doc.WTDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.log4j.LogR;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionServerHelper;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtility;
import wt.util.WTException;
import wt.vc.config.LatestConfigSpec;
import wt.workflow.engine.WfProcess;
import wt.workflow.engine.WfRequester;
import wt.workflow.engine.WfRequesterActivity;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WfAssignmentState;
import wt.workflow.work.WorkItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 段鑫扬
 * @version 2020/1/14
 * 结案资料通过率报表
 * 只要结案资料相关信息。
 */
@ComponentBuilder("ext.task2.workflow.builder.WFReportBuilder")
public class WFReportBuilder extends AbstractComponentBuilder {
    private static final String CLASSNAME = WFReportBuilder.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = workflowRB.class.getName();
    private ClientMessageSource message = getMessageSource(RESOURCE);

    private static final String ACTIVITY_NAME = "审核";

    /**
     * 设置表格列
     *
     * @param componentParams 组件参数
     * @return
     * @throws WTException
     */
    @Override
    public ComponentConfig buildComponentConfig(ComponentParams componentParams) throws WTException {
        TableConfig tableConfig = null;
        try {
            ComponentConfigFactory factory = getComponentConfigFactory();
            tableConfig = factory.newTableConfig();
            tableConfig.setLabel(message.getMessage(workflowRB.TASK_WFREPORT_TITLE));
            //系统的导出操作
            tableConfig.setActionModel("folderbrowser_toolbar_exportlisttofile_submenu");
            tableConfig.addComponent(factory.newColumnConfig("wfName", "流程名称", true));
            tableConfig.addComponent(factory.newColumnConfig("techProductGroup", "技术产品组", true));
            tableConfig.addComponent(factory.newColumnConfig("creator", "提出人", true));
            tableConfig.addComponent(factory.newColumnConfig("createTime", "提出时间", true));
            tableConfig.addComponent(factory.newColumnConfig("auditor", "审核人", true));
            tableConfig.addComponent(factory.newColumnConfig("auditTime", "审核时间", true));
            tableConfig.addComponent(factory.newColumnConfig("rejectMsg", "驳回原因", true));

        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
        return tableConfig;
    }

    /**
     * 表格数据
     *
     * @param componentConfig 表格配置
     * @param componentParams 组件参数
     * @return 表格数据
     * @throws Exception
     */
    @Override
    public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams) throws Exception {

        List<WFReportBean> list = null;
        boolean enforced = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            QuerySpec qs = buildQS();
            //获取结案资料对象
            QueryResult qr = PersistenceHelper.manager.find(qs);
            //最新小版本对象
            qr = new LatestConfigSpec().process(qr);
            //数据集合
            list = new ArrayList<>(qr.size());
            while (qr.hasMoreElements()) {
                WTDocument document = (WTDocument) qr.nextElement();
                //获取的工作流，由结案资料启动的对象
                WfProcess process = getProcess(document);
                if (process != null) {
                    QueryResult wfActivities = PIWorkflowHelper.service.findWfActivities(process, ACTIVITY_NAME);
                    if (wfActivities != null) {
                        if (wfActivities.hasMoreElements()) {
                            //获取审核节点 （流程中只有一个审核节点）
                            WfAssignedActivity activity = (WfAssignedActivity) wfActivities.nextElement();
                            //此流程中所有已完成的审核 节点的路由选项
                            QueryResult workItems = PIWorkflowHelper.service.findWorkItems(activity, new WfAssignmentState[]{WfAssignmentState.COMPLETED});
                            while (workItems.hasMoreElements()) {
                                WorkItem workItem = (WorkItem) workItems.nextElement();
                                String voteEvent = WorkflowHelper.getVoteEvent(workItem);
                                if ("驳回".equals(voteEvent)) {
                                    //有驳回则创建  报表数据对象
                                    WFReportBean bean = new WFReportBean(process, workItems);
                                    list.add(bean);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (WTException e) {
            e.printStackTrace();
            throw new WTException(e);
        } finally {
            SessionServerHelper.manager.setAccessEnforced(enforced);
        }
        return list;
    }

    /**
     * 构建查询条件，查询所有的结案资料的对象
     *
     * @return 查询条件
     */
    private QuerySpec buildQS() throws WTException {
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

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>qs by softType sql=" + qs);
        }
        return qs;
    }

    /**
     * 根据pbo获取process对象
     *
     * @param persistable pbo
     * @return process
     * @throws WTException
     */
    private WfProcess getProcess(Persistable persistable) throws WTException {
        NmOid nmoid = NmOid.newNmOid(PersistenceHelper.getObjectIdentifier(persistable));
        QueryResult processResult = NmWorkflowHelper.service.getRoutingHistoryData(nmoid);
        WfProcess process = null;
        while (processResult.hasMoreElements()) {
            process = (WfProcess) processResult.nextElement();
            // 判断wfRequester,如果wfRequester不为空,且类型为WfRequesterActivity那么process就是子流程
            WfRequester wfRequester = process.getRequester();
            if ((!(wfRequester instanceof WfRequesterActivity))) {
                break;
            }
        }
        return process;
    }

}
