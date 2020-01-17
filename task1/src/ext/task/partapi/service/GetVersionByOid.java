package ext.task.partapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.part.WTPart;
import wt.util.WTException;
import wt.vc.IterationIdentifier;
import wt.vc.VersionIdentifier;

/**
 * 部件api测试
 *
 * @author 段鑫扬
 */
public class GetVersionByOid {

    /**
     * 获取部件版本
     *
     * @throws WTException
     */
    public static void getVersionByOid(String oid) throws WTException {
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            WTPart wtPart = (WTPart) refObject;
            //大版本
            VersionIdentifier versionIdentifier = wtPart.getVersionIdentifier();
            System.out.println("versionIdentifier  = " + versionIdentifier.getValue());
            //小版本
            IterationIdentifier iterationIdentifier = wtPart.getIterationIdentifier();
            System.out.println("iterationIdentifier  = " + iterationIdentifier.getValue());

        }

    }
}
