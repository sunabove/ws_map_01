package gmlviewer.gis.file.shape;

import java.io.*;
import java.util.*;
import gmlviewer.gis.model.*;
import gmlviewer.gis.comp.*;
import gmlviewer.gis.*;
import gmlviewer.gis.map.*;
import gmlviewer.gis.file.*;

public class ShapeFileReader
    extends DataFileReader {

  public ShapeFileReader( InputStream src ) {
    super( src );
  }

  public void readLayer() throws IOException {
    readMainFile();
  }

  private void readMainFile() throws IOException {

    debug.debug(this, "Reading Main File ......");

    // reads main file header

    readMainFileHeader();

    // end of reading main file header

    // reads main file record contents

    readMainFileRecordContents();

    // end of reading main file record contents

    debug.debug(this, "Done reading main file.");

  }

  private void readMainFileHeader() throws IOException {

    debug.debug(this, "Reading main file header .....");

    // reads file code reading
    this.fileCode = IntBig();

    debug.debug(this, "file code = " + fileCode);

    // end of file code reading

    // reads unused bytes
    readBytes(20);
    // end of reading unused bytes

    // reads file length

    long shapeLength = IntBig();

    this.length = 2*shapeLength;

    debug.debug(this, "file length = " + shapeLength );

    // end of reading file length

    // reads version

    this.version = IntLittle();

    debug.debug(this, "version = " + version);

    // end of reading version

    // reads shape type

    this.shapeType = IntLittle();

    // end of reading shape type

    // reads bounding box

    this.xmin = DoubleLittle();
    this.ymin = DoubleLittle();
    this.xmax = DoubleLittle();
    this.ymax = DoubleLittle();

    // end of reading bounding box

    // reads z and m data
    this.zmin = DoubleLittle();
    this.zmax = DoubleLittle();
    this.mmin = DoubleLittle();
    this.mmax = DoubleLittle();

    // end of reading z and m data

    fireWorkStateEvent(createWorkStateEvent());

    debug.debug(this, "" + this);

  }

  public String toString() {

    return
        "File Code = " + fileCode
        + ", File Length = " + length
        + ", Version = " + version
        + ", Shape Type = " + shapeType
        + ", Xmin = " + xmin + ", Ymin = " + ymin + ", Xmax = " + xmax +
        ", Ymax = " + ymax
        + ", Zmin = " + zmin + ", Zmax = " + zmax + ", Mmin = " + mmin +
        ", Mmax = " + mmax;

  }

  public void readMainFileRecordContents() throws IOException {

    int shapeType = this.shapeType;

    if (shapeType == POLYGON) {

      readPolygons();

    }
    else if (shapeType == POLY_LINE) {

      readPolyLines();

    }
    else {

      debug.debug(this, "Not Supported Shape Type = " + shapeType);

    }

  }

  public void readPolyLines() throws IOException {

    debug.debug(this, "reading polygon shapes .....");

    final long length = this.length;

    for (; position < length; ) {

      readPolyLine(readRecordHeader());

      fireWorkStateEvent(createWorkStateEvent());

    }

    fireWorkStateEvent(createWorkStateEvent());

  }

  public void readPolyLine(RecordHeader recordHeader) throws IOException {
    //Utilities.debug(this, "Reading an polygon ....." );
    // reads shape type

    int shapeType = IntLittle();

    // end of reading shape type

    // reads box values

    double[] box = new double[4];

    for (int i = 0; i < 4; i++) {
      box[i] = DoubleLittle();
    }

    // end of reading box values

    // reads numParts
    int numParts = IntLittle();
    // end of reading numParts

    // reads numPoints
    int numPoints = IntLittle();
    // end of reading numPoints

    // reads Parts

    int[] parts = new int[numParts];

    for (int i = 0; i < numParts; i++) {
      parts[i] = IntLittle();
    }
    // end of reading Parts

    // reads Points

    Pnt[] points = new Pnt[numPoints];

    for (int i = 0; i < numPoints; i++) {
      points[i] = new Pnt(DoubleLittle(), DoubleLittle());
    }

    // end of reading Points

    addEsriShape(new ShapePolyLine(recordHeader.recordNumber, box, numParts,
                                   numPoints, parts, points));

  }

  public void readPolygons() throws IOException {

    debug.debug(this, "reading polygon shapes .....");

    final long length = this.length;

    for (; position < length; ) {

      readPolygon(readRecordHeader());

      fireWorkStateEvent(createWorkStateEvent());

    }

    fireWorkStateEvent(createWorkStateEvent());

  }

  class RecordHeader {

    int recordNumber;
    int contentLength;

    public RecordHeader(int recordNumber, int contentLength) {
      this.recordNumber = recordNumber;
      this.contentLength = contentLength;
    }

    public String toString() {
      return "rec num = " + recordNumber + ", content Length = " +
          contentLength;
    }

  }

  public RecordHeader readRecordHeader() throws IOException {

    RecordHeader recHeader = new RecordHeader(IntBig(), IntBig());
    debug.debug(this, "record header = " + recHeader);
    return recHeader;

  }

  public void readPolygon(RecordHeader recordHeader) throws IOException {

    //Utilities.debug(this, "Reading an polygon ....." );

    // reads shape type

    int shapeType = IntLittle();

    // end of reading shape type

    // reads box values

    double[] box = new double[4];

    for (int i = 0; i < 4; i++) {
      box[i] = DoubleLittle();
    }

    // end of reading box values

    // reads numParts
    int numParts = IntLittle();
    // end of reading numParts

    // reads numPoints
    int numPoints = IntLittle();
    // end of reading numPoints

    // reads Parts

    int[] parts = new int[numParts];

    for (int i = 0; i < numParts; i++) {
      parts[i] = IntLittle();
    }
    // end of reading Parts

    // reads Points

    Pnt[] points = new Pnt[numPoints];

    for (int i = 0; i < numPoints; i++) {
      points[i] = new Pnt(DoubleLittle(), DoubleLittle());
    }

    // end of reading Points

    addEsriShape(new ShapePolygon(recordHeader.recordNumber, box, numParts,
                                  numPoints, parts, points));

  }

  public void addEsriShape(EsriShape shape) {

    shapes.addElement(shape);

  }

  public long getReadBytesNumber() {
    return this.position;
  }

  public long getFileLength() {

    return this.length;

  }

  public Lyr getLayer( String lyrName ) {

    Shape[] spatialShapes = getSpatialShapes();

    if (spatialShapes.length < 1) {

      return null;

    }

    Shape refShape = spatialShapes[0];

    Lyr layer;

    if (refShape instanceof Area) {
      layer = new Lyr( lyrName, LyrType.AREA, new Integer(0) );
    }
    else {
      layer = new Lyr( lyrName, LyrType.LINE, new Integer(0)); // poly line type
    }

    layer.setMBR( new MBR( xmin, ymin, xmax, ymax) );

    for (int i = 0, len = spatialShapes.length; i < len; i++) {

      layer.addSpatialShape(spatialShapes[i]);

    }

    return layer;

  }

  private Shape[] getSpatialShapes() {

    if (shapeType == POLYGON) {
      return getSpatialShapesFromShapePolygons();
    }
    else if (shapeType == POLY_LINE) {
      return getSpatialShapesFromShapePolyLines();
    }
    else {
      return new Shape[] {};
    }

  }

  private Shape[] getSpatialShapesFromShapePolygons() {

    Vector shapes = this.shapes;

    Enumeration it = shapes.elements();

    Vector spatialShapesVec = new Vector();

    int recId = 0;

    while (it.hasMoreElements()) {

      ShapePolygon polygon = (ShapePolygon) it.nextElement();

      double[] box = polygon.getBox();

      int[] parts = polygon.getParts();

      Pnt[] points = polygon.getPoints();

      int from = 0, to = 0;

      int partNum = parts.length;

      for (int i = 0; i < partNum; i++) {

        if (i == partNum - 1) {
          to = points.length - 1;
        }
        else {
          to = parts[i + 1];
        }

        int pointsNum = to - from;

        Pnt[] spoints = new Pnt[pointsNum];

        for (int j = from, k = 0; j < to; j++) {
          spoints[k] = points[j];
          k++;
        }

        Area spatialArea = new Area( polygon.getRecordNumber(), spoints );

        spatialShapesVec.addElement(spatialArea);

        recId++;

        from = to;

      }

    }

    int len = spatialShapesVec.size();

    Shape[] spatialShapes = new Shape[len];

    it = spatialShapesVec.elements();

    int i = 0;

    while (it.hasMoreElements()) {

      spatialShapes[i] = (Shape) it.nextElement();

      i++;

    }

    return spatialShapes;

  }

  private Shape[] getSpatialShapesFromShapePolyLines() {

    Vector shapes = this.shapes;

    Enumeration it = shapes.elements();

    Vector spatialShapesVec = new Vector();

    int recId = 0;

    while (it.hasMoreElements()) {

      ShapePolyLine polyLine = (ShapePolyLine) it.nextElement();

      double[] box = polyLine.getBox();

      int[] parts = polyLine.getParts();

      Pnt[] points = polyLine.getPoints();

      int from = 0, to = 0;

      int partNum = parts.length;

      for (int i = 0; i < partNum; i++) {

        if (i == partNum - 1) {
          to = points.length - 1;
        }
        else {
          to = parts[i + 1];
        }

        int pointsNum = to - from;

        Pnt[] spoints = new Pnt[pointsNum];

        for (int j = from, k = 0; j < to; j++) {

          spoints[k] = points[j];

          k++;
        }

        Line spatialLine = new Line( "", polyLine.getRecordNumber(), spoints );

        spatialShapesVec.addElement(spatialLine);

        recId++;

        from = to;

      }

    }

    int len = spatialShapesVec.size();

    Shape[] spatialShapes = new Shape[len];

    it = spatialShapesVec.elements();

    int i = 0;

    while (it.hasMoreElements()) {

      spatialShapes[i] = (Shape) it.nextElement();

      i++;

    }

    return spatialShapes;

  }

  public StateEvent createWorkStateEvent() {
    long length = this.length;
    return
        new StateEvent(
        position, length,
        (position < length) ? "reading now....." : "reading done."
        );

  }

  private int fileCode;
  private int version;
  private int shapeType;

  private double xmin, ymin, xmax, ymax, zmin, zmax, mmin, mmax;

  private Vector shapes = new Vector();

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

}
