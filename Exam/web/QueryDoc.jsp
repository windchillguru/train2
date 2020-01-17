<%@page pageEncoding="UTF-8" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="wt.util.WTContext,java.util.*" %>
<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="ext.exam.doc.resource.docResource"/>
<fmt:message var="SEARCH_NAME" key="SEARCH_NAME"/>
<fmt:message var="SEARCH_NUMBER" key="SEARCH_NUMBER"/>
<fmt:message var="SEARCH_CUSTOMERNAME" key="SEARCH_CUSTOMERNAME"/>
<fmt:message var="SEARCH_BUTTON" key="SEARCH_BUTTON"/>
<fmt:message var="SEARCH_RESET_BUTTON" key="SEARCH_RESET_BUTTON"/>

<script type="text/javascript" language="javascript">

    //页面初始时
    Ext.onReady(function () {
    });

    var tableId = "ext.exam.doc.builder.SearchDocListTableBuilder";

    //搜索展示builder
    function refreshTableByParamChange() {
        var searchName = document.getElementById("searchName").value;
        var searchNumber = document.getElementById("searchNumber").value;
        var customerName = document.getElementById("customerName").value;


        var params = {
            searchName: searchName,
            searchNumber: searchNumber,
            customerName: customerName,
        };

        PTC.jca.table.Utils.reload(tableId, params, true);

    }

    function resetParams() {
        document.getElementById("searchName").value = "";
        document.getElementById("searchNumber").value = "";
        document.getElementById("customerName").value = "";
    }
</script>

<div style="width: auto; height: auto;">
  <table border="0" class="attributePanel-group-panel" id="commonTab">
	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;${SEARCH_NAME}:</td>
	  <td>
		<w:textBox propertyLabel="" id="searchName" name="searchName" size="30" maxlength="200"/>
	  </td>
	</tr>

	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;${SEARCH_NUMBER}:</td>
	  <td>
		<w:textBox propertyLabel="" id="searchNumber" name="searchNumber" size="30" maxlength="200"/>
	  </td>
	</tr>

	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;${SEARCH_CUSTOMERNAME}:</td>
	  <td>
		<w:textBox propertyLabel="" id="customerName" name="customerName" size="30" maxlength="200"/>
	  </td>
	</tr>

	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label"></td>
	  <td>
		<w:button name="search" value="${SEARCH_BUTTON}" onclick="refreshTableByParamChange();"></w:button>
		<w:button name="reset" value="${SEARCH_RESET_BUTTON}" onclick="resetParams();"></w:button>
	  </td>
	</tr>

  </table>
</div>
<br/>
<div id="builderDiv">
  <jsp:include page="${mvc:getComponentURL('ext.exam.doc.builder.SearchDocListTableBuilder')}" flush="true"/>
</div>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>
