package ext.example.part.mvc.handler;

import com.ptc.core.components.beans.TreeHandlerAdapter;
import wt.fc.ObjectVector;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTArrayList;
import wt.part.WTPart;
import wt.part.WTPartHelper;
import wt.query.QuerySpec;
import wt.util.WTException;
import wt.vc.config.ConfigHelper;
import wt.vc.config.ConfigSpec;
import wt.vc.config.LatestConfigSpec;

import java.util.*;

/**
 * 树数据处理类
 *
 * @author 段鑫扬
 */
public class MyPartTreeHandler extends TreeHandlerAdapter {

    private ConfigSpec configSpec = new LatestConfigSpec();
    /**
     * 获取子节点
     * @param parents
     * @return
     * @throws WTException
     */
    @Override
    public Map<Object, List> getNodes(List parents) throws WTException {
        Map<Object, List> result = new HashMap<Object, List>();
        Persistable[][][] all_children = WTPartHelper.service.getUsesWTParts(new WTArrayList(parents), configSpec);
        ListIterator i = parents.listIterator();
        while (i.hasNext()) {
            WTPart parent = (WTPart) i.next();
            Persistable[][] branch = all_children[i.previousIndex()];
            if (branch == null) {
                continue;
            }
            List children = new ArrayList(branch.length);
            for (Persistable[] persistables : branch) {
                /*
                persistables[0]  [1]  主数据和link
                 */
                children.add(persistables[1]);
            }
            result.put(parent,children);
        }
        return result;
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
        QuerySpec qs = new QuerySpec(WTPart.class);
        try {
            ObjectVector objectVector = new ObjectVector();
            //查询结果
            QueryResult queryResult = PersistenceHelper.manager.find(qs);
            while (queryResult.hasMoreElements()) {
                WTPart wtPart = (WTPart) queryResult.nextElement();
                //将结果添加到vector中
                objectVector.addElement(wtPart.getMaster());
            }
            QueryResult masterQR = new QueryResult();
            masterQR.appendObjectVector(objectVector);
            //过滤，按configSpec过滤，过滤版本
            QueryResult qr = ConfigHelper.service.filteredIterationsOf(masterQR, configSpec);
            while (qr.hasMoreElements()) {
                WTPart wtPart = (WTPart) qr.nextElement();
                objectList.add(wtPart);
            }
        } catch (WTException e) {
            e.printStackTrace();
        }
        return objectList;
    }
}
