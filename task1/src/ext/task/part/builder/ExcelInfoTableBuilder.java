package ext.task.part.builder;

import com.ptc.core.htmlcomp.components.AbstractConfigurableTableBuilder;
import com.ptc.core.htmlcomp.tableview.ConfigurableTable;
import com.ptc.mvc.components.*;
import com.ptc.mvc.util.ClientMessageSource;
import ext.task.part.reader.ExcelInfoBeanHelper;
import ext.task.part.resource.excelInfoResource;
import ext.task.part.tableview.ExcelInfoTableView;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.util.WTException;

/**
 * excel信息表格
 *
 * @author 段鑫扬
 */
@ComponentBuilder("ExcelInfoTableBuilder")
public class ExcelInfoTableBuilder extends AbstractConfigurableTableBuilder {

    private static final String CLASSNAME = ExcelInfoTableBuilder.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = excelInfoResource.class.getName();
    private ClientMessageSource message = getMessageSource(RESOURCE);

    /**
     * 获取数据
     *
     * @param componentConfig
     * @param componentParams
     * @return
     * @throws Exception
     */
    @Override
    public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams) throws Exception {
        return ExcelInfoBeanHelper.getInstance().getExcelInfoList();
    }

    /**
     * 画表格列
     *
     * @param componentParams
     * @return
     * @throws WTException
     */
    @Override
    public ComponentConfig buildComponentConfig(ComponentParams componentParams) throws WTException {
        TableConfig tableConfig = null;
        try {
            ComponentConfigFactory factory = getComponentConfigFactory();
            tableConfig = factory.newTableConfig();
            tableConfig.setLabel(message.getMessage(excelInfoResource.TASKPART_EXCEL_TOOLTIP));
            tableConfig.setActionModel("excel_info_toolbar");

            tableConfig.addComponent(factory.newColumnConfig("cosNum", message.getMessage(excelInfoResource.TASKPART_EXCELINFO_COSNUM), true));
            tableConfig.addComponent(factory.newColumnConfig("cosName", message.getMessage(excelInfoResource.TASKPART_EXCELINFO_COSNAME), true));
            tableConfig.addComponent(factory.newColumnConfig("cosSex", message.getMessage(excelInfoResource.TASKPART_EXCELINFO_COSSEX), true));
            tableConfig.addComponent(factory.newColumnConfig("cosAge", message.getMessage(excelInfoResource.TASKPART_EXCELINFO_COSAGE), true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableConfig;
    }

    /**
     * 指定配置表格视图
     *
     * @param s
     * @return
     * @throws WTException
     */
    @Override
    public ConfigurableTable buildConfigurableTable(String s) throws WTException {
        return new ExcelInfoTableView();
    }
}
