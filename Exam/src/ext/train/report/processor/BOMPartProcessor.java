package ext.train.report.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormProcessingStatus;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.forms.FormResultAction;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.lang.PIFileUtils;
import ext.lang.office.PIExcelWriter;
import ext.pi.PIException;
import ext.pi.core.PICoreHelper;
import ext.pi.core.PIPartHelper;
import ext.train.report.bean.BOMOrderBean;
import ext.train.report.bean.SubPartBean;
import ext.train.report.util.BOMOrderUtil;
import org.apache.log4j.Logger;
import wt.fc.Persistable;
import wt.fc.QueryResult;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.part.WTPartUsageLink;
import wt.session.SessionServerHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 导出BOM报表数据 processor
 *
 * @author 段鑫扬
 */
public class BOMPartProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = BOMPartProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    private String fileName = "";
    private static String TEMPPATH = "";//临时目录
    private static String EXCEL_TEMPLATE_PATH = "";//模板目录
    private static final String DEFAULTVIEW = "Design";
    private static final String XLSX = ".xlsx";

    static {
        try {
            String codebase = PICoreHelper.service.getCodebase();
            TEMPPATH = codebase + File.separator + "temp";
            File filePath = new File(TEMPPATH);
            if (!filePath.exists()) {
                filePath.mkdir();
            }
            EXCEL_TEMPLATE_PATH = codebase + File.separator + "ext/template/BOMPart.xlsx";
            EXCEL_TEMPLATE_PATH = PIFileUtils.formatFilePath(EXCEL_TEMPLATE_PATH);
        } catch (PIException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FormResult doOperation(NmCommandBean commandBean, List<ObjectBean> objectBeans) throws WTException {
        FormResult formResult = super.doOperation(commandBean, objectBeans);
        boolean enforced = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            NmOid actionOid = commandBean.getActionOid();
            Object refObject = actionOid.getRefObject();
            WTPart part = null;
            if (refObject instanceof WTPart) {
                part = (WTPart) refObject;
            }
            if (part == null) {
                throw new WTException("部件为空");
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> doOperation--->" + CLASSNAME + ",part =" + part.getName());
            }
            String fileNamePre = part.getNumber() + "_BOM";
            //下载的文件名
            fileName = fileNamePre + XLSX;
            //查询bom结构
            BOMOrderUtil bomOrderUtil = new BOMOrderUtil(part, true);
            List<BOMOrderBean> beans = bomOrderUtil.findBOMChildren();
            File resultFile = new File(TEMPPATH + File.separator + fileName);
            //写excel
            PIExcelWriter writer = new PIExcelWriter(EXCEL_TEMPLATE_PATH);
            writer.setStartRowIndex(1);
            writer.write2Workbook(beans);
            writer.writeOutStream(resultFile);
        } catch (WTPropertyVetoException e) {
            e.printStackTrace();
            throw new WTException(e);
        } finally {
            SessionServerHelper.manager.setAccessEnforced(enforced);
        }
        return formResult;
    }

    /**
     * 根据部件查询所有子件
     *
     * @param
     * @return
     */
    private void querySubPartBeans(WTPart part, List<SubPartBean> beans, Integer level) throws WTException {
        try {
            QueryResult children = PIPartHelper.service.findChildrenAndLinks(part);
            if (children.size() == 0) {
                return;
            }
            List<WTPart> childList = new ArrayList<>();
            while (children.hasMoreElements()) {
                Object obj = children.nextElement();
                if (obj instanceof Object[]) {
                    Persistable[] objects = (Persistable[]) obj;
                    //数据的索引0的元素是WTPartUsageLink,数组的1元素是子件对象WTPartMaster
                    WTPart childPart = null;
                    if (objects[1] instanceof WTPart) {
                        childPart = (WTPart) objects[1];
                        childList.add(childPart);
                        /*SubPartBean bean = new SubPartBean(childPart);
                        beans.add(bean);
                        querySubPartBeans(childPart, beans);*/
                    }
                }
            }
            if (childList.size() > 0) {
                //层级排序
                Collections.sort(childList, (arg0, arg1) -> arg0.getNumber().compareTo(arg1.getNumber()));
            }
            for (WTPart wtPart : childList) {
                QueryResult usageLinks = PIPartHelper.service.findUsageLinks(part, wtPart);
                WTPartUsageLink link = null;
                if (usageLinks.size() > 0) {
                    link = (WTPartUsageLink) usageLinks.nextElement();
                }
                SubPartBean bean = new SubPartBean(link, wtPart, level);
                beans.add(bean);
                querySubPartBeans(wtPart, beans, level + 1);
            }
        } catch (WTException e) {
            e.printStackTrace();
            throw new WTException(e);
        }
    }

    /**
     * 使浏览器下载文件,不需要弹窗
     *
     * @param formResult
     * @param nmCommandBean
     * @param list
     * @return
     * @throws WTException
     */
    @Override
    public FormResult setResultNextAction(FormResult formResult, NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
        //不需要弹窗
        String str = "window.PTC.util.downloadUrl(\"";
        str = str.concat("temp/"); // codebase的temp目录下的文件
        str = str.concat(fileName);
        str = str.concat("\");");
        LOGGER.debug("next action：" + str);
        formResult.setStatus(FormProcessingStatus.SUCCESS);
        formResult.setJavascript(str);
        formResult.setNextAction(FormResultAction.JAVASCRIPT);
        return formResult;
    }
}
