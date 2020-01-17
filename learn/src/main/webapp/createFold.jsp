<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w" %>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt"%>
<%@ include file="/netmarkets/jsp/util/begin.jspf"%>
<%@page import="ext.task.doc.util.*" %>

<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="ext.task.folder.resource.createFolderResource"/>

<fmt:message var="FOLDER_NAME" key="FOLDER_NAME"/>
<%
    String name = CommandBeanUtil.getDocName(commandBean);
%>

<!-- w:textBox 标签为文本框 -->
<div style="margin:5%">

    ${FOLDER_NAME}: <w:textBox propertyLabel="" id="folderName" name="folderName" value="${name}"
                               size="30" styleClass="required" required="true"  maxlength="200">
</div>

<script type="text/javascript" language="javascript">

    //页面初始化时
    Ext.onReady(function() {
    });

</script>


<%@ include file="/netmarkets/jsp/util/end.jspf"%>