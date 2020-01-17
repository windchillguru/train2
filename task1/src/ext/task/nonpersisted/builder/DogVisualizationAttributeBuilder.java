package ext.task.nonpersisted.builder;

import com.ptc.core.richtext.HTMLText;
import com.ptc.jca.mvc.builders.VisualizationAttributesBuilder;
import com.ptc.mvc.components.*;
import ext.task.nonpersisted.bean.Dog;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * @version:2019/11/25
 * @Author:wzp
 * @Date:Created in 15:00 2019/11/25
 * @Description:狗的可视化组件builder
 */
@TypeBased({"Dog"})
@ComponentBuilder(value = "visualizationAndAttributes")
public class DogVisualizationAttributeBuilder extends VisualizationAttributesBuilder {

    @Override
    protected AttributePanelConfig buildPrimaryAttributePanelConfig(ComponentParams params) throws WTException {
        ComponentConfigFactory factory = getComponentConfigFactory();

        AttributePanelConfig panelConfig = factory.newAttributePanelConfig();
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
