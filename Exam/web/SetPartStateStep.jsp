<%@page pageEncoding="UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="ext.exam.part.util.PartStatusUtil ,java.util.*" %>
<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="ext.exam.part.resource.partResource"/>
<fmt:message var="SET_PART_STATUS" key="SET_PART_STATUS"/>
<fmt:message var="SET_PART_STATUS_ERR" key="SET_PART_STATUS_ERR"/>


<%
  //当前生命周期状态
  String state = PartStatusUtil.getState(commandBean);
  Map<String, String> stateMap = PartStatusUtil.getAllState(commandBean);
  List<String> internalKeys = new ArrayList<String>();
  List<String> displayValues = new ArrayList<String>();
  List<String> selectedValues = new ArrayList<String>();

  internalKeys.add("");
  String s = stateMap.get(state);
  //默认值
  displayValues.add(stateMap.get(state));
  selectedValues.add("");
  for (String key : stateMap.keySet()) {
	internalKeys.add(key);
	displayValues.add(stateMap.get(key));
  }
%>

<c:set var="s" scope="page" value="<%=s%>"/>
<c:set var="selectedValues" scope="page" value="<%=selectedValues%>"/>
<c:set var="internalKeys" scope="page" value="<%=internalKeys%>"/>
<c:set var="displayValues" scope="page" value="<%=displayValues%>"/>

<div style="width: auto; height: auto;">
  <table border="0" class="attributePanel-group-panel" id="commonTab">
	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp; ${SET_PART_STATUS}:</td>
	  <td>
		<w:comboBox propertyLabel="" id="status" name="status" onselect="lauch()"
					styleClass="required" required="true" internalValues="${internalKeys}"
					displayValues="${displayValues}" selectedValues="${selectedValues}"/>
	  </td>
	</tr>
  </table>
</div>

<script type="text/javascript" language="javascript">

    //页面初始时
    Ext.onReady(function () {
    });

    function checkNewStatus() {
        var newStatus = document.getElementById("status").value;
        var oldStatus = document.getElementById("status___old").value;
        if (newStatus == oldStatus) {
            alert("${SET_PART_STATUS_ERR}");
            return false;
        }
        return true;
    }
</script>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>