package surface;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.util.*;
import com.jcosmos.java3d.*;
import java.awt.*;
import java.awt.geom.*;
import jcosmos.*;

public class SurfaceCell {

  Rectangle2D area;
  int top; // topology
  double p, q, r, s;

  public SurfaceCell( int top, Rectangle2D area, double p, double q, double r, double s ) {

    this.top = top;

    this.area = area;

    this.p = p;
    this.q = q;
    this.r = r;
    this.s = s;

  }

  private AffineTransform getAffineTransform() {

    int top = this.top;

    Rectangle2D area = this.area;

    double x = area.getX(), y = area.getY(), w = area.getWidth(), h = area.getHeight();

    double cx = x + w/2.0, cy = y + h/2.0;

    if( top == 0 ) {

      return AffineTransform.getTranslateInstance( 0, 0 );

    } else if( top == 1 ) {

      AffineTransform at = AffineTransform.getTranslateInstance( cx, y );

      at.scale( -1.0, 1.0 );

      at.translate( - cx, - y );

      return at;

    } else if( top == 2 ) {

      AffineTransform at = AffineTransform.getTranslateInstance( x, cy );

      at.scale( 1.0, -1.0 );

      at.translate( -x, - cy );

      return at;

    } else { // if top == 3

      AffineTransform at = AffineTransform.getTranslateInstance( cx, cy );

      at.scale( -1.0, -1.0 );

      at.translate( - cx, - cy );

      return at;

    }

  }

  public void paint(Graphics2D g2, Color [] colors, double min, double max, int level ) {

    Utility.debug(this, "TOP = " + top + ", P = " + p + ", Q = " + q + ", R= " + r + ", S= " + s );

    Polygon3D [] polygonArray = getSurfacePolygon3D( p, q, r, s, min, max, level );

    AffineTransform at = getAffineTransform();

    double unit = ( max - min ) / level;

    for( int i = 0, len = polygonArray.length; i < len; i ++ ) {

      Polygon3D p3 = polygonArray[ i ];

      if( p3 == null ) {

	continue;

      }

      Shape shape = p3.getGeneralPath();

      shape = at.createTransformedShape( shape );

      int currLevel = (int) ( (p3.id - min) / unit );

//      System.out.println("Polygon ID = " + p3.id +  ", CURR LEVEL = " + currLevel );

      Color color = colors[ currLevel ];

      g2.setColor( color );

      g2.fill( shape );

    }

    g2.setColor( Color.black );

    g2.draw( area );

  }

  private Polygon3D [] getSurfacePolygon3D( double p, double q, double r, double s,
					   double min, double max, int level ) {

     Rectangle2D area = this.area;

     double x = area.getX(), y = area.getY(), w = area.getWidth(), h = area.getHeight();

     return getSurfacePolygon3D(
				 new Point3D( x, y, p ), new Point3D( x + w, y, q ),
				 new Point3D( x + w, y + h, r ), new Point3D( x, y + h, s ),
				 min, max, level
				);

  }

  private Polygon3D [] getSurfacePolygon3D( Point3D p, Point3D q, Point3D r, Point3D s,

					   double min, double max, int level ) {

    Point3D pointsPQ [] = getPointsInterpolated( p, q, min, max, level );
    Point3D pointsQR [] = getPointsInterpolated( q, r, min, max, level );

    Point3D pointsPS [] = getPointsInterpolated( p, s, min, max, level );
    Point3D pointsSR [] = getPointsInterpolated( s, r, min, max, level );

    Point3D pointsPR [] = getPointsInterpolated( p, r, min, max, level );

    Point3D pointsPQR [] = getPointsAppended( pointsPQ, pointsQR );
    Point3D pointsPSR [] = getPointsAppended( pointsPS, pointsSR );

    return getSurfacePolygon3D( pointsPQR, pointsPR, pointsPSR, min, max, level );

  }

  private Polygon3D [] getSurfacePolygon3D( Point3D a [], Point3D b [], Point3D c [],
					    final double min, final double max, final int level ) {

    Vector polygonList = new Vector();

    final double unit = ( max - min )/level;

    for( double topLevelValue = max; topLevelValue > min; topLevelValue -= unit ) {

      Polygon3D polygon = getSurfacePolygon3DOfLevel( a, b, c, topLevelValue, topLevelValue - unit, unit );

      if( polygon == null ) {

	continue;

      }

      polygonList.add( polygon );

    }

    int len = polygonList.size();

    Polygon3D polygonArray [] = new Polygon3D[ len ];

    Enumeration enumIt = polygonList.elements();

    for( int i = 0; i < len; i ++ ) {

      polygonArray[ i ] = (Polygon3D) enumIt.nextElement();

    }

    return polygonArray;

  }

