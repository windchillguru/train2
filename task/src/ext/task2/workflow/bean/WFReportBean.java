package ext.task2.workflow.bean;

import com.ptc.windchill.enterprise.workflow.WfDataUtilitiesHelper;
import ext.generic.workflow.util.WorkflowHelper;
import ext.pi.core.PIPrincipalHelper;
import org.apache.log4j.Logger;
import wt.doc.WTDocument;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTSet;
import wt.log4j.LogR;
import wt.org.WTGroup;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTStandardDateFormat;
import wt.workflow.engine.WfProcess;
import wt.workflow.engine.WfVotingEventAudit;
import wt.workflow.work.WorkItem;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/**
 * @author 段鑫扬
 * @version 2020/1/15
 * 结案资料工作流报表的数据对象
 */
public class WFReportBean implements Serializable {
    private static final String CLASSNAME = WFReportBean.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final long serialVersionUID = 8035125081472649244L;

    //流程名称
    private String wfName;
    //技术产品组
    private String techProductGroup;
    //提出人
    private String creator;
    //提出时间
    private String createTime;
    //审核人
    private String auditor = "";
    //审核时间
    private String auditTime = "";
    //驳回原因
    private String rejectMsg = "";
    //启动的签审流程
    private WfProcess process;
    //签审流程中的所有审核节点 已完成的的 路由选项
    private QueryResult workItems;

    public WFReportBean(WfProcess process, QueryResult workItems) throws WTException {
        try {
            this.process = process;
            this.workItems = workItems;
            setProperty();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>bean =" + this.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.error(">>>> " + e);
            }
            throw new WTException(e);
        }
    }

    /**
     * 设置属性，根据流程和路由结果
     *
     * @throws WTException
     */
    private void setProperty() throws WTException {
        Locale locale = SessionHelper.manager.getLocale();
        // 获取pbo
        WTObject object = (WTObject) process.getContext().getValue("primaryBusinessObject");
        if (object instanceof WTDocument) {
            //（主题） 流程名称
            wfName = object.getDisplayIdentifier().getLocalizedMessage(locale);
        }
        //创建人
        WTPrincipalReference creator = process.getCreator();
        //获取父用户组
        WTSet parentWTGroups = PIPrincipalHelper.service.findParentWTGroups(creator.getPrincipal());
        WTGroup wtGroup = null;
        Collection collection = parentWTGroups.persistableCollection();
        for (Object parentWTGroup : collection) {
            if (parentWTGroup instanceof WTGroup) {
                wtGroup = (WTGroup) parentWTGroup;
                break;
            }
        }
        if (wtGroup != null) {
            this.techProductGroup = wtGroup.getDisplayIdentifier().getLocalizedMessage(locale);
        }
        this.creator = creator.getDisplayName();
        //创建时间戳
        Timestamp createTimestamp = process.getCreateTimestamp();
        this.createTime = WTStandardDateFormat.format(new Date(createTimestamp.getTime())
                , WTStandardDateFormat.LONG_STANDARD_DATE_FORMAT_MINUS_TIME, locale);
        //将qr 转换成 list遍历， (遇到一个bug， qr遍历时，少一个对象，不知道为什么)
        WTArrayList list = new WTArrayList(workItems);
        for (int i = 0; i < list.size(); i++) {
            WorkItem workItem = (WorkItem) list.getPersistable(i);
            String voteEvent = WorkflowHelper.getVoteEvent(workItem);
            if ("驳回".equals(voteEvent)) {
                //如果是驳回，则添加信息
                //审核人
                String completedBy = workItem.getCompletedBy();
                WTSet wtUserByName = PIPrincipalHelper.service.findWTUserByName(completedBy);
                Iterator iterator = wtUserByName.persistableIterator();
                if (iterator.hasNext()) {
                    WTUser user = (WTUser) iterator.next();
                    this.auditor += user.getFullName() + "\n";
                }

                //驳回时间
                Timestamp modifyTimestamp = workItem.getModifyTimestamp();
                String format = WTStandardDateFormat.format(new Date(modifyTimestamp.getTime())
                        , WTStandardDateFormat.LONG_STANDARD_DATE_FORMAT_MINUS_TIMEZONE, locale);
                this.auditTime += format + "\n";
                //驳回原因
                //获取备注
                WfVotingEventAudit matchingEventAudit = WfDataUtilitiesHelper.getMatchingEventAudit(workItem);
                this.rejectMsg += matchingEventAudit.getUserComment() + "\n";
            }
        }
    }

    public String getWfName() {
        return wfName;
    }

    public void setWfName(String wfName) {
        this.wfName = wfName;
    }

    public String getTechProductGroup() {
        return techProductGroup;
    }

    public void setTechProductGroup(String techProductGroup) {
        this.techProductGroup = techProductGroup;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getRejectMsg() {
        return rejectMsg;
    }

    public void setRejectMsg(String rejectMsg) {
        this.rejectMsg = rejectMsg;
    }

    public WfProcess getProcess() {
        return process;
    }

    public void setProcess(WfProcess process) {
        this.process = process;
    }

    public QueryResult getWorkItems() {
        return workItems;
    }

    public void setWorkItems(QueryResult workItems) {
        this.workItems = workItems;
    }

    @Override
    public String toString() {
        return "WFReportBean{" +
                "wfName='" + wfName + '\'' +
                ", techProductGroup='" + techProductGroup + '\'' +
                ", creator='" + creator + '\'' +
                ", createTime='" + createTime + '\'' +
                ", auditor='" + auditor + '\'' +
                ", auditTime='" + auditTime + '\'' +
                ", rejectMsg='" + rejectMsg + '\'' +
                ", process=" + process +
                ", workItems=" + workItems +
                '}';
    }
}
