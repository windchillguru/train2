package ext.task2.change.bean;

import ext.pi.core.PIAttributeHelper;
import wt.change2.WTChangeActivity2;
import wt.change2.WTChangeOrder2;
import wt.change2.WTChangeRequest2;
import wt.org.WTPrincipalReference;
import wt.project.Role;
import wt.session.SessionHelper;
import wt.team.Team;
import wt.util.WTException;
import wt.util.WTStandardDateFormat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 段鑫扬
 * @version 2020/1/10
 * 更改报表的数据实体类
 */
public class ChangeReportBean implements Serializable {
    private static final long serialVersionUID = 8313768800000132380L;
    //ecr编号
    private String ecrNumber;
    //ecr名称
    private String ecrName;
    //提出人（创建者）
    private String ecrCreator;
    //提出时间（创建时间）
    private String ecrCreateTime;
    //等级
    private String ecrGrade;
    //ECA提出时间（创建时间）
    private String ecaCreateTime;
    //ECA责任人(责任人)
    private String ecaResponsible;
    //关联的项目信息（ECN  IBA属性）
    private String ecnAssociatedItemInfo;
    //及时回复率
    private String recoveryRate;

    private WTChangeRequest2 ecr;
    private WTChangeActivity2 eca;
    private WTChangeOrder2 ecn;
    // iba属性 关联的项目信息
    private static String IBA_ASSOCIATEDITEMINFO = "AssociatedItemInfo";
    //iba属性 等级
    private static String IBA_GRADE = "Grade";
    // iba属性 责任人内部值
    private static final String IBA_ROLE_ASSIGNEE = "ROLE_ASSIGNEE";

    public ChangeReportBean(WTChangeRequest2 ecr, WTChangeActivity2 eca, WTChangeOrder2 ecn) throws WTException {
        if (ecr == null || eca == null || ecn == null) {
            throw new WTException("不能创建变更相关的报表对象");
        }
        try {
            this.ecr = ecr;
            this.eca = eca;
            this.ecn = ecn;
            setProperty();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
    }

    private void setProperty() throws Exception {
        this.ecrNumber = ecr.getNumber();
        this.ecrName = ecr.getName();
        this.ecrCreator = ecr.getCreatorName();
        Timestamp createTimestamp = ecr.getCreateTimestamp();
        Locale locale = SessionHelper.manager.getLocale();

        this.ecrCreateTime = WTStandardDateFormat.format(new Date(createTimestamp.getTime())
                , WTStandardDateFormat.LONG_STANDARD_DATE_FORMAT_MINUS_TIME, locale);

        Object value = PIAttributeHelper.service.getValue(ecr, IBA_GRADE);
        if (value != null) {
            this.ecrGrade = value + "";
        }
        if (ecrGrade == null) {
            //默认值 1
            ecrGrade = "1";
        }

        //eca 创建时间
        Timestamp ecaCreateTimestamp = eca.getCreateTimestamp();
        this.ecaCreateTime = WTStandardDateFormat.format(new Date(ecaCreateTimestamp.getTime())
                , WTStandardDateFormat.LONG_STANDARD_DATE_FORMAT_MINUS_TIME, locale);
        //获取工作负责人
        Team team = (Team) eca.getTeamId().getObject();
        Map rolePrincipalMap = team.getRolePrincipalMap();
        //获取的角色
        Object assignee = rolePrincipalMap.get(Role.toRole("ASSIGNEE"));
        if (assignee instanceof ArrayList) {
            ArrayList list = (ArrayList) assignee;
            Object o = list.get(0);
            if (o instanceof WTPrincipalReference) {
                WTPrincipalReference principalReference = (WTPrincipalReference) o;
                this.ecaResponsible = principalReference.getFullName();
            }
        }

        //  ecn 的 项目相关信息
        this.ecnAssociatedItemInfo = (String) PIAttributeHelper.service.getValue(ecn, IBA_ASSOCIATEDITEMINFO);
        // 及时回复率  （ECA提交时间-ECR提交时间）/问题等级对应工期（7、3、1）
        Date a1 = null;
        Date b1 = null;
        try {
            a1 = new SimpleDateFormat("yyyy/MM/dd").parse(ecaCreateTime);
            b1 = new SimpleDateFormat("yyyy/MM/dd").parse(ecrCreateTime);
            //获取相减后天数
            long day = (a1.getTime() - b1.getTime()) / (24 * 60 * 60 * 1000);

            double rate = ((float) day / Integer.parseInt(ecrGrade));
            //转百分比显示
            DecimalFormat df = new DecimalFormat("0.00%");
            this.recoveryRate = df.format(rate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public String getEcrNumber() {
        return ecrNumber;
    }

    public void setEcrNumber(String ecrNumber) {
        this.ecrNumber = ecrNumber;
    }

    public String getEcrName() {
        return ecrName;
    }

    public void setEcrName(String ecrName) {
        this.ecrName = ecrName;
    }

    public String getEcrCreator() {
        return ecrCreator;
    }

    public void setEcrCreator(String ecrCreator) {
        this.ecrCreator = ecrCreator;
    }

    public String getEcrCreateTime() {
        return ecrCreateTime;
    }

    public void setEcrCreateTime(String ecrCreateTime) {
        this.ecrCreateTime = ecrCreateTime;
    }

    public String getEcrGrade() {
        return ecrGrade;
    }

    public void setEcrGrade(String ecrGrade) {
        this.ecrGrade = ecrGrade;
    }

    public String getEcaCreateTime() {
        return ecaCreateTime;
    }

    public void setEcaCreateTime(String ecaCreateTime) {
        this.ecaCreateTime = ecaCreateTime;
    }

    public String getEcaResponsible() {
        return ecaResponsible;
    }

    public void setEcaResponsible(String ecaResponsible) {
        this.ecaResponsible = ecaResponsible;
    }

    public String getEcnAssociatedItemInfo() {
        return ecnAssociatedItemInfo;
    }

    public void setEcnAssociatedItemInfo(String ecnAssociatedItemInfo) {
        this.ecnAssociatedItemInfo = ecnAssociatedItemInfo;
    }

    public String getRecoveryRate() {
        return recoveryRate;
    }

    public void setRecoveryRate(String recoveryRate) {
        this.recoveryRate = recoveryRate;
    }

    public WTChangeRequest2 getEcr() {
        return ecr;
    }

    public void setEcr(WTChangeRequest2 ecr) {
        this.ecr = ecr;
    }

    public WTChangeActivity2 getEca() {
        return eca;
    }

    public void setEca(WTChangeActivity2 eca) {
        this.eca = eca;
    }

    public WTChangeOrder2 getEcn() {
        return ecn;
    }

    public void setEcn(WTChangeOrder2 ecn) {
        this.ecn = ecn;
    }

    @Override
    public String toString() {
        return "ChangeReportBean{" +
                "ecrNumber='" + ecrNumber + '\'' +
                ", ecrName='" + ecrName + '\'' +
                ", ecrCreator='" + ecrCreator + '\'' +
                ", ecrCreateTime='" + ecrCreateTime + '\'' +
                ", ecrGrade='" + ecrGrade + '\'' +
                ", ecaCreateTime='" + ecaCreateTime + '\'' +
                ", ecaResponsible='" + ecaResponsible + '\'' +
                ", ecnAssociatedItemInfo='" + ecnAssociatedItemInfo + '\'' +
                ", recoveryRate=" + recoveryRate +
                ", ecr=" + ecr +
                ", eca=" + eca +
                ", ecn=" + ecn +
                '}';
    }
}
