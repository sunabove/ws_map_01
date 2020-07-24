package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.*;

import org.jdesktop.swingx.JXTable;

import com.ynhenc.comm.DebugInterface;
import com.ynhenc.gis.model.map.GisProject;
import com.ynhenc.gis.model.map.GisProjectFileFormat;
import com.ynhenc.gis.model.map.GisProjectOpener;
import com.ynhenc.gis.model.map.MapDataBase;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.ui.comp.JGridTable;
import com.ynhenc.gis.ui.viewer_01.map_viewer.LayerFontStyleShower;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;

public class JLayerStyleEditor extends JGridTable {

	public void setGisProject(GisProject gisProject) {
		this.getTableModel().setGisProject(gisProject);
	}

	public void setMapViewer(MapViewer mapViewer) {
		this.getTableModel().setMapViewer(mapViewer);
	}

	@Override
	public ColListener[] getColListnerList() {
		if (this.colListenerList == null) {
			int margin = this.isSortable() ? 10 : 0;
			this.colListenerList = new ColListener[] {
					// new ColListener_00_RecordNo("��ȣ", 50 + margin),
					new ColListener_13_LayerType("Ÿ��", 20 + margin), new ColListener_14_LayerShowing("ǥ����", 50 + margin),
					new ColListener_02_LayerNameEditor("���̾��", 120 + margin, true),
					new ColListener_01_LayerNoEditor("��ȣ", 50 + margin, true),
					new ColListener_12_LayerEnabler("���", 40 + margin),
					new ColListener_11_LayerLevelSelector("MIN", 40 + margin, true, true),
					new ColListener_11_LayerLevelSelector("MAX", 40 + margin, true, false),
					new ColListener_06_GraphicEffectEditor("���", 40 + margin, true, 0),
					new ColListener_06_GraphicEffectEditor("����", 40 + margin, true, 1),
					new ColListener_04_LineStrokeSelector("����", 40 + margin, true),
					new ColListener_05_LineWidthSelector("����", 40 + margin, true),
					new ColListener_08_IconImageSelector("������", 90 + margin, true),
					new ColListener_07_LabelTextColumnSelector("���̺�", 90 + margin, true),
					new ColListener_09_LineSymbolArrowSelector("ȭ��ǥ", 50 + margin, true), };
		}

		return this.colListenerList;
	}

	@Override
	public TableModelGisProject getTableModel() {
		if (this.tableModelGisProject != null) {
			return this.tableModelGisProject;
		} else {
			TableModelGisProject tableModelGisProject = new TableModelGisProject(this.getColListnerList(), this);
			this.tableModelGisProject = tableModelGisProject;

			return this.tableModelGisProject;
		}
	}

	@Override
	public void whenMousePressed(MouseEvent e) {
		if (true) {
			JTable table = this;
			int viewRow = table.getSelectedRow();

			if (viewRow > -1 && viewRow < this.getRowCount() ) {
				int modelRow = table.convertRowIndexToModel(viewRow);
				this.debug.println(this, "sel row: " + modelRow);

				if (modelRow > -1 && modelRow < this.getRowCount() ) {
					this.synchWithMapEditor(modelRow);
				}
			}
		}
	}

	private void synchWithMapEditor(int modelRow) {
		TableModelGisProject tableModel = this.getTableModel();

		if (tableModel != null) {
			GisProject gisProject = tableModel.getGisProject();
			ColListener[] colListenerList = this.getColListnerList();
			if (gisProject != null && colListenerList != null && colListenerList.length > 1) {
				int col = 0;
				ColListener colListener = colListenerList[col];
				MapViewer mapViewer = tableModel.getMapViewer();

				colListener.synchWithMapViewer(modelRow, col, gisProject, mapViewer);
			}
		}
	}

	public JLayerStyleEditor() {
		super();
	}

	private TableModelGisProject tableModelGisProject;

	private ColListener[] colListenerList;

	public static void main(String args[]) throws Exception {

		JFrame frame = new JFrame("Editable Color Table");
		JLayerStyleEditor layerStyleEditor = new JLayerStyleEditor();

		JScrollPane scrollPane = new JScrollPane(layerStyleEditor);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		try {
			// test code

			String filePath = "C:\\Documents and Settings\\sbmoon\\My Documents\\ccc.xml";

			File file = new File(filePath);

			GisProjectOpener opener = new GisProjectOpener(null);

			GisProjectFileFormat gisProjectFileFormat = opener.openGisProjectFile(file);

			GisProject gisProject = gisProjectFileFormat.getGisProject();

			layerStyleEditor.setGisProject(gisProject);

		} catch (Exception e) {
			e.printStackTrace();
		}

		frame.setSize(400, 150);
		frame.setVisible(true);

	}

}