  private Polygon3D getSurfacePolygon3DOfLevel( Point3D [] a, Point3D [] b, Point3D [] c,
						double topLevelValue, double bottomLevelValue, double unit ) {

    Point3D aPoints [] = getPointsBetween( a, topLevelValue, bottomLevelValue );

    if( aPoints.length < 1 ) {

      return null;

    }

    Point3D bPoints [] = getPointsBetween( b, topLevelValue, bottomLevelValue );

    if( bPoints.length < 1 ) {

      return null;

    }

    Point3D cPoints [] = getPointsBetween( c, topLevelValue, bottomLevelValue );

    Polygon3D polygon = new Polygon3D( bottomLevelValue );

    for( int i = 0, len = aPoints.length; i < len; i ++ ) {

      polygon.add( aPoints[ i ] );

    }

    final int bPointsLen = bPoints.length;

    polygon.add( bPoints[ bPointsLen -1 ] );

    for( int i = cPoints.length - 1; i > -1 ; i -- ) {

      polygon.add( cPoints[ i ] );

    }

    polygon.add( bPoints[ 0 ] );

    return polygon;

  }

  private Point3D [] getPointsBetween(Point3D [] a, double topLevelValue, double bottomLevelValue ) {

    // a 값들은 표고가 높은 값에서 낯은 값으로 정렬 되어 있다.

    Vector pointList = new Vector();

    for( int i = 0, len = a.length; i < len; i ++ ) {

      Point3D p = a[ i ];

      double pZ = p.z;

      if( topLevelValue >= pZ && pZ >= bottomLevelValue ) {

	pointList.add( p );

      }

    }

    final int len = pointList.size();

    Point3D [] pointArray = new Point3D[ len ];

    Enumeration enumIt = pointList.elements();

    for( int i = 0; i < len; i ++ ) {

      pointArray[ i ] = (Point3D) enumIt.nextElement();

    }

    return pointArray;

  }

  private void addPoints(Vector pointList, Point3D [] p ) {

    for( int i = 0, len = p.length; i < len; i ++ ) {

      pointList.add( p[ i ] );

    }

  }

  private Point3D [] getPointsAppended(Point3D [] p, Point3D [] q) {

    int pLen = p.length;
    int qLen = q.length;

    int totLen = pLen + qLen - 1;

    Point3D totPoints [] = new Point3D[ totLen ];

    System.arraycopy( p, 0, totPoints, 0, pLen );

    System.arraycopy( q, 1, totPoints, pLen, qLen - 1 );

    return totPoints;

  }

//  private Point3D [] getPointsAppended(Point3D [] p, Point3D [] q) {
//
//    Vector pointList = new Vector();
//
//    addPoints( pointList, p );
//
//    addPoints( pointList, q );
//
//    for( int i = 0; i < pointList.size() - 1; ) {
//
//      Point3D curr = (Point3D) pointList.elementAt( i );
//      Point3D next = (Point3D) pointList.elementAt( i + 1 );
//
//      if( curr.equals( next  ) ) {
//
//	pointList.remove( next );
//
//      } else {
//
//	i ++;
//
//      }
//
//    }
//
//    int len = pointList.size();
//
//    Point3D pointArray [] = new Point3D[ len ];
//
//    Enumeration enumIt = pointList.elements();
//
//    for( int i = 0; i < len; i ++ ) {
//
//      pointArray[ i ] = (Point3D) enumIt.nextElement();
//
//    }
//
//    return pointArray;
//
//  }

  private Point3D [] getPointsInterpolated( Point3D p, Point3D q, double min, double max, int level ) {

    double pZ = p.z;
    double qZ = q.z;

    if( pZ < qZ ) {

      return getPointsInterpolated( q, p, min, max, level );

    } else if( pZ == qZ ) {

      return new Point3D [] { p, q };

    }

    // pZ 의 값이 항상 qZ 의 값보다 크다.

    double px = p.x;
    double py = p.y;

    double vx = q.x - px;
    double vy = q.y - py;

//    double vLen = Math.sqrt( vx*vx + vy*vy );

    Vector pointsList = new Vector();

    pointsList.add( p );

    double unit = ( max - min )/( level + 0.0);

//    Utility.debug(this, "UNIT = " + unit );

    double z  = ( ( int ) (pZ/unit) )*unit; // 표고 값

    z = ( z == pZ ) ? z - unit : z;

    double zDiff = pZ - qZ;

    for( ; z > qZ ; z -= unit ) {

//      double dz = pZ - z;

//      double ratio = dz/zDiff;

      double ratio = 1.0/( 1.0 + ( z - qZ )/( pZ - z ) );

      Point3D r = new Point3D( px + vx*ratio, py + vy*ratio, z );

//      Utility.debug(this, "PZ = " + r );

      pointsList.add( r );

    }

    pointsList.add( q );

    int len = pointsList.size();

    Enumeration enumIt = pointsList.elements();

    Point3D pointsArray [] = new Point3D[ len ];

    for( int i = 0; i < len; i ++ ) {

      pointsArray[ i ] = (Point3D) enumIt.nextElement();

    }

    return pointsArray;

  }

}
