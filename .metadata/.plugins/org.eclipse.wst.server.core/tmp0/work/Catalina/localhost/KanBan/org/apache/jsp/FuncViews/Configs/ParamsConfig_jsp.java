/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2016-01-25 07:07:56 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.FuncViews.Configs;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class ParamsConfig_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\t\tvar localKeys = [ \"ParamsConfig\", \"config_id\", \"config_value\", \"config_desc\", \"load_def_format\", \"load_def_fail\", \"load_def_success\", \"load_def_result_title\",\r\n");
      out.write("\t\t\t\t\"load_def_confirm_title\", \"load_def_confirm_msg\", \"save_fail\", \"save_success\", \"save_result_title\", \"save_fail\", \"move\", \"still\", \"cus_format\" ];\r\n");
      out.write("\t\tvar keys = localKeys.concat(globeKeys);\r\n");
      out.write("\t\tvar uid = '");
      if (_jspx_meth_dcitag_005freqParam_005f1(_jspx_page_context))
        return;
      out.write("';\r\n");
      out.write("\t\tthis.dcistore.setMultiLangKeys(keys);\r\n");
      out.write("\t\tthis.dcistore.setUid(uid);\r\n");
      out.write("\t\tthis.dcistore.load(function(records) {\r\n");
      out.write("\t\t\tif (records != null && records.length == 1) {\r\n");
      out.write("\t\t\t\tlangs = buildMultiLangObjct(keys, records[0].get('langValues'));\r\n");
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
      out.write("\t\t\t/* var headform = Ext.create('DCI.Customer.HeadFormPanel', {\r\n");
      out.write("\t\t\t\theight : 100,\r\n");
      out.write("\t\t\t\turl : '../../Configs/ParamsConfig.dsc',\r\n");
      out.write("\t\t\t\tlanguageObj : langs,\r\n");
      out.write("\t\t\t\titems : [],\r\n");
      out.write("\t\t\t\tshowQueryWindow : function() {\r\n");
      out.write("\t\t\t\t\tthis.resetDataLoaded();\r\n");
      out.write("\t\t\t\t\tvar pageSize = 50;\r\n");
      out.write("\t\t\t\t\tvar initStore = Ext.create('Ext.data.Store', {\r\n");
      out.write("\t\t\t\t\t\tautoLoad : false,\r\n");
      out.write("\t\t\t\t\t\tfields : [ 'cols' ],\r\n");
      out.write("\t\t\t\t\t\tproxy : {\r\n");
      out.write("\t\t\t\t\t\t\ttype : 'ajax',\r\n");
      out.write("\t\t\t\t\t\t\turl : '../../Configs/ParamsConfig.dsc',\r\n");
      out.write("\t\t\t\t\t\t\tactionMethods : {\r\n");
      out.write("\t\t\t\t\t\t\t\tread : 'POST'\r\n");
      out.write("\t\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\t\treader : {\r\n");
      out.write("\t\t\t\t\t\t\t\ttype : 'json'\r\n");
      out.write("\t\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\t\textraParams : {\r\n");
      out.write("\t\t\t\t\t\t\t\tDCITag : postvalue\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tvar gstore = Ext.create('Ext.data.Store', {\r\n");
      out.write("\t\t\t\t\t\tautoLoad : false,\r\n");
      out.write("\t\t\t\t\t\tfields : [],\r\n");
      out.write("\t\t\t\t\t\tproxy : {\r\n");
      out.write("\t\t\t\t\t\t\ttype : 'ajax',\r\n");
      out.write("\t\t\t\t\t\t\turl : '../../Configs/ParamsConfig.dsc',\r\n");
      out.write("\t\t\t\t\t\t\tactionMethods : {\r\n");
      out.write("\t\t\t\t\t\t\t\tread : 'POST'\r\n");
      out.write("\t\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\t\treader : {\r\n");
      out.write("\t\t\t\t\t\t\t\troot : 'items',\r\n");
      out.write("\t\t\t\t\t\t\t\ttotalProperty : 'total'\r\n");
      out.write("\t\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\t\textraParams : {\r\n");
      out.write("\t\t\t\t\t\t\t\tDCITag : postvalue,\r\n");
      out.write("\t\t\t\t\t\t\t\taction : 'search',\r\n");
      out.write("\t\t\t\t\t\t\t\tpage : 1,\r\n");
      out.write("\t\t\t\t\t\t\t\tpagesize : pageSize,\r\n");
      out.write("\t\t\t\t\t\t\t\tfilter : ''\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\tpageSize : pageSize\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tvar gridColumns = [];\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tvar openwin = Ext.create('DCI.Customer.QueryWindow', {\r\n");
      out.write("\t\t\t\t\t\theight : 200,\r\n");
      out.write("\t\t\t\t\t\twidth : 450,\r\n");
      out.write("\t\t\t\t\t\theadform : this\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tinitStore.load(function(records) {\r\n");
      out.write("\t\t\t\t\t\topenwin.setInitObjects(records, gstore, gridColumns);\r\n");
      out.write("\t\t\t\t\t\topenwin.show();\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\tvar headpanel = Ext.create('DCI.Customer.HeadPanel', {\r\n");
      out.write("\t\t\t\theight : 200,\r\n");
      out.write("\t\t\t\twidth : '100%',\r\n");
      out.write("\t\t\t\theadform : headform,\r\n");
      out.write("\t\t\t\tpostvalue : postvalue\r\n");
      out.write("\t\t\t}); */\r\n");
      out.write("\r\n");
      out.write("\t\t\tvar conn_Combo = Ext.create('DCI.Customer.ComboBox', {\r\n");
      out.write("\t\t\t\tlabelWidth : 0\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\tvar bodypanel = Ext.create('DCI.Customer.BodyGridPanel', {\r\n");
      out.write("\t\t\t\ttitle : langs.ParamsConfig,\r\n");
      out.write("\t\t\t\tlanguageObj : langs,\r\n");
      out.write("\t\t\t\tgridid : 'ParamsConfig_G01',\r\n");
      out.write("\t\t\t\tactionurl : '../../Configs/ParamsConfig.dsc',\r\n");
      out.write("\t\t\t\tpostvalue : postvalue,\r\n");
      out.write("\t\t\t\tuid : uid,\r\n");
      out.write("\t\t\t\t//headform : headform,\r\n");
      out.write("\t\t\t\tkeyfields : [ 'config_id' ],\r\n");
      out.write("\t\t\t\tcanEdit : canEdit,\r\n");
      out.write("\t\t\t\tstopUsingBtn : [ 0, 2 ]\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\tvar bodyColumns = [ {\r\n");
      out.write("\t\t\t\txtype : 'rownumberer',\r\n");
      out.write("\t\t\t\tcolid : 'colrownum',\r\n");
      out.write("\t\t\t\twidth : 40,\r\n");
      out.write("\t\t\t\tsortable : false,\r\n");
      out.write("\t\t\t\tlocked : true\r\n");
      out.write("\t\t\t}, {\r\n");
      out.write("\t\t\t\ttext : langs.config_id,\r\n");
      out.write("\t\t\t\tdataIndex : 'config_id',\r\n");
      out.write("\t\t\t\tcolid : 'colconfig_id',\r\n");
      out.write("\t\t\t\twidth : 150\r\n");
      out.write("\t\t\t}, {\r\n");
      out.write("\t\t\t\ttext : langs.config_value,\r\n");
      out.write("\t\t\t\tdataIndex : 'config_value',\r\n");
      out.write("\t\t\t\tcolid : 'colconfig_value',\r\n");
      out.write("\t\t\t\twidth : 150,\r\n");
      out.write("\t\t\t\tgetEditor : function(record) {\r\n");
      out.write("\t\t\t\t\tvar editor = null;\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tif (record.data.config_id == 'RemoveUserPwd') {\r\n");
      out.write("\t\t\t\t\t\teditor = Ext.create('DCI.Customer.TextField', {\r\n");
      out.write("\t\t\t\t\t\t\tfieldLabel : \"\",\r\n");
      out.write("\t\t\t\t\t\t\tlabelWidth : 0,\r\n");
      out.write("\t\t\t\t\t\t\tinputType : 'password',\r\n");
      out.write("\t\t\t\t\t\t\tdefaultvalue : '',\r\n");
      out.write("\t\t\t\t\t\t\tallowBlank : false\r\n");
      out.write("\t\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\t} else if (record.data.config_id == 'MarqueeType') {\r\n");
      out.write("\t\t\t\t\t\teditor = Ext.create('DCI.Customer.ComboBox', {\r\n");
      out.write("\t\t\t\t\t\t\tfieldLabel : \"\",\r\n");
      out.write("\t\t\t\t\t\t\tlabelWidth : 0,\r\n");
      out.write("\t\t\t\t\t\t\tdefaultvalue : '1',\r\n");
      out.write("\t\t\t\t\t\t\tstore : {\r\n");
      out.write("\t\t\t\t\t\t\t\tfields : [ 'label', 'value' ],\r\n");
      out.write("\t\t\t\t\t\t\t\tdata : [ {\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\"label\" : langs.move,\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\"value\" : \"1\"\r\n");
      out.write("\t\t\t\t\t\t\t\t}, {\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\"label\" : langs.still,\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\"value\" : \"2\"\r\n");
      out.write("\t\t\t\t\t\t\t\t} ]\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\teditor = Ext.create('DCI.Customer.TextField', {\r\n");
      out.write("\t\t\t\t\t\t\tfieldLabel : \"\",\r\n");
      out.write("\t\t\t\t\t\t\tlabelWidth : 0,\r\n");
      out.write("\t\t\t\t\t\t\tdefaultvalue : '',\r\n");
      out.write("\t\t\t\t\t\t\tallowBlank : false\r\n");
      out.write("\t\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\treturn Ext.create('Ext.grid.CellEditor', {\r\n");
      out.write("\t\t\t\t\t\tfield : editor,\r\n");
      out.write("\t\t\t\t\t\tautoSize : {\r\n");
      out.write("\t\t\t\t\t\t\twidth : 'boundEl',\r\n");
      out.write("\t\t\t\t\t\t\theight : 'boundEl'\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t},\r\n");
      out.write("\t\t\t\trenderer : function(value, metaData, record, row, col, store, gridView) {\r\n");
      out.write("\t\t\t\t\tvar display = \"\";\r\n");
      out.write("\t\t\t\t\tif (record.data.config_id == 'RemoveUserPwd') {\r\n");
      out.write("\t\t\t\t\t\tfor ( var i = 0; i < value.length; i++) {\r\n");
      out.write("\t\t\t\t\t\t\tdisplay += \"．\";\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t} else if (record.data.config_id == 'MarqueeType') {\r\n");
      out.write("\t\t\t\t\t\tif (value == '1') {\r\n");
      out.write("\t\t\t\t\t\t\tdisplay = langs.move;\r\n");
      out.write("\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\tdisplay = langs.still;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\tdisplay = value;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\treturn display;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}, {\r\n");
      out.write("\t\t\t\ttext : langs.config_desc,\r\n");
      out.write("\t\t\t\tdataIndex : 'config_desc',\r\n");
      out.write("\t\t\t\tcolid : 'colconfig_desc',\r\n");
      out.write("\t\t\t\twidth : 400\r\n");
      out.write("\t\t\t} ];\r\n");
      out.write("\r\n");
      out.write("\t\t\tvar bodyStore = Ext.create('Ext.data.Store', {\r\n");
      out.write("\t\t\t\tautoLoad : false,\r\n");
      out.write("\t\t\t\tfields : [ 'config_id', 'config_value', 'config_desc', 'moditag', 'moditp' ],\r\n");
      out.write("\t\t\t\tproxy : {\r\n");
      out.write("\t\t\t\t\ttype : 'ajax',\r\n");
      out.write("\t\t\t\t\turl : '../../Configs/ParamsConfig.dsc',\r\n");
      out.write("\t\t\t\t\tactionMethods : {\r\n");
      out.write("\t\t\t\t\t\tread : 'POST'\r\n");
      out.write("\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\treader : {\r\n");
      out.write("\t\t\t\t\t\troot : 'items'\r\n");
      out.write("\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\textraParams : {\r\n");
      out.write("\t\t\t\t\t\tDCITag : postvalue,\r\n");
      out.write("\t\t\t\t\t\tuid : uid,\r\n");
      out.write("\t\t\t\t\t\taction : 'bodyData'\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\tbodypanel.initBody(bodyStore, bodyColumns);\r\n");
      out.write("\r\n");
      out.write("\t\t\tvar main = Ext.create('DCI.Customer.SubPanel', {\r\n");
      out.write("\t\t\t\tid : 'ParamsConfigMain',\r\n");
      out.write("\t\t\t\trenderTo : 'ParamsConfigPage',\r\n");
      out.write("\t\t\t\tborder : 0,\r\n");
      out.write("\t\t\t\tbodyPadding : '0 5 5 5',\r\n");
      out.write("\t\t\t\tlayout : 'fit',\r\n");
      out.write("\t\t\t\twidthChangeControls : [ bodypanel ],\r\n");
      out.write("\t\t\t\titems : [ bodypanel ]\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\tbodyStore.load();\r\n");
      out.write("\t\t\t//bodypanel.getGrid().getStore().load();\r\n");
      out.write("\t\t\tbodypanel.dataloaded(true);\r\n");
      out.write("\r\n");
      out.write("\t\t\t/* var initQueryStore = Ext.create('Ext.data.Store', {\r\n");
      out.write("\t\t\t\tautoLoad : false,\r\n");
      out.write("\t\t\t\tfields : [],\r\n");
      out.write("\t\t\t\tproxy : {\r\n");
      out.write("\t\t\t\t\ttype : 'ajax',\r\n");
      out.write("\t\t\t\t\turl : '../../Configs/ParamsConfig.dsc',\r\n");
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
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}); */\r\n");
      out.write("\r\n");
      out.write("\t\t\tmain.resize(Ext.get(\"ParamsConfigPage\").getWidth(), Ext.get(\"ParamsConfigPage\").getHeight());\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<div id=\"ParamsConfigPage\" class=\"page_div\"></div>\r\n");
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
    // /FuncViews/Configs/ParamsConfig.jsp(8,7) name = paramName type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
    // /FuncViews/Configs/ParamsConfig.jsp(15,13) name = paramName type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
    // /FuncViews/Configs/ParamsConfig.jsp(22,52) name = paramName type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005freqParam_005f2.setParamName("canEdit");
    _jspx_th_dcitag_005freqParam_005f2.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005freqParam_005f2);
    return false;
  }
}