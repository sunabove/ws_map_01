package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.ynhenc.comm.DebugInterface;
import com.ynhenc.gis.model.map.GisProject;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.ui.viewer_01.map_viewer.LayerFontStyleShower;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;

public class TableModelGisProject extends AbstractTableModel implements DebugInterface, TableModelListener  {

	public JTable getJTable() {
		return jTable;
	}

	public void setJTable(JTable table) {
		jTable = table;
	}

	public MapViewer getMapViewer() {
		return mapViewer;
	}

	public void setMapViewer(MapViewer mapViewer) {
		this.mapViewer = mapViewer;
	}

	public GisProject getGisProject() {

		if (this.gisProject != null) {
			return this.gisProject;
		} else {
			return this.gisProject;
		}

	}

	public void setGisProject(GisProject gisProject) {
		this.gisProject = gisProject;
	}

	private void synchWithMapViewer(int row, int col) {

		GisProject gisProject = this.getGisProject();
		if (gisProject != null) {
			Layer currTargetLayer = gisProject.getTargetLayer();
			Layer newTargetLayer = this.getLayer(row);
			if (currTargetLayer != newTargetLayer) {
				gisProject.setTargetLayer(newTargetLayer);
				LayerFontStyleShower fontStyleShower = mapViewer.getLayerFontStyleShower();
				if (fontStyleShower != null) {
					fontStyleShower.showLayerFontSyle(newTargetLayer);
				}
			}

		}

	}

	public Object getValueAt(int row, int col) {

		ColListener colListener = this.getColListener(col);
		if (colListener != null) {
			return colListener.getValueAt(row, col, gisProject);
		} else {
			return "";
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		boolean localDebug = true;

		if (localDebug) {
			this.debug.println(this, "COLUMN SET = " + row + ", " + col + " = " + value);
		}

		ColListener colListener = this.getColListener(col);

		if (colListener != null) {
			colListener.setValueAt(value, row, col, gisProject, mapViewer, this.getJTable() );
		}
	}

	@Override
	public String getColumnName(int col) {
		return this.getColListener(col).getName();
	}

	public int getColumnCount() {
		return this.colListenerList.length;
	}

	public int getRowCount() {
		try {
			int size = -1;
			boolean localDebug = false;
			if (localDebug) {
				this.debug.println(this, "LAYER SIZE = " + size);
			}
			size = this.getGisProject().getMapData_Base().getLayerSize();
			if (localDebug) {
				this.debug.println(this, "LAYER SIZE = " + size);
			}
			return size;
		} catch (Exception e) {

			return 0;
		}
	}

	@Override
	public Class getColumnClass(int column) {
		Object obj = this.getValueAt(0, column);
		if (obj != null) {
			return obj.getClass();
		} else {
			return null;
		}
		// return ( getValueAt(0, column ).getClass() );
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return this.getColListener(col).isCellEditable(row, col, this.gisProject);
	}

	public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        this.debug.println( this, "row:" + row );
    }


	public final Layer getLayer(int row) {
		return this.getGisProject().getMapData_Base().getLayer(row);
	}

	private final ColListener getColListener(int col) {
		return this.colListenerList[col];
	}

	public TableModelGisProject(ColListener[] colListenerList, JTable jTable) {
		this.colListenerList = colListenerList;
		this.jTable = jTable;

		this.addTableModelListener(this);

	}

	private GisProject gisProject;
	private transient MapViewer mapViewer;
	private transient JTable jTable;

	private ColListener[] colListenerList;

}
