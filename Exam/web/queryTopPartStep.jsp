<%@page pageEncoding="UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/netmarkets/jsp/util/begin.jspf" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="ext.exam.part.util.PartStatusUtil ,java.util.*" %>
<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="ext.train.report.resource.reportResource"/>
<fmt:message var="SELECT_FILE" key="SELECT_FILE"/>
<fmt:message var="SELECT_FILE_INVALID_ERR" key="SELECT_FILE_INVALID_ERR"/>
<fmt:message var="SELECT_FILE_NONE_ERR" key="SELECT_FILE_NONE_ERR"/>

<div style="width: auto; height: auto;">
  <br/>
  <br/>
  ${SELECT_FILE}<input type="file" name="file" id="file">
</div>

<script type="text/javascript" language="javascript">
    function validateFileName() {
        let fileName = document.getElementById("file").value;
        if (fileName.trim().length == 0) {
            alert("${SELECT_FILE_NONE_ERR}");
            return false;
        }
        if (!fileName.toLowerCase().endsWith(".xls") && !fileName.toLowerCase().endsWith(".xlsx") && !fileName.toLowerCase().endsWith(".txt")) {
            alert("${SELECT_FILE_INVALID_ERR}");
            return false;
        }
        return true;
    }

</script>

<%@ include file="/netmarkets/jsp/util/end.jspf" %>