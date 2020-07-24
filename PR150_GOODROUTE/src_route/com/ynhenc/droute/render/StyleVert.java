package com.ynhenc.droute.render;

import java.awt.*;

public class StyleVert extends Style {

	public Dimension getNodeSize() {
		return nodeSize;
	}

	public StyleVert(Color fillColor, Color lineColor, Dimension nodeSize) {
		super(fillColor, lineColor);
		this.nodeSize = nodeSize;
	}

	private Dimension nodeSize;

}
