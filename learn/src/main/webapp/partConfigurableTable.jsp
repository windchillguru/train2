<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/jcaMvc" prefix="jcaMvc"%>
<%@ taglib prefix="wctags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc"%>
<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jca:describeTable var="tableDescriptor" id="myTable" label="Part List Table"  configurable="true"
                   configurableTableId="ext.example.part.mvc.builder.MyPartListConfigurableTableView">
    <jca:setComponentProperty key="actionModel" value="my_part_list_toolbar"/>
    <jca:setComponentProperty key="selectable" value="true"/>
    <jca:describeColumn id="type_icon" sortable="true"/>
    <jca:describeColumn id="name"/>
    <jca:describeColumn id="number" sortable="true"/>
    <jca:describeColumn id="version"/>
    <jca:describeColumn id="description"/>
    <jca:describeColumn id="infoPageAction"/>
    <jca:describeColumn id="lifecycle" need="state.state" sortable="true"/>
</jca:describeTable>

<%--获取数据
serviceName 类名
 methodName 调用方法名--%>
<jca:getModel var="tableModel" descriptor="${tableDescriptor}"
              serviceName="ext.example.part.command.MyPartCommand"
                methodName="getData">
    <jca:addServiceArgument value="${commandBean}" type="com.ptc.netmarkets.util.beans.NmCommandBean"/>
</jca:getModel>
<jca:renderTable model="${tableModel}"/>

<%@ include file="/netmarkets/jsp/util/end.jspf"%>
