<%@page pageEncoding="UTF-8" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="wt.util.WTContext,java.util.*" %>
<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="ext.example.pg.resource.pgTreeActionRB"/>
<fmt:message var="PG_NAME" key="PG_NAME"/>
<fmt:message var="SEARCH_BUTTON" key="SEARCH_BUTTON"/>
<fmt:message var="SEARCH_RESET_BUTTON" key="SEARCH_RESET_BUTTON"/>

<script type="text/javascript" language="javascript">

    //页面初始时
    Ext.onReady(function () {
    });

    var tableId = "ext.example.pg.builder.AddMemberTableBuilder";

    //搜索展示builder
    function refreshTableByParamChange() {
        var pgName = document.getElementById("pgName").value;

        var params = {};

        PTC.jca.table.Utils.reload(tableId, params, true);

    }

    function resetParams() {
        document.getElementById("searchName").value = "";
    }
</script>

<div style="width: auto; height: auto;">
  <table border="0" class="attributePanel-group-panel" id="tab">
	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;${PG_NAME}:</td>
	  <td>
		<w:textBox propertyLabel="" id="pgName" name="pgName" size="30" maxlength="200"/>
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
  <jsp:include page="${mvc:getComponentURL('ext.example.pg.builder.AddMemberTableBuilder')}" flush="true"/>
</div>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>
