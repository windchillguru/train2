<%@ page
	import="com.ptc.netmarkets.model.NmOid,com.ptc.netmarkets.util.beans.NmCommandBean,com.ptc.windchill.rd.receivedDeliveryResource,com.ptc.windchill.rd.ReceivedDelivery,wt.fc.ObjectReference,com.ptc.windchill.rd.service.ReceivedDeliveryHelper,com.ptc.windchill.rd.ReceivedDeliveryImportState,wt.util.WTException,com.ptc.windchill.rd.ReceivedDelivery" %>
<%
  String isExtendedPreviewParam = (String) request.getParameter("isExtendedPreview");
  boolean isExtendedPreview = false;
  if (isExtendedPreviewParam.equalsIgnoreCase("true")) {
	isExtendedPreview = true;
  }
  String selectContainer = (String) request.getParameter("selectContainer");
  int selectedIndex = Integer.parseInt(selectContainer);
  boolean needMapping = false;
  if (selectedIndex == 0) {
	needMapping = true;
  }
  String calledFrom = (String) request.getParameter("From");
  String rdOID = (String) request.getParameter("rdOID");
  ReceivedDelivery rd = ReceivedDeliveryHelper.getReceivedDeliveryObject(rdOID);
  if (rd != null) {
	boolean isStatusInProgress = ReceivedDeliveryHelper.checkIfRDStatusInvalidForPreview(rd);
	if (!isStatusInProgress) {
	  String contents = String.valueOf(ReceivedDeliveryHelper.callPreviewMapping(rd, needMapping, isExtendedPreview));
	  contents = contents.replaceAll("\\\\n", "\n").replaceAll("\\\\t", "\t");
	  out.print(contents);
	} else {
	  out.println("StatusException");
	}
  }
%>
