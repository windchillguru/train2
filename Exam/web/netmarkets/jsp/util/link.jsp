<%@ page buffer="none"
%>
<%@ page import="com.ptc.netmarkets.util.misc.*" %>
<%
  //
%>
<%@ page import="com.ptc.netmarkets.util.beans.*" %>
<%
  //
%>
<%@ page import="java.util.Stack" %>
<%
  //
%>
<%@ page import="wt.util.HTMLEncoder" %>
<%
  //

  try {

%>
<jsp:useBean id="urlFactoryBean" class="com.ptc.netmarkets.util.beans.NmURLFactoryBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="linkBean" class="com.ptc.netmarkets.util.beans.NmLinkBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="objectBean" class="com.ptc.netmarkets.util.beans.NmObjectBean" scope="request"/>
<%
  //
%>
<jsp:useBean id="nmcontext" class="com.ptc.netmarkets.util.beans.NmContextBean" scope="request"/>
<%
  //

  NmLink link = linkBean.getLink();
  String fontclass = linkBean.getFontClass();
  String href = null;
  String img = link.getIcon();
  String label = link.getLabel();
  String labelEncodedForHTMLContent = HTMLEncoder.encodeForHTMLContent(label);
  String labelEncodedForHTMLAttribute = HTMLEncoder.encodeForHTMLAttribute(label);
  Stack s = nmcontext.getContext().getContextItems();
  String toploc = "top.window.location=";
  boolean isMenu = false;
  boolean isMore = (NmAction.Command.MORE.equals(((NmContextItem) s.peek()).getAction()));
  if (link instanceof NmURL) {
	NmURL url = (NmURL) link;
	isMenu = url.isMenu();
	href = NetmarketURL.buildURL(urlFactoryBean, url.getType(), url.getAction(), url.getOid(), url.getParams(), (isMenu || isMore));
  } else if (link instanceof NmAnyURL) {
	href = urlFactoryBean.getHREF(((NmAnyURL) link).getUrl());
  }

  String moreinfo = link.getMoreUrlInfo();
  if (moreinfo != null && moreinfo.length() > 0) {
	if (href.indexOf("?") > 0)
	  href += "&" + moreinfo;
	else
	  href += "?" + moreinfo;
  }

  if (s != null && s.size() > 0 && isMore) {
	toploc = "window.opener.location=";
	href = "javascript:" + toploc + "'" + href + "'; WaitAndClose(); ";
  }

  if (isMenu) {
%><%=toploc%>'<%=href%>'<%
} else if (img == null || !linkBean.isUseIcon()) {
  if (link instanceof NmAnyURL && ((NmAnyURL) link).isOpenNewWindow()) {
%><A HREF="<%=href%>" CLASS="<%=fontclass%>" target="_blank"><%=labelEncodedForHTMLContent%>
</A><%
} else {
%><A HREF="<%=href%>" CLASS="<%=fontclass%>"><%=labelEncodedForHTMLContent%>
</A><%
  }
} else {
  String imgHTML = "<IMG SRC=\"" + urlFactoryBean.getHREF(img) + "\"";
  if (labelEncodedForHTMLAttribute != null) {
	imgHTML += " ALT=\"" + labelEncodedForHTMLAttribute + "\" TITLE=\"" + labelEncodedForHTMLAttribute + "\"";
  }
  imgHTML += " hspace=\"0\" vspace=\"0\" border=\"0\"/>";
  if (link instanceof NmAnyURL && ((NmAnyURL) link).isOpenNewWindow()) {
%>
<CENTER><A HREF="<%=href%>" target="_blank"><%=imgHTML%><BR/><font
	class="<%=fontclass%>"><%=labelEncodedForHTMLContent%>
</font></A></CENTER>
<%
} else if (link instanceof NmAnyURL && !((NmAnyURL) link).isOpenNewWindow()) {
%>
<CENTER><A HREF="<%=href%>"><%=imgHTML%><BR/><font class="<%=fontclass%>"><%=labelEncodedForHTMLContent%>
</font></A></CENTER>
<%
} else {
%><A HREF="<%=href%>"><%=imgHTML%>
</A><%
	}
  }
} catch (Throwable t) {
%>
<jsp:useBean id="errorBean" class="com.ptc.netmarkets.util.beans.NmErrorBean" scope="request"/>
<%
  errorBean.setThrowable(t);
%>
<jsp:include page="/netmarkets/jsp/util/error.jsp" flush="true"/>
<%
	//
	errorBean.setThrowable(null);
  }
%>
