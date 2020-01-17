<%@page import="com.ptc.core.components.chart.ChartSaveSettingsHelper" %>
<%@page import="com.ptc.netmarkets.util.beans.NmCommandBean" %>
<%@page import="com.ptc.netmarkets.util.beans.HTTPRequestData" %>
<%@page import="java.util.HashMap" %>
<jsp:useBean id="commandBean" class="com.ptc.netmarkets.util.beans.NmCommandBean" scope="request"/>

<%
  commandBean.setRequest(request);
  HTTPRequestData requestData = new HTTPRequestData();
  HashMap requestMap = new HashMap();
  requestData.setParameterMap(requestMap);
  commandBean.setRequestData(requestData);

  ChartSaveSettingsHelper helper = new ChartSaveSettingsHelper();
  helper.saveChartSettings(out, commandBean);
%>

