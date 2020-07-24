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

import com.ynhenc.gis.ui.resource.IconImage;

public class Icon_08_IconImage implements Icon {

	public Icon_08_IconImage(IconImage iconImage) {
		this.iconImage = iconImage;
	}

	public int getIconHeight() {
		return this.iconImage.getHeight();
	}

	public int getIconWidth() {
		return this.iconImage.getWidth();
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

		int width = size.width;
		int height = size.height;

		if (false) {
			Rectangle2D rect = new Rectangle2D.Double(0, 0, width, height);

			g2.setColor(Color.white);
			g2.fill(rect);
		}

		IconImage iconImage = this.iconImage;

		if (iconImage != null) {
			BufferedImage bfrImage = iconImage.getBfrImage();

			if (bfrImage != null) {
				g2.drawImage(bfrImage, 0, 0, null);
			}
		}

	}

	private IconImage iconImage;

	private boolean selected;
}