package gmlviewer.gis.viewer;

import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import gmlviewer.gis.model.*;
import gmlviewer.gis.sbmoon.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JPropTable
    extends JTable {

  public JPropTable() {

    this.initTable();

  }

  private void initTable() {

    this.setModel( this.createTableModel() );

  }

  public void changeSelection(int rowIndex, int columnIndex, boolean toggle,
                              boolean extend) {
    super.changeSelection(rowIndex, columnIndex, toggle, extend);

    String selVal = "" + this.getModel().getValueAt(rowIndex, columnIndex);

    if ( rowIndex == 4 && columnIndex == 1 && selVal != null ) {

      // 홈 페이지 바로 가기 기능.....

      try {
        String command = "explorer " + selVal;

        final Process process = java.lang.Runtime.getRuntime().exec(
            command);

        final int result = process.waitFor();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }

    }
  }

  private Shape shape;

  public void setShapeAttribute(Shape shape) {

    if( this.shape != shape ) {

      this.shape = shape;

      this.initTable();

      this.validate();

    }

  }

  Object rows[][] = {
      //상호명/전화/주소/특징/홈페이지 순서로.

      { "상호명", "" },
      { "전화", "" },
      { "주소", "" },
      { "특징", "" },
      { "홈페이지", "" },

  };

  String columns[] = {
      "ID", ""
  };

  private TableModel createTableModel() {

    TableModel tm = new AbstractTableModel() {

      public int getRowCount() {
        return rows.length;
      }

      public int getColumnCount() {
        return columns.length;
      }

      public Object getValueAt(int r, int c) {
        if (c == 1 && shape != null) {
          ShapeAttribute sa = shape.getShapeAttribute();
          if (sa != null) {
            Property[] props = sa.getProperties();
            if (props != null && r < props.length) {
              return props[r].getValue();
            }
          }
        }
        return rows[r][c];
      }

      public String getColumnName(int c) {
        if (c == 1) {
          if (shape != null) {
            return "" + shape.getSid();
          }
        }
        return columns[c];
      }

    };

    return tm;

  }

}
