package ext.task.part.builder;

import com.ptc.mvc.components.*;
import wt.util.WTException;

/**
 * @author 段鑫扬
 * 部件信息的builder
 */
@ComponentBuilder("PartInfoBuilder")
public class PartInfoBuilder extends AbstractComponentBuilder {

    @Override
    public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams) throws Exception {
        //获取该部件对象
        return componentParams.getContextObject();
    }

    @Override
    public ComponentConfig buildComponentConfig(ComponentParams componentParams) throws WTException {
        AttributePanelConfig panelConfig = null;
        try {
            ComponentConfigFactory factory = getComponentConfigFactory();
            //属性面板配置
            panelConfig = factory.newAttributePanelConfig();

            //建组部件信息
            GroupConfig groupConfig = factory.newGroupConfig("PartInfo", "部件信息", Integer.valueOf(1));
            //第1行，第1列
            groupConfig.addComponent(factory.newAttributeConfig("name", "名称", 0, 0));
            //第1行，第2列
            groupConfig.addComponent(factory.newAttributeConfig("number", "编号", 0, 1));

            //第2行，第1列
            AttributeConfig attributeConfig = factory.newAttributeConfig("version", "版本", 1, 0);
            //占2列
            attributeConfig.setColSpan(Integer.valueOf(2));
            groupConfig.addComponent(attributeConfig);

            //第3行，第1列 状态
            AttributeConfig attributeVersionConfig = factory.newAttributeConfig("state.state", "状态", 2, 0);
            //占2列
            attributeVersionConfig.setColSpan(Integer.valueOf(2));
            groupConfig.addComponent(attributeVersionConfig);

            //第4行，第1列
            groupConfig.addComponent(factory.newAttributeConfig("thePersistInfo.createStamp", "创建时间", 3, 0));
            groupConfig.addComponent(factory.newAttributeConfig("thePersistInfo.updateStamp", "修改时间", 3, 1));

            //第5行，第1列 上下文 容器
            AttributeConfig attributeContainConfig = factory.newAttributeConfig("containerReference", "上下文", 4, 0);
            //占2列
            attributeContainConfig.setColSpan(Integer.valueOf(2));
            groupConfig.addComponent(attributeContainConfig);

            panelConfig.addComponent(groupConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return panelConfig;
    }
}
