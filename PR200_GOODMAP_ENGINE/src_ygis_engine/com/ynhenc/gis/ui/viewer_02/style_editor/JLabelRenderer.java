package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.*;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class JLabelRenderer extends DefaultTableCellRenderer {
	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public JLabelRenderer() {
		// this.setBorder(null);
		this.setHorizontalAlignment(JTextField.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {

		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		this.setHorizontalAlignment(JTextField.CENTER);

		return this;
	}

	private Color bgColor;
}
