package com.dsc.dci.jweb.init;

import java.util.Enumeration;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.PublicLib.APControl;
import com.dsc.dci.jweb.pub.Singleton;

public class DCISessionListener {

	//@Override
	// public void sessionCreated(HttpSessionEvent sessionEvent) {
	// // TODO Auto-generated method stub
	// // System.out.println("session create");
	// /*
	// * String ipAddr = "";
	// *
	// * RequestAttributes attribs =
	// * RequestContextHolder.getRequestAttributes();
	// *
	// * if (attribs instanceof NativeWebRequest) { HttpServletRequest request
	// * = (HttpServletRequest) ((NativeWebRequest)
	// * attribs).getNativeRequest(); ipAddr = request.getRemoteAddr(); }
	// * System.out.println("session create :" + ipAddr);
	// * sessionEvent.getSession().setAttribute("IPInSession", ipAddr);
	// */
	//
	// //System.out.println("session Created");
	// }
	//
	// @Override
	// public void sessionDestroyed(HttpSessionEvent sessionEvent) {
	// // String ipAddr = ((ServletRequestAttributes)
	// // RequestContextHolder.currentRequestAttributes()).getRequest()
	// // .getRemoteAddr();
	// //System.out.println("session destroyed");
	// if (sessionEvent.getSession().getAttribute("IPInSession") == null) {
	// // System.out.println("session destroyed");
	// } else {
	// String uid = null;
	// String ipAddr =
	// sessionEvent.getSession().getAttribute("IPInSession").toString();
	// String sessionName = null;
	// Singleton s = Singleton.getInstance();
	// APControl apc = new APControl();
	// // String uid =
	// // apc.getUserInfoFromSession(sessionEvent.getSession(), "user_id");
	//
	// Enumeration<String> sessions =
	// sessionEvent.getSession().getAttributeNames();
	// // System.out.println("session Destroyed : " +
	// // DCIWebConstants.getUserInfoTag() + uid);
	// while (sessions.hasMoreElements()) {
	// sessionName = sessions.nextElement();
	// // System.out.println(sessions.nextElement());
	// if (sessionName.indexOf(DCIWebConstants.getUserInfoTag()) != -1) {
	// uid = sessionName.replace(DCIWebConstants.getUserInfoTag(), "");
	// s.removeLicenseUser(uid, ipAddr);
	// s.removeOnlineUser(uid, ipAddr);
	// }
	// }
	//
	// apc.clearSession(sessionEvent.getSession());
	// // s.removeLicenseUser(uid, ipAddr);
	// // s.removeOnlineUser(uid, ipAddr);
	// // System.out.println("session destroyed : " + ipAddr);
	// }
	// }
	// @Override
	// public void requestDestroyed(ServletRequestEvent arg0) {
	// // TODO Auto-generated method stub
	// }
	//
	// @Override
	// public void requestInitialized(ServletRequestEvent requestEvent) {
	// // TODO Auto-generated method stub
	// HttpServletRequest request = ((HttpServletRequest)
	// requestEvent.getServletRequest());
	// HttpSession session = request.getSession();
	// // String page =
	// //
	// request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")
	// // + 1);
	// if (session.getAttribute("IPInSession") == null) {
	// session.setAttribute("IPInSession",
	// requestEvent.getServletRequest().getRemoteAddr());
	// // System.out.println("request create+
	// // requestEvent.getServletRequest().getRemoteAddr());
	// }
	//
	// }
}
