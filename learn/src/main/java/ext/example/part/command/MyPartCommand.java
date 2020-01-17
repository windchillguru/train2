package ext.example.part.command;

import com.ptc.netmarkets.util.beans.NmCommandBean;
import org.apache.log4j.Logger;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.log4j.LogR;
import wt.method.RemoteAccess;
import wt.part.WTPart;
import wt.query.QuerySpec;
import wt.util.WTException;

import java.io.Serializable;

/**
 * @author 段鑫扬
 * jsp tag 服务类
 */
public class MyPartCommand implements RemoteAccess , Serializable {
    private static final long serialVersionUID = 1L;

    private static final String CLASSNAME = MyPartCommand.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    /**
     * 获取部件列表数据
     * @param commandBean
     * @return
     */
    public static QueryResult getData(NmCommandBean commandBean) {
        QueryResult queryResult = null;
        try {
            //查询所有部件的条件
            QuerySpec qs = new QuerySpec(WTPart.class);
            LOGGER.debug("####qs:"+qs);
            //查询
            queryResult = PersistenceHelper.manager.find(qs);
        } catch (WTException e) {
            e.printStackTrace();
        }
        return queryResult;
    }
}
