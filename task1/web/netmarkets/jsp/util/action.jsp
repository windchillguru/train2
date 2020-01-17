<%@ page import="com.ptc.netmarkets.util.misc.*"
%>
<%@ page import="com.ptc.netmarkets.model.NmOid"
%>
<%@ page import="java.util.HashMap"
%>
<%@ page import="java.util.Set"
%>
<%@ page import="java.util.Iterator"
%>
<%@ page import="java.util.Vector"
%>
<%@ page import="com.ptc.netmarkets.util.beans.NmActionBean"
%>
<%@ page import="com.ptc.netmarkets.project.NmProjectHelper"
%>
<%@ page import="wt.httpgw.URLFactory"
%>
<%@ page import="com.ptc.netmarkets.util.beans.NmContextBean" %>
<%
  //

%>
<jsp:useBean id="modelBean" class="com.ptc.netmarkets.util.beans.NmModelBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="commandBean" class="com.ptc.netmarkets.util.beans.NmCommandBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="nmcontext" class="com.ptc.netmarkets.util.beans.NmContextBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="actionBean" class="com.ptc.netmarkets.util.beans.NmActionBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="objectBean" class="com.ptc.netmarkets.util.beans.NmObjectBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="urlFactoryBean" class="com.ptc.netmarkets.util.beans.NmURLFactoryBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="sessionBean" class="com.ptc.netmarkets.util.beans.NmSessionBean" scope="session"/>
<%
  //
%>
<jsp:useBean id="localeBean" class="com.ptc.netmarkets.util.beans.NmLocaleBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="checkBoxBean" class="com.ptc.netmarkets.util.beans.NmCheckBoxBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="textBoxBean" class="com.ptc.netmarkets.util.beans.NmTextBoxBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="radioButtonBean" class="com.ptc.netmarkets.util.beans.NmRadioButtonBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="textAreaBean" class="com.ptc.netmarkets.util.beans.NmTextAreaBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="comboBoxBean" class="com.ptc.netmarkets.util.beans.NmComboBoxBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="dateBean" class="com.ptc.netmarkets.util.beans.NmDateBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="stringBean" class="com.ptc.netmarkets.util.beans.NmStringBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="linkBean" class="com.ptc.netmarkets.util.beans.NmLinkBean" scope="request"/>
<%
  //
  NmAction.actionjsp(actionBean, linkBean, objectBean, localeBean, urlFactoryBean, nmcontext, sessionBean, out, request, response);
%>
