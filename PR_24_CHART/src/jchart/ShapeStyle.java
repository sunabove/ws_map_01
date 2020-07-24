/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      JCOSMOS DEVELOPMENT<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package jchart;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import jcosmos.*;
import jchartui.*;

/**
 * 워드 아트의 속성을 나타내는 클래스이다.
 */
public class ShapeStyle {

	private static final double PI = Math.PI;

	private int type = -1; // Unpredefined word art sytle.

	public static Font defaultFont = FontManager.getDefaultFont(); // 폰트
	private Font font = defaultFont; // 폰트

	// 선의 색과 굵기
	private FillEffect lineTexture = null; // 선 무늬
	private double lineWidth = 1; // 선 굵기
	private Color lineColor = Color.black; // 선 색깔
	private BasicStroke lineStroke = new BasicStroke((int) (lineWidth + 0.5)); // 선
																				// 스트로크

	// 채우기
	private Color fillColor = Color.black; // 채우기 칼라
	private FillEffect fillEffect = null; // 채우기 효과

	private Rectangle2D dimension; // 전체 영역 (위치 및 크기)
	private Point2D location = new Point2D.Float(); // 위치
	private double rotationAngle; // 회전 각도, 단위는 라디안(Radian) 이다.
	private boolean vertical = false; // 수직 문자열 설정

	// 워드 아트 자체에서는 쓰이지 않는 멤버 데이터들
	private String horizontalLocationReference; // 가로 위치 기준
	private String verticalLocationReference; // 세로 위치 기준
	private boolean moveWithString; // 문자열과 함께 개체 이동
	private boolean framLocationFixed; // 틀 위치 고정
	// 끝. 워드 아트 자체에서는 쓰이지 않은 멤버들

	private boolean hasShade = false; // 그림자가 있는 지 여부를 나타낸다.

	/**
	 * 생성자
	 */
	public ShapeStyle() {
	}

	/**
	 * 생성자
	 */
	public ShapeStyle(Color fillColor, Color lineColor) {
		this.fillColor = fillColor;
		this.lineColor = lineColor;
	}

	/**
	 * default shape style
	 */

	public static ShapeStyle getDefaultShapeStyle() {
		return new ShapeStyle(Color.white, Color.black);
	}

	public void paint3DShapess(Graphics2D g2, Shape[][] shapes3D) {

		for (int i = 0; i < 6; i++) {

			Shape[] shapesPlane = shapes3D[i];

			if (shapesPlane == null) {

				continue;

			}

			for (int k = 0, len = shapesPlane.length; k < len; k++) {

				paint(g2, shapesPlane[k]);

			}

		}

	}

	public void paint3DShapess(Graphics2D g2, Shape[][] shapes3D,
			ShapeStyle bottomPlaneShapeStyle) {

		for (int i = 0; i < 5; i++) {

			Shape[] shapesPlane = shapes3D[i];

			if (shapesPlane == null) {

				continue;

			}

			for (int k = 0, len = shapesPlane.length; k < len; k++) {

				paint(g2, shapesPlane[k]);

			}

		}

		Shape[] bottomShapesPlane = shapes3D[5];

		if (bottomShapesPlane != null) {

			for (int i = 0, len = bottomShapesPlane.length; i < len; i++) {

				bottomPlaneShapeStyle.paint(g2, bottomShapesPlane[i]);

			}

		}

	}

	/**
	 * drawing filled Shape
	 */

	private void fillShape(Graphics2D g2, Shape shape) {

		Color fillColor = getFillColor();

		if (fillColor != null) {

			g2.setColor(fillColor);

			g2.fill(shape);

		} else {

			Paint gp = getGradientPaint(); // Gradient Paint

			if (gp != null) {

				g2.setPaint(gp);

				g2.fill(shape);

			} else {

				Paint tp = getTexturePaint(); // Texture Paint

				if (tp != null) {

					g2.setPaint(tp);

					g2.fill(shape);

				}

			}

		}

	}

	/**
	 * drawing Shape
	 */

	public void drawShape(Graphics2D g2, Shape shape) {

		Color lineColor = getLineColor();

		BasicStroke ls = getLineStroke(); // line stroke

		if (lineColor != null) {

			g2.setColor(lineColor);

			if (ls != null) {

				g2.setStroke(ls);

			}

		} else {

			FillEffect lfe = getLineTexture(); // line fill effect

			if (lfe != null) {

				if (ls != null) {

					g2.setStroke(ls);

				}

				Paint ltp = lfe.getTexturePaint(); // line texture Paint

				if (ltp != null) {

					g2.setPaint(ltp);

				}

			}

		}

		g2.draw(shape);

	}

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
	public FillEffect getFillEffect() {
		return this.fillEffect;
	}

	/**
	 * 선 무늬를 지정한다.
	 * 
	 * @param fe
	 *            선 무늬 속성
	 */
	public void setLineTexture(FillEffect fe) {
		this.lineTexture = fe;
		this.lineColor = null;
	}

	/**
	 * 선 무늬를 리턴한다.
	 */
	public FillEffect getLineTexture() {
		return this.lineTexture;
	}

