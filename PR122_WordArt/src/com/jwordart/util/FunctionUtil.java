
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
 * ���� Ư���� ���� �Լ��� ������ Ŭ�����̴�.
 */

public class FunctionUtil {

  /**
   * Ÿ�� (x*x/a/a + y*y/b/b = 1)�� Ÿ�� ���̸� ���ϴ� �Լ��̴�.
   * @param a Ÿ���� ��
   *        b Ÿ���� ����
   *        theta0 ���� ����
   *        theta1 ���� ����
   */
  public static double getArcLength(final double a, final double b,
                                    double theta0, double theta1) {
      if( theta0 > theta1 ) {
         // ���� ������ ���� ���� ���� ������
         // ���� ������ 360 ��ŭ ���Ѵ�.
         // �׷��� �Ͽ�, ���� ������ ���� ���� ���� ��ġ������ ũ�� �Ѵ�.
         theta1 += 2*Math.PI;
      }

      final double dTheta = Math.PI/50.0; // ���а��� 180/100 ���� �Ѵ�.

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
   * Ÿ�� (x*x/a/a + y*y/b/b = 1)�� Ÿ�� ���̸� �����ϴ� ������ ���ϴ� �Լ��̴�.
   * getArcLength( .... )�� ���Լ� �̴�.
   *
   * @param a Ÿ���� ��
   *        b Ÿ���� ����
   *        theta ���� ����
   *        arcLength ��ũ ����
   *        increased ���ϴ� ������ ���⼺(��/��) ����
   */
  public static double getThetaOfArcLength(final double a, final double b, final double theta,
                                           final double arcLength, final boolean increased) {
      final double sign = increased ? 1 : -1; // ������ ���� ���� ����

      final double dTheta = sign*Math.PI/50.0; // ���а��� 180/100 ���� �Ѵ�.

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