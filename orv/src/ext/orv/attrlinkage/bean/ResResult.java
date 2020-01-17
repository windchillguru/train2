package ext.orv.attrlinkage.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 段鑫扬
 * @version 2020/1/2
 */
public class ResResult<E> implements Serializable {
    private String FLAG;
    private List<E> list;
}
