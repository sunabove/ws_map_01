package com.ynhenc.gis.model.style;

import java.awt.*;

import com.jwordart.model.fillEffect.GraphicEffect;
import com.jwordart.util.WordArtUtil;
import com.ynhenc.comm.GisComLib;

/**
 * ���� ��Ʈ�� �Ӽ��� ��Ÿ���� Ŭ�����̴�.
 */
public class ShapeEffect extends GisComLib {

	/**
	 * ä��� ȿ���� �����Ѵ�.
	 */
	public GraphicEffect getGraphicEffect_Fill() {
		return this.graphicEffect_Fill;
	}

	/**
	 * ���� ��Ʈ�� ä��� ȿ���� �����Ѵ�.
	 *
	 * @param graphicEffect
	 *            ���� ��Ʈ ä��� ȿ��
	 */
	public void setGraphicEffect_Fill(GraphicEffect graphicEffect_Fill) {
		this.graphicEffect_Fill = graphicEffect_Fill;
		this.fillColor = null;
	}

	/**
	 * �� ���̸� �����Ѵ�.
	 */
	public GraphicEffect getGraphicEffect_Line() {
		return this.graphicEffect_Line;
	}

	/**
	 * �� ���̸� �����Ѵ�.
	 *
	 * @param graphicEffect
	 *            �� ���� �Ӽ�
	 */
	public void setGraphicEffect_Line(GraphicEffect graphicEffect_Line) {
		this.graphicEffect_Line = graphicEffect_Line;
		this.lineColor = null;
	}

	/**
	 * ���� ���⸦ �����Ѵ�.
	 *
	 * @param lineWidth
	 *            ���� ����
	 */
	public void setLineWidth(double lineWidth) {

		this.debug("New Line Width = " + lineWidth);

		if (lineWidth < 0) {
			this.lineWidth = 0;
		} else {
			this.lineWidth = lineWidth;
		}

	}

	/**
	 * ���� ��Ʈ�� ���� ���⸦ �˾Ƴ���.
	 */
	public double getLineWidth() {
		return this.lineWidth;
	}

	/**
	 * ���� ��Ʈ�� ä��� ������ �����Ѵ�.
	 *
	 * @param c
	 *            ä��� ����
	 */
	public void setFillColor(Color c) {
		this.fillColor = c;
		this.graphicEffect_Fill = null;
	}

	/**
	 * ���� ��Ʈ�� ä��� ������ �����Ѵ�.
	 */
	public Color getFillColor() {
		return this.fillColor;
	}

	/**
	 * ���� ��Ʈ�� �� ������ �����Ѵ�.
	 *
	 * @param c
	 *            �� ����
	 */
	public void setLineColor(Color c) {
		this.lineColor = c;
		this.graphicEffect_Line = null;
	}

	/**
	 * ���� ��Ʈ�� �� ������ �����Ѵ�.
	 */
	public Color getLineColor() {
		return this.lineColor;
	}

	/**
	 * �� ��ü�� Ŭ��(Clone) �޼ҵ� �̴�.
	 */
	public ShapeEffect create() {

		ShapeEffect style = new ShapeEffect();

		style.fillColor = this.fillColor;
		style.lineColor = this.lineColor;
		style.lineStroke = this.lineStroke;
		style.lineWidth = this.lineWidth;

		if (this.graphicEffect_Line != null) {
			style.graphicEffect_Line = this.graphicEffect_Line.create();
		} else {
			style.graphicEffect_Line = null;
		}
		if (this.graphicEffect_Fill != null) {
			style.graphicEffect_Fill = this.graphicEffect_Fill.create();
		} else {
			style.graphicEffect_Fill = null;
		}

		return style;
	}

	/**
	 * ���� ��Ʈ ��Ÿ���� ������ �˾Ƴ��� ���� �Լ��̴�.
	 */
	@Override
	public String toString() {
		String value = "lw = " + this.getLineWidth();
		if (this.fillColor != null) {
			value += ", Fill Color = (" + fillColor.getRed() + ", " + fillColor.getGreen() + ", " + fillColor.getBlue() + ")";
		}
		return value;
	}

	/**
	 * ���� ��Ʈ�� �� ���¸� �����Ѵ�.
	 */
	public void setLineStroke(BasicStroke lineStroke) {
		this.lineStroke = lineStroke;
	}

	/**
	 * �� ���¸� �����Ѵ�.
	 */
	public BasicStroke getLineStroke() {
		/** TODO ���ν�Ʈ��ũ ��ȯ ����ȭ �ʿ�.
		 */

		BasicStroke stroke = this.lineStroke;

		float lineWidth = (float) this.lineWidth;

		float[] dashList = stroke.getDashArray();

		float[] newDashList;

		if (dashList == null) {
			newDashList = new float[] { 10, 0 };
		} else {
			newDashList = new float[dashList.length];
			for (int i = 0; i < dashList.length; i++) {
				if (false) {
					newDashList[i] = dashList[i] * lineWidth / 2.0F;
				} else if (false) {
					newDashList[i] = dashList[i] * lineWidth;
				} else {
					newDashList[i] = dashList[i];
				}
			}
		}

		return new BasicStroke(lineWidth, stroke.getEndCap(), stroke.getLineJoin(), stroke.getMiterLimit(), newDashList, stroke
				.getDashPhase());
	}

	public BasicStroke getOrgLineStroke() {
		return this.lineStroke;
	}

	/**
	 * ������
	 */
	public ShapeEffect() {
		this.lineStroke = new BasicStroke( (int) (lineWidth + 0.5), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND );
	}

	// ���� ���� ����
	private GraphicEffect graphicEffect_Line = null; // �� ����
	private double lineWidth = 1; // �� ����
	private Color lineColor = Color.black; // �� ����
	private BasicStroke lineStroke ; // ��
	// ��Ʈ��ũ

	// ä���
	private Color fillColor = Color.black; // ä��� Į��
	private GraphicEffect graphicEffect_Fill = null; // ä��� ȿ��

}