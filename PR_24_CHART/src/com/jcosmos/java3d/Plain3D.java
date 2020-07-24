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
import jchart.*;

public class Plain3D extends Shape3D {

  //
  //          r                s
  //           ______________
  //         /              /
  //     v  /              /
  //       /              /
  //      /--------------/
  //            u
  //      p              q
  //

  private Point3D p, q, r, s;

  private Point3DGroup point3DGroup;

  public Plain3D( final ShapeStyle style, final Point3D p, final Point3D q, final Point3D r) {

    super( style );

    this.p = p;
    this.q = q;
    this.r = r;

    final Point3D u = p.getVectorTo( q );

    final Point3D v = p.getVectorTo( r );

    this.s = p.getVectorSum( u.getVectorSum( v ) );

    final Point3D [] point3Ds = new Point3D [] { p, q, s, r }; // p, q, s, r 순서에 주의

    this.point3DGroup = new Point3DGroup( point3Ds, u.getCrossProduct( v ).getUnitVector() );

  }

  public Cube3D getBoundsCube3D() {

    return Point3D.getBoundsCube3DFromPoint3DArray( new Point3D[] { p, q, r, s } );

  }

  public Point3DGroup [] getPoint3DGroups( ) {

    return new Point3DGroup [] { point3DGroup };

  }

}