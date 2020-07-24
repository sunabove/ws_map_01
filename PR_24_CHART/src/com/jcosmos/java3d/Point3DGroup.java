package com.jcosmos.java3d;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.awt.geom.*;

public class Point3DGroup {

  private Point3D [] point3Ds;
  private Point3D normalVector; // 평면에 수직인 방향 벡터

  public Point3DGroup( final Point3D point3Ds [], final Point3D normalVector ) {

    this.point3Ds = point3Ds;
    this.normalVector = normalVector;

  }

  public Point3D [] getPoint3Ds() {

    return point3Ds;

  }

  public Point3D getCenterApproximate() {

    final Point3D [] points = getPoint3Ds();

    double x = 0, y = 0 , z = 0;

    final int len = points.length;

    for( int i = 0; i < len; i ++ ) {

      final Point3D point = points[ i ];

      x += point.x;
      y += point.y;
      z += point.z;

    }

    return new Point3D( x/len, y/len, z/len );

  }

  public Point3D getNormalVector() {

    return normalVector;

  }

  public Point2D [] getScreenPoints(final Matrix3D matrix3D, final double screenDistance ) {

    final Point3D [] point3Ds = this.point3Ds;

    final int len = point3Ds.length;

    final Point2D screenPoints [] = new Point2D[ len ];

    for(int i = 0; i < len; i ++ ) {

      screenPoints[ i ] = matrix3D.getScreenPoint( point3Ds[i], screenDistance );

    }

    return screenPoints;

  }

}