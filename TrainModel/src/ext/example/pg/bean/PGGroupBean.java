package ext.example.pg.bean;

import ext.lang.PIStringUtils;
import ext.lang.bean.annotations.ExcelField;
import ext.lang.bean.persistable.ExcelBeanReadable;
import ext.lang.bean.persistable.ExcelBeanWritable;
import ext.lang.office.PIExcelReader;
import org.apache.poi.ss.usermodel.Row;
import wt.util.WTException;

/**
 * pg组的bean
 *
 * @author 段鑫扬
 * @version 2019/12/20
 */
public class PGGroupBean implements ExcelBeanReadable, ExcelBeanWritable {
    private static final long serialVersionUID = 1L;
    //记录excel的行号
    private int rowIndex = -1;

    //组名
    @ExcelField(columnIndex = 0)
    private String pgGroupName;

    //备注
    @ExcelField(columnIndex = 1)
    private String comments;

    //是否启用
    @ExcelField(columnIndex = 2)
    private boolean enabled;

    //是否根节点
    @ExcelField(columnIndex = 3)
    private boolean root;

    private String pgName;

    public PGGroupBean() {
    }

    public PGGroupBean(String pgGroupName, String comments, boolean enabled, boolean root) {
        this.pgGroupName = pgGroupName;
        this.comments = comments;
        this.enabled = enabled;
        this.root = root;
        this.pgName = pgGroupName;
    }

    /**
     * 判断是否是最后的数据
     *
     * @return
     * @throws WTException
     */
    @Override
    public boolean isEnd() throws WTException {
        if (!PIStringUtils.hasText(this.pgGroupName)) {
            //组名为空，则读取结束
            return true;
        }
        return false;
    }

    /**
     * 后置数据处理
     *
     * @param row
     * @param piExcelReader
     * @throws WTException
     */
    @Override
    public void postProcessExcelValue(Row row, PIExcelReader<?> piExcelReader) throws WTException {
        if (PIStringUtils.hasText(this.pgGroupName)) {
            this.pgName = this.pgGroupName;
        }
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

    public String getPgGroupName() {
        return pgGroupName;
    }

    public void setPgGroupName(String pgGroupName) {
        this.pgGroupName = pgGroupName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }
}
