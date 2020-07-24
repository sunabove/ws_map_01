package com.ynhenc.gis.model.shape;

import java.awt.image.BufferedImage;

import com.ynhenc.gis.ui.resource.IconImage;

public class ShapePointPoi extends ShapePoint implements PoiInterface {

	public Poi getPoi() {
		if (this.poi == null) {
			this.poi = new Poi();
		}
		return poi;
	}

	public void setPoi(Poi poi) {
		this.poi = poi;
	}

	public boolean isContains(Projection projection, MousePnt mouse) {
		ShapePointPoi shapePoi = this;
		GeoPoint centroid = shapePoi.getCentroid();
		PntShort pixelPnt = projection.toGraphics(centroid);
		Poi poi = shapePoi.getPoi();
		IconImage iconImage = poi.getIconImage();

		if (iconImage != null) {
			BufferedImage bfrImage = iconImage.getBfrImage();
			if (bfrImage != null) {
				double imageWidth = bfrImage.getWidth();
				double imageHeight = bfrImage.getHeight();
				double dx = Math.abs( pixelPnt.getX() - mouse.getX() );
				double dy = Math.abs( pixelPnt.getY() - mouse.getY() );
				if( dx <= imageWidth/2.0 && dy <= imageHeight/2.0 ) {
					return true;
				}
			}
		}
		return false;
	}

	public ShapePointPoi(int id, int recordNo, double x, double y) {
		super(id, recordNo, x, y);
	}

	public ShapePointPoi(int id, int recordNo, GeoPoint point) {
		super(id, recordNo, point);
	}

	private Poi poi;

}
