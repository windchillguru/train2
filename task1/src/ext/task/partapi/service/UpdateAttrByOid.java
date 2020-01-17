package ext.task.partapi.service;

import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.meta.common.UpdateOperationIdentifier;
import wt.fc.PersistenceHelper;
import wt.part.Source;
import wt.part.WTPart;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * 部件api测试
 *
 * @author 段鑫扬
 */
public class UpdateAttrByOid {

    /**
     * 更新属性
     *
     * @param oid
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static void updateAttrByOid(String oid) throws WTException, WTPropertyVetoException {
        //获取工作副本
        WTPart wtPart = CheckOutByOid2.checkOutByOid(oid);

        PersistableAdapter adapter = new PersistableAdapter(wtPart, null, null, new UpdateOperationIdentifier());
        adapter.load("source");
        //修改来源
        adapter.set("source", Source.BUY);
        adapter.apply();
        //修改
        wtPart = (WTPart) PersistenceHelper.manager.save(wtPart);
    }
}

