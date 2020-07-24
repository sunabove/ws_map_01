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
import com.jwordart.util.FunctionUtil;

/**
 * 상단은 SimpleTopArcMappingType, 중단은 확대, 하단은 SimpleBottomArcMappingType으로 매핑하는 워드
 * 아트의 글립을 회전만 하는 매핑 타입은 반드시 본 클래스를 상속(implements) 하여야 한다.
 */

public class SimpleButtonMappingType extends WordArtMappingType implements TripleGlyphRotationMappingType {
	private int upperLineNum = 0, middleLineNum = 1;
	private double a, b; // 타원 변수
	private double theta0, theta1; // 시작 각도, 종료 각도
	private double arcLength; // 타원 길이
	private double scaleX = 1, scaleY = 1; // 글립 스케일 값

	/**
	 * 생성자
	 *
	 * @param type
	 *            매핑 클래스 아이디
	 */
	public SimpleButtonMappingType(int type) {
		super(type);
	}

	/**
	 * 선형 매핑 타입의 변형 객체를 구한다.
	 *
	 * @param wa
	 *            변형 객체를 구할 워드 아트 glyph 로테이션할 글립 lineNum 라인 넘버
	 */
	public AffineTransform getTransformInstance(WordArt wa, Shape glyph, int lineNum) {
		if (lineNum <= upperLineNum) {
			return this.getUpperAffineTransformInstance(wa, glyph);
		} else if (lineNum <= middleLineNum) {
			return this.getMiddleAffineTransformInstance(wa, glyph);
		} else {
			return this.getDownAffineTransformInstance(wa, glyph);
		}
	}

	/**
	 * 상단 컴포넌트 변형 객체를 리턴한다.
	 *
	 * @param wa
	 *            워드아트 glyph 글립
	 */

	public AffineTransform getUpperAffineTransformInstance(WordArt wa, Shape glyph) {

		Rectangle2D bounds = glyph.getBounds2D();

		double gw = bounds.getWidth() * scaleX, gh = bounds.getHeight() * scaleY;

		final double gx = bounds.getX() * scaleX, cx = gx + gw / 2.0, // 글립의
		// 중앙 좌표
		gy = bounds.getY() * scaleY, cy = gy + gh / 2.0;

		double refTheta = theta0; // 시작 각도
		double theta = FunctionUtil.getThetaOfArcLength(a, b, refTheta, cx * arcLength / (2.0 * a * scaleX), true);

		double cos = Math.cos(theta), sin = Math.sin(theta);

		double r = 1.0 / Math.sqrt(cos * cos / a / a + sin * sin / b / b); // 타원
		// 까지
		// 거리

		r = r + r * (b * scaleY - cy) / (2 * b * scaleY);

		double ax = r * cos, ay = r * sin; // 타원 상의 좌표

		if (ay == 0) {
			if (ax < 0) { // 수평 축 왼쪽
				theta = -0.5 * Math.PI;
			} else { // 수평 축 오른 쪽
				theta = 0.5 * Math.PI;
			}
		} else {
			double ascent = -b * b / a / a * ax / ay;
			double ascentAngle = Math.abs(Math.atan(ascent));
			if (ax > 0 && ay > 0) { // 1/4 분면
				theta = Math.PI - ascentAngle;
			} else if (ax < 0 && ay > 0) { // 2/4 분면
				theta = Math.PI + ascentAngle;
			} else if (ax < 0 && ay < 0) { // 3/4 분면
				theta = -ascentAngle;
			} else if (ax > 0 && ay < 0) { // 4/4 분면
				theta = ascentAngle;
			} else if (ax == 0 && ay > 0) { // 수직 축 위
				theta = Math.PI;
			} else if (ax == 0 && ay < 0) { // 수직 축 아래
				theta = 0;
			}
		}

		AffineTransform at = AffineTransform.getTranslateInstance(ax + a, ay + b);
		at.rotate(theta, 0, 0);
		at.translate(-cx, -cy);
		at.scale(scaleX, scaleY);

		return at;
	}

	/**
	 * 중단 컴포넌트 변형 객체를 리턴한다.
	 *
	 * @param wa
	 *            워드아트 glyph 글립
	 */

	public AffineTransform getMiddleAffineTransformInstance(WordArt wa, Shape glyph) {
		Rectangle2D bounds = glyph.getBounds2D();
		double gy = bounds.getY(), gh = bounds.getHeight();

		double newGy = b - gh * scaleY / 2.0;
		AffineTransform at = AffineTransform.getTranslateInstance(0, newGy);
		at.scale(1, scaleY);
		at.translate(0, -gy);

		return at;
	}

	/**
	 * 하단 컴포넌트 변형 객체를 리턴한다.
	 *
	 * @param wa
	 *            워드아트 glyph 글립
	 */

