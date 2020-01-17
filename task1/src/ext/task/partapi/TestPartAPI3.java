package ext.task.partapi;

import ext.task.partapi.service.GetPartView;
import org.apache.log4j.Logger;
import wt.httpgw.GatewayAuthenticator;
import wt.log4j.LogR;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * 测试部件相关的API
 * 创建部件 自定义编码
 *
 * @author 段鑫扬
 */
public class TestPartAPI3 implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = TestPartAPI3.class.getName();
    private static final Logger logger = LogR.getLogger(CLASSNAME);

    public static void main(String[] args) {
        if (args.length >= 0) {
            Class[] clsAry = {String[].class};
            Object[] objAry = {args};
            String method = "testPartAPI";//执行的方法
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

    /**
     * 执行部件api方法
     *
     * @param args VR:wt.part.WTPart:103520
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static void testPartAPI(String[] args) throws WTException, WTPropertyVetoException {
        //修改部件属性
        // ChangePartMasterAttr.changePartMasterAttr();

        if (args.length > 0) {
            String oid = args[0];
            //获取版本
            // GetVersionByOid.getVersionByOid(oid);
            //检出部件
            //  CheckOutByOid.checkOutByOid(oid);
            //修改属性
            // UpdateAttrByOid.updateAttrByOid(oid);
            //检入
            // CheckInByOid.checkOutByOid(oid);
            //修订
            //ReviseByOid.eviseByOid(oid);
            //查询指定版本的对象
            // FindVersionByOid.findVersionByOid(oid);
            //查询最新版本的对象
            //FindLastVersionByOid.findVersionByOid(oid);
            //获取生命周期
            // LifeCycleState.getLifeCycleState(oid);
            //修改生命周期
            // SetLifeCycleState.setSetLifeCycleState(oid);
            //获取生命周期模板
            //GetLifeCycleTemplate.getLifeCycleTemplate(oid);
            //修改生命周期模板
            //SetLifeCycleTemplate.getLifeCycleTemplate(oid);

            //获取部件视图
            GetPartView.getPartView(oid);
            //修改部件视图
            //SetPartView.setPartView(oid);
            //新建视图版本
            //NewViewVersion.newViewVersion(oid);
        }

        //获取所有的视图
        // GetAllView.getAllView();
    }


}



