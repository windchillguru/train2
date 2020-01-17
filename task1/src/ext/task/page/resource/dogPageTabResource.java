package ext.task.page.resource;


import wt.util.resource.RBEntry;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * 客制化页面的资源文件
 *
 * @author 段鑫扬
 */
@RBUUID("dogPageTabResource")
public final class dogPageTabResource extends WTListResourceBundle {

    @RBEntry("dog info")
    public static final String OBJECT_DOGINFODEFAULTDETAILS_TITLE = "object.dogInfoDefaultDetails.title";

    @RBEntry("dog info ")
    public static final String OBJECT_DOGINFODEFAULTDETAILS_DESC = "object.dogInfoDefaultDetails.description";

    @RBEntry("dog info ")
    public static final String OBJECT_DOGINFODEFAULTDETAILS_TOOLTIP = "object.dogInfoDefaultDetails.tooltip";

}
