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
 * ���� ��Ʈ�� �Ӽ��� ��Ÿ���� Ŭ�����̴�.
 */
public class ShapeStyle {

	private static final double PI = Math.PI;

	private int type = -1; // Unpredefined word art sytle.

	public static Font defaultFont = FontManager.getDefaultFont(); // ��Ʈ
	private Font font = defaultFont; // ��Ʈ

	// ���� ���� ����
	private FillEffect lineTexture = null; // �� ����
	private double lineWidth = 1; // �� ����
	private Color lineColor = Color.black; // �� ����
	private BasicStroke lineStroke = new BasicStroke((int) (lineWidth + 0.5)); // ��
																				// ��Ʈ��ũ

	// ä���
	private Color fillColor = Color.black; // ä��� Į��
	private FillEffect fillEffect = null; // ä��� ȿ��

	private Rectangle2D dimension; // ��ü ���� (��ġ �� ũ��)
	private Point2D location = new Point2D.Float(); // ��ġ
	private double rotationAngle; // ȸ�� ����, ������ ����(Radian) �̴�.
	private boolean vertical = false; // ���� ���ڿ� ����

	// ���� ��Ʈ ��ü������ ������ �ʴ� ��� �����͵�
	private String horizontalLocationReference; // ���� ��ġ ����
	private String verticalLocationReference; // ���� ��ġ ����
	private boolean moveWithString; // ���ڿ��� �Բ� ��ü �̵�
	private boolean framLocationFixed; // Ʋ ��ġ ����
	// ��. ���� ��Ʈ ��ü������ ������ ���� �����

	private boolean hasShade = false; // �׸��ڰ� �ִ� �� ���θ� ��Ÿ����.

	/**
	 * ������
	 */
	public ShapeStyle() {
	}

	/**
	 * ������
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
	 * ���� ��Ʈ�� ���� ���� �Ǻ� �Լ�
	 */
	public boolean isVertical() {
		return this.vertical;
	}

	/**
	 * ���� ��Ʈ�� ���� ���� ����
	 * 
	 * @param b
	 *            ���� ��Ʈ ���� �Ӽ�
	 */
	public void setVertical(boolean b) {
		this.vertical = b;
	}

	/**
	 * ä��� ȿ���� �����Ѵ�.
	 */
	public FillEffect getFillEffect() {
		return this.fillEffect;
	}

	/**
	 * �� ���̸� �����Ѵ�.
	 * 
	 * @param fe
	 *            �� ���� �Ӽ�
	 */
	public void setLineTexture(FillEffect fe) {
		this.lineTexture = fe;
		this.lineColor = null;
	}

	/**
	 * �� ���̸� �����Ѵ�.
	 */
	public FillEffect getLineTexture() {
		return this.lineTexture;
	}

	/**
	 * ���� ��Ʈ�� ä��� ȿ���� �����Ѵ�.
	 * 
	 * @param fe
	 *            ���� ��Ʈ ä��� ȿ��
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
	 * ���� ��Ʈ�� ��Ʈ�� �����Ѵ�.
	 */
	public Font getFont() {
		return this.font;
	}

	/**
	 * ���� ��Ʈ�� ��Ʈ�� �����Ѵ�.
	 * 
	 * @param font
	 *            ���� ��Ʈ ��Ʈ
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * ���� ��Ʈ�� ��Ʈ�� ���Ÿ��ϰ� �����Ѵ�.
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
	 * ���� ��Ʈ�� ���� ���⸦ �����Ѵ�.
	 * 
	 * @param lw
	 *            ���� ����
	 */
	public void setLineWidth(double lw) {
		Utility.debug(this, "New Line Width = " + lw);
		if (lw < 0) {
			lw = 0;
		}
		this.lineWidth = lw;
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
		this.fillEffect = null;
	}

