<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>

<%@ page import="com.ptc.windchill.rd.receivedDeliveryResource" %>

<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>

<jca:initializeItem operation="${createBean.edit}"/>

<jca:wizard helpSelectorKey="ReceivedDeliveryEdit" buttonList="DefaultWizardButtonsNoApply">
  <jca:wizardStep action="setAttributesWizStep" type="rd"/>
</jca:wizard>

<%@include file="/netmarkets/jsp/util/end.jspf" %>
