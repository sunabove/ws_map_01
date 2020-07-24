/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.model.wordart;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

import com.jwordart.model.fillEffect.GraphicEffect;
import com.jwordart.ui.comp.WordArtFillEffectEditor;
import com.jwordart.ui.resource.Resource_WordArt;
import com.jwordart.util.FontManager_WordArt;
import com.jwordart.util.UnitUtil_WordArt;
import com.jwordart.util.WordArtUtil;

/**
 * 워드 아트의 속성을 나타내는 클래스이다.
 */
public class WordArtStyle {

	/**
	 * 워드 아트의 수직 여부 판별 함수
	 */
	public boolean isVertical() {
		return this.vertical;
	}

	/**
	 * 워드 아트의 수직 여부 설정
	 *
	 * @param b
	 *            워드 아트 수직 속성
	 */
	public void setVertical(boolean b) {
		this.vertical = b;
	}

	/**
	 * 채우기 효과를 리턴한다.
	 */
	public GraphicEffect getFillEffect() {
		return this.graphicEffect;
	}

	/**
	 * 선 무늬를 지정한다.
	 *
	 * @param fe
	 *            선 무늬 속성
	 */
	public void setFillEffect_LineTexture(GraphicEffect fe) {
		this.fillEffect_LineTexture = fe;
		this.lineColor = null;
	}

	/**
	 * 선 무늬를 리턴한다.
	 */
	public GraphicEffect getFillEffect_LineTexture() {
		return this.fillEffect_LineTexture;
	}

	/**
	 * 워드 아트의 채우기 효과를 지정한다.
	 *
	 * @param fe
	 *            워드 아트 채우기 효과
	 */
	public void setFillEffect(GraphicEffect fe) {
		this.graphicEffect = fe;
		this.fillColor = null;
		if (this.graphicEffect != null && this.dimension != null) {
			this.graphicEffect.setBounds(this.dimension.getWidth(), this.dimension
					.getHeight());
		}
	}

	/**
	 * 워드 아트의 폰트를 리턴한다.
	 */
	public Font getFont() {
		return this.font;
	}

	/**
	 * 워드 아트의 폰트를 설정한다.
	 *
	 * @param font
	 *            워드 아트 폰트
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * 워드 아트의 폰트를 이탤릭하게 설정한다.
	 */
	public void setFontItalic() {
		Font font = this.font;
		String name = font.getName();
		int size = font.getSize();

		font = new Font(name, Font.ITALIC, size);
		this.setFont(font);
	}

	/**
	 * 워드 아트의 선의 굵기를 설정한다.
	 *
	 * @param lw
	 *            선의 굵기
	 */
	public void setLineWidth(double lw) {
		if (lw < 0) {
			lw = 0;
		}
		this.lineWidth = lw;
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
		this.graphicEffect = null;
	}

	/**
	 * 워드 아트 채우기 효과의 GradientPaint를 리턴한다.
	 */
	public Paint getGradientPaint() {
		if (this.graphicEffect == null) {
			return null;
		} else {
			if (this.isVertical()) {
				int type = this.graphicEffect.getType();
				if (type == GraphicEffect.HORIZONTAL) {
					this.graphicEffect.setType(GraphicEffect.VERTICAL);
				} else if (type == GraphicEffect.VERTICAL) {
					this.graphicEffect.setType(GraphicEffect.HORIZONTAL);
				}
				Paint value = this.graphicEffect.getGradientPaint();
				if (type == GraphicEffect.HORIZONTAL) {
					this.graphicEffect.setType(type);
				} else if (type == GraphicEffect.VERTICAL) {
					this.graphicEffect.setType(type);
				}
				return value;
			} else {
				return this.graphicEffect.getGradientPaint();
			}
		}
	}

