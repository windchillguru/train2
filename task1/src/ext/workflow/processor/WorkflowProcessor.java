package ext.workflow.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.util.FeedbackMessage;
import com.ptc.core.ui.resources.FeedbackType;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.pi.core.PIWorkflowHelper;
import ext.workflow.resource.workflowRB;
import org.apache.log4j.Logger;
import wt.fc.PersistenceHelper;
import wt.inf.container.WTContained;
import wt.log4j.LogR;
import wt.org.WTOrganization;
import wt.org.WTPrincipal;
import wt.org.WTPrincipalReference;
import wt.part.WTPart;
import wt.session.SessionHelper;
import wt.session.SessionServerHelper;
import wt.team.TeamReference;
import wt.util.WTException;
import wt.util.WTMessage;
import wt.workflow.definer.WfProcessTemplate;
import wt.workflow.engine.ProcessData;
import wt.workflow.engine.WfEngineHelper;
import wt.workflow.engine.WfEngineServerHelper;
import wt.workflow.engine.WfProcess;

import java.util.List;
import java.util.Locale;

/**
 * 启动工作流
 *
 * @author 段鑫扬
 * @version 2019/12/25
 */
public class WorkflowProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = WorkflowProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String RESOURCE = workflowRB.class.getName();
    private static final String WORKFLOW_NAME = "test";

    /**
     * 启动流程
     *
     * @param commandBean
     * @param objectBeans
     * @return
     * @throws WTException
     */
    @Override
    public FormResult doOperation(NmCommandBean commandBean, List<ObjectBean> objectBeans) throws WTException {
        boolean enforce = SessionServerHelper.manager.setAccessEnforced(false);
        FormResult formResult = super.doOperation(commandBean, objectBeans);
        try {
            Locale locale = commandBean.getLocale();
            Object actionObj = commandBean.getActionOid().getRefObject();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>actionObj=" + actionObj);
            }
            if (actionObj instanceof WTPart) {
                WTPart part = (WTPart) actionObj;
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(">>>PBO:" + part.getDisplayIdentifier());
                }

                //查询工作流模板
                WTOrganization organization = ((WTContained) part).getContainer().getOrganization();
                WfProcessTemplate wfprocessTemplate = PIWorkflowHelper.service.findWfProcessTemplate(WORKFLOW_NAME, organization);
                if (wfprocessTemplate == null) {
                    throw new WTException("流程模板：" + WORKFLOW_NAME + "不存在");
                }

                WTPrincipal wtprincipal = SessionHelper.manager.getPrincipal();
                WTPrincipalReference wtprincipalreference = WTPrincipalReference.newWTPrincipalReference(wtprincipal);
                //流程实例的名称
                String processName = WORKFLOW_NAME + "_" + part.getDisplayIdentifier();

                //创建流程实例
                WfProcess process = WfEngineHelper.service.createProcess(wfprocessTemplate, part.getLifeCycleTemplate(), part.getContainerReference());
                //设置部件对象
                WfEngineServerHelper.service.setPrimaryBusinessObject(process, part);
                //设置名称和描述
                process.setName(processName);
                process.setDescription(wfprocessTemplate.getDescription());

                //存储流程变量等信息
                ProcessData processdata = process.getContext();
                //设置创建者
                process.setCreator(wtprincipalreference);

                TeamReference team = part.getTeamId();
                process.setTeamId(team);//设置流程团队

                process = (WfProcess) PersistenceHelper.manager.save(process);//保存
                //启动流程
                process.start(processdata, true, part.getContainerReference());
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(">>>Start process:" + process.getName() + " Successfully");
                }
            }
            String successMsg = WTMessage.getLocalizedMessage(RESOURCE, workflowRB.START_WORKFLOW_SUCCESS_MSG
                    , new Object[]{}, locale);
            FeedbackMessage feedbackMessage = new FeedbackMessage(FeedbackType.SUCCESS, locale, successMsg, null, new String[]{""});
            formResult.addFeedbackMessage(feedbackMessage);
        } catch (WTException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        } finally {
            SessionServerHelper.manager.setAccessEnforced(enforce);
        }

        return formResult;
    }

}
