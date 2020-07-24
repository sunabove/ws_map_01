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

public class Bar3D extends BaseAndTopShape3D {

  public Bar3D( final ShapeStyle style, final double baseZ, final Rectangle2D baseRect, final double h ) {

    super( style, baseZ, baseRect, 1.0, h );

  }

  public Shape get2DBaseShape() {

    return getBase2DShapeBounds();

  }

}