package ext.task.nonpersisted.bean;

import com.ptc.core.components.rendering.GuiComponent;
import com.ptc.core.components.rendering.guicomponents.IconComponent;
import com.ptc.core.richtext.HTMLText;
import com.ptc.netmarkets.model.NmObject;
import com.ptc.netmarkets.model.NmSimpleOid;

/**
 * @version:2019/11/21
 * @Author:wzp
 * @Date:Created in 15:49 2019/11/21
 * @Description:非持久化狗对象(非持久化对象必须继承NmObject)
 */
public class Dog extends NmObject {

    //名称
    private String name;
    //年纪
    private int age;
    //版本
    private String version;
    //图片地址
    private String imagePath;
    //描述
    private HTMLText description;

    public Dog() {
        this.setOid(new NmSimpleOid());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public HTMLText getDescription() {
        return description;
    }

    public void setDescription(HTMLText description) {
        this.description = description;
    }

    public String getDisplayType() {
        return this.getClass().getSimpleName();
    }

    public String getDisplayIdentifier() {
        return this.getName() + "," + this.getAge() + "," + this.getVersion();
    }

    /**
     * 页面上在版本号之后的图标
     *
     * @return
     */
    public GuiComponent getStatusFamily_General() {
        return createIconComponent("com/ptc/core/htmlcomp/images/checkedout_byyou9x9.gif", "Checked Out...", "statusFamily_General");
    }

    /**
     * 页面上在版本号之后的图标
     *
     * @return
     */
    public GuiComponent getStatusFamily_Share() {
        return createIconComponent("com/ptc/core/htmlcomp/images/shared_toproject9x9.gif", "Shared...", "statusFamily_Share");
    }

    /**
     * 页面上在版本号之后的图标
     *
     * @return
     */
    public GuiComponent getStatusFamily_Change() {
        return createIconComponent("netmarkets/images/chg_pending9x9.gif", "Pending Change...", "statusFamily_Change");
    }

    /**
     * 页面上在版本号之后的图标
     *
     * @return
     */
    public GuiComponent getStatusFamily_Security() {
        return createIconComponent("netmarkets/images/security_label9x9.gif", "Security...", "statusFamily_Security");
    }

    /**
     * 创建图标的方法
     *
     * @param image   图标路径
     * @param tooltip 图标描述
     * @param id      图标id
     * @return
     */
    public GuiComponent createIconComponent(String image, String tooltip, String id) {
        IconComponent statusGlyph = new IconComponent(image);
        statusGlyph.setTooltip(tooltip);
        statusGlyph.setId(id);
        return statusGlyph;
    }
}
