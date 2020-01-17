package ext.orv.doc.util;

import ext.lang.PIStringUtils;
import org.apache.log4j.Logger;
import wt.auth.SimpleAuthenticator;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTKeyedHashMap;
import wt.log4j.LogR;
import wt.method.MethodContext;
import wt.method.MethodServerException;
import wt.method.RemoteMethodServer;
import wt.pom.WTConnection;
import wt.util.WTException;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 文档生成编码的工具类
 *
 * @author 段鑫扬
 */
public class DocCodeUtil implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = DocCodeUtil.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    private DocCodeUtil() {
    }

    /**
     * 生成正式码
     *
     * @param doc
     * @return
     */
    public static String generateNumber(WTDocument doc, String prefix, String seqLength) {
        String msg = "";
        WTArrayList docs = new WTArrayList();
        docs.add(doc);
        WTKeyedHashMap resultMap = generateNumber(docs, prefix, seqLength);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> resultMap=" + resultMap);
        }
        StringBuilder error = new StringBuilder("");
        if (resultMap != null && resultMap.containsKey(doc)) {
            String info = (String) resultMap.get(doc);
            error.append("文档:").append(doc.getName()).append("编码异常:").append(info).append(";");
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> error = " + error.toString());
        }
        return msg;
    }

    /**
     * 生成正式码
     *
     * @param docs
     * @return map 用来存储编码失败的对象，如果没有失败对象，则返回空map
     */
    public static WTKeyedHashMap generateNumber(WTArrayList docs, String prefix, String seqLength) {
        WTKeyedHashMap errMap = new WTKeyedHashMap();
        int size = docs.size();
        for (int i = 0; i < size; i++) {
            WTDocument doc = null;
            try {
                doc = (WTDocument) docs.getPersistable(i);
            } catch (WTException e) {
                e.printStackTrace();
            }
            if (doc != null) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(">>>> doc:" + doc.getIterationIdentifier() + ",开始修改编码");
                }
                DocCoder coder = new DocCoder(doc, prefix, seqLength);
                coder.updateDocNumber();
                String errMsg = coder.getErrMsg().toString();
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(">>>>errMsg= " + errMsg);
                }
                if (PIStringUtils.hasText(errMsg)) {
                    errMap.put(doc, errMsg);
                }
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(">>>> doc = " + doc.getName() + "更新编码结束");
                }
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> generateNumber() errMap =" + errMap);
        }
        return errMap;
    }

    /**
     * 获取序列的下一个值
     *
     * @param seqName
     * @param n       流水码长度
     * @return
     */
    public static String getSeqNextValue(String seqName, int n) throws WTException {
        String seqValue = null;
        try {
            seqValue = PersistenceHelper.manager.getNextSequence(seqName);
            seqValue = formatValue(seqValue, n);
        } catch (WTException e) {
            e.printStackTrace();
        }
        return seqValue;
    }


    /**
     * 将整数转成指定位数，前面填充0
     *
     * @param value
     * @param flowNumDigit
     * @return
     */
    public static String formatValue(String value, int flowNumDigit) {
        Integer number = Integer.parseInt(value);
        // 0 代表前面补充0
        // flowNumDigit 代表长度
        // d 代表参数为正数型
        String formatConfig = "%0" + flowNumDigit + "d";
        value = String.format(formatConfig, number);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>after formatValue() value =" + value);
        }
        return value;
    }

    /**
     * 判断序列是否存在
     *
     * @param seqName
     * @return
     */
    public static boolean isSeqExist(String seqName) {
        boolean exist = false;

        String sql = "select * from user_sequences where sequence_name = ?";
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            WTConnection conn = getConnection();//不能关闭
            pstm = conn.prepareStatement(sql);
            pstm.setObject(1, seqName);
            rs = pstm.executeQuery();
            if (rs.next()) {
                exist = true;
            }
        } catch (UnknownHostException | WTException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeSQLCon(pstm, rs);
        }
        return exist;
    }

    /**
     * 创建序列
     *
     * @param seqName
     */
    public static void createSequence(String seqName) {
        String sql = " CREATE SEQUENCE  " + seqName +
                "      START WITH  1" +
                "      MAXVALUE 9999999999 " +
                "      MINVALUE 1" +
                "      CYCLE " +
                "      CACHE 10 " +
                "      NOORDER";
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            WTConnection conn = getConnection();//不能关闭
            pstm = conn.prepareStatement(sql);
            pstm.execute();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("--Create Seq:" + seqName + " Successfully");
            }
        } catch (UnknownHostException | WTException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeSQLCon(pstm, rs);
        }
    }

    /**
     * 关闭输出
     *
     * @param pstm
     * @param rs
     */
    public static void closeSQLCon(PreparedStatement pstm, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接Windchill数据库链接
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
