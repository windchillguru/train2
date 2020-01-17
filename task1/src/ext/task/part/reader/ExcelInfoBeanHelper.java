package ext.task.part.reader;

import ext.lang.PIFileUtils;
import ext.task.part.bean.ExcelInfoBean;
import wt.util.WTException;
import wt.util.WTProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * excel 的 helper
 *
 * @author 段鑫扬
 */
public class ExcelInfoBeanHelper {
    private static final long serialVersionUID = 1L;
    private static String Excel_path = "";
    private static String ExcelInfo_path = "ext/task/info.xlsx";

    static {
        //加载excel
        try {
            WTProperties wtProperties = WTProperties.getLocalProperties();
            String codebasePath = wtProperties.getProperty("wt.codebase.location");
            Excel_path = codebasePath + File.separator + ExcelInfo_path;
            Excel_path = PIFileUtils.formatFilePath(Excel_path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<ExcelInfoBean> excelInfoList;

    //存储修改时间
    private static Map<Long, ExcelInfoBeanHelper> map = new TreeMap<>();

    /**
     * 获取实例
     *
     * @return
     * @throws WTException
     */
    public static ExcelInfoBeanHelper getInstance() throws WTException {
        ExcelInfoBeanHelper instance = null;
        try {
            File file = new File(Excel_path);
            long lastModifiedTime = file.lastModified();
            instance = null;

            //如果修改了，则重新创建对象
            if (map.containsKey(lastModifiedTime)) {
                instance = map.get(lastModifiedTime);
            } else {
                instance = new ExcelInfoBeanHelper();
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
    private ExcelInfoBeanHelper() throws WTException {
        loadExcelInfo();
    }

    /**
     * 加载文件
     *
     * @throws WTException
     */
    private void loadExcelInfo() throws WTException {
        FileInputStream fis = null;
        try {
            File file = new File(Excel_path);
            fis = new FileInputStream(file);

            ExcelInfoBeanReader reader = new ExcelInfoBeanReader(fis);

            Collection<Integer> indexs = new ArrayList<>();
            indexs.add(0);
            reader.setSheetIndexs(indexs);//从第一页读取

            reader.setStartRowIndex(1); //从第二行开始
            reader.read();
            excelInfoList = reader.getBeanList();//获取excel信息
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
     * 获取数据
     *
     * @return
     */
    public List<ExcelInfoBean> getExcelInfoList() {
        return excelInfoList;
    }
}
