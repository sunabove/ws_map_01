package com.ynhenc.comm.db;

import java.io.*;

import com.ynhenc.comm.GisComLib;

public abstract class Encoding extends GisComLib {

	public abstract String fromDb(String text);

	public abstract String toDb(String text);

	protected String convert(String text, String srcEnc, String desEnc) {
		if (srcEnc.equals(desEnc)) {
			return text;
		} else {
			try {
				return new String(text.getBytes(srcEnc), desEnc);
			} catch (Exception e) {
				e.printStackTrace();
				return text;
			}
		}
	}

}
