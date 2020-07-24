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
 * ����� SimpleTopArcMappingType, �ߴ��� Ȯ��, �ϴ��� SimpleBottomArcMappingType���� �����ϴ� ����
 * ��Ʈ�� �۸��� ȸ���� �ϴ� ���� Ÿ���� �ݵ�� �� Ŭ������ ���(implements) �Ͽ��� �Ѵ�.
 */

public class SimpleButtonMappingType extends WordArtMappingType implements TripleGlyphRotationMappingType {
	private int upperLineNum = 0, middleLineNum = 1;
	private double a, b; // Ÿ�� ����
	private double theta0, theta1; // ���� ����, ���� ����
	private double arcLength; // Ÿ�� ����
	private double scaleX = 1, scaleY = 1; // �۸� ������ ��

	/**
	 * ������
	 *
	 * @param type
	 *            ���� Ŭ���� ���̵�
	 */
	public SimpleButtonMappingType(int type) {
		super(type);
	}

	/**
	 * ���� ���� Ÿ���� ���� ��ü�� ���Ѵ�.
	 *
	 * @param wa
	 *            ���� ��ü�� ���� ���� ��Ʈ glyph �����̼��� �۸� lineNum ���� �ѹ�
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
	 * ��� ������Ʈ ���� ��ü�� �����Ѵ�.
	 *
	 * @param wa
	 *            �����Ʈ glyph �۸�
	 */

