package com.ynhenc.gis.model.shape;

import java.awt.*;
import java.util.*;


public class ShapeHash extends Hashtable<Integer, Shape> {

	public Shape getShape(ShapeObject shapeObj, Projection projection) {

		Shape shape = this.get(shapeObj.getId());

		if (shape != null) {
			return shape;
		} else {
			shape = shapeObj.getShape(projection);
			this.put(shapeObj.getId(), shape);
			return shape;
		}
	}

}
