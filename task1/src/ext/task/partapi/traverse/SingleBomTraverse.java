package ext.task.partapi.traverse;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.part.*;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.views.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 遍历单层bom
 *
 * @author 段鑫扬
 */
public class SingleBomTraverse implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = SingleBomTraverse.class.getName();

    //Bom根节点，相对的
    private WTPart rootPart = null;
    //遍历bom ,所有的子件清单，不包含重复编码， key 为ObjectIdentifier id ,value 为WTPart
    private Map<Long, WTPart> childList = null;
    //遍历bom ,所有的link清单， key 为ObjectIdentifier id ,value WTPartUsageLink
    private Map<Long, WTPartUsageLink> linkList = null;

    public SingleBomTraverse() {
        this.childList = new LinkedHashMap<Long, WTPart>();
        this.linkList = new LinkedHashMap<Long, WTPartUsageLink>();
    }

    public SingleBomTraverse(WTPart rootPart) {
        this();
        this.rootPart = rootPart;
    }

    public void traverse() throws WTException, WTPropertyVetoException {
        //清除历史缓存数据
        clearHistoryData();
        QueryResult qr = firstLevelBOM(this.rootPart);
        processData(this.rootPart, qr);
    }

    /**
     * 解析数据
     *
     * @param parent
     * @param qr
     */
    private List<WTPart> processData(WTPart parent, QueryResult qr) {
        List<WTPart> children = new ArrayList<WTPart>();

        if (qr != null) {
            while (qr.hasMoreElements()) {
                Persistable[] persistables = (Persistable[]) qr.nextElement();
                //一号元素为link对象
                WTPartUsageLink link = (WTPartUsageLink) persistables[0];
                //二号元素为持久化对象
                Persistable childPersistable = persistables[1];
                WTPart child = null;
                if (childPersistable instanceof WTPart) {
                    child = (WTPart) childPersistable;
                }
                addData(link, child);
                children.add(child);
            }
        }
        return children;
    }

    /**
     * 存储数据
     *
     * @param link
     * @param child
     */
    private void addData(WTPartUsageLink link, WTPart child) {
        Long linkId = (link == null ? null : PersistenceHelper.getObjectIdentifier(link).getId());
        Long childId = (child == null ? null : PersistenceHelper.getObjectIdentifier(child).getId());

        if (linkId != null) {
            this.linkList.put(linkId, link);
        }

        if (childId != null) {
            this.childList.put(childId, child);
        }
    }

    /**
     * 查询第一层Bom
     *
     * @param parent
     * @return
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    private QueryResult firstLevelBOM(WTPart parent) throws WTException, WTPropertyVetoException {
        if (parent == null) {
            return null;
        }
        //获取视图
        View view = (View) parent.getView().getObject();

        WTPartConfigSpec config = WTPartHelper.service.findWTPartConfigSpec();
        WTPartStandardConfigSpec standardConfig = config.getStandard();
        //获取视图的配置规范
        standardConfig.setView(view);

        //开始查询
        QueryResult qr = WTPartHelper.service.getUsesWTParts(parent, standardConfig);
        return qr;
    }


    /**
     * 清除历史缓存数据
     */
    private void clearHistoryData() {
        this.childList.clear();
        this.linkList.clear();
    }

    public WTPart getRootPart() {
        return rootPart;
    }

    public void setRootPart(WTPart rootPart) {
        this.rootPart = rootPart;
    }

    public Map<Long, WTPart> getChildList() {
        return childList;
    }

    public void setChildList(Map<Long, WTPart> childList) {
        this.childList = childList;
    }

    public Map<Long, WTPartUsageLink> getLinkList() {
        return linkList;
    }

    public void setLinkList(Map<Long, WTPartUsageLink> linkList) {
        this.linkList = linkList;
    }
}
