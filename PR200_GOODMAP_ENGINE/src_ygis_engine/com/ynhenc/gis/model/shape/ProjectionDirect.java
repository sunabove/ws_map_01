package com.ynhenc.gis.model.shape;

import java.awt.Dimension;
import java.awt.geom.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.util.UnitUtil;

public class ProjectionDirect extends Projection {

	protected ProjectionDirect(Mbr mbr, Rectangle2D pixelRect) {

		super(mbr, pixelRect);

	}

	@Override
	public void initProjection() {

		this.scale = this.calcScale();

		double[] translation = this.getTranslation(this.scale);

		this.tx = translation[0];
		this.ty = translation[1];
	}

	@Override
	public double getMapScale() {

		// TODO 2008.06.14.002 축척 최적화 필요.

		Rectangle2D pixelRect = this.getPixelRect();

		int width = (int) pixelRect.getWidth();

		GeoPoint p = this.toSpatial(0, 0);
		GeoPoint q = this.toSpatial(width, 0);

		double distX_cm = Math.abs(p.x - q.x) * 100;

		double pixelW_cm = UnitUtil.convertPixelToCM(width);

		return distX_cm / pixelW_cm;

	}

	@Override
	public double getScale() {
		return this.scale;
	}

	private double calcScale() {

		double w = this.pixelRect.getWidth();
		double h = this.pixelRect.getHeight();

		Mbr mbr = this.getMbr();

		double WW = mbr.getWidth(), HH = mbr.getHeight();

		double sw = w / WW;
		double sh = h / HH;

		return (sw < sh) ? sw : sh;

	}

	private double[] getTranslation(double scale) {

		PntShort center = this.mbr.getCentroid();

		double cx = center.x * scale;
		double cy = -(center.y * scale);

		double w = this.pixelRect.getWidth();
		double h = this.pixelRect.getHeight();

		return new double[] { w / 2.0 - cx, h / 2.0 - cy };

	}

	@Override
	public PntShort toGraphics(double x, double y) {

		return PntShort.pntShort(x * this.scale + this.tx, -(y * this.scale) + this.ty);

	}

	@Override
	public GeoPoint toSpatial(int x, int y) {

		return new GeoPoint((x - this.tx) / this.scale, (this.ty - y) / this.scale);

	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		} else if (obj instanceof Projection) {
			Projection proj = (Projection) (obj);
			return this.mbr != null && this.mbr.equals(proj.getMbr()) && this.pixelRect != null
					&& this.pixelRect.equals(proj.getPixelRect());
		} else {
			return false;
		}

	}

	@Override
	public String toString() {
		return "Projection : " + this.pixelRect + ", scale = " + this.scale + ", tx = " + this.tx + ", ty = " + this.ty
				+ ", " + this.mbr;
	}

	// member
	private double tx;
	private double ty;
	private double scale;

	// end of member

}
