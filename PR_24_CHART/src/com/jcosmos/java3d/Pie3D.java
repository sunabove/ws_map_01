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

public class Pie3D extends BaseAndTopShape3D {

  private int start;
  private int extent;

  public Pie3D( final ShapeStyle style, final double baseZ, final Rectangle2D baseEllipseBounds,
		final int start, final int extent,
		final double h ) {

    super( style, baseZ, baseEllipseBounds, 1.0, h );

    this.start = start;
    this.extent = extent;

  }

  public Shape get2DBaseShape() {

    final Rectangle2D baseEllipseBounds = getBase2DShapeBounds();

    return new Arc2D.Double(
			      baseEllipseBounds.getX(), baseEllipseBounds.getY(),
			      baseEllipseBounds.getWidth(), baseEllipseBounds.getHeight(),
			      start, extent, Arc2D.PIE
			    );

  }

}