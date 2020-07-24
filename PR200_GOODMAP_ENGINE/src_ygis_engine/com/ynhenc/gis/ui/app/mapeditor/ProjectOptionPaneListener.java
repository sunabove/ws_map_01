package com.ynhenc.gis.ui.app.mapeditor;

import java.awt.Component;
import java.awt.event.*;

import javax.swing.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.gis.model.map.GisProject;
import com.ynhenc.gis.model.map.GisProjectOption;
import com.ynhenc.gis.projection.CoordinateConversion;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;

public class ProjectOptionPaneListener extends GisComLib implements ActionListener {

	public MapViewer getMapViewer() {
		return this.mapViewer;
	}

	public void setMapViewer(MapViewer mapViewer) {
		this.mapViewer = mapViewer;
	}

	public void actionPerformed(ActionEvent e) {

		Object src = e.getSource();

		MapViewer mapViewer = this.getMapViewer();

		if (mapViewer != null) {

			GisProject gisProject = mapViewer.getGisProject();

			GisProjectOption option = (gisProject != null) ? gisProject.getGisProjectOption() : null;

			if (src instanceof JCheckBox) {

				JCheckBox checkBox = (JCheckBox) src;

				if (checkBox.isShowing()) {

					String checkBoxName = checkBox.getName().trim().toUpperCase();

					boolean selected = checkBox.isSelected();

					if (option != null) {

						if (checkBoxName.equalsIgnoreCase("CrossLine")) {
							option.setCrossLine_Show(selected);
							mapViewer.repaint_MapViewer_After_Creating_Map_Again();
						} else if (checkBoxName.equalsIgnoreCase("AntiAliasing")) {
							option.setGraphics_HighQuality(selected);
							mapViewer.repaint_MapViewer_After_Creating_Map_Again();
						} else if (checkBoxName.equalsIgnoreCase("GlobalMbr")) {
							option.setGraphics_HighQuality(selected);
							mapViewer.repaint_MapViewer_After_Creating_Map_Again();
						} else if (checkBoxName.equalsIgnoreCase("Logo")) {
							option.setLogo_Show(selected);
							mapViewer.repaint_MapViewer_After_Creating_Map_Again();
						} else if (checkBoxName.equalsIgnoreCase("RenderingTime")) {
							option.setRenderingTime_Show(selected);
							mapViewer.repaint_MapViewer_After_Creating_Map_Again();
						}else if (checkBoxName.equalsIgnoreCase("ShapeAndIconLabelTogether")) {
							option.setDisplayShapeAndIconLabelTogether(selected);
							mapViewer.repaint_MapViewer_After_Creating_Map_Again();
						}

					}

				}

			} else if (src instanceof JComboBox) {
				JComboBox comboBox = (JComboBox) src;

				if (comboBox.isShowing()) {

					Object selObj = comboBox.getSelectedItem();

					if (selObj instanceof CoordinateConversion && option != null) {

						CoordinateConversion coordinateConversion = (CoordinateConversion) selObj;

						if (!coordinateConversion.equals(option.getCoordinateConversion())) {
							option.setCoordinateConversion(coordinateConversion);

							String message = "좌변변환 옵션이 변경되었습니다.\n프로젝트를 저장 후 다시 오픈하여 주세요.";

							Component comp = this.getMapViewer();

							this.messageDialog(comp, message);

						}
					}

				}
			}

		}
	}

	private MapViewer mapViewer;

}
