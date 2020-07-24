
/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.model.wordart.mappingType;

import com.jwordart.model.wordart.ControlPointArc;
import com.jwordart.model.wordart.WordArt;


public class BottomArcMappingType extends NonLinearMappingType {
  double a0, b0;  // x*x/a/a + y*y/b/b = 1; (outer arc)
  double a1, b1; // x*x/a/a + y*y/b/b = 1; (inner arc)
  double theta0, theta1; // starting and ending radian angle

  public BottomArcMappingType(int type) {
     super(type);
  }

  @Override
public float [] f(float [] point) {

    double x = point[0], y = point[1];

    double theta = theta0;

    if( theta0 > Math.PI && theta0 < 1.5*Math.PI ) {  // 3/4분면
       theta = theta0 - (2*Math.PI - (theta1 - theta0))*x/(2*a0);
    } else if( theta0 >= 1.5*Math.PI ) {               // 4/4 분면
       theta = theta0 - (theta0 - theta1)*x/(2*a0);
    } else if( theta0 <= 0.5*Math.PI ) {
       theta = theta0 - (2*Math.PI - (theta1 - theta0))*x/(2*a0);
    } else {                                          // 1/4, 2/4 분면
       theta = theta0 - (theta0 - theta1)*x/(2*a0);
    }

    double cos = Math.cos(theta), sin = Math.sin(theta);

    double r0 = 1.0/Math.sqrt( cos*cos/a0/a0 + sin*sin/b0/b0 ); // outer radius

    double r1 = r0;

    if( a1 == 0 && b1 == 0 ) {
       r1 = 0;
    } else {
       r1 = 1.0/Math.sqrt( cos*cos/a1/a1 + sin*sin/b1/b1 ); // inner radius
    }

    double r = r1 + (r0 - r1)*y/(2*b0);

    double newX = r*cos, newY = r*sin;

    newX += a0; newY += b0;

    return new float [] { (float)newX, (float)newY };
  }

  @Override
public void setParameters(WordArt wa) {
    a0 = wa.getGlobalDimension().getWidth()/2.0;
    b0 = wa.getGlobalDimension().getHeight()/2.0;
    double x = wa.getControlPoint().getCenterX();
    double y = wa.getControlPoint().getCenterY();

    x -= a0; y -= b0;

    double scale = Math.sqrt( x*x/a0/a0 + y*y/b0/b0);

    a1 = a0*scale; b1 = b0*scale;

    if( x == 0 && y == 0 ) {
       theta1 = - Math.PI;
       theta0 = 0;
    } else {
       theta0 = com.jwordart.util.WordArtUtil.getAngle( - x, y );
       theta1 = com.jwordart.util.WordArtUtil.getAngle( x, y );
    }

  }

  @Override
public void setControlPoint(WordArt wa) {
     wa.setControlPoint( new ControlPointArc(wa, 0.5, 0, 0 ) );
  }
}