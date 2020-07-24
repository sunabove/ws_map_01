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

public class Cube3D {

  private Point3D min;
  private Point3D max;

  public Cube3D(Point3D min, Point3D max) {

    this.min = min;
    this.max = max;

  }

  public Point3D getMin() {

    return min;

  }

  public Point3D getMax() {

    return max;

  }

  public Point3D getCenter() {

    final Point3D min = getMin();
    final Point3D max = getMax();

    return
	    new Point3D(

	       ( min.x + max.x )/2.0,
	       ( min.y + max.y )/2.0,
	       ( min.z + max.z )/2.0

	    );

  }

  public double getDiagonalValue() {

    return Math.sqrt( getDiagonalSquarValue() );

  }

  public double getDiagonalSquarValue() {

    final Point3D min = getMin();
    final Point3D max = getMax();

    final double dx = max.x - min.x;
    final double dy = max.y - min.y;
    final double dz = max.z - min.z;

    return dx*dx + dy*dy + dz*dz;

  }

  public String toString() {

    return "Cube3D [ min = " + min + ", max = " + max + "]";

  }

}