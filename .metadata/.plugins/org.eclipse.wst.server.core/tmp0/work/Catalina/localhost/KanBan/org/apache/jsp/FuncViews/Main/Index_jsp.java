/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2016-01-28 03:40:53 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.FuncViews.Main;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class Index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/FuncViews/Main/./../../dcitag/dcitag.tld", Long.valueOf(1452650348276L));
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
      if (_jspx_meth_dcitag_005fmultiLang_005f0(_jspx_page_context))
        return;
      out.write("</title>\r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"./../../extjs/resources/css/ext-all.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"./../../dcicss/dcicss.css\" />\r\n");
      out.write("<script type=\"text/javascript\" src=\"./../../codemirror/js/codemirror.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./../../extjs/js/ext-all.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./../../dcijs/dci-all.js\"></script>\r\n");
      out.write("<script>\r\n");
      out.write("\t\r\n");
      out.write("</script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      if (_jspx_meth_dcitag_005fextLangFile_005f0(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./../js/Main/Index.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tvar globeKeys = [ \"close\", \"search\", \"save\", \"add\", \"_delete\", \"edit\", \"exit\", \"errmsg\", \"save_format\", \"to_edit\", \"to_view\", \"mode_view\", \"mode_edit\", \"no_edit_auth\",\r\n");
      out.write("\t\t\t\"expand\", \"collapse\", \"refersh\", \"clear\", \"system_error\", \"ok\", \"cancel\", \"first_row\", \"back_row\", \"next_row\", \"last_row\" ];\r\n");
      out.write("\t//var cnt = 0;\r\n");
      out.write("\tvar posvalue;\r\n");
      out.write("\tvar islogout = false;\r\n");
      out.write("\tvar isrefresh = false;\r\n");
      out.write("\tvar iserror = false;\r\n");
      out.write("\tvar idxuid;\r\n");
      out.write("\twindow.onbeforeunload = check;\r\n");
      out.write("\tdocument.onkeydown = getrefresh;\r\n");
      out.write("\r\n");
      out.write("\tfunction check(e) {\r\n");
      out.write("\t\t//console.log(iserror);\t\t\r\n");
      out.write("\r\n");
      out.write("\t\tif (!isrefresh && !islogout && !iserror) {\r\n");
      out.write("\t\t\te = e || window.event;\r\n");
      out.write("\r\n");
      out.write("\t\t\tif (e) {\r\n");
      out.write("\t\t\t\te.returnValue = '");
      if (_jspx_meth_dcitag_005fmultiLang_005f1(_jspx_page_context))
        return;
      out.write("';\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\treturn '");
      if (_jspx_meth_dcitag_005fmultiLang_005f2(_jspx_page_context))
        return;
      out.write("';\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction pagelogout() {\r\n");
      out.write("\t\tif (!isrefresh && !islogout) {\r\n");
      out.write("\t\t\ttry {\r\n");
      out.write("\t\t\t\tlogoutF(posvalue);\r\n");
      out.write("\t\t\t} catch (e) {\r\n");
      out.write("\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction getrefresh() {\r\n");
      out.write("\t\t//console.log(event.keyCode);\r\n");
      out.write("\t\tif (event.keyCode == 116 || (event.ctrlKey && event.keyCode == 82)) {\r\n");
      out.write("\t\t\tisrefresh = true;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" charset=\"UTF-8\">\r\n");
      out.write("\tExt.onReady(function() {\r\n");
      out.write("\t\t//修正日期元件中文顯示\r\n");
      out.write("\t\tvar proto = Ext.picker.Date.prototype, date = Ext.Date;\r\n");
      out.write("\r\n");
      out.write("\t\tproto.monthNames = date.monthNames;\r\n");
      out.write("\t\tproto.dayNames = date.dayNames;\r\n");
      out.write("\t\tproto.format = date.defaultFormat;\r\n");
      out.write("\t\t//\r\n");
      out.write("\r\n");
      out.write("\t\tExt.QuickTips.init();\r\n");
      out.write("\t\tvar localKeys = [ \"tree_panel_title\", \"refresh\", \"auto_refresh\", \"conn_target\", \"func_tree_root\", \"add_folder_title\", \"node_lang_panel\", \"user_id\", \"user_name\",\r\n");
      out.write("\t\t\t\t\"group_name\", \"login_time\", \"functions\", \"favorties\", \"no_f_node_selected\", \"system_error\", \"save_success\", \"save_result_title\", \"save_fail\", \"add_success\",\r\n");
      out.write("\t\t\t\t\"add_result_title\", \"add_fail\", \"clear_user_folder\", \"clear_user_folder_title\", \"delete_fail\", \"delete_success\", \"delete_confirm_title\", \"delete_confirm_msg\",\r\n");
      out.write("\t\t\t\t\"delete_result_title\", \"clear_success\", \"clear_result_title\", \"clear_fail\", \"clear_success\", \"clear_fail\", \"kanban_not_exist\", \"favor_exist\", \"not_program\",\r\n");
      out.write("\t\t\t\t\"load_fail\", \"add_favorite\", \"logout\", \"kanban_loop\", \"logout_confirm\", \"logout_fail\", \"confirm_title\", \"no_kanban_can_show\", \"confirm_title\", \"data_lose_warning\",\r\n");
      out.write("\t\t\t\t\"clear_favor_folder_title\", \"clear_favor_folder\", \"get_task_gap_fail\", \"cus_format\", \"have_node_warning\", \"remove_node_before_delete\" ];\r\n");
      out.write("\t\tvar keys = localKeys.concat(globeKeys);\r\n");
      out.write("\t\tthis.dcistore.setMultiLangKeys(keys);\r\n");
      out.write("\t\tvar loginKey = '");
      if (_jspx_meth_dcitag_005freqParam_005f0(_jspx_page_context))
        return;
      out.write("';\r\n");
      out.write("\t\tthis.dcistore.setLoginKey(loginKey);\r\n");
      out.write("\t\tthis.dcistore.load(function(records) {\r\n");
      out.write("\t\t\tif (records != null && records.length == 1) {\r\n");
      out.write("\t\t\t\tvar langs = buildMultiLangObjct(keys, records[0].get('langValues'));\r\n");
      out.write("\t\t\t\tposvalue = records[0].get('dcitagValue');\r\n");
      out.write("\r\n");
      out.write("\t\t\t\tExt.Ajax.request({\r\n");
      out.write("\t\t\t\t\tmethod : 'POST',\r\n");
      out.write("\t\t\t\t\turl : './../../PublicCtrl.dsc',\r\n");
      out.write("\t\t\t\t\tparams : {\r\n");
      out.write("\t\t\t\t\t\tDCITag : posvalue,\r\n");
      out.write("\t\t\t\t\t\tuid : records[0].get('userID'),\r\n");
      out.write("\t\t\t\t\t\taction : 'getuid',\r\n");
      out.write("\t\t\t\t\t\tkey : loginKey\r\n");
      out.write("\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\tsuccess : function(a) {\r\n");
      out.write("\t\t\t\t\t\tif (a.responseText.indexOf(\"@dcifiltererrtag@$\") != -1) {\r\n");
      out.write("\t\t\t\t\t\t\tvar result = a.responseText.split('$');\r\n");
      out.write("\t\t\t\t\t\t\tif (result.length >= 2) {\r\n");
      out.write("\t\t\t\t\t\t\t\tvar resultdata = Ext.JSON.decode(result[1]);\r\n");
      out.write("\t\t\t\t\t\t\t\tExt.Msg.alert(langs.errmsg, resultdata.msg, function() {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tiserror = true;\r\n");
      out.write("\t\t\t\t\t\t\t\t\twindow.location = resultdata.result;\r\n");
      out.write("\t\t\t\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\tvar result = Ext.JSON.decode(a.responseText);\r\n");
      out.write("\t\t\t\t\t\t\tif (result.result) {\r\n");
      out.write("\t\t\t\t\t\t\t\tidxuid = result.uid;\r\n");
      out.write("\t\t\t\t\t\t\t\tshowIndexPage(records[0].get('dcitagValue'), records[0].get('frowardTagValue'), langs, result.uid);\r\n");
      out.write("\t\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\t\tiserror = true;\r\n");
      out.write("\t\t\t\t\t\t\t\tpagelogout();\r\n");
      out.write("\t\t\t\t\t\t\t\twindow.location = \"/KanBan/FuncViews/Main/ErrorPage.jsp?errcode=dcie01&ltp=\" + records[0].get('langType');\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\tfailure : function(f, a) {\r\n");
      out.write("\t\t\t\t\t\tExt.MessageBox.alert(langs.errmsg, langs.system_error);\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\r\n");
      out.write("\t});\r\n");
      out.write("\r\n");
      out.write("\t/* function test() {\r\n");
      out.write("\t\talert(\"1234\");\r\n");
      out.write("\t\tif (mainPanel == null) {\r\n");
      out.write("\t\t\talert(\"null\");\r\n");
      out.write("\t\t} else {\r\n");
      out.write("\t\t\tvar o = new Object();\r\n");
      out.write("\t\t\to[\"func_package\"] = \"EKB\";\r\n");
      out.write("\t\t\to[\"url\"] = \"./../../FuncViews/Funcs/EKB/KanBan.jsp\";\r\n");
      out.write("\t\t\to[\"text\"] = \"test\";\r\n");
      out.write("\t\t\to[\"can_edit\"] = \"0\";\r\n");
      out.write("\t\t\to[\"filter\"] = \"\";\r\n");
      out.write("\t\t\tmainPanel.beforeAddCheck(\"K0010\", \"C002\", o, false);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t} */\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body onunload=\"pagelogout()\">\r\n");
      out.write("\t<form action=\"./../../Login.dsc\" method=\"POST\" id=\"logoutform\">\r\n");
      out.write("\t\t<input type=\"hidden\" id=\"logoutaction\" name=\"action\" /> <input type=\"hidden\" id=\"logouttag\" name=\"DCITag\" />\r\n");
      out.write("\t</form>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
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

  private boolean _jspx_meth_dcitag_005fmultiLang_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  dcitag:multiLang
    com.dsc.dci.jweb.aptags.getMultiLanguage _jspx_th_dcitag_005fmultiLang_005f0 = (new com.dsc.dci.jweb.aptags.getMultiLanguage());
    _jsp_instancemanager.newInstance(_jspx_th_dcitag_005fmultiLang_005f0);
    _jspx_th_dcitag_005fmultiLang_005f0.setJspContext(_jspx_page_context);
    // /FuncViews/Main/Index.jsp(8,7) name = langKey type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fmultiLang_005f0.setLangKey("title_index");
    _jspx_th_dcitag_005fmultiLang_005f0.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005fmultiLang_005f0);
    return false;
  }

  private boolean _jspx_meth_dcitag_005fextLangFile_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  dcitag:extLangFile
    com.dsc.dci.jweb.aptags.getExtLangFilePath _jspx_th_dcitag_005fextLangFile_005f0 = (new com.dsc.dci.jweb.aptags.getExtLangFilePath());
    _jsp_instancemanager.newInstance(_jspx_th_dcitag_005fextLangFile_005f0);
    _jspx_th_dcitag_005fextLangFile_005f0.setJspContext(_jspx_page_context);
    // /FuncViews/Main/Index.jsp(18,36) name = langDirPath type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fextLangFile_005f0.setLangDirPath("./../../extjs/js");
    // /FuncViews/Main/Index.jsp(18,36) name = useType type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fextLangFile_005f0.setUseType("1");
    _jspx_th_dcitag_005fextLangFile_005f0.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005fextLangFile_005f0);
    return false;
  }

  private boolean _jspx_meth_dcitag_005fmultiLang_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  dcitag:multiLang
    com.dsc.dci.jweb.aptags.getMultiLanguage _jspx_th_dcitag_005fmultiLang_005f1 = (new com.dsc.dci.jweb.aptags.getMultiLanguage());
    _jsp_instancemanager.newInstance(_jspx_th_dcitag_005fmultiLang_005f1);
    _jspx_th_dcitag_005fmultiLang_005f1.setJspContext(_jspx_page_context);
    // /FuncViews/Main/Index.jsp(39,21) name = langKey type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fmultiLang_005f1.setLangKey("logout_confirm");
    _jspx_th_dcitag_005fmultiLang_005f1.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005fmultiLang_005f1);
    return false;
  }

  private boolean _jspx_meth_dcitag_005fmultiLang_005f2(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  dcitag:multiLang
    com.dsc.dci.jweb.aptags.getMultiLanguage _jspx_th_dcitag_005fmultiLang_005f2 = (new com.dsc.dci.jweb.aptags.getMultiLanguage());
    _jsp_instancemanager.newInstance(_jspx_th_dcitag_005fmultiLang_005f2);
    _jspx_th_dcitag_005fmultiLang_005f2.setJspContext(_jspx_page_context);
    // /FuncViews/Main/Index.jsp(42,11) name = langKey type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fmultiLang_005f2.setLangKey("logout_confirm");
    _jspx_th_dcitag_005fmultiLang_005f2.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005fmultiLang_005f2);
    return false;
  }

  private boolean _jspx_meth_dcitag_005freqParam_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  dcitag:reqParam
    com.dci.jweb.DCITags.Request.getReqParameter _jspx_th_dcitag_005freqParam_005f0 = (new com.dci.jweb.DCITags.Request.getReqParameter());
    _jsp_instancemanager.newInstance(_jspx_th_dcitag_005freqParam_005f0);
    _jspx_th_dcitag_005freqParam_005f0.setJspContext(_jspx_page_context);
    // /FuncViews/Main/Index.jsp(84,18) name = paramName type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005freqParam_005f0.setParamName("key");
    _jspx_th_dcitag_005freqParam_005f0.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005freqParam_005f0);
    return false;
  }
}