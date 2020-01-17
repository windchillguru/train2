package ext.example.pg.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.util.FeedbackMessage;
import com.ptc.core.ui.resources.FeedbackType;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.misc.NmContext;
import ext.example.pg.model.PGGroup;
import ext.example.pg.model.PGMemberLink;
import ext.example.pg.resource.pgTreeActionRB;
import ext.example.pg.service.PGInfoHelper;
import org.apache.log4j.Logger;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
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
 * 移除成员的processor 处理类
 *
 * @author 段鑫扬
 */
public class RemovePGMemberProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = RemovePGMemberProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = pgTreeActionRB.class.getName();

    @Override
    public FormResult doOperation(NmCommandBean commandBean, List<ObjectBean> objectBeans) throws WTException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>doOperation() ");
        }
        FormResult formResult = super.doOperation(commandBean, objectBeans);
        boolean enforced = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            Locale locale = commandBean.getLocale();
            //选择的所有对象
            ArrayList selected = commandBean.getSelected();
            WTSet deleteMembers = new WTHashSet();
            for (Object obj : selected) {
                if (obj instanceof NmContext) {
                    NmContext nmContext = (NmContext) obj;
                    //在页面上选择的对象，需要删除的成员
                    NmOid targetOid = nmContext.getTargetOid();
                    //当前选中的对象
                    Object removeRef = targetOid.getRefObject();
                    PGGroup group = null;
                    if (removeRef instanceof PGGroup) {
                        //当前对象为组，则为根组，不能移除
                        group = (PGGroup) removeRef;
                        throw new WTException("当前选中的对象：" + group.getPgName() + "为根组，不能移除");
                    }
                    PGMemberLink link = null;
                    if (removeRef instanceof PGMemberLink) {
                        //当前不是根组
                        link = (PGMemberLink) removeRef;
                        WTObject pgMember = link.getPgMember();
                        if (pgMember instanceof PGGroup) {
                            //如果当前选中删除的成员为group，则判断是否有子成员
                            group = (PGGroup) pgMember;
                            hasChildMember(group);
                        }
                        deleteMembers.add(link);
                    }
                }
            }
            if (!deleteMembers.isEmpty()) {
                PersistenceHelper.manager.delete(deleteMembers);
            }
            String successMsg = WTMessage.getLocalizedMessage(RESOURCE, pgTreeActionRB.PGMEMBER_REMOVE_SUCCESS_MSG
                    , new Object[]{}, locale);
            FeedbackMessage fbm = new FeedbackMessage(FeedbackType.SUCCESS, locale
                    , successMsg, null, new String[]{""});
            formResult.addFeedbackMessage(fbm);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        } finally {
            SessionServerHelper.manager.setAccessEnforced(enforced);
        }
        return formResult;
    }

    /**
     * 判断选择删除的组是否有子成员；
     * 如果有抛出异常
     *
     * @param removePGMember
     * @throws WTException
     */
    private void hasChildMember(PGGroup removePGMember) throws WTException {
        QueryResult qr = PGInfoHelper.service.findMembers(removePGMember, false);
        if (qr.hasMoreElements()) {
            throw new WTException("当前选择删除的组：" + removePGMember.getPgName() + ",还存在子成员，不能删除");
        }
    }

}
