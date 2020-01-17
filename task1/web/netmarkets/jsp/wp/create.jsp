<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/attachments" prefix="attachments" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>
<%@ page import="com.ptc.netmarkets.wp.collection.AddToPackageHelper" %>
<%@ page import="com.ptc.windchill.wp.collection.tags.InitializeAddToPackageDataTag" %>
<%@ include file="/netmarkets/jsp/attachments/initAttachments.jspf" %>

<c:set var="isNewPackageWizardInit" value="true" scope="request"/>
<input type="hidden" name="isNewPackageWizard" value="true" id="isNewPackageWizard"/>

<c:if test='${fn:containsIgnoreCase(param.tableID, "relatedPackage") or param.actionName=="create"}'>
  <c:set var="seedObjOidToAdd" scope="request"
		 value='<%= new InitializeAddToPackageDataTag().addSelectedItemsToSeedList(commandBean, "false", "", request.getParameter("JSTreeTable_objOID")) %>'/>
</c:if>
<input type="hidden" name="seedObjOidToAdd" id="seedObjOidToAdd" value="${seedObjOidToAdd}"/>

<script language="JavaScript">
    PTC.navigation.loadScript('netmarkets/javascript/wp/workpackage.js');
    PTC.navigation.loadScript('netmarkets/javascript/wp/addToPackage.js');
    PTC.WORK_PACKAGE = {};
    PTC.WORK_PACKAGE.hidePreviewButton = function () {
        $("leftButton").style.visibility = "hidden";
    }
    PTC.onReady(PTC.WORK_PACKAGE.hidePreviewButton);
</script>

<jca:initializeItem operation="${createBean.create}" objectHandle="workPackageHandle"
					baseTypeName="com.ptc.windchill.wp.AbstractWorkPackage"/>

<jca:initializeItem operation="${createBean.create}" objectHandle="wpCollectionHandle"
					baseTypeName="com.ptc.windchill.wp.AbstractWorkPackage"/>

<c:choose>
  <c:when test='<%= wt.util.version.WindchillVersion.isAssemblyInstalled("wadm") %>'>
	<jca:wizard helpSelectorKey="PackageCreate" buttonList="AddToPackageWizardButtons"
				formProcessorController="com.ptc.netmarkets.wp.CreatePackageFormProcessorController">
	  <jca:wizardStep action="setContextWizStep" type="object"/>
	  <jca:wizardStep action="defineItemAttributesWizStep" type="wp" objectHandle="workPackageHandle"/>
	  <jca:wizardStep action="setContractAttributesWizStep" type="cdrl" objectHandle="workPackageHandle"/>
	  <jca:wizardStep action="setDataItemAttributesWizStep" type="cdrl" objectHandle="workPackageHandle"/>
	  <jca:wizardStep action="securityLabelStep" type="securityLabels" objectHandle="workPackageHandle"/>
	  <jca:wizardStep action="addToPackageStep" type="wpcollection" objectHandle="wpCollectionHandle"/>
	  <jca:wizardStep action="selectContextStep" type="rep" objectHandle="wpCollectionHandle"/>
	  <jca:wizardStep action="wp_attachments_step" type="wp" objectHandle="workPackageHandle"/>
	</jca:wizard>
  </c:when>
  <c:otherwise>
	<jca:wizard helpSelectorKey="PackageCreate" buttonList="AddToPackageWizardButtons"
				formProcessorController="com.ptc.netmarkets.wp.CreatePackageFormProcessorController">
	  <jca:wizardStep action="setContextWizStep" type="object"/>
	  <jca:wizardStep action="defineItemAttributesWizStep" type="wp" objectHandle="workPackageHandle"/>
	  <jca:wizardStep action="securityLabelStep" type="securityLabels" objectHandle="workPackageHandle"/>
	  <jca:wizardStep action="addToPackageStep" type="wpcollection" objectHandle="wpCollectionHandle"/>
	  <jca:wizardStep action="selectContextStep" type="rep" objectHandle="wpCollectionHandle"/>
	  <jca:wizardStep action="wp_attachments_step" type="wp" objectHandle="workPackageHandle"/>
	</jca:wizard>
  </c:otherwise>
</c:choose>

<attachments:fileSelectionAndUploadApplet/>

<%@include file="/netmarkets/jsp/util/end.jspf" %>
