package ext.task.partapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.fc.PersistenceHelper;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.part.WTPart;
import wt.pom.PersistentObjectManager;
import wt.pom.Transaction;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.IterationIdentifier;
import wt.vc.VersionControlHelper;
import wt.vc.VersionIdentifier;
import wt.vc.Versioned;
import wt.vc.wip.WorkInProgressHelper;

/**
 * 部件api测试
 *
 * @author 段鑫扬
 */
public class ReviseByOid {

    /**
     * 修订
     *
     * @param oid
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static WTPart eviseByOid(String oid) throws WTException, WTPropertyVetoException {
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            WTPart wtPart = (WTPart) refObject;
            //判断部件是否被检出
            if (WorkInProgressHelper.isCheckedOut(wtPart)) {
                //已检出,则不能修订
                return wtPart;
            }
            Transaction tx = null;
            boolean canCommit = false;
            try {
                //没有可用的活动事务，则启用新事物
                if (!PersistentObjectManager.getPom().isTransactionActive()) {
                    tx = new Transaction();
                    tx.start();
                    canCommit = true;
                }
                //修订后的大版本
                VersionIdentifier nextVersionId = VersionControlHelper.nextVersionId(wtPart);
                //修订后的小版本
                IterationIdentifier nextIteration = VersionControlHelper.firstIterationId(wtPart);
                //新建版本对象
                Versioned newPart = VersionControlHelper.service.newVersion(wtPart, nextVersionId, nextIteration);
                //分配存储位置
                FolderHelper.assignLocation((FolderEntry) newPart, FolderHelper.getFolder(wtPart));

                wtPart = (WTPart) PersistenceHelper.manager.save(newPart);
                if (canCommit) {
                    tx.commit();
                    tx = null;
                }
                return wtPart;
            } finally {
                if (canCommit) {
                    if (tx != null) {
                        tx.rollback();
                        tx = null;
                    }
                }
            }
        }
        return null;
    }
}
