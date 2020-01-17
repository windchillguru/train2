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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author 段鑫扬
 */
public class DeleteContent {

    /**
     * VR%3Awt.doc.WTDocument%3A105694
     * windchill TestDocAPI VR:wt.doc.WTDocument:105694  rule.xml
     *
     * @param oid
     * @return
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static WTDocument deleteContent(String oid, String deleteContent) throws WTException, PropertyVetoException, IOException {
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
                        if (applicationData.getFileName().equals(deleteContent)) {
                            //删除
                            ContentServerHelper.service.deleteContent(doc, applicationData);
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
