package ext.example.pg.builder;

import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.mvc.components.*;
import com.ptc.mvc.util.ClientMessageSource;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.beans.NmHelperBean;
import ext.example.pg.model.PGGroup;
import ext.example.pg.model.PGInformation;
import ext.example.pg.resource.pgInfoRB;
import org.apache.log4j.Logger;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTArrayList;
import wt.log4j.LogR;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionServerHelper;
import wt.util.WTException;

import java.util.HashMap;

/**
 * 搜索PG组和PG信息的表格
 *
 * @author 段鑫扬
 */
@ComponentBuilder("ext.example.pg.builder.AddMemberTableBuilder")
public class AddMemberTableBuilder extends AbstractComponentBuilder {

    private static final String CLASSNAME = AddMemberTableBuilder.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = pgInfoRB.class.getName();
    private ClientMessageSource messageSource = getMessageSource(RESOURCE);


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
        WTArrayList result = new WTArrayList();
        boolean enforce = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            NmHelperBean helperBean = ((JcaComponentParams) componentParams).getHelperBean();
            NmCommandBean commandBean = helperBean.getNmCommandBean();
            HashMap textMap = commandBean.getText();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>textMap =" + textMap);
            }
            if (textMap == null || textMap.isEmpty()) {
                return null;
            }
            String pgName = (String) textMap.get("pgName");
            pgName = "%" + pgName + "%";
            pgName = pgName.replace("*", "%");

            QuerySpec qs = new QuerySpec(PGGroup.class);
            SearchCondition sc = new SearchCondition(PGGroup.class, PGGroup.PG_NAME, SearchCondition.LIKE, pgName);
            qs.appendWhere(sc, new int[]{0});
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>qs = " + qs);
            }
            QueryResult qr = PersistenceHelper.manager.find(qs);
            //查询的PG组
            result.addAll(qr);

            qs = new QuerySpec(PGInformation.class);
            sc = new SearchCondition(PGInformation.class, PGInformation.PG_NAME, SearchCondition.LIKE, pgName);
            qs.appendWhere(sc, new int[]{0});
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>qs = " + qs);
            }
            qr = PersistenceHelper.manager.find(qs);
            //查询的PG信息
            result.addAll(qr);

        } catch (WTException e) {
            e.printStackTrace();
        } finally {
            SessionServerHelper.manager.setAccessEnforced(enforce);
        }
        return result;
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

        //pgName
        ColumnConfig pgName = factory.newColumnConfig("pgName", messageSource.getMessage(pgInfoRB.PG_NAME), true);
        table.addComponent(pgName);
        //备注
        table.addComponent(factory.newColumnConfig("comments", messageSource.getMessage(pgInfoRB.COMMENTS), true));

        //是否是组
        ColumnConfig isGroup = factory.newColumnConfig("isGroup", messageSource.getMessage(pgInfoRB.IS_GROUP), true);
        //设置dataUtility
        isGroup.setDataUtilityId("PGInfoDataUtility");
        table.addComponent(isGroup);

        return table;
    }

}
