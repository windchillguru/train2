<%-- This bean controls the behavior of Netmarkets widgets --%><%
  //
%><%-- This bean controls the behavior of Netmarkets widgets --%><%
  //
%><%-- WTContextBean sets up environment for communication with method server --%><%
  //
%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="request"/>
<%
  //
  wtcontext.setRequest(request);

%>
<%@ page import="com.ptc.netmarkets.util.beans.NmContextBean" %>
<%
  //
%>
<jsp:useBean id="nmcontext" class="com.ptc.netmarkets.util.beans.NmContextBean" scope="request"/>
<%
  //
  request.setCharacterEncoding("UTF-8");
  if (request.getParameter("portlet") != null)
	nmcontext.setPortlet(request.getParameter("portlet"));
  nmcontext.setRequest(request);
  nmcontext.setResponse(response);
  nmcontext.adjustContext(request);
%>
<jsp:useBean id="localeBean" class="com.ptc.netmarkets.util.beans.NmLocaleBean" scope="request"/>