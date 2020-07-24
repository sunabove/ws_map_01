package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.jwordart.model.fillEffect.GraphicEffect;
import com.jwordart.model.wordart.WordArtStyleFactory;
import com.jwordart.ui.comp.WordArtFillEffectEditor;
import com.jwordart.ui.comp.WordArtLineTextureEditor;
import com.jwordart.util.WordArtUtil;
import com.ynhenc.gis.model.map.GisProject;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.model.shape.LayerType;
import com.ynhenc.gis.model.style.Style_05_General;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;

public class ColListener_06_GraphicEffectEditor extends ColListener {

	@Override
	public Object getValueAt(int row, int col, GisProject gisProject) {
		// TODO ä���ȿ�� ���� ���̾�� ���� ���´�.

		Layer layer = this.getLayer(row, gisProject);

		if (layer != null) {
			return layer;
		} else {
			return "";
		}

	}

	@Override
	public void setValueAt(final Object value, final int row, final int col,
			final GisProject gisProject, final MapViewer mapViewer, JTable jTable) {

		int editMode = this.editMode;

		if (editMode == 0) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					setValueAt_FillMode_Runnable(value, row, col, gisProject,
							mapViewer);
				}
			});
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					setValueAt_LineMode_Runnable(value, row, col, gisProject,
							mapViewer);
				}
			});
		}

	}

	private void setValueAt_LineMode_Runnable(Object value, int row, int col,
			GisProject gisProject, MapViewer mapViewer) {

		this.debug("VALUE = " + value); // ������� üũ�ڽ��� �ִ� ���� �Ѿ�´�.

		int index = -1;

		if (value instanceof IndexObject) {
			IndexObject indexObject = (IndexObject) value;
			index = indexObject.getIndex();
		}

		Layer layer = gisProject.getMapData_Base().getLayer(row);
		Style_05_General styleGeneral = layer.getShapeStyle();

		if (index == 1) {
			// ä��� ������ �����ϸ�, �� ������ �η� �����Ѵ�.
			styleGeneral.setGraphicEffect_Line(null);
			styleGeneral.setLineColor(null);

			repaintMapViewerAndStyleEditor(mapViewer);
		} else if (index == 2) {
			// �ٸ� ���� �����ϸ�, �� ������ �����Ѵ�.
			Component comp = mapViewer;

			Color refColor = styleGeneral.getLineColor();

			Color selColor = JColorChooser.showDialog(comp, "��", refColor);

			if (selColor == null) {
				return;
			} else {
				refColor = selColor;
				styleGeneral.setLineColor(selColor);

				repaintMapViewerAndStyleEditor(mapViewer);
			}
		} else if (index == 3) { // �������� �����Ѵ�.

			GraphicEffect graphicEffect = styleGeneral.getGraphicEffect_Line();

			WordArtLineTextureEditor editor = WordArtLineTextureEditor.EDITOR;

			if (graphicEffect == null) {
				graphicEffect = new GraphicEffect();
				Color lineColor = styleGeneral.getLineColor();
				if (lineColor != null) {
					graphicEffect.setFirstGradientColor(lineColor);
				}
			} else {
				graphicEffect = graphicEffect.create();
			}

			editor.setGraphicEffect(graphicEffect);

			editor.setModal(true);
			editor.setLocationByPlatform(true);
			editor.setVisible(true);

			if (true) {

				graphicEffect = editor.getGrahicEffect();

				if (graphicEffect != null) {

					graphicEffect.create();

					styleGeneral.setGraphicEffect_Line(graphicEffect);

					repaintMapViewerAndStyleEditor(mapViewer);
				}

			}

		}
	}

	private void setValueAt_FillMode_Runnable(Object value, int row, int col,
			GisProject gisProject, MapViewer mapViewer) {

		this.debug("VALUE = " + value); // ������� üũ�ڽ��� �ִ� ���� �Ѿ�´�.

		int index = -1;

		if (value instanceof IndexObject) {
			IndexObject indexObject = (IndexObject) value;
			index = indexObject.getIndex();
		}

		Layer layer = gisProject.getMapData_Base().getLayer(row);
		Style_05_General styleGeneral = layer.getShapeStyle();

		if (index == 1) {
			// ä��� ������ �����ϸ�, ä��� Į�� �η� �����Ѵ�.
			styleGeneral.setGraphicEffect_Fill(null);
			styleGeneral.setFillColor(null);

			repaintMapViewerAndStyleEditor(mapViewer);
		} else if (index == 2) {
			// �ٸ� ���� �����ϸ�, ä��� Į�� �����Ѵ�.
			Component comp = mapViewer;

			Color refColor = styleGeneral.getFillColor();

			Color selColor = JColorChooser.showDialog(comp, "��", refColor);

			if (selColor == null) {
				debug.println(this, "Null Color. canceled fill color.");
				return;
			} else {
				refColor = selColor;
				styleGeneral.setFillColor(selColor);

				repaintMapViewerAndStyleEditor(mapViewer);
			}
		} else if (index == 3) { // ä��� ȿ���� �����ϴ� ���

			GraphicEffect graphicEffect = styleGeneral.getGraphicEffect_Fill();

			WordArtFillEffectEditor editor = WordArtFillEffectEditor.EDITOR;

			if (graphicEffect == null) {
				graphicEffect = new GraphicEffect();
				Color fillColor = styleGeneral.getFillColor();
				if (fillColor != null) {
					graphicEffect.setFirstGradientColor(fillColor);
				}
			} else {
				graphicEffect = graphicEffect.create();
			}

			editor.setGraphicEffect(graphicEffect);

			editor.setModal(true);
			editor.setLocationByPlatform(true);
			editor.setVisible(true);

			if (true) {

				graphicEffect = editor.getGraphicEffect();

				if (graphicEffect != null) {

					graphicEffect.create();

					styleGeneral.setGraphicEffect_Fill(graphicEffect);

					repaintMapViewerAndStyleEditor(mapViewer);
				}

			}

		}
	}

	@Override
	public boolean isCellEditable(int row, int col, GisProject gisProject) {
		Layer layer = gisProject.getMapData_Base().getLayer(row);

		if (this.editMode == 0) {
			// ��� ������ ��.....
			// ���� ���̾��� ���� �������� �ʴ´�.
			return LayerType.LINE != layer.getLyrType();
		}

		return true;
		// ����Ÿ�Ը� �ƴϸ� ä��Ⱑ �����ϴ�.
	}

	@Override
	public TableCellRenderer getTableCellRenderer() {

		Renderer_06_GraphicEffect renderer = new Renderer_06_GraphicEffect(
				this.editMode);

		JComboBox graphicEffectComboBox = this.getGraphicEffectComboBox();

		graphicEffectComboBox.setRenderer(renderer);

		return renderer;
	}

	@Override
	public TableCellEditor getTableCellEditor() {

		JComboBox graphicEffectComboBox = this.getGraphicEffectComboBox();

		DefaultCellEditor tableCellEditor = new DefaultCellEditor(
				graphicEffectComboBox);
		return tableCellEditor;
	}

	public ColListener_06_GraphicEffectEditor(String name, int width,
			boolean editable, int editMode) {
		super(name, width, editable);
		this.editMode = editMode;
	}

	public JComboBox getGraphicEffectComboBox() {

		if (this.graphicEffectComboBox != null) {
			return this.graphicEffectComboBox;
		}

		if (this.editMode == 0) {
			// ä��� ���� �϶�....
			this.graphicEffectComboBox = new JComboBox(new IndexObject[] {
					new IndexObject(0, ""), new IndexObject(1, "����"),
					new IndexObject(2, "���"), new IndexObject(3, "ȿ��") });
		} else {
			this.graphicEffectComboBox = new JComboBox(new IndexObject[] {
					new IndexObject(0, ""), new IndexObject(1, "����"),
					new IndexObject(2, "����"), new IndexObject(3, "����") });
		}
		return this.graphicEffectComboBox;
	}

	private int editMode; // ��尡 0 �̸� ä��� ȿ�� ����
	// ��尡 1 �̸� ���� ȿ�� ����

	private JComboBox graphicEffectComboBox = null;

}
