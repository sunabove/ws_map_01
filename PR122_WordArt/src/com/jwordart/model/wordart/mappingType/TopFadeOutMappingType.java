
/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.model.wordart.mappingType;

import com.jwordart.model.wordart.ControlPointHorizontal;
import com.jwordart.model.wordart.WordArt;


public class TopFadeOutMappingType extends NonLinearMappingType {
  float w; // 폭 값
  float h; // 높이 값
  float cpX;// 콘트롤 포인트 수평 값
  float a;  // 기울기

  public TopFadeOutMappingType(int type) {
     super(type);
  }

  @Override
public float [] f(float [] point) {
    if( cpX == 0 ) {
       return point;
    }
    float x = point[0], y = point[1];

    float newX = x, newY = y;

    float refX0 = (y - h)/a, refX1 = (h - y)/a + w;

    float scaleX = (refX1 - refX0)/w;

    newX = refX0 + x*scaleX;

    return new float [] { newX, newY };
  }

  @Override
public void setParameters(WordArt wa) {
    w = (float) (wa.getGlobalDimension().getWidth());
    h = (float) (wa.getGlobalDimension().getHeight());
    cpX = (float) (wa.getControlPoint().getCenterX());
    if( cpX != 0 ) {
       a = - h/cpX;
    }
  }

  @Override
public void setControlPoint(WordArt wa) {
     wa.setControlPoint( new ControlPointHorizontal(wa, 0, 0.5, 0.3, ControlPointHorizontal.TOP) );
  }
}