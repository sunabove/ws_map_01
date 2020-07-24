
/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.model.wordart.mappingType;

import java.awt.geom.*;
import java.awt.*;

import com.jwordart.model.wordart.ControlPointArc;
import com.jwordart.model.wordart.WordArt;


/**
 * ��,��,�ϴ����� ���� ��Ʈ�� ����� ���� �޸� �����Ѵ�.
 * ����� TopArcMappingType �̰�,
 * �ߴ��� Ȯ��/��� �����̸�,
 * �ϴ��� BottomArcMappingType �̴�.
 * ���� ��Ʈ�� ���� �ѹ��� �ϳ��̸� ��ü�� ������� �����ϸ�,
 * ���� ��Ʈ�� ���� �ѹ��� �ΰ��̸� ��ܰ� �ߴ����� ������ �����Ѵ�.
 * ���� ��Ʈ�� ���� �ѹ��� �����̻��̸� ��,��,�ϴܿ��� ��� �����Ѵ�.
 */

public class ButtonMappingType extends NonLinearMappingType {

  double a0, b0;  // x*x/a/a + y*y/b/b = 1; (outer arc)
  double a1, b1; // x*x/a/a + y*y/b/b = 1; (inner arc)
  double topTheta0, topTheta1; // top component starting and ending radian angle
  double bottomTheta0, bottomTheta1; // bottom component starting and ending radian angle
  double arcLength; // outer arc length
  double lineGap; // ���� ��Ʈ �ٰ� ����

  int lineNum = 1; // ���� ��Ʈ ���� �ѹ�

  double h; // ���� ��

  double upperBottomY; // ��� ������Ʈ ���ϴ� ��ǥ ���� ��
  double middleTopY; // �ߴ� ������Ʈ �ֻ�� ��ǥ ���� ��
  double middleBottomY ; // �ߴ� ������Ʈ ���ϴ� ������
  double downTopY; // �ϴ� ������Ʈ �ֻ�� ���� ��

  double middleScaleX = 1, middleScaleY = 1; // �ߴ� ������Ʈ ������ ��
  double intersectX, intersectY; // �� Ÿ���� �ߴ� ������Ʈ ��� ������ ������
  double middleComponentHeight; // �ߴ� ������Ʈ ����(������)

  /**
   * ������
   * �������� �ƱԸ�Ʈ�� ���� �� ���� Ŭ������ ��Ÿ���� ���̵� ��Ȯ�� �Ѵ�.
   * �� ���� Ŭ���������� �߿��� �ǹ̴� ����.
   * �׷���, ��Ÿ ���� Ŭ���������� ������ �ƱԸ�Ʈ�� �߿��� ������ �� �� �ִ�.
   */

  public ButtonMappingType(int type) {
     super(type);
  }

  /**
   * ���� ��Ʈ�� �ٰ� ������ �����Ѵ�.
   */
  public double getLineGap() {
     return this.lineGap;
  }

  /**
   * �� ���� �����Ͽ� ���ε� ���� �����Ѵ�.
   * x, y�� �Լ� f �� �����Ͽ� x', y'�� ��ȯ�Ѵ�.
   * (x', y') = f(x,y)
   */

  @Override
public float [] f(float [] point) {
    float y = point[1];

    if( y <= this.upperBottomY ) {
        return getTopArcMapping( point );
    } else if( y <= this.middleBottomY ) {
        return getScaleMapping( point );
    } else {
        return getBottomArcMapping( point );
    }
  }

  /**
   * �Էµ� ���� TopArcMapping�� �Ͽ� ����� �����Ѵ�.
   * @param point �Է� ��
   */
  public float [] getTopArcMapping(float [] point) {
     double x = point[0], y = point[1];

    double theta = topTheta0;

    if( topTheta0 > 0.5*Math.PI && topTheta0 < Math.PI ) {
       theta = topTheta0 + (2*Math.PI - (topTheta0 - topTheta1))*x/(2*a0);
    } else if( topTheta0 > 1.5*Math.PI ) {
       theta = topTheta0 + (2*Math.PI - (topTheta0 - topTheta1))*x/(2*a0);
    } else {
       theta = topTheta0 + Math.abs(topTheta0 - topTheta1)*x/(2*a0);
    }

    double cos = Math.cos(theta), sin = Math.sin(theta);

    double r0 = 1.0/Math.sqrt( cos*cos/a0/a0 + sin*sin/b0/b0 ); // outer radius

    double r1 = r0;

    if( a1 == 0 && b1 == 0 ) {
       r1 = 0;
    } else {
       r1 = 1.0/Math.sqrt( cos*cos/a1/a1 + sin*sin/b1/b1 ); // inner radius
    }

    //double r = r0 - (r0 - r1)*y/(2*b0);
    // upperBottomY �� lineGap/2.0 ��ŭ ������ �����Ƿ�, �̸� ������ �ش�.
    double r = r0 - (r0 - r1)*y/(this.upperBottomY - this.lineGap/2.0);

    double newX = r*cos, newY = r*sin;

    newX += a0; newY += b0;

    return new float [] { (float)newX, (float)newY };
  }

