
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


public class DownwardStairMappingType extends NonLinearMappingType {
  float w; // 폭 값
  float h; // 높이 값
  float cpY0, cpY1;// 아랫 및 위 콘트롤 포인트 수직 값
  float a0, a1;  // 아랫 기울기, 윗 기울기

  public DownwardStairMappingType(int type) {
     super(type);
  }

  @Override
public float [] f(float [] point) {
    if( a0 == 0 ) {
       return point;
    }

    float x = point[0], y = point[1];

    float newX = x, newY = y;

    float refY0 = a0*x + cpY0, refY1 = a1*x; // 아래 및 윗 기준 수직 좌표 값

    float scaleY = (refY0 - refY1)/h;

    newY = refY1 + y*scaleY;

    return new float [] { newX, newY };
  }

  @Override
public void setParameters(WordArt wa) {
    w = (float) (wa.getGlobalDimension().getWidth());
    h = (float) (wa.getGlobalDimension().getHeight());
    cpY0 = (float) (wa.getControlPoint().getCenterY());
    cpY1 = (h - cpY0)*0.2F;
    a0 = (h - cpY0)/w; // 아랫 기울기
    a1 = cpY1/w;       // 위 기울기
  }

  @Override
public void setControlPoint(WordArt wa) {
     wa.setControlPoint( new ControlPointVertical(wa, 0.3, 1, 0.3) );
  }
}