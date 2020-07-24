
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

import com.jwordart.model.wordart.ControlPointVertical;
import com.jwordart.model.wordart.WordArt;


/**
 * ��,��,�ϴ����� ���� ��Ʈ�� ����� ���� �޸� �����Ѵ�.
 * ����� �������� �����̰�,
 * �ߴ��� ��Ǯ���� �����̸�,
 * �ϴ��� �ٽ� ��ġ���� �����̴�.
 * ���� ��Ʈ�� ���� �ѹ��� �ϳ��̸� ��ü�� ������� �����ϸ�,
 * ���� ��Ʈ�� ���� �ѹ��� �ΰ��̸� ��ܰ� �ߴ����� ������ �����Ѵ�.
 * ���� ��Ʈ�� ���� �ѹ��� �����̻��̸� ��,��,�ϴܿ��� ��� �����Ѵ�.
 * ��,��,�ϴ��� ���� ��� y = a*x*x + y0 �� ���ؼ� �����Ѵ�.
 */

public class CompressionExpansionAndCompressionMappingType extends NonLinearMappingType {
  int lineNum = 1; // ���� ��Ʈ ���� �ѹ�
  double mw; // �� �߰� ��
  double mh;  // ���� �߰���
  double h; // ���� ��
  double cpY; // ��Ʈ�� ����Ʈ ���� ��
  double middleGap; // ���� ��
  double a; // y = a*x*x + y0
  double divisionHeight; // ���� ��

  double upperBottomY; // ��� ������Ʈ ���ϴ� ��ǥ ���� ��
  double middleTopY; // �ߴ� ������Ʈ �ֻ�� ��ǥ ���� ��
  double middleBottomY ; // �ߴ� ������Ʈ ���ϴ� ������
  double downTopY; // �ϴ� ������Ʈ �ֻ�� ���� ��

  double upperScaleY; // ��� ������Ʈ ���� Ȯ�� ��
  double middleScaleY; // �ߴ� ������Ʈ ���� Ȯ�� ��
  double downScaleY; // �ϴ� ������Ʈ ���� Ȯ�� ��

  /**
   * ������
   * �������� �ƱԸ�Ʈ�� ���� �� ���� Ŭ������ ��Ÿ���� ���̵� ��Ȯ�� �Ѵ�.
   * �� ���� Ŭ���������� �߿��� �ǹ̴� ����.
   * �׷���, ��Ÿ ���� Ŭ���������� ������ �ƱԸ�Ʈ�� �߿��� ������ �� �� �ִ�.
   */

  public CompressionExpansionAndCompressionMappingType(int type) {
     super(type);
  }

  /**
   * �� ���� �����Ͽ� ���ε� ���� �����Ѵ�.
   * x, y�� �Լ� f �� �����Ͽ� x', y'�� ��ȯ�Ѵ�.
   * (x', y') = f(x,y)
   */

  @Override
public float [] f(float [] point) {
    double x = point[0], y = point[1];

    double newX = x, newY = y;


    if( y <= this.upperBottomY || lineNum == 1 ) {
        double refY0 = a*(x - mw)*(x - mw) + cpY;
        newY *= this.upperScaleY;
        newY = newY*refY0/this.divisionHeight;
    } else if( y <= this.middleBottomY ) {
        double refY0 = a*(x - mw)*(x - mw) - a*mw*mw,
               refY1 = - a*(x - mw)*(x - mw) + a*mw*mw + divisionHeight;
        newY -= this.middleTopY;
        newY *= this.middleScaleY;
        double halfDH = 0.5*divisionHeight; // ��������� �� ��
        if( newY <= halfDH ) {
            newY = halfDH - (halfDH - newY)*(halfDH - refY0)/halfDH;
        } else {
            newY = halfDH + (newY - halfDH)*(refY1 - halfDH)/halfDH;
        }
        newY += (divisionHeight + middleGap);
    } else {
        newY -= this.downTopY;
        newY *= this.downScaleY;
        double refY0 = - a*(x - mw)*(x - mw) + a*mw*mw;
        newY = divisionHeight - (divisionHeight - newY)*(divisionHeight - refY0)/divisionHeight;
        newY += 2.0*(divisionHeight + middleGap);
    }

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
       return this.h;
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
    return bottomY;
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
       return h;
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
    return bottomY;
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
    this.lineNum = glyphs.length;
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
    // ��Ʈ�� ����Ʈ�� ���ؼ� �����Ǵ� �������� �����Ѵ�.
    this.setControledParameters( wa );

  }

  /**
   * ������Ʈ ������ ���� �����Ѵ�.
   */
   public double getMiddleGap() {
      return this.middleGap;
   }
   /**
    * ������Ʈ ���� �����Ѵ�.
    */
   public double getDivisionHeight() {
      return this.divisionHeight;
   }

  /**
   * ��Ʈ�� ����Ʈ�� ���ؼ� �����Ǵ� �������� �����Ѵ�.
   */

  public void setControledParameters(WordArt wa) {
    h = wa.getGlobalDimension().getHeight();
    mh = h/2.0;
    this.middleGap = h*0.055;
    this.divisionHeight = (h - 2.0*this.middleGap)/3.0;
    if( lineNum == 1 ) {
       this.upperScaleY = divisionHeight/h;
    } else if( lineNum == 2) {
       this.upperScaleY = divisionHeight/this.upperBottomY;
       this.middleScaleY = divisionHeight/( this.middleBottomY - this.middleTopY );
    } else {
       this.upperScaleY = divisionHeight/this.upperBottomY;
       this.middleScaleY = divisionHeight/( this.middleBottomY - this.middleTopY );
       this.downScaleY = divisionHeight/(h - this.downTopY);
    }
    mw = wa.getGlobalDimension().getWidth()/2.0;

    cpY = wa.getControlPoint().getCenterY();
    a = (this.divisionHeight - cpY)/mw/mw;
  }

  /**
   * �� ���� Ŭ������ ����ϴ� ���� ��Ʈ�� ��Ʈ�� ����Ʈ�� �����ϴ�
   * �Լ��̴�.
   * ���� ��Ʈ�� �߽��࿡���� �̵��ϴ� ���� ��Ʈ�� ����Ʈ�� �����Ѵ�.
   */
  @Override
public void setControlPoint(WordArt wa) {
     wa.setControlPoint( new ControlPointVertical(wa, 0.05, (1.0 - 2*0.055)/3.0, 0.25, ControlPointVertical.CENTER) );
  }
}