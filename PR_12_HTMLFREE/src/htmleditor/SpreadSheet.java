package htmleditor;

/**
 * Title:        Test Publishing System
 * Description:  Internet Based Test Publishing System V1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import jcosmos.*;

public class SpreadSheet extends JTable {

  private ChartData chartData;

  public SpreadSheet(ChartData chartData) {

    super( chartData );

    this.chartData = chartData;

    setCellSelectionEnabled( true );
    setColumnSelectionAllowed( true );

    addMouseListener(

      new MouseAdapter() {

	 public void mousePressed(MouseEvent e) {

	     if( Utility.isRightMouseButton( e ) ) {

		 showRowColPopupMenu( e );

	     }

	 }

      }

    );

  }

  private void showRowColPopupMenu(MouseEvent e) {

    final int selRow = getSelectedRow();
    final int selCol = getSelectedColumn();

    JPopupMenu popupMenu = new JPopupMenu("AAAa");

    JMenu row = new JMenu( "Row" );

    JMenuItem rowAdd = new JMenuItem( "Add" );
    JMenuItem rowDel = new JMenuItem( "Delete" );

    JMenu col = new JMenu( "Col" );

    JMenuItem colAdd = new JMenuItem( "Add" );
    JMenuItem colDel = new JMenuItem( "Delete" );

    popupMenu.add( row );
    row.add( rowAdd );
    row.add( rowDel );
    popupMenu.add( col );
    col.add( colAdd );
    col.add( colDel );

    row.setEnabled( selRow > -1 );
    col.setEnabled( selCol > -1 );

    rowAdd.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	addChartDataRow( selRow );
      }
    });

    rowDel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	delChartDataRow( selRow );
      }
    });

    colAdd.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	addChartDataCol( selCol );
      }
    });

    colDel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	delChartDataCol( selCol );
      }
    });

    popupMenu.show( (Component) ( e.getSource() ), e.getX(), e.getY() );

  }

  private void addChartDataRow(int row) {

    chartData.addChartDataRow( row );

  }

  private void delChartDataRow(int row) {

    chartData.delChartDataRow( row );

  }

  private void addChartDataCol(int col) {

    chartData.addChartDataCol( col );

  }

  private void delChartDataCol(int col) {

    chartData.delChartDataCol( col );

  }

}