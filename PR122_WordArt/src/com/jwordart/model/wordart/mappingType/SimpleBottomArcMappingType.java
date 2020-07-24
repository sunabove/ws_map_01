
/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.model.wordart.mappingType;

import java.awt.*;
import java.awt.geom.*;

import com.jwordart.model.wordart.ControlPointArc;
import com.jwordart.model.wordart.WordArt;
import com.jwordart.util.FunctionUtil;


/**
 * 워드 아트의 반폭과 반길이를 a, b로 하는 타원(x*x/a/a + y*y/b/b = 1 )
 * 을 기준으로 글립을 회전하는 매핑 타입이다.
 * 각각의 글립의 회전 각도만 달라진다.
 */
public class SimpleBottomArcMappingType extends WordArtMappingType
       implements GlyphRotationMappingType {
    /**
     * 생성자
     * @param type 매핑 타입 클래스 아이디
     *             현 매핑에서는 중요한 의미는 없다.
     */
    public SimpleBottomArcMappingType(int type) {
      super(type);
    }

    /**
     * 각 글립을 회전시킬 변형 객체를 구한다.
     * @parma wa 변형할 워드 아트
     *        glyph 변형할 워드 아트의 글립
     */
    public AffineTransform getTransformInstance(WordArt wa, Shape glyph) {

       final double a = wa.getGlobalDimension().getWidth()/2.0,
                    b = wa.getGlobalDimension().getHeight()/2.0;

       ControlPointArc cp = (ControlPointArc) wa.getControlPoint();

       double theta = cp.getCurrentAngle();  // 시작 각도

       double theta1 = 0; // counterpart angle (종료 각도)

       if( theta >= 1.5*Math.PI ) {  // 4/4 분면
           theta1 = Math.PI + ( 2.0*Math.PI - theta );
       } else if( theta >= Math.PI ) {  // 3/4 분면
           theta1 = 2.0*Math.PI - (theta - Math.PI);
       } else if( theta >= 0.5*Math.PI ) { // 2/4 분면
           theta1 = Math.PI - theta;
       } else {   // 1/4 분면
           theta1 = Math.PI - theta;
       }

       final double arcLength = FunctionUtil.getArcLength(a, b, theta, theta1);

       Rectangle2D bounds = glyph.getBounds2D();

       final double scaleX = arcLength/(2.0*a), // 글립 스케일 값
             scaleY = scaleX*0.4;

       double gw = bounds.getWidth()*scaleX, gh = bounds.getHeight()*scaleY;

       final double gx = bounds.getX()*scaleX, cx = gx + gw/2.0, // 글립의 중앙 좌표
               gy = bounds.getY()*scaleY, cy = gy + gh/2.0;

       theta = FunctionUtil.getThetaOfArcLength(a, b, theta1, cx*arcLength/(2.0*a*scaleX), false);

       double cos = Math.cos(theta), sin = Math.sin(theta);

       double r = 1.0/Math.sqrt(cos*cos/a/a + sin*sin/b/b); // 타원 까지 거리

       r = r + r*(b*scaleY - cy)/(2*b*scaleY);

       double ax = r*cos, ay = r*sin; // 타원 상의 좌표

       if( ay == 0 ) {
          if( ax < 0 ) {   // 수평 축 왼 쪽
             theta = 0.5*Math.PI;
          } else {        // 수평 축 오른 쪽
             theta = - 0.5*Math.PI;
          }
       } else {
          double ascent = - b*b/a/a*ax/ay;
          double ascentAngle = Math.abs(Math.atan( ascent ));
          if( ax > 0 && ay > 0 ) {  // 1/4 분면
             theta = Math.PI - ascentAngle;
          } else if( ax < 0 && ay > 0 ) {  // 2/4 분면
             theta = Math.PI + ascentAngle;
          } else if( ax < 0 && ay < 0 ) { //3/4 분면
             theta = - ascentAngle;
          } else if( ax > 0 && ay < 0 ) {  // 4/4 분면
             theta = ascentAngle;
          } else if( ax == 0 && ay > 0 ) { // 수직 축 위
             theta = Math.PI;
          } else if( ax == 0 && ay < 0 ) { // 수직 축 아래
             theta = 0;
          }
          theta += Math.PI;
       }

       AffineTransform at = AffineTransform.getTranslateInstance( ax + a, ay + b);
       at.rotate( theta, 0, 0 );
       at.translate( - cx, - cy );
       at.scale( scaleX, scaleY);

       return at;
    }


    /**
     * 본 매핑 클래스를 사용하는 워드 아트의 콘트롤 포인트를 설정한다.
     * @param wa 콘트롤 포인트를 적용할 워드아트
     */
   @Override
public void setControlPoint(WordArt wa){
      // 타원을 따라 이동하는 단순 아크 콘트롤 포인트를 지정한다.
      // 초기 각도는 180도 이다.
      wa.setControlPoint( new ControlPointArc(wa, 1, 1, 0) );
   }
}