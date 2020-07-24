package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.ynhenc.gis.model.map.GisProject;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.model.shape.LayerType;
import com.ynhenc.gis.model.style.Style_05_General;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;

public class ColListener_05_LineWidthSelector extends ColListener {

	@Override
	public Object getValueAt(int row, int col, GisProject gisProject) {
		Layer layer = this.getLayer(row, gisProject);
		if (layer != null) {
			Style_05_General shapeStyle = layer.getShapeStyle();
			return shapeStyle.getLineWidth();
		} else {
			return -1;
		}

	}

	@Override
	public void setValueAt(final Object value, final int row, final int col,
			final GisProject gisProject, final MapViewer mapViewer, JTable jTable) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColListener_05_LineWidthSelector.this.setValueAt_Runnable(value, row, col, gisProject, mapViewer);
			}
		});

	}

	private void setValueAt_Runnable(Object value, int row, int col,
			GisProject gisProject, MapViewer mapViewer) {

		this.debug("VALUE = " + value); // 밸류값은 체크박스에 있는 놈이 넘어온다.

		Integer lineWidth = null;

		if (value instanceof IndexObject) {
			IndexObject indexObject = (IndexObject) value;
			lineWidth = (Integer) indexObject.getValue();
		} else {
			lineWidth = (int) Double.parseDouble(("" + value).trim());
		}

		Layer layer = gisProject.getMapData_Base().getLayer(row);
		Style_05_General styleGeneral = layer.getShapeStyle();

		if (lineWidth != null) {
			// 선 폭 변

			styleGeneral.setLineWidth( lineWidth.doubleValue() );

			this.repaintMapViewerAndStyleEditor(mapViewer);
		}
	}

	@Override
	public boolean isCellEditable(int row, int col, GisProject gisProject) {
		Layer layer = gisProject.getMapData_Base().getLayer(row);

		return true;
		// 라인타입만 아니면 채우기가 가능하다.
	}

	@Override
	public TableCellRenderer getTableCellRenderer() {
		return null;
	}

	@Override
	public TableCellEditor getTableCellEditor() {

		final JComboBox comboBox = this.getComboBox_LineWidth();

		DefaultCellEditor editor = new DefaultCellEditor(comboBox) {
			@Override
			public Component getTableCellEditorComponent(JTable table,
					Object value, boolean isSelected, int row, int column) {
				Component c = super.getTableCellEditorComponent(table, value,
						isSelected, row, column);

				boolean localDebug = false;

				if (localDebug) {
					debug.println(this, "VALUE = " + value);
				}

				final String valueText = ""
						+ (int) (Double.parseDouble("" + value));

				for (int i = 0, iLen = comboBox.getItemCount(); i < iLen; i++) {
					if (valueText.equalsIgnoreCase("" + comboBox.getItemAt(i))) {
						comboBox.setSelectedIndex(i);
					}
				}

				return c;
			}
		};

		return editor;
	}

	public ColListener_05_LineWidthSelector(String name, int width,
			boolean editable) {
		super(name, width, editable);
	}

	public JComboBox getComboBox_LineWidth() {

		if (this.comboBox_LineWidth != null) {
			return this.comboBox_LineWidth;
		}

		int size = 100;
		int increment = 1;

		IndexObject idxObjList[] = new IndexObject[size];

		for (int i = 0, iLen = idxObjList.length; i < iLen; i++) {
			idxObjList[i] = new IndexObject(i, new Integer(i + increment));
		}

		if (true) {
			this.comboBox_LineWidth = new JComboBox(idxObjList);
		}

		return this.comboBox_LineWidth;
	}

	private JComboBox comboBox_LineWidth = null;

}
