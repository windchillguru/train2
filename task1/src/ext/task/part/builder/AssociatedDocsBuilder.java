package ext.task.part.builder;

import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.jca.mvc.components.JcaColumnConfig;
import com.ptc.mvc.components.*;
import com.ptc.mvc.util.ClientMessageSource;
import com.ptc.windchill.enterprise.part.commands.PartDocServiceCommand;
import ext.task.part.resource.excelInfoResource;
import org.apache.log4j.Logger;
import wt.fc.QueryResult;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTCollection;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.util.WTException;

/**
 * 关联文档表格
 *
 * @author 段鑫扬
 */
@ComponentBuilder("AssociatedDocsBuilder")
public class AssociatedDocsBuilder extends AbstractComponentBuilder {
    private static final String CLASSNAME = AssociatedDocsBuilder.class.getName();
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
        Object contextObject = componentParams.getContextObject();
        WTCollection collection = new WTArrayList();
        if (contextObject != null && contextObject instanceof WTPart) {
            WTPart part = (WTPart) contextObject;
            QueryResult qr = PartDocServiceCommand.getAssociatedDescribeDocuments(part);
            if (qr != null) {
                collection.addAll(qr);
            }
            qr = PartDocServiceCommand.getAssociatedReferenceDocuments(part);
            if (qr != null) {
                collection.addAll(qr);
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("qr 关联文档" + collection);
            }
        }
        return collection;
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
            table.setLabel("关联文档");
            //是否可选
            table.setSelectable(true);
            table.setActionModel("associate_doc_table_toolbar");
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

}
