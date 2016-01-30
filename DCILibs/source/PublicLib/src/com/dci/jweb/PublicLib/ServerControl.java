package com.dci.jweb.PublicLib;

import java.io.PrintWriter;
import java.net.Socket;

import com.dci.jweb.DCIEnums.Server.ServerStatus;

public class ServerControl {

	private String localhostIP = "127.0.0.1";

	public ServerStatus checkServerStats(int port) {
		Socket socket = null;
		ServerStatus status = ServerStatus.Shutdown;
		try {
			socket = new Socket(this.localhostIP, port);
			if (socket.isConnected()) {
				status = ServerStatus.StartUp;
			}
		} catch (Exception e) {

		} finally {
			try {
				socket.close();
			} catch (Exception ex) {

			}
		}
		return status;
	}

	public void ShutdownServer(int port) {
		Socket socket = null;
		PrintWriter pw = null;
		try {
			socket = new Socket(this.localhostIP, port);
			if (socket.isConnected()) {
				pw = new PrintWriter(socket.getOutputStream(), true);
				pw.println("StopSFTServer");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				pw.close();
			} catch (Exception e) {

			}
		}
	}
}
