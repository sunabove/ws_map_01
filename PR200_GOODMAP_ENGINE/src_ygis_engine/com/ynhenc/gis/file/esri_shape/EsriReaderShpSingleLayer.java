package com.ynhenc.gis.file.esri_shape;

import java.io.*;
import java.util.*;

import com.ynhenc.gis.file.*;
import com.ynhenc.gis.model.map.*;
import com.ynhenc.gis.model.shape.*;
import com.ynhenc.gis.projection.*;
import com.ynhenc.gis.ui.comp.*;

public class EsriReaderShpSingleLayer extends DataReader {

	public Layer getLayer() {
		return layer;
	}

	public CoordinateConversion getCoordinateConversion() {
		return this.coordinateConversion;
	}

	public void setCoordinateConversion(CoordinateConversion coordinateConversion) {
		this.coordinateConversion = coordinateConversion;
	}

	public void readMainFileHeader() throws Exception {

		boolean localDebug = false;

		this.debug("Reading main file header .....");

		this.seek(0);

		// reads file code reading
		this.fileCode = this.readIntBig();

		if (localDebug) {
			this.debug("file code = " + this.fileCode);
		}

		// end of file code reading

		// reads unused bytes
		this.skipBytes(20);
		//this.readBytes(20);
		// end of reading unused bytes

		// reads file length

		long shapeLength = this.readIntBig();

		long length = 2 * shapeLength;

		if (localDebug) {
			this.debug("file length = " + length);
		}

		// end of reading file length

		// reads version

		this.version = this.readIntLittle();

		if (localDebug) {
			this.debug("version = " + this.version);
		}

		// end of reading version

		// reads shape type

		int shapeType = this.readIntLittle();

		this.shapeType = shapeType;

		Layer layer = this.getLayer();

		if (shapeType == this.POLY_LINE) {
			layer.setLyrType(LayerType.LINE);
		} else if (shapeType == this.POLYGON) {
			layer.setLyrType(LayerType.AREA);
		} else if (shapeType == this.POINT) {
			layer.setLyrType(LayerType.POINT);
		}

		// end of reading shape type

		// reads bounding box

		this.box = this.getReadMbr();

		if (true) {
			this.getLayer().setMbr(this.box);
		}

		// end of reading bounding box

		// reads z and m data

		this.zmin = this.readDoubleLittle();
		this.zmax = this.readDoubleLittle();
		this.mmin = this.readDoubleLittle();
		this.mmax = this.readDoubleLittle();

		// end of reading z and m data

		this.debug("" + this);

	}

	public void readMainFileRecordContents() throws Exception {

		try {
			this.readMainFileRecordContents_Runnable();
		} catch (Exception e) {
			this.debug(e);
		}

		if (true) {// 인덱스 리더 파일 클로징.
			EsriReaderIdx indexReader = this.indexReader;

			if (indexReader != null) {
				try {
					indexReader.close();
				} catch (Exception e) {
					this.debug(e);
				}
			}
		} // 끝. 인덱스 리더 파일 클로징.

		if (true) {// SHP 파일 클로징.
			this.close();
		} // 끝. SHP 파일 클로징.

	}

	public void readMainFileRecordContents_Runnable() throws Exception {

		int layerShapeType = this.shapeType;

		this.debug("reading shapeType " + layerShapeType);

		this.seek(100);

		ArrayList<ShapeObject> shapeList;

		EsriShapeShpHeader esriShapeShpHeader;

		for (; this.hasMoreRecord(this.recordNoCurr);) {

			if (false) {
				long pos = this.getPosition();
				this.debug("pos = " + pos + " : 0x" + Integer.toHexString((int) pos));
			}

			esriShapeShpHeader = this.readRecordHeader();

			if (false) {
				this.debug("record header = " + esriShapeShpHeader);
			}

			if (layerShapeType == POLYGON || layerShapeType == POLY_LINE) {
				shapeList = this.readSinglePolyLineOrPolygon(esriShapeShpHeader, layerShapeType);
			} else if (layerShapeType == POINT) {
				shapeList = this.readSinglePoint(esriShapeShpHeader, layerShapeType);
			} else {
				this.debug("Not Supported Shape_00_Object Type = " + this.shapeType);
				return;
			}

		}

	}

