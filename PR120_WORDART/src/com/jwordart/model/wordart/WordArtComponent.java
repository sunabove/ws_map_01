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
	private WordArt wordArt; // ���� ���� ��Ʈ
	private String[] strings; // ���ڿ�
	// private WordArtMappingType localMappingType; // �κ� ���� ����
	private Shape glyphs[][]; // �۸� �ܰ���
	private Rectangle2D glyphsBounds[][]; // �۸� �ٿ����.
	private double[] lineYLocations; // N ��° ������ ��ġ.

	// OutLines.
	// private Shape [] mappingOutLines = new Shape[2]; // ���� �ƿ� ����

	public WordArtComponent(WordArt wa, String str) {
		this.wordArt = wa;
		// ���� ���� Ÿ�� ����
		// this.setLocalMappingType( mt );
		// ��. ���� ���� Ÿ�� ����

		// �Ľ��� ���� Ÿ�Կ� �����Ѵ�.
		this.parseString(str);
		// ��. �Ľ�

		// ���� ���� ���� �Ľ�
		this.parseTheSameCharacterHeight();
		// ��. ���� ���� ���� �Ľ�
	}

	public void transformGlyphs() {
		// ���� �ܰ����� ���� Ÿ�Կ� ���� �޶�����.
		this.getMappingType().transformGlyphs(this);
		// ��. ���� �ܰ��� ����
	}

	public WordArtMappingType getMappingType() {
		return this.wordArt.getMappingType();
	}

	public WordArt getWordArt() {
		return this.wordArt;
	}

	// ���� ���ڿ� ����
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

	// i ��° ���� j ��° ������ ���� ���� �Ǻ�
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

		Shape[][] glyphs = this.glyphs; // �۸���
		String[] strings = this.strings; // ���ڿ���
		double w = this.wordArt.getGlobalDimension().getWidth(); // ����� ��
		Shape gl; // ������ �۸�
		Rectangle2D gb, glb; // �� �۸� �� ������ �۸� �ٿ����
		double lw, dw; // �� ������, �� ���� ����
		String string;
		for (int i = 0; i < glyphs.length; i++) {
			string = strings[i];
			// �����̸� ���� �����ͷ��̼����� �Ѿ��.
			if (string.trim().length() == 0) {
				continue;
			}
			gl = glyphs[i][glyphs[i].length - 1];
			glb = gl.getBounds2D();
			lw = glb.getX() + glb.getWidth();
			dw = w - lw;
			// ������ ���θ� ���� �����ͷ��̼����� �Ѿ��.
			if (dw == 0) {
				continue;
			}
			// �� ���ڰ� ������ ���� ������ �ϰ�,
			// ������ ���� ������ �Ѵ�.
			if (string.indexOf(' ') >= 0 || string.indexOf('\t') >= 0) {
				// ���� ����.
				// �� ���� ���� ����
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
				// ��. �� ���� ���� ����
				// �۸� �� ���
				/*
				 * double gbws = 0; for(int j = 0; j < glyphs[i].length; j ++ ) {
				 * c = string.charAt( j ); if( c == ' ' || c == '\t' ) {
				 * continue; } else { gb = glyphs[i][j].getBounds2D(); gbws +=
				 * gb.getWidth(); if( j != glyphs[i].length -1 ) { char cn =
				 * string.charAt( j + 1 ); if( cn == ' ' || c == '\t' ) { } else {
				 * Rectangle2D gnb = glyphs[i][j + 1].getBounds2D(); gbws += (
				 * gnb.getX() - gb.getX() - gb.getWidth() ); } } } }
				 */
				// ��. �۸� �� ���
				// ���� ����
				// double margin = lw - gbws; // ���� ���� ����
				// double unitMargin = margin/spaceNum; // ���� �����̽� ����
				double unitMargin = dw / spaceNum; // ���� �����̽� ����
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
				// ��. ���� ����
			} else {
				// ���� ����
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
				// ��. ���� ����
			}
		}
	}

	public void alignGlyphsOnBoth() {

		Shape[][] glyphs = this.glyphs;
		double w = this.wordArt.getGlobalDimension().getWidth();
		double gbws = 0.0, lw = 0.0, dw = 0.0, orgX = 0, newX = 0, sx = 1.0;
		// �۸�����, ������, ������ ����, ���� �۸� ��ġ, �� �۸� ��ġ, ������ X
		double nextGlyphGap = 0.0; // ���� �۸� ��
		Shape g, gl; // ���� �۸�, ������ �۸�
		Rectangle2D gb, glb; // ���� �۸� �� ������ �۸� �ٿ����
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
		double y, py, fontLeading = this.wordArt.getFontLeading(); // ���� ��, ����
		// �� ����, ��Ʈ
		// ����.
		double sy = -1; // Y ������ ��
		Shape g; // �۸�
		double gy; // �۸� X ��ǥ ��.
		Rectangle2D gb; // �۸� �ٿ����
		AffineTransform at; // ��ȯ ��ü
		for (int i = 0; i < glyphs.length; i++) {
			y = lineYLocations[i]; // ���� �� ���� ����.
			// ���� �� ���� ����
			if (i == 0) {
				// ù ���� ���
				py = 0;
			} else {
				// ù ���� �ƴ� ���
				py = lineYLocations[i - 1];
			}
			for (int j = 0; j < glyphs[i].length; j++) {
				g = glyphs[i][j];
				gb = gbs[i][j];
				gy = gb.getY() + gb.getHeight(); // �۸��� Y �� ����, �۸��� Y ���� �����
				// ��ǥ���̰�,
				// ������ Y ���� �ϴ��� ��ǥ���̹Ƿ�,
				// �۸��� Y ���� �۸��� ���� ��ŭ ��������,
				// ������ Y ���� ���� �ǹ̸� ������ �Ѵ�.
				if (i == 0) {
					// ù ���� �ƴ� ���, ��Ʈ ������ �������� �ʰ� Y ������ ���� �����Ѵ�.
					sy = (y - py) / gb.getHeight();
				} else {
					// ù ���� �ƴ� ���, ��Ʈ ������ �����Ͽ� Y ������ ���� �����Ѵ�.
					sy = (y - py - fontLeading) / gb.getHeight();
				}
				// ��ȯ ��ü ���� �� ��ȯ
				// �������� ��ȭ��Ų��.
				// ������ ��ȭ�� ���� �������� ���� ��Ų��.
				// ����, ���� ������ Y ���� �ű��, �������� ��ȭ��Ű��,
				// �ٽ� ����ġ�� Y ���� �����Ѵ�.
				// �� ��, �۸��� ���� ��ġ�� ���� ���ڿ� ������ ��쿡��
				// �۸��� ���� Y ���� �ƴϰ�, ������ Y ���� �Ǿ�����.
				at = AffineTransform.getTranslateInstance(0, y);
				at.scale(1.0, sy);
				at.translate(0, -gy);
				g = at.createTransformedShape(g);
				glyphs[i][j] = g; // ��ȯ�� �۸��� �Ҵ�.
				// ��. ��ȯ ��ü ���� �� ��ȯ
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

		// ���� �ܰ��� ����� ����
		double h = 0;
		// �۸� ������ ����
		AffineTransform at;

		for (int j = 0; j < tokenNum; j++) {
			this.strings[j] = tokenizer.nextToken();
			// ���� ���ڴ� �����̽� �ϳ��� ��ġ�Ѵ�. �ڵ��� ������ ���ؼ�.
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
			// ���� �Ͱ��� ���� ���� �� �۸� ���� ����
			this.glyphs[j] = new Shape[string.length()];
			Shape s; // �۸� ����
			Rectangle2D sb; // �۸� ���� �ٿ����
			Point2D p; // �۸� ��ġ
			// �۸� �ٿ����
			this.glyphsBounds[j] = new Rectangle2D[string.length()];

			double xDiff = 0; // X �� ����. ���� �ٿ������ ���߱� ���ؼ�.
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

			// �ֻ�� �۸� �� �۸� �ٿ���� ��ġ ����
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
			// ��. �ֻ�� �۸� �� �۸� �ٿ���� ��ġ ����

			// �۸� �� �۸� �ٿ���� ��ġ ����
			if (j >= 0) {
				double maxY = this.glyphsBounds[j][0].getY() + this.glyphsBounds[j][0].getHeight();
				double by;
				for (int i = 0; i < string.length(); i++) {
					by = this.glyphsBounds[j][i].getY() + this.glyphsBounds[j][i].getHeight();
					maxY = (maxY > by) ? maxY : by;
				}
				h = maxY;
			}
			// ��. �۸� �� �׸� �ٿ���� ��ġ ����.

			// N ��° ������ Y ��ǥ �� ���
			this.lineYLocations[j] = h;
			// ��. Y ��ǥ �� ���

			// ��. ���� ���� �۸� �� �۸� �ٿ���� ��ġ ����
			h += fontLeading; // ���� ��Ʈ���� �ϰ��� ������ ���ؼ� ��Ʈ ������ ����Ѵ�.
			// ��. ���� �� ��ġ ����// ��. ���� ���� �۸� �� �۸� �ٿ���� ��ġ ����
		}
	}

	public Graphics2D getGraphics2D() {
		return this.wordArt.getGraphics2D();
	}

	public void drawWrodArtComponent() {
		// ä��⸦ �����Ѵ�.
		this.fillGlyphs();
		// �ܰ����� �׸���.
		this.drawGlyphs();
	}

	public void fillGlyphs() {

		// TODO 700 �����Ʈ �� �ڵ�

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
			// �۸� ä��⸦ ���� �ʴ´�.
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

		// TODO 701 �����Ʈ ����� �ڵ�

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
			// �� ���� �� ���̰� ������ ������ �׸��� �ʴ´�.
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