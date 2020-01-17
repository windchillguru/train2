<%@ page import="com.ptc.netmarkets.model.*"
%>
<%@ page import="java.util.ArrayList"
%>
<%@ page import="java.util.Locale"
%>
<%@ page import="java.util.ResourceBundle"
%>
<%@ page import="java.net.URL"
%>
<%@ page import="com.ptc.core.HTMLtemplateutil.server.processors.WizardProcessor"
%>
<%@ page import="com.ptc.netmarkets.util.utilResource"
%>
<%@ page import="com.ptc.netmarkets.util.misc.*"
%>
<%@ page import="com.ptc.netmarkets.util.wizard.*"
%>
<%@ page import="wt.help.HelpLinkHelper"
%>
<%@ page import="com.ptc.core.components.servlets.*"
%>
<%@ page import="com.ptc.core.components.util.RenderUtil"
%>
<%@ page import="com.ptc.netmarkets.util.misc.NmContextItem"
%>
<%@ page import="com.ptc.netmarkets.util.beans.*"
%>
<%@ page import="wt.util.HTMLEncoder" %>
<%
  //
  if (NmSessionBean.REMOVE_ALL_HTML) {
	if (request.getAttribute("CheckScripts") == null) {
	  request.setAttribute("CheckScripts", "true");
	  String ourl = request.getRequestURI();
	  if (ourl.toLowerCase().indexOf("cript:") >= 0) {
		ResourceBundle begin_rb2 = ResourceBundle.getBundle(BEGIN_RESOURCE, wt.session.SessionHelper.getLocale());
		throw new NmException(begin_rb2.getString(utilResource.SECURITY_BLOCKED));
	  }
	  java.util.Enumeration names = request.getParameterNames();
	  while (names.hasMoreElements()) {
		String name = (String) names.nextElement();
		if (name.indexOf(com.ptc.netmarkets.util.beans.NmStringBean.LT) >= 0 || name.indexOf(com.ptc.netmarkets.util.beans.NmStringBean.GT) >= 0 || name.toLowerCase().indexOf("cript:") >= 0) {
		  ResourceBundle begin_rb2 = ResourceBundle.getBundle(BEGIN_RESOURCE, wt.session.SessionHelper.getLocale());
		  throw new NmException(begin_rb2.getString(utilResource.SECURITY_BLOCKED));
		}
		String[] values = request.getParameterValues(name);
		for (int i = 0; i < values.length; i++) {
		  String value = values[i];
		  if (NmSessionBean.CONVERT_INPUT) value = new String(value.getBytes("ISO-8859-1"), "UTF8");
		  if (value.indexOf(com.ptc.netmarkets.util.beans.NmStringBean.LT) >= 0 || value.indexOf(com.ptc.netmarkets.util.beans.NmStringBean.GT) >= 0 || value.toLowerCase().indexOf("cript:") >= 0) {
			ResourceBundle begin_rb2 = ResourceBundle.getBundle(BEGIN_RESOURCE, wt.session.SessionHelper.getLocale());
			throw new NmException(begin_rb2.getString(utilResource.SECURITY_BLOCKED));
		  }
		}
	  }
	}
  }
  // When form contains a file input field it must post as multipart/form-data to get content.
  // Pass multipart input to parseMultipart.jsp which will parse parameters, save streams
  // to temp files, and forward back here when done.
  String ct = request.getContentType();
  Boolean parsed = null;
  if (ct != null && ct.startsWith("multipart")) {
	parsed = (Boolean) request.getAttribute(NmWizard.Attributes.MULTIPART_PARSED);
	if (parsed == null || !parsed.booleanValue()) {
	  request.setAttribute(NmWizard.Attributes.ORIGINAL_REQUEST, request);
	  request.setAttribute(NmWizard.Attributes.FORWARD_PAGE, request.getServletPath());
%>
<jsp:forward page="/netmarkets/jsp/util/parseMultipart.jsp"/>
<%
	} else {
	  JCAPageModelFactory factory = new DefaultJCAPageModelFactory();
	  NmHelperBean helper = NmHelperBeanFactory.getInstance().getHelperBean(request, response);
	  factory.setHelper(helper);

	  request.setAttribute("jcaPageModel", factory.getPageModel());
	}
  }

