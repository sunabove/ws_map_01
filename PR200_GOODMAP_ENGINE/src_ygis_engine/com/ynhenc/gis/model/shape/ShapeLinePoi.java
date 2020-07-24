package com.ynhenc.gis.model.shape;

import com.ynhenc.gis.ui.resource.IconImage;

public class ShapeLinePoi extends ShapeLine implements PoiInterface {

	public Poi getPoi() {
		if( this.poi == null ) {
			this.poi = new Poi();
		}
		return poi;
	}

	public void setPoi(Poi poi) {
		this.poi = poi;
	}

	public ShapeLinePoi(int id, int recordNo, GeoPolyLine poly) {
		super(id, recordNo, poly);
	}

	private Poi poi;

}
