<%

  request.setAttribute("com.ptc.netmarkets.body.onload", "try { displayMessages();recordLoadTime();init_page();showTimeAfterOnLoad(); } catch (Exception e) {}");

%>

<%@ include file="/netmarkets/jsp/util/begin.jspf" %>

<%@taglib uri="http://www.ptc.com/windchill/taglib/util" prefix="util" %>


<% // These calls initialize the WTContextBean to be used in the page %>

<jsp:setProperty name="wtcontext" property="session" value="true"/>

<jsp:setProperty name="wtcontext" property="request" value="<%= request %>"/>


<util:processaction jspAction="ObjProps"/>


<%@ include file="/netmarkets/jsp/util/end.jspf" %>
