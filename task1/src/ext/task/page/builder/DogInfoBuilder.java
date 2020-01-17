package ext.task.page.builder;

import com.ptc.core.richtext.HTMLText;
import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.mvc.components.*;
import ext.task.page.bean.Dog;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * 非持久化对象的客制化页面
 *
 * @author 段鑫扬
 */
@TypeBased(value = "Dog")
@ComponentBuilder(value = ComponentId.INFOPAGE_ID)
public class DogInfoBuilder extends AbstractInfoConfigBuilder implements ComponentDataBuilder {

    @Override
    protected InfoConfig buildInfoConfig(ComponentParams componentParams) throws WTException {
        InfoConfig infoConfig = null;
        try {
            infoConfig = getComponentConfigFactory().newInfoConfig();

            infoConfig.setActionListName("dog_action_list");
            infoConfig.setTabSet("dogInfoPageTabSet");
            infoConfig.setNavBarName("dog_third_level_navigation");
            //infoConfig.setView("/components/infoPage.jsp");

            infoConfig.addComponent(newStatusGlyph("statusFamily_Share"));
            infoConfig.addComponent(newStatusGlyph("statusFamily_General"));
            infoConfig.addComponent(newStatusGlyph("statusFamily_Change"));
            infoConfig.addComponent(newStatusGlyph("statusFamily_Security"));
            infoConfig.setType(Dog.class.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return infoConfig;
    }

    public PropertyConfig newStatusGlyph(String id) {
        PropertyConfig glyph = null;
        try {
            glyph = getComponentConfigFactory().newPropertyConfig(id);
            glyph.setStatusGlyph(true);
            glyph.setDataUtilityId("defaultDataUtility");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return glyph;
    }

    @Override
    public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams) throws Exception {
        Dog dog = null;
        try {
            dog = new Dog();
            dog.setName("Spider dog");
            dog.setAge(3);
            dog.setVersion("A.a.1");
            dog.setImagePath("netmarkets/images/VCG211182553007.jpg");
            String desc = "Spider Man's dog is amazing";
            HTMLText htmlText = HTMLText.newHTMLText(desc);

            dog.setDescription(htmlText);
            JcaComponentParams jcaComponentParams = (JcaComponentParams) componentParams;
            jcaComponentParams.setContextObject(dog);
            jcaComponentParams.getHelperBean().getRequest().setAttribute("dog", dog);
        } catch (WTPropertyVetoException e) {
            e.printStackTrace();
        }
        return dog;
    }
}
