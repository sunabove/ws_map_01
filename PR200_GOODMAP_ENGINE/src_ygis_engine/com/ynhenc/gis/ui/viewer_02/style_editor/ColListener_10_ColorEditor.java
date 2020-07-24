package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.ynhenc.gis.model.map.GisProject;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;

public class ColListener_10_ColorEditor extends ColListener {

	@Override
	public void setValueAt(Object value, int row, int col,
			GisProject gisProject, MapViewer mapViewer, JTable jTable) {

	} 

	@Override
	public Object getValueAt(int row, int col, GisProject gisProject) {
		
		Layer layer = this.getLayer(row, gisProject);

		return layer ;
	}

	public TableCellRenderer getTableCellRenderer() {
		return null;
	}

	public TableCellEditor getTableCellEditor() {

		DefaultCellEditor tableCellEditor = new DefaultCellEditor(comboBox);
		return tableCellEditor;
	}
	
	public ColListener_10_ColorEditor(String name, int width, boolean editable) {
		super(name, width, editable);
	}
	
	private Color colorList[] = { Color.red, Color.orange, Color.yellow, Color.green,
			Color.blue, Color.magenta };
	private JComboBox comboBox = new JComboBox(colorList);

}
