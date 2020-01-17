package ext.task.nonpersisted.builder;

import com.ptc.core.richtext.HTMLText;
import com.ptc.jca.mvc.components.JcaComponentParams;
import com.ptc.mvc.components.*;
import ext.task.nonpersisted.bean.Dog;
import wt.util.WTException;

/**
 * @version:2019/11/25
 * @Author:wzp
 * @Date:Created in 14:00 2019/11/25
 * @Description:狗info builder
 */
@TypeBased({"Dog"})//类路径
@ComponentBuilder(value = ComponentId.INFOPAGE_ID)
public class DogInfoBuilder extends AbstractInfoConfigBuilder implements ComponentDataBuilder {
    /**
     * 拿到info配置视图
     *
     * @param componentParams
     * @return
     * @throws WTException
     */
    @Override
    protected InfoConfig buildInfoConfig(ComponentParams componentParams) throws WTException {
        //获得infoConfig对象
        InfoComponentConfigFactory factory = getComponentConfigFactory();
        InfoConfig infoConfig = factory.newInfoConfig();
        //页面上的操作栏
        infoConfig.setActionListName("dog_action_list");
        //狗的tab栏
        infoConfig.setTabSet("dogInfoPageTabSet");
        //"自定义"栏下拉列表
        infoConfig.setNavBarName("dog_third_level_nav_bar");
        //添加4个小图标
        infoConfig.addComponent(newStatusGlyph("statusFamily_General"));
        infoConfig.addComponent(newStatusGlyph("statusFamily_Share"));
        infoConfig.addComponent(newStatusGlyph("statusFamily_Change"));
        infoConfig.addComponent(newStatusGlyph("statusFamily_Security"));
        //设置类型
        infoConfig.setType(Dog.class.getName());
        infoConfig.setView("/components/infoPage.jsp");
        return infoConfig;
    }

    /**
     * 获取具体非持久化对象信息
     *
     * @param componentConfig
     * @param componentParams
     * @return
     * @throws Exception
     */
    @Override
    public Object buildComponentData(ComponentConfig componentConfig, ComponentParams componentParams) throws Exception {
        Dog dog = new Dog();
        dog.setName("Spider dog");
        dog.setAge(3);
        dog.setVersion("A.a.1");
        dog.setImagePath("netmarkets/images/VCG211182553007.jpg");
        String desc = "Spider Man's dog is amazing";
        HTMLText desc1 = HTMLText.newHTMLText(desc);
        dog.setDescription(desc1);
        JcaComponentParams jcaComponentParams = (JcaComponentParams) componentParams;
        //将b组件的信息显示在a组件上
        jcaComponentParams.setContextObject(dog);
        jcaComponentParams.getHelperBean().getRequest().setAttribute("dog", dog);
        return dog;
    }

    /**
     * 返回那4个小图标配置
     *
     * @param id
     * @return
     */
    public PropertyConfig newStatusGlyph(String id) {
        PropertyConfig glyph = getComponentConfigFactory().newPropertyConfig(id);
        glyph.setStatusGlyph(true);
        glyph.setDataUtilityId("defaultDataUtility");
        return glyph;
    }
}
