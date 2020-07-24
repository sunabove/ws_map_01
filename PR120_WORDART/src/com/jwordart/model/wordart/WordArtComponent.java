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
import java.awt.font.*;

import com.jwordart.model.fillEffect.*;
import com.jwordart.model.wordart.mappingType.*;
import com.jwordart.util.*;

public class WordArtComponent {
	private WordArt wordArt; // 상위 워드 아트
	private String[] strings; // 문자열
	// private WordArtMappingType localMappingType; // 부분 매핑 형식
	private Shape glyphs[][]; // 글립 외곽선
	private Rectangle2D glyphsBounds[][]; // 글립 바운더리.
	private double[] lineYLocations; // N 번째 라인의 위치.

	// OutLines.
	// private Shape [] mappingOutLines = new Shape[2]; // 매핑 아웃 라인

	public WordArtComponent(WordArt wa, String str) {
		this.wordArt = wa;
		// 지역 매핑 타잎 설정
		// this.setLocalMappingType( mt );
		// 끝. 지역 매핑 타잎 설정

		// 파싱은 매핑 타입에 무관한다.
		this.parseString(str);
		// 끝. 파싱

		// 같은 문자 높이 파싱
		this.parseTheSameCharacterHeight();
		// 끝. 같은 문자 높이 파싱
	}

	public void transformGlyphs() {
		// 매핑 외곽선음 매핑 타입에 따라 달라진다.
		this.getMappingType().transformGlyphs(this);
		// 끝. 매핑 외과선 매핑
	}

	public WordArtMappingType getMappingType() {
		return this.wordArt.getMappingType();
	}

	public WordArt getWordArt() {
		return this.wordArt;
	}

	// 수직 문자열 설정
	public void setGlyphsVertical() {
		if (!this.wordArt.isVertical()) {
			return;
		}
		Shape[][] glyphs = this.glyphs;
		for (int i = 0; i < glyphs.length; i++) {
			double orgX = 0, orgY = 0;
			Rectangle2D gb;
			for (int j = 0; j < glyphs[i].length; j++) {
				if (this.isEnglishCharacter(i, j)) {
					continue;
				}
				gb = glyphs[i][j].getBounds2D();
				double w = gb.getWidth(), h = gb.getHeight();
				if (w == 0 || h == 0) {
					continue;
				}
				orgX = gb.getX();
				orgY = gb.getY();
				AffineTransform at = AffineTransform.getTranslateInstance(orgX, orgY + h);
				at.rotate(-Math.PI / 2.0);
				at.scale(h / w, w / h);
				at.translate(-orgX, -orgY);
				glyphs[i][j] = at.createTransformedShape(glyphs[i][j]);
			}
		}
	}

	// i 번째 줄의 j 번째 문자의 영문 여부 판별
	public boolean isEnglishCharacter(int i, int j) {
		char c = this.strings[i].charAt(j);
		if (c < 127) {
			return true;
		} else {
			return false;
		}
	}

	public void rotateGlyphs() {
		AffineTransform at = this.wordArt.getRotateAffineTransform();
		Shape[][] glyphs = this.getGlyphs();
		for (int i = 0; i < glyphs.length; i++) {
			for (int j = 0; j < glyphs[i].length; j++) {
				glyphs[i][j] = at.createTransformedShape(glyphs[i][j]);
			}
		}
	}

	public void parseGlyphGap() {
		double glyphGapRatio = this.wordArt.getGlyphGapRatio();
		Shape[][] glyphs = this.glyphs;
		double w = this.wordArt.getGlobalDimension().getWidth();
		if (glyphGapRatio < 0.01) {
			glyphGapRatio = 0.01;
		}
		double scaleX = 1.0 / (glyphGapRatio);
		Shape g;
		Rectangle2D gb;
		for (int i = 0; i < glyphs.length; i++) {
			for (int j = 0; j < glyphs[i].length; j++) {
				g = glyphs[i][j];
				gb = g.getBounds2D();
				double gx = gb.getX(), gw = gb.getWidth(), gc = gx + gw / 2.0, newGx = gc - gw * scaleX / 2.0;
				AffineTransform at = AffineTransform.getTranslateInstance(newGx, 0);
				at.scale(scaleX, 1.0);
				at.translate(-gx, 0);
				glyphs[i][j] = at.createTransformedShape(glyphs[i][j]);
				g = glyphs[i][j];
				gb = g.getBounds2D();
				gx = gb.getX();
				gw = gb.getWidth();
				if (gx < 0) {
					at = AffineTransform.getTranslateInstance(-gx, 0);
					glyphs[i][j] = at.createTransformedShape(glyphs[i][j]);
				} else if (gx + gw > w) {
					at = AffineTransform.getTranslateInstance(-(gx + gw - w), 0);
					glyphs[i][j] = at.createTransformedShape(glyphs[i][j]);
				}
			}
		}
	}

