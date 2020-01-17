<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ page
	import="com.ptc.windchill.rd.receivedDeliveryResource,com.ptc.windchill.rd.service.ReceivedDeliveryHelper,wt.util.WTMessage,wt.fc.ReferenceFactory,wt.fc.WTReference" %>

<%
  String inaccessibleMembersMessage = null;
  String resourceBundle = receivedDeliveryResource.class.getName();
  WTReference wtRef = new ReferenceFactory().getReference(request.getParameter("container"));
  if (!ReceivedDeliveryHelper.service.hasAccessToAllReceivedDeliveries(wtRef)) {
	inaccessibleMembersMessage = WTMessage.getLocalizedMessage(resourceBundle,
		receivedDeliveryResource.RD_INACCESSIBLE_MESSAGE, null,
		new java.util.Locale(request.getParameter("locale")));
  }
  request.setAttribute("inaccessibleMembersMessage", inaccessibleMembersMessage);
%>
<c:choose>
  <c:when test="${inaccessibleMembersMessage!=null}">
	<table>
	  <tr>
		<td><img src="com/ptc/core/htmlcomp/images/warning.gif"/></td>
		<td><w:label id="no_access_msg_label"
					 name="no_access_msg_label" required="false"
					 value="${inaccessibleMembersMessage}"/></td>
	  </tr>
	</table>
	<br>
  </c:when>
</c:choose>
