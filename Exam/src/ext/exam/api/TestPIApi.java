package ext.exam.api;

import ext.pi.core.PIAttributeHelper;
import ext.pi.core.PICoreHelper;
import ext.pi.core.PIPrincipalHelper;
import org.apache.log4j.Logger;
import wt.fc.WTObject;
import wt.fc.collections.WTSet;
import wt.httpgw.GatewayAuthenticator;
import wt.inf.container.WTContainer;
import wt.log4j.LogR;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTGroup;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.session.SessionHelper;
import wt.util.WTException;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;

/**
 * 测试pi封装包api
 *
 * @author 段鑫扬
 */
public class TestPIApi implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = TestPIApi.class.getName();
    private static final Logger logger = LogR.getLogger(CLASSNAME);

    public static void main(String[] args) {
        if (args.length >= 0) {
            Class[] clsAry = {String[].class};
            Object[] objAry = {args};
            String method = "testPIAPI";//执行的方法
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


    public static void testPIAPI(String[] args) throws WTException, PropertyVetoException, IOException {
        if (args.length > 0) {
            role(args);
        }
    }

    /**
     * 根据Oid 获取部件的2个iba属性
     * windchill ext.exam.api.TestPIApi VR:wt.part.WTPart:124694
     *
     * @param args
     * @throws WTException
     */
    public static void findPartAttr(String[] args) throws WTException {
        String oid = args[0];

        WTObject wtObjectByOid = PICoreHelper.service.getWTObjectByOid(oid);
        WTPart part = null;
        if (wtObjectByOid instanceof WTPart) {
            part = (WTPart) wtObjectByOid;
        }
        if (part == null) {
            throw new WTException("部件为空");
        }
        Locale locale = SessionHelper.manager.getLocale();
        Collection<String> ibaAttributeNames = PIAttributeHelper.service.getIBAAttributeNames(part);
        for (String ibaAttributeName : ibaAttributeNames) {
            System.out.println("ibaAttributeName 内部值 = " + ibaAttributeName);
            String attributeDisplayName = PIAttributeHelper.service.getAttributeDisplayName(part.getClass().getName(), ibaAttributeName, Locale.CHINA);
            System.out.println("attributeDisplayName 显示值= " + attributeDisplayName);
            Object value = PIAttributeHelper.service.getValue(part, ibaAttributeName);
            System.out.println("value 内部值" + value);
            String displayValue = PIAttributeHelper.service.getDisplayValue(part, ibaAttributeName, Locale.CHINA);
            System.out.println("displayValue 显示值 = " + displayValue);
        }
    }

    /**
     * 判断用户是否在用户组
     *
     * @throws WTException
     */
    public static void isGroup() throws WTException {

        boolean xduan = PIPrincipalHelper.service.isChildUser("测试api", "xduan");
        System.out.println("xduan 是否是 测试api 组的用户 :" + xduan);

        boolean Administrator = PIPrincipalHelper.service.isChildUser("测试api", "Administrator");
        System.out.println("Administrator 是否是 测试api 组的用户:" + Administrator);
    }

    public static void role(String[] args) throws WTException {
        String oid = args[0];

        WTObject wtObjectByOid = PICoreHelper.service.getWTObjectByOid(oid);
        WTPart part = null;
        if (wtObjectByOid instanceof WTPart) {
            part = (WTPart) wtObjectByOid;
        }
        if (part == null) {
            throw new WTException("部件为空");
        }
        WTContainer container = part.getContainer();

        //获取角色组
        WTGroup design_engineer = PIPrincipalHelper.service.findWTGroup4Role(container, "DESIGN ENGINEER");
        //获取所有参与者
        WTSet immediateMembers = PIPrincipalHelper.service.findImmediateMembers(design_engineer);
        System.out.println("所有参与者：");
        //持久化对象迭代
        Iterator iterator = immediateMembers.persistableIterator();
        while (iterator.hasNext()) {
            Object immediateMember = iterator.next();
            if (immediateMember instanceof WTUser) {
                WTUser user = (WTUser) immediateMember;
                String name = user.getName();
                System.out.println("用户:" + name);
            }
            if (immediateMember instanceof WTGroup) {
                WTGroup group = (WTGroup) immediateMember;
                String name = group.getName();
                System.out.println("组:" + name);
            }
        }

        System.out.println("所有用户：");
        //当前角色组的所有用户
        Enumeration members = design_engineer.members();
        while (members.hasMoreElements()) {
            Object o = members.nextElement();
            WTUser user = (WTUser) o;
            System.out.println("用户：" + user.getName());
        }

        WTUser xduan = PIPrincipalHelper.service.findWTUserById("xduan");
        boolean childUser = PIPrincipalHelper.service.isChildUser(design_engineer, xduan);
        System.out.println("xduan 是否是 当前产品库设计工程师组下的用户:" + childUser);
    }


}
