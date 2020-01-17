package ext.task.partapi.service;

import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.meta.common.CreateOperationIdentifier;
import wt.fc.PersistenceHelper;
import wt.part.WTPart;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

/**
 * @author 段鑫扬
 */
public class CreateSoftPart2 {

    /**
     * 创建软类型部件
     *
     * @throws WTException
     * @throws WTPropertyVetoException
     */
    public static void createSoftPartWithORI() throws WTException {
        PersistableAdapter adapter = new PersistableAdapter("wt.part.WTPart|com.ptc.ElectricalPart", null,
                new CreateOperationIdentifier());
        adapter.load("name", "view");
        adapter.set("name", "CreateSoftPartWithORI");
        adapter.set("view", "Design");
        WTPart part = (WTPart) adapter.apply();
        part = (WTPart) PersistenceHelper.manager.save(part);
    }
}
