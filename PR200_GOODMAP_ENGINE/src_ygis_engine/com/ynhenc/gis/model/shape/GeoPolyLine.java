package com.ynhenc.gis.model.shape;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;


public class GeoPolyLine extends GeoObject {

	@Override
	public boolean isIntersects(Mbr mbr) {

		if (Mbr.isIntersects(this.getMbr(), mbr)) {
			GeoPoint[] pointList = this.getPointList();
			if (true) {
				for (GeoPoint point : pointList) {
					if (mbr.isIncludes(point)) {
						return true;
					}
				}
			}

			GeoPoint[] cornerPointList = mbr.getCornerPointList();
			int topo;
			for (int i = 0, iLen = 4; i < iLen; i++) {
				topo = GeoAlgorithm.getTopoLineToPolyLine(cornerPointList[i], cornerPointList[(i + 1) % iLen], pointList);
				if (topo < 0) { // 교차할 경우 ....
					return true;
				}
			}
		}

		return false;
	}

	public GeoPolyLine(GeoPoint[] pointList) {
		this.pointList = pointList;
	}

	@Override
	public Mbr getMbr() {

		if (this.mbr == null) {
			this.mbr = this.calcMbr();
		}

		return mbr;
	}

	@Override
	public Mbr calcMbr() {
		return GeoAlgorithm.calcMBR(this.pointList);
	}

	@Override
	public GeoPoint[] getPointList() {
		return pointList;
	}

	public void setPointList(GeoPoint[] pointList) {
		this.pointList = pointList;
	}

	@Override
	public double getLength() {
		return GeoAlgorithm.getLineLength(this.pointList);
	}

	@Override
	public double getArea() {
		return 0;
	}

	public ArrayList<GeoPoint> getPointListToRender(Projection proj) {

		ArrayList<GeoPoint> renderList = new ArrayList<GeoPoint>();

		Mbr mbr = proj.getMbrScreen();

		GeoPoint[] pointList = this.getPointList();

		GeoPoint prev = null, curr;
		int prevTopo = 0; // previous topology, -1 : out, 0 : not set, +1 : in
		int skipNo = 0;

		boolean isPolygon = this instanceof GeoPolygon;

		for (int i = 0, iLen = pointList.length; i < iLen; i++) {

			curr = pointList[i];

			if ( true || isPolygon) {
				renderList.add(curr);
			} else if (mbr.isIncludes(curr)) {
				// when inside
				if (prevTopo < 0) {
					if (skipNo > -1) {
						renderList.add(prev);
					}
					skipNo = 0;
				}
				renderList.add(curr);
				prevTopo = 1;
			} else {
				// when outside
				if (prevTopo > 0) {
					renderList.add(curr);
					skipNo = 0;
				} else {
					skipNo++;
				}
				prevTopo = -1;
			}

			prev = curr;
		}

		return renderList;

	}

	@Override
	public Shape createShape(Projection proj) {
		ArrayList<GeoPoint> pointList = this.getPointListToRender(proj);
		GeneralPath gp = new GeneralPath();
		if (pointList.size() > 0) {
			PntShort scrPnt = proj.toGraphics(pointList.get(0));
			gp.moveTo(scrPnt.x, scrPnt.y);
			PntShort scrPntPrev = scrPnt;
			float distum;
			for (int i = 1, iLen = pointList.size(); i < iLen; i++) {
				scrPnt = proj.toGraphics(pointList.get(i));
				if (true) {// distum 비체크.
					gp.lineTo(scrPnt.x, scrPnt.y);
				} else {
					// distum 체크.
					distum = Math.abs(scrPnt.x - scrPntPrev.x) + Math.abs(scrPnt.y - scrPntPrev.y);
					if ( distum < 2) {
						if (i == iLen - 1) {
							gp.lineTo(scrPnt.x, scrPnt.y);
						}
					} else {
						gp.lineTo(scrPnt.x, scrPnt.y);
						scrPntPrev = scrPnt;
					}
				}
			}
		} else {
			// crate virtual path
			gp.moveTo(-1, -1);
			gp.lineTo(-1, -1);
		}

		return gp;
	}

	public Shape createShape_Old(Projection proj) {

		GeoPoint[] pointList = this.getPointList();

		GeneralPath gp = new GeneralPath();

		PntShort scrPnt = proj.toGraphics(pointList[0]);

		gp.moveTo(scrPnt.x, scrPnt.y);

		PntShort scrPntPrev = scrPnt;

		float distum;

		for (int i = 1, iLen = pointList.length; i < iLen; i++) {

			scrPnt = proj.toGraphics(pointList[i]);

			if (true) {// distum 비체크.
				gp.lineTo(scrPnt.x, scrPnt.y);
			} else {
				// distum 체크.
				distum = Math.abs(scrPnt.x - scrPntPrev.x) + Math.abs(scrPnt.y - scrPntPrev.y);
				if (distum < 2) {
					if (i == iLen - 1) {
						gp.lineTo(scrPnt.x, scrPnt.y);
					}
				} else {
					gp.lineTo(scrPnt.x, scrPnt.y);
					scrPntPrev = scrPnt;
				}
			}
		}

		return gp;

	}

	@Override
	public GeoPoint getCentroid() {
		return GeoAlgorithm.calcCentroidLine(this);
	}

	@Override
	public boolean isContainedByCircleRadium(GeoPoint center, double radium) {
		for (GeoPoint point : this.getPointList()) {
			if (!point.isContainedByCircleRadium(center, radium)) {
				return false;
			}
		}
		return true;
	}

	public boolean isCCW() {
		return GeoAlgorithm.isCcw(this.getPointList());
	}

	protected GeoPoint[] pointList;

	private Mbr mbr;

}
