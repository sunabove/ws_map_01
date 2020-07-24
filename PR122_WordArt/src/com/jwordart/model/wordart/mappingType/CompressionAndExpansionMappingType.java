
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
 * 워드 아트를 상하단으로 달리 매핑하는 클래스이다.
 * 상 하단 구분은 워드 아트의 라인 넘버가 하나 이면
 * 하단은 비어 두고, 전체를 상단으로 매핑한다.
 * 상단 매핑은 움츠리기 매핑이며,
 * 하단 매핑은 부풀리기 매핑이다.
 * 상단 매핑은 y = a*x*x + y0 에 의해서 매핑하고,
 * 하단 매핑 역시 y = a*x*x + y0 에 의해서 매핑한다.
 */

public class CompressionAndExpansionMappingType extends NonLinearMappingType {
  double mw; // 폭 중간 값
  double mh;  // 높이 중간값
  double h; // 높이 값
  double cpY; // 콘트롤 포인트 수직 값
  double a;  // y = a*x*x
  double upperBottomY = -1; // 상단 컴포넌트 최하단 값
  double downTopY = -1;  // 하단 컴포넌트 최상단 값
  int lineNum = 1; // 워드 아트 라인 넘버
  double middleGap; // 중간 나눔 폭
  double upperScaleY; // 상단 컴포넌트 세로 확대 값
  double downScaleY; // 하단 컴포넌트 세로 확대 값

  /**
   * 생성자, 파라미터의 값은 매핑 타입을 나타내는 구분 기호이다.
   * 현 매핑 타입에서는 중요한 의미는 가지지 않는다.
   * 기타 매핑 타입에서는 중요한 의미를 가질 수 있다.
   */
  public CompressionAndExpansionMappingType(int type) {
     super(type);
  }

  /**
   * 상, 하단 컴포넌트 사이의 중간 갭을 리턴한다.
   */
  public double getMiddleGap() {
     return this.middleGap;
  }

  /**
   * x, y 점을 매핑하여 x', y' 값으로 변환하는 함수이다.
   * 매핑의 핵심이 되는 함수이다.
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
   * 상단 컴포넌트의 베이스 라인 값을 설정한다.
   */
  public void setUpperComponentBottomY(double bottomY) {
    this.upperBottomY = bottomY;
  }

  /**
   * 움쳐렸다 부풀리기의 구분 점 구하기
   * 상단 컴포넌트의 최하단 점을 구한다.
   */

  public double getUpperComponentBottomY(WordArt wa) {
    // 이미 값이 구해져 있으면 그 값을 리턴한다.
    if( this.upperBottomY > 0 ) {
       return this.upperBottomY;
    }
    // 끝. 미리 정해진 값 리턴.
    // 새 값 구하기
    Shape [][] glyphs = wa.getGlyphs();
    int lineNum = glyphs.length; // 워드 아트 라인수
    int upperNum = 1; // 상단 컴포넌트 마지막 인덱스
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
    // 끝. 새 값 구하기
    return bottomY;
  }

  /**
   * 하단 컴포넌트의 최상단 점을 설정한다.
   */
  public void setDownComponentTopY(double topY) {
     this.downTopY = topY;
  }

  /**
   * 움쳐렸다 부풀리기의 구분 점 구하기
   * 하단 컴포넌트의 최상단 점을 구한다.
   */

  public double getDownComponentTopY(WordArt wa) {
    // 이미 새 값이 정해져 있으면 그 값을 리턴한다.
    if( this.downTopY > 0 ) {
       return this.downTopY;
    }
    // 끝. 정해진 값 리턴하기
    // 새 값 구하기
    Shape [][] glyphs = wa.getGlyphs();
    int lineNum = glyphs.length; // 워드 아트 라인 수
    int upperNum = 1; // 상단 컴포넌트 마지막 인덱스
    if( lineNum < 3 ) {
       upperNum = 1;
    } else {
       upperNum = lineNum/2;
       upperNum += (lineNum - 2*upperNum);
    }
    int downNum = upperNum + 1; // 하단 컴포넌트 첫 라인 인덱스
    if( downNum > lineNum ) {  // 하단 컴포넌트가 존재하지 않으면(한 줄의 워드 컴포넌트 일경우에)
       return this.getUpperComponentBottomY( wa ); // 상단 컴포넌트의 최하단 좌표를 리턴한다.
    }
    Rectangle2D bounds;
    double topY = h;
    for(int i = 0; i < glyphs[ downNum -1 ].length; i ++ ) {
       bounds = glyphs[downNum -1][i].getBounds2D();
       double y = bounds.getY();
       topY = (topY < y ) ? topY : y;
    }
    // 끝. 새 값 구하기

    return topY;
  }

  /**
   * 매핑에 필요한 각종 변수들을 설정한다.
   * 매핑에 필요한 각정 변수들은 워드 아트 글립을 매핑하기 전에
   * 일괄적으로 한 번 만 설정한다.
   * 퍼포먼스의 향상에 절대적이다.
   */
  @Override
public void setParameters(WordArt wa) {

    Shape [][] glyphs = wa.getGlyphs();
    lineNum = glyphs.length; // 워드 아트 라인 넘버
    // 상, 하단 컴포넌트 구별 좌표 값 구하기
    // 상, 하단 구분점 초기화
    // 상, 하단의 구분점은 0 보다 적어야 다시 값을 구한다.
    this.upperBottomY = -1;
    this.downTopY = -1;

    // 끝. 상,하단 구분점 초기화
    this.upperBottomY = this.getUpperComponentBottomY( wa );
    this.downTopY = this.getDownComponentTopY( wa );
    // 끝. 상, 하단 컴포넌트 구별 좌표 값 구하기

    // 콘트롤 포인트에 의해서 결정되는 변수들을 설정한다.
    this.setControledParameters( wa );
  }

  /**
   * 콘트롤 포인트에 의해서 결정되는 변수들을 설정한다.
   */

  public void setControledParameters(WordArt wa) {
    h = wa.getGlobalDimension().getHeight();
    mw = wa.getGlobalDimension().getWidth()/2.0; // 워드 아트 폭의 반 값
    mh = h/2.0;

    // 컴포넌트 사이 간격 설정
    this.middleGap = h*0.055;

    cpY = wa.getControlPoint().getCenterY(); // y = a*x*x + y0

    a = (mh - middleGap/2.0 - cpY)/mw/mw; // y = a*x*x + y0

    // 상단 컴포넌트 확대 값 설정
    if( lineNum == 1 ) {
       this.upperScaleY = 0.5 - middleGap/(2.0*h);
    } else {
       this.upperScaleY = ((h - middleGap)/2.0)/upperBottomY;
    }
    // 끝. 상단 컴포넌트 확대 값 설정
    // 하단 컴포넌트 확대 값 설정
    this.downScaleY = ((h - middleGap)/2.0)/(h - downTopY);
    // 끝. 하단 컴포넌트 확대 값 설정
  }

  /**
   * 콘트롤 포인트를 설정한다.
   */
  @Override
public void setControlPoint(WordArt wa) {
     wa.setControlPoint( new ControlPointVertical(wa, 0.05, 0.42, 0.3, ControlPointVertical.CENTER) );
  }
}