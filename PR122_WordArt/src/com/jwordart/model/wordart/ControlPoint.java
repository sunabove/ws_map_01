/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.model.wordart;

import java.awt.*;
import java.awt.geom.*;

abstract public class ControlPoint extends Rectangle2D.Double {
	WordArt wordArt;

	public static final double defaultWidth = 6, defaultHeight = 6;
	private double minMovableBoundsRatio = 0, maxMovableBoundsRatio = 1.0,
			currentMoveRatio = minMovableBoundsRatio;

	public ControlPoint(WordArt wa) {
		super(0, 0, defaultWidth, defaultHeight);
		this.wordArt = wa;
	}

	abstract public ControlPoint create(WordArt wa);

	@Override
	abstract public double getCenterX();

	@Override
	abstract public double getCenterY();

	public Shape getRotatedShape() {
		double x = super.getX(), y = super.getY(), w = ControlPoint.defaultWidth, h = ControlPoint.defaultHeight;

		x += w / 2;
		y += h / 2;
		AffineTransform at = AffineTransform.getRotateInstance(Math.PI / 4, x,
				y);
		return at.createTransformedShape(this);
	}

	public double getWordArtWidth() {
		return this.wordArt.getGlobalDimension().getWidth();
	}

	public double getWordArtHeight() {
		return this.wordArt.getGlobalDimension().getHeight();
	}

	abstract public void moveBy(double dx, double dy);

	abstract public void reset();

	public void setWordArt(WordArt wa) {
		this.wordArt = wa;
	}

	public void setMinMovableBoundsRatio(double min) {
		this.minMovableBoundsRatio = min;
	}

	public void setMaxMovableBoundsRatio(double max) {
		this.maxMovableBoundsRatio = max;
	}

	public void setMovableBoundsRatio(double min, double max) {
		this.setMinMovableBoundsRatio(min);
		this.setMaxMovableBoundsRatio(max);
	}

	public void setCurrentMoveRatio(double currentRatio) throws Exception {
		if (currentRatio < this.minMovableBoundsRatio
				|| currentRatio > this.maxMovableBoundsRatio) {
			throw new Exception("Wrong current move ratio.");
		}
		this.currentMoveRatio = currentRatio;
	}

	public double getCurrentMoveRatio() {
		return this.currentMoveRatio;
	}

	public double getMinMovableRatio() {
		return this.minMovableBoundsRatio;
	}

	public double getMaxMovableRatio() {
		return this.maxMovableBoundsRatio;
	}

	public WordArt getWordArt() {
		return this.wordArt;
	}
}