package com.dsc.dci.jweb.init;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.DCIExceptions.Server.FilterException;
import com.dci.jweb.PublicLib.APControl;
import com.dci.jweb.PublicLib.ConfigControl;
import com.dci.jweb.PublicLib.DCIHashMap;
import com.dci.jweb.PublicLib.DCIString;
import com.dci.jweb.PublicLib.Register.RegisterObject;
import com.dsc.dci.jweb.pub.APConstants;
import com.dsc.dci.jweb.pub.Singleton;

public class JSPFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String tpage = httpRequest.getRequestURI().substring(httpRequest.getRequestURI().lastIndexOf("/") + 1);
		httpResponse.setHeader("Cache-Control", "no-cache");
		httpResponse.setHeader("Pragma", "no-cache");
		httpResponse.setDateHeader("Expires", -1);
		String ruid = httpRequest.getParameter("uid");

		if (tpage.equals("Index.jsp")) {
			ruid = DCIString.strDecode(httpRequest.getParameter("key")).split("\\^")[0];
		}

		// System.out.println(tpage + " jspfilter " +
		// httpRequest.getParameter("uid"));

		if (tpage.indexOf(";") != -1) {
			tpage = tpage.substring(0, tpage.indexOf(";"));
		}
		Singleton s = Singleton.getInstance();
		ConfigControl cc = new ConfigControl(s.isTestArea(), s.getTestAreaConfigPath());
		APControl apc = new APControl();
		String currlang = apc.getUserInfoFromSession(httpRequest.getSession(), ruid, "lang");
		if (DCIString.isNullOrEmpty(currlang)) {
			currlang = s.getDeflang();
		}
		if (new File(cc.getConfigXMLPath(APConstants.getConfigfilename())).exists()) {
			if (s.getDatabaseStatus()) {
				if (s.getLicenseStatus()) {
					Object obj = null;
					if (tpage.equals("") || tpage.length() == 0 || tpage.equals("Login.jsp")) {
						// new
						// APControl().clearSession(httpRequest.getSession());
						// new Login().loginOut(httpRequest);
					} else if (tpage.equals("ErrorPage.jsp") || tpage.equals("SysDBConfig.jsp")
							|| tpage.equals("SystemFuncs.jsp") || tpage.equals("RegSerial.jsp")
							|| tpage.equals("test.jsp")) {
						// tpage.equals("IndLayoutKanBan.jsp") ||
						// tpage.equals("IndLKB001.jsp") ||
						// tpage.equals("IndLKB002.jsp") ||
						// dci public page throw tunnel
					} else if (tpage.equals("IndKanBan.jsp")) {
						String ipaddr = apc.getRequestIP(httpRequest);
						String uid = apc.getUserInfoFromSession(httpRequest.getSession(), ruid, "user_id");
						if (s.licUserExist(uid, ipaddr)) {
							s.addLicenseUser(uid, ipaddr);
						} else {
							if (s.getUsedLicense() >= RegisterObject.getInstance().getMaxUser()) {
								// if (s.getUsedLicense() >= s.getUserLimit()) {
								AJAXRedirect(httpResponse, s.getLanguage(currlang, "dcie11"), "/KanBan");
							} else {
								s.addLicenseUser(uid, ipaddr);
							}
						}
					} else {
						if (apc.sessionCheck(httpRequest.getSession(), ruid)
								&& s.userExist(apc.getUserInfoFromSession(httpRequest.getSession(), ruid, "user_id"),
										apc.getRequestIP(httpRequest))) {
							if (tpage.equals("Index.jsp")) {
								obj = httpRequest.getSession().getAttribute(
										DCIWebConstants.getSessionForwardTagId() + ruid);
								// httpRequest.getSession().removeAttribute(DCIWebConstants.getForwardTagId());
							} else {
								obj = httpRequest.getParameter(DCIWebConstants.getForwardTagId());
							}

							if (obj == null || !obj.toString().equals(DCIWebConstants.getForwardTagValue())) {
								apc.clearSession(httpRequest.getSession());
								if (isAJAXRequest(httpRequest)) {
									AJAXRedirect(httpResponse, s.getLanguage(currlang, "dcie03"), "/KanBan");
								} else {
									httpResponse
											.sendRedirect("/KanBan/FuncViews/Main/ErrorPage.jsp?errcode=dcie03&ltp="
													+ currlang);
									return;
								}
							}
						} else {
							if (isAJAXRequest(httpRequest)) {
								AJAXRedirect(httpResponse, s.getLanguage(currlang, "dcie01"), "/KanBan");
							} else {
								httpResponse.sendRedirect("/KanBan/FuncViews/Main/ErrorPage.jsp?errcode=dcie01&ltp="
										+ currlang);
								return;
							}
						}

						String ipaddr = apc.getRequestIP(httpRequest);
						String uid = apc.getUserInfoFromSession(httpRequest.getSession(), ruid, "user_id");

						if (!tpage.equals("LayoutKanBan.jsp") && !tpage.equals("Login.jsp")
								&& !tpage.equals("Index.jsp")) {
							if (s.licUserExist(uid, ipaddr)) {
								s.addLicenseUser(uid, ipaddr);
							} else {
								if (s.getUsedLicense() >= RegisterObject.getInstance().getMaxUser()) {
									// if (s.getUsedLicense() >=
									// s.getUserLimit()) {
									AJAXRedirect(httpResponse, s.getLanguage(currlang, "dcie11"), "");
								} else {
									s.addLicenseUser(uid, ipaddr);
								}
							}
						}
						if (!tpage.equals("Login.jsp")) {
							s.addOnlineUser(uid, ipaddr);
						}
					}
				} else {
					if (!tpage.equals("ErrorPage.jsp") && !tpage.equals("RegSerial.jsp")
							&& !tpage.equals("SysDBConfig.jsp") && !tpage.equals("SystemFuncs.jsp")) {
						if (s.isNolicense()) {
							if (isAJAXRequest(httpRequest)) {
								AJAXRedirect(httpResponse, s.getLanguage(currlang, "no license"),
										"/KanBan/FuncViews/Configs/RegSerial.jsp");
							} else {
								httpResponse.sendRedirect("/KanBan/FuncViews/Configs/RegSerial.jsp");
								return;
							}
						} else {
							if (isAJAXRequest(httpRequest)) {
								AJAXRedirect(httpResponse, s.getLanguage(currlang, "dcie09"), "/KanBan");
							} else {
								httpResponse.sendRedirect("/KanBan/FuncViews/Main/ErrorPage.jsp?errcode=dcie09&ltp="
										+ currlang);
								return;
							}
						}
					}
				}

			} else {
				if (!tpage.equals("ErrorPage.jsp") && !tpage.equals("SysDBConfig.jsp")) {
					if (isAJAXRequest(httpRequest)) {
						AJAXRedirect(httpResponse, s.getLanguage(currlang, "dcie10"), "/KanBan");
					} else {
						httpResponse
								.sendRedirect("/KanBan/FuncViews/Main/ErrorPage.jsp?errcode=dcie10&ltp=" + currlang);
						return;
					}
				}
			}
		} else {
			if (!tpage.equals("SysDBConfig.jsp")) {
				if (isAJAXRequest(httpRequest)) {
					AJAXRedirect(httpResponse, s.getLanguage(currlang, "config not found"),
							"/KanBan/FuncViews/Configs/SysDBConfig.jsp");
				} else {
					httpResponse.sendRedirect("/KanBan/FuncViews/Configs/SysDBConfig.jsp");
					return;
				}
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub

	}

	private boolean isAJAXRequest(HttpServletRequest request) {
		boolean check = false;
		String facesRequest = request.getHeader("x-requested-with");
		if (facesRequest != null && facesRequest.equalsIgnoreCase("XMLHttpRequest")) {
			check = true;
		}

		return check;
	}

	private void AJAXRedirect(HttpServletResponse response, String msg, String result) throws FilterException,
			IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print("@dcifiltererrtag@$" + DCIHashMap.hashToJson(DCIHashMap.buildResponseData(msg, result)));
		pw.flush();
		// throw new ServletException("filter exception");
		throw new FilterException();
	}

}
