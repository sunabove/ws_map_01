package com.ynhenc.gis.ui.comp;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import org.jdesktop.swingx.JXTable;

import com.ynhenc.comm.DebugInterface;
import com.ynhenc.gis.model.map.GisProject;
import com.ynhenc.gis.model.map.GisProjectFileFormat;
import com.ynhenc.gis.model.map.GisProjectOpener;
import com.ynhenc.gis.model.map.MapDataBase;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.ui.viewer_01.map_viewer.LayerFontStyleShower;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;
import com.ynhenc.gis.ui.viewer_02.style_editor.*;

public abstract class JGridTable extends JXTable implements DebugInterface {

	public final void initTable() {

		this.setSortable(true);
		this.setDoubleBuffered(false);

		if (true) {
			this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}

		if (true) {
			this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		} else {
			this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			this.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
			this.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
			this.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		}

		// 데이터 설정
		AbstractTableModel model = this.getTableModel();

		if (model != null) {
			this.setModel(model);
			this.initColListenerList();
		}

	}

	private void initColListenerList() {

		TableColumnModel colModel = this.getColumnModel();

		// fire event when mouse clicked.
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JGridTable.this.whenMousePressed(e);
			}
		});

		ColListener[] colListenerList = this.getColListnerList();

		if (colListenerList != null) {
			int colIdx = 0;
			TableColumn column;
			for (ColListener colListener : colListenerList) {
				column = colModel.getColumn(colIdx);

				if (colListener.getTableCellRenderer() != null) {
					// 렌더러 설정
					column.setCellRenderer(colListener.getTableCellRenderer());
				}

				if (colListener.getTableCellEditor() != null) {
					// 에디터 설정
					column.setCellEditor(colListener.getTableCellEditor());
				}

				if (colListener.getWidth() > 0) {
					column.setPreferredWidth(colListener.getWidth());
					column.setMinWidth(colListener.getWidth());
				}

				colIdx++;
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {

		Dimension size = super.getPreferredSize();

		if (false) {
			TableColumnModel colModel = this.getColumnModel();

			int width = colModel.getTotalColumnWidth();
			int height = this.getRowHeight() * this.getRowCount();

			size.width = width;
			size.height = height;

			return size;
		}

		ColListener[] colListenerList = this.getColListnerList();

		if (colListenerList != null) {
			int width = 0;

			for (ColListener colListener : colListenerList) {
				width += colListener.getWidth() > 0 ? colListener.getWidth() : 0;
			}
			size.width = width > size.width ? width : size.width;

			return size;
		}

		return size;

	}

	public JGridTable() {
		this.initTable();
	}

	public abstract ColListener[] getColListnerList();

	public abstract AbstractTableModel getTableModel();

	public abstract void whenMousePressed(MouseEvent e);

}
