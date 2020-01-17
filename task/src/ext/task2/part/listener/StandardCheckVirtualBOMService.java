package ext.task2.part.listener;

import ext.pi.core.PIPartHelper;
import ext.task2.part.util.BOMUtil;
import org.apache.log4j.Logger;
import wt.events.KeyedEvent;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTCollection;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.part.WTPartSubstituteLink;
import wt.part.WTPartUsageLink;
import wt.services.ManagerException;
import wt.services.ServiceEventListenerAdapter;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import wt.vc.wip.WorkInProgressServiceEvent;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author 段鑫扬
 * @version 2020/1/7
 */
public class StandardCheckVirtualBOMService extends StandardManager implements Serializable, CheckVirtualBOMListener {
    private static final long serialVersionUID = 6107692431365858566L;
    private static final String CLASSNAME = StandardCheckBOMViewService.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String TOP_PART_NUMBER = "V0000001";

    private ServiceEventListenerAdapter listener;

    @Override
    public String getConceptualClassname() {
        return CLASSNAME;
    }

    /**
     * 返回监听实例
     *
     * @return
     * @throws WTException
     */
    public static StandardCheckVirtualBOMService newStandardCheckVirtualBOMService() throws WTException {
        StandardCheckVirtualBOMService service = new StandardCheckVirtualBOMService();
        service.initialize();
        return service;
    }

    @Override
    protected synchronized void performStartupProcess() throws ManagerException {
        listener = new CheckVirtualBOMListenerImpl(getConceptualClassname());
        LOGGER.info("BOM视图监听注册开始----");
        //注册监听，事件类型:检入
        getManagerService().addEventListener(listener, WorkInProgressServiceEvent.generateEventKey(WorkInProgressServiceEvent.POST_CHECKIN));
        LOGGER.info("BOM视图监听注册结束----");
    }

    class CheckVirtualBOMListenerImpl extends ServiceEventListenerAdapter {

        public CheckVirtualBOMListenerImpl(String s) {
            super(s);
        }

        @Override
        public void notifyVetoableEvent(Object obj) throws Exception {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("obj.class =" + obj.getClass());
            }
            //当前触发的事件对象
            KeyedEvent keyedEvent = (KeyedEvent) obj;
            //事件的目标对象，操作的持久化对象,原始版本
            Object target = keyedEvent.getEventTarget();
            //事件类型
            String eventType = keyedEvent.getEventType();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("target ==>" + target + ",eventType ==>" + eventType);
            }
            if (eventType == null) {
                return;
            }

            if (eventType.equals(WorkInProgressServiceEvent.POST_CHECKIN)) {
                //检入事件
                if (target instanceof WTPart) {
                    //原始版本的部件，需要获取工作副本
                    WTPart part = (WTPart) target;
                    if (!part.isLatestIteration()) {//一般监听的对象不是最新版本对象
                        //获取最新小版本的对象
                        part = (WTPart) VersionControlHelper.service.getLatestIteration(part, true);
                    }
                    if (!BOMUtil.isVirtualPart(part)) {
                        //不是虚拟部件
                        WTCollection children = PIPartHelper.service.findChildren(part);
                        Collection persistables = children.persistableCollection();
                        for (Object persistable : persistables) {
                            if (persistable instanceof WTPart) {
                                WTPart childPart = (WTPart) persistable;
                                //判断是否存在于虚拟BOM中
                                boolean exist = BOMUtil.existInVirtualBOM(childPart, TOP_PART_NUMBER);
                                if (exist) {
                                    //存在则需要添加局部替换关系，WTPartSubstituteLink
                                    addSubstituteLink(part, childPart);
                                }
                            }
                        }
                        return;
                    }
                    //执行虚拟bom的检查逻辑
                    if (!TOP_PART_NUMBER.equals(part.getNumber())) {//虚拟BOM顶层成品，不需要检查子件分类
                        //检查分类,如果有问题则抛出异常
                        BOMUtil.checkClassification(part);
                    }
                    //检查虚拟BOM,是否有重复物料
                    BOMUtil.checkVirtualBOM(TOP_PART_NUMBER);
                    //检验优先级
                    BOMUtil.checkPriority(part);
                }
            }
        }

        /**
         * 添加特定替换关系
         * 根据子件在虚拟BOM中查出替换物料，
         * 再根据父件和子件给子件添加特定替换关系
         * WTPartSubstituteLink
         *
         * @param wtPart 父件
         * @param part   子件
         */
        private void addSubstituteLink(WTPart wtPart, WTPart part) throws WTException {
            //替换部件
            List<WTPart> replaceParts = BOMUtil.findReplaceParts(part, TOP_PART_NUMBER);
            QueryResult usageLinks = PIPartHelper.service.findUsageLinks(wtPart, part);
            if (usageLinks.hasMoreElements()) {
                WTPartUsageLink link = (WTPartUsageLink) usageLinks.nextElement();
                WTArrayList wtArrayList = new WTArrayList();
                for (WTPart replacePart : replaceParts) {
                    QueryResult queryResult = PersistenceHelper.manager.find(WTPartSubstituteLink.class, link, WTPartSubstituteLink.SUBSTITUTE_FOR_ROLE, replacePart.getMaster());
                    if (queryResult.size() != 0) {
                        //当前替换部件的替换关系已经存在。
                        continue;
                    }
                    //创建特定替换关系，param1: 需要替换的link关系， param2 用来替换的部件
                    WTPartSubstituteLink wtPartSubstituteLink = WTPartSubstituteLink.newWTPartSubstituteLink(link, replacePart.getMaster());
                    wtArrayList.add(wtPartSubstituteLink);
                }
                //存储特定替换关系
                PersistenceHelper.manager.store(wtArrayList);
            }
        }
    }
}

