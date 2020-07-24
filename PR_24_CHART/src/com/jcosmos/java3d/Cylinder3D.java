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

public class Cylinder3D extends Cone3D {

  public Cylinder3D( final ShapeStyle style, final double baseZ, final Rectangle2D baseEllipseBounds, final double h ) {

      super( style, baseZ, baseEllipseBounds, 1.0, h );

  }

}