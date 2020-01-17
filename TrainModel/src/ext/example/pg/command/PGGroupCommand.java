package ext.example.pg.command;

import com.ptc.core.components.forms.DynamicRefreshInfo;
import com.ptc.core.components.forms.FormProcessingStatus;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.forms.FormResultAction;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.example.pg.model.PGGroup;
import ext.example.pg.service.PGInfoHelper;
import org.apache.log4j.Logger;
import wt.fc.Persistable;
import wt.log4j.LogR;
import wt.method.RemoteAccess;
import wt.util.WTException;

import java.io.Serializable;

/**
 * 组操作，启用/禁用组
 *
 * @author 段鑫扬
 * @version 2019/12/23
 */
public class PGGroupCommand implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = PGGroupCommand.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    /**
     * 启用组
     *
     * @param commandBean
     * @return
     * @throws WTException
     */
    public static FormResult enableGroup(NmCommandBean commandBean) throws WTException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> enableGroup..");
        }
        FormResult formResult = new FormResult();
        NmOid nmOid = commandBean.getActionOid();
        if (nmOid == null) {
            LOGGER.error("enableGroup: called with no target objects.");
            throw new WTException();
        } else {
            PGGroup pgGroup = null;
            if (nmOid.getRefObject() instanceof PGGroup) {
                pgGroup = (PGGroup) nmOid.getRefObject();
            }
            //启用
            pgGroup = PGInfoHelper.service.enablePGGroups(pgGroup);
            formResult.addDynamicRefreshInfo(new DynamicRefreshInfo((Persistable) pgGroup, (Persistable) pgGroup, "U"));
            formResult.setStatus(FormProcessingStatus.SUCCESS);
            formResult.setNextAction(FormResultAction.NONE);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("enableGroup: " + pgGroup.getPgName());
                LOGGER.debug("enableGroup: returning formResult: " + formResult);
                LOGGER.debug("dynamicRefreshInfo: " + formResult.getDynamicRefreshInfo());
            }
            return formResult;
        }
    }

    /**
     * 禁用组
     *
     * @param var0
     * @return
     * @throws WTException
     */
    public static final FormResult disableGroup(NmCommandBean var0) throws WTException {
        LOGGER.trace("disableRule: Entering method");
        FormResult formResult = new FormResult();
        NmOid nmOid = var0.getActionOid();
        if (nmOid == null) {
            LOGGER.error("disableGroup: called with no target objects.");
            throw new WTException();
        } else {
            PGGroup pgGroup = null;
            if (nmOid.getRefObject() instanceof PGGroup) {
                pgGroup = (PGGroup) nmOid.getRefObject();
            }
            //禁用
            pgGroup = PGInfoHelper.service.disablePGGroups(pgGroup);
            formResult.addDynamicRefreshInfo(new DynamicRefreshInfo((Persistable) pgGroup, (Persistable) pgGroup, "U"));
            formResult.setStatus(FormProcessingStatus.SUCCESS);
            formResult.setNextAction(FormResultAction.NONE);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("disableRule: " + pgGroup.getPgGroupName());
                LOGGER.debug("disableRule: returning formResult: " + formResult);
                LOGGER.debug("dynamicRefreshInfo: " + formResult.getDynamicRefreshInfo());
            }

            return formResult;
        }
    }

}
