package com.ynhenc.gis.file.esri_shape;

public abstract class EsriShapeObject {

	public int getFid() {
		return this.recordNo - 1;
	}

	public int getRecordNo() {
		return this.recordNo;
	}

	public EsriShapeObject(int recordNo) {
		this.recordNo = recordNo;
	}

	private int recordNo;

}
