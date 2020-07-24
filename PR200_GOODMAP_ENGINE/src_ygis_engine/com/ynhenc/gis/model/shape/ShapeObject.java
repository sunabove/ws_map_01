package com.ynhenc.gis.model.shape;

import java.awt.Shape;
import java.awt.geom.Area;
import java.sql.*;

import com.ynhenc.gis.*;
import com.ynhenc.gis.model.mapobj.*;

public abstract class ShapeObject {

	public int getId() {
		return this.id;
	}

	public int getRecordNo() {
		return this.recordNo;
	}

	public int getFid() {
		return this.recordNo - 1;
	}

	public void setMbr(Mbr mbr) {
		this.mbr = mbr;
	}

	public final Mbr getMbr() {
		return this.getGeoObject().getMbr();
	}

	public final boolean isIntersects(Mbr mbr) {
		return this.getGeoObject().isIntersects(mbr);
	}

	public GeoPoint getCentroid() {

		if (this.centroid == null) {
			this.centroid = this.getGeoObject().getCentroid();
		}

		return this.centroid;
	}

	public void setCentroid(GeoPoint centroid) {
		this.centroid = centroid;
	}

	@Override
	public String toString() {
		return "GeomFromText(\'" + this.toGeomText() + "\')";
	}

	public boolean isNeedClipping( Shape shape, Projection projection ) {
		return
		( shape.getBounds2D().getWidth() > projection.getPixelWidth() * 4  )
		|| ( shape.getBounds2D().getHeight() > projection.getPixelHeight() * 4  )
		 ;
	}

	public Shape getShape(Projection projection) {

		if (this.getGeoObject() != null) {
			Shape shape = this.getGeoObject().createShape(projection);
			if( this instanceof ShapeLinePoi) {
				return shape;
			}
			if ( this.isNeedClipping(shape, projection) ) {
				Area area = new Area(shape);
				area.intersect( projection.getClippingRect() );
				return area;
			} else {
				return shape;
			}
		} else {
			return null;
		}

	}

	public void setShape(java.awt.Shape shape) {
		// this.shape = shape;
	}

	public double getLength() {
		GeoObject poly = this.getGeoObject();
		if (poly != null) {
			return poly.getLength();
		}
		return 0;
	}

	public final GeoPoint calcCentroid() {
		return null;
	}

	public final Mbr calcMbr() {
		return null;
	}

	public GeoPoint[] getPointList() {
		return this.getGeoObject().getPointList();
	}

	public boolean isContainedByCircle(GeoPoint center, double radius) {
		return this.getGeoObject().isContainedByCircleRadium(center, radius * radius);
	}

	public ShapeObject(int id, int recordNo) {
		this.id = id;
		this.recordNo = recordNo;
	}

	public abstract GeoObject getGeoObject();

	public abstract void addGeoPoint(GeoPoint point);

	public abstract double getArea();

	public abstract String toGeomText();
	// end of abstract methods

	// member

	private int recordNo;
	private int id;
	protected transient Mbr mbr;

	protected transient GeoPoint centroid;

	// static member

	public static final int NULL_SHAPE = 0,

	POINT = 1, POLY_LINE = 3, POLYGON = 5, MULTI_POINT = 8,

	POINT_Z = 11, POLY_LINE_Z = 13, POLYGON_Z = 15, MULTI_POINT_Z = 18,

	POINT_M = 21, POLY_LINE_M = 23, POLYGON_M = 25, MULTI_POINT_M = 28,

	MULTI_PATCH = 31;

}
