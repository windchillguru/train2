package ext.task2.part.bean;

import ext.lang.PIStringUtils;
import ext.lang.bean.annotations.ExcelField;
import ext.lang.bean.persistable.ExcelBeanReadable;
import ext.lang.bean.persistable.ExcelBeanWritable;
import wt.util.WTException;

/**
 * @author 段鑫扬
 * @version 2020/1/15
 */
public class BOMBean implements ExcelBeanWritable, ExcelBeanReadable {
    private static final long serialVersionUID = -791269319835892440L;
    //料号
    @ExcelField(columnIndex = 0)
    private String partNumber;
    //类别
    @ExcelField(columnIndex = 1)
    private String category;
    //子料号
    @ExcelField(columnIndex = 2)
    private String subPartNumber;
    //优先级
    @ExcelField(columnIndex = 3)
    private String priority;
    //备注
    @ExcelField(columnIndex = 4)
    private String comment;

    public BOMBean() {
    }

    public BOMBean(String partNumber, String category, String subPartNumber, String priority, String comment) {
        this.partNumber = partNumber;
        this.category = category;
        this.subPartNumber = subPartNumber;
        this.priority = priority;
        this.comment = comment;
    }

    /**
     * 判断是否是最后的数据
     *
     * @return
     * @throws WTException
     */
    @Override
    public boolean isEnd() throws WTException {
        if (!PIStringUtils.hasText(this.partNumber)) {
            return true;
        }
        return false;
    }


    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubPartNumber() {
        return subPartNumber;
    }

    public void setSubPartNumber(String subPartNumber) {
        this.subPartNumber = subPartNumber;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
