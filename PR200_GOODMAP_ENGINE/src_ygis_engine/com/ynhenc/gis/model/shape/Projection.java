package com.ynhenc.gis.model.shape;

import java.awt.Dimension;
import java.awt.geom.*;

import com.ynhenc.comm.GisComLib;

public abstract class Projection extends GisComLib {

	protected Projection(Mbr mbr, Rectangle2D pixelRect) {

		this.mbr = mbr;
		this.pixelRect = pixelRect;

		this.clippingRect = this.createClippingRect(pixelRect);

		this.initProjection();

		this.mbrScreen = this.calcMbrScreen();
	}

	private Area createClippingRect( Rectangle2D pixelRect ) {

		double margin = 10;

		Rectangle2D pixelRctClpping = new Rectangle2D.Double( pixelRect.getX() - margin, pixelRect.getY() - margin , pixelRect.getWidth() + 2*margin , pixelRect.getHeight() + 2*margin );

		return new Area( pixelRctClpping );
	}

	public Area getClippingRect() {

		if( this.clippingRect == null ) {
			this.clippingRect = this.createClippingRect( this.getPixelRect() );
		}
		return this.clippingRect;
	}

	public final Mbr getMbrScreen() {

		return this.mbrScreen;
	}

	private final Mbr calcMbrScreen() {

		return new Mbr( this.toSpatial(0, 0)
				, this.toSpatial((int) (this.pixelRect.getWidth()), (int) (this.pixelRect.getHeight())) );
	}

	public final Mbr getMbr() {
		return this.mbr;
	}

	public final Rectangle2D getPixelRect() {
		return this.pixelRect;
	}

	public final Dimension getPixelSize() {
		return new Dimension((int) this.getPixelWidth(), (int) this.getPixelHeight());
	}

	public final double getPixelWidth() {
		return this.getPixelRect().getWidth();
	}

	public final double getPixelHeight() {
		return this.getPixelRect().getHeight();
	}

	public final PntShort toGraphics(GeoPoint p) {
		return this.toGraphics(p.x, p.y);
	}

	@Override
	public boolean equals(Object obj) {
		if( this == obj ) {
			return true;
		} else if (obj instanceof Projection) {
			Projection projection = (Projection) obj;
			Mbr mbr = this.getMbr();
			return mbr != null && mbr.equals(projection.getMbr()) && this.getPixelSize().equals(projection.getPixelSize());
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return mbr.toString() + ", pixelRect = " + this.getPixelRect() ;
	}

	public abstract double getMapScale();

	public abstract double getScale();

	public abstract PntShort toGraphics(double x, double y);

	public abstract GeoPoint toSpatial(int x, int y);

	public abstract void initProjection();

	protected Mbr mbr;
	protected Mbr mbrScreen;
	protected Rectangle2D pixelRect;
	private transient Area clippingRect;

	// static method

	public static Projection getProject(Mbr mbr, double w, double h) {
		return new ProjectionDirect(mbr, new Rectangle2D.Double(0, 0, w, h));
	}

	public static Projection getProject(Mbr mbr, Dimension pixelRect) {
		return getProject(mbr, pixelRect.width, pixelRect.height);
	}

	// end of static method

}