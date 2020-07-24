package com.ynhenc.gis.model.style;

public abstract class Align {

	protected Align(int value) {
		this.value = value;
	}

	public boolean equals(Align align) {
		return ( this == align ) || ( align != null ? ( this.value == align.value ) : false );
	}

	protected int value;

}
