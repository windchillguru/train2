package ext.task.query;

import ext.task.query.queryspec.DocQuerySpecIBA;
import org.apache.log4j.Logger;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.httpgw.GatewayAuthenticator;
import wt.log4j.LogR;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.query.QuerySpec;
import wt.util.WTException;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.ParseException;

/**
 * @author 段鑫扬
 */
public class TestDocQueryAPI implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = TestDocQueryAPI.class.getName();
    private static final Logger logger = LogR.getLogger(CLASSNAME);

    public static void main(String[] args) {
        if (args.length >= 0) {
            Class[] clsAry = {String[].class};
            Object[] objAry = {args};
            String method = "testQueryAPI";//执行的方法
            try {
                if (!RemoteMethodServer.ServerFlag) {//是否远程调用
                    GatewayAuthenticator auth = new GatewayAuthenticator();
                    auth.setRemoteUser("Administrator");// 直接设置管理员，执行命令时，不会再弹出输入用户名密码框
                    RemoteMethodServer.getDefault().setAuthenticator(auth);
                    /*
                     * method 执行的方法
                     * CLASSNAME 类名
                     * clsAry
                     * objAry args 参数
                     * */
                    RemoteMethodServer.getDefault().invoke(method, CLASSNAME, null, clsAry, objAry);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static void testQueryAPI(String[] args) throws WTException, PropertyVetoException, IOException, ParseException {
        DocQuerySpecIBA docQuerySpecIBA = new DocQuerySpecIBA();
       /* docQuerySpecIBA.setBooleanIBAName("testBoolean");
        docQuerySpecIBA.setBooleanIBAValue(true);*/
       /* docQuerySpecIBA.setUrlIBAName("testURL");
        docQuerySpecIBA.setUrlIBAValue("https://tencentcloudbase.github.io/handbook/tcb01.html");*/
       /* docQuerySpecIBA.setDateIBAName("testDate");
        String createTime = "2019/12/03 00:00:00";
        java.util.Date startDate = WTStandardDateFormat.parse(createTime, WTStandardDateFormat.LONG_STANDARD_DATE_FORMAT_MINUS_TIMEZONE);
        Timestamp dateIBAValue = new Timestamp(startDate.getTime());
        docQuerySpecIBA.setDateIBAValue(dateIBAValue);*/
        docQuerySpecIBA.setDoubleIBAName("testDouble");
        docQuerySpecIBA.setDoubleIBAValue(new Double("11.2222222222222"));
        QuerySpec qs = docQuerySpecIBA.buildQuerySpec();
        System.out.println("qs:" + qs);
        if (qs != null) {
            QueryResult qr = PersistenceHelper.manager.find(qs);
            while (qr != null && qr.hasMoreElements()) {
                WTDocument doc = (WTDocument) qr.nextElement();
                System.out.println("doc.display=" + doc.getDisplayIdentifier());
            }
        }
    }
}
