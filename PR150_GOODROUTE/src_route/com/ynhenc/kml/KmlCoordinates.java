package com.ynhenc.kml;

public class KmlCoordinates extends KmlContainer< KmlCoord > {

	public KmlCoordinates() {
		super( "", "" );
	}

	@Override
	public String getTag() {
		return "coordinates" ;
	}

}
