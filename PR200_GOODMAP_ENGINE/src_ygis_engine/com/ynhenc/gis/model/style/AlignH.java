package com.ynhenc.gis.model.style;

public class AlignH extends Align {

	private AlignH( int value ) {
		super( value );
	}

	public final static AlignH LEFT = new AlignH( 0 );

	public final static AlignH CENTER = new AlignH( 1 );

	public final static AlignH RIGHT = new AlignH( 2 );

}
