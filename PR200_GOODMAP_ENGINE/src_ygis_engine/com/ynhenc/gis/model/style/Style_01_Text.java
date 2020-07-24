package com.ynhenc.gis.model.style;

import java.awt.*;
import java.awt.image.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.text.*;
import java.util.*;

import com.ynhenc.comm.util.*;
import com.ynhenc.gis.model.shape.*;
import com.ynhenc.gis.ui.resource.IconImage;
import com.ynhenc.gis.ui.viewer_03.dbf_viewer.TableModelDbf;

public class Style_01_Text extends Style_00_Object {

	public IconImage getIconImage() {
		return this.iconImage;
	}

	public void setIconImage(IconImage iconImage) {
		this.iconImage = iconImage;
	}

	public AlignV getAlignV() {
		return this.alignV;
	}

	public void setAlignV(AlignV alignV) {
		this.alignV = alignV;
	}

	public AlignH getAlignH() {
		return this.alignH;
	}

	public void setAlignH(AlignH alignH) {
		this.alignH = alignH;
	}

	public boolean isUnderLine() {
		return this.underLine;
	}

	public void setUnderLine(boolean underLine) {
		this.underLine = underLine;
	}

	public String getFontFamily() {
		return this.fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
		this.font = null;
	}

	public int getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
		this.font = null;
	}

	public int getFontStyle() {
		return this.fontStyle;
	}

	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
		this.font = null;
	}

	public Color getFontColor() {
		return this.fontColor;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	public TableModelDbf getTableModel() {
		return this.tableModel;
	}

	public void setTableModel(TableModelDbf tableModel) {
		this.tableModel = tableModel;
	}

	public MetaDataLabel getMetaDataLabel() {
		return this.metaDataLabel;
	}

	public void setMetaDataLabel(MetaDataLabel metaDataLabel) {
		this.metaDataLabel = metaDataLabel;
	}

	public OffSet getOffSet() {
		return this.offSet;
	}

	public void setOffSet(OffSet offSet) {
		this.offSet = offSet;
	}

	@Override
	public boolean setGraphics_02_OutsideDraw(Graphics2D g, Projection projection) {

		Font font = this.getFont();

		if (font != null && this.fontColor != null) {
			g.setFont(font);
			g.setColor(this.fontColor);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean setGraphics_01_InsideFill(Graphics2D g, Projection projection) {

		return this.setGraphics_02_OutsideDraw(g, projection);

	}

	@Override
	public void paintShape_01_InsideFill(Graphics2D g, ShapeObject shapeObj, Projection projection, ShapeHash shapeHash) {

		// do nothing !!!!

	}

	@Override
	public void paintShape_02_OutsideDraw(Graphics2D g, ShapeObject shapeObj, Projection projection, ShapeHash shapeHash) {

		IconImage iconImage = this.getIconImage();

		Font font = this.getFont();

		FontRenderContext frc = g.getFontRenderContext();

		this.paintIconAndText(g, font, frc, shapeObj, iconImage, projection, this.alignH, this.alignV);

	}

	private void paintIconAndText(Graphics2D g, Font font, FontRenderContext frc, ShapeObject shapeObj, IconImage iconImage,
			Projection projection, AlignH alignH, AlignV alignV) {

		String labelText = this.getTextLabel(shapeObj);

		Poi poi = null;

		if (shapeObj instanceof PoiInterface) {
			PoiInterface shapePoi = (PoiInterface) shapeObj;
			poi = shapePoi.getPoi();
			labelText = poi.getTextLabel();
			iconImage = poi.getIconImage();
		}

		GeoPoint centroid = shapeObj.getCentroid();

		PntShort pixelPnt = projection.toGraphics(centroid);

		BufferedImage bfrImage = iconImage != null ? iconImage.getBfrImage() : null;

		int imageWidth = 0, imageHeight = 0; // 이미지 폭, 이미지 높이

		if (bfrImage != null) {
			// 아이콘 그리기.
			imageWidth = bfrImage.getWidth();
			imageHeight = bfrImage.getHeight();
			int ix = (int) (pixelPnt.x - imageWidth / 2.0);
			int iy = (int) (pixelPnt.y - imageHeight / 2.0);
			g.drawImage(bfrImage, ix, iy, null);
			// 끝. 아이콘 그리기.
		}

		if (this.isGood(labelText)) {

			// 이미지와 텍스트 사이의 마진.
			int imageMargin = imageWidth > 0 ? 2 : 0;
			LineMetrics lm = this.getLineMetrics(font, frc, labelText);
			Rectangle2D bounds = font.getStringBounds(labelText, frc);
			OffSet offSet = this.getOffSet();
			double textWidth = bounds.getWidth();
			double textHeight = bounds.getHeight();

			double textX = pixelPnt.x;
			double textY = pixelPnt.y + textHeight / 2.0 - lm.getDescent();

			if (alignH == null || alignH.equals(AlignH.CENTER)) {
				// 가운데 정렬.
				textX -= textWidth / 2.0 - offSet.getX();
			} else if (alignH.equals(AlignH.LEFT)) {
				// 좌측 정렬.
				textX += imageWidth / 2.0 + imageMargin + offSet.getX();
			} else if (alignH.equals(AlignH.RIGHT)) {
				// 우측 정
				textX -= imageWidth / 2.0 + imageMargin + textWidth + offSet.getX();
			}

			if (alignV == null || alignV.equals(AlignV.MIDDLE)) {
				// 중앙 정렬.
				textY += offSet.getY();
			} else if (alignV.equals(AlignV.TOP)) {
				// 상단 정렬.
				textY -= textHeight / 2.0 + imageHeight / 2.0 + imageMargin + offSet.getY();
			} else if (alignV.equals(AlignV.BOTTOM)) {
				// 하단 정렬.
				textY += textHeight / 2.0 + imageHeight / 2.0 + imageMargin + offSet.getY();
			}

			if (false) { // 후광 효과 그리기.
				this.drawStringWithDropShadow(g, font, frc, labelText, textX, textY);
			} else {
				if( poi != null ) {
					// paint text area bg color and line color
					Color bgColor = poi.getBgColor();
					Insets insets = new Insets( 0, 4, 4, 4 );
					float poiLineWidth = poi.getLineWidth();
					textY += 1.5*poiLineWidth;
					int x = (int) ( textX - insets.left - poiLineWidth ) ;
					int y = (int) ( textY - textHeight - insets.top - poiLineWidth ) ;
					int w = (int) ( textWidth + 2*poiLineWidth + insets.left + insets.right) ;
					int h = (int) ( textHeight +2*poiLineWidth + insets.bottom + insets.top );
					if( bgColor != null ) {
						g.setColor(bgColor);
						g.fillRoundRect( x, y, w, h, h/2, h/2 );
					}
					Color lineColor = poi.getLineColor();
					if( lineColor != null ) {
						//Stroke orgStroke = g.getStroke();
						Stroke stroke = new BasicStroke( poiLineWidth );
						g.setStroke( stroke );
						g.setColor(lineColor);
						g.drawRoundRect( x, y, w, h, h/2, h/2 );
						//g.setStroke(orgStroke);
					}
					// end of painting text area
				}
				// draw text
				if (poi == null || poi.getTextColor() == null) {
					g.setColor(this.fontColor);
				} else {
					g.setColor(poi.getTextColor());
				}
				g.drawString(labelText, (int) textX, (int) textY);
				// end of drawing text
			}

			if (this.isUnderLine()) {
				int lineY = (int) (textY + lm.getDescent() + 2);
				g.drawLine((int) textX, lineY, (int) (textX + textWidth), lineY);
			}

		}
	}

	private void drawStringWithDropShadow(Graphics2D g2, Font font, FontRenderContext frc, String labelText, double textX,
			double textY) {

		Stroke orgStroke = g2.getStroke();
		Paint orgPaint = g2.getPaint();

		TextLayout tl = new TextLayout(labelText, this.font, frc);

		final int tx = (int) (textX);
		final int ty = (int) (textY);

		AffineTransform transform = AffineTransform.getTranslateInstance(tx, ty);

		Shape shape = tl.getOutline(transform);
		Rectangle r = shape.getBounds();

		g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		g2.setColor(this.getFontColorComp());

		g2.draw(shape);

		g2.setStroke(orgStroke);
		g2.setPaint(orgPaint);

		g2.draw(r);

		g2.setColor(this.fontColor);

		g2.drawString(labelText, tx, ty );

	}

	private Color getFontColorComp() {
		return new Color(~this.fontColor.getRGB());
	}

	private void paintCentroid(Graphics2D g2, Projection projection, GeoPoint centroid) {
		Shape shape = centroid.createShapeDiamond(projection, centroid, 2);
		g2.draw(shape);
	}

	private String getTextLabel(ShapeObject shape) {
		int id = shape.getRecordNo();

		if (this.tableModel != null) {
			// 도형의 아이디는 1부터 인덱싱을 시작한다.
			// 테이블 모델에서 매핑하기 위해서는 -1 해준다.
			// 테이블 모델에서 인덱싱은 0 부터 시작한다.
			int colIdx = this.metaDataLabel.getColumnIndex();
			if( colIdx > -1 ) {
				return this.tableModel.getStringValueAt(id - 1, colIdx) ;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private Rectangle2D getTextBounds(Graphics2D g2, Font font, String text) {
		FontRenderContext frc = g2.getFontRenderContext();
		GlyphVector gv = font.createGlyphVector(frc, text);
		return gv.getVisualBounds();
	}

	private LineMetrics getLineMetrics(Font font, FontRenderContext frc, String text) {
		return font.getLineMetrics(text, frc);
	}

	@Override
	public String toString() {
		Font font = this.getFont();
		Color color = this.fontColor;
		String info = "";
		if (font != null) {
			info += ("fontName:" + font.getName() + "; " + "fontSize:" + font.getSize() + "; " + "fontStyle:" + font.getStyle() + "; ");
		} else {
			info += "font: null; ";
		}
		if (color != null) {
			info += ("fontColor:" + HtmlUtil.toHtmlColor(color) + "; ");
		} else {
			info += "fontColor: null; ";
		}
		return info;
	}

	public Font getFont() {

		if (this.font == null) {
			this.font = new Font(this.fontFamily, this.fontStyle, this.fontSize);
			// this.debug.println(this, "FONT = " + this.font);
		}

		return this.font;
	}

	public void setColor(Color color) {
		this.fontColor = color;
	}

	public Style_01_Text create() {

		Style_01_Text clone = new Style_01_Text(this.getFont(), this.getFontColor(), this.getOffSet(), this.getAlignH(), this
				.getAlignV());
		return clone;

	}

	private Style_01_Text(Font font, Color fontColor, OffSet offSet) {
		this(font, fontColor, offSet, AlignH.CENTER, AlignV.BOTTOM);
	}

	private Style_01_Text(Font font, Color fontColor, OffSet offSet, AlignH alignH, AlignV alignV) {

		this.fontFamily = font.getFamily();
		this.fontSize = font.getSize();
		this.fontStyle = font.getStyle();

		this.fontColor = fontColor;
		this.offSet = offSet;

		this.alignH = alignH;
		this.alignV = alignV;

	}

	// member
	private String fontFamily;
	private int fontSize;
	private int fontStyle;
	private boolean underLine;

	private transient Font font;

	private Color fontColor;

	private AlignV alignV;
	private AlignH alignH;

	private OffSet offSet;

	private IconImage iconImage;

	private transient MetaDataLabel metaDataLabel;
	private transient TableModelDbf tableModel;

	// static method

	public static Style_01_Text getStyle(String fontName, int fontStyle, Color fontColor, int fontSize) {

		Font font;
		if (fontName == null || fontName.equalsIgnoreCase("NULL")) {
			font = null;
		} else {
			font = new Font(fontName, fontStyle, fontSize);
		}

		OffSet offSet = new OffSet(0, 0);

		Style_01_Text textStyle = new Style_01_Text(font, fontColor, offSet);

		textStyle.setAlignV(AlignV.BOTTOM);
		textStyle.setAlignH(AlignH.CENTER);

		return textStyle;
	}

	// end of static methods.

}
