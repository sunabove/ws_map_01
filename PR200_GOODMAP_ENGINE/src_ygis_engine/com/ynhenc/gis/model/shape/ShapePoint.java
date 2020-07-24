package com.ynhenc.gis.model.shape;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class ShapePoint extends ShapeObject {

	@Override
	public double getLength() {
		return 0;
	}

	@Override
	public GeoObject getGeoObject() {
		return this.point;
	}

	@Override
	public double getArea() {
		return 0;
	}

	@Override
	public void addGeoPoint(GeoPoint point) {
		this.point = point;
		this.setShape(null);
	}

	public GeoPoint getPoint() {
		return this.point;
	}

	public void setLocation( PntShort point ) {
		this.setGeoObject( new GeoPoint( point.getX() , point.getY() ) );
	}

	public void setGeoObject( GeoPoint geoObject  ) {
		this.point = geoObject;
		this.centroid = null ; // init centroid after update location
	}

	@Override
	public String toGeomText() {
		if (this.point == null) {
			return "NULL";
		} else {
			return "POINT(" + this.point.x + " " + this.point.y + ")";
		}
	}

	@Override
	public String toString() {
		return "ID = " + this.getRecordNo() + ", " + this.point;
	}

	public ShapePoint(int id, int recordNo, double x, double y) {
		this(id, recordNo, new GeoPoint(x, y));
	}

	public ShapePoint(int id, int recordNo, GeoPoint point) {
		super(id, recordNo);
		this.point = point;
	}

	// member
	private GeoPoint point;

	// end of member

}
