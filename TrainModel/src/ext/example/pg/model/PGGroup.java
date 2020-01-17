package ext.example.pg.model;

import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.SupportedAPI;

import wt.fc.WTObject;
import wt.util.WTException;

/**
 * PG组模型
 *
 * @author LiShuYan
 * @version 2019年5月27日
 */

@GenAsPersistable(superClass = WTObject.class,
        properties = {
                @GeneratedProperty(name = "pgGroupName", type = String.class,
                        columnProperties = @ColumnProperties(index = true, unique = true),
                        constraints = @PropertyConstraints(required = true), supportedAPI = SupportedAPI.PUBLIC, javaDoc = "组名"),
                @GeneratedProperty(name = "comments", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000)),//备注
                @GeneratedProperty(name = "enabled", type = Boolean.class, initialValue = "true"),//组是否启用
                @GeneratedProperty(name = "root", type = Boolean.class, initialValue = "false"),//根节点组
                @GeneratedProperty(name = "pgName", type = String.class)//创建一个共同的列显示树
        }
)
public class PGGroup extends _PGGroup {
    static final long serialVersionUID = 1;

    public static PGGroup newPGGroup() throws WTException {
        PGGroup instance = new PGGroup();
        instance.initialize();
        return instance;
    }

    public boolean equals(Object obj) {
        return wt.fc.PersistenceHelper.equals(this, obj);
    }

    public int hashCode() {
        return wt.fc.PersistenceHelper.hashCode(this);
    }

    /* (non-Javadoc)
     * @see wt.fc.WTObject#getIdentity()
     */
    @Override
    public String getIdentity() {
        return this.pgName;
    }

}
