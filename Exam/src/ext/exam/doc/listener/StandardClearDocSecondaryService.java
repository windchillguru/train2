package ext.exam.doc.listener;

import org.apache.log4j.Logger;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.doc.WTDocument;
import wt.events.KeyedEvent;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.log4j.LogR;
import wt.pom.PersistentObjectManager;
import wt.pom.Transaction;
import wt.services.ManagerException;
import wt.services.ServiceEventListenerAdapter;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import wt.vc.wip.WorkInProgressServiceEvent;

import java.io.Serializable;

/**
 * 清除文档的word 附件 监听
 *
 * @author 段鑫扬
 */
public class StandardClearDocSecondaryService extends StandardManager implements ClearDocSecondaryListener, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = StandardClearDocSecondaryService.class.getName();
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
    public static StandardClearDocSecondaryService newStandardClearDocSecondaryService() throws WTException {
        StandardClearDocSecondaryService service = new StandardClearDocSecondaryService();
        service.initialize();
        return service;
    }

    @Override
    protected synchronized void performStartupProcess() throws ManagerException {
        listener = new ClearDocSecondaryListenerImpl(getConceptualClassname());
        LOGGER.info("清除文档word附件监听注册开始----");
        //注册监听，事件类型
        getManagerService().addEventListener(listener, WorkInProgressServiceEvent.generateEventKey(WorkInProgressServiceEvent.POST_CHECKIN));
        LOGGER.info("清除文档word附件监听注册结束----");
    }

    /**
     * 内部类，处理监听的功能
     */
    class ClearDocSecondaryListenerImpl extends ServiceEventListenerAdapter {
        public ClearDocSecondaryListenerImpl(String s) {
            super(s);
        }

        /**
         * 检入事件，清除文档的word附件
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
                if (target instanceof WTDocument) {
                    WTDocument doc = (WTDocument) target;
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("doc.display ==>" + doc.getDisplayIdentifier());
                    }
                    if (!doc.isLatestIteration()) {
                        //不是最新小版本，
                        // 第二参数 true将尝试获取最新的迭代，即使标记为delete。
                        doc = (WTDocument) VersionControlHelper.service.getLatestIteration(doc, true);
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("after.latestDoc.display ==>" + doc.getDisplayIdentifier());
                        }
                    }
                    //将文件内容列表加载到内存中
                    doc = (WTDocument) ContentHelper.service.getContents(doc);
                    //获取附件列表
                    QueryResult qr = ContentHelper.service.getContentsByRole(doc, ContentRoleType.SECONDARY);

                    Transaction tx = null;
                    boolean canCommit = false;
                    try {
                        if (!PersistentObjectManager.getPom().isTransactionActive()) {
                            //开启事务
                            tx = new Transaction();
                            tx.start();
                            canCommit = true;
                        }
                        if (qr != null) {
                            while (qr.hasMoreElements()) {
                                Object nextElement = qr.nextElement();
                                if (nextElement != null && nextElement instanceof ApplicationData) {
                                    ApplicationData applicationData = (ApplicationData) nextElement;
                                    String fileName = applicationData.getFileName();
                                    if (fileName.endsWith(".docx") || fileName.endsWith(".doc")) {
                                        //需要删除
                                        //删除
                                        ContentServerHelper.service.deleteContent(doc, applicationData);
                                    }
                                }
                            }
                        }
                        if (canCommit) {
                            tx.commit();
                            tx = null;
                        }
                        doc = (WTDocument) PersistenceHelper.manager.refresh(doc, true, true);
                    } finally {
                        if (canCommit) {
                            if (tx != null) {
                                tx.rollback();
                                tx = null;
                            }
                        }
                    }
                }
            }
        }
    }
}

