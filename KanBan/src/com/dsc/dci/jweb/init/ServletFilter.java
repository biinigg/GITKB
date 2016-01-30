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
import com.dsc.dci.jweb.pub.APConstants;
import com.dsc.dci.jweb.pub.Singleton;

public class ServletFilter implements Filter {

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
		String ruid = httpRequest.getParameter("uid");

		// System.out.println(tpage + " servletfilter " +
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

		if (!tpage.equals("AppReceiver.dsc")) {
			if (new File(cc.getConfigXMLPath(APConstants.getConfigfilename())).exists()) {
				// System.out.println(tpage);
				if (s.getDatabaseStatus()) {
					if (s.getLicenseStatus() || tpage.equals("RegSerial.dsc") || tpage.equals("SysDBConfig.dsc")) {
						Object obj = null;
						// !tpage.equals("LayoutKanBan.dsc") &&
						// !tpage.equals("LKB001.dsc") &&
						// !tpage.equals("LKB002.dsc") &&
						// !tpage.equals("PublicCtrl.dsc")
						if (!tpage.equals("DCIAppDatas.dsc") && !tpage.equals("ImageLoader.dsc")
								&& !tpage.equals("IndKanBan.dsc") && !tpage.equals("IconConfig.dsc")
								&& !tpage.equals("KBExport.dsc")) {
							if ((apc.sessionCheck(httpRequest.getSession(), ruid) && s.userExist(
									apc.getUserInfoFromSession(httpRequest.getSession(), ruid, "user_id"),
									apc.getRequestIP(httpRequest)))
									|| tpage.equals("Login.dsc")
									|| tpage.equals("LoginInfo.dsc")
									|| tpage.equals("RegSerial.dsc")
									|| tpage.equals("SysDBConfig.dsc")
									|| tpage.equals("SystemFuncs.dsc") || tpage.equals("UserSyncRecv.dsc")) {
								obj = httpRequest.getParameter(DCIWebConstants.getPostTagId());

								// System.out.println(tpage + "---" + obj +
								// "---" + ruid + "---"
								// + httpRequest.getParameter("action"));

								if (obj == null || !obj.toString().equals(DCIWebConstants.getPostTagValue())) {
									if (isAJAXRequest(httpRequest)) {
										AJAXRedirect(httpResponse, s.getLanguage(currlang, "dcie02"), "/KanBan");
									} else {
										httpResponse
												.sendRedirect("/KanBan/FuncViews/Main/ErrorPage.jsp?errcode=dcie02&ltp="
														+ currlang);
										return;
									}
								}
							} else {
								if (isAJAXRequest(httpRequest)) {
									AJAXRedirect(httpResponse, s.getLanguage(currlang, "dcie01"), "/KanBan");
								} else {
									httpResponse
											.sendRedirect("/KanBan/FuncViews/Main/ErrorPage.jsp?errcode=dcie01&ltp="
													+ currlang);
									return;
								}
							}
						}

						if (!tpage.equals("LayoutKanBan.dsc") && !tpage.equals("Login.dsc")
								&& !tpage.equals("LoginInfo.dsc") && !tpage.equals("DCIAppDatas.dsc")
								&& !tpage.equals("Index.dsc") && !tpage.equals("RegSerial.dsc")
								&& !tpage.equals("SysDBConfig.dsc") && !tpage.equals("SystemFuncs.dsc")
								&& !tpage.equals("IndKanBan.dsc") && !tpage.equals("PublicCtrl.dsc")) {
							// System.out.println(tpage + " add to lic");
							s.addLicenseUser(apc.getUserInfoFromSession(httpRequest.getSession(), ruid, "user_id"),
									apc.getRequestIP(httpRequest));
						}
						if (!tpage.equals("Login.dsc") && !tpage.equals("LoginInfo.dsc")
								&& !tpage.equals("DCIAppDatas.dsc") && !tpage.equals("RegSerial.dsc")
								&& !tpage.equals("SysDBConfig.dsc") && !tpage.equals("SystemFuncs.dsc")
								&& !tpage.equals("IndKanBan.dsc")) {
							s.addOnlineUser(apc.getUserInfoFromSession(httpRequest.getSession(), ruid, "user_id"),
									apc.getRequestIP(httpRequest));
						}

					} else {
						if (!tpage.equals("DCIAppDatas.dsc")) {
							if (s.isNolicense()) {
								httpResponse.sendRedirect("/KanBan/FuncViews/Configs/RegSerial.jsp");
								return;
							} else {
								if (isAJAXRequest(httpRequest)) {
									AJAXRedirect(httpResponse, s.getLanguage(currlang, "dcie09"), "/KanBan");
								} else {
									httpResponse
											.sendRedirect("/KanBan/FuncViews/Main/ErrorPage.jsp?errcode=dcie09&ltp="
													+ currlang);
									return;
								}
							}
						}
					}
				} else {
					if (!tpage.equals("DCIAppDatas.dsc") && !tpage.equals("SysDBConfig.dsc")) {
						if (isAJAXRequest(httpRequest)) {
							AJAXRedirect(httpResponse, s.getLanguage(currlang, "dcie10"), "/KanBan");
						} else {
							httpResponse.sendRedirect("/KanBan/FuncViews/Main/ErrorPage.jsp?errcode=dcie10&ltp="
									+ currlang);
							return;
						}
					}
				}
			} else {
				if (!tpage.equals("DCIAppDatas.dsc")) {
					if (tpage.equals("SysDBConfig.dsc")) {
						Object obj = null;
						obj = httpRequest.getParameter(DCIWebConstants.getPostTagId());

						if (obj == null || !obj.toString().equals(DCIWebConstants.getPostTagValue())) {
							if (isAJAXRequest(httpRequest)) {
								AJAXRedirect(httpResponse, s.getLanguage(currlang, "dcie02"), "/KanBan");
							} else {
								httpResponse.sendRedirect("/KanBan/FuncViews/Main/ErrorPage.jsp?errcode=dcie02&ltp="
										+ currlang);
								return;
							}
						}
					} else {
						httpResponse.sendRedirect("/KanBan/FuncViews/Configs/SysDBConfig.jsp");
						return;
					}
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

	// private boolean sessionCheck2(HttpSession session, String uid) {
	// boolean ok = false;
	// Enumeration<String> sessions = session.getAttributeNames();
	// System.out.println(DCIWebConstants.getUserInfoTag() + uid);
	// while (sessions.hasMoreElements()) {
	// System.out.println(sessions.nextElement());
	// }
	//
	// if (session == null) {
	// ok = false;
	// } else if (session.getAttribute(DCIWebConstants.getUserInfoTag() + uid)
	// == null) {
	// ok = false;
	// } else {
	// ok = true;
	// }
	// return ok;
	// }

}
