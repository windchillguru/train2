package ext.example.pg.datautility;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import ext.example.pg.model.PGGroup;
import ext.example.pg.model.PGInformation;
import ext.example.pg.model.PGMemberLink;
import org.apache.log4j.Logger;
import wt.fc.WTObject;
import wt.log4j.LogR;
import wt.util.WTException;

/**
 * pg信息的dataUtility 处理tree的pgName显示问题
 *
 * @author 段鑫扬
 * @version 2019/12/23
 */
public class PGInfoDataUtility extends DefaultDataUtility {
    private static final String CLASSNAME = PGInfoDataUtility.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    @Override
    public Object getDataValue(String s, Object obj, ModelContext modelContext) throws WTException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> s=" + s + ",obj=" + obj);
        }
        Object superResult = super.getDataValue(s, obj, modelContext);
        if ("pgName".equals(s)) {
            //处理pgName
            if (obj instanceof PGGroup) {
                return ((PGGroup) obj).getPgName();
            } else if (obj instanceof PGMemberLink) {
                PGMemberLink link = (PGMemberLink) obj;
                WTObject member = link.getPgMember();
                if (member instanceof PGGroup) {
                    return ((PGGroup) member).getPgName();
                } else if (member instanceof PGInformation) {
                    return ((PGInformation) member).getPgName();
                }
            }
        } else if ("isGroup".equals(s)) {
            //是否是组
            if (obj instanceof PGGroup) {
                return "是";
            } else {
                return "否";
            }
        }
        return superResult;
    }
}
