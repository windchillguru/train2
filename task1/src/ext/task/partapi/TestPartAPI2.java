package ext.task.partapi;

import ext.task.partapi.service.ChangePartMasterNumAndName;
import org.apache.log4j.Logger;
import wt.fc.PersistenceHelper;
import wt.httpgw.GatewayAuthenticator;
import wt.log4j.LogR;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.views.View;
import wt.vc.views.ViewHelper;
import wt.vc.views.ViewReference;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * 测试部件相关的API
 * 创建部件 自定义编码
 *
 * @author 段鑫扬
 */
public class TestPartAPI2 implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = TestPartAPI2.class.getName();
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


    public static void testPartAPI(String[] args) throws WTException, WTPropertyVetoException {
        //创建部件
        //createPartWithORI();
        // CreateSoftPart2.CreateSoftPartWithORI();
        // CreatePartAndSetAttr.CreatePartAndSetAttr();
        /*if (args.length > 0) {
            String num = args[0];
            WTPartMaster master = FindPartMasterByNum.findPartMasterByNum(num);
            //打印
            System.out.println("master.display=" + master.getDisplayIdentifier());
            //获取WTPartMaster的属性，并输出
            if(master != null) {
                String number = master.getNumber();
                System.out.println("number = " + number);
                String name = master.getName();
                System.out.println("name = " + name);
                Timestamp createTimestamp = master.getCreateTimestamp();
                //创建时间
                System.out.println("createTimestamp = " + createTimestamp);
                QuantityUnit defaultUnit = master.getDefaultUnit();
                //单位
                System.out.println("defaultUnit = " + defaultUnit);
            }
        }*/
        ChangePartMasterNumAndName.changePartMasterNumAndName();
    }

    /**
     * 创建部件 使用初始化规则
     *
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    private static void createPartWithORI() throws WTException, WTPropertyVetoException {
        WTPart part = WTPart.newWTPart();
        part.setName("CreatePartWithORI");
        View viewObj = ViewHelper.service.getView("Design");
        part.setView(ViewReference.newViewReference(viewObj));//设置视图
        part = (WTPart) PersistenceHelper.manager.save(part);
        if (logger.isDebugEnabled()) {
            logger.debug("Create Part Successfully, part=" + part.getDisplayIdentifier());
        }
    }

}



