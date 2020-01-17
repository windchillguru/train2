<%@ page import="com.ptc.netmarkets.util.beans.NmCommandBean,
				 wt.ixb.handlers.netmarkets.NmFeedbackSpec,
				 com.ptc.netmarkets.util.utilResource,
				 wt.util.*,
				 java.util.ResourceBundle"
%>
<%@ include file="/netmarkets/jsp/util/beginPopup.jspf"
%>
<%!
  private static final String STATUS_RESOURCE = "com.ptc.netmarkets.util.utilResource";
%><%
  //NmAction
  Locale status_locale = localeBean.getLocale();
  ResourceBundle status_rb = ResourceBundle.getBundle(STATUS_RESOURCE, status_locale);
  NmCommandBean cb = new NmCommandBean();
  cb.setCompContext(nmcontext.getContext().toString());
  cb.setRequest(request);
  String refresh = request.getParameter("refresh");
  String key = request.getParameter("feedbackkey");
  if (key == null)
	key = cb.getPrimaryOid().toString();
%><input type="hidden" name="refresh" value="<%=HTMLEncoder.encodeForHTMLAttribute(refresh)%>"/><%
%><input type="hidden" name="feedbackkey" value="<%=HTMLEncoder.encodeForHTMLAttribute(key)%>"/><%
  NmFeedbackSpec nmfs = (NmFeedbackSpec) sessionBean.getStorage().get(key);
  String title = "";
  if (nmfs != null) {
	title = WTMessage.getLocalizedMessage(STATUS_RESOURCE, utilResource.PROGRESS, new Object[]{nmfs.getTitle()}, status_locale);
	if (title == null)
	  title = "";
  } else {
	title = status_rb.getString(utilResource.COMPLETE);
  }
//System.out.println("Get feedback for " + key);
  boolean destroyNow = false;
  String SPACER = urlFactoryBean.getHREF(NetmarketURL.PREFIX + "images/sp.gif");


