package com.dsc.dci.jweb.init;

import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.PublicLib.APControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.patchs.EKBVersionCheck;
import com.dsc.dci.jweb.pub.APPubMethods;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.jweb.tasks.AppPushKanBan;
import com.dsc.dci.jweb.tasks.ConnectionCheck;
import com.dsc.dci.jweb.tasks.MultiLanguageSync;
import com.dsc.dci.jweb.tasks.UserCheck;

public class DCIServletContextListener implements ServletContextListener, HttpSessionListener, ServletRequestListener,
		HttpSessionAttributeListener {
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// System.out.println("session destroyed");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		System.out.println("session destroyed");
		HttpSession session = sessionEvent.getSession();
		Object ip = session.getAttribute("IPInSession");
		if (ip != null) {
			clearAllUser(ip.toString(), session.getAttributeNames());
		}
		if (session != null) {
			new APControl().clearSession(session);
		}
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		event.getSession().getServletContext().setAttribute(event.getName(), event.getValue());
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		event.getSession().getServletContext().removeAttribute(event.getName());
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		event.getSession().getServletContext().removeAttribute(event.getName());
		event.getSession().getServletContext().setAttribute(event.getName(), event.getValue());
	}

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void requestInitialized(ServletRequestEvent requestEvent) {
		// TODO Auto-generated method stub
		HttpServletRequest request = ((HttpServletRequest) requestEvent.getServletRequest());
		HttpSession session = request.getSession();
		String ipaddr = new APControl().getRequestIP(request);
		if (session.getAttribute("IPInSession") == null) {
			session.setAttribute("IPInSession", ipaddr);
		}
		// System.out.println("request created from " + ipaddr);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		Object ip = event.getServletContext().getAttribute("IPInSession");
		if (ip != null) {
			clearAllUser(ip.toString(), event.getServletContext().getAttributeNames());
		}
		System.out.println("context destroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		startProcess(event.getServletContext().getRealPath("/"));
		stratTasks();
	}

	private void startProcess(String contextPath) {
		System.out.println("Start DCI web process");
		System.out.println("Init singleton objects");
		Singleton s = Singleton.getInstance();
		s.setContextPath(contextPath);
		APPubMethods method = new APPubMethods();

		System.out.println("JBoss log directory : " + System.getProperty("jboss.server.log.dir"));
		System.out.println("Build all datasource");
		method.setConnectionPool();
		if (s.getDatabaseStatus()) {
			System.out.println("Load System Config");
			method.loadSystemConfig();
			System.out.println("Check Current Version");
			new EKBVersionCheck();
			// method.checkKanBanLegend();//need move to patch class
			System.out.println("Load Multi Language");
			method.loadMultiLanguage();
			System.out.println("Multi Language Loaded");
			method.checkLicense();
			if (!s.getLicenseStatus()) {
				System.out.println("license check fail");
			}
		} else {
			System.out.println("set connection pool fail");
		}

	}

	private void stratTasks() {
		SystemTimer st = SystemTimer.getInstance();
		st.addTask(new UserCheck(), 10000);
		st.addTask(new MultiLanguageSync(), 1800000);
		st.addTask(new ConnectionCheck(), 180000);
		if (!Singleton.getInstance().isTestArea()) {
			st.addTask(new AppPushKanBan(), 180000);
		}
	}

	private void clearAllUser(String ipAddr, Enumeration<String> sessions) {
		if (DCIString.isNullOrEmpty(ipAddr) || sessions == null) {
			// System.out.println("session destroyed");
		} else {
			String uid = null;
			String sessionName = null;
			Singleton s = Singleton.getInstance();

			while (sessions.hasMoreElements()) {
				sessionName = sessions.nextElement();
				// System.out.println(sessionName);
				if (sessionName.indexOf(DCIWebConstants.getUserInfoTag()) != -1) {
					uid = sessionName.replace(DCIWebConstants.getUserInfoTag(), "");
					s.removeLicenseUser(uid, ipAddr);
					s.removeOnlineUser(uid, ipAddr);
				}
			}
		}
	}
}
