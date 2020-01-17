package ext.train.report.bean;

import ext.lang.bean.annotations.ExcelField;
import ext.lang.bean.persistable.ExcelBeanWritable;
import wt.org.TimeZoneHelper;
import wt.part.WTPart;
import wt.part.WTPartUsageLink;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTStandardDateFormat;

import java.util.Locale;
import java.util.TimeZone;

/**
 * @author 段鑫扬
 */
public class BOMOrderBean implements ExcelBeanWritable {
    private static final long serialVersionUID = 1L;

    @ExcelField(columnIndex = 0)
    private Integer level;//层级

    @ExcelField(columnIndex = 1)
    private String partNumber;

    @ExcelField(columnIndex = 2)
    private String partName;

    @ExcelField(columnIndex = 3)
    private String partView;

    @ExcelField(columnIndex = 4)
    private String partVersion;

    @ExcelField(columnIndex = 5)
    private String partStatus;

    @ExcelField(columnIndex = 6)
    private String modifier;//修改者

    @ExcelField(columnIndex = 7)
    private String modifyTime;

    @ExcelField(columnIndex = 8)
    private String LineNumber;//行号

    @ExcelField(columnIndex = 9)
    private String amount;//数量

    private WTPartUsageLink link;
    private WTPart part;

    public BOMOrderBean() {
        super();
    }

    /**
     * @param
     * @param part
     * @throws WTException
     */
    public BOMOrderBean(WTPartUsageLink link, WTPart part, Integer level) throws WTException {
        super();
        this.link = link;
        this.part = part;
        this.level = level;
        if (part != null) {
            this.partName = part.getName();
            this.partNumber = part.getNumber();
            this.partView = part.getViewName();
            Locale locale = SessionHelper.manager.getLocale();
            this.partStatus = part.getState().getState().getDisplay(locale);
            this.partVersion = part.getVersionIdentifier().getValue() + "." + part.getIterationIdentifier().getValue();
            this.modifier = part.getModifier().getDisplayName();
            TimeZone tz = TimeZoneHelper.getTimeZone();
            this.modifyTime = WTStandardDateFormat.format(part.getModifyTimestamp(), "yyyy/MM/dd HH:mm:ss", locale, tz);
        }

        if (link != null) {
            wt.part.LineNumber lineNumber = link.getLineNumber();
            if (lineNumber != null) {
                this.LineNumber = lineNumber.getValue() + "";
            }
            this.amount = link.getQuantity().getAmount() + "" + link.getQuantity().getUnit()
                    .getDisplay(SessionHelper.manager.getLocale());
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

    public String getPartView() {
        return partView;
    }

    public void setPartView(String partView) {
        this.partView = partView;
    }

    public String getPartVersion() {
        return partVersion;
    }

    public void setPartVersion(String partVersion) {
        this.partVersion = partVersion;
    }

    public String getPartStatus() {
        return partStatus;
    }

    public void setPartStatus(String partStatus) {
        this.partStatus = partStatus;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getModifiyTime() {
        return modifyTime;
    }

    public void setModifiyTime(String modifiyTime) {
        this.modifyTime = modifiyTime;
    }

    public String getLineNumber() {
        return LineNumber;
    }

    public void setLineNumber(String lineNumber) {
        LineNumber = lineNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public WTPartUsageLink getLink() {
        return link;
    }

    public void setLink(WTPartUsageLink link) {
        this.link = link;
    }

    public WTPart getPart() {
        return part;
    }

    public void setPart(WTPart part) {
        this.part = part;
    }

    @Override
    public String toString() {
        return "SubPartBean{" +
                "level='" + level + '\'' +
                ", partNumber='" + partNumber + '\'' +
                ", partName='" + partName + '\'' +
                ", partView='" + partView + '\'' +
                ", partVersion='" + partVersion + '\'' +
                ", partStatus='" + partStatus + '\'' +
                ", modifier='" + modifier + '\'' +
                ", modifiyTime='" + modifyTime + '\'' +
                ", LineNumber='" + LineNumber + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
