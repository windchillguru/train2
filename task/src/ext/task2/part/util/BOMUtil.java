package ext.task2.part.util;

import com.ptc.core.meta.common.FloatingPoint;
import ext.pi.PIException;
import ext.pi.core.PIAttributeHelper;
import ext.pi.core.PIClassificationHelper;
import ext.pi.core.PICoreHelper;
import ext.pi.core.PIPartHelper;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTCollection;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartUsageLink;
import wt.util.WTException;

import java.util.*;

/**
 * @author 段鑫扬
 * @version 2020/1/6
 * BOM结构工具类
 */
public class BOMUtil {
    //设计视图
    private static final String viewName = "Design";

    /**
     * 返回所有bom树部件
     *
     * @return bom中所有的部件集合
     * @throws WTException
     */
    public static List<WTPart> findBOMChildren(WTPart part) throws WTException {
        List<WTPart> parts = new ArrayList<>();
        findChildren(part, parts);
        return parts;
    }

    /**
     * 遍历bom
     * （不包含父件本身）
     *
     * @param parentPart 父件
     * @param parts      存储部件的集合
     * @throws WTException
     * @
     */
    private static void findChildren(WTPart parentPart, List<WTPart> parts) throws WTException {
        if (parentPart == null) {
            return;
        }
        Map<WTPart, WTPartUsageLink> bom = new HashMap<>();
        findChildren(parentPart, bom);
        Set<WTPart> wtParts = bom.keySet();
        parts.addAll(wtParts);
    }

    /**
     * 返回bom的子部件和link关系的map
     *
     * @param parentPart
     * @return
     * @throws WTException
     */
    public static Map<WTPart, WTPartUsageLink> findChildren(WTPart parentPart) throws WTException {
        if (parentPart == null) {
            return new HashMap<>();
        }
        Map<WTPart, WTPartUsageLink> bom = new HashMap<>();
        findChildren(parentPart, bom);
        return bom;
    }

    /**
     * 遍历 bom ，存储部件和使用关系
     *
     * @param parentPart 父件
     * @param bom        已部件对象为key,link关系为value的map   ， （不包含父件本身）
     * @throws WTException
     */
    private static void findChildren(WTPart parentPart, Map<WTPart, WTPartUsageLink> bom) throws WTException {
        if (parentPart == null) {
            return;
        }
        QueryResult childrenAndLinks = PIPartHelper.service.findChildrenAndLinks(parentPart);
        if (childrenAndLinks.size() == 0) {
            return;
        }
        while (childrenAndLinks.hasMoreElements()) {
            Object nextElement = childrenAndLinks.nextElement();
            if (nextElement instanceof Persistable[]) {
                Persistable[] persistableArray = (Persistable[]) nextElement;
                WTPartUsageLink link = (WTPartUsageLink) persistableArray[0];
                Persistable persistable = persistableArray[1];
                if (persistable instanceof WTPart) {
                    WTPart childPart = (WTPart) persistable;
                    bom.put(childPart, link);
                    findChildren(childPart, bom);
                }
            }
        }

    }