	public void alignGlyphs() {
		int align = this.wordArt.getAdjustmentType();
		if (align == WordArt.LEFT) {
			return;
		} else if (align == WordArt.CENTER) {
			this.alignGlyphsOnCenter();
		} else if (align == WordArt.RIGHT) {
			this.alignGlyphsOnRight();
		} else if (align == WordArt.WORD) {
			this.alignGlyphsOnWord();
		} else if (align == WordArt.CHARACTER) {
			this.alignGlyphsOnCharacter();
		} else if (align == WordArt.BOTH) {
			this.alignGlyphsOnBoth();
		}
	}

	public void alignGlyphsOnWord() {

		Shape[][] glyphs = this.glyphs; // 글립들
		String[] strings = this.strings; // 문자열들
		double w = this.wordArt.getGlobalDimension().getWidth(); // 디멘젼 폭
		Shape gl; // 마지막 글립
		Rectangle2D gb, glb; // 현 글립 및 마지막 글립 바운더리
		double lw, dw; // 현 라인폭, 현 라인 마진
		String string;
		for (int i = 0; i < glyphs.length; i++) {
			string = strings[i];
			// 흰문자이면 다음 아이터레이션으로 넘어간다.
			if (string.trim().length() == 0) {
				continue;
			}
			gl = glyphs[i][glyphs[i].length - 1];
			glb = gl.getBounds2D();
			lw = glb.getX() + glb.getWidth();
			dw = w - lw;
			// 마진이 제로면 다음 아이터레이션으로 넘어간다.
			if (dw == 0) {
				continue;
			}
			// 흰 문자가 있으면 워드 정렬을 하고,
			// 없으면 문자 정렬을 한다.
			if (string.indexOf(' ') >= 0 || string.indexOf('\t') >= 0) {
				// 워드 정렬.
				// 흰 문자 갯수 산출
				int spaceNum = 0;
				char c;
				for (int j = 0; j < string.length(); j++) {
					c = string.charAt(j);
					if (c == ' ') {
						spaceNum++;
					} else if (c == '\t') {
						spaceNum += 2;
					}
				}
				// 끝. 흰 문자 갯수 산출
				// 글립 폭 계산
				/*
				 * double gbws = 0; for(int j = 0; j < glyphs[i].length; j ++ ) {
				 * c = string.charAt( j ); if( c == ' ' || c == '\t' ) {
				 * continue; } else { gb = glyphs[i][j].getBounds2D(); gbws +=
				 * gb.getWidth(); if( j != glyphs[i].length -1 ) { char cn =
				 * string.charAt( j + 1 ); if( cn == ' ' || c == '\t' ) { } else {
				 * Rectangle2D gnb = glyphs[i][j + 1].getBounds2D(); gbws += (
				 * gnb.getX() - gb.getX() - gb.getWidth() ); } } } }
				 */
				// 끝. 글립 폭 계산
				// 워드 정렬
				// double margin = lw - gbws; // 워드 정렬 마진
				// double unitMargin = margin/spaceNum; // 단위 스페이스 마진
				double unitMargin = dw / spaceNum; // 단위 스페이스 마진
				double xDiff = 0;
				for (int j = 0; j < glyphs[i].length; j++) {
					c = string.charAt(j);
					if (c == ' ') {
						xDiff += unitMargin;
						continue;
					} else if (c == '\t') {
						xDiff += (2 * unitMargin);
						continue;
					}
					gb = glyphs[i][j].getBounds2D();
					AffineTransform at = AffineTransform.getTranslateInstance(xDiff, 0);
					glyphs[i][j] = at.createTransformedShape(glyphs[i][j]);
				}
				// 끝. 워드 정렬
			} else {
				// 문자 정렬
				double xTrans = 0;
				for (int j = 0; j < glyphs[i].length; j++) {
					gb = glyphs[i][j].getBounds2D();
					if (glyphs[i].length == 1) {
						xTrans = (w - gb.getWidth()) / 2.0;
					} else {
						xTrans = (w - lw) / (glyphs[i].length - 1) * j;
					}
					AffineTransform at = AffineTransform.getTranslateInstance(xTrans, 0);
					glyphs[i][j] = at.createTransformedShape(glyphs[i][j]);
				}
				// 끝. 문자 정렬
			}
		}
	}

