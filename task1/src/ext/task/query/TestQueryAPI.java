package ext.task.query;

import com.ptc.core.lwc.common.view.AttributeDefinitionReadView;
import com.ptc.core.lwc.common.view.TypeDefinitionReadView;
import com.ptc.core.lwc.server.TypeDefinitionServiceHelper;
import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.TypeIdentifierHelper;
import ext.pi.core.PICoreHelper;
import org.apache.log4j.Logger;
import wt.content.ApplicationData;
import wt.content.ContentItem;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.fc.*;
import wt.httpgw.GatewayAuthenticator;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.definition.service.IBADefinitionHelper;
import wt.iba.value.StringValue;
import wt.lifecycle.State;
import wt.log4j.LogR;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.part.WTPartDescribeLink;
import wt.part.WTPartMaster;
import wt.part.WTPartReferenceLink;
import wt.pom.PersistenceException;
import wt.query.*;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtility;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.util.WTStandardDateFormat;
import wt.vc.IterationIdentifier;
import wt.vc.VersionIdentifier;
import wt.vc.VersionInfo;
import wt.vc.config.LatestConfigSpec;
import wt.vc.views.View;
import wt.vc.views.ViewReference;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

/**
 * 测试查询相关的API
 * windchill ext.task.query.TestQueryAPI
 *
 * @author 段鑫扬
 */
