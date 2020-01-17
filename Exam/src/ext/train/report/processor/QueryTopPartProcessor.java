package ext.train.report.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormProcessingStatus;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.components.forms.FormResultAction;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.lang.PIExcelUtils;
import ext.lang.PIFileUtils;
import ext.lang.PIStringUtils;
import ext.lang.office.PIExcelWriter;
import ext.pi.PIException;
import ext.pi.core.PICoreHelper;
import ext.pi.core.PIPartHelper;
import ext.train.report.bean.TopPartBean;
import ext.train.report.util.PartUtil;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import wt.fc.collections.WTArrayList;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.session.SessionServerHelper;
import wt.util.WTException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 查询顶级物料
 *
 * @author 段鑫扬
 */
public class QueryTopPartProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = QueryTopPartProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    private String fileName = "";
    private static String TEMPPATH = "";//临时目录
    private static String EXCEL_TEMPLATE_PATH = "";//模板目录
    private static final String DEFAULTVIEW = "Design";
    private static final String RESULT = "Result";
    private static final String XLSX = ".xlsx";

    static {
        try {
            String codebase = PICoreHelper.service.getCodebase();
            TEMPPATH = codebase + File.separator + "temp";
            File filePath = new File(TEMPPATH);
            if (!filePath.exists()) {
                filePath.mkdir();
            }
            EXCEL_TEMPLATE_PATH = codebase + File.separator + "ext/template/TopPartResult.xlsx";
            EXCEL_TEMPLATE_PATH = PIFileUtils.formatFilePath(EXCEL_TEMPLATE_PATH);
        } catch (PIException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FormResult doOperation(NmCommandBean commandBean, List<ObjectBean> objectBeans) throws WTException {
        FormResult formResult = super.doOperation(commandBean, objectBeans);
        boolean enforced = SessionServerHelper.manager.setAccessEnforced(false);
        File file = null;
        try {
            HashMap<String, Object> fileUploadMap = (HashMap<String, Object>) commandBean.getMap().get("fileUploadMap");
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> fileUploadMap=" + fileUploadMap);
            }
            file = (File) fileUploadMap.get("file");
            //上传文件名
            String inputFileName = commandBean.getTextParameter("file");
            //需要查询的物料的id集合
            List<String> partNos = new ArrayList<>();
            if (inputFileName.toLowerCase().endsWith(".txt")) {
                //txt
                fetchPartNos4Txt(file, partNos);
            } else {
                //excel
                fetchPartNos4Excel(file, partNos);
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> 需要查询的数据：" + partNos);
            }
            if (partNos.isEmpty()) {
                throw new WTException("没有获得任何需要查询的数据");
            }
            String fileNamePre = inputFileName.substring(0, inputFileName.lastIndexOf("."));
            //下载的文件名
            fileName = fileNamePre + RESULT + XLSX;

            List<TopPartBean> beans = queryTopPartBeans(partNos);
            File resultFile = new File(TEMPPATH + File.separator + fileName);
            //写excel
            PIExcelWriter writer = new PIExcelWriter(EXCEL_TEMPLATE_PATH);
            writer.setStartRowIndex(1);
            writer.write2Workbook(beans);
            writer.writeOutStream(resultFile);
        } finally {
            //删除上传文件的临时文件
            if (file != null && file.exists()) {
                file.delete();
            }
            SessionServerHelper.manager.setAccessEnforced(enforced);
        }
        return formResult;
    }

    /**
     * 查询顶层物料的数据
     *
     * @param partNos
     * @return
     */
    private List<TopPartBean> queryTopPartBeans(List<String> partNos) throws WTException {
        List<TopPartBean> beans = new ArrayList<>();
        for (String partNo : partNos) {
            WTPart part = PIPartHelper.service.findWTPart(partNo, DEFAULTVIEW);

            if (part == null) {
                TopPartBean bean = new TopPartBean(partNo, null, null);
                beans.add(bean);
            } else {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(">>>> part.display=" + part.getDisplayIdentifier());
                }
                WTArrayList topParts = PartUtil.findTopParts(part);
                int size = topParts.size();
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        WTPart wtPart = (WTPart) topParts.getPersistable(i);
                        TopPartBean bean = new TopPartBean(partNo, part, wtPart);
                        beans.add(bean);
                    }
                } else {
                    TopPartBean bean = new TopPartBean(partNo, part, null);
                    beans.add(bean);
                }
            }
        }
        return beans;
    }

    /**
     * 读取excel文件数据
     *
     * @param file
     * @param partNos
     */
    private void fetchPartNos4Excel(File file, List<String> partNos) throws WTException {
        try {
            Workbook book = PIExcelUtils.getWorkbook(file);
            //读取第一页
            Sheet sheet = book.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>lastRowNum= " + lastRowNum);
            }

            Row row = null;
            String cellValueStr = "";
            for (int i = 1; i < lastRowNum; i++) {//从第二行开始读
                row = sheet.getRow(i);
                Object cellValue = PIExcelUtils.getCellValue(row, 0);
                //单元格数据
                cellValueStr = cellValue == null ? "" : cellValue.toString().trim();
                if (PIStringUtils.hasText(cellValueStr)) {
                    partNos.add(cellValueStr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
    }

    /**
     * 读取txt文件 获取部件number
     *
     * @param file
     * @param partNos
     */
    private void fetchPartNos4Txt(File file, List<String> partNos) throws WTException {
        FileInputStream fis = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(file);
            //txt
            InputStreamReader isr = new InputStreamReader(fis, "GBK");
            List<String> contents = new ArrayList<>();
            br = new BufferedReader(isr);

            String temp = null;
            while ((temp = br.readLine()) != null) {
                contents.add(temp);
            }
            br.close();
            int size = contents.size();
            for (int i = 1; i < size; i++) {//从第二行开始读
                String content = contents.get(i);
                if (!PIStringUtils.hasText(content)) {
                    LOGGER.error("line:" + (i + 1) + "is blank");
                    continue;
                }
                if (!partNos.contains(content)) {
                    partNos.add(content.trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 使浏览器下载文件
     *
     * @param formResult
     * @param nmCommandBean
     * @param list
     * @return
     * @throws WTException
     */
    @Override
    public FormResult setResultNextAction(FormResult formResult, NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {

        String str = "window.opener.PTC.util.downloadUrl(\"";
        str = str.concat("temp/"); // codebase的temp目录下的文件
        str = str.concat(fileName);
        str = str.concat("\");window.close();");
        LOGGER.debug("next action：" + str);
        formResult.setStatus(FormProcessingStatus.SUCCESS);
        formResult.setJavascript(str);
        formResult.setNextAction(FormResultAction.JAVASCRIPT);

        return formResult;
    }
}