  /**
   * �Էµ� ���� Ȯ��/����Ͽ� ����� �����Ѵ�.
   * @param point �Է� ��
   */
  public float [] getScaleMapping(float [] point) {
     double x = point[0], y = point[1];
     double newX = x, newY = y;
     // middleBottomY�� lineGap/2.0 ��ŭ ������ �����Ƿ� �̸� ������ �ش�.
     double realMiddleBottomY = this.middleBottomY - lineGap/2.0;
     // ��. ����
     newY = (realMiddleBottomY - y)*this.middleScaleY;
     newY = h/2.0 + (b0 - b1)/2.0 - newY;
     newX = (a0 - x)*this.middleScaleX;
     newX = a0 - newX;
     return new float [] { (float)newX, (float)newY };
  }

  /**
   * �Էµ� ���� BottomArcMapping�� �Ͽ� ����� �����Ѵ�.
   * @param point �Է� ��
   */
  public float [] getBottomArcMapping(float [] point) {
     double x = point[0], y = point[1];

    double theta = bottomTheta0;

    if( bottomTheta0 > Math.PI && bottomTheta0 < 1.5*Math.PI ) {  // 3/4�и�
       theta = bottomTheta0 - (2*Math.PI - (bottomTheta1 - bottomTheta0))*x/(2*a0);
    } else if( bottomTheta0 >= 1.5*Math.PI ) {               // 4/4 �и�
       theta = bottomTheta0 - (bottomTheta0 - bottomTheta1)*x/(2*a0);
    } else if( bottomTheta0 <= 0.5*Math.PI ) {
       theta = bottomTheta0 - (2*Math.PI - (bottomTheta1 - bottomTheta0))*x/(2*a0);
    } else {                                          // 1/4, 2/4 �и�
       theta = bottomTheta0 - (bottomTheta0 - bottomTheta1)*x/(2*a0);
    }

    double cos = Math.cos(theta), sin = Math.sin(theta);

    double r0 = 1.0/Math.sqrt( cos*cos/a0/a0 + sin*sin/b0/b0 ); // outer radius

    double r1 = r0;

    if( a1 == 0 && b1 == 0 ) {
       r1 = 0;
    } else {
       r1 = 1.0/Math.sqrt( cos*cos/a1/a1 + sin*sin/b1/b1 ); // inner radius
    }

    // double r = r1 + (r0 - r1)*y/(2*b0);
    // upperBottomY �� lineGap/2.0 ��ŭ ������ �����Ƿ�, �̸� ������ �ش�.
    double r = r1 + (r0 - r1)*(y - this.downTopY)/(h - this.downTopY);

    double newX = r*cos, newY = r*sin;

    newX += a0; newY += b0;

    return new float [] { (float)newX, (float)newY };
  }

  /**
   *  ��� ������Ʈ ���ϴ� ���� ��ǥ ���̴�.
   */

  public double getUpperComponentBottomY(WordArt wa) {
    // �̹� ���� �ʱ�ȭ �Ǿ� ������ ���� ���� �����Ѵ�.
    if( this.upperBottomY > 0 ) {
       return this.upperBottomY;
    }
    // ��. ���� �� ����
    if( lineNum == 1 ) {
       return this.h + lineGap/2.0;
    }
    // �� �� ���ϱ�
    Shape [][] glyphs = wa.getGlyphs();
    int upperNum = 1;

    if( lineNum > 3 ) {
       upperNum = lineNum/3;
       double etcNum = lineNum - upperNum*3;
       if( etcNum != 0 ) {
          upperNum ++;
       }
    }

    Rectangle2D bounds;

    double bottomY = 0;

    for(int i = 0; i < glyphs[ upperNum -1 ].length; i ++ ) {
       bounds = glyphs[upperNum -1][i].getBounds2D();
       double y = bounds.getY() + bounds.getHeight();
       bottomY = (bottomY > y ) ? bottomY : y;
    }
    return bottomY + lineGap/2.0;
    // ��. �� �� ���ϱ�
  }

