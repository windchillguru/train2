package ext.task.part.tableview;

import com.ptc.core.htmlcomp.components.JCAConfigurableTable;
import com.ptc.core.htmlcomp.tableview.SortColumnDescriptor;
import com.ptc.core.htmlcomp.tableview.TableColumnDefinition;
import com.ptc.core.htmlcomp.tableview.TableViewDescriptor;
import com.ptc.mvc.util.ClientMessageSource;
import ext.task.part.resource.excelInfoResource;
import wt.util.WTException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

/**
 * excel的视图类
 *
 * @author 段鑫扬
 */
public class ExcelInfoTableView extends JCAConfigurableTable {
    private static final String RESOURCE = excelInfoResource.class.getName();
    private ClientMessageSource message = getMessageSource(RESOURCE);

    /**
     * 设置表头
     *
     * @param locale
     * @return
     */
    @Override
    public String getLabel(Locale locale) {
        return "Excel信息";
    }

    /**
     * 设置排序字段
     *
     * @return
     */
    @Override
    public String getDefaultSortColumn() {
        return "cosNum";
    }

    /**
     * 设置可选对象
     *
     * @return
     */
    @Override
    public Class[] getClassTypes() {
        //非持久化对象返回 null
        return null;
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
            //编号
            columnDefinitionVector.add(TableColumnDefinition.newTableColumnDefinition("cosNum", true));
            //姓名
            columnDefinitionVector.add(TableColumnDefinition.newTableColumnDefinition("cosName", true));
            //性别
            columnDefinitionVector.add(TableColumnDefinition.newTableColumnDefinition("cosSex", true));
            //年龄
            columnDefinitionVector.add(TableColumnDefinition.newTableColumnDefinition("cosAge", true));

            List sortList = new ArrayList();                                                //排序依据数组
            SortColumnDescriptor sortColumnDescriptor = new SortColumnDescriptor();        //获取列排序对象
            sortColumnDescriptor.setColumnId("cosNum");                                //设置排序依据
            sortColumnDescriptor.setOrder(sortColumnDescriptor.ASCENDING);                //设置排序ASC顺序,DESC倒序
            sortList.add(sortColumnDescriptor);                                            //把设置排序的对象添加至集合

            //创建视图
            TableViewDescriptor tableViewDescriptor = TableViewDescriptor.newTableViewDescriptor("Default", param, true
                    , true, columnDefinitionVector, null, true, "Default");
            treeViewList.add(tableViewDescriptor);
        } catch (Exception e) {
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