	public void alignGlyphsOnBoth() {

		Shape[][] glyphs = this.glyphs;
		double w = this.wordArt.getGlobalDimension().getWidth();
		double gbws = 0.0, lw = 0.0, dw = 0.0, orgX = 0, newX = 0, sx = 1.0;
		// 글립폭합, 라인폭, 현라인 마진, 현재 글립 위치, 새 글립 위치, 스케일 X
		double nextGlyphGap = 0.0; // 다음 글립 갭
		Shape g, gl; // 현재 글립, 마지막 글립
		Rectangle2D gb, glb; // 현재 글립 및 마지막 글립 바운더리
		for (int i = 0; i < glyphs.length; i++) {
			gl = glyphs[i][glyphs[i].length - 1];
			glb = gl.getBounds2D();
			lw = glb.getX() + glb.getWidth();
			gbws = 0;
			for (int j = 0; j < glyphs[i].length; j++) {
				gbws += glyphs[i][j].getBounds2D().getWidth();
			}
			dw = w - lw;

			if (dw == 0) {
				continue;
			}
			if (lw == 0) {
				continue;
			}
			for (int j = 0; j < glyphs[i].length; j++) {
				if (glyphs[i].length == 1) {
					g = glyphs[i][j];
					gb = g.getBounds2D();
					sx = w / lw;
					orgX = gb.getX();
					newX = 0;
				} else {
					g = glyphs[i][j];
					gb = g.getBounds2D();
					sx = 1.0 + dw / gbws;
					orgX = gb.getX();
					if (j == 0) {
						newX = 0;
					} else {
						newX = glyphs[i][j - 1].getBounds2D().getX() + glyphs[i][j - 1].getBounds2D().getWidth() + nextGlyphGap;
					}
					if (j != glyphs[i].length - 1) {
						nextGlyphGap = glyphs[i][j + 1].getBounds2D().getX() - gb.getX() - gb.getWidth();
					}

				}
				AffineTransform at = AffineTransform.getTranslateInstance(newX, 0);
				at.scale(sx, 1.0);
				at.translate(-orgX, 0);
				glyphs[i][j] = at.createTransformedShape(glyphs[i][j]);
			}
		}
	}

	public void alignGlyphsOnCharacter() {
		Shape[][] glyphs = this.glyphs;
		double w = this.wordArt.getGlobalDimension().getWidth();
		double lw = 0, xTrans = 0;
		Shape g;
		Rectangle2D gb;
		for (int i = 0; i < glyphs.length; i++) {
			g = glyphs[i][glyphs[i].length - 1];
			gb = g.getBounds2D();
			lw = gb.getX() + gb.getWidth();
			xTrans = w - lw;
			if (xTrans == 0) {
				continue;
			}
			for (int j = 0; j < glyphs[i].length; j++) {
				if (glyphs[i].length == 1) {
					xTrans = (w - gb.getWidth()) / 2.0;
				} else {
					xTrans = (w - lw) / (glyphs[i].length - 1) * j;
				}
				AffineTransform at = AffineTransform.getTranslateInstance(xTrans, 0);
				glyphs[i][j] = at.createTransformedShape(glyphs[i][j]);
			}
		}
	}

