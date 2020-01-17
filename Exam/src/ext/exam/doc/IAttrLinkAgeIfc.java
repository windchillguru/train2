package ext.exam.doc;

import wt.method.RemoteAccess;

import java.io.Serializable;

/**
 * 地铁常量类
 *
 * @author 段鑫扬
 */
public interface IAttrLinkAgeIfc extends RemoteAccess, Serializable {
    public static final String IBA_CUSTOMERNAME = "CustomerName"; // 客户名称
    public static final String IBA_BELONGMANAGER = "BelongManager"; // 经理
}