	protected boolean hasMoreRecord(int recordNo) throws Exception {

		if (this.indexReader != null) {
			EsriShapeIdxHeader idxRecordHeader = this.indexReader.getNextRecordHeader();
			if (idxRecordHeader != null) {
				long pos = idxRecordHeader.getOffSet() * 2L;
				this.seek(pos);
				return true;
			} else {
				return false;
			}
		} else {
			return this.getPosition() < this.getLength() && this.getPosition() > -1;
		}
	}

	public ShapeObjectList readSinglePoint(EsriShapeShpHeader header,
			int layerShapeType) throws Exception {

		// reads shape type
		int shapeType = this.readIntLittle();

		if( shapeType == NULL_SHAPE ) {
			// 널 도형 타입 처리.
			if (true) {
				this.debug( "ShapeType:" + shapeType + ", FID:" + header.getFid() + ", --> NULL SHAPE" );
			}
			return null;
		}

		// reads box values
		// 포인트 형은 박스를 읽어 들이지 않는다.
		// end of reading box values

		// read point
		GeoPoint geoPoint = this.getReadGeoPoint();

		if ( false ) {
			this.debug( "ShapeType:" + shapeType + ", FID:" + header.getFid() + ", point:" + geoPoint);
		}

		EsriShapeObject esriShape = new EsriShapePoint(header.getRecordNumber(), geoPoint);

		if (esriShape != null) {
			return this.addEsriShapeToLayerAfterConvertionToShape(esriShape);
		} else {
			return null;
		}

	}

	public ArrayList<ShapeObject> readSinglePolyLineOrPolygon(EsriShapeShpHeader header, int layerShapeType)
			throws Exception {

		// reads shape type

		int shapeType = this.readIntLittle();

		if( shapeType == NULL_SHAPE ) {
			if (true) {
				this.debug( "ShapeType:" + shapeType + ", FID:" + header.getFid() + ", --> NULL SHAPE" );
			}
			return null;
		}

		// end of reading shape type

		// reads box values
		Mbr box = this.getReadMbr();
		// end of reading box values

		// reads numParts
		int numParts = this.readIntLittle();

		if ( false ) {
			this.debug( "ShapeType:" + shapeType + ", FID:" + header.getFid() + ", numParts:" + numParts);
		}

		// reads numPoints
		int numPoints = this.readIntLittle();

		// reads Parts
		int[] parts = new int[numParts];

		for (int i = 0; i < numParts; i++) {
			parts[i] = this.readIntLittle();
		}
		// end of reading Parts

		// reads Points
		GeoPoint[] points = new GeoPoint[numPoints];
		for (int i = 0; i < numPoints; i++) {
			points[i] = this.getReadGeoPoint();
		}

		// end of reading Points

		EsriShapeObject esriShape = null;

		if (layerShapeType == POLYGON) {
			esriShape = new EsriShapePolygon(header.getRecordNumber(), box, numParts, numPoints, parts, points);
		} else if (layerShapeType == POLY_LINE) {
			esriShape = new EsriShapePolyLine(header.getRecordNumber(), box, numParts, numPoints, parts, points);
		}

		if (esriShape != null) {
			return this.addEsriShapeToLayerAfterConvertionToShape(esriShape);
		} else {
			return null;
		}

	}

	public ShapeObjectList addEsriShapeToLayerAfterConvertionToShape(EsriShapeObject esriShape) {

		// 도형 변환
		ShapeObjectList shapeList = this.convertToShape(esriShape);
		// 끝. 도형 변환.

		if (shapeList != null) {
			this.getLayer().addSpatialShapeList(shapeList);
		} else {
			this.debug("NULL SHAPELIST encountered.");
		}

		return shapeList;

	}

