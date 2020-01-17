package ext.orv.attrlinkage.bean;

import ext.lang.PIStringUtils;
import ext.lang.bean.annotations.ExcelField;
import ext.lang.bean.persistable.ExcelBeanReadable;
import ext.lang.office.PIExcelReader;
import org.apache.poi.ss.usermodel.Row;
import wt.util.WTException;

/**
 * 产品系列，产品型号的java类
 *
 * @author 段鑫扬
 */
public class ProductSESeriesGPBean implements ExcelBeanReadable {

    private int rowIndex = -1;//excel 实际行标

    @ExcelField(columnIndex = 0)
    private String productSE;     //产品系列

    @ExcelField(columnIndex = 1)
    private String productSEDesc;     //产品系列说明

    @ExcelField(columnIndex = 2)
    private String seriesGP;  //产品型号

    @ExcelField(columnIndex = 3)
    private String seriesGPDesc;  //产品型号说明


    /**
     * 判断是否是最后的数据
     *
     * @return
     * @throws WTException
     */
    @Override
    public boolean isEnd() throws WTException {
        //产品系列没有值时则数据读取结束
        if (!PIStringUtils.hasText(this.productSE)) {
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
     * 对于返回的数据进行判断，无有效数据可以不返回
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

    public String getProductSE() {
        return productSE;
    }

    public void setProductSE(String productSE) {
        this.productSE = productSE;
    }

    public String getProductSEDesc() {
        return productSEDesc;
    }

    public void setProductSEDesc(String productSEDesc) {
        this.productSEDesc = productSEDesc;
    }

    public String getSeriesGP() {
        return seriesGP;
    }

    public void setSeriesGP(String seriesGP) {
        this.seriesGP = seriesGP;
    }

    public String getSeriesGPDesc() {
        return seriesGPDesc;
    }

    public void setSeriesGPDesc(String seriesGPDesc) {
        this.seriesGPDesc = seriesGPDesc;
    }

    @Override
    public String toString() {
        return "ProductSESeriesGPBean{" +
                "rowIndex=" + rowIndex +
                ", productSE='" + productSE + '\'' +
                ", productSEDesc='" + productSEDesc + '\'' +
                ", seriesGP='" + seriesGP + '\'' +
                ", seriesGPDesc='" + seriesGPDesc + '\'' +
                '}';
    }
}
