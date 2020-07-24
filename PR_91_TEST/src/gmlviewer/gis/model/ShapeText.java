package gmlviewer.gis.model;

import gmlviewer.gis.style.*;
import java.awt.Shape;
import java.awt.Graphics2D;
import java.awt.geom.*;
import gmlviewer.gis.style.*;

public class ShapeText
    extends gmlviewer.gis.model.Shape {

  public ShapeText( String name, int id, String text, Pnt point, Pnt offSet )
  {
    this( name, id, text, point, offSet.x, offSet.y );
  }


  public ShapeText( String name, int id, String text, double x, double y,
              double offSetX, double offSetY) {
    this( name, id, text, new Pnt( x, y ), offSetX, offSetY );
  }

  public ShapeText( String name, int id, String text, Pnt point,
               double offSetX, double offSetY )
  {
    super( name, id );
    this.text = text;
    this.point = point;
    this.offSetX = offSetX;
    this.offSetY = offSetY;
  }

  public Shape createShape( Project proj ) {
    return null;
  }

  public double getLength() {
    return 0;
  }

  public Pnt[] getGeoPoints() {
    return null;
  }

  public double getArea() {
    return 0;
  }

  public void addGeoPoint(Pnt point) {
  }

  public Pnt getPoint() {
    return this.point;
  }

  public String getString() {
    return text;
  }

  public MBR calcMBR() {
    return new MBR( point, point );
  }

  public String toGeomText() {
    if( point == null ) {
      return "NULL";
    } else {
      return "POINT(" + point.x + " " + point.y + ")";
    }
  }

  public double getOffSetX() {
    return this.offSetX;
  }

  public double getOffSetY() {
    return this.offSetY;
  }

  public String toString() {
    return "POI = " + this.getName() + ", TEXT = " + this.text + ", " + point
        + ", OFFSET = " + offSetX + ", " + offSetY ;
  }

  // member
  private String text;
  public String iching;

  private Pnt point;
  private double offSetX ;
  private double offSetY ;
  // end of member

}
