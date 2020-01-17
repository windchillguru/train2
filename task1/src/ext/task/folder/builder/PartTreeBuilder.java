package ext.task.folder.builder;


import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.core.htmlcomp.components.AbstractConfigurableTableBuilder;
import com.ptc.core.htmlcomp.tableview.ConfigurableTable;
import com.ptc.jca.mvc.components.JcaColumnConfig;
import com.ptc.mvc.components.*;
import com.ptc.mvc.util.ClientMessageSource;
import ext.task.folder.resource.partTreeResource;
import ext.task.folder.tableview.PartTreeView;
import ext.task.folder.treehandler.PartTreeHandler;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.util.WTException;

/**
 * 显示所有部件的普通树
 *
 * @author 段鑫扬
 */
@ComponentBuilder("PartTreeBuilder")
public class PartTreeBuilder extends AbstractConfigurableTableBuilder {

    private static final String CLASSNAME = PartTreeBuilder.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = partTreeResource.class.getName();
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
        return new PartTreeHandler();
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
        ComponentConfigFactory factory = getComponentConfigFactory();
        TreeConfig tree = factory.newTreeConfig();
        tree.setLabel(message.getMessage(partTreeResource.PART_TREE_LABEL));
        //是否可选
        tree.setSelectable(true);
        tree.setActionModel("task1_part_tree_toolbar");
        //是否单选
        tree.setSingleSelect(true);
        //设置树展示级别，  所有
        tree.setExpansionLevel(DescriptorConstants.TableTreeProperties.FULL_EXPAND);
        //图标
        tree.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.ICON, true));
        //名称
        tree.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.NAME, true));
        //编号
        tree.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.NUMBER, true));
        //状态
        tree.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.STATE, true));
        //对象右键菜单
        JcaColumnConfig columnConfig = (JcaColumnConfig) factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.NM_ACTIONS, false);
        columnConfig.setDescriptorProperty(DescriptorConstants.ActionProperties.ACTION_MODEL, "task1_part_tree_row_level_actions");
        tree.addComponent(columnConfig);
        return tree;
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
        return new PartTreeView();
    }

}
