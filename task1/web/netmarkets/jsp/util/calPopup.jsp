<%@ include file="/netmarkets/jsp/util/begin.jspf" %>

<script language="JavaScript">
    if (window.opener.myCalendarWindow == null) {
        window.opener.myCalendarWindow = window;
    }
    PTC.onReady(window.opener.writeNoFramesCalendar);
</script>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>
