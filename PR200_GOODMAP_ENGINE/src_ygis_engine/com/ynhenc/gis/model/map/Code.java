package com.ynhenc.gis.model.map;

import java.util.Hashtable;

public class Code {

	public int getValue() {
		return value;
	}

	private Code(int value) {
		super();
		this.value = value;
	}

	@Override
	public String toString() {
		return "" + this.getValue();
	}

	public static Code getCode(int value) {
		Code code = codeList.get(value);
		if (code == null) {
			code = new Code(value);
			codeList.put(value, code);
		}
		return code;
	}

	private int value;

	private static final Hashtable<Integer, Code> codeList = new Hashtable<Integer, Code>();
}
