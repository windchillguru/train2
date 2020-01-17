package ext.orv.attrlinkage.reader;

import ext.lang.PIFileUtils;
import ext.lang.PIStringUtils;
import ext.orv.attrlinkage.bean.ProductSESeriesGPBean;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.util.WTException;
import wt.util.WTProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * 产品系列，产品型号的excel 解析helper
 *
 * @author 段鑫扬
 */
public class ProductSESeriesGPBeanHelper {

    private static final String CLASSNAME = ProductSESeriesGPBeanHelper.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final long serialVersionUID = 1L;
    private static String Excel_path = "";
    private static String ExcelSub_path = "ext/orv/conf/productSE.xlsx";

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

    //存储产品系列，按顺序存储
    private List<String> productSEList = new ArrayList<>();
    //存储产品系列和产品型号的关系
    private Map<String, List<String>> productSESeriesGPMap = new HashMap<String, List<String>>();
    //存储产品系列，产品型号和它们的说明的关系
    private Map<String, String> descMap = new HashMap<String, String>();

    //存储修改时间
    private static Map<Long, ProductSESeriesGPBeanHelper> map = new TreeMap<>();

    /**
     * 获取实例
     *
     * @return
     * @throws WTException
     */
    public static ProductSESeriesGPBeanHelper getInstance() throws WTException {
        ProductSESeriesGPBeanHelper instance = null;
        try {
            File file = new File(Excel_path);
            long lastModifiedTime = file.lastModified();
            //如果修改了，则重新创建对象
            if (map.containsKey(lastModifiedTime)) {
                instance = map.get(lastModifiedTime);
            } else {
                instance = new ProductSESeriesGPBeanHelper();
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
    private ProductSESeriesGPBeanHelper() throws WTException {
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

            ProductSESeriesGPBeanReader reader = new ProductSESeriesGPBeanReader(fis);

            Collection<Integer> indexs = new ArrayList<>();
            indexs.add(0);
            reader.setSheetIndexs(indexs);//从第一页读取

            reader.setStartRowIndex(1); //从第二行开始
            reader.read();
            List<ProductSESeriesGPBean> beanList = reader.getBeanList();//获取ProductSESeriesGPBean的集合

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("beanList =" + beanList);
            }
            parseProductSESeriesGPBeans(beanList);
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
     * 将产品系列，产品型号的集合解析为map
     *
     * @param beanList
     * @return
     */
    private void parseProductSESeriesGPBeans(List<ProductSESeriesGPBean> beanList) {
        try {
            for (ProductSESeriesGPBean bean : beanList) {
                if (bean == null) {
                    continue;
                }
                String productSE = bean.getProductSE();
                String seriesGP = bean.getSeriesGP();
                //存储产品系列/产品型号和它们的说明
                if (PIStringUtils.hasText(productSE) && PIStringUtils.hasText(seriesGP)) {
                    if (!this.descMap.containsKey(productSE)) {
                        //当前产品系列还没有存储
                        //按顺序存储产品系列
                        productSEList.add(productSE);
                        descMap.put(productSE, bean.getProductSEDesc());
                    }
                    if (!this.descMap.containsKey(seriesGP)) {
                        //当前产品型号还没有存储
                        descMap.put(seriesGP, bean.getSeriesGPDesc());
                    }
                }
                //存储 产品系列，产品型号的关系
                if (PIStringUtils.hasText(productSE)) {
                    if (this.productSESeriesGPMap.containsKey(productSE)) {
                        //如果当前产品系列已经存储，则在集合中添加产品型号
                        this.productSESeriesGPMap.get(productSE).add(seriesGP);
                    } else {
                        List<String> list = new ArrayList<String>();
                        list.add(seriesGP);
                        //存储新的产品系列
                        this.productSESeriesGPMap.put(productSE, list);
                    }
                }
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>productSESeriesGPMap = " + productSESeriesGPMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据产品系列获取产品型号 (内部值)
     *
     * @param productSE
     * @return
     */
    public List<String> getSeriesGPListByproductSE(String productSE) {
        List<String> list = null;
        try {
            list = new ArrayList<>();
            if (productSESeriesGPMap != null && productSESeriesGPMap.containsKey(productSE)) {
                list.addAll(productSESeriesGPMap.get(productSE));
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
     * 根据产品系列/产品型号 获取它们的说明（显示值）
     *
     * @param internal 产品系列/产品型号(内部值）
     * @return
     */
    public String getDescByInternal(String internal) throws WTException {
        String desc = "";
        try {
            if (descMap != null && descMap.containsKey(internal)) {
                desc = descMap.get(internal);
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>internal =" + internal + " desc =" + desc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
        return desc;
    }

    /**
     * 根据产品系列/产品型号 获取它们的说明（显示值） 批量处理
     *
     * @param internals 产品系列/产品型号(内部值）
     * @return
     */
    public List<String> getDescsByInternals(List<String> internals) throws WTException {
        List<String> descs = null;
        try {
            descs = new ArrayList<>();
            for (String internal : internals) {
                if (descMap != null && descMap.containsKey(internal)) {
                    descs.add(descMap.get(internal));
                }
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>descs =" + descs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
        return descs;
    }

    /**
     * 获取所有产品系列
     *
     * @return
     */
    public List<String> getProductSEList() {
        List<String> list = new ArrayList<>();
        if (productSEList != null) {
            list.addAll(productSEList);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>> subwayList = " + list);
        }
        return list;
    }
}
