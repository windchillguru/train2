package ext.task.part;

import wt.method.RemoteAccess;

import java.io.Serializable;

/**
 * @author 段鑫扬
 */
public interface IPartCodeIfc extends RemoteAccess, Serializable {
    //临时码前缀
    public static final String LS_PREFIX = "LS";
    //分类属性内部值
    public static final String IBA_CLASSIFICATION_NAME = "Classification";
    //5位流水码
    public static final int FLOW_NUM_DIGIT = 5;
}
