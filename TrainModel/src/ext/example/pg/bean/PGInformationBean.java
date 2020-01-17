package ext.example.pg.bean;

import ext.lang.PIStringUtils;
import ext.lang.bean.annotations.ExcelField;
import ext.lang.bean.persistable.ExcelBeanReadable;
import ext.lang.bean.persistable.ExcelBeanWritable;
import ext.lang.office.PIExcelReader;
import ext.pi.core.PIDocumentHelper;
import org.apache.poi.ss.usermodel.Row;
import wt.doc.WTDocument;
import wt.util.WTException;

/**
 * @author 段鑫扬
 * @version 2019/12/20
 */
public class PGInformationBean implements ExcelBeanReadable, ExcelBeanWritable {
    private static final long serialVersionUID = 1L;
    //记录excel的行号
    private int rowIndex = -1;

    //工号
    @ExcelField(columnIndex = 0)
    private String employeeNo;

    //姓名
    @ExcelField(columnIndex = 1)
    private String employeeName;

    //用户名
    @ExcelField(columnIndex = 2)
    private String employeeUserName;

    //邮箱
    @ExcelField(columnIndex = 3)
    private String employeeEmail;

    //电话
    @ExcelField(columnIndex = 4)
    private String employeePhone;

    //备注
    @ExcelField(columnIndex = 5)
    private String comments;

    //是否有经验
    @ExcelField(columnIndex = 6)
    private boolean experienced;

    //简历文档编号
    @ExcelField(columnIndex = 7)
    private String resumeDocNo;

    //信息来源
    @ExcelField(columnIndex = 8)
    private String informationSource;

    //是否组长
    @ExcelField(columnIndex = 9)
    private boolean leader;

    //信息编号
    @ExcelField(columnIndex = 10)
    private String informationNo;

    private String pgName;

    //简历文档
    private WTDocument resumeDoc;

    public PGInformationBean() {
        super();
    }

    public PGInformationBean(String employeeNo, String employeeName, String employeeUserName, String employeeEmail, String employeePhone, String comments, boolean experienced, String informationSource, boolean leader, WTDocument resumeDoc, String pgName) {
        this.employeeNo = employeeNo;
        this.employeeName = employeeName;
        this.employeeUserName = employeeUserName;
        this.employeeEmail = employeeEmail;
        this.employeePhone = employeePhone;
        this.comments = comments;
        this.experienced = experienced;
        this.informationSource = informationSource;
        this.leader = leader;
        this.pgName = pgName;
        this.resumeDoc = resumeDoc;
    }

    /**
     * 判断是否是最后的数据
     *
     * @return
     * @throws WTException
     */
    @Override
    public boolean isEnd() throws WTException {
        if (!PIStringUtils.hasText(this.employeeNo)) {
            //工号为空，则读取结束
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
        if (PIStringUtils.hasText(this.employeeName) && PIStringUtils.hasText(this.employeeUserName)) {
            this.pgName = this.employeeName + "(" + this.employeeUserName + ")";
        }
        if (PIStringUtils.hasText(this.resumeDocNo)) {
            this.resumeDoc = PIDocumentHelper.service.findWTDocument(this.resumeDocNo);
            if (this.resumeDoc == null) {
                throw new WTException("row :" + this.rowIndex + " 的简历编号：" + this.resumeDocNo + " 对应的文档不存在");
            }
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

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeUserName() {
        return employeeUserName;
    }

    public void setEmployeeUserName(String employeeUserName) {
        this.employeeUserName = employeeUserName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isExperienced() {
        return experienced;
    }

    public void setExperienced(boolean experienced) {
        this.experienced = experienced;
    }

    public String getResumeDocNo() {
        return resumeDocNo;
    }

    public void setResumeDocNo(String resumeDocNo) {
        this.resumeDocNo = resumeDocNo;
    }

    public String getInformationSource() {
        return informationSource;
    }

    public void setInformationSource(String informationSource) {
        this.informationSource = informationSource;
    }

    public boolean isLeader() {
        return leader;
    }

    public void setLeader(boolean leader) {
        this.leader = leader;
    }

    public String getInformationNo() {
        return informationNo;
    }

    public void setInformationNo(String informationNo) {
        this.informationNo = informationNo;
    }

    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public WTDocument getResumeDoc() {
        return resumeDoc;
    }

    public void setResumeDoc(WTDocument resumeDoc) {
        this.resumeDoc = resumeDoc;
    }
}
