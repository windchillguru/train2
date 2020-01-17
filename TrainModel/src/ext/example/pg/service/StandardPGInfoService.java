package ext.example.pg.service;

import ext.example.pg.bean.PGGroupBean;
import ext.example.pg.bean.PGInformationBean;
import ext.example.pg.model.*;
import ext.lang.PIStringUtils;
import org.apache.log4j.Logger;
import wt.fc.*;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTKeyedMap;
import wt.log4j.LogR;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 段鑫扬
 * @version 2019/12/20
 */
public class StandardPGInfoService extends StandardManager implements PGInfoService, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = StandardPGInfoService.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    /**
     * 构造实例
     *
     * @return
     */
    public static StandardPGInfoService newStandardPGInfoService() throws WTException {
        StandardPGInfoService instance = new StandardPGInfoService();
        instance.initialize();
        return instance;
    }

    /**
     * 根据PG信息对应的bean去创建数据,没有就创建，已经存在则抛异常（已存在）
     *
     * @param bean
     * @return
     * @throws WTException
     */
    @Override
    public PGInformation createPGInformation(PGInformationBean bean) throws WTException {
        if (bean != null) {
            List<PGInformationBean> beans = new ArrayList<>();
            beans.add(bean);
            WTArrayList wtlist = createPGInformations(beans);
            if (wtlist != null && wtlist.size() > 0) {
                return (PGInformation) wtlist.getPersistable(0);
            }
        }
        return null;
    }

    /**
     * 根据PG信息对应的bean集合去创建数据,没有就创建，已经存在则抛异常（已存在）
     *
     * @param beans
     * @return
     * @throws WTException
     */
    @Override
    public WTArrayList createPGInformations(List<PGInformationBean> beans) throws WTException {
        WTArrayList wtlist = new WTArrayList();
        if (beans.isEmpty()) {
            return wtlist;
        }
        //过滤重复信息
        Set<PGInformationBean> beanSet = new HashSet<>();
        beanSet.addAll(beans);

        StringBuilder errBuilder = new StringBuilder();
        WTArrayList createList = new WTArrayList();
        for (PGInformationBean bean : beanSet) {
            if (queryPGInformationByNo(bean.getEmployeeNo()) != null) {
                errBuilder.append("工号：").append(bean.getEmployeeNo()).append(" 已存在；");
                continue;
            }
            if (queryPGInformationByNo(bean.getEmployeeUserName()) != null) {
                errBuilder.append("用户名：").append(bean.getEmployeeUserName()).append(" 已存在；");
                continue;
            }
            PGInformation pgInformation = newPGInformation(bean);
            createList.add(pgInformation);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>errBuilder =" + errBuilder.toString());
        }
        if (PIStringUtils.hasText(errBuilder.toString())) {
            throw new WTException(errBuilder.toString());
        }

        if (!createList.isEmpty()) {
            createList = (WTArrayList) PersistenceHelper.manager.save(createList);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>createList =" + createList.size());
            }
            wtlist.addAll(createList);
        }

        return wtlist;
    }

    /**
     * 创建PGInformation 实例
     *
     * @param bean
     * @return
     */
    private PGInformation newPGInformation(PGInformationBean bean) throws WTException {
        PGInformation pgInfo = null;
        try {
            pgInfo = PGInformation.newPGInformation();
            pgInfo.setEmployeeNo(bean.getEmployeeNo());//工号
            pgInfo.setEmployeeName(bean.getEmployeeName());//姓名
            pgInfo.setEmployeeUserName(bean.getEmployeeUserName());//用户名
            pgInfo.setEmployeeEmail(bean.getEmployeeEmail());//邮箱
            pgInfo.setEmployeePhone(bean.getEmployeePhone());//电话
            pgInfo.setComments(bean.getComments());//备注
            pgInfo.setExperienced(bean.isExperienced());//是否有经验
            if (bean.getResumeDoc() != null) {
                //简历文档
                pgInfo.setResumeInfo(ObjectReference.newObjectReference(bean.getResumeDoc()));
            }
            //信息源
            pgInfo.setInformationSource(InformationSource.toInformationSource(bean.getInformationSource()));
            pgInfo.setLeader(bean.isLeader());//是否组长
            pgInfo.setPgName(bean.getPgName());//是否组长
            pgInfo.setInformationNo(getSequenceNextValue(InformationNoSeq.class, 10));//信息编号
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
        return pgInfo;
    }

    /**
     * 获得序列的下一个值
     *
     * @param informationNoSeqClass
     * @param i
     * @return
     */
    private String getSequenceNextValue(Class<InformationNoSeq> informationNoSeqClass, int i) {
        String seqValue = null;
        try {
            seqValue = PersistenceHelper.manager.getNextSequence(informationNoSeqClass);
            seqValue = formatValue(seqValue, i);
        } catch (WTException e) {
            e.printStackTrace();
        }
        return seqValue;
    }

    private String formatValue(String seqValue, int i) {
        Integer number = Integer.parseInt(seqValue);
        // 0 代表前面补充0
        // i 代表长度
        // d 代表参数为正数型
        String formatConfig = "%0" + i + "d";
        seqValue = String.format(formatConfig, number);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>after formatValue() value =" + seqValue);
        }
        return seqValue;
    }

    /**
     * 根据工号查询
     *
     * @param empNo
     * @return
     * @throws WTException
     */
    @Override
    public PGInformation queryPGInformationByNo(String empNo) throws WTException {
        PGInformation pgInformation = null;
        if (PIStringUtils.hasText(empNo)) {
            QuerySpec qs = new QuerySpec(PGInformation.class);

            SearchCondition sc = new SearchCondition(PGInformation.class, PGInformation.EMPLOYEE_NO
                    , SearchCondition.EQUAL, empNo);
            qs.appendWhere(sc, new int[]{0});
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> qs=" + qs);
            }
            QueryResult queryResult = PersistenceHelper.manager.find(qs);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> queryResult.size=" + queryResult.size());
            }
            if (queryResult.hasMoreElements()) {
                pgInformation = (PGInformation) queryResult.nextElement();
            }
        }
        return pgInformation;
    }

    /**
     * 根据用户名查询
     *
     * @param userName
     * @return
     * @throws WTException
     */
    @Override
    public PGInformation queryPGInformationByUserName(String userName) throws WTException {
        PGInformation pgInformation = null;
        if (PIStringUtils.hasText(userName)) {
            QuerySpec qs = new QuerySpec(PGInformation.class);

            SearchCondition sc = new SearchCondition(PGInformation.class, PGInformation.EMPLOYEE_USER_NAME
                    , SearchCondition.EQUAL, userName);
            qs.appendWhere(sc, new int[]{0});
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> qs=" + qs);
            }
            QueryResult queryResult = PersistenceHelper.manager.find(qs);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> queryResult.size=" + queryResult.size());
            }
            if (queryResult.hasMoreElements()) {
                pgInformation = (PGInformation) queryResult.nextElement();
            }
        }
        return pgInformation;
    }

    /**
     * 根据PG信息对应的bean去创建数据,没有就创建，已经存在则更新
     *
     * @param bean
     * @return
     * @throws WTException
     */
    @Override
    public PGInformation savePGInformation(PGInformationBean bean) throws WTException {
        if (bean != null) {
            List<PGInformationBean> beans = new ArrayList<>();
            beans.add(bean);
            WTArrayList wtlist = savePGInformations(beans);
            if (wtlist != null && wtlist.size() > 0) {
                return (PGInformation) wtlist.getPersistable(0);
            }
        }
        return null;
    }

    /**
     * 根据PG信息对应的bean集合去创建数据,没有就创建，已经存在则更新
     *
     * @param beans
     * @return
     * @throws WTException
     */
    @Override
    public WTArrayList savePGInformations(List<PGInformationBean> beans) throws WTException {
        WTArrayList wtlist = new WTArrayList();
        if (beans.isEmpty()) {
            return wtlist;
        }
        //过滤重复信息
        Set<PGInformationBean> beanSet = new HashSet<>();
        beanSet.addAll(beans);

        StringBuilder errBuilder = new StringBuilder();
        WTArrayList saveList = new WTArrayList();
        for (PGInformationBean bean : beanSet) {
            PGInformation existPgByNo = queryPGInformationByNo(bean.getEmployeeNo());
            PGInformation existPgByName = queryPGInformationByUserName(bean.getEmployeeUserName());
            if (existPgByNo != null && existPgByName != null
                    && PersistenceHelper.isEquivalent(existPgByName, existPgByNo)) {
                errBuilder.append("工号：").append(bean.getEmployeeNo()).append("用户名：")
                        .append(bean.getEmployeeUserName()).append("在系统已存在，且不对应");
            }
            if (existPgByNo != null || existPgByName != null) {
                PGInformation existPg = null;
                if (existPgByNo != null) {
                    existPg = existPgByNo;
                }
                if (existPgByName != null) {
                    existPg = existPgByName;
                }
                existPg = updatePGInformation(bean, existPg);
                saveList.add(existPg);
            } else {
                PGInformation pgInformation = newPGInformation(bean);
                saveList.add(pgInformation);
            }

        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>errBuilder =" + errBuilder.toString());
        }
        if (PIStringUtils.hasText(errBuilder.toString())) {
            throw new WTException(errBuilder.toString());
        }

        if (!saveList.isEmpty()) {
            saveList = (WTArrayList) PersistenceHelper.manager.save(saveList);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>saveList =" + saveList.size());
            }
            wtlist.addAll(saveList);
        }

        return wtlist;
    }

    /**
     * 更新信息
     *
     * @param bean
     * @param pgInfo 数据库中的数据 pgInfo对象I
     * @return
     */
    private PGInformation updatePGInformation(PGInformationBean bean, PGInformation pgInfo) throws WTException {
        try {
            pgInfo.setEmployeeNo(bean.getEmployeeNo());//工号
            pgInfo.setEmployeeName(bean.getEmployeeName());//姓名
            pgInfo.setEmployeeUserName(bean.getEmployeeUserName());//用户名
            pgInfo.setEmployeeEmail(bean.getEmployeeEmail());//邮箱
            pgInfo.setEmployeePhone(bean.getEmployeePhone());//电话
            pgInfo.setComments(bean.getComments());//备注
            pgInfo.setExperienced(bean.isExperienced());//是否有经验
            if (bean.getResumeDoc() != null) {
                //简历文档
                pgInfo.setResumeInfo(ObjectReference.newObjectReference(bean.getResumeDoc()));
            }
            //信息源
            pgInfo.setInformationSource(InformationSource.toInformationSource(bean.getInformationSource()));
            pgInfo.setLeader(bean.isLeader());//是否组长
            pgInfo.setPgName(bean.getPgName());//是否组长
            // pgInfo.setInformationNo(getSequenceNextValue(InformationNoSeq.class, 10));//信息编号
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
        return pgInfo;

    }

    /**
     * 查询所有的pg用户
     *
     * @return
     * @throws WTException
     */
    @Override
    public QueryResult queryPGInformations() throws WTException {
        QuerySpec qs = new QuerySpec(PGInformation.class);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> qr.size =" + qr.size());
        }
        return qr;
    }

    /**
     * 根据PG组对应的bean去创建数据,没有就创建，已经存在则抛异常（已存在）
     *
     * @param bean
     * @return
     * @throws WTException
     */
    @Override
    public PGGroup createPGGroup(PGGroupBean bean) throws WTException {
        if (bean != null) {
            List<PGGroupBean> beans = new ArrayList<>();
            beans.add(bean);
            WTArrayList wtlist = createPGGroups(beans);
            if (wtlist != null && wtlist.size() > 0) {
                return (PGGroup) wtlist.getPersistable(0);
            }
        }
        return null;
    }

    /**
     * 根据PG组对应的bean集合去创建数据,没有就创建，已经存在则抛异常（已存在）
     *
     * @param beans
     * @return
     * @throws WTException
     */
    @Override
    public WTArrayList createPGGroups(List<PGGroupBean> beans) throws WTException {
        WTArrayList wtlist = new WTArrayList();
        if (beans.isEmpty()) {
            return wtlist;
        }
        //过滤重复信息
        Set<PGGroupBean> beanSet = new HashSet<>();
        beanSet.addAll(beans);

        StringBuilder errBuilder = new StringBuilder();
        WTArrayList createList = new WTArrayList();
        for (PGGroupBean bean : beanSet) {
            if (queryPGGroup(bean.getPgGroupName()) != null) {
                errBuilder.append("组名：").append(bean.getPgGroupName()).append(" 已存在；");
                continue;
            }
            PGGroup pgGroup = newPGGroup(bean);
            createList.add(pgGroup);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>errBuilder =" + errBuilder.toString());
        }
        if (PIStringUtils.hasText(errBuilder.toString())) {
            throw new WTException(errBuilder.toString());
        }

        if (!createList.isEmpty()) {
            createList = (WTArrayList) PersistenceHelper.manager.save(createList);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>createList =" + createList.size());
            }
            wtlist.addAll(createList);
        }

        return wtlist;
    }

    /**
     * 创建pg组，为持久化
     *
     * @param bean
     * @return
     * @throws WTException
     */
    private PGGroup newPGGroup(PGGroupBean bean) throws WTException {
        PGGroup pgGroup = null;
        try {
            pgGroup = PGGroup.newPGGroup();
            pgGroup.setPgGroupName(bean.getPgGroupName());
            pgGroup.setEnabled(bean.isEnabled());
            pgGroup.setRoot(bean.isRoot());
            pgGroup.setComments(bean.getComments());
            pgGroup.setPgName(bean.getPgName());

        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
        return pgGroup;
    }

    /**
     * 根据组名查询
     *
     * @param groupName
     * @return
     * @throws WTException
     */
    @Override
    public PGGroup queryPGGroup(String groupName) throws WTException {
        PGGroup pgGroup = null;
        if (PIStringUtils.hasText(groupName)) {
            QuerySpec qs = new QuerySpec(PGGroup.class);

            SearchCondition sc = new SearchCondition(PGGroup.class, PGGroup.PG_GROUP_NAME
                    , SearchCondition.EQUAL, groupName);
            qs.appendWhere(sc, new int[]{0});
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> qs=" + qs);
            }
            QueryResult queryResult = PersistenceHelper.manager.find(qs);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> queryResult.size=" + queryResult.size());
            }
            if (queryResult.hasMoreElements()) {
                pgGroup = (PGGroup) queryResult.nextElement();
            }
        }
        return pgGroup;
    }

    /**
     * 根据PG组对应的bean去创建数据,没有就创建，已经存在则更新
     *
     * @param bean
     * @return
     * @throws WTException
     */
    @Override
    public PGGroup savePGGroup(PGGroupBean bean) throws WTException {
        if (bean != null) {
            List<PGGroupBean> beans = new ArrayList<>();
            beans.add(bean);
            WTArrayList wtlist = savePGGroups(beans);
            if (wtlist != null && wtlist.size() > 0) {
                return (PGGroup) wtlist.getPersistable(0);
            }
        }
        return null;
    }

    /**
     * 根据PG组对应的bean集合去创建数据,没有就创建，已经存在则更新
     *
     * @param beans
     * @return
     * @throws WTException
     */
    @Override
    public WTArrayList savePGGroups(List<PGGroupBean> beans) throws WTException {
        WTArrayList wtlist = new WTArrayList();
        if (beans.isEmpty()) {
            return wtlist;
        }
        //过滤重复信息
        Set<PGGroupBean> beanSet = new HashSet<>();
        beanSet.addAll(beans);

        StringBuilder errBuilder = new StringBuilder();
        WTArrayList saveList = new WTArrayList();
        for (PGGroupBean bean : beanSet) {
            PGGroup pgGroup = queryPGGroup(bean.getPgGroupName());
            if (pgGroup != null) {
                pgGroup = updatePGGroup(pgGroup, bean);
                saveList.add(pgGroup);
            } else {
                PGGroup newPGGroup = newPGGroup(bean);
                saveList.add(newPGGroup);
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>errBuilder =" + errBuilder.toString());
        }
        if (PIStringUtils.hasText(errBuilder.toString())) {
            throw new WTException(errBuilder.toString());
        }

        if (!saveList.isEmpty()) {
            saveList = (WTArrayList) PersistenceHelper.manager.save(saveList);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>saveList =" + saveList.size());
            }
            wtlist.addAll(saveList);
        }
        return wtlist;
    }

    private PGGroup updatePGGroup(PGGroup pgGroup, PGGroupBean bean) throws WTException {
        try {
            pgGroup.setPgGroupName(bean.getPgGroupName());
            pgGroup.setEnabled(bean.isEnabled());
            pgGroup.setRoot(bean.isRoot());
            pgGroup.setComments(bean.getComments());
            pgGroup.setPgName(bean.getPgName());
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
        return pgGroup;
    }

    /**
     * 启用
     *
     * @param pgGroup
     * @return
     * @throws WTException
     */
    @Override
    public PGGroup enablePGGroups(PGGroup pgGroup) throws WTException {
        if (pgGroup != null) {
            if (!pgGroup.getEnabled()) {
                try {
                    pgGroup.setEnabled(true);
                    pgGroup = (PGGroup) PersistenceHelper.manager.modify(pgGroup);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new WTException(e);
                }
            }
        }
        return pgGroup;
    }

    /**
     * 禁用
     *
     * @param pgGroup
     * @return
     * @throws WTException
     */
    @Override
    public PGGroup disablePGGroups(PGGroup pgGroup) throws WTException {
        if (pgGroup != null) {
            if (pgGroup.getEnabled()) {
                try {
                    pgGroup.setEnabled(false);
                    pgGroup = (PGGroup) PersistenceHelper.manager.modify(pgGroup);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new WTException(e);
                }
            }
        }
        return pgGroup;
    }

    /**
     * 查询pg组
     *
     * @param onlyEnabled 是否只查询启用组 ，true 只查询启用组，false 查询所有
     * @return
     * @throws WTException
     */
    @Override
    public QueryResult queryPGGroups(boolean onlyEnabled) throws WTException {
        QuerySpec qs = new QuerySpec(PGGroup.class);
        if (onlyEnabled) {
            SearchCondition sc = new SearchCondition(PGGroup.class, PGGroup.ENABLED, SearchCondition.IS_TRUE);
            qs.appendWhere(sc, new int[]{0});
        }
        QueryResult qr = PersistenceHelper.manager.find(qs);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> qr.size =" + qr.size());
        }
        return qr;
    }

    /**
     * 创建pg组和组成员的link,没有就创建，已经存在则抛异常（已存在）
     *
     * @param group
     * @param member
     * @param comments
     * @return
     * @throws WTException
     */
    @Override
    public PGMemberLink createPGMemberLink(PGGroup group, WTObject member, String comments) throws WTException {
        PGMemberLink link = null;
        if (group != null && member != null) {
            link = findLink(group, member);
            if (link != null) {
                throw new WTException("组:" + group.getIdentity() + "和成员 " + member.getIdentity() + "之间已存在link关系");
            }
            try {
                link = PGMemberLink.newPGMemberLink(group, member);
                link.setComments(comments);
                PersistenceHelper.manager.save(link);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(">>>> 创建link成功" + group.getIdentity() + " " + member.getIdentity());
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new WTException(e);
            }
        }
        return link;
    }

    /**
     * 保存pg组和组成员的link,没有就创建，已经存在则更新
     *
     * @param group
     * @param member
     * @param comments
     * @return
     * @throws WTException
     */
    @Override
    public PGMemberLink saveLink(PGGroup group, WTObject member, String comments) throws WTException {
        PGMemberLink link = null;
        if (group != null && member != null) {
            link = findLink(group, member);
            try {
                if (link == null) {
                    link = PGMemberLink.newPGMemberLink(group, member);
                }
                link.setComments(comments);
                PersistenceHelper.manager.save(link);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(">>>> 保存link成功" + group.getIdentity() + " " + member.getIdentity());
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new WTException(e);
            }
        }
        return link;
    }

    /**
     * 查询pg组和组成员的link
     *
     * @param group
     * @param member
     * @return
     * @throws WTException
     */
    @Override
    public PGMemberLink findLink(PGGroup group, WTObject member) throws WTException {
        if (group != null && member != null) {
            QueryResult qr = PersistenceHelper.manager.find(PGMemberLink.class, group, PGMemberLink.PG_GROUP_ROLE, member);
            if (qr != null && qr.hasMoreElements()) {
                return (PGMemberLink) qr.nextElement();
            }
        }
        return null;
    }

    /**
     * 创建pg组和组成员的link,没有就创建，已经存在则抛异常
     *
     * @param group
     * @param members
     * @param commentMap
     * @return
     * @throws WTException
     */
    @Override
    public WTArrayList createLinks(PGGroup group, WTArrayList members, WTKeyedMap commentMap) throws WTException {
        WTArrayList wtlist = new WTArrayList();
        if (group != null && !members.isEmpty()) {
            StringBuilder errBuilder = new StringBuilder();
            WTArrayList createList = new WTArrayList();
            WTArrayList existMemberList = new WTArrayList();
            QueryResult existMembers = findMembers(group, true);
            existMemberList.addAll(existMembers);
            for (int i = 0; i < members.size(); i++) {
                Persistable persistable = members.getPersistable(i);
                if (existMemberList.contains(persistable)) {
                    errBuilder.append("成员：").append(persistable.getIdentity()).append("已存在;");
                    continue;
                }
                PGMemberLink link = PGMemberLink.newPGMemberLink(group, (WTObject) persistable);
                if (commentMap != null && commentMap.containsKey(persistable)) {
                    try {
                        link.setComments((String) commentMap.get(persistable));
                    } catch (WTPropertyVetoException e) {
                        e.printStackTrace();
                    }
                }
                createList.add(link);
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>errBuilder =" + errBuilder.toString());
            }
            if (PIStringUtils.hasText(errBuilder.toString())) {
                throw new WTException(errBuilder.toString());
            }

            if (!createList.isEmpty()) {
                createList = (WTArrayList) PersistenceHelper.manager.save(createList);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(">>>>createList =" + createList.size());
                }
                wtlist.addAll(createList);
            }
        }
        return wtlist;
    }

    /**
     * 保存pg组和组成员的link,没有就创建，已经存在则更新
     *
     * @param group
     * @param members
     * @param commentMap
     * @return
     * @throws WTException
     */
    @Override
    public WTArrayList saveLinks(PGGroup group, WTArrayList members, WTKeyedMap commentMap) throws WTException {
        WTArrayList wtlist = new WTArrayList();
        if (group != null && !members.isEmpty()) {
            WTArrayList saveList = new WTArrayList();
            for (int i = 0; i < members.size(); i++) {
                WTObject wtObject = (WTObject) members.getPersistable(i);
                PGMemberLink link = findLink(group, wtObject);
                if (link == null) {
                    link = PGMemberLink.newPGMemberLink(group, wtObject);
                }
                if (commentMap != null && commentMap.containsKey(wtObject)) {
                    try {
                        link.setComments((String) commentMap.get(wtObject));
                    } catch (WTPropertyVetoException e) {
                        e.printStackTrace();
                    }
                }
                saveList.add(link);
            }
            if (!saveList.isEmpty()) {
                saveList = (WTArrayList) PersistenceHelper.manager.save(saveList);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(">>>>saveList =" + saveList.size());
                }
                wtlist.addAll(saveList);
            }
        }
        return wtlist;
    }

    /**
     * 移除link
     *
     * @param group
     * @param member
     * @throws WTException
     */
    @Override
    public void removeLink(PGGroup group, WTObject member) throws WTException {
        if (group != null && member != null) {
            PGMemberLink link = findLink(group, member);
            if (link != null) {
                PersistenceHelper.manager.delete(link);
            }
        }
    }

    /**
     * 根据组查成员
     *
     * @param group
     * @param selectObject
     * @return
     * @throws WTException
     */
    @Override
    public QueryResult findMembers(PGGroup group, boolean selectObject) throws WTException {
        QueryResult result = null;
        if (group != null) {
            // roleA, roleB角色，link.class, false 获取link对象，true 获取 roleB对象
            result = PersistenceHelper.manager.navigate(group, PGMemberLink.PG_MEMBER_ROLE, PGMemberLink.class, selectObject);
        }
        return result == null ? new QueryResult() : result;
    }

    /**
     * 根据Pg成员查组
     *
     * @param member
     * @param selectObject
     * @return
     * @throws WTException
     */
    @Override
    public QueryResult findGroups(WTObject member, boolean selectObject) throws WTException {
        QueryResult result = null;
        if (member != null) {
            // roleB, roleA角色，link.class,  false 获取link对象，true 获取 roleA对象
            result = PersistenceHelper.manager.navigate(member, PGMemberLink.PG_GROUP_ROLE, PGMemberLink.class, selectObject);
        }
        return result == null ? new QueryResult() : result;
    }


}
