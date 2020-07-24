package com.ynhenc.gis.model.shape;

import java.awt.*;
import java.util.*;

import com.ynhenc.comm.ArrayListEx;

public class ShapePointPoiList extends ArrayListEx<ShapePointPoi> {

	public void addList(ShapePointPoiList poiList) {
		if (poiList != null) {
			for (ShapePointPoi poi : poiList) {
				if (poi != null) {
					this.add(poi);
				}
			}
		}
	}

}
