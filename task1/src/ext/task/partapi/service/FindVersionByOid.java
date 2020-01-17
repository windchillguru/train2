package ext.task.partapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.fc.ObjectIdentifier;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.identity.IdentityFactory;
import wt.part.WTPart;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.LocalizableMessage;
import wt.util.WTException;
import wt.vc.Iterated;
import wt.vc.IterationIdentifier;
import wt.vc.VersionIdentifier;
import wt.vc.Versioned;
import wt.vc.views.ViewManageable;
import wt.vc.views.ViewReference;

import java.util.Locale;

/**
 * 部件api测试
 *
 * @author 段鑫扬
 */
public class FindVersionByOid {

    /**
     * 获取指定版本
     * windchill TestPartAPI3 OR:wt.part.WTPart:103521
     *
     * @throws WTException
     */
    public static WTPart findVersionByOid(String oid) throws WTException {
        WTPart versionPart = null;
        NmOid nmOid = NmOid.newNmOid(oid);
        Object refObject = nmOid.getRefObject();
        if (refObject != null && refObject instanceof WTPart) {
            WTPart wtPart = (WTPart) refObject;

            QuerySpec qs = new QuerySpec(WTPart.class);
            SearchCondition sc = new SearchCondition(WTPart.class, "master>number", SearchCondition.EQUAL, wtPart.getNumber());
            qs.appendWhere(sc, new int[]{0});
            //大版本
            qs.appendAnd();
            sc = new SearchCondition(WTPart.class, Versioned.VERSION_IDENTIFIER + "." + VersionIdentifier.VERSIONID
                    , SearchCondition.EQUAL, "A");
            qs.appendWhere(sc, new int[]{0});
            //小版本
            qs.appendAnd();
            sc = new SearchCondition(WTPart.class, Iterated.ITERATION_IDENTIFIER + "." + IterationIdentifier.ITERATIONID
                    , SearchCondition.EQUAL, "2");
            qs.appendWhere(sc, new int[]{0});
            //视图
            qs.appendAnd();
            ObjectIdentifier viewId = PersistenceHelper.getObjectIdentifier(wtPart.getView().getObject());
            sc = new SearchCondition(WTPart.class, ViewManageable.VIEW + "." + ViewReference.KEY
                    , SearchCondition.EQUAL, viewId);
            qs.appendWhere(sc, new int[]{0});

            QueryResult queryResult = PersistenceHelper.manager.find(qs);
            if (queryResult != null && queryResult.hasMoreElements()) {
                versionPart = (WTPart) queryResult.nextElement();
                LocalizableMessage displayIdentifier = IdentityFactory.getDisplayIdentifier(versionPart);
                String localizedMessage = displayIdentifier.getLocalizedMessage(Locale.CHINA);
                System.out.println("localizedMessage = " + localizedMessage);
            }

        }
        return versionPart;
    }
}
