<%@ page buffer="none"
%>
<%@ page import="com.ptc.netmarkets.util.misc.*,java.util.ResourceBundle,com.ptc.netmarkets.util.utilResource" %>
<%
  //
%>
<jsp:useBean id="urlFactoryBean" class="com.ptc.netmarkets.util.beans.NmURLFactoryBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="nmcontext" class="com.ptc.netmarkets.util.beans.NmContextBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="localeBean" class="com.ptc.netmarkets.util.beans.NmLocaleBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="linkBean" class="com.ptc.netmarkets.util.beans.NmLinkBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="modelBean" class="com.ptc.netmarkets.util.beans.NmModelBean" scope="request"/>
<%
  //
%><%! private static final String HELP_RESOURCE = "com.ptc.netmarkets.util.utilResource"; %><%
  //

  if (modelBean.getHelpFile() != null) {
	if (modelBean.getHelpFile().equals("null"))
	  NmAction.helpjsp(null, urlFactoryBean, localeBean, nmcontext, linkBean, out, request, response);
	else
	  NmAction.helpjsp(modelBean.getHelpFile(), urlFactoryBean, localeBean, nmcontext, linkBean, out, request, response);
  } else {
	NmAction.helpjsp(urlFactoryBean, localeBean, nmcontext, linkBean, out, request, response);
  }
%>
