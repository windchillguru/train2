package ext.task2.part.processor;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.core.meta.type.mgmt.server.impl.TypeDomainHelper;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.pi.core.PIAttributeHelper;
import ext.pi.core.PICoreHelper;
import ext.pi.core.PIPartHelper;
import ext.task2.part.bean.BOMBean;
import ext.task2.part.reader.BOMBeanReader;
import org.apache.log4j.Logger;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.folder.Folder;
import wt.folder.FolderHelper;
import wt.folder.SubFolderReference;
import wt.inf.container.WTContainer;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.part.WTPartUsageLink;
import wt.session.SessionServerHelper;
import wt.type.ClientTypedUtility;
import wt.type.TypeDefinitionReference;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.views.View;
import wt.vc.views.ViewHelper;
import wt.vc.views.ViewReference;

import java.io.File;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

/**
 * 导入虚拟BOM报表数据 processor
 *
 * @author 段鑫扬
 */
public class ImportBOMProcessor extends DefaultObjectFormProcessor {
    private static final String CLASSNAME = ImportBOMProcessor.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    //虚拟bom的顶层件编号
    private static final String PART_NUMBER = "V0000001";

    @Override
    public FormResult doOperation(NmCommandBean commandBean, List<ObjectBean> objectBeans) throws WTException {
        FormResult formResult = super.doOperation(commandBean, objectBeans);
        boolean enforced = SessionServerHelper.manager.setAccessEnforced(false);
        File file = null;
        try {
            HashMap<String, Object> fileUploadMap = (HashMap<String, Object>) commandBean.getMap().get("fileUploadMap");
            LOGGER.debug("fileUploadMap" + fileUploadMap);
            file = (File) fileUploadMap.get("file");
            String inputFileName = commandBean.getTextParameter("file");
            LOGGER.debug("inputFileName" + inputFileName);
            BOMBeanReader beanReader = new BOMBeanReader(file);
            beanReader.setStartRowIndex(1);
            beanReader.read();
            List<BOMBean> beanList = beanReader.getBeanList();
            handleExcel(beanList);
        } catch (WTException e) {
            e.printStackTrace();
            throw new WTException(e);
        } catch (WTPropertyVetoException e) {
            e.printStackTrace();
        } finally {
            if (file != null) {
                file.delete();
            }
            SessionServerHelper.manager.setAccessEnforced(enforced);
        }
        return formResult;
    }

