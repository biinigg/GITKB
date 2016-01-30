package com.dci.jweb.DCIExceptions.Server;

import java.io.PrintWriter;
import java.io.PrintStream;

import javax.servlet.ServletException;

public class FilterException extends ServletException {
	public FilterException() {
		super("Filter Exception");
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
