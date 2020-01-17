package ext.task2.folder.util;

import ext.lang.PIFileUtils;
import wt.util.WTProperties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author 段鑫扬
 * @version 2020/1/7
 */
public class FolderUtil {
    private static String SUBFOLDER_PATH = "ext\\task2\\config\\subfolder.properties";
    private static String key = "subFolder";

    /**
     * 获取配置文件的子文件夹名数据
     *
     * @return
     */
    public static String[] getSubFolder() throws IOException {
        String filePath = "";
        WTProperties wtProperties = WTProperties.getLocalProperties();
        String codebasePath = wtProperties.getProperty("wt.codebase.location");
        filePath = codebasePath + File.separator + SUBFOLDER_PATH;
        filePath = PIFileUtils.formatFilePath(filePath);
        Properties properties = new Properties();
        // 使用InPutStream流读取properties文件
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        properties.load(bufferedReader);
        // 获取key对应的value值
        String subFolderName = properties.getProperty(key);
        //子文件夹的name的数组
        String[] split = subFolderName.split(",");
        return split;
    }

}
