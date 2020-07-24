package com.ynhenc.gis.model.shape;

import com.ynhenc.gis.projection.UtmToWgs;
import com.ynhenc.gis.projection.WgsToUtm;

public class PntShort {

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public boolean equals( PntShort obj ) {
		if( obj != null ){
			return ( this.x == obj.x && this.y == obj.y ) ;
		}
		return false ;
	}

	public PntShort getUtmToWgs() {
		UtmToWgs cv = new UtmToWgs();
		return cv.getConvertValue( this );
	}

	public PntShort getWgsToUtm() {
		WgsToUtm cv = new WgsToUtm();
		return cv.getConvertValue( this );
	}

	@Override
	public String toString() {
		return "x = " + this.x + ", y= " + this.y;
	}

	public PntShort( double x, double y ) {
		this.x = (float) x;
		this.y = (float) y;
	}

	public float x;
	public float y;

	public static PntShort pntShort( double x, double y ) {
		return new PntShort( x, y );
	}

}
