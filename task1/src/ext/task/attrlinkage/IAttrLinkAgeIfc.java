package ext.task.attrlinkage;

import wt.method.RemoteAccess;

import java.io.Serializable;

/**
 * 地铁常量类
 *
 * @author 段鑫扬
 */
public interface IAttrLinkAgeIfc extends RemoteAccess, Serializable {
    public static final String IBA_SUBWAY = "Subway"; // 地铁
    public static final String IBA_STATION = "Station"; // 站台
    public static final String IBA_EXIT = "Exit"; // 出口
}
