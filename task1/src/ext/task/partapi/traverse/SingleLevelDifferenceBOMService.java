package ext.task.partapi.traverse;

import wt.method.RemoteAccess;
import wt.part.WTPart;
import wt.part.WTPartUsageLink;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import java.io.Serializable;
import java.util.*;

/**
 * 比较
 *
 * @author 段鑫扬
 */
public class SingleLevelDifferenceBOMService implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;

    private WTPart newPart = null;
    private WTPart oldPart = null;

    private List<WTPartUsageLink> createLinks = null;
    private List<WTPartUsageLink> updateLinks = null;
    private List<WTPartUsageLink> deleteLinks = null;

    public SingleLevelDifferenceBOMService() {
        this.createLinks = new ArrayList<>();
        this.updateLinks = new ArrayList<>();
        this.deleteLinks = new ArrayList<>();
    }

    public SingleLevelDifferenceBOMService(WTPart newPart, WTPart oldPart) {
        this();
        this.newPart = newPart;
        this.oldPart = oldPart;
    }

    public void compare() throws WTException, WTPropertyVetoException {
        clearHistoryData();
        SingleBomTraverse newPartTraverse = new SingleBomTraverse(this.newPart);
        newPartTraverse.traverse();
        Map<Long, WTPartUsageLink> newMap = newPartTraverse.getLinkList();
        Map<String, WTPartUsageLink> newComponentIdMap = toComponentIdMap(newMap.values());

        SingleBomTraverse oldPartTraverse = new SingleBomTraverse(this.oldPart);
        oldPartTraverse.traverse();
        Map<Long, WTPartUsageLink> oldMap = oldPartTraverse.getLinkList();
        Map<String, WTPartUsageLink> oldComponentIdMap = toComponentIdMap(oldMap.values());

        //遍历比较
        Iterator<String> newKeyIte = newComponentIdMap.keySet().iterator();
        while (newKeyIte.hasNext()) {
            String componentId = newKeyIte.next();
            if (oldComponentIdMap.containsKey(componentId)) {
                this.updateLinks.add(newComponentIdMap.get(componentId));
            } else {
                this.createLinks.add(newComponentIdMap.get(componentId));
            }
        }

        Iterator<String> oldKeyIte = oldComponentIdMap.keySet().iterator();
        while (oldKeyIte.hasNext()) {
            String componentId = oldKeyIte.next();
            if (!newComponentIdMap.containsKey(componentId)) {
                this.deleteLinks.add(oldComponentIdMap.get(componentId));
            }
        }
    }

    private Map<String, WTPartUsageLink> toComponentIdMap(Collection<WTPartUsageLink> usageLinks) {
        Map<String, WTPartUsageLink> linkMap = new LinkedHashMap<>();
        if (usageLinks != null) {
            for (WTPartUsageLink link : usageLinks) {
                if (link != null) {
                    linkMap.put(link.getComponentId(), link);
                }
            }
        }
        return linkMap;
    }

    /**
     * 清除历史记录
     */
    private void clearHistoryData() {
        this.createLinks.clear();
        this.updateLinks.clear();
        this.deleteLinks.clear();
    }

    public WTPart getNewPart() {
        return newPart;
    }

    public void setNewPart(WTPart newPart) {
        this.newPart = newPart;
    }

    public WTPart getOldPart() {
        return oldPart;
    }

    public void setOldPart(WTPart oldPart) {
        this.oldPart = oldPart;
    }

    public List<WTPartUsageLink> getCreateLinks() {
        return createLinks;
    }

    public void setCreateLinks(List<WTPartUsageLink> createLinks) {
        this.createLinks = createLinks;
    }

    public List<WTPartUsageLink> getUpdateLinks() {
        return updateLinks;
    }

    public void setUpdateLinks(List<WTPartUsageLink> updateLinks) {
        this.updateLinks = updateLinks;
    }

    public List<WTPartUsageLink> getDeleteLinks() {
        return deleteLinks;
    }

    public void setDeleteLinks(List<WTPartUsageLink> deleteLinks) {
        this.deleteLinks = deleteLinks;
    }
}
