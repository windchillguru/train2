package ext.train.report.util;

import ext.pi.core.PIPartHelper;
import org.apache.log4j.Logger;
import wt.auth.SimpleAuthenticator;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTArrayList;
import wt.log4j.LogR;
import wt.method.MethodContext;
import wt.method.MethodServerException;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.part.WTPartHelper;
import wt.pom.WTConnection;
import wt.util.WTException;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author 段鑫扬
 */
public class PartUtil implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = PartUtil.class.getName();
    private static final Logger logger = LogR.getLogger(CLASSNAME);

    /**
     * 获得顶层件
     *
     * @param part 当前部件
     * @param p    父件
     * @param tops
     * @throws WTException
     */
    public static void findTopParts(WTPart part, WTPart p, WTArrayList tops) throws WTException {
        QueryResult parentResult = WTPartHelper.service.getUsedByWTParts(p.getMaster(), true);
        WTArrayList parentList = new WTArrayList();
        if (parentResult == null || parentResult.size() <= 0) {
            if (!PersistenceHelper.isEquivalent(part, p) && !tops.contains(p)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Add Top Part:" + p.getDisplayIdentifier() + "");
                }
                tops.add(p);
            }
        } else {
            while (parentResult != null && parentResult.hasMoreElements()) {
                WTPart parent = (WTPart) parentResult.nextElement();
                if (!parent.getViewName().equals(part.getViewName())) {
                    continue;
                }
                WTPart latestPart = PIPartHelper.service.findWTPart(parent.getMaster(), parent.getViewName());
                if (!PersistenceHelper.isEquivalent(latestPart, parent)) {
                    continue;
                }
                if (!parentList.contains(parent)) {
                    logger.debug("part:" + part.getNumber() + ",parent:" + parent.getDisplayIdentifier());
                    parentList.add(parent);
                }
            }

            int size = parentList.size();
            for (int w = 0; w < size; w++) {
                WTPart ppart = (WTPart) parentList.getPersistable(w);
                findTopParts(part, ppart, tops);
            }
        }

    }

    /**
     * 获取当前部件的顶层件 通过队列/（隐式递归）
     *
     * @param child
     * @return
     * @throws WTException
     */
    public static WTArrayList findTopParts(WTPart child) throws WTException {
        if (logger.isDebugEnabled()) {
            logger.debug(">>>findTopParts() child=" + child.getDisplayIdentifier());
        }
        WTArrayList resultList = new WTArrayList();
        Queue<WTPart> queue = new LinkedList<>();
        queue.add(child);
        while (!queue.isEmpty()) {
            WTPart currentPart = queue.poll(); // 当前要查询的部件，并且从队列中移除
            // 查询,当前部件的父件,不限制视图
            QueryResult parentResult = WTPartHelper.service.getUsedByWTParts(currentPart.getMaster(), true);
            if (parentResult.size() == 0) {
                //如果当前部件没有父件，即为顶层物料
                if (!PersistenceHelper.isEquivalent(child, currentPart)) {
                    //如果当前部件不是传进来的部件，即当前部件为所查询的顶层物料
                    resultList.add(currentPart);
                }
            }
            while (parentResult.hasMoreElements()) {
                Object nextElement = parentResult.nextElement();
                WTPart parentPart = null;
                if (nextElement instanceof WTPart) {
                    parentPart = (WTPart) nextElement;
                    queue.add(parentPart);
                }
            }
        }
        return resultList;
    }

    /**
     * 查找子件的顶层父件
     * @param child
     * @return
     */
    /*public static WTArrayList findTopParts(WTPart child){
        if(logger.isDebugEnabled()){
            logger.debug(">>>findTopParts() child="+child.getDisplayIdentifier());
        }
        WTArrayList wtlist = new WTArrayList();
        String view = child.getViewName();
        long ida2a2 = PersistenceHelper.getObjectIdentifier(child).getId();
        if(logger.isDebugEnabled()){
            logger.debug(">>>child="+child.getAmount()+",view="+view+",ida2a2="+ida2a2);
        }
        //该sql只在oracle中可用
        String sql = "select distinct wm.wtpartnumber as fnumber,t.ida2a2 as id "
                + "from wtpartmaster wm, wtpart t where t.ida3masterreference = wm.ida2a2 and t.ida2a2 in "
                + "(select s.id from (select linka.ida3a5 as id,sa.ida2a2 as sid from wtpart sa, wtpart fa, wtpartusagelink linka "
                + "where sa.ida3masterreference = linka.ida3b5 and linka.ida3a5 = fa.ida2a2 and fa.latestiterationinfo = 1 "
                + "and fa.ida3view = (select v.ida2a2 from WTVIEW v where v.name = ? )) s "
                + "where CONNECT_BY_ISLEAF = 1 start with s.sid = ? connect by nocycle prior s.id = s.sid) "
                + "order by t.ida2a2 desc";
        if(logger.isDebugEnabled()){
            logger.debug(">>>sql="+sql);
        }
        Map<String, String> topParentMap = new HashMap<String, String>();
        PreparedStatement pstm = null;
        ResultSet result = null;
        try {
            WTConnection conn = getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setObject(1, view);
            pstm.setObject(2, ida2a2);
            result = pstm.executeQuery();
            while(result.next()){
                String parentNumber = result.getString("fnumber");
                String id = result.getString("id");
                if(!topParentMap.containsKey(parentNumber)){
                    topParentMap.put(parentNumber, id);
                }
            }
            logger.debug("Result in Sql topParentMap="+topParentMap);

            //过滤掉非最新版本
            for(String topNumber : topParentMap.keySet()){
                WTPart lpart = PIPartHelper.service.findWTPart(topNumber, view);
                String oid = "OR:wt.part.WTPart:" + topParentMap.get(topNumber);
                WTPart spart = (WTPart) PICoreHelper.service.getWTObjectByOid(oid);
                if(logger.isDebugEnabled()){
                    logger.debug("lpart="+lpart+",spart="+spart);
                }
                if(PersistenceHelper.isEquivalent(lpart, spart)){
                    wtlist.add(lpart);
                }
            }
        } catch (UnknownHostException e) {
        } catch (WTException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(result != null){
                try {
                    result.close();
                } catch (SQLException e) {
                }
            }
            if(pstm != null){
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if(logger.isDebugEnabled()){
            logger.debug(">>>findTopParts() child="+child.getDisplayIdentifier());
        }
        return wtlist;
    }
*/

    /**
     * 连接Windchill数据库
     *
     * @return
     * @throws UnknownHostException
     * @throws WTException
     */
    public static WTConnection getConnection() throws UnknownHostException, WTException {
        WTConnection wt_connection = null;
        MethodContext method_context = null;
        try {
            method_context = MethodContext.getContext();
        } catch (MethodServerException methodserverexception) {
            RemoteMethodServer.ServerFlag = true;
            InetAddress inet_address = InetAddress.getLocalHost();
            String host_name = inet_address.getHostName();
            if (host_name == null) {
                host_name = inet_address.getHostAddress();
            }
            SimpleAuthenticator Simple_authenticator = new SimpleAuthenticator();
            method_context = new MethodContext(host_name, Simple_authenticator);
            method_context.setThread(Thread.currentThread());
        }
        if (method_context != null) {
            try {
                wt_connection = (WTConnection) method_context.getConnection();
            } catch (Exception e) {
                throw new WTException(e);
            }
        }
        return wt_connection;
    }

}
