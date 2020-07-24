package com.ynhenc.gis.model.shape;

import java.awt.Shape;
import java.awt.geom.*;


public class ShapeArea extends ShapeLine {

	@Override
	public double getArea() {

		if (this.poly == null) {
			return 0;
		} else {
			return this.poly.getArea();
		}
	}

	@Override
	public String toGeomText() {

		GeoPolyLine poly = this.poly;

		if (poly == null) {
			return "NULL";
		} else {
			GeoPoint[] points = poly.getPointList();
			if (points == null) {
				return "NULL";
			} else {
				StringBuffer bfr = new StringBuffer();
				bfr.append("POLYGON((");
				GeoPoint point;
				for (int i = 0, len = points.length; i < len; i++) {
					point = points[i];
					bfr.append(point.x + " " + point.y);
					if (i < len - 1) {
						bfr.append(",");
					}
				}
				bfr.append("))");
				return bfr.toString();
			}
		}
	}

	public ShapeArea( int id, int recordNo, GeoPolygon poly ) {
		super( id, recordNo, poly );
	}

}
