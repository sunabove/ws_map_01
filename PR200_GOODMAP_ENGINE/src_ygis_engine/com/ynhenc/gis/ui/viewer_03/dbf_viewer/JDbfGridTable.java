package com.ynhenc.gis.ui.viewer_03.dbf_viewer;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.jdesktop.swingx.JXTable;

import com.ynhenc.comm.DebugInterface;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.ui.comp.JGridTable;
import com.ynhenc.gis.ui.viewer_02.style_editor.ColListener;
import com.ynhenc.gis.ui.viewer_02.style_editor.TableModelGisProject;

public class JDbfGridTable extends JGridTable implements DebugInterface {

	public void setLayer(Layer layer) throws Exception {
		File dbfFile = layer.getFileShape_Dbf();
		this.setDbfFile(dbfFile);
	}

	public void setDbfFile(File dbfFile) throws Exception {

		if (this.getModel() instanceof TableModelDbf) {
			// do nothing !!!
		} else {
			// 다른 모델이 설정되어 있으면.....
			this.initTable();
		}

		this.getTableModel().setDbf(dbfFile);
		this.getTableModel().fireTableStructureChanged();

		this.validate();

	}

	@Override
	public TableModelDbf getTableModel() {
		if( this.tableModelDbf == null ) {
			try {
			this.tableModelDbf = new TableModelDbf();
			} catch ( Exception e ) {
				this.debug.println( e );
			}
		}
		return this.tableModelDbf;
	}

	@Override
	public ColListener[] getColListnerList() {
		return null;
	}

	@Override
	public void whenMousePressed(MouseEvent e) {
	}

	public JDbfGridTable() {
		try {
			this.initTable();
		} catch (Exception e) {
			this.debug.println(this, e);
		}
	}

	private TableModelDbf tableModelDbf;

}
