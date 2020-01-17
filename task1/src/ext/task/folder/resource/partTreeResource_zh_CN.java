package ext.task.folder.resource;

import wt.util.resource.RBEntry;
import wt.util.resource.RBPseudo;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * 部件树的资源类
 *
 * @author 段鑫扬
 */
@RBUUID("partTreeResource")
public class partTreeResource_zh_CN extends WTListResourceBundle {
    @RBEntry("部件树")
    public static final String TASKFOLDER_PARTTREE_TITLE = "taskFolder.partTree.title";

    @RBEntry("部件树")
    public static final String TASKFOLDER_PARTTREE_DESC = "taskFolder.partTree.description";

    @RBEntry("部件树")
    public static final String TASKFOLDER_PARTTREE_TOOLTIP = "taskFolder.partTree.tooltip";

    @RBEntry("iterate.png")
    @RBPseudo(false)
    public static final String TASKFOLDER_PARTTREE_ICON = "taskFolder.partTree.icon";

    @RBEntry("部件树")
    public static final String PART_TREE_LABEL = "part tree label";

}
