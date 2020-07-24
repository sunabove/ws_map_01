package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.ynhenc.gis.model.map.GisProject;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.model.shape.LayerType;
import com.ynhenc.gis.model.shape.SymbolArrow;
import com.ynhenc.gis.model.style.Style_05_General;
import com.ynhenc.gis.ui.resource.IconImage;
import com.ynhenc.gis.ui.resource.IconImageFactory;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;

public class ColListener_09_LineSymbolArrowSelector extends ColListener {

	@Override
	public Object getValueAt(int row, int col, GisProject gisProject) {
		// TODO 채우기효과 값을 레이어로 부터 얻어온다.

		Layer layer = this.getLayer(row, gisProject);

		if (layer != null) {

			Style_05_General shapeStyle = layer.getShapeStyle();

			return this.toInt( shapeStyle.getSymbolArrow().getType() ) ;

		} else {
			return -1;
		}

	}

	@Override
	public void setValueAt(final Object value, final int row, final int col, final GisProject gisProject,
			final MapViewer mapViewer, JTable jTable) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColListener_09_LineSymbolArrowSelector.this.setValueAt_Runnable(value, row, col, gisProject, mapViewer);
			}
		});

	}

	private void setValueAt_Runnable(Object value, int row, int col, GisProject gisProject, MapViewer mapViewer) {

		this.debug("VALUE = " + value); // 밸류값은 체크박스에 있는 놈이 넘어온다.

		if (value instanceof IndexObject ) {

			IndexObject indexObj = (IndexObject) value;

			int arrowType = indexObj.getIndex();

			Layer layer = this.getLayer(row, gisProject);

			Style_05_General styleGeneral = layer.getShapeStyle();

			if (arrowType > -1) {
				// 선 폭 변

				SymbolArrow arrow = styleGeneral.getSymbolArrow() ;

				if( arrowType == 0 ) {
					arrow.setType( SymbolArrow.Type.ARROW_ZERO );
				} else if( arrowType == 1 ) {
					arrow.setType( SymbolArrow.Type.ARROW_RIGHT );
				} else if( arrowType == 2 ) {
					arrow.setType( SymbolArrow.Type.ARROW_LEFT );
				} else if( arrowType == 3 ) {
					arrow.setType( SymbolArrow.Type.ARROW_BOTH );
				}

				this.repaintMapViewerAndStyleEditor(mapViewer);
			}

		}

	}

	@Override
	public boolean isCellEditable(int row, int col, GisProject gisProject) {

		Layer layer = this.getLayer(row, gisProject);

		return layer.getLyrType() == LayerType.LINE;
		// 라인타입만 아니면 채우기가 가능하다.
	}

	@Override
	public TableCellRenderer getTableCellRenderer() {

		return null;

	}

	@Override
	public TableCellEditor getTableCellEditor() {

		final JComboBox comboBox = this.getComboBox();

		DefaultCellEditor editor = new DefaultCellEditor(comboBox) {
			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

				Component c = super.getTableCellEditorComponent(table, value, isSelected, row, column);

				int selIdx = 0;

				if (value instanceof SymbolArrow) {

					SymbolArrow symboArrow = (SymbolArrow) value;

					SymbolArrow.Type arrowType = symboArrow.getType();

					if (arrowType == SymbolArrow.Type.ARROW_RIGHT) {
						selIdx = 1;
					} else if (arrowType == SymbolArrow.Type.ARROW_LEFT) {
						selIdx = 2;
					} else if (arrowType == SymbolArrow.Type.ARROW_BOTH) {
						selIdx = 3;
					}

				}

				comboBox.setSelectedIndex(selIdx);

				return c;
			}
		};

		return editor;
	}

	private Integer toInt( SymbolArrow.Type arrowType ) {

		int selIdx = 0;

		if (arrowType == SymbolArrow.Type.ARROW_RIGHT) {
			selIdx = 1;
		} else if (arrowType == SymbolArrow.Type.ARROW_LEFT) {
			selIdx = 2;
		} else if (arrowType == SymbolArrow.Type.ARROW_BOTH) {
			selIdx = 3;
		}

		return selIdx ;

	}

	public ColListener_09_LineSymbolArrowSelector(String name, int width, boolean editable) {
		super(name, width, editable);
	}

	public JComboBox getComboBox() {

		if (this.comboBox != null) {
			return this.comboBox;
		}

		String [] strList = { "―" , "→" , "←" , "↔" };

		IndexObject [] objList = IndexObject.getIndexObjectList( strList );

//		Integer[] objList = { 0, 1, 2, 3 };

		if (true) {
			this.comboBox = new JComboBox(objList);
		}

		return this.comboBox;
	}

	private JComboBox comboBox = null;

}
