package com.ynhenc.comm;

import java.io.*;

import com.ynhenc.comm.util.*;

public class Kernel implements DebugInterface {

	public void debug(Exception e) {
		debug.println(this, e);
	}

	public void debug(String msg) {
		debug.println(this, msg);
	}

	public void showStatus() {

		String msg = null;

		if (this.exceptionNo > 0) {
			msg = "Exception " + exceptionNo + " has occured!";
		} else {
			msg = "There are no exceptions occured.";
		}

		this.println(this, msg);

	}

	public void processException(Exception e) {

		this.preProcessException(e);

		e.printStackTrace();

	}

	public void processException(Error e) {

		this.preProcessException(e);

		e.printStackTrace();

	}

	private void preProcessException(Object e) {
		this.exceptionNo++;
	}

	public void println(Object obj, String msg) {

		StringBuffer msgHeader = this.getMessageHeader(obj, this.processDepth, this.msgNo);

		System.out.println(msgHeader.toString() + msg);

		msgNo += 1;

	}

	private StringBuffer getMessageHeader(Object obj, int processDepth, int msgNo) {

		Class klass = (obj instanceof Class) ? (Class) obj : obj.getClass();

		StringBuffer bfr = new StringBuffer();

		for (int i = 0, len = processDepth + 1; i < len; i++) {
			bfr.append("* ");
		}

		bfr.append(msgNo + " [" + ClassUtil.getSimplifiedName(klass) + "] ");

		return bfr;

	}

	private Kernel() {
	}

	// member

	private int exceptionNo = 0;
	private int msgNo = 0;
	private int processDepth = -1;

	// end of member

	// static member

	private final static Kernel KERNEL = new Kernel();

	// end of static member

	// static method

	public static Kernel getKernel() {
		return KERNEL;
	}

	// end of static method

}
