package ext.task2.part.resource;

import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * @author 段鑫扬
 * @version 2020/1/15
 * 部件操作资源文件
 */
@RBUUID("ext.task2.part.resource.partRB")
public class partRB_zh_CN extends WTListResourceBundle {
    @RBEntry("导出替换BOM")
    public static final String TASK_EXPORTBOM_01 = "task.exportBOM.title";

    @RBEntry("导出替换BOM")
    public static final String TASK_EXPORTBOM_02 = "task.exportBOM.description";

    @RBEntry("导出替换BOM")
    public static final String TASK_EXPORTBOM_03 = "task.exportBOM.tooltip";

    @RBEntry("export.gif")
    @RBPseudo(false)
    public static final String TASK_EXPORTBOM_04 = "task.exportBOM.icon";

    @RBEntry("导入替换BOM")
    public static final String TASK_EXPORTBOM_05 = "task.importBOM.title";

    @RBEntry("导入替换BOM")
    public static final String TASK_EXPORTBOM_06 = "task.importBOM.description";

    @RBEntry("导入替换BOM")
    public static final String TASK_EXPORTBOM_07 = "task.importBOM.tooltip";

    @RBEntry("import.gif")
    @RBPseudo(false)
    public static final String TASK_EXPORTBOM_08 = "task.importBOM.icon";

    @RBEntry("excel文件")
    public static final String TASK_EXPORTBOM_09 = "TASK_EXPORTBOM_09";

    @RBEntry("请选择excel文件")
    public static final String SELECT_FILE_NONE_ERR = "SELECT_FILE_NONE_ERR";

    @RBEntry("文件格式必须为.xlsx结尾")
    public static final String SELECT_FILE_INVALID_ERR = "SELECT_FILE_INVALID_ERR";
}