	public AffineTransform getUpperAffineTransformInstance(WordArt wa, Shape glyph) {

		Rectangle2D bounds = glyph.getBounds2D();

		double gw = bounds.getWidth() * scaleX, gh = bounds.getHeight() * scaleY;

		final double gx = bounds.getX() * scaleX, cx = gx + gw / 2.0, // �۸���
		// �߾� ��ǥ
		gy = bounds.getY() * scaleY, cy = gy + gh / 2.0;

		double refTheta = theta0; // ���� ����
		double theta = FunctionUtil.getThetaOfArcLength(a, b, refTheta, cx * arcLength / (2.0 * a * scaleX), true);

		double cos = Math.cos(theta), sin = Math.sin(theta);

		double r = 1.0 / Math.sqrt(cos * cos / a / a + sin * sin / b / b); // Ÿ��
		// ����
		// �Ÿ�

		r = r + r * (b * scaleY - cy) / (2 * b * scaleY);

		double ax = r * cos, ay = r * sin; // Ÿ�� ���� ��ǥ

		if (ay == 0) {
			if (ax < 0) { // ���� �� ����
				theta = -0.5 * Math.PI;
			} else { // ���� �� ���� ��
				theta = 0.5 * Math.PI;
			}
		} else {
			double ascent = -b * b / a / a * ax / ay;
			double ascentAngle = Math.abs(Math.atan(ascent));
			if (ax > 0 && ay > 0) { // 1/4 �и�
				theta = Math.PI - ascentAngle;
			} else if (ax < 0 && ay > 0) { // 2/4 �и�
				theta = Math.PI + ascentAngle;
			} else if (ax < 0 && ay < 0) { // 3/4 �и�
				theta = -ascentAngle;
			} else if (ax > 0 && ay < 0) { // 4/4 �и�
				theta = ascentAngle;
			} else if (ax == 0 && ay > 0) { // ���� �� ��
				theta = Math.PI;
			} else if (ax == 0 && ay < 0) { // ���� �� �Ʒ�
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
	 * �ߴ� ������Ʈ ���� ��ü�� �����Ѵ�.
	 *
	 * @param wa
	 *            �����Ʈ glyph �۸�
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
	 * �ϴ� ������Ʈ ���� ��ü�� �����Ѵ�.
	 *
	 * @param wa
	 *            �����Ʈ glyph �۸�
	 */

	public AffineTransform getDownAffineTransformInstance(WordArt wa, Shape glyph) {
		Rectangle2D bounds = glyph.getBounds2D();

		double gw = bounds.getWidth() * scaleX, gh = bounds.getHeight() * scaleY;

		final double gx = bounds.getX() * scaleX, cx = gx + gw / 2.0, // �۸���
		// �߾� ��ǥ
		gy = bounds.getY() * scaleY, cy = gy + gh / 2.0;

		double refTheta = 2 * Math.PI - theta0; // ���� ����
		if (theta0 == Math.PI) {
			refTheta = Math.PI - 0.00001;
		}
		double theta = FunctionUtil.getThetaOfArcLength(a, b, refTheta, cx * arcLength / (2.0 * a * scaleX), false);

		double cos = Math.cos(theta), sin = Math.sin(theta);

		double r = 1.0 / Math.sqrt(cos * cos / a / a + sin * sin / b / b); // Ÿ��
		// ����
		// �Ÿ�

		r = r + r * (b * scaleY - cy) / (2 * b * scaleY);

		double ax = r * cos, ay = r * sin; // Ÿ�� ���� ��ǥ

		if (ay == 0) {
			if (ax < 0) { // ���� �� �� ��
				theta = -0.5 * Math.PI;
			} else { // ���� �� ���� ��
				theta = 0.5 * Math.PI;
			}
		} else {
			double ascent = -b * b / a / a * ax / ay;
			double ascentAngle = Math.abs(Math.atan(ascent));
			if (ax > 0 && ay > 0) { // 1/4 �и�
				theta = Math.PI - ascentAngle;
			} else if (ax < 0 && ay > 0) { // 2/4 �и�
				theta = Math.PI + ascentAngle;
			} else if (ax < 0 && ay < 0) { // 3/4 �и�
				theta = -ascentAngle;
			} else if (ax > 0 && ay < 0) { // 4/4 �и�
				theta = ascentAngle;
			} else if (ax == 0 && ay > 0) { // ���� �� ��
				theta = Math.PI;
			} else if (ax == 0 && ay < 0) { // ���� �� �Ʒ�
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
	 * ���� ���� ���� ���� �������� �����Ѵ�.
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

		// ���� ����, ���� ���� ����
		ControlPointArc cp = (ControlPointArc) wa.getControlPoint();

		theta0 = cp.getCurrentAngle(); // ���� ����

		theta1 = 0; // counterpart angle (���� ����)

		if (theta0 >= 1.5 * Math.PI) { // 4/4 �и�
			theta1 = Math.PI + (2.0 * Math.PI - theta0);
		} else if (theta0 >= Math.PI) { // 3/4 �и�
			theta1 = 2.0 * Math.PI - (theta0 - Math.PI);
		} else if (theta0 >= 0.5 * Math.PI) { // 2/4 �и�
			theta1 = Math.PI - theta0;
		} else { // 1/4 �и�
			theta1 = Math.PI - theta0;
		}
		// ��. ���� ���� ���� ���� ����

		// Ÿ�� ���� ���ϱ�
		arcLength = FunctionUtil.getArcLength(a, b, theta0, theta1);
		// ��. Ÿ�� ���� ���ϱ�

		scaleX = arcLength / (2.0 * a); // �۸� ������ ��
		scaleY = scaleX * 0.4;
	}

	/**
	 * ��� ���� �ѹ��� �����Ѵ�.
	 */

	public int getUpperLineNumber() {
		return this.upperLineNum;
	}

	/**
	 * �ߴ� ���γѹ��� �����Ѵ�.
	 */

	public int getMiddleLineNumber() {
		return this.middleLineNum;
	}

	/**
	 * �� ���� Ŭ������ ����ϴ� ���� ��Ʈ�� ��Ʈ�� ����Ʈ�� �����Ѵ�.
	 *
	 * @param wa
	 *            ��Ʈ�� ����Ʈ�� ������ �����Ʈ
	 */
	@Override
	public void setControlPoint(WordArt wa) {
		// Ÿ���� ���� �̵��ϴ� �ܼ� ��ũ ��Ʈ�� ����Ʈ�� �����Ѵ�.
		// �ʱ� ������ 180�� �̴�.
		wa.setControlPoint(new ControlPointArc(wa, 1, 1, Math.PI));
	}
}