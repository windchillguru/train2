<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components"
%>
<%@taglib prefix="attachments" uri="http://www.ptc.com/windchill/taglib/attachments"
%>

<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>

<jca:initializeItem operation="${createBean.edit}"/>

<!-- initialize applet for the attachments step -->
<attachments:fileSelectionAndUploadApplet/>

<jca:wizard buttonList="DefaultWizardButtonsNoApply">
  <jca:wizardStep action="attachments_step" type="attachments"/>
</jca:wizard>

<%@include file="/netmarkets/jsp/util/end.jspf" %>
