package com.ynhenc.gis.model.shape;

import java.awt.Shape;
import java.awt.geom.GeneralPath;

import com.ynhenc.comm.GisComLib;

public final class GeoAlgorithm extends GisComLib {

	// 토폴로지 계산

	public static int getTopoLineToPolyLine(GeoPoint aP, GeoPoint aQ, GeoPolyLine polyLine) {
		return getTopoLineToPolyLine(aP, aQ, polyLine.getPointList());
	}

	public static int getTopoLineToPolyLine(GeoPoint aP, GeoPoint aQ, GeoPoint[] polyLine) {
		// 라인과 폴리라인의 토폴로지.
		// topo : -1 : intersect
		// topo : 0 : meet
		// topo : 1 : does not intersect

		int topo = 1;

		for (int i = 0, iLen = polyLine.length - 1; i < iLen; i++) {
			topo = getTopoLineToLine(aP, aQ, polyLine[i], polyLine[i + 1]);
			if (topo <= 0) {
				return topo;
			}
		}

		return topo;
	}

	public static int getTopoLineToLine(GeoPoint aP, GeoPoint aQ, GeoPoint bP, GeoPoint bQ) {
		// 라인과 라인 사이의 토폴로지.
		return getTopoPointToLine(aP, bP, bQ) * getTopoPointToLine(aQ, bP, bQ);
	}

	public static int getTopoPointToLine(GeoPoint p, GeoPoint lineA, GeoPoint lineB) {
		// 한 점과 라인 사이의 토폴로지.
		double product = getCrossProduct(p, lineA, lineB);
		if (product > 0) {
			return 1;
		} else if (product < 0) {
			return -1;
		} else {
			return 0;
		}
	}

	// 끝. 토폴로지 계산.

	// 방향성 체크

	public static boolean isCcw(GeoPoint p, GeoPoint q, GeoPoint r) {

		return getCrossProduct(p, q, r) > 0;

		// return getCrossProduct( getVector(p, q), getVector(p, r)) > 0 ;

	}

	public static boolean isCcw(GeoPoint[] pointList) {
		return getAreaProduct(pointList) > 0;
	}

	// 끝. 방향성 체크.

	// 프로덕트.

	public static double getCrossProduct(GeoPoint p, GeoPoint q) {

		return p.x * q.y - p.y * q.x;

	}

	public static double getCrossProduct(GeoPoint o, GeoPoint p, GeoPoint q) {

		return getCrossProduct(getVector(p, o), getVector(q, o));
	}

	public static GeoPoint getVector(GeoPoint p, GeoPoint q) {

		return new GeoPoint(q.getX() - p.getX(), q.getY() - p.getY());

	}

	public static double getPolygonArea(GeoPoint[] pointList) {

		return Math.abs(getAreaProduct(pointList) / 2.0);

	}

	public static double getAreaProduct(GeoPoint o, GeoPolyLine polyLine ) {
		return getAreaProduct( o, polyLine.getPointList() );
	}

	public static double getAreaProduct(GeoPoint o, GeoPoint[] pointList) {

		if (pointList.length < 3) {
			return 0;
		}

		double area = 0;

		GeoPoint p, q;

		for (int i = 0, iLen = pointList.length - 1 ; i < iLen; i++) {
			p = pointList[i];
			q = pointList[ i + 1 ];
			area += ((p.x - o.x) * (q.y - o.y)) - ((q.x - o.x) * (p.y - o.y));
		}

		return area;

	}

	public static double getAreaProduct(GeoPoint[] pointList) {

		if (pointList.length < 3) {
			return 0;
		}

		double area = 0;

		GeoPoint p, q;

		for (int i = 0, iLen = pointList.length - 1 ; i < iLen; i++) {
			p = pointList[i];
			q = pointList[ i + 1 ];
			area += (p.x * q.y) - (q.x * p.y);
		}

		return area;

	}
	// 끝. 프로덕트.

	public static double getLineLength(GeoPoint[] pointList) {

		if (pointList == null) {
			return 0;
		} else {
			double length = 0;
			GeoPoint p, q;
			double dx, dy;
			for (int i = 0, iLen = pointList.length - 1; i < iLen; i++) {
				p = pointList[i];
				q = pointList[i + 1];
				dx = p.x - q.x;
				dy = p.y - q.y;
				length += Math.sqrt(dx * dx + dy * dy);
			}

			return length;
		}

	}