	public AffineTransform getDownAffineTransformInstance(WordArt wa, Shape glyph) {
		Rectangle2D bounds = glyph.getBounds2D();

		double gw = bounds.getWidth() * scaleX, gh = bounds.getHeight() * scaleY;

		final double gx = bounds.getX() * scaleX, cx = gx + gw / 2.0, // 글립의
		// 중앙 좌표
		gy = bounds.getY() * scaleY, cy = gy + gh / 2.0;

		double refTheta = 2 * Math.PI - theta0; // 시작 각도
		if (theta0 == Math.PI) {
			refTheta = Math.PI - 0.00001;
		}
		double theta = FunctionUtil.getThetaOfArcLength(a, b, refTheta, cx * arcLength / (2.0 * a * scaleX), false);

		double cos = Math.cos(theta), sin = Math.sin(theta);

		double r = 1.0 / Math.sqrt(cos * cos / a / a + sin * sin / b / b); // 타원
		// 까지
		// 거리

		r = r + r * (b * scaleY - cy) / (2 * b * scaleY);

		double ax = r * cos, ay = r * sin; // 타원 상의 좌표

		if (ay == 0) {
			if (ax < 0) { // 수평 축 왼 쪽
				theta = -0.5 * Math.PI;
			} else { // 수평 축 오른 쪽
				theta = 0.5 * Math.PI;
			}
		} else {
			double ascent = -b * b / a / a * ax / ay;
			double ascentAngle = Math.abs(Math.atan(ascent));
			if (ax > 0 && ay > 0) { // 1/4 분면
				theta = Math.PI - ascentAngle;
			} else if (ax < 0 && ay > 0) { // 2/4 분면
				theta = Math.PI + ascentAngle;
			} else if (ax < 0 && ay < 0) { // 3/4 분면
				theta = -ascentAngle;
			} else if (ax > 0 && ay < 0) { // 4/4 분면
				theta = ascentAngle;
			} else if (ax == 0 && ay > 0) { // 수직 축 위
				theta = Math.PI;
			} else if (ax == 0 && ay < 0) { // 수직 축 아래
				theta = 0;
			}
			theta += Math.PI;
		}

		AffineTransform at = AffineTransform.getTranslateInstance(ax + a, ay + b);
		at.rotate(theta, 0, 0);
		at.translate(-cx, -cy);
		at.scale(scaleX, scaleY);

		return at;
	}

	/**
	 * 매핑 전에 각종 매핑 변수들은 설정한다.
	 */
	public void setParamenters(WordArt wa) {
		Shape[][] glyph = wa.getGlyphs();
		int lineNum = glyph.length;
		if (lineNum <= 3) {
			upperLineNum = 0;
			middleLineNum = 1;
		} else {
			upperLineNum = lineNum / 3;
			upperLineNum += (lineNum - upperLineNum * 3);
			upperLineNum -= 1;
			middleLineNum = upperLineNum + lineNum / 3;
		}

		// x*x/a/a + y*y/b/b = 1
		a = wa.getGlobalDimension().getWidth() / 2.0;
		b = wa.getGlobalDimension().getHeight() / 2.0;

		// 시작 각도, 종료 각도 설정
		ControlPointArc cp = (ControlPointArc) wa.getControlPoint();

		theta0 = cp.getCurrentAngle(); // 시작 각도

		theta1 = 0; // counterpart angle (종료 각도)

		if (theta0 >= 1.5 * Math.PI) { // 4/4 분면
			theta1 = Math.PI + (2.0 * Math.PI - theta0);
		} else if (theta0 >= Math.PI) { // 3/4 분면
			theta1 = 2.0 * Math.PI - (theta0 - Math.PI);
		} else if (theta0 >= 0.5 * Math.PI) { // 2/4 분면
			theta1 = Math.PI - theta0;
		} else { // 1/4 분면
			theta1 = Math.PI - theta0;
		}
		// 끝. 시작 각도 종료 각도 설정

		// 타원 길이 구하기
		arcLength = FunctionUtil.getArcLength(a, b, theta0, theta1);
		// 끝. 타원 길이 구하기

		scaleX = arcLength / (2.0 * a); // 글립 스케일 값
		scaleY = scaleX * 0.4;
	}

	/**
	 * 상단 라인 넘버를 리턴한다.
	 */

	public int getUpperLineNumber() {
		return this.upperLineNum;
	}

	/**
	 * 중단 라인넘버를 리턴한다.
	 */

	public int getMiddleLineNumber() {
		return this.middleLineNum;
	}

	/**
	 * 본 매핑 클래스를 사용하는 워드 아트의 콘트롤 포인트를 설정한다.
	 *
	 * @param wa
	 *            콘트롤 포인트를 적용할 워드아트
	 */
	@Override
	public void setControlPoint(WordArt wa) {
		// 타원을 따라 이동하는 단순 아크 콘트롤 포인트를 지정한다.
		// 초기 각도는 180도 이다.
		wa.setControlPoint(new ControlPointArc(wa, 1, 1, Math.PI));
	}
}