<%@ page import="java.util.HashMap,
				 com.ptc.mvc.util.MVCUtil,
				 java.util.ResourceBundle,
				 wt.util.HTMLEncoder"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%>
<%
  request.setAttribute(NmContextBean.Attributes.POPUP, "popup");
%>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>
<input type="hidden" name="popupWindow" value="true"/>
<input type="hidden" name="dialogButton" value="<%=NmContextStringTokenizer.NONE%>"/>
<%
  boolean dynWiz = request.getParameter("refreshTable") == null;
  if (dynWiz) {%>
<!--WIZARD_ID:<%=HTMLEncoder.encodeForHTMLContent(request.getParameter("stepId"))%>:-->
<!--DYNAMIC_PAGE_START:-->
<%}%>
<c:catch var="ex">
  <c:if test="${param.stepurl != null}">
	<c:set var="stepurl" value="${param.stepurl}" scope="request"/>
  </c:if>
  <c:if test="${stepurl != null}">
	<%
	  String baseHREF = urlFactoryBean.getFactory().getBaseHREF();
	  //the import won't work for fully qualified HREFs so try to turn it into a relative one
	  String stepurl = NmURLFactoryBean.relativizeUrl((String) request.getAttribute("stepurl"), true);

	  // SPR 2133825: protect stepurl from XSS vulnerability
	  stepurl = HTMLEncoder.encodeForHTMLAttribute(stepurl);

	  // verify the url is a JSP or servlet and not some other type of file that might have sensitive configuration data (i.e. XML or properties file). SPR 2124769
	  if (com.ptc.core.components.util.RequestHelper.isRestrictedResource(stepurl)) {
		response.sendError(response.SC_FORBIDDEN);
	  }

	  request.setAttribute("stepurl", stepurl);
	  // Using a context on c:import to avoid XSS attacks, where the url could be to a different web-site
	  // All urls will be required to start with /
	  request.setAttribute("jca_wizard_urlContext", "/" + urlFactoryBean.getWEB_APP());

	  request.setAttribute("jca.no_500_error", Boolean.TRUE);
	  // for c:import tag, setting 500 error will cause the output to be lost,
	  // the import tag wil generate a generic exception withough the real root cause
	  // the jca.no_500_error allows the real root cause to be displayed.
	%>
	<c:import url="${stepurl}" context="${jca_wizard_urlContext}"/>
	<%
	  request.removeAttribute("jca.no_500_error");
	%>
  </c:if>
</c:catch>
<c:if test="${not empty ex}">
  <c:choose>
	<c:when test="${not empty jca_no_500_error_message}">
	  <c:out value="${jca_no_500_error_message}"/><br/>
	  <%
		request.removeAttribute("jca_no_500_error_message");
	  %>
	</c:when>
	<c:otherwise>
	  Page could not respond: ${stepurl}<br/><br/>
	  <%
		out.print(HTMLEncoder.encodeAndFormatForHTMLContent(MVCUtil.getLocalizedMessage((Throwable) pageContext.getAttribute("ex"))));
	  %>
	</c:otherwise>
  </c:choose>
</c:if>
<%
  closingPopupDialog = true;
  if (dynWiz) {
%><!--DYNAMIC_PAGE_END:--><%
  } %>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>
