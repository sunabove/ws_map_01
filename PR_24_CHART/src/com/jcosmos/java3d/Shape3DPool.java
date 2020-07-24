package com.jcosmos.java3d;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import jcosmos.*;
import jchart.*;

public class Shape3DPool extends Shape3D {

  private final LinkedList shape3DList = new LinkedList();

  private double rho = -1;

  private double theta = PI/6;
  private double phi = PI/3;

  private int accommodation= 100; // ¿ø±Ù°¨

  public Shape3DPool() {

    super( null );

  }

  public void putShape3D(final Shape3D shape3D) {

    final LinkedList shape3DList = this.shape3DList;

    if( shape3DList.contains( shape3D ) ) {

      shape3DList.remove( shape3D );

    }

    shape3DList.addLast( shape3D );

    rho = -1; // rho value initialization

  }

  private Shape3D [] getShape3DArray() {

    final Object [] objs = shape3DList.toArray();

    final int len = objs.length;

    final Shape3D [] shape3DArray = new Shape3D[ len ];

    for(int i = 0; i < len; i ++ ) {

      shape3DArray[ i ] = (Shape3D) ( objs[ i ] );

    }

    return shape3DArray;

  }

  public Cube3D getBoundsCube3D() {

    final Shape3D [] shape3DArray = getShape3DArray();

    double minx = Double.MAX_VALUE, miny = minx, minz = minx;
    double maxx = Double.MIN_VALUE, maxy = maxx, maxz = maxx;

    for(int i = 0, len = shape3DArray.length; i < len; i ++ ) {

      final Cube3D bounds = shape3DArray[ i ].getBoundsCube3D();

      final Point3D min = bounds.getMin();
      final Point3D max = bounds.getMax();

      minx = ( minx < min.x ) ? minx : min.x;
      miny = ( miny < min.y ) ? miny : min.y;
      minz = ( minz < min.z ) ? minz : min.z;

      maxx = ( maxx > max.x ) ? maxx : max.x;
      maxy = ( maxy > max.y ) ? maxy : max.y;
      maxz = ( maxz > max.z ) ? maxz : max.z;

    }

    return new Cube3D( new Point3D( minx, miny, minz ), new Point3D( maxx, maxy, maxz ) );

  }

  public double getRho() {

    if( this.rho > 0 ) {

      return rho;

    }

    this.rho = 5.0 * ( getBoundsCube3D().getDiagonalValue() );

    return this.rho;

  }

  public double getTheta() {

    return theta;

  }

  public double getPhi() {

    return phi;

  }

  public int getAccommodation() {

    return accommodation;

  }

  private Matrix3D getMatrix3D( ) {

    final double rho = getRho();
    final double theta = getTheta();
    final double phi = getPhi();

    return new Matrix3D( rho, theta, phi );

  }

  public void paint(final Graphics2D g2, final Rectangle2D area ) {

      final double screenDistance = getPreferredScreenDistance( );

      final Cube3D bounds = getBoundsCube3D();

      final Point3D boundsCenter = bounds.getCenter();

      final double bouldsDiagonalLen = bounds.getDiagonalValue();

      final Point3D sunLoc = new Point3D( bouldsDiagonalLen, - bouldsDiagonalLen, bouldsDiagonalLen );

      final Matrix3D matrix3D = getMatrix3D();

      final Point2D screenCenter = matrix3D.getScreenPoint( boundsCenter, screenDistance );

      final double tx = area.getWidth()/2.0 - screenCenter.getX();

      final double ty = area.getHeight()/2.0 - screenCenter.getY();

      g2.translate( tx, ty );

      paint( g2, bounds, matrix3D, screenDistance, sunLoc );

      g2.translate( - tx, - ty );

  }

