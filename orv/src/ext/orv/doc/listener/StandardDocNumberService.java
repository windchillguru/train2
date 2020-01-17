package ext.orv.doc.listener;

import ext.lang.PIStringUtils;
import ext.orv.doc.IXmlAttrIfc;
import ext.orv.doc.provider.ORVDocNumXmlProvider;
import ext.orv.doc.util.DocCodeUtil;
import ext.pi.core.PIAttributeHelper;
import ext.pi.core.PICoreHelper;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import wt.doc.WTDocument;
import wt.events.KeyedEvent;
import wt.fc.PersistenceManagerEvent;
import wt.log4j.LogR;
import wt.services.ManagerException;
import wt.services.ServiceEventListenerAdapter;
import wt.services.StandardManager;
import wt.util.WTException;

import java.io.Serializable;

/**
 * 生成文档编号监听服务
 *
 * @author 段鑫扬
 * @version 2019/12/31
 */
public class StandardDocNumberService extends StandardManager implements DocNumberListener, IXmlAttrIfc, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = StandardDocNumberService.class.getName();
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
    public static StandardDocNumberService newStandardDocNumberService() throws WTException {
        StandardDocNumberService service = new StandardDocNumberService();
        service.initialize();
        return service;
    }

    @Override
    protected synchronized void performStartupProcess() throws ManagerException {
        listener = new DocNumberListenerImpl(getConceptualClassname());
        LOGGER.info("文档编号监听注册开始----");
        //注册监听，事件类型:存储对象后
        getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.POST_STORE));
        LOGGER.info("文档编号监听注册结束----");
    }

    /**
     * 内部类，处理监听的功能
     */
    class DocNumberListenerImpl extends ServiceEventListenerAdapter {
        public DocNumberListenerImpl(String s) {
            super(s);
        }

        /**
         * 根据配置修改文档编码
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
            if (eventType == null) {
                return;
            }
            if (eventType.equals(PersistenceManagerEvent.POST_STORE)) {
                //存储对象事件,监听处理开始
                if (target instanceof WTDocument) {
                    WTDocument document = (WTDocument) target;
                    if (PICoreHelper.service.isCheckout(document)) {
                        LOGGER.debug("当前事件是检出：不做处理");
                        return;
                    }
                    ORVDocNumXmlProvider provider = new ORVDocNumXmlProvider();
                    //当前对象的类名 typeName
                    String simpleTypeName = PICoreHelper.service.getSimpleTypeName(document);
                    //获取大类配置
                    Element docType = provider.findTypeByDocType(simpleTypeName);
                    if (docType == null) {
                        //没有配置当前文档的对象编码规则，则不处理。
                        return;
                    }
                    String prefix = docType.getAttribute(ATTR_PREFIX);
                    String seqLength = docType.getAttribute(ATTR_SEQLENGTH);
                    //获取文档小类属性值
                    Object value = PIAttributeHelper.service.getValue(document, IBA_SMALLDOCTYPE);
                    String smallDocTypeValue = "";
                    if (value instanceof String) {
                        smallDocTypeValue = (String) value;
                    }
                    //获取小类配置
                    Element smallDocType = provider.findSmallTypeBySmallDocType(docType, smallDocTypeValue);
                    if (smallDocType != null) {
                        //使用文档小类的配置规则
                        prefix = smallDocType.getAttribute(ATTR_PREFIX);
                        seqLength = smallDocType.getAttribute(ATTR_SEQLENGTH);
                    }
                    //生成文档编码
                    String msg = DocCodeUtil.generateNumber(document, prefix, seqLength);
                    if (PIStringUtils.hasText(msg)) {
                        throw new WTException(msg);
                    }
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("document.number ==>" + document.getNumber());
                    }
                }
            }
        }
    }
}
