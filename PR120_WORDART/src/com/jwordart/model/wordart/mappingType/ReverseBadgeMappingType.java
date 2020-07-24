
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


public class ReverseBadgeMappingType extends NonLinearMappingType {
  float mw; // 폭 중간 값
  float sY; // 수직 스케일
  float cpY;// 콘트롤 포인트 수직 값
  float a;  // 기울기

  public ReverseBadgeMappingType(int type) {
     super(type);
  }

  @Override
public float [] f(float [] point) {
    if( a == 0.0F ) {
       return point;
    }
    float x = point[0], y = point[1];

    float newX = x, newY = y;
    float dy = 0;

    if( x < mw ) {
       dy = a*x;
    } else {
       dy = -a*(x - 2*mw);
    }

    newY = sY*y + dy;

    return new float [] { newX, newY };
  }

  @Override
public void setParameters(WordArt wa) {
    mw = (float) (wa.getGlobalDimension().getWidth()/2.0F);
    float h = (float) (wa.getGlobalDimension().getHeight());
    cpY = (float) (wa.getControlPoint().getCenterY());
    a = (h - cpY)/mw;
    sY = cpY/h;
  }

  @Override
public void setControlPoint(WordArt wa) {
     wa.setControlPoint( new ControlPointVertical(wa, 0.5, 1.0, 0.75) );
  }
}