package com.ynhenc.gis.model.style;

public class AlignV extends Align {

	private AlignV(int value) {
		super(value);
	}

	public final static AlignV TOP = new AlignV(3);

	public final static AlignV MIDDLE = new AlignV(4);

	public final static AlignV BOTTOM = new AlignV(5);

}
