package com.ynhenc.droute.render;

import java.awt.Dimension;
import java.awt.event.MouseEvent;

import com.ynhenc.droute.PntShort;
import com.ynhenc.droute.Vert;
import com.ynhenc.droute.map.Projection;

public class IncludeInfo {

	public Vert getVert() {
		return vert;
	}

	public Dimension getNodeSize() {
		return nodeSize;
	}

	public double getX() {
		return this.getVert().getX();
	}
	public double getY() {
		return this.getVert().getY();
	}

	public IncludeInfo( Vert vert, Dimension nodeSize) {
		super();
		this.vert = vert ;
		this.nodeSize = nodeSize ;
	}

	Vert vert;
	Projection projection;
	Dimension nodeSize ;

}
