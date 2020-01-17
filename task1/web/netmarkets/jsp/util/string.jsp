<%@ page buffer="none"
%>
<jsp:useBean id="stringBean" class="com.ptc.netmarkets.util.beans.NmStringBean" scope="request"/>
<%
  //
  String formattedString = stringBean.getString();
  out.print(formattedString);
%>
