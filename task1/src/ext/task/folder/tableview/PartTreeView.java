package ext.task.folder.tableview;

import com.ptc.core.components.descriptor.DescriptorConstants;
import com.ptc.core.htmlcomp.components.JCAConfigurableTable;
import com.ptc.core.htmlcomp.tableview.SortColumnDescriptor;
import com.ptc.core.htmlcomp.tableview.TableColumnDefinition;
import com.ptc.core.htmlcomp.tableview.TableViewDescriptor;
import com.ptc.mvc.util.ClientMessageSource;
import ext.task.folder.resource.partTreeResource;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

/**
 * 部件树的视图类
 *
 * @author 段鑫扬
 */
public class PartTreeView extends JCAConfigurableTable {
    private static final String RESOURCE = partTreeResource.class.getName();
    private ClientMessageSource message = getMessageSource(RESOURCE);

    /**
     * 设置表头
     *
     * @param locale
     * @return
     */
    @Override
    public String getLabel(Locale locale) {
        return message.getMessage(partTreeResource.PART_TREE_LABEL);
    }

    /**
     * 设置排序字段
     *
     * @return
     */
    @Override
    public String getDefaultSortColumn() {
        return WTPartMaster.NUMBER;
    }

    /**
     * 设置可选对象
     *
     * @return
     */
    @Override
    public Class[] getClassTypes() {
        return new Class[]{WTPart.class};
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
        List<TableViewDescriptor> treeViewList = null;
        try {
            //表视图列表
            treeViewList = new ArrayList<TableViewDescriptor>();
            //定义视图列
            Vector<TableColumnDefinition> columnDefinitionVector = new Vector<TableColumnDefinition>();
            //图标
            columnDefinitionVector.add(TableColumnDefinition.newTableColumnDefinition(DescriptorConstants.ColumnIdentifiers.ICON, true));
            //名字
            columnDefinitionVector.add(TableColumnDefinition.newTableColumnDefinition(DescriptorConstants.ColumnIdentifiers.NAME, true));
            //编号
            columnDefinitionVector.add(TableColumnDefinition.newTableColumnDefinition(DescriptorConstants.ColumnIdentifiers.NUMBER, true));
            columnDefinitionVector.add(TableColumnDefinition.newTableColumnDefinition(DescriptorConstants.ColumnIdentifiers.INFO_ACTION, true));
            //生命周期模板
            columnDefinitionVector.add(TableColumnDefinition.newTableColumnDefinition(DescriptorConstants.ColumnIdentifiers.CONTAINER_NAME, true));
            //可排序列
            List<SortColumnDescriptor> sortColumnDescriptorList = new ArrayList<SortColumnDescriptor>();
            //编号升序
            sortColumnDescriptorList.add(new SortColumnDescriptor(DescriptorConstants.ColumnIdentifiers.NUMBER, SortColumnDescriptor.ASCENDING));

            //创建视图
            TableViewDescriptor tableViewDescriptor = TableViewDescriptor.newTableViewDescriptor("Default", param, true
                    , true, columnDefinitionVector, null, true, message.getMessage(partTreeResource.TASKFOLDER_PARTTREE_DESC));
            tableViewDescriptor.setColumnSortOrder(sortColumnDescriptorList);
            treeViewList.add(tableViewDescriptor);
        } catch (WTPropertyVetoException e) {
            e.printStackTrace();
        }
        return treeViewList;
    }

    /**
     * 设置激活视图名称
     *
     * @return
     */
    @Override
    public String getOOTBActiveViewName() {
        return "Default";
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
