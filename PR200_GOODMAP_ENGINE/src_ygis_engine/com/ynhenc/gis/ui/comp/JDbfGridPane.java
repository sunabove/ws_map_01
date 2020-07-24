package com.ynhenc.gis.ui.comp;

import java.awt.*;
import java.io.File;

import javax.swing.*;

import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.ui.viewer_03.dbf_viewer.JDbfGridTable;

public class JDbfGridPane extends JPanel {

	public void setLayer(Layer layer) throws Exception {
		this.getDbfGridTable().setLayer(layer);
	}

	public void setDbfFile(File dbfFile) throws Exception {
		this.getDbfGridTable().setDbfFile(dbfFile);
	}

	private JDbfGridTable getDbfGridTable() {
		if( this.dbfGridTable == null ) {
			this.dbfGridTable = new JDbfGridTable();
		}
		return this.dbfGridTable;
	}

	private JScrollPane getScrollPane() {
		if( this.scrollPane == null ) {
			this.scrollPane = new JScrollPane( this.getDbfGridTable() );
		}
		return scrollPane;
	}

	public JDbfGridPane() {
		this.setBorder( null );
		this.setLayout( new BorderLayout());
		JScrollPane scollPane = this.getScrollPane();
		this.add( scollPane, BorderLayout.CENTER );
	}

	private JScrollPane scrollPane;
	private JDbfGridTable dbfGridTable;

}
