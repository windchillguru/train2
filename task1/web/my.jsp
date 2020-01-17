<%@page pageEncoding="UTF-8" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="wt.util.WTContext,java.util.*" %>
<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="ext.example.createfolder.resource.createFolderRB"/>
<fmt:message var="SEARCH_NAME" key="SEARCH_NAME"/>
<fmt:message var="SEARCH_NUMBER" key="SEARCH_NUMBER"/>
<fmt:message var="SEARCH_BEGINTIME" key="SEARCH_BEGINTIME"/>
<fmt:message var="SEARCH_ENDTIME" key="SEARCH_ENDTIME"/>
<fmt:message var="SEARCH_BUTTON" key="SEARCH_BUTTON"/>
<fmt:message var="SEARCH_RESET_BUTTON" key="SEARCH_RESET_BUTTON"/>

<%

  //获取当前时间
  Locale locale = WTContext.getContext().getLocale();
  Calendar calendar = Calendar.getInstance(WTContext.getContext().getTimeZone(), locale);
  java.util.Date curTime = calendar.getTime();

  calendar.set(Calendar.DAY_OF_MONTH, 1);
  java.util.Date beginOfMonth = calendar.getTime();

%>

<c:set var="startTime" value="<%=beginOfMonth %>"/>
<c:set var="curTime" value="<%=curTime %>"/>

<script type="text/javascript" language="javascript">

    //页面初始时
    Ext.onReady(function () {
    });

    var tableId = "my.part.list.configurable.table";

    //搜索展示builder
    function refreshTableByParamChange() {
        var searchName = document.getElementById("searchName").value;
        var searchNumber = document.getElementById("searchNumber").value;
        var searchBeginTime = document.getElementById("searchBeginTime").value;
        var searchEndTime = document.getElementById("searchEndTime").value;

        var params = {
            searchName: searchName,
            searchNumber: searchNumber,
            searchBeginTime: searchBeginTime,
            searchEndTime: searchEndTime
        };

        PTC.jca.table.Utils.reload(tableId, params, true);

    }

    function resetParams() {
        document.getElementById("searchName").value = "";
        document.getElementById("searchNumber").value = "";
        document.getElementById("searchBeginTime").value = "2019/05/01";
        document.getElementById("searchEndTime").value = "2019/05/16";

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
	  <td class="attributePanel-label">&nbsp;&nbsp;${SEARCH_BEGINTIME}:</td>
	  <td>
		<w:dateInputComponent propertyLabel="" id="searchBeginTime" name="searchBeginTime" dateValueType="DATE_ONLY"
							  dateValidation="true" dateValue="${startTime}"/>
	  </td>
	</tr>

	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;${SEARCH_ENDTIME}:</td>
	  <td>
		<w:dateInputComponent propertyLabel="" id="searchEndTime" name="searchEndTime" dateValueType="DATE_ONLY"
							  dateValidation="true" dateValue="${curTime}"/>
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
  <jsp:include page="${mvc:getComponentURL('my.part.list.configurable.table')}" flush="true"/>
</div>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>
