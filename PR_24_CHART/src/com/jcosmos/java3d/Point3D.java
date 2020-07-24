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

public class Point3D {

  public double x, y, z;

  public Point3D( final double x, final double y, final double z ) {

    this.x = x;
    this.y = y;
    this.z = z;

  }

  public Point3D( final Point2D p, final double z ) {

    this( p.getX(), p.getY(), z );

  }

  public boolean equals(Point3D p) {

    return ( x == p.x && y == p.y && z == p.z );

  }

  public double getSquareLength() {

    return x*x + y*y + z*z;

  }

  public double getLength() {

    return Math.sqrt( getSquareLength() );

  }

  public Point3D getUnitVector() {

    final double len = getLength();

    return new Point3D( x/len, y/len, z/len );

  }

  public Point3D getVectorTo( final Point3D q ) {

    return new Point3D( q.x - x, q.y - y, q.z - z );

  }

  public Point3D getVectorSum( final Point3D q ) {

    return new Point3D( q.x + x, q.y + y, q.z + z );

  }

  public Point3D getVectorMultipliedBy( double a ) {

    return new Point3D( a*x, a*y, a*z );

  }

  public Point3D getCrossProduct( final Point3D q ) {

    return new Point3D( y*q.z - z*q.y , z*q.x - x*q.z,  x*q.y - y*q.x );

  }

  public double getDotProduct( final Point3D q ) {

    return x*q.x + y*q.y + z*q.z ;

  }

  public static Cube3D getBoundsCube3DFromPoint3DArray( final Point3D [] point3Ds ) {

    double minx = Double.MAX_VALUE, miny = minx, minz = minx;
    double maxx = Double.MIN_VALUE, maxy = maxx, maxz = maxx;

    for(int i = 0, len = point3Ds.length; i < len; i ++ ) {

      final Point3D point3D = point3Ds[ i ];

      minx = ( minx < point3D.x ) ? minx : point3D.x;
      miny = ( miny < point3D.y ) ? miny : point3D.y;
      minz = ( minz < point3D.z ) ? minz : point3D.z;

      maxx = ( maxx > point3D.x ) ? maxx : point3D.x;
      maxy = ( maxy > point3D.y ) ? maxy : point3D.y;
      maxz = ( maxz > point3D.z ) ? maxz : point3D.z;

    }

    return new Cube3D( new Point3D( minx, miny, minz ), new Point3D( maxx, maxy, maxz ) );

  }

  public String toString() {

    return "Point3D [ x = " + x + ", y = " + y + ", z = " + z + " ]";

  }

}