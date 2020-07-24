package com.ynhenc.gis.model.shape;

public class Vertex extends PntShort {

	public boolean isNode() {
		return ( this instanceof Node );
	}

	@Override
	public String toString() {
		return "x = " + this.x + ", y= " + this.y + ", node = " + this.isNode() ;
	}

	public Vertex(double x, double y ) {
		super(x, y);
	}

}
