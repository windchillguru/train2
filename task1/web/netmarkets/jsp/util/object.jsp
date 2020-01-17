<%@ page buffer="none"
%>
<%@ page import="com.ptc.netmarkets.model.NmObject"
%>
<%@ page import="com.ptc.netmarkets.model.NmOid"
%>
<%@ page import="com.ptc.netmarkets.util.misc.*"
%>
<%@ page import="com.ptc.netmarkets.util.beans.*"
%>
<%@ page import="java.util.ResourceBundle,com.ptc.netmarkets.util.utilResource"
%>
<%@ page import="java.util.Locale"

%>
<jsp:useBean id="sessionBean" class="com.ptc.netmarkets.util.beans.NmSessionBean" scope="session"/>
<%
  //
%>
<jsp:useBean id="errorBean" class="com.ptc.netmarkets.util.beans.NmErrorBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="localeBean" class="com.ptc.netmarkets.util.beans.NmLocaleBean" scope="request"/>
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
%>
<jsp:useBean id="urlFactoryBean" class="com.ptc.netmarkets.util.beans.NmURLFactoryBean" scope="request"/>
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
  NmAction.objectjsp(objectBean, sessionBean, localeBean, urlFactoryBean, actionBean, stringBean, linkBean, nmcontext, checkBoxBean, textBoxBean, radioButtonBean, textAreaBean, comboBoxBean, dateBean, out, request, response);
%>
