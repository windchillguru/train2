package ext.task2.part.reader;

import ext.lang.office.PIExcelReader;
import ext.task2.part.bean.BOMBean;
import org.apache.poi.ss.usermodel.Workbook;
import wt.util.WTException;

import java.io.File;
import java.io.InputStream;

/**
 * @author 段鑫扬
 * @version 2020/1/15
 */
public class BOMBeanReader extends PIExcelReader<BOMBean> {
    private static final long serialVersionUID = -8220918419733486252L;

    /**
     * 无参构造函数，需要把Excel的Workbook对象，通过set方法设置到类变量{@link #workbook}
     */
    public BOMBeanReader() {
    }

    /**
     * 直接传递Workbook对象的构造方法
     *
     * @param workbook
     * @throws WTException
     */
    public BOMBeanReader(Workbook workbook) throws WTException {
        super(workbook);
    }

    /**
     * 通过Excel文件的全路径构造Workbook对象，此构造方法会关闭文件流
     *
     * @param filePath
     * @throws WTException
     */
    public BOMBeanReader(String filePath) throws WTException {
        super(filePath);
    }

    /**
     * 通过Excel文件的File对象构造Workbook对象，此构造方法会关闭文件流
     *
     * @param file
     * @throws WTException
     */
    public BOMBeanReader(File file) throws WTException {
        super(file);
    }

    /**
     * 通过给定的Excel文件流构造Workbook对象，此方法不会关闭参数传递的文件流
     *
     * @param inStream
     * @throws WTException
     */
    public BOMBeanReader(InputStream inStream) throws WTException {
        super(inStream);
    }
}
