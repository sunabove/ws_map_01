
/**
 * Title:        java chart project<p>
 * Description:  jchart v1.0<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      JCOSMOS DEVELOPMENT<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package jchart;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class SpreadSheetTableModel extends AbstractTableModel {

     String data [][] = {

	 { "    ", "1분기", "2분기", "3분기"," 4분기", "    ", "1분기", "2분기", "3분기"," 4분기" },
	 { "동부", "34",   "22", "28", "5", "최고 가격", "50", "50", "60", "50" },
//	 { "동부", "20.4", "27.4", "90", "20.4", "최고 가격", "50", "50", "60", "50" },
	 { "서부", "3.93",   "2", "3.31", "1.53", "최저 가격", "40", "25", "40", "20" },
//	 { "서부", "30.6", "38.6", "34.6", "31.6", "최저 가격", "40", "25", "40", "20" },
	 { "북부", "45.9", "66.9", "45", "43.9", "최종 가격", "30", "25", "50", "40" },
	 { "중부", "45.9", "66.9", "45", "43.9",  "" },

	 { "    ", "1분기", "2분기", "3분기"," 4분기", "    ", "1분기", "2분기", "3분기"," 4분기" },
	 { "동부", "20.4", "27.4", "90", "20.4", "개시 가격", "20", "40", "60", "30" },
	 { "서부", "30.6", "38.6", "34.6", "31.6", "최고 가격", "50", "50", "60", "50" },
	 { "북부", "45.9", "66.9", "45", "43.9", "최저 가격", "40", "25", "40", "20" },
	 { "중부", "45.9", "66.9", "45", "43.9", "최종 가격", "30", "25", "50", "40" },

	 { "    ", "수박", "사과" },
	 { "봄", "200", "100" },
	 { "여름", "200", "100" },

	 { "    ", "수박", "사과" },
	 { "봄", "100", "30" },
	 { "여름", "200", "200" },

	 { "    ", "수박", "사과", "바나나" },
	 { "봄", "50", "40", "5" },
	 { "여름", "200", "70", "10" },
	 { "가을", "200", "100", "10" },

	 { "    ", "1분기", "2분기", "3분기"," 4분기", "5분기", "6분기" },
	 { "동부", "34",   "22",     "28",      "5",    "40",   "15" },

	 { "    ", "1분기", "2분기", "3분기"," 4분기", "5분기", "6분기" },
	 { "동부", "1",   "1",     "1",      "2",    "2",   "2" },

     };

     public int getColumnCount() {
	if( data != null && data[0] != null ) {
	   return data[0].length;
	}
	return 0;
     }

     public int getRowCount() {
	if( data != null ) {
	   return data.length;
	}
	return 0;
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
	try {
	    data[row][col] = "" + value;
	    fireTableRowsUpdated( row, col );
	} catch (Exception e) {
	    jcosmos.Utility.debug( e );
	}
     }

     public void open(File file) throws FileNotFoundException, IOException {
	BufferedReader in = new BufferedReader( new InputStreamReader( new FileInputStream( file ) ) );

	String text;

	int col = -1;

	Vector vec = new Vector();

	while( (text = in.readLine()) != null ) {

	   if( text.startsWith( "," ) ) {
	      text = " " + text;
	   }

	   StringTokenizer token = new StringTokenizer( text, "," );

	   if( col < 0 ) {
	       col = 0;
	       while( token.hasMoreElements() ) {
		  col ++;
		  token.nextToken();
	       }

	       token = new StringTokenizer( text, "," );
	   }

	   String [] obj = new String[ col ];

	   int i = 0;

	   while( token.hasMoreElements() ) {
	      obj[ i ] = token.nextToken();
	      i ++;
	   }

	   vec.addElement( obj );
	}

	int size = vec.size();

	String [][] data = new String [ size ][];

	for( int i = 0; i < size; i ++ ) {
	   data[ i ] = (String []) vec.elementAt( i );
	}

	this.data = data;

	super.fireTableDataChanged();
     }

     public void save(File file) {
     }

}