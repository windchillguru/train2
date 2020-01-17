package ext.orv.doc.provider;

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
import java.util.Map;
import java.util.TreeMap;

/**
 * xml provider
 *
 * @author 段鑫扬
 */
public class ORVDocNumXmlProvider implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String CLASSNAME = ORVDocNumXmlProvider.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static String xml_path = "";
    private Document document;
    private static String XML_CONFIG = "ext\\orv\\conf\\ORVDocNum.xml";
    private Long lastModifiedTime;

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

    public ORVDocNumXmlProvider() {
        try {
            load();
        } catch (WTException e) {
            e.printStackTrace();
        }
    }


    //存储修改时间
    private static Map<Long, Document> map = new TreeMap<>();

    /**
     * 加载配置文件
     *
     * @return
     * @throws WTException
     */
    public void load() throws WTException {
        File file = new File(xml_path);
        lastModifiedTime = file.lastModified();
        //如果修改了，则重新加载xml对象
        if (map.containsKey(lastModifiedTime)) {
            //当前修改时间的xml 的document对象
            document = map.get(lastModifiedTime);
        } else {
            //重新加载xml
            document = reload();
        }
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
            //存储xml document对象
            map.put(lastModifiedTime, document);
            if (document == null) {
                LOGGER.error("不能解析配置文件: " + xml_path);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 根据文档大类获取对应xml的element ,如果没有返回null
     *
     * @param docType
     * @return
     */
    public Element findTypeByDocType(String docType) {
        NodeList actions = document.getElementsByTagName("type");
        int length = actions.getLength();
        for (int i = 0; i < length; i++) {
            Node item = actions.item(i);
            if (item instanceof Element) {
                Element element = (Element) item;
                String name = element.getAttribute("name");
                if (name != null && name.equals(docType)) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(">>>>docType" + docType + " 的element =" + element);
                    }
                    return element;
                }
            }
        }
        return null;
    }

    /**
     * 根据文档大类的ele 查对应的小类ele，如果没有则返回null
     *
     * @param element
     * @param smallDocType
     * @return
     */
    public Element findSmallTypeBySmallDocType(Element element, String smallDocType) {
        if (!PIStringUtils.hasText(smallDocType)) {
            return null;
        }
        Element ele = null;
        NodeList elements = element.getElementsByTagName("smalltype");
        int length = elements.getLength();
        for (int i = 0; i < length; i++) {
            Node item = elements.item(i);
            if (item instanceof Element) {
                ele = (Element) item;
                String name = ele.getAttribute("name");
                if (PIStringUtils.hasText(name) && name.equals(smallDocType)) {
                    return ele;
                }
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>smallType" + smallDocType + " 的ele =" + ele);
        }
        return ele;
    }
}
