package ext.task.docapi.service;

import wt.content.ApplicationData;
import wt.content.ContentItem;
import wt.content.ContentServerHelper;
import wt.util.WTException;

import java.beans.PropertyVetoException;
import java.io.*;

/**
 * @author 段鑫扬
 */
public class DownloadPrimary {

    private static final int ONE_KB = 1024;

    /**
     * windchill TestDocAPI VR:wt.doc.WTDocument:101741  E:/download/
     *
     * @param oid
     * @param directory 服务器的文件夹路径
     * @return
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static File downloadPrimary(String oid, String directory) throws WTException, PropertyVetoException, IOException {
        File file = null;
        //获取主数据
        ContentItem primary = FindPrimary.findPrimary(oid);
        if (primary != null && primary instanceof ApplicationData) {
            ApplicationData applicationData = (ApplicationData) primary;
            //io 下载
            file = new File(directory + applicationData.getFileName());
            InputStream inputStream = null;
            BufferedInputStream bufferedInputStream = null;
            FileOutputStream outputStream = null;

            try {
                inputStream = ContentServerHelper.service.findContentStream(applicationData);
                bufferedInputStream = new BufferedInputStream(inputStream);
                byte[] buf = new byte[ONE_KB];

                outputStream = new FileOutputStream(file);
                int readBytes = 0;
                while ((readBytes = bufferedInputStream.read(buf, 0, ONE_KB)) != -1) {
                    outputStream.write(buf, 0, readBytes);
                }

            } finally {
                outputStream.close();
                bufferedInputStream.close();
                inputStream.close();
            }
        }
        return file;
    }
}
