package ext.example.pg.service;

import ext.example.pg.bean.PGGroupBean;
import ext.example.pg.bean.PGInformationBean;
import ext.example.pg.model.PGGroup;
import ext.example.pg.model.PGInformation;
import ext.example.pg.model.PGMemberLink;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTKeyedMap;
import wt.util.WTException;

import java.util.List;

/**
 * @author 段鑫扬
 * @version 2019/12/20
 */
public interface PGInfoService {
    /**
     * 根据PG信息对应的bean去创建数据,没有就创建，已经存在则抛异常（已存在）
     *
     * @param bean
     * @return
     * @throws WTException
     */
    public PGInformation createPGInformation(PGInformationBean bean) throws WTException;

    /**
     * 根据PG信息对应的bean集合去创建数据,没有就创建，已经存在则抛异常（已存在）
     *
     * @param beans
     * @return
     * @throws WTException
     */
    public WTArrayList createPGInformations(List<PGInformationBean> beans) throws WTException;

    /**
     * 根据工号查询
     *
     * @param empNo
     * @return
     * @throws WTException
     */
    public PGInformation queryPGInformationByNo(String empNo) throws WTException;

    /**
     * 根据用户名查询
     *
     * @param userName
     * @return
     * @throws WTException
     */
    public PGInformation queryPGInformationByUserName(String userName) throws WTException;

    /**
     * 根据PG信息对应的bean去创建数据,没有就创建，已经存在则更新
     *
     * @param bean
     * @return
     * @throws WTException
     */
    public PGInformation savePGInformation(PGInformationBean bean) throws WTException;

    /**
     * 根据PG信息对应的bean集合去创建数据,没有就创建，已经存在则更新
     *
     * @param beans
     * @return
     * @throws WTException
     */
    public WTArrayList savePGInformations(List<PGInformationBean> beans) throws WTException;

    /**
     * 查询所有的pg用户
     *
     * @return
     * @throws WTException
     */
    public QueryResult queryPGInformations() throws WTException;

    /**
     * 根据PG组对应的bean去创建数据,没有就创建，已经存在则抛异常（已存在）
     *
     * @param bean
     * @return
     * @throws WTException
     */
    public PGGroup createPGGroup(PGGroupBean bean) throws WTException;

    /**
     * 根据PG组对应的bean集合去创建数据,没有就创建，已经存在则抛异常（已存在）
     *
     * @param beans
     * @return
     * @throws WTException
     */
    public WTArrayList createPGGroups(List<PGGroupBean> beans) throws WTException;

    /**
     * 根据组名查询
     *
     * @param groupName
     * @return
     * @throws WTException
     */
    public PGGroup queryPGGroup(String groupName) throws WTException;

    /**
     * 根据PG组对应的bean去创建数据,没有就创建，已经存在则更新
     *
     * @param bean
     * @return
     * @throws WTException
     */
    public PGGroup savePGGroup(PGGroupBean bean) throws WTException;

    /**
     * 根据PG组对应的bean集合去创建数据,没有就创建，已经存在则更新
     *
     * @param beans
     * @return
     * @throws WTException
     */
    public WTArrayList savePGGroups(List<PGGroupBean> beans) throws WTException;

    /**
     * 启用
     *
     * @param pgGroup
     * @return
     * @throws WTException
     */
    public PGGroup enablePGGroups(PGGroup pgGroup) throws WTException;

    /**
     * 禁用
     *
     * @param pgGroup
     * @return
     * @throws WTException
     */
    public PGGroup disablePGGroups(PGGroup pgGroup) throws WTException;

    /**
     * 查询pg组
     *
     * @param onlyEnabled 是否只查询启用组 ，true 只查询启用组，false 查询所有
     * @return
     * @throws WTException
     */
    public QueryResult queryPGGroups(boolean onlyEnabled) throws WTException;

    /**
     * 创建pg组和组成员的link,没有就创建，已经存在则抛异常（已存在）
     *
     * @param group
     * @param member
     * @param comments
     * @return
     * @throws WTException
     */
    public PGMemberLink createPGMemberLink(PGGroup group, WTObject member, String comments) throws WTException;

    /**
     * 保存pg组和组成员的link,没有就创建，已经存在则更新
     *
     * @param group
     * @param member
     * @param comments
     * @return
     * @throws WTException
     */
    public PGMemberLink saveLink(PGGroup group, WTObject member, String comments) throws WTException;

    /**
     * 查询pg组和组成员的link
     *
     * @param group
     * @param member
     * @return
     * @throws WTException
     */
    public PGMemberLink findLink(PGGroup group, WTObject member) throws WTException;

    /**
     * 创建pg组和组成员的link,没有就创建，已经存在则抛异常
     *
     * @param group
     * @param members
     * @param commentMap
     * @return
     * @throws WTException
     */
    public WTArrayList createLinks(PGGroup group, WTArrayList members, WTKeyedMap commentMap) throws WTException;

    /**
     * 保存pg组和组成员的link,没有就创建，已经存在则更新
     *
     * @param group
     * @param members
     * @param commentMap
     * @return
     * @throws WTException
     */
    public WTArrayList saveLinks(PGGroup group, WTArrayList members, WTKeyedMap commentMap) throws WTException;


    /**
     * 移除link
     *
     * @param group
     * @param member
     * @param comment
     * @throws WTException
     */
    public void removeLink(PGGroup group, WTObject member) throws WTException;

    /**
     * 根据组查成员
     *
     * @param group
     * @param selectObject
     * @return
     * @throws WTException
     */
    public QueryResult findMembers(PGGroup group, boolean selectObject) throws WTException;

    /**
     * 根据Pg成员查组
     *
     * @param member
     * @param selectObject
     * @return
     * @throws WTException
     */
    public QueryResult findGroups(WTObject member, boolean selectObject) throws WTException;


}
