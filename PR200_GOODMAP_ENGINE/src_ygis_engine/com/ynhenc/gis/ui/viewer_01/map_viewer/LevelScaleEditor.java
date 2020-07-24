package com.ynhenc.gis.ui.viewer_01.map_viewer;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.ynhenc.comm.DebugInterface;
import com.ynhenc.gis.model.map.GisProject;
import com.ynhenc.gis.ui.comp.JGridTable;
import com.ynhenc.gis.ui.viewer_02.style_editor.ColListener;

public class LevelScaleEditor extends JGridTable  {

	public LevelScaleEditor() {
	}

	public void setMapViewer(MapViewer mapViewer) {

		this.mapViewer = mapViewer;

		GisProject gisProject = mapViewer.getGisProject();

		this.zoomLevelMax = gisProject.getZoomLevelMax() ;

		this.tableModel = new LevelScaleTableModel( gisProject );

		this.setModel(this.tableModel);

		tableModel.fireTableStructureChanged();

	}

	@Override
	public void paint( Graphics g ) {
		super.paint( g );

		this.synchGisProject( );
	}

	private void synchGisProject( ) {

		MapViewer mapViewer = this.mapViewer;
		LevelScaleTableModel tableModel = this.getTableModel();

		if (mapViewer != null && tableModel != null) {

			GisProject gisProject = mapViewer.getGisProject() ;

			if ( tableModel.getGisProject() != gisProject ) {

				if (gisProject != null) {

					this.zoomLevelMax = gisProject.getZoomLevelMax() ;

					tableModel.setGisProject( gisProject );
					tableModel.fireTableStructureChanged();
				}

			}

			if( this.zoomLevelMax != gisProject.getZoomLevelMax() ) {
				this.zoomLevelMax = gisProject.getZoomLevelMax() ;

				tableModel.fireTableStructureChanged();
			}
		}

	}

	@Override
	public ColListener[] getColListnerList() {
		return null;
	}

	@Override
	public LevelScaleTableModel getTableModel() {
		return this.tableModel ;
	}

	@Override
	public void whenMousePressed(MouseEvent e) {
	}

	private MapViewer mapViewer;

	private int zoomLevelMax;

	private LevelScaleTableModel tableModel ;



}
