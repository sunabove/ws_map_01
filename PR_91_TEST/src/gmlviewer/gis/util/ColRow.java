package gmlviewer.gis.util;

public class ColRow {

  public ColRow(int col, int row) {
    this.col = col;
    this.row = row;
  }

  public int getCol() {
    return col;
  }

  public int getRow() {
    return row;
  }

  private int col;
  private int row;

}