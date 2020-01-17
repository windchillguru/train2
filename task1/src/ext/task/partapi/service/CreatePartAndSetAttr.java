package ext.task.partapi.service;

import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.meta.common.CreateOperationIdentifier;
import wt.fc.PersistenceHelper;
import wt.part.QuantityUnit;
import wt.part.WTPart;
import wt.util.WTException;

/**
 * 测试部件api
 *
 * @author 段鑫扬
 */
public class CreatePartAndSetAttr {

    /**
     * 创建部件并设置属性
     *
     * @throws WTException
     */
    public static void createPartAndSetAttr() throws WTException {
        PersistableAdapter adapter = new PersistableAdapter("wt.part.WTPart", null,
                new CreateOperationIdentifier());
        adapter.load("name");
        //设置属性 endItem 是否成品   defaultUnit 默认单位
        adapter.load("endItem", "defaultUnit");
        adapter.set("name", "CreatePartAndSetAttr");
        //成品
        adapter.set("endItem", true);
        //千克
        adapter.set("defaultUnit", QuantityUnit.KG);
        WTPart part = (WTPart) adapter.apply();
        part = (WTPart) PersistenceHelper.manager.save(part);
    }
}
