
/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.model.wordart.mappingType;

import com.jwordart.model.wordart.ControlPointVertical;
import com.jwordart.model.wordart.WordArt;


public class RingMappingType extends NonLinearMappingType {
  double a, b;  // x*x/a/a + y*y/b/b = 1; (outer arc)
  double ringHeight; // 링 높이
  double h ; // 워드 아트 폭
  double sign = 1.0; // 부호

  public RingMappingType(int type) {
     super(type);
  }

  @Override
public float [] f(float [] point) {

    double x = point[0], y = point[1];

    double theta = Math.PI + sign*2*Math.PI*x/(2*a);

    double cos = Math.cos(theta), sin = Math.sin(theta);

    double r = 1.0/Math.sqrt( cos*cos/a/a + sin*sin/b/b ); // outer radius

    double newX = r*cos, newY = r*sin;

    newX += a;
    newY += b + y*ringHeight/h;

    return new float [] { (float)newX, (float)newY };
  }

  @Override
public void setParameters(WordArt wa) {
    double cpY = wa.getControlPoint().getCenterY();
    a = wa.getGlobalDimension().getWidth()/2.0;
    b = cpY/2.0;
    h = wa.getGlobalDimension().getHeight();
    ringHeight = h - cpY;
    if( super.getTypeCode() == 7 ) {
       this.sign = - 1.0;
    } else {
       this.sign = 1.0;
    }
  }

  @Override
public void setControlPoint(WordArt wa) {
     wa.setControlPoint( new ControlPointVertical(wa, 0.5, 1.0, 0.6, ControlPointVertical.CENTER) );
  }
}