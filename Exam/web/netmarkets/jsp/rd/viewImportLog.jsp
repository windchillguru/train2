<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>

<jca:initializeItem operation="${createBean.edit}"/>

<jca:wizard helpSelectorKey="ReceivedDeliveryImportLogView" buttonList="DefaultCloseButton">
  <jca:wizardStep action="setViewLogAttributesWizStep" type="rd"/>
</jca:wizard>

<%@include file="/netmarkets/jsp/util/end.jspf" %>
