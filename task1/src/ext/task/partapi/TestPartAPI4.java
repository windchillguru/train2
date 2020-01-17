package ext.task.partapi;

import ext.task.partapi.service.CopyLink;
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
public class TestPartAPI4 implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = TestPartAPI4.class.getName();
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
        if (args.length > 0) {
           /* String parentOid = args[0];
            String childOid = args[1];*/
            //创建link      bom树
            // CreateLink.createLink(parentOid, childOid);
            //插入link
            //InsertLink.createLink(parentOid, childOid);

           /* String oId = args[0];
            //遍历单层bom
            SingleBomTraverseService.singleBomTraverse(oId);*/
         /*   String baselineOid = args[1];
            //遍历基线
            BaseLineTraverseService.baseLineTraverse(oId, baselineOid);*/

            //比较bom树
           /* String newOid = args[0];
            String oldOid = args[1];
            SingleBomTraverseCompare.compare(newOid, oldOid);*/

            //查找link
            String parentOid = args[0];
            String childOid = args[1];
            //String newChildOid = args[2];
            // FindLink.findLink(parentOid, childOid);
            //改变link属性
            // ChangeLink.changeLink(parentOid, childOid, newChildOid);
            //获取对象单位和数量
            //GetLinkCount.getLinkQuantity(parentOid, childOid);
            //修改对象单位和数量
            //ChangeLinkQuantity.changeLinkQuantity(parentOid, childOid);

            //WTPartUsageLink对象行号获取
            //GetLineNum.getLineNum(parentOid, childOid);
            //WTPartUsageLink对象行号修改
            //ChangeLineNum.changeLineNum(parentOid, childOid);
            //获取位号
            //GetOccur.getOccur(parentOid, childOid);
            //修改位号
            //ChangeOccur.changeOccur(parentOid,childOid);
            //复制link
            String newParentOid = args[2];
            CopyLink.copyLink(parentOid, childOid, newParentOid);
        }

    }


}



