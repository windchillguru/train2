<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="wca" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>

<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="ext.task.doc.resource.docAPIResource"/>

<fmt:message var="describedHeader" key="MODIFY_DOCIDENTITY_TITLE"/>

<!-- 定义修改标识的大页面 -->
<wca:wizard title="${describedHeader }" buttonList="DefaultWizardButtonsNoApply">
  <!-- 第一页的页面 -->
  <wca:wizardStep action="modifyDocIdentityStep" type="taskDoc"/>
</wca:wizard>


<%@ include file="/netmarkets/jsp/util/end.jspf" %>