	private ShapeObjectList convertToShape(EsriShapeObject esriShape) {

		if (this.shapeType == POLYGON) {
			return this.convertToShapeLineAreaList((EsriShapePolygon) esriShape);
		} else if (this.shapeType == POLY_LINE) {
			return this.convertToShapeLineList((EsriShapePolyLine) esriShape);
		} else if (this.shapeType == POINT) {
			return this.convertToShapePointList((EsriShapePoint) esriShape);
		}

		return null;
	}

	private ShapeObjectList convertToShapePointList(EsriShapePoint esriPoint) {
		ShapeObjectList shapeList = new ShapeObjectList();

		ShapePoint shape = new ShapePoint(shapeId++, esriPoint.getRecordNo(), esriPoint.getPoint());

		shapeList.add(shape);

		return shapeList;
	}

	private ShapeObjectList convertToShapeLineAreaList(EsriShapePolygon esriPolyLine) {
		Mbr box = esriPolyLine.getBox();

		final int[] parts = esriPolyLine.getParts();

		final GeoPoint[] points = esriPolyLine.getPoints();

		final int partNum = parts.length;

		final ArrayList<GeoPolygon> polygonList = new ArrayList<GeoPolygon>();

		int from = 0, to = 0;

		for (int i = 0; i < partNum; i++) {

			if (i == partNum - 1) {
				to = points.length; // debugged at 2008/MAY/20/TUE
			} else {
				to = parts[i + 1];
			}

			int pointsNum = to - from;

			GeoPoint[] pointList = new GeoPoint[pointsNum];

			for (int j = from, jLen = to, k = 0; j < jLen; j++, k++) {
				pointList[k] = points[j];
			}

			from = to;

			polygonList.add(new GeoPolygon(pointList));

		}

		final int recordNo = esriPolyLine.getRecordNo();
		ShapeObjectList shapeList = new ShapeObjectList();
		GeoPolygon outerPoly = null, innerPoly = null;
		for (int i = 0, iLen = polygonList.size(); i < iLen; i ++ ) {
			outerPoly = polygonList.get(i);

			ArrayList<GeoPolygon> innerRingList = null;
			boolean isCCW_Q;
			for (int k = i + 1 , kLen = iLen ; k < kLen; k++) {
				innerPoly = polygonList.get(k);
				isCCW_Q = innerPoly.isCCW();
				if (isCCW_Q) { // inner ring
					if (innerRingList == null) {
						innerRingList = new ArrayList<GeoPolygon>();
					}
					innerRingList.add(innerPoly);
				} else {
					k = kLen;
				}
			}

			if (innerRingList == null || innerRingList.size() < 1) {
				ShapeObject shape = new ShapeArea(shapeId++, recordNo, outerPoly);
				shapeList.add(shape);
			} else {
				// 내부 홀이 있을 때...
				if (true) {
					this.debug("FID : " + esriPolyLine.getFid() + ", HOLE NUMBER : " + innerRingList.size());
				}

				i += ( innerRingList.size() );

				GeoPolygonHole polygonHole = new GeoPolygonHole(outerPoly, innerRingList);

				ShapeObject shape = new ShapeAreaHole(shapeId++, recordNo, polygonHole);
				shapeList.add(shape);
			}
		}

		return shapeList;
	}

	private ShapeObjectList convertToShapeLineList(EsriShapePolyLine polyLine) {

		ShapeObjectList shapeList = new ShapeObjectList();

		ShapeObject shape = null;

		Mbr box = polyLine.getBox();

		int[] parts = polyLine.getParts();

		GeoPoint[] points = polyLine.getPoints();

		int from = 0, to = 0;

		int partNum = parts.length;

		for (int i = 0; i < partNum; i++) {

			if (i == partNum - 1) {
				to = points.length; // debugged at 2008/MAY/20/TUE
			} else {
				to = parts[i + 1];
			}

			int pointsNum = to - from;

			GeoPoint[] pointList = new GeoPoint[pointsNum];

			for (int j = from, k = 0; j < to; j++) {
				pointList[k] = points[j];
				k++;
			}

			from = to;

			shape = new ShapeLine(shapeId++, polyLine.getRecordNo(), new GeoPolyLine(pointList));

			shapeList.add(shape);

		}

		return shapeList;
	}

