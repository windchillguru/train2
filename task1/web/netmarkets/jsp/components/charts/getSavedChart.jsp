<%@page import="com.ptc.core.components.chart.ChartSaveSettingsHelper" %>
<jsp:useBean id="commandBean" class="com.ptc.netmarkets.util.beans.NmCommandBean" scope="request"/>

<%
  commandBean.setRequest(request);
  ChartSaveSettingsHelper helper = new ChartSaveSettingsHelper();
  helper.getSavedChart(out, commandBean);
%>
