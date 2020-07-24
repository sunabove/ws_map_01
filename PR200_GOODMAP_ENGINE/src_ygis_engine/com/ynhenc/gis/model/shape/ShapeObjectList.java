package com.ynhenc.gis.model.shape;

import java.awt.*;
import java.util.*;

import com.ynhenc.comm.ArrayListEx;

public class ShapeObjectList extends ArrayListEx<ShapeObject> {

	 public ShapeHash getShapeObjHash() {
		return shapeHash;
	}

	public ShapeObjectList() {
		this.shapeHash = new ShapeHash();
	}

	private ShapeHash shapeHash;

}
