package com.ynhenc.droute.map.link;

import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

import com.ynhenc.droute.*;

public class SymbolArrow {

	public Shape createArrowLine(PntShort pntStart, PntShort pntFinal, Dimension size, PntShort normal) {

		double radian = this.getRadian(pntStart, pntFinal);
		final Shape shapeArrow = this.createSimpleArrowLine(size);
		AffineTransform at = null;
		if (normal != null) {
			at = AffineTransform.getTranslateInstance(pntFinal.x + normal.getX(), pntFinal.y + normal.getY());
		} else {
			at = AffineTransform.getTranslateInstance(pntFinal.x, pntFinal.y);
		}
		at.rotate(radian);

		return at.createTransformedShape(shapeArrow);

	}

	private Shape createSimpleArrowLine(Dimension size) {
		GeneralPath gp = new GeneralPath();
		gp.moveTo(0, 0);
		gp.lineTo(-size.getWidth(), size.getHeight() / 2);
		gp.lineTo(-size.getWidth(), -size.getHeight() / 2);
		gp.closePath();

		return gp;

	}

	public double getRadian(PntShort p, PntShort q) {
		return this.getRadian(q.x - p.x, q.y - p.y);
	}

	private double getRadian(double x, double y) {
		double theta = Math.acos(x / Math.sqrt(x * x + y * y));

		if (x < 0 && y < 0) { // 3/4 분면
			theta = 2.0 * Math.PI - theta;
		} else if (x > 0 && y < 0) { // 4/4 분면
			theta = 2.0 * Math.PI - theta;
		}
		return theta;
	}

}
