/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.model.wordart;

public class ControlPointVertical extends ControlPoint {
	public static final int LEFT = 0, RIGHT = 1, CENTER = 2;
	private int align = LEFT;
	private double locMargin = 13;

	public ControlPointVertical(WordArt wa, double minRatio, double maxRatio,
			double currentRatio) {
		super(wa);

		super.setMovableBoundsRatio(minRatio, maxRatio);

		try {
			super.setCurrentMoveRatio(currentRatio);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.reset();
	}

	public ControlPointVertical(WordArt wa, double minRatio, double maxRatio,
			double currentRatio, int align) {
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

	public int getAlign() {
		return this.align;
	}

	@Override
	public ControlPoint create(WordArt wa) {
		ControlPointVertical cp = new ControlPointVertical(wa, this
				.getMinMovableRatio(), this.getMaxMovableRatio(), this
				.getCurrentMoveRatio(), this.getAlign());
		cp.setRect(this.getX(), this.getY(), ControlPoint.defaultWidth,
				ControlPoint.defaultHeight);
		return cp;
	}

	@Override
	public void reset() {
		double w = this.getWordArtWidth();
		double h = this.getWordArtHeight();

		double x = -locMargin;
		if (align == ControlPointVertical.RIGHT) {
			x = w + locMargin;
		} else if (align == ControlPointVertical.CENTER) {
			x = w / 2.0 - ControlPoint.defaultWidth / 2.0;
		}

		double y = h * this.getCurrentMoveRatio() - ControlPoint.defaultHeight
				/ 2.0;
		super.setRect(x, y, super.getWidth(), super.getHeight());
	}

	@Override
	public double getCenterX() {
		if (this.align == ControlPointVertical.RIGHT) {
			return super.getX() - this.locMargin;
		} else if (this.align == ControlPointVertical.CENTER) {
			return super.getX() + ControlPoint.defaultWidth / 2.0;
		} else {
			return super.getX() + this.locMargin;
		}
	}

	@Override
	public double getCenterY() {
		return super.getY() + ControlPoint.defaultHeight / 2.0;
	}

	@Override
	public void moveBy(double dx, double dy) {
		dx = 0;

		if (super.getWordArt().isVertical()) {
			dy *= -1;
		}

		double x = super.getX(), y = super.getY() + dy;

		double h = super.getWordArtHeight();

		double moveRatio = y / h;
		if (moveRatio < this.getMinMovableRatio()) {
			y = h * this.getMinMovableRatio() - ControlPoint.defaultHeight
					/ 2.0;
			moveRatio = this.getMinMovableRatio();
		} else if (moveRatio > this.getMaxMovableRatio()) {
			y = h * this.getMaxMovableRatio() - ControlPoint.defaultHeight
					/ 2.0;
			moveRatio = this.getMaxMovableRatio();
		}
		try {
			this.setCurrentMoveRatio(moveRatio);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.setRect(x, y, ControlPoint.defaultWidth,
				ControlPoint.defaultHeight);
	}
}
