package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class Renderer_10_IconDiamond extends Renderer_00_Object {
	
	@Override
	public Icon getIcon(Object value) {
		return new Icon_01_Diamond((Color) value) ; 
	} 

}
