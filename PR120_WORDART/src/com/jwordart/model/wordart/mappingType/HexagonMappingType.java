
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


public class HexagonMappingType extends NonLinearMappingType {
  float w; // 폭 값
  float mh; // 높이 중간 값
  float cpY;// 콘트롤 포인트 수직 값
  float a;  // 기울기

  public HexagonMappingType(int type) {
     super(type);
  }

  @Override
public float [] f(float [] point) {
    if( a == 0.0F ) {
       return point;
    }
    float x = point[0], y = point[1];

    float newX = x, newY = y;

    float scaleY = 1;
    float refY = 0;

    if( x < w/3.0F ) {
       if( y < mh ) {
          refY = a*x + cpY;
          scaleY = (mh - refY)/mh;
          newY = mh -(mh-y)*scaleY;
       } else {
          refY = -a*x + 2*mh - cpY;
          scaleY = (refY - mh)/mh;
          newY = mh + (y - mh)*scaleY;
       }
    } else if( x > w*2.0F/3.0F ) {
       if( y < mh ) {
          refY = - a*(x - w*2.0F/3.0F);
          scaleY = (mh - refY)/mh;
          newY = mh -(mh-y)*scaleY;
       } else {
          refY = a*(x - w*2.0F/3.0F) + 2*mh;
          scaleY = (refY - mh)/mh;
          newY = mh + (y - mh)*scaleY;
       }
    }

    return new float [] { newX, newY };
  }

  @Override
public void setParameters(WordArt wa) {
    w = (float) (wa.getGlobalDimension().getWidth());
    mh = (float) (wa.getGlobalDimension().getHeight()/2.0F);
    cpY = (float) (wa.getControlPoint().getCenterY());
    a = - cpY/(w/3.0F);
  }

  @Override
public void setControlPoint(WordArt wa) {
     wa.setControlPoint( new ControlPointVertical(wa, 0, 0.49, 0.22) );
  }
}