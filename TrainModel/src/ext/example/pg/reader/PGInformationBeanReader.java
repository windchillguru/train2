package ext.example.pg.reader;

import ext.example.pg.bean.PGInformationBean;
import ext.lang.PIStringUtils;
import ext.lang.bean.annotations.ExcelField;
import ext.lang.office.PIExcelReader;
import org.apache.poi.ss.usermodel.Workbook;
import wt.util.WTException;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * pg信息的reader
 *
 * @author 段鑫扬
 * @version 2019/12/20
 */
public class PGInformationBeanReader extends PIExcelReader<PGInformationBean> {

    public PGInformationBeanReader() {
        super();
    }

    public PGInformationBeanReader(Workbook workbook) throws WTException {
        super(workbook);
    }

    public PGInformationBeanReader(String s) throws WTException {
        super(s);
    }

    public PGInformationBeanReader(File file) throws WTException {
        super(file);
    }

    public PGInformationBeanReader(InputStream inputStream) throws WTException {
        super(inputStream);
    }

    @Override
    protected Boolean toBooleanValue(ExcelField excelField, PGInformationBean pgInformationBean, Field field, Object value) throws WTException {
        if (value == null) {
            return false;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof String) {
            String str = (String) value;
            if (PIStringUtils.equalsIgnoreCase(str, "true")) {
                return true;
            }
            if (PIStringUtils.equalsIgnoreCase(str, "y")) {
                return true;
            }
            if (PIStringUtils.equalsIgnoreCase(str, "yes")) {
                return true;
            }
            if (PIStringUtils.equalsIgnoreCase(str, "是")) {
                return true;
            }
        }

        return false;
    }
}
