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
<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="ext.example.pg.resource.pgInfoRB"/>
<fmt:message var="PGGROUP_NANE" key="PGGROUP_NANE"/>
<fmt:message var="ENABLED" key="ENABLED"/>
<fmt:message var="ROOT" key="ROOT"/>
<fmt:message var="COMMENTS" key="COMMENTS"/>


<div style="width: auto; height: auto;">
  <table border="0" class="attributePanel-group-panel" id="tab">

	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;*${PGGROUP_NANE}:</td>
	  <td>
		<w:textBox propertyLabel="" id="pgGroupName" name="pgGroupName" size="30" styleClass="required" required="true"
				   maxlength="200"/>
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
	  <td class="attributePanel-label">&nbsp;&nbsp;${ROOT}:</td>
	  <td>
		<w:checkBox id="isRoot" name="isRoot" checked="false"/>
	  </td>
	</tr>

	<tr>
	  <td>&nbsp;</td>
	  <td class="attributePanel-label">&nbsp;&nbsp;&nbsp;${ENABLED}:</td>
	  <td>
		<w:checkBox id="isEnabled" name="isEnabled" checked="true"/>
	  </td>
	</tr>

  </table>
</div>

<script>
    function validData() {
        //alert(document.getElementById("resumeInfo").value);
    }

    setUserSubmitFunction(validData);

</script>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>