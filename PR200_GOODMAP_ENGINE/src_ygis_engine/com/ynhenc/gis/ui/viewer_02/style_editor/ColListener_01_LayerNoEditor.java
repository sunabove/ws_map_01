package com.ynhenc.gis.ui.viewer_02.style_editor;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.*;

import com.ynhenc.gis.model.map.GisProject;
import com.ynhenc.gis.model.map.MapDataBase;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;

public class ColListener_01_LayerNoEditor extends ColListener {

	public ColListener_01_LayerNoEditor(String name, int width, boolean editable) {
		super(name, width, editable);
	}

	@Override
	public Object getValueAt(int row, int col, GisProject gisProject) {
		Layer layer = this.getLayer(row, gisProject);
		if (layer != null) {
			return layer.getLayerNo();
		} else {
			return "";
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col, GisProject gisProject, MapViewer mapViewer, JTable jTable) {

		MapDataBase mapBase = gisProject.getMapData_Base();

		final Layer layer = this.getLayer(row, gisProject);

		if (layer != null) {

			boolean moveResult = false;

			if (textFirst.equals(value)) {
				moveResult = mapBase.moveLayerTop(layer);
			} else if (textUp.equals(value)) {
				moveResult = mapBase.moveLayerDown(layer);
			} else if (textDown.equals(value)) {
				moveResult = mapBase.moveLayerUp(layer);
			} else if (textLast.equals(value)) {
				moveResult = mapBase.moveLayerBottom(layer);
			} else if (textDelete.equals(value)) {
				moveResult = mapBase.removeLayer(layer);
			}

			if (moveResult) {
				int start = -1;
				int rowCount = jTable.getRowCount();
				for (int i = 0, iLen = rowCount; i < iLen; i++) {
					if (layer == this.getLayer( i, gisProject)) {
						start = i;
					}
				}

				start= jTable.convertRowIndexToView( start );
				if (start > -1) {
					int end = start;
					jTable.addRowSelectionInterval(start, end);
					AbstractTableModel model = (AbstractTableModel) jTable.getModel();
					model.fireTableDataChanged();
					jTable.repaint();
				}
			}

			if (moveResult && mapViewer != null) {
				this.repaintMapViewerAndStyleEditor(mapViewer);
				debug.println(this, "value = " + value + "moveResult = " + moveResult);
			}
		}
	}

	@Override
	public TableCellRenderer getTableCellRenderer() {
		return null;
	}

	@Override
	public TableCellEditor getTableCellEditor() {
		return new DefaultCellEditor(this.getComboBox());
	}

	private JComboBox getComboBox() {
		if (this.comboBox == null) {
			Object objList[] = { "", textFirst, textUp, textDown, textLast, textDelete };
			this.comboBox = new JComboBox(objList);
		}
		return this.comboBox;
	}

	private final String textFirst = "제일위", textUp = "위로", textDown = "아래로", textLast = "제일 아래", textDelete = "삭제";

	private JComboBox comboBox = null;

}
