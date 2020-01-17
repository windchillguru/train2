package ext.example.pg.builder;

import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.jca.mvc.components.JcaColumnConfig;
import com.ptc.jca.mvc.components.JcaTreeConfig;
import com.ptc.mvc.components.*;
import com.ptc.mvc.components.ds.DataSourceMode;
import com.ptc.mvc.util.ClientMessageSource;
import ext.example.pg.resource.pgInfoRB;
import ext.example.pg.treehandler.PGTreeHandler;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.session.SessionServerHelper;
import wt.util.WTException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 段鑫扬
 * pg信息的builder
 */
@ComponentBuilder("ext.example.pg.builder.PGTreeBuilder")
public class PGTreeBuilder extends AbstractComponentBuilder implements TreeDataBuilderAsync {
    private static final String CLASSNAME = PGTreeBuilder.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = pgInfoRB.class.getName();
    private ClientMessageSource messageSource = getMessageSource(RESOURCE);
    private PGTreeHandler treeHandler = null;

    @Override
    public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams) throws Exception {
        //获取对象
        return new PGTreeHandler();
    }

    @Override
    public ComponentConfig buildComponentConfig(ComponentParams componentParams) throws WTException {
        ComponentConfigFactory factory = getComponentConfigFactory();
        TreeConfig tree = factory.newTreeConfig();
        tree.setLabel(messageSource.getMessage(pgInfoRB.PGINFO_TREE_TABLE));
        tree.setActionModel("pg_treeInfo_toolbar");
        tree.setExpansionLevel("none");

        //设置异步加载
        ((JcaTreeConfig) tree).setDataSourceMode(DataSourceMode.ASYNCHRONOUS);
        tree.setDisableAction("false");
        //是否可选
        tree.setSelectable(true);
        //是否单选
        tree.setSingleSelect(false);
        tree.setShowCount(true);
        //名称
        ColumnConfig pgName = factory.newColumnConfig("pgName", messageSource.getMessage(pgInfoRB.PG_NAME), true);
        //设置dataUtility
        pgName.setDataUtilityId("PGInfoDataUtility");
        tree.addComponent(pgName);
        //备注
        tree.addComponent(factory.newColumnConfig("comments", messageSource.getMessage(pgInfoRB.COMMENTS), true));
        //表格对象右键菜单
        JcaColumnConfig columnConfig = (JcaColumnConfig) factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.NM_ACTIONS, false);
        columnConfig.setActionModel("pg_treeInfo_right_click_actions");
        tree.addComponent(columnConfig);
        return tree;
    }

    /**
     * 树异步加载的方法
     *
     * @param o
     * @param componentResultProcessor
     * @throws Exception
     */
    @Override
    public void buildNodeData(Object o, ComponentResultProcessor componentResultProcessor) throws Exception {
        boolean flag = false;
        try {
            flag = SessionServerHelper.manager.setAccessEnforced(flag);
            if (o == TreeNode.RootNode) {
                //获取根节点
                treeHandler = new PGTreeHandler();
                componentResultProcessor.addElements(treeHandler.getRootNodes());
            } else {
                //获取子节点
                List nodeList = new ArrayList();
                nodeList.add(o);
                Map<Object, List> map = treeHandler.getNodes(nodeList);
                Set<Object> keySet = map.keySet();
                for (Object key : keySet) {
                    List list = map.get(key);
                    componentResultProcessor.addElements(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SessionServerHelper.manager.setAccessEnforced(flag);
        }
    }
}
