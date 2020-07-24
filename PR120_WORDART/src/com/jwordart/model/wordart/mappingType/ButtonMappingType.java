
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
 * 상,중,하단으로 워드 아트를 나누어서 각기 달리 매핑한다.
 * 상단은 TopArcMappingType 이고,
 * 중단은 확대/축소 매핑이며,
 * 하단은 BottomArcMappingType 이다.
 * 워드 아트의 라인 넘버가 하나이면 전체를 상단으로 매핑하며,
 * 워드 아트의 라인 넘버가 두개이면 상단과 중단으로 나누어 매핑한다.
 * 워드 아트의 라인 넘버가 세개이상이면 상,중,하단에서 모두 매핑한다.
 */

public class ButtonMappingType extends NonLinearMappingType {

  double a0, b0;  // x*x/a/a + y*y/b/b = 1; (outer arc)
  double a1, b1; // x*x/a/a + y*y/b/b = 1; (inner arc)
  double topTheta0, topTheta1; // top component starting and ending radian angle
  double bottomTheta0, bottomTheta1; // bottom component starting and ending radian angle
  double arcLength; // outer arc length
  double lineGap; // 워드 아트 줄간 간격

  int lineNum = 1; // 워드 아트 라인 넘버

  double h; // 높이 값

  double upperBottomY; // 상단 컴포넌트 최하단 좌표 수직 값
  double middleTopY; // 중단 컴포넌트 최상단 좌표 수직 값
  double middleBottomY ; // 중단 컴포넌트 최하단 수직값
  double downTopY; // 하단 컴포넌트 최상단 수직 값

  double middleScaleX = 1, middleScaleY = 1; // 중단 컴포넌트 스케일 값
  double intersectX, intersectY; // 내 타원과 중단 컴포넌트 상단 직선의 교차점
  double middleComponentHeight; // 중단 컴포넌트 높이(변형후)

  /**
   * 생성자
   * 생성자의 아규먼트의 값은 본 매핑 클래스를 나타내는 아이디 역확을 한다.
   * 본 매핑 클래스에서는 중요한 의미는 없다.
   * 그러나, 기타 매핑 클래스에서는 생성자 아규먼트가 중요한 역할을 할 수 있다.
   */

  public ButtonMappingType(int type) {
     super(type);
  }

  /**
   * 워드 아트의 줄간 간격을 리턴한다.
   */
  public double getLineGap() {
     return this.lineGap;
  }

  /**
   * 한 점을 매핑하여 매핑된 점을 리턴한다.
   * x, y를 함수 f 가 매핑하여 x', y'로 변환한다.
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
   * 입력된 점을 TopArcMapping을 하여 결과를 리턴한다.
   * @param point 입력 점
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
    // upperBottomY 는 lineGap/2.0 만큼 더해져 있으므로, 이를 보정해 준다.
    double r = r0 - (r0 - r1)*y/(this.upperBottomY - this.lineGap/2.0);

    double newX = r*cos, newY = r*sin;

    newX += a0; newY += b0;

    return new float [] { (float)newX, (float)newY };
  }

  /**
   * 입력된 점을 확대/축소하여 결과를 리턴한다.
   * @param point 입력 점
   */
  public float [] getScaleMapping(float [] point) {
     double x = point[0], y = point[1];
     double newX = x, newY = y;
     // middleBottomY는 lineGap/2.0 만큼 더해져 있으므로 이를 보정해 준다.
     double realMiddleBottomY = this.middleBottomY - lineGap/2.0;
     // 끝. 보정
     newY = (realMiddleBottomY - y)*this.middleScaleY;
     newY = h/2.0 + (b0 - b1)/2.0 - newY;
     newX = (a0 - x)*this.middleScaleX;
     newX = a0 - newX;
     return new float [] { (float)newX, (float)newY };
  }

