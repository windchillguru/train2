package ext.task.partapi.service;

import wt.configuration.TraceCode;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.QuantityUnit;
import wt.part.WTPartMaster;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * 测试部件api
 *
 * @author 段鑫扬
 */
public class ChangePartMasterAttr {
    /**
     * 修改部件的属性
     */
    public static WTPartMaster changePartMasterAttr() throws WTException, WTPropertyVetoException {
        WTPartMaster wtPartMaster = null;
        QuerySpec qs = new QuerySpec(WTPartMaster.class);
        //设置条件
        SearchCondition searchCondition = new SearchCondition(WTPartMaster.class, WTPartMaster.NUMBER, SearchCondition.EQUAL, "0000000003");
        qs.appendWhere(searchCondition, new int[]{0});
        QueryResult queryResult = PersistenceHelper.manager.find(qs);
        if (queryResult.hasMoreElements()) {
            wtPartMaster = (WTPartMaster) queryResult.nextElement();
            //修改部件的属性
            //设置单位
            wtPartMaster.setDefaultUnit(QuantityUnit.L);
            //设置跟踪代码
            wtPartMaster.setDefaultTraceCode(TraceCode.LOT_NUMBER);
            //修改
            PersistenceHelper.manager.save(wtPartMaster);
        }
        return wtPartMaster;
    }
}