%>
<jsp:include page="/netmarkets/jsp/util/wizardstep.jsp" flush="true"/>
<%@ include file="/netmarkets/jsp/util/beginPopup.jspf" %>
<%!
  private static final String WIZARD_RESOURCE = "com.ptc.netmarkets.util.utilResource";
%>
<%
  // check to see if the request parameter doWizardValidate set for calling an action validator
  // if so we need to call the action validator specified by the action in the context
  // doWizardValidate should be set to true for the action to be called
  String doWizardValidate = (String) request.getAttribute("doWizardValidate");
  if ("true".equals(doWizardValidate)) {
	NmContextItem ci = (NmContextItem) nmcontext.getContext().getContextItems().lastElement();
	RenderUtil.doWizardValidation(out, ci.getAction(), ci.getType(), commandBean);
  }

  out.write("<script type=\"text/javascript\">");
  JCAPageModel legacyWizardModel = (JCAPageModel) request.getAttribute("jcaPageModel");
  boolean popupWindowInitialized = request.getParameter("popupWindow") != null;
  if (!popupWindowInitialized && request.getAttribute("pjl.skiponload") == null) {
	out.write("Event.observe(window,'load', initPopupForm);");
  } else if (commandBean.getExecutionStatus().equals(NmAction.ExecutionStatus.EXECUTED)) {
	if (request.getAttribute("KEEP_OPEN") == null) {
	  String wdialogButton = legacyWizardModel.getDialogButton();
	  if (NmCommandBean.DialogButton.OK.equals(wdialogButton)) {
		if (request.getAttribute("CLOSE_ONLY") != null) {
		  out.write("Event.observe(window,'load', wfWindowClose1);");
		} else {
		  out.write("Event.observe(window,'load', closePopupAndRefreshOpener);");
		}
		legacyWizardModel.setClosingPopupDialog(true);
	  } else if (NmCommandBean.DialogButton.APPLY.equals(wdialogButton)) {
		out.write("Event.observe(window,'load', PJLrefreshOpener);");
	  } else {
		out.write("Event.observe(window,'load', resetPopupForm);");
	  }
	} else {
	  //onLoad = "";
	}
  } else if (request.getAttribute("pjl.skiponload") == null) {
	out.write("Event.observe(window,'load', resetPopupForm);");
  }
  out.write("</script>");
  NmCommandBean cb = new NmCommandBean();
  Locale wizard_locale = localeBean.getLocale();
  ResourceBundle wizard_rb = ResourceBundle.getBundle(WIZARD_RESOURCE, wizard_locale);
  int old_step = wizardBean.getOldStep();
  int new_step = wizardBean.getNewStep();

  NmWizard w = wizardBean.getWizard();
  ArrayList steps = w.getSteps();
  if (steps != null && steps.size() > 0) {
	if (new_step >= steps.size())
	  new_step = steps.size() - 1;

	ArrayList stepData = wizardBean.getStepData();
	NmWizardStep old_step_def = (NmWizardStep) steps.get(old_step);
	NmWizardStep new_step_def = (NmWizardStep) steps.get(new_step);

	// this is to prevent an execution on page refreshing
	cb.setInBeginJsp(true);

	// checking whether APPLY button was pressed
	String buttonPressed = request.getParameter("dialogButton");
	if (buttonPressed != null && buttonPressed.equals(NmCommandBean.DialogButton.APPLY)) {
	  wizardBean.setApplyPressed(true);
	}
	// the following is an attempt to execute a command associated with wizard page
	NmWizardModel model = new NmWizardModel();
	modelBean.setModel(model);

	try {
	  cb.setCompContext(nmcontext.getContext().toString());
	  cb.setOpenerCompContext(request.getParameter("openerCompContext"));
	  cb.setOpenerElemAddress(request.getParameter("openerElemAddress"));
	  cb.setActionClass(w.getClassName());
	  cb.setSessionBean(sessionBean);
	  cb.setRequest(request);
	  cb.setResponse(response);
	  cb.setOut(out);
	  cb.setWtcontextBean(wtcontext);
	  cb.setUrlFactoryBean(urlFactoryBean);
	  cb.setContextBean(nmcontext);
	  cb.setClipboardBean(clipboardBean);
	  cb.setWizardBean(wizardBean);

	  cb.setActionMethod(w.getOnPreRenderMethod());
	  if (cb.readyToExecute()) {
		// Executing method before rendering any pages
		Object retObj = cb.execute();
		if (retObj instanceof NmModel)
		  model.setPreRenderModel((NmModel) retObj);
		else if (retObj != null && retObj instanceof NmWizardAlert)
		  NmAction.showError(((NmWizardAlert) retObj).getMessage(), out);
		else if (wizard.isDownloadResult() && ((retObj instanceof String) || (retObj instanceof URL))) {

		  String wizardDownloadStr = null;
		  if (retObj instanceof String)
			wizardDownloadStr = (String) retObj;
		  else if (retObj instanceof URL)
			wizardDownloadStr = ((URL) retObj).toExternalForm();

		  if (wizardDownloadStr != null) {
%>
<script language="JavaScript">
    top.window.location = '<%=wizardDownloadStr%>';
    setTimeout('wfWindowClose()', 2000);
</script>
<%
		//
	  }
	} else {
	  String cc = request.getParameter("compContext");
	  sessionBean.updateModels(cc, retObj);
	}
  }
  cb.setActionMethod(new_step_def.getStepMethod());
  if (cb.readyToExecute()) {
	// Executing page command if any
	Object retObj = cb.execute();
	if (retObj instanceof NmModel)
	  model.setStepModel((NmModel) retObj);
	else if (retObj != null && retObj instanceof NmWizardAlert)
	  NmAction.showError(((NmWizardAlert) retObj).getMessage(), out);
	else if (wizard.isDownloadResult() && ((retObj instanceof String) || (retObj instanceof URL))) {
	  String wizardDownloadStr = null;
	  if (retObj instanceof String)
		wizardDownloadStr = (String) retObj;
	  else if (retObj instanceof URL)
		wizardDownloadStr = ((URL) retObj).toExternalForm();

	  if (wizardDownloadStr != null) {
%>
<script language="JavaScript">
    top.window.location = '<%=wizardDownloadStr%>';
    setTimeout('wfWindowClose()', 2000);
</script>
<%
		//
	  }
	} else {
	  String cc = request.getParameter("compContext");
	  sessionBean.updateModels(cc, retObj);
	}
  }

  if ((request.getMethod().equals("POST") && !wizardBean.isApplyPressed()) || request.getMethod().equals("GET")) {

	if (new_step != old_step) {
	  cb.setActionMethod(old_step_def.getOnDeActivateMethod());

	  if (cb.readyToExecute()) {
		// Executing page command if any
		Object retObj = cb.execute();
		if (retObj instanceof NmModel)
		  model.setOldStepModel((NmModel) retObj);
		else if (wizard.isDownloadResult() && ((retObj instanceof String) || (retObj instanceof URL))) {
		  String wizardDownloadStr = null;
		  if (retObj instanceof String)
			wizardDownloadStr = (String) retObj;
		  else if (retObj instanceof URL)
			wizardDownloadStr = ((URL) retObj).toExternalForm();

		  if (wizardDownloadStr != null) {
%>
<script language="JavaScript">
    top.window.location = '<%=wizardDownloadStr%>';
    setTimeout('wfWindowClose()', 2000);
</script>
<%
		  //
		}
	  } else if (retObj != null && retObj instanceof NmWizardAlert)
		NmAction.showError(((NmWizardAlert) retObj).getMessage(), out);
	  else {
		String cc = request.getParameter("compContext");
		sessionBean.updateModels(cc, retObj);
	  }
	}
  }

  cb.setActionMethod(new_step_def.getOnActivateMethod());

  if (cb.readyToExecute()) {
	// Executing page command if any
	Object retObj = cb.execute();
	if (retObj instanceof NmModel)
	  model.setNewStepModel((NmModel) retObj);
	else if (wizard.isDownloadResult() && ((retObj instanceof String) || (retObj instanceof URL))) {
	  String wizardDownloadStr = null;
	  if (retObj instanceof String)
		wizardDownloadStr = (String) retObj;
	  else if (retObj instanceof URL)
		wizardDownloadStr = ((URL) retObj).toExternalForm();

	  if (wizardDownloadStr != null) {
%>
<script language="JavaScript">
    top.window.location = '<%=wizardDownloadStr%>';
    setTimeout('wfWindowClose()', 2000);
</script>
<%
		  //
		}
	  } else if (retObj != null && retObj instanceof NmWizardAlert)
		NmAction.showError(((NmWizardAlert) retObj).getMessage(), out);
	  else {
		String cc = request.getParameter("compContext");
		sessionBean.updateModels(cc, retObj);
	  }
	}

  }
  // merging instructions possibly produced by one of the commands
  commandBean.getInstructions().getList().addAll(cb.getInstructions().getList());
  commandBean.getOpenerInstructions().getList().addAll(cb.getOpenerInstructions().getList());
} catch (Throwable t) {
  if (t instanceof java.lang.reflect.InvocationTargetException) {
	Throwable nested = ((java.lang.reflect.InvocationTargetException) t).getTargetException();
	if (nested != null)
	  t = nested;
  }
  if (t instanceof NmException) {
	NmException e = (NmException) t;
	escaping = e.isToEscape();
  }

  if (escaping) {
	break app; // see end of begin.jspf
  } else {
	try {
	  errorBean.setThrowable(t);
	  errorBean.setUsingJavaScript(true);
%>
<jsp:include page="/netmarkets/jsp/util/error.jsp" flush="true"/>
<%
	  } finally {
		errorBean.setThrowable(null);
		errorBean.setUsingJavaScript(false);
	  }

	  // do not allow changing a step
	  new_step = old_step;
	}
  }
  ArrayList selected = cb.getSelectedOidForPopup();
  if ((request.getParameter(NmCommandBean.ElementName.SELECTED_OID) == null || request.getParameter("rewrite_soids") != null) && selected.size() > 0) {
	out.write("<input type=\"hidden\" name=\"rewrite_soids\" value=\"true\"/>\n");
	for (int i = 0; i < selected.size(); i++) {
	  out.write("<input type=\"hidden\" name=\"" + NmCommandBean.ElementName.SELECTED_OID + "\" value=\"" + HTMLEncoder.encodeForHTMLAttribute(selected.get(i).toString()) + "\"/>\n");
	}
  }

  if (cb.getSharedContextOid() != null) {
%><input type="hidden" name="<%=NmCommandBean.ElementName.SHARED_CONTEXT_OID%>"
		 value="<%=HTMLEncoder.encodeForHTMLAttribute(cb.getSharedContextOid().toString())%>"/><%
  }

%>
<script language="JavaScript">
    window.onBeforeunload = executeCancel;

    function onApply() {
        var error = <%=(w.getOnApplyScript() != null) ? (w.getOnApplyScript() + "()") : "null"%>;
        if (error == null) {
            disableButtons();
            if (!submitPopupForm('<%=NmCommandBean.DialogButton.APPLY%>')) {
                enableButtons();
                setDialogFormData("<%=NmCommandBean.DialogButton.OK%>");
            }
        } else {
            popupErrorDialog(error);
        }
    }

    <%
    for (int i=0; i<steps.size(); i++) {
        NmWizardStep step = (NmWizardStep)steps.get(i); %>

    function gotoStep<%=i%>() {
        var error = <%=(new_step_def.getOnDeActivateScript() != null) ? (new_step_def.getOnDeActivateScript() + "()") : "null"%>;

        if (error == null) {
            error = <%=(step.getOnActivateScript() != null) ? (step.getOnActivateScript() + "()") : "null"%>;
        }

        if (error == null) {
            gotoStep(<%=i%>);
        } else {
            popupErrorDialog(error);
        }
    }

    <% } %>
</script>
<input type="hidden" name="<%=NmWizard.Parameters.OLD_STEP%>" value="<%=new_step%>"/>
<input type="hidden" name="<%=NmWizard.Parameters.NEW_STEP%>" value="<%=new_step%>"/>
<%--  It works but will not be included into DSU1
<a href="javascript:resizeWindow(false)")>Smaller</a>&nbsp;
<a href="javascript:resizeWindow(true)")>Larger</a>
--%><%
  //
  String SPACER = urlFactoryBean.getHREF(NetmarketURL.PREFIX + "images/sp.gif");
