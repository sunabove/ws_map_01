package com.ynhenc.droute;

public class PntShort implements PntInterface {

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public boolean equals( PntShort obj ) {
		if( obj != null ){
			return ( this.x == obj.x && this.y == obj.y ) ;
		}
		return false ;
	}

	@Override
	public String toString() {
		return "x = " + this.x + ", y= " + this.y;
	}

	protected PntShort( double x, double y ) {
		this.x = (float) x;
		this.y = (float) y;
	}

	public double x;
	public double y;

	public static PntShort pntShort( double x, double y ) {
		return new PntShort( x, y );
	}

}
