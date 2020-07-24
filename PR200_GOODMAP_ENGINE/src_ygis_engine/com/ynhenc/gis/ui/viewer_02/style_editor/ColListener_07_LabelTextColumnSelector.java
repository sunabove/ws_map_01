package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.ynhenc.gis.model.map.GisProject;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.model.shape.MetaDataLabel;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;
import com.ynhenc.gis.ui.viewer_03.dbf_viewer.JDbfGridTable;
import com.ynhenc.gis.ui.viewer_03.dbf_viewer.TableModelDbf;

public class ColListener_07_LabelTextColumnSelector extends ColListener {

	@Override
	public void setValueAt(Object value, int row, int col,
			GisProject gisProject, MapViewer mapViewer, JTable jTable) {

		Layer layer = this.getLayer(row, gisProject);

		IndexObject idxObj = (value instanceof IndexObject) ? (IndexObject) value
				: null;

		if (layer != null && idxObj != null) {
			layer.getMetaDataLabel().setColumnIndex(idxObj.getIndex());

			this.repaintMapViewerAndStyleEditor(mapViewer);
		}

		if (layer != null) {

			super.showShapeDbf(layer, mapViewer);

		}

	}

	@Override
	public Object getValueAt(int row, int col, GisProject gisProject) {

		Layer layer = this.getLayer(row, gisProject);

		boolean localDebug = false;
		if (localDebug) {
			this.debug.println(this, "LAYER NAME = " + layer.getName());
		}

		return layer;
	}

	public void configureJComboBox(JComboBox comboBox, Object value, int row) {

		Layer layer = null;

		if (value instanceof Layer) {
			layer = (Layer) value;
		}

		if (layer != null) {
			TableModelDbf model = layer.getTableModel();
			if (model != null) {
				String[] colNameList = model.getColNameList();
				if (colNameList != null) {
					comboBox.removeAllItems();
					comboBox.addItem(new IndexObject(-1, "없음"));
					int index = 0;
					for (String colName : colNameList) {
						comboBox.addItem(new IndexObject(index, colName));
						index++;
					}
				}
			}
		}
	}

	public void configureJLabel(JLabel jLabel, Object value, int row) {
		Layer layer = (value instanceof Layer) ? (Layer) value : null;

		jLabel.setText("");

		if (layer != null) {

			MetaDataLabel metaDataLabel = layer.getMetaDataLabel();

			int colIdx = metaDataLabel.getColumnIndex();

			if (colIdx > -1) {
				TableModelDbf model = layer.getTableModel();

				if (model != null) {
					String[] colNameList = model.getColNameList();

					if (colIdx < colNameList.length) {
						jLabel.setText(colNameList[colIdx]);
					}
				}
			}
		}
	}

	@Override
	public TableCellRenderer getTableCellRenderer() {

		DefaultTableCellRenderer render = new DefaultTableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {

				Component comp = super.getTableCellRendererComponent(table,
						value, isSelected, hasFocus, row, column);

				ColListener_07_LabelTextColumnSelector.this.configureJLabel(this, value, row);

				return comp;
			}

		};

		return render;
	}

	@Override
	public TableCellEditor getTableCellEditor() {

		DefaultCellEditor tableCellEditor = new DefaultCellEditor(comboBox) {
			@Override
			public Component getTableCellEditorComponent(JTable table,
					Object value, boolean isSelected, int row, int column) {

				ColListener_07_LabelTextColumnSelector.this.configureJComboBox(comboBox, value, row);

				return comboBox;
			}

		};
		return tableCellEditor;
	}

	public ColListener_07_LabelTextColumnSelector(String name, int width,
			boolean editable) {
		super(name, width, editable);
	}

	private Object[] objList = new Object[] { "없음" };

	private JComboBox comboBox = new JComboBox(objList);

}
