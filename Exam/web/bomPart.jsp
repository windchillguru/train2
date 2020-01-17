<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="wca" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>

<!-- 定义大页面 -->
<wca:wizard title="download BOM" buttonList="DefaultWizardButtonsNoApply">
  <!-- 第一页的页面 -->
  <wca:wizardStep action="BOMPartStep" type="report"/>
</wca:wizard>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>


