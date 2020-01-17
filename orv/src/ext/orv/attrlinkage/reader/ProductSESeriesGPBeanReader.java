package ext.orv.attrlinkage.reader;

import ext.lang.office.PIExcelReader;
import ext.orv.attrlinkage.bean.ProductSESeriesGPBean;
import org.apache.poi.ss.usermodel.Workbook;
import wt.util.WTException;

import java.io.File;
import java.io.InputStream;

/**
 * 产品系列，产品型号的java类 的reader类
 * @author 段鑫扬
 * @version 2019/12/30
 */
public class ProductSESeriesGPBeanReader  extends PIExcelReader<ProductSESeriesGPBean> {
    /**
     * 无参构造函数，需要把Excel的Workbook对象，通过set方法设置到类变量{@link #workbook}
     */
    public ProductSESeriesGPBeanReader() {
        super();
    }

    /**
     * 直接传递Workbook对象的构造方法
     *
     * @param workbook
     * @throws WTException
     */
    public ProductSESeriesGPBeanReader(Workbook workbook) throws WTException {
        super(workbook);
    }

    /**
     * 通过Excel文件的全路径构造Workbook对象，此构造方法会关闭文件流
     *
     * @param filePath
     * @throws WTException
     */
    public ProductSESeriesGPBeanReader(String filePath) throws WTException {
        super(filePath);
    }

    /**
     * 通过Excel文件的File对象构造Workbook对象，此构造方法会关闭文件流
     *
     * @param file
     * @throws WTException
     */
    public ProductSESeriesGPBeanReader(File file) throws WTException {
        super(file);
    }

    /**
     * 通过给定的Excel文件流构造Workbook对象，此方法不会关闭参数传递的文件流
     *
     * @param inStream
     * @throws WTException
     */
    public ProductSESeriesGPBeanReader(InputStream inStream) throws WTException {
        super(inStream);
    }
}
