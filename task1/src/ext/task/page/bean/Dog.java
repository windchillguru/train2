package ext.task.page.bean;

import com.ptc.core.components.rendering.GuiComponent;
import com.ptc.core.components.rendering.guicomponents.IconComponent;
import com.ptc.core.richtext.HTMLText;
import com.ptc.netmarkets.model.NmObject;
import com.ptc.netmarkets.model.NmSimpleOid;

/**
 * @author 段鑫扬
 */
public class Dog extends NmObject {
    private String name;
    private int age;
    private String version;
    private String imagePath;
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

    public GuiComponent getStatusFamily_General() {
        return createGuiComponent("com/ptc/core/htmlcomp/images/checkedout_byyou9x9.gif", "Checked out ", "statusFamily_General");
    }

    public GuiComponent getStatusFamily_Share() {
        return createGuiComponent("com/ptc/core/htmlcomp/images/shared_toproject9x9.gif", "Shared ", "statusFamily_Share");
    }

    public GuiComponent getStatusFamily_Change() {
        return createGuiComponent("netmarkets/images/chg_pending9x9.gif", "Pending Change... ", "statusFamily_Change");
    }

    public GuiComponent getStatusFamily_Security() {
        return createGuiComponent("netmarkets/images/security_label9x9.gif", "Security... ", "statusFamily_Security");
    }

    public GuiComponent createGuiComponent(String image, String tooltip, String id) {
        IconComponent statusGlyph = new IconComponent(image);
        statusGlyph.setTooltip(tooltip);
        statusGlyph.setId(id);
        return statusGlyph;
    }
}
