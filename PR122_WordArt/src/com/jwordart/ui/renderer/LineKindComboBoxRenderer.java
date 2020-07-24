/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.ui.renderer;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;

public class LineKindComboBoxRenderer extends JLabel implements ListCellRenderer {

	private ImageIcon[] icons = new ImageIcon[8];
	private BasicStroke[] strokes = new BasicStroke[] { new BasicStroke(2),
			new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2, 2 }, 0),
			new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 4, 3 }, 0),
			new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9, 4 }, 0),
			new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9, 4, 4, 4 }, 0),
			new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 14, 4 }, 0),
			new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 14, 4, 4, 4 }, 0),
			new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 14, 4, 4, 4, 4, 4 }, 0) };
	private int selIndex = 0;

	public LineKindComboBoxRenderer() {
		try {
			Image iconImage;
			Graphics2D g2;
			int w = 80, h = 12;
			Rectangle2D rect;

			Line2D dashedLine = new Line2D.Double(2, h / 2, w - 2, h / 2);

			for (int i = 0; i < icons.length; i++) {
				iconImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				g2 = (Graphics2D) iconImage.getGraphics();
				rect = new Rectangle2D.Double(0, 0, w, h);
				g2.setColor(Color.white);
				g2.fill(rect);
				g2.setColor(Color.black);
				g2.setStroke(this.strokes[i]);
				g2.draw(dashedLine);
				this.icons[i] = new ImageIcon(iconImage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalAlignment(SwingConstants.CENTER);
		this.setOpaque(true);
		// setBorder(new javax.swing.border.EmptyBorder (1, 1, 1, 1));
	}

	public BasicStroke getStroke(int i) {
		if (i >= this.strokes.length) {
			return this.strokes[0];
		} else {
			return this.strokes[i];
		}
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		try {
			if (index == -1) {
				this.setIcon(this.icons[selIndex]);
			} else {
				this.setIcon(this.icons[index]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public void setSelectedIndex(int i) {
		this.selIndex = i;
	}
}