package gmlviewer.gis.model;

import java.awt.*;
import java.awt.geom.*;
import gmlviewer.gis.style.*;

public class Point extends Shape{

  public Point( String name, int recId, double x, double y ) {
     super( name, recId );
     this.point = new Pnt( x, y );
     this.kind = this.getKind();
  }

  public double getArea() {
     return 0;
  }

  public double getLength() {
     return 0;
  }

  public Pnt [] getGeoPoints() {
     return new Pnt [] { point };
  }

  public java.awt.Shape createShape( Project proj ) {
       Pnt gra = proj.toGraphics( this.point );
       double radius = 1.0;
       return new Ellipse2D.Double( gra.x - radius, gra.y - radius, radius*2, radius*2 );
  }

  public void addGeoPoint(Pnt point) {
     this.point = point;
     setShape( null );
  }

  public MBR calcMBR() {
    return new MBR( point.x, point.y, point.x, point.y );
  }

  public String toGeomText() {
    if( point == null ) {
      return "NULL";
    } else {
      return "POINT(" + point.x + " " + point.y + ")";
    }
  }

  private String kind;
  private Pnt point;

}