%>

<table border="0" cellspacing="0" cellpadding="0" width="100%" class="wiz">
  <tr>
	<td class="wizCnr" colspan="2"><img src="<%=SPACER%>"/></td>
	<td class="wizBdr" colspan="4" width="100%"></td>
	<td class="wizCnr" colspan="2"></td>
  </tr>
  <tr>
	<td class="wizCnr"><img src="<%=SPACER%>"/></td>
	<td class="wizInCnr"></td>
	<td class="wizTitle" colspan="4" width="100%"></td>
	<td class="wizInCnr"></td>
	<td class="wizCnr"></td>
  </tr>
  <tr>
	<td class="wizBdr"><img src="<%=SPACER%>" height="1"/></td>
	<td class="wizTitle"><img src="<%=SPACER%>" height="1"/></td>
	<td class="wizTitle" width="100%" height="1" nowrap="nowrap" colspan="2"><font
		class="wizardtitlefont">&nbsp;<%=w.getLabel()%> &nbsp;</font></td>
	<td class="wizTitle" height="1"></td>
	<td class="wizTitle" align="right" height="2">
	  <table border="0" cellspacing="0" cellpadding="2">
		<tr>
		  <td class="wizTitle" width="1"></td>
		  <td class="wizTitle" width="1" align="right"><%
			if (modelBean.getHelpFile() != null) {
			  if (modelBean.getHelpFile().equals("null"))
				NmAction.helpjsp(null, urlFactoryBean, localeBean, nmcontext, linkBean, out, request, response);
			  else
				NmAction.helpjsp(modelBean.getHelpFile(), urlFactoryBean, localeBean, nmcontext, linkBean, out, request, response);
			} else if (modelBean.getHelpSelectorKey() != null) {
			  String helpURL = HelpLinkHelper.createHelpHREF(modelBean.getHelpSelectorKey());
			  NmAction.helpjsp(helpURL, urlFactoryBean, localeBean, nmcontext, linkBean, out, request, response);

			} else {
			  NmAction.helpjsp(urlFactoryBean, localeBean, nmcontext, linkBean, out, request, response);
			}%>
		  </td>
		</tr>
	  </table>
	</td>
	<td class="wizTitle" width="1" height="1"></td>
	<td class="wizBdr" height="1"></td>
  </tr>
  <tr>
	<td class="wizBdr" rowspan="3"><img src="<%=SPACER%>"/></td>
	<td class="wizBdr" colspan="2"></td>
	<td class="wizBdr" width="100%"><img src="<%=SPACER%>" width="100" height="1"/></td>
	<td class="wizBdr" colspan="3"></td>
	<td class="wizBdr" rowspan="3"></td>
  </tr>
  <tr class="wizTblBdy">
	<td rowspan="2"><img src="<%=SPACER%>"/></td>
	<td valign="top"><%
	  //
	  int next_step = -1;
	  int prev_step = -1;

	  //Create the table of steps if there is more than one step in the wizard
	  if (steps.size() > 1) {
	%>
	  <div ALIGN="CENTER"><br/>
		<table border="0">
		  <tr>
			<td>
			  <table border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td class="wizCnr" colspan="2"><img src="<%=SPACER%>"/></td>
				  <td class="wizBdr" colspan="2"></td>
				  <td class="wizCnr" colspan="2"></td>
				</tr>
				<tr>
				  <td class="wizCnr"><img src="<%=SPACER%>"/></td>
				  <td class="wizInCnr"></td>
				  <td class="wizTitle" colspan="2"></td>
				  <td class="wizInCnr"></td>
				  <td class="wizCnr"></td>
				</tr>
				<tr>
				  <td class="wizBdr"><img src="<%=SPACER%>"/></td>
				  <td class="wizTitle"></td>
				  <td class="wizTitle" width="6"></td>
				  <td class="wizTitle wizardsteptilefontOLD"><%=wizard_rb.getString(utilResource.STEPS)%>
				  </td>
				  <td class="wizTitle"></td>
				  <td class="wizBdr"></td>
				</tr>
				<%

				  int stepIndexForUser = 0;

				  for (int i = 0; i < steps.size(); i++) {
					NmWizardStepData sdata = (NmWizardStepData) stepData.get(i);

					// display only available steps
					if (sdata.isVisible()) {
					  // find out previous accessable step
					  if (i < new_step && sdata.isAccessible()) {
						prev_step = i;
					  }

					  // find out next accessable step
					  if (i > new_step && sdata.isAccessible() && next_step == -1) {
						next_step = i;
					  }

					  stepIndexForUser++;

				%>
				<tr class="wizStp">
				  <td class="wizBdr"><img src="<%=SPACER%>" height="5" width="1"/></td>
				  <td colspan="4"></td>
				  <td class="wizBdr"></td>
				</tr>
				<tr class="wizStp<% if (i == new_step) { %>Sel<% } %>">
				  <td class="wizBdr"><img src="<%=SPACER%>"/></td>
				  <td colspan="2"></td>

				  <% NmWizardStep step = (NmWizardStep) steps.get(i); %>

				  <td nowrap="nowrap" align="left"><%
					// display current and inaccessable steps as plain text
					if (i != new_step && sdata.isAccessible()) {
				  %> <font class="wizardstepsfont"><a href="javascript:gotoStep<%=i%>()" class="wizardstepsfont2"><%
					} else if (i == new_step) {
					  out.println("<font class=\"wizardstepsfont\"> ");
					} else if (!sdata.isAccessible()) {
					  out.println(" <font class=\"wizardstepsfont3\"> ");
					}

				  %><%=stepIndexForUser%>.&nbsp;<%=step.getLabel()%>&nbsp;<%

					if (i != new_step && sdata.isAccessible()) {
				  %></a><%
					}

				  %></font></td>
				  <td></td>
				  <td class="wizBdr"></td>
				</tr>
				<%
					}
				  } %>

				<tr class="wizStp">
				  <td class="wizBdr"><img src="<%=SPACER%>" height="5" width="1"/></td>
				  <td colspan="4"></td>
				  <td class="wizBdr"></td>
				</tr>
				<tr>
				  <td class="wizCnr"><img src="<%=SPACER%>"/></td>
				  <td class="wizInCnr"></td>
				  <td class="wizStp" colspan="2"></td>
				  <td class="wizInCnr"></td>
				  <td class="wizCnr"></td>
				</tr>
				<tr>
				  <td class="wizCnr" colspan="2"><img src="<%=SPACER%>"/></td>
				  <td class="wizBdr" colspan="2"></td>
				  <td class="wizCnr" colspan="2"></td>
				</tr>
			  </table>
			</td>
		  </tr>
		</table>
		<%
			//
		  }

		%>&nbsp;
	  </div>
	</td>
	<td colspan="3" width="100%" valign="top">
	  <table>
		<tr>
		  <td><%

			for (int i = 0; i < steps.size(); i++) {
			  NmWizardStep step = (NmWizardStep) steps.get(i);
			  wizardBean.setThisStep(i);
			  boolean backingupParameters = (i != new_step);
			  wizardBean.setBackingupParameters(backingupParameters);

			  // pages can access a model if it exists
			  //System.out.println(step.getUrl());
		  %>
			<jsp:include page="<%=step.getUrl()%>" flush="true"/>
			<%
			  }

			  cb.setActionMethod(w.getOnPostRenderMethod());
			  if (cb.readyToExecute()) {
				// Executing method after rendering any pages.
				Object retObj = cb.execute();
				if (retObj instanceof NmModel) {
				  model.setPostRenderModel((NmModel) retObj);
				} else {
				  String cc = request.getParameter("compContext");
				  sessionBean.updateModels(cc, retObj);
				}
			  }

			  modelBean.setModel(null);

			%></td>
		</tr>
	  </table>
	</td>
	<td></td>
  </tr>

  <%
	boolean ok_accessable = wizardBean.isReady() && (parsed == null || parsed.booleanValue());
	boolean apply_visible = w.isApplyVisible();
	boolean cancel_visible = w.isCancelVisible();
	boolean next_prev_visible = (steps.size() > 1);
	boolean next_accessable = (next_step != -1);
	boolean prev_accessable = (prev_step != -1);
  %>

  <tr>
	<td class="wizCnr"><img src="<%=SPACER%>"/></td>
	<td class="wizInCnr"></td>
	<td colspan="4" width="100%"></td>
	<td class="wizInCnr"></td>
	<td class="wizCnr"></td>
  </tr>
  <tr>
	<td class="wizCnr" colspan="2"><img src="<%=SPACER%>"/></td>
	<td class="wizBdr" colspan="4" width="100%"></td>
	<td class="wizCnr" colspan="2"></td>
  </tr>
  <tr>
	<td colspan="3"><img src="<%=SPACER%>" height="1"/></td>
	<td colspan="5" height="1" align="right"></td>
  </tr>