	public void alignGlyphsOnCenter() {
		Shape[][] glyphs = this.glyphs;
		double w = this.wordArt.getGlobalDimension().getWidth();
		double lw = 0, xTrans = 0;
		Shape g;
		Rectangle2D gb;
		for (int i = 0; i < glyphs.length; i++) {
			g = glyphs[i][glyphs[i].length - 1];
			gb = g.getBounds2D();
			lw = gb.getX() + gb.getWidth();
			xTrans = (w - lw) / 2.0;
			if (xTrans == 0) {
				continue;
			}
			AffineTransform at = AffineTransform.getTranslateInstance(xTrans, 0);
			for (int j = 0; j < glyphs[i].length; j++) {
				glyphs[i][j] = at.createTransformedShape(glyphs[i][j]);
			}
		}
	}

	public void alignGlyphsOnRight() {
		Shape[][] glyphs = this.glyphs;
		double w = this.wordArt.getGlobalDimension().getWidth();
		double lw = 0, xTrans = 0;
		Shape g;
		Rectangle2D gb;
		for (int i = 0; i < glyphs.length; i++) {
			g = glyphs[i][glyphs[i].length - 1];
			gb = g.getBounds2D();
			lw = gb.getX() + gb.getWidth();
			xTrans = (w - lw);
			if (xTrans == 0) {
				continue;
			}
			AffineTransform at = AffineTransform.getTranslateInstance(xTrans, 0);
			for (int j = 0; j < glyphs[i].length; j++) {
				glyphs[i][j] = at.createTransformedShape(glyphs[i][j]);
			}
		}
	}

	public Shape[][] getGlyphs() {
		return this.glyphs;
	}

	public Rectangle2D getGlyphsBounds() {
		double xmin = 0, ymin = 0, xmax = 0, ymax = 0;
		Shape[][] glyphs = this.getGlyphs();
		Rectangle2D bounds;
		if (glyphs != null && glyphs[0] != null && glyphs[0][0] != null) {
			bounds = glyphs[0][0].getBounds2D();
			xmin = bounds.getX();
			ymin = bounds.getY();
			xmax = xmin;
			ymax = ymin;
		}
		double x, y;
		for (int i = 0; i < glyphs.length; i++) {
			for (int j = 0; j < glyphs[i].length; j++) {
				bounds = glyphs[i][j].getBounds2D();
				x = bounds.getX();
				y = bounds.getY();
				xmin = (x < xmin) ? x : xmin;
				ymin = (y < ymin) ? y : ymin;
				xmax = (x + bounds.getWidth() > xmax) ? x + bounds.getWidth() : xmax;
				ymax = (y + bounds.getHeight() > ymax) ? y + bounds.getHeight() : ymax;
			}
		}
		return new Rectangle2D.Double(xmin, ymin, xmax - xmin, ymax - ymin);
	}

	public void parseTheSameCharacterHeight() {
		if (!this.wordArt.isSameCharacterHeight()) {
			return;
		}
		double[] lineYLocations = this.lineYLocations;
		Shape[][] glyphs = this.glyphs;
		Rectangle2D[][] gbs = this.glyphsBounds;
		double y, py, fontLeading = this.wordArt.getFontLeading(); // 현재 줄, 이전
		// 줄 높이, 폰트
		// 리딩.
		double sy = -1; // Y 스케일 값
		Shape g; // 글립
		double gy; // 글립 X 좌표 값.
		Rectangle2D gb; // 글립 바운더리
		AffineTransform at; // 변환 객체
		for (int i = 0; i < glyphs.length; i++) {
			y = lineYLocations[i]; // 현재 줄 높이 설정.
			// 이전 줄 높이 설정
			if (i == 0) {
				// 첫 줄인 경우
				py = 0;
			} else {
				// 첫 줄이 아닌 경우
				py = lineYLocations[i - 1];
			}
			for (int j = 0; j < glyphs[i].length; j++) {
				g = glyphs[i][j];
				gb = gbs[i][j];
				gy = gb.getY() + gb.getHeight(); // 글립의 Y 값 지정, 글립의 Y 값은 상단의
				// 좌표값이고,
				// 라인의 Y 값은 하단의 좌표값이므로,
				// 글립의 Y 값은 글립의 높이 만큼 증가시켜,
				// 라인의 Y 값과 같은 의미를 가지게 한다.
				if (i == 0) {
					// 첫 줄이 아닌 경우, 폰트 리딩을 보정하지 않고 Y 스케일 값을 설정한다.
					sy = (y - py) / gb.getHeight();
				} else {
					// 첫 줄이 아닌 경우, 폰트 리딩을 보정하여 Y 스케일 값을 설정한다.
					sy = (y - py - fontLeading) / gb.getHeight();
				}
				// 변환 객체 생성 및 변환
				// 스케일을 변화시킨다.
				// 스케일 변화는 적용 역순으로 생성 시킨다.
				// 먼저, 제로 값으로 Y 값을 옮기고, 스케일을 변화시키고,
				// 다시 제위치로 Y 값을 설정한다.
				// 이 때, 글립의 최종 위치는 같은 문자열 높이의 경우에는
				// 글립의 이전 Y 값이 아니고, 라인의 Y 값이 되어진다.
				at = AffineTransform.getTranslateInstance(0, y);
				at.scale(1.0, sy);
				at.translate(0, -gy);
				g = at.createTransformedShape(g);
				glyphs[i][j] = g; // 변환된 글립을 할당.
				// 끝. 변환 객체 생성 및 변환
			}
		}
	}