  public void paint( final Graphics2D g2, final Cube3D bounds, Matrix3D matrix3D,
		     final double screenDistance, final Point3D sunLoc ) {

//    paintXYZPlains( g2, ShapeStyle.getDefaultShapeStyle(), bounds, matrix3D, screenDistance, sunLoc );

    paintAxis( g2, ShapeStyle.getDefaultShapeStyle(), bounds, matrix3D, screenDistance, sunLoc );

    g2.setColor( Color.black );

    final Shape3D [] shape3DArray = getShape3DArray();

    for(int i = 0, len = shape3DArray.length; i < len; i ++ ) {

      Shape3D shape3D = shape3DArray[ i ];

      shape3D.paint( g2, matrix3D, screenDistance, sunLoc );

    }

  }

  private void paintXYZPlains( final Graphics2D g2, final ShapeStyle style, final Cube3D bounds,
			      final Matrix3D matrix3D, final double screenDistance,
			      final Point3D sunLoc ) {

    final Point3D boundsMax = bounds.getMax();

    final Point3D origin = Shape3DPool.ORIGIN;

    final Point3D xTick = new Point3D( boundsMax.x,           0,           0 );
    final Point3D yTick = new Point3D(           0, boundsMax.y,           0 );
    final Point3D zTick = new Point3D(           0,           0, boundsMax.z );

    final Plain3D [] plain3Ds = {

      new Plain3D( style, origin, xTick, yTick ), // XY Plain
      new Plain3D( style, origin, yTick, zTick ), // YZ plain
      new Plain3D( style, origin, zTick, xTick ), // ZX plain

    };

    final Color [] axisColors = { Color.red, Color.green, Color.blue };

    for(int i = 0; i < 3; i ++ ) {

      g2.setColor( axisColors[ i ] );

      plain3Ds[ i ].paint( g2, matrix3D, screenDistance, sunLoc );

    }

  }

  private void paintAxis( final Graphics2D g2, final ShapeStyle style, final Cube3D bounds,
			  final Matrix3D matrix3D, final double screenDistance,
			  final Point3D sunLoc ) {

    final Point3D boundsMax = bounds.getMax();

    final Point3D origin = Shape3DPool.ORIGIN;

    final Color [] axisColors = { Color.red, Color.green, Color.blue };

    for(int i = 0; i < 3; i ++ ) {

      final Point3D infPoint;

      final String axisName;

      final double axisMarginRatio = 0.9;

      if( i == 0 ) {

	 infPoint = new Point3D( boundsMax.x*axisMarginRatio,        0,         0 );

	 axisName = "x";

      } else if( i == 1 ) {

	 infPoint = new Point3D(        0, boundsMax.y*axisMarginRatio,         0 );

	 axisName = "y";

      } else {

	 infPoint = new Point3D(        0,         0, boundsMax.z*axisMarginRatio );

	 axisName = "z";

      }

      final Line3D axis = new Line3D( style, origin, infPoint );

      style.setLineColor ( axisColors[ i ] );

      style.setFillColor ( axisColors[ i ] );

      axis.paint( g2, matrix3D, screenDistance, sunLoc );

      Point2D axisNameLoc = matrix3D.getScreenPoint( infPoint, screenDistance );

      g2.drawString( axisName, (int) axisNameLoc.getX(), (int) axisNameLoc.getY() );

    }

  }

  public Point3DGroup [] getPoint3DGroups( ) {

    return null;

  }

  public void controlViewPoint(int thetaAngle, int phiAngle, int accommodation ) {

      while( thetaAngle < 0 ) {

	thetaAngle += 360;

      }

      thetaAngle %= 360;

      phiAngle = phiAngle < 0 ? 0 : phiAngle;
      phiAngle = phiAngle > 180 ? 180 : phiAngle;

      accommodation = ( accommodation > 0 ) ? accommodation : 0 ;

      accommodation = ( accommodation > 100 ) ? 100 : accommodation;

      this.theta = Unit.convertDegreeToRadian( thetaAngle );

      this.phi = Unit.convertDegreeToRadian( phiAngle );

      this.accommodation = accommodation;

   }

   public double getPreferredScreenDistance( ) {

      final double ratio = 2.0 - ( getAccommodation()/100.0 );

      return ratio*getRho();

   }

}