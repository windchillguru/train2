package ext.task.doc.provider;

import ext.lang.PIFileUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
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
public class XmlDataProvider implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String CLASSNAME = XmlDataProvider.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static String xml_path = "";
    private Document document;
    private static String XML_CONFIG = "ext\\example\\config\\xmlData.xml";
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

    public XmlDataProvider() {
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
     * 获取根节点
     *
     * @return
     */
    public List<Element> getAllRoots() {
        List<Element> rootNodes = new ArrayList<>();
        NodeList nodeList = document.getElementsByTagName("config");
        int length = nodeList.getLength();
        LOGGER.debug("root length:" + length);

        for (int i = 0; i < length; i++) {
            Node rootNode = nodeList.item(i);
            if (rootNode instanceof Element) {
                rootNodes.addAll(findChildren(rootNode));
            }
        }
        return rootNodes;
    }

    /**
     * 获取子节点
     *
     * @param parentNode
     * @return
     */
    public List<Element> findChildren(Node parentNode) {
        List<Element> childNodes = new ArrayList<>();
        if (parentNode != null) {
            NodeList childList = parentNode.getChildNodes();
            int length = childList.getLength();
            for (int i = 0; i < length; i++) {
                Node childNode = childList.item(i);
                if (childNode instanceof Element) {
                    childNodes.add((Element) childNode);
                }
            }
        }
        return childNodes;
    }
}
