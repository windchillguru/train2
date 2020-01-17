package ext.task.docapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.content.ApplicationData;
import wt.content.ContentItem;
import wt.content.ContentServerHelper;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.pom.PersistentObjectManager;
import wt.pom.Transaction;
import wt.util.WTException;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author 段鑫扬
 */
public class UpdatePrimary {

    private static final int ONE_KB = 1024;

    /**
     * windchill TestDocAPI VR:wt.doc.WTDocument:101741  E:/download/003_Windchill基础知识02.pptx
     *
     * @param oid
     * @param filepath 服务器的文件路径
     * @return
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static WTDocument updatePrimary(String oid, String filepath) throws WTException, PropertyVetoException, IOException {
        WTDocument doc = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTDocument) {
            doc = (WTDocument) refObject;

            FileInputStream inputStream = null;
            Transaction tx = null;
            try {
                if (!PersistentObjectManager.getPom().isTransactionActive()) {
                    //开启事务
                    tx = new Transaction();
                    tx.start();
                }
                //获取主数据
                ContentItem primary = FindPrimary.findPrimary(oid);
                if (primary != null) {
                    //删除原来的主数据
                    ContentServerHelper.service.deleteContent(doc, primary);
                }
                File newFile = new File(filepath);
                ApplicationData newApplicationData = ApplicationData.newApplicationData(doc);
                newApplicationData.setFileName(newFile.getName());
                newApplicationData.setFileSize(newFile.length());

                inputStream = new FileInputStream(newFile);
                //更新主内容
                ContentServerHelper.service.updatePrimary(doc, newApplicationData, inputStream);

                tx.commit();
                tx = null;
                doc = (WTDocument) PersistenceHelper.manager.refresh(doc, true, true);
                System.out.println("跟新后的 doc name = " + doc.getName());
            } finally {
                if (tx != null) {
                    tx.rollback();
                    tx = null;
                }
                inputStream.close();
            }
        }
        return doc;
    }
}
