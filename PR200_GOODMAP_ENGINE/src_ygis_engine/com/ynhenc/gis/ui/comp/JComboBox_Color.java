package com.ynhenc.gis.ui.comp;

import java.awt.Color;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.ynhenc.comm.util.ColorManager;

public class JComboBox_Color extends JComboBox {

	public JComboBox_Color() {
		initComponent();
	} 

	private void initComponent() {
		ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(
				PREFERRED_COLOR_LIST );
		this.setModel(jComboBox1Model);
		this.setRenderer(new ComboBoxRenderer_Color());
	}
	
	public void setSelectedColor( Color color ) {
		if( color != null ) {
			for( int i = 0, iLen = this.getItemCount() ; i < iLen ; i ++ ) {
				if( color.equals( this.getItemAt(i))) {
					this.setSelectedIndex( i );
					
					return;
				}
			}
			
			this.addItem( color );
			this.setSelectedItem( color );
		}
	}

	private static final Object[] PREFERRED_COLOR_LIST = { 

		    "다른색",
		
		    new Color( 255, 0, 0 ),
		    
		    new Color( 0, 255, 0 ),
		    
		    new Color( 0, 0, 255 ),
			
			new Color(132, 0, 0), // second row
			new Color(255, 99, 0),
			new Color(132, 132, 0),
			new Color(0, 132, 0),
			new Color(0, 132, 132),
			new Color(0, 0, 255),
			new Color(99, 99, 156),
			new Color(132, 132, 132),

			new Color(255, 0, 0), // third row
			new Color(255, 156, 0),
			new Color(156, 206, 0),
			new Color(49, 156, 99),
			new Color(49, 206, 206),
			new Color(49, 99, 255),
			new Color(132, 0, 132),
			new Color(148, 148, 148),

			new Color(255, 0, 255), // fouth row
			new Color(255, 206, 0),
			new Color(255, 255, 0),
			new Color(0, 255, 0),
			new Color(0, 255, 255),
			new Color(0, 206, 255),
			new Color(156, 49, 99),
			new Color(192, 192, 192),

			new Color(255, 156, 206), // fifth row
			new Color(255, 206, 156),
			new Color(255, 255, 156),
			new Color(206, 255, 206),
			new Color(206, 255, 255),
			new Color(156, 206, 255),
			new Color(206, 156, 255),
			new Color(255, 255, 255),

			// Color 배열 - 기본색지정
			new Color(0, 0, 0), // first row
			new Color(156, 49, 0), new Color(49, 49, 0), new Color(0, 49, 0),
			new Color(0, 49, 99), new Color(0, 0, 132), new Color(49, 49, 156),
			new Color(49, 49, 49),

	};

}
