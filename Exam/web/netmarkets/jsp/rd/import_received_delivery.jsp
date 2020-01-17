<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components" %>
<%@ page
	import="com.ptc.netmarkets.model.NmOid,com.ptc.netmarkets.util.beans.NmCommandBean,com.ptc.windchill.rd.ReceivedDelivery,wt.fc.ObjectReference,com.ptc.windchill.rd.service.ReceivedDeliveryHelper,com.ptc.windchill.rd.ReceivedDeliveryImportState" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>

<jca:initializeItem operation="${createBean.edit}"/>

<jca:wizard helpSelectorKey="ReceivedDeliveryImport" buttonList="ImportButtonModel">
  <jca:wizardStep action="setImportAttributesStep" type="rd"/>
</jca:wizard>

<%@include file="/netmarkets/jsp/util/end.jspf" %>
