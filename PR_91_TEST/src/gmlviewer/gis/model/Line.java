package gmlviewer.gis.model;

import java.awt.geom.*;

public class Line
    extends Shape {

  public Line( String name, int recId, Pnt[] points) {
    super( name, recId);
    this.points = points;
    this.noPnt = points != null ? points.length : 0;
  }

  public double getArea() {

    return 0;

  }

  public java.awt.Shape createShape(Project proj) {

    Pnt[] points = getGeoPoints();

    int len = points.length;

    if (len < 1) {
      GeneralPath gp = new GeneralPath();
      gp.moveTo(0, 0);
      return gp;
    }
    else {
      GeneralPath gp = new GeneralPath();
      Pnt gra;
      gra = proj.toGraphics(points[0]);
      gp.moveTo( (float) gra.x, (float) gra.y);
      for (int i = 1; i < len; i++) {
        gra = proj.toGraphics(points[i]);
        gp.lineTo( (float) gra.x, (float) gra.y);
      }
      return gp;
    }

  }

  public Pnt[] getGeoPoints() {
    return points;
  }

  public void addGeoPoint(Pnt point) {

    Pnt[] points = this.points;
    int len = points.length;
    Pnt[] newPoints = new Pnt[len + 1];
    System.arraycopy(points, 0, newPoints, 0, len);
    newPoints[len] = point;
    this.points = newPoints;
    setShape(null);

  }

  public MBR calcMBR() {
    Pnt[] points = this.points;
    if (points == null || points.length < 1) {
      return null;
    }
    else {
      Pnt p = points[0];
      double minx = p.x, miny = p.y, maxx = minx, maxy = miny;
      for (int i = 1, len = points.length; i < len; i++) {
        p = points[i];
        minx = minx < p.x ? minx : p.x;
        miny = miny < p.y ? miny : p.y;
        maxx = maxx > p.x ? maxx : p.x;
        maxy = maxy > p.y ? maxy : p.y;
      }
      return new MBR(minx, miny, maxx, maxy);
    }
  }

  public String toGeomText() {
    if( points == null ) {
      return "NULL";
    } else {
      StringBuffer bfr = new StringBuffer();
      Pnt [] points = this.points;
      bfr.append( "LINESTRING(" );
      Pnt point;
      for( int i = 0, len = points.length; i < len; i ++ ) {
        point = points[i];
        bfr.append( point.x + " " + point.y );
        if( i < len -1 ) {
          bfr.append( "," );
        }
      }
      bfr.append( ")" );
      return bfr.toString();
    }
  }

  // member
  Pnt [] points;
  private int noPnt;
  // end of member

}
