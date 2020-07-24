package com.ynhenc.gis.model.shape;

public class OffSet {

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String toString() {
		return "offset(" + x + "," + y  + ")" ;
	}

	public OffSet(double x, double y) {
		this.x = x;
		this.y = y;
	} 
	
	private double x;
	private double y;

}
