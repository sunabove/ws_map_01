package com.ynhenc.comm.registry;

public class RegiItem implements Comparable<RegiItem>{

	public long getKey() {
		return key;
	}

	public void setKey(long key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public RegiItem( ) {
		super();
	}

	@Override
	public int compareTo(RegiItem o) {
		return (int)( this.getKey() - o.getKey() );
	}

	private long key;
	private String value;

}
