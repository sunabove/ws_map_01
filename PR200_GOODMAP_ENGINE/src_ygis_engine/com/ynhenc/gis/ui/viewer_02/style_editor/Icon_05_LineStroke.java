package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Icon_05_LineStroke implements Icon {

	public Icon_05_LineStroke(BasicStroke stroke) {
		this.stroke = stroke;
	}

	public int getIconHeight() {
		return height;
	}

	public int getIconWidth() {
		return width;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {

		Graphics2D g2 = (Graphics2D) g;

		g2.translate(x, y);
		if (selected) {
			paintIconReal(c, g2, x, y);
		} else {
			paintIconReal(c, g2, x, y);
		}
		g2.translate(-x, -y);

	}

	private void paintIconReal(Component c, Graphics2D g2, int x, int y) {

		Dimension size = c.getSize();

		this.width = size.width;
		this.height = size.height;

		int width = this.width;
		int height = this.height;

		Rectangle2D rect = new Rectangle2D.Double(0, 0, width, height);

		BasicStroke stroke = this.stroke;
		
		g2.setColor(Color.white);
		g2.fill(rect);

		if (stroke != null) {
			Line2D dashedLine = new Line2D.Double(2, height / 2, width - 2, height / 2);
			g2.setColor(Color.black);
			g2.setStroke(stroke);
			g2.draw(dashedLine);
		}

	}

	BasicStroke stroke;

	private int width = 10;

	private int height = 10;

	private boolean selected;
}