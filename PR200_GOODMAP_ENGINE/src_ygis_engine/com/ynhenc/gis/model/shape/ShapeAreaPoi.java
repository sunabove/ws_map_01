package com.ynhenc.gis.model.shape;

import com.ynhenc.gis.ui.resource.IconImage;

public class ShapeAreaPoi extends ShapeArea implements PoiInterface {

	public Poi getPoi() {
		if( this.poi == null ) {
			this.poi = new Poi();
		}
		return poi;
	}

	public void setPoi(Poi poi) {
		this.poi = poi;
	}

	public ShapeAreaPoi(int id, int recordNo, GeoPolygon poly) {
		super(id, recordNo, poly);
	}

	private Poi poi;

}
