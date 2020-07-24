/**
 * Title:        First JAVA 2D<p>
 * Description:  First JAVA 2D  Drawing<p>
 * Copyright:    Copyright (c) sbmoon<p> 
 * @author sbmoon
 * @version 1.0
 */

package com.jwordart.model.wordart;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

import com.jwordart.model.wordart.mappingType.NonLinearMappingType;

public class GeneralCurve {
	Shape shape; // 기준 도형
	int windingRule = GeneralPath.WIND_EVEN_ODD; // 와인딩 룰
	// float [] closeToPoint; // 클로즈 지점
	float[] moveToPoint; // 현 지점
	boolean closePath = false; // 담힘 여부 판별
	Vector points = new Vector();

	public GeneralCurve(Shape s) {
		this.shape = s;
		this.processGeneralPath();
	}

	// 자바2의 도형을 점으로 이루어진 도형으로
	// 변환하는 함수이다.
	public void processGeneralPath() {
		Shape s = this.shape;

		// 도형의 패스를 분석하여,
		// 이동(move_to), 선(line_to), 이차(quad_to), 삼차(cubic_to)의 패스인지를 분류한다.
		// 각 패스의 구성점들을 구한다.
		// 각 패스의 구성점들을 벡터에 추가한다.
		// 각 패스의 구성점들을 다시 제너럴 패스로 변환한다.

		// 도형 패스 분석
		PathIterator pi = s.getPathIterator(null);

		this.windingRule = pi.getWindingRule();

		// 패스 구성점 벡터
		Vector points = this.points;

		// 패스의 구성점들을 구한다.
		try {
			while (!pi.isDone()) {
				getPoints(pi, points);
				pi.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 끝. 패스의 구성점 구하기
	}

	public float[][][] getGeneralPoints() {
		float[][][] genPoints = new float[points.size()][][]; // 제너럴 포인트들

		Enumeration enumIt = points.elements();

		int i = 0;
		while (enumIt.hasMoreElements()) {
			genPoints[i++] = (float[][]) enumIt.nextElement();
		}

		return genPoints;
	}

	public Shape getShape(NonLinearMappingType mt) {
		float[][][] genPoints = this.getGeneralPoints();
		float[][][] mappedGenPoints = new float[genPoints.length][][];
		for (int i = 0; i < genPoints.length; i++) {
			mappedGenPoints[i] = new float[genPoints[i].length][];
			for (int j = 0; j < genPoints[i].length; j++) {
				mappedGenPoints[i][j] = mt.f(genPoints[i][j]);
			}
		}
		return this.getShape(mappedGenPoints);
	}

	public Shape getShape() {
		return this.getShape(this.getGeneralPoints());
	}

	public Shape getShape(float[][][] genPoints) {
		GeneralPath path = new GeneralPath(this.windingRule);

		// 패스 구성점들을 패스로 변환한다.

		for (int i = 0; i < genPoints.length; i++) {
			float[][] coords = genPoints[i];
			if (coords == null) {
				continue;
			}
			float[] point;
			for (int j = 0; j < coords.length; j++) {
				point = coords[j];
				if (coords.length == 1) {
					path.moveTo(point[0], point[1]);
					continue;
				}
				path.lineTo(point[0], point[1]);
			}
		}

		if (closePath) {
			path.closePath();
		}

		// 끝. 도형 패스 분석
		return path;
	}

	// 패스의 구성점들을 구한다.
	public void getPoints(PathIterator pi, Vector<float[][]> points)
			throws Exception {

		float[] coords = new float[6];

		// 현 패스 타입
		int type = pi.currentSegment(coords);

		switch (type) {
		// 이동의 경우
		case PathIterator.SEG_MOVETO:
			this.moveToPoint = new float[2];
			this.moveToPoint[0] = coords[0];
			this.moveToPoint[1] = coords[1];
			/*
			 * if( this.closeToPoint == null ) { this.closeToPoint = coords; }
			 */
			points.addElement(new float[][] { this.moveToPoint });

			break;
		// 라인의 경우
		case PathIterator.SEG_LINETO:
			points.addElement(this.getLinePoints(coords));
			break;
		// 이차 커브의 경우
		case PathIterator.SEG_QUADTO:
			points.addElement(this.getQuadraticPoints(coords));
			break;
		// 삼차 커버의 경우
		case PathIterator.SEG_CUBICTO:
			points.addElement(this.getCubicPoints(coords));
			break;
		// 닫을 경우
		case PathIterator.SEG_CLOSE:
			points.addElement(this.getLinePoints(this.moveToPoint));
			closePath = true;
			break;
		}
	}

	// 라인 구성점들을 구한다
	public float[][] getLinePoints(float[] coords) {

		float[][] points = null;
		float[] p0 = this.moveToPoint;
		float[] p1 = { coords[0], coords[1] };

		Vector pointList = new Vector();

		getLinePoint(p0, p1, pointList);

		points = new float[pointList.size()][];

		Enumeration enumIt = pointList.elements();

		int i = 0;

		while (enumIt.hasMoreElements()) {
			points[i++] = (float[]) enumIt.nextElement();
		}

		this.moveToPoint = p1;

		return points;
	}

	public void getLinePoint(float[] p0, float[] p1, Vector<float[]> pointList) {
		float[] mid = { (p0[0] + p1[0]) / 2.0F, (p0[1] + p1[1]) / 2.0F };

		pointList.addElement(p0);
		if ((Math.abs(p0[0] - p1[0]) < 1) && (Math.abs(p0[1] - p1[1]) < 1)) {
		} else {
			getLinePoint(p0, mid, pointList);
			getLinePoint(mid, p1, pointList);
		}
		pointList.addElement(p1);
	}

	// 이차 곡선의 구성점들을 구한다
	public float[][] getQuadraticPoints(float[] coords) {

		float[][] points = null;

		float[] p0 = this.moveToPoint;
		float[] p1 = { coords[0], coords[1] };
		float[] p2 = { coords[2], coords[3] };

		Vector pointList = new Vector();

		this.getQuadraticPoint(p0, p1, p2, pointList);

		points = new float[pointList.size()][];

		Enumeration enumIt = pointList.elements();

		int i = 0;

		while (enumIt.hasMoreElements()) {
			points[i++] = (float[]) enumIt.nextElement();
		}

		this.moveToPoint = p2;

		return points;

	}

	public void getQuadraticPoint(float[] p0, float[] p1, float[] p2,
			Vector<float[]> pointList) {

		float[] a = { (p0[0] + p1[0]) / 2.0F, (p0[1] + p1[1]) / 2.0F };
		float[] b = { (p1[0] + p2[0]) / 2.0F, (p1[1] + p2[1]) / 2.0F };
		float[] c = { (a[0] + b[0]) / 2.0F, (a[1] + b[1]) / 2.0F };

		pointList.addElement(p0);
		if ((Math.abs(c[0] - p0[0]) < 1 && Math.abs(c[1] - p0[1]) < 1)
				&& (Math.abs(c[0] - p1[0]) < 1 && Math.abs(c[1] - p1[1]) < 1)) {
			pointList.addElement(c);
		} else {
			this.getQuadraticPoint(p0, a, c, pointList);
			this.getQuadraticPoint(c, b, p2, pointList);
		}
		pointList.addElement(p2);
	}

	// 삼차 곡선의 구성점들을 구한다
	public float[][] getCubicPoints(float[] coords) {
		float[][] points = null;

		float[] p0 = this.moveToPoint;
		float[] p1 = { coords[0], coords[1] };
		float[] p2 = { coords[2], coords[3] };
		float[] p3 = { coords[4], coords[5] };

		Vector pointList = new Vector();

		getCubicPoint(p0, p1, p2, p3, pointList);

		Enumeration enumIt = pointList.elements();

		points = new float[pointList.size()][];

		int i = 0;

		while (enumIt.hasMoreElements()) {
			points[i++] = (float[]) enumIt.nextElement();
		}

		this.moveToPoint = p3;

		return points;
	}

	public void getCubicPoint(float[] p0, float[] p1, float[] p2, float[] p3,
			Vector<float[]> pointList) {
		float[] a = { (p0[0] + p1[0]) / 2, (p0[1] + p1[1]) / 2 };
		float[] b = { (p2[0] + p3[0]) / 2, (p2[1] + p3[1]) / 2 };
		float[] c = { (p1[0] + p2[0]) / 2, (p1[1] + p2[1]) / 2 };
		float[] a1 = { (a[0] + c[0]) / 2, (a[1] + c[1]) / 2 };
		float[] b1 = { (c[0] + b[0]) / 2, (c[1] + b[1]) / 2 };
		float[] c1 = { (a1[0] + b1[0]) / 2, (a1[1] + b1[1]) / 2 };

		pointList.addElement(p0);
		if (((Math.abs(c1[0] - p0[0]) < 1) && (Math.abs(c1[1] - p0[1]) < 1))
				|| ((Math.abs(c1[0] - p3[0]) < 1) && (Math.abs(c1[1] - p3[1]) < 1))) {
			pointList.addElement(c1);
			// pointList.addElement( p3 );
		} else {
			getCubicPoint(p0, a, a1, c1, pointList);
			getCubicPoint(c1, b1, b, p3, pointList);
		}
		pointList.addElement(p3);
	}
}
