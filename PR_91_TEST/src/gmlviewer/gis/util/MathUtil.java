package gmlviewer.gis.util;

public class MathUtil {

  public static double getRadian(double x, double y) {

      if( x == 0 && y == 0 ) {

        return 0;

      }

      double theta = Math.acos( x / Math.sqrt(x*x + y*y) );

      if( y < 0 ) { // 3/4 �и� �� 4/4 �и�

        theta = 2*Math.PI - theta;

      }

      return theta%(2*Math.PI);

  }

}
