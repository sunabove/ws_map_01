
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


public class BottomExpansionMappingType extends NonLinearMappingType {
  float mw; // 폭 중간 값
  float h;  // 높이 값
  float a;  // y = a*x*x

  public BottomExpansionMappingType(int type) {
     super(type);
  }

  @Override
public float [] f(float [] point) {
    if( a == 0.0F ) {
       return point;
    }
    float x = point[0], y = point[1];

    float newX = x, newY = y;

    float refY = a*(x - mw)*(x - mw) + h;

    float scaleY = refY/h;

    newY = y*scaleY;

    return new float [] { newX, newY };
  }

  @Override
public void setParameters(WordArt wa) {
    mw = (float) (wa.getGlobalDimension().getWidth()/2.0);
    h = (float) (wa.getGlobalDimension().getHeight());
    float cpY = (float) (wa.getControlPoint().getCenterY());
    a = (cpY - h)/mw/mw;
  }

  @Override
public void setControlPoint(WordArt wa) {
     wa.setControlPoint( new ControlPointVertical(wa, 0.5, 1.0, 0.7) );
  }
}