<%@ page errorPage="/netmarkets/jsp/util/error.jsp" %>

<%@ page import="com.ptc.netmarkets.wp.wpResource" %>

<%@ taglib prefix="jca" uri="http://www.ptc.com/windchill/taglib/components" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>

<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="com.ptc.netmarkets.wp.wpResource"/>
<fmt:message var="lock_msg" key="<%= wpResource.LOCK_PACKAGE_MSG %>"/>

<script language="JavaScript">
    PTC.navigation.loadScript('netmarkets/javascript/wp/workpackage.js');
</script>

<jca:wizard buttonList="NoStepsWizardButtons" helpSelectorKey="PackageFreeze">
  <jca:wizardStep action="lock_step" type="wp" embeddedHelp="${lock_msg}"/>
</jca:wizard>
<script type="text/javascript">
    setUserSubmitFunction(function () {
        return wpValidateFormSubmission('lock');
    });
</script>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>

