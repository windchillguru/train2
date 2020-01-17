<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>

<jsp:useBean id="commandBean" class="com.ptc.netmarkets.util.beans.NmCommandBean" scope="request"/>
<jsp:useBean id="localeBean" class="com.ptc.netmarkets.util.beans.NmLocaleBean" scope="request"/>
<jsp:useBean id="urlFactoryBean" class="com.ptc.netmarkets.util.beans.NmURLFactoryBean" scope="request"/>

<jsp:include page="${mvc:getComponentURL('receiveDelivery.table')}" flush="true"/>

<div id="inaccessible_warning_msg_label" name="inaccessible_warning_msg_label"></div>

<script language="JavaScript">

    PTC.rd = {};

    PTC.rd.setWarningMessage = function () {
        var url = "<%=urlFactoryBean.getFullyQualifiedHREF("netmarkets/jsp/rd/receivedDeliveryAccessWarning.jsp")%>";
        var containerRef = "<%=commandBean.getContainerRef()%>";
        var locale = "<%=localeBean.getLocale()%>";
        var paramString = "container=" + containerRef + "&locale=" + locale;
        var options = {
            asynchronous: true,
            parameters: paramString,
            onSuccess: function (req) {
                document.getElementById("inaccessible_warning_msg_label").innerHTML = req.responseText;
            }
        };
        requestHandler.doRequest(url, options);
    }

    PTC.rd.setWarningMessage();
</script>
