package com.ynhenc.gis.projection;

import com.ynhenc.gis.model.shape.PntShort;

public class Utm extends PntShort {

	private Utm(double x, double y) {
		super(x, y);
	}

	public static Utm utm( double x, double y ) {
		return new Utm( x , y );
	}

}
