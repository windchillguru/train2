package ext.exam.doc.bean;

import ext.lang.PIStringUtils;
import ext.lang.bean.annotations.ExcelField;
import ext.lang.bean.persistable.ExcelBeanReadable;
import ext.lang.office.PIExcelReader;
import org.apache.poi.ss.usermodel.Row;
import wt.util.WTException;

/**
 * 客户和经理的java类
 *
 * @author 段鑫扬
 */
public class CustomerManagerBean implements ExcelBeanReadable {

    private int rowIndex = -1;//excel 实际行标

    @ExcelField(columnIndex = 0)
    private String customerName;     //客户名称

    @ExcelField(columnIndex = 1)
    private String belongManager;     //所属经理

    /**
     * 判断是否是最后的数据
     *
     * @return
     * @throws WTException
     */
    @Override
    public boolean isEnd() throws WTException {
        if (!PIStringUtils.hasText(this.customerName) && !PIStringUtils.hasText(this.belongManager)) {
            return true;
        }
        return false;
    }


    /**
     * 前置数据处理
     *
     * @param row
     * @param piExcelReader
     * @throws WTException
     */
    @Override
    public void preProcessExcelValue(Row row, PIExcelReader<?> piExcelReader) throws WTException {
        if (row != null) {
            this.rowIndex = row.getRowNum() + 1;
        }
    }

    /**
     * 对于返回的数据进行判断，无效数据可以不返回
     *
     * @return
     * @throws WTException
     */
    @Override
    public boolean validate() throws WTException {
        //最后一行
        if (isEnd()) {
            return false;
        }
        return true;
    }


    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBelongManager() {
        return belongManager;
    }

    public void setBelongManager(String belongManager) {
        this.belongManager = belongManager;
    }

    @Override
    public String toString() {
        return "CustomerManagerBean{" +
                "rowIndex=" + rowIndex +
                ", customerName='" + customerName + '\'' +
                ", belongManager='" + belongManager + '\'' +
                '}';
    }
}
