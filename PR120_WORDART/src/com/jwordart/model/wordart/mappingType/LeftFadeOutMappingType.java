
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


public class LeftFadeOutMappingType extends NonLinearMappingType {
  float w; // 폭 값
  float h; // 높이 값
  float cpY;// 콘트롤 포인트 수직 값
  float a;  // 기울기

  public LeftFadeOutMappingType(int type) {
     super(type);
  }

  @Override
public float [] f(float [] point) {
    if( a == 0.0F ) {
       return point;
    }
    float x = point[0], y = point[1];

    float newX = x, newY = y;

    float refY0 = a*x + cpY, refY1 = -a*x + h - cpY;

    float scaleY = (refY1 - refY0)/h;

    newY = refY0 + y*scaleY;

    return new float [] { newX, newY };
  }

  @Override
public void setParameters(WordArt wa) {
    w = (float) (wa.getGlobalDimension().getWidth());
    h = (float) (wa.getGlobalDimension().getHeight());
    cpY = (float) (wa.getControlPoint().getCenterY());
    a = - cpY/w;
  }

  @Override
public void setControlPoint(WordArt wa) {
     wa.setControlPoint( new ControlPointVertical(wa, 0, 0.485, 0.3) );
  }
}