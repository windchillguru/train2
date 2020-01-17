package ext.exam.doc.reader;

import ext.exam.doc.bean.CustomerManagerBean;
import ext.lang.PIFileUtils;
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
public class CustomerManagerBeanHelper {

    private static final String CLASSNAME = CustomerManagerBeanHelper.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final long serialVersionUID = 1L;
    private static String Excel_path = "";
    private static String ExcelSub_path = "ext/exam/CustomerInfo.xlsx";

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
    private Map<String, String> customerManagerMap = new HashMap<String, String>();

    //存储修改时间
    private static Map<Long, CustomerManagerBeanHelper> map = new TreeMap<>();

    /**
     * 获取实例
     *
     * @return
     * @throws WTException
     */
    public static CustomerManagerBeanHelper getInstance() throws WTException {
        CustomerManagerBeanHelper instance = null;
        try {
            File file = new File(Excel_path);
            long lastModifiedTime = file.lastModified();
            //如果修改了，则重新创建对象
            if (map.containsKey(lastModifiedTime)) {
                instance = map.get(lastModifiedTime);
            } else {
                instance = new CustomerManagerBeanHelper();
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
    private CustomerManagerBeanHelper() throws WTException {
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

            CustomerManagerBeanReader reader = new CustomerManagerBeanReader(fis);

            Collection<Integer> indexs = new ArrayList<>();
            indexs.add(0);
            reader.setSheetIndexs(indexs);//从第一页读取

            reader.setStartRowIndex(1); //从第二行开始
            reader.read();
            List<CustomerManagerBean> customerManagerBeans = reader.getBeanList();//获取站点集合

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("subwayStationBeans =" + customerManagerBeans);
            }
            parseCustomerManagerBeans(customerManagerBeans);
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
     * 将客户和经理集合解析为map
     *
     * @param customerManagerBeans
     * @return
     */
    private void parseCustomerManagerBeans(List<CustomerManagerBean> customerManagerBeans) {
        try {

            for (CustomerManagerBean customerManagerBean : customerManagerBeans) {
                customerManagerMap.put(customerManagerBean.getCustomerName(), customerManagerBean.getBelongManager());
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>customerManagerMap = " + customerManagerMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有客户
     *
     * @return
     */
    public List<String> getCustomerList() {
        List<String> list = new ArrayList<>();
        if (customerManagerMap != null) {
            list.addAll(customerManagerMap.keySet());
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>> CustomerList = " + list);
        }
        return list;
    }

    /**
     * 根据客户获取所属经理
     *
     * @param customerName
     * @return
     */
    public String getBelongManager(String customerName) {
        return customerManagerMap.get(customerName);
    }
}