    /**
     * 查询当前部件的子件，单层
     *
     * @param wtPart 父件
     * @return 当前部件的子件集合
     * @throws WTException
     */
    public static List<WTPart> findSingleBOMChildren(WTPart wtPart) throws WTException {
        List<WTPart> parts = new ArrayList<>();
        WTCollection childrenList = PIPartHelper.service.findChildren(wtPart);
        Iterator iterator = childrenList.persistableIterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next instanceof WTPart) {
                WTPart childrenPart = (WTPart) next;
                parts.add(childrenPart);
            }
        }
        return parts;
    }

    /**
     * 验证虚拟部件BOM是否有重复的部件
     * 如果没有重复的，则返回true, 否则抛出异常
     *
     * @param virtualPartTopNum 虚拟部件BOM顶层部件的编号
     * @return 如果没有重复的，则返回true
     * @throws WTException
     */
    public static boolean checkVirtualBOM(String virtualPartTopNum) throws WTException {
        List<WTPart> parts = new ArrayList<>();
        WTPart virtualPartTop = PIPartHelper.service.findWTPart(virtualPartTopNum, viewName);
        check(virtualPartTop, parts);
        return true;
    }

    /**
     * 判断是否是虚拟部件
     *
     * @param part
     * @return
     * @throws WTException
     */
    public static boolean isVirtualPart(WTPart part) throws WTException {
        String simpleTypeName = PICoreHelper.service.getSimpleTypeName(part);
        if ("VirtualPart".equals(simpleTypeName)) {
            return true;
        }
        return false;
    }


    /**
     * 验证分类属性
     * 如果分类属性一致则返回true ，否则抛出异常
     *
     * @param part 当前虚拟件
     * @return 如果分类属性一致则返回true
     * @throws WTException
     */
    public static boolean checkClassification(WTPart part) throws WTException {
        List<WTPart> singleBOMChildren = findSingleBOMChildren(part);
        Collection<String> classifyNodes = PIClassificationHelper.service.getClassifyNodes(part);
        //父件的分类属性
        String parentClassify = "";
        for (String classifyNode : classifyNodes) {
            parentClassify = classifyNode;
        }

        for (WTPart subPart : singleBOMChildren) {

            Collection<String> subPartClassifys = PIClassificationHelper.service.getClassifyNodes(subPart);
            //子件的分类属性
            String subPartClassify = "";
            for (String classifyNode : subPartClassifys) {
                subPartClassify = classifyNode;
            }
            if (isVirtualPart(subPart)) {
                //是虚拟部件
                //判断虚拟部件的分类
                boolean flag = checkPartClassification(subPartClassify, parentClassify);
                if (!flag) {
                    //子件的分类属性不是父件的分类属性的子节点
                    throw new WTException("虚拟部件：" + subPart.getNumber() + "的分类属性不是父件分类属性的子节点");
                }
                continue;
            }

            if (!subPartClassify.equals(parentClassify)) {
                throw new WTException("子件：" + subPart.getNumber() + " 的分类属性和父件分类属性不一致");
            }
        }
        return true;
    }

    /**
     * 验证部件的分类，
     * 子件的分类属性需要是父件的分类属性的子类/同级
     * 如果不是，则返回false
     *
     * @param subPartClassify 子虚拟部件的分类属性
     * @param parentClassify  父虚拟部件的分类属性
     */
    private static boolean checkPartClassification(String subPartClassify, String parentClassify) throws PIException {
        Set<String> classifyTreeNodeNames = new HashSet<>();
        if (subPartClassify.equals(parentClassify)) {
            return true;
        }
        //遍历树
        traverseClassifyTree(parentClassify, classifyTreeNodeNames);
        if (classifyTreeNodeNames.contains(subPartClassify)) {
            return true;
        }
        return false;
    }

    /**
     * 遍历分类属性节点下的所有子节点，（多层）
     *
     * @param parentClassify        需要查询的节点
     * @param classifyTreeNodeNames 存储子节点的所有数据
     */
    private static void traverseClassifyTree(String parentClassify, Collection<String> classifyTreeNodeNames) throws PIException {
        Collection<String> childrenNodeNames = PIClassificationHelper.service.getChildrenNodeNames(parentClassify);
        if (childrenNodeNames == null || childrenNodeNames.size() == 0) {
            return;
        }
        classifyTreeNodeNames.addAll(childrenNodeNames);
        for (String childrenNodeName : childrenNodeNames) {
            traverseClassifyTree(childrenNodeName, classifyTreeNodeNames);
        }
    }

    /**
     * 检查部件的子件的优先级是否有重复
     * 有则抛出异常
     *
     * @param part
     */
    public static void checkPriority(WTPart part) throws WTException {
        //优先级 属性的内部值
        String IBA_priority = "priority";
        if (part == null) {
            return;
        }
        QueryResult childrenAndLinks = PIPartHelper.service.findChildrenAndLinks(part);
        if (childrenAndLinks.size() == 0) {
            return;
        }
        Set<String> prioritySet = new HashSet<>();
        while (childrenAndLinks.hasMoreElements()) {
            Object nextElement = childrenAndLinks.nextElement();
            if (nextElement instanceof Persistable[]) {
                Persistable[] persistableArray = (Persistable[]) nextElement;
                WTPartUsageLink link = (WTPartUsageLink) persistableArray[0];
                //优先级
                Object priority = PIAttributeHelper.service.getValue(link, IBA_priority);
                if (priority instanceof String) {
                    String priorityValue = (String) priority;
                    if (!prioritySet.contains(priorityValue)) {
                        prioritySet.add(priorityValue);
                    } else {
                        WTPartMaster partMaster = (WTPartMaster) link.getRoleBObject();
                        throw new WTException("部件:" + partMaster.getNumber() + " 的优先级已存在,不允许重复");
                    }
                }
            }
        }
    }

    /**
     * 遍历虚拟BOM，并验证是否有重复的部件，有则抛出异常
     *
     * @param parentPart 父件
     * @param parts      部件集合
     * @throws WTException
     */
    private static void check(WTPart parentPart, List<WTPart> parts) throws WTException {
        if (parentPart == null) {
            return;
        }
        WTCollection children = PIPartHelper.service.findChildren(parentPart);
        if (children.size() == 0) {
            return;
        }
        Iterator iterator = children.persistableIterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next instanceof WTPart) {
                WTPart part = (WTPart) next;
                if (parts.contains(part)) {
                    //如果当前子件已经存在于集合中，则抛出异常
                    throw new WTException("同一物料：" + part.getNumber() + " 不允许存在与多个替换组中");
                }
                parts.add(part);
                check(part, parts);
            }
        }
    }

    /**
     * 验证部件是否存在于虚拟BOM中
     *
     * @param part
     * @param topPartNumber
     * @return 部件存在于虚拟BOM中，则返回true ,否则返回false
     */
    public static boolean existInVirtualBOM(WTPart part, String topPartNumber) throws WTException {
        WTPart topPart = PIPartHelper.service.findWTPart(topPartNumber, viewName);
        //虚拟BOM
        List<WTPart> bomChildren = findBOMChildren(topPart);

        return bomChildren.contains(part);
    }

    /**
     * 查询部件在替换BOM中的同级替换件
     *
     * @param part          需要查询替换件的部件
     * @param topPartNumber 替换BOM 顶层件的编号
     * @return 返回替换件的集合
     */
    public static List<WTPart> findReplaceParts(WTPart part, String topPartNumber) throws WTException {
        //公差的内部值
        String tolerance = "tolerance";

        //替换的部件集合
        List<WTPart> replaceParts = new ArrayList<>();
        WTPart topPart = PIPartHelper.service.findWTPart(topPartNumber, viewName);
        //虚拟BOM
        Map<WTPart, WTPartUsageLink> bom = new HashMap<>();
        findChildren(topPart, bom);
        //当前物料的分类是否是电子件或其子集
        boolean isElePart = isElePart(part);
        //当前部件的公差
        Object value = PIAttributeHelper.service.getValue(part, tolerance);
        Double toleranceValue = 0.0;
        if (value instanceof FloatingPoint) {
            toleranceValue = ((FloatingPoint) value).getValue();
        }
        //当前子件对应的link
        WTPartUsageLink link = bom.get(part);
        WTPart usedBy = link.getUsedBy();
        WTCollection children = PIPartHelper.service.findChildren(usedBy);
        Collection collection = children.persistableCollection();
        for (Object o : collection) {
            if (o instanceof WTPart) {
                //同级部件
                WTPart replacePart = (WTPart) o;
                if (PersistenceHelper.isEquivalent(replacePart, part)) {
                    continue;
                }
                if (!isVirtualPart(replacePart)) {
                    if (isElePart) {
                        //是电子件，判断公差
                        //替换件的公差
                        Object replacePartToleran = PIAttributeHelper.service.getValue(replacePart, tolerance);

                        Double replacePartToleranValue = 0.0;
                        if (replacePartToleran instanceof FloatingPoint) {
                            replacePartToleranValue = ((FloatingPoint) replacePartToleran).getValue();
                        }

                        if (replacePartToleranValue < toleranceValue) {
                            //替换件公差比当前部件公差小
                            replaceParts.add(replacePart);
                        }
                    } else {
                        //不是电子件，所有物料直接加入替换物料集合
                        replaceParts.add(replacePart);
                    }
                }
            }
        }
        return replaceParts;
    }

    /**
     * 验证部件的分类是否是电子件或者其子节点  ElectronicPart
     *
     * @return 如果是电子节点则返回true, 否则返回False
     */
    private static boolean isElePart(WTPart part) throws PIException {
        //分类，电子件的内部值
        String elePartClassify = "ElectronicPart";
        Collection<String> subPartClassifys = PIClassificationHelper.service.getClassifyNodes(part);
        //子件的分类属性
        String subClassify = "";
        for (String classifyNode : subPartClassifys) {
            subClassify = classifyNode;
        }

        return checkPartClassification(subClassify, elePartClassify);
    }

}
