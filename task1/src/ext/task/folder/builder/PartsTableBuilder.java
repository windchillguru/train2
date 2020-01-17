package ext.task.folder.builder;

import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.core.htmlcomp.components.AbstractConfigurableTableBuilder;
import com.ptc.core.htmlcomp.tableview.ConfigurableTable;
import com.ptc.jca.mvc.components.JcaColumnConfig;
import com.ptc.mvc.components.*;
import com.ptc.mvc.util.ClientMessageSource;
import ext.task.folder.resource.partsInfoTableResource;
import ext.task.folder.tableview.PartsConfigurableTableView;
import org.apache.log4j.Logger;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.query.QuerySpec;
import wt.util.WTException;

/**
 * 显示所有部件信息表格
 *
 * @author 段鑫扬
 */
@ComponentBuilder("PartsTableBuilder")
public class PartsTableBuilder extends AbstractConfigurableTableBuilder {

    private static final String CLASSNAME = PartsTableBuilder.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = partsInfoTableResource.class.getName();
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
        QueryResult queryResult = null;
        try {
            //查询所有部件的条件
            QuerySpec qs = new QuerySpec(WTPart.class);
            LOGGER.debug("####qs:" + qs);
            //查询
            queryResult = PersistenceHelper.manager.find(qs);
        } catch (WTException e) {
            e.printStackTrace();
        }
        return queryResult;
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
        TableConfig table = null;
        try {
            ComponentConfigFactory factory = getComponentConfigFactory();
            table = factory.newTableConfig();
            //表头
            table.setLabel(message.getMessage(partsInfoTableResource.ALL_PARTS_INFO_TABLE));
            //是否可选
            table.setSelectable(true);
            table.setActionModel("all_part_table_toolbar");
            //是否单选
            table.setSingleSelect(false);
            //是否显示对象的计数
            table.setShowCount(true);
            //图标
            table.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.ICON, true));
            //名称
            table.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.NAME, true));
            //编号
            table.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.NUMBER, true));
            //状态
            table.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.STATE, true));
            //表格对象右键菜单
            JcaColumnConfig columnConfig = (JcaColumnConfig) factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.NM_ACTIONS, false);
            columnConfig.setActionModel("part_table_right_click_actions");
            table.addComponent(columnConfig);

            //名称
            table.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.VERSION, true));
            //编号
            table.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.LAST_MODIFIED, true));
            //容器上下文
            table.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.CONTAINER_NAME, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
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
        return new PartsConfigurableTableView();
    }
}
