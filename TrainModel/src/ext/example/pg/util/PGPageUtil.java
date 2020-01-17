package ext.example.pg.util;

import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.example.pg.model.InformationSource;
import ext.example.pg.model.PGGroup;
import ext.example.pg.model.PGInformation;
import ext.pi.core.PICoreHelper;
import org.apache.log4j.Logger;
import wt.doc.WTDocument;
import wt.fc.ObjectReference;
import wt.log4j.LogR;
import wt.method.RemoteAccess;
import wt.util.WTException;

import java.io.Serializable;
import java.util.*;

/**
 * @author 段鑫扬
 * @version 2019/12/23
 */
public class PGPageUtil implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = PGPageUtil.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    //定义常量
    public static final String INFOSOURCE_KEY = "INFOSOURCE_KEY";
    public static final String INFOSOURCE_DISPLAY = "INFOSOURCE_DISPLAY";
    public static final String INFOSOURCE_VALUE = "INFOSOURCE_VALUE";
    public static final String RESUMEINFO_DEFAULTVALUE = "RESUMEINFO_DEFAULTVALUE";
    public static final String RESUMEINFO_DEFAULTHIDDENVALUE = "RESUMEINFO_DEFAULTHIDDENVALUE";

    /**
     * 创建PG信息的初始化参数信息
     *
     * @param commandBean
     * @return
     */
    public static Map<String, Object> initPGInfoMap(NmCommandBean commandBean) {
        Map<String, Object> map = new HashMap<>();
        try {
            Locale locale = commandBean.getLocale();

            List<String> internals = new ArrayList<>();
            List<String> displays = new ArrayList<>();
            List<String> selecteds = new ArrayList<>();
            selecteds.add(InformationSource.getInformationSourceDefault() != null ? InformationSource.getInformationSourceDefault().toString() : "");

            internals.add("");
            displays.add("");
            InformationSource[] informationSourceSet = InformationSource.getInformationSourceSet();
            for (InformationSource informationSource : informationSourceSet) {
                // if(informationSource.isSelectable()){} // 枚举类型，的值是否能选择
                internals.add(informationSource.toString());
                displays.add(informationSource.getDisplay(locale));
            }

            map.put(INFOSOURCE_KEY, internals);
            map.put(INFOSOURCE_DISPLAY, displays);
            map.put(INFOSOURCE_VALUE, selecteds);
        } catch (WTException e) {
            e.printStackTrace();
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> initPGInfoMap map=" + map);
        }
        return map;
    }

    /**
     * 初始化编辑PG信息的页面信息
     *
     * @param commandBean
     * @return
     */
    public static Map<String, Object> initEditPGInfoMap(NmCommandBean commandBean) {
        Map<String, Object> map = new HashMap<>();
        try {
            Locale locale = commandBean.getLocale();
            Object actionObj = commandBean.getActionOid().getRefObject();
            PGInformation pgInfo = null;
            if (actionObj instanceof PGInformation) {
                pgInfo = (PGInformation) actionObj;
            }
            if (pgInfo == null) {
                throw new WTException("PGInformation 对象为null");
            }

            map.put(PGInformation.EMPLOYEE_NO, pgInfo.getEmployeeNo());//工号
            map.put(PGInformation.EMPLOYEE_NAME, pgInfo.getEmployeeName());//姓名
            map.put(PGInformation.EMPLOYEE_USER_NAME, pgInfo.getEmployeeUserName());//用户名
            map.put(PGInformation.EMPLOYEE_EMAIL, pgInfo.getEmployeeEmail());//邮箱
            map.put(PGInformation.EMPLOYEE_PHONE, pgInfo.getEmployeePhone());//电话
            map.put(PGInformation.COMMENTS, pgInfo.getComments());//备注
            map.put(PGInformation.EXPERIENCED, pgInfo.getExperienced());//有经验
            map.put(PGInformation.LEADER, pgInfo.getLeader());//组长

            //简历信息
            ObjectReference resumeInfo = pgInfo.getResumeInfo();
            if (resumeInfo != null && resumeInfo.getObject() != null) {
                WTDocument doc = (WTDocument) resumeInfo.getObject();
                map.put(RESUMEINFO_DEFAULTVALUE, doc.getNumber());
                //隐藏域，vid
                map.put(RESUMEINFO_DEFAULTHIDDENVALUE, PICoreHelper.service.getVid(doc));
            }
            //信息来源
            List<String> internals = new ArrayList<>();
            List<String> displays = new ArrayList<>();
            List<String> selecteds = new ArrayList<>();
            if (pgInfo != null) {
                selecteds.add(pgInfo.getInformationSource() != null ? pgInfo.getInformationSource().toString() : "");
            } else {
                selecteds.add(InformationSource.getInformationSourceDefault() != null ? InformationSource.getInformationSourceDefault().toString() : "");

            }

            internals.add("");
            displays.add("");
            InformationSource[] informationSourceSet = InformationSource.getInformationSourceSet();
            for (InformationSource informationSource : informationSourceSet) {
                // if(informationSource.isSelectable()){} // 枚举类型，的值是否能选择
                internals.add(informationSource.toString());
                displays.add(informationSource.getDisplay(locale));
            }

            map.put(INFOSOURCE_KEY, internals);
            map.put(INFOSOURCE_DISPLAY, displays);
            map.put(INFOSOURCE_VALUE, selecteds);
        } catch (WTException e) {
            e.printStackTrace();
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> initPGInfoMap map=" + map);
        }
        return map;
    }

    /**
     * 初始化编辑组页面数据
     *
     * @param commandBean
     * @return
     */
    public static Map<String, Object> initEditPGGroupMap(NmCommandBean commandBean) {
        Map<String, Object> map = new HashMap<>();
        try {
            Object actionObj = commandBean.getActionOid().getRefObject();
            PGGroup pgGroup = null;
            if (actionObj instanceof PGGroup) {
                pgGroup = (PGGroup) actionObj;
            }
            if (pgGroup != null) {
                map.put(PGGroup.PG_GROUP_NAME, pgGroup.getPgGroupName());
                map.put(PGGroup.COMMENTS, pgGroup.getComments());
                map.put(PGGroup.ROOT, pgGroup.getRoot());
                map.put(PGGroup.ENABLED, pgGroup.getEnabled());
            }

        } catch (WTException e) {
            e.printStackTrace();
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> initPGInfoMap map=" + map);
        }
        return map;
    }

}
