package ext.task.attrlinkage.reader;

import ext.lang.PIFileUtils;
import ext.lang.PIStringUtils;
import ext.task.attrlinkage.bean.SubwayStationBean;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.util.WTException;
import wt.util.WTProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * excel 的解析helper
 *
 * @author 段鑫扬
 */
public class SubwayStationBeanHelper {

    private static final String CLASSNAME = SubwayStationBeanHelper.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final long serialVersionUID = 1L;
    private static String Excel_path = "";
    private static String ExcelSub_path = "ext/task/Sub_Station.xlsx";

    static {
        //加载excel
        try {
            WTProperties wtProperties = WTProperties.getLocalProperties();
            String codebasePath = wtProperties.getProperty("wt.codebase.location");
            Excel_path = codebasePath + File.separator + ExcelSub_path;
            Excel_path = PIFileUtils.formatFilePath(Excel_path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //存储地铁和站点信息
    private Map<String, List<String>> subwayStationMap = new HashMap<String, List<String>>();
    //存储站点和出口信息
    private Map<String, List<String>> stationExitMap = new HashMap<String, List<String>>();

    //存储修改时间
    private static Map<Long, SubwayStationBeanHelper> map = new TreeMap<>();

    /**
     * 获取实例
     *
     * @return
     * @throws WTException
     */
    public static SubwayStationBeanHelper getInstance() throws WTException {
        SubwayStationBeanHelper instance = null;
        try {
            File file = new File(Excel_path);
            long lastModifiedTime = file.lastModified();
            //如果修改了，则重新创建对象
            if (map.containsKey(lastModifiedTime)) {
                instance = map.get(lastModifiedTime);
            } else {
                instance = new SubwayStationBeanHelper();
                map.put(lastModifiedTime, instance);
            }
        } catch (WTException e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 单例
     *
     * @throws WTException
     */
    private SubwayStationBeanHelper() throws WTException {
        loadExcel();
    }

    /**
     * 加载文件
     *
     * @throws WTException
     */
    private void loadExcel() throws WTException {
        FileInputStream fis = null;
        try {
            File file = new File(Excel_path);
            fis = new FileInputStream(file);

            SubwayStationBeanReader reader = new SubwayStationBeanReader(fis);

            Collection<Integer> indexs = new ArrayList<>();
            indexs.add(0);
            reader.setSheetIndexs(indexs);//从第一页读取

            reader.setStartRowIndex(1); //从第二行开始
            reader.read();
            List<SubwayStationBean> subwayStationBeans = reader.getBeanList();//获取站点集合

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("subwayStationBeans =" + subwayStationBeans);
            }
            parseSubwayStationBeans(subwayStationBeans);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将地铁数据集合解析为map
     *
     * @param subwayStationBeans
     * @return
     */
    private void parseSubwayStationBeans(List<SubwayStationBean> subwayStationBeans) {
        try {

            for (SubwayStationBean subwayStationBean : subwayStationBeans) {
                //解析地铁和站点
                parseSubAndStation(subwayStationBean);
                //解析站点和出口
                parseStationAndExit(subwayStationBean);
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>subwayStationMap = " + subwayStationMap);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析站点和出口
     *
     * @param subwayStationBean
     */
    private void parseStationAndExit(SubwayStationBean subwayStationBean) {
        String exit = subwayStationBean.getExit();
        if (!PIStringUtils.hasText(exit)) {
            //如果出口为空，则不处理
            return;
        }
        String station = subwayStationBean.getStation();
        if (stationExitMap.containsKey(station)) {
            //如果站点已经存储了，则在出口集合中添加出口
            stationExitMap.get(station).add(subwayStationBean.getExit());
        } else {
            //第一次添加站点
            List<String> list = new ArrayList<>();
            list.add(subwayStationBean.getExit());
            stationExitMap.put(station, list);
        }

    }

    /**
     * 解析地铁和站点
     *
     * @param subwayStationBean
     */
    private void parseSubAndStation(SubwayStationBean subwayStationBean) {
        String subway = subwayStationBean.getSubway();//地铁线路
        if (subwayStationMap.containsKey(subway)) {
            List<String> list = subwayStationMap.get(subway);
            //如果地铁已经存储了，则在站点集合中添加站点
            String station = subwayStationBean.getStation();
            if (!list.contains(station)) {//如果不包含此站点
                list.add(station);
            }
        } else {
            //第一次添加地铁线路
            List<String> list = new ArrayList<>();
            list.add(subwayStationBean.getStation());
            subwayStationMap.put(subway, list);
        }
    }

    /**
     * 根据地铁获取站点信息
     *
     * @param subway
     * @return
     */
    public List<String> getStationListBySubway(String subway) {
        List<String> list = null;
        try {
            list = new ArrayList<>();
            if (subwayStationMap != null && subwayStationMap.containsKey(subway)) {
                list.addAll(subwayStationMap.get(subway));
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>stationList =" + list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据站点获取出口信息
     *
     * @param station
     * @return
     */
    public List<String> getExitListByStation(String station) {
        List<String> list = null;
        try {
            list = new ArrayList<>();
            if (stationExitMap != null && stationExitMap.containsKey(station)) {
                list.addAll(stationExitMap.get(station));
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>stationList =" + list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取所有地铁线路
     *
     * @return
     */
    public List<String> getSubwayList() {
        List<String> list = new ArrayList<>();
        if (subwayStationMap != null) {
            list.addAll(subwayStationMap.keySet());
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>> subwayList = " + list);
        }
        return list;
    }
}
