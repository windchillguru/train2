package ext.example.part.mvc.builder;

import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.core.htmlcomp.components.AbstractConfigurableTableBuilder;
import com.ptc.core.htmlcomp.tableview.ConfigurableTable;
import com.ptc.jca.mvc.components.JcaColumnConfig;
import com.ptc.jca.mvc.components.JcaTreeConfig;
import com.ptc.mvc.components.*;
import com.ptc.mvc.components.ds.DataSourceMode;
import com.ptc.mvc.util.ClientMessageSource;
import ext.example.part.mvc.handler.MyPartTreeHandler;
import ext.example.part.mvc.tableview.MyPartTreeView;
import ext.example.part.resource.treeResource;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.session.SessionServerHelper;
import wt.util.WTException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 显示所有部件的普通树
 *
 * @author 段鑫扬
 */
@ComponentBuilder("ext.example.part.mvc.builder.MyAsyncPartTreeBuilder")
public class MyAsyncPartTreeBuilder extends AbstractConfigurableTableBuilder implements TreeDataBuilderAsync {

    private static final String CLASSNAME = MyAsyncPartTreeBuilder.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = treeResource.class.getName();
    private ClientMessageSource message = getMessageSource(RESOURCE);
    private  MyPartTreeHandler treeHandler = null;
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
        return new MyPartTreeHandler();
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
        tree.setLabel(message.getMessage(treeResource.TREE_LABEL));
        //是否可选
        tree.setSelectable(true);
        //设置异步加载
        ((JcaTreeConfig) tree).setDataSourceMode(DataSourceMode.ASYNCHRONOUS);
        tree.setDisableAction("false");
        //设置树展示级别，
        tree.setExpansionLevel(DescriptorConstants.TableTreeProperties.NO_EXPAND);
        //图标
        tree.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.ICON,true));
        //名称
        tree.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.NAME,true));
        //编号
        tree.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.NUMBER,true));
        //状态
        tree.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.STATE,true));
        //表格对象右键菜单
        JcaColumnConfig columnConfig = (JcaColumnConfig) factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.NM_ACTIONS, false);
        columnConfig.setDescriptorProperty(DescriptorConstants.ActionProperties.ACTION_MODEL,"my_part_tree_row_level_actions");
        tree.addComponent(columnConfig);
        return tree;
    }

    /**
     * 指定配置表格视图
     * @param s
     * @return
     * @throws WTException
     */
    @Override
    public ConfigurableTable buildConfigurableTable(String s) throws WTException {
        return new MyPartTreeView();
    }

    @Override
    public void buildNodeData(Object o, ComponentResultProcessor componentResultProcessor) throws Exception {
         boolean flag = false;
         try {
             flag= SessionServerHelper.manager.setAccessEnforced(flag);
             if(o == TreeNode.RootNode) {
                 //获取根节点
                 treeHandler = new MyPartTreeHandler();
                 componentResultProcessor.addElements(treeHandler.getRootNodes());
             } else {
                 //获取子节点
                 List nodeList = new ArrayList();
                 nodeList.add(o);
                 Map<Object,List> map = treeHandler.getNodes(nodeList);
                 Set<Object> keySet = map.keySet();
                 for (Object key : keySet) {
                     List list = map.get(key);
                     componentResultProcessor.addElements(list);
                 }
             }
         } catch (Exception e){
             e.printStackTrace();
         } finally {
             SessionServerHelper.manager.setAccessEnforced(flag);
         }
    }
}
