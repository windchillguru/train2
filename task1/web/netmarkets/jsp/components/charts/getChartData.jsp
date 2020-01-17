<%@page import="com.ptc.core.components.chart.ChartGenerationHelper" %>
<%@page import="java.util.Map" %>
<%@page import="com.ptc.netmarkets.util.beans.NmCommandBean" %>
<%@page import="com.ptc.netmarkets.util.beans.HTTPRequestData" %>
<%@page import="java.util.HashMap" %>
<jsp:useBean id="commandBean" class="com.ptc.netmarkets.util.beans.NmCommandBean" scope="request"/>

<%
  //we get the new command bean so need to set request and request data here
  commandBean.setRequest(request);
  HTTPRequestData requestData = new HTTPRequestData();
  HashMap requestMap = new HashMap();
  requestData.setParameterMap(requestMap);
  commandBean.setRequestData(requestData);

  ChartGenerationHelper helper = new ChartGenerationHelper();
  helper.getChartData(out, commandBean);

%>

