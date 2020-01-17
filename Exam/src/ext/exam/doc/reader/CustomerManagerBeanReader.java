package ext.exam.doc.reader;

import ext.exam.doc.bean.CustomerManagerBean;
import ext.lang.office.PIExcelReader;
import org.apache.poi.ss.usermodel.Workbook;
import wt.util.WTException;

import java.io.File;
import java.io.InputStream;

/**
 * excel的reader
 *
 * @author 段鑫扬
 */
public class CustomerManagerBeanReader extends PIExcelReader<CustomerManagerBean> {

    public CustomerManagerBeanReader() {
        super();
    }

    public CustomerManagerBeanReader(Workbook workbook) throws WTException {
        super(workbook);
    }

    public CustomerManagerBeanReader(String filePath) throws WTException {
        super(filePath);
    }

    public CustomerManagerBeanReader(File file) throws WTException {
        super(file);
    }

    public CustomerManagerBeanReader(InputStream inStream) throws WTException {
        super(inStream);
    }
}
