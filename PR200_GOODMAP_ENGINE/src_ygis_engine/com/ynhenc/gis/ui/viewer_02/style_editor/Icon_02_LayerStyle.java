package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.TexturePaint;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.jwordart.model.fillEffect.GraphicEffect;
import com.ynhenc.comm.DebugInterface;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.model.style.Style_05_General;

public class Icon_02_LayerStyle implements Icon, DebugInterface {

	public Icon_02_LayerStyle(Layer layer, int editMode) {
		this.layer = layer;
		this.editMode = editMode;
	}

	public int getIconWidth() {
		return size.width;
	}

	public int getIconHeight() {
		return size.height;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {

		Graphics2D g2 = (Graphics2D) g;

		g2.translate(x, y);
		if (selected) {
			this.paint_02_IconReal(c, g2);
		} else {
			this.paint_02_IconReal(c, g2);
		}
		g2.translate(-x, -y);
	}

	public void paint_02_IconReal(Component c, Graphics2D g2) {

		Dimension size = c.getSize();

		this.size = size;

		int width = size.width - 1, height = size.height - 1;

		Layer layer = this.layer;

		Style_05_General style = layer.getShapeStyle();

		int editMode = this.editMode;

		GraphicEffect graphicEffect = null;

		if (editMode == 0) {
			// 채우기 효과 편집 일 때......
			graphicEffect = style.getGraphicEffect_Fill();
		} else {
			// 선 효과 편집 일 때...
			graphicEffect = style.getGraphicEffect_Line();
		}

		if (graphicEffect != null) {
			graphicEffect = graphicEffect.create();
		}

		Color fillColor = style.getFillColor();
		Color lineColor = style.getLineColor();

		Rectangle2D rect = new Rectangle2D.Double(0, 0, width, height);

		if (editMode == 0) {
			this.paint_03_AreaFill(c, g2, rect, graphicEffect, fillColor);
		} else {
			BasicStroke lineStroke = style.getLineStroke() ;
			
			this.paint_04_LineDraw(c, g2, rect, graphicEffect, lineStroke,  lineColor);
		}

	}

	public void paint_03_AreaFill(Component c, Graphics2D g2, Rectangle2D rect,
			GraphicEffect graphicEffect, Color fillColor) {

		if (fillColor != null && graphicEffect == null) { 
			g2.setColor(fillColor);
			g2.fill(rect);
		} else if (graphicEffect != null) {
			graphicEffect.setBounds(rect.getWidth(), rect.getHeight());
			Paint gp = graphicEffect.getGradientPaint();
			if (gp != null) {
				g2.setPaint(gp); 
			} else {
				TexturePaint tp = graphicEffect.getTexturePaint();
				
				if( tp != null ) {
					g2.setPaint(tp);
				} 
			}
			g2.fill(rect);
		} else if( true ) {
			if (c instanceof JLabel) {
				JLabel label = (JLabel) c; 
				label.setText( "無");
			}
		}
	}

	public void paint_04_LineDraw(Component c, Graphics2D g2, Rectangle2D rect,
			GraphicEffect graphicEffect, BasicStroke lineStroke , Color lineColor) {
		
		double mh = rect.getHeight() / 2.0F ;

		Line2D line = new Line2D.Double( 1, mh, rect.getWidth() - 3 , mh );

		g2.setColor(Color.white);
		g2.fill(rect); 

		if (lineColor != null && graphicEffect == null) { 
			g2.setColor(lineColor);
			
			if( lineStroke != null ) {
				g2.setStroke( lineStroke );
			}
			g2.fill(rect);
			g2.draw(line);
		} else if (graphicEffect != null) {
			
			graphicEffect.setBounds(rect.getWidth(), rect.getHeight());
			
			if( lineStroke != null ) {
				g2.setStroke( lineStroke );
			}

			TexturePaint tp = graphicEffect.getTexturePaint();
			
			if ( tp != null ) {
				g2.setPaint(tp); 
			}
			g2.fill(rect);
			g2.draw( line );
			
		} else if( true ) {
			if (c instanceof JLabel) {
				JLabel label = (JLabel) c; 
				label.setText( "無");
			}
		}
	}

	private Dimension size = new Dimension(10, 10);

	private boolean selected;

	private Layer layer;

	private int editMode;

}