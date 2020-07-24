package com.ynhenc.gis.model.shape;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.geom.GeneralPath;

public class Mbr {

	public Mbr create() {
		return new Mbr(this.minX, this.minY, this.maxX, this.maxY);
	}

	public double getMinX() {
		return this.minX;
	}

	public double getMinY() {
		return this.minY;
	}

	public double getMaxX() {
		return this.maxX;
	}

	public double getMaxY() {
		return this.maxY;
	}

	public double getWidth() {
		return this.maxX - this.minX;
	}

	public double getHeight() {
		return this.maxY - this.minY;
	}

	public PntShort getCentroid() {
		return PntShort.pntShort((this.maxX + this.minX) / 2.0, (this.maxY + this.minY) / 2.0);
	}

	public double getArea() {
		return this.getWidth() * this.getHeight();
	}

	public double getLength() {
		return 2 * (this.getWidth() + this.getHeight());
	}

	public boolean intersects(Mbr a) {
		return Mbr.isIntersects(this, a);
	}

	public boolean isIncludes(GeoPoint point) {
		return this.isIncludes(point.getX(), point.getY());
	}

	public boolean isIncludes(PntShort point) {
		return this.isIncludes(point.getX(), point.getY());
	}

	public boolean isIncludes(double mapX, double mapY) {
		return this.minX <= mapX && mapX <= this.maxX && this.minY <= mapY && mapY <= this.maxY;
	}

	public int getTopology(GeoPoint point) {
		return this.getTopology(point.getX(), point.getY());
	}

	public int getTopology(double mapX, double mapY) {

		if (mapX < minX || maxX < mapX || mapY < minY || maxY < mapY) {
			// 영역 외부에 있을 때.....
			return 1;
		} else if (minX == mapX || mapX == maxX || minY == mapY || mapY == maxY) {
			// 접할 때....
			return 0;
		} else {
			// 교차할 대....
			return -1;
		}

	}

	@Override
	public String toString() {
		return "MBR = " + this.minX + ", " + this.minY + ", " + this.maxX + ", " + this.maxY;
	}

	public String toGeomText() {

		String wkt = "POLYGON(" + this.minX + " " + this.minY + "," + this.maxX + " " + this.maxY + ")";

		return wkt;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof Mbr) {
			Mbr mbr = (Mbr) (obj);
			return (this.minX == mbr.minX) && (this.minY == mbr.minY) && (this.maxX == mbr.maxX) && (this.maxY == mbr.maxY);
		} else {
			return false;
		}
	}

	public PntShort getMin() {
		return PntShort.pntShort(this.getMinX(), this.getMinY());
	}

	public PntShort getMax() {
		return PntShort.pntShort(this.getMaxX(), this.getMaxY());
	}

	Rectangle2D getToRectangle() {
		return new Rectangle2D.Double( this.getMinX(), this.getMaxY(), this.getWidth(), this.getHeight() );
	}

	public GeoPoint[] getCornerPointList() {
		// 최소점에서 부터 출발하여 시계방향으로 돌아가면서 구석점을 배열로 반환한다.
		if (this.cornerPointList == null) {
			this.cornerPointList = new GeoPoint[] {
					new GeoPoint(minX, minY), new GeoPoint(minX, maxY),
					new GeoPoint(maxX, maxY), new GeoPoint(maxX, minY), };
		}

		return this.cornerPointList;
	}

	public Mbr(double minx, double miny, double maxx, double maxy) {
		this.minX = minx < maxx ? minx : maxx;
		this.minY = miny < maxy ? miny : maxy;
		this.maxX = minx > maxx ? minx : maxx;
		this.maxY = miny > maxy ? miny : maxy;
	}

	public Mbr(PntShort min, PntShort max) {
		this(min.getX(), min.getY(), max.getX(), max.getY());
	}

	public Mbr(GeoPoint min, GeoPoint max) {
		this(min.x, min.y, max.x, max.y);
	}

	// member
	private double minX, minY, maxX, maxY;

	private transient GeoPoint[] cornerPointList;

	// end of member

	// static method

	public static Mbr newMbr(double minx, double miny, double maxx, double maxy) {

		return new Mbr( minx, miny, maxx, maxy );

	}

	public static Mbr union(Mbr a, Mbr b) {
		if (a == null || b == null) {
			return a == null ? b : a;
		} else {
			return new Mbr(a.minX < b.minX ? a.minX : b.minX, a.minY < b.minY ? a.minY : b.minY, a.maxX > b.maxX ? a.maxX
					: b.maxX, a.maxY > b.maxY ? a.maxY : b.maxY);
		}
	}

	public static Mbr getMbrIntersected(Mbr a, Mbr b) {
		if (a == null || b == null) {
			return null;
		}

		double minx = (a.minX > b.minX) ? a.minX : b.minX;
		double maxx = (a.maxX < b.maxX) ? a.maxX : b.maxX;

		if (minx > maxx) {
			return null;
		} else {
			double miny = (a.minY > b.minY) ? a.minY : b.minY;
			double maxy = (a.maxY < b.maxY) ? a.maxY : b.maxY;

			if (miny > maxy) {
				return null;
			} else {
				return new Mbr(minx, miny, maxx, maxy);
			}
		}
	}

	public static boolean isIntersects(Mbr a, Mbr b) {
		if (a == null || b == null) {
			return false;
		}

		double minx = (a.minX > b.minX) ? a.minX : b.minX;
		double maxx = (a.maxX < b.maxX) ? a.maxX : b.maxX;

		double xdiff = maxx - minx;

		if (xdiff < 0) {
			return false;
		} else {
			double miny = (a.minY > b.minY) ? a.minY : b.minY;
			double maxy = (a.maxY < b.maxY) ? a.maxY : b.maxY;

			double diffy = maxy - miny;

			if (diffy < 0) {
				return false;
			} else {
				return true;
			}
		}
	}

	public static Mbr calculateMbr(GeoPoint[] points) {

		int len = points.length;

		if (len < 1) {
			return null;
		}

		GeoPoint p = points[0];

		double minx = p.x;
		double maxx = minx;
		double miny = p.y;
		double maxy = miny;

		for (int i = 1; i < len; i++) {
			p = points[i];
			minx = minx < p.x ? minx : p.x;
			miny = miny < p.y ? miny : p.y;
			maxx = maxx > p.x ? maxx : p.x;
			maxy = maxy > p.y ? maxy : p.y;
		}

		return new Mbr(minx, miny, maxx, maxy);
	}

	public static Mbr getMbrWithMargin(Mbr mbr, double marginRatio) {

		if (mbr == null) {
			return null;
		} else if (marginRatio > 1 || marginRatio <= 0) {
			return mbr;
		} else {
			double w = mbr.getWidth();
			double h = mbr.getHeight();

			w = w * marginRatio / 2.0F;
			h = h * marginRatio / 2.0F;

			return new Mbr(mbr.minX - w, mbr.minY - h, mbr.maxX + w, mbr.maxY + h);
		}

	}

	// end of static method

}
