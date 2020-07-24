package com.ynhenc.gis.model.shape;

import java.awt.Shape;
import java.awt.geom.*;


public class ShapeAreaHole extends ShapeObject {

	@Override
	public double getArea() {

		if (this.polyHole == null) {
			return 0;
		} else {
			return this.polyHole.getArea();
		}
	}

	@Override
	public String toGeomText() {

		return "NULL";

	}

	@Override
	public GeoObject getGeoObject() {
		return this.polyHole;
	}

	@Override
	public void addGeoPoint(GeoPoint point) {
		// TODO 2008.06.20 ������ Ȧ�� ����Ʈ�� �߰��ϴ� �˰��� ����.
	}

	public ShapeAreaHole( int id, int recordNo, GeoPolygonHole polyHole ) {
		super( id, recordNo );

		this.polyHole = polyHole;
	}

	private  GeoPolygonHole polyHole;

}
