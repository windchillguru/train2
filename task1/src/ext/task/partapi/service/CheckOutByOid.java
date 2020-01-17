package ext.task.partapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.folder.Folder;
import wt.part.WTPart;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.wip.CheckoutLink;
import wt.vc.wip.WorkInProgressHelper;

/**
 * 部件api测试
 *
 * @author 段鑫扬
 */
public class CheckOutByOid {

    /**
     * 检出
     *
     * @param oid
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static void checkOutByOid(String oid) throws WTException, WTPropertyVetoException {
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            WTPart wtPart = (WTPart) refObject;
            //判断部件是否被检出
            if (WorkInProgressHelper.isCheckedOut(wtPart)) {
                //已检出
                //判断部件是否被当前用户检出
                if (WorkInProgressHelper.isCheckedOut(wtPart, SessionHelper.getPrincipal())) {
                    //判断是否为检出后的工作副本
                    if (WorkInProgressHelper.isWorkingCopy(wtPart)) {
                        return;
                    }
                }
            } else {
                //没检出
                //获取检出文件夹
                Folder checkoutFolder = WorkInProgressHelper.service.getCheckoutFolder();
                //检出操作
                CheckoutLink checkoutLink = WorkInProgressHelper.service.checkout(wtPart, checkoutFolder, null);

                //获取检出后的工作副本
                //Workable workingCopy = checkoutLink.getWorkingCopy();
            }

        }

    }
}
