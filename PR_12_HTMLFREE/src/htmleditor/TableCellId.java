package htmleditor;

/**
 * Title:        HTML FREE EDITOR
 * Description:  Html Free Editor v2.0
 * Copyright:    Copyright (c) 2001
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.util.*;

public class TableCellId {

  private LinkedList rowId;
  private LinkedList colId;

  public TableCellId(LinkedList rowId, LinkedList colId) {

    this.rowId = rowId;
    this.colId = colId;

  }

  public LinkedList getRowId(){

    return rowId;

  }

  public LinkedList getColId() {

    return colId;

  }


  public boolean equals(TableCellId id) {

    return ( getRowId() == id.getRowId() ) && ( getColId() == id.getColId() );

  }

}