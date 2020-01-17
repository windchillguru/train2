package ext.task.attrlinkage.datautility;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.AttributeDataUtilityHelper;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import com.ptc.core.components.rendering.guicomponents.ComboBox;
import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.ui.resources.ComponentMode;
import com.ptc.netmarkets.model.NmOid;
import ext.task.attrlinkage.IAttrLinkAgeIfc;
import ext.task.attrlinkage.reader.SubwayStationBeanHelper;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.util.WTException;

import java.util.ArrayList;
import java.util.List;

/**
 * 地铁和站点的dataUtility的数据处理类
 *
 * @author 段鑫扬
 */
public class SubwayStationDataUtility extends DefaultDataUtility {

    private static final String CLASSNAME = SubwayStationDataUtility.class.getName();
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
                if (IAttrLinkAgeIfc.IBA_SUBWAY.equals(componentId)) {
                    //地铁
                    return createSubway(componentId, obj, modelContext);
                }
                if (IAttrLinkAgeIfc.IBA_STATION.equals(componentId)) {
                    return createStation(componentId, obj, modelContext);
                }
                if (IAttrLinkAgeIfc.IBA_EXIT.equals(componentId)) {
                    return createExit(componentId, obj, modelContext);
                }
            }

        } catch (WTException e) {
            e.printStackTrace();
        }
        return dataValue;
    }

    /**
     * 创建出口的数据
     *
     * @param componentId
     * @param obj
     * @param modelContext
     * @return
     */
    private Object createExit(String componentId, Object obj, ModelContext modelContext) {
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
            //站点没有值，则出口没有可选值
            if (modelContext.getDescriptorMode().equals(ComponentMode.EDIT)) {
                //编辑页面下，站点可能已经有值
                NmOid actionOid = modelContext.getNmCommandBean().getActionOid();
                if (actionOid != null && actionOid.getRefObject() instanceof WTPart) {
                    WTPart wtPart = (WTPart) actionOid.getRefObject();
                    PersistableAdapter adapter = new PersistableAdapter(wtPart, null, modelContext.getLocale(), null);
                    adapter.load(IAttrLinkAgeIfc.IBA_STATION);
                    //获取站点对应的值
                    Object value = adapter.get(IAttrLinkAgeIfc.IBA_STATION);
                    if (value != null && value instanceof String) {
                        String station = (String) value;
                        List<String> exitList = SubwayStationBeanHelper.getInstance().getExitListByStation(station);
                        internals.addAll(exitList);
                        dispalys.addAll(exitList);
                    }
                }
            }
            box.setInternalValues(internals);
            box.setValues(dispalys);
            if (modelContext.getRawValue() != null) {
                box.setSelected(modelContext.getRawValue().toString());
            }
        } catch (WTException e) {
            e.printStackTrace();
        }
        return box;
    }

    /**
     * 创建站点的数据
     *
     * @param componentId
     * @param obj
     * @param modelContext
     * @return
     */
    private Object createStation(String componentId, Object obj, ModelContext modelContext) {
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
            //地铁没有值，则站台没有可选值
            if (modelContext.getDescriptorMode().equals(ComponentMode.EDIT)) {
                //编辑页面下，地铁可能已经有值
                NmOid actionOid = modelContext.getNmCommandBean().getActionOid();
                if (actionOid != null && actionOid.getRefObject() instanceof WTPart) {
                    WTPart wtPart = (WTPart) actionOid.getRefObject();
                    PersistableAdapter adapter = new PersistableAdapter(wtPart, null, modelContext.getLocale(), null);
                    adapter.load(IAttrLinkAgeIfc.IBA_SUBWAY);
                    //获取地铁对应的值
                    Object value = adapter.get(IAttrLinkAgeIfc.IBA_SUBWAY);
                    if (value != null && value instanceof String) {
                        String subway = (String) value;
                        List<String> stationList = SubwayStationBeanHelper.getInstance().getStationListBySubway(subway);
                        internals.addAll(stationList);
                        dispalys.addAll(stationList);
                    }
                }
            }
            box.setInternalValues(internals);
            box.setValues(dispalys);
            if (modelContext.getRawValue() != null) {
                box.setSelected(modelContext.getRawValue().toString());
            }
            //设置事件和js方法
            box.addJsAction("onChange", "refreshExitByStation('" + IAttrLinkAgeIfc.IBA_STATION + "','"
                    + IAttrLinkAgeIfc.IBA_EXIT + "')");
        } catch (WTException e) {
            e.printStackTrace();
        }
        return box;
    }

    /**
     * 创建地铁的数据
     *
     * @param componentId
     * @param obj
     * @param modelContext
     * @return
     * @throws WTException
     */
    private Object createSubway(String componentId, Object obj, ModelContext modelContext) throws WTException {
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
            //获取地铁列表
            List<String> subwayList = SubwayStationBeanHelper.getInstance().getSubwayList();
            internals.addAll(subwayList);
            dispalys.addAll(subwayList);

            box.setInternalValues(internals);
            box.setValues(dispalys);
            if (modelContext.getRawValue() != null) {
                box.setSelected(modelContext.getRawValue().toString());
            }
            //设置事件和js方法
            box.addJsAction("onChange", "refreshStationBySubway('" + IAttrLinkAgeIfc.IBA_SUBWAY + "','"
                    + IAttrLinkAgeIfc.IBA_STATION + "')");
        } catch (WTException e) {
            e.printStackTrace();
        }
        return box;
    }
}
