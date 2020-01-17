package ext.task.docapi.service.secondary;

import com.ptc.netmarkets.model.NmOid;
import wt.content.ApplicationData;
import wt.content.ContentItem;
import wt.content.ContentServerHelper;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.collections.WTHashSet;
import wt.fc.collections.WTSet;
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
public class ResetContent {

    /**
     * windchill TestDocAPI VR:wt.doc.WTDocument:105694 E:/download/actionmodels.dtd E:/download/003_Windchill基础知识02.pptx
     *
     * @param oid
     * @param filepaths
     * @return
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static WTDocument resetContent(String oid, List<String> filepaths) throws WTException, PropertyVetoException, IOException {
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

                WTSet wtSet = new WTHashSet();
                for (ContentItem contentItem : contents) {
                    if (contentItem instanceof ApplicationData) {
                        wtSet.add(contentItem);
                    }
                }
                ContentServerHelper.service.deleteContent(wtSet);

                if (filepaths == null) {
                    return null;
                }

                for (String filepath : filepaths) {
                    File file = new File(filepath);
                    ApplicationData newContent = ApplicationData.newApplicationData(doc);
                    newContent.setFileName(file.getName());
                    newContent.setFileSize(file.length());

                    inputStream = new FileInputStream(file);
                    ContentServerHelper.service.updateContent(doc, newContent, inputStream);
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
