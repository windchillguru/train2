package ext.example.pg.model;

import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.SupportedAPI;

import wt.fc.ObjectReference;
import wt.fc.WTObject;
import wt.util.WTException;

/**
 * 编译java
 * ant -f bin/tools.xml class -Dclass.includes=ext/example/pg/model/*
 * 编译rbinfo
 * resourcebuild ext.example.pg.model.InformationSourceRB
 * 编译sql
 * ant -f bin/tools.xml sql_script -Dgen.input=ext.example.pg.model.*
 * 执行sql
 * cd db\sql3
 * sqlplus pisx/pisx@wind
 *
 * @author LiShuYan
 * @version 2019年5月20日
 * @ext/example/pg/model/Make_pkg_model_Table.sql
 * @ext/example/pg/model/Make_pkg_model_Sequence.sql
 * @ext/example/pg/model/Make_pkg_model_Index.sql PG信息的模型
 */
@GenAsPersistable(superClass = WTObject.class,
        properties = {
                @GeneratedProperty(name = "employeeNo", type = String.class,
                        columnProperties = @ColumnProperties(index = true, unique = true),
                        constraints = @PropertyConstraints(required = true), supportedAPI = SupportedAPI.PUBLIC, javaDoc = "员工工号"),
                @GeneratedProperty(name = "employeeName", type = String.class, supportedAPI = SupportedAPI.PUBLIC, javaDoc = "姓名"),
                @GeneratedProperty(name = "employeeUserName", type = String.class,
                        columnProperties = @ColumnProperties(index = true), supportedAPI = SupportedAPI.PUBLIC, javaDoc = "用户名"),
                @GeneratedProperty(name = "employeeEmail", type = String.class, supportedAPI = SupportedAPI.PUBLIC, javaDoc = "邮箱"),
                @GeneratedProperty(name = "employeePhone", type = String.class, supportedAPI = SupportedAPI.PUBLIC, javaDoc = "电话"),
                @GeneratedProperty(name = "comments", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000)),//备注
                @GeneratedProperty(name = "experienced", type = Boolean.class, initialValue = "false"),//是否有经验
                @GeneratedProperty(name = "resumeInfo", type = ObjectReference.class),//简历信息
                @GeneratedProperty(name = "informationSource", type = InformationSource.class, initialValue = "InformationSource.getInformationSourceDefault()"),//信息来源
                @GeneratedProperty(name = "informationNo", type = String.class),//信息编号
                @GeneratedProperty(name = "leader", type = Boolean.class, initialValue = "false"),//是否是组长
                @GeneratedProperty(name = "pgName", type = String.class)//创建一个共同的列显示树
        }
)
public class PGInformation extends _PGInformation {
    static final long serialVersionUID = 1;

    public static PGInformation newPGInformation() throws WTException {
        PGInformation instance = new PGInformation();
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