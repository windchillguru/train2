package ext.task.partapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.enterprise.EnterpriseHelper;
import wt.part.WTPart;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * 部件api测试
 *
 * @author 段鑫扬
 */
public class SaveAs {

    /**
     * 获取指定版本
     * windchill TestPartAPI5 VR:wt.part.WTPart:104859
     *
     * @throws WTException
     */
    public static WTPart saveAs(String oid) throws WTException, WTPropertyVetoException {
        WTPart newPart = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            WTPart wtpart = (WTPart) refObject;
            //复制
            newPart = (WTPart) EnterpriseHelper.service.newCopy(wtpart);
            //设置必须属性
            newPart.setName("TestImg Save As API 0");
            newPart.setNumber("SAVE 000000092");
            newPart = (WTPart) EnterpriseHelper.service.saveCopy(wtpart, newPart);
        }
        return newPart;
    }
}
