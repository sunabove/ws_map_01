package com.ynhenc.gis.file.esri_shape;

import java.awt.*;

import com.ynhenc.gis.model.shape.*;
import com.ynhenc.gis.ui.comp.*;

public class EsriShapePolygon extends EsriShapePolyLine {

	public EsriShapePolygon(int recordNumber, Mbr box, int numParts,
			int numPoints, int[] parts, GeoPoint[] points) {
		super(recordNumber, box, numParts, numPoints, parts, points);
	}

	@Override
	public String getShapeType() {
		return "Polygon";
	}

}
