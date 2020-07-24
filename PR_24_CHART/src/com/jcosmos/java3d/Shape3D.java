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
import jcosmos.*;

public abstract class Shape3D {

  public static final double PI = Math.PI;

  public static final Point3D ORIGIN = new Point3D( 0, 0, 0 );

  public static final Point3D I_CARET       = new Point3D(  1,  0,  0 ),
			      I_MINUS_CARET = new Point3D( -1,  0,  0 ),
			      J_CARET       = new Point3D(  0,  1,  0 ),
			      J_MINUS_CARET = new Point3D(  0, -1,  0 ),
			      K_CARET       = new Point3D(  0,  0,  1 ),
			      K_MINUS_CARET = new Point3D(  0,  0, -1 );

  private ShapeStyle style;

  public Shape3D( final ShapeStyle style ) {

    this.style = style;

  }

  public ShapeStyle getShapeStyle() {

    return style;

  }

  abstract public Cube3D getBoundsCube3D();

  abstract public Point3DGroup [] getPoint3DGroups();

  public void paint( final Graphics2D g2, final Matrix3D matrix3D,
		     final double screenDistance, final Point3D sunLoc ) {

    final Point3DGroup [] point3DGroups = getPoint3DGroups();

    final Point3D eyeLoc = matrix3D.getEyeLocation3D();

    for(int i = 0, len = point3DGroups.length; i < len; i ++ ) {

      final Point3DGroup point3DGroup = point3DGroups[ i ];
      final Point2D [] screenPoints  = point3DGroup.getScreenPoints( matrix3D, screenDistance );

      if( ! VectorUtilities.isCounterClockWise( screenPoints ) ) {

	final Shape screenShape = ShapeUtilities.createGeneralPath( screenPoints );

	final ShapeStyle style = getShapeStyle();

	final Color fillColor = style.getFillColor() != null ? style.getFillColor() : Color.yellow ;

	final Color lightColor = getLightColor( fillColor, point3DGroup, sunLoc, eyeLoc );

	g2.setColor( lightColor );

	g2.fill( screenShape );

	if( this instanceof Linear3D ) {

	   g2.setColor( style.getLineColor() );

	   g2.draw( screenShape );

	}

      }

    }

  }

  private Color getLightColor( final Color c, final Point3DGroup point3DGroup,
			       final Point3D sunLoc, final Point3D eyeLoc ) {

    final Point3D normalVector = point3DGroup.getNormalVector();

    if( normalVector == null ) {

      return c;

    }

    return getLightColor( c,
			   getLightDensity(
					    sunLoc, eyeLoc,
					    normalVector, point3DGroup.getCenterApproximate()
					   )
			);

  }

  private double getLightDensity( final Point3D sunLoc, final Point3D eyeLoc,
				  final Point3D normalVector, final Point3D point ) {

    final Point3D s = point.getVectorTo( sunLoc ).getUnitVector();
    final Point3D e = point.getVectorTo( eyeLoc ).getUnitVector();

    return Math.abs( s.getDotProduct( normalVector) * e.getDotProduct( normalVector ) );

  }

  private Color getLightColor( final Color c, final double lightDensity) {

//    Utility.debug(this, "LIGHT DENSITY = " + lightDensity );

    final int r = (int) ( c.getRed()*lightDensity );
    final int g = (int) ( c.getGreen()*lightDensity );
    final int b = (int) ( c.getBlue()*lightDensity );

    return new Color( r, g, b );

  }

}