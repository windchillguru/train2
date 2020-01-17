<%@page import="com.ptc.core.components.chart.ChartSaveSettingsHelper" %>
<%
  String reportTemplateOid = request.getParameter("reportTemplateOid");
  String tableId = request.getParameter("tableId");
  ChartSaveSettingsHelper helper = new ChartSaveSettingsHelper();
  helper.getSavedChartMenuData(out, reportTemplateOid, tableId);
%>