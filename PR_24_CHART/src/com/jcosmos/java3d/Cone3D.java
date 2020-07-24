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

public class Cone3D extends BaseAndTopShape3D {

  public Cone3D( final ShapeStyle style, final double baseZ, final Rectangle2D baseEllipseBounds,
		 final double topShapeRatio, final double h ) {

      super( style, baseZ, baseEllipseBounds, topShapeRatio, h );

  }

  public Shape get2DBaseShape() {

    final Rectangle2D baseEllipseBounds = getBase2DShapeBounds();

    return new Ellipse2D.Double(
			      baseEllipseBounds.getX(), baseEllipseBounds.getY(),
			      baseEllipseBounds.getWidth(), baseEllipseBounds.getHeight()
			    );

  }

}