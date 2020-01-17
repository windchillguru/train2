package ext.task.attrlinkage.bean;

import ext.lang.PIStringUtils;
import ext.lang.bean.annotations.ExcelField;
import ext.lang.bean.persistable.ExcelBeanReadable;
import ext.lang.office.PIExcelReader;
import org.apache.poi.ss.usermodel.Row;
import wt.util.WTException;

import java.util.List;

/**
 * 地铁 站台，出口的java类
 *
 * @author 段鑫扬
 */
public class SubwayStationBean implements ExcelBeanReadable {

    private int rowIndex = -1;//excel 实际行标

    @ExcelField(columnIndex = 0)
    private String subway;     //地铁线路

    @ExcelField(columnIndex = 1)
    private String station;     //站台

    @ExcelField(columnIndex = 2)
    private String exit;  //出口


    /**
     * 判断是否是最后的数据
     *
     * @return
     * @throws WTException
     */
    @Override
    public boolean isEnd() throws WTException {
        //地铁，站点，出口
        if (!PIStringUtils.hasText(this.subway) && !PIStringUtils.hasText(this.station)) {
            return true;
        }
        return false;
    }

    /**
     * 后置处理数据
     *
     * @param row
     * @param reader
     * @throws WTException
     */
    @Override
    public void postProcessExcelValue(Row row, PIExcelReader<?> reader) throws WTException {
        try {
            List<?> beanList = reader.getBeanList();
            if (!PIStringUtils.hasText(this.subway)) {
                //地铁线路为空时，使用上一条数据的地铁线路
                if (beanList != null && beanList.size() > 0) {
                    int size = beanList.size();
                    int lastIndex = size - 1;//上一个数据的索引
                    Object obj = beanList.get(lastIndex);//上一个数据
                    if (obj != null && obj instanceof SubwayStationBean) {
                        SubwayStationBean subwayStationBean = (SubwayStationBean) obj;//上一个数据
                        //将上一个地铁线路赋值给当前对象
                        this.subway = subwayStationBean.getSubway();
                        if (!PIStringUtils.hasText(this.station)) {
                            //当站点为空时，在excel表格中地铁线路必定为空，
                            //将上一个站点赋值给当前对象
                            this.station = subwayStationBean.getStation();
                        }
                    }
                }
            }

        } catch (WTException e) {
            e.printStackTrace();
        }
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

    public String getSubway() {
        return subway;
    }

    public void setSubway(String subway) {
        this.subway = subway;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    @Override
    public String toString() {
        return "SubwayStationBean{" +
                "subway='" + subway + '\'' +
                ", station='" + station + '\'' +
                '}';
    }
}
