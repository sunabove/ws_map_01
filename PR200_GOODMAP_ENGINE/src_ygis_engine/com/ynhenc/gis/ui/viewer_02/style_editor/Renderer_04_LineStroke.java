package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Stroke;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class Renderer_04_LineStroke extends Renderer_00_Object {
	
	@Override
	public Icon getIcon( Object value) {
		 
		return new Icon_05_LineStroke((BasicStroke) value) ;
		
	}

}
