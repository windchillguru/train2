package ext.task.part.bean;

import com.ptc.netmarkets.model.NmOid;
import ext.lang.PIStringUtils;
import ext.lang.bean.annotations.ExcelField;
import ext.lang.bean.persistable.ExcelBeanReadable;
import wt.util.WTException;

/**
 * excel 信息的bean
 *
 * @author 段鑫扬
 */
public class ExcelInfoBean extends NmOid implements ExcelBeanReadable {

    @ExcelField(columnIndex = 0)
    private String cosNum;     //编号

    @ExcelField(columnIndex = 1)
    private String cosName;     //姓名

    @ExcelField(columnIndex = 2)
    private String cosSex;     //性别

    @ExcelField(columnIndex = 3)
    private String cosAge;     //年龄

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

    /**
     * 判断是否最后的数据
     *
     * @return
     * @throws WTException
     */
    @Override
    public boolean isEnd() throws WTException {
        //如果编号为空，则结束
        if (!PIStringUtils.hasText(this.cosNum)) {
            return true;
        }
        return false;
    }

    public String getCosNum() {
        return cosNum;
    }

    public void setCosNum(String cosNum) {
        this.cosNum = cosNum;
    }

    public String getCosName() {
        return cosName;
    }

    public void setCosName(String cosName) {
        this.cosName = cosName;
    }

    public String getCosSex() {
        return cosSex;
    }

    public void setCosSex(String cosSex) {
        this.cosSex = cosSex;
    }

    public String getCosAge() {
        return cosAge;
    }

    public void setCosAge(String cosAge) {
        this.cosAge = cosAge;
    }

    @Override
    public String toString() {
        return "ExcelInfoBean{" +
                "cosNum='" + cosNum + '\'' +
                ", cosName='" + cosName + '\'' +
                ", cosSex='" + cosSex + '\'' +
                ", cosAge='" + cosAge + '\'' +
                '}';
    }
}
