package ext.orv.attrlinkage.datautility;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.AttributeDataUtilityHelper;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import com.ptc.core.components.rendering.guicomponents.ComboBox;
import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.ui.resources.ComponentMode;
import com.ptc.netmarkets.model.NmOid;
import ext.lang.PIStringUtils;
import ext.orv.attrlinkage.IAttrLinkAgeIfc;
import ext.orv.attrlinkage.reader.ProductSESeriesGPBeanHelper;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.util.WTException;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品系列和产品型号的dataUtility的数据处理类
 *
 * @author 段鑫扬
 */
public class ProductSESeriesGPDataUtility extends DefaultDataUtility {

    private static final String CLASSNAME = ProductSESeriesGPDataUtility.class.getName();
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
            //判断是否时创建/编辑/显示详细页面
            if (modelContext.getDescriptorMode().equals(ComponentMode.CREATE)
                    || modelContext.getDescriptorMode().equals(ComponentMode.EDIT)
                    || modelContext.getDescriptorMode().equals(ComponentMode.VIEW)) {
                if (IAttrLinkAgeIfc.IBA_PRODUCTSE.equals(componentId)) {
                    //产品系列
                    return createProductSE(componentId, obj, modelContext);
                }
                if (IAttrLinkAgeIfc.IBA_SERIESGP.equals(componentId)) {
                    //产品型号
                    return createSeriesGP(componentId, obj, modelContext);
                }
            }

        } catch (WTException e) {
            e.printStackTrace();
        }
        return dataValue;
    }

    /**
     * 创建产品型号的返回数据
     *
     * @param componentId
     * @param obj
     * @param modelContext
     * @return
     */
    private Object createSeriesGP(String componentId, Object obj, ModelContext modelContext) {
        ComboBox box = null;
        try {
            //显示界面
            if (modelContext.getDescriptorMode().equals(ComponentMode.VIEW)) {
                String display = "";
                //产品型号的显示值
                Object displayValue = createViewValue(componentId, obj, modelContext, IAttrLinkAgeIfc.IBA_SERIESGP);
                if (displayValue != null && displayValue instanceof String) {
                    display = (String) displayValue;
                }
                return display;
            }
            box = new ComboBox();
            //设置id
            box.setId(componentId);
            //设置列名
            box.setColumnName(AttributeDataUtilityHelper.getColumnName(componentId, obj, modelContext));
            //设置必填
            box.setRequired(AttributeDataUtilityHelper.isInputRequired(modelContext));

            ArrayList<String> internals = new ArrayList<>();//内部值
            ArrayList<String> displays = new ArrayList<>();//显示值
            internals.add("");
            displays.add("");

            //产品系列没有值，则产品型号没有可选值
            if (modelContext.getDescriptorMode().equals(ComponentMode.EDIT)) {
                //编辑页面下，产品系列可能已经有值
                NmOid actionOid = modelContext.getNmCommandBean().getActionOid();
                WTPart wtPart = null;
                if (actionOid != null && actionOid.getRefObject() instanceof WTPart) {
                    wtPart = (WTPart) actionOid.getRefObject();
                }
                PersistableAdapter adapter = new PersistableAdapter(wtPart, null, modelContext.getLocale(), null);
                adapter.load(IAttrLinkAgeIfc.IBA_PRODUCTSE);
                //获取产品系列对应的值
                Object value = adapter.get(IAttrLinkAgeIfc.IBA_PRODUCTSE);
                if (value != null && value instanceof String) {
                    String productSE = (String) value;
                    ProductSESeriesGPBeanHelper helper = ProductSESeriesGPBeanHelper.getInstance();
                    List<String> seriesGPList = helper.getSeriesGPListByproductSE(productSE);
                    List<String> displayList = helper.getDescsByInternals(seriesGPList);
                    internals.addAll(seriesGPList);
                    displays.addAll(displayList);
                }
            }

            box.setInternalValues(internals);
            box.setValues(displays);
            if (modelContext.getRawValue() != null) {
                box.setSelected(modelContext.getRawValue().toString());
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>internals = " + internals);
                LOGGER.debug(">>>>displays = " + displays);
            }
        } catch (
                WTException e) {
            e.printStackTrace();
        }
        return box;
    }

    /**
     * 根据IBA属性name获取它对应的显示值，如果没有则返回内部值;
     *
     * @param componentId
     * @param obj
     * @param modelContext
     * @param ibaName
     * @return
     * @throws WTException
     */
    private Object createViewValue(String componentId, Object obj, ModelContext modelContext, String ibaName) throws WTException {
        NmOid actionOid = modelContext.getNmCommandBean().getActionOid();
        WTPart wtPart = null;
        if (actionOid != null && actionOid.getRefObject() instanceof WTPart) {
            wtPart = (WTPart) actionOid.getRefObject();
        }
        //获取对应的属性的值
        PersistableAdapter adapter = new PersistableAdapter(wtPart, null, modelContext.getLocale(), null);
        adapter.load(ibaName);
        Object value = adapter.get(ibaName);
        if (value instanceof String) {
            String productSE = (String) value;
            ProductSESeriesGPBeanHelper helper = ProductSESeriesGPBeanHelper.getInstance();
            String display = helper.getDescByInternal(productSE);
            if (PIStringUtils.hasText(display)) {
                return display;
            }
            return productSE;
        }
        return value;
    }


    /**
     * 创建产品系列的返回数据
     *
     * @param componentId
     * @param obj
     * @param modelContext
     * @return
     * @throws WTException
     */
    private Object createProductSE(String componentId, Object obj, ModelContext modelContext) throws WTException {
        ComboBox box = null;
        try {
            //显示界面
            if (modelContext.getDescriptorMode().equals(ComponentMode.VIEW)) {
                String display = "";
                //产品系列的显示值
                Object displayValue = createViewValue(componentId, obj, modelContext, IAttrLinkAgeIfc.IBA_PRODUCTSE);
                if (displayValue != null && displayValue instanceof String) {
                    display = (String) displayValue;
                }
                return display;
            }
            box = new ComboBox();
            //设置id
            box.setId(componentId);
            //设置列名
            box.setColumnName(AttributeDataUtilityHelper.getColumnName(componentId, obj, modelContext));
            //设置必填
            box.setRequired(AttributeDataUtilityHelper.isInputRequired(modelContext));

            ArrayList<String> internals = new ArrayList<>();//内部值
            ArrayList<String> displays = new ArrayList<>();//显示值
            internals.add("");
            displays.add("");

            ProductSESeriesGPBeanHelper helper = ProductSESeriesGPBeanHelper.getInstance();
            //获取产品系列列表
            List<String> productSEList = helper.getProductSEList();//内部值
            List<String> displayList = helper.getDescsByInternals(productSEList);//显示值
            internals.addAll(productSEList);
            displays.addAll(displayList);
            box.setInternalValues(internals);
            box.setValues(displays);

            if (modelContext.getRawValue() != null) {
                box.setSelected(modelContext.getRawValue().toString());
            }
            //设置事件和js方法
            box.addJsAction("onChange", "refreshSeriesGPByProductSE('" + IAttrLinkAgeIfc.IBA_PRODUCTSE + "','"
                    + IAttrLinkAgeIfc.IBA_SERIESGP + "')");

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>internals = " + internals);
                LOGGER.debug(">>>>displays = " + displays);
            }
        } catch (WTException e) {
            e.printStackTrace();
        }
        return box;
    }
}
