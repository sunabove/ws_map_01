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

public class Line3D extends Shape3D implements Linear3D {

  private Point3D from;
  private Point3D to;

  public Line3D( final ShapeStyle style, final Point3D from, final Point3D to ) {

    super( style );

    this.from = from;
    this.to = to;

  }

  public Cube3D getBoundsCube3D() {

    final Point3D from = this.from;
    final Point3D to = this.to;

    return new Cube3D(

	new Point3D(
		     from.x < to.x ? from.x : to.x,
		     from.y < to.y ? from.y : to.y,
		     from.z < to.z ? from.z : to.z
		   ),

	new Point3D(
		     from.x > to.x ? from.x : to.x,
		     from.y > to.y ? from.y : to.y,
		     from.z > to.z ? from.z : to.z
		   )

    );

  }

  public Point3DGroup [] getPoint3DGroups( ) {

    return new Point3DGroup [] { new Point3DGroup( new Point3D [] { from, to }, null ) };

  }

}