	// MBR 알고리즘.

	public static Mbr calcMBR(GeoPoint[] pointList) {

		if (pointList == null || pointList.length < 1) {
			return null;
		} else {
			GeoPoint p = pointList[0];
			double minx = p.x, miny = p.y, maxx = minx, maxy = miny;
			for (int i = 1, len = pointList.length; i < len; i++) {
				p = pointList[i];
				minx = minx < p.x ? minx : p.x;
				miny = miny < p.y ? miny : p.y;
				maxx = maxx > p.x ? maxx : p.x;
				maxy = maxy > p.y ? maxy : p.y;
			}
			return new Mbr(minx, miny, maxx, maxy);
		}
	}

	public static Mbr unionMBR(GeoPolyLine[] polyList) {

		Mbr mbr = null;

		if (polyList == null || polyList.length < 1) {
			return null;
		} else {
			for (GeoPolyLine poly : polyList) {
				mbr = Mbr.union(mbr, poly.getMbr());
			}
		}

		return mbr;
	}

	// 끝. MBR 알고리즘.

	public static GeoPoint calcCentroidLine(GeoPolyLine poly) {

		if (poly == null) {
			return null;
		} else {

			GeoPoint[] pointList = poly.getPointList();

			if (pointList != null && pointList.length > 0) {

				final double lengthHalf = GeoAlgorithm.getLineLength(pointList) / 2.0;

				GeoPoint p = pointList[0];
				GeoPoint q = null;

				double lengthSum = 0;

				for (int i = 0, iLen = pointList.length - 1; i < iLen; i++) {
					q = pointList[i + 1];
					lengthSum += GeoAlgorithm.getLineLength(p, q);

					if (lengthSum >= lengthHalf) {
						return getGeoPoint_Middle(q, p, lengthSum - lengthHalf);
					}

					p = q;
				}

				if (p != null) {
					return new GeoPoint(p.x, p.y);
				} else {
					return null;
				}
			}

			return null;
		}

	}

	public static GeoPoint getGeoPoint_Middle(GeoPoint p, GeoPoint q, double l) {

		double len = GeoAlgorithm.getLineLength(p, q);

		if (len > 0) {

			double rx = p.x + (l / len) * (q.x - p.x);
			double ry = p.y + (l / len) * (q.y - p.y);

			return new GeoPoint(rx, ry);
		} else {
			return new GeoPoint(p.x, p.y);
		}

	}

	public static GeoPoint calcCentroidPolygon_New_Fail(GeoPolygon poly) {
		if (poly == null) {
			return null;
		} else {
			GeoPoint[] pointList = poly.getPointList();
			if (pointList != null && pointList.length > 0) {
				if (pointList.length > 2) {
					// 점 갯수가 2 보다 클때.....
					GeoPoint o = pointList[0];
					GeoPoint q, r, c;
					double isCCW = isCcw(pointList) ? 1 : -1;
					double massSum = 0, mass;
					double x = 0, y = 0;
					int pointsLen = pointList.length;
					for (int i = 1, iLen = pointList.length - 1; i < iLen; i++) {
						q = pointList[i];
						r = pointList[i + 1];
						mass = getCrossProduct(o, q, r);
						massSum += Math.abs(mass);
						c = getCentroidTriangle(o, q, r);
						x += isCCW * mass * c.x;
						y += isCCW * mass * c.y;
					}
					x = x / massSum;
					y = y / massSum;
					return new GeoPoint(x, y);
				} else if (pointList.length == 2) {
					GeoPoint p = pointList[0];
					GeoPoint q = pointList[1];
					return getGeoPoint_Middle(p, q, getLineLength(p, q) / 2.0);
				} else if (pointList.length == 1) {
					GeoPoint p = pointList[0];
					return new GeoPoint(p.x, p.y);
				}
			}

			return null;
		}

	}

