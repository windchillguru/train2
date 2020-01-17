package ext.task2.part.util;

import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.pi.core.PIContainerHelper;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTArrayList;
import wt.folder.SubFolder;
import wt.part.WTPart;
import wt.part.WTPartUsageLink;
import wt.util.WTException;

/**
 * @author 段鑫扬
 * @version 2020/1/6
 * 验证文件夹下的部件 没有上层部件的验证是否是成品 或者 它的上层部件在当前文件夹
 */
public class PartUtil {
    /**
     * 验证当前文件夹的所有部件
     * 它们有上层部件且上层部件在当前文件夹
     * 如果没有上层部件，则必须是成品
     *
     * @param commandBean
     * @return
     * @throws WTException
     */
    public static String checkPart(NmCommandBean commandBean) throws WTException {
        NmOid actionOid = commandBean.getActionOid();
        Object refObject = actionOid.getRefObject();
        SubFolder folder = null;
        if (refObject instanceof SubFolder) {
            folder = (SubFolder) refObject;
        }
        if (folder == null) {
            return "";
        }
        StringBuilder info = new StringBuilder();
        QueryResult folderContents = PIContainerHelper.service.findFolderContents(folder);
        WTArrayList folderContentList = new WTArrayList(folderContents);
        while (folderContents.hasMoreElements()) {
            Object nextElement = folderContents.nextElement();
            if (nextElement instanceof WTPart) {
                WTPart part = (WTPart) nextElement;
                // roleB, roleA角色，link.class,  false 获取link对象，true 获取 roleA对象
                QueryResult qr = PersistenceHelper.manager.navigate(part.getMaster(), WTPartUsageLink.ROLE_AOBJECT_ROLE, WTPartUsageLink.class, true);
                if (qr.hasMoreElements()) {
                    WTPart parentPart = (WTPart) qr.nextElement();
                    boolean contains = folderContentList.contains(parentPart);
                    if (!contains) {
                        //上层部件不在当前文件夹
                        info.append("部件：").append(part.getNumber()).append("的上层部件不在")
                                .append(folder.getFolderPath()).append("下<br/>");
                    }
                } else {
                    //当前部件没有上层部件
                    boolean endItem = part.isEndItem();
                    if (!endItem) {
                        info.append("部件：").append(part.getNumber()).append("没有上层，且不是成品<br/>");
                    }
                }
            }
        }
        return info.toString();
    }

}