	public void parseString(String str) {

		java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(str, "\n");
		int tokenNum = tokenizer.countTokens();

		this.strings = new String[tokenNum];
		this.glyphs = new Shape[tokenNum][];
		this.glyphsBounds = new Rectangle2D[tokenNum][];
		this.lineYLocations = new double[tokenNum];

		// Glyph Vector Setting.
		Graphics2D g2 = this.wordArt.getGraphics2D();
		Font font = this.wordArt.getFont();
		FontRenderContext frc = g2.getFontRenderContext();
		double fontLeading = this.wordArt.getFontLeading();
		GlyphVector gv;

		// 매핑 외곽선 추출용 변수
		double h = 0;
		// 글립 변형용 변수
		AffineTransform at;

		for (int j = 0; j < tokenNum; j++) {
			this.strings[j] = tokenizer.nextToken();
			// 공백 문자는 스페이스 하나로 대치한다. 코딩의 편리성을 위해서.
			if (this.strings[j].equals("")) {
				this.strings[j] = " ";
			}

			String string = this.strings[j];

			while (string.indexOf(' ') >= 0) {
				string = string.replace(' ', 'I');
			}

			while (string.indexOf('\t') >= 0) {
				string = string.replace(' ', 'I');
			}

			gv = font.createGlyphVector(frc, string);
			h += gv.getOutline().getBounds2D().getHeight();
			// 매핑 와곽선 높이 추출 및 글립 도형 설정
			this.glyphs[j] = new Shape[string.length()];
			Shape s; // 글립 도형
			Rectangle2D sb; // 글립 도형 바운더리
			Point2D p; // 글립 위치
			// 글립 바운더리
			this.glyphsBounds[j] = new Rectangle2D[string.length()];

			double xDiff = 0; // X 값 보정. 좌측 바운더리에 맞추기 위해서.
			for (int i = 0; i < string.length(); i++) {
				s = gv.getGlyphOutline(i);
				p = gv.getGlyphPosition(i);
				if (i == 0) {
					xDiff = s.getBounds2D().getX();
				}
				at = AffineTransform.getTranslateInstance(p.getX() - xDiff, h);
				s = at.createTransformedShape(s);
				this.glyphs[j][i] = s;
				this.glyphsBounds[j][i] = s.getBounds2D();
			}

			// 최상단 글립 및 글립 바운더리 위치 보정
			if (j == 0) {
				double minY = this.glyphsBounds[j][0].getY(), by;
				for (int i = 0; i < string.length(); i++) {
					by = this.glyphsBounds[j][i].getY();
					minY = (minY < by) ? minY : by;
				}
				at = AffineTransform.getTranslateInstance(0, -minY);
				for (int i = 0; i < string.length(); i++) {
					this.glyphs[j][i] = at.createTransformedShape(this.glyphs[j][i]);
					sb = this.glyphsBounds[j][i];
					sb.setRect(sb.getX(), sb.getY() - minY, sb.getWidth(), sb.getHeight());
				}
			}
			// 끝. 최상단 글립 및 글립 바운더리 위치 보정

			// 글립 및 글립 바운더리 위치 보정
			if (j >= 0) {
				double maxY = this.glyphsBounds[j][0].getY() + this.glyphsBounds[j][0].getHeight();
				double by;
				for (int i = 0; i < string.length(); i++) {
					by = this.glyphsBounds[j][i].getY() + this.glyphsBounds[j][i].getHeight();
					maxY = (maxY > by) ? maxY : by;
				}
				h = maxY;
			}
			// 끝. 글립 및 그립 바운더리 위치 보정.

			// N 번째 라인의 Y 좌표 값 기록
			this.lineYLocations[j] = h;
			// 끝. Y 좌표 값 기록

			// 끝. 다음 줄의 글립 및 글립 바운더리 위치 보정
			h += fontLeading; // 워드 아트와의 일관선 유지를 위해서 폰트 리딩을 사용한다.
			// 끝. 다음 줄 위치 보정// 끝. 다음 줄의 글립 및 글립 바운더리 위치 보정
		}
	}

