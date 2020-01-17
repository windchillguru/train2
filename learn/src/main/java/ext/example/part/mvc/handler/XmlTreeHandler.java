package ext.example.part.mvc.handler;

import com.ptc.core.components.beans.TreeHandlerAdapter;
import ext.example.part.mvc.provider.XmlDataProvider;
import ext.example.pginfo.bean.XmlDataBean;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import wt.log4j.LogR;
import wt.util.WTException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xml树数据处理类
 *
 * @author 段鑫扬
 */
public class XmlTreeHandler extends TreeHandlerAdapter {

    private XmlDataProvider provider = new XmlDataProvider();
    private static final String CLASSNAME = XmlTreeHandler.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);


    /**
     * 获取子节点
     * @param list
     * @return
     * @throws WTException
     */
    @Override
    public Map<Object, List> getNodes(List list) throws WTException {
        Map<Object, List> result = new HashMap<Object, List>();
        for (Object obj : list) {
            List childList = new ArrayList();
            if(obj instanceof XmlDataBean) {
                XmlDataBean bean = (XmlDataBean) obj;
                //获取子节点
                List<Element> childEles = provider.findChildren(bean.getNode());
                for (Element ele : childEles) {
                    String name = ele.getAttribute("name");
                    LOGGER.debug("node_name: "+name);
                    childList.add(new XmlDataBean(name,ele));
                }
                result.put(obj,childList);
            }
        }
        return result;
    }

    /**
     * 获取根节点数据
     *
     * @return
     * @throws WTException
     */
    @Override
    public List<Object> getRootNodes() throws WTException {
        List<Object> rootList = new ArrayList<>();
        List<Element> allRoots = provider.getAllRoots();
        for (Element element : allRoots) {
            String name = element.getAttribute("name");
            LOGGER.debug("root_node_name: "+name);
            rootList.add(new XmlDataBean(name,element));
        }

        return rootList;
    }
}
