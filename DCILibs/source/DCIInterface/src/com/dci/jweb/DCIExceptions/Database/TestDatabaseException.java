package com.dci.jweb.DCIExceptions.Database;

import java.io.PrintStream;
import java.io.PrintWriter;

public class TestDatabaseException extends Exception {
	public TestDatabaseException(String msg) {
		super("test connection fail : " + msg);
	}

	@Override
	public void printStackTrace() {
		System.err.println(this);
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		System.err.println(this);
	}

	@Override
	public void printStackTrace(PrintStream s) {
		System.err.println(this);
	}
}
