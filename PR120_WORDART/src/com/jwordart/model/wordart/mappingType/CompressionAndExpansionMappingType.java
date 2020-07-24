
/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.model.wordart.mappingType;

import java.awt.geom.*;
import java.awt.*;

import com.jwordart.model.wordart.ControlPointVertical;
import com.jwordart.model.wordart.WordArt;


/**
 * ���� ��Ʈ�� ���ϴ����� �޸� �����ϴ� Ŭ�����̴�.
 * �� �ϴ� ������ ���� ��Ʈ�� ���� �ѹ��� �ϳ� �̸�
 * �ϴ��� ��� �ΰ�, ��ü�� ������� �����Ѵ�.
 * ��� ������ �������� �����̸�,
 * �ϴ� ������ ��Ǯ���� �����̴�.
 * ��� ������ y = a*x*x + y0 �� ���ؼ� �����ϰ�,
 * �ϴ� ���� ���� y = a*x*x + y0 �� ���ؼ� �����Ѵ�.
 */

public class CompressionAndExpansionMappingType extends NonLinearMappingType {
  double mw; // �� �߰� ��
  double mh;  // ���� �߰���
  double h; // ���� ��
  double cpY; // ��Ʈ�� ����Ʈ ���� ��
  double a;  // y = a*x*x
  double upperBottomY = -1; // ��� ������Ʈ ���ϴ� ��
  double downTopY = -1;  // �ϴ� ������Ʈ �ֻ�� ��
  int lineNum = 1; // ���� ��Ʈ ���� �ѹ�
  double middleGap; // �߰� ���� ��
  double upperScaleY; // ��� ������Ʈ ���� Ȯ�� ��
  double downScaleY; // �ϴ� ������Ʈ ���� Ȯ�� ��

  /**
   * ������, �Ķ������ ���� ���� Ÿ���� ��Ÿ���� ���� ��ȣ�̴�.
   * �� ���� Ÿ�Կ����� �߿��� �ǹ̴� ������ �ʴ´�.
   * ��Ÿ ���� Ÿ�Կ����� �߿��� �ǹ̸� ���� �� �ִ�.
   */
  public CompressionAndExpansionMappingType(int type) {
     super(type);
  }

  /**
   * ��, �ϴ� ������Ʈ ������ �߰� ���� �����Ѵ�.
   */
  public double getMiddleGap() {
     return this.middleGap;
  }

  /**
   * x, y ���� �����Ͽ� x', y' ������ ��ȯ�ϴ� �Լ��̴�.
   * ������ �ٽ��� �Ǵ� �Լ��̴�.
   */
  @Override
public float [] f(float [] point) {

    double x = point[0], y = point[1];

    double newX = x, newY = y;
    double refY;

    if( y <= this.upperBottomY || lineNum == 1) {
       newY *= this.upperScaleY;
       refY = a*(x - mw)*(x - mw) + cpY;
       newY = newY*refY/(mh - middleGap/2.0);
    } else {
       newY -= this.downTopY;
       newY *= this.downScaleY;
       refY = a*(x - mw)*(x - mw) - a*mw*mw;
       double hh = (h - middleGap)/2.0;
       newY = hh - (hh - newY)*(hh - refY )/hh;
       newY += (h + middleGap)/2.0;
    }

    return new float [] { (float)newX, (float)newY };
  }

  /**
   * ��� ������Ʈ�� ���̽� ���� ���� �����Ѵ�.
   */
  public void setUpperComponentBottomY(double bottomY) {
    this.upperBottomY = bottomY;
  }

  /**
   * ���ķȴ� ��Ǯ������ ���� �� ���ϱ�
   * ��� ������Ʈ�� ���ϴ� ���� ���Ѵ�.
   */

  public double getUpperComponentBottomY(WordArt wa) {
    // �̹� ���� ������ ������ �� ���� �����Ѵ�.
    if( this.upperBottomY > 0 ) {
       return this.upperBottomY;
    }
    // ��. �̸� ������ �� ����.
    // �� �� ���ϱ�
    Shape [][] glyphs = wa.getGlyphs();
    int lineNum = glyphs.length; // ���� ��Ʈ ���μ�
    int upperNum = 1; // ��� ������Ʈ ������ �ε���
    if( lineNum < 3 ) {
       upperNum = 1;
    } else {
       upperNum = lineNum/2;
       upperNum += (lineNum - 2*upperNum);
    }
    Rectangle2D bounds;
    double bottomY = 0;
    for(int i = 0; i < glyphs[ upperNum -1 ].length; i ++ ) {
       bounds = glyphs[upperNum -1][i].getBounds2D();
       double y = bounds.getY() + bounds.getHeight();
       bottomY = (bottomY > y ) ? bottomY : y;
    }
    // ��. �� �� ���ϱ�
    return bottomY;
  }

