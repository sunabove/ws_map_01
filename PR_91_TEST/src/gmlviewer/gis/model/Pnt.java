package gmlviewer.gis.model;

public class Pnt {

  public Pnt() {
    this(0, 0);
  }

  public Pnt(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public boolean equals(Object obj) {
    if( obj instanceof Pnt ) {
      Pnt q = (Pnt) obj;
      return this.x == q.x && this.y == q.y;
    } else {
      return false;
    }
  }

  public String toString() {
    return "x = " + x + ", y = " + y;
  }

  public double x;
  public double y;

}
