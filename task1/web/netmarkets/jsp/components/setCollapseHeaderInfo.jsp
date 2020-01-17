<%@ include file="/netmarkets/jsp/util/begin.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${param.Header != null}">
  <c:set var="headerCollapsed" value="<%= new Boolean(request.getParameter(\"Header\")) %>"/>
  <c:set target="${sessionBean}" property="headerCollapsed" value="${headerCollapsed}"/>
</c:if>

<c:if test="${param.AttributesPanel != null}">
  <c:set var="attributesPanelCollapsed" value="<%= new Boolean(request.getParameter(\"AttributesPanel\")) %>"/>
  <c:set target="${sessionBean}" property="attributesPanelCollapsed" value="${attributesPanelCollapsed}"/>
</c:if>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>