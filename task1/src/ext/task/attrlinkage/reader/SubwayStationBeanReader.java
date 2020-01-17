package ext.task.attrlinkage.reader;

import ext.lang.office.PIExcelReader;
import ext.task.attrlinkage.bean.SubwayStationBean;
import org.apache.poi.ss.usermodel.Workbook;
import wt.util.WTException;

import java.io.File;
import java.io.InputStream;

/**
 * excel的reader
 *
 * @author 段鑫扬
 */
public class SubwayStationBeanReader extends PIExcelReader<SubwayStationBean> {

    public SubwayStationBeanReader() {
    }

    public SubwayStationBeanReader(Workbook workbook) throws WTException {
        super(workbook);
    }

    public SubwayStationBeanReader(String s) throws WTException {
        super(s);
    }

    public SubwayStationBeanReader(File file) throws WTException {
        super(file);
    }

    public SubwayStationBeanReader(InputStream inputStream) throws WTException {
        super(inputStream);
    }
}
