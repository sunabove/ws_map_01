package com.ynhenc.gis.model.shape;

import java.awt.*;
import java.awt.geom.*;

import com.ynhenc.gis.model.style.*;
import com.ynhenc.gis.ui.comp.*;

public class ShapeLine extends ShapeObject {

	@Override
	public double getArea() {
		return 0;
	}

	@Override
	public GeoPolyLine getGeoObject() {
		return this.poly;
	}

	public void setGeoObject( GeoPolyLine poly ) {
		this.poly = poly;
		this.centroid = null ; // init centroid after update location
	}

	@Override
	public void addGeoPoint(GeoPoint point) {

		GeoPolyLine poly = this.poly;

		if (poly != null) {
			GeoAlgorithm.addGeoPoint(point, poly);
		} else {
			this.poly = new GeoPolyLine(new GeoPoint[] { point });
		}

		this.setShape(null);

		this.mbr = null;

	}

	@Override
	public String toGeomText() {

		GeoPolyLine poly = this.poly;

		if (poly == null) {
			return "NULL";
		} else {
			GeoPoint [] points = poly.getPointList();
			if (points == null) {
				return "NULL";
			} else {
				StringBuffer bfr = new StringBuffer();
				bfr.append("LINESTRING(");
				GeoPoint point;
				for (int i = 0, len = points.length; i < len; i++) {
					point = points[i];
					bfr.append(point.x + " " + point.y);
					if (i < len - 1) {
						bfr.append(",");
					}
				}
				bfr.append(")");
				return bfr.toString();
			}
		}
	}

	public Shape [] getShapeArrowList( SymbolArrow arrow , Projection projection ) {
		return this.createShapeArrowList( arrow, projection );
	}

	private Shape [] createShapeArrowList( SymbolArrow arrow, Projection projection ) {
		return SymbolArrowFactory.createArrowLineList( arrow, this.poly , projection ) ;
	}

	public ShapeLine(int id, int recordNo, GeoPolyLine poly ) {
		super( id, recordNo );
		this.poly = poly ;
	}

	// member

	protected GeoPolyLine poly;

	// end of member

}
