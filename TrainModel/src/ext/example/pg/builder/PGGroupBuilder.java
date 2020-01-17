package ext.example.pg.builder;

import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.jca.mvc.components.JcaColumnConfig;
import com.ptc.mvc.components.*;
import com.ptc.mvc.util.ClientMessageSource;
import ext.example.pg.model.PGGroup;
import ext.example.pg.resource.pgInfoRB;
import ext.example.pg.service.PGInfoHelper;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.util.WTException;

/**
 * @author 段鑫扬
 * pg信息的builder
 */
@ComponentBuilder("ext.example.pg.builder.PGGroupBuilder")
public class PGGroupBuilder extends AbstractComponentBuilder {
    private static final String CLASSNAME = PGGroupBuilder.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = pgInfoRB.class.getName();
    private ClientMessageSource messageSource = getMessageSource(RESOURCE);

    @Override
    public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams) throws Exception {
        //获取对象
        return PGInfoHelper.service.queryPGGroups(false);
    }

    @Override
    public ComponentConfig buildComponentConfig(ComponentParams componentParams) throws WTException {
        ComponentConfigFactory factory = getComponentConfigFactory();
        TableConfig table = factory.newTableConfig();
        table.setLabel(messageSource.getMessage(pgInfoRB.PGINFO_GROUP_TABLE));
        table.setActionModel("pg_groupInfo_toolbar");
        ///是否可选
        table.setSelectable(true);
        //是否单选
        table.setSingleSelect(false);
        table.setShowCount(true);
        //组名
        table.addComponent(factory.newColumnConfig(PGGroup.PG_GROUP_NAME, messageSource.getMessage(pgInfoRB.PGGROUP_NANE), true));
        //备注
        table.addComponent(factory.newColumnConfig(PGGroup.COMMENTS, messageSource.getMessage(pgInfoRB.COMMENTS), true));
        //是否启用
        table.addComponent(factory.newColumnConfig(PGGroup.ENABLED, messageSource.getMessage(pgInfoRB.ENABLED), true));
        //根节点
        table.addComponent(factory.newColumnConfig(PGGroup.ROOT, messageSource.getMessage(pgInfoRB.ROOT), true));

        //表格对象右键菜单
        JcaColumnConfig columnConfig = (JcaColumnConfig) factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.NM_ACTIONS, false);
        columnConfig.setActionModel("pg_groupInfo_right_click_actions");
        table.addComponent(columnConfig);

        return table;
    }
}
