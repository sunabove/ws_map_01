/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.model.wordart;

public class ControlPointHorizontal extends ControlPoint {
	public static final int BOTTOM = 0, TOP = 1;
	private double locMargin = 10;
	private int align = BOTTOM;

	public ControlPointHorizontal(WordArt wa, double minRatio, double maxRatio, double currentRatio) {
		super(wa);

		super.setMovableBoundsRatio(minRatio, maxRatio);
		try {
			super.setCurrentMoveRatio(currentRatio);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.reset();
	}

	public ControlPointHorizontal(WordArt wa, double minRatio, double maxRatio, double currentRatio, int align) {
		super(wa);

		this.align = align;

		super.setMovableBoundsRatio(minRatio, maxRatio);
		try {
			super.setCurrentMoveRatio(currentRatio);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.reset();
	}

	@Override
	public void reset() {
		double w = super.getWordArtWidth(), h = super.getWordArtHeight();
		/*
		 * if( super.getWordArt().isVertical() ) { double tempW = w; w = h; h =
		 * tempW; }
		 */
		double x = w * super.getCurrentMoveRatio() - ControlPoint.defaultWidth / 2.0, y = h + this.locMargin;
		if (this.align == TOP) {
			y = -this.locMargin;
		}
		super.setRect(x, y, ControlPoint.defaultWidth, ControlPoint.defaultHeight);
	}

	public int getAlign() {
		return this.align;
	}

	@Override
	public ControlPoint create(WordArt wa) {
		ControlPointHorizontal cp = new ControlPointHorizontal(wa, this.getMinMovableRatio(), this.getMaxMovableRatio(), this
				.getCurrentMoveRatio(), this.getAlign());
		cp.setRect(this.getX(), this.getY(), ControlPoint.defaultWidth, ControlPoint.defaultHeight);
		return cp;
	}

	@Override
	public double getCenterX() {
		return super.getX() + ControlPoint.defaultWidth / 2.0;
	}

	@Override
	public double getCenterY() {
		if (this.align == TOP) {
			return super.getY() + this.locMargin;
		} else {
			return super.getY() - this.locMargin;
		}
	}

	@Override
	public void moveBy(double dx, double dy) {

		dy = 0;

		double x = super.getX() + dx, y = super.getY();
		double w = super.getWordArtWidth();

		double moveRatio = x / w;
		if (moveRatio < this.getMinMovableRatio()) {
			x = w * this.getMinMovableRatio() - ControlPoint.defaultWidth / 2.0;
			moveRatio = this.getMinMovableRatio();
		} else if (moveRatio > this.getMaxMovableRatio()) {
			x = w * this.getMaxMovableRatio() - ControlPoint.defaultWidth / 2.0;
			moveRatio = this.getMaxMovableRatio();
		}
		try {
			this.setCurrentMoveRatio(moveRatio);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.setRect(x, y, ControlPoint.defaultWidth, ControlPoint.defaultHeight);
	}
}