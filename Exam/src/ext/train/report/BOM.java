package ext.train.report;

import ext.pi.core.PIPartHelper;
import ext.train.report.bean.SubPartBean;
import wt.fc.Persistable;
import wt.fc.QueryResult;
import wt.part.*;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author 段鑫扬
 */
public class BOM {
    /**
     * 遍历多层树bom
     *
     * @param parentPart
     * @return
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    private List<SubPartBean> getBOMParts(WTPart parentPart) throws WTException, WTPropertyVetoException {
        List<SubPartBean> resultList = new ArrayList<>();
        Queue<WTPart> queue = new LinkedList<>();
        queue.add(parentPart);
        while (!queue.isEmpty()) {
            WTPart currentPart = queue.poll(); // 当前要查询的部件
            /* resultList.add(new SubPartBean(currentPart));*/
            // 查询
            QueryResult qr = PIPartHelper.service.findChildrenAndLinks(currentPart);
            while (qr.hasMoreElements()) {
                Persistable[] persistables = (Persistable[]) qr.nextElement();
                // 获取到WTPartUsageLink对象
                WTPartUsageLink link = (WTPartUsageLink) persistables[0];
                Persistable childPersistable = persistables[1];
                WTPart child = null;
                if (childPersistable instanceof WTPart) {
                    child = (WTPart) childPersistable;
                    queue.add(child);
                }
            }
        }
        return resultList;
    }
}
