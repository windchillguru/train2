package ext.task.nonpersisted.delegate;

import com.ptc.core.components.rendering.guicomponents.VisualizationComponent;
import com.ptc.core.components.visualization.DefaultVisualizationDelegate;
import com.ptc.core.ui.validation.UIValidationCriteria;
import ext.task.nonpersisted.bean.Dog;

import javax.servlet.ServletRequest;

/**
 * @version:2019/11/25
 * @Author:wzp
 * @Date:Created in 14:35 2019/11/25
 * @Description:狗的可视化组件
 */
public class DogVisualizationDelegate extends DefaultVisualizationDelegate {
    @Override
    public VisualizationComponent getVisualizationComponent(Object o, ServletRequest servletRequest) {
        String html = "";
        if (o != null && o instanceof Dog) {
            Dog dog = (Dog) o;
            html = "<div id=wvs_pview_div class=vizComponent><IMG src='" + dog.getImagePath() + "'/></div>";
        } else {
            html = "<div id=wvs_pview_div class=vizComponent><IMG src='netmarkets/images/UPG.gif'/></div>";
        }
        return new VisualizationComponent(html);
    }

    @Override
    public boolean showVisualization(UIValidationCriteria uiValidationCriteria) {
        return true;
    }
}
