<%@page pageEncoding="UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="ext.task.part.util.ModifyPartIdentityUtil,java.util.*" %>
<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="ext.task.part.resource.partAPIResource"/>
<fmt:message var="NEW_NUMBER" key="MODIFY_PARTIDENTITY_NUMBER"/>
<fmt:message var="NEW_NAME" key="MODIFY_PARTIDENTITY_NAME"/>
<fmt:message var="MODIFY_PARTIDENTIY_ERR1" key="MODIFY_PARTIDENTITY_ERR1"/>
<%
  Map<String, String> partInfoMap = ModifyPartIdentityUtil.initPartParams(commandBean);
  System.out.println("partInfoMap=" + partInfoMap);
  String oldNumber = partInfoMap.get("amount");
  String oldName = partInfoMap.get("name");
%>

<c:set var="oldNumber" scope="page" value="<%=oldNumber %>"/>
<c:set var="oldName" scope="page" value="<%=oldName %>"/>

<div style="width: auto; height: auto;">
  <table border="0" class="attributePanel-group-panel" id="commonTab">
	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;*${NEW_NUMBER}:</td>
	  <td>
		<w:textBox propertyLabel="" id="newNumber" name="newNumber" size="30" value="${oldNumber}" styleClass="required"
				   required="true" maxlength="200"/>
	  </td>
	</tr>

	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;*${NEW_NAME}:</td>
	  <td>
		<w:textBox propertyLabel="" id="newName" name="newName" size="30" value="${oldName}" styleClass="required"
				   required="true" maxlength="200"/>
	  </td>
	</tr>
  </table>
</div>
<!--w:textBox propertyLabel="" id="oldNumber" name="oldNumber" size="30" value="${oldNumber}" hidden="true" styleClass="hidden" /-->
<!--w:textBox propertyLabel="" id="oldName" name="oldName" size="30" value="${oldName}" hidden="true" styleClass="hidden" /-->
<!--input type="hidden" id="oldNumber" name="oldNumber" value="${oldNumber}"/-->
<!--input type="hidden" id="oldName" name="oldName" value="${oldName}"/-->

<script type="text/javascript" language="javascript">

    //页面初始时
    Ext.onReady(function () {
    });

    function checkNewNumberAndName() {
        var newNumber = document.getElementById("newNumber").value;
        var newName = document.getElementById("newName").value;
        var oldNumber = document.getElementById("newNumber___old").value;
        var oldName = document.getElementById("newName___old").value;

        if ((newNumber == oldNumber) && (newName == oldName)) {
            alert("${MODIFY_PARTIDENTIY_ERR1}");
            return false;

        }

        return true;
    }

</script>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>