	/**
	 * 워드 아트의 채우기 효과를 지정한다.
	 * 
	 * @param fe
	 *            워드 아트 채우기 효과
	 */
	public void setFillEffect(FillEffect fe) {
		this.fillEffect = fe;
		this.fillColor = null;
		if (this.fillEffect != null && this.dimension != null) {
			this.fillEffect.setBounds(this.dimension.getWidth(), this.dimension
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
		Utility.debug(this, "Font : family = " + name + ", style = italic"
				+ ", size = " + size);
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
		Utility.debug(this, "New Line Width = " + lw);
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
		this.fillEffect = null;
	}

	/**
	 * 워드 아트 채우기 효과의 GradientPaint를 리턴한다.
	 */
	public Paint getGradientPaint() {
		if (this.fillEffect == null) {
			return null;
		} else {
			if (this.isVertical()) {
				int type = this.fillEffect.getType();
				if (type == FillEffect.HORIZONTAL) {
					this.fillEffect.setType(FillEffect.VERTICAL);
				} else if (type == FillEffect.VERTICAL) {
					this.fillEffect.setType(FillEffect.HORIZONTAL);
				}
				Paint value = this.fillEffect.getGradientPaint();
				if (type == FillEffect.HORIZONTAL) {
					this.fillEffect.setType(type);
				} else if (type == FillEffect.VERTICAL) {
					this.fillEffect.setType(type);
				}
				return value;
			} else {
				return this.fillEffect.getGradientPaint();
			}
		}
	}

	/**
	 * 워드 아트 채우기 효과의 TexturePaint를 리턴한다.
	 */
	public TexturePaint getTexturePaint() {
		if (this.fillEffect == null) {
			return null;
		} else {
			return this.fillEffect.getTexturePaint();
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
		this.lineTexture = null;
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
	public ShapeStyle create() {

		boolean vertical = this.isVertical();

		ShapeStyle style = new ShapeStyle();

		style.setFont(this.font);
		this.setVertical(false); // 잠시 수평으로 설정

		style.setDimension(this.getDimensionWidth(), this.getDimensionHeight());
		style.setLocation(this.getLocationX(), this.getLocationY());
		style.setRotaionRadianAngle(this.getRotationRadianAngle());
		style.setVertical(vertical);

		this.setVertical(vertical); // 수직 모드 정상으로 환원

		style.fillColor = this.fillColor;
		style.lineColor = this.lineColor;
		style.lineStroke = this.lineStroke;
		style.lineWidth = this.lineWidth;

		if (this.lineTexture != null) {
			style.lineTexture = this.lineTexture.create();
		} else {
			style.lineTexture = null;
		}
		if (this.fillEffect != null) {
			style.fillEffect = this.fillEffect.create();
		} else {
			style.fillEffect = null;
		}

		return style;
	}

	/**
	 * 워드 아트 스타일의 정보를 알아내기 위한 함수이다.
	 */
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
		if (this.getLineTexture() != null) {
			this.getLineTexture().setBounds(dim);
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
	 * 워드 아트 스타일의 분류 아이디를 리턴한다.
	 */
	public int getTypeCode() {
		return this.type;
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
		this.setRotaionRadianAngle(Unit.convertDegreeToRadian(degree));
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
		return Unit.convertRadianToDegree(getRotationRadianAngle());
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
		this.lineStroke = stroke;
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
	 * 선 형태를 리턴한다.
	 */
	public BasicStroke getLineStroke() {

		BasicStroke value;
		BasicStroke stroke = this.lineStroke;
		float[] refDash = stroke.getDashArray();
		float[] newDash;
		float lw = (float) (this.lineWidth);

		if (refDash == null) {
			newDash = new float[] { 10, 0 };
		} else {
			Utility.debug(this, "lw = " + lw);
			newDash = new float[refDash.length];
			for (int i = 0; i < refDash.length; i++) {
				newDash[i] = refDash[i] * lw;
			}
		}
		value = new BasicStroke(lw, stroke.getEndCap(), stroke.getLineJoin(),
				stroke.getMiterLimit(), newDash, stroke.getDashPhase());
		return value;

	}

	public boolean hasShade() {

		return hasShade;

	}

	public void setShade(boolean hasShade) {

		this.hasShade = hasShade;

	}

	/**
	 * drawing Shape
	 */

	public void paint(Graphics2D g2, Shape shape) {

		if (hasShade()) {

			paintShade(g2, shape);

		}

		fillShape(g2, shape);

		drawShape(g2, shape);

	}

	public void paintShade(Graphics2D g2, Shape shape) {

		Rectangle2D bounds = shape.getBounds2D();

		g2.setColor(Color.black);

		double x = bounds.getX();
		double y = bounds.getY();
		double w = bounds.getWidth();
		double h = bounds.getHeight();

		double shadeWidth = 5;

		g2.fill(new Rectangle2D.Double(x + shadeWidth, y + h, w - shadeWidth,
				shadeWidth));

		g2.fill(new Rectangle2D.Double(x + w, y + shadeWidth, shadeWidth, h));

	}

	public void paintShapeArray(final Graphics2D g2, final Shape[] shapes) {

		Shape shape;

		for (int i = 0, len = shapes.length; i < len; i++) {

			shape = shapes[i];

			if (shape == null) {

				continue;

			}

			paint(g2, shape);

		}

	}

}