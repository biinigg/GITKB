/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2016-01-13 07:48:39 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.FuncViews.Configs;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class IconConfig_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/FuncViews/Configs/./../../dcitag/dcitag.tld", Long.valueOf(1452650348276L));
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=9\">\r\n");
      out.write("<title>");
      if (_jspx_meth_dcitag_005freqParam_005f0(_jspx_page_context))
        return;
      out.write("</title>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" charset=\"UTF-8\">\r\n");
      out.write("\tExt.onReady(function() {\r\n");
      out.write("\t\tvar localKeys = [ \"save_msg\", \"delete_msg\", \"delete_fail\", \"delete_success\", \"delete_result_title\", \"delete_fail\", \"save_fail\", \"save_success\", \"save_result_title\",\r\n");
      out.write("\t\t\t\t\"save_fail\", \"icon_id\", \"icon_name\", \"icon_map_key\", \"icon_path\", \"icon_type\", \"system_build\", \"upload_icon\", \"delete_confirm_title\", \"delete_confirm_msg\",\r\n");
      out.write("\t\t\t\t\"toolbar_query_title\", \"confirm_title\", \"data_lose_warning\", \"system_def_can_not_delete\", \"cht\", \"chs\", \"cus_format\" ];\r\n");
      out.write("\t\tvar keys = localKeys.concat(globeKeys);\r\n");
      out.write("\t\tvar uid = '");
      if (_jspx_meth_dcitag_005freqParam_005f1(_jspx_page_context))
        return;
      out.write("';\r\n");
      out.write("\t\tthis.dcistore.setMultiLangKeys(keys);\r\n");
      out.write("\t\tthis.dcistore.setUid(uid);\r\n");
      out.write("\t\tthis.dcistore.load(function(records) {\r\n");
      out.write("\t\t\tif (records != null && records.length == 1) {\r\n");
      out.write("\t\t\t\tvar funclangs = buildMultiLangObjct(keys, records[0].get('langValues'));\r\n");
      out.write("\t\t\t\tshowPage(records[0].get('dcitagValue'), funclangs, '");
      if (_jspx_meth_dcitag_005freqParam_005f2(_jspx_page_context))
        return;
      out.write("', uid, records[0].get('langType'));\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\tfunction showPage(postvalue, langs, recvAuth, uid, langtp) {\r\n");
      out.write("\t\t\tvar canEdit = recvAuth == \"1\";\r\n");
      out.write("\t\t\tvar iconpath = \"./../../ImageLoader.dsc?iconid=\";\r\n");
      out.write("\r\n");
      out.write("\t\t\tvar privateImage = Ext.create('DCI.Customer.Img', {\r\n");
      out.write("\t\t\t\tx : 500,\r\n");
      out.write("\t\t\t\ty : 50\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\tvar nameTextBox = Ext.create('DCI.Customer.TextField', {\r\n");
      out.write("\t\t\t\tfieldLabel : langs.icon_name,\r\n");
      out.write("\t\t\t\temptyText : '',\r\n");
      out.write("\t\t\t\tname : 'icon_show_name',\r\n");
      out.write("\t\t\t\tallowBlank : false,\r\n");
      out.write("\t\t\t\tmaxLength : 70,\r\n");
      out.write("\t\t\t\tdefaultvalue : '',\r\n");
      out.write("\t\t\t\tcanEdit : false,\r\n");
      out.write("\t\t\t\tx : 250,\r\n");
      out.write("\t\t\t\ty : 10\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\tvar langOpenWinBtn = Ext.create('DCI.Customer.EmptyButton', {\r\n");
      out.write("\t\t\t\tx : 485,\r\n");
      out.write("\t\t\t\ty : 5,\r\n");
      out.write("\t\t\t\tcls : 'search-toolbar',\r\n");
      out.write("\t\t\t\twidth : 30,\r\n");
      out.write("\t\t\t\theight : 30,\r\n");
      out.write("\t\t\t\ttargetComp : nameTextBox,\r\n");
      out.write("\t\t\t\tvalueComp : null,\r\n");
      out.write("\t\t\t\tnames : [],\r\n");
      out.write("\t\t\t\thandler : function() {\r\n");
      out.write("\t\t\t\t\tvar me = this;\r\n");
      out.write("\t\t\t\t\tvar gStore = Ext.create('Ext.data.Store', {\r\n");
      out.write("\t\t\t\t\t\tautoLoad : false,\r\n");
      out.write("\t\t\t\t\t\tfields : [ 'chs_name', 'cht_name' ]\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tvar win = Ext.create('Ext.window.Window', {\r\n");
      out.write("\t\t\t\t\t\ttitle : langs.toolbar_query_title,\r\n");
      out.write("\t\t\t\t\t\twidth : 515,\r\n");
      out.write("\t\t\t\t\t\theight : 200,\r\n");
      out.write("\t\t\t\t\t\tminWidth : 515,\r\n");
      out.write("\t\t\t\t\t\tminHeight : 200,\r\n");
      out.write("\t\t\t\t\t\tlayout : 'fit',\r\n");
      out.write("\t\t\t\t\t\tplain : true,\r\n");
      out.write("\t\t\t\t\t\tmodal : true,\r\n");
      out.write("\t\t\t\t\t\titems : [ {\r\n");
      out.write("\t\t\t\t\t\t\txtype : 'grid',\r\n");
      out.write("\t\t\t\t\t\t\trenderer : \"component\",\r\n");
      out.write("\t\t\t\t\t\t\tstripeRows : true,\r\n");
      out.write("\t\t\t\t\t\t\tautoScroll : true,\r\n");
      out.write("\t\t\t\t\t\t\tloadMask : true,\r\n");
      out.write("\t\t\t\t\t\t\tenableTextSelection : true,\r\n");
      out.write("\t\t\t\t\t\t\tviewConfig : {\r\n");
      out.write("\t\t\t\t\t\t\t\tforceFit : false,\r\n");
      out.write("\t\t\t\t\t\t\t\tautoLoad : false\r\n");
      out.write("\t\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\t\tcolumns : [ {\r\n");
      out.write("\t\t\t\t\t\t\t\ttext : langs.cht,\r\n");
      out.write("\t\t\t\t\t\t\t\tdataIndex : 'cht_name',\r\n");
      out.write("\t\t\t\t\t\t\t\twidth : 250,\r\n");
      out.write("\t\t\t\t\t\t\t\teditor : {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tallowBlank : true\r\n");
      out.write("\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t}, {\r\n");
      out.write("\t\t\t\t\t\t\t\ttext : langs.chs,\r\n");
      out.write("\t\t\t\t\t\t\t\tdataIndex : 'chs_name',\r\n");
      out.write("\t\t\t\t\t\t\t\twidth : 250,\r\n");
      out.write("\t\t\t\t\t\t\t\teditor : {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tallowBlank : true\r\n");
      out.write("\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t} ],\r\n");
      out.write("\t\t\t\t\t\t\tstore : gStore,\r\n");
      out.write("\t\t\t\t\t\t\tplugins : [ Ext.create('Ext.grid.plugin.CellEditing', {\r\n");
      out.write("\t\t\t\t\t\t\t\tclicksToEdit : 1\r\n");
      out.write("\t\t\t\t\t\t\t}) ]\r\n");
      out.write("\t\t\t\t\t\t} ],\r\n");
      out.write("\t\t\t\t\t\tbuttons : [ {\r\n");
      out.write("\t\t\t\t\t\t\ttext : langs.ok,\r\n");
      out.write("\t\t\t\t\t\t\thandler : function() {\r\n");
      out.write("\t\t\t\t\t\t\t\tif (this.up('.window').items.get(0).plugins[0] != null) {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tthis.up('.window').items.get(0).plugins[0].completeEdit();\r\n");
      out.write("\t\t\t\t\t\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\tif (gStore.getCount() >= 1) {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tme.names = [ gStore.getAt(0).get(\"chs_name\"), gStore.getAt(0).get(\"cht_name\") ];\r\n");
      out.write("\t\t\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tme.names = [ '', '' ];\r\n");
      out.write("\t\t\t\t\t\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\tif (langtp == 'CHT') {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tme.targetComp.setValue(me.names[1]);\r\n");
      out.write("\t\t\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tme.targetComp.setValue(me.names[0]);\r\n");
      out.write("\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t\tif (me.valueComp != null) {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tme.valueComp.setSelectedValue(gStore.getAt(0).get(\"chs_name\") + \"$\" + gStore.getAt(0).get(\"cht_name\"));\r\n");
      out.write("\t\t\t\t\t\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\tthis.up('.window').close();\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t}, {\r\n");
      out.write("\t\t\t\t\t\t\ttext : langs.cancel,\r\n");
      out.write("\t\t\t\t\t\t\thandler : function() {\r\n");
      out.write("\t\t\t\t\t\t\t\tthis.up('.window').close();\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t} ]\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tif (me.names.length == 2) {\r\n");
      out.write("\t\t\t\t\t\tgStore.add({\r\n");
      out.write("\t\t\t\t\t\t\tchs_name : me.names[0],\r\n");
      out.write("\t\t\t\t\t\t\tcht_name : me.names[1]\r\n");
      out.write("\t\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\tgStore.add({\r\n");
      out.write("\t\t\t\t\t\t\tchs_name : '',\r\n");
      out.write("\t\t\t\t\t\t\tcht_name : ''\r\n");
      out.write("\t\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\twin.show();\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t},\r\n");
      out.write("\t\t\t\tsetReadOnly : function(readonly) {\r\n");
      out.write("\t\t\t\t\tthis.setDisabled(readonly);\r\n");
      out.write("\t\t\t\t},\r\n");
      out.write("\t\t\t\tloadDefault : function() {\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t},\r\n");
      out.write("\t\t\t\tsetInit : function(comp) {\r\n");
      out.write("\t\t\t\t\tthis.valueComp = comp;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\tvar iconNames = Ext.create('DCI.Customer.Hidden', {\r\n");
      out.write("\t\t\t\tname : 'icon_names',\r\n");
      out.write("\t\t\t\tvalue : '',\r\n");
      out.write("\t\t\t\tdefaultvalue : '',\r\n");
      out.write("\t\t\t\ttriggerModi : false,\r\n");
      out.write("\t\t\t\tsetSelectedValue : function(value) {\r\n");
      out.write("\t\t\t\t\tthis.triggerModi = true;\r\n");
      out.write("\t\t\t\t\tthis.setValue(value);\r\n");
      out.write("\t\t\t\t\tthis.triggerModi = false;\r\n");
      out.write("\t\t\t\t},\r\n");
      out.write("\t\t\t\tlisteners : {\r\n");
      out.write("\t\t\t\t\tchange : function(comp, newValue, oldValue, eOpts) {\r\n");
      out.write("\t\t\t\t\t\tif (!this.triggerModi) {\r\n");
      out.write("\t\t\t\t\t\t\tif (langOpenWinBtn != null) {\r\n");
      out.write("\t\t\t\t\t\t\t\tif (newValue != null && newValue.indexOf('$') != -1) {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tlangOpenWinBtn.names = newValue.split('$');\r\n");
      out.write("\t\t\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tlangOpenWinBtn.names = [ '', '' ];\r\n");
      out.write("\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\t\tlangOpenWinBtn.names = [ '', '' ];\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\tlangOpenWinBtn.setInit(iconNames);\r\n");
      out.write("\t\t\tvar headform = Ext.create('DCI.Customer.HeadFormPanel', {\r\n");
      out.write("\t\t\t\theight : 100,\r\n");
      out.write("\t\t\t\turl : '../../Configs/IconConfig.dsc',\r\n");
      out.write("\t\t\t\tlanguageObj : langs,\r\n");
      out.write("\t\t\t\titems : [ Ext.create('DCI.Customer.TextField', {\r\n");
      out.write("\t\t\t\t\tfieldLabel : langs.icon_id,\r\n");
      out.write("\t\t\t\t\temptyText : '',\r\n");
      out.write("\t\t\t\t\tname : 'icon_id',\r\n");
      out.write("\t\t\t\t\tallowBlank : false,\r\n");
      out.write("\t\t\t\t\tispk : true,\r\n");
      out.write("\t\t\t\t\tcanEdit : false,\r\n");
      out.write("\t\t\t\t\tdefaultvalue : langs.system_build,\r\n");
      out.write("\t\t\t\t\tx : 0,\r\n");
      out.write("\t\t\t\t\ty : 10\r\n");
      out.write("\t\t\t\t}), nameTextBox, Ext.create('DCI.Customer.TextField', {\r\n");
      out.write("\t\t\t\t\tfieldLabel : langs.icon_map_key,\r\n");
      out.write("\t\t\t\t\temptyText : '',\r\n");
      out.write("\t\t\t\t\tname : 'icon_map_key',\r\n");
      out.write("\t\t\t\t\tmaxLength : 5,\r\n");
      out.write("\t\t\t\t\tallowBlank : false,\r\n");
      out.write("\t\t\t\t\tcanEdit : false,\r\n");
      out.write("\t\t\t\t\tdefaultvalue : langs.system_build,\r\n");
      out.write("\t\t\t\t\tx : 530,\r\n");
      out.write("\t\t\t\t\ty : 10\r\n");
      out.write("\t\t\t\t}), Ext.create('DCI.Customer.FileField', {\r\n");
      out.write("\t\t\t\t\tfieldLabel : langs.upload_icon,\r\n");
      out.write("\t\t\t\t\tname : 'file',\r\n");
      out.write("\t\t\t\t\twidth : 490,\r\n");
      out.write("\t\t\t\t\tcanEdit : true,\r\n");
      out.write("\t\t\t\t\tispk : true,\r\n");
      out.write("\t\t\t\t\treadOnly : true,\r\n");
      out.write("\t\t\t\t\tallowBlank : false,\r\n");
      out.write("\t\t\t\t\tx : 0,\r\n");
      out.write("\t\t\t\t\ty : 70\r\n");
      out.write("\t\t\t\t}), privateImage, {\r\n");
      out.write("\t\t\t\t\txtype : 'hiddenfield',\r\n");
      out.write("\t\t\t\t\tname : 'icon_path',\r\n");
      out.write("\t\t\t\t}, Ext.create('DCI.Customer.Hidden', {\r\n");
      out.write("\t\t\t\t\tname : 'icon_type',\r\n");
      out.write("\t\t\t\t\tvalue : '',\r\n");
      out.write("\t\t\t\t\tdefaultvalue : 'CUS'\r\n");
      out.write("\t\t\t\t}), Ext.create('DCI.Customer.Hidden', {\r\n");
      out.write("\t\t\t\t\tname : 'icon_name',\r\n");
      out.write("\t\t\t\t\tvalue : '',\r\n");
      out.write("\t\t\t\t\tdefaultvalue : ''\r\n");
      out.write("\t\t\t\t}), langOpenWinBtn, iconNames ],\r\n");
      out.write("\t\t\t\tcusValid : function(fn) {\r\n");
      out.write("\t\t\t\t\tvar me = this;\r\n");
      out.write("\t\t\t\t\tvar field_name = me.items.get(2);\r\n");
      out.write("\t\t\t\t\tvar icon_id = me.items.get(0).getValue();\r\n");
      out.write("\t\t\t\t\tvar value = field_name.getValue();\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tExt.Ajax.request({\r\n");
      out.write("\t\t\t\t\t\tmethod : 'POST',\r\n");
      out.write("\t\t\t\t\t\turl : '../../Configs/IconConfig.dsc',\r\n");
      out.write("\t\t\t\t\t\tparams : {\r\n");
      out.write("\t\t\t\t\t\t\tDCITag : postvalue,\r\n");
      out.write("\t\t\t\t\t\t\taction : 'mapkeyCheck',\r\n");
      out.write("\t\t\t\t\t\t\ticon_id : icon_id,\r\n");
      out.write("\t\t\t\t\t\t\tvalue : value\r\n");
      out.write("\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\tsuccess : function(a) {\r\n");
      out.write("\t\t\t\t\t\t\tme.setLoading(false);\r\n");
      out.write("\t\t\t\t\t\t\tvar result = Ext.JSON.decode(a.responseText);\r\n");
      out.write("\t\t\t\t\t\t\tif (result.success) {\r\n");
      out.write("\t\t\t\t\t\t\t\tfn();\r\n");
      out.write("\t\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\t\tfield_name.markInvalid(result.msg);\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\tfailure : function(f, action) {\r\n");
      out.write("\t\t\t\t\t\t\tme.setLoading(false);\r\n");
      out.write("\t\t\t\t\t\t\tfield_name.markInvalid(langs.system_error);\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t},\r\n");
      out.write("\t\t\t\tshowQueryWindow : function() {\r\n");
      out.write("\t\t\t\t\tvar me = this;\r\n");
      out.write("\t\t\t\t\tme.initQueryWindow();\r\n");
      out.write("\t\t\t\t\tvar pageSize = 10;\r\n");
      out.write("\t\t\t\t\tvar initStore = Ext.create('Ext.data.Store', {\r\n");
      out.write("\t\t\t\t\t\tautoLoad : false,\r\n");
      out.write("\t\t\t\t\t\tfields : [ 'cols' ],\r\n");
      out.write("\t\t\t\t\t\tproxy : {\r\n");
      out.write("\t\t\t\t\t\t\ttype : 'ajax',\r\n");
      out.write("\t\t\t\t\t\t\turl : '../../Configs/IconConfig.dsc',\r\n");
      out.write("\t\t\t\t\t\t\tactionMethods : {\r\n");
      out.write("\t\t\t\t\t\t\t\tread : 'POST'\r\n");
      out.write("\t\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\t\treader : {\r\n");
      out.write("\t\t\t\t\t\t\t\ttype : 'json'\r\n");
      out.write("\t\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\t\textraParams : {\r\n");
      out.write("\t\t\t\t\t\t\t\tDCITag : postvalue,\r\n");
      out.write("\t\t\t\t\t\t\t\tuid : uid\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tvar gstore = Ext.create('Ext.data.Store', {\r\n");
      out.write("\t\t\t\t\t\tautoLoad : false,\r\n");
      out.write("\t\t\t\t\t\tfields : [ \"icon_id\", \"icon_name\", \"icon_show_name\", \"icon_map_key\", \"icon_path\", \"icon_type\", \"icon_id_show\", \"icon_names\" ],\r\n");
      out.write("\t\t\t\t\t\tproxy : {\r\n");
      out.write("\t\t\t\t\t\t\ttype : 'ajax',\r\n");
      out.write("\t\t\t\t\t\t\turl : '../../Configs/IconConfig.dsc',\r\n");
      out.write("\t\t\t\t\t\t\tactionMethods : {\r\n");
      out.write("\t\t\t\t\t\t\t\tread : 'POST'\r\n");
      out.write("\t\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\t\treader : {\r\n");
      out.write("\t\t\t\t\t\t\t\troot : 'items',\r\n");
      out.write("\t\t\t\t\t\t\t\ttotalProperty : 'total'\r\n");
      out.write("\t\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\t\textraParams : {\r\n");
      out.write("\t\t\t\t\t\t\t\tDCITag : postvalue,\r\n");
      out.write("\t\t\t\t\t\t\t\tuid : uid,\r\n");
      out.write("\t\t\t\t\t\t\t\taction : 'search',\r\n");
      out.write("\t\t\t\t\t\t\t\tpage : 1,\r\n");
      out.write("\t\t\t\t\t\t\t\tpagesize : pageSize,\r\n");
      out.write("\t\t\t\t\t\t\t\tfilter : ''\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\tpageSize : pageSize\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tvar gridColumns = [ {\r\n");
      out.write("\t\t\t\t\t\ttext : langs.icon_id,\r\n");
      out.write("\t\t\t\t\t\tdataIndex : 'icon_id',\r\n");
      out.write("\t\t\t\t\t\twidth : 100\r\n");
      out.write("\t\t\t\t\t}, {\r\n");
      out.write("\t\t\t\t\t\ttext : langs.icon_name,\r\n");
      out.write("\t\t\t\t\t\tdataIndex : 'icon_show_name',\r\n");
      out.write("\t\t\t\t\t\twidth : 200\r\n");
      out.write("\t\t\t\t\t}, {\r\n");
      out.write("\t\t\t\t\t\ttext : langs.icon_map_key,\r\n");
      out.write("\t\t\t\t\t\tdataIndex : 'icon_map_key',\r\n");
      out.write("\t\t\t\t\t\twidth : 100\r\n");
      out.write("\t\t\t\t\t}, {\r\n");
      out.write("\t\t\t\t\t\t//text : langs.icon_name,\r\n");
      out.write("\t\t\t\t\t\txtype : 'dcilightcolumn',\r\n");
      out.write("\t\t\t\t\t\talign : 'center',\r\n");
      out.write("\t\t\t\t\t\tdataIndex : 'icon_id_show',\r\n");
      out.write("\t\t\t\t\t\twidth : 100\r\n");
      out.write("\t\t\t\t\t} ];\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tvar openwin = Ext.create('DCI.Customer.QueryWindow', {\r\n");
      out.write("\t\t\t\t\t\theight : 365,\r\n");
      out.write("\t\t\t\t\t\twidth : 550,\r\n");
      out.write("\t\t\t\t\t\theadform : me,\r\n");
      out.write("\t\t\t\t\t\ttitle : langs.toolbar_query_title,\r\n");
      out.write("\t\t\t\t\t\tloadHeadData : function(record) {\r\n");
      out.write("\t\t\t\t\t\t\tthis.headform.loadRecord(record);\r\n");
      out.write("\t\t\t\t\t\t\tthis.headform.setDataLoaded(true);\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\tif (privateImage != null) {\r\n");
      out.write("\t\t\t\t\t\t\t\tprivateImage.setSrc(iconpath + record.get(\"icon_id_show\"));\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\tthis.close();\r\n");
      out.write("\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\tclickFunc : function() {\r\n");
      out.write("\t\t\t\t\t\t\tvar me = this;\r\n");
      out.write("\t\t\t\t\t\t\tvar gstore = me.items.get(1).getStore();\r\n");
      out.write("\t\t\t\t\t\t\tvar fcol = me.items.get(0).items.get(0).getValue();\r\n");
      out.write("\t\t\t\t\t\t\tvar fvalue = me.items.get(0).items.get(1).getValue();\r\n");
      out.write("\t\t\t\t\t\t\t//console.log(fcol);\r\n");
      out.write("\t\t\t\t\t\t\tif (gstore.getProxy().extraParams.hasOwnProperty('filter')) {\r\n");
      out.write("\t\t\t\t\t\t\t\tif (fcol == \"icon_name\") {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tgstore.getProxy().extraParams['filter'] = \" and lang_value like '%\" + fvalue + \"%' \";\r\n");
      out.write("\t\t\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tgstore.getProxy().extraParams['filter'] = \" and \" + fcol + \" like '%\" + fvalue + \"%' \";\r\n");
      out.write("\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\tif (gstore.getProxy().extraParams.hasOwnProperty('page')) {\r\n");
      out.write("\t\t\t\t\t\t\t\tgstore.getProxy().extraParams['page'] = 1;\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\tgstore.currentPage = 1;\r\n");
      out.write("\t\t\t\t\t\t\tgstore.load();\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tinitStore.load(function(records) {\r\n");
      out.write("\t\t\t\t\t\topenwin.setInitObjects(records, gstore, gridColumns);\r\n");
      out.write("\t\t\t\t\t\tme.searchWin = openwin;\r\n");
      out.write("\t\t\t\t\t\tme.searchStore = gstore;\r\n");
      out.write("\t\t\t\t\t\topenwin.show();\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\tvar headpanel = Ext.create('DCI.Customer.HeadPanel', {\r\n");
      out.write("\t\t\t\theight : 200,\r\n");
      out.write("\t\t\t\twidth : '100%',\r\n");
      out.write("\t\t\t\theadform : headform,\r\n");
      out.write("\t\t\t\tpostvalue : postvalue,\r\n");
      out.write("\t\t\t\tuid : uid,\r\n");
      out.write("\t\t\t\tcanEdit : canEdit,\r\n");
      out.write("\t\t\t\tkeepAdd : false,\r\n");
      out.write("\t\t\t\texceptionDeleteAuth : function() {\r\n");
      out.write("\t\t\t\t\tvar result = [ \"1\", \"\" ];\r\n");
      out.write("\t\t\t\t\tvar form = this.bindform;\r\n");
      out.write("\t\t\t\t\tif (headform != null) {\r\n");
      out.write("\t\t\t\t\t\tif (form.items.get(6).getValue() == \"SYS\") {\r\n");
      out.write("\t\t\t\t\t\t\tresult = [ \"0\", langs.system_def_can_not_delete ];\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\treturn result;\r\n");
      out.write("\t\t\t\t},\r\n");
      out.write("\t\t\t\tafterSaveFunc : function(result) {\r\n");
      out.write("\t\t\t\t\tif (privateImage != null) {\r\n");
      out.write("\t\t\t\t\t\tprivateImage.setSrc(iconpath + result.icon_id_show);\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\tvar main = Ext.create('DCI.Customer.SubPanel', {\r\n");
      out.write("\t\t\t\tid : 'IconConfigMain',\r\n");
      out.write("\t\t\t\trenderTo : 'IconConfigPage',\r\n");
      out.write("\t\t\t\tborder : 0,\r\n");
      out.write("\t\t\t\tbodyPadding : '0 5 5 5',\r\n");
      out.write("\t\t\t\t//layout : 'border',\r\n");
      out.write("\t\t\t\twidthChangeControls : [ headpanel ],\r\n");
      out.write("\t\t\t\titems : [ headpanel ]\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\t/* var initQueryStore = Ext.create('Ext.data.Store', {\r\n");
      out.write("\t\t\t\tautoLoad : false,\r\n");
      out.write("\t\t\t\tfields : [ 'currlang' ],\r\n");
      out.write("\t\t\t\tproxy : {\r\n");
      out.write("\t\t\t\t\ttype : 'ajax',\r\n");
      out.write("\t\t\t\t\turl : '../../Configs/IconConfig.dsc',\r\n");
      out.write("\t\t\t\t\tactionMethods : {\r\n");
      out.write("\t\t\t\t\t\tread : 'POST'\r\n");
      out.write("\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\treader : {\r\n");
      out.write("\t\t\t\t\t\ttype : 'json'\r\n");
      out.write("\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\textraParams : {\r\n");
      out.write("\t\t\t\t\t\tDCITag : postvalue,\r\n");
      out.write("\t\t\t\t\t\taction : 'init'\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\tinitQueryStore.load(function(record) {\r\n");
      out.write("\t\t\t\tif (record.length > 0) {\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}); */\r\n");
      out.write("\r\n");
      out.write("\t\t\t//main.resize(Ext.get(\"IconConfigPage\").getWidth(), Ext.get(\"IconConfigPage\").getHeight());\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<div id=\"IconConfigPage\" class=\"page_div\"></div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_dcitag_005freqParam_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  dcitag:reqParam
    com.dci.jweb.DCITags.Request.getReqParameter _jspx_th_dcitag_005freqParam_005f0 = (new com.dci.jweb.DCITags.Request.getReqParameter());
    _jsp_instancemanager.newInstance(_jspx_th_dcitag_005freqParam_005f0);
    _jspx_th_dcitag_005freqParam_005f0.setJspContext(_jspx_page_context);
    // /FuncViews/Configs/IconConfig.jsp(8,7) name = paramName type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005freqParam_005f0.setParamName("func_name");
    _jspx_th_dcitag_005freqParam_005f0.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005freqParam_005f0);
    return false;
  }

  private boolean _jspx_meth_dcitag_005freqParam_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  dcitag:reqParam
    com.dci.jweb.DCITags.Request.getReqParameter _jspx_th_dcitag_005freqParam_005f1 = (new com.dci.jweb.DCITags.Request.getReqParameter());
    _jsp_instancemanager.newInstance(_jspx_th_dcitag_005freqParam_005f1);
    _jspx_th_dcitag_005freqParam_005f1.setJspContext(_jspx_page_context);
    // /FuncViews/Configs/IconConfig.jsp(16,13) name = paramName type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005freqParam_005f1.setParamName("uid");
    _jspx_th_dcitag_005freqParam_005f1.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005freqParam_005f1);
    return false;
  }

  private boolean _jspx_meth_dcitag_005freqParam_005f2(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  dcitag:reqParam
    com.dci.jweb.DCITags.Request.getReqParameter _jspx_th_dcitag_005freqParam_005f2 = (new com.dci.jweb.DCITags.Request.getReqParameter());
    _jsp_instancemanager.newInstance(_jspx_th_dcitag_005freqParam_005f2);
    _jspx_th_dcitag_005freqParam_005f2.setJspContext(_jspx_page_context);
    // /FuncViews/Configs/IconConfig.jsp(22,56) name = paramName type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005freqParam_005f2.setParamName("canEdit");
    _jspx_th_dcitag_005freqParam_005f2.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005freqParam_005f2);
    return false;
  }
}
