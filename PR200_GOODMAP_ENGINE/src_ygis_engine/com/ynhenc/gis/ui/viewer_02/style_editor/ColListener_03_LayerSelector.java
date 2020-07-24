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

public class ColListener_03_LayerSelector extends ColListener {

	@Override
	public Object getValueAt(int row, int col, GisProject gisProject) {

		Layer layer = this.getLayer(row, gisProject);

		if (layer != null) {
			int zoomLevel = gisProject.getZoomLevelList().getZoomLevelCurr();
			return new Boolean( layer.isSelectedAtZoomLevel(zoomLevel) );
		} else {
			return "";
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col,
			GisProject gisProject, MapViewer mapViewer, JTable jTable) {
		Layer layer = gisProject.getMapData_Base().getLayer(row);

		if (layer != null) {
			// 레이어 선택 토글

			Boolean selected = (Boolean) value;

			int zoomLevel = gisProject.getZoomLevelList().getZoomLevelCurr();

			layer.setSelectedAtZoomLevel(zoomLevel, selected);

			if (mapViewer.isShowing()) {
				this.repaintMapViewerAndStyleEditor(mapViewer);
			} else {
				mapViewer.createMapImageAgain();
			}
		}
	}

	@Override
	public TableCellRenderer getTableCellRenderer() {
		return null;
	}

	@Override
	public TableCellEditor getTableCellEditor() {
		return null;
	}

	public ColListener_03_LayerSelector(String name, int width, boolean editable) {
		super(name, width, editable);
	}

}
