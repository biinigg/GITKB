/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2016-01-13 03:51:47 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.FuncViews.Configs;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class ConnConfig_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\t\tvar localKeys = [ \"errmsg\", \"system_error\", \"save_msg\", \"delete_msg\", \"delete_fail\", \"delete_success\", \"delete_confirm_title\", \"delete_confirm_msg\", \"delete_result_title\",\r\n");
      out.write("\t\t\t\t\"save_fail\", \"save_success\", \"save_result_title\", \"save_fail\", \"system_build\", \"conn_id\", \"conn_name\", \"conn_desc\", \"db_addr\", \"db_name\", \"db_user\", \"db_pwd\",\r\n");
      out.write("\t\t\t\t\"db_type\", \"db_port\", \"visible\", \"enable\", \"disable\", \"toolbar_query_title\", \"system_def_can_not_edit\", \"info_msg_title\", \"conn_check_fail\", \"conn_check_success\",\r\n");
      out.write("\t\t\t\t\"confirm_title\", \"data_lose_warning\", \"check\", \"cus_format\" ];\r\n");
      out.write("\t\tvar keys = localKeys.concat(globeKeys);\r\n");
      out.write("\t\tvar uid = '");
      if (_jspx_meth_dcitag_005freqParam_005f1(_jspx_page_context))
        return;
      out.write("';\r\n");
      out.write("\t\tthis.dcistore.setMultiLangKeys(keys);\r\n");
      out.write("\t\tthis.dcistore.setUid(uid);\r\n");
      out.write("\t\tthis.dcistore.load(function(records) {\r\n");
      out.write("\t\t\tif (records != null && records.length == 1) {\r\n");
      out.write("\t\t\t\tvar langs = buildMultiLangObjct(keys, records[0].get('langValues'));\r\n");
      out.write("\t\t\t\tshowPage(records[0].get('dcitagValue'), langs, '");
      if (_jspx_meth_dcitag_005freqParam_005f2(_jspx_page_context))
        return;
      out.write("', uid);\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\tfunction showPage(postvalue, langs, recvAuth, uid) {\r\n");
      out.write("\t\t\tvar canEdit = recvAuth == \"1\";\r\n");
      out.write("\t\t\tvar db_type_combo = Ext.create('DCI.Customer.ComboBox', {\r\n");
      out.write("\t\t\t\tfieldLabel : langs.db_type,\r\n");
      out.write("\t\t\t\tname : 'db_type',\r\n");
      out.write("\t\t\t\tx : 500,\r\n");
      out.write("\t\t\t\ty : 90,\r\n");
      out.write("\t\t\t\tlisteners : {\r\n");
      out.write("\t\t\t\t\tchange : function(comp, newValue, oldValue, eOpts) {\r\n");
      out.write("\t\t\t\t\t\tvar form = this.up('form');\r\n");
      out.write("\t\t\t\t\t\tif (form != null) {\r\n");
      out.write("\t\t\t\t\t\t\tvar textbox = form.items.get(7);\r\n");
      out.write("\t\t\t\t\t\t\tif (newValue == 'Oracle') {\r\n");
      out.write("\t\t\t\t\t\t\t\ttextbox.setValue(1521);\r\n");
      out.write("\t\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\t\ttextbox.setValue(1433);\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\tvar visible_combo = Ext.create('DCI.Customer.ComboBox', {\r\n");
      out.write("\t\t\t\tfieldLabel : langs.visible,\r\n");
      out.write("\t\t\t\tname : 'visible',\r\n");
      out.write("\t\t\t\tstore : {\r\n");
      out.write("\t\t\t\t\tfields : [ 'label', 'value' ],\r\n");
      out.write("\t\t\t\t\tdata : [ {\r\n");
      out.write("\t\t\t\t\t\t\"label\" : langs.enable,\r\n");
      out.write("\t\t\t\t\t\t\"value\" : '1'\r\n");
      out.write("\t\t\t\t\t}, {\r\n");
      out.write("\t\t\t\t\t\t\"label\" : langs.disable,\r\n");
      out.write("\t\t\t\t\t\t\"value\" : '0'\r\n");
      out.write("\t\t\t\t\t} ]\r\n");
      out.write("\t\t\t\t},\r\n");
      out.write("\t\t\t\tdefaultvalue : '1',\r\n");
      out.write("\t\t\t\tx : 500,\r\n");
      out.write("\t\t\t\ty : 10\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\tvar headform = Ext.create('DCI.Customer.HeadFormPanel', {\r\n");
      out.write("\t\t\t\theight : 100,\r\n");
      out.write("\t\t\t\turl : '../../Configs/ConnConfig.dsc',\r\n");
      out.write("\t\t\t\tlanguageObj : langs,\r\n");
      out.write("\t\t\t\titems : [ Ext.create('DCI.Customer.TextField', {\r\n");
      out.write("\t\t\t\t\tfieldLabel : langs.conn_id,\r\n");
      out.write("\t\t\t\t\temptyText : '',\r\n");
      out.write("\t\t\t\t\tname : 'conn_id',\r\n");
      out.write("\t\t\t\t\tallowBlank : false,\r\n");
      out.write("\t\t\t\t\tispk : true,\r\n");
      out.write("\t\t\t\t\tcanEdit : false,\r\n");
      out.write("\t\t\t\t\tdefaultvalue : langs.system_build,\r\n");
      out.write("\t\t\t\t\tx : 0,\r\n");
      out.write("\t\t\t\t\ty : 10\r\n");
      out.write("\t\t\t\t}), Ext.create('DCI.Customer.TextField', {\r\n");
      out.write("\t\t\t\t\tfieldLabel : langs.conn_name,\r\n");
      out.write("\t\t\t\t\temptyText : '',\r\n");
      out.write("\t\t\t\t\tname : 'conn_name',\r\n");
      out.write("\t\t\t\t\tallowBlank : false,\r\n");
      out.write("\t\t\t\t\tdefaultvalue : '',\r\n");
      out.write("\t\t\t\t\tx : 250,\r\n");
      out.write("\t\t\t\t\ty : 10\r\n");
      out.write("\t\t\t\t}), Ext.create('DCI.Customer.TextField', {\r\n");
      out.write("\t\t\t\t\tfieldLabel : langs.conn_desc,\r\n");
      out.write("\t\t\t\t\temptyText : '',\r\n");
      out.write("\t\t\t\t\tname : 'conn_desc',\r\n");
      out.write("\t\t\t\t\tallowBlank : true,\r\n");
      out.write("\t\t\t\t\tdefaultvalue : '',\r\n");
      out.write("\t\t\t\t\twidth : 465,\r\n");
      out.write("\t\t\t\t\tx : 0,\r\n");
      out.write("\t\t\t\t\ty : 50\r\n");
      out.write("\t\t\t\t}), Ext.create('DCI.Customer.TextField', {\r\n");
      out.write("\t\t\t\t\tfieldLabel : langs.db_addr,\r\n");
      out.write("\t\t\t\t\temptyText : '',\r\n");
      out.write("\t\t\t\t\tname : 'db_addr',\r\n");
      out.write("\t\t\t\t\tallowBlank : false,\r\n");
      out.write("\t\t\t\t\tdefaultvalue : '',\r\n");
      out.write("\t\t\t\t\tx : 0,\r\n");
      out.write("\t\t\t\t\ty : 90\r\n");
      out.write("\t\t\t\t}), Ext.create('DCI.Customer.TextField', {\r\n");
      out.write("\t\t\t\t\tfieldLabel : langs.db_name,\r\n");
      out.write("\t\t\t\t\temptyText : '',\r\n");
      out.write("\t\t\t\t\tname : 'db_name',\r\n");
      out.write("\t\t\t\t\tallowBlank : false,\r\n");
      out.write("\t\t\t\t\tdefaultvalue : '',\r\n");
      out.write("\t\t\t\t\tx : 250,\r\n");
      out.write("\t\t\t\t\ty : 90\r\n");
      out.write("\t\t\t\t}), Ext.create('DCI.Customer.TextField', {\r\n");
      out.write("\t\t\t\t\tfieldLabel : langs.db_user,\r\n");
      out.write("\t\t\t\t\temptyText : '',\r\n");
      out.write("\t\t\t\t\tname : 'db_user',\r\n");
      out.write("\t\t\t\t\tallowBlank : false,\r\n");
      out.write("\t\t\t\t\tdefaultvalue : '',\r\n");
      out.write("\t\t\t\t\tx : 0,\r\n");
      out.write("\t\t\t\t\ty : 130\r\n");
      out.write("\t\t\t\t}), Ext.create('DCI.Customer.TextField', {\r\n");
      out.write("\t\t\t\t\tfieldLabel : langs.db_pwd,\r\n");
      out.write("\t\t\t\t\temptyText : '',\r\n");
      out.write("\t\t\t\t\tname : 'db_pwd',\r\n");
      out.write("\t\t\t\t\tallowBlank : false,\r\n");
      out.write("\t\t\t\t\tinputType : 'password',\r\n");
      out.write("\t\t\t\t\tdefaultvalue : '',\r\n");
      out.write("\t\t\t\t\tx : 250,\r\n");
      out.write("\t\t\t\t\ty : 130\r\n");
      out.write("\t\t\t\t}), Ext.create('DCI.Customer.TextField', {\r\n");
      out.write("\t\t\t\t\tfieldLabel : langs.db_port,\r\n");
      out.write("\t\t\t\t\temptyText : '',\r\n");
      out.write("\t\t\t\t\tname : 'db_port',\r\n");
      out.write("\t\t\t\t\tallowBlank : false,\r\n");
      out.write("\t\t\t\t\tdefaultvalue : '1433',\r\n");
      out.write("\t\t\t\t\tx : 500,\r\n");
      out.write("\t\t\t\t\ty : 130\r\n");
      out.write("\t\t\t\t}), db_type_combo, visible_combo ],\r\n");
      out.write("\t\t\t\tcusValid : function(fn) {\r\n");
      out.write("\t\t\t\t\tvar me = this;\r\n");
      out.write("\t\t\t\t\tvar field_name = me.items.get(1);\r\n");
      out.write("\t\t\t\t\tvar conn_id = me.items.get(0).getValue();\r\n");
      out.write("\t\t\t\t\tvar conn_name = field_name.getValue();\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tExt.Ajax.request({\r\n");
      out.write("\t\t\t\t\t\tmethod : 'POST',\r\n");
      out.write("\t\t\t\t\t\turl : '../../Configs/ConnConfig.dsc',\r\n");
      out.write("\t\t\t\t\t\tparams : {\r\n");
      out.write("\t\t\t\t\t\t\tDCITag : postvalue,\r\n");
      out.write("\t\t\t\t\t\t\tuid : uid,\r\n");
      out.write("\t\t\t\t\t\t\taction : 'checkName',\r\n");
      out.write("\t\t\t\t\t\t\tconn_name : conn_name,\r\n");
      out.write("\t\t\t\t\t\t\tconn_id : conn_id\r\n");
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
      out.write("\t\t\t\t\t\t\turl : '../../Configs/ConnConfig.dsc',\r\n");
      out.write("\t\t\t\t\t\t\tactionMethods : {\r\n");
      out.write("\t\t\t\t\t\t\t\tread : 'POST'\r\n");
      out.write("\t\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\t\treader : {\r\n");
      out.write("\t\t\t\t\t\t\t\ttype : 'json'\r\n");
      out.write("\t\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\t\textraParams : {\r\n");
      out.write("\t\t\t\t\t\t\t\tDCITag : postvalue,\r\n");
      out.write("\t\t\t\t\t\t\t\tuid : uid,\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tvar gstore = Ext.create('Ext.data.Store', {\r\n");
      out.write("\t\t\t\t\t\tautoLoad : false,\r\n");
      out.write("\t\t\t\t\t\tfields : [ 'conn_id', 'conn_name', 'conn_desc', 'db_addr', 'db_name', 'db_user', 'db_pwd', 'db_type', 'db_port', 'visible' ],\r\n");
      out.write("\t\t\t\t\t\tproxy : {\r\n");
      out.write("\t\t\t\t\t\t\ttype : 'ajax',\r\n");
      out.write("\t\t\t\t\t\t\turl : '../../Configs/ConnConfig.dsc',\r\n");
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
      out.write("\t\t\t\t\t\ttext : langs.conn_id,\r\n");
      out.write("\t\t\t\t\t\tdataIndex : 'conn_id',\r\n");
      out.write("\t\t\t\t\t\twidth : 200\r\n");
      out.write("\t\t\t\t\t}, {\r\n");
      out.write("\t\t\t\t\t\ttext : langs.conn_name,\r\n");
      out.write("\t\t\t\t\t\tdataIndex : 'conn_name',\r\n");
      out.write("\t\t\t\t\t\twidth : 200\r\n");
      out.write("\t\t\t\t\t} ];\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tvar openwin = Ext.create('DCI.Customer.QueryWindow', {\r\n");
      out.write("\t\t\t\t\t\theight : 335,\r\n");
      out.write("\t\t\t\t\t\twidth : 450,\r\n");
      out.write("\t\t\t\t\t\theadform : me,\r\n");
      out.write("\t\t\t\t\t\ttitle : langs.toolbar_query_title\r\n");
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
      out.write("\t\t\t\texceptionEditAuth : function() {\r\n");
      out.write("\t\t\t\t\tvar result = [ \"1\", \"\" ];\r\n");
      out.write("\t\t\t\t\tvar form = this.bindform;\r\n");
      out.write("\t\t\t\t\tif (headform != null) {\r\n");
      out.write("\t\t\t\t\t\tif (form.items.get(0).getValue() == \"CSYS\") {\r\n");
      out.write("\t\t\t\t\t\t\tresult = [ \"0\", langs.system_def_can_not_edit ];\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\treturn result;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\theadpanel.setCusButtons([ {\r\n");
      out.write("\t\t\t\txtype : 'button',\r\n");
      out.write("\t\t\t\tcls : 'sqlcheckbutton',\r\n");
      out.write("\t\t\t\ttooltip : langs.check,\r\n");
      out.write("\t\t\t\tx : 215,\r\n");
      out.write("\t\t\t\ty : 3,\r\n");
      out.write("\t\t\t\twidth : 30,\r\n");
      out.write("\t\t\t\theight : 30,\r\n");
      out.write("\t\t\t\thandler : function() {\r\n");
      out.write("\t\t\t\t\tvar bform = this.up('panel').bindform;\r\n");
      out.write("\t\t\t\t\tvar pvalue = this.up('panel').postvalue;\r\n");
      out.write("\t\t\t\t\tvar langobj = bform.languageObj;\r\n");
      out.write("\t\t\t\t\tif (bform.getCurrMode() == PageMode.Add || bform.dataloaded) {\r\n");
      out.write("\t\t\t\t\t\tbform.setLoading(true);\r\n");
      out.write("\t\t\t\t\t\tExt.Ajax.request({\r\n");
      out.write("\t\t\t\t\t\t\tmethod : 'POST',\r\n");
      out.write("\t\t\t\t\t\t\turl : '../../Configs/ConnConfig.dsc',\r\n");
      out.write("\t\t\t\t\t\t\tparams : {\r\n");
      out.write("\t\t\t\t\t\t\t\tDCITag : pvalue,\r\n");
      out.write("\t\t\t\t\t\t\t\tuid : uid,\r\n");
      out.write("\t\t\t\t\t\t\t\taction : 'connCheck',\r\n");
      out.write("\t\t\t\t\t\t\t\tdatas : Ext.encode(bform.getForm().getValues())\r\n");
      out.write("\t\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\t\tsuccess : function(a) {\r\n");
      out.write("\t\t\t\t\t\t\t\tbform.setLoading(false);\r\n");
      out.write("\t\t\t\t\t\t\t\tvar result = Ext.JSON.decode(a.responseText);\r\n");
      out.write("\t\t\t\t\t\t\t\tif (result.success) {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tExt.Msg.alert(langobj.info_msg_title, langobj.conn_check_success);\r\n");
      out.write("\t\t\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tExt.Msg.alert(langobj.errmsg, langobj.conn_check_fail + \"</br>\" + result.msg);\r\n");
      out.write("\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\t\tfailure : function(f, action) {\r\n");
      out.write("\t\t\t\t\t\t\t\tbform.setLoading(false);\r\n");
      out.write("\t\t\t\t\t\t\t\tExt.Msg.alert(langobj.errmsg, langobj.system_error);\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t} ]);\r\n");
      out.write("\r\n");
      out.write("\t\t\tvar main = Ext.create('DCI.Customer.SubPanel', {\r\n");
      out.write("\t\t\t\tid : 'ConnConfigMain',\r\n");
      out.write("\t\t\t\trenderTo : 'ConnConfigPage',\r\n");
      out.write("\t\t\t\tborder : 0,\r\n");
      out.write("\t\t\t\tbodyPadding : '0 5 5 5',\r\n");
      out.write("\t\t\t\t//layout : 'border',\r\n");
      out.write("\t\t\t\twidthChangeControls : [ headpanel ],\r\n");
      out.write("\t\t\t\titems : [ headpanel ]\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\tvar initQueryStore = Ext.create('Ext.data.Store', {\r\n");
      out.write("\t\t\t\tautoLoad : false,\r\n");
      out.write("\t\t\t\tfields : [ 'dbtypes' ],\r\n");
      out.write("\t\t\t\tproxy : {\r\n");
      out.write("\t\t\t\t\ttype : 'ajax',\r\n");
      out.write("\t\t\t\t\turl : '../../Configs/ConnConfig.dsc',\r\n");
      out.write("\t\t\t\t\tactionMethods : {\r\n");
      out.write("\t\t\t\t\t\tread : 'POST'\r\n");
      out.write("\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\treader : {\r\n");
      out.write("\t\t\t\t\t\ttype : 'json'\r\n");
      out.write("\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\textraParams : {\r\n");
      out.write("\t\t\t\t\t\tDCITag : postvalue,\r\n");
      out.write("\t\t\t\t\t\tuid : uid,\r\n");
      out.write("\t\t\t\t\t\taction : 'init'\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\tinitQueryStore.load(function(record) {\r\n");
      out.write("\t\t\t\tif (record.length > 0) {\r\n");
      out.write("\t\t\t\t\tdb_type_combo.getStore().loadData(record[0].get(\"dbtypes\"));\r\n");
      out.write("\t\t\t\t\tdb_type_combo.loadDefault();\r\n");
      out.write("\t\t\t\t\tvisible_combo.loadDefault();\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tdb_type_combo.listConfig = {\r\n");
      out.write("\t\t\t\t\t\tminWidth : db_type_combo.getWidth() - db_type_combo.getLabelWidth()\r\n");
      out.write("\t\t\t\t\t};\r\n");
      out.write("\t\t\t\t\tvisible_combo.listConfig = {\r\n");
      out.write("\t\t\t\t\t\tminWidth : visible_combo.getWidth() - visible_combo.getLabelWidth()\r\n");
      out.write("\t\t\t\t\t};\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\t//main.resize(Ext.get(\"ConnConfigPage\").getWidth(), Ext.get(\"ConnConfigPage\").getHeight());\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<div id=\"ConnConfigPage\" class=\"page_div\"></div>\r\n");
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
    // /FuncViews/Configs/ConnConfig.jsp(8,7) name = paramName type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
    // /FuncViews/Configs/ConnConfig.jsp(17,13) name = paramName type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
    // /FuncViews/Configs/ConnConfig.jsp(23,52) name = paramName type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005freqParam_005f2.setParamName("canEdit");
    _jspx_th_dcitag_005freqParam_005f2.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005freqParam_005f2);
    return false;
  }
}