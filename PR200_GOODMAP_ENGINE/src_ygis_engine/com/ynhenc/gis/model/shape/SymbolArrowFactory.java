package com.ynhenc.gis.model.shape;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.util.UnitUtil;

public class SymbolArrowFactory extends GisComLib {

	public static Shape[] createArrowLineList(SymbolArrow arrow, GeoPolyLine polyLine, Projection projection) {

		SymbolArrow.Type arrowType = arrow.getType();

		if( arrowType == SymbolArrow.Type.ARROW_ZERO ) {
			return null ;
		}

		GeoPoint[] pointList = polyLine.getPointList();

		int pointListLen = pointList.length;

		if (pointListLen < 2) {

			return null;

		}

		GeoPoint p, q, r, s; // p --> q , r --> s

		if (arrowType != SymbolArrow.Type.ARROW_LEFT) {
			p = pointList[pointListLen - 2];
			q = pointList[pointListLen - 1];
			r = pointList[1];
			s = pointList[0];
		} else {
			p = pointList[1];
			q = pointList[0];
			r = pointList[pointListLen - 2];
			s = pointList[pointListLen - 1];
		}

		if (arrowType == SymbolArrow.Type.ARROW_ZERO) {
			return null;
		} else if (arrowType == SymbolArrow.Type.ARROW_BOTH) {
			Shape shapeFirst = createArrowLine(arrow, p, q, projection);
			Shape shapeSecond = createArrowLine(arrow, r, s, projection);
			return new Shape[] { shapeFirst, shapeSecond };
		} else {
			Shape shapeFirst = createArrowLine(arrow, p, q, projection);
			return new Shape[] { shapeFirst };
		}

	}

	private static Shape createArrowLine(SymbolArrow arrow, GeoPoint p, GeoPoint q, Projection projection) {
		int width = arrow.getWidth();
		int height = arrow.getHeight();

		PntShort pntStart = projection.toGraphics(p);
		PntShort pntFinal = projection.toGraphics(q);

		double radian = GeoAlgorithm.getRadian(pntStart, pntFinal);
		final Shape shapeArrow = SymbolArrowFactory.createSimpleArrowLine(width, height);
		AffineTransform at = AffineTransform.getTranslateInstance(pntFinal.x, pntFinal.y);
		at.rotate(radian);

		return at.createTransformedShape(shapeArrow);

	}

	private static Shape createSimpleArrowLine( float w, float h ) {
		GeneralPath gp = new GeneralPath();
		gp.moveTo(0, 0 );
		gp.lineTo( - w, h/2 );
		gp.lineTo( - w, - h/2 );
		gp.closePath();

		return gp;

	}

	private static Shape createZeroArrowLine(final double w, double t) {
		if (t == 1) {
			return new Line2D.Double(0, 0, w, 0);
		}
		return new Rectangle2D.Double(0, -t / 2.0, w, t);
	}

	private static Shape createOneArrowLine(final double w, double t) {

		t = t / 2.0; // 굵기를 반으로 줄인다. 위 아래로 트래블(travel) 하므로

		final double bRatio = 2;

		final double b = ((bRatio * t) < 4) ? 4 : (bRatio * t);

		final double h = (b + t) / TANGENT30;

		final GeneralPath gp = new GeneralPath();

		gp.moveTo(0, (float) -t);

		gp.lineTo((float) (w - h), (float) -t);

		gp.lineTo((float) (w - h), (float) (-t - b));

		gp.lineTo((float) w, 0);

		gp.lineTo((float) (w - h), (float) (t + b));

		gp.lineTo((float) (w - h), (float) t);

		gp.lineTo(0, (float) t);

		gp.closePath();

		return gp;

	}

	private static Shape createTwoArrowLine(final double w, double t) {

		t = t / 2.0; // 굵기를 반으로 줄인다. 위 아래로 트래블(travel) 하므로

		final double bRatio = 2;

		final double b = ((bRatio * t) < 4) ? 4 : (bRatio * t);

		final double h = (b + t) / TANGENT30;

		final GeneralPath gp = new GeneralPath();

		gp.moveTo(0, 0);

		gp.lineTo((float) (h), (float) (-t - b));

		gp.lineTo((float) (h), (float) (-t));

		gp.lineTo((float) (w - h), (float) -t);

		gp.lineTo((float) (w - h), (float) (-t - b));

		gp.lineTo((float) (w), 0);

		gp.lineTo((float) (w - h), (float) (t + b));

		gp.lineTo((float) (w - h), (float) (t));

		gp.lineTo((float) (h), (float) (t));

		gp.lineTo((float) (h), (float) (t + b));

		gp.closePath();

		return gp;

	}

	private static Shape createArrowLine_Old(SymbolArrow.Type arrowType, int width, int height, GeoPoint p,
			GeoPoint q, Projection projection) {

		PntShort pntStart = projection.toGraphics(p);
		PntShort pntFinal = projection.toGraphics(q);

		double radian = GeoAlgorithm.getRadian(pntStart, pntFinal);

		final Shape shapeArrow;

		if (arrowType == SymbolArrow.Type.ARROW_RIGHT) {

			shapeArrow = SymbolArrowFactory.createOneArrowLine(width, height);

		} else if (arrowType == SymbolArrow.Type.ARROW_LEFT) {

			shapeArrow = SymbolArrowFactory.createOneArrowLine(width, height);

		} else if (arrowType == SymbolArrow.Type.ARROW_ZERO) {

			shapeArrow = SymbolArrowFactory.createZeroArrowLine(width, height);

		} else {

			shapeArrow = SymbolArrowFactory.createTwoArrowLine(width, height);
		}

		AffineTransform at = AffineTransform.getTranslateInstance(pntFinal.x, pntFinal.y);

		at.rotate(radian);

		return at.createTransformedShape(shapeArrow);

	}

	private static final double TANGENT30 = Math.tan(Math.PI / 7.0);

}
