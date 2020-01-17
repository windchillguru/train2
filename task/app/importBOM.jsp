<%@page pageEncoding="UTF-8" %>
<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components" %>

<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>

<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="ext.task2.part.resource.partRB"/>
<fmt:message var="TASK_EXPORTBOM_05" key="TASK_EXPORTBOM_05"/>

<jca:wizard title="${TASK_EXPORTBOM_05}" buttonList="DefaultWizardButtonsNoApply">
  <jca:wizardStep action="importBOMStep" type="task"/>
</jca:wizard>


<%@ include file="/netmarkets/jsp/util/end.jspf" %>