	/**
	 * ���� ��Ʈ ä��� ȿ���� GradientPaint�� �����Ѵ�.
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
	 * ���� ��Ʈ ä��� ȿ���� TexturePaint�� �����Ѵ�.
	 */
	public TexturePaint getTexturePaint() {
		if (this.fillEffect == null) {
			return null;
		} else {
			return this.fillEffect.getTexturePaint();
		}
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
		/*
		 * if( c != null ) { this.lineTexture = null; }
		 */
		this.lineTexture = null;
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
	public ShapeStyle create() {

		boolean vertical = this.isVertical();

		ShapeStyle style = new ShapeStyle();

		style.setFont(this.font);
		this.setVertical(false); // ��� �������� ����

		style.setDimension(this.getDimensionWidth(), this.getDimensionHeight());
		style.setLocation(this.getLocationX(), this.getLocationY());
		style.setRotaionRadianAngle(this.getRotationRadianAngle());
		style.setVertical(vertical);

		this.setVertical(vertical); // ���� ��� �������� ȯ��

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
	 * ���� ��Ʈ ��Ÿ���� ������ �˾Ƴ��� ���� �Լ��̴�.
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
	 * ���� ��Ʈ�� ������ �����Ѵ�.
	 */
	public Rectangle2D getDimension() {
		return this.dimension;
	}

	/**
	 * ���� ��Ʈ�� ������ �����Ѵ�.
	 * 
	 * @param dim
	 *            ���� ��Ʈ ����
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
	 * ���� ��Ʈ�� ������ �����Ѵ�.
	 * 
	 * @param w
	 *            �� h ����
	 */
	public void setDimension(double w, double h) {
		// ���� ���̰� 0 ���� ũ�ų� ���� �׻� �Ҵ��Ѵ�.
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
	 * ���� ��Ʈ�� ��ġ�� �����Ѵ�.
	 * 
	 * @param x
	 *            ���� ��ġ y ���� ��ġ
	 */
	public void setLocation(double x, double y) {
		if (this.location == null) {
			this.location = new Point2D.Double(x, y);
		}
		if (this.isVertical()) {
			double w = this.getDimensionWidth(), // ������ ����
			h = this.getDimensionHeight(); // ������ ����
			double lx = this.location.getX(), ly = this.location.getY();
			double diff = (w - h) / 2.0;
			x = lx - diff;
			y = ly + diff;
		}
		this.location.setLocation(x, y);
	}

	/**
	 * ���� ��Ʈ�� ���� ��ġ�� �����Ѵ�.
	 * 
	 * @param x
	 *            ���� ��ġ
	 */
	public void setLocationX(double x) {
		if (this.location == null) {
			this.location = new Point2D.Double(x, 0);
		}
		this.setLocation(x, this.location.getY());
	}

	/**
	 * ���� ��Ʈ�� ���� ��ġ�� �����Ѵ�.
	 * 
	 * @param y
	 *            ���� ��ġ
	 */
	public void setLocationY(double y) {
		if (this.location == null) {
			this.location = new Point2D.Double(0, y);
		}
		this.setLocation(this.location.getX(), y);
	}

	/**
	 * ���� ��Ʈ ��Ÿ���� �з� ���̵� �����Ѵ�.
	 */
	public int getTypeCode() {
		return this.type;
	}

	/**
	 * ���� ��Ʈ�� ���̸� Ȯ���Ѵ�.
	 * 
	 * @param dh
	 *            ���� Ȯ�� ��
	 */
	public void increaseDimensionHeight(double dh) {
		this.setDimension(this.getDimensionWidth(), this.getDimensionHeight()
				+ dh);
	}

	/**
	 * ���� ��Ʈ�� ���� Ȯ���Ѵ�.
	 * 
	 * @param dw
	 *            �� Ȯ�� ��
	 */
	public void increaseDimensionWidth(double dw) {
		this.setDimension(this.getDimensionWidth() + dw, this
				.getDimensionHeight());
	}

	/**
	 * ���� ��Ʈ�� ���̸� �����Ѵ�.
	 */
	public double getDimensionHeight() {
		if (this.dimension == null) {
			return 0;
		} else {
			return this.dimension.getHeight();
		}
	}

	/**
	 * ���� ��Ʈ�� ���� �����Ѵ�.
	 */
	public double getDimensionWidth() {
		if (this.dimension == null) {
			return 0;
		} else {
			return this.dimension.getWidth();
		}
	}

	/**
	 * ���� ��Ʈ�� ��ġ�� �����Ѵ�.
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
	 * ���� ��Ʈ�� ��ġ�� �����Ѵ�.
	 */
	public void setLocation(Point2D loc) {
		this.setLocation(loc.getX(), loc.getY());
	}

	/**
	 * ���� ��Ʈ�� ���� ��ġ�� �����Ѵ�.
	 */
	public double getLocationX() {
		return this.getLocation().getX();
	}

	/**
	 * ���� ��Ʈ�� ���� ��ġ�� �����Ѵ�.
	 */
	public double getLocationY() {
		return this.getLocation().getY();
	}

	/**
	 * ���� ��Ʈ�� ȸ�� ������ �����Ѵ�.
	 * 
	 * @param radian
	 *            ȸ�� ����(����)
	 */
	public void setRotaionRadianAngle(double radian) {
		if (this.isVertical()) {
			this.rotationAngle = radian - Math.PI / 2.0;
		} else {
			this.rotationAngle = radian;
		}
	}

	/**
	 * ���� ��Ʈ�� ȸ�� ������ �����Ѵ�.
	 * 
	 * @param degree
	 *            ȸ�� ����(��,��)
	 */
	public void setRotationDegreeAngle(double degree) {
		this.setRotaionRadianAngle(Unit.convertDegreeToRadian(degree));
	}

	/**
	 * ���� ��Ʈ�� ȸ�� ������ �����Ѵ�.
	 */
	public double getRotationRadianAngle() {
		if (this.isVertical()) {
			return this.rotationAngle + Math.PI / 2.0;
		} else {
			return this.rotationAngle;
		}
	}

	/**
	 * ���� ��Ʈ�� ȸ�� ������ �����Ѵ�.
	 */
	public double getRotationDegreeAngle() {
		return Unit.convertRadianToDegree(getRotationRadianAngle());
	}

	/**
	 * ���� ��Ʈ�� ���� ��ġ�� �̵��Ѵ�.
	 * 
	 * @param dx
	 *            �̵� ��
	 */
	public void increaseLocationX(double dx) {
		this.setLocation(this.getLocationX() + dx, this.getLocationY());
	}

	/**
	 * ���� ��Ʈ�� ���� ��ġ�� �̵��Ѵ�.
	 * 
	 * @param dy
	 *            �̵� ��
	 */
	public void increaseLocationY(double dy) {
		this.setLocation(this.getLocationX(), this.getLocationY() + dy);
	}

	/**
	 * ���� ��Ʈ�� �� ���¸� �����Ѵ�.
	 */
	public void setLineStroke(BasicStroke stroke) {
		this.lineStroke = stroke;
	}

	/**
	 * ���� ��Ʈ�� �̵��Ѵ�.
	 * 
	 * @param dx
	 *            ���� �̵� �� dy ���� �̵� ��
	 */
	public void moveBy(double dx, double dy) {
		double x = this.location.getX();
		double y = this.location.getY();
		this.location.setLocation(x + dx, y + dy);
	}

	/**
	 * �� ���¸� �����Ѵ�.
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