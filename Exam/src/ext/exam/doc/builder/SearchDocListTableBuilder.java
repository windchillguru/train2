package ext.exam.doc.builder;

import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.core.htmlcomp.components.AbstractConfigurableTableBuilder;
import com.ptc.core.htmlcomp.tableview.ConfigurableTable;
import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.mvc.components.*;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.beans.NmHelperBean;
import ext.exam.doc.query.DocQuerySpec;
import ext.exam.doc.tableview.SearchDocListConfigurableTableView;
import ext.lang.PIStringUtils;
import org.apache.log4j.Logger;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.log4j.LogR;
import wt.query.QuerySpec;
import wt.session.SessionServerHelper;
import wt.util.WTException;
import wt.vc.config.LatestConfigSpec;

import java.util.HashMap;

/**
 * 显示满足搜索条件文档表格
 *
 * @author 段鑫扬
 */
@ComponentBuilder("ext.exam.doc.builder.SearchDocListTableBuilder")
public class SearchDocListTableBuilder extends AbstractConfigurableTableBuilder {

    private static final String CLASSNAME = SearchDocListTableBuilder.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

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
        boolean enforce = SessionServerHelper.manager.setAccessEnforced(false);
        try {
           /* String searchName = (String) componentParams.getParameter("searchName");
            String searchNumber = (String) componentParams.getParameter("searchNumber");
            String customerName = (String) componentParams.getParameter("customerName");
            if (!PIStringUtils.hasText(searchName) && !PIStringUtils.hasText(searchNumber) && !PIStringUtils.hasText(customerName)) {
                return null;
            }*/
            NmHelperBean helperBean = ((JcaComponentParams) componentParams).getHelperBean();
            NmCommandBean commandBean = helperBean.getNmCommandBean();

            /*HashMap<String, Object> parameterMap = commandBean.getParameterMap();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>parameterMap= " + parameterMap);
            }*/
            HashMap textMap = commandBean.getText();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>textMap =" + textMap);
            }

            if (textMap == null || textMap.isEmpty()) {
                return null;
            }
            //搜索条件查询
            DocQuerySpec docQuerySpec = new DocQuerySpec(commandBean);
            QuerySpec qs = docQuerySpec.buildQuerySpec();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>qs = " + qs);
            }
            queryResult = PersistenceHelper.manager.find(qs);
            queryResult = new LatestConfigSpec().process(queryResult);
        } catch (WTException e) {
            e.printStackTrace();
        } finally {
            SessionServerHelper.manager.setAccessEnforced(enforce);
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
        ComponentConfigFactory factory = getComponentConfigFactory();
        TableConfig table = factory.newTableConfig();
        table.setLabel("Doc List Table");
        //table.setId("ext.example.part.mvc.builder.MyCouPartListTableBuilder");
        //是否可选
        table.setSelectable(true);
        //是否单选
        table.setSingleSelect(true);
        table.setShowCount(true);
        //图标
        table.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.ICON, true));
        //名称
        table.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.NAME, true));
        //编号
        table.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.NUMBER, true));
        //状态
        table.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.STATE, true));
        //上下文
        table.addComponent(factory.newColumnConfig(DescriptorConstants.ColumnIdentifiers.CONTAINER_NAME, true));
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
        return new SearchDocListConfigurableTableView();
    }
}
