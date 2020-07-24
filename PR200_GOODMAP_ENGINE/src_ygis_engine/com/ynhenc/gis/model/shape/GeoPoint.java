package com.ynhenc.gis.model.shape;

import java.awt.*;
import java.awt.geom.*;

import com.ynhenc.gis.projection.UtmToWgs;
import com.ynhenc.gis.projection.WgsToUtm;

public class GeoPoint extends GeoObject {

	@Override
	public boolean isIntersects(Mbr mbr) {
		return mbr.isIncludes(this.getX(), this.getY());
	}

	@Override
	public Mbr getMbr() {
		// 포인트형의 경우에는 메모리 절약을 위해서 특별히 MBR 멤버를 두지 않는다.
		return this.calcMbr();
	}

	@Override
	public Mbr calcMbr() {
		return new Mbr(this.x, this.y, this.x, this.y);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GeoPoint) {
			GeoPoint q = (GeoPoint) obj;
			return this.x == q.x && this.y == q.y;
		} else {
			return false;
		}
	}

	@Override
	public double getLength() {
		return 0;
	}

	@Override
	public double getArea() {
		return 0;
	}

	@Override
	public String toString() {
		return "x = " + this.x + ", y = " + this.y;
	}

	@Override
	public Shape createShape(Projection projection) {
		if( true ) {
			return super.createShapeDot(projection, this);
		} else if ( false ) {
			return super.createShapeDiamond(projection, this, 3.0F);
		} else {
			return null;
		}
	}

	@Override
	public GeoPoint getCentroid() {
		return this;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public PntShort getUtmToWgs() {
		UtmToWgs cv = new UtmToWgs();
		return cv.getConvertValue(PntShort.pntShort(this.x, this.y));
	}

	public PntShort getWgsToUtm() {
		WgsToUtm cv = new WgsToUtm();
		return cv.getConvertValue(PntShort.pntShort(this.x, this.y));
	}

	@Override
	public GeoPoint[] getPointList() {
		return new GeoPoint[] { this };
	}

	@Override
	public boolean isContainedByCircleRadium(GeoPoint center, double radium) {
		return GeoAlgorithm.getDistum(this, center) <= radium;
	}

	public GeoPoint() {
		this(0, 0);
	}

	public GeoPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	protected double x;
	protected double y;

}
