package ext.task2.part.helper;

import ext.pi.PIException;
import ext.pi.core.PIPartHelper;
import wt.fc.Persistable;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.part.WTPartUsageLink;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 段鑫扬
 * @version 2020/1/8
 * 虚拟BOM帮助类
 */
public class VirtualBOMHelper {

    //虚拟部件bom，以子件为key,link关系为value
    private Map<WTPart, WTPartUsageLink> bom = null;
    private String viewName = "Design";

    public VirtualBOMHelper(String virtualPartTopNum) throws PIException {
        this.bom = new HashMap<>();
        WTPart parentPart = PIPartHelper.service.findWTPart(virtualPartTopNum, viewName);
        traverse(parentPart);
    }

    /**
     * 遍历虚拟部件树，存储到map中
     */
    private void traverse(WTPart parentPart) throws PIException {
        if (parentPart == null) {
            return;
        }
        QueryResult childrenAndLinks = PIPartHelper.service.findChildrenAndLinks(parentPart);
        if (childrenAndLinks.size() == 0) {
            return;
        }
        while (childrenAndLinks.hasMoreElements()) {
            Object nextElement = childrenAndLinks.nextElement();
            if (nextElement instanceof Persistable[]) {
                Persistable[] persistableArray = (Persistable[]) nextElement;
                WTPartUsageLink link = (WTPartUsageLink) persistableArray[0];
                WTPart childPart = (WTPart) persistableArray[1];
                bom.put(childPart, link);
                traverse(childPart);
            }
        }
    }


}
