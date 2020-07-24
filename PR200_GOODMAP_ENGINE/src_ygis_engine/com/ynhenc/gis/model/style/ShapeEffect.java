package com.ynhenc.gis.model.style;

import java.awt.*;

import com.jwordart.model.fillEffect.GraphicEffect;
import com.jwordart.util.WordArtUtil;
import com.ynhenc.comm.GisComLib;

/**
 * 워드 아트의 속성을 나타내는 클래스이다.
 */
public class ShapeEffect extends GisComLib {

	/**
	 * 채우기 효과를 리턴한다.
	 */
	public GraphicEffect getGraphicEffect_Fill() {
		return this.graphicEffect_Fill;
	}

	/**
	 * 워드 아트의 채우기 효과를 지정한다.
	 *
	 * @param graphicEffect
	 *            워드 아트 채우기 효과
	 */
	public void setGraphicEffect_Fill(GraphicEffect graphicEffect_Fill) {
		this.graphicEffect_Fill = graphicEffect_Fill;
		this.fillColor = null;
	}

	/**
	 * 선 무늬를 리턴한다.
	 */
	public GraphicEffect getGraphicEffect_Line() {
		return this.graphicEffect_Line;
	}

	/**
	 * 선 무늬를 지정한다.
	 *
	 * @param graphicEffect
	 *            선 무늬 속성
	 */
	public void setGraphicEffect_Line(GraphicEffect graphicEffect_Line) {
		this.graphicEffect_Line = graphicEffect_Line;
		this.lineColor = null;
	}

	/**
	 * 선의 굵기를 설정한다.
	 *
	 * @param lineWidth
	 *            선의 굵기
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
	 * 워드 아트의 선의 굵기를 알아낸다.
	 */
	public double getLineWidth() {
		return this.lineWidth;
	}

	/**
	 * 워드 아트의 채우기 색상을 설정한다.
	 *
	 * @param c
	 *            채우기 색상
	 */
	public void setFillColor(Color c) {
		this.fillColor = c;
		this.graphicEffect_Fill = null;
	}

	/**
	 * 워드 아트의 채우기 색상을 리턴한다.
	 */
	public Color getFillColor() {
		return this.fillColor;
	}

	/**
	 * 워드 아트의 선 색상을 설정한다.
	 *
	 * @param c
	 *            선 색상
	 */
	public void setLineColor(Color c) {
		this.lineColor = c;
		this.graphicEffect_Line = null;
	}

	/**
	 * 워드 아트의 선 색상을 리턴한다.
	 */
	public Color getLineColor() {
		return this.lineColor;
	}

	/**
	 * 본 객체의 클론(Clone) 메소드 이다.
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
	 * 워드 아트 스타일의 정보를 알아내기 위한 함수이다.
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
	 * 워드 아트의 선 형태를 설정한다.
	 */
	public void setLineStroke(BasicStroke lineStroke) {
		this.lineStroke = lineStroke;
	}

	/**
	 * 선 형태를 리턴한다.
	 */
	public BasicStroke getLineStroke() {
		/** TODO 라인스트로크 반환 최적화 필요.
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
	 * 생성자
	 */
	public ShapeEffect() {
		this.lineStroke = new BasicStroke( (int) (lineWidth + 0.5), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND );
	}

	// 선의 색과 굵기
	private GraphicEffect graphicEffect_Line = null; // 선 무늬
	private double lineWidth = 1; // 선 굵기
	private Color lineColor = Color.black; // 선 색깔
	private BasicStroke lineStroke ; // 선
	// 스트로크

	// 채우기
	private Color fillColor = Color.black; // 채우기 칼라
	private GraphicEffect graphicEffect_Fill = null; // 채우기 효과

}