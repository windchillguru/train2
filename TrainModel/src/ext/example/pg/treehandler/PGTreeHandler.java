package ext.example.pg.treehandler;

import com.ptc.core.components.beans.TreeHandlerAdapter;
import ext.example.pg.model.PGGroup;
import ext.example.pg.model.PGMemberLink;
import ext.example.pg.service.PGInfoHelper;
import wt.fc.ObjectReference;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * pg树的处理类
 *
 * @author 段鑫扬
 * @version 2019/12/22
 */
public class PGTreeHandler extends TreeHandlerAdapter {

    /**
     * 获取子节点
     *
     * @param parents
     * @return
     * @throws WTException
     */
    @Override
    public Map<Object, List> getNodes(List parents) throws WTException {
        Map<Object, List> map = new HashMap<>();
        for (Object object : parents) {
            //成员(子节点)
            List<Object> memberList = new ArrayList<>();

            if (object instanceof ObjectReference) {
                object = ((ObjectReference) object).getObject();
            }
            if (object instanceof PGGroup) {
                //组
                PGGroup pgGroup = (PGGroup) object;
                //查询的link关系
                QueryResult memberResult = PGInfoHelper.service.findMembers(pgGroup, false);
                memberList.addAll(memberResult.getObjectVectorIfc().getVector());

            } else if (object instanceof PGMemberLink) {
                WTObject memberObj = ((PGMemberLink) object).getPgMember();
                if (memberObj instanceof PGGroup) {
                    PGGroup pgGroup = (PGGroup) memberObj;
                    //查询link关系
                    QueryResult memberResult = PGInfoHelper.service.findMembers(pgGroup, false);
                    memberList.addAll(memberResult.getObjectVectorIfc().getVector());
                }
            }
            map.put(object, memberList);
        }
        return map;
    }


    /**
     * 获取根节点数据
     *
     * @return
     * @throws WTException
     */
    @Override
    public List<Object> getRootNodes() throws WTException {
        List<Object> objectList = new ArrayList<>();
        QuerySpec qs = new QuerySpec(PGGroup.class);
        //查询根组
        SearchCondition sc = new SearchCondition(PGGroup.class, PGGroup.ROOT, SearchCondition.IS_TRUE);
        qs.appendWhere(sc, new int[]{0});
        QueryResult qr = PersistenceHelper.manager.find(qs);
        while (qr.hasMoreElements()) {
            PGGroup pgGroup = (PGGroup) qr.nextElement();
            objectList.add(pgGroup);
        }
        return objectList;
    }
}
