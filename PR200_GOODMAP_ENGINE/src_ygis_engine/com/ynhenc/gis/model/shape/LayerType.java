package com.ynhenc.gis.model.shape;

public class LayerType {

	private LayerType(int type, String typeDesc) {
		this.type = type;
		this.typeDesc = typeDesc;
	}

	@Override
	public String toString() {
		return this.typeDesc;
	}

	// member
	private int type;
	private String typeDesc;
	// end of member

	// static member
	public static final LayerType POINT = new LayerType(ShapeObject.POINT, "X"),
			LINE = new LayerType(ShapeObject.POLY_LINE, "L"),
			AREA = new LayerType(ShapeObject.POLYGON, "A") ;
	// end of static member

}
