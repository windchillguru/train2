package ext.task.query.queryspec;

import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.TypeIdentifierHelper;
import ext.lang.PIStringUtils;
import org.apache.log4j.Logger;
import wt.doc.WTDocument;
import wt.fc.ObjectIdentifier;
import wt.fc.ObjectReference;
import wt.fc.PersistInfo;
import wt.fc.Persistable;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.definition.service.IBADefinitionHelper;
import wt.iba.value.*;
import wt.log4j.LogR;
import wt.query.*;
import wt.type.TypedUtility;
import wt.util.WTException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;


/**
 * @author 段鑫扬
 */
public class DocQuerySpecIBA implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = DocQuerySpecIBA.class.getName();
    private static final Logger logger = LogR.getLogger(CLASSNAME);

    private String number;//编号
    private String name;//名称
    private String softType;//软类型
    private String booleanIBAName;//布尔IBA属性内部值
    private Boolean booleanIBAValue; //布尔IBA属性值
    private String urlIBAName;
    private String urlIBAValue;
    private String dateIBAName;//日期型IBA属性内部值
    private Timestamp dateIBAValue;//日期型IBA属性内部值
    private String doubleIBAName;//实数
    private Double doubleIBAValue;//实数


    public DocQuerySpecIBA() {
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
            appendSoftType(qs);//添加软类型
            appendBooleanIBA(qs);//添加IBA
            appendURLIBA(qs);
            appendDateIBA(qs);
            appendFloatIBA(qs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug(">>>buildQuerySpec() sql=" + qs);
        }
        return qs;
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

    /**
     * 添加版本最新LATEST_ITERATION
     *
     * @param qs
     * @throws WTException
     */
    protected void appendLatest(QuerySpec qs) throws WTException {
        appendAnd(qs);
        qs.appendWhere(new SearchCondition(WTDocument.class,
                        WTDocument.LATEST_ITERATION, SearchCondition.IS_TRUE),
                new int[]{0});
    }

    /**
     * 添加编号
     *
     * @param qs
     * @throws WTException
     */
    protected void appendNumber(QuerySpec qs) throws WTException {
        if (PIStringUtils.hasText(number)) {
            //如果想自动模糊查询
            number = "%" + number + "%";
            number = number.replace("*", "%");
            appendAnd(qs);
            ConstantExpression numberConstant = new ConstantExpression(number.toUpperCase());
            ClassAttribute numberCA = new ClassAttribute(WTDocument.class, WTDocument.NUMBER);
            SearchCondition sc = new SearchCondition(numberCA, SearchCondition.LIKE, numberConstant);

//			sc = new SearchCondition(WTDocument.class,WTDocument.NUMBER,
//					SearchCondition.LIKE, number, false );

            qs.appendWhere(sc, new int[]{0});
        }

    }

    /**
     * 添加名称
     *
     * @param qs
     * @throws WTException
     */
    protected void appendName(QuerySpec qs) throws WTException {
        if (PIStringUtils.hasText(name)) {
            //如果想自动模糊查询
//			name = "%"+name+"%";
            name = name.replace("*", "%");
            appendAnd(qs);
            //false 代表忽略大小写，true代表严格匹配
            SearchCondition sc = new SearchCondition(WTDocument.class, WTDocument.NAME,
                    SearchCondition.LIKE, name, false);
            qs.appendWhere(sc, new int[]{0});
        }

    }

    /**
     * 添加软类型
     *
     * @param qs
     * @throws WTException
     */
    protected void appendSoftType(QuerySpec qs) throws WTException {
        if (PIStringUtils.hasText(softType)) {
            TypeIdentifier typeIdentifier = TypeIdentifierHelper.getTypeIdentifier(softType);
            //第二个参数目前没有研究出具体的作用，true和false效果一样，都是只能查到给定软类型，并不能获得
            SearchCondition sc = TypedUtility.getSearchCondition(typeIdentifier, false);
            qs.appendWhere(sc, new int[]{0});
        }

    }

    /**
     * 添加布尔型IBA
     *
     * @param qs
     * @throws WTException
     * @throws RemoteException
     */
    private void appendBooleanIBA(QuerySpec qs) throws WTException, RemoteException {
        if (PIStringUtils.hasText(booleanIBAName) && (booleanIBAValue != null)) {
            //iba查询
            AttributeDefDefaultView addv = IBADefinitionHelper.service.getAttributeDefDefaultViewByPath(booleanIBAName);
            if (addv != null) {
                long ibaDefId = addv.getObjectID().getId();//拿到IBA属性定义的id
                logger.debug("booleanIBAName=" + booleanIBAName + ",ibaDefId=" + ibaDefId);

                QuerySpec qsub = new QuerySpec();
                int index = qsub.appendClassList(BooleanValue.class, false);
                //查询ida3a4列
                ClassAttribute ca = new ClassAttribute(BooleanValue.class, AbstractValue.IBAHOLDER_REFERENCE + "."
                        + ObjectReference.KEY + "." + ObjectIdentifier.ID);
                qsub.appendSelect(ca, new int[]{index}, false);

                //指定idA3A6列的值
                SearchCondition sc = new SearchCondition(BooleanValue.class, BooleanValue.DEFINITION_REFERENCE + ""
                        + "." + ObjectReference.KEY + "."
                        + ObjectIdentifier.ID, SearchCondition.EQUAL, ibaDefId);
                qsub.appendWhere(sc, new int[]{index});
                qsub.appendAnd();

                if (booleanIBAValue == true) {
                    sc = new SearchCondition(BooleanValue.class, BooleanValue.VALUE,
                            SearchCondition.IS_TRUE);
                }

                if (booleanIBAValue == false) {
                    sc = new SearchCondition(BooleanValue.class, BooleanValue.VALUE,
                            SearchCondition.IS_FALSE);
                }

                qsub.appendWhere(sc, new int[]{index});
                //构造子查询
                logger.debug("子查询sql=" + qsub);
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

    private void appendURLIBA(QuerySpec qs) throws WTException, RemoteException {
        if (PIStringUtils.hasText(urlIBAName) && (urlIBAValue != null)) {
            //iba查询
            AttributeDefDefaultView addv = IBADefinitionHelper.service.getAttributeDefDefaultViewByPath(urlIBAName);
            if (addv != null) {
                long ibaDefId = addv.getObjectID().getId();//拿到IBA属性定义的id
                logger.debug("urlIBAName=" + urlIBAName + ",ibaDefId=" + ibaDefId);

                QuerySpec qsub = new QuerySpec();
                int index = qsub.appendClassList(URLValue.class, false);
                //查询ida3a4列
                ClassAttribute ca = new ClassAttribute(URLValue.class, AbstractValue.IBAHOLDER_REFERENCE + "."
                        + ObjectReference.KEY + "." + ObjectIdentifier.ID);
                qsub.appendSelect(ca, new int[]{index}, false);

                //指定idA3A6列的值
                SearchCondition sc = new SearchCondition(URLValue.class, URLValue.DEFINITION_REFERENCE + ""
                        + "." + ObjectReference.KEY + "."
                        + ObjectIdentifier.ID, SearchCondition.EQUAL, ibaDefId);
                qsub.appendWhere(sc, new int[]{index});
                qsub.appendAnd();

                sc = new SearchCondition(URLValue.class, URLValue.VALUE,
                        SearchCondition.EQUAL, urlIBAValue);


                qsub.appendWhere(sc, new int[]{index});
                //构造子查询
                logger.debug("子查询sql=" + qsub);
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

    private void appendDateIBA(QuerySpec qs) throws WTException, RemoteException {
        if (PIStringUtils.hasText(dateIBAName) && (dateIBAValue != null)) {
            //iba查询
            AttributeDefDefaultView addv = IBADefinitionHelper.service.getAttributeDefDefaultViewByPath(dateIBAName);
            if (addv != null) {
                long ibaDefId = addv.getObjectID().getId();//拿到IBA属性定义的id
                logger.debug("urlIBAName=" + dateIBAName + ",ibaDefId=" + ibaDefId);

                QuerySpec qsub = new QuerySpec();
                int index = qsub.appendClassList(TimestampValue.class, false);
                //查询ida3a4列
                ClassAttribute ca = new ClassAttribute(TimestampValue.class, AbstractValue.IBAHOLDER_REFERENCE + "."
                        + ObjectReference.KEY + "." + ObjectIdentifier.ID);
                qsub.appendSelect(ca, new int[]{index}, false);

                //指定idA3A6列的值
                SearchCondition sc = new SearchCondition(TimestampValue.class, TimestampValue.DEFINITION_REFERENCE + ""
                        + "." + ObjectReference.KEY + "."
                        + ObjectIdentifier.ID, SearchCondition.EQUAL, ibaDefId);
                qsub.appendWhere(sc, new int[]{index});
                qsub.appendAnd();

                sc = new SearchCondition(TimestampValue.class, TimestampValue.VALUE,
                        SearchCondition.EQUAL, dateIBAValue);


                qsub.appendWhere(sc, new int[]{index});
                //构造子查询
                logger.debug("子查询sql=" + qsub);
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

    private void appendFloatIBA(QuerySpec qs) throws WTException, RemoteException {
        if (PIStringUtils.hasText(doubleIBAName) && (doubleIBAValue != null)) {
            //iba查询
            AttributeDefDefaultView addv = IBADefinitionHelper.service.getAttributeDefDefaultViewByPath(doubleIBAName);
            if (addv != null) {
                long ibaDefId = addv.getObjectID().getId();//拿到IBA属性定义的id
                logger.debug("urlIBAName=" + doubleIBAName + ",ibaDefId=" + ibaDefId);

                QuerySpec qsub = new QuerySpec();
                int index = qsub.appendClassList(FloatValue.class, false);
                //查询ida3a4列
                ClassAttribute ca = new ClassAttribute(FloatValue.class, AbstractValue.IBAHOLDER_REFERENCE + "."
                        + ObjectReference.KEY + "." + ObjectIdentifier.ID);
                qsub.appendSelect(ca, new int[]{index}, false);

                //指定idA3A6列的值
                SearchCondition sc = new SearchCondition(FloatValue.class, FloatValue.DEFINITION_REFERENCE + ""
                        + "." + ObjectReference.KEY + "."
                        + ObjectIdentifier.ID, SearchCondition.EQUAL, ibaDefId);
                qsub.appendWhere(sc, new int[]{index});
                qsub.appendAnd();

                sc = new SearchCondition(FloatValue.class, FloatValue.VALUE,
                        SearchCondition.EQUAL, doubleIBAValue);


                qsub.appendWhere(sc, new int[]{index});
                //构造子查询
                logger.debug("子查询sql=" + qsub);
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
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the softType
     */
    public String getSoftType() {
        return softType;
    }

    /**
     * @param softType the softType to set
     */
    public void setSoftType(String softType) {
        this.softType = softType;
    }

    /**
     * @return the booleanIBAName
     */
    public String getBooleanIBAName() {
        return booleanIBAName;
    }

    /**
     * @param booleanIBAName the booleanIBAName to set
     */
    public void setBooleanIBAName(String booleanIBAName) {
        this.booleanIBAName = booleanIBAName;
    }

    public Boolean getBooleanIBAValue() {
        return booleanIBAValue;
    }

    public String getUrlIBAName() {
        return urlIBAName;
    }

    public void setUrlIBAName(String urlIBAName) {
        this.urlIBAName = urlIBAName;
    }

    public String getUrlIBAValue() {
        return urlIBAValue;
    }

    public void setUrlIBAValue(String urlIBAValue) {
        this.urlIBAValue = urlIBAValue;
    }

    public void setBooleanIBAValue(Boolean booleanIBAValue) {
        this.booleanIBAValue = booleanIBAValue;
    }

    public String getDateIBAName() {
        return dateIBAName;
    }

    public void setDateIBAName(String dateIBAName) {
        this.dateIBAName = dateIBAName;
    }

    public Timestamp getDateIBAValue() {
        return dateIBAValue;
    }

    public void setDateIBAValue(Timestamp dateIBAValue) {
        this.dateIBAValue = dateIBAValue;
    }

    public String getDoubleIBAName() {
        return doubleIBAName;
    }

    public void setDoubleIBAName(String doubleIBAName) {
        this.doubleIBAName = doubleIBAName;
    }

    public Double getDoubleIBAValue() {
        return doubleIBAValue;
    }

    public void setDoubleIBAValue(Double doubleIBAValue) {
        this.doubleIBAValue = doubleIBAValue;
    }

    @Override
    public String toString() {
        return "DocQuerySpecIBA{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", softType='" + softType + '\'' +
                ", booleanIBAName='" + booleanIBAName + '\'' +
                ", booleanIBAValue=" + booleanIBAValue +
                '}';
    }
}