	public static GeoPoint calcCentroidPolygon(GeoPolygon poly) {
		if (poly == null) {
			return null;
		} else {

			GeoPoint[] pointList = poly.getPointList();

			if (pointList != null && pointList.length > 0) {

				if (pointList.length > 2) {  // 점 갯수가 2 보다 클때.....
					final double area = GeoAlgorithm.getPolygonArea(pointList);

					GeoPoint p, q;
					double x = 0, y = 0;
					double factor;

					for (int i = 0, iLen = pointList.length; i < iLen; i++) {
						p = pointList[iLen - 1 - i];
						q = pointList[iLen - 1 - (i + 1) % iLen];

						factor = (p.x * q.y) - (q.x * p.y);

						x += ((p.x + q.x) * factor);
						y += ((p.y + q.y) * factor);
					}

					x = x / 6.0 / area;
					y = y / 6.0 / area;

					return new GeoPoint(x, y);

				} else if (pointList.length == 2) {
					GeoPoint p = pointList[0];
					GeoPoint q = pointList[1];
					return getGeoPoint_Middle(p, q, getLineLength(p, q) / 2.0);
				} else if (pointList.length == 1) {
					GeoPoint p = pointList[0];
					return new GeoPoint(p.x, p.y);
				}
			}

			return null;
		}

	}

	public static GeoPoint calcCentroidPolygon_New_Fail01(GeoPolygon poly) {
		if (poly == null) {
			return null;
		} else {
			GeoPoint[] pointList = poly.getPointList();

			if (pointList != null && pointList.length > 2) {

				double x = 0, y = 0;

				final GeoPoint p = pointList[0];

				GeoPoint point;

				final int iLen = pointList.length - 2;

				for (int i = 1; i < iLen; i++) {
					point = GeoAlgorithm.getCentroidTriangle(p, pointList[i], pointList[i + 1]);
					x += point.x;
					y += point.y;
				}

				x = x / iLen;
				y = y / iLen;

				return new GeoPoint(x, y);
			} else if (pointList.length == 2) {
				GeoPoint p = pointList[0];
				GeoPoint q = pointList[1];
				return getGeoPoint_Middle(p, q, getLineLength(p, q) / 2.0);
			} else if (pointList.length == 1) {
				GeoPoint p = pointList[0];
				return new GeoPoint(p.x, p.y);
			}

			return null;
		}

	}

	public static GeoPoint getCentroidTriangle(GeoPoint p, GeoPoint q, GeoPoint r) {

		return new GeoPoint((p.x + q.x + r.x) / 3.0, (p.y + q.y + r.y) / 3.0);

	}

	// 시작. 벡터 알고리즘.

	public static double getRadian(GeoPoint p, GeoPoint q) {

		return getRadian(q.x - p.x, q.y - p.y);

	}

	public static double getRadian(PntShort p, PntShort q) {

		return getRadian(q.x - p.x, q.y - p.y);

	}

	private static double getRadian(double x, double y) {

		double theta = Math.acos(x / Math.sqrt(x * x + y * y));

		if (x < 0 && y < 0) { // 3/4 분면
			theta = 2.0 * Math.PI - theta;
		} else if (x > 0 && y < 0) { // 4/4 분면
			theta = 2.0 * Math.PI - theta;
		}

		return theta;

	}

	public static double getDistum(GeoPoint p, GeoPoint q) {

		return (p.x - q.x) * (p.x - q.x) + (p.y - q.y) * (p.y - q.y);

	}

	public static double getLineLength(GeoPoint p, GeoPoint q) {

		return Math.sqrt((p.x - q.x) * (p.x - q.x) + (p.y - q.y) * (p.y - q.y));

	}

	public static double getLineLength(PntShort p, PntShort q) {

		return Math.sqrt((p.x - q.x) * (p.x - q.x) + (p.y - q.y) * (p.y - q.y));

	}

	public static void addGeoPoint(GeoPoint point, GeoPolyLine poly) {

		if (poly != null) {

			GeoPoint[] pointList = poly.getPointList();
			int len = pointList.length;
			GeoPoint[] newPointList = new GeoPoint[len + 1];
			System.arraycopy(pointList, 0, newPointList, 0, len);
			newPointList[len] = point;
			poly.setPointList(newPointList);

		}

	}

	// 끝. 벡터 알고리즘.

}
