package ext.task.docapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.content.*;
import wt.doc.WTDocument;
import wt.fc.QueryResult;
import wt.util.WTException;

import java.beans.PropertyVetoException;

/**
 * @author 段鑫扬
 */
public class FindPrimary {
    /**
     * windchill TestDocAPI VR:wt.doc.WTDocument:101741
     *
     * @param oid
     * @return
     * @throws WTException
     * @throws PropertyVetoException
     */
    public static ContentItem findPrimary(String oid) throws WTException, PropertyVetoException {
        ContentItem primary = null;
        WTDocument document = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTDocument) {
            document = (WTDocument) refObject;

            ContentHolder contents = ContentHelper.service.getContents(document);
            //获取主文件
            QueryResult qr = ContentHelper.service.getContentsByRole(contents, ContentRoleType.PRIMARY);
            if (qr != null && qr.hasMoreElements()) {
                primary = (ContentItem) qr.nextElement();
                if (primary != null && primary instanceof ApplicationData) {
                    ApplicationData applicationData = (ApplicationData) primary;
                    String fileName = applicationData.getFileName();
                    System.out.println("fileName = " + fileName);
                }
            }
        }
        return primary;
    }
}
