package ext.task.part.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.part.forms.CreatePartAndCADDocFormProcessor;
import ext.task.part.util.PartCodeUtil;
import org.apache.log4j.Logger;
import wt.fc.ObjectReference;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTKeyedHashMap;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.util.WTException;

import java.util.List;

/**
 * @author 段鑫扬
 */
public class CusCreatePartAndCADDocFormProcessor extends CreatePartAndCADDocFormProcessor {
    private static final String CLASSNAME = CusCreatePartAndCADDocFormProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    /**
     * 修改编码。
     *
     * @param nmCommandBean
     * @param objectBeans
     * @return
     * @throws WTException
     */
    @Override
    public FormResult postProcess(NmCommandBean nmCommandBean, List<ObjectBean> objectBeans) throws WTException {
        FormResult formResult = super.postProcess(nmCommandBean, objectBeans);
        //objectBeans 中取得的创建的物料
        WTArrayList parts = new WTArrayList();
        for (ObjectBean objectBean : objectBeans) {
            if (objectBean != null && objectBean.getObject() != null) {
                Object object = objectBean.getObject();
                if (object instanceof WTPart) {
                    WTPart part = (WTPart) object;
                    parts.add(part);
                }
            }
        }
        //调用生成编码的方法
        WTKeyedHashMap resultMap = PartCodeUtil.generateNumber(parts, true);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>resultMap = " + resultMap);
        }
        StringBuilder err = new StringBuilder("");
        if (resultMap != null && resultMap.size() > 0) {
            for (Object key : resultMap.keySet()) {
                if (key instanceof ObjectReference) {
                    key = ((ObjectReference) key).getObject();
                }
                if (key instanceof WTPart) {
                    WTPart part = (WTPart) key;
                    String info = (String) resultMap.get(key);
                    err.append("物料：").append(part.getName()).append("编码异常:").append(info).append(";");
                }
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> err =" + err.toString());
        }
        if (err.length() > 0) {
            throw new WTException(err.toString());
        }
        return formResult;
    }
}
