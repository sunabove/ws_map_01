
/**
 * Title:        java chart project<p>
 * Description:  jchart v1.0<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      JCOSMOS DEVELOPMENT<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package htmleditor;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import jcosmos.*;

public class ChartData extends AbstractTableModel {

     public static final int BAR_TYPE = 0, PIE_TYPE = 1, LINE_TYPE = 2;

     public static String [][] getRefData() {

	 return new String [][] {

	    { "", "1993", "1994", "1995", "1996", "1997" },
	    { "Apples", "" + 950, "" + 1005, "" + 1210, "" + 1165, "" + 1255 },
	    { "Oranges", "" + 1435, "" + 1650, "" + 1555, "" + 1440, "" + 1595 },
	    { "Kiwi", "" + 1255, "" + 1650, "" + 1210, "" + 1165, "" + 1595 },
	    { "Grape", "" + 648, "" + 1200, "" + 1710, "" + 1105, "" + 800 }

	  };
     }

     private String data [][];

     private Dimension size;

     private int type;

     private String title;
     private String xTitle;
     private String yTitle;

     public ChartData(int type, String title, String xTitle, String yTitle, Dimension size) {

	 this( getRefData(), type, title, xTitle, yTitle, size );

     }

     public ChartData(String [][] data, int type, String title, String xTitle, String yTitle, Dimension size) {

	this.type = type;
	this.data = data;
	this.title = title;
	this.xTitle = xTitle;
	this.yTitle = yTitle;
	this.size = size;

     }

     public String getNameTag() {

	String tag = "name = \"chart\" type = \"" + type + "\" title = \"" + title
		     + "\" xtitle = \"" + xTitle + "\" ytitle = \"" + yTitle + "\"";

	tag += " data = \"";

	String [][] data = this.data;

	for(int i = 0; i < data.length; i ++ ) {

	  String row [] = data[ i ];

	  for(int k = 0; k < row.length; k ++ ) {

	    tag += row[ k ];

	    if( k < row.length - 1 ) {

	      tag += "\\";

	    }

	  }

	  if( i < data.length - 1 ) {

	    tag += "\\\\";

	  }

	}

	tag += "\" ";

	return tag;

     }

     public String toString() {

	 return getNameTag();

     }

     public void setType(int type) {

	this.type = type;

     }

     public int getType() {

	return type;

     }

     public void setSize(int width, int height) {

	 setSize( new Dimension( width, height ) );

     }

     public void setSize(Dimension size) {

	this.size = size;

     }

     public Dimension getSize() {

	return size;

     }

     public ChartData cloneChartData() {

	return new ChartData( cloneData(), type, title, xTitle, yTitle, new Dimension( size ) );

     }

     private String [][] cloneData() {

	String [][] data = this.data;

	String [][] clone = new String [ data.length ] [];

	for(int i = 0, len = data.length; i < len; i ++ ) {

	  String [] src = data[ i ];
	  String [] dst = new String [ src.length ];

	  for(int k = 0; k < src.length; k ++ ) {

	      dst[ k ] = src[ k ];

	  }

	  clone[ i ] = dst;

	}

	return clone;

     }

     public String getTitle() {

	return title;

     }

     public String getXTitle() {

	return xTitle;

     }

     public String getYTtitle() {

	return yTitle;

     }

     public void setTitle(String title) {

	this.title = title;

     }

     public void setXTitle(String xTitle) {

	this.xTitle = xTitle;

     }

     public void setYTitle(String yTitle) {

	this.yTitle = yTitle;

     }

     public int getRowCount() {

	if( data != null ) {

	   return data.length;

	}

	return 0;

     }

     public int getColumnCount() {

	if( data != null && data[0] != null ) {

	   return data[0].length;

	}

	return 0;

     }

     public int getDataNum() {

	 return data.length - 1;

     }

     public String [] getLabels() {

	 String labels [] = new String [ data[0].length - 1 ];

	 System.arraycopy( data[0], 1, labels, 0, labels.length );

	 return labels;

     }

     public String getLabel(int index) {

	 return data[index + 1][0];

     }

     public double [] getData(int index) {

	String dataText [] = this.data[ index + 1 ];

	int len = dataText.length - 1;

	double data [] = new double[ len ];

	for(int i = 0; i < len; i ++ ) {

	  try {

	    data[ i ] = new Double( dataText[ i + 1] ).doubleValue();

	  } catch (Exception e) {

	    data[ i ] = 0;

	  }

	}

	return data;

     }

     public Class getColumnClass(int col) {
	 return String.class;
     }

     public boolean isCellEditable(int row, int col) {
	return true;
     }

     public Object getValueAt(int row, int col) {
	try {

	   return data[row][col];

	} catch (Exception e) {

	   return "";

	}
     }

     public void setValueAt(Object value, int row, int col) {

	data[row][col] = "" + value;
	fireTableRowsUpdated( row, col );

     }

     public void addChartDataRow(int row) {

	int rowNum = getRowCount();
	int colNum = getColumnCount();

	if( row < 0 || row > rowNum - 1 ) {

	  Utility.debug(this, "Invalid row index = " + row );

	  return;

	}

	String [][] data = this.data;

	String [][] cloneData = new String [ rowNum + 1 ] [];

	for(int i = 0; i < row; i ++ ) {

	  cloneData[ i ] = data[ i ];

	}

	// add a row
	String [] newRow  = new String[ colNum ];

	for(int i = 0; i < colNum; i ++ ) {

	  newRow[ i ] = "";

	}

	cloneData[ row ] = newRow;

	// end of addint a row

	for(int i = row ; i < rowNum; i ++ ) {

	    cloneData[ i + 1 ] = data[ i ];

	}

	this.data = cloneData;

	fireTableDataChanged();

     }

     public void addChartDataCol(int col) {

	int rowNum = getRowCount();
	int colNum = getColumnCount();

	if( col < 0 || col > colNum - 1 ) {

	  Utility.debug(this, "Invalid col index = " + col );

	  return;

	}

	String [][] data = this.data;

	String [][] cloneData = new String [ rowNum ] [];

	for(int i = 0; i < rowNum; i ++ ) {

	   String row [] = data[ i ];

	   String newRow [] = new String[ colNum + 1 ];

	   for( int k = 0; k < col; k ++ ) {

	       newRow[ k ] = row[ k ];

	   }

	   // add column element

	   newRow[ col ] = "";

	   for( int k = col; k < colNum; k ++ ) {

	      newRow[ k + 1 ] = row[ k ] ;

	   }

	   // end of adding column element

	   cloneData[ i ] = newRow;

	}

	this.data = cloneData;

	fireTableDataChanged();

     }

     public void delChartDataRow(int row) {

	int rowNum = getRowCount();
	int colNum = getColumnCount();

	if( row < 0 || row > rowNum - 1 ) {

	  Utility.debug(this, "Invalid row index = " + row );

	  return;

	}

	String [][] data = this.data;

	String [][] cloneData = new String [ rowNum - 1 ] [];

	for(int i = 0; i < row; i ++ ) {

	  cloneData[ i ] = data[ i ];

	}

	for(int i = row + 1; i < rowNum; i ++ ) {

	  cloneData[ i - 1 ] = data[ i ];

	}

	this.data = cloneData;

	fireTableDataChanged();

     }

     public void delChartDataCol(int col) {

	int rowNum = getRowCount();
	int colNum = getColumnCount();

	if( col < 0 || col > colNum - 1 ) {

	  Utility.debug(this, "Invalid col index = " + col );

	  return;

	}

	String [][] data = this.data;

	String [][] cloneData = new String [ rowNum ] [];

	for(int i = 0; i < rowNum; i ++ ) {

	   String row [] = data[ i ];

	   String newRow [] = new String[ colNum - 1 ];

	   for( int k = 0; k < col; k ++ ) {

	       newRow[ k ] = row[ k ];

	   }

	   for( int k = col + 1; k < colNum; k ++ ) {

	      newRow[ k - 1 ] = row[ k ] ;

	   }

	   // end of adding column element

	   cloneData[ i ] = newRow;

	}

	this.data = cloneData;

	fireTableDataChanged();

     }

}