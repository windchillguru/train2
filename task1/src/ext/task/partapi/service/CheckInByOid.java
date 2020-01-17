package ext.task.partapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.part.WTPart;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.wip.WorkInProgressHelper;

/**
 * 部件api测试
 *
 * @author 段鑫扬
 */
public class CheckInByOid {

    /**
     * 检入
     *
     * @param oid
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static WTPart checkOutByOid(String oid) throws WTException, WTPropertyVetoException {
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            WTPart wtPart = (WTPart) refObject;
            //判断部件是否被检出
            if (WorkInProgressHelper.isCheckedOut(wtPart)) {
                //已检出
                //判断部件是否被当前用户检出
                if (WorkInProgressHelper.isCheckedOut(wtPart, SessionHelper.getPrincipal())) {
                    //工作副本
                    WTPart wtPartCopy = null;
                    //判断是否为检出后的工作副本
                    if (WorkInProgressHelper.isWorkingCopy(wtPart)) {
                        wtPartCopy = wtPart;
                        ;
                    } else {
                        wtPartCopy = (WTPart) WorkInProgressHelper.service.workingCopyOf(wtPart);
                    }
                    if (wtPartCopy != null) {
                        //检入操作
                        wtPart = (WTPart) WorkInProgressHelper.service.checkin(wtPartCopy, "代码检入测试");
                        return wtPart;
                    }
                } else {
                    //不是当前用户检出，直接返回
                    return wtPart;
                }
            } else {
                //没检出直接返回
                return wtPart;
            }
        }
        return null;
    }
}
