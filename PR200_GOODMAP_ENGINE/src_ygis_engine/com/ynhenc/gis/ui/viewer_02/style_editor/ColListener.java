package com.ynhenc.gis.ui.viewer_02.style_editor;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.gis.model.map.GisProject;
import com.ynhenc.gis.model.map.MapDataBase;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.ui.comp.JDbfGridPane;
import com.ynhenc.gis.ui.viewer_01.map_viewer.LayerFontStyleShower;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;
import com.ynhenc.gis.ui.viewer_03.dbf_viewer.JDbfGridTable;

public abstract class ColListener extends GisComLib {

	public float getHorizontalAlignment() {
		return horizontalAlignment;
	}

	public void setHorizontalAlignment(float horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public final boolean isEditable() {
		return editable;
	}

	public boolean isCellEditable(int row, int col, GisProject gisProject) {
		return this.isEditable();
	}

	public final void setEditable(boolean editable) {
		this.editable = editable;
	}

	public final Layer getLayer(int row, GisProject gisProject) {
		MapDataBase mapData_Base = gisProject != null ? gisProject.getMapData_Base() : null;
		if (mapData_Base != null) {
			Layer layer = mapData_Base.getLayer(row);
			return layer;
		} else {
			return null;
		}
	}

	public void synchWithMapViewer(int row, int col, GisProject gisProject, MapViewer mapViewer ) {
		if (gisProject != null) {
			Layer currTargetLayer = gisProject.getTargetLayer();
			Layer newTargetLayer = this.getLayer(row, gisProject);
			if ( currTargetLayer != newTargetLayer ) {
				gisProject.setTargetLayer( newTargetLayer );
				LayerFontStyleShower fontStyleShower = mapViewer.getLayerFontStyleShower();
				if (fontStyleShower != null) {
					fontStyleShower.showLayerFontSyle(newTargetLayer);
				}
			}
		}
	}

	public void repaintMapViewerAndStyleEditor(final MapViewer mapViewer) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColListener.this.repaintMapViewerAndStyleEditor_Runnalbe(mapViewer);
			}
		});
	}

	protected void showShapeDbf(final Layer layer, final MapViewer mapViewer) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				ColListener.this.showShapeDbf_Runnable(layer, mapViewer);
			}
		});
	}

	protected void showShapeDbf_Runnable(Layer layer, MapViewer mapViewer) {
		if (layer != null && mapViewer != null) {
			// 속성 조회
			JDbfGridPane dbfViewer = mapViewer.getDbfGrid();
			if (dbfViewer != null && dbfViewer.isVisible() && dbfViewer.isShowing()) {
				try {
					dbfViewer.setLayer(layer);
					dbfViewer.getParent().validate();
					dbfViewer.getParent().repaint();
				} catch (Exception e) {
					this.debug(e);
				}
			}

		}
	}

	private void repaintMapViewerAndStyleEditor_Runnalbe(MapViewer mapViewer) {
		JLayerStyleEditor styleEditor = mapViewer.getJLayerStyleEditor();
		if (styleEditor != null) {
			mapViewer.getJLayerStyleEditor().repaint();
			mapViewer.getJLayerStyleEditor().validate();
		}
		mapViewer.createMapImageAgain();
		mapViewer.repaint();
	}

	public abstract void setValueAt(Object value, int row, int col, GisProject gisProject, MapViewer mapViewer, JTable jTable);

	public abstract Object getValueAt(int row, int col, GisProject gisProject);

	public abstract TableCellRenderer getTableCellRenderer();

	public abstract TableCellEditor getTableCellEditor();

	public ColListener(String name, int width, boolean editable ) {
		super();
		this.name = name;
		this.width = width;
		this.editable = editable;
	}

	private String name;
	private int width;
	private boolean editable;
	private float horizontalAlignment = - 1 ;

}