package com.ynhenc.gis.model.shape;

import java.awt.*;
import java.awt.geom.*;


public class GeoPolygon extends GeoPolyLine {

	@Override
	public boolean isIntersects(Mbr mbr) {
		if (super.isIntersects(mbr)) {
			return true;
		} else {
			for (GeoPoint cornerPoint : mbr.getCornerPointList()) {
				if (this.containsPoint(cornerPoint.getX(), cornerPoint.getY())) {
					return true;
				}
			}
			return false;
		}
	}

	public boolean isIncludes(Mbr mbr) {
		if (Mbr.isIntersects(this.getMbr(), mbr)) {
			for (GeoPoint cornerPoint : mbr.getCornerPointList()) {
				if (!this.containsPoint(cornerPoint.getX(), cornerPoint.getY())) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	private boolean containsPoint(double x, double y) {

		GeoPoint[] pointList = this.getPointList();

		int npoints = pointList.length;

		int hits = 0;

		double lastx = pointList[npoints - 1].getX();
		double lasty = pointList[npoints - 1].getY();
		double curx, cury;

		// Walk the edges of the polygon
		for (int i = 0; i < npoints; lastx = curx, lasty = cury, i++) {
			curx = pointList[i].getX();
			cury = pointList[i].getY();

			if (cury == lasty) {
				continue;
			}

			double leftx;
			if (curx < lastx) {
				if (x >= lastx) {
					continue;
				}
				leftx = curx;
			} else {
				if (x >= curx) {
					continue;
				}
				leftx = lastx;
			}

			double test1, test2;
			if (cury < lasty) {
				if (y < cury || y >= lasty) {
					continue;
				}
				if (x < leftx) {
					hits++;
					continue;
				}
				test1 = x - curx;
				test2 = y - cury;
			} else {
				if (y < lasty || y >= cury) {
					continue;
				}
				if (x < leftx) {
					hits++;
					continue;
				}
				test1 = x - lastx;
				test2 = y - lasty;
			}

			if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
				hits++;
			}
		}

		return ((hits & 1) != 0);
	}

	public GeoPolygon(GeoPoint[] pointList) {
		super(pointList);
	}

	@Override
	public double getArea() {
		return GeoAlgorithm.getPolygonArea(this.pointList);
	}

	@Override
	public GeoPoint getCentroid() {
		return GeoAlgorithm.calcCentroidPolygon(this);
	}

	@Override
	public Shape createShape(Projection proj) {
		GeneralPath gp = (GeneralPath) (super.createShape(proj));
		gp.closePath();
		return gp;
	}

}
