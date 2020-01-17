<%@ page errorPage="/netmarkets/jsp/util/error.jsp" %>
<%@ page import="com.ptc.netmarkets.util.beans.NmCommandBean" %>
<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components" %>
<%@ taglib prefix="attachments" uri="http://www.ptc.com/windchill/taglib/attachments" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>

<jca:initializeItem operation="${createBean.create}" baseTypeName="com.ptc.windchill.rd.Inbox"/>

<jca:wizard helpSelectorKey="ReceivedDeliveryFileUpload">
  <jca:wizardStep action="uploadDeliveryFilesTable" type="rd"/>
</jca:wizard>

<!-- initialize applet for the attachments step -->
<attachments:fileSelectionAndUploadApplet/>

<%@include file="/netmarkets/jsp/util/end.jspf" %>
