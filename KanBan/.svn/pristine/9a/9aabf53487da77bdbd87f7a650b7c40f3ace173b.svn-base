package com.dsc.dci.jweb.tasks;

import com.dci.jweb.DCIInterface.Server.IDCITask;
import com.dsc.dci.jweb.pub.Singleton;

public class UserCheck implements Runnable, IDCITask {

	private final String taskid = "usercheck";
	private long gap;
	private boolean keeprun;

	public UserCheck() {
		this.gap = 1800000;
		this.keeprun = true;
	}

	private void todo() {
		Singleton.getInstance().checkUser();
	}

	@Override
	public void run() {
		try {
			while (this.keeprun) {
				try {
					todo();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				Thread.currentThread();
				Thread.sleep(this.gap);
			}
		} catch (InterruptedException e) {

		}
	}

	@Override
	public void setTaskGap(long gap) {
		this.gap = gap;
	}

	@Override
	public String getTaskID() {
		return this.taskid;
	}

	@Override
	public void stopTask() {
		this.keeprun = false;
	}

}
