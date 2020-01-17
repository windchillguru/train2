package ext.example.pg.command;

import ext.example.pg.bean.PGGroupBean;
import ext.example.pg.bean.PGInformationBean;
import ext.example.pg.bean.PGMemberLinkBean;
import ext.example.pg.model.PGGroup;
import ext.example.pg.reader.PGGroupBeanReader;
import ext.example.pg.reader.PGInformationBeanReader;
import ext.example.pg.reader.PGMemberLinkBeanReader;
import ext.example.pg.service.PGInfoHelper;
import org.apache.log4j.Logger;
import wt.fc.WTObject;
import wt.httpgw.GatewayAuthenticator;
import wt.log4j.LogR;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.util.WTException;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author 段鑫扬
 * @version 2019/12/20
 */
public class ImportPGCommand implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = ImportPGCommand.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    public static void main(String[] args) {
        if (args.length >= 0) {
            Class[] clsAry = {String[].class};
            Object[] objAry = {args};
            String method = "importPG";//执行的方法
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

    public static void importPG(String[] args) throws WTException {
        if (args.length == 2) {
            String type = args[0];
            String filePath = args[1];
            if ("1".equals(type)) {
                importPGInfo(filePath);
            } else if ("2".equals(type)) {
                importPGGroup(filePath);
            } else if ("3".equals(type)) {
                importPGMemberLink(filePath);
            }
        }
    }

    /**
     * windchill ext.example.pg.command.ImportPGCommand 2  e:\ImportPGGroup.xlsx
     * 导入组信息
     *
     * @param filePath
     */
    private static void importPGGroup(String filePath) throws WTException {
        PGGroupBeanReader reader = new PGGroupBeanReader(filePath);
        //设置需要解析的Excel 页的索引
        Collection<Integer> integers = new HashSet<>();
        integers.add(0);

        reader.setSheetIndexs(integers);
        reader.setStartRowIndex(1);
        reader.read();

        List<PGGroupBean> beanList = reader.getBeanList();
        PGInfoHelper.service.savePGGroups(beanList);
        System.out.println("导入组成功");
    }

    /**
     * windchill ext.example.pg.command.ImportPGCommand 1  e:\ImportPG.xlsx
     * 导入成员信息
     *
     * @param filePath
     */
    private static void importPGInfo(String filePath) throws WTException {
        PGInformationBeanReader reader = new PGInformationBeanReader(filePath);
        //设置需要解析的Excel 页的索引
        Collection<Integer> integers = new HashSet<>();
        integers.add(0);

        reader.setSheetIndexs(integers);
        reader.setStartRowIndex(1);
        reader.read();

        List<PGInformationBean> beanList = reader.getBeanList();
        PGInfoHelper.service.savePGInformations(beanList);
        System.out.println("导入成功");
    }

    /**
     * windchill ext.example.pg.command.ImportPGCommand 3  e:\ImportPGLink.xlsx
     * 导入link关系
     *
     * @param filePath
     * @throws WTException
     */
    private static void importPGMemberLink(String filePath) throws WTException {
        PGMemberLinkBeanReader reader = new PGMemberLinkBeanReader(filePath);
        //设置需要解析的Excel 页的索引
        Collection<Integer> integers = new HashSet<>();
        integers.add(0);

        reader.setSheetIndexs(integers);
        reader.setStartRowIndex(1);
        reader.read();

        List<PGMemberLinkBean> beanList = reader.getBeanList();
        for (PGMemberLinkBean bean : beanList) {
            if (bean != null) {
                String pgGroupName = bean.getPgGroupName();
                PGGroup pgGroup = PGInfoHelper.service.queryPGGroup(pgGroupName);
                WTObject member = null;
                if (bean.isGroup()) {
                    //如果是组
                    member = PGInfoHelper.service.queryPGGroup(bean.getMember());
                } else {
                    member = PGInfoHelper.service.queryPGInformationByNo(bean.getMember());
                }
                PGInfoHelper.service.saveLink(pgGroup, member, bean.getComments());
            }
        }
        System.out.println("导入link关系成功");
    }

}
