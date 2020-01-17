package ext.example.part.mvc.builder;

import com.ptc.mvc.components.*;
import wt.util.WTException;

/**
 * @author 段鑫扬
 * 属性面板页
 */
@ComponentBuilder("ext.example.part.mvc.builder.MyPartAttributesPanelBuilder")
public class MyPartAttributesPanelBuilder extends AbstractComponentBuilder {

    @Override
    public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams) throws Exception {
        //获取部件对象
        return componentParams.getContextObject();
    }

    @Override
    public ComponentConfig buildComponentConfig(ComponentParams componentParams) throws WTException {
        ComponentConfigFactory factory = getComponentConfigFactory();

        AttributePanelConfig panelConfig = factory.newAttributePanelConfig();
        //建组1
        GroupConfig groupConfig = factory.newGroupConfig("testGrp1","测试组1",Integer.valueOf(1));
        //第1行，第1列
        groupConfig.addComponent(factory.newAttributeConfig("name","名字",0,0));
        //第1行，第2列
        groupConfig.addComponent(factory.newAttributeConfig("number","编号",0,1));
        //第2行，第1列
        AttributeConfig attributeConfig = factory.newAttributeConfig("version","版本",1,0);
        //占2列
        attributeConfig.setColSpan(Integer.valueOf(2));
        groupConfig.addComponent(attributeConfig);
        //第3行，第1列
        groupConfig.addComponent(factory.newAttributeConfig("thePersistInfo.createStamp","创建时间",2,0));
        groupConfig.addComponent(factory.newAttributeConfig("thePersistInfo.updateStamp","修改时间",2,1));

        panelConfig.addComponent(groupConfig);
        //建组2
        groupConfig = factory.newGroupConfig("testGrp2","测试组2",Integer.valueOf(1));
        //第1行，第1列
        groupConfig.addComponent(factory.newAttributeConfig("name","name",0,0));
        //第1行，第2列
        groupConfig.addComponent(factory.newAttributeConfig("number","number",0,1));
        //第2行，第1列
        attributeConfig = factory.newAttributeConfig("version","version",1,0);
        //占2列
        attributeConfig.setColSpan(Integer.valueOf(2));
        groupConfig.addComponent(attributeConfig);
        //第3行，第1列
        groupConfig.addComponent(factory.newAttributeConfig("thePersistInfo.createStamp","createStamp",2,0));
        //第3行，第2列
        groupConfig.addComponent(factory.newAttributeConfig("thePersistInfo.updateStamp","updateStamp",2,1));
        panelConfig.addComponent(groupConfig);
        return panelConfig;
    }

    /**
     * 创建属性配置
     * @param id    取数据的key
     * @param label 显示标签
     * @return
     */
    private ComponentConfig getAttributeConfig(String id,String label) {
        AttributeConfig attributeConfig = getComponentConfigFactory().newAttributeConfig();
        attributeConfig.setId(id);
        attributeConfig.setLabel(label);
        return attributeConfig;
    }


}
