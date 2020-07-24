package com.ynhenc.gis.ui.viewer_02.style_editor;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.ynhenc.gis.model.map.GisProject;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;

public class ColListener_00_RecordNo extends ColListener {

	public ColListener_00_RecordNo(String name, int width ) {
		super(name, width, false);
	}

	@Override
	public Object getValueAt(int row, int col, GisProject gisProject) {
		return (row + 1);
	}

	@Override
	public void setValueAt(Object value, int row, int col, GisProject gisProject, MapViewer mapViewer, JTable jTable) {
		// do nothing
	}

	@Override
	public TableCellRenderer getTableCellRenderer() {
		return null;
	}

	@Override
	public TableCellEditor getTableCellEditor() {
		return null;
	}

}
