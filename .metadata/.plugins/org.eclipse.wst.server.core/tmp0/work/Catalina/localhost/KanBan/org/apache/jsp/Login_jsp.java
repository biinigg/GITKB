/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2016-01-13 03:51:29 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class Login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/./dcitag/dcitag.tld", Long.valueOf(1452650348276L));
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
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=9\">\r\n");
      out.write("<title>");
      if (_jspx_meth_dcitag_005fmultiLang_005f0(_jspx_page_context))
        return;
      out.write("</title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"./extjs/resources/css/ext-all.css\" />\r\n");
      out.write("<script type=\"text/javascript\" src=\"./extjs/js/ext-all.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./dcijs/dci-all.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      if (_jspx_meth_dcitag_005fextLangFile_005f0(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"./FuncViews/js/Login.js\"></script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body style=\"background-color: #E0E8F3;\">\r\n");
      out.write("\t<form action=\"./Login.dsc\" method=\"post\" onsubmit=\"return formCheck();\">\r\n");
      out.write("\t\t<div style=\"width: 100%; height: 100vh; display: table;\">\r\n");
      out.write("\t\t\t<div style=\"display: table-cell; vertical-align: middle;\">\r\n");
      out.write("\t\t\t\t<table border=\"0\" align=\"center\" id=\"loginmain\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 730; height: 550; background-image: url(images/background/KanBan_login_TC.jpg); background-repeat: no-repeat;\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"420\" height=\"185\" style=\"vertical-align: text-top;\"><div id=\"labelver\"></div></td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"294\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"420\" height=\"150\"></td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"294\">\r\n");
      out.write("\t\t\t\t\t\t\t<table width=\"100%\" height=\"150\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr height=\"40\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td width=\"40%\"><div align=\"right\" style=\"padding-right: 10px\" id=\"labelid\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t");
      if (_jspx_meth_dcitag_005fmultiLang_005f1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</div></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td width=\"60%\"><input type=\"text\" id=\"uid\" name=\"uid\" maxlength=\"10\" style=\"width: 90%\"></input></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr height=\"40\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><div align=\"right\" style=\"padding-right: 10px\" id=\"labelpwd\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t");
      if (_jspx_meth_dcitag_005fmultiLang_005f2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</div></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input type=\"password\" id=\"pwd\" name=\"pwd\" maxlength=\"10\" style=\"width: 90%\"></input></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr height=\"40\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><div align=\"right\" style=\"padding-right: 10px\" id=\"labeltp\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t");
      if (_jspx_meth_dcitag_005fmultiLang_005f3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</div></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><select id=\"langtp\" name=\"langtp\" style=\"width: 90%\" ONCHANGE=\"changeLang();\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<option value=\"CHT\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
      if (_jspx_meth_dcitag_005fmultiLang_005f4(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<option value=\"CHS\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
      if (_jspx_meth_dcitag_005fmultiLang_005f5(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</select></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr height=\"30\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td colspan=\"2\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<table width=\"95%\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"right\"><input type=\"button\" id=\"newie\" value=\"");
      if (_jspx_meth_dcitag_005fmultiLang_005f6(_jspx_page_context))
        return;
      out.write("\" style=\"visibility: hidden\" onclick=\"openIE();\"><input type=\"button\" id=\"userlistbtn\" value=\"");
      if (_jspx_meth_dcitag_005fmultiLang_005f7(_jspx_page_context))
        return;
      out.write("\" onclick=\"showUserList();\"><input\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\ttype=\"button\" id=\"resetbtn\" value=\"");
      if (_jspx_meth_dcitag_005fmultiLang_005f8(_jspx_page_context))
        return;
      out.write("\" onclick=\"resetForm();\"><input type=\"submit\" id=\"submit\" value=\"");
      if (_jspx_meth_dcitag_005fmultiLang_005f9(_jspx_page_context))
        return;
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td colspan=\"2\"><input type=\"hidden\" id=\"action\" name=\"action\" /><input type=\"hidden\" id=\"checkcode\" name=\"checkcode\" /> <input type=\"hidden\" id=\"DCITag\" name=\"DCITag\" /></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"420\" height=\"43\"></td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"294\">\r\n");
      out.write("\t\t\t\t\t\t\t<table width=\"100%\" height=\"43\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td align=\"center\">");
      if (_jspx_meth_dcitag_005fmultiLang_005f10(_jspx_page_context))
        return;
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"420\"></td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"294\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</form>\r\n");
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
    // /Login.jsp(9,7) name = langKey type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
    // /Login.jsp(14,36) name = langDirPath type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fextLangFile_005f0.setLangDirPath("./extjs/js");
    // /Login.jsp(14,36) name = useType type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fextLangFile_005f0.setUseType("0");
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
    // /Login.jsp(33,11) name = langKey type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fmultiLang_005f1.setLangKey("user_id");
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
    // /Login.jsp(39,11) name = langKey type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fmultiLang_005f2.setLangKey("user_pwd");
    _jspx_th_dcitag_005fmultiLang_005f2.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005fmultiLang_005f2);
    return false;
  }

  private boolean _jspx_meth_dcitag_005fmultiLang_005f3(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  dcitag:multiLang
    com.dsc.dci.jweb.aptags.getMultiLanguage _jspx_th_dcitag_005fmultiLang_005f3 = (new com.dsc.dci.jweb.aptags.getMultiLanguage());
    _jsp_instancemanager.newInstance(_jspx_th_dcitag_005fmultiLang_005f3);
    _jspx_th_dcitag_005fmultiLang_005f3.setJspContext(_jspx_page_context);
    // /Login.jsp(45,11) name = langKey type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fmultiLang_005f3.setLangKey("lang_type");
    _jspx_th_dcitag_005fmultiLang_005f3.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005fmultiLang_005f3);
    return false;
  }

  private boolean _jspx_meth_dcitag_005fmultiLang_005f4(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  dcitag:multiLang
    com.dsc.dci.jweb.aptags.getMultiLanguage _jspx_th_dcitag_005fmultiLang_005f4 = (new com.dsc.dci.jweb.aptags.getMultiLanguage());
    _jsp_instancemanager.newInstance(_jspx_th_dcitag_005fmultiLang_005f4);
    _jspx_th_dcitag_005fmultiLang_005f4.setJspContext(_jspx_page_context);
    // /Login.jsp(49,12) name = langKey type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fmultiLang_005f4.setLangKey("lang_cht");
    _jspx_th_dcitag_005fmultiLang_005f4.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005fmultiLang_005f4);
    return false;
  }

  private boolean _jspx_meth_dcitag_005fmultiLang_005f5(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  dcitag:multiLang
    com.dsc.dci.jweb.aptags.getMultiLanguage _jspx_th_dcitag_005fmultiLang_005f5 = (new com.dsc.dci.jweb.aptags.getMultiLanguage());
    _jsp_instancemanager.newInstance(_jspx_th_dcitag_005fmultiLang_005f5);
    _jspx_th_dcitag_005fmultiLang_005f5.setJspContext(_jspx_page_context);
    // /Login.jsp(52,12) name = langKey type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fmultiLang_005f5.setLangKey("lang_chs");
    _jspx_th_dcitag_005fmultiLang_005f5.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005fmultiLang_005f5);
    return false;
  }

  private boolean _jspx_meth_dcitag_005fmultiLang_005f6(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  dcitag:multiLang
    com.dsc.dci.jweb.aptags.getMultiLanguage _jspx_th_dcitag_005fmultiLang_005f6 = (new com.dsc.dci.jweb.aptags.getMultiLanguage());
    _jsp_instancemanager.newInstance(_jspx_th_dcitag_005fmultiLang_005f6);
    _jspx_th_dcitag_005fmultiLang_005f6.setJspContext(_jspx_page_context);
    // /Login.jsp(60,69) name = langKey type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fmultiLang_005f6.setLangKey("newie");
    _jspx_th_dcitag_005fmultiLang_005f6.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005fmultiLang_005f6);
    return false;
  }

  private boolean _jspx_meth_dcitag_005fmultiLang_005f7(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  dcitag:multiLang
    com.dsc.dci.jweb.aptags.getMultiLanguage _jspx_th_dcitag_005fmultiLang_005f7 = (new com.dsc.dci.jweb.aptags.getMultiLanguage());
    _jsp_instancemanager.newInstance(_jspx_th_dcitag_005fmultiLang_005f7);
    _jspx_th_dcitag_005fmultiLang_005f7.setJspContext(_jspx_page_context);
    // /Login.jsp(60,199) name = langKey type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fmultiLang_005f7.setLangKey("user_list");
    _jspx_th_dcitag_005fmultiLang_005f7.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005fmultiLang_005f7);
    return false;
  }

  private boolean _jspx_meth_dcitag_005fmultiLang_005f8(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  dcitag:multiLang
    com.dsc.dci.jweb.aptags.getMultiLanguage _jspx_th_dcitag_005fmultiLang_005f8 = (new com.dsc.dci.jweb.aptags.getMultiLanguage());
    _jsp_instancemanager.newInstance(_jspx_th_dcitag_005fmultiLang_005f8);
    _jspx_th_dcitag_005fmultiLang_005f8.setJspContext(_jspx_page_context);
    // /Login.jsp(61,48) name = langKey type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fmultiLang_005f8.setLangKey("reset");
    _jspx_th_dcitag_005fmultiLang_005f8.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005fmultiLang_005f8);
    return false;
  }

  private boolean _jspx_meth_dcitag_005fmultiLang_005f9(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  dcitag:multiLang
    com.dsc.dci.jweb.aptags.getMultiLanguage _jspx_th_dcitag_005fmultiLang_005f9 = (new com.dsc.dci.jweb.aptags.getMultiLanguage());
    _jsp_instancemanager.newInstance(_jspx_th_dcitag_005fmultiLang_005f9);
    _jspx_th_dcitag_005fmultiLang_005f9.setJspContext(_jspx_page_context);
    // /Login.jsp(61,149) name = langKey type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fmultiLang_005f9.setLangKey("login");
    _jspx_th_dcitag_005fmultiLang_005f9.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005fmultiLang_005f9);
    return false;
  }

  private boolean _jspx_meth_dcitag_005fmultiLang_005f10(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  dcitag:multiLang
    com.dsc.dci.jweb.aptags.getMultiLanguage _jspx_th_dcitag_005fmultiLang_005f10 = (new com.dsc.dci.jweb.aptags.getMultiLanguage());
    _jsp_instancemanager.newInstance(_jspx_th_dcitag_005fmultiLang_005f10);
    _jspx_th_dcitag_005fmultiLang_005f10.setJspContext(_jspx_page_context);
    // /Login.jsp(78,28) name = requestTag type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_dcitag_005fmultiLang_005f10.setRequestTag("ecode");
    _jspx_th_dcitag_005fmultiLang_005f10.doTag();
    _jsp_instancemanager.destroyInstance(_jspx_th_dcitag_005fmultiLang_005f10);
    return false;
  }
}
