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

public class ColListener_11_LayerLevelSelector extends ColListener {

	@Override
	public Object getValueAt(int row, int col, GisProject gisProject) {
		Layer layer = this.getLayer(row, gisProject);
		if (layer != null) {
			return this.isMinControl ? layer.getDispLevelMin() : layer.getDispLevelMax() ;
		} else {
			return -1;
		}

	}

	@Override
	public void setValueAt(final Object value, final int row, final int col,
			final GisProject gisProject, final MapViewer mapViewer, JTable jTable) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColListener_11_LayerLevelSelector.this.setValueAt_Runnable(value, row, col, gisProject, mapViewer);
			}
		});

	}

	private void setValueAt_Runnable(Object value, int row, int col,
			GisProject gisProject, MapViewer mapViewer) {

		this.debug("VALUE = " + value); // 밸류값은 체크박스에 있는 놈이 넘어온다.

		Integer dispLevel = null;

		if (value instanceof IndexObject) {
			IndexObject indexObject = (IndexObject) value;
			dispLevel = (Integer) indexObject.getValue();
		} else {
			dispLevel = (int) Double.parseDouble(("" + value).trim());
		}

		Layer layer = gisProject.getMapData_Base().getLayer(row);
		if( layer != null && dispLevel != null ) {
			if( this.isMinControl ) {
				layer.setDispLevelMin( dispLevel );
			} else {
				layer.setDispLevelMax( dispLevel );
			}
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

	public JComboBox getComboBox_LineWidth() {

		if (this.comboBox_LineWidth != null) {
			return this.comboBox_LineWidth;
		}

		int startIndex = 0;
		int size = 14 ;
		int increment = 1;

		IndexObject idxObjList[] = new IndexObject[size];

		int index;

		for (int i = 0, iLen = idxObjList.length; i < iLen; i++) {
			index = startIndex + i;
			idxObjList[i] = new IndexObject(index, new Integer(index));
		}

		if (true) {
			this.comboBox_LineWidth = new JComboBox(idxObjList);
		}

		return this.comboBox_LineWidth;
	}

	public ColListener_11_LayerLevelSelector(String name, int width,
			boolean editable, boolean isMinControl) {
		super(name, width, editable);
		this.isMinControl = isMinControl;
	}

	private JComboBox comboBox_LineWidth = null;

	private boolean isMinControl = true;

}
