package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.ynhenc.gis.model.map.GisProject;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;
import com.ynhenc.gis.ui.viewer_03.dbf_viewer.JDbfGridTable;

public class ColListener_14_LayerShowing extends ColListener {

	@Override
	public Object getValueAt(int row, int col, GisProject gisProject) {
		Layer layer = this.getLayer(row, gisProject);

		if (layer != null) {
			return layer.isShowingNow() ? "O" : "X";
		} else {
			return "";
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col, GisProject gisProject, MapViewer mapViewer, JTable jTable) {

	}

	@Override
	public TableCellRenderer getTableCellRenderer() {
		if (renderer == null) {
			renderer = new JLabelRenderer( );
		}

		return this.renderer;
	}

	@Override
	public TableCellEditor getTableCellEditor() {
		return null;
	}

	public ColListener_14_LayerShowing(String name, int width) {
		super(name, width, false);
	}

	private TableCellRenderer renderer = null;

}
