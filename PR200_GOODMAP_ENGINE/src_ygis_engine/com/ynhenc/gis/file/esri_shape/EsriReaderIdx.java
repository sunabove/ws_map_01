package com.ynhenc.gis.file.esri_shape;

import java.io.*;

import com.ynhenc.gis.file.*;

public class EsriReaderIdx extends DataReader {

	public EsriReaderIdx( File file ) throws Exception {

		super( file );

		this.recordNoTot =  ( this.getLength() - 100L ) / 8L;

		this.seek( 100 ); // 헤더 부분 100 바이트를 건너 뛴다.
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
