package gmlviewer.gis.model;

import gmlviewer.gis.map.*;

public abstract class Shape extends MapObject {

  public Shape( String name, int sid ) {
    super( name, sid );
    this.sid = sid;
    this.shapeGeom = null;
    this.shape = null;
    this.proj = null;
  }

  public String toString() {
    return "GeomFromText(\'" + toGeomText() + "\')" ;
  }

  public java.awt.Shape getShape( Project proj ) {
    if( proj.equals( this.proj ) ) {
      if( this.shape != null ) {
        return this.shape;
      } else {
        this.shape = createShape( proj );
        return this.shape;
      }
    } else {
      this.shape = createShape(proj);
      this.proj = proj;
      return shape;
    }
  }

  public double cross(Pnt p, Pnt q) {

    return p.x * q.y - p.y * q.x;

  }

  public void setShape(java.awt.Shape shape) {
    this.shape = shape;
  }

//  public void paint(Graphics2D g2, Project proj, Style style) {
//      style.paint( g2, this.getShape( proj ) );
//  }

  public double getLength() {
    Pnt[] points = this.getGeoPoints();
    int len = points.length - 1;
    double length = 0;
    Pnt p, q;
    double dx, dy;
    for (int i = 0; i < len; i++) {
      p = points[i];
      q = points[i + 1];
      dx = p.x - q.x;
      dy = p.y - q.y;
      length += Math.sqrt(dx * dx + dy * dy);
    }
    return length;
  }

  public void setLyr( Lyr lyr ) {
    this.lyr = lyr;
  }

  public void setShapeAttribute(ShapeAttribute shapeAttribute) {
    this.shapeAttribute = shapeAttribute;
  }

  public int getSid() {
    return this.sid;
  }

  public ShapeAttribute getShapeAttribute() {
    return shapeAttribute;
  }

  public Pnt getCenter() {
    return null;
  }

  public abstract Pnt[] getGeoPoints();

  public abstract void addGeoPoint(Pnt point);

  public abstract double getArea();

  public abstract java.awt.Shape createShape( Project proj );

  public abstract String toGeomText();

  // end of abstract methods

  // member
  private int sid;
  private Lyr lyr;
  private Geometry shapeGeom;
  private ShapeAttribute shapeAttribute;

  private transient java.awt.Shape shape;
  private transient Project proj;

  // static member

  public static final int
      NULL_SHAPE = 0,

      POINT = 1,
      POLY_LINE = 3,
      POLYGON = 5,
      MULTI_POINT = 8,

      POINT_Z = 11,
      POLY_LINE_Z = 13,
      POLYGON_Z = 15,
      MULTI_POINT_Z = 18,

      POINT_M = 21,
      POLY_LINE_M = 23,
      POLYGON_M = 25,
      MULTI_POINT_M = 28,

      MULTI_PATCH = 31,

      // type defined by sbmoon
      TEXT = 32
      // end of type defined by sbmoon

      ;

}
