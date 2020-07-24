package com.ynhenc.comm.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class SystemUtil {

	public static void waitForSeconds(double sec) {
		sec = Math.abs(sec);
		long start = System.currentTimeMillis(), now = start;
		while (Math.abs(now - start) < sec * 1000) {
			try {
				// message( cls, "Waiting now ......." );
				Thread.sleep(100);
				now = System.currentTimeMillis();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// message( cls, "Done waiting.");
	}

	public static int getResolution() {

		int dpi = -1;
		try {
			dpi = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
			return dpi;
		} catch (Exception e) {
			System.setProperty("java.awt.headless", "true");

			try {
				dpi = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
				return dpi;
			} catch (Exception e1) {
				e.printStackTrace();
				e1.printStackTrace();
			}
		}

		if (isWindowOs()) {
			dpi = 96; // Windows ....
		} else {
			dpi = 98; // Aix Unix .....
		}

		return dpi;
	}

	public static boolean isWindowOs() {
		return getOsName().toUpperCase().startsWith("WIN");
	}

	public static String getOsName() {
		return System.getProperty("os.name");
	}

	private void initSystemOut() {
		java.util.Date now = DateUtil.getNowDate();
		String date_info = now.toString();
		String fileName = "kernel_out_" + date_info + ".txt";
		fileName.replaceAll(" ", "_");
		File file = new File(fileName);
		try {
			OutputStream out = new FileOutputStream(file);
			PrintStream prnt = new PrintStream(out);
			System.setOut(prnt);
		} catch (FileNotFoundException ex) {
		}

	}

	public static void main(String[] args) throws Exception {
		System.out.println("os.name = " + getOsName());
		System.out.println("isWindowOs = " + isWindowOs());
		System.out.println("getResolution = " + getResolution());

		oracle.jdbc.pool.OracleConnectionPoolDataSource a = new oracle.jdbc.pool.OracleConnectionPoolDataSource();

	}

}
