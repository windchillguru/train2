<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>

<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="com.ptc.core.ui.actionsRB"/>
<fmt:message var="LOGIN_SUCCESS_TITLE" key="LOGIN_SUCCESS_TITLE"/>

<jca:wizard buttonList="WizardButtonClose" title="${LOGIN_SUCCESS_TITLE}">
  <jca:wizardStep action="reloadFBA" type="object"/>
</jca:wizard>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>