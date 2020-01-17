package ext.task.partapi.service;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPartMaster;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;

/**
 * 测试部件api
 *
 * @author 段鑫扬
 */
public class FindPartMasterByNum {
    /**
     * 通过编号查询 部件主数据
     *
     * @param num
     * @return
     * @throws WTException
     */
    public static WTPartMaster findPartMasterByNum(String num) throws WTException {
        WTPartMaster wtPartMaster = null;
        QuerySpec qs = new QuerySpec(WTPartMaster.class);
        //设置条件
        SearchCondition searchCondition = new SearchCondition(WTPartMaster.class, WTPartMaster.NUMBER, SearchCondition.EQUAL, num);
        qs.appendWhere(searchCondition, new int[]{0});
        QueryResult queryResult = PersistenceHelper.manager.find(qs);
        if (queryResult.hasMoreElements()) {
            wtPartMaster = (WTPartMaster) queryResult.nextElement();
        }
        return wtPartMaster;
    }
}
