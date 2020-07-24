
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
import com.jwordart.model.wordart.ControlPointVertical;
import com.jwordart.model.wordart.WordArt;


public class WaveMappingType extends NonLinearMappingType {
  float mw; // 폭 중간 값
  float h;  // 높이 값
  float cpY; // 수직 콘트롤 포인트 수직 값
  float cpX; // 평행 콘트롤 포인트 수평 값
  float scaleY; // 웨이브시 수직 스케일 값
  double a, b;  // y = sin(a*x + b);
  float shear = 0; // 기울기 값
  float scaleX = 1.0F; // 쉬어링시 수평 스케일 값

  public WaveMappingType(int type) {
     super(type);
  }

  @Override
public float [] f(float [] point) {
    float x = point[0], y = point[1];

    float newX = x, newY = y;

    if( cpY != 0 ) {
       float refY = (float)(cpY*Math.sin(a*x + b)) + cpY;
       newY = refY + y*scaleY;
    }
    if( shear != 0 ) {
       newY -= cpY;
       newX = newX*scaleX;
       newX = newX + shear*newY;
       if( shear < 0 ) {
          newX += (2*cpY - h)*shear;
       }
       newY += cpY;
    }

    return new float [] { newX, newY };
  }

  @Override
public void setParameters(WordArt wa) {
    mw = (float) (wa.getGlobalDimension().getWidth()/2.0F);
    h = (float) (wa.getGlobalDimension().getHeight());
    cpY = (float) (wa.getControlPoint().getCenterY());
    cpX = (float) (wa.getSecondControlPoint().getCenterX());
    scaleY = (h - 2*cpY)/h;
    if( h != 2.0F*cpY ) {
       shear = 2.0F*(cpX - mw)/(h - 2.0F*cpY);
       scaleX = 1.0F - Math.abs(shear)*(h - 2.0F*cpY)/(2.0F*mw);
    }
    int mappingType = super.getTypeCode();
    switch( mappingType ) {
       case 20:
          a = Math.PI/mw;
          b = Math.PI;
          break;
       case 21:
          a = Math.PI/mw;
          b = 0;
          break;
       case 22:
          a = 2*Math.PI/mw;
          b = Math.PI;
          break;
       case 23:
          a = 2*Math.PI/mw;
          b = 0;
          break;
       default:
          a = Math.PI/mw;
          b = Math.PI;
          break;
    }
  }

  @Override
public void setControlPoint(WordArt wa) {
     wa.setControlPoint( new ControlPointVertical(wa, 0.0, 0.15, 0.1) );
     wa.setSecondControlPoint( new ControlPointHorizontal(wa, 0.4, 0.6, 0.5) );
  }
}