package com.ynhenc.gis.file.esri_shape;

public class EsriShapeShpHeader {

	public int getRecordNumber() {
		return recordNumber;
	}

	public int getFid() {
		return this.recordNumber - 1;
	}

	@Override
	public String toString() {
		return "rec num = " + this.recordNumber + ", content Length = " + this.contentLength;
	}

	public EsriShapeShpHeader(int recordNumber, int contentLength) {
		this.recordNumber = recordNumber;
		this.contentLength = contentLength;
	}

	private int recordNumber;
	private int contentLength;

}