</table>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr align="left">
	<td><%
	  if (wizardBean.isShowRequiredLabel() && request.getAttribute(NmWizard.Attributes.NONE_REQUIRED) == null) {
	%>
	  <font class="requiredfield">*</font> <font
		  class="requiredfieldfont"><%=wizard_rb.getString(utilResource.REQ_FIELDS)%>
	  </font><%
		} %>

	</td>
	<td></td>
  </tr>
  <tr valign="right">

	<td align="right" valign="middle">
	  <% if (request.getParameter("HideButtons") == null || !request.getParameter("HideButtons").equalsIgnoreCase("true")) { %>
	  <table border="0" cellpadding="3" cellspacing="3">
		<tr>
		  <%
			if (steps.size() > 1)
			  w.setOkLabel(WTMessage.getLocalizedMessage(UTIL_RESOURCE, utilResource.FINISH, null, wizard_locale));
			if (next_prev_visible) {
			  if (prev_accessable) {
		  %>
		  <td valign="middle"><font class="wizardbuttonfont"><input type="button" id="PJL_wizard_previous"
																	name="prevButton"
																	value=" <%=HTMLEncoder.encodeForHTMLAttribute(wizard_rb.getString(utilResource.PREV))%>"
																	onClick="gotoStep<%=prev_step%>()"
																	class="wizPrevBtn"/> </font></td>
		  <%
		  } else {
		  %>
		  <td valign="middle"><font class="wizarddisabledbuttonfont"><input type="button" disabled="true"
																			name="prevButton"
																			value=" <%=HTMLEncoder.encodeForHTMLAttribute(wizard_rb.getString(utilResource.PREV))%>"
																			class="wizPrevBtndisabled"/> </font></td>
		  <%
			}

			if (next_accessable) {
		  %>
		  <td valign="middle"><font class="wizardbuttonfont"><input type="button" id="PJL_wizard_next" name="nextButton"
																	value="<%=HTMLEncoder.encodeForHTMLAttribute(wizard_rb.getString(utilResource.NEXT))%>   "
																	onClick="gotoStep<%=next_step%>()"
																	class="wizNxtBtn"/> </font></td>
		  <%
		  } else {
		  %>
		  <td valign="middle"><font class="wizarddisabledbuttonfont"><input type="button" disabled="true"
																			name="nextButton"
																			value="<%=HTMLEncoder.encodeForHTMLAttribute(wizard_rb.getString(utilResource.NEXT))%>   "
																			class="wizNxtBtndisabled"/> </font></td>
		  <%
			  }
			}

			if (ok_accessable) {
		  %>
		  <td valign="middle"><font class="wizardbuttonfont"><input type="submit" id="PJL_wizard_ok" name="okButton"
																	value="<%=HTMLEncoder.encodeForHTMLAttribute(w.getOkLabel())%>"
																	class="wizBtn"/> </font></td>
		  <%
		  } else {
		  %>
		  <td valign="middle"><font class="wizarddisabledbuttonfont"><input type="button" disabled="true"
																			name="nextButton"
																			value="<%=HTMLEncoder.encodeForHTMLAttribute(w.getOkLabel())%>"
																			class="wizBtn"/> </font></td>
		  <%
			}

			if (apply_visible) {
			  if (ok_accessable) {
		  %>
		  <td valign="middle"><font class="wizardbuttonfont"><input type="button" id="PJL_wizard_apply"
																	name="applyButton"
																	value="<%=HTMLEncoder.encodeForHTMLAttribute(w.getApplyLabel())%>"
																	onClick="onApply()" class="wizBtn"/> </font></td>
		  <%
		  } else {
		  %>
		  <td valign="middle"><font class="wizarddisabledbuttonfont"><input type="button" disabled="true"
																			name="nextButton"
																			value="<%=HTMLEncoder.encodeForHTMLAttribute(w.getApplyLabel())%>"
																			class="wizBtn"/> </font></td>
		  <%
			  }
			}

			if (cancel_visible) {
		  %>
		  <td valign="middle"><font class="wizardbuttonfont"><input type="button" id="PJL_wizard_cancel"
																	name="cancelButton"
																	value="<%=HTMLEncoder.encodeForHTMLAttribute(w.getCancelLabel())%>"
																	onClick="closePopup()" class="wizCancelBtn"/>
		  </font></td>
		  <%
			} %>
		</tr>
	  </table>
	  <% } %>
	</td>
  </tr>
