/**
 *
 */
package com.ynhenc.gis.file.esri_shape;

import com.ynhenc.gis.model.shape.GeoPoint;

/**
 * @author sbmoon
 *
 */
public class EsriShapePoint extends EsriShapeObject {

	public EsriShapePoint(int recordNumber, GeoPoint point) {
		super(recordNumber);
		this.point = point;
	}

	public GeoPoint getPoint() {
		return this.point;
	}

	public void setPoint(GeoPoint point) {
		this.point = point;
	}

	private GeoPoint point;

}
