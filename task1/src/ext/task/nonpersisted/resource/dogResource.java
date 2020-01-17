package ext.task.nonpersisted.resource;

import wt.util.resource.RBEntry;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 * @version:2019/11/18
 * @Author:wzp
 * @Date:Created in 14:25 2019/11/18
 * @Description:自定义非持久化资源类
 */
@RBUUID("dogResource")
public class dogResource extends WTListResourceBundle {

    @RBEntry("Dog info Details")
    public static final String DOG_INFO_DETAILS_TAB_DESC = "object.dogInfoDetailsTab.description";


    @RBEntry("Dog Attribute")
    public static final String DOG_INFO_ATTRIBUTES_DESC = "object.dogInfoPageAttributes.description";

    @RBEntry("Dog Attribute")
    public static final String DOG_INFO_ATTRIBUTES_TOOLTIP = "object.dogInfoPageAttributes.tooltip";

//    @RBEntry("materialsButton")
//    public static final String XP_OWN_MATERIALS_BUTTON_TITLE ="xpPro.xpMaterials.title";
//
//    @RBEntry("materialsButton")
//    public static final String XP_OWN_MATERIALS_BUTTON_DESCRIPTION ="xpPro.xpMaterials.description";
//
//    @RBEntry("materialsButton")
//    public static final String XP_OWN_MATERIALS_BUTTON_TOOLTIP ="xpPro.xpMaterials.tooltip";
//
//    @RBEntry("copy24x24.png")
//    public static final String XP_OWN_MATERIALS_BUTTON_ICON ="xpPro.xpMaterials.icon";
//
//    @RBEntry("report")
//    public static final String XP_OWN_REPORT_TITLE ="navigation.reportManagement.title";
//
//    @RBEntry("report")
//    public static final String XP_OWN_REPORT_DESCRIPTION ="navigation.reportManagement.description";
//
//    @RBEntry("report")
//    public static final String XP_OWN_REPORT_TOOLTIP ="navigation.reportManagement.tooltip";
//
//    @RBEntry("copy24x24.png")
//    public static final String XP_OWN_REPORT_ICON ="navigation.reportManagement.icon";
//
//    @RBEntry("reportList")
//    public static final String XP_OWN_REPORTLIST_TITLE ="reportList.reportSubList.title";
//
//    @RBEntry("reportList")
//    public static final String XP_OWN_REPORTLIST_DESCRIPTION ="reportList.reportSubList.description";
//
//    @RBEntry("reportList")
//    public static final String XP_OWN_REPORTLIST_TOOLTIP ="reportList.reportSubList.tooltip";
//
//    @RBEntry("documentTestButton")
//    public static final String XP_OWN_DOCUMENT_TEST_BUTTON_TITLE ="dcButton.documentTestButton.title";
//
//    @RBEntry("documentTestButton")
//    public static final String XP_OWN_DOCUMENT_TEST_BUTTON_DESCRIPTION ="dcButton.documentTestButton.description";
//
//    @RBEntry("documentTestButton")
//    public static final String XP_OWN_DOCUMENT_TEST_BUTTON_TOOLTIP ="dcButton.documentTestButton.tooltip";
//
//    @RBEntry("cdrlsdrl_package_add.gif")
//    public static final String XP_OWN_DOCUMENT_TEST_BUTTON_TOOLTIP_ICON ="dcButton.documentTestButton.icon";
//
//    @RBEntry("documentSonMenu")
//    public static final String XP_OWN_DOCUMENTSONMENU_TITLE ="object.xpSonMenu.title";
//
//    @RBEntry("documentSonMenu")
//    public static final String XP_OWN_DOCUMENTSONMENU_DESCRIPTION ="object.xpSonMenu.description";
//
//    @RBEntry("documentSonMenu")
//    public static final String XP_OWN_DOCUMENTSONMENU_TOOLTIP ="object.xpSonMenu.tooltip";
//
//    @RBEntry("documentSonMenu1")
//    public static final String XP_OWN_DOCUMENTSONMENUDEMO_TITLE ="object.folderbrowser_toolbar_create_submenu.title";
//
//    @RBEntry("documentSonMenu1")
//    public static final String XP_OWN_DOCUMENTSONMENUDEMO_DESCRIPTION ="object.folderbrowser_toolbar_create_submenu.description";
//
//    @RBEntry("documentSonMenu1")
//    public static final String XP_OWN_DOCUMENTSONMENUDEMO_TOOLTIP ="object.folderbrowser_toolbar_create_submenu.tooltip";
//
//    @RBEntry("excelToolbar")
//    public static final String XP_OWN_XPTOOLBAR_TITLE ="xpMake.xpToolbar.title";
//
//    @RBEntry("excelToolbar")
//    public static final String XP_OWN_XPTOOLBAR_DESCRIPTION ="xpMake.xpToolbar.description";
//
//    @RBEntry("excelToolbar")
//    public static final String XP_OWN_XPTOOLBAR_TOOLTIP ="xpMake.xpToolbar.tooltip";
//
//    @RBEntry("cdrlsdrl_package_add.gif")
//    public static final String XP_OWN_XPTOOLBAR_ICON ="xpMake.xpToolbar.icon";
//
//    @RBEntry("rightClickList")
//    public static final String XP_OWN_XPRIGHTCLICKLIST_TITLE ="xpMakeRightClickList.xpRightClickList.title";
//
//    @RBEntry("rightClickList")
//    public static final String XP_OWN_XPRIGHTCLICKLIST_DESCRIPTION ="xpMakeRightClickList.xpRightClickList.description";
//
//    @RBEntry("rightClickList")
//    public static final String XP_OWN_XPRIGHTCLICKLIST_TOOLTIP ="xpMakeRightClickList.xpRightClickList.tooltip";
//
//    @RBEntry("copy24x24.png")
//    public static final String XP_OWN_XPRIGHTCLICKLIST_ICON ="xpMakeRightClickList.xpRightClickList.icon";
//
//    @RBEntry("insertTree")
//    public static final String XP_OWN_INSERT_TREE_BUTTON_TITLE ="xpTree.toolbar_insert_tree.title";
//
//    @RBEntry("insertTree")
//    public static final String XP_OWN_INSERT_TREE_BUTTON_DESCRIPTION ="xpTree.toolbar_insert_tree.description";
//
//    @RBEntry("insertTree")
//    public static final String XP_OWN_INSERT_TREE_BUTTON_TOOLTIP ="xpTree.toolbar_insert_tree.tooltip";
//
//    @RBEntry("qms_24x24.png")
//    public static final String XP_OWN_INSERT_TREE_BUTTON_TOOLTIP_ICON ="xpTree.toolbar_insert_tree.icon";
//
//    @RBEntry("treeToolbar")
//    public static final String XP_OWN_TREE_TOOLBAR_BUTTON_TITLE ="makeByXp.xpTreeToolbar.title";
//
//    @RBEntry("treeToolbar")
//    public static final String XP_OWN_TREE_TOOLBAR_BUTTON_DESCRIPTION ="makeByXp.xpTreeToolbar.description";
//
//    @RBEntry("treeToolbar")
//    public static final String XP_OWN_TREE_TOOLBAR_BUTTON_TOOLTIP ="makeByXp.xpTreeToolbar.tooltip";
//
//    @RBEntry("qms_24x24.png")
//    public static final String XP_OWN_TREE_TOOLBAR_BUTTON_TOOLTIP_ICON ="makeByXp.xpTreeToolbar.icon";
//
//    @RBEntry("treeRightClickList")
//    public static final String XP_OWN_TREE_RIGHT_CLICK_BUTTON_TITLE ="makeRightClickListByXp.xpTreeRightClickList.title";
//
//    @RBEntry("treeRightClickList")
//    public static final String XP_OWN_TREE_RIGHT_CLICK_BUTTON_DESCRIPTION ="makeRightClickListByXp.xpTreeRightClickList.description";
//
//    @RBEntry("treeRightClickList")
//    public static final String XP_OWN_TREE_RIGHT_CLICK_BUTTON_TOOLTIP ="makeRightClickListByXp.xpTreeRightClickList.tooltip";
//
//
//
//    @RBEntry("insertTreeByXml")
//    public static final String XP_OWN_INSERT_TREE_BY_XML_BUTTON_TITLE ="xpTree.toolbar_insert_tree_xml.title";
//
//    @RBEntry("insertTreeByXml")
//    public static final String XP_OWN_INSERT_TREE_BY_XML_BUTTON_DESCRIPTION ="xpTree.toolbar_insert_tree_xml.description";
//
//    @RBEntry("insertTreeByXml")
//    public static final String XP_OWN_INSERT_TREE_BY_XML_BUTTON_TOOLTIP ="xpTree.toolbar_insert_tree_xml.tooltip";
//
//    @RBEntry("failure_fixable_large.png")
//    public static final String XP_OWN_INSERT_TREE_BY_XML_BUTTON_TOOLTIP_ICON ="xpTree.toolbar_insert_tree_xml.icon";
//
//    @RBEntry("insertTreeLevelFull")
//    public static final String XP_OWN_INSERT_TREE_LEVEL_FULL_BUTTON_TITLE ="xpTree.toolbar_insert_tree_level_full.title";
//
//    @RBEntry("insertTreeLevelFull")
//    public static final String XP_OWN_INSERT_TREE_LEVEL_FULL_BUTTON_DESCRIPTION ="xpTree.toolbar_insert_tree_level_full.description";
//
//    @RBEntry("insertTreeLevelFull")
//    public static final String XP_OWN_INSERT_TREE_LEVEL_FULL_BUTTON_TOOLTIP ="xpTree.toolbar_insert_tree_level_full.tooltip";
//
//    @RBEntry("filter_properties_24.png")
//    public static final String XP_OWN_INSERT_TREE_LEVEL_FULL_BUTTON_TOOLTIP_ICON ="xpTree.toolbar_insert_tree_level_full.icon";
}

