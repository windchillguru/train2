<%@ include file="/netmarkets/jsp/util/begin.jspf" %>
<%@ page import="com.ptc.jca.json.table.TableConfigHolder" %>
<%@ page import="com.ptc.mvc.components.FindInTableMode" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="attachments" tagdir="/WEB-INF/tags/attachments" %>
<%@ include file="/netmarkets/jsp/attachments/initAttachments.jspf" %>
<%@ include file="/netmarkets/jsp/attachments/initPTCAttachments.jspf" %>

<c:set var="tableId" value="attachments.list.editable"/>

<!-- Get the localized strings from the resource bundle -->
<fmt:setLocale value="${localeBean.locale}"/>

<fmt:setBundle basename="com.ptc.windchill.rd.receivedDeliveryResource"/>
<fmt:message var="lblUploadChunkTableHeader" key="RD_UPLOAD_FILE_TABLE_TITLE"/>

<fmt:setBundle basename="com.ptc.windchill.rd.receivedDeliveryResource"/>
<fmt:message var="lblFileLocation" key="DELIVERY_FILE_LOCATION"/>

<fmt:setBundle basename="com.ptc.windchill.enterprise.attachments.attachmentsResource"/>
<fmt:message var="lblFileName" key="ATTACHMENT_NAME"/>

<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>
<input type="hidden" name="newFiles" id="newFiles" value=""/>
<input type="hidden" name="fileSep" id="fileSep" value="\"/>
<input type="hidden" name="fileAttachmentCount" id="fileAttachmentCount" value="0"/>

<jca:describeTable var="tableDescriptor" id="${tableId}" type="wt.content.ContentItem"
				   label="${lblUploadChunkTableHeader}" mode="CREATE" scope="request">
  <jca:setComponentProperty key="actionModel" value="upload files wizard table toolbar actions"/>
  <jca:setComponentProperty key="variableRowHeight" value="true"/>
  <jca:setComponentProperty key="<%=TableConfigHolder.FIND_IN_TABLE_MODE%>" value="<%=FindInTableMode.DISABLED%>"/>

  <%-- the gridfileinputhandler plugin will disable many grid features, so that using a browser input field in the ext grid will work --%>
  <jca:setTablePlugin ptype="gridfileinputhandler"/>

  <jca:describeColumn id="type_icon" sortable="false"/>
  <jca:describeColumn id="contentName" sortable="false" label="*${lblFileName}">
	<jca:setComponentProperty key="useExact" value="true"/>
  </jca:describeColumn>
  <jca:describeColumn id="contentLocation" sortable="false" label="*${lblFileLocation}">
	<jca:setComponentProperty key="useExact" value="true"/>
  </jca:describeColumn>
</jca:describeTable>

<c:set target="${tableDescriptor.properties}" property="selectable" value="true"/>

<jca:getModel var="tableModel" descriptor="${tableDescriptor}"
			  serviceName="com.ptc.windchill.enterprise.attachments.commands.AttachmentQueryCommands"
			  methodName="getAttachments">
  <jca:addServiceArgument value="${commandBean}" type="com.ptc.netmarkets.util.beans.NmCommandBean"/>
  <jca:addServiceArgument value="<%= wt.content.ContentRoleType.SECONDARY %>"/>
</jca:getModel>

<jca:renderTable model="${tableModel}" scroll="true"/>


<!-- Below script is added for auto resizing of the table column -->
<script Language="JavaScript">
    //This table many be empty or have some rows when its launched. This is based on where its used. Hence it requires both 'add' and 'datachanged' callbacks
    //In some cases, if table is already available, 'onAvailable' is not applicable. And in other case, table will be availabe, and here it requires onAvailable
    var grid = Ext.getCmp('${tableId}');
    if (grid) {
        grid.getStore().on('add', function (store) {
            grid.clearStickyConfig(); // reset values
            PTC.jca.ColumnUtils.resizeAllColumns(grid);
            return true;
        }, null, {single: true, delay: 100}); // only run this function one time

        grid.getStore().on('datachanged', function (store) {
            grid.clearStickyConfig(); // reset values
            PTC.jca.ColumnUtils.resizeAllColumns(grid);
            return true;
        }, null, {single: true, delay: 100}); // only run this function one time
    } else {
        Ext.ComponentMgr.onAvailable('${tableId}', function () {
            var grid = Ext.getCmp('${tableId}');
            if (grid) {
                grid.getStore().on('add', function (store) {
                    grid.clearStickyConfig(); // reset values
                    PTC.jca.ColumnUtils.resizeAllColumns(grid);
                    return true;
                }, null, {single: true, delay: 100}); // only run this function one time

                grid.getStore().on('datachanged', function (store) {
                    grid.clearStickyConfig(); // reset values
                    PTC.jca.ColumnUtils.resizeAllColumns(grid);
                    return true;
                }, null, {single: true, delay: 100}); // only run this function one time
            }
        });
    }
</script>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>