	private Mbr getReadMbr() throws Exception {
		return new Mbr(this.getReadPnt(), this.getReadPnt());
	}

	private GeoPoint getReadGeoPoint() throws Exception {
		PntShort pntShort = this.getReadPnt();

		return new GeoPoint(pntShort.getX(), pntShort.getY());
	}

	private PntShort getReadPnt() throws Exception {
		if (true) {
			return this.getCoordinateConversion().getConvertValue(Wgs.wgs(this.readDoubleLittle(), this.readDoubleLittle()));
		} else {
			return PntShort.pntShort(this.readDoubleLittle(), this.readDoubleLittle());
		}
	}

	public StateEvent getStateEvent() {

		try {
			// TODO 2008.11.28.금. 001 상태 메시지 전달 최적화.

			// this.stateEvent.current = this.getPosition();
			// this.stateEvent.total = this.getLength();
		} catch (Exception e) {
			this.debug(e);
			this.stateEvent.current = -1;
			this.stateEvent.total = -1;
		}

		return this.stateEvent;

	}

	public EsriShapeShpHeader readRecordHeader() throws Exception {

		EsriShapeShpHeader esriShapeShpHeader = new EsriShapeShpHeader(this.readIntBig(), this
				.readIntBig());

		this.recordNoCurr++;

		return esriShapeShpHeader;

	}

	@Override
	public String toString() {

		return "File Code = " + this.fileCode + ", File Length = " + this.getLength() + ", Version = " + this.version
				+ ", Shape_00_Object Type = " + this.shapeType + ", Box = " + this.box + ", Zmin = " + this.zmin + ", Zmax = "
				+ this.zmax + ", Mmin = " + this.mmin + ", Mmax = " + this.mmax;

	}

	private EsriReaderShpSingleLayer(Layer layer, CoordinateConversion coordinateConversion) throws Exception {

		super(layer.getFileShape_Shp());

		this.coordinateConversion = coordinateConversion;
		this.layer = layer;

		if (true) {
			File fileIdx = layer.getFileShape_Idx();

			if (fileIdx != null && fileIdx.exists()) {
				this.debug("SHX: " + fileIdx.getCanonicalPath());
				this.indexReader = new EsriReaderIdx(fileIdx);
			} else {
				this.debug("SHX Wrong: " + layer.getFileShape_Idx());
			}
		}

		this.debug("READING LAYER =" + layer.getName() + "......");
	}

	private int shapeId = 0;

	private EsriReaderIdx indexReader;

	private CoordinateConversion coordinateConversion;

	private StateEvent stateEvent = new StateEvent(0, 1, "");

	private int recordNoCurr = 0;

	private int fileCode;
	private int version;
	private int shapeType;

	private Mbr box;

	private double zmin, zmax, mmin, mmax;

	private transient Layer layer;

	public static final int NULL_SHAPE = 0;
	public static final int POINT = 1;
	public static final int POLY_LINE = 3;
	public static final int POLYGON = 5;
	public static final int MULTI_POINT = 8;
	public static final int POINT_Z = 11;
	public static final int POLY_LINE_Z = 13;
	public static final int POLYGON_Z = 15;
	public static final int MULTI_POINT_Z = 18;
	public static final int POINT_M = 21;
	public static final int POLY_LINE_M = 23;
	public static final int POLYGON_M = 25;
	public static final int MULTI_POINT_M = 28;
	public static final int MULTI_PATCH = 31;

	public static EsriReaderShpSingleLayer getEsriShapeReader(Layer layer, CoordinateConversion coordinateConversion)
			throws Exception {

		final EsriReaderShpSingleLayer esriReader = new EsriReaderShpSingleLayer(layer, coordinateConversion);

		return esriReader;

	}

}
