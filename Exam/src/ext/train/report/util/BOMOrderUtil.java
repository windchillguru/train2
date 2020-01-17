package ext.train.report.util;

import ext.train.report.bean.BOMOrderBean;
import org.apache.log4j.Logger;
import wt.fc.Persistable;
import wt.fc.QueryResult;
import wt.log4j.LogR;
import wt.method.RemoteAccess;
import wt.part.*;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.views.View;

import java.io.Serializable;
import java.util.*;

/**
 * @author 段鑫扬
 */
public class BOMOrderUtil implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = BOMOrderUtil.class.getName();
    private static final Logger logger = LogR.getLogger(CLASSNAME);

    private WTPart topPart;//顶层部件
    private boolean returnTopPart;//是否返回顶层部件

    public BOMOrderUtil(WTPart topPart, boolean returnTopPart) {
        this.topPart = topPart;
        this.returnTopPart = returnTopPart;
    }

    /**
     * 返回所有bom的bean
     *
     * @return
     * @throws WTException
     */
    public List<BOMOrderBean> findBOMChildren() throws WTException, WTPropertyVetoException {
        List<BOMOrderBean> beans = new ArrayList<>();
        if (returnTopPart) {
            BOMOrderBean bean = new BOMOrderBean(null, topPart, 0);
        }
        findChildren(0, this.topPart, beans);
        return beans;
    }

    private void findChildren(int level, WTPart parentPart, List<BOMOrderBean> beans) throws WTException, WTPropertyVetoException {
        if (parentPart == null) {
            return;
        }
        level = level + 1;//层级加1
        Stack<BOMOrderBean> stack = new Stack<>(); //存储需要遍历的bean
        //将顶层bean放进栈
        stack.push(new BOMOrderBean(null, parentPart, 0));
        while (!stack.isEmpty()) {
            BOMOrderBean currentBean = stack.pop(); // 当前要查询的部件bomBean
            //将当前的bean 放入集合
            if (!beans.contains(currentBean)) {
                beans.add(currentBean);
            }
            WTPart currentPart = currentBean.getPart();//当前遍历的部件
            System.out.println("traverse current Part identifier = " + currentPart.getDisplayIdentifier());
            // 获取当前部件的视图
            View view = (View) currentPart.getView().getObject();
            WTPartConfigSpec config = WTPartHelper.service.findWTPartConfigSpec();
            WTPartStandardConfigSpec standard = config.getStandard();
            standard.setView(view);
            // 查询
            QueryResult qr = WTPartHelper.service.getUsesWTParts(currentPart, standard);
            List<BOMOrderBean> childBeans = new ArrayList<>();
            while (qr.hasMoreElements()) {
                Persistable[] persistables = (Persistable[]) qr.nextElement();
                // 获取到WTPartUsageLink对象
                WTPartUsageLink link = (WTPartUsageLink) persistables[0];
                Persistable childPersistable = persistables[1];
                WTPart child = null;
                if (childPersistable instanceof WTPart) {
                    child = (WTPart) childPersistable;
                    BOMOrderBean childBean = new BOMOrderBean(link, child, currentBean.getLevel() + 1);
                    childBeans.add(childBean);
                }
            }
            if (childBeans.size() > 0) {
                //层级排序
                //逆序
                childBeans.sort((arg0, arg1) -> arg1.getPartNumber().compareTo(arg0.getPartNumber()));
            }
            stack.addAll(childBeans);
        }
    }
}
