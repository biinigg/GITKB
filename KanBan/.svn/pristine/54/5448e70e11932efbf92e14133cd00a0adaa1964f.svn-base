package com.dsc.dci.jweb.tasks;

import com.dci.jweb.DCIInterface.Server.IDCITask;
import com.dsc.dci.jweb.pub.APPubMethods;

public class MultiLanguageSync  implements Runnable, IDCITask{
	private final String taskid = "langsync";
	private long gap;
	private boolean keeprun;

	public MultiLanguageSync() {
		this.gap = 1800000;
		this.keeprun = true;
	}

	private void todo() {
		new APPubMethods().loadMultiLanguage();
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
