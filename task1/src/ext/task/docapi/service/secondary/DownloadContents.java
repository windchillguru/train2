package ext.task.docapi.service.secondary;

import com.ptc.netmarkets.model.NmOid;
import wt.content.ApplicationData;
import wt.content.ContentItem;
import wt.content.ContentServerHelper;
import wt.doc.WTDocument;
import wt.identity.IdentityFactory;
import wt.util.WTException;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author 段鑫扬
 */
public class DownloadContents {

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
    public static File downloadContents(String oid, String directory) throws WTException, PropertyVetoException, IOException {
        WTDocument doc = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTDocument) {
            doc = (WTDocument) refObject;
        }
        if (doc == null) {
            return null;
        }
        //压缩包文件名
        String zipFileParth = directory + IdentityFactory.getDisplayIdentifier(doc) + ".zip";
        File zipFile = new File(zipFileParth);
        ZipOutputStream zipOut = null;
        try {
            zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            List<ContentItem> contents = FindContents.findContents(oid);
            if (contents == null) {
                return null;
            }

            for (ContentItem content : contents) {
                InputStream inputStream = null;
                try {
                    if (content != null && content instanceof ApplicationData) {
                        ApplicationData applicationData = (ApplicationData) content;

                        //压缩包的文件添加
                        ZipEntry zipEntry = new ZipEntry(applicationData.getFileName());
                        zipOut.putNextEntry(zipEntry);

                        inputStream = ContentServerHelper.service.findContentStream(applicationData);

                        byte[] buf = new byte[ONE_KB];
                        int readBytes = 0;
                        while ((readBytes = inputStream.read(buf, 0, ONE_KB)) != -1) {
                            zipOut.write(buf, 0, readBytes);
                        }
                    }
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            }
            //输出压缩包
            zipOut.finish();
        } finally {
            if (zipOut != null) {
                zipOut.close();
            }
        }
        return zipFile;
    }
}
