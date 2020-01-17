package ext.task2.change.builder;

import com.ptc.mvc.components.*;
import com.ptc.mvc.util.ClientMessageSource;
import ext.pi.core.PIChangeHelper;
import ext.task2.change.bean.ChangeReportBean;
import ext.task2.change.resource.reportRB;
import org.apache.log4j.Logger;
import wt.change2.WTChangeActivity2;
import wt.change2.WTChangeOrder2;
import wt.change2.WTChangeRequest2;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTCollection;
import wt.log4j.LogR;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 段鑫扬
 * @version 2020/1/10
 * 产品问题反馈及时回复率
 * 更改相关的报表
 */
@ComponentBuilder("ext.task2.change.builder.ChangeReportBuilder")
public class ChangeReportBuilder extends AbstractComponentBuilder {
    private static final String CLASSNAME = ChangeReportBuilder.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = reportRB.class.getName();
    private ClientMessageSource message = getMessageSource(RESOURCE);

    //已解决 生命周期状态内部值
    private static final String RESOLVED_STATE = "RESOLVED";

    /**
     * 返回数据，只要已解决状态的更改请求(ecr)的相关数据
     *
     * @param componentConfig
     * @param componentParams
     * @return
     * @throws Exception
     */
    @Override
    public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams) throws Exception {
        List list = null;
        try {
            list = new ArrayList();
            //构建查询条件
            QuerySpec qs = buildQS();
            QueryResult qr = PersistenceHelper.manager.find(qs);
            if (qr != null) {
                while (qr.hasMoreElements()) {
                    //更改请求
                    WTChangeRequest2 ecr = (WTChangeRequest2) qr.nextElement();
                    WTCollection relatedChangesProcesses = PIChangeHelper.service.findRelatedChangesProcesses(ecr, true);
                    //更改通告
                    WTChangeOrder2 ecn = null;
                    Collection collection = relatedChangesProcesses.persistableCollection();
                    for (Object obj : collection) {
                        if (obj instanceof WTChangeOrder2) {
                            ecn = (WTChangeOrder2) obj;
                            break;
                        }
                    }
                    //更改任务
                    WTChangeActivity2 eca = null;
                    QueryResult changeActivities = PIChangeHelper.service.findChangeActivities(ecn, true);
                    if (changeActivities.hasMoreElements()) {
                        eca = (WTChangeActivity2) changeActivities.nextElement();
                    }
                    ChangeReportBean changeReportBean = new ChangeReportBean(ecr, eca, ecn);
                    list.add(changeReportBean);
                }
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>返回的数据 list=" + list);
            }
        } catch (WTException e) {
            e.printStackTrace();
            throw new WTException(e);
        }
        return list;
    }

    /**
     * 构建表格列
     *
     * @param componentParams
     * @return
     * @throws WTException
     */
    @Override
    public ComponentConfig buildComponentConfig(ComponentParams componentParams) throws WTException {
        TableConfig tableConfig = null;
        try {
            ComponentConfigFactory factory = getComponentConfigFactory();
            tableConfig = factory.newTableConfig();
            tableConfig.setLabel(message.getMessage(reportRB.TASK_CHANGEREPORT_TITLE));
            tableConfig.setActionModel("folderbrowser_toolbar_exportlisttofile_submenu");
            tableConfig.addComponent(factory.newColumnConfig("ecrNumber", "ECR单号", true));
            tableConfig.addComponent(factory.newColumnConfig("ecrName", "ECR名称", true));
            tableConfig.addComponent(factory.newColumnConfig("ecrCreator", "提出人", true));
            tableConfig.addComponent(factory.newColumnConfig("ecrCreateTime", "提出时间", true));
            tableConfig.addComponent(factory.newColumnConfig("ecrGrade", "等级", true));
            tableConfig.addComponent(factory.newColumnConfig("ecaCreateTime", "ECA提出时间", true));
            tableConfig.addComponent(factory.newColumnConfig("ecaResponsible", "ECA责任人", true));
            tableConfig.addComponent(factory.newColumnConfig("ecnAssociatedItemInfo", "关联的项目信息", true));
            tableConfig.addComponent(factory.newColumnConfig("recoveryRate", "及时回复率", true));
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
        return tableConfig;
    }

    /**
     * 构建查询条件，查询所有状态已解决的更改请求（ecr)
     *
     * @return 查询条件
     */
    private QuerySpec buildQS() throws QueryException {
        QuerySpec qs = new QuerySpec(WTChangeRequest2.class);
        SearchCondition sc = new SearchCondition(WTChangeRequest2.class, WTChangeRequest2.LIFE_CYCLE_STATE, SearchCondition.EQUAL, RESOLVED_STATE);
        qs.appendWhere(sc, new int[]{0});
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>qs= " + qs);
        }
        return qs;
    }


}
