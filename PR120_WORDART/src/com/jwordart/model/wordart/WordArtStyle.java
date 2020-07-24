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
 * ���� ��Ʈ�� �Ӽ��� ��Ÿ���� Ŭ�����̴�.
 */
public class WordArtStyle {

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
	public GraphicEffect getFillEffect() {
		return this.graphicEffect;
	}

	/**
	 * �� ���̸� �����Ѵ�.
	 *
	 * @param fe
	 *            �� ���� �Ӽ�
	 */
	public void setFillEffect_LineTexture(GraphicEffect fe) {
		this.fillEffect_LineTexture = fe;
		this.lineColor = null;
	}

	/**
	 * �� ���̸� �����Ѵ�.
	 */
	public GraphicEffect getFillEffect_LineTexture() {
		return this.fillEffect_LineTexture;
	}

	/**
	 * ���� ��Ʈ�� ä��� ȿ���� �����Ѵ�.
	 *
	 * @param fe
	 *            ���� ��Ʈ ä��� ȿ��
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
		this.graphicEffect = null;
	}

	/**
	 * ���� ��Ʈ ä��� ȿ���� GradientPaint�� �����Ѵ�.
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
	 * ���� ��Ʈ ä��� ȿ���� TexturePaint�� �����Ѵ�.
	 */
	public TexturePaint getTexturePaint() {
		if (this.graphicEffect == null) {
			return null;
		} else {
			return this.graphicEffect.getTexturePaint();
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
		this.fillEffect_LineTexture = null;
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
	public WordArtStyle create() {

		boolean vertical = this.isVertical();

		WordArtStyle style = new WordArtStyle();

		style.setFont(this.font);
		style.setShade(this.wordArtShade);
		this.setVertical(false); // ��� �������� ����

		style.setDimension(this.getDimensionWidth(), this.getDimensionHeight());
		style.setLocation(this.getLocationX(), this.getLocationY());
		style.setRotaionRadianAngle(this.getRotationRadianAngle());
		style.setVertical(vertical);

		this.setVertical(vertical); // ���� ��� �������� ȯ��

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
	 * ���� ��Ʈ ��Ÿ���� ������ �˾Ƴ��� ���� �Լ��̴�.
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
		if (this.getFillEffect_LineTexture() != null) {
			this.getFillEffect_LineTexture().setBounds(dim);
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
		this.setRotaionRadianAngle(UnitUtil_WordArt.convertDegreeToRadian(degree));
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
		return UnitUtil_WordArt.convertRadianToDegree(this.getRotationRadianAngle());
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
		this.strokeLine = stroke;
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
	 * �׸��� ��Ʈ�ѷ��� �����Ѵ�.
	 */
	public WordArtShade getShade() {
		return this.wordArtShade;
	}

	/**
	 * �׸��� ��Ʈ�ѷ��� �����Ѵ�.
	 *
	 * @param wordArtShade
	 *            �׸��� ��Ʈ�ѷ�
	 */
	public void setShade(WordArtShade wordArtShade) {
		this.wordArtShade = wordArtShade;
	}

	/**
	 * �� ���¸� �����Ѵ�.
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
	 * �ܼ� �׸��� �Ӽ��� �����Ѵ�.
	 *
	 * @param shadeColor
	 *            �׸��� Į��
	 */
	public void setSimpleShade(Color shadeColor) {
		this.wordArtShade = new WordArtSimpleShade(4, 4, shadeColor);
	}

	/**
	 * �ܼ� �׸��� �Ӽ��� �����Ѵ�.
	 *
	 * @param transX
	 *            ���� �̵� �� transY ���� �̵� �� shadeColor �׸��� Į��
	 */
	public void setSimpleShade(int transX, int transY, Color shadeColor) {
		this.wordArtShade = new WordArtSimpleShade(transX, transY, shadeColor);
	}

	/**
	 * ������
	 */
	public WordArtStyle() {
	}

	// ���� ���� ����
	private GraphicEffect fillEffect_LineTexture = null; // �� ����
	private double lineWidth = 1; // �� ����
	private Color lineColor = Color.black; // �� ����
	private BasicStroke strokeLine = new BasicStroke((int) (lineWidth + 0.5)); // �� ��Ʈ��ũ

	// ä���
	private Color fillColor = Color.black; // ä��� Į��
	private GraphicEffect graphicEffect = null; // ä��� ȿ��

	// ��ġ, ũ��, ����
	private Rectangle2D dimension; // ��ü ���� (��ġ �� ũ��)
	private Point2D location = new Point2D.Float(); // ��ġ
	private double rotationAngle; // ȸ�� ����, ������ ����(Radian) �̴�.

	// ��Ʈ , ��������
	private Font font = FontManager_WordArt.getDefaultFont() ; // ��Ʈ
	private boolean vertical = false; // ���� ���ڿ� ����

	private WordArtShade wordArtShade; // �׸��� ��Ʈ�ѷ� Ŭ����

}