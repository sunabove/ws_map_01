package com.ynhenc.gis.file.esri_shape;

import java.awt.*;

import com.ynhenc.comm.util.*;
import com.ynhenc.gis.model.shape.*;
import com.ynhenc.gis.ui.comp.*;

public class EsriShapePolyLine extends EsriShapeObject {

	private Mbr box;

	private int numParts;
	private int numPoints;
	private int[] parts;
	private GeoPoint[] points;

	public EsriShapePolyLine(int recordNumber, Mbr box, int numParts, int numPoints, int[] parts, GeoPoint[] points) {

		super(recordNumber);

		this.box = box;

		this.numParts = numParts;
		this.numPoints = numPoints;
		this.parts = parts;
		this.points = points;
	}

	@Override
	public String toString() {
		return this.getShapeType() + " recNum = " + this.getRecordNo() + ", BOX = " + this.box
				+ ", numParts = " + this.numParts + ", numPoints = "
				+ this.numPoints;
	}

	public String getShapeType() {
		return "PolyLine";
	}

	public int getNumParts() {
		return this.numParts;
	}

	public int getNumPoints() {
		return this.numPoints;
	}

	public int[] getParts() {
		return this.parts;
	}

	public GeoPoint[] getPoints() {
		return this.points;
	}

	public Mbr getBox() {
		return this.box;
	}

}
