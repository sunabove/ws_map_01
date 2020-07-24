package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.Color;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.ynhenc.gis.model.map.GisProject;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;
import com.ynhenc.gis.ui.viewer_03.dbf_viewer.JDbfGridTable;

public class ColListener_02_LayerNameEditor extends ColListener {

	@Override
	public Object getValueAt(int row, int col, GisProject gisProject) {
		Layer layer = this.getLayer(row, gisProject);

		if (layer != null) {
			return layer.getName();
		} else {
			return "";
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col, GisProject gisProject, MapViewer mapViewer, JTable jTable) {

		Layer layer = this.getLayer(row, gisProject);
		if (layer != null) {
			this.showShapeDbf(layer, mapViewer);
		}
	}

	@Override
	public TableCellRenderer getTableCellRenderer() {
		if (renderer == null) {
			renderer = new JLabelRenderer();
			renderer.setBgColor( Color.red );
		}
		return this.renderer;
	}

	@Override
	public TableCellEditor getTableCellEditor() {
		return null;
	}

	public ColListener_02_LayerNameEditor(String name, int width, boolean editable) {
		super(name, width, editable);
	}

	private JLabelRenderer renderer = null;

}
