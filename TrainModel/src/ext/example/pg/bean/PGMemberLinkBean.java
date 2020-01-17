package ext.example.pg.bean;

import ext.lang.PIStringUtils;
import ext.lang.bean.annotations.ExcelField;
import ext.lang.bean.persistable.ExcelBeanReadable;
import ext.lang.bean.persistable.ExcelBeanWritable;
import ext.lang.office.PIExcelReader;
import org.apache.poi.ss.usermodel.Row;
import wt.util.WTException;

/**
 * pg成员link的bean
 *
 * @author 段鑫扬
 * @version 2019/12/20
 */
public class PGMemberLinkBean implements ExcelBeanReadable, ExcelBeanWritable {
    private static final long serialVersionUID = 1L;
    //记录excel的行号
    private int rowIndex = -1;

    //PG组名
    @ExcelField(columnIndex = 0)
    private String pgGroupName;

    //PG组成员,(组名/工号）
    @ExcelField(columnIndex = 1)
    private String member;

    //成员是否是组
    @ExcelField(columnIndex = 2)
    private boolean group;

    //备注
    @ExcelField(columnIndex = 3)
    private String comments;


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


    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
