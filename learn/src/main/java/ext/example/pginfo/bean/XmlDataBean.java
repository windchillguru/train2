package ext.example.pginfo.bean;

import com.ptc.netmarkets.model.NmOid;
import org.w3c.dom.Element;


/**
 * @author 段鑫扬
 */
public class XmlDataBean extends NmOid {
    private String name;
    private Element node;

    public XmlDataBean() {
    }

    public XmlDataBean(String name, Element node) {
        super();
        this.name = name;
        this.node = node;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Element getNode() {
        return node;
    }

    public void setNode(Element node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
