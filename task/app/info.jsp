<%@page import="wt.httpgw.URLFactory" %>
<%@page pageEncoding="UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>
<%@taglib uri="http://www.ptc.com/windchill/taglib/jcaMvc" prefix="jcaMvc" %>

<%@page import="ext.task2.part.util.PartUtil ,java.util.*" %>
<%
  String info = PartUtil.checkPart(commandBean);
  if (info == null || "".equals(info)) {
	info = "检查通过";
  }
%>
<c:set var="info" scope="page" value="<%=info%>"/>
<br/>
<br/>
<br/>
<br/>
<div style="width: auto; height: auto;">
  <p><font size="4">注意：</font></p>
  <p>${info}</P>
</div>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>