	/**
	 * 워드 아트 채우기 효과의 TexturePaint를 리턴한다.
	 */
	public TexturePaint getTexturePaint() {
		if (this.graphicEffect == null) {
			return null;
		} else {
			return this.graphicEffect.getTexturePaint();
		}
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
		/*
		 * if( c != null ) { this.lineTexture = null; }
		 */
		this.fillEffect_LineTexture = null;
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
	public WordArtStyle create() {

		boolean vertical = this.isVertical();

		WordArtStyle style = new WordArtStyle();

		style.setFont(this.font);
		style.setShade(this.wordArtShade);
		this.setVertical(false); // 잠시 수평으로 설정

		style.setDimension(this.getDimensionWidth(), this.getDimensionHeight());
		style.setLocation(this.getLocationX(), this.getLocationY());
		style.setRotaionRadianAngle(this.getRotationRadianAngle());
		style.setVertical(vertical);

		this.setVertical(vertical); // 수직 모드 정상으로 환원

		style.fillColor = this.fillColor;
		style.lineColor = this.lineColor;
		style.strokeLine = this.strokeLine;
		style.lineWidth = this.lineWidth;

		if (this.fillEffect_LineTexture != null) {
			style.fillEffect_LineTexture = this.fillEffect_LineTexture.create();
		} else {
			style.fillEffect_LineTexture = null;
		}
		if (this.graphicEffect != null) {
			style.graphicEffect = this.graphicEffect.create();
		} else {
			style.graphicEffect = null;
		}

		return style;
	}

	/**
	 * 워드 아트 스타일의 정보를 알아내기 위한 함수이다.
	 */
	@Override
	public String toString() {
		String value = "x = " + this.getLocationX() + ", y = "
				+ this.getLocationY() + ", w = " + this.getDimensionWidth()
				+ ", h = " + this.getDimensionHeight() + ", angle = "
				+ this.getRotationDegreeAngle() + ", lw = "
				+ this.getLineWidth();
		if (this.fillColor != null) {
			value += ", Fill Color = (" + fillColor.getRed() + ", "
					+ fillColor.getGreen() + ", " + fillColor.getBlue() + ")";
		}
		return value;
	}

	/**
	 * 워드 아트의 영역을 리턴한다.
	 */
	public Rectangle2D getDimension() {
		return this.dimension;
	}

	/**
	 * 워드 아트의 영역을 설정한다.
	 *
	 * @param dim
	 *            워드 아트 영역
	 */
	public void setDimension(Rectangle2D dim) {
		this.dimension = dim;
		if (this.getFillEffect() != null) {
			this.getFillEffect().setBounds(dim);
		}
		if (this.getFillEffect_LineTexture() != null) {
			this.getFillEffect_LineTexture().setBounds(dim);
		}
	}

	/**
	 * 워드 아트의 영역을 설정한다.
	 *
	 * @param w
	 *            폭 h 높이
	 */
	public void setDimension(double w, double h) {
		// 폭과 높이가 0 보다 크거나 같게 항상 할당한다.
		if (w < 0) {
			w = 0;
		}
		if (h < 0) {
			h = 0;
		}

		if (this.dimension == null) {
			this.dimension = new Rectangle2D.Double(0, 0, w, h);
		} else {
			this.dimension.setRect(0, 0, w, h);
		}
	}

	/**
	 * 워드 아트의 위치를 설정한다.
	 *
	 * @param x
	 *            세로 위치 y 가로 위치
	 */
	public void setLocation(double x, double y) {
		if (this.location == null) {
			this.location = new Point2D.Double(x, y);
		}
		if (this.isVertical()) {
			double w = this.getDimensionWidth(), // 역으로 설정
			h = this.getDimensionHeight(); // 역으로 설정
			double lx = this.location.getX(), ly = this.location.getY();
			double diff = (w - h) / 2.0;
			x = lx - diff;
			y = ly + diff;
		}
		this.location.setLocation(x, y);
	}

	/**
	 * 워드 아트의 가로 위치를 설정한다.
	 *
	 * @param x
	 *            가로 위치
	 */
	public void setLocationX(double x) {
		if (this.location == null) {
			this.location = new Point2D.Double(x, 0);
		}
		this.setLocation(x, this.location.getY());
	}

	/**
	 * 워드 아트의 세로 위치를 설정한다.
	 *
	 * @param y
	 *            세로 위치
	 */
	public void setLocationY(double y) {
		if (this.location == null) {
			this.location = new Point2D.Double(0, y);
		}
		this.setLocation(this.location.getX(), y);
	}

	/**
	 * 워드 아트의 높이를 확대한다.
	 *
	 * @param dh
	 *            높이 확대 값
	 */
	public void increaseDimensionHeight(double dh) {
		this.setDimension(this.getDimensionWidth(), this.getDimensionHeight()
				+ dh);
	}

	/**
	 * 워드 아트의 폭을 확대한다.
	 *
	 * @param dw
	 *            폭 확대 값
	 */
	public void increaseDimensionWidth(double dw) {
		this.setDimension(this.getDimensionWidth() + dw, this
				.getDimensionHeight());
	}

	/**
	 * 워드 아트의 높이를 리턴한다.
	 */
	public double getDimensionHeight() {
		if (this.dimension == null) {
			return 0;
		} else {
			return this.dimension.getHeight();
		}
	}

	/**
	 * 워드 아트의 폭을 리턴한다.
	 */
	public double getDimensionWidth() {
		if (this.dimension == null) {
			return 0;
		} else {
			return this.dimension.getWidth();
		}
	}

	/**
	 * 워드 아트의 위치를 리턴한다.
	 */
	public Point2D getLocation() {
		if (this.isVertical()) {
			double w = this.getDimensionWidth(), h = this.getDimensionHeight();
			double x = this.location.getX(), y = this.location.getY();
			double diff = (w - h) / 2.0;
			return new Point2D.Double(x - diff, y + diff);
		} else {
			return this.location;
		}
		// return this.location;
	}

	/**
	 * 워드 아트의 위치를 설정한다.
	 */
	public void setLocation(Point2D loc) {
		this.setLocation(loc.getX(), loc.getY());
	}

	/**
	 * 워드 아트의 가로 위치를 리턴한다.
	 */
	public double getLocationX() {
		return this.getLocation().getX();
	}

	/**
	 * 워드 아트의 세로 위치를 리턴한다.
	 */
	public double getLocationY() {
		return this.getLocation().getY();
	}

	/**
	 * 워드 아트의 회전 각도를 설정한다.
	 *
	 * @param radian
	 *            회전 각도(라디안)
	 */
	public void setRotaionRadianAngle(double radian) {
		if (this.isVertical()) {
			this.rotationAngle = radian - Math.PI / 2.0;
		} else {
			this.rotationAngle = radian;
		}
	}

	/**
	 * 워드 아트의 회전 각도를 설정한다.
	 *
	 * @param degree
	 *            회전 각도(분,초)
	 */
	public void setRotationDegreeAngle(double degree) {
		this.setRotaionRadianAngle(UnitUtil_WordArt.convertDegreeToRadian(degree));
	}

	/**
	 * 워드 아트의 회전 각도를 리턴한다.
	 */
	public double getRotationRadianAngle() {
		if (this.isVertical()) {
			return this.rotationAngle + Math.PI / 2.0;
		} else {
			return this.rotationAngle;
		}
	}

	/**
	 * 워드 아트의 회전 각도를 리턴한다.
	 */
	public double getRotationDegreeAngle() {
		return UnitUtil_WordArt.convertRadianToDegree(this.getRotationRadianAngle());
	}

	/**
	 * 워드 아트의 가로 위치를 이동한다.
	 *
	 * @param dx
	 *            이동 폭
	 */
	public void increaseLocationX(double dx) {
		this.setLocation(this.getLocationX() + dx, this.getLocationY());
	}

	/**
	 * 워드 아트의 세로 위치를 이동한다.
	 *
	 * @param dy
	 *            이동 폭
	 */
	public void increaseLocationY(double dy) {
		this.setLocation(this.getLocationX(), this.getLocationY() + dy);
	}

	/**
	 * 워드 아트의 선 형태를 설정한다.
	 */
	public void setLineStroke(BasicStroke stroke) {
		this.strokeLine = stroke;
	}

	/**
	 * 워드 아트를 이동한다.
	 *
	 * @param dx
	 *            가로 이동 폭 dy 세로 이동 폭
	 */
	public void moveBy(double dx, double dy) {
		double x = this.location.getX();
		double y = this.location.getY();
		this.location.setLocation(x + dx, y + dy);
	}

	/**
	 * 그림자 콘트롤러를 리턴한다.
	 */
	public WordArtShade getShade() {
		return this.wordArtShade;
	}

	/**
	 * 그림자 콘트롤러를 설정한다.
	 *
	 * @param wordArtShade
	 *            그림자 콘트롤러
	 */
	public void setShade(WordArtShade wordArtShade) {
		this.wordArtShade = wordArtShade;
	}

	/**
	 * 선 형태를 리턴한다.
	 */
	public BasicStroke getLineStroke() {
		BasicStroke value;
		BasicStroke stroke = this.strokeLine;
		float[] refDash = stroke.getDashArray();
		float[] newDash;
		float lw = (float) (this.lineWidth);

		if (refDash == null) {
			newDash = new float[] { 10, 0 };
		} else {
			newDash = new float[refDash.length];
			for (int i = 0; i < refDash.length; i++) {
				newDash[i] = refDash[i] * lw;
			}
		}
		value = new BasicStroke(lw, stroke.getEndCap(), stroke.getLineJoin(),
				stroke.getMiterLimit(), newDash, stroke.getDashPhase());
		return value;
	}

	/**
	 * 단순 그림자 속성을 지정한다.
	 *
	 * @param shadeColor
	 *            그림자 칼라
	 */
	public void setSimpleShade(Color shadeColor) {
		this.wordArtShade = new WordArtSimpleShade(4, 4, shadeColor);
	}

	/**
	 * 단순 그림자 속성을 지원한다.
	 *
	 * @param transX
	 *            수평 이동 값 transY 수직 이동 값 shadeColor 그림자 칼락
	 */
	public void setSimpleShade(int transX, int transY, Color shadeColor) {
		this.wordArtShade = new WordArtSimpleShade(transX, transY, shadeColor);
	}

	/**
	 * 생성자
	 */
	public WordArtStyle() {
	}

	// 선의 색과 굵기
	private GraphicEffect fillEffect_LineTexture = null; // 선 무늬
	private double lineWidth = 1; // 선 굵기
	private Color lineColor = Color.black; // 선 색깔
	private BasicStroke strokeLine = new BasicStroke((int) (lineWidth + 0.5)); // 선 스트로크

	// 채우기
	private Color fillColor = Color.black; // 채우기 칼라
	private GraphicEffect graphicEffect = null; // 채우기 효과

	// 위치, 크기, 각도
	private Rectangle2D dimension; // 전체 영역 (위치 및 크기)
	private Point2D location = new Point2D.Float(); // 위치
	private double rotationAngle; // 회전 각도, 단위는 라디안(Radian) 이다.

	// 폰트 , 수직여부
	private Font font = FontManager_WordArt.getDefaultFont() ; // 폰트
	private boolean vertical = false; // 수직 문자열 설정

	private WordArtShade wordArtShade; // 그림자 콘트롤러 클래스

}