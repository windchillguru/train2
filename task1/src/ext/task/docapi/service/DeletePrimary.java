package ext.task.docapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.content.ContentItem;
import wt.content.ContentServerHelper;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.pom.PersistentObjectManager;
import wt.pom.Transaction;
import wt.util.WTException;

import java.beans.PropertyVetoException;
import java.io.IOException;

/**
 * @author 段鑫扬
 */
public class DeletePrimary {

    private static final int ONE_KB = 1024;

    /**
     * windchill TestDocAPI VR:wt.doc.WTDocument:101741
     *
     * @param oid
     * @return
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static WTDocument deletePrimary(String oid) throws WTException, PropertyVetoException, IOException {
        WTDocument doc = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTDocument) {
            doc = (WTDocument) refObject;

            Transaction tx = null;
            boolean canCommit = false;
            try {
                if (!PersistentObjectManager.getPom().isTransactionActive()) {
                    //开启事务
                    tx = new Transaction();
                    tx.start();
                    canCommit = true;
                }
                //获取主数据
                ContentItem primary = FindPrimary.findPrimary(oid);
                if (primary != null) {
                    //删除原来的主数据
                    ContentServerHelper.service.deleteContent(doc, primary);
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
