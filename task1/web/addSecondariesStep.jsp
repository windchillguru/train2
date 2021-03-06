<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="wca" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf" %>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf" %>

<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="ext.task.doc.resource.docAPIResource"/>

<fmt:message var="SELECT_FILE" key="SELECT_FILE"/>
<fmt:message var="SELECT_FILE_NONE_ERR" key="SELECT_FILE_NONE_ERR"/>


<div>
  <br/>
  ${SELECT_FILE}:<input type="file" name="secondFiles" id="file" multiple="multiple"/>
</div>

<script type="text/javascript" language="JavaScript">
    function validateFileName() {
        var fileName = document.getElementById("file").value;
        if (fileName.trim().length == 0) {
            alert("${SELECT_FILE_NONE_ERR}");
            return false;
        }
        return true;
    }

    <%@ include file="/netmarkets/jsp/util/end.jspf"%>

