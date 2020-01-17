package ext.task.folder.builder;

import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.jca.mvc.components.JcaColumnConfig;
import com.ptc.mvc.components.*;
import com.ptc.mvc.util.ClientMessageSource;
import ext.task.folder.resource.docsInfoTableResource;
import org.apache.log4j.Logger;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.log4j.LogR;
import wt.query.QuerySpec;
import wt.util.WTException;

/**
 * 显示所有文档信息表格
 *
 * @author 段鑫扬
 */
@ComponentBuilder("DocsTableBuilder")
public class DocsTableBuilder extends AbstractComponentBuilder {

    private static final String CLASSNAME = DocsTableBuilder.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = docsInfoTableResource.class.getName();
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
            //查询所有文档的条件
            QuerySpec qs = new QuerySpec(WTDocument.class);
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
            table.setLabel(getResource(docsInfoTableResource.DOC_TABLE_LABEL));
            //是否可选
            table.setSelectable(true);
            table.setActionModel("all_doc_table_toolbar");
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
            columnConfig.setActionModel("doc_table_right_click_actions");
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

    private String getResource(String resource) {
        return message.getMessage(resource);
    }
}
