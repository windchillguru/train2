package ext.train.report.bean;

import ext.lang.bean.annotations.ExcelField;
import ext.lang.bean.persistable.ExcelBeanWritable;
import wt.part.WTPart;
import wt.session.SessionHelper;
import wt.util.WTException;

/**
 * @author 段鑫扬
 */
public class TopPartBean implements ExcelBeanWritable {
    private static final long serialVersionUID = 1L;

    @ExcelField(columnIndex = 0)
    private String partNumber;

    @ExcelField(columnIndex = 1)
    private String partName;

    @ExcelField(columnIndex = 2)
    private String partVersion;

    @ExcelField(columnIndex = 3)
    private String topPartNumber;

    @ExcelField(columnIndex = 4)
    private String topPartName;

    @ExcelField(columnIndex = 5)
    private String topPartState;

    @ExcelField(columnIndex = 6)
    private String topPartVersion;
    //物料
    private WTPart part;
    //顶层物料
    private WTPart topPart;

    public TopPartBean() {
        super();
    }

    public TopPartBean(String partNumber, WTPart part, WTPart topPart) {
        super();
        this.partNumber = partNumber;
        this.part = part;
        this.topPart = topPart;

        if (part == null) {
            this.partName = "物料在系统中不存在";
        } else {
            this.partName = part.getName();
            this.partVersion = part.getDisplayIdentifier().toString();
            if (topPart == null) {
                this.topPartNumber = "没有顶层物料";
            }
        }

        if (topPart != null) {
            this.topPartNumber = topPart.getNumber();
            this.topPartName = topPart.getName();
            try {
                this.topPartState = topPart.getLifeCycleState().getDisplay(SessionHelper.manager.getLocale());
            } catch (WTException e) {
                e.printStackTrace();
            }
            this.topPartVersion = topPart.getDisplayIdentifier().toString();
        }
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartVersion() {
        return partVersion;
    }

    public void setPartVersion(String partVersion) {
        this.partVersion = partVersion;
    }

    public String getTopPartNumber() {
        return topPartNumber;
    }

    public void setTopPartNumber(String topPartNumber) {
        this.topPartNumber = topPartNumber;
    }

    public String getTopPartName() {
        return topPartName;
    }

    public void setTopPartName(String topPartName) {
        this.topPartName = topPartName;
    }

    public String getTopPartState() {
        return topPartState;
    }

    public void setTopPartState(String topPartState) {
        this.topPartState = topPartState;
    }

    public String getTopPartVersion() {
        return topPartVersion;
    }

    public void setTopPartVersion(String topPartVersion) {
        this.topPartVersion = topPartVersion;
    }

    public WTPart getPart() {
        return part;
    }

    public void setPart(WTPart part) {
        this.part = part;
    }

    public WTPart getTopPart() {
        return topPart;
    }

    public void setTopPart(WTPart topPart) {
        this.topPart = topPart;
    }

    @Override
    public String toString() {
        return "TopPartBean{" +
                "partNumber='" + partNumber + '\'' +
                ", partName='" + partName + '\'' +
                ", partVersion='" + partVersion + '\'' +
                ", topPartNumber='" + topPartNumber + '\'' +
                ", topPartName='" + topPartName + '\'' +
                ", topPartState='" + topPartState + '\'' +
                ", topPartVersion='" + topPartVersion + '\'' +
                ", part=" + part +
                ", topPart=" + topPart +
                '}';
    }
}
