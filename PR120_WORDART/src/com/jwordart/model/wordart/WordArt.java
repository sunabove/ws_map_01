/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.model.wordart;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import com.jwordart.model.wordart.mappingType.WaveMappingType;
import com.jwordart.model.wordart.mappingType.WordArtMappingType;

public class WordArt {
	// �⺻ ũ��
	public static final double width = 500, height = 100;

	// ���ڿ� �迭 ��� ���� ����
	public static final int LEFT = 0, CENTER = 1, RIGHT = 2, WORD = 3, CHARACTER = 4, BOTH = 5;

	// ���� ��Ʈ ũ�� ���� ��� ���� ����
	public static final int EXT_OUT = -2, GLYPH_ON = -1, NW_RESIZE = 0, N_RESIZE = 1, NE_RESIZE = 2, W_RESIZE = 3, E_RESIZE = 4,
			SW_RESIZE = 5, S_RESIZE = 6, SE_RESIZE = 7, ROTATE_ON = 8, CONTROL_POINT = 9;

	private ControlPoint controlPointOne = null; // ��Ʈ�� ����Ʈ
	private ControlPoint controlPointTwo = null; // �ι�° ��Ʈ�� ����Ʈ
	private ControlPoint selectedControlPoint = null; // ���õ� ��Ʈ�� ����Ʈ(�̵� ���)

	private WordArtGroupManager manager; // �����Ʈ �׷� �Ŵ���
	private WordArtComponent wordArtComponent;

	private Rectangle2D rawBounds; // ���� �۸����� ���̿� ����

	private Shape cornerRects[]; // �ڳ� �簢��, �������� �� ���
	private Shape rotateCircles[]; // ���� ���� ��
	private Graphics2D g2; // Graphics2D
	private String string; // ���ڿ�

	private WordArtMappingType mappingType; // ��ü ���� Ÿ��
	private Shape[] mappingOutLines; // ��ü ���� �ܰ���
	private WordArtStyle style; // ä���, ���� ���� ����, ũ��, ����,
	// ��ġ, ���� ���� ��ġ ����
	// ���ڿ��� �Բ� ��ü �̵�, Ʋ ��ġ ����

	private double glyphGapRatio = 1.0; // ���� ���� �ۼ�Ƽ��
	private int adjustmentType = CENTER; // ���ڿ� ���� ���
	private boolean sameCharacterHeight = false; // ���� ���� ����

	private Shape[][] shadeGlyphs; // �׸��� �۸�

