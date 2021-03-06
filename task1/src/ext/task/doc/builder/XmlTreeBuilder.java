package ext.task.doc.builder;

import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.core.htmlcomp.components.AbstractConfigurableTableBuilder;
import com.ptc.core.htmlcomp.tableview.ConfigurableTable;
import com.ptc.mvc.components.*;
import com.ptc.mvc.util.ClientMessageSource;
import ext.task.doc.resource.xmlInfoResource;
import ext.task.doc.tableview.XmlTreeView;
import ext.task.doc.treehandler.XmlTreeHandler;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.util.WTException;

/**
 * 显示xml 信息的普通树
 *
 * @author 段鑫扬
 */
@ComponentBuilder("XmlTreeBuilder")
public class XmlTreeBuilder extends AbstractConfigurableTableBuilder {

    private static final String CLASSNAME = XmlTreeBuilder.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = xmlInfoResource.class.getName();
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

        return new XmlTreeHandler();
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
        tree.setLabel("xml info");
        tree.setActionModel("xml_tree_toolbar");
        //设置树展示级别，  所有
        tree.setExpansionLevel(DescriptorConstants.TableTreeProperties.FULL_EXPAND);
        //表格对象右键菜单
        ColumnConfig columnConfig = factory.newColumnConfig("name", "名称", false);
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
        return new XmlTreeView();
    }

}
