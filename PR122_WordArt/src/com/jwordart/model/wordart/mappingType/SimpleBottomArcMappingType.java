
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
 * ���� ��Ʈ�� ������ �ݱ��̸� a, b�� �ϴ� Ÿ��(x*x/a/a + y*y/b/b = 1 )
 * �� �������� �۸��� ȸ���ϴ� ���� Ÿ���̴�.
 * ������ �۸��� ȸ�� ������ �޶�����.
 */
public class SimpleBottomArcMappingType extends WordArtMappingType
       implements GlyphRotationMappingType {
    /**
     * ������
     * @param type ���� Ÿ�� Ŭ���� ���̵�
     *             �� ���ο����� �߿��� �ǹ̴� ����.
     */
    public SimpleBottomArcMappingType(int type) {
      super(type);
    }

    /**
     * �� �۸��� ȸ����ų ���� ��ü�� ���Ѵ�.
     * @parma wa ������ ���� ��Ʈ
     *        glyph ������ ���� ��Ʈ�� �۸�
     */
    public AffineTransform getTransformInstance(WordArt wa, Shape glyph) {

       final double a = wa.getGlobalDimension().getWidth()/2.0,
                    b = wa.getGlobalDimension().getHeight()/2.0;

       ControlPointArc cp = (ControlPointArc) wa.getControlPoint();

       double theta = cp.getCurrentAngle();  // ���� ����

       double theta1 = 0; // counterpart angle (���� ����)

       if( theta >= 1.5*Math.PI ) {  // 4/4 �и�
           theta1 = Math.PI + ( 2.0*Math.PI - theta );
       } else if( theta >= Math.PI ) {  // 3/4 �и�
           theta1 = 2.0*Math.PI - (theta - Math.PI);
       } else if( theta >= 0.5*Math.PI ) { // 2/4 �и�
           theta1 = Math.PI - theta;
       } else {   // 1/4 �и�
           theta1 = Math.PI - theta;
       }

       final double arcLength = FunctionUtil.getArcLength(a, b, theta, theta1);

       Rectangle2D bounds = glyph.getBounds2D();

       final double scaleX = arcLength/(2.0*a), // �۸� ������ ��
             scaleY = scaleX*0.4;

       double gw = bounds.getWidth()*scaleX, gh = bounds.getHeight()*scaleY;

       final double gx = bounds.getX()*scaleX, cx = gx + gw/2.0, // �۸��� �߾� ��ǥ
               gy = bounds.getY()*scaleY, cy = gy + gh/2.0;

       theta = FunctionUtil.getThetaOfArcLength(a, b, theta1, cx*arcLength/(2.0*a*scaleX), false);

       double cos = Math.cos(theta), sin = Math.sin(theta);

       double r = 1.0/Math.sqrt(cos*cos/a/a + sin*sin/b/b); // Ÿ�� ���� �Ÿ�

       r = r + r*(b*scaleY - cy)/(2*b*scaleY);

       double ax = r*cos, ay = r*sin; // Ÿ�� ���� ��ǥ

       if( ay == 0 ) {
          if( ax < 0 ) {   // ���� �� �� ��
             theta = 0.5*Math.PI;
          } else {        // ���� �� ���� ��
             theta = - 0.5*Math.PI;
          }
       } else {
          double ascent = - b*b/a/a*ax/ay;
          double ascentAngle = Math.abs(Math.atan( ascent ));
          if( ax > 0 && ay > 0 ) {  // 1/4 �и�
             theta = Math.PI - ascentAngle;
          } else if( ax < 0 && ay > 0 ) {  // 2/4 �и�
             theta = Math.PI + ascentAngle;
          } else if( ax < 0 && ay < 0 ) { //3/4 �и�
             theta = - ascentAngle;
          } else if( ax > 0 && ay < 0 ) {  // 4/4 �и�
             theta = ascentAngle;
          } else if( ax == 0 && ay > 0 ) { // ���� �� ��
             theta = Math.PI;
          } else if( ax == 0 && ay < 0 ) { // ���� �� �Ʒ�
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
     * �� ���� Ŭ������ ����ϴ� ���� ��Ʈ�� ��Ʈ�� ����Ʈ�� �����Ѵ�.
     * @param wa ��Ʈ�� ����Ʈ�� ������ �����Ʈ
     */
   @Override
public void setControlPoint(WordArt wa){
      // Ÿ���� ���� �̵��ϴ� �ܼ� ��ũ ��Ʈ�� ����Ʈ�� �����Ѵ�.
      // �ʱ� ������ 180�� �̴�.
      wa.setControlPoint( new ControlPointArc(wa, 1, 1, 0) );
   }
}