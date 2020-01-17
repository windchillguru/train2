package ext.task.part.datautility;


import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.AttributeDataUtilityHelper;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import com.ptc.core.components.rendering.guicomponents.DateInputComponent;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.util.WTException;

/**
 * 部件的  测试 DataUtility 处理
 * 使用DateInputComponent
 *
 * @author 段鑫扬
 */
public class TestDateInputDataUtility extends DefaultDataUtility {

    private static final String CLASSNAME = TestDateInputDataUtility.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    @Override
    public Object getDataValue(String componentId, Object obj, ModelContext modelContext) throws WTException {
        DateInputComponent input = null;
        try {
            Object dataValue = super.getDataValue(componentId, obj, modelContext);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>> getDataValue() componentId= " + componentId + ", obj = " + obj +
                        ",modelContext=" + modelContext + ",class=" + obj.getClass().getName());

                LOGGER.debug("super.getDataValue(componentId, obj, modelContext) = " + dataValue);
            }

            input = new DateInputComponent();
            input.setId(componentId);//id
            //input.setName(componentId);//name
            //生成默认列名
            String columnName = AttributeDataUtilityHelper.getColumnName(componentId, obj, modelContext);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("columnName = " + columnName);
            }
            if (columnName != null) {
                input.setColumnName(columnName);
            } else {
                input.setColumnName(componentId);
            }
            //是否必须
            boolean isRequired = AttributeDataUtilityHelper.isInputRequired(modelContext);
            input.setRequired(isRequired);
            //是否可编辑
            input.setEditable(true);
        } catch (WTException e) {
            e.printStackTrace();
        }
        return input;
    }
}
