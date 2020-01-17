<%@ taglib uri="http://www.ptc.com/windchill/taglib/mvc" prefix="mvc" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ page import="com.ptc.netmarkets.util.beans.NmHelperBean" %>

<%-- need to initialize the url factory bean so the base href is set correctly by the htmlHeaderRenderer--%>
<% new NmHelperBean(request).getNmURLFactoryBean(); %>
<tags:htmlHeaderRenderer displayTopHtml="true" renderCSSLinks="true"/>

<style type="text/css">
  .frame_outer {
	margin: 0 8px 0px 8px;
  }
</style>
<%--
    Table display code.
--%>
<Div id="tableDiv">

</Div>
<Div id="tableDiv_view">
  <jsp:include page="${mvc:getComponentURL('psb.ec.listVariantSpec')}" flush="true"/>
</Div>
<%--
    Script to register the table into gwt to later retrieve the selected NC on Ok click.
    Since this jsp is displayed under an iframe, gwt code is not able to call script methods
    in this file. Hence calling gwt native methods from scripts here and passing the objects as
    workaround. Ok click and resize events are handled in SelectVariantSpecDialog.java
--%>
<script language="javascript">
    PTC.onReady(function () {
        var c = new PTC.jca.table.TableContainer({
            id: 'vsTableContainer',
            renderTo: 'tableDiv',
            collapsible: false,
            collapsed: false,
            contentEl: 'tableDiv_view',
            height: 510
        });
        var psb_ec_listVariantSpec_ready_handler1 = function () {
            var cont = Ext.getCmp('vsTableContainer');
            var table = PTC.jca.table.Utils.getTable('psb.ec.listVariantSpec');
            if (table != null) {
                // calling gwt native method
                parent.psb_ec_listVariantSpec_setUp(table, cont);
            }
            var toggle = Ext.getCmp('psb.ec.listVariantSpec_toggle');
            if (toggle != null) {
                toggle.hide();
            }
        }();
    });
</script>
