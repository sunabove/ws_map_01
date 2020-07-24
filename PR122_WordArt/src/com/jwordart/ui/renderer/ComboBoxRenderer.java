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
import javax.swing.*;

public class ComboBoxRenderer extends JLabel implements ListCellRenderer {
	public ComboBoxRenderer(int hAlign, int vAlign) {
		setOpaque(true);
		setHorizontalAlignment(hAlign);
		setVerticalAlignment(vAlign);
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		if (value instanceof ImageIcon) {

			ImageIcon icon = (ImageIcon) value;
			if (icon != null) {
				setText(icon.getDescription());
				setIcon(icon);
			}
		}
		
		return this;
	}
}
