package com.jcosmos.java3d;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.awt.geom.*;

public class Matrix3D {

  private double t00, t01, t02;
  private double t10, t11, t12;
  private double      t21, t22;
  private double           t32;

  private double rho;
  private double theta;
  private double phi;

//  private Point3D sunLoc;

  public Matrix3D( final double rho, final double theta, final double phi ) {

    this.rho = rho;
    this.theta = theta;
    this.phi = phi;

    final double cosTheta = Math.cos( theta );
    final double sinTheta = Math.sin( theta );

    final double cosPhi   = Math.cos( phi );
    final double sinPhi   = Math.sin( phi );

    t00 = - sinTheta ; t01 = - cosPhi*cosTheta ; t02 = sinPhi*cosTheta;
    t10 =   cosTheta ; t11 = - cosPhi*sinTheta ; t12 = sinPhi*sinTheta;
		       t21 =            sinPhi ; t22 =          cosPhi;
						 t32 = - rho;

  }

  public Point3D getEyeLocation3D() {

    final double phi = this.phi;
    final double theta = this.theta;

    final double r = rho*Math.sin( phi );

    return new Point3D( r*Math.cos( theta ), r*Math.sin( theta ), rho*Math.cos( phi ) );

  }

  private double [] getEyePoint( final Point3D wordPoint3D ) {

    return new double [] {
			   t00*wordPoint3D.x + t10*wordPoint3D.y,
			   t01*wordPoint3D.x + t11*wordPoint3D.y + t21*wordPoint3D.z,
			   t02*wordPoint3D.x + t12*wordPoint3D.y + t22*wordPoint3D.z + t32
			 };

  }

  private Point2D getScreenPoint( final double [] eyePoint3D, final double screenDistance) {

    return new Point2D.Double (
	    - screenDistance*eyePoint3D[0]/eyePoint3D[2],
	    - screenDistance*eyePoint3D[1]/eyePoint3D[2]
	   );

  }

  public Point2D getScreenPoint( final Point3D wordPoint3D, final double screenDistance ) {

    return getScreenPoint( getEyePoint( wordPoint3D ), screenDistance );

  }

}