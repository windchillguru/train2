package ext.task.doc.provider;

import ext.lang.PIFileUtils;
import ext.lang.PIStringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import wt.log4j.LogR;
import wt.util.WTException;
import wt.util.WTProperties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * xml provider
 *
 * @author 段鑫扬
 */
public class FilterXmlProvider implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String CLASSNAME = FilterXmlProvider.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static String xml_path = "";
    private Document document;
    private static String XML_CONFIG = "ext\\task2\\common-filter-action-rule.xml";
    private static long lastModifyTime = -1L;

    static {
        try {
            WTProperties wtProperties = WTProperties.getLocalProperties();
            String codebasePath = wtProperties.getProperty("wt.codebase.location");
            xml_path = codebasePath + File.separator + XML_CONFIG;
            xml_path = PIFileUtils.formatFilePath(xml_path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FilterXmlProvider() {
        try {
            load();
        } catch (WTException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载配置文件
     *
     * @return
     * @throws WTException
     */
    public Document load() throws WTException {
        File file = new File(xml_path);
        if (this.document == null || file.lastModified() != lastModifyTime) {
            this.document = reload();
        }
        return this.document;
    }

    /**
     * 重新加载配置文件
     *
     * @return
     */
    private Document reload() {
        document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            document = documentBuilder.parse(xml_path);
            if (document == null) {
                LOGGER.error("不能解析配置文件: " + xml_path);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 根据按钮的名称获取对应xml的element ,如果没有返回null
     *
     * @param attr
     * @return
     */
    public Element findActionByAttr(String attr) {
        Element element = null;
        NodeList actions = document.getElementsByTagName("action");
        int length = actions.getLength();
        for (int i = 0; i < length; i++) {
            Node item = actions.item(i);
            if (item instanceof Element) {
                element = (Element) item;
                String name = element.getAttribute("name");
                if (name != null && name.equals(attr)) {
                    return element;
                }
            }
        }
        return element;
    }

    public List<String> findAttrByTag(Element element, String tagName) {
        List<String> list = new ArrayList<>();
        Element ele = null;
        NodeList elements = element.getElementsByTagName(tagName);
        int length = elements.getLength();
        for (int i = 0; i < length; i++) {
            Node item = elements.item(i);
            if (item instanceof Element) {
                ele = (Element) item;
                String name = ele.getAttribute("name");
                if (PIStringUtils.hasText(name)) {
                    list.add(name);
                }
            }
        }
        return list;
    }
}
