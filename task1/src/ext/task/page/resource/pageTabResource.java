package ext.task.page.resource;


import wt.util.resource.RBEntry;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * 客制化页面的资源文件
 *
 * @author 段鑫扬
 */
@RBUUID("pageTabResource")
public final class pageTabResource extends WTListResourceBundle {
    @RBEntry("pageTab")
    public static final String OBJECT_EXAMPLETAB_TITLE = "object.exampleTab.title";

    @RBEntry("pageTab")
    public static final String OBJECT_EXAMPLETAB_DESC = "object.exampleTab.description";

    @RBEntry("pageTab")
    public static final String OBJECT_EXAMPLETAB_TOOLTIP = "object.exampleTab.tooltip";

    @RBEntry("part")
    public static final String OBJECT_CUSTOMIZEDITEMS_TITLE = "object.customizedItems.title";

    @RBEntry("part")
    public static final String OBJECT_CUSTOMIZEDITEMS_DESC = "object.customizedItems.description";

    @RBEntry("electrical part detailsTab")
    public static final String OBJECT_ELECTRICALPARTINFODETAILSTAB_DESC = "object.electricalPartInfoDetailsTab.description";


}
