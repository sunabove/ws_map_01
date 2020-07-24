package gmlviewer.gis.model;

public class Matrix {

  public Matrix( double tx, double ty ) {
    super();
    this.tx = tx;
    this.ty = ty;
  }

  public Pnt transform( Pnt p ) {
    return new Pnt( p.x + tx, p.y + ty );
  }

  public String toString() {
    return "tx : " + tx + ", ty : " + ty;
  }

  public double getTx() {
    return this.tx;
  }

  public double getTy() {
    return this.ty;
  }

  // member
  private double tx;
  private double ty;
  // end of member

  // static method
  private static Matrix readFrom( String txt ) {
    txt = txt.trim();
    int comma = txt.indexOf( ',' );
    double tx = 0, ty = 0;
    if( comma > -1 ) {
      String prex = txt.substring( 0, comma );
      int colon = prex.indexOf( ':' );
      if( colon > -1 && colon < prex.length() - 1 ) {
        String xt = prex.substring( colon + 1 ).trim();
        tx = new Double( xt ).doubleValue();
      }
    }
    if( comma < txt.length() - 1 ) {
      String posty = txt.substring( comma + 1 );
      int colon = posty.indexOf( ':' );
      if( colon > -1 && colon < posty.length() - 1 ) {
        String yt = posty.substring( colon + 1 ).trim();
        ty = new Double( yt ).doubleValue();
      }

    }
    return new Matrix( tx, ty );
  }

  public static void main( String [] args ) {
    Matrix m = new Matrix( 3.129, 4.125 );
    String q = m.toString();
    System.out.println( "CRE M = " + q );
    Matrix c = Matrix.readFrom( q );
    System.out.println( "REA M = " + c );
  }
  // end of static method

}
