package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class JCheckBoxRenderer extends JCheckBox implements TableCellRenderer {
	public JCheckBoxRenderer(boolean editable) {
		 this.setBorder(null);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
			int col) {
		TableColumn column = table.getColumnModel().getColumn(col);
		this.setSize(column.getWidth(), table.getRowHeight(row));
		this.setPreferredSize(new Dimension(column.getWidth(), table.getRowHeight(row)));
		this.setText( "" + value );
		this.setSelected( isSelected );
		return this;
	}
}
