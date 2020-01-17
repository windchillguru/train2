<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/core" prefix="wc" %>

<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="ext.task2.part.resource.partRB"/>
<fmt:message var="TASK_EXPORTBOM_09" key="TASK_EXPORTBOM_09"/>
<fmt:message var="SELECT_FILE_NONE_ERR" key="SELECT_FILE_NONE_ERR"/>
<fmt:message var="SELECT_FILE_INVALID_ERR" key="SELECT_FILE_INVALID_ERR"/>

<div>
  <br/><br/>
  ${TASK_EXPORTBOM_09}：<input type="file" name="file" id="file"/>
  <br/><br/>
</div>
<script type="text/javascript" language="javascript">

    function validateFileName() {

        var filename = document.getElementById("file").value;
        //alert("filename:"+filename);
        if (filename.trim().length == 0) {
            alert("${SELECT_FILE_NONE_ERR}");
            return false;
        }

        if (!filename.toLowerCase().endsWith(".xlsx")) {
            alert("${SELECT_FILE_INVALID_ERR}");
            return false;

        }
        return true;

    }

</script>