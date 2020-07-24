package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.ynhenc.gis.model.map.GisProject;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.model.style.Style_05_General;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;

public class ColListener_04_LineStrokeSelector extends ColListener {

	@Override
	public void setValueAt(Object value, int row, int col,
			GisProject gisProject, MapViewer mapViewer, JTable jTable) {
		
		if( value instanceof BasicStroke ) {
			BasicStroke lineStroke = (BasicStroke) value;
			
			Layer layer = gisProject.getMapData_Base().getLayer(row);

			Style_05_General styleGeneral = layer.getShapeStyle();
			
			styleGeneral.setLineStroke(lineStroke);
			
			this.repaintMapViewerAndStyleEditor(mapViewer);
			
		}
	}

	@Override
	public Object getValueAt(int row, int col, GisProject gisProject) {
		
		Layer layer = this.getLayer(row, gisProject);
		
		if( layer != null ) {
			return layer.getShapeStyle().getOrgLineStroke() ;
		} else {
			return "";
		} 
	}

	public TableCellRenderer getTableCellRenderer() {
		Renderer_04_LineStroke renderer = new Renderer_04_LineStroke();
		
		JComboBox comboBox = this.getComboBox();
		comboBox.setRenderer(renderer);
		
		return renderer;
	}

	public TableCellEditor getTableCellEditor() {
		DefaultCellEditor editor = new DefaultCellEditor( this.getComboBox() );
		return editor;
	}

	public ColListener_04_LineStrokeSelector(String name, int width,
			boolean editable) {
		super(name, width, editable);
	}

	private JComboBox getComboBox() {
		if (this.comboBox == null) {
			BasicStroke[] strokeList = new BasicStroke[] {
					new BasicStroke(2),
					new BasicStroke(2, BasicStroke.CAP_BUTT,
							BasicStroke.JOIN_BEVEL, 0, new float[] { 2, 2 }, 0),
					new BasicStroke(2, BasicStroke.CAP_BUTT,
							BasicStroke.JOIN_BEVEL, 0, new float[] { 4, 3 }, 0),
					new BasicStroke(2, BasicStroke.CAP_BUTT,
							BasicStroke.JOIN_BEVEL, 0, new float[] { 9, 4 }, 0),
					new BasicStroke(2, BasicStroke.CAP_BUTT,
							BasicStroke.JOIN_BEVEL, 0,
							new float[] { 9, 4, 4, 4 }, 0),
					new BasicStroke(2, BasicStroke.CAP_BUTT,
							BasicStroke.JOIN_BEVEL, 0, new float[] { 14, 4 }, 0),
					new BasicStroke(2, BasicStroke.CAP_BUTT,
							BasicStroke.JOIN_BEVEL, 0, new float[] { 14, 4, 4,
									4 }, 0),
					new BasicStroke(2, BasicStroke.CAP_BUTT,
							BasicStroke.JOIN_BEVEL, 0, new float[] { 14, 4, 4,
									4, 4, 4 }, 0) };
			this.comboBox = new JComboBox( strokeList );
		}
		return this.comboBox;
	} 
	
	private JComboBox comboBox;

}
