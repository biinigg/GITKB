package com.dsc.dci.jweb.init;

import java.lang.Thread.State;
import java.util.HashMap;

import com.dci.jweb.DCIInterface.Server.IDCITask;
import com.dci.jweb.PublicLib.DCIString;

public class SystemTimer {
	//private static final Object IDCITask = null;
	private static SystemTimer instance = null;
	private HashMap<String, Thread> tasks;
	private HashMap<String, IDCITask> taskClass;

	public static SystemTimer getInstance() {
		if (instance == null) {
			synchronized (SystemTimer.class) {
				if (instance == null) {
					instance = new SystemTimer();
				}
			}
		}
		return instance;
	}

	public SystemTimer() {
		this.tasks = new HashMap<String, Thread>();
		this.taskClass = new HashMap<String, IDCITask>();
	}

	public void addTask(Runnable task, long gap) {
		if (task != null) {
			String taskid = ((IDCITask) task).getTaskID();
			if (!DCIString.isNullOrEmpty(taskid)) {
				System.out.println("Add Task " + taskid);
				((IDCITask) task).setTaskGap(gap);
				Thread thread = new Thread(task);
				thread.start();
				this.tasks.put(taskid, thread);
				this.taskClass.put(taskid, (IDCITask) task);
			}
		}
	}

	public void stopTask(String taskid) {
		if (this.tasks.containsKey(taskid) && this.taskClass.containsKey(taskid)) {
			this.taskClass.get(taskid).stopTask();

			if (this.tasks.get(taskid).isAlive() && this.tasks.get(taskid).getState() != State.RUNNABLE) {
				this.tasks.get(taskid).interrupt();
			}

		}
	}
}
