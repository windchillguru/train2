package ext.example.pg.builder;

import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.jca.mvc.components.JcaColumnConfig;
import com.ptc.mvc.components.*;
import com.ptc.mvc.util.ClientMessageSource;
import ext.example.pg.model.PGInformation;
import ext.example.pg.resource.pgInfoRB;
import ext.example.pg.service.PGInfoHelper;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.util.WTException;

/**
 * @author 段鑫扬
 * pg信息的builder
 */
@ComponentBuilder("ext.example.pg.builder.PGInformationBuilder2")
public class PGInformationBuilder extends AbstractComponentBuilder {
    private static final String CLASSNAME = PGInformationBuilder.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = pgInfoRB.class.getName();
    private ClientMessageSource messageSource = getMessageSource(RESOURCE);

    @Override
    public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams) throws Exception {
        //获取对象
        return PGInfoHelper.service.queryPGInformations();
    }

    @Override
    public ComponentConfig buildComponentConfig(ComponentParams componentParams) throws WTException {
        ComponentConfigFactory factory = getComponentConfigFactory();
        TableConfig table = factory.newTableConfig();
        table.setLabel(messageSource.getMessage(pgInfoRB.PGINFO_USER_TABLE));
        table.setActionModel("pg_userInfo_toolbar");
        //是否可选
        table.setSelectable(true);
        //是否单选
        table.setSingleSelect(false);
        table.setShowCount(true);
        //工号
        table.addComponent(factory.newColumnConfig(PGInformation.EMPLOYEE_NO, messageSource.getMessage(pgInfoRB.EMPLOYEE_NO), true));
        //姓名
        table.addComponent(factory.newColumnConfig(PGInformation.EMPLOYEE_NAME, messageSource.getMessage(pgInfoRB.EMPLOYEE_NAME), true));
        //用户名
        table.addComponent(factory.newColumnConfig(PGInformation.EMPLOYEE_USER_NAME, messageSource.getMessage(pgInfoRB.EMPLOYEE_USERNAME), true));
        //邮箱
        table.addComponent(factory.newColumnConfig(PGInformation.EMPLOYEE_EMAIL, messageSource.getMessage(pgInfoRB.EMPLOYEE_EMAIL), true));
        //电话
        table.addComponent(factory.newColumnConfig(PGInformation.EMPLOYEE_PHONE, messageSource.getMessage(pgInfoRB.EMPLOYEE_PHONE), true));
        //备注
        table.addComponent(factory.newColumnConfig(PGInformation.COMMENTS, messageSource.getMessage(pgInfoRB.COMMENTS), true));
        //是否有经验
        table.addComponent(factory.newColumnConfig(PGInformation.EXPERIENCED, messageSource.getMessage(pgInfoRB.EXPERIENCED), true));
        //简历
        table.addComponent(factory.newColumnConfig(PGInformation.RESUME_INFO, messageSource.getMessage(pgInfoRB.RESUMEINFO), true));
        //信息编号
        table.addComponent(factory.newColumnConfig(PGInformation.INFORMATION_NO, messageSource.getMessage(pgInfoRB.INFORNAITIONNO), true));
        //信息来源
        table.addComponent(factory.newColumnConfig(PGInformation.INFORMATION_SOURCE, messageSource.getMessage(pgInfoRB.INFORMATION_SOURCE), true));
        //是否组长
        table.addComponent(factory.newColumnConfig(PGInformation.LEADER, messageSource.getMessage(pgInfoRB.LEADER), true));

        //表格对象右键菜单
        JcaColumnConfig columnConfig = (JcaColumnConfig) factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.NM_ACTIONS, false);
        columnConfig.setActionModel("pg_userInfo_right_click_actions");
        table.addComponent(columnConfig);

        return table;
    }
}
