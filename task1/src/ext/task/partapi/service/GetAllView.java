package ext.task.partapi.service;

import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.views.View;
import wt.vc.views.ViewHelper;

/**
 * 部件api测试
 *
 * @author 段鑫扬
 */
public class GetAllView {

    /**
     * 获取所有视图
     *
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static void getAllView() throws WTException, WTPropertyVetoException {
        View[] allViews = ViewHelper.service.getAllViews();
        for (View view : allViews) {
            System.out.println("View = " + view.getName());
        }
    }
}
