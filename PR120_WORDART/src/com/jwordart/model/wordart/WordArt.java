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
	// 기본 크기
	public static final double width = 500, height = 100;

	// 문자열 배열 방식 설정 변수
	public static final int LEFT = 0, CENTER = 1, RIGHT = 2, WORD = 3, CHARACTER = 4, BOTH = 5;

	// 워드 아트 크기 변경 방식 설정 변수
	public static final int EXT_OUT = -2, GLYPH_ON = -1, NW_RESIZE = 0, N_RESIZE = 1, NE_RESIZE = 2, W_RESIZE = 3, E_RESIZE = 4,
			SW_RESIZE = 5, S_RESIZE = 6, SE_RESIZE = 7, ROTATE_ON = 8, CONTROL_POINT = 9;

	private ControlPoint controlPointOne = null; // 콘트롤 포인트
	private ControlPoint controlPointTwo = null; // 두번째 콘트롤 포인트
	private ControlPoint selectedControlPoint = null; // 선택된 콘트롤 포인트(이동 대상)

	private WordArtGroupManager manager; // 워드아트 그룹 매니저
	private WordArtComponent wordArtComponent;

	private Rectangle2D rawBounds; // 원본 글립들의 넓이와 높이

	private Shape cornerRects[]; // 코너 사각형, 리사이즈 시 사용
	private Shape rotateCircles[]; // 각도 조절 원
	private Graphics2D g2; // Graphics2D
	private String string; // 문자열

	private WordArtMappingType mappingType; // 전체 매핑 타잎
	private Shape[] mappingOutLines; // 전체 매핑 외곽선
	private WordArtStyle style; // 채우기, 선의 색과 굵기, 크기, 각도,
	// 위치, 가로 세로 위치 기준
	// 문자열과 함께 개체 이동, 틀 위치 고정

	private double glyphGapRatio = 1.0; // 글자 간격 퍼센티지
	private int adjustmentType = CENTER; // 문자열 정렬 방식
	private boolean sameCharacterHeight = false; // 같은 문자 높이

	private Shape[][] shadeGlyphs; // 그림자 글립

	private Color xorColor = Color.white; // XOR Masking Color.
	// 현 대상 컴포넌트의 백 그라운드 칼라로 설정
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

		// 전체 영역을 설정하면서, 파싱한다.
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
	 * 워드 아트 컴포넌트를 파싱한다.
	 */
	public void parseWordArtComponent() {
		this.wordArtComponent = new WordArtComponent(this, this.string);
		// 콘트롤 포인트를 설정한다.
		// 콘트롤 포인트 타입은 매핑 타입에 따라서 달라진다.
		if (this.getControlPoint() == null) {
			this.mappingType.setControlPoint(this);
		}
	}

	/**
	 * 워드 아트를 파싱한다.
	 *
	 * @param firstParsing
	 *            처음 파싱 여부 설정 참이면 전체 크기를 재 설정한다.
	 */
	public void parse(boolean firstParsing) {
		// set graphics2d before parsing
		this.setGraphics2D();

		// 워드 아트 컴포넌트를 파싱한다.
		this.parseWordArtComponent();

		// 처음 파싱하면, 전체 영역을 글립을 변형하기 전에 설정한다.
		// 글로벌 디멘젼 값을 제대로 반환하기 위해서.
		if (firstParsing) {
			// this.setGlobalDimension();
			this.setGlobalDimension(new Rectangle2D.Double(0, 0, width, height));
		}

		// raw Bounds를 설정한다.
		this.setRawBounds();

		// 스케일에 맞춰서 글립들을 확대/축소 한다.
		this.scaleGlyphs();

		// 글자 간격을 조정한다.
		this.parseGlyphGap();

		// 글립들을 정렬한다.
		this.alignGlyphs();

		// 수직 문자열 설정
		this.setGlyphsVertical();

		// 글립 매핑
		this.transformGlyphs();
		// 끝. 글립 매핑

		// 글립을 회전한다.
		this.rotateGlyphs();
		// 끝. 글립 회전

		// 그림자 글립을 생성한다.
		this.makeShadeGlyphs();

		// 필요한 데이터 만 리셋한다.
		this.resetNecessaryData();
	}

	public void transformGlyphs() {
		// 콭트롤 포인트를 재설정한다.

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

		// 스케일에 변화가 없으면 함수를 종료한다.
		if (sx == 1 && sy == 1) {
			return;
		}

		double fontLeading = this.getFontLeading();

		AffineTransform at;
		Shape[][] glyphs;

		at = AffineTransform.getTranslateInstance(x, y);

		at.scale(sx, sy);

		glyphs = wc.getGlyphs();
		// 글립들을 리스케일 한다.
		for (int j = 0; j < glyphs.length; j++) {
			for (int k = 0; k < glyphs[j].length; k++) {
				glyphs[j][k] = at.createTransformedShape(glyphs[j][k]);
			}
		}

		y += (wc.getLocalDimension().getHeight() + fontLeading);

		// 리스케일 후에는 글로벌 디멘젼을 재 설정한다.
		this.setGlobalDimension();
		// 끝. 글로벌 디멘젼 재 설정
	}

	public void resetNecessaryData() {
		this.cornerRects = null;
		this.rotateCircles = null;
		this.mappingOutLines = null;
		this.resetControlPoints();
	}

	// 원(Raw) 영역을 설정한다.
	public void setRawBounds() {
		this.rawBounds = wordArtComponent.getLocalDimension();
	}

	public void setGlobalDimension() {
		if (this.wordArtComponent == null) {
			this.setGlobalDimension(new Rectangle2D.Double(0, 0, 1, 1));
			return;
		}

		// 글로벌 디멘젼을 설정.
		this.setGlobalDimension(wordArtComponent.getLocalDimension());
	}

	void setTransferGraphics2DToLocation() {
		Graphics2D g2 = this.getGraphics2D();

		// Transform to word art location.
		g2.setTransform(AffineTransform.getTranslateInstance(this.style.getLocationX(), this.style.getLocationY()));

		// 각도 만틈 회전한다.
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
	 * 워드 아트를 그래픽하게 그린다.
	 */
	public void drawWordArt() {
		// 첫 워드 아트 컴포넌트를 그리기 위해서 좌표 이동한다.
		this.setTransferGraphics2DToLocation();
		WordArtComponent wc = this.wordArtComponent;
		if (wc != null) {
			this.drawShadeGlyphs();
			wc.drawWrodArtComponent();
		}
	}

	/**
	 * 그림자를 그린다.
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
	 * 그림자 그립을 생성한다.
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
		// 전체 영역을 다시 설정하면서, 다시 파싱한다.
		// 글로벌 영역을 재 설정하기 위해 널로 설정한다.
		// this.style.setDimension( null );
		// 끝. 글로벌 영역 널로 설정
		this.parse(false);
		// 끝. 재 파싱
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

		// 정렬 타입을 원본과 같이 맞추어 준다.
		value.setAdjustmentType(this.getAdjustmentType());
		// 글립 간격을 원본과 같이 맞추어 준다.
		value.setGlyphGapRatio(this.getGlyphGapRatio());
		// 글로벌 디멘젼을 원본과 같이 맞추어 준다.
		value.setGlobalDimension(new Rectangle2D.Double(0, 0, w, h));
		// 수직 여부 설정
		value.setVertical(vertical);
		this.setVertical(vertical);

		// 글로벌 디멘젼의 변화 없이 리파싱한다.
		value.parse(false);

		// 첫 번째 콘트롤 포인트 복사
		ControlPoint cp = this.getControlPoint();
		if (cp != null) {
			value.setControlPoint(cp.create(this));
		} else {
			value.controlPointOne = null;
		}
		// 끝. 첫 번째 콘트롤 포인트 복사

		// 두 번째 콘트롤 포인트 복수
		cp = this.getSecondControlPoint();
		if (cp != null) {
			value.setSecondControlPoint(cp.create(this));
		} else {
			value.controlPointTwo = null;
		}
		// 끝. 두 번째 콘트롤 포인트 복사

		return value;
	}

	/**
	 * 그림자 글립을 지정한다.
	 *
	 * @param shadeGlyphs
	 *            지정할 그림자 글립
	 */
	public void setShadeGlyphs(Shape[][] shadeGlyphs) {
		this.shadeGlyphs = shadeGlyphs;
	}

	/**
	 * 그림자 글립을 리턴한다.
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

		// 워드 아트를 그린다.
		this.drawWordArt();
		// 끝. 워드 아트 그리기

		// 본 워드 아트가 선택되면,
		if (this.selectedWordArt()) {
			// 콘트롤 포인트가 선택되면, 매핑 외곽선을 먼저 그린다.
			// MOVE_CONTROL_POINT 모드는 한개의 워드 아트가 선택되었을 경우에만 설정된다.
			if (this.manager != null && this.manager.getWorkMode() == WordArtGroupManager.MOVE_CONTROL_POINT) {
				this.drawMappingOutLines();
			}
			// 끝. 외곽선 그리기
			if (manager.getWorkMode() == WordArtGroupManager.ROTATE_MODE) {
				this.setGraphics2D();
				// 회전 원을 그린다.
				this.drawRotateCircles();
				// 끝. 회전원 그리기
			} else {
				this.setGraphics2D();
				// 코너 사각형을 그린다.
				this.drawCornerRects();
				// 끝. 코너 사각형 그리기
				// 콘트롤 포인트를 그린다.
				if (this.manager != null && this.manager.getSelectedWordArtsNumber() == 1) {
					this.drawControlPoints();
				}
				// 끝. 콘트롤 포인트 그리기
			}

		}
	}

	public void drawControlPoints() {
		ControlPoint cp = this.getControlPoint();

		Graphics2D g2 = this.getGraphics2D();

		// transform to the location.
		this.setTransferGraphics2DToLocation();

		Shape cps; // 회전된 콘트롤 포인트

		// 첫 번째 콘트롤 포인트 그리기
		if (cp != null) {
			cps = this.getRotatedShape(cp.getRotatedShape());
			g2.setColor(Color.yellow);
			g2.fill(cps);
			g2.setColor(Color.black);
			g2.draw(cps);
		} else {
			return;
		}
		// 끝. 첫 번째 콘트롤 포인트 그리기

		// 두번째 콘트롤 포인트 그리
		// 웨이브 매핑 타입일 때만 두번째 콘트롤 포인트를 그린다.

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
		// 끝. 두번째 콘트롤 포인트 그리기
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
		// 첫 워드 아트 컴포넌트를 그리기 위해서 그래픽스의 좌표를 이동한다.
		this.setTransferGraphics2DToLocation();
		// 끝. 그래픽스 좌표 이동.

		// 매핑 외곽선을 그린다.
		Shape[] mappingOutLines = this.getMappingOutLines();
		// Shape [] mappingOutLines =
		// this.globalMappingType.getTransformedMappingOutLines( this );
		Graphics2D g2 = this.getGraphics2D();
		// 매핑 외곽선은 XOR 모드로 그린다.
		g2.setXORMode(this.xorColor);
		// 끝. XOR 모드 설정.
		g2.setColor(Color.black);
		g2.setStroke(dashedStroke);
		for (int i = 0; i < mappingOutLines.length; i++) {
			g2.draw(mappingOutLines[i]);
		}
		g2.setPaintMode();
		// 끝. 외곽선 그리기.
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

		// 마우스 좌표를 워드 아트 위치에 맞추어 이동
		Point2D p = new Point2D.Float(x, y);
		AffineTransform at = AffineTransform.getTranslateInstance(-this.style.getLocationX(), -this.style.getLocationY());
		p = at.transform(p, p);

		// 끝. 마우스 좌표 이동

		// 회전 원 검사
		if (this.selectedWordArt() && this.manager.getWorkMode() == WordArtGroupManager.ROTATE_MODE) {
			Shape[] circles = this.getRotateCircles();
			for (int i = 0; i < circles.length; i++) {
				if (circles[i].contains(p)) {
					return ROTATE_ON;
				}
			}
		}
		// 끝. 회전 원 검사.

		// 콘트롤 포인트 검사, 회전원 검사 다음에 콘트롤 포인트 검사를 하여야 한다(논리적으로).
		if (this.manager.getWorkMode() != WordArtGroupManager.ROTATE_MODE // 회전
				// 모드가
				// 아니고
				&& this.manager.getSelectedWordArtsNumber() == 1 // 선택 워드 아트
				// 갯수가 하나이고
				&& this.selectedWordArt()) { // 본 워드 아트가 선택되었을 경우만
			// 위상을 계산한다.
			// 첫 번째 콘트롤 포인트 위상 검사
			ControlPoint cp = this.getControlPoint();
			Shape cps = null;
			if (cp != null) {
				cps = this.getRotatedShape(cp.getRotatedShape());
			}
			if (cp != null && cps.contains(p)) {
				this.selectedControlPoint = cp;
				return WordArt.CONTROL_POINT;
			}
			// 끝. 첫 번째 콘트롤 포인트 위상 검사

			// 두 번째 콘트롤 포인트 위상 검사
			// 웨이브 매핑 타입 일때만 두번째 콘트롤 포인트 위상 검사를 한다.
			cp = this.getSecondControlPoint();
			if (cp != null) {
				cps = this.getRotatedShape(cp.getRotatedShape());
			}
			if (cp != null && this.mappingType instanceof WaveMappingType && cps.contains(p)) {
				this.selectedControlPoint = cp;
				return WordArt.CONTROL_POINT;
			}
			// 끝. 두 번째 콘트롤 포인트 위상 검사
		}
		// 끝. 콘트롤 포인트 검사

		// 코너 사각형 검사
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
		// 끝. 코너 사각형 검사.

		// 글립 도형 검사.
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
		// 끝. 글립 도형 검사.

		return EXT_OUT;
	}

	public Point2D reverseTransformPoint2D(Point2D p) {
		double radian = this.getWordArtStyle().getRotationRadianAngle();
		if (radian != 0) {
			Point2D center = this.getCenterPoint();

			// 음의 각도 만큼 중심정을 기준으로 재 변환한다.
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
				// 슆트키를 누른 채로 본 워드아트가 선택 목록에 없을 경우,
				// 선택 목록에 추가한다.
				manager.selectAppend(this);
			} else if (e.isShiftDown() && this.selectedWordArt()) {
				// 슆트키를 누른 채로 선택된 워드 아트를 다시 선택할 경우에는,
				// 선택 목록에서 제외한다.
				manager.deSelect(this);
			} else if (!e.isShiftDown() && !this.selectedWordArt() && selWAs.length < 2) {
				// 마우스가 오버하고, 슆트키가 눌러지지 않은채
				// 선택 목록의 갯수가 두개 이상이 아니 상태에서,
				// 본 워드아트를 선택하면, 본 워드아트 만을 선택 목록에 입력한다.
				manager.selectAlone(this);
			} else {
				// 만약 두개 이상의 워드 아트가 선택 되어 있으면 아무 일도 아니 한다.

			}
		} else { // 본 워드 아트가 선택된 상태에서 마우스가 오버하지 않으면서
			// 슆트키가 눌러 지지 않은 채로
			// 선택 목록의 갯수가 두개 이상이 아니면
			// 다른 워드 아트를 선택할 경우
			// 선택 워드 아트 목록에서 제외한다.
			if (!e.isShiftDown() && this.selectedWordArt() && selWAs.length < 2) {
				manager.deSelect(this);
			}
		}
	}

	// 변형된 워드 아트의 외곽선을 그린다.
	public void drawMappingOutLineMovedNrotatedNresizedBy(double dx, double dy, double radian, double dw, double dh) {

		// 변형하기 이전의 외곽선을 그린다.

		// 이전의 외곽선을 검출한다.
		Shape[] outLines = this.getMappingOutLines();
		// 끝. 이전 외곽선 검출.

		if (outLines == null) {
			return;
		}

		// 먼저 그래픽스를 기준점으로 이동한다.
		this.setTransferGraphics2DToLocation();

		Graphics2D g2 = this.getGraphics2D();

		g2.setXORMode(this.xorColor);
		g2.setColor(Color.black);
		g2.setStroke(this.dashedStroke);
		for (int i = 0; i < outLines.length; i++) {
			g2.draw(outLines[i]);
		}

		// 끝. 이동 전의 외곽선 그리기

		// x, y 만큼 이동한다.
		if (dx == 0 && dy == 0) {
		} else {
			this.moveBy(dx, dy);
		}
		// 끝. 이동

		// radian 만큼 회전한다.
		if (radian != 0) {
			this.rotateBy(radian);
		}
		// 끝. 회전

		// 리사이즈 한다.
		if (dw == 0 && dh == 0) {
		} else {
			this.resizeBy(dw, dh);
		}
		// 끝. 리사이즈

		// 이동한 외곽선을 그린다.

		// 먼저 그래픽스를 기준점으로 이동한다.
		this.setTransferGraphics2DToLocation();
		// 끝. 그래픽스 기준점 이동.

		// 다시 그릴 외곽선을 재 검출한다.
		this.setMappingOutLines(null);
		outLines = this.getMappingOutLines();
		// 끝. 외곽선 재 검출.

		for (int i = 0; i < outLines.length; i++) {
			g2.draw(outLines[i]);
		}
		g2.setPaintMode();

		// 끝. 이동한 외곽선 그리기.
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

		// 전체 영역은 변경하지 않고, 다시 파싱한다.
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