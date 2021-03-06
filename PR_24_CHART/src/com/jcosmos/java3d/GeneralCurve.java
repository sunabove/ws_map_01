
/**
 * Title:        First JAVA 2D<p>
 * Description:  First JAVA 2D  Drawing<p>
 * Copyright:    Copyright (c) sbmoon<p>
 * Company:      jcosmos development<p>
 * @author sbmoon
 * @version 1.0
 */

package com.jcosmos.java3d;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import jchart.*;

public class GeneralCurve {

  private Shape shape; // 기준 도형

  private int windingRule = GeneralPath.WIND_EVEN_ODD; // 와인딩 룰

  private float [] moveToPoint; // 현 지점

  private float [] closingPoint; // 클로징 할 지점

  private boolean closePath = false; // 담힘 여부 판별

  private LinkedList allPointList = new LinkedList();

  public GeneralCurve(final Shape shape) {

     this.shape = shape;

     processGeneralPath();

  }

  public float [][] getPointsFloat() {

    final Object [] allPointsArray = this.allPointList.toArray();

    final int len = allPointsArray.length;

    final float [][] pointsFloat = new float[ len ] [] ;

    for(int i = 0; i < len; i ++ ) {

      pointsFloat[ i ] = (float []) allPointsArray[ i ];

    }

    return pointsFloat;

  }

  public Point2D [] getPoint2Ds() {

    final float [][] pointsFloat = getPointsFloat();

    final int len = pointsFloat.length;

    final Point2D point2Ds [] = new Point2D[ len ];

    for(int i = 0; i < len; i ++ ) {

      final float [] pointFloat = pointsFloat[ i ];

      point2Ds[ i ] = new Point2D.Float( pointFloat[ 0 ], pointFloat[ 1 ] );

    }

    return point2Ds;

  }

  public Point2D [] getPoint2DsCounterClockWise() {

    return ShapeUtilities.getPointsCounterClockWise( getPoint2Ds() );

  }

  public Point2D [] getPoint2DsClockWise() {

    return ShapeUtilities.getPointsClockWise( getPoint2Ds() );

  }

  public GeneralPath getGeneralPath() {

    return ShapeUtilities.createGeneralPath( getPoint2Ds() );

  }

  // 자바2의 도형을 점으로 이루어진 도형으로
  // 변환하는 함수이다.

  public void processGeneralPath() {

     final Shape shape = this.shape;

     // 도형의 패스를 분석하여,
     // 이동(move_to), 선(line_to), 이차(quad_to), 삼차(cubic_to)의 패스인지를 분류한다.
     // 각 패스의 구성점들을 구한다.
     // 각 패스의 구성점들을 벡터에 추가한다.
     // 각 패스의 구성점들을 다시 제너럴 패스로 변환한다.

     // 도형 패스 분석
     final PathIterator pi = shape.getPathIterator( null );

     this.windingRule = pi.getWindingRule();

     // 패스 구성점 벡터
     final LinkedList allPointList = this.allPointList;

     // 패스의 구성점들을 구한다.

     try {

	while( ! pi.isDone() ) {

	   getPoints( pi, allPointList );

	   pi.next();

	}

     } catch (Exception e) {

	e.printStackTrace();

     }

     // 끝. 패스의 구성점 구하기

     // 시작점과 끝 점이 같으면 끝 점을 리스트에서 삭제한다.

     final float [] first = (float []) allPointList.getFirst();

     final float [] last  = (float []) allPointList.getLast();

     if( first[ 0 ] == last[ 0 ] && first[ 1 ] == last[ 1 ] ) {

	allPointList.remove( last );

     }

     // 끝. 점과 시작점 같은면 끝 점 삭제 하기

  }

  private void addPoints(final Vector pointList) {

    final LinkedList allPointList = this.allPointList;

    float [] lastPoint = (float []) allPointList.getLast();

    final Enumeration enumIt = pointList.elements();

    while( enumIt.hasMoreElements() ) {

      final float [] currPoint = (float []) enumIt.nextElement();

      if( ( currPoint[ 0 ] == lastPoint[ 0 ] ) && ( currPoint[ 1 ] == lastPoint[ 1 ] ) ) {

	continue;

      }

      allPointList.addLast( currPoint );

      lastPoint = currPoint;

    }

  }

  // 패스의 구성점들을 구한다.

