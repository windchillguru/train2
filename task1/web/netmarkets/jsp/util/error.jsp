<%@ page import="java.io.PrintWriter"
%>
<%@ page import="java.util.Enumeration"
%>
<%@ page import="wt.util.WTException"
%>
<jsp:useBean id="errorBean" class="com.ptc.netmarkets.util.beans.NmErrorBean" scope="request"/>
<%
  //
%><%
  //

  if (request.getAttribute("javax.servlet.error.status_code") == null) {
	// need to have these attributes set to match the spec when the error.jsp is manually included

	request.setAttribute("javax.servlet.error.status_code", response.SC_INTERNAL_SERVER_ERROR);
	Throwable t = errorBean.getThrowable();
	if (t != null) {
	  request.setAttribute("javax.servlet.error.exception_type", t.getClass());
	  request.setAttribute("javax.servlet.error.message", t.getMessage());
	  request.setAttribute("javax.servlet.error.exception", t);
	} else {
	  request.setAttribute("javax.servlet.error.exception_type", Exception.class);
	  request.setAttribute("javax.servlet.error.message", "");
	  Exception e = new WTException("error");
	  request.setAttribute("javax.servlet.error.exception", e);
	}

	request.setAttribute("javax.servlet.error.request_uri", request.getRequestURI());
	request.setAttribute("javax.servlet.error.servlet_name", "none");

  }
%>
<jsp:include page="/netmarkets/jsp/util/error_details.jsp" flush="true"/>
<%

  // Remove these so that the Servlet engine doesn't do its thing
  request.removeAttribute("javax.servlet.error.exception_type");
  request.removeAttribute("javax.servlet.error.message");
  request.removeAttribute("javax.servlet.error.exception");
  request.removeAttribute("javax.servlet.error.request_uri");
  request.removeAttribute("javax.servlet.error.servlet_name");
%>