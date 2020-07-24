package gmlviewer.gis.model;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.geom.GeneralPath;
import gmlviewer.gis.kernel.CommonLib;

public class MBR extends CommonLib {

  public MBR(double minx, double miny, double maxx, double maxy) {

    //super( "", -2 );

    this.minx = minx < maxx ? minx : maxx;
    this.miny = miny < maxy ? miny : maxy;
    this.maxx = minx > maxx ? minx : maxx;
    this.maxy = miny > maxy ? miny : maxy;
  }

  public MBR(Pnt min, Pnt max) {
    this(min.x, min.y, max.x, max.y);
  }

  public MBR() {
    this( 0, 0, 0, 0 );
  }

  public MBR create() {
    return new MBR(minx, miny, maxx, maxy);
  }

  public double minx() {
    return minx;
  }

  public double miny() {
    return miny;
  }

  public double maxx() {
    return maxx;
  }

  public double maxy() {
    return maxy;
  }

  public double getWidth() {
    return maxx - minx;
  }

  public double getHeight() {
    return maxy - miny;
  }

  public Pnt getCenter() {
    return new Pnt( (maxx + minx) / 2.0, (maxy + miny) / 2.0);
  }

  public double getArea() {
    return getWidth()*getHeight();
  }

  public double getLength() {
    return 2*( getWidth() + getHeight() );
  }

  public boolean intersects(MBR a) {
    return MBR.intersect(this, a) != null;
  }

  public String toString() {
    return "MBR = " + minx + ", " + miny + ", " + maxx + ", " + maxy
        + ", tx : " + getWidth() + ", ty = " + getHeight();
  }

  public String toGeomText() {

    String wkt = "POLYGON("
        + minx + " " + miny
        + "," + maxx + " " + maxy
        + ")";

    return wkt;
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    else if (obj instanceof MBR) {
      MBR mbr = (MBR) (obj);
      return (this.minx == mbr.minx)
          && (this.miny == mbr.miny)
          && (this.maxx == mbr.maxx)
          && (this.maxy == mbr.maxy);
    }
    else {
      return false;
    }
  }

  public MBR moveBy(double dx, double dy) {
    return new MBR(minx + dx, miny + dy, maxx + dx, maxy + dy);
  }

  public MBR zoomIn() {
    return zoom(0.75);
  }

  public MBR zoomOut() {
    return zoom(1.25);
  }

  private MBR zoom(double zoomRate) {
    Pnt center = this.getCenter();
    double w = this.getWidth();
    double h = this.getHeight();
    w = w * zoomRate / 2.0;
    h = h * zoomRate / 2.0;
    return new MBR(center.x - w, center.y - h, center.x + w, center.y + h);
  }

  // member
  private double minx, miny, maxx, maxy;
  // end of member

  // static method

  public static MBR union(MBR a, MBR b) {
    if (a == null || b == null) {
      return a == null ? b : a;
    }
    else {
      return new MBR(
          a.minx < b.minx ? a.minx : b.minx,
          a.miny < b.miny ? a.miny : b.miny,
          a.maxx > b.maxx ? a.maxx : b.maxx,
          a.maxy > b.maxy ? a.maxy : b.maxy
          );
    }
  }

  public static MBR intersect(MBR a, MBR b) {
    if (a == null || b == null) {
      return null;
    }

    double minx = (a.minx > b.minx) ? a.minx : b.minx;
    double maxx = (a.maxx < b.maxx) ? a.maxx : b.maxx;

    if (minx >= maxx) {
      return null;
    }
    else {
      double miny = (a.miny > b.miny) ? a.miny : b.miny;
      double maxy = (a.maxy < b.maxy) ? a.maxy : b.maxy;

      if (miny >= maxy) {
        return null;
      }
      else {
        return new MBR(minx, miny, maxx, maxy);
      }
    }
  }

  public static MBR calculateMBR(Pnt[] points) {
    int len = points.length;

    if (len < 1) {
      return null;
    }

    Pnt p = points[0];

    double minx = p.x;
    double maxx = minx;
    double miny = p.y;
    double maxy = miny;

    for (int i = 1; i < len; i++) {
      p = points[i];
      minx = minx < p.x ? minx : p.x;
      miny = miny < p.y ? miny : p.y;
      maxx = maxx > p.x ? maxx : p.x;
      maxy = maxy > p.y ? maxy : p.y;
    }

    return new MBR(minx, miny, maxx, maxy);
  }

  // end of static method

}
