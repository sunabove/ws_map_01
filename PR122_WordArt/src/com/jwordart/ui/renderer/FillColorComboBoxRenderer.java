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
import java.awt.image.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.table.*;

import com.jwordart.model.fillEffect.GraphicEffect;
import com.jwordart.util.WordArtUtil;

public class FillColorComboBoxRenderer extends JLabel implements ListCellRenderer, TableCellRenderer {

	private static final long serialVersionUID = 5608506737574600440L;

	public void setGraphicEffect(GraphicEffect graphicEffect) {
		this.graphicEffect = graphicEffect;
	}

	public Color getFillColor() {
		if (this.graphicEffect != null) {
			return this.graphicEffect.getFirstGradientColor();
		} else {
			return null;
		}
	}

	public GraphicEffect getGraphicEffect() {
		return this.graphicEffect;
	}

	private void createIcon() {
		int w = 71, h = 12;
		this.iconImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) this.iconImage.getGraphics();
		Rectangle2D rect = new Rectangle2D.Double(0, 0, w, h);
		Color fillColor = this.getFillColor();
		if (fillColor != null && this.graphicEffect == null) {
			g2.setColor(fillColor);
			g2.fill(rect);
		} else if (this.graphicEffect != null) {
			this.graphicEffect.setBounds(w, h);
			Paint gp = this.graphicEffect.getGradientPaint();
			TexturePaint tp = this.graphicEffect.getTexturePaint();
			if (gp != null) {
				g2.setPaint(gp);
			} else {
				g2.setPaint(tp);
			}
			g2.fill(rect);
		} else {
			g2.setColor(Color.white);
			g2.fill(rect);
			g2.setColor(Color.black);
			g2.drawString(this.nullMessage, 5, 10);
		}
		this.icon = new ImageIcon(this.iconImage);
	}

	public void changeColor(Color c) {

		this.graphicEffect = new GraphicEffect();
		this.graphicEffect.setFirstGradientColor(c);

		this.repaint();
	}

	public void changeFillEffect(GraphicEffect graphicEffect) {
		this.graphicEffect = graphicEffect;
		this.repaint();
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		return this.getCellRenderComponent(value, index);
	}

	public Component getCellRenderComponent(Object value, int index) {

		if (value instanceof GraphicEffect) {
			this.setGraphicEffect((GraphicEffect) value);
		}

		String str = value == null ? "" : value.toString();

		if (value instanceof GraphicEffect || "".equals(str)) {
			try {
				this.createIcon();
				this.setIcon(this.icon);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return this;
		}

		if (index == 1) {
			// 채우기 없음 시.
			this.nullMessage = str;
		}

		if (true) {
			JLabel label = new JLabel();
			label.setText(str);
			return label;
		} else {
			JButton btn = new JButton(str);
			btn.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.LOWERED));

			return btn;
		}
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {
		// TODO 채우기효과 테이블셀렌더러 코드
		return this.getCellRenderComponent(value, row);
	}

	private ImageIcon icon = null;
	private BufferedImage iconImage;
	private GraphicEffect graphicEffect = null;
	private String nullMessage = "없음";
	private Color fillColor;

	public FillColorComboBoxRenderer() {
		this.setOpaque(true);
		this.setBorder(new javax.swing.border.EmptyBorder(1, 1, 1, 1));
	}

}