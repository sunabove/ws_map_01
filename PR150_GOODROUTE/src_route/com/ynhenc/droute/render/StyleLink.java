package com.ynhenc.droute.render;

import java.awt.*;

public class StyleLink extends Style {

	public boolean isPath() {
		return path;
	}

	public void setPath(boolean path) {
		this.path = path;
	}

	public StyleLink(Color fillColor, Color lineColor, float lineWidth, int alpha, boolean path ) {
		super(fillColor, lineColor, lineWidth, path, alpha);
		this.path = path;
	}

	private boolean path = true ;

}
