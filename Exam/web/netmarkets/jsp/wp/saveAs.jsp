<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components" %>

<%@ page import="com.ptc.netmarkets.model.NmOid" %>
<%@ page import="com.ptc.core.components.util.AttributeHelper" %>

<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>

<script language="JavaScript">
    PTC.navigation.loadScript('netmarkets/javascript/wp/workpackage.js');
</script>

<%
  // Get correct oid to retrieve source object type
  NmOid oid = null;
  // If initialOid set, use that as it is saved from initial page load prior to any context change
  String oidStr = commandBean.convertRequestParam(request.getParameter("initialOid"));
  if (oidStr == null || oidStr.length() == 0) {
	oidStr = commandBean.convertRequestParam(request.getParameter("oid"));
  }
  if (oidStr != null && oidStr.length() > 0) {
	oid = NmOid.newNmOid(oidStr);
  }
  // Get type of source object which is needed for OIR lookup
  String baseTypeName;
  if (oid != null) {
	baseTypeName = AttributeHelper.getTI(oid.getRef(), false).getTypename();
  } else {
	// Default just in case; path not expected
	baseTypeName = "com.ptc.windchill.wp.AbstractWorkPackage";
  }
%>

<jca:initializeItem operation="${createBean.create}" baseTypeName="<%= baseTypeName %>"/>

<jca:wizard helpSelectorKey="PackageSaveAsNew">
  <jca:wizardStep action="saveAs_step1" type="wp"/>
</jca:wizard>
<script type="text/javascript">
    setUserSubmitFunction(function () {
        return wpValidateFormSubmission('saveAs');
    });
</script>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>
