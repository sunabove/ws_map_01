package gmlviewer.gis.model;


import java.awt.*;
import java.awt.geom.*;
import gmlviewer.gis.style.*;

public class Area extends Line {

  public Area( int recId, Pnt [] points ) {
     super( "", recId, points );
     this.surface = getArea();
  }

  public double getArea() {
     Pnt [] points = this.points;
     int len = points.length;
     if( len < 1 ) {
        return 0;
     }

     double area = 0;
     Pnt p = points[ 0 ];
     for( int i = 1; i < len; i ++ ) {
        area += cross( p, points[ i ] );
     }

     return Math.abs( area );
  }

  public java.awt.Shape createShape( Project proj ) {
      GeneralPath gp = (GeneralPath) ( super.createShape( proj ) );
      gp.closePath();
      return gp;
  }

  public String toGeomText() {
    if( points == null ) {
      return "NULL";
    } else {
      StringBuffer bfr = new StringBuffer();
      Pnt [] points = this.points;
      bfr.append( "POLYGON((" );
      Pnt point;
      for( int i = 0, len = points.length; i < len; i ++ ) {
        point = points[i];
        bfr.append( point.x + " " + point.y );
        if( i < len -1 ) {
          bfr.append( "," );
        }
      }
      bfr.append( "))" );
      return bfr.toString();
    }
  }

  private double surface;

}
