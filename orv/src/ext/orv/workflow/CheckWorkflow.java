package ext.orv.workflow;

import com.ptc.core.lwc.server.PersistableAdapter;
import ext.generic.workflow.WorkFlowBase;
import ext.generic.workflow.application.util.WFApplyUtil;
import ext.lang.PIStringUtils;
import ext.pi.core.PIPartHelper;
import ext.pi.core.PIWorkflowHelper;
import org.apache.log4j.Logger;
import wt.fc.*;
import wt.fc.collections.WTArrayList;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.definition.service.IBADefinitionHelper;
import wt.iba.value.AbstractValue;
import wt.iba.value.StringValue;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.vc.config.LatestConfigSpec;
import wt.workflow.engine.WfProcess;

import java.rmi.RemoteException;
import java.util.Iterator;

/**
 * 验证编码申请流程和部件签审流程编制提交节点的随迁对象
 *料号唯一
 * @author 段鑫扬
 * @version 2019/12/30
 */
public class CheckWorkflow extends WorkFlowBase {
    private static final String CLASSNAME = CheckWorkflow.class.getName();
    private static final Logger logger;
    private static final String ibaName = "Liaohao";

    static {
        logger = LogR.getLogger(CLASSNAME);
    }

    public CheckWorkflow() {
    }

    public CheckWorkflow(WTObject pbo, ObjectReference self) {
        if (pbo != null && self != null) {
            this.pbo = pbo;
            this.self = self;
        }
    }

    /**
     * 验证IBA属性料号唯一。
     *
     * @throws WTException
     */
    public void checkIBALiaohao() throws WTException {
        //通过启动工作流按钮启动工作流才能使用封装包
        WfProcess process = PIWorkflowHelper.service.getProcess(self);
        //获取随迁对象
        WTArrayList processObjs = WFApplyUtil.fetchReviewObjs(process);
        int size = processObjs.size();
        StringBuilder errMSg = new StringBuilder("");
        for (int i = 0; i < size; i++) {
            Persistable persistable = processObjs.getPersistable(i);
            if (persistable instanceof WTPart) {
                WTPart part = (WTPart) persistable;
                PersistableAdapter adapter = new PersistableAdapter(part, null, SessionHelper.manager.getLocale(), null);
                adapter.load(ibaName);
                //当前对象的料号value
                String value = (String) adapter.get(ibaName);
                if (!PIStringUtils.hasText(value)) {
                    continue;
                }
                WTArrayList list = null;
                try {
                    list = queryPart(ibaName, value, part);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (list != null && list.size() > 0) {
                    errMSg.append(" 随签物料：").append(part.getNumber()).append("和");
                    Iterator iterator = list.persistableIterator();
                    while (iterator.hasNext()) {
                        //重复物料
                        Object next = iterator.next();
                        if (next instanceof WTPart) {
                            WTPart wtPart = (WTPart) next;
                            //和当前对象料号重复的部件对象的编号
                            errMSg.append(" 物料：").append(wtPart.getNumber());
                        }
                    }
                    errMSg.append("的料号重复");
                }
            }

        }
        if (PIStringUtils.hasText(errMSg.toString())) {
            throw new WTException(errMSg.toString());
        }
    }

    /**
     * 根据iba属性和属性值，查询对应的部件 有可能有多个
     *
     * @param ibaName
     * @param value
     * @param part    验证的部件,随迁对象
     * @return
     */
    private WTArrayList queryPart(String ibaName, String value, WTPart part) throws WTException, RemoteException {
        WTArrayList list = new WTArrayList();
        if (PIStringUtils.hasText(ibaName) && (value != null)) {
            QuerySpec qs = new QuerySpec(WTPart.class);
            qs.setAdvancedQueryEnabled(true);
            //iba查询
            AttributeDefDefaultView addv = IBADefinitionHelper.service.getAttributeDefDefaultViewByPath(ibaName);
            if (addv != null) {
                long ibaDefId = addv.getObjectID().getId();//拿到IBA属性定义的id
                logger.debug("ibaName=" + ibaName + ",ibaDefId=" + ibaDefId);

                QuerySpec qsub = new QuerySpec();
                int index = qsub.appendClassList(StringValue.class, false);
                //查询ida3a4列
                ClassAttribute ca = new ClassAttribute(StringValue.class, AbstractValue.IBAHOLDER_REFERENCE + "."
                        + ObjectReference.KEY + "." + ObjectIdentifier.ID);
                qsub.appendSelect(ca, new int[]{index}, false);

                //指定idA3A6列的值
                SearchCondition sc = new SearchCondition(StringValue.class, StringValue.DEFINITION_REFERENCE + ""
                        + "." + ObjectReference.KEY + "."
                        + ObjectIdentifier.ID, SearchCondition.EQUAL, ibaDefId);
                qsub.appendWhere(sc, new int[]{index});
                qsub.appendAnd();

                sc = new SearchCondition(StringValue.class, StringValue.VALUE2,
                        SearchCondition.EQUAL, value);

                qsub.appendWhere(sc, new int[]{index});
                //构造子查询
                logger.debug("子查询sql=" + qsub);
                SubSelectExpression sse = new SubSelectExpression(qsub);

                //查询最新小版本
                sc = new SearchCondition(WTPart.class, WTPart.LATEST_ITERATION, SearchCondition.IS_TRUE);
                qs.appendWhere(sc, new int[]{0});
                qs.appendAnd();
                //过滤随迁对象
                sc = new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.NOT_EQUAL, part.getNumber());
                qs.appendWhere(sc, new int[]{0});
                qs.appendAnd();

                //子查询添加到主查询
                ClassAttribute ida2a2 = new ClassAttribute(WTPart.class,
                        Persistable.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID);
                qs.appendWhere(new SearchCondition(ida2a2, SearchCondition.IN, sse), new int[]{0});

                logger.debug("查询sql=" + qs);
            }
            QueryResult qr = PersistenceHelper.manager.find(qs);
            qr = new LatestConfigSpec().process(qr);
            if (qr != null) {
                while (qr.hasMoreElements()) {
                    WTPart wtPart = (WTPart) qr.nextElement();
                    //最新版本的部件
                    WTPart latestPart = PIPartHelper.service.findWTPart(wtPart.getMaster(), wtPart.getViewName());
                    if (PersistenceHelper.isEquivalent(wtPart, latestPart)) {
                        //如果是最新版本
                        list.add(wtPart);
                    }
                }
            }
        }
        return list;
    }
}
