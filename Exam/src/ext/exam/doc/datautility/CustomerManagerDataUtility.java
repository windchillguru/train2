package ext.exam.doc.datautility;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.AttributeDataUtilityHelper;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import com.ptc.core.components.rendering.guicomponents.ComboBox;
import com.ptc.core.components.rendering.guicomponents.StringInputComponent;
import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.ui.resources.ComponentMode;
import com.ptc.netmarkets.model.NmOid;
import ext.exam.doc.IAttrLinkAgeIfc;
import ext.exam.doc.reader.CustomerManagerBeanHelper;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.util.WTException;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户和经理的dataUtility的数据处理类
 *
 * @author 段鑫扬
 */
public class CustomerManagerDataUtility extends DefaultDataUtility {

    private static final String CLASSNAME = CustomerManagerDataUtility.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    /**
     * 返回数据
     *
     * @param componentId
     * @param obj
     * @param modelContext
     * @return
     * @throws WTException
     */
    @Override
    public Object getDataValue(String componentId, Object obj, ModelContext modelContext) throws WTException {
        Object dataValue = null;
        try {
            dataValue = super.getDataValue(componentId, obj, modelContext);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>> getDataValue() componentId= " + componentId + ", obj = " + obj +
                        ",modelContext=" + modelContext + ",class=" + obj.getClass().getName());

                LOGGER.debug("super.getDataValue(componentId, obj, modelContext) = " + dataValue);
            }

            //判断是否时创建/编辑
            if (modelContext.getDescriptorMode().equals(ComponentMode.CREATE)
                    || modelContext.getDescriptorMode().equals(ComponentMode.EDIT)) {
                if (IAttrLinkAgeIfc.IBA_CUSTOMERNAME.equals(componentId)) {
                    //客户
                    return createCustomer(componentId, obj, modelContext);
                }
                if (IAttrLinkAgeIfc.IBA_BELONGMANAGER.equals(componentId)) {
                    getManager(componentId, obj, modelContext);
                }
            }

        } catch (WTException e) {
            e.printStackTrace();
        }
        return dataValue;
    }

    /**
     * 经理
     *
     * @param componentId
     * @param obj
     * @param modelContext
     * @return
     */
    private Object getManager(String componentId, Object obj, ModelContext modelContext) {
        StringInputComponent stringInputComponent = null;
        try {
            stringInputComponent = new StringInputComponent();
            //设置id
            stringInputComponent.setId(componentId);
            //设置列名
            stringInputComponent.setColumnName(AttributeDataUtilityHelper.getColumnName(componentId, obj, modelContext));

            //客户没有值，则经理没有值
            if (modelContext.getDescriptorMode().equals(ComponentMode.EDIT)) {
                //编辑页面下，客户可能已经有值
                NmOid actionOid = modelContext.getNmCommandBean().getActionOid();
                if (actionOid != null && actionOid.getRefObject() instanceof WTPart) {
                    WTPart wtPart = (WTPart) actionOid.getRefObject();
                    PersistableAdapter adapter = new PersistableAdapter(wtPart, null, modelContext.getLocale(), null);
                    adapter.load(IAttrLinkAgeIfc.IBA_CUSTOMERNAME);
                    //获取客户名称
                    Object value = adapter.get(IAttrLinkAgeIfc.IBA_CUSTOMERNAME);
                    if (value != null && value instanceof String) {
                        String customerName = (String) value;
                        String belongManager = CustomerManagerBeanHelper.getInstance().getBelongManager(customerName);
                        stringInputComponent.setValue(belongManager);
                    }
                }
            }
        } catch (WTException e) {
            e.printStackTrace();
        }
        return stringInputComponent;
    }

    /**
     * 创建客户的数据
     *
     * @param componentId
     * @param obj
     * @param modelContext
     * @return
     * @throws WTException
     */
    private Object createCustomer(String componentId, Object obj, ModelContext modelContext) throws WTException {
        ComboBox box = null;
        try {
            box = new ComboBox();
            //设置id
            box.setId(componentId);
            //设置列名
            box.setColumnName(AttributeDataUtilityHelper.getColumnName(componentId, obj, modelContext));
            //设置必填
            box.setRequired(AttributeDataUtilityHelper.isInputRequired(modelContext));

            ArrayList<String> internals = new ArrayList<>();//内部值
            ArrayList<String> dispalys = new ArrayList<>();//显示值
            internals.add("");
            dispalys.add("");
            //获取客户列表
            List<String> customerList = CustomerManagerBeanHelper.getInstance().getCustomerList();
            internals.addAll(customerList);
            dispalys.addAll(customerList);

            box.setInternalValues(internals);
            box.setValues(dispalys);
            if (modelContext.getRawValue() != null) {
                box.setSelected(modelContext.getRawValue().toString());
            }
            //设置事件和js方法
            box.addJsAction("onChange", "refreshManagerByCustomer('" + IAttrLinkAgeIfc.IBA_CUSTOMERNAME + "','"
                    + IAttrLinkAgeIfc.IBA_BELONGMANAGER + "')");
        } catch (WTException e) {
            e.printStackTrace();
        }
        return box;
    }
}
