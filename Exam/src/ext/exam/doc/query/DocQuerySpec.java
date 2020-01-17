package ext.exam.doc.query;

import com.ptc.core.meta.common.FloatingPoint;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import ext.lang.PIStringUtils;
import ext.pi.core.PIAttributeHelper;
import org.apache.log4j.Logger;
import wt.doc.WTDocument;
import wt.fc.ObjectIdentifier;
import wt.fc.ObjectReference;
import wt.fc.PersistInfo;
import wt.fc.Persistable;
import wt.iba.definition.litedefinition.*;
import wt.iba.definition.service.IBADefinitionHelper;
import wt.iba.value.*;
import wt.log4j.LogR;
import wt.org.TimeZoneHelper;
import wt.pds.StatementSpec;
import wt.query.*;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTStandardDateFormat;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

/**
 * 构建查询语句类
 *
 * @author 段鑫扬
 */
public class DocQuerySpec implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = DocQuerySpec.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final Double precision = 0.01d;//精度
    private String name;//名称
    private String number;//编号

    private String searchBeginTime;//修改开始时间
    private String searchEndTime;//修改结束时间

    private String customerName;//客户名称
    private String customerNameId = "CustomerName";//客户名称的内部值

    private Map<Long, Object> ibaMap = null;//iba属性的id定义值，查询值
    private Map<Long, AttributeDefDefaultView> ibaDefMap = null;//iba属性的id定义值，iba属性定义

    public DocQuerySpec() {
        ibaMap = new HashMap<>();
        ibaDefMap = new HashMap<>();
    }

    public DocQuerySpec(String name, String number, String customerName) {
        this();
        this.name = name;
        this.number = number;
        this.customerName = customerName;
    }

    public DocQuerySpec(NmCommandBean commandBean) throws WTException {
        this();
        try {
            HashMap textMap = commandBean.getText();
            this.name = (String) textMap.get("searchName");
            this.number = (String) textMap.get("searchNumber");
            this.customerName = (String) textMap.get("customerName");
            this.searchBeginTime = (String) textMap.get("searchBeginTime_col_searchBeginTime");
            this.searchEndTime = (String) textMap.get("searchEndTime_col_searchEndTime");
            Map<String, Object> ibaMap = new HashMap<>();

            String testQueryInteger = (String) textMap.get("TestQueryInteger");
            if (PIStringUtils.hasText(testQueryInteger)) {
                ibaMap.put("testInteger", Integer.parseInt(testQueryInteger));
            }

            String testQueryDouble = (String) textMap.get("TestQueryDouble");
            if (PIStringUtils.hasText(testQueryDouble)) {
                ibaMap.put("testDouble", Double.parseDouble(testQueryDouble));
            }

            String testQueryTime = (String) textMap.get("TestQueryTime_col_TestQueryTime");
            if (PIStringUtils.hasText(testQueryTime)) {
                Date ibaDate = WTStandardDateFormat.parse(testQueryTime, "yyyy/MM/dd"
                        , commandBean.getLocale(), TimeZoneHelper.getTimeZone());
                ibaMap.put("testDate", ibaDate);
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> ibaMap=" + ibaMap);
            }
            setIBAMap(ibaMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
    }

    private void setIBAMap(Map<String, Object> map) throws WTException {
        if (map == null) {
            return;
        }
        for (String ibaName : map.keySet()) {
            setIBA(ibaName, map.get(ibaName));
        }
    }

    /**
     * 设置iba
     *
     * @param ibaName  iba属性内部值
     * @param ibaValue iba值
     */
    private void setIBA(String ibaName, Object ibaValue) throws WTException {
        if (ibaValue == null || ibaValue.equals("")) {
            return;
        }
        if (ibaValue instanceof Object[]) {
            throw new WTException("不考虑多值");
        }
        try {
            AttributeDefDefaultView addv = PIAttributeHelper.service.findAttributeDefinition(ibaName);
            if (addv == null) {
                throw new WTException("未找到属性" + ibaName + "的定义");
            }
            this.ibaMap.put(addv.getObjectID().getId(), ibaValue);
            this.ibaDefMap.put(addv.getObjectID().getId(), addv);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
    }


    /**
     * 构建查询
     *
     * @return
     * @throws WTException
     */
    public QuerySpec buildQuerySpec() throws WTException {
        QuerySpec qs = null;
        try {
            qs = new QuerySpec(WTDocument.class);
            appendNumber(qs);//添加编号
            appendName(qs);//添加名称
            appendCustomerName(qs);//添加客户名称
            appendIBA(qs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>buildQuerySpec() sql=" + qs);
        }
        return qs;
    }

    /**
     * 构建iba查询
     *
     * @param qs
     */
    private void appendIBA(QuerySpec qs) throws WTException {
        if (ibaMap.isEmpty()) {
            return;
        }
        StatementSpec subQs = buildIBASubQuerySpec();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>subQs= " + subQs);
        }
        if (subQs instanceof CompoundQuerySpec) {
            CompoundQuerySpec cqs = (CompoundQuerySpec) subQs;
            if (cqs == null || cqs.getComponents().isEmpty()) {
                return;
            }
        }
        appendAnd(qs);
        SubSelectExpression subSelectExpression = new SubSelectExpression(subQs);
        String ida2a2 = WTDocument.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute ida2a2Attr = new ClassAttribute(WTDocument.class, ida2a2);
        SearchCondition sc = new SearchCondition(ida2a2Attr, SearchCondition.IN, subSelectExpression);
        qs.appendWhere(sc, new int[]{0});
        qs.setQueryLimit(-1);
        qs.setAdvancedQueryEnabled(true);
    }

    /**
     * 使用查询的交集运算，查询IBA关联部件的ida2a2
     *
     * @return
     */
    private StatementSpec buildIBASubQuerySpec() throws WTException {
        try {
            CompoundQuerySpec compoundQuerySpec = new CompoundQuerySpec();
            compoundQuerySpec.setAdvancedQueryEnabled(true);
            compoundQuerySpec.setSetOperator(SetOperator.INTERSECT);

            for (Long ibaId : this.ibaMap.keySet()) {
                StatementSpec subQs = buildIBASubQuerySpec(ibaId, this.ibaMap.get(ibaId));
                compoundQuerySpec.addComponent(subQs);
            }
            return compoundQuerySpec;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
    }

    /**
     * 根据iba属性查询相关部件的ida2a2
     *
     * @param ibaId
     * @param ibaValue
     * @return
     */
    private StatementSpec buildIBASubQuerySpec(Long ibaId, Object ibaValue) throws WTException {
        try {
            AttributeDefDefaultView ibaDef = ibaDefMap.get(ibaId);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>ibaDef class= " + ibaDef.getAttributeDefinitionClassName());
                LOGGER.debug(">>>>class= " + ibaDef.getClass());
            }
            QuerySpec qs = new QuerySpec();
            if (ibaDef instanceof StringDefView) {//字符串类型
                buildStringIBA(ibaId, ibaValue, qs);
            } else if (ibaDef instanceof BooleanDefView) {//布尔类型
                buildBooleanIBA(ibaId, ibaValue, qs);
            } else if (ibaDef instanceof FloatDefView) {//实数类型，浮点数
                buildFloatIBA(ibaId, ibaValue, qs);
            } else if (ibaDef instanceof IntegerDefView) {//整数
                buildIntegerIBA(ibaId, ibaValue, qs);
            } else if (ibaDef instanceof TimestampDefView) {//日期
                buildTimestampIBA(ibaId, ibaValue, qs);
            } else {
                throw new WTException("目前只支持 日期，字符串，布尔，实数，整数类型");
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> qs =" + qs);
            }
            return qs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
    }

    private void buildTimestampIBA(Long ibaId, Object ibaValue, QuerySpec qs) throws WTException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> 日期");
        }
        Timestamp timeValue = null;
        if (ibaValue instanceof Timestamp) {
            timeValue = ((Timestamp) ibaValue);
        } else if (ibaValue instanceof Date) {
            timeValue = new Timestamp(((Date) ibaValue).getTime());
        } else {
            throw new WTException("--TimestampDefView类型错误：" + ibaValue);
        }
        int index = qs.appendClassList(TimestampValue.class, false);
        ClassAttribute ca = new ClassAttribute(TimestampValue.class, AbstractValue.IBAHOLDER_REFERENCE + "."
                + ObjectReference.KEY + "." + ObjectIdentifier.ID);
        qs.appendSelect(ca, new int[]{index}, false);
        //指定idA3A6列的值
        SearchCondition sc = new SearchCondition(TimestampValue.class, IntegerValue.DEFINITION_REFERENCE + ""
                + "." + ObjectReference.KEY + "."
                + ObjectIdentifier.ID, SearchCondition.EQUAL, ibaId);
        qs.appendWhere(sc, new int[]{index});
        qs.appendAnd();
        //不确定。
        qs.appendWhere(new SearchCondition(TimestampValue.class, TimestampValue.VALUE
                , SearchCondition.EQUAL, timeValue), new int[]{index});

    }

    private void buildIntegerIBA(Long ibaId, Object ibaValue, QuerySpec qs) throws WTException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> 整数型");
        }
        long longValue = 0L;
        if (ibaValue instanceof Long) {
            longValue = ((Long) ibaValue);
        } else if (ibaValue instanceof Integer) {
            longValue = (Integer) ibaValue;
        } else if (ibaValue instanceof String) {
            longValue = Long.parseLong((String) ibaValue);
        } else {
            throw new WTException("--IntegerDefView类型错误：" + ibaValue);
        }
        int index = qs.appendClassList(IntegerValue.class, false);
        ClassAttribute ca = new ClassAttribute(IntegerValue.class, AbstractValue.IBAHOLDER_REFERENCE + "."
                + ObjectReference.KEY + "." + ObjectIdentifier.ID);
        qs.appendSelect(ca, new int[]{index}, false);
        //指定idA3A6列的值
        SearchCondition sc = new SearchCondition(IntegerValue.class, IntegerValue.DEFINITION_REFERENCE + ""
                + "." + ObjectReference.KEY + "."
                + ObjectIdentifier.ID, SearchCondition.EQUAL, ibaId);
        qs.appendWhere(sc, new int[]{index});
        qs.appendAnd();
        qs.appendWhere(new SearchCondition(IntegerValue.class, IntegerValue.VALUE
                , SearchCondition.EQUAL, longValue), new int[]{index});

    }

    private void buildFloatIBA(Long ibaId, Object ibaValue, QuerySpec qs) throws WTException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> 实数");
        }
        double doubleValue = 0d;
        if (ibaValue instanceof Float) {
            doubleValue = ((Float) ibaValue).doubleValue();
        } else if (ibaValue instanceof Double) {
            doubleValue = (double) ibaValue;
        } else if (ibaValue instanceof String) {
            doubleValue = Double.parseDouble((String) ibaValue);
        } else if (ibaValue instanceof FloatingPoint) {
            doubleValue = ((FloatingPoint) ibaValue).doubleValue();
        } else {
            throw new WTException("--floatDefView类型错误：" + ibaValue);
        }
        int index = qs.appendClassList(FloatValue.class, false);
        ClassAttribute ca = new ClassAttribute(FloatValue.class, AbstractValue.IBAHOLDER_REFERENCE + "."
                + ObjectReference.KEY + "." + ObjectIdentifier.ID);
        qs.appendSelect(ca, new int[]{index}, false);
        //指定idA3A6列的值
        SearchCondition sc = new SearchCondition(FloatValue.class, FloatValue.DEFINITION_REFERENCE + ""
                + "." + ObjectReference.KEY + "."
                + ObjectIdentifier.ID, SearchCondition.EQUAL, ibaId);
        qs.appendWhere(sc, new int[]{index});
        qs.appendAnd();
        qs.appendOpenParen();
        //value 需要转大写，value2是实际值
        qs.appendWhere(new SearchCondition(FloatValue.class, FloatValue.VALUE
                , SearchCondition.GREATER_THAN_OR_EQUAL, doubleValue - precision), new int[]{index});
        qs.appendAnd();
        qs.appendWhere(new SearchCondition(FloatValue.class, FloatValue.VALUE
                , SearchCondition.LESS_THAN_OR_EQUAL, doubleValue + precision), new int[]{index});
        qs.appendCloseParen();
    }

    private void buildBooleanIBA(Long ibaId, Object ibaValue, QuerySpec qs) throws QueryException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> 布尔");
        }
        int index = qs.appendClassList(BooleanValue.class, false);
        ClassAttribute ca = new ClassAttribute(BooleanValue.class, AbstractValue.IBAHOLDER_REFERENCE + "."
                + ObjectReference.KEY + "." + ObjectIdentifier.ID);
        qs.appendSelect(ca, new int[]{index}, false);
        //指定idA3A6列的值
        SearchCondition sc = new SearchCondition(BooleanValue.class, BooleanValue.DEFINITION_REFERENCE + ""
                + "." + ObjectReference.KEY + "."
                + ObjectIdentifier.ID, SearchCondition.EQUAL, ibaId);
        qs.appendWhere(sc, new int[]{index});
        qs.appendAnd();
        if ((Boolean) ibaValue) {
            qs.appendWhere(new SearchCondition(BooleanValue.class, BooleanValue.VALUE
                    , SearchCondition.IS_TRUE), new int[]{index});
        } else {
            qs.appendWhere(new SearchCondition(BooleanValue.class, BooleanValue.VALUE
                    , SearchCondition.IS_FALSE), new int[]{index});
        }
    }

    private void buildStringIBA(Long ibaId, Object ibaValue, QuerySpec qs) throws QueryException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> 字符串");
        }
        String stringValue = (String) ibaValue;
        int index = qs.appendClassList(StringValue.class, false);
        ClassAttribute ca = new ClassAttribute(StringValue.class, AbstractValue.IBAHOLDER_REFERENCE + "."
                + ObjectReference.KEY + "." + ObjectIdentifier.ID);
        qs.appendSelect(ca, new int[]{index}, false);
        //指定idA3A6列的值
        SearchCondition sc = new SearchCondition(StringValue.class, StringValue.DEFINITION_REFERENCE + ""
                + "." + ObjectReference.KEY + "."
                + ObjectIdentifier.ID, SearchCondition.EQUAL, ibaId);
        qs.appendWhere(sc, new int[]{index});
        qs.appendAnd();
        //value 需要转大写，value2是实际值
        qs.appendWhere(new SearchCondition(StringValue.class, StringValue.VALUE
                , SearchCondition.EQUAL, stringValue.toUpperCase()), new int[]{index});
    }


    /**
     * 添加客户名称查询
     *
     * @param qs
     */
    private void appendCustomerName(QuerySpec qs) throws WTException, RemoteException {
        if (PIStringUtils.hasText(customerNameId) && (PIStringUtils.hasText(customerName))) {
            //iba查询
            AttributeDefDefaultView addv = IBADefinitionHelper.service.getAttributeDefDefaultViewByPath(customerNameId);
            if (addv != null) {
                long ibaDefId = addv.getObjectID().getId();//拿到IBA属性定义的id
                LOGGER.debug("customerNameId=" + customerNameId + ",ibaDefId=" + ibaDefId);

                QuerySpec qsub = new QuerySpec();
                int index = qsub.appendClassList(StringValue.class, false);
                //查询ida3a4列
                ClassAttribute ca = new ClassAttribute(StringValue.class, AbstractValue.IBAHOLDER_REFERENCE + "."
                        + ObjectReference.KEY + "." + ObjectIdentifier.ID);
                qsub.appendSelect(ca, new int[]{index}, false);

                //指定idA3A6列的值
                SearchCondition sc = new SearchCondition(StringValue.class, StringValue.DEFINITION_REFERENCE + ""
                        + "." + ObjectReference.KEY + "."
                        + ObjectIdentifier.ID, SearchCondition.EQUAL, ibaDefId);
                qsub.appendWhere(sc, new int[]{index});
                qsub.appendAnd();

                //模糊查询
                customerName = "%" + customerName + "%";
                sc = new SearchCondition(StringValue.class, StringValue.VALUE,
                        SearchCondition.LIKE, customerName);
                qsub.appendWhere(sc, new int[]{index});
                //构造子查询
                LOGGER.debug("子查询sql=" + qsub);
                SubSelectExpression sse = new SubSelectExpression(qsub);

                //添加到主查询
                appendAnd(qs);
                ClassAttribute ida2a2 = new ClassAttribute(WTDocument.class,
                        Persistable.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID);
                qs.appendWhere(new SearchCondition(ida2a2, SearchCondition.IN, sse), new int[]{0});
                qs.setAdvancedQueryEnabled(true);//设置高级查询
            }
        }

    }

    /**
     * 添加修改时间
     *
     * @param qs
     * @throws QueryException
     */
    private void appendM(QuerySpec qs) throws WTException, ParseException {
        if (PIStringUtils.hasText(searchBeginTime) || PIStringUtils.hasText(searchEndTime)) {
            Timestamp startTime = null;
            Timestamp endTime = null;
            Locale locale = SessionHelper.manager.getLocale();
            TimeZone timeZone = TimeZoneHelper.getTimeZone();
            if (PIStringUtils.hasText(searchBeginTime)) {
                Date parse = WTStandardDateFormat.parse(searchBeginTime, "yyyy/MM/dd", locale, timeZone);
                startTime = new Timestamp(parse.getTime());
            }

            if (PIStringUtils.hasText(searchEndTime)) {
                searchEndTime = searchEndTime + "23:59:59";
                Date parse = WTStandardDateFormat.parse(searchEndTime, "yyyy/MM/dd HH:mm:ss", locale, timeZone);
                endTime = new Timestamp(parse.getTime());
            }

            appendAnd(qs);
            qs.appendOpenParen();
            ClassAttribute ca = new ClassAttribute(WTDocument.class, WTDocument.MODIFY_TIMESTAMP);
            if (startTime != null && endTime == null) {
                qs.appendWhere(new SearchCondition(ca, SearchCondition.GREATER_THAN_OR_EQUAL
                        , new ConstantExpression(startTime)), new int[]{0});
            } else if (startTime == null && endTime != null) {
                qs.appendWhere(new SearchCondition(ca, SearchCondition.LESS_THAN_OR_EQUAL
                        , new ConstantExpression(endTime)), new int[]{0});
            } else if (startTime != null && endTime != null) {
                AttributeRange attributeRange = new AttributeRange(startTime, endTime);
                qs.appendWhere(new SearchCondition(WTDocument.class, WTDocument.MODIFY_TIMESTAMP
                        , true, attributeRange), new int[]{0});
            }
            qs.appendCloseParen();
        }
    }

    /**
     * 添加编号查询
     *
     * @param qs
     */
    private void appendNumber(QuerySpec qs) throws QueryException {
        if (PIStringUtils.hasText(number)) {
            //模糊查询
            number = "%" + number + "%";
            number = number.replace("_", "\\_");
            appendAnd(qs);
            ConstantExpression numberConstant = new ConstantExpression(number.toUpperCase());
            //设置转义字符 \ 忽略
            numberConstant.setUseEscape(true);
            ClassAttribute numberCA = new ClassAttribute(WTDocument.class, WTDocument.NUMBER);
            SearchCondition sc = new SearchCondition(numberCA, SearchCondition.LIKE, numberConstant);
            qs.appendWhere(sc, new int[]{0});
        }
    }

    /**
     * 添加名称查询
     *
     * @param qs
     */
    private void appendName(QuerySpec qs) throws QueryException {
        if (PIStringUtils.hasText(name)) {
            name = "%" + name + "%";
            name = name.replace("_", "\\_");
            appendAnd(qs);
            ConstantExpression nameConstant = new ConstantExpression(name);
            //设置转义字符 \ 忽略
            nameConstant.setUseEscape(true);

            ClassAttribute nameCA = new ClassAttribute(WTDocument.class, WTDocument.NAME);
            SearchCondition sc = new SearchCondition(nameCA,
                    SearchCondition.LIKE, nameConstant);
            qs.appendWhere(sc, new int[]{0});
        }
    }

    /**
     * 多个查询条件组合查询时，在中间添加And关键字
     *
     * @param qs
     */
    public void appendAnd(QuerySpec qs) {
        WhereClause whereClause = qs.getWhereClause();
        int count = whereClause.getCount();
        if (count > 0) {
            qs.appendAnd();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNameId() {
        return customerNameId;
    }

    public void setCustomerNameId(String customerNameId) {
        this.customerNameId = customerNameId;
    }
}


