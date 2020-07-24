
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
 * 상,중,하단으로 워드 아트를 나누어서 각기 달리 매핑한다.
 * 상단은 움츠리기 매핑이고,
 * 중단은 부풀리기 매핑이며,
 * 하단은 다시 움치르기 매핑이다.
 * 워드 아트의 라인 넘버가 하나이면 전체를 상단으로 매핑하며,
 * 워드 아트의 라인 넘버가 두개이면 상단과 중단으로 나누어 매핑한다.
 * 워드 아트의 라인 넘버가 세개이상이면 상,중,하단에서 모두 매핑한다.
 * 상,중,하단의 매핑 모두 y = a*x*x + y0 에 의해서 매핑한다.
 */

public class CompressionExpansionAndCompressionMappingType extends NonLinearMappingType {
  int lineNum = 1; // 워드 아트 라인 넘버
  double mw; // 폭 중간 값
  double mh;  // 높이 중간값
  double h; // 높이 값
  double cpY; // 콘트롤 포인트 수직 값
  double middleGap; // 나눔 갭
  double a; // y = a*x*x + y0
  double divisionHeight; // 나눔 폭

  double upperBottomY; // 상단 컴포넌트 최하단 좌표 수직 값
  double middleTopY; // 중단 컴포넌트 최상단 좌표 수직 값
  double middleBottomY ; // 중단 컴포넌트 최하단 수직값
  double downTopY; // 하단 컴포넌트 최상단 수직 값

  double upperScaleY; // 상단 컴포넌트 세로 확대 값
  double middleScaleY; // 중단 컴포넌트 세로 확대 값
  double downScaleY; // 하단 컴포넌트 세로 확대 값

  /**
   * 생성자
   * 생성자의 아규먼트의 값은 본 매핑 클래스를 나타내는 아이디 역확을 한다.
   * 본 매핑 클래스에서는 중요한 의미는 없다.
   * 그러나, 기타 매핑 클래스에서는 생성자 아규먼트가 중요한 역할을 할 수 있다.
   */

  public CompressionExpansionAndCompressionMappingType(int type) {
     super(type);
  }

  /**
   * 한 점을 매핑하여 매핑된 점을 리턴한다.
   * x, y를 함수 f 가 매핑하여 x', y'로 변환한다.
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
        double halfDH = 0.5*divisionHeight; // 디비전폭의 반 값
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
   *  상단 컴포넌트 최하단 수직 좌표 값이다.
   */

  public double getUpperComponentBottomY(WordArt wa) {
    // 이미 값이 초기화 되어 있으면 기존 값을 리턴한다.
    if( this.upperBottomY > 0 ) {
       return this.upperBottomY;
    }
    // 끝. 이전 값 리턴
    if( lineNum == 1 ) {
       return this.h;
    }
    // 새 값 구하기
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
    // 끝. 새 값 구하기
  }

  /**
   * 중단 컴포넌트 최하단 수직 좌표 값이다.
   */
  public double getMiddleComponentBottomY(WordArt wa) {
    // 이미 값이 초기화 되어 있으면 기존 값을 리턴한다.
    if( this.middleBottomY > 0 ) {
       return this.middleBottomY;
    }
    // 끝. 이전 값 리턴

    // 새 값 구하기
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
    // 끝. 새 값 구하기
  }

  /**
   * 중단 컴포넌트 최상단 수직 좌표 값이다.
   */
  public double getMiddleComponentTopY(WordArt wa) {
    // 이미 값이 초기화 되어 있으면 기존 값을 리턴한다.
    if( this.middleTopY > 0 ) {
       return this.middleTopY;
    }
    // 끝. 기존 값 리턴
    // 새 값 구하기
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
    // 끝. 새 값 구하기
  }

  /**
   * 하단 컴포넌트 최상단 수직 좌표 값이다.
   */
  public double getDownComponentTopY(WordArt wa) {
    // 이미 기존 값이 초기화 되어 있으면, 기존 값을 리턴한다.
    if( this.downTopY > 0 ) {
       return this.downTopY;
    }
    // 끝. 기존 값 리턴
    // 새 값 구하기
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
    // 끝. 새 값 구하기
  }

  /**
   * 워드 아트의 라인 갯수를 리턴한다.
   */

  public int getLineNumber() {
     return this.lineNum;
  }

  /**
   * 매핑전에 반드시 호출하여
   * 매핑에 필요한 각종 변수들을 설정하는 함수이다.
   * 매핑 전에 호출하지 않으면 매핑이 적절히 실행 되지 않는다.
   */
  @Override
public void setParameters(WordArt wa) {
    Shape [][] glyphs = wa.getGlyphs();
    this.lineNum = glyphs.length;
    // 상,중,하단 컴포넌트 구분 값 초기화
    this.upperBottomY = this.middleBottomY = this.middleTopY = this.downTopY = -1;
    h = wa.getGlobalDimension().getHeight();
    // 끝. 구분 값 초기화
    // 상,중,하단 컴포넌트 구분 값 구하기
    this.upperBottomY = this.getUpperComponentBottomY( wa );
    this.middleTopY = this.getMiddleComponentTopY( wa );
    this.middleBottomY = this.getMiddleComponentBottomY( wa );
    this.downTopY = this.getDownComponentTopY( wa );
    // 끝. 구분 값 구하기
    // 콘트롤 포인트에 의해서 결정되는 변수들을 설정한다.
    this.setControledParameters( wa );

  }

  /**
   * 컴포넌트 사이의 갭을 리턴한다.
   */
   public double getMiddleGap() {
      return this.middleGap;
   }
   /**
    * 컴포넌트 폭을 리턴한다.
    */
   public double getDivisionHeight() {
      return this.divisionHeight;
   }

  /**
   * 콘트롤 포인트에 의해서 결정되는 변수들을 설정한다.
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
   * 본 매핑 클래스를 사용하는 워드 아트의 콘트롤 포인트를 설정하는
   * 함수이다.
   * 워드 아트의 중심축에서만 이동하는 수직 콘트롤 포인트를 설정한다.
   */
  @Override
public void setControlPoint(WordArt wa) {
     wa.setControlPoint( new ControlPointVertical(wa, 0.05, (1.0 - 2*0.055)/3.0, 0.25, ControlPointVertical.CENTER) );
  }
}