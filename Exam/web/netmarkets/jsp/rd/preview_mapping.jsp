<%
  String calledFrom = "rightClick";
%>

<%@ include file="/netmarkets/jsp/rd/initializePreviewMapping.jspf" %>

<script type="text/javascript">
    var tl_tableID = "previewMappingTable";
    var context = "<%= request.getAttribute("context") %>";
    var disableButton = "<%= request.getAttribute("disableButton") %>";
    var rdOID = "<%=request.getAttribute("rdOID") %>";
    var logs = escape("<%=request.getAttribute("finalLogBuffer")%>");
    var extendedLogs = escape("<%=request.getAttribute("extendedLogBuffer")%>");
    var calledFrom = 'rightClick';
    var soid = '<%=request.getAttribute("soid")%>';
    var windefineMapping;
    var selectContainer = "<%=request.getAttribute("needContextMapping")%>";

    PTC.onReady(function () {
        PTC.wizard.loadMask = new Ext.LoadMask(Ext.getBody(), {msg: bundleHandler.get('com.ptc.windchill.rd.receivedDeliveryResource.PREVIEW_IN_PROGRESS')});
        PTC.wizard.loadMask.show();
        new createPreviewMappingPanel(tl_tableID, context, rdOID, soid, disableButton, logs, extendedLogs, calledFrom, windefineMapping, selectContainer);
        PTC.wizard.loadMask.hide();
    }); // END of PTC ONREADY
</script>


