<%@ page buffer="none"
%>
<%@ page import="java.util.Locale"
%>
<%@ page import="java.util.ResourceBundle"
%>
<%@ page import="java.util.ArrayList"
%>
<%@ page import="java.util.Stack"
%>
<%@ page import="java.util.HashMap"
%>
<%@ page import="java.util.Iterator"
%>
<%@ page import="java.util.Set"
%>
<%@ page import="java.util.StringTokenizer"
%>
<%@ page import="com.ptc.netmarkets.util.utilResource"
%>
<%@ page import="com.ptc.netmarkets.util.table.*"
%>
<%@ page import="com.ptc.netmarkets.util.misc.*"
%>
<%@ page import="com.ptc.netmarkets.util.beans.NmCommandBean"
%>
<%@ page import="com.ptc.netmarkets.util.beans.NmContextBean"
%>
<%@ page import="com.ptc.netmarkets.util.beans.NmSessionBean"
%>
<%@ page import="com.ptc.netmarkets.folder.folderResource"
%>
<%@ page import="com.ptc.netmarkets.model.*"
%>
<%@ page import="javax.servlet.http.HttpUtils"
%>
<%@ page import="com.ptc.netmarkets.util.beans.*"
%>
<%!
  private static final String TABLE_RESOURCE = "com.ptc.netmarkets.util.utilResource";

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
  NmTableRenderer.draw(modelBean, objectBean, sessionBean, localeBean, urlFactoryBean, actionBean, stringBean, linkBean, nmcontext, checkBoxBean, textBoxBean, radioButtonBean, textAreaBean, comboBoxBean, dateBean, request.getParameter("showCount"), request.getParameter("lmt"), out, request, response);
%>
