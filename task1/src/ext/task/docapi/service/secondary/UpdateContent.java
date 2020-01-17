package ext.task.docapi.service.secondary;

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
import java.util.List;

/**
 * @author 段鑫扬
 */
public class UpdateContent {

    /**
     * windchill TestDocAPI VR:wt.doc.WTDocument:101741  E:/download/actionmodels.dtd  rule.xml
     *
     * @param oid
     * @param newFilepath        服务器的文件路径
     * @param oldContentFileName
     * @return
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static WTDocument updateContent(String oid, String newFilepath, String oldContentFileName) throws WTException, PropertyVetoException, IOException {
        WTDocument doc = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTDocument) {
            doc = (WTDocument) refObject;

            FileInputStream inputStream = null;
            Transaction tx = null;
            boolean canCommit = false;
            try {
                if (!PersistentObjectManager.getPom().isTransactionActive()) {
                    //开启事务
                    tx = new Transaction();
                    tx.start();
                    canCommit = true;
                }
                List<ContentItem> contents = FindContents.findContents(oid);
                if (contents == null) {
                    return null;
                }

                for (ContentItem contentItem : contents) {
                    if (contentItem instanceof ApplicationData) {
                        ApplicationData applicationData = (ApplicationData) contentItem;
                        if (applicationData.getFileName().equals(oldContentFileName)) {
                            ContentServerHelper.service.deleteContent(doc, applicationData);

                            File newFile = new File(newFilepath);
                            ApplicationData newContent = ApplicationData.newApplicationData(doc);
                            newContent.setFileName(newFile.getName());
                            newContent.setFileSize(newFile.length());

                            inputStream = new FileInputStream(newFile);
                            ContentServerHelper.service.updateContent(doc, newContent, inputStream);
                            break;
                        }
                    }
                }

                if (canCommit) {
                    tx.commit();
                    tx = null;
                }
                doc = (WTDocument) PersistenceHelper.manager.refresh(doc, true, true);
            } finally {
                if (canCommit) {
                    if (tx != null) {
                        tx.rollback();
                        tx = null;
                    }
                }
            }
        }
        return doc;
    }
}
