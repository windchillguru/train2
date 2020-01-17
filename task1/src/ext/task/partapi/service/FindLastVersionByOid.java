package ext.task.partapi.service;

import com.ptc.netmarkets.model.NmOid;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.identity.IdentityFactory;
import wt.part.WTPart;
import wt.part.WTPartStandardConfigSpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.LocalizableMessage;
import wt.util.WTException;
import wt.vc.config.ConfigSpec;
import wt.vc.views.View;

import java.util.Locale;

/**
 * 部件api测试
 *
 * @author 段鑫扬
 */
public class FindLastVersionByOid {

    /**
     * 获取最新版本
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
            SearchCondition sc = new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.EQUAL, wtPart.getNumber());
            qs.appendWhere(sc, new int[]{0});

            View view = (View) wtPart.getView().getObject();
            ConfigSpec configSpec = WTPartStandardConfigSpec.newWTPartStandardConfigSpec(view, null);
            qs = configSpec.appendSearchCriteria(qs);

            QueryResult queryResult = PersistenceHelper.manager.find(qs);
            if (queryResult != null && queryResult.hasMoreElements()) {
                versionPart = (WTPart) queryResult.nextElement();
                LocalizableMessage displayIdentifier = IdentityFactory.getDisplayIdentifier(versionPart);
                String localizedMessage = displayIdentifier.getLocalizedMessage(Locale.CHINA);
                System.out.println("localizedMessage = " + localizedMessage);
            }

        }
        return versionPart;//104644
    }
}
