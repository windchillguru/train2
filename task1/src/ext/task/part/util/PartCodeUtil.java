package ext.task.part.util;

import ext.lang.PIStringUtils;
import ext.task.part.IPartCodeIfc;
import org.apache.log4j.Logger;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTKeyedHashMap;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.util.WTException;

/**
 * @author 段鑫扬
 */
public class PartCodeUtil implements IPartCodeIfc {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = PartCodeUtil.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    /**
     * 生成正式码
     *
     * @param part
     * @param needCheckClf
     * @return
     */
    public static String generateNumber(WTPart part, boolean needCheckClf) {
        String msg = "";
        WTArrayList parts = new WTArrayList();
        parts.add(part);
        WTKeyedHashMap resultMap = generateNumber(parts, needCheckClf);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> resultMap=" + resultMap);
        }
        StringBuilder error = new StringBuilder("");
        if (resultMap != null && resultMap.containsKey(part)) {
            String info = (String) resultMap.get(part);
            error.append("物料:").append(part.getName()).append("编码异常:").append(info).append(";");
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> error = " + error.toString());
        }
        return msg;
    }

    /**
     * 生成正式码
     *
     * @param parts
     * @param needCheckClf
     * @return map 用来存储编码失败的对象，如果没有失败对象，则返回空map
     */
    public static WTKeyedHashMap generateNumber(WTArrayList parts, boolean needCheckClf) {
        WTKeyedHashMap errMap = new WTKeyedHashMap();

        int size = parts.size();
        for (int i = 0; i < size; i++) {
            WTPart part = null;
            try {
                part = (WTPart) parts.getPersistable(i);
            } catch (WTException e) {
                e.printStackTrace();
            }
            if (part != null) {
                if (!part.getNumber().startsWith(LS_PREFIX)) {
                    //非临时码
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("part: " + part.getIterationIdentifier() + ",不是临时码，不需要修改");
                    }
                    continue;
                }
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(">>>> part:" + part.getIterationIdentifier() + ",开始修改编码");
                }
                PartCoder coder = new PartCoder(part);
                if (coder.canGenerateNumber()) {
                    coder.updatePartNumber();
                    String errMsg = coder.getErrMsg().toString();
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(">>>>errMsg= " + errMsg);
                    }
                    if (PIStringUtils.hasText(errMsg)) {
                        errMap.put(part, errMap);
                    }
                } else {
                    if (needCheckClf && !PIStringUtils.hasText(coder.getClfValue())) {
                        errMap.put(part, "分类为空，无法编码");
                    } else {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug(">>>> part = " + part.getIterationIdentifier() + ",不能更新编码");
                        }
                    }
                }
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(">>>> part = " + part.getIterationIdentifier() + "更新编码结束");
                }
            }
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> generateNumber() errMap =" + errMap);
        }
        return errMap;
    }

    /**
     * 查找最大编码
     *
     * @param part
     * @param likeCode
     * @return
     */
    public static String queryMaxNumber(WTPart part, String likeCode) throws WTException {
        String max = "";
        QuerySpec qs = new QuerySpec();
        int index = qs.appendClassList(WTPartMaster.class, false);
        qs.setAdvancedQueryEnabled(true);
        ClassAttribute numberAttr = new ClassAttribute(WTPartMaster.class, WTPartMaster.NUMBER);
        SQLFunction maxFunction = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, numberAttr);
        qs.appendSelect(maxFunction, new int[]{index}, false);
        likeCode = likeCode.toUpperCase();
        SearchCondition sc = new SearchCondition(WTPartMaster.class, WTPartMaster.NUMBER, SearchCondition.LIKE, likeCode);
        qs.appendWhere(sc, new int[]{index});

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> qs=" + qs);
        }

        QueryResult qr = PersistenceServerHelper.manager.query(qs);
        if (qr != null) {
            while (qr.hasMoreElements()) {
                Object nextElement = qr.nextElement();
                if (nextElement != null && (nextElement instanceof Object[])) {
                    Object[] resultArr = (Object[]) nextElement;
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(">>>>resultArr.len= " + resultArr.length);
                    }
                    max = (String) resultArr[0];
                }
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> queryMaxNumber() max =" + max);
        }
        return max;
    }

    /**
     * 将整数转成指定位数，前面填充0
     *
     * @param value
     * @param flowNumDigit
     * @return
     */
    public static String formatValue(String value, int flowNumDigit) {
        Integer number = Integer.parseInt(value);
        // 0 代表前面补充0
        // flowNumDigit 代表长度为4
        // d 代表参数为正数型
        String formatConfig = "%0" + flowNumDigit + "d";
        value = String.format(formatConfig, number);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>after formatValue() value =" + value);
        }
        return value;
    }
}
