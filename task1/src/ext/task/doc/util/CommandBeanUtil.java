package ext.task.doc.util;

import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import wt.doc.WTDocument;
import wt.util.WTException;

/**
 * 获取文档名称的工具类
 *
 * @author 段鑫扬
 */
public class CommandBeanUtil {
    /**
     * 获取文档名称
     *
     * @param commandBean
     * @return
     */
    public static String getDocName(NmCommandBean commandBean) {
        String name = null;
        try {
            NmOid actionOid = commandBean.getActionOid();
            Object refObject = actionOid.getRefObject();
            if (refObject instanceof WTDocument) {
                WTDocument document = (WTDocument) refObject;
                //文档名
                name = document.getName();
            }

        } catch (WTException e) {
            e.printStackTrace();
        }
        return name;
    }
}
