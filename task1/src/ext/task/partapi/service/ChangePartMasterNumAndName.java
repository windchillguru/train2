package ext.task.partapi.service;

import wt.fc.IdentityHelper;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPartMaster;
import wt.part.WTPartMasterIdentity;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * 测试部件api
 *
 * @author 段鑫扬
 */
public class ChangePartMasterNumAndName {
    /**
     * 修改部件编号和名称
     */
    public static WTPartMaster changePartMasterNumAndName() throws WTException, WTPropertyVetoException {
        WTPartMaster wtPartMaster = null;
        QuerySpec qs = new QuerySpec(WTPartMaster.class);
        //设置条件
        SearchCondition searchCondition = new SearchCondition(WTPartMaster.class, WTPartMaster.NUMBER, SearchCondition.EQUAL, "CHANGE 0000000005");
        qs.appendWhere(searchCondition, new int[]{0});
        QueryResult queryResult = PersistenceHelper.manager.find(qs);
        if (queryResult.hasMoreElements()) {
            wtPartMaster = (WTPartMaster) queryResult.nextElement();
            WTPartMasterIdentity masterIdentity = (WTPartMasterIdentity) wtPartMaster.getIdentificationObject();
            masterIdentity.setNumber("change 0000000005");
            masterIdentity.setName("TestImg Change Part Name");
            //修改编号和名称
            IdentityHelper.service.changeIdentity(wtPartMaster, masterIdentity);
        }
        return wtPartMaster;
    }
}
