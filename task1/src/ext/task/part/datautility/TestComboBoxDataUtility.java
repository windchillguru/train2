package ext.task.part.datautility;


import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.AttributeDataUtilityHelper;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import com.ptc.core.components.rendering.guicomponents.ComboBox;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.util.WTException;

import java.util.ArrayList;

/**
 * 部件的  测试 DataUtility 处理
 *
 * @author 段鑫扬
 */
public class TestComboBoxDataUtility extends DefaultDataUtility {

    private static final String CLASSNAME = TestComboBoxDataUtility.class.getName();
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
        ComboBox comboBox = null;
        try {
            Object dataValue = super.getDataValue(componentId, obj, modelContext);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>> getDataValue() componentId= " + componentId + ", obj = " + obj +
                        ",modelContext=" + modelContext + ",class=" + obj.getClass().getName());

                LOGGER.debug("super.getDataValue(componentId, obj, modelContext) = " + dataValue);
            }

            comboBox = new ComboBox();
            comboBox.setId(componentId);//id
            //不能指定name，否则创建时，设置属性值会失败
            //comboBox.setName(componentId);//name
            ArrayList<String> internals = new ArrayList<>();
            internals.add("");
            internals.add("value1");
            internals.add("value2");

            ArrayList<String> dispalys = new ArrayList<>();
            dispalys.add("");
            dispalys.add("值1");
            dispalys.add("值2");

            comboBox.setInternalValues(internals);//设置内部值
            comboBox.setValues(dispalys);//设置显示值
            //生成默认列名
            String columnName = AttributeDataUtilityHelper.getColumnName(componentId, obj, modelContext);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("columnName = " + columnName);
            }
            if (columnName != null) {
                comboBox.setColumnName(columnName);
            } else {
                comboBox.setColumnName(componentId);
            }
            //是否必须
            boolean isRequired = AttributeDataUtilityHelper.isInputRequired(modelContext);
            comboBox.setRequired(isRequired);
            //是否可编辑
            //comboBox.setEditable(true);
            comboBox.setSelected("value1");
            if (modelContext.getRawValue() != null) {
                comboBox.setSelected(modelContext.getRawValue().toString());
            }
        } catch (WTException e) {
            e.printStackTrace();
        }
        return comboBox;
    }
}
