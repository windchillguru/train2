<%@ include file="/netmarkets/jsp/util/beginPopup.jspf"
%>
<%@ page import="java.util.ResourceBundle,
				 com.ptc.core.ui.aboutRB,
				 com.ptc.netmarkets.util.NmUtils,
				 java.util.Locale,
				 wt.util.WTMessage,
				 wt.util.InstalledProperties,
				 wt.util.version.*,
				 com.ptc.windchill.instassm.*"
%>
<%!
  private static final String ABOUT_RESOURCE = "com.ptc.core.ui.aboutRB";

  //Gets the title text for the about page that is properly escaped for javascript.
  private static String getTitleForAboutPage(ResourceBundle about_rb, Locale aboutLocale) {
	String title = WTMessage.getLocalizedMessage(ABOUT_RESOURCE, aboutRB.ABOUT_PTC, null, aboutLocale);

	//escape any single quotes so it can be set by a javascript function
	return NmUtils.escapeSingleQuotes(title);
  }
%>
<%
  //declared here because these variables cannot be static
  Locale aboutLocale = localeBean.getLocale();
  ResourceBundle about_rb = ResourceBundle.getBundle(ABOUT_RESOURCE, aboutLocale);
%>
<script language="javascript">
    PTC.navigation.setPageTitle('<%=getTitleForAboutPage(about_rb,aboutLocale)%>');
</script>

<table id="aboutTable" class="aboutPage">
  <tr>
	<td>
	  <table class="productTable">
		<tr>
		  <td><img id="logo" src="netmarkets/images/PTC_logo_about.png"/></td>
			<%
            String base_release_num = WindchillVersion.getInstallationReleaseNumber();
            String base_date_code = WindchillVersion.getInstallationDateCode();
            out.print("<td class=\"products\">");
            out.print("<label>");
            out.print(WTMessage.getLocalizedMessage (ABOUT_RESOURCE, aboutRB.RELEASE, null,aboutLocale));
            out.print("</label>");
            out.print("&nbsp;" + base_release_num + "&nbsp;" + base_date_code);
            out.print("<br/>\n");

            out.print("&nbsp;<br/>\n");

            out.print("<label>");
            out.print(about_rb.getString(aboutRB.INSTALLED_ASSEMBLIES));
            out.print("</label><br/>\n");

            ReleaseId[] release_ids = WindchillVersion.getInstalledAssemblyReleaseIds();
            for(int i=0; i<release_ids.length; i++)
            {
               String rel_id_text = release_ids[i].toString();
               String sequence_text = WindchillVersion.getInstallerSequenceFor(release_ids[i]);
               if (sequence_text == null)
                  sequence_text = "";
               if (sequence_text.length() > 0)
                  sequence_text = "-" + sequence_text;
               rel_id_text = rel_id_text + sequence_text;
               out.print("<span style=\"white-space:nowrap\" title=\"" + release_ids[i].toString() + "\">");
               out.print(WindchillVersion.getDisplayLabelFor(release_ids[i]));
               out.print("</span><br/>\n");
            }
            out.print("&nbsp;<br/>\n");

            out.print("<label>");
            out.print(WTMessage.getLocalizedMessage (ABOUT_RESOURCE, aboutRB.LOCALE, null,aboutLocale)+"</label>&nbsp;");

            InstalledLocale[] ls = WindchillVersion.getInstalledLocales();
            if(ls.length == 0) {
                out.print(WTMessage.getLocalizedMessage (ABOUT_RESOURCE, aboutRB.NO_LOCALE, null,aboutLocale));
            }
            else {
                for(int i=0; i<ls.length; i++) {
                    InstalledLocale il = ls[i];
                    String to_display = il.getName();
                    if(to_display == null || to_display.trim().length() == 0)
                        to_display = il.getLocaleCode();
                    out.print(to_display);
                    if((i+1)<ls.length)
                        out.print(", ");
                }
            }
            out.print("</td></tr>");

      %>
	  </table>
	  <br/>
	</td>
  </tr>
  <tr>
	<td class="copyright">
	  <iframe onload="CopyrightInfo.initHyperlinks(this);"
			  src="<%= about_rb.getString(aboutRB.copyright_info_filepath) %>" id="copyRightFrame"></iframe>
	</td>
  </tr>
  <tr>
	<td>
	  <div class="aboutOK">
		<button name="okButton" class="wizOkBtn"
				onclick="window.close();"><%= WTMessage.getLocalizedMessage(ABOUT_RESOURCE, aboutRB.OKb, null, aboutLocale)%>
		</button>
	  </div>
	</td>
  </tr>
</table>
<script type="text/javascript">
    <!--
    /** putting these functions under a scope of CopyrightInfo to ensure they won't conflict with any functions in other js files */
    var CopyrightInfo = function () {
        return {
            openWindow: function (url, windowName) {
                var newWindow = window.open(url, windowName, 'resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,location=yes,status=yes');
                newWindow.focus();
            },
            initHyperlinks: function (parent) {
                var links = parent.contentWindow.document.getElementsByTagName("a");
                if (links) {
                    var alength = links.length;
                    for (var i = 0; i < alength; i++) {
                        var href = links[i].href;
                        if (href != null && href != "" && href != " ") {
                            links[i].href = "javascript:top.CopyrightInfo.openWindow('" + href + "', 'COPYRIGHT_INFO_WINDOW_" + i + "');"
                        }
                    }
                }
            },
            resizeToAboutTable: function () {
                // this function will have no effect on Chrome
                var h = Ext.getDom('aboutTable').offsetHeight;
                var wh = 0;
                if (window.innerHeight) {
                    wh = window.innerHeight;
                } else {
                    // for Internet Explorer, must adjust a bit for window borders
                    wh = document.documentElement.offsetHeight - 5;
                }
                window.resizeBy(0, h - wh);
            }
        };
    }();

    PTC.onWindowLoad(CopyrightInfo.resizeToAboutTable);

    /**
     *  Resize the iframe to take up any new space when the window is resized
     */
    CopyrightInfo.onResizeIframeListener = function () {

        var cpTD = Ext.get(document.querySelectorAll('.copyright')[0]);
        var okButton = Ext.get(document.querySelectorAll('.aboutOK')[0]);
        var cpFrame = Ext.get('copyRightFrame');

        if (cpTD && okButton && cpFrame) {
            //Subtract out the known elements below the text to keep scrollbars from appearing.
            //Total size minus start position and minus known elements below
            var margins = okButton.getMargins();
            var total = 0;
            if (window.innerHeight) {
                total = window.innerHeight;
            } else if (document.documentElement.offsetHeight) {
                total = document.documentElement.offsetHeight;
            } else {
                total = document.body.scrollHeight;
            }
            var newHeight = total - cpTD.getOffsetsTo(document.body)[1] - okButton.getHeight(false) - margins.top * 2 - margins.bottom * 2;
            // Restrict height of cpFrame if it is getting smaller.
            // Height 80 is sufficiant to display title of copyright note: "Software Copyright".
            newHeight = Math.max(newHeight, 80);
            var oldHeight = cpFrame.getHeight(false);
            cpFrame.setHeight(newHeight);
        }
    };

    PTC.onReady(function () {
        Ext.EventManager.onWindowResize(CopyrightInfo.onResizeIframeListener);
    }, null, {delay: 100, buffer: 100});

    -->
</script>
<%nmcontext.setPortlet(NmContextBean.Portlet.POPPED_UP);%>
<%@ include file="/netmarkets/jsp/util/end.jspf" %>
