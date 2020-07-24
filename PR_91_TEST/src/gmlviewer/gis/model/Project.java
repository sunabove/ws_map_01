package gmlviewer.gis.model;

import java.awt.geom.*;

public class Project {

  private Project(MBR mbr, Rectangle2D rect) {
    this.mbr = mbr;
    this.rect = rect;

    this.scale = this.getScale();
    Pnt translation = this.getTranslation(this.scale);

    this.tx = translation.x;
    this.ty = translation.y;
  }

  private Project(MBR mbr, double w, double h) {
    this( mbr, new Rectangle2D.Double( 0, 0, w, h ) );
  }

  public double getScale() {

    double w = this.rect.getWidth();
    double h = this.rect.getHeight();

    double WW = this.mbr.getWidth(), HH = this.mbr.getHeight();

    double sw = w / WW;
    double sh = h / HH;

    return (sw < sh) ? sw : sh;

  }

  private Pnt getTranslation(double scale) {

    Pnt center = this.mbr.getCenter();

    double cx = center.x * scale;
    double cy = - (center.y * scale);

    double w = this.rect.getWidth();
    double h = this.rect.getHeight();

    return new Pnt( w / 2.0 - cx, h / 2.0 - cy);

  }

  public Pnt toGraphics(Pnt p) {
    return new Pnt(p.x * scale + tx, - (p.y * scale) + ty);
  }

  public Pnt toGraphics(double x, double y) {
    return this.toGraphics(new Pnt(x, y));
  }

  public Pnt toSpatial(int x, int y) {
    return new Pnt( (x - tx) / scale, (ty - y) / scale);
  }

  public MBR getMBR() {
    return mbr;
  }

  public boolean equals( Object obj ) {
    if( this == obj ) {
      return true;
    } else if( obj instanceof Project ) {
      Project proj = (Project) ( obj );
      return this.mbr != null && this.mbr.equals( proj.getMBR() )
          && this.rect != null && this.rect.equals( proj.rect );
          //&& ( this.w == proj.w ) && ( this.h == proj.h ) ;
    } else {
      return false;
    }
  }

  public String toString() {
    return "Project : rect = " + rect + ", scale = " + scale
        + ", tx = " + tx + ", ty = " + ty + ", MBR = " + mbr ;
  }

  public Rectangle2D getPixelRect() {
    return rect;
  }

  // member
  private MBR mbr;
  private double tx;
  private double ty;
  private double scale;
  private Rectangle2D rect;
  // end of member

  // static method

  public static Project getProject(MBR mbr, double w, double h) {
    return new Project(mbr, w, h);
  }

  public static Project getProject(MBR mbr, java.awt.Dimension size ) {
    return getProject( mbr, size.width, size.height );
  }

  // end of static method

}
