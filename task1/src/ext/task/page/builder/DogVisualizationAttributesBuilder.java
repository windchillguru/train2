package ext.task.page.builder;

import com.ptc.core.richtext.HTMLText;
import com.ptc.jca.mvc.builders.VisualizationAttributesBuilder;
import com.ptc.mvc.components.*;
import ext.task.page.bean.Dog;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * @author 段鑫扬
 */
@ComponentBuilder(value = "visualizationAndAttributes")
@TypeBased(value = "Dog")
public class DogVisualizationAttributesBuilder extends VisualizationAttributesBuilder {
    @Override
    protected AttributePanelConfig buildPrimaryAttributePanelConfig(ComponentParams componentParams) throws WTException {
        ComponentConfigFactory factory = getComponentConfigFactory();
        AttributePanelConfig panelConfig = factory.newAttributePanelConfig();
        //id,组名
        GroupConfig groupConfig = factory.newGroupConfig("Attributes", "Attributes", 1);

        groupConfig.addComponent(factory.newAttributeConfig("name", "Name", 1, 1));
        groupConfig.addComponent(factory.newAttributeConfig("age", "Age", 2, 1));
        groupConfig.addComponent(factory.newAttributeConfig("version", "Version", 3, 1));
        groupConfig.addComponent(factory.newAttributeConfig("description", "Description", 4, 1));
        panelConfig.addComponent(groupConfig);


        addVisualizationConfig(panelConfig);
        return panelConfig;
    }


    @Override
    public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams) throws WTException, WTPropertyVetoException {
        Dog dog = new Dog();
        //设置数据
        dog.setName("Spider dog");
        dog.setAge(3);
        dog.setVersion("A.a.1");
        dog.setImagePath("netmarkets/images/VCG211182553007.jpg");
        String desc = "Spider Man's dog is amazing";
        HTMLText desc1 = HTMLText.newHTMLText(desc);
        dog.setDescription(desc1);
        return dog;
    }
}
