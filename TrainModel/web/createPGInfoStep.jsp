<%@page import="wt.httpgw.URLFactory" %>
<%@page pageEncoding="UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>
<%@taglib uri="http://www.ptc.com/windchill/taglib/jcaMvc" prefix="jcaMvc" %>
<%@ taglib prefix="wctags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="wt.session.SessionHelper,java.util.*,ext.example.pg.util.PGPageUtil" %>
<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="ext.example.pg.resource.pgInfoRB"/>
<fmt:message var="EMPLOYEE_NO" key="EMPLOYEE_NO"/>
<fmt:message var="EMPLOYEE_NAME" key="EMPLOYEE_NAME"/>
<fmt:message var="EMPLOYEE_USERNAME" key="EMPLOYEE_USERNAME"/>
<fmt:message var="EMPLOYEE_EMAIL" key="EMPLOYEE_EMAIL"/>
<fmt:message var="EMPLOYEE_PHONE" key="EMPLOYEE_PHONE"/>
<fmt:message var="COMMENTS" key="COMMENTS"/>
<fmt:message var="EXPERIENCED" key="EXPERIENCED"/>
<fmt:message var="RESUMEINFO" key="RESUMEINFO"/>
<fmt:message var="INFORMATION_SOURCE" key="INFORMATION_SOURCE"/>
<fmt:message var="LEADER" key="LEADER"/>


<%
  Map<String, Object> infoMap = PGPageUtil.initPGInfoMap(commandBean);
  List<String> internalKeys = (List<String>) infoMap.get(PGPageUtil.INFOSOURCE_KEY);
  List<String> displayValues = (List<String>) infoMap.get(PGPageUtil.INFOSOURCE_DISPLAY);
  List<String> selectedValues = (List<String>) infoMap.get(PGPageUtil.INFOSOURCE_VALUE);

%>
<c:set var="internalKeys" scope="page" value="<%=internalKeys%>"/>
<c:set var="displayValues" scope="page" value="<%=displayValues%>"/>
<c:set var="selectedValues" scope="page" value="<%=selectedValues%>"/>

<div style="width: auto; height: auto;">
  <table border="0" class="attributePanel-group-panel" id="tab">

	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;*${EMPLOYEE_NO}:</td>
	  <td>
		<w:textBox propertyLabel="" id="employeeNo" name="employeeNo" size="30" styleClass="required" required="true"
				   maxlength="200"/>
	  </td>
	</tr>

	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;*${EMPLOYEE_NAME}:</td>
	  <td>
		<w:textBox propertyLabel="" id="employeeName" name="employeeName" size="30" styleClass="required"
				   required="true" maxlength="200"/>
	  </td>
	</tr>

	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;*${EMPLOYEE_USERNAME}:</td>
	  <td>
		<w:textBox propertyLabel="" id="employeeUserName" name="employeeUserName" size="30" styleClass="required"
				   required="true" maxlength="200"/>
	  </td>
	</tr>

	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;*${EMPLOYEE_EMAIL}:</td>
	  <td>
		<w:textBox propertyLabel="" id="employeeEmail" name="employeeEmail" size="30" styleClass="required"
				   required="true" maxlength="200"/>
	  </td>
	</tr>

	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;&nbsp;${EMPLOYEE_PHONE}:</td>
	  <td>
		<w:textBox propertyLabel="" id="employeePhone" name="employeePhone" size="30" maxlength="200"/>
	  </td>
	</tr>

	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;&nbsp;${COMMENTS}:</td>
	  <td>
		<w:textArea propertyLabel="" id="comments" name="comments" rows="5" cols="50" maxLength="500"/>
	  </td>
	</tr>


	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;${EXPERIENCED}:</td>
	  <td>
		<w:checkBox id="isExperienced" name="isExperienced" checked="true"/>
	  </td>
	</tr>

	<tr>
	  <td>
		<wctags:genericPicker id="resumeInfo" label="${RESUMEINFO}" multiSelect="false" inline="false"
							  displayAttribute="number" objectType="wt.doc.WTDocument"
							  componentId="RelatedObjectAddAssociation"
							  customAccessController="com.ptc.windchill.enterprise.search.server.LatestVersionAccessController"
							  baseWhereClause="(state.state='RELEASED')|(state.state='INWORK')"/>
	  </td>
	</tr>


	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;*${INFORMATION_SOURCE}:</td>
	  <td>
		<w:comboBox propertyLabel="" id="informationSource" name="informationSource" onselect="lauch()"
					internalValues="${internalKeys}" displayValues="${displayValues}" selectedValues="${selectedValues}"
					required="true"/>
	  </td>
	</tr>

	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;&nbsp;${LEADER}:</td>
	  <td>
		<w:checkBox id="isLeader" name="isLeader" checked="false"/>
	  </td>
	</tr>

  </table>
</div>

<script>
    function validData() {
        //alert(document.getElementById("resumeInfo").value);
        var employeeEmail = document.getElementById("employeeEmail").value;
        var employeePhone = document.getElementById("employeePhone").value;
        if (employeePhone != "") {
            if (!(/^1(3|4|5|6|7|8|9)\d{9}$/.test(employeePhone))) {
                alert("手机号填写的格式不对，请正确填写");
                return false;
            }
        }
        if (employeeEmail != "") {
            if (!(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(employeeEmail))) {
                alert("邮箱填写的格式不对，请正确填写");
                return false;
            }
        }

    }


    setUserSubmitFunction(validData);

</script>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>