	public Graphics2D getGraphics2D() {
		return this.wordArt.getGraphics2D();
	}

	public void drawWrodArtComponent() {
		// 채우기를 먼저한다.
		this.fillGlyphs();
		// 외곽선을 그린다.
		this.drawGlyphs();
	}

	public void fillGlyphs() {

		// TODO 700 워드아트 필 코드

		WordArt wordArt = this.wordArt;
		Graphics2D g2 = wordArt.getGraphics2D();

		WordArtStyle wordArtStyle = wordArt.getWordArtStyle();
		Color fillColor = wordArtStyle.getFillColor();
		Paint gradientPaint = wordArtStyle.getGradientPaint();
		TexturePaint texturePaint = wordArtStyle.getTexturePaint();

		if (fillColor != null) {
			g2.setColor(fillColor);
		} else if (gradientPaint != null) {
			g2.setPaint(gradientPaint);
		} else if (texturePaint != null) {
			g2.setPaint(texturePaint);
		} else {
			// 글립 채우기를 하지 않는다.
			return;
		}

		Shape[][] glyphs = this.glyphs;
		String[] strings = this.strings;
		char c;
		for (int j = 0; j < glyphs.length; j++) {
			for (int i = 0; i < glyphs[j].length; i++) {
				c = strings[j].charAt(i);
				if (c == ' ' || c == '\t') {
				} else {
					g2.fill(glyphs[j][i]);
				}
			}
		}
	}

	public void drawGlyphs() {

		// TODO 701 워드아트 드로잉 코드

		WordArt wordArt = this.wordArt;
		Graphics2D g2 = wordArt.getGraphics2D();

		WordArtStyle wordArtStyle = wordArt.getWordArtStyle();

		Color lineColor = wordArtStyle.getLineColor();
		GraphicEffect lineTexture = wordArtStyle.getFillEffect_LineTexture();
		BasicStroke lineStroke = wordArtStyle.getLineStroke();

		if (lineColor != null) {
			g2.setColor(lineColor);
			if (lineStroke != null) {
				g2.setStroke(lineStroke);
			}
		} else if (lineTexture != null) {
			if (lineStroke != null) {
				g2.setStroke(lineStroke);
			}
			TexturePaint lineTp = lineTexture.getTexturePaint();
			if (lineTp != null) {
				g2.setPaint(lineTp);
			}
		} else {
			// 선 색과 선 무늬가 없으면 도형을 그리지 않는다.
			return;
		}

		Shape[][] glyphs = this.glyphs;
		String[] strings = this.strings;
		char c;

		for (int j = 0; j < glyphs.length; j++) {
			for (int i = 0; i < glyphs[j].length; i++) {
				c = strings[j].charAt(i);
				if (c == ' ' || c == '\t') {
				} else {
					g2.draw(glyphs[j][i]);
				}
			}
		}
	}

	public String[] getStrings() {
		return this.strings;
	}

	public Rectangle2D getLocalDimension() {
		return this.getGlyphsBounds();
	}
}