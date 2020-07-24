package gmlviewer.gis.model;

import java.awt.*;
import java.awt.geom.*;
import gmlviewer.gis.map.*;
import gmlviewer.gis.style.*;
import java.util.*;
import java.io.*;

public class Lyr
    extends MapFolder implements Comparable {

  public Lyr( String lyrName, LyrType lyrType,  int index) {
    this( lyrName, lyrType, new Integer(index));
  }

  public Lyr( String lyrName, LyrType lyrType, Integer index) {
    super( lyrName, index );
    this.lyrType = lyrType;
    this.lyrName = this.getName();
    this.mbr = null;
  }

  public LyrType getLyrType() {
    return this.lyrType;
  }

  public boolean isTextType() {
    return this.lyrType == LyrType.TEXT;
  }

  public void addSpatialShape(Shape shape) {
    this.add(shape);
    shape.setLyr(this);
  }

  public void removeSpatialShape(Shape shape) {
    this.remove(shape);
  }

  public void removeAllShapes() {
    this.removeAll();
  }

  public Style getStyle() {
    if( this.style != null ) {
      return this.style;
    } else {
      return this.style;
    }
  }

  public void setStyle( Style style ) {
     this.style = style;
  }

  private void paintOld(Graphics2D g2, Project proj) {

    Style style = this.getStyle();

    Shape[] shapes = getSpatialShapes();

    //debug( "LYR CODE = " + this.code );

    for (int i = 0, len = shapes.length; i < len; i++) {
      style.paint(g2, shapes[i], proj);
    }

  }

  public void paintNonTextOnly(Graphics2D g2, Project proj) {

    Stroke orgStroke = g2.getStroke();
    Paint orgPaint = g2.getPaint();

    Style style = this.getStyle();

    Shape[] shapes = getSpatialShapes();

    //debug( "LYR CODE = " + this.code );

    Shape shape;

    for (int i = 0, len = shapes.length; i < len; i++) {
      shape = shapes[i];
      if (! (shape instanceof ShapeText)) {
        style.paint(g2, shape, proj);
      }
    }

    g2.setStroke( orgStroke );
    g2.setPaint( orgPaint );

  }

  public void paintTextOnly(Graphics2D g2, Project proj) {

    Stroke orgStroke = g2.getStroke();
    Paint orgPaint = g2.getPaint();

    Style style = this.getStyle();

    Shape[] shapes = getSpatialShapes();

    //debug( "LYR CODE = " + this.code );

    Shape shape;
    GmlArea gmlArea;

    for (int i = 0, len = shapes.length; i < len; i++) {
      shape = shapes[i];

      if( shape instanceof GmlArea ) {

        gmlArea = (GmlArea) shape;
        gmlArea.paintTextOnly( g2, proj );

      } else if (shape instanceof ShapeText) {
        style.paint(g2, shape, proj);
      }
    }

    g2.setStroke( orgStroke );
    g2.setPaint( orgPaint );

  }

  public Shape[] getSpatialShapes() {
    int len = this.getSize();
    Shape[] ss = new Shape[len];
    this.toArrays(ss);
    return ss;
  }

  public void saveAsWKT(Writer out) throws IOException {
    Shape[] shapes = this.getSpatialShapes();
    Shape shape;
    String wkt;
    String nl = "\r\n";

    for (int i = 0, len = shapes.length; i < len; i++) {
      shape = shapes[i];
      if (shape != null) {
        wkt = shape.toGeomText();
        debug("WKT = " + wkt);
        out.write(wkt);
        out.write(nl);
      }
    }
  }

  public Lyr getShapesIncludes(int x, int y, Project proj) {

    Lyr layer = new Lyr( this.lyrName,  this.lyrType, new Integer(0) );

    Shape[] shapes = getSpatialShapes();
    Shape shape;
    java.awt.Shape shp;
    for (int i = 0, len = shapes.length; i < len; i++) {
      shape = shapes[i];
      shp = shape.getShape(proj);
      if (shp.contains(x, y)) {
        layer.addSpatialShape(shape);
      }
    }

    return layer;
  }

  public void setLineColor(java.awt.Color color) {

  }

  public void setFillColor(java.awt.Color color) {

  }

  public Color getLineColor() {
    return lineColor;
  }

  public Color getFillColor() {
    return fillColor;
  }

  public int compareTo(Object obj) {
    return 0;

  }

  // abstract method

  // end of abstract method

  // member
  protected LyrType lyrType;
  private String lyrName;

  private Color lineColor;
  private Color fillColor;

  private Style style;

  // end of member

}
