package ext.task.part.datautility;


import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.util.WTException;

/**
 * 部件的  测试 DataUtility 处理
 *
 * @author 段鑫扬
 */
public class TestPartDataUtility extends DefaultDataUtility {

    private static final String CLASSNAME = TestPartDataUtility.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    @Override
    public Object getDataValue(String componentId, Object obj, ModelContext modelContext) throws WTException {
        try {
            Object dataValue = super.getDataValue(componentId, obj, modelContext);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>> getDataValue() componentId= " + componentId + ", obj = " + obj +
                        ",modelContext=" + modelContext + ",class=" + obj.getClass().getName());

                LOGGER.debug("super.getDataValue(componentId, obj, modelContext) = " + dataValue);
            }
        } catch (WTException e) {
            e.printStackTrace();
        }
        return "abc";
    }
}