public class TestQueryAPI implements RemoteAccess, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLASSNAME = TestQueryAPI.class.getName();
    private static final Logger logger = LogR.getLogger(CLASSNAME);

    public static void main(String[] args) {
        if (args.length >= 0) {
            Class[] clsAry = {String[].class};
            Object[] objAry = {args};
            String method = "testQueryAPI";//执行的方法
            try {
                if (!RemoteMethodServer.ServerFlag) {//是否远程调用
                    GatewayAuthenticator auth = new GatewayAuthenticator();
                    auth.setRemoteUser("Administrator");// 直接设置管理员，执行命令时，不会再弹出输入用户名密码框
                    RemoteMethodServer.getDefault().setAuthenticator(auth);
                    /*
                     * method 执行的方法
                     * CLASSNAME 类名
                     * clsAry
                     * objAry args 参数
                     * */
                    RemoteMethodServer.getDefault().invoke(method, CLASSNAME, null, clsAry, objAry);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static void testQueryAPI(String[] args) throws WTException, PropertyVetoException, IOException, ParseException {
        QuerySpec qs = new QuerySpec(WTPart.class);
        SearchCondition sc = new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.EQUAL, "L0000000244");
        qs.appendWhere(sc, new int[]{0});
        QueryResult queryResult = PersistenceHelper.manager.find(qs);
        if (queryResult.hasMoreElements()) {
            Object nextElement = queryResult.nextElement();
            System.out.println("nextElement = " + nextElement.getClass().getName());
        }
        queryDocLastestAndOrderBy();
    }


    private static void testQueryPartNumber(boolean selectPart, boolean selectNumber) throws WTException {
        QuerySpec qs = new QuerySpec();
        //第二个参数，代表部件是否添加进来查询（类似sql里的*）
        int partIndex = qs.appendClassList(WTPart.class, selectPart);
        ClassAttribute ca = new ClassAttribute(WTPart.class, WTPart.NUMBER);

        //第三个参数，代表属性列是否添加到select后面,如果为true，则不会作为返回结果
        qs.appendSelect(ca, new int[]{partIndex}, selectNumber);
        System.out.println(">>>sql=" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        System.out.println("qr.size=" + qr.size());
        while (qr.hasMoreElements()) {
            Object queryObj = qr.nextElement();
//			System.out.println("queryObj.class="+queryObj.getClass()+",queryObj="+queryObj);
            if (queryObj instanceof Persistable[]) {
                Persistable[] pary = (Persistable[]) queryObj;
                System.out.println("pary.length=" + pary.length);
                for (Persistable pobj : pary) {
                    if (pobj instanceof WTPart) {
                        System.out.println("part=" + PICoreHelper.service.getDisplayIdentifier(pobj));
                    }
                }
            } else if (queryObj instanceof Object[]) {
                Object[] oary = (Object[]) queryObj;
                System.out.println("oary.length=" + oary.length);
                for (Object pobj : oary) {
//					System.out.println("ObjectAry.pobj.class="+pobj.getClass()+",pobj="+pobj);
                    if (pobj instanceof WTPart) {
                        System.out.println("part=" + PICoreHelper.service.getDisplayIdentifier(pobj));
                    }
                    if (pobj instanceof String) {
                        System.out.println("number=" + pobj);
                    }
                }
            }
        }
    }

    private static QueryResult queryPartNumbersUseTableColumn() throws WTException {
        QuerySpec qs = new QuerySpec();
        //启用高级查询
        qs.setAdvancedQueryEnabled(true);

        int classIndex = qs.appendClassList(WTPartMaster.class, false);
        //sql中from语句的表清单
        FromClause fromClause = qs.getFromClause();
        //获取表别名
        String tableAlias = fromClause.getAliasAt(classIndex);
        TableColumn numberTableColumn = new TableColumn(tableAlias, "WTPartNumber");
        qs.appendSelect(numberTableColumn, new int[]{classIndex}, false);
        System.out.println("qs = " + qs);

        QueryResult qr = PersistenceHelper.manager.find(qs);
        return qr;
    }

    private static QueryResult queryUpperNumberAndNameUseSQLFunction() throws WTException {
        QuerySpec qs = new QuerySpec();
        //启用高级查询
        qs.setAdvancedQueryEnabled(true);
        int classIndex = qs.appendClassList(WTPartMaster.class, false);
        //sql中from语句的表清单
        FromClause fromClause = qs.getFromClause();
        //获取表别名
        String tableAlias = fromClause.getAliasAt(classIndex);
        TableColumn numberTableColumn = new TableColumn(tableAlias, "WTPartNumber");
        //将数据转换为小写
        SQLFunction numberLower = SQLFunction.newSQLFunction(SQLFunction.LOWER, numberTableColumn);

        ClassAttribute nameAttr = new ClassAttribute(WTPartMaster.class, WTPartMaster.NAME);
        //将数据转换为大写
        SQLFunction nameUpper = SQLFunction.newSQLFunction(SQLFunction.UPPER, nameAttr);

        qs.appendSelect(numberLower, new int[]{classIndex}, false);
        qs.appendSelect(nameUpper, new int[]{classIndex}, false);

        System.out.println("qs :" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);

        return qr;
    }

    private static QueryResult queryPartByLikeName() throws WTException {
        String likeName = "%a%";
        QuerySpec qs = new QuerySpec(WTPartMaster.class);
        qs.setAdvancedQueryEnabled(true);

        ClassAttribute nameAttr = new ClassAttribute(WTPartMaster.class, WTPartMaster.NAME);
        //将数据转换为大写
        SQLFunction nameUpper = SQLFunction.newSQLFunction(SQLFunction.UPPER, nameAttr);

        ColumnExpression nameExpression = ConstantExpression.newExpression(likeName.trim().toUpperCase(), nameAttr.getColumnDescriptor().getJavaType());
        SearchCondition sc = new SearchCondition(nameUpper, SearchCondition.LIKE, nameExpression);
        qs.appendWhere(sc, new int[]{0});
        System.out.println("qs :" + qs);

        QueryResult qr = PersistenceHelper.manager.find(qs);

        return qr;
    }

    private static QueryResult queryRowNum() throws WTException {
        QuerySpec qs = new QuerySpec();
        qs.setAdvancedQueryEnabled(true);

        int classIndex = qs.appendClassList(WTPart.class, true);
        qs.appendSelect(KeywordExpression.ROWNUM, new int[]{classIndex}, false);
        ClassAttribute attribute = new ClassAttribute(WTPart.class, WTPart.NUMBER);
        qs.appendSelect(attribute, new int[]{classIndex}, false);

        System.out.println("qs :" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        return qr;
    }

    private static QueryResult queryPartByTableExpression() throws WTException, WTPropertyVetoException {
        QuerySpec qs = new QuerySpec();
        qs.setAdvancedQueryEnabled(true);

        ClassTableExpression tableExpression = new ClassTableExpression(WTPartMaster.class);
        int classIndex = qs.appendFrom(tableExpression);
        FromClause fromClause = qs.getFromClause();
        fromClause.setAliasPrefix("B");
        //获取表的别名
        String tableAs = fromClause.getAliasAt(classIndex);
        //通过表名和列名，构建tableColumn
        TableColumn tableColumn = new TableColumn(tableAs, "ida2a2");
        qs.appendSelect(tableColumn, new int[]{classIndex}, false);

        System.out.println("qs :" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        return qr;
    }

    private static ClassViewExpression buildViewForContentItem() throws WTException, WTPropertyVetoException {
        ClassViewExpression classViewExpression = new ClassViewExpression(ApplicationData.class);

        ClassAttribute roleAttr = new ClassAttribute(ContentItem.class, ContentItem.ROLE);
        classViewExpression.appendClassAttribute(roleAttr);

        String persistId = ContentItem.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute persistIdAttr = new ClassAttribute(ContentItem.class, persistId);
        classViewExpression.appendClassAttribute(persistIdAttr);

        ClassAttribute appAttr = new ClassAttribute(ApplicationData.class, "thePersistInfo.theObjectIdentifier.classname");
        classViewExpression.appendClassAttribute(appAttr);

        ClassAttribute fileNameAttr = new ClassAttribute(ApplicationData.class, ApplicationData.FILE_NAME);
        classViewExpression.appendClassAttribute(fileNameAttr);

        return classViewExpression;
    }

    private static QueryResult queryDataFromClassView() throws WTException, WTPropertyVetoException {
        QuerySpec qs = new QuerySpec();
        qs.setAdvancedQueryEnabled(true);

        ClassViewExpression classView = buildViewForContentItem();
        int classIndex = qs.appendFrom(classView);
        FromClause fromClause = qs.getFromClause();
        fromClause.setAliasPrefix("D");
        //获取表的别名
        String tableAs = fromClause.getAliasAt(classIndex);
        //通过表名和列名，构建tableColumn
        TableColumn tableColumn = new TableColumn(tableAs, "ida2a2");
        qs.appendSelect(tableColumn, new int[]{classIndex}, false);

        System.out.println("qs :" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        return qr;
    }

    private static QueryResult querySameNumberOfPartAndDoc() throws WTException, WTPropertyVetoException {
        QuerySpec subQs = new QuerySpec();
        int classIndex = subQs.appendClassList(WTPartMaster.class, false);
        ClassAttribute partNumAttr = new ClassAttribute(WTPartMaster.class, WTPartMaster.NUMBER);
        subQs.appendSelect(partNumAttr, new int[]{classIndex}, true);
        SubSelectExpression subSelectExpression = new SubSelectExpression(subQs);

        QuerySpec qs = new QuerySpec(WTDocumentMaster.class);
        qs.setAdvancedQueryEnabled(true);
        ClassAttribute docNumAttr = new ClassAttribute(WTDocumentMaster.class, WTDocumentMaster.NUMBER);
        SearchCondition sc = new SearchCondition(docNumAttr, SearchCondition.IN, subSelectExpression);
        qs.appendWhere(sc, new int[]{0});

        System.out.println("qs :" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        return qr;
    }

    private static QueryResult queryDaysUntilExpiration() throws WTException, WTPropertyVetoException {
        //外部查询，查oracle 用户
        QuerySpec qs = new QuerySpec();
        qs.setAdvancedQueryEnabled(true);

        ExternalTableExpression externalTableExpression = new ExternalTableExpression("user_users");
        qs.appendFrom(externalTableExpression);

        TableColumn username = new TableColumn(null, "USERNAME");
        qs.appendSelect(username, false);

        TableColumn status = new TableColumn(null, "ACCOUNT_STATUS");
        qs.appendSelect(status, false);

        System.out.println("qs :" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        return qr;
    }

    private static QueryResult queryDataUseNullTableEx() throws WTException, WTPropertyVetoException {
        QuerySpec qs = new QuerySpec();
        qs.setAdvancedQueryEnabled(true);

        ConstantExpression constantExpression = new ConstantExpression(Boolean.class);
        qs.appendSelect(constantExpression, false);

        NullTableExpression nullTableExpression = new NullTableExpression();
        qs.appendFrom(nullTableExpression);
        System.out.println("qs :" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        return qr;
    }

    private static QueryResult queryDataUseSC() throws WTException, WTPropertyVetoException {
        QuerySpec qs = new QuerySpec(WTPart.class);
        SearchCondition sc = new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.EQUAL, "0000000042");
        qs.appendWhere(sc, new int[]{0});
        System.out.println("qs :" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        return qr;
    }

    private static QueryResult queryDataByAnd() throws WTException, WTPropertyVetoException {
        String number = "000000004";
        String name = "ee";
        String version = "A";
        String iteration = "1";
        QuerySpec qs = new QuerySpec(WTPart.class);
        //编号模糊匹配，忽略大小写
        ClassAttribute numAttr = new ClassAttribute(WTPart.class, WTPart.NUMBER);
        SQLFunction numUpper = SQLFunction.newSQLFunction(SQLFunction.UPPER, numAttr);
        ColumnExpression numExpression = ConstantExpression.newExpression("%" + number.trim().toUpperCase() + "%"
                , numAttr.getColumnDescriptor().getJavaType());
        SearchCondition sc = new SearchCondition(numUpper, SearchCondition.LIKE, numExpression);
        qs.appendWhere(sc, new int[]{0});

        //名称模糊匹配，忽略大小写
        qs.appendAnd();
        ClassAttribute nameAttr = new ClassAttribute(WTPart.class, WTPart.NAME);
        SQLFunction nameUpper = SQLFunction.newSQLFunction(SQLFunction.UPPER, nameAttr);
        ColumnExpression nameExpression = ConstantExpression.newExpression("%" + name.trim().toUpperCase() + "%"
                , numAttr.getColumnDescriptor().getJavaType());
        sc = new SearchCondition(nameUpper, SearchCondition.LIKE, nameExpression);
        qs.appendWhere(sc, new int[]{0});

        //大版本相同
        qs.appendAnd();
        String versionInfo = WTPart.VERSION_IDENTIFIER + "." + VersionIdentifier.VERSIONID;
        sc = new SearchCondition(WTPart.class, versionInfo, SearchCondition.EQUAL, version);
        qs.appendWhere(sc, new int[]{0});

        //小版本相同
        qs.appendAnd();
        String iterationInfo = WTPart.ITERATION_IDENTIFIER + "." + IterationIdentifier.ITERATIONID;
        sc = new SearchCondition(WTPart.class, iterationInfo, SearchCondition.EQUAL, iteration);
        qs.appendWhere(sc, new int[]{0});

        System.out.println("qs :" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        return qr;
    }

    private static QueryResult queryDataByOr() throws WTException, WTPropertyVetoException {
        String number = "000000004";
        String name = "ee";
        String version = "A";
        String iteration = "1";
        QuerySpec qs = new QuerySpec(WTPart.class);
        //添加左括号
        qs.appendOpenParen();
        //编号模糊匹配，忽略大小写
        ClassAttribute numAttr = new ClassAttribute(WTPart.class, WTPart.NUMBER);
        SQLFunction numUpper = SQLFunction.newSQLFunction(SQLFunction.UPPER, numAttr);
        ColumnExpression numExpression = ConstantExpression.newExpression("%" + number.trim().toUpperCase() + "%"
                , numAttr.getColumnDescriptor().getJavaType());
        SearchCondition sc = new SearchCondition(numUpper, SearchCondition.LIKE, numExpression);
        qs.appendWhere(sc, new int[]{0});
        //名称模糊匹配，忽略大小写
        qs.appendOr();
        ClassAttribute nameAttr = new ClassAttribute(WTPart.class, WTPart.NAME);
        SQLFunction nameUpper = SQLFunction.newSQLFunction(SQLFunction.UPPER, nameAttr);
        ColumnExpression nameExpression = ConstantExpression.newExpression("%" + name.trim().toUpperCase() + "%"
                , numAttr.getColumnDescriptor().getJavaType());
        sc = new SearchCondition(nameUpper, SearchCondition.LIKE, nameExpression);
        qs.appendWhere(sc, new int[]{0});
        //添加右括号
        qs.appendCloseParen();
        //大版本相同
        qs.appendAnd();
        String versionInfo = WTPart.VERSION_IDENTIFIER + "." + VersionIdentifier.VERSIONID;
        sc = new SearchCondition(WTPart.class, versionInfo, SearchCondition.EQUAL, version);
        qs.appendWhere(sc, new int[]{0});

        //小版本相同
        qs.appendAnd();
        String iterationInfo = WTPart.ITERATION_IDENTIFIER + "." + IterationIdentifier.ITERATIONID;
        sc = new SearchCondition(WTPart.class, iterationInfo, SearchCondition.EQUAL, iteration);
        qs.appendWhere(sc, new int[]{0});

        System.out.println("qs :" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        return qr;
    }

    private static QuerySpec queryLatestPartSubQuery() throws WTException, WTPropertyVetoException {
        String number = "0000000042";
        String viewname = "Design";

        QuerySpec qs = new QuerySpec();
        qs.setAdvancedQueryEnabled(true);
        int classIndex = qs.appendClassList(WTPart.class, false);
        int masterIndex = qs.appendClassList(WTPartMaster.class, false);
        int viewIndex = qs.appendClassList(View.class, false);
        //编号匹配，忽略大小写
        ClassAttribute numAttr = new ClassAttribute(WTPart.class, WTPart.NUMBER);
        SQLFunction numUpper = SQLFunction.newSQLFunction(SQLFunction.UPPER, numAttr);
        ColumnExpression numExpression = ConstantExpression.newExpression(number.trim().toUpperCase(), numAttr.getColumnDescriptor().getJavaType());
        SearchCondition sc = new SearchCondition(numUpper, SearchCondition.EQUAL, numExpression);
        qs.appendWhere(sc, new int[]{classIndex});
        qs.appendAnd();

        //部件的主数据引用id应该和主数据的id一样
        String partMasterRefId = WTPart.MASTER_REFERENCE + "." + ObjectReference.KEY + "." + ObjectIdentifier.ID;
        String masterIda2a2 = WTPartMaster.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute partMasterRefIdAttr = new ClassAttribute(WTPart.class, partMasterRefId);
        ClassAttribute masterIdAttr = new ClassAttribute(WTPartMaster.class, masterIda2a2);
        sc = new SearchCondition(partMasterRefIdAttr, SearchCondition.EQUAL, masterIdAttr);
        qs.appendWhere(sc, new int[]{classIndex, masterIndex});
        qs.appendAnd();

        //视图匹配
        //视图和部件引用id一样
        String viewRefId = WTPart.VIEW + "." + ViewReference.KEY + "." + ObjectIdentifier.ID;
        String viewIda2a2 = WTPartMaster.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute viewRefIdAttr = new ClassAttribute(WTPart.class, viewRefId);
        ClassAttribute viewIdAttr = new ClassAttribute(View.class, viewIda2a2);
        sc = new SearchCondition(viewRefIdAttr, SearchCondition.EQUAL, viewIdAttr);
        qs.appendWhere(sc, new int[]{classIndex, viewIndex});
        qs.appendAnd();
        sc = new SearchCondition(View.class, View.NAME, SearchCondition.EQUAL, viewname.trim());
        qs.appendWhere(sc, new int[]{viewIndex});

        //查询part大版本的最大值
        String versionId = WTPart.VERSION_INFO + "." + VersionInfo.IDENTIFIER + "." + VersionIdentifier.VERSIONID;
        ClassAttribute versionAttribute = new ClassAttribute(WTPart.class, versionId);
        SQLFunction maxVersion = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, versionAttribute);
        qs.appendSelect(maxVersion, new int[]{classIndex}, false);

        System.out.println("qs :" + qs);
        return qs;
    }

    private static QueryResult queryLatestPart() throws WTException, WTPropertyVetoException {
        String number = "0000000042";
        String viewname = "Design";

        QuerySpec qs = new QuerySpec();
        qs.setAdvancedQueryEnabled(true);
        int classIndex = qs.appendClassList(WTPart.class, true);
        int masterIndex = qs.appendClassList(WTPartMaster.class, false);
        int viewIndex = qs.appendClassList(View.class, false);
        //编号匹配，忽略大小写
        ClassAttribute numAttr = new ClassAttribute(WTPart.class, WTPart.NUMBER);
        SQLFunction numUpper = SQLFunction.newSQLFunction(SQLFunction.UPPER, numAttr);
        ColumnExpression numExpression = ConstantExpression.newExpression(number.trim().toUpperCase(), numAttr.getColumnDescriptor().getJavaType());
        SearchCondition sc = new SearchCondition(numUpper, SearchCondition.EQUAL, numExpression);
        qs.appendWhere(sc, new int[]{classIndex});
        qs.appendAnd();

        //部件的主数据引用id应该和主数据的id一样
        String partMasterRefId = WTPart.MASTER_REFERENCE + "." + ObjectReference.KEY + "." + ObjectIdentifier.ID;
        String masterIda2a2 = WTPartMaster.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute partMasterRefIdAttr = new ClassAttribute(WTPart.class, partMasterRefId);
        ClassAttribute masterIdAttr = new ClassAttribute(WTPartMaster.class, masterIda2a2);
        sc = new SearchCondition(partMasterRefIdAttr, SearchCondition.EQUAL, masterIdAttr);
        qs.appendWhere(sc, new int[]{classIndex, masterIndex});
        qs.appendAnd();

        //视图匹配
        //视图和部件引用id一样
        String viewRefId = WTPart.VIEW + "." + ViewReference.KEY + "." + ObjectIdentifier.ID;
        String viewIda2a2 = WTPartMaster.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute viewRefIdAttr = new ClassAttribute(WTPart.class, viewRefId);
        ClassAttribute viewIdAttr = new ClassAttribute(View.class, viewIda2a2);
        sc = new SearchCondition(viewRefIdAttr, SearchCondition.EQUAL, viewIdAttr);
        qs.appendWhere(sc, new int[]{classIndex, viewIndex});
        qs.appendAnd();
        sc = new SearchCondition(View.class, View.NAME, SearchCondition.EQUAL, viewname.trim());
        qs.appendWhere(sc, new int[]{viewIndex});

        //最新小版本
        LatestConfigSpec configSpec = new LatestConfigSpec();
        qs = configSpec.appendSearchCriteria(qs);
        qs.appendAnd();
        ;
        QuerySpec subQuery = queryLatestPartSubQuery();
        SubSelectExpression subSelect = new SubSelectExpression(subQuery);

        String versionId = WTPart.VERSION_INFO + "." + VersionInfo.IDENTIFIER + "." + VersionIdentifier.VERSIONID;
        ClassAttribute versionAttr = new ClassAttribute(WTPart.class, versionId);
        sc = new SearchCondition(versionAttr, SearchCondition.IN, subSelect);
        qs.appendWhere(sc, new int[]{classIndex, masterIndex, viewIndex});

        System.out.println("qs :" + qs);
        QueryResult qr = PersistenceServerHelper.manager.query(qs);
        return qr;
    }

    private static QueryResult queryDocBystate() throws WTException, WTPropertyVetoException {
        String state = State.INWORK.toString();
        QuerySpec qs = new QuerySpec();
        qs.setAdvancedQueryEnabled(true);
        int classIndex = qs.appendClassList(WTDocument.class, true);

        SearchCondition sc = new SearchCondition(WTDocument.class, WTDocument.LIFE_CYCLE_STATE, SearchCondition.EQUAL, state);
        qs.appendWhere(sc, new int[]{classIndex});
        qs.appendAnd();

        QuerySpec subQs = queryLatestDocSubQuery();
        SubSelectExpression subSelect = new SubSelectExpression(subQs);
        String docIda2a2 = WTDocument.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute docIdAttr = new ClassAttribute(WTDocument.class, docIda2a2);
        sc = new SearchCondition(docIdAttr, SearchCondition.IN, subSelect);
        qs.appendWhere(sc, new int[]{classIndex});
        System.out.println("qs :" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        return qr;
    }

    /**
     * 按master 的ida2a2分组，然后取其中的ida2a2最大的一条记录
     *
     * @return
     * @throws
     */
    private static QuerySpec queryLatestDocSubQuery() throws QueryException {
        QuerySpec qs = new QuerySpec();
        qs.setAdvancedQueryEnabled(true);
        int classIndex = qs.appendClassList(WTDocument.class, false);
        String docIda2a2 = WTDocument.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute docIdAttr = new ClassAttribute(WTDocument.class, docIda2a2);
        SQLFunction maxId = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, docIdAttr);
        qs.appendSelect(maxId, new int[]{classIndex}, false);

        String docMasterRef = WTDocument.MASTER_REFERENCE + "." + ObjectReference.KEY + "." + ObjectIdentifier.ID;
        ClassAttribute masterRefAttr = new ClassAttribute(WTDocument.class, docMasterRef);
        qs.appendGroupBy(masterRefAttr, new int[]{classIndex}, false);
        return qs;
    }

    private static QueryResult queryDateByCreateTime() throws WTException, ParseException {
        String createTime = "2019/12/01 00:00:00";
        String endTime = "2019/12/10 00:00:00";
        Date startDate = WTStandardDateFormat.parse(createTime, WTStandardDateFormat.LONG_STANDARD_DATE_FORMAT_MINUS_TIMEZONE);
        Timestamp startStamp = new Timestamp(startDate.getTime());
        Date endDate = WTStandardDateFormat.parse(endTime, WTStandardDateFormat.LONG_STANDARD_DATE_FORMAT_MINUS_TIMEZONE);
        Timestamp endStamp = new Timestamp(endDate.getTime());
        AttributeRange timeRange = new AttributeRange(startStamp, endStamp);

        QuerySpec qs = new QuerySpec(WTDocument.class);
        qs.setAdvancedQueryEnabled(true);
        int classIndex = qs.appendClassList(WTDocument.class, true);
        SearchCondition sc = new SearchCondition(WTDocument.class, WTDocument.CREATE_TIMESTAMP, true, timeRange);
        qs.appendWhere(sc, new int[]{classIndex});
        qs.appendAnd();

        String docIda2a2 = WTDocument.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute docIdAttr = new ClassAttribute(WTDocument.class, docIda2a2);
        QuerySpec subQs = queryLatestDocSubQuery();
        SubSelectExpression subSelect = new SubSelectExpression(subQs);
        sc = new SearchCondition(docIdAttr, SearchCondition.IN, subSelect);
        qs.appendWhere(sc, new int[]{classIndex});

        QueryResult qr = PersistenceHelper.manager.find(qs);
        return qr;
    }

    private static QueryResult queryPartDescribeLinks() throws WTException, ParseException {
        String partName = "CCCCC2222";
        String partNum = "0000000021";
        String docName = "aaaaa222233444";
        String docNum = "0000000021";
        QuerySpec qs = new QuerySpec();
        qs.setAdvancedQueryEnabled(true);

        int partIndex = qs.appendClassList(WTPart.class, true);
        int partMasterIndex = qs.appendClassList(WTPartMaster.class, false);
        int docIndex = qs.appendClassList(WTDocument.class, true);
        int docMasterIndex = qs.appendClassList(WTDocumentMaster.class, false);
        int describeLinkIndex = qs.appendClassList(WTPartDescribeLink.class, true);
        //part ida2a2
        String partIda2a2 = WTPart.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute partIdAttr = new ClassAttribute(WTPart.class, partIda2a2);

        //part master ref idA3masterRef
        String partMasterRefId = WTPart.MASTER_REFERENCE + "." + ObjectReference.KEY + "." + ObjectIdentifier.ID;
        ClassAttribute partMasterRefIdAttr = new ClassAttribute(WTPart.class, partMasterRefId);

        //partMaster ida2a2
        String partMasterIda2a2 = WTPartMaster.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute partMasterIdAttr = new ClassAttribute(WTPartMaster.class, partMasterIda2a2);

        //doc ida2a2
        String docIda2a2 = WTDocument.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute docIdAttr = new ClassAttribute(WTDocument.class, docIda2a2);

        //doc master ref idA3masterRef
        String docMasterRefId = WTDocument.MASTER_REFERENCE + "." + ObjectReference.KEY + "." + ObjectIdentifier.ID;
        ClassAttribute docMasterRefIdAttr = new ClassAttribute(WTDocument.class, docMasterRefId);

        //docMaster ida2a2
        String docMasterIda2a2 = WTDocumentMaster.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute docMasterIdAttr = new ClassAttribute(WTDocumentMaster.class, docMasterIda2a2);

        SearchCondition sc = null;
        sc = new SearchCondition(partMasterRefIdAttr, SearchCondition.EQUAL, partMasterIdAttr);
        qs.appendWhere(sc, new int[]{partIndex, partMasterIndex});
        qs.appendAnd();

        sc = new SearchCondition(docMasterRefIdAttr, SearchCondition.EQUAL, docMasterIdAttr);
        qs.appendWhere(sc, new int[]{docIndex, docMasterIndex});
        qs.appendAnd();

        String descRoleAIda2a2 = WTPartDescribeLink.ROLE_AOBJECT_REF + "." + ObjectReference.KEY + "." + ObjectIdentifier.ID;
        ClassAttribute descRoleAAttr = new ClassAttribute(WTPartDescribeLink.class, descRoleAIda2a2);
        sc = new SearchCondition(descRoleAAttr, SearchCondition.EQUAL, partIdAttr);
        qs.appendWhere(sc, new int[]{describeLinkIndex, partIndex});
        qs.appendAnd();

        String descRoleBIda2a2 = WTPartDescribeLink.ROLE_BOBJECT_REF + "." + ObjectReference.KEY + "." + ObjectIdentifier.ID;
        ClassAttribute descRoleBAttr = new ClassAttribute(WTPartDescribeLink.class, descRoleBIda2a2);
        sc = new SearchCondition(descRoleBAttr, SearchCondition.EQUAL, docIdAttr);
        qs.appendWhere(sc, new int[]{describeLinkIndex, docIndex});

        if (partNum != null) {
            qs.appendAnd();
            sc = buildSearchCondition(WTPartMaster.class, WTPartMaster.NUMBER, partNum);
            qs.appendWhere(sc, new int[]{partMasterIndex});
        }
        if (partName != null) {
            qs.appendAnd();
            sc = buildSearchCondition(WTPartMaster.class, WTPartMaster.NAME, partName);
            qs.appendWhere(sc, new int[]{partMasterIndex});
        }
        if (docNum != null) {
            qs.appendAnd();
            sc = buildSearchCondition(WTDocumentMaster.class, WTDocumentMaster.NUMBER, docNum);
            qs.appendWhere(sc, new int[]{docMasterIndex});
        }
        if (docName != null) {
            qs.appendAnd();
            sc = buildSearchCondition(WTDocumentMaster.class, WTDocumentMaster.NAME, docName);
            qs.appendWhere(sc, new int[]{docMasterIndex});
        }

        System.out.println("qs:" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        return qr;
    }

    private static SearchCondition buildSearchCondition(Class<?> clazz, String field, String likeValue) throws QueryException, PersistenceException {
        ClassAttribute classAttribute = new ClassAttribute(clazz, field);
        SQLFunction attrUpper = SQLFunction.newSQLFunction(SQLFunction.UPPER, classAttribute);
        ColumnExpression attrExpression = ConstantExpression.newExpression("%" + likeValue.trim().toUpperCase() + "%"
                , classAttribute.getColumnDescriptor().getJavaType());
        SearchCondition sc = new SearchCondition(attrUpper, SearchCondition.LIKE, attrExpression);
        return sc;
    }

    private static QueryResult queryPartRefLinks() throws WTException, ParseException {
        String partName = "ddddddd";
        String partNum = "0000000022";
        String docName = "rule";
        String docNum = "0000000041";
        QuerySpec qs = new QuerySpec();
        qs.setAdvancedQueryEnabled(true);

        int partIndex = qs.appendClassList(WTPart.class, true);
        int partMasterIndex = qs.appendClassList(WTPartMaster.class, false);
        int docIndex = qs.appendClassList(WTDocument.class, true);
        int docMasterIndex = qs.appendClassList(WTDocumentMaster.class, false);
        int refLinkIndex = qs.appendClassList(WTPartReferenceLink.class, true);
        //part ida2a2
        String partIda2a2 = WTPart.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute partIdAttr = new ClassAttribute(WTPart.class, partIda2a2);

        //part master ref idA3masterRef
        String partMasterRefId = WTPart.MASTER_REFERENCE + "." + ObjectReference.KEY + "." + ObjectIdentifier.ID;
        ClassAttribute partMasterRefIdAttr = new ClassAttribute(WTPart.class, partMasterRefId);

        //partMaster ida2a2
        String partMasterIda2a2 = WTPartMaster.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute partMasterIdAttr = new ClassAttribute(WTPartMaster.class, partMasterIda2a2);

        //doc ida2a2
        String docIda2a2 = WTDocument.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute docIdAttr = new ClassAttribute(WTDocument.class, docIda2a2);

        //doc master ref idA3masterRef
        String docMasterRefId = WTDocument.MASTER_REFERENCE + "." + ObjectReference.KEY + "." + ObjectIdentifier.ID;
        ClassAttribute docMasterRefIdAttr = new ClassAttribute(WTDocument.class, docMasterRefId);

        //docMaster ida2a2
        String docMasterIda2a2 = WTDocumentMaster.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute docMasterIdAttr = new ClassAttribute(WTDocumentMaster.class, docMasterIda2a2);

        SearchCondition sc = null;
        sc = new SearchCondition(partMasterRefIdAttr, SearchCondition.EQUAL, partMasterIdAttr);
        qs.appendWhere(sc, new int[]{partIndex, partMasterIndex});
        qs.appendAnd();

        sc = new SearchCondition(docMasterRefIdAttr, SearchCondition.EQUAL, docMasterIdAttr);
        qs.appendWhere(sc, new int[]{docIndex, docMasterIndex});
        qs.appendAnd();

        String refRoleAIda2a2 = WTPartReferenceLink.ROLE_AOBJECT_REF + "." + ObjectReference.KEY + "." + ObjectIdentifier.ID;
        ClassAttribute refRoleAAttr = new ClassAttribute(WTPartReferenceLink.class, refRoleAIda2a2);
        sc = new SearchCondition(refRoleAAttr, SearchCondition.EQUAL, partIdAttr);
        qs.appendWhere(sc, new int[]{refLinkIndex, partIndex});
        qs.appendAnd();

        String refRoleBIda2a2 = WTPartReferenceLink.ROLE_BOBJECT_REF + "." + ObjectReference.KEY + "." + ObjectIdentifier.ID;
        ClassAttribute refRoleBAttr = new ClassAttribute(WTPartReferenceLink.class, refRoleBIda2a2);
        sc = new SearchCondition(refRoleBAttr, SearchCondition.EQUAL, docMasterIdAttr);
        qs.appendWhere(sc, new int[]{refLinkIndex, docMasterIndex});

        if (partNum != null) {
            qs.appendAnd();
            sc = buildSearchCondition(WTPartMaster.class, WTPartMaster.NUMBER, partNum);
            qs.appendWhere(sc, new int[]{partMasterIndex});
        }
        if (partName != null) {
            qs.appendAnd();
            sc = buildSearchCondition(WTPartMaster.class, WTPartMaster.NAME, partName);
            qs.appendWhere(sc, new int[]{partMasterIndex});
        }
        if (docNum != null) {
            qs.appendAnd();
            sc = buildSearchCondition(WTDocumentMaster.class, WTDocumentMaster.NUMBER, docNum);
            qs.appendWhere(sc, new int[]{docMasterIndex});
        }
        if (docName != null) {
            qs.appendAnd();
            sc = buildSearchCondition(WTDocumentMaster.class, WTDocumentMaster.NAME, docName);
            qs.appendWhere(sc, new int[]{docMasterIndex});
        }

        System.out.println("qs:" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        return qr;
    }

    private static QueryResult queryPartsByIBA() throws WTException, ParseException, RemoteException {
        String ibaName = "testIBA";
        String ibaValue = "testIBA";
        QuerySpec qs = new QuerySpec(WTPart.class);
        qs.setAdvancedQueryEnabled(true);
        //查询每个大版本的最新小版本
        SearchCondition sc = new SearchCondition(WTPart.class, WTPart.LATEST_ITERATION
                , SearchCondition.IS_TRUE);
        qs.appendWhere(sc, new int[]{0});

        //iba查询
        AttributeDefDefaultView addv = IBADefinitionHelper.service.getAttributeDefDefaultViewByPath(ibaName);
        if (addv != null) {
            //iba 属性定义的id
            long ibaDefId = addv.getObjectID().getId();
            logger.debug("ibaName=" + ibaName + ",ibaDefId =" + ibaDefId);
            QuerySpec subQs = new QuerySpec();
            int index = subQs.appendClassList(StringValue.class, false);

            //查询ida3a4列
            String ibaIda3a4 = StringValue.IBAHOLDER_REFERENCE + "." + ObjectReference.KEY +
                    "." + ObjectIdentifier.ID;
            ClassAttribute ca = new ClassAttribute(StringValue.class, ibaIda3a4);
            subQs.appendSelect(ca, new int[]{index}, false);

            //查询ida3a6列
            String ibaIda3a6 = StringValue.DEFINITION_REFERENCE + "." + ObjectReference.KEY +
                    "." + ObjectIdentifier.ID;
            sc = new SearchCondition(StringValue.class, ibaIda3a6, SearchCondition.EQUAL, ibaDefId);
            subQs.appendWhere(sc, new int[]{index});
            subQs.appendAnd();

            sc = new SearchCondition(StringValue.class, StringValue.VALUE2, SearchCondition.LIKE, ibaValue);
            subQs.appendWhere(sc, new int[]{index});

            logger.debug("子查询：" + subQs);
            SubSelectExpression subSelect = new SubSelectExpression(subQs);

            qs.appendAnd();
            String partIda2a2 = WTPart.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
            ClassAttribute ida2a2 = new ClassAttribute(WTPart.class, partIda2a2);
            qs.appendWhere(new SearchCondition(ida2a2, SearchCondition.IN, subSelect), new int[]{0});
        }
        System.out.println("qs:" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        System.out.println("qr , 过滤前" + qr);
        qr = new LatestConfigSpec().process(qr);
        System.out.println("qr , 过滤后" + qr);
        return qr;
    }

    private static QueryResult queryPartsBySoftType() throws WTException, ParseException, RemoteException {
        String softType = "www.pisx.softPart";
        QuerySpec qs = new QuerySpec(WTPart.class);
        qs.setAdvancedQueryEnabled(true);
        SearchCondition sc = new SearchCondition(WTPart.class, WTPart.LATEST_ITERATION
                , SearchCondition.IS_TRUE);
        qs.appendWhere(sc, new int[]{0});

        qs.appendAnd();
        TypeDefinitionReference typeDefinitionReference = TypedUtility.getTypeDefinitionReference(softType);
        sc = new SearchCondition(WTPart.class, "typeDefinitionReference.key.branchId"
                , SearchCondition.EQUAL, typeDefinitionReference.getKey().getBranchId());
        qs.appendWhere(sc, new int[]{0});
        logger.debug(">>>>qs by softType sql=" + qs);

        System.out.println("qs:" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);

        qr = new LatestConfigSpec().process(qr);

        return qr;
    }

    private static QueryResult queryPartsByStandard() throws WTException, ParseException, RemoteException {
        String softType = "www.pisx.softPart";
        String standName = "localAttr";
        String standValue = "aaa";
        QuerySpec qs = new QuerySpec(WTPart.class);
        qs.setAdvancedQueryEnabled(true);
        SearchCondition sc = new SearchCondition(WTPart.class, WTPart.LATEST_ITERATION
                , SearchCondition.IS_TRUE);
        qs.appendWhere(sc, new int[]{0});

        qs.appendAnd();
        String columnName = getMBABaseQueryName(softType, standName);
        sc = new SearchCondition(WTPart.class, columnName
                , SearchCondition.LIKE, standValue);
        qs.appendWhere(sc, new int[]{0});
        logger.debug(">>>>qs by standard sql=" + qs);

        System.out.println("qs:" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        logger.debug("size=" + qr.size());
        qr = new LatestConfigSpec().process(qr);
        return qr;
    }

    private static String getMBABaseQueryName(String softType, String standName) throws WTException {
        TypeIdentifier typeIden = TypeIdentifierHelper.getTypeIdentifier(softType);
        TypeDefinitionReadView tdrv = TypeDefinitionServiceHelper.service.getTypeDefView(typeIden);
        AttributeDefinitionReadView modelView = tdrv.getAttributeByName(standName);
        String columnName = "";
        if (modelView != null) {
            columnName = modelView.getColumnAllocations().get("value");
        }
        if (logger.isDebugEnabled()) {
            logger.debug(">>>>softType:" + softType + ",standName:"
                    + standName + ",columnName" + columnName);
        }
        return columnName;
    }

    private static QueryResult queryDocLastestAndOrderBy() throws WTException, ParseException, RemoteException {
        QuerySpec qs = new QuerySpec(WTDocument.class);
        qs.setAdvancedQueryEnabled(true);

        int classIndex = qs.addClassList(WTDocument.class, true);

        QuerySpec subQs = queryLatestDocSubQuery();
        SubSelectExpression subSelect = new SubSelectExpression(subQs);
        String docIda2a2 = WTDocument.PERSIST_INFO + "." + PersistInfo.OBJECT_IDENTIFIER + "." + ObjectIdentifier.ID;
        ClassAttribute docIda2a2Attr = new ClassAttribute(WTDocument.class, docIda2a2);
        SearchCondition sc = new SearchCondition(docIda2a2Attr, SearchCondition.IN, subSelect);
        qs.appendWhere(sc, new int[]{classIndex});

        ClassAttribute numAttr = new ClassAttribute(WTDocument.class, WTDocument.NUMBER);
        SQLFunction numbAttrUpper = SQLFunction.newSQLFunction(SQLFunction.UPPER, numAttr);
        OrderBy numberOrderBy = new OrderBy(numbAttrUpper, false);
        qs.appendOrderBy(numberOrderBy, new int[]{classIndex});

        logger.debug(">>>>qs by standard sql=" + qs);
        System.out.println("qs:" + qs);
        QueryResult qr = PersistenceHelper.manager.find(qs);
        logger.debug("size=" + qr.size());
        qr = new LatestConfigSpec().process(qr);
        return qr;
    }

}






