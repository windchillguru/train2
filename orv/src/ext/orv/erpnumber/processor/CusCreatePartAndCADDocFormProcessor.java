package ext.orv.erpnumber.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.part.forms.CreatePartAndCADDocFormProcessor;
import ext.pi.core.PIAttributeHelper;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.util.WTException;

import java.util.List;

/**
 * 重写新建部件，设置erp编号
 * @author 段鑫扬
 */
public class CusCreatePartAndCADDocFormProcessor extends CreatePartAndCADDocFormProcessor {
    private static final String CLASSNAME = CusCreatePartAndCADDocFormProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    //erp编号
    private static String IBA_ATTR="erpnumber";

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
        for (ObjectBean objectBean : objectBeans) {
            if (objectBean != null && objectBean.getObject() != null) {
                Object object = objectBean.getObject();
                if (object instanceof WTPart) {
                    WTPart part = (WTPart) object;
                    String number = part.getNumber();
                    //修改属性erp编号
                    Object value = PIAttributeHelper.service.getValue(part, IBA_ATTR);
                    String liaoHao ="";
                    if(value instanceof String){
                        liaoHao = (String) value;
                    }
                    if(!liaoHao.equals(number)){
                        //修改属性erp编号
                        PIAttributeHelper.service.updateSoftAttribute(part, IBA_ATTR, number);
                    }
                }
            }
        }
        return formResult;
    }
}
