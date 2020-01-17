package ext.task2.part.resource;

import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * @author 段鑫扬
 * @version 2020/1/6
 * 文件夹操作资源
 */
@RBUUID("ext.task2.part.resource.folderRB")
public class folderRB extends WTListResourceBundle {
    @RBEntry("find Parent part")
    public static final String TASK_FINDPARENT_01 = "task.findParent.title";

    @RBEntry("find Parent part")
    public static final String TASK_FINDPARENT_02 = "task.findParent.description";

    @RBEntry("find Parent part")
    public static final String TASK_FINDPARENT_03 = "task.findParent.tooltip";

    @RBEntry("refresh.gif")
    @RBPseudo(false)
    public static final String TASK_FINDPARENT_04 = "task.findParent.icon";

}