    /**
     * 根据excel数据对替换BOM进行处理
     *
     * @param beanList excel中的所有数据对象
     */
    private void handleExcel(List<BOMBean> beanList) throws WTException, WTPropertyVetoException {
        String design = "Design";
        //备注
        String IBA_comment = "comment";
        //优先级
        String IBA_priority = "priority";
        try {
            WTPart topPart = PIPartHelper.service.findWTPart(PART_NUMBER, design);
            for (BOMBean bean : beanList) {
                //父件编号
                String partNumber = bean.getPartNumber();
                WTPart parentPart = null;
                if ("new".equalsIgnoreCase(bean.getCategory())) {
                    int endIndex = partNumber.indexOf("(");
                    if (endIndex != -1) {
                        String number = partNumber.substring(0, endIndex);
                        //先查当前父件是否已经创建，如果创建则不再创建
                        parentPart = PIPartHelper.service.findWTPart(number, design);
                    } else {
                        //先查当前父件是否已经创建，如果创建则不再创建
                        parentPart = PIPartHelper.service.findWTPart(partNumber, design);
                    }

                    if (parentPart == null) {
                        //创建父件
                        parentPart = createPart(partNumber, topPart);
                        //创建link关系
                        topPart = (WTPart) PICoreHelper.service.checkoutObject(topPart);
                        createLink(parentPart, topPart);
                        PICoreHelper.service.checkinObject(topPart);
                    }
                } else {
                    //如果不是 new ,则直接查找父件，不需要处理
                    parentPart = PIPartHelper.service.findWTPart(partNumber, design);
                }

                //子件编号
                String subPartNumber = bean.getSubPartNumber();
                WTPart subPart = PIPartHelper.service.findWTPart(subPartNumber, design);
                if (subPart == null) {
                    //创建子件
                    subPart = createSubPart(subPartNumber, parentPart);
                }
                QueryResult usageLinks = PIPartHelper.service.findUsageLinks(parentPart, subPart);
                if (usageLinks.size() > 0) {
                    //如果已经存在关系，则跳过
                    continue;
                }
                //创建link关系
                parentPart = (WTPart) PICoreHelper.service.checkoutObject(parentPart);
                WTPartUsageLink link = WTPartUsageLink.newWTPartUsageLink(parentPart, subPart.getMaster());
                link = (WTPartUsageLink) PersistenceHelper.manager.store(link);
                //修改优先级，备注
                PIAttributeHelper.service.updateSoftAttribute(link, IBA_comment, bean.getComment());
                PIAttributeHelper.service.updateSoftAttribute(link, IBA_priority, bean.getPriority());
                PICoreHelper.service.checkinObject(parentPart);
            }
        } catch (WTException e) {
            e.printStackTrace();
            throw new WTException(e);
        } catch (WTPropertyVetoException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给虚拟部件创建link
     *
     * @param part       虚拟部件
     * @param parentPart 顶层件
     */
    private void createLink(WTPart part, WTPart parentPart) throws WTException {
        WTPartUsageLink link = WTPartUsageLink.newWTPartUsageLink(parentPart, part.getMaster());
        PersistenceHelper.manager.store(link);
    }

    /**
     * 根据父件分类 以及编号 创建子件
     *
     * @param subPartNumber 子件编号
     * @param parentPart    父件
     * @return
     */
    private WTPart createSubPart(String subPartNumber, WTPart parentPart) throws WTException, WTPropertyVetoException {
        WTPart part = null;
        try {
            WTContainer container = parentPart.getContainer();
            SubFolderReference parentFolder = parentPart.getParentFolder();
            part = WTPart.newWTPart();
            part.setName(subPartNumber + "部件");
            part.setNumber(subPartNumber);
            //设置容器
            part.setContainer(container);
            //设置文件夹
            FolderHelper.assignLocation(part, (Folder) parentFolder.getObject());
            //设置视图
            View design = ViewHelper.service.getView("Design");
            ViewReference viewReference = ViewReference.newViewReference(design);
            part.setView(viewReference);
            //创建部件
            part = (WTPart) PersistenceHelper.manager.save(part);

            //修改分类
            String classification = (String) PIAttributeHelper.service.getValue(parentPart, "Classification");
            part = (WTPart) PIAttributeHelper.service.updateSoftAttribute(part, "Classification", classification);
        } catch (WTException e) {
            e.printStackTrace();
            throw new WTException(e);
        } catch (WTPropertyVetoException e) {
            e.printStackTrace();
        }
        return part;
    }

    /**
     * 创建父件，（虚拟部件），并且确定分类
     *
     * @param partNumber 编号（分类）
     * @param topPart    虚拟BOM的顶层件
     */
    private WTPart createPart(String partNumber, WTPart topPart) throws WTException, WTPropertyVetoException {
        WTPart part = null;
        try {
            WTContainer container = topPart.getContainer();
            SubFolderReference parentFolder = topPart.getParentFolder();
            int startIndex = partNumber.indexOf("(");
            if (startIndex == -1) {
                throw new WTException("新建虚拟部件分类不能为空");
            }
            int endIndex = partNumber.indexOf(")");
            //编号
            String number = partNumber.substring(0, startIndex);
            //分类
            String classification = partNumber.substring(startIndex + 1, endIndex);
            String exchangeDomain = TypeDomainHelper.getExchangeDomain();
            //虚拟部件的全内部名称
            String softType = exchangeDomain + ".VirtualPart";
            //创建虚拟部件
            part = WTPart.newWTPart();
            //获取软类型，softType 值为，软类型的内部值
            TypeDefinitionReference definitionReference = ClientTypedUtility.getTypeDefinitionReference(softType);
            if (definitionReference != null) {
                //设置软类型
                part.setTypeDefinitionReference(definitionReference);
            }
            //设置名称
            part.setName(partNumber);
            //设置编号
            part.setNumber(number);
            //设置容器
            part.setContainer(container);
            //设置文件夹
            FolderHelper.assignLocation(part, (Folder) parentFolder.getObject());
            //设置视图
            View design = ViewHelper.service.getView("Design");
            ViewReference viewReference = ViewReference.newViewReference(design);
            part.setView(viewReference);
            part = (WTPart) PersistenceHelper.manager.save(part);

            //修改分类
            part = (WTPart) PIAttributeHelper.service.updateSoftAttribute(part, "Classification", classification);
        } catch (WTException e) {
            e.printStackTrace();
            throw new WTException(e);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return part;
    }


}