</table>


<script language="JavaScript">
    function onSubmit() {
        var status = false;
        var mform = window.document.forms.mainform;
        var wasOkClicked = mform.dialogButton.value == "<%=NmCommandBean.DialogButton.OK%>";
        var wasApplyClicked = mform.dialogButton.value == "<%=NmCommandBean.DialogButton.APPLY%>";
        var wasStepChanged = mform.<%=NmWizard.Parameters.NEW_STEP%>.value != mform.<%=NmWizard.Parameters.OLD_STEP%>.value;
        var wasRefreshedFromPopup = mform.<%=NmWizard.Parameters.REFRESHED_FROM_POPUP%>.value != "<%=NmContextStringTokenizer.NONE%>";
        var wasEnterClicked = !wasOkClicked && !wasApplyClicked && !wasStepChanged && !wasRefreshedFromPopup;
        if (wasEnterClicked || wasOkClicked) {<%
   // need to make sure that we do not change steps or click apply
   // onclick event hadler can not prevent a form from being submited
   if (ok_accessable) {%>
            var error = <%=(w.getOnOkScript() != null) ? (w.getOnOkScript() + "()") : "null"%>;
            if (error != null) {
                popupErrorDialog(error);
                status = false;
            } else {
                prepareToSubmitPopupForm('<%=NmCommandBean.DialogButton.OK%>');
                status = true;
            }
            <%
			   } else { %>
            status = false;
            <%
			  } %>
        } else {
            status = true;
        }

        if (!status) {
            resetPopupForm();
        }

        if (!callUserSubmitFunction())
            status = false;

        if (status) {
            setFormEncTypeIfFile(mform);
            okClicked();
            disableIEButtonsOnSubmit();
        }

        return status;
    }
</script>

<input type="hidden" name="architecture"
	   value="<%=HTMLEncoder.encodeForHTMLAttribute(request.getParameter( "architecture" ))%>"/>
<input type="hidden" name="<%=WizardProcessor.FOLDER_PICKER%>"
	   value="<%=HTMLEncoder.encodeForHTMLAttribute(request.getParameter( WizardProcessor.FOLDER_PICKER ))%>"/>
<input type="hidden" name="<%=WizardProcessor.FOLDER_LOCATION%>"
	   value="<%=HTMLEncoder.encodeForHTMLAttribute(request.getParameter( WizardProcessor.FOLDER_LOCATION ))%>"/>
<%
  }
%>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>