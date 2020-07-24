package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import com.ynhenc.comm.file.FileFilter_01_Simple;
import com.ynhenc.gis.model.map.*;
import com.ynhenc.gis.model.shape.*;
import com.ynhenc.gis.model.style.*;
import com.ynhenc.gis.ui.resource.*;
import com.ynhenc.gis.ui.viewer_01.map_viewer.*;

public class ColListener_08_IconImageSelector extends ColListener {

	@Override
	public void setValueAt(Object value, int row, int col,
			GisProject gisProject, MapViewer mapViewer, JTable jTable) {

		IconImage iconImage = null;
		if (value instanceof IconImage) {
			iconImage = (IconImage) value;
		}

		Layer layer = this.getLayer(row, gisProject);

		if (layer != null) {

			Style_01_Text textStyle = layer.getTextStyle();

			if (textStyle != null) {
				textStyle.setIconImage(iconImage);
				mapViewer.repaint_MapViewer_After_Creating_Map_Again();
			}

		}

	}

	@Override
	public Object getValueAt(int row, int col, GisProject gisProject) {

		if (this.getGisProject() != gisProject) {
			this.setGisProject(gisProject);
		}

		Layer layer = this.getLayer(row, gisProject);

		if (layer != null) {

			Style_01_Text textStyle = layer.getTextStyle();

			if (textStyle != null) {
				IconImage iconImage = textStyle.getIconImage();
				if (iconImage != null) {
					return iconImage;
				}
			}

		}

		return "없음";
	}

	@Override
	public TableCellRenderer getTableCellRenderer() {

		Renderer_08_IconImage renderer = new Renderer_08_IconImage();

		JComboBox comboBox = this.getJComboBox();
		comboBox.setRenderer(renderer);

		return renderer;

	}

	@Override
	public TableCellEditor getTableCellEditor() {

		JComboBox comboBox = this.getJComboBox();

		DefaultCellEditor tableCellEditor = new DefaultCellEditor(comboBox);

		return tableCellEditor;
	}

	private Vector<IconImage> getIconImageList(GisProject gisProject) {

		return gisProject.getIconImageList();

	}

	private void configureJComboBox( GisProject gisProject ) {
		JComboBox comboBox = this.getJComboBox();

		comboBox.removeAllItems();

		comboBox.addItem("없음");
		Vector<IconImage> iconImageList = this.getIconImageList( gisProject );
		for (IconImage iconImage : iconImageList) {
			comboBox.addItem(iconImage);
		}

		comboBox.setSelectedIndex(0);

	}

	private JComboBox getJComboBox() {
		if (this.comboBox == null) {
			this.comboBox = new JComboBox();
		}
		return this.comboBox;
	}

	public ColListener_08_IconImageSelector(String name, int width,
			boolean editable) {
		super(name, width, editable);
	}

	public GisProject getGisProject() {
		return this.gisProject;
	}

	public void setGisProject(GisProject gisProject) {
		this.gisProject = gisProject;

		this.configureJComboBox( gisProject );

	}

	private JComboBox comboBox;

	private GisProject gisProject;

}
