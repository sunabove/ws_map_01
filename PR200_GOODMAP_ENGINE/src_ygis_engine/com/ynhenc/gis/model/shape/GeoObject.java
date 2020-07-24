package com.ynhenc.gis.model.shape;

import java.awt.Shape;
import java.awt.geom.GeneralPath;

import com.ynhenc.comm.DebugInterface;
import com.ynhenc.comm.GisComLib;

public abstract class GeoObject implements DebugInterface {

	public GeoObject() {
	}

	public Shape createShapeDiamond(Projection projection, GeoPoint point, float d) {
		PntShort center = projection.toGraphics(point.x, point.y);
		GeneralPath gp = new GeneralPath();
		gp.moveTo(center.x, center.y - d);
		gp.lineTo(center.x + d, center.y);
		gp.lineTo(center.x, center.y + d);
		gp.lineTo(center.x - d, center.y);
		gp.closePath();
		return gp;
	}

	public Shape createShapeDot(Projection projection, GeoPoint point ) {
		PntShort center = projection.toGraphics(point.x, point.y);
		GeneralPath gp = new GeneralPath();
		gp.moveTo(center.x, center.y);
		gp.lineTo(center.x, center.y);
		gp.closePath();
		return gp;
	}

	public abstract Mbr getMbr();

	public abstract Mbr calcMbr();

	public abstract double getLength();

	public abstract double getArea();

	public abstract GeoPoint getCentroid();

	public abstract Shape createShape(Projection projection);

	public abstract GeoPoint [] getPointList() ;

	public abstract boolean isContainedByCircleRadium( GeoPoint center, double radium ) ;

	public abstract boolean isIntersects( Mbr mbr );

}
