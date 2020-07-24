package com.ynhenc.comm.db;

public class BiEncoding extends Encoding {

	public BiEncoding(String srcCharSet, String destCharSet) {
		this.srcCharSet = srcCharSet;
		this.destCharSet = destCharSet;
	}

	@Override
	public String fromDb(String text) {
		return this.convert(text, this.destCharSet, this.srcCharSet);
	}

	@Override
	public String toDb(String text) {
		return this.convert(text, this.srcCharSet, this.destCharSet);
	}

	private String srcCharSet;
	private String destCharSet;

}
