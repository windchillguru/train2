package ext.task.page.builder;

import com.ptc.jca.mvc.builders.DefaultInfoComponentBuilder;
import com.ptc.mvc.components.*;
import wt.util.WTException;

import java.util.List;

/**
 * 客制化页面的builder
 *
 * @author 段鑫扬
 */
@TypeBased({"com.ptc.ElectricalPart"})
@ComponentBuilder(value = ComponentId.INFOPAGE_ID)
public class ElectricalPartInfoBuilder extends DefaultInfoComponentBuilder {
    /**
     * 配置客制化页面
     *
     * @param componentParams
     * @return
     * @throws WTException
     */
    @Override
    protected InfoConfig buildInfoConfig(ComponentParams componentParams) throws WTException {
        InfoComponentConfigFactory factory = getComponentConfigFactory();
        InfoConfig infoConfig = factory.newInfoConfig();

        List<ComponentConfig> componentConfigList = factory.getStandardStatusConfigs();
        //设置查询属性
        PropertyConfig propertyConfig = factory.newPropertyConfig("statusFamily_General");
        propertyConfig.setStatusGlyph(true);
        componentConfigList.add(propertyConfig);

        for (ComponentConfig componentConfig : componentConfigList) {
            infoConfig.addComponent(componentConfig);
        }

        //设置三级菜单
        infoConfig.setNavBarName("electrical_part_third_level_nav_bar");
        //设置页面名
        infoConfig.setTabSet("electricalPartInfoPageTabSet");
        //操作列表
        infoConfig.setActionListName("electrical_part_action_list");

        return infoConfig;
    }
}
