
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


public class TriangleMappingType extends NonLinearMappingType {
  float mw; // ���� �߰� ��ġ ��
  float h; // ����  ��
  float cpY;// ��Ʈ�� ����Ʈ ���� ��
  float a;  // ����

  public TriangleMappingType(int type) {
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

    if( x < mw ) { // ��� ������ ���� ���
       refY = a*x + cpY;
    } else if( x >= mw) { // ��� ������ ���� ���
       refY = -a*(x - mw);
    }
    scaleY = (h - refY)/h;
    newY = h -(h-y)*scaleY;

    return new float [] { newX, newY };
  }

  @Override
public void setParameters(WordArt wa) {
    mw = (float) (wa.getGlobalDimension().getWidth()/2.0F);
    h = (float) (wa.getGlobalDimension().getHeight());
    cpY = (float) (wa.getControlPoint().getCenterY());
    a = - cpY/mw;
  }

  @Override
public void setControlPoint(WordArt wa) {
     wa.setControlPoint( new ControlPointVertical(wa, 0, 1, 0.5) );
  }
}