package com.ynhenc.droute.map.square;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.Writer;
import java.lang.Math;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.droute.*;
import com.ynhenc.droute.render.*;
import com.ynhenc.kml.KmlCoordinates;
import com.ynhenc.kml.KmlGeometry;
import com.ynhenc.kml.KmlLineString;

public class GeoSquare extends GeoObject {

	@Override
	public Mbr getMbr() {
		return new Mbr( x, y, x, y );
	}

	@Override
	public void paint(RenderInfo render, StyleLink style, int index ) {
		Graphics2D g = render.getGraphics();
		Color fill = style.getFillColor();
		Color line = style.getLineColor();
		Dimension nodeSize = render.getNodeSize();
		g.setColor(fill);
		g.fillRect((int) this.x, (int) this.y, nodeSize.width, nodeSize.height);
		g.setColor(line);
		g.drawRect((int) this.x, (int) this.y, nodeSize.width, nodeSize.height);
	}

	@Override
	public double getSelfCost( SrchOption srchOption) {
		return 0;
	}

	@Override
	public double getCostTo( GeoObject o , SrchOption srchOption) {
		GeoSquare b = ( GeoSquare) o;
		// return (float)Math.sqrt(Math.pow(b.x-x,2.0)+Math.pow(b.y-y,2.0));
		return Math.sqrt((b.x - x) * (b.x - x) + (b.y - y) * (b.y - y));
	}

	@Override
	public boolean isIncluded( IncludeInfo inclInfo ) {
		double mouseX = inclInfo.getX();
		double mouseY = inclInfo.getY();
		Dimension nodeSize = inclInfo.getNodeSize();
		if ( GisComLib.isBetween( this.x, mouseX, this.x + nodeSize.width)) {
			if ( GisComLib.isBetween( this.y, mouseY, this.y + nodeSize.height)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public double getPhysicalDist(Vert b, Find.Mode findMode ) {
		return Math.sqrt((b.getX() - x) * (b.getX() - x) + (b.getY() - y) * (b.getY() - y));
	}

	@Override
	public KmlGeometry getKmlGeometry( GeoLinkCoordLevel coordLevel ) {
		KmlLineString kmlLineString = new KmlLineString();
		KmlCoordinates kmlCoordinates = new KmlCoordinates();

		kmlLineString.addComponent( kmlCoordinates );
		return kmlLineString;
	}

	@Override
	public Vert getFrVert() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vert getToVert() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getLength() {
		return 0;
	}

	public GeoSquare( Long id, double x, double y ) {
		super( id );
		this.x = x;
		this.y = y;
	}

	private double x, y;

}
