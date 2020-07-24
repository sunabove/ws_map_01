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

public class Pyramid3D extends BaseAndTopShape3D {

  public Pyramid3D( final ShapeStyle style, final double baseZ, final Rectangle2D baseShapeBounds,
		 final double topShapeRatio, final double h ) {

      super( style, baseZ, baseShapeBounds, topShapeRatio, h );

  }

  public Shape get2DBaseShape() {

    return getBase2DShapeBounds();

  }

}