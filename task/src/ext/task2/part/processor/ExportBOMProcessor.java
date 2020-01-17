package ext.task2.part.processor;

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
import ext.pi.core.PIAttributeHelper;
import ext.pi.core.PICoreHelper;
import ext.pi.core.PIPartHelper;
import ext.task2.part.bean.BOMBean;
import ext.task2.part.util.BOMUtil;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartUsageLink;
import wt.session.SessionHelper;
import wt.session.SessionServerHelper;
import wt.util.WTException;

import java.io.File;
import java.util.*;

/**
 * 导出虚拟BOM报表数据 processor
 *
 * @author 段鑫扬
 */
public class ExportBOMProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = ExportBOMProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    private String fileName = "";
    private static String TEMPPATH = "";//临时目录
    private static String EXCEL_TEMPLATE_PATH = "";//模板目录
    private static final String DEFAULTVIEW = "Design";
    private static final String XLSX = ".xlsx";
    //虚拟bom的顶层件编号
    private static final String PART_NUMBER = "V0000001";

    static {
        try {
            String codebase = PICoreHelper.service.getCodebase();
            TEMPPATH = codebase + File.separator + "temp";
            File filePath = new File(TEMPPATH);
            if (!filePath.exists()) {
                filePath.mkdir();
            }
            EXCEL_TEMPLATE_PATH = codebase + File.separator + "ext/template/VaBOMPart.xlsx";
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
            List<BOMBean> beans = findBOM();
            File resultFile = new File(TEMPPATH + File.separator + fileName);
            //写excel
            PIExcelWriter writer = new PIExcelWriter(EXCEL_TEMPLATE_PATH);
            writer.setStartRowIndex(1);
            writer.write2Workbook(beans);
            writer.writeOutStream(resultFile);
        } catch (WTException e) {
            e.printStackTrace();
            throw new WTException(e);
        } finally {
            SessionServerHelper.manager.setAccessEnforced(enforced);
        }
        return formResult;
    }

    private List<BOMBean> findBOM() throws WTException {
        //默认类别
        String category = "update";
        //备注
        String IBA_comment = "comment";
        //优先级
        String IBA_priority = "priority";

        Locale locale = SessionHelper.manager.getLocale();
        List<BOMBean> beans = new ArrayList<>();
        //获取顶层件
        WTPart topPart = PIPartHelper.service.findWTPart(PART_NUMBER, "Design");
        Map<WTPart, WTPartUsageLink> bom = BOMUtil.findChildren(topPart);
        Collection<WTPartUsageLink> values = bom.values();
        //所有的link，虚拟Bom中的
        List<WTPartUsageLink> links = new ArrayList<>(values);
        for (WTPartUsageLink link : links) {
            WTPart parentPart = (WTPart) link.getRoleAObject();
            if (parentPart.getNumber().equals(topPart.getNumber())) {
                continue;
            }
            WTPartMaster childPartMaster = (WTPartMaster) link.getRoleBObject();
            WTPart childPart = PIPartHelper.service.findWTPart(childPartMaster, "Design");
            if (BOMUtil.isVirtualPart(childPart)) {
                continue;
            }
            String priority = PIAttributeHelper.service.getDisplayValue(link, IBA_priority, locale);
            String comment = PIAttributeHelper.service.getDisplayValue(link, IBA_comment, locale);
            BOMBean bean = new BOMBean(parentPart.getNumber(), category, childPartMaster.getNumber(), priority, comment);
            beans.add(bean);
        }
        //排序，根据编号
        beans.sort(Comparator.comparing(BOMBean::getPartNumber));
        return beans;
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
