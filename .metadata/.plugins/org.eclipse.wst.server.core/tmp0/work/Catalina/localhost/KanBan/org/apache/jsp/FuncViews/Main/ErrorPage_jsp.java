/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2016-01-13 07:46:44 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.FuncViews.Main;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class ErrorPage_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"./../../extjs/resources/css/ext-all.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"./../../dcicss/dcicss.css\" />\r\n");
      out.write("<script type=\"text/javascript\" src=\"./../../extjs/js/ext-all.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./../../extjs/js/ext-lang-zh_CN.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" charset=\"UTF-8\">\r\n");
      out.write("\tExt.onReady(function() {\r\n");
      out.write("\t\tvar langs;\r\n");
      out.write("\t\tvar keys = [ \"errmsg\", \"back_to_login\" ];\r\n");
      out.write("\r\n");
      out.write("\t\tsetMultiLangKeys(keys);\r\n");
      out.write("\t\tthis.dcistore.load(function(records) {\r\n");
      out.write("\t\t\tif (records != null && records.length == 1) {\r\n");
      out.write("\t\t\t\tlangs = buildMultiLangObjct(keys, records[0].get('langValues'));\r\n");
      out.write("\t\t\t\tshowErrPage();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\tfunction showErrPage() {\r\n");
      out.write("\t\t\tif (langs.errmsg == \"errmsg\") {\r\n");
      out.write("\t\t\t\tlangs.errmsg = \"錯誤訊息\";\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tif (langs.back_to_login == \"back_to_login\") {\r\n");
      out.write("\t\t\t\tlangs.back_to_login = \"回登入頁面\";\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\tvar code = document.getElementById(\"ErrorContent\").innerHTML.replace(/^\\s+/, \"\").replace(/\\s+$/, \"\");\r\n");
      out.write("\r\n");
      out.write("\t\t\tif (code == 'dcie10') {\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"ErrorContent\").innerHTML = \"資料庫連線異常\";\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\tExt.create('Ext.Panel', {\r\n");
      out.write("\t\t\t\ttitle : langs.errmsg,\r\n");
      out.write("\t\t\t\tframe : true,\r\n");
      out.write("\t\t\t\tlayout : 'absolute',\r\n");
      out.write("\t\t\t\tdeferredRender : false,\r\n");
      out.write("\t\t\t\trenderTo : \"ErrorPanel\",\r\n");
      out.write("\t\t\t\tcontentEl : 'ErrorContent',\r\n");
      out.write("\t\t\t\twidth : 500,\r\n");
      out.write("\t\t\t\theight : 150,\r\n");
      out.write("\t\t\t\tbodyPadding : 5,\r\n");
      out.write("\t\t\t\titems : [ {\r\n");
      out.write("\t\t\t\t\txtype : 'button',\r\n");
      out.write("\t\t\t\t\ttext : langs.back_to_login,\r\n");
      out.write("\t\t\t\t\trenderer : \"component\",\r\n");
      out.write("\t\t\t\t\tcheckSession : false,\r\n");
      out.write("\t\t\t\t\twidth : 75,\r\n");
      out.write("\t\t\t\t\tx : 210,\r\n");
      out.write("\t\t\t\t\ty : 90,\r\n");
      out.write("\t\t\t\t\thandler : function() {\r\n");
      out.write("\t\t\t\t\t\twindow.location = \"./../../\";\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t} ]\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<div id=\"ErrorPanel\" align=\"center\"></div>\r\n");
      out.write("\t<div id=\"ErrorContent\" class=\"x-hide-display\">\r\n");
      out.write("\t\t");
      if (_jspx_meth_dcitag_005fmultiLang_005f1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
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

  private boolean _jspx_meth_dcitag_005fmultiLang_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  dcitag:multiLang
    com.dsc.dci.jweb.aptags.getMultiLanguage _jspx_th_dcitag_005fmultiLang_005f0 = (new com.dsc.dci.jweb.aptags.getMultiLanguage());
    _jsp_instancemanager.newInstance(_jspx_th_dcitag_005fmultiLang_005f0);
    _jspx_th_dcitag_005fmultiLang_005f0.setJspContext(_jspx_page_context);
    // /FuncViews/Main/ErrorPage.jsp(8,7) name = langKey type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fmultiLang_005f0.setLangKey("errpage");
    _jspx_th_dcitag_005fmultiLang_005f0.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005fmultiLang_005f0);
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
    // /FuncViews/Main/ErrorPage.jsp(71,2) name = requestTag type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fmultiLang_005f1.setRequestTag("errcode");
    _jspx_th_dcitag_005fmultiLang_005f1.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005fmultiLang_005f1);
    return false;
  }
}
