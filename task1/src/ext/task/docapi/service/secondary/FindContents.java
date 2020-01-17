package ext.task.docapi.service.secondary;

import com.ptc.netmarkets.model.NmOid;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.doc.WTDocument;
import wt.fc.QueryResult;
import wt.util.WTException;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 段鑫扬
 */
public class FindContents {

    /**
     * windchill TestDocAPI VR:wt.doc.WTDocument:101741
     *
     * @param oid
     * @return
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static List<ContentItem> findContents(String oid) throws WTException, PropertyVetoException, IOException {
        List<ContentItem> contentItems = new ArrayList<>();
        WTDocument doc = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTDocument) {
            doc = (WTDocument) refObject;

            //将文件内容列表加载到内存中
            doc = (WTDocument) ContentHelper.service.getContents(doc);
            //获取附件列表
            QueryResult qr = ContentHelper.service.getContentsByRole(doc, ContentRoleType.SECONDARY);

            if (qr != null) {
                while (qr.hasMoreElements()) {
                    Object nextElement = qr.nextElement();
                    if (nextElement != null && nextElement instanceof ApplicationData) {
                        ApplicationData applicationData = (ApplicationData) nextElement;
                        System.out.println("applicationData   file name= " + applicationData.getFileName());
                        contentItems.add(applicationData);
                    }
                }
            }
        }
        return contentItems;
    }
}
