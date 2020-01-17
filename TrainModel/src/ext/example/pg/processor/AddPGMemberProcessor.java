package ext.example.pg.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.netmarkets.util.misc.NmContext;
import ext.example.pg.model.PGGroup;
import ext.example.pg.model.PGInformation;
import ext.example.pg.model.PGMemberLink;
import ext.example.pg.service.PGInfoHelper;
import org.apache.log4j.Logger;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.log4j.LogR;
import wt.session.SessionServerHelper;
import wt.util.WTException;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加成员的processor 处理类
 *
 * @author 段鑫扬
 */
public class AddPGMemberProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = AddPGMemberProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    @Override
    public FormResult doOperation(NmCommandBean commandBean, List<ObjectBean> objectBeans) throws WTException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>doOperation() ");
        }
        FormResult formResult = super.doOperation(commandBean, objectBeans);
        boolean enforced = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            NmOid actionOid = commandBean.getActionOid();
            Object refObject = actionOid.getRefObject();//ext.example.pg.model.PGMemberLink:128017  上一级的link
            //当前对象,link的roleA
            PGGroup group = null;
            if (refObject instanceof PGGroup) {
                group = (PGGroup) refObject;
            }
            PGMemberLink link = null;
            if (refObject instanceof PGMemberLink) {
                //当前不是根组
                link = (PGMemberLink) refObject;
                WTObject pgMember = link.getPgMember();
                if (pgMember != null && pgMember instanceof PGGroup) {
                    group = (PGGroup) pgMember;
                }
            }
            if (group == null) {
                throw new WTException("当前对象不能添加成员");
            }
            ArrayList selected = commandBean.getSelected();
            Object obj = selected.get(0);
            //添加的成员,link的roleB
            if (obj instanceof NmContext) {
                NmContext nmContext = (NmContext) obj;
                //在页面上选择的对象，需要添加的成员 roleB
                NmOid targetOid = nmContext.getTargetOid();
                Object addRef = targetOid.getRefObject();
                if (addRef instanceof PGGroup) {
                    //添加的成员是PG组
                    PGGroup addPGMember = (PGGroup) addRef;
                    hasUsed(addPGMember);
                    PGInfoHelper.service.createPGMemberLink(group, addPGMember, addPGMember.getComments());
                }

                if (addRef instanceof PGInformation) {
                    //添加的成员是PG信息
                    PGInformation addPGMember = (PGInformation) addRef;
                    hasUsed(addPGMember);
                    PGInfoHelper.service.createPGMemberLink(group, addPGMember, addPGMember.getComments());
                }
            }

        } finally {
            SessionServerHelper.manager.setAccessEnforced(enforced);
        }
        return formResult;
    }

    /**
     * 验证当前添加的成员是否被其他组关联过，
     * 如果已经和其他组关联过，则抛出异常：当前成员以及被关联。
     *
     * @param addPGMember
     * @throws WTException
     */
    private void hasUsed(WTObject addPGMember) throws WTException {
        //查询当前成员是否已经被其他组使用过
        QueryResult qr = PGInfoHelper.service.findGroups(addPGMember, false);
        if (qr.hasMoreElements()) {
            String pgName = "";
            if (addPGMember instanceof PGGroup) {
                pgName = (String) ((PGGroup) addPGMember).getPgName();
            }
            if (addPGMember instanceof PGInformation) {
                pgName = (String) ((PGInformation) addPGMember).getPgName();
            }
            throw new WTException("当前member:" + pgName + "已经被关联另一个组。");
        }
    }
}