  /**
   * �ϴ� ������Ʈ�� �ֻ�� ���� �����Ѵ�.
   */
  public void setDownComponentTopY(double topY) {
     this.downTopY = topY;
  }

  /**
   * ���ķȴ� ��Ǯ������ ���� �� ���ϱ�
   * �ϴ� ������Ʈ�� �ֻ�� ���� ���Ѵ�.
   */

  public double getDownComponentTopY(WordArt wa) {
    // �̹� �� ���� ������ ������ �� ���� �����Ѵ�.
    if( this.downTopY > 0 ) {
       return this.downTopY;
    }
    // ��. ������ �� �����ϱ�
    // �� �� ���ϱ�
    Shape [][] glyphs = wa.getGlyphs();
    int lineNum = glyphs.length; // ���� ��Ʈ ���� ��
    int upperNum = 1; // ��� ������Ʈ ������ �ε���
    if( lineNum < 3 ) {
       upperNum = 1;
    } else {
       upperNum = lineNum/2;
       upperNum += (lineNum - 2*upperNum);
    }
    int downNum = upperNum + 1; // �ϴ� ������Ʈ ù ���� �ε���
    if( downNum > lineNum ) {  // �ϴ� ������Ʈ�� �������� ������(�� ���� ���� ������Ʈ �ϰ�쿡)
       return this.getUpperComponentBottomY( wa ); // ��� ������Ʈ�� ���ϴ� ��ǥ�� �����Ѵ�.
    }
    Rectangle2D bounds;
    double topY = h;
    for(int i = 0; i < glyphs[ downNum -1 ].length; i ++ ) {
       bounds = glyphs[downNum -1][i].getBounds2D();
       double y = bounds.getY();
       topY = (topY < y ) ? topY : y;
    }
    // ��. �� �� ���ϱ�

    return topY;
  }

  /**
   * ���ο� �ʿ��� ���� �������� �����Ѵ�.
   * ���ο� �ʿ��� ���� �������� ���� ��Ʈ �۸��� �����ϱ� ����
   * �ϰ������� �� �� �� �����Ѵ�.
   * �����ս��� ��� �������̴�.
   */
  @Override
public void setParameters(WordArt wa) {

    Shape [][] glyphs = wa.getGlyphs();
    lineNum = glyphs.length; // ���� ��Ʈ ���� �ѹ�
    // ��, �ϴ� ������Ʈ ���� ��ǥ �� ���ϱ�
    // ��, �ϴ� ������ �ʱ�ȭ
    // ��, �ϴ��� �������� 0 ���� ����� �ٽ� ���� ���Ѵ�.
    this.upperBottomY = -1;
    this.downTopY = -1;

    // ��. ��,�ϴ� ������ �ʱ�ȭ
    this.upperBottomY = this.getUpperComponentBottomY( wa );
    this.downTopY = this.getDownComponentTopY( wa );
    // ��. ��, �ϴ� ������Ʈ ���� ��ǥ �� ���ϱ�

    // ��Ʈ�� ����Ʈ�� ���ؼ� �����Ǵ� �������� �����Ѵ�.
    this.setControledParameters( wa );
  }

  /**
   * ��Ʈ�� ����Ʈ�� ���ؼ� �����Ǵ� �������� �����Ѵ�.
   */

  public void setControledParameters(WordArt wa) {
    h = wa.getGlobalDimension().getHeight();
    mw = wa.getGlobalDimension().getWidth()/2.0; // ���� ��Ʈ ���� �� ��
    mh = h/2.0;

    // ������Ʈ ���� ���� ����
    this.middleGap = h*0.055;

    cpY = wa.getControlPoint().getCenterY(); // y = a*x*x + y0

    a = (mh - middleGap/2.0 - cpY)/mw/mw; // y = a*x*x + y0

    // ��� ������Ʈ Ȯ�� �� ����
    if( lineNum == 1 ) {
       this.upperScaleY = 0.5 - middleGap/(2.0*h);
    } else {
       this.upperScaleY = ((h - middleGap)/2.0)/upperBottomY;
    }
    // ��. ��� ������Ʈ Ȯ�� �� ����
    // �ϴ� ������Ʈ Ȯ�� �� ����
    this.downScaleY = ((h - middleGap)/2.0)/(h - downTopY);
    // ��. �ϴ� ������Ʈ Ȯ�� �� ����
  }

  /**
   * ��Ʈ�� ����Ʈ�� �����Ѵ�.
   */
  @Override
public void setControlPoint(WordArt wa) {
     wa.setControlPoint( new ControlPointVertical(wa, 0.05, 0.42, 0.3, ControlPointVertical.CENTER) );
  }
}