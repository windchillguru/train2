package ext.task.docapi;

import ext.task.docapi.service.docusagelink.Traverse;
import org.apache.log4j.Logger;
import wt.httpgw.GatewayAuthenticator;
import wt.log4j.LogR;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.util.WTException;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * 测试文档相关的API
 * 创建部件 自定义编码
 *
 * @author 段鑫扬
 */
public class TestDocAPI implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = TestDocAPI.class.getName();
    private static final Logger logger = LogR.getLogger(CLASSNAME);

    public static void main(String[] args) {
        if (args.length >= 0) {
            Class[] clsAry = {String[].class};
            Object[] objAry = {args};
            String method = "testDocAPI";//执行的方法
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


    public static void testDocAPI(String[] args) throws WTException, PropertyVetoException, IOException {
        if (args.length > 0) {
            //获取文档主数据
            //FindPrimary.findPrimary(args[0]);
            String oid = args[0];
            //String dir = args[1];
           /* try {
                //下载主数据到服务器
               // DownloadPrimary.downloadPrimary(oid, dir);
                UpdatePrimary.updatePrimary(oid, args[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            //获取附件
            // FindContents.findContents(oid);
            //下载压缩包
            // DownloadContents.downloadContents(oid, dir);
            //修改附件
            //UpdateContent.updateContent(oid, args[1],args[2]);
            //删除指定附件
            //DeleteContent.deleteContent(oid, args[1]);
          /*  List list = new ArrayList();
            list.add(args[1]);
            list.add(args[2]);
            ResetContent.resetContent(oid,list);*/

            //根据WTPart获取WTPartReferenceLink
            // FindWTPartReferenceLink.findWTPartReferenceLink(oid);
            //根据WTDocument获取WTPartReferenceLink
            // FindWTPartReferenceLinkByDoc.findWTPartReferenceLink(oid);
            // 获取WTPartReferenceLink 根据部件和文档
            //FindWTPartReferenceLink3.findWTPartReferenceLink(oid, args[1]);
            //获取参考文档关联的所有零部件
            // FindReferenceParts.findReferenceParts(oid);
            //获取零部件关联的所有参考文档
            // FindReferenceDocs.findReferenceDocs(oid);
            //根据WTPart获取WTPartDescribeLink
            // FindWTPartDescribeLinkByDoc.findWTPartReferenceLink(oid);
            //获取WTPartDescribeLink
            //FindWTPartDescribeLink3.findWTPartReferenceLink(oid, args[1]);
            //获取说明文档关联的所有零部件
            //FindDescribeParts.findReferenceParts(oid);

            // FindDescribeDocs.findReferenceDocs(oid);

            //FindWTDocumentDependencyLinkByRoleB.findWTDocumentDependencyLinkByRoleB(oid);
            // FindDependsOnDocs.findDependsOnDocs(oid);
            // FindDependentDocs.findDependsOnDocs(oid);
            Traverse.singleTraverse(oid);
        }

    }


}



