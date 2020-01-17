package ext.task.part.listener;

import ext.pi.core.PIAttributeHelper;
import org.apache.log4j.Logger;
import wt.events.KeyedEvent;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.services.ManagerException;
import wt.services.ServiceEventListenerAdapter;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import wt.vc.wip.WorkInProgressServiceEvent;

import java.io.Serializable;

/**
 * 清除部件iba的监听
 *
 * @author 段鑫扬
 */
public class StandardClearPartAttrService extends StandardManager implements ClearPartAttrListener, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = StandardClearPartAttrService.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    private ServiceEventListenerAdapter listener;
    private static final String IBA_DELETE_ATTRTESTIBA = "testIBA";

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
    public static StandardClearPartAttrService newStandardClearPartAttrService() throws WTException {
        StandardClearPartAttrService service = new StandardClearPartAttrService();
        service.initialize();
        return service;
    }

    @Override
    protected synchronized void performStartupProcess() throws ManagerException {
        listener = new ClearPartAttrListenerImpl(getConceptualClassname());
        LOGGER.info("清除属性监听注册开始----");
        //注册监听，事件类型
        getManagerService().addEventListener(listener, WorkInProgressServiceEvent.generateEventKey(WorkInProgressServiceEvent.POST_CHECKIN));
        LOGGER.info("清除属性监听注册结束----");
    }

    /**
     * 内部类，处理监听的功能
     */
    class ClearPartAttrListenerImpl extends ServiceEventListenerAdapter {
        public ClearPartAttrListenerImpl(String s) {
            super(s);
        }

        /**
         * 检入事件，清除IBA属性，部件类型
         *
         * @param obj
         * @throws Exception
         */
        @Override
        public void notifyVetoableEvent(Object obj) throws Exception {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("obj.class =" + obj.getClass());
            }
            //当前触发的事件对象
            KeyedEvent keyedEvent = (KeyedEvent) obj;
            //事件的目标对象，操作的持久化对象
            Object target = keyedEvent.getEventTarget();
            //事件类型
            String eventType = keyedEvent.getEventType();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("target ==>" + target + ",eventType ==>" + eventType);
            }

            if (eventType != null && eventType.equals(WorkInProgressServiceEvent.POST_CHECKIN)) {
                //检入事件,监听处理开始
                if (target instanceof WTPart) {
                    WTPart part = (WTPart) target;
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("part.display ==>" + part.getDisplayIdentifier());
                    }
                    if (!part.isLatestIteration()) {
                        //不是最新小版本，
                        // 第二参数 true将尝试获取最新的迭代，即使标记为delete。
                        part = (WTPart) VersionControlHelper.service.getLatestIteration(part, true);
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("after.latestPart.display ==>" + part.getDisplayIdentifier());
                        }
                    }
                    //将该属性清除数据。
                    PIAttributeHelper.service.forceUpdateSoftAttribute(part, IBA_DELETE_ATTRTESTIBA, "");
                }
            }
        }
    }
}
