package ext.task.docapi.service.docusagelink;

import wt.doc.*;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 段鑫扬
 */
public class SingleDocUsageLinkService implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = SingleDocUsageLinkService.class.getName();

    private WTDocument rootDoc = null;

    //遍历bom ,所有的子件清单，不包含重复编码， key 为ObjectIdentifier id ,value WTDocument
    private Map<Long, WTDocument> childList = null;
    //遍历bom ,所有的link清单， key 为ObjectIdentifier id ,value WTDocumentUsageLink
    private Map<Long, WTDocumentUsageLink> linkList = null;

    public SingleDocUsageLinkService() {
        this.childList = new LinkedHashMap<Long, WTDocument>();
        this.linkList = new LinkedHashMap<Long, WTDocumentUsageLink>();
    }

    public SingleDocUsageLinkService(WTDocument rootDoc) {
        this();
        this.rootDoc = rootDoc;
    }

    public void traverse() throws WTException, WTPropertyVetoException {
        //清除历史缓存数据
        clearHistoryData();
        QueryResult qr = firstLevelBOM(this.rootDoc);
        processData(this.rootDoc, qr);
    }

    /**
     * 解析数据
     *
     * @param parent
     * @param qr
     */
    private List<WTDocument> processData(WTDocument parent, QueryResult qr) {
        List<WTDocument> children = new ArrayList<WTDocument>();
        if (qr != null) {
            while (qr.hasMoreElements()) {
                Persistable[] persistables = (Persistable[]) qr.nextElement();
                //一号元素为link对象
                WTDocumentUsageLink link = (WTDocumentUsageLink) persistables[0];
                //二号元素为持久化对象
                Persistable childPersistable = persistables[1];
                WTDocument child = null;
                if (childPersistable != null && childPersistable instanceof WTDocument) {
                    child = (WTDocument) childPersistable;
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
    private void addData(WTDocumentUsageLink link, WTDocument child) {
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
    private QueryResult firstLevelBOM(WTDocument parent) throws WTException, WTPropertyVetoException {
        if (parent == null) {
            return null;
        }
        WTDocumentStandardConfigSpec standardConfigSpec = WTDocumentStandardConfigSpec.newWTDocumentStandardConfigSpec();
        WTDocumentConfigSpec configSpec = WTDocumentConfigSpec.newWTDocumentConfigSpec(standardConfigSpec);

        //开始查询
        QueryResult qr = WTDocumentHelper.service.getUsesWTDocuments(parent, configSpec);
        return qr;
    }


    /**
     * 清除历史缓存数据
     */
    private void clearHistoryData() {
        this.childList.clear();
        this.linkList.clear();
    }

    public WTDocument getRootDoc() {
        return rootDoc;
    }

    public void setRootDoc(WTDocument rootDoc) {
        this.rootDoc = rootDoc;
    }

    public Map<Long, WTDocument> getChildList() {
        return childList;
    }

    public void setChildList(Map<Long, WTDocument> childList) {
        this.childList = childList;
    }

    public Map<Long, WTDocumentUsageLink> getLinkList() {
        return linkList;
    }

    public void setLinkList(Map<Long, WTDocumentUsageLink> linkList) {
        this.linkList = linkList;
    }
}
