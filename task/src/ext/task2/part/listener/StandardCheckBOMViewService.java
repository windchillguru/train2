package ext.task2.part.listener;

import ext.lang.PIStringUtils;
import ext.task2.part.util.BOMUtil;
import org.apache.log4j.Logger;
import wt.events.KeyedEvent;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.services.ManagerException;
import wt.services.ServiceEventListenerAdapter;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.VersionControlHelper;
import wt.vc.wip.WorkInProgressServiceEvent;

import java.io.Serializable;
import java.util.List;

/**
 * @author 段鑫扬
 * @version 2020/1/6
 * 检查BOM视图（制造视图下不能下挂设计视图） 的监听
 */
public class StandardCheckBOMViewService extends StandardManager implements Serializable, CheckBOMViewListener {

    private static final long serialVersionUID = 145354963648848420L;
    private static final String CLASSNAME = StandardCheckBOMViewService.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String VIEW_NAME = "Manufacturing";

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
    public static StandardCheckBOMViewService newStandardCheckBOMViewService() throws WTException {
        StandardCheckBOMViewService service = new StandardCheckBOMViewService();
        service.initialize();
        return service;
    }

    @Override
    protected synchronized void performStartupProcess() throws ManagerException {
        listener = new CheckBOMViewListenerImpl(getConceptualClassname());
        LOGGER.info("BOM视图监听注册开始----");
        //注册监听，事件类型:检入
        getManagerService().addEventListener(listener, WorkInProgressServiceEvent.generateEventKey(WorkInProgressServiceEvent.POST_CHECKIN));
        LOGGER.info("BOM视图监听注册结束----");
    }

    class CheckBOMViewListenerImpl extends ServiceEventListenerAdapter {

        public CheckBOMViewListenerImpl(String s) {
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

                    if (!VIEW_NAME.equals(part.getViewName())) {
                        //不是制造视图
                        return;
                    }
                    String errMsg = checkViewName(part);
                    if (PIStringUtils.hasText(errMsg)) {
                        throw new WTException(errMsg);
                    }
                }
            }
        }
    }

    /**
     * 检查当前制造视图部件的BOM结构的视图是否是制造视图
     *
     * @param part
     * @return
     */
    private String checkViewName(WTPart part) throws WTException, WTPropertyVetoException {
        List<WTPart> bom = BOMUtil.findBOMChildren(part);
        StringBuilder errMsg = new StringBuilder();
        for (WTPart wtPart : bom) {
            if (!VIEW_NAME.equals(wtPart.getViewName())) {
                //bom结构中的部件不是制造视图
                errMsg.append("部件：").append(wtPart.getNumber()).append("的视图和被检入的部件不一致").append("\r\n");
            }
        }
        if (PIStringUtils.hasText(errMsg.toString())) {
            errMsg.append("以上信息不符，不允许提交");
        }
        return errMsg.toString();
    }

}
