package com.ynhenc.gis.file.esri_shape;

public class EsriShapeIdxHeader {

	public long getOffSet() {
		return offSet;
	}

	@Override
	public String toString() {
		return "rec num = " + this.recordNumber + ", offSet = " + this.offSet + ", content Length = " + this.contentLength;
	}

	public EsriShapeIdxHeader(int recordNumber, long offSet, int contentLength) {
		this.recordNumber = recordNumber;
		this.offSet = offSet;
		this.contentLength = contentLength;
	}

	private int recordNumber;
	private long offSet;
	private int contentLength;

}
