
/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.util;

/**
 * 각종 특별한 수학 함수를 구현한 클래스이다.
 */

public class FunctionUtil {

  /**
   * 타원 (x*x/a/a + y*y/b/b = 1)의 타원 길이를 구하는 함수이다.
   * @param a 타원의 폭
   *        b 타원의 높이
   *        theta0 시작 각도
   *        theta1 종료 각도
   */
  public static double getArcLength(final double a, final double b,
                                    double theta0, double theta1) {
      if( theta0 > theta1 ) {
         // 종료 각도가 시작 각도 보다 작으면
         // 종료 각도를 360 만큼 더한다.
         // 그렇게 하여, 종료 각도가 시작 각도 보다 수치적으로 크게 한다.
         theta1 += 2*Math.PI;
      }

      final double dTheta = Math.PI/50.0; // 증분각을 180/100 도로 한다.

      double length = 0;

      final double AA = a*a, BB = b*b;

      double cos = Math.cos(theta0), sin = Math.sin(theta0),
             r = 1.0/Math.sqrt(cos*cos/AA + sin*sin/BB); // cosine, sine, radius

      double x0 = r*cos, y0 = r*sin, x1, y1;

      while( theta0 < theta1 ) {
          theta0 += dTheta;
          cos = Math.cos(theta0);
          sin = Math.sin(theta0);
          r = 1.0/Math.sqrt(cos*cos/AA + sin*sin/BB);
          x1 = r*cos;
          y1 = r*sin;
          length += Math.sqrt((x1 - x0)*(x1 - x0) + (y1 - y0)*(y1 - y0));
          x0 = x1; y0 = y1;
      }

      return length;
  }

  /**
   * 타원 (x*x/a/a + y*y/b/b = 1)의 타원 길이를 만족하는 각도를 구하는 함수이다.
   * getArcLength( .... )의 역함수 이다.
   *
   * @param a 타원의 폭
   *        b 타원의 높이
   *        theta 시작 각도
   *        arcLength 아크 길이
   *        increased 구하는 각도의 방향성(증/감) 여부
   */
  public static double getThetaOfArcLength(final double a, final double b, final double theta,
                                           final double arcLength, final boolean increased) {
      final double sign = increased ? 1 : -1; // 각도의 증감 여부 설정

      final double dTheta = sign*Math.PI/50.0; // 증분각을 180/100 도로 한다.

      double length = 0;

      final double AA = a*a, BB = b*b;

      double theta0 = theta;

      double cos = Math.cos(theta0), sin = Math.sin(theta0),
             r = 1.0/Math.sqrt(cos*cos/AA + sin*sin/BB); // cosine, sine, radius

      double x0 = r*cos, y0 = r*sin, x1, y1;

      while( length < arcLength ) {
          theta0 += dTheta;
          cos = Math.cos(theta0);
          sin = Math.sin(theta0);
          r = 1.0/Math.sqrt(cos*cos/AA + sin*sin/BB);
          x1 = r*cos;
          y1 = r*sin;
          length += Math.sqrt((x1 - x0)*(x1 - x0) + (y1 - y0)*(y1 - y0));
          x0 = x1; y0 = y1;
      }

      return theta0;
  }

  public static void main(String args [] ) {
     double a = 1, b = 1;
     double length = FunctionUtil.getArcLength(a, b, 0, Math.PI/2.0);
  }
}