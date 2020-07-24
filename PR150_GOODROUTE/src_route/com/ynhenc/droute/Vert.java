package com.ynhenc.droute;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Hashtable;

import com.ynhenc.droute.map.Projection;
import com.ynhenc.droute.map.link.Wgs84ToTm;
import com.ynhenc.droute.render.*;

public class Vert {

	public double getDistanceTo(Vert to) {
		return this.getTmPoint().distance(to.getTmPoint());
	}

	public double getSquareLength() {
		return x*x + y*y;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public Point2D getTmPoint() {
		if (this.tmPoint == null) {
			this.tmPoint = Wgs84ToTm.getTmPointFromWgs84(x, y);
		}
		return this.tmPoint;
	}

	public void paintVert(RenderInfo render, StyleVert style, int index) {

		Projection projection = render.getProjection();
		Dimension nodeSize = style.getNodeSize();

		PntShort a = projection.toGraphics(this.getX(), this.getY());

		Graphics2D g = render.getGraphics();
		Color color = style.getLineColor();
		g.setColor(color);
		Ellipse2D e1 = this.createNodeShape( projection, nodeSize , null );
		g.fill(e1);

	}

	public Ellipse2D createNodeShape( Projection projection, Dimension nodeSize, PntShort normal ) {
		PntShort a = projection.toGraphics(this.getX(), this.getY());
		if( normal != null) {
			a = PntShort.pntShort(a.getX() + normal.getX(), a.getY() + normal.getY());
		}
		return new Ellipse2D.Double(a.getX() - nodeSize.width / 2.0, a.getY() - nodeSize.height / 2.0, nodeSize.width,
				nodeSize.height);
	}

	private Vert(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public static Vert getVert(double x, double y) {

		String key = "" + x + "" + y;

		Vert vert = vertList.get(key);

		if (vert == null) {
			vert = new Vert(x, y);
			vertList.put(key, vert);
		}

		return vert;

	}

	private double x;
	private double y;

	private transient Point2D tmPoint;

	private static Hashtable<String, Vert> vertList = new Hashtable<String, Vert>();

}