  /**
   * �ߴ� ������Ʈ ���ϴ� ���� ��ǥ ���̴�.
   */
  public double getMiddleComponentBottomY(WordArt wa) {
    // �̹� ���� �ʱ�ȭ �Ǿ� ������ ���� ���� �����Ѵ�.
    if( this.middleBottomY > 0 ) {
       return this.middleBottomY;
    }
    // ��. ���� �� ����

    // �� �� ���ϱ�
    if( lineNum == 1 || lineNum == 2) {
       return h + lineGap/2.0;
    }

    Shape [][] glyphs = wa.getGlyphs();

    int middleBottomNum = 2;

    if( lineNum > 2 ) {
        middleBottomNum = lineNum - lineNum/3;
    }

    Rectangle2D bounds;
    double bottomY = 0;
    for(int i = 0; i < glyphs[ middleBottomNum -1 ].length; i ++ ) {
       bounds = glyphs[middleBottomNum -1][i].getBounds2D();
       double y = bounds.getY() + bounds.getHeight();
       bottomY = (bottomY > y ) ? bottomY : y;
    }
    return bottomY + lineGap/2.0;
    // ��. �� �� ���ϱ�
  }

  /**
   * �ߴ� ������Ʈ �ֻ�� ���� ��ǥ ���̴�.
   */
  public double getMiddleComponentTopY(WordArt wa) {
    // �̹� ���� �ʱ�ȭ �Ǿ� ������ ���� ���� �����Ѵ�.
    if( this.middleTopY > 0 ) {
       return this.middleTopY;
    }
    // ��. ���� �� ����
    // �� �� ���ϱ�
    if( lineNum == 1 ) {
       return h;
    }

    Shape [][] glyphs = wa.getGlyphs();

    int upperNum = 1;

    if( lineNum > 3 ) {
       upperNum = lineNum/3;
       double etcNum = lineNum - upperNum*3;
       if( etcNum != 0 ) {
          upperNum ++;
       }
    }

    int middleTopNum = upperNum + 1;

    Rectangle2D bounds;

    double topY = h;

    for(int i = 0; i < glyphs[ middleTopNum -1 ].length; i ++ ) {
       bounds = glyphs[middleTopNum -1][i].getBounds2D();
       double y = bounds.getY();
       topY = (topY < y ) ? topY : y;
    }

    return topY;
    // ��. �� �� ���ϱ�
  }

  /**
   * �ϴ� ������Ʈ �ֻ�� ���� ��ǥ ���̴�.
   */
  public double getDownComponentTopY(WordArt wa) {
    // �̹� ���� ���� �ʱ�ȭ �Ǿ� ������, ���� ���� �����Ѵ�.
    if( this.downTopY > 0 ) {
       return this.downTopY;
    }
    // ��. ���� �� ����
    // �� �� ���ϱ�
    if( lineNum == 1 || lineNum == 2) {
       return h;
    }

    Shape [][] glyphs = wa.getGlyphs();

    int downTopNum = 3;

    if( lineNum > 3 ) {
        downTopNum = lineNum - lineNum/3 + 1;
    }

    Rectangle2D bounds;
    double topY = h;
    for(int i = 0; i < glyphs[ downTopNum -1 ].length; i ++ ) {
       bounds = glyphs[downTopNum -1][i].getBounds2D();
       double y = bounds.getY();
       topY = (topY < y ) ? topY : y;
    }
    return topY;
    // ��. �� �� ���ϱ�
  }

  /**
   * ���� ��Ʈ�� ���� ������ �����Ѵ�.
   */

  public int getLineNumber() {
     return this.lineNum;
  }

