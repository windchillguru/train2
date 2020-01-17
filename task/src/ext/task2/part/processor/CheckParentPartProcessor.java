package ext.task2.part.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormProcessingStatus;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.forms.FormResultAction;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.lang.PIStringUtils;
import ext.pi.core.PIContainerHelper;
import ext.task2.part.resource.folderRB;
import org.apache.log4j.Logger;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTArrayList;
import wt.folder.SubFolder;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.part.WTPartUsageLink;
import wt.util.WTException;

import java.util.List;

/**
 * @author 段鑫扬
 * @version 2020/1/6
 * <p>
 * 检查当前文件夹的有部件的上层部件是否在当前文件夹/或者是成品
 */
public class CheckParentPartProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = CheckParentPartProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = folderRB.class.getName();
    private StringBuilder info = new StringBuilder();

    /**
     * 检查当前文件夹的有部件的上层部件是否在当前文件夹/或者是成品 ,否则抛出异常
     *
     * @param commandBean
     * @param objectBeans
     * @return
     * @throws WTException
     */
    @Override
    public FormResult doOperation(NmCommandBean commandBean, List<ObjectBean> objectBeans) throws WTException {
        FormResult formResult = super.doOperation(commandBean, objectBeans);
        NmOid actionOid = commandBean.getActionOid();
        Object refObject = actionOid.getRefObject();
        SubFolder folder = null;
        if (refObject instanceof SubFolder) {
            folder = (SubFolder) refObject;
        }
        if (folder == null) {
            return formResult;
        }

        QueryResult folderContents = PIContainerHelper.service.findFolderContents(folder);
        WTArrayList folderContentList = new WTArrayList(folderContents);
        while (folderContents.hasMoreElements()) {
            Object nextElement = folderContents.nextElement();
            if (nextElement instanceof WTPart) {
                WTPart part = (WTPart) nextElement;
                // roleB, roleA角色，link.class,  false 获取link对象，true 获取 roleA对象
                QueryResult qr = PersistenceHelper.manager.navigate(part.getMaster(), WTPartUsageLink.ROLE_AOBJECT_ROLE, WTPartUsageLink.class, true);
                if (qr.size() > 0) {
                    //有上层
                    //默认上层不在当前文件夹
                    boolean flag = false;
                    while (qr.hasMoreElements()) {
                        WTPart parentPart = (WTPart) qr.nextElement();
                        boolean contains = folderContentList.contains(parentPart);
                        if (contains) {
                            //当有一个上层在当前文件夹时
                            flag = true;
                        }
                    }
                    if (!flag) {
                        //所有上层部件都不在当前文件夹
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
        return formResult;
    }

    @Override
    public FormResult setResultNextAction(FormResult formResult, NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
        //执行的js
        String js = "";
        if (PIStringUtils.hasText(info.toString())) {
            js = "Ext.MessageBox.show({ title:\"注意\", msg:\""
                    + info.toString() + "\", width:500,buttons:Ext.MessageBox.OK});";
        } else {
            js = "Ext.MessageBox.show({ title:\"注意\", msg:\"检查通过\"" +
                    ", width:300,buttons:Ext.MessageBox.OK});";
        }
        formResult.setJavascript(js);
        formResult.setStatus(FormProcessingStatus.SUCCESS);
        formResult.setNextAction(FormResultAction.JAVASCRIPT);
        return formResult;
    }
}
