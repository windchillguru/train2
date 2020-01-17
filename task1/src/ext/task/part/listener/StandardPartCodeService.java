package ext.task.part.listener;

import ext.lang.PIStringUtils;
import ext.pi.core.PICoreHelper;
import ext.task.part.IPartCodeIfc;
import ext.task.part.util.PartCodeUtil;
import org.apache.log4j.Logger;
import wt.events.KeyedEvent;
import wt.fc.PersistenceManagerEvent;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.services.ManagerException;
import wt.services.ServiceEventListenerAdapter;
import wt.services.StandardManager;
import wt.util.WTException;

import java.io.Serializable;

/**
 * 清除部件iba的监听
 *
 * @author 段鑫扬
 */
public class StandardPartCodeService extends StandardManager implements PartCodeListener, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = StandardPartCodeService.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

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
    public static StandardPartCodeService newStandardPartCodeService() throws WTException {
        StandardPartCodeService service = new StandardPartCodeService();
        service.initialize();
        return service;
    }

    @Override
    protected synchronized void performStartupProcess() throws ManagerException {
        listener = new PartCodeListenerIpml(getConceptualClassname());
        LOGGER.info("生成编码监听注册开始----");
        //注册监听，事件类型:保存持久化对象
        getManagerService().addEventListener(listener, PersistenceManagerEvent
                .generateEventKey(PersistenceManagerEvent.POST_STORE));
        LOGGER.info("生成编码监听注册结束----");
    }

    /**
     * 内部类，处理监听的功能
     */
    class PartCodeListenerIpml extends ServiceEventListenerAdapter {
        public PartCodeListenerIpml(String s) {
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

            if (eventType != null && eventType.equals(PersistenceManagerEvent.POST_STORE)) {
                //保存事件,监听处理开始
                if (target != null && target instanceof WTPart) {
                    WTPart part = (WTPart) target;
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("part.display ==>" + part.getDisplayIdentifier());
                    }
                    if (PICoreHelper.service.isCheckout(part)) {
                        //检出不处理
                        LOGGER.info("部件：" + part.getNumber() + "检出，非创建事件，不处理");
                        return;
                    }
                    if (!part.getNumber().startsWith(IPartCodeIfc.LS_PREFIX)) {
                        //非临时码
                        LOGGER.info("部件：" + part.getNumber() + "已经是正式码，不处理");
                        return;
                    }
                    String errMsg = PartCodeUtil.generateNumber(part, false);
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(">>>>errMsg = " + errMsg);
                    }
                    if (PIStringUtils.hasText(errMsg)) {
                        throw new WTException(errMsg);
                    }
                }
            }
        }
    }
}
