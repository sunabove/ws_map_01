package com.ynhenc.gis.model.shape;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;


public class GeoPolygonHole extends GeoObject {

	@Override
	public boolean isIntersects(Mbr mbr) {
		if (this.getOuterRing().isIntersects(mbr)) {
			// TODO 2007.07.31 innerRing 교차 알고리즘 최적화 필요.
			if (false) {
				for (GeoPolygon innerRing : this.getInnerRingList()) {
					if (innerRing.isIncludes(mbr)) {
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public GeoPolygonHole(GeoPolygon outerRing, ArrayList<GeoPolygon> innerRingList) {
		this.outerRing = outerRing;
		this.innerRingList = innerRingList;
	}

	@Override
	public double getArea() {

		double area = this.getOuterRing().getArea();

		ArrayList<GeoPolygon> innerRingList = this.getInnerRingList();
		if (innerRingList != null) {
			for (GeoPolygon innerRing : innerRingList) {
				area -= innerRing.getArea();
			}
		}

		return area;
	}

	@Override
	public double getLength() {

		double length = this.getOuterRing().getLength();

		ArrayList<GeoPolygon> innerRingList = this.getInnerRingList();
		if (innerRingList != null) {
			for (GeoPolygon innerRing : innerRingList) {
				length += innerRing.getLength();
			}
		}

		return length;
	}

	@Override
	public Mbr getMbr() {
		return this.getOuterRing().getMbr();
	}

	@Override
	public Mbr calcMbr() {
		return this.getOuterRing().calcMbr();
	}

	public GeoPolygon getOuterRing() {
		return this.outerRing;
	}

	public ArrayList<GeoPolygon> getInnerRingList() {
		return this.innerRingList;
	}

	public void setInnerRingList(ArrayList<GeoPolygon> innerRingList) {
		this.innerRingList = innerRingList;
	}

	@Override
	public Shape createShape(Projection projection) {
		// TODO 2009.05.22 홀 도형 생성 최적화.
		// create outer area
		// intersect outer area and mbr screen area
		// for( innerRing ) : subtract inner ring from outer area

		// create outer ring area
		Area outerArea = new Area(this.getOuterRing().createShape(projection));

		Mbr mbrScr = projection.getMbrScreen();

		if (false) {
			// intersect outer ring and mbr screen rectangle
			outerArea.intersect(projection.getClippingRect());
		}

		// subtract inne ring area from outer ring area if mbr is intersect

		ArrayList<GeoPolygon> innerRingList = this.getInnerRingList();

		if (innerRingList.size() == 1) {
			GeoPolygon innerRing = innerRingList.get(0);
			if (innerRing.isIntersects(mbrScr)) {
				Area innerArea = new Area(innerRing.createShape(projection));
				this.debug.println(this, "Subtract Hole...");
				outerArea.subtract(innerArea);
			}
		} else if (innerRingList.size() > 1) {
			Area innerArea = new Area(innerRingList.get(0).createShape(projection));
			GeoPolygon innerRing;
			for (int i = 1, iLen = innerRingList.size(); i < iLen; i++) {
				innerRing = innerRingList.get(i);
				if (innerRing.isIntersects(mbrScr)) {
					this.debug.println(this, "Adding Hole..." + (i + 1));
					innerArea.add(new Area(innerRing.createShape(projection)));
				}
			}
			outerArea.subtract(innerArea);
		}

		return outerArea;

	}

	public Shape createShape_Old(Projection projection) {
		// TODO 2009.05.22 홀 도형 생성 최적화.
		// create outer area
		// intersect outer area and mbr screen area
		// for( innerRing ) : subtract inner ring from outer area

		// create outer ring area
		Area outerArea = new Area(this.getOuterRing().createShape(projection));

		Mbr mbrScr = projection.getMbrScreen();

		if (false) {
			// intersect outer ring and mbr screen rectangle
			outerArea.intersect(projection.getClippingRect());
		}

		// subtract inne ring area from outer ring area if mbr is intersect
		int index = 0;
		for (GeoPolygon innerRing : this.getInnerRingList()) {
			if (innerRing.isIntersects(mbrScr)) {
				this.debug.println(this, "Making Hole..." + (index + 1) + ", centroid:" + innerRing.getCentroid());
				outerArea.subtract(new Area(innerRing.createShape(projection)));
				index++;
			}
		}

		return outerArea;

	}

	@Override
	public GeoPoint getCentroid() {

		return this.getOuterRing().getCentroid();

	}

	@Override
	public boolean isContainedByCircleRadium(GeoPoint center, double radium) {
		return this.getOuterRing().isContainedByCircleRadium(center, radium);
	}

	@Override
	public GeoPoint[] getPointList() {
		// TODO 2008.07.20 홀 좌표값 정보 최적화 필요.
		return this.getOuterRing().getPointList();
	}

	private GeoPolygon outerRing;
	private ArrayList<GeoPolygon> innerRingList;

}
