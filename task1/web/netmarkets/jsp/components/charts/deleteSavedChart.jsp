<%@page import="com.ptc.core.components.chart.ChartSaveSettingsHelper" %>
<%
  String savedChartOid = request.getParameter("savedChartOid");
  ChartSaveSettingsHelper helper = new ChartSaveSettingsHelper();
  helper.deleteSavedChart(out, savedChartOid);
%>