  public void getPoints(final PathIterator pi, final LinkedList pointList) throws Exception {

     final float [] coords = new float[6];

     // 현재 패스 타입

     final int type = pi.currentSegment( coords );

     switch(type) {

	// 이동의 경우
	case PathIterator.SEG_MOVETO:

	    this.moveToPoint = new float[2];

	    this.moveToPoint[0] = coords[0];
	    this.moveToPoint[1] = coords[1];

	    this.closingPoint = new float[2];

	    this.closingPoint[0] = coords[0];
	    this.closingPoint[1] = coords[1];

	    pointList.addLast( this.moveToPoint );

	    break;

	// 라인의 경우
	case PathIterator.SEG_LINETO:

	    addPoints( this.getLinePoints( coords ) );

	    break;

	// 이차 커브의 경우
	case PathIterator.SEG_QUADTO:

	    addPoints( this.getQuadraticPoints( coords ) );

	    break;

	// 삼차 커버의 경우
	case PathIterator.SEG_CUBICTO:

	    addPoints( this.getCubicPoints( coords ) );

	    break;

	// 닫을 경우
	case PathIterator.SEG_CLOSE:

	    addPoints( this.getLinePoints( this.closingPoint ) );

	    closePath = true;

	    break;

     }

  }

  // 라인 구성점들을 구한다

  public Vector getLinePoints(final float [] coords) {

       final float [] p1 = {coords[0], coords[1] };

       final Vector pointList = new Vector();

       pointList.add( this.moveToPoint );

       this.moveToPoint = p1;

       return pointList;

  }

  public void getLinePoint(float [] p0, float [] p1, Vector pointList ) {

       float [] mid = { (p0[0] + p1[0])/2.0F, (p0[1] + p1[1])/2.0F };

       pointList.addElement( p0 );

       if( (Math.abs(p0[0] - p1[0]) < 1) && (Math.abs(p0[1] - p1[1]) < 1) ) {
       } else {

	  getLinePoint(p0, mid, pointList);
	  getLinePoint(mid, p1, pointList);

       }

       pointList.addElement( p1 );

  }

  // 이차 곡선의 구성점들을 구한다
  public Vector getQuadraticPoints(float [] coords) {

       float [] p0 = this.moveToPoint;

       float [] p1 = { coords[0], coords[1] };
       float [] p2 = { coords[2], coords[3] };

       Vector pointList = new Vector();

       this.getQuadraticPoint( p0, p1, p2, pointList);

       this.moveToPoint = p2;

       return pointList;

  }

  public void getQuadraticPoint(float [] p0, float [] p1, float [] p2, Vector pointList ) {

       float [] a = { (p0[0] + p1[0])/2.0F, (p0[1] + p1[1])/2.0F };
       float [] b = { (p1[0] + p2[0])/2.0F, (p1[1] + p2[1])/2.0F };
       float [] c = { (a[0] + b[0])/2.0F, (a[1] + b[1])/2.0F };

       pointList.addElement( p0 );

       if( (Math.abs( c[0] - p0[0]) < 1 && Math.abs( c[1] - p0[1]) < 1 )
	   && (Math.abs( c[0] - p1[0]) < 1 && Math.abs( c[1] - p1[1]) < 1 ) ) {

	   pointList.addElement( c );

       } else {

	   this.getQuadraticPoint( p0, a, c, pointList);
	   this.getQuadraticPoint( c, b, p2, pointList);

       }

       pointList.addElement( p2 );

  }

  // 삼차 곡선의 구성점들을 구한다
  public Vector getCubicPoints(float [] coords) {

       float [] p0 = this.moveToPoint;
       float [] p1 = {coords[0], coords[1]};
       float [] p2 = {coords[2], coords[3]};
       float [] p3 = {coords[4], coords[5]};

       Vector pointList = new Vector();

       getCubicPoint( p0, p1, p2, p3, pointList);

       this.moveToPoint = p3;

       return pointList;

  }

  public void getCubicPoint(float [] p0, float [] p1, float [] p2, float [] p3, Vector pointList ) {

      float [] a = { (p0[0] + p1[0])/2, (p0[1] + p1[1])/2 };
      float [] b = { (p2[0] + p3[0])/2, (p2[1] + p3[1])/2 };
      float [] c = { (p1[0] + p2[0])/2, (p1[1] + p2[1])/2 };
      float [] a1 = { (a[0] + c[0])/2, (a[1] + c[1])/2 };
      float [] b1 = { (c[0] + b[0])/2, (c[1] + b[1])/2 };
      float [] c1 = { (a1[0] + b1[0])/2, (a1[1] + b1[1])/2 };

      pointList.addElement( p0 );

      if( ( (Math.abs( c1[0] - p0[0] ) < 1) && (Math.abs( c1[1] - p0[1]) < 1 ) )
	  ||  ( (Math.abs( c1[0] - p3[0] ) < 1) && (Math.abs( c1[1] - p3[1]) < 1 ) ) ) {

	  pointList.addElement( c1 );

      } else {

	  getCubicPoint( p0, a, a1, c1, pointList );

	  getCubicPoint( c1, b1, b, p3, pointList );

      }

      pointList.addElement( p3 );

  }

}