  /**
   * �������� �ݵ�� ȣ���Ͽ�
   * ���ο� �ʿ��� ���� �������� �����ϴ� �Լ��̴�.
   * ���� ���� ȣ������ ������ ������ ������ ���� ���� �ʴ´�.
   */
  @Override
public void setParameters(WordArt wa) {
    Shape [][] glyphs = wa.getGlyphs();
    // ���� ��Ʈ ���� ���� ����
    this.lineNum = glyphs.length;
    // ��. ���� ���� ����

    // ���� ������ ���� ����
    this.lineGap = wa.getFontLeading()*wa.getDimensionScaleY();
    // ���� ���� ����

    // ��,��,�ϴ� ������Ʈ ���� �� �ʱ�ȭ
    this.upperBottomY = this.middleBottomY = this.middleTopY = this.downTopY = -1;
    h = wa.getGlobalDimension().getHeight();
    // ��. ���� �� �ʱ�ȭ

    // ��,��,�ϴ� ������Ʈ ���� �� ���ϱ�
    this.upperBottomY = this.getUpperComponentBottomY( wa );
    this.middleTopY = this.getMiddleComponentTopY( wa );
    this.middleBottomY = this.getMiddleComponentBottomY( wa );
    this.downTopY = this.getDownComponentTopY( wa );
    // ��. ���� �� ���ϱ�

    // �ܰ� Ÿ�� �� ����
    a0 = wa.getGlobalDimension().getWidth()/2.0;
    b0 = wa.getGlobalDimension().getHeight()/2.0;
    // ��. �ܰ� Ÿ�� �� ����

    this.setControledParameters( wa );
  }


  /**
   * ��Ʈ�� ����Ʈ�� ���� ���� �Ǵ� �������� �����Ѵ�.
   */

  public void setControledParameters(WordArt wa) {
    // ��Ʈ�� ����Ʈ ��ǥ�� ȹ��
    double x = wa.getControlPoint().getCenterX();
    double y = wa.getControlPoint().getCenterY();
    // ��. ��Ʈ�� ����Ʈ ��ǥ�� ȹ��

    // ��Ʈ�� ����Ʈ ��ǥ�� ����(������ �̵�)
    x -= a0; y -= b0;
    // ��. ��Ʈ�� ����Ʈ ��ǥ�� ����

    // ���� Ÿ�� �� ����
    // ��Ʈ�� ����Ʈ�� ��ġ�� ���߾� �ܰ� Ÿ�� ���� ������ �Ͽ� �����Ѵ�.
    // ������ ���ϱ�
    double scale = Math.sqrt( x*x/a0/a0 + y*y/b0/b0);
    // ��. ������ ���ϱ�
    a1 = a0*scale; b1 = b0*scale;
    // ��. ���� Ÿ�� �� ����

    // �ߴ� ������Ʈ ������ �� ����
    // middleBottomY�� ���� ������ lineGap/2.0 ������ �����Ƿ�, �̸� �����Ͽ� �ش�.
    middleComponentHeight = b0 - b1;
    intersectY = middleComponentHeight/2.0;  // �� Ÿ���� ������ ������
    intersectX = a1*Math.sqrt(1.0 - intersectY*intersectY/b1/b1); // �� Ÿ���� ������ ������
    this.middleScaleX = intersectX/a0;
    if( lineNum > 1 ) {
       double realMiddleBottomY = this.middleBottomY - lineGap/2.0;
       this.middleScaleY = middleComponentHeight/(realMiddleBottomY - this.middleTopY);
    }
    // ��. ������ �� ����

    // ���� �� ���� ���� ����
    if( x == 0 && y == 0 ) {
       topTheta0 = - Math.PI;
       topTheta1 = 0;
       bottomTheta0 = Math.PI;
       bottomTheta1 = 0;
    } else {
       topTheta0 = com.jwordart.util.WordArtUtil.getAngle( x, y );
       topTheta1 = com.jwordart.util.WordArtUtil.getAngle( -x, y );
       bottomTheta0 = 2*Math.PI - topTheta0;
       bottomTheta1 = 2*Math.PI - topTheta1;
       bottomTheta1 %= 2*Math.PI;
    }
    // ��. ���� �� ���� ���� ����
  }

  public Shape [] getMiddleComponentLines() {
      return new Shape [] {
               new Line2D.Double( - intersectX + a0, intersectY + b0/2.0,
                                  intersectX + a0, intersectY + b0/2.0),
               new Line2D.Double( - intersectX + a0, intersectY + b0/2.0 + middleComponentHeight,
                                  intersectX + a0, intersectY + b0/2.0 + middleComponentHeight )
             };
  }

  /**
   * �� ���� Ŭ������ ����ϴ� ���� ��Ʈ�� ��Ʈ�� ����Ʈ�� �����ϴ�
   * �Լ��̴�.
   * ���� ��Ʈ�� �߽��࿡���� �̵��ϴ� ���� ��Ʈ�� ����Ʈ�� �����Ѵ�.
   */
  @Override
public void setControlPoint(WordArt wa) {
     wa.setControlPoint( new ControlPointArc(wa, 0.6, 0.52, Math.PI ) );
  }
}