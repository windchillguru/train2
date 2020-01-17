package ext.example.pg.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.util.FeedbackMessage;
import com.ptc.core.ui.resources.FeedbackType;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.example.pg.model.PGGroup;
import ext.example.pg.resource.pgGroupActionRB;
import ext.example.pg.service.PGInfoHelper;
import org.apache.log4j.Logger;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTHashSet;
import wt.fc.collections.WTSet;
import wt.log4j.LogR;
import wt.session.SessionServerHelper;
import wt.util.WTException;
import wt.util.WTMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 删除PG组的processor 处理类
 *
 * @author 段鑫扬
 */
public class RemovePGGroupProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = RemovePGGroupProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = pgGroupActionRB.class.getName();

    @Override
    public FormResult doOperation(NmCommandBean commandBean, List<ObjectBean> objectBeans) throws WTException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>doOperation() ");
        }
        Locale locale = commandBean.getLocale();
        FormResult formResult = super.doOperation(commandBean, objectBeans);
        boolean enforced = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            ArrayList<NmOid> selected = commandBean.getNmOidSelected();
            WTSet wtSet = new WTHashSet();
            for (NmOid nmOid : selected) {
                if (nmOid.getRefObject() != null && nmOid.getRefObject() instanceof PGGroup) {
                    PGGroup pgGroup = (PGGroup) nmOid.getRefObject();
                    QueryResult memberLinks = PGInfoHelper.service.findMembers(pgGroup, false);
                    QueryResult groupLinks = PGInfoHelper.service.findGroups(pgGroup, false);
                    //如果当前组包含有其他的组/成员 link关系，则不能删除
                    if (memberLinks.hasMoreElements() || groupLinks.hasMoreElements()) {
                        throw new WTException("pgGroup:" + pgGroup.getPgName() + "正在使用，不能删除");
                    }
                    wtSet.add(pgGroup);
                }
            }
            if (!wtSet.isEmpty()) {
                PersistenceHelper.manager.delete(wtSet);
            }
            String successMsg = WTMessage.getLocalizedMessage(RESOURCE, pgGroupActionRB.PGGROUP_REMOVE_SUCCESS_MSG
                    , new Object[]{}, locale);
            FeedbackMessage fbm = new FeedbackMessage(FeedbackType.SUCCESS, locale
                    , successMsg, null, new String[]{""});
            formResult.addFeedbackMessage(fbm);
        } finally {
            SessionServerHelper.manager.setAccessEnforced(enforced);
        }

        return formResult;
    }
}
