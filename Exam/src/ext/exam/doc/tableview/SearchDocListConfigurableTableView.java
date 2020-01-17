package ext.exam.doc.tableview;

import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.core.htmlcomp.components.JCAConfigurableTable;
import com.ptc.core.htmlcomp.tableview.TableColumnDefinition;
import com.ptc.core.htmlcomp.tableview.TableViewDescriptor;
import com.ptc.mvc.util.ClientMessageSource;
import ext.exam.doc.resource.docResource;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.util.WTException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

/**
 * 搜索文档列表的视图
 *
 * @author 段鑫扬
 */
public class SearchDocListConfigurableTableView extends JCAConfigurableTable {

    private static final String RESOURCE = docResource.class.getName();
    private ClientMessageSource message = getMessageSource(RESOURCE);

    /**
     * 设置表头
     *
     * @param locale
     * @return
     */
    @Override
    public String getLabel(Locale locale) {
        return "doc List Table";
    }

    /**
     * 设置排序字段
     *
     * @return
     */
    @Override
    public String getDefaultSortColumn() {
        return WTDocumentMaster.NUMBER;
    }

    /**
     * 设置可选对象
     *
     * @return
     */
    @Override
    public Class[] getClassTypes() {
        return new Class[]{WTDocument.class};
    }

    /**
     * 配置视图
     *
     * @param
     * @param locale
     * @return
     * @throws WTException
     */
    @Override
    public List getOOTBTableViews(String param, Locale locale) throws WTException {
        List<TableViewDescriptor> tableViewList = null;
        try {
            //表视图列表
            tableViewList = new ArrayList<TableViewDescriptor>();
            //定义视图列
            Vector<TableColumnDefinition> columnDefinitionVector = new Vector<TableColumnDefinition>();
            //图标
            columnDefinitionVector.add(TableColumnDefinition.newTableColumnDefinition(DescriptorConstants.ColumnIdentifiers.ICON, true));
            //名字
            columnDefinitionVector.add(TableColumnDefinition.newTableColumnDefinition(DescriptorConstants.ColumnIdentifiers.NAME, true));
            //编号
            columnDefinitionVector.add(TableColumnDefinition.newTableColumnDefinition(DescriptorConstants.ColumnIdentifiers.NUMBER, true));
            columnDefinitionVector.add(TableColumnDefinition.newTableColumnDefinition(DescriptorConstants.ColumnIdentifiers.INFO_ACTION, true));

            //表视图
            TableViewDescriptor tableViewDescriptor = TableViewDescriptor.newTableViewDescriptor("默认", param, true
                    , true, columnDefinitionVector, null, true, "默认");

            tableViewList.add(tableViewDescriptor);
        } catch (WTException e) {
            e.printStackTrace();
        }
        return tableViewList;
    }

    /**
     * 设置激活视图名称
     *
     * @return
     */
    @Override
    public String getOOTBActiveViewName() {
        return "默认";
    }


    /**
     * 设置特殊视图列
     *
     * @param locale
     * @return
     */
    @Override
    public List<TableViewDescriptor> getSpecialTableColumnsAttrDefinition(Locale locale) {
        return new ArrayList<TableViewDescriptor>();
    }
}
