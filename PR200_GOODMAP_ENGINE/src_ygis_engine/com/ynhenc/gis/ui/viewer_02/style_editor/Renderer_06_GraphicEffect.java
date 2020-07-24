package com.ynhenc.gis.ui.viewer_02.style_editor;

import java.awt.*; 
import javax.swing.*;
import javax.swing.table.*;

import com.ynhenc.comm.DebugInterface;
import com.ynhenc.gis.model.shape.Layer; 

public class Renderer_06_GraphicEffect extends Renderer_00_Object { 

	@Override
	public Icon getIcon( Object value) {
		if( value instanceof Layer ) {
			return new Icon_02_LayerStyle((Layer) value , this.editMode ) ;  
		} else {
			return null ;
		}
	} 

	public Renderer_06_GraphicEffect( int editMode ) {
		this.editMode = editMode;
	}
	
	private int editMode; 

}