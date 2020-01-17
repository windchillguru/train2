package ext.task.part.reader;

import ext.lang.office.PIExcelReader;
import ext.task.part.bean.ExcelInfoBean;
import org.apache.poi.ss.usermodel.Workbook;
import wt.util.WTException;

import java.io.File;
import java.io.InputStream;

/**
 * excel 的reader
 *
 * @author 段鑫扬
 */
public class ExcelInfoBeanReader extends PIExcelReader<ExcelInfoBean> {
    private static final long serialVersionUID = 1L;

    public ExcelInfoBeanReader() throws WTException {
    }

    public ExcelInfoBeanReader(Workbook workbook) throws WTException {
        super(workbook);
    }

    public ExcelInfoBeanReader(String s) throws WTException {
        super(s);
    }

    public ExcelInfoBeanReader(File file) throws WTException {
        super(file);
    }

    public ExcelInfoBeanReader(InputStream inputStream) throws WTException {
        super(inputStream);
    }


}
