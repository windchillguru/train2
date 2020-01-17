package ext.task.partapi.traverse;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.part.*;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.baseline.Baseline;
import wt.vc.views.View;
import wt.vc.views.ViewHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 遍历基线
 *
 * @author 段鑫扬
 */
public class BaseLineFirstTraverse implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = BaseLineFirstTraverse.class.getName();
    //设计视图
    public static final String D_VIEW = "Design";
    //制造视图
    public static final String M_VIEW = "Manufacturing";

    //Bom根节点，相对的
    private WTPart rootPart = null;
    //遍历bom ,所有的子件清单，不包含重复编码， key 为ObjectIdentifier id ,value 为WTPart
    private Map<Long, WTPart> childList = null;
    //遍历bom ,所有的link清单， key 为ObjectIdentifier id ,value WTPartUsageLink
    private Map<Long, WTPartUsageLink> linkList = null;

    private Baseline baseline = null;

    public BaseLineFirstTraverse() {
        this.childList = new LinkedHashMap<Long, WTPart>();
        this.linkList = new LinkedHashMap<Long, WTPartUsageLink>();
    }

    public BaseLineFirstTraverse(WTPart rootPart, Baseline baseline) {
        this();
        this.rootPart = rootPart;
        this.baseline = baseline;
    }

    public void traverse() throws WTException, WTPropertyVetoException {
        //清除历史缓存数据
        clearHistoryData();
        QueryResult qr = firstLevelBOM(this.rootPart);
        List<WTPart> children = processData(this.rootPart, qr);

        while (children != null && children.size() > 0) {
            List<WTPart> tempList = new ArrayList<WTPart>();
            for (WTPart child : children) {
                QueryResult tempQr = firstLevelBOM(child);
                List<WTPart> tempChildren = processData(child, tempQr);
                tempList.addAll(tempChildren);
            }
            children.clear();
            children = tempList;
        }

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
                if (childPersistable != null && childPersistable instanceof WTPart) {
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
     * 查询
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
        if (view == null) {
            view = ViewHelper.service.getView(D_VIEW);
        }

        //查询配置
        WTPartBaselineConfigSpec baselineConfig = WTPartBaselineConfigSpec.newWTPartBaselineConfigSpec(this.baseline);

        //开始查询
        QueryResult qr = WTPartHelper.service.getUsesWTParts(parent, baselineConfig);
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

    public Baseline getBaseline() {
        return baseline;
    }

    public void setBaseline(Baseline baseline) {
        this.baseline = baseline;
    }
}