%>
<table border="0" cellspacing="0" cellpadding="0" width="100%" class="wiz">
  <TR>
	<TD class="wizCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<TD class="wizCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<TD class="wizBdr" colspan="4" width="100%"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<TD class="wizCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<TD class="wizCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
  </TR>
  <TR>
	<TD class="wizCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<TD class="wizInCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<TD class="wizTitle" colspan="4" width="100%"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<TD class="wizInCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<TD class="wizCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
  </TR>
  <tr>
	<td class="wizBdr" rowspan="4"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<TD class="wizTitle"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<TD class="wizTitle" width="100%" nowrap="nowrap" colspan="2"><FONT CLASS="wizardtitlefont">&nbsp;<%= title%>
	  &nbsp;</FONT></TD>
	<TD class="wizTitle" width="1"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<TD class="wizTitle" width="1" align="right">
	  <jsp:include page="/netmarkets/jsp/util/help.jsp" flush="true"/>
	</TD>
	<TD class="wizTitle" width="1"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<TD class="wizBdr" rowspan="4"><img src="<%=SPACER%>" width="1" height="1"/></TD>
  </tr>
  <TR>
	<TD class="wizBdr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<TD class="wizBdr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<TD class="wizBdr" width="100%"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<TD class="wizBdr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<TD class="wizBdr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<TD class="wizBdr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
  </TR>
  <TR class="wizTblBdy">
	<td rowspan="2"><img src="<%=SPACER%>" width="1" height="1"/></TD>
	<td valign="top" colspan="5">
	  <CENTER><BR/>

		<table border="0" cellspacing="0" cellpadding="0">
		  <TR>
			<TD><img src="<%=SPACER%>" width="6" height="1"/></TD>
			<TD class="tblCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tblCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tableborderbg"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tblCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tblCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD><img src="<%=SPACER%>" width="6" height="1"/></TD>
		  </TR>
		  <TR>
			<TD><img src="<%=SPACER%>" width="6" height="1"/></TD>
			<TD class="tblCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tableborderbg"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tablebg"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tableborderbg"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tblCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD><img src="<%=SPACER%>" width="6" height="1"/></TD>
		  </TR>
		  <TR>
			<TD><img src="<%=SPACER%>" width="6" height="1"/></TD>
			<TD class="tableborderbg"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tablebg"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tablebg">
			  <BR/><BR/>
			  <%
				if (nmfs != null) {
				  if (nmfs.getException() != null) {
					destroyNow = true;
					Exception exception = nmfs.getException();
					if (exception instanceof WTException) {
					  Exception nested = (Exception) ((WTException) exception).getNestedThrowable();
					  if (nested != null)
						exception = nested;
					}
					String message = exception.getLocalizedMessage();
					NmAction.showError(message, out);
			  %>
			  <SCRIPT LANGUAGE="JavaScript">
                  setTimeout('wfWindowClose1()', 10000);
			  </SCRIPT>
			  <%
				  //
				}
				if (nmfs.isAllDone()) {
				  destroyNow = true;
				  nmfs = null;
				} else {
				  StringBuffer history = new StringBuffer(" ");
				  String[] all = nmfs.getAllMessages();
				  // Store the message at index 0 in latestMessage and all remaining messages in history
				  for (int alli = 1; alli < all.length; alli++) {
					history.append(all[alli] + "\n");
				  }

				  String latestMessage = null;
				  if (all.length > 0) {
					latestMessage = all[0];
				  }
				  if (latestMessage == null)
					latestMessage = status_rb.getString(utilResource.GENERATING_RESULT);

			  %>
			  <table border="0">
				<tr>
				  <td align="right" valign="top" nowrap="nowrap">
					<font class="wizardlabel"><%=status_rb.getString(utilResource.CURRENT_WORK)%>
					</font>
				  </td>
				  <td align="left" valign="top"><font class="wizardlabel">
					<%=latestMessage%>
				  </font>
				  </td>
				</tr>
				<tr>
				  <td align="right" valign="top">
					<font class="wizardlabel"><%=status_rb.getString(utilResource.HISTORY)%>
					</font>
				  </td>
				  <td align="left" valign="top">
					<textarea wrap="wrap" name="description" cols="55" rows="7"
							  disabled="disabled"><%=history.toString().trim()%></textarea>
				  </td>
				</tr>
			  </table>
			  <%
				  }
				} else {
				  destroyNow = true;
				}
				if (destroyNow) {
				  if (request.getParameter("destroyWhenDone") != null) {
			  %>
			  <SCRIPT LANGUAGE="JavaScript">
                  closePopupAndRefreshOpener();
                  wfWindowClose1();
			  </SCRIPT>
			  <%
				//
			  } else {
				String url = (String) sessionBean.getStorage().get("urlToDownload");
				sessionBean.getStorage().remove("urlToDownload");
				if (url != null && url.length() > 0) {
				  if (refresh != null && refresh.equals("true")) {
			  %>
			  <SCRIPT LANGUAGE="JavaScript">
                  // defer for  1915052 - fixing refresh during download
                  refreshOpenerOnly.defer(2000);
			  </SCRIPT>
			  <%
				  //
				}

				//System.out.println("the url to forward to is " + url);
			  %>
			  <SCRIPT LANGUAGE="JavaScript">
                  var downloadFunc = function () {
                      if (opener && opener.top.window && !Ext.isGCF) {
                          opener.top.window.location = '<%=NmAction.escapeJsMessage(url)%>';
                      } else {
                          top.window.location = '<%=NmAction.escapeJsMessage(url)%>';
                      }
                  }
                  if (Ext.isGCF) {
                      //2127541 - Delay the download in chrome-frame to allow the browser to hand off control properly
                      setTimeout(downloadFunc, 500);
                  } else {
                      downloadFunc();
                  }

                  if (!Ext.isGCF) {
                      //Because GCF uses the status window for the download, it should not close it until the download is complete
                      //The window will need to be closed manually for GCF
                      setTimeout('wfWindowClose1()', 4000);
                  }
			  </SCRIPT>
			  <%
				//
			  } else {
			  %>
			  <SCRIPT LANGUAGE="JavaScript">
                  wfWindowClose1();
			  </SCRIPT>
			  <%
					//
				  }
				}
				sessionBean.getStorage().remove(key);
			  } else {
			  %>
			  <SCRIPT LANGUAGE="JavaScript">
                  // SPRs 1354057, 1407618, 1407880 - remove quotes on null params to prevent javascript errors
                  setTimeout("submitIt('<%=HTMLEncoder.encodeForJavascript(key)%>', 'none', null, null, 'inbegin','', '','','','','','','','','','','','','','','','','','','','','','','','','','','')", 5000);
			  </SCRIPT>
			  <br/><br/><FONT CLASS="wizardlabel"><%=status_rb.getString(utilResource.SIT_TIGHT)%>
			</FONT>
			  <%}%>
			  <%@ include file="/netmarkets/jsp/util/end.jspf" %>
			  <BR/><BR/>
			</TD>
			<TD class="tablebg"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tableborderbg"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD><img src="<%=SPACER%>" width="6" height="1"/></TD>
		  </TR>
		  <TR>
			<TD><img src="<%=SPACER%>" width="6" height="1"/></TD>
			<TD class="tblCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tableborderbg"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tablebg"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tableborderbg"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tblCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD><img src="<%=SPACER%>" width="6" height="1"/></TD>
		  </TR>
		  <TR>
			<TD><img src="<%=SPACER%>" width="6" height="1"/></TD>
			<TD class="tblCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tblCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tableborderbg"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tblCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD class="tblCnr"><img src="<%=SPACER%>" width="1" height="1"/></TD>
			<TD><img src="<%=SPACER%>" width="6" height="1"/></TD>
		  </TR>
		</TABLE>
	  </CENTER>
	</TD>
  </TR>
</table>