  /**
   * 입력된 점을 BottomArcMapping을 하여 결과를 리턴한다.
   * @param point 입력 점
   */
  public float [] getBottomArcMapping(float [] point) {
     double x = point[0], y = point[1];

    double theta = bottomTheta0;

    if( bottomTheta0 > Math.PI && bottomTheta0 < 1.5*Math.PI ) {  // 3/4분면
       theta = bottomTheta0 - (2*Math.PI - (bottomTheta1 - bottomTheta0))*x/(2*a0);
    } else if( bottomTheta0 >= 1.5*Math.PI ) {               // 4/4 분면
       theta = bottomTheta0 - (bottomTheta0 - bottomTheta1)*x/(2*a0);
    } else if( bottomTheta0 <= 0.5*Math.PI ) {
       theta = bottomTheta0 - (2*Math.PI - (bottomTheta1 - bottomTheta0))*x/(2*a0);
    } else {                                          // 1/4, 2/4 분면
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
    // upperBottomY 는 lineGap/2.0 만큼 더해져 있으므로, 이를 보정해 준다.
    double r = r1 + (r0 - r1)*(y - this.downTopY)/(h - this.downTopY);

    double newX = r*cos, newY = r*sin;

    newX += a0; newY += b0;

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
       return this.h + lineGap/2.0;
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
    return bottomY + lineGap/2.0;
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
    // 워드 아트 라인 갯수 설정
    this.lineNum = glyphs.length;
    // 끝. 라인 개수 설정

    // 라인 사이의 간격 설정
    this.lineGap = wa.getFontLeading()*wa.getDimensionScaleY();
    // 라인 사이 간격

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

    // 외곽 타원 폭 설정
    a0 = wa.getGlobalDimension().getWidth()/2.0;
    b0 = wa.getGlobalDimension().getHeight()/2.0;
    // 끝. 외곽 타원 폭 설정

    this.setControledParameters( wa );
  }


  /**
   * 콘트롤 포인트에 따라서 결정 되는 변수들을 설정한다.
   */

  public void setControledParameters(WordArt wa) {
    // 콘트롤 포인트 좌표값 획득
    double x = wa.getControlPoint().getCenterX();
    double y = wa.getControlPoint().getCenterY();
    // 끝. 콘트롤 포인트 좌표값 획득

    // 콘트롤 포인트 좌표값 보정(원점을 이동)
    x -= a0; y -= b0;
    // 끝. 콘트롤 포인트 좌표값 보정

    // 내곽 타원 폭 설정
    // 콘트롤 포인트가 위치에 맞추어 외곽 타원 폭을 스케일 하여 설정한다.
    // 스케일 구하기
    double scale = Math.sqrt( x*x/a0/a0 + y*y/b0/b0);
    // 끝. 스케일 구하기
    a1 = a0*scale; b1 = b0*scale;
    // 끝. 내곽 타원 폭 설정

    // 중단 컴포넌트 스케일 값 설정
    // middleBottomY는 실제 값보다 lineGap/2.0 더해져 있으므로, 이를 보정하여 준다.
    middleComponentHeight = b0 - b1;
    intersectY = middleComponentHeight/2.0;  // 내 타원과 직선의 교차점
    intersectX = a1*Math.sqrt(1.0 - intersectY*intersectY/b1/b1); // 내 타원과 직선의 교차점
    this.middleScaleX = intersectX/a0;
    if( lineNum > 1 ) {
       double realMiddleBottomY = this.middleBottomY - lineGap/2.0;
       this.middleScaleY = middleComponentHeight/(realMiddleBottomY - this.middleTopY);
    }
    // 끝. 스케일 값 설정

    // 시작 및 종료 각도 설정
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
    // 끝. 시작 및 종료 각도 설정
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
   * 본 매핑 클래스를 사용하는 워드 아트의 콘트롤 포인트를 설정하는
   * 함수이다.
   * 워드 아트의 중심축에서만 이동하는 수직 콘트롤 포인트를 설정한다.
   */
  @Override
public void setControlPoint(WordArt wa) {
     wa.setControlPoint( new ControlPointArc(wa, 0.6, 0.52, Math.PI ) );
  }
}