	private Color xorColor = Color.white; // XOR Masking Color.
	// �� ��� ������Ʈ�� �� �׶��� Į��� ����
	private BasicStroke dashedStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 3, 3 },
			0);

	public WordArt(WordArtGroupManager manager, WordArtMappingTypeAndStyle type, FontAndString fs, Point2D loc) {
		// set member data
		this.manager = manager;
		// set xor Color
		this.setXORColor();
		this.mappingType = type.getWordArtMappingType();
		this.style = type.getWordArtStyle();
		this.string = fs.string;
		this.setFont(fs.font);
		this.style.setLocation(loc);

		// ��ü ������ �����ϸ鼭, �Ľ��Ѵ�.
		this.parse(true);
	}

	public void setFont(Font font) {
		if (this.style != null) {
			this.style.setFont(font);
		}
	}

	public Color getXorColor() {
		return this.xorColor;
	}

	public void setControlPoint(ControlPoint point) {
		this.controlPointOne = point;
	}

	public ControlPoint getControlPoint() {
		return this.controlPointOne;
	}

	public void setGlyphGapRatio(double glyphGapRatio) {
		if (glyphGapRatio < 0) {
			return;
		}
		this.glyphGapRatio = glyphGapRatio;
	}

	public double getGlyphGapRatio() {
		return this.glyphGapRatio;
	}

	public void setSecondControlPoint(ControlPoint point) {
		this.controlPointTwo = point;
	}

	public ControlPoint getSecondControlPoint() {
		return this.controlPointTwo;
	}

	public BasicStroke getDashedStroke() {
		return this.dashedStroke;
	}

	public boolean isVertical() {
		return this.style.isVertical();
	}

	public void setVertical(boolean b) {
		this.style.setVertical(b);
	}

	public int getAdjustmentType() {
		return this.adjustmentType;
	}

	public void setAdjustmentType(int pType) {
		this.adjustmentType = pType;
	}

	public boolean isSameCharacterHeight() {
		return this.sameCharacterHeight;
	}

	public void setTheSameCharacterHeight(boolean b) {
		this.sameCharacterHeight = b;
	}

	/**
	 * ���� ��Ʈ ������Ʈ�� �Ľ��Ѵ�.
	 */
	public void parseWordArtComponent() {
		this.wordArtComponent = new WordArtComponent(this, this.string);
		// ��Ʈ�� ����Ʈ�� �����Ѵ�.
		// ��Ʈ�� ����Ʈ Ÿ���� ���� Ÿ�Կ� ���� �޶�����.
		if (this.getControlPoint() == null) {
			this.mappingType.setControlPoint(this);
		}
	}

	/**
	 * ���� ��Ʈ�� �Ľ��Ѵ�.
	 *
	 * @param firstParsing
	 *            ó�� �Ľ� ���� ���� ���̸� ��ü ũ�⸦ �� �����Ѵ�.
	 */
	public void parse(boolean firstParsing) {
		// set graphics2d before parsing
		this.setGraphics2D();

		// ���� ��Ʈ ������Ʈ�� �Ľ��Ѵ�.
		this.parseWordArtComponent();

		// ó�� �Ľ��ϸ�, ��ü ������ �۸��� �����ϱ� ���� �����Ѵ�.
		// �۷ι� ����� ���� ����� ��ȯ�ϱ� ���ؼ�.
		if (firstParsing) {
			// this.setGlobalDimension();
			this.setGlobalDimension(new Rectangle2D.Double(0, 0, width, height));
		}

		// raw Bounds�� �����Ѵ�.
		this.setRawBounds();

		// �����Ͽ� ���缭 �۸����� Ȯ��/��� �Ѵ�.
		this.scaleGlyphs();

		// ���� ������ �����Ѵ�.
		this.parseGlyphGap();

		// �۸����� �����Ѵ�.
		this.alignGlyphs();

		// ���� ���ڿ� ����
		this.setGlyphsVertical();

		// �۸� ����
		this.transformGlyphs();
		// ��. �۸� ����

		// �۸��� ȸ���Ѵ�.
		this.rotateGlyphs();
		// ��. �۸� ȸ��

		// �׸��� �۸��� �����Ѵ�.
		this.makeShadeGlyphs();

		// �ʿ��� ������ �� �����Ѵ�.
		this.resetNecessaryData();
	}

	public void transformGlyphs() {
		// �AƮ�� ����Ʈ�� �缳���Ѵ�.

		this.resetControlPoints();

		WordArtComponent wc = this.getWordArtComponent();
		if (wc == null) {
			return;
		}
		wc.transformGlyphs();
	}

	public void setGlyphsVertical() {
		if (!this.isVertical()) {
			return;
		}

		WordArtComponent wc = this.wordArtComponent;

		if (wc == null) {
			return;
		}

		wc.setGlyphsVertical();

		this.setGlobalDimension();
		this.resetControlPoints();
	}

	public void alignGlyphs() {
		WordArtComponent wc = this.wordArtComponent;
		if (wc == null) {
			return;
		}

		wc.alignGlyphs();
	}

	public void parseGlyphGap() {
		if (this.getGlyphGapRatio() == 1.0) {
			return;
		}
		WordArtComponent wc = this.wordArtComponent;
		if (wc == null) {
			return;
		}

		wc.parseGlyphGap();
	}

	public void scaleGlyphs() {
		WordArtComponent wc = this.wordArtComponent;

		if (wc == null) {
			return;
		}

		double x = 0, y = 0, sx = this.getDimensionScaleX(), sy = this.getDimensionScaleY();

		// �����Ͽ� ��ȭ�� ������ �Լ��� �����Ѵ�.
		if (sx == 1 && sy == 1) {
			return;
		}

		double fontLeading = this.getFontLeading();

		AffineTransform at;
		Shape[][] glyphs;

		at = AffineTransform.getTranslateInstance(x, y);

		at.scale(sx, sy);

		glyphs = wc.getGlyphs();
		// �۸����� �������� �Ѵ�.
		for (int j = 0; j < glyphs.length; j++) {
			for (int k = 0; k < glyphs[j].length; k++) {
				glyphs[j][k] = at.createTransformedShape(glyphs[j][k]);
			}
		}

		y += (wc.getLocalDimension().getHeight() + fontLeading);

		// �������� �Ŀ��� �۷ι� ������� �� �����Ѵ�.
		this.setGlobalDimension();
		// ��. �۷ι� ����� �� ����
	}

	public void resetNecessaryData() {
		this.cornerRects = null;
		this.rotateCircles = null;
		this.mappingOutLines = null;
		this.resetControlPoints();
	}

	// ��(Raw) ������ �����Ѵ�.
	public void setRawBounds() {
		this.rawBounds = wordArtComponent.getLocalDimension();
	}

	public void setGlobalDimension() {
		if (this.wordArtComponent == null) {
			this.setGlobalDimension(new Rectangle2D.Double(0, 0, 1, 1));
			return;
		}

		// �۷ι� ������� ����.
		this.setGlobalDimension(wordArtComponent.getLocalDimension());
	}

	void setTransferGraphics2DToLocation() {
		Graphics2D g2 = this.getGraphics2D();

		// Transform to word art location.
		g2.setTransform(AffineTransform.getTranslateInstance(this.style.getLocationX(), this.style.getLocationY()));

		// ���� ��ƴ ȸ���Ѵ�.
		/*
		 * double radian = this.style.getRotationRadianAngle(); if( radian != 0 ) {
		 * Point2D center = this.getCenterPoint(); g2.rotate(radian,
		 * center.getX(), center.getY() ); }
		 */
	}

	public AffineTransform getRotateAffineTransform() {
		double radian = this.style.getRotationRadianAngle();
		Point2D center = this.getCenterPoint();
		return AffineTransform.getRotateInstance(radian, center.getX(), center.getY());
	}

	public Shape getRotatedShape(Shape s) {
		if (this.style.getRotationRadianAngle() == 0) {
			return s;
		} else {
			return this.getRotateAffineTransform().createTransformedShape(s);
		}
	}

	public void rotateGlyphs() {
		if (this.style.getRotationRadianAngle() == 0) {
			return;
		}
		WordArtComponent wc = this.wordArtComponent;

		if (wc != null) {
			wc.rotateGlyphs();
		}
	}

	public double getFontLeading() {
		Font font = this.getFont();

		FontMetrics fm = null;
		if (font != null) {
			fm = this.getGraphics2D().getFontMetrics(this.getFont());
		} else {
			fm = this.getGraphics2D().getFontMetrics();
		}

		if (fm != null) {
			return fm.getLeading();
		} else {
			return 1;
		}
	}

	/**
	 * ���� ��Ʈ�� �׷����ϰ� �׸���.
	 */
	public void drawWordArt() {
		// ù ���� ��Ʈ ������Ʈ�� �׸��� ���ؼ� ��ǥ �̵��Ѵ�.
		this.setTransferGraphics2DToLocation();
		WordArtComponent wc = this.wordArtComponent;
		if (wc != null) {
			this.drawShadeGlyphs();
			wc.drawWrodArtComponent();
		}
	}

	/**
	 * �׸��ڸ� �׸���.
	 */
	public void drawShadeGlyphs() {
		if (this.getShade() == null) {
			return;
		}
		this.getShade().drawShadeGlyphs(this);
	}

	public WordArtShade getShade() {
		if (this.style == null) {
			return null;
		}
		return this.style.getShade();
	}

	/**
	 * �׸��� �׸��� �����Ѵ�.
	 */
	public void makeShadeGlyphs() {
		if (this.getShade() == null) {
			return;
		}
		this.getShade().makeShadeGlyphs(this);
	}

	public void reparse(FontAndString fs) {
		this.setFont(fs.font);
		this.string = fs.string;
		// ��ü ������ �ٽ� �����ϸ鼭, �ٽ� �Ľ��Ѵ�.
		// �۷ι� ������ �� �����ϱ� ���� �η� �����Ѵ�.
		// this.style.setDimension( null );
		// ��. �۷ι� ���� �η� ����
		this.parse(false);
		// ��. �� �Ľ�
	}

	public WordArt create() {
		boolean vertical = this.isVertical();
		this.setVertical(false);

		Point2D newLoc = new Point2D.Double(this.style.getLocationX(), this.style.getLocationY());
		double w, h;

		w = this.getGlobalDimension().getWidth();
		h = this.getGlobalDimension().getHeight();

		WordArt value = new WordArt(this.manager, new WordArtMappingTypeAndStyle(WordArtMappingType
				.getWordArtMappingType(this.mappingType.getTypeCode()), this.style.create()), new FontAndString(this.getFont(),
				this.string), newLoc);

		// ���� Ÿ���� ������ ���� ���߾� �ش�.
		value.setAdjustmentType(this.getAdjustmentType());
		// �۸� ������ ������ ���� ���߾� �ش�.
		value.setGlyphGapRatio(this.getGlyphGapRatio());
		// �۷ι� ������� ������ ���� ���߾� �ش�.
		value.setGlobalDimension(new Rectangle2D.Double(0, 0, w, h));
		// ���� ���� ����
		value.setVertical(vertical);
		this.setVertical(vertical);

		// �۷ι� ������� ��ȭ ���� ���Ľ��Ѵ�.
		value.parse(false);

		// ù ��° ��Ʈ�� ����Ʈ ����
		ControlPoint cp = this.getControlPoint();
		if (cp != null) {
			value.setControlPoint(cp.create(this));
		} else {
			value.controlPointOne = null;
		}
		// ��. ù ��° ��Ʈ�� ����Ʈ ����

		// �� ��° ��Ʈ�� ����Ʈ ����
		cp = this.getSecondControlPoint();
		if (cp != null) {
			value.setSecondControlPoint(cp.create(this));
		} else {
			value.controlPointTwo = null;
		}
		// ��. �� ��° ��Ʈ�� ����Ʈ ����

		return value;
	}

	/**
	 * �׸��� �۸��� �����Ѵ�.
	 *
	 * @param shadeGlyphs
	 *            ������ �׸��� �۸�
	 */
	public void setShadeGlyphs(Shape[][] shadeGlyphs) {
		this.shadeGlyphs = shadeGlyphs;
	}

	/**
	 * �׸��� �۸��� �����Ѵ�.
	 */
	public Shape[][] getShadeGlyphs() {
		return this.shadeGlyphs;
	}

	// set XOR Color as target component's background color.
	private void setXORColor() {
		this.xorColor = this.getComponent().getBackground();
	}

	public void changeWordArtGallery(WordArtMappingTypeAndStyle mns) {
		this.setMappingType(mns.getWordArtMappingType());
		this.setWordArtStyle(mns.getWordArtStyle());
	}

	public void setMappingType(WordArtMappingType mt) {
		this.mappingType = mt;
		this.mappingType.setControlPoint(this);
	}

	public void setLocation(double x, double y) {
		this.style.setLocation(x, y);
	}

	public Point2D getLocation() {
		return this.style.getLocation();
	}

	public void setString(String str) {
		if (!str.endsWith("\n")) {
			str = str + "\n";
		}
		this.string = str;
	}

	public String getString() {
		return this.string;
	}

	public void draw() {
		// Set graphics2D object.
		this.setGraphics2D();

		// ���� ��Ʈ�� �׸���.
		this.drawWordArt();
		// ��. ���� ��Ʈ �׸���

		// �� ���� ��Ʈ�� ���õǸ�,
		if (this.selectedWordArt()) {
			// ��Ʈ�� ����Ʈ�� ���õǸ�, ���� �ܰ����� ���� �׸���.
			// MOVE_CONTROL_POINT ���� �Ѱ��� ���� ��Ʈ�� ���õǾ��� ��쿡�� �����ȴ�.
			if (this.manager != null && this.manager.getWorkMode() == WordArtGroupManager.MOVE_CONTROL_POINT) {
				this.drawMappingOutLines();
			}
			// ��. �ܰ��� �׸���
			if (manager.getWorkMode() == WordArtGroupManager.ROTATE_MODE) {
				this.setGraphics2D();
				// ȸ�� ���� �׸���.
				this.drawRotateCircles();
				// ��. ȸ���� �׸���
			} else {
				this.setGraphics2D();
				// �ڳ� �簢���� �׸���.
				this.drawCornerRects();
				// ��. �ڳ� �簢�� �׸���
				// ��Ʈ�� ����Ʈ�� �׸���.
				if (this.manager != null && this.manager.getSelectedWordArtsNumber() == 1) {
					this.drawControlPoints();
				}
				// ��. ��Ʈ�� ����Ʈ �׸���
			}

		}
	}

	public void drawControlPoints() {
		ControlPoint cp = this.getControlPoint();

		Graphics2D g2 = this.getGraphics2D();

		// transform to the location.
		this.setTransferGraphics2DToLocation();

		Shape cps; // ȸ���� ��Ʈ�� ����Ʈ

		// ù ��° ��Ʈ�� ����Ʈ �׸���
		if (cp != null) {
			cps = this.getRotatedShape(cp.getRotatedShape());
			g2.setColor(Color.yellow);
			g2.fill(cps);
			g2.setColor(Color.black);
			g2.draw(cps);
		} else {
			return;
		}
		// ��. ù ��° ��Ʈ�� ����Ʈ �׸���

		// �ι�° ��Ʈ�� ����Ʈ �׸�
		// ���̺� ���� Ÿ���� ���� �ι�° ��Ʈ�� ����Ʈ�� �׸���.

		if (!(this.mappingType instanceof WaveMappingType)) {
			return;
		}

		cp = this.getSecondControlPoint();

		if (cp != null) {
			cps = this.getRotatedShape(cp.getRotatedShape());
			g2.setColor(Color.yellow);
			g2.fill(cps);
			g2.setColor(Color.black);
			g2.draw(cps);
		} else {
			return;
		}
		// ��. �ι�° ��Ʈ�� ����Ʈ �׸���
	}

	public void setMappingOutLines(Shape[] lines) {
		this.mappingOutLines = lines;
	}

	public Shape[] getMappingOutLines() {
		if (this.mappingOutLines == null) {
			Rectangle2D bounds = this.getGlobalDimension();

			double x0 = bounds.getX(), y0 = bounds.getY(), w = bounds.getWidth(), h = bounds.getHeight();

			Shape[] value = new Shape[2];

			value[0] = new Line2D.Double(x0, y0, x0 + w, y0);
			value[1] = new Line2D.Double(x0, y0 + h, x0 + w, y0 + h);

			this.mappingOutLines = value;

			this.mappingType.transformMappingOutLines(this);

			for (int i = 0; i < this.mappingOutLines.length; i++) {
				this.mappingOutLines[i] = this.getRotatedShape(this.mappingOutLines[i]);
			}
		}

		return this.mappingOutLines;
	}

	public void drawMappingOutLines() {
		this.setGraphics2D();
		// ù ���� ��Ʈ ������Ʈ�� �׸��� ���ؼ� �׷��Ƚ��� ��ǥ�� �̵��Ѵ�.
		this.setTransferGraphics2DToLocation();
		// ��. �׷��Ƚ� ��ǥ �̵�.

		// ���� �ܰ����� �׸���.
		Shape[] mappingOutLines = this.getMappingOutLines();
		// Shape [] mappingOutLines =
		// this.globalMappingType.getTransformedMappingOutLines( this );
		Graphics2D g2 = this.getGraphics2D();
		// ���� �ܰ����� XOR ���� �׸���.
		g2.setXORMode(this.xorColor);
		// ��. XOR ��� ����.
		g2.setColor(Color.black);
		g2.setStroke(dashedStroke);
		for (int i = 0; i < mappingOutLines.length; i++) {
			g2.draw(mappingOutLines[i]);
		}
		g2.setPaintMode();
		// ��. �ܰ��� �׸���.
	}

	public void drawRotateCircles() {
		Shape[] circles = this.getRotateCircles();

		// transform to the location.
		this.setTransferGraphics2DToLocation();

		Graphics2D g2 = this.getGraphics2D();
		g2.setStroke(new BasicStroke(1));

		for (int i = 0; i < circles.length; i++) {
			if (circles[i] != null) {
				g2.setColor(Color.green);
				g2.fill(circles[i]);
				g2.setColor(Color.black);
				g2.draw(circles[i]);
			}
		}
	}

	// Draw wordart corner rectangles with or without control point.
	public void drawCornerRects() {
		Shape[] corners = this.getCornerRects();

		// transform to the location.
		this.setTransferGraphics2DToLocation();

		Graphics2D g2 = this.getGraphics2D();
		g2.setStroke(new BasicStroke(1));

		for (int i = 0; i < corners.length; i++) {
			if (corners[i] != null) {
				g2.setColor(Color.white);
				g2.fill(corners[i]);
				g2.setColor(Color.black);
				g2.draw(corners[i]);
			}
		}
	}

	public Shape[] getRotateCircles() {
		// this.setGlobalDimension();
		Rectangle2D dim = this.getGlobalDimension();
		double w = 7, h = 7;
		double x0 = dim.getX() - w / 2, y0 = dim.getY() - h / 2, x1 = x0 + dim.getWidth(), y1 = y0 + dim.getHeight();

		if (this.rotateCircles == null) {
			this.rotateCircles = new Shape[4];

			rotateCircles[0] = new Ellipse2D.Double(x0, y0, w, h); // NW

			rotateCircles[1] = new Ellipse2D.Double(x1, y0, w, h); // NE

			rotateCircles[2] = new Ellipse2D.Double(x0, y1, w, h); // SN

			rotateCircles[3] = new Ellipse2D.Double(x1, y1, w, h); // SE

			for (int i = 0; i < this.rotateCircles.length; i++) {
				rotateCircles[i] = this.getRotatedShape(rotateCircles[i]);
			}
		}

		return this.rotateCircles;
	}

	public Shape[] getCornerRects() {
		// this.setGlobalDimension();
		Rectangle2D dim = this.getGlobalDimension();
		double w = 6, h = 6;
		double x0 = dim.getX() - w / 2, y0 = dim.getY() - h / 2, x1 = x0 + dim.getWidth(), y1 = y0 + dim.getHeight();

		if (this.cornerRects == null) {
			this.cornerRects = new Shape[8];

			cornerRects[0] = new Rectangle2D.Double(x0, y0, w, h); // NW
			cornerRects[1] = new Rectangle2D.Double((x0 + x1) / 2, y0, w, h); // N
			cornerRects[2] = new Rectangle2D.Double(x1, y0, w, h); // NE

			cornerRects[3] = new Rectangle2D.Double(x0, (y0 + y1) / 2, w, h); // W
			cornerRects[4] = new Rectangle2D.Double(x1, (y0 + y1) / 2, w, h); // E

			cornerRects[5] = new Rectangle2D.Double(x0, y1, w, h); // SN
			cornerRects[6] = new Rectangle2D.Double((x0 + x1) / 2, y1, w, h); // S
			cornerRects[7] = new Rectangle2D.Double(x1, y1, w, h); // SE

			for (int i = 0; i < this.cornerRects.length; i++) {
				this.cornerRects[i] = this.getRotatedShape(this.cornerRects[i]);
			}
		}

		return this.cornerRects;
	}

	public Point2D getCenterPoint() {
		Rectangle2D globalDim = this.getGlobalDimension();
		double w = globalDim.getWidth(), h = globalDim.getHeight();
		return new Point2D.Double(globalDim.getX() + w / 2.0, globalDim.getY() + h / 2.0);
	}

	boolean selectedWordArt() {
		return manager.isSelectedWordArt(this);
	}

	public Font getFont() {
		return this.style.getFont();
	}

	public void setGraphics2D() {
		this.g2 = (Graphics2D) this.getComponent().getGraphics().create();
		this.g2.setFont(this.getFont());
		this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// this.frc = this.g2.getFontRenderContext();
	}

	public Graphics2D getGraphics2D() {
		if (this.g2 == null) {
			this.setGraphics2D();
		}
		this.g2.setFont(this.getFont());
		return this.g2;
	}

	public Component getComponent() {
		return manager.getTargetComponent();
	}

	public Rectangle2D getGlobalDimension() {
		Rectangle2D dim = this.style.getDimension();
		if (dim == null) {
			this.setGlobalDimension();
		}
		return this.style.getDimension();
	}

	/*
	 * public Rectangle2D getGlobalDimension() { this.setGlobalDimension();
	 * return this.style.getDimension(); }
	 */

	public void setGlobalDimension(Rectangle2D dim) {
		this.style.setDimension(dim);
	}

	public void resetControlPoints() {
		ControlPoint cp = this.getControlPoint();
		if (cp != null) {
			cp.reset();
		}
		cp = this.getSecondControlPoint();
		if (cp != null) {
			cp.reset();
		}
	}

	public void resizeBy(double dw, double dh) {
		if (dw == 0 && dh == 0) {
			return;
		}

		if (this.isVertical()) {
			double tempDw = dw;
			dw = dh;
			dh = tempDw;
		}

		Rectangle2D dim = this.getGlobalDimension();
		double w = dim.getWidth() + dw;
		double h = dim.getHeight() + dh;

		if (w <= 0) {
			w = 1;
		}

		if (h <= 0) {
			h = 1;
		}

		this.setGlobalDimension(new Rectangle2D.Double(0, 0, w, h));
		this.resetControlPoints();
		this.resetNecessaryData();
	}

	public WordArtGroupManager getWordArtGroupManager() {
		return this.manager;
	}

	public void setWordArtGroupManager(WordArtGroupManager manager) {
		this.manager = manager;
	}

	public WordArtComponent getWordArtComponent() {
		return this.wordArtComponent;
	}

	public void setWordArtComponent(WordArtComponent wc) {
		this.wordArtComponent = wc;
	}

	// returns the outer shape of the string.
	/*
	 * public Shape [] getOuterShapes() {
	 *
	 * if( this.wordArtComponent == null ){ return null; } else { return
	 * this.wordArtComponent.getGlyphs(); } }
	 */

	// returns true if mouseOver
	public int getTopology(MouseEvent e) {
		int x = e.getX(), y = e.getY();

		// ���콺 ��ǥ�� ���� ��Ʈ ��ġ�� ���߾� �̵�
		Point2D p = new Point2D.Float(x, y);
		AffineTransform at = AffineTransform.getTranslateInstance(-this.style.getLocationX(), -this.style.getLocationY());
		p = at.transform(p, p);

		// ��. ���콺 ��ǥ �̵�

		// ȸ�� �� �˻�
		if (this.selectedWordArt() && this.manager.getWorkMode() == WordArtGroupManager.ROTATE_MODE) {
			Shape[] circles = this.getRotateCircles();
			for (int i = 0; i < circles.length; i++) {
				if (circles[i].contains(p)) {
					return ROTATE_ON;
				}
			}
		}
		// ��. ȸ�� �� �˻�.

		// ��Ʈ�� ����Ʈ �˻�, ȸ���� �˻� ������ ��Ʈ�� ����Ʈ �˻縦 �Ͽ��� �Ѵ�(��������).
		if (this.manager.getWorkMode() != WordArtGroupManager.ROTATE_MODE // ȸ��
				// ��尡
				// �ƴϰ�
				&& this.manager.getSelectedWordArtsNumber() == 1 // ���� ���� ��Ʈ
				// ������ �ϳ��̰�
				&& this.selectedWordArt()) { // �� ���� ��Ʈ�� ���õǾ��� ��츸
			// ������ ����Ѵ�.
			// ù ��° ��Ʈ�� ����Ʈ ���� �˻�
			ControlPoint cp = this.getControlPoint();
			Shape cps = null;
			if (cp != null) {
				cps = this.getRotatedShape(cp.getRotatedShape());
			}
			if (cp != null && cps.contains(p)) {
				this.selectedControlPoint = cp;
				return WordArt.CONTROL_POINT;
			}
			// ��. ù ��° ��Ʈ�� ����Ʈ ���� �˻�

			// �� ��° ��Ʈ�� ����Ʈ ���� �˻�
			// ���̺� ���� Ÿ�� �϶��� �ι�° ��Ʈ�� ����Ʈ ���� �˻縦 �Ѵ�.
			cp = this.getSecondControlPoint();
			if (cp != null) {
				cps = this.getRotatedShape(cp.getRotatedShape());
			}
			if (cp != null && this.mappingType instanceof WaveMappingType && cps.contains(p)) {
				this.selectedControlPoint = cp;
				return WordArt.CONTROL_POINT;
			}
			// ��. �� ��° ��Ʈ�� ����Ʈ ���� �˻�
		}
		// ��. ��Ʈ�� ����Ʈ �˻�

		// �ڳ� �簢�� �˻�
		Shape[] rects = this.getCornerRects();
		for (int i = 0; i < rects.length; i++) {
			if (rects[i].contains(p)) {
				if (this.isVertical()) {
					switch (i) {
					case 0:
						i = 2;
						break;
					case 1:
						i = 4;
						break;
					case 2:
						i = 7;
						break;
					case 3:
						i = 1;
						break;
					case 4:
						i = 6;
						break;
					case 5:
						i = 0;
						break;
					case 6:
						i = 3;
						break;
					case 7:
						i = 5;
						break;
					}
				}
				return i;
			}
		}
		// ��. �ڳ� �簢�� �˻�.

		// �۸� ���� �˻�.
		if (this.wordArtComponent != null) {

			Shape[][] outerShapes = this.wordArtComponent.getGlyphs();
			String[] strings = this.wordArtComponent.getStrings();
			char c;
			for (int i = 0; i < outerShapes.length; i++) {
				for (int j = 0; j < outerShapes[i].length; j++) {
					c = strings[i].charAt(j);
					if (c == ' ' || c == '\t') {
					} else if (outerShapes[i][j].contains(p)) {
						return GLYPH_ON;
					}
				}
			}
		}
		// ��. �۸� ���� �˻�.

		return EXT_OUT;
	}

	public Point2D reverseTransformPoint2D(Point2D p) {
		double radian = this.getWordArtStyle().getRotationRadianAngle();
		if (radian != 0) {
			Point2D center = this.getCenterPoint();

			// ���� ���� ��ŭ �߽����� �������� �� ��ȯ�Ѵ�.
			AffineTransform at = AffineTransform.getRotateInstance(-radian, center.getX(), center.getY());
			p = at.transform(p, null);
		}

		return p;
	}

	public void mousePressed(MouseEvent e) {
		WordArt[] selWAs = manager.getSelectedWordArts();

		int top = this.getTopology(e);

		if (top == GLYPH_ON || top == ROTATE_ON) {
			if (e.isShiftDown() && !this.selectedWordArt()) {
				// ��ƮŰ�� ���� ä�� �� �����Ʈ�� ���� ��Ͽ� ���� ���,
				// ���� ��Ͽ� �߰��Ѵ�.
				manager.selectAppend(this);
			} else if (e.isShiftDown() && this.selectedWordArt()) {
				// ��ƮŰ�� ���� ä�� ���õ� ���� ��Ʈ�� �ٽ� ������ ��쿡��,
				// ���� ��Ͽ��� �����Ѵ�.
				manager.deSelect(this);
			} else if (!e.isShiftDown() && !this.selectedWordArt() && selWAs.length < 2) {
				// ���콺�� �����ϰ�, ��ƮŰ�� �������� ����ä
				// ���� ����� ������ �ΰ� �̻��� �ƴ� ���¿���,
				// �� �����Ʈ�� �����ϸ�, �� �����Ʈ ���� ���� ��Ͽ� �Է��Ѵ�.
				manager.selectAlone(this);
			} else {
				// ���� �ΰ� �̻��� ���� ��Ʈ�� ���� �Ǿ� ������ �ƹ� �ϵ� �ƴ� �Ѵ�.

			}
		} else { // �� ���� ��Ʈ�� ���õ� ���¿��� ���콺�� �������� �����鼭
			// ��ƮŰ�� ���� ���� ���� ä��
			// ���� ����� ������ �ΰ� �̻��� �ƴϸ�
			// �ٸ� ���� ��Ʈ�� ������ ���
			// ���� ���� ��Ʈ ��Ͽ��� �����Ѵ�.
			if (!e.isShiftDown() && this.selectedWordArt() && selWAs.length < 2) {
				manager.deSelect(this);
			}
		}
	}

	// ������ ���� ��Ʈ�� �ܰ����� �׸���.
	public void drawMappingOutLineMovedNrotatedNresizedBy(double dx, double dy, double radian, double dw, double dh) {

		// �����ϱ� ������ �ܰ����� �׸���.

		// ������ �ܰ����� �����Ѵ�.
		Shape[] outLines = this.getMappingOutLines();
		// ��. ���� �ܰ��� ����.

		if (outLines == null) {
			return;
		}

		// ���� �׷��Ƚ��� ���������� �̵��Ѵ�.
		this.setTransferGraphics2DToLocation();

		Graphics2D g2 = this.getGraphics2D();

		g2.setXORMode(this.xorColor);
		g2.setColor(Color.black);
		g2.setStroke(this.dashedStroke);
		for (int i = 0; i < outLines.length; i++) {
			g2.draw(outLines[i]);
		}

		// ��. �̵� ���� �ܰ��� �׸���

		// x, y ��ŭ �̵��Ѵ�.
		if (dx == 0 && dy == 0) {
		} else {
			this.moveBy(dx, dy);
		}
		// ��. �̵�

		// radian ��ŭ ȸ���Ѵ�.
		if (radian != 0) {
			this.rotateBy(radian);
		}
		// ��. ȸ��

		// �������� �Ѵ�.
		if (dw == 0 && dh == 0) {
		} else {
			this.resizeBy(dw, dh);
		}
		// ��. ��������

		// �̵��� �ܰ����� �׸���.

		// ���� �׷��Ƚ��� ���������� �̵��Ѵ�.
		this.setTransferGraphics2DToLocation();
		// ��. �׷��Ƚ� ������ �̵�.

		// �ٽ� �׸� �ܰ����� �� �����Ѵ�.
		this.setMappingOutLines(null);
		outLines = this.getMappingOutLines();
		// ��. �ܰ��� �� ����.

		for (int i = 0; i < outLines.length; i++) {
			g2.draw(outLines[i]);
		}
		g2.setPaintMode();

		// ��. �̵��� �ܰ��� �׸���.
	}

	// move location by x, y without drawing.
	public void moveBy(double x, double y) {
		this.style.moveBy(x, y);
	}

	public WordArtStyle getWordArtStyle() {
		return this.style;
	}

	public void setWordArtStyle(WordArtStyle style) {
		this.style = style;
		if (style.getFillEffect() != null) {
			style.getFillEffect().setBounds(style.getDimension());
		}
		if (style.getFillEffect_LineTexture() != null) {
			style.getFillEffect_LineTexture().setBounds(style.getDimension());
		}

		// ��ü ������ �������� �ʰ�, �ٽ� �Ľ��Ѵ�.
		this.parse(false);
	}

	public WordArtMappingType getMappingType() {
		return this.mappingType;
	}

	public void rotateBy(double radian) {
		WordArtStyle style = this.style;
		double newRadian = style.getRotationRadianAngle() + radian;
		newRadian = newRadian % Math.PI;
		style.setRotaionRadianAngle(newRadian);
	}

	public double getRawWidth() {
		return this.rawBounds.getWidth();
	}

	public double getRawHeight() {
		return this.rawBounds.getHeight();
	}

	public double getDimensionScaleX() {
		double rw = this.getRawWidth(), w = this.getGlobalDimension().getWidth();

		return w / rw;
	}

	public double getDimensionScaleY() {
		double rh = this.getRawHeight(), h = this.getGlobalDimension().getHeight();

		return h / rh;
	}

	public void moveControlPointsBy(double dx, double dy) {
		if (this.isVertical()) {
			double tempDx = dx;
			dx = dy;
			dy = tempDx;
		}
		ControlPoint cp = this.selectedControlPoint;
		if (cp != null) {
			cp.moveBy(dx, dy);
		}
	}

	public Shape[][] getGlyphs() {
		if (this.wordArtComponent == null) {
			return null;
		} else {
			return this.wordArtComponent.getGlyphs();
		}
	}

	/*
	 * public double getSpaceWidth() { String str = "A C"; Font font =
	 * this.getFont(); FontRenderContext frc =
	 * this.getGraphics2D().getFontRenderContext(); GlyphVector gv =
	 * font.createGlyphVector(frc, str); GlyphMetrics gm =
	 * gv.getGlyphMetrics(1); double width = gm.getAdvance(); return
	 * width*this.getDimensionScaleX(); }
	 */
}