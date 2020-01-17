package ext.task.page.delegate;

import com.ptc.core.components.rendering.guicomponents.VisualizationComponent;
import com.ptc.core.components.visualization.AbstractVisualizationDelegate;
import ext.task.page.bean.Dog;

import javax.servlet.ServletRequest;

/**
 * dog 可视化组件
 *
 * @author 段鑫扬
 */
public class DogVisualizationDelegate extends AbstractVisualizationDelegate {

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
}
