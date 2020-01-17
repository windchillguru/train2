package ext.example.part.mvc.tableview;

import com.ptc.core.htmlcomp.components.JCAConfigurableTable;
import com.ptc.core.htmlcomp.createtableview.Attribute;
import com.ptc.core.htmlcomp.tableview.TableColumnDefinition;
import com.ptc.core.htmlcomp.tableview.TableViewDescriptor;
import com.ptc.mvc.util.ClientMessageSource;
import ext.example.part.resource.treeResource;
import wt.util.WTException;
import wt.util.WTMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

/**
 * 树视图
 * @author 段鑫扬
 */
public class XmlTreeView extends JCAConfigurableTable {
    private static final String RESOURCE = treeResource.class.getName();
    private ClientMessageSource message = getMessageSource(RESOURCE);

    /**
     * 设置表头
     * @param locale
     * @return
     */
    @Override
    public String getLabel(Locale locale) {
        return message.getMessage(treeResource.TREE_XML_LABEL);
    }

    /**
     * 设置排序字段
     * @return
     */
    @Override
    public String getDefaultSortColumn() {
        return null;
    }

    /**
     * 设置可选对象
     * @return
     */
    @Override
    public Class[] getClassTypes() {
        //非持久化对象，返回null
        return null;
    }

    /**
     * 配置视图
     * @param
     * @param locale
     * @return
     * @throws WTException
     */
    @Override
    public List getOOTBTableViews(String param, Locale locale) throws WTException {
        List<TableViewDescriptor> treeViewList = null;
        try {
            //表视图列表
            treeViewList = new ArrayList<TableViewDescriptor>();
            //定义视图列
            Vector<TableColumnDefinition> columnDefinitionVector = new Vector<TableColumnDefinition>();

            columnDefinitionVector.add(TableColumnDefinition.newTableColumnDefinition("name",true));
            //创建视图
            TableViewDescriptor tableViewDescriptor = TableViewDescriptor.newTableViewDescriptor(message.getMessage(treeResource.TREE_NAME),param,true
                    ,true,columnDefinitionVector,null,true,message.getMessage(treeResource.TREE_DESC));

            treeViewList.add(tableViewDescriptor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return treeViewList;
    }

    /**
     * 视图名称
     * @return
     */
    @Override
    public String getOOTBActiveViewName() {
        return message.getMessage(treeResource.TREE_XML_NAME);
    }


    /**
     * 设置特殊视图列
     * @param locale
     * @return
     */
    @Override
    public List<Attribute.TextAttribute> getSpecialTableColumnsAttrDefinition(Locale locale) {
        List<Attribute.TextAttribute> attri = new ArrayList<>();
        attri.add(new Attribute.TextAttribute("name", WTMessage.getLocalizedMessage(RESOURCE,treeResource.TREE_XML_LABEL, new Object[]{}, locale),locale));
        return attri;
    }
}
