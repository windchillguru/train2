<%@ include file="/netmarkets/jsp/util/beginShell.jspf"
%>
<%@page import="wt.util.HTMLEncoder"
%>
<%@page import="com.ptc.core.components.chart.chartResource"
%>
<%@page import="java.util.ResourceBundle"
%>
<%@page import="com.ptc.netmarkets.util.beans.HTTPRequestData" %>

<%
  String index = HTMLEncoder.encodeForJavascript(request.getParameter("thisChartIndex"));
  String chartIndex = HTMLEncoder.encodeForJavascript(request.getParameter("chartIndex" + index));
  String tableId = null; // no table in print preview
  String chartDivId = "printChartDiv";
  String chartType = HTMLEncoder.encodeForJavascript(request.getParameter("chartType" + index));
  String chartTitle = HTMLEncoder.encodeForJavascript(request.getParameter("chartTitle" + index));
  String showYAxisGrid = HTMLEncoder.encodeForJavascript(request.getParameter("showYAxisGrid" + index));
  String showXAxisGrid = HTMLEncoder.encodeForJavascript(request.getParameter("showXAxisGrid" + index));
  String horizontalAxisLabels = HTMLEncoder.encodeForJavascript(request.getParameter("horizontalAxisLabels" + index));
  String showBackground = HTMLEncoder.encodeForJavascript(request.getParameter("showBackground" + index));
  String showPieAsDonut = HTMLEncoder.encodeForJavascript(request.getParameter("showPieAsDonut" + index));
  String showLegend = HTMLEncoder.encodeForJavascript(request.getParameter("showLegend" + index));
  String showDataLabels = HTMLEncoder.encodeForJavascript(request.getParameter("showDataLabels" + index));
  String showRegressionLine = HTMLEncoder.encodeForJavascript(request.getParameter("showRegressionLine" + index));
  String showline = HTMLEncoder.encodeForJavascript(request.getParameter("showline" + index));
  String showTooltips = HTMLEncoder.encodeForJavascript(request.getParameter("showTooltips" + index));
  String showChartInline = "true";
  String xField = HTMLEncoder.encodeForJavascript(request.getParameter("xField" + index));
  String xFieldLabel = HTMLEncoder.encodeForJavascript(request.getParameter("xFieldLabel" + index));
  String xFieldDataType = HTMLEncoder.encodeForJavascript(request.getParameter("xFieldDataType" + index));
  String yField = HTMLEncoder.encodeForJavascript(request.getParameter("yField" + index));
  String yFieldLabel = HTMLEncoder.encodeForJavascript(request.getParameter("yFieldLabel" + index));
  String yFieldDataType = HTMLEncoder.encodeForJavascript(request.getParameter("yFieldDataType" + index));
  String colorPalette = HTMLEncoder.encodeForJavascript(request.getParameter("colorPaletteValue" + index)); // combo box
  String fontSize = HTMLEncoder.encodeForJavascript(request.getParameter("fontSizeValue" + index)); // combo box
  String displayValueCount = HTMLEncoder.encodeForJavascript(request.getParameter("displayValueCountValue" + index)); // combo box
  String chartData = HTMLEncoder.encodeForJavascript(request.getParameter("chartData" + index));
  ResourceBundle chartResourceRB = ResourceBundle.getBundle(chartResource.class.getName());
  String windowTitle = HTMLEncoder.encodeForHTMLContent(chartResourceRB.getString(chartResource.PRINT_CHART_WINDOW_NAME));
  if (chartTitle.length() > 1) {
	windowTitle = windowTitle + " - " + chartTitle;
  } else {
	chartTitle = " ";
  }
%>

<html>
<body onload="window.resizeTo(400,650)">
<div id="printChartDivinline"></div>
<script language="javascript">
    document.title = '<%=windowTitle%>';
    var chartConfig = {
        chartIndex: '<%=chartIndex%>',
        tableId: '<%=tableId%>',
        chartDivId: '<%=chartDivId%>',
        chartType: '<%=chartType%>',
        chartTitle: '<%=chartTitle%>',
        showYAxisGrid: getCheckboxValue('<%=showYAxisGrid%>'),
        showXAxisGrid: getCheckboxValue('<%=showXAxisGrid%>'),
        horizontalAxisLabels: getCheckboxValue('<%=horizontalAxisLabels%>'),
        showBackground: getCheckboxValue('<%=showBackground%>'),
        showPieAsDonut: getCheckboxValue('<%=showPieAsDonut%>'),
        showLegend: getCheckboxValue('<%=showLegend%>'),
        showDataLabels: getCheckboxValue('<%=showDataLabels%>'),
        showRegressionLine: getCheckboxValue('<%=showRegressionLine%>'),
        showLine: getCheckboxValue('<%=showline%>'),
        showChartInline: 'true',
        showTooltips: getCheckboxValue('<%=showTooltips%>'),
        xField: '<%=xField%>',
        xFieldLabel: '<%=xFieldLabel%>',
        xFieldDataType: '<%=xFieldDataType%>',
        yField: '<%=yField%>',
        yFieldLabel: '<%=yFieldLabel%>',
        yFieldDataType: '<%=yFieldDataType%>',
        colorPalette: '<%=colorPalette%>',
        fontSize: '<%=fontSize%>',
        displayValueCount: '<%=displayValueCount%>',
        chartData: '<%=chartData%>',
        windowControlsOff: true
    };
    PTC.charting.addChart(chartConfig);

    function getCheckboxValue(value) {
        if (value) {
            if (value === 'on' || value === 'true') {
                return true;
            } else {
                return false;
            }
        }
        return false;
    };
</script>
</body>
</html>
