package com.ynhenc.droute.render;

import java.awt.* ;

import com.ynhenc.droute.map.Projection;

public class RenderInfo {

	public void setDrawLinkShape(boolean drawLinkShape) {
		this.drawLinkShape = drawLinkShape;
	}
	public boolean isDrawLinkShape() {
		return drawLinkShape;
	}
	public Projection getProjection() {
		return projection;
	}
	public Dimension getNodeSize() {
		return nodeSize;
	}
	public Graphics2D getGraphics() {
		return graphics;
	}
	public Dimension getPanelSize() {
		return panelSize;
	}

	public RenderInfo(Graphics2D graphics, Projection projection, Dimension panelSize, Dimension nodeSize, boolean drawLinkShape ) {
		super();
		this.graphics = graphics;
		this.projection = projection;
		this.panelSize = panelSize;
		this.nodeSize = nodeSize;
		this.drawLinkShape = drawLinkShape;
	}

	private Graphics2D graphics;
	private Dimension panelSize;
	private Dimension nodeSize;
	private Projection projection;
	private boolean drawLinkShape;
}
