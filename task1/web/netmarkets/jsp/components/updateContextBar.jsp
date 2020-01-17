<%@ page contentType="text/html; charset=UTF-8" %>
<%response.setContentType("text/html; charset=UTF-8");%>
<%@page import="com.ptc.windchill.enterprise.navigation.renderers.ContextBarRenderer,
				com.ptc.netmarkets.util.beans.NmHelperBean,
				com.ptc.netmarkets.util.beans.NmHelperBeanFactory,
				com.ptc.netmarkets.util.beans.NmCommandBean,
				com.ptc.core.components.beans.NavigationBean,
				com.ptc.windchill.enterprise.navigation.NavigationUtilities,
				com.ptc.core.components.rendering.RenderingContext" %>
<%-->
	Used to dynamically update the context bar when using the folder
	browser.  The setup for this file is made in such a way that there
	is *just enough* information available to populate the context bar.
	It is actually just enough information to populate the left side of
	the context bar.

	Author: Ryan Alberts
<--%>
<%
  //Setup HelperBean which can get most of the information we need
  NmHelperBean helperBean = NmHelperBeanFactory.getInstance().getHelperBean(pageContext);
  helperBean.setRequest(request);
  helperBean.setResponse(response);

  //Get the command bean to be passed into the remote method call
  NmCommandBean commandBean = helperBean.getNmCommandBean();

  ///////////////////////////////////////////
  //Draw
  RenderingContext renderContext = new RenderingContext();
  renderContext.setHelperBean(helperBean);

  //Remote method call to populate the nav bean with context bar information
  NavigationBean navBean = NavigationUtilities.updateContextBarInfo(commandBean);

  ContextBarRenderer cb = new ContextBarRenderer();

  //Render Left Side
  cb.renderLeftSideComponents(navBean, out, renderContext);

  //Render Actions
  cb.renderContextBarActions(navBean, out, renderContext);
%>