package com.ynhenc.droute.render;

import java.awt.*;

public abstract class Style {

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public BasicStroke getStroke() {
		return stroke;
	}
	public Color getFillColor() {
		return fillColor;
	}
	public Color getLineColor() {
		return lineColor;
	}

	public Style(Color fillColor, Color lineColor ) {
		this( fillColor, lineColor, 1 );
	}

	public Style(Color fillColor, Color lineColor , float lineWidth ) {
		this( fillColor, lineColor, lineWidth, true );
	}

	public Style(Color fillColor, Color lineColor , float lineWidth, boolean path ) {
		this( fillColor, lineColor, lineWidth, path, 255 );
	}

	public Style(Color fillColor, Color lineColor , float lineWidth, boolean path , int alpha ) {
		this.fillColor = this.getAlphaColor( fillColor, alpha);
		this.lineColor = this.getAlphaColor( lineColor, alpha);
		this.stroke = new BasicStroke( lineWidth );
	}

	private Color getAlphaColor( Color c, int alpha ) {
		alpha = alpha > 255 ? 255 : alpha ;
		alpha = alpha < 0 ? 0 : alpha ;
		return new Color( c.getRed() , c.getGreen(), c.getBlue(), alpha );
	}

	public float getLineWidth() {
		return this.getStroke().getLineWidth();
	}

	private int id;
	private Color fillColor;
	private Color lineColor;
	private BasicStroke stroke;

}
