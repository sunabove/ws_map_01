
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


public class TopCurveMappingType extends NonLinearMappingType {
  float w;  // 폭 중간 값
  float h;  // 높이 값
  float a, b;  // y = a*x*x*x + b

  public TopCurveMappingType(int type) {
     super(type);
  }

  @Override
public float [] f(float [] point) {
    if( a == 0.0F ) {
       return point;
    }

    float x = point[0], y = point[1];

    float newX = x, newY = y;

    double refY0 = a*Math.abs((x - 0.3*w)*(x - 0.3*w)*(x - 0.3*w)) + b;
    double refY1 = a*Math.abs((x - 0.5*w)*(x - 0.5*w)*(x - 0.5*w)) + h;

    double scaleY = (refY1 - refY0)/h;

    newY = (float)(refY0 + y*scaleY);

    return new float [] { newX, newY };
  }

  @Override
public void setParameters(WordArt wa) {
    w = (float) (wa.getGlobalDimension().getWidth());
    h = (float) (wa.getGlobalDimension().getHeight());
    float cpY = (float) (wa.getControlPoint().getCenterY());
    a = cpY/(0.027F - 0.7F*0.7F*0.7F)/w/w/w;
    b = -a*(0.7F*w)*(0.7F*w)*(0.7F*w);
  }

  @Override
public void setControlPoint(WordArt wa) {
     wa.setControlPoint( new ControlPointVertical(wa, 0.0, 0.6, 0.45) );
  }
}