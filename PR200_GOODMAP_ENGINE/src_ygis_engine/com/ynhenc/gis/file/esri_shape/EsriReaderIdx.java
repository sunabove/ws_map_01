package com.ynhenc.gis.file.esri_shape;

import java.io.*;

import com.ynhenc.gis.file.*;

public class EsriReaderIdx extends DataReader {

	public EsriReaderIdx( File file ) throws Exception {

		super( file );

		this.recordNoTot =  ( this.getLength() - 100L ) / 8L;

		this.seek( 100 ); // ��� �κ� 100 ����Ʈ�� �ǳ� �ڴ�.
	}

	public EsriShapeIdxHeader getNextRecordHeader() throws Exception {

		if ( this.getPosition() < this.getLength() ) {

			EsriShapeIdxHeader rh = new EsriShapeIdxHeader(this.recordNoCur, this.readIntBig(), this.readIntBig());

			this.recordNoCur++;

			return rh;
		}

		return null;

	}

	private long recordNoTot = -1;
	private int recordNoCur = -1;

}
