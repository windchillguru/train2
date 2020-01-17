package ext.example.pg.model;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.fc.ObjectToObjectLink;
import wt.fc.WTObject;
import wt.util.WTException;

/**
 * PG组和PG成员之间的link关系
 *
 * @author LiShuYan
 * @version 2019年5月27日
 */

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, extendable = true,
        roleA = @GeneratedRole(name = "pgGroup", type = PGGroup.class),
        roleB = @GeneratedRole(name = "pgMember", type = WTObject.class),
        properties = {
                @GeneratedProperty(name = "comments", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000))//备注
        }
)
public class PGMemberLink extends _PGMemberLink {
    static final long serialVersionUID = 1;//需要，否则报错

    public static PGMemberLink newPGMemberLink(PGGroup pgGroup, WTObject pgMember) throws WTException {
        PGMemberLink instance = new PGMemberLink();
        instance.initialize(pgGroup, pgMember);
        return instance;
    }

    public boolean equals(Object obj) {
        return wt.fc.PersistenceHelper.equals(this, obj);
    }

    public int hashCode() {
        return wt.fc.PersistenceHelper.hashCode(this);
    }

}
