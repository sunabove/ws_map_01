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
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import jcosmos.*;

public class TableDocument extends HtmlDocument {

  private LinkedList colDocList = new LinkedList();
  private LinkedList rowDocList = new LinkedList();

  public TableDocument(final HtmlDocument parentDoc,
		       final int row, final int col,
		       final Rectangle2D area) {

    this( parentDoc, row, col, area, null );

  }

  private TableDocument(final HtmlDocument parentDoc,
		       final int row, final int col,
		       final Rectangle2D area, final TableDocument cloneTable) {

    super( parentDoc );

    setTable( row, col, area, cloneTable );

  }

  private void setTable(final int row, final int col, final Rectangle2D area, final TableDocument cloneTable) {

    setRequestNewDocument( false );

    super.setLocation( area.getX(), area.getY() );

    final LinkedList imageElements = getImageElementsList();

    Utility.removeAllListElements( imageElements );

    final int w = (int) area.getWidth();
    final int h = (int) area.getHeight();

    final int cw = w/col;
    final int ch = h/row;

    final LinkedList colDocList = this.colDocList;

    Utility.removeAllListElements( colDocList );

    for(int i = 0; i < col; i ++ ) {

      colDocList.addLast( new LinkedList( ) );

    }

    final LinkedList rowDocList = this.rowDocList;

    Utility.removeAllListElements( rowDocList );

    for(int i = 0; i < row; i ++ ) {

      rowDocList.addLast( new LinkedList( ) );

    }

    final HtmlDocument tableDoc = this;

    final ImageElement [] cloneTableCellDocs = ( cloneTable != null ) ? cloneTable.getImageElementsArray() : null ;

    for(int r = 0; r < row; r ++ ) {

      for(int c = 0; c < col; c ++ ) {

	  final HtmlDocument cellDoc = ( cloneTableCellDocs != null ) ?
				       (HtmlDocument) ( cloneTableCellDocs[ r*col + c ].clone( this ) )
				       : new HtmlDocument( this );

	  cellDoc.setStyle( ImageElement.FRONT_TEXT );

	  final TableCellId id = getCellId( r, c );

	  id.getColId().addLast( cellDoc );
	  id.getRowId().addLast( cellDoc );

	  cellDoc.setTableCellId( id );

	  if( cloneTableCellDocs == null ) {
	  // 클론을 안 할 경우에만, 셀의 영역을 초기화한다.

	      final int cx = c*cw;
	      final int cy = r*ch;

	      cellDoc.setArea( cx, cy, cw, ch );

	  }

	  tableDoc.addImageElement( cellDoc );

      }

    }

    checkCellAreaValidity();

    setRequestNewDocument( true );

//    requestNewHtmlDocView();

  }

  public Shape [] getCornerRects() {

    Shape [] cornerRects = new Shape [] {

					null, null, null,
					null,       null,
					null, null, getBottomRightCornerRect(),
					getTopLeftCornerRect()

				      };

    return cornerRects;

  }

  public Shape getTopLeftCornerRect() {

    final Rectangle2D area = getArea();

    final double w = 10;

    final int rightInset = 1;

    Rectangle2D outRect = new Rectangle2D.Double(
				    area.getX() - w - rightInset,
				    area.getY() - w - rightInset,
				    w,
				    w
				  );

    final double insideInset = 3;

    Rectangle2D insideRect = new Rectangle2D.Double(
				    outRect.getX() + insideInset,
				    outRect.getY() + insideInset,
				    w - 2*insideInset,
				    w - 2*insideInset
				  );

    Area outRectArea = new Area( outRect );

    outRectArea.subtract( new Area( insideRect ) );

    return outRectArea;

  }

  public Shape getBottomRightCornerRect() {

    final Rectangle2D area = getArea();

    final double w = 6;

    final int rightInset = 1;

    return new Rectangle2D.Double(
				    area.getX() + area.getWidth() + rightInset,
				    area.getY() + area.getHeight() + rightInset,
				    w,
				    w
				  );

  }

  protected void checkCellAreaValidity() {

    setRequestNewDocument( false );

    final int colNum = getColNum();

    final Insets scanMargin = getScanMargin();

    int x = scanMargin.left;

    for( int col = 0; col < colNum; col ++ ) {

      final int maxW = getMaxWidthOfCol( col );

      final Iterator colCellDocs = getColCellList( col ).iterator();

      while( colCellDocs.hasNext() ) {

	final HtmlDocument cellDoc = (HtmlDocument) colCellDocs.next();

	final Rectangle2D area = cellDoc.getArea();

	cellDoc.setArea( x, area.getY(), maxW, area.getHeight() );

      }

      x += maxW;

    }

    final int rowNum = getRowNum();

    int y = scanMargin.top;

    for(int row = 0; row < rowNum; row ++ ) {

      final int maxH = getMaxHeightOfRow( row );

      final Iterator rowCellDocs = getRowCellList( row ).iterator();

      while( rowCellDocs.hasNext() ) {

	final HtmlDocument cellDoc = (HtmlDocument) rowCellDocs.next();

	final Rectangle2D area = cellDoc.getArea();

	cellDoc.setRequestNewDocument( false );

	cellDoc.setArea( area.getX(), y, area.getWidth(), maxH );

	cellDoc.setRequestNewDocument( true );

      }

      y += maxH;

    }

    super.setSize( x , y );

    setRequestNewDocument( true );

  }

  // 테이블을 읽어 들인 후에 글자가 안 나타나는 버그를 해결하기 위해서
  // HtmlLayer 에서 테이블을 읽어 들인 후 강제적으로
  // 테이블과 셀의 뷰를 널로 설정하여,
  // 페인트시 다시 뷰를 생성하도록 데이터(뷰)를 설정한다.

  public void setHtmlDocViewAsNull() {

    this.docView = null;

    final ImageElement [] imageElements = this.getImageElementsArray();

    for( int i = 0, len = imageElements.length; i < len; i ++ ) {

      final HtmlDocument cellDoc = (HtmlDocument) imageElements[ i ];

      cellDoc.setHtmlDocViewAsNull();

    }

  }

  private int getMaxWidthOfCol(int col) {

    final LinkedList colCellList = getColCellList( col );

    double maxW = 0;

    final Iterator it = colCellList.iterator();

    while( it.hasNext() ) {

      final HtmlDocument doc = (HtmlDocument) it.next();

      final Rectangle2D area = doc.getArea();

      final double w = area.getWidth();

      maxW = ( maxW > w ) ? maxW : w;

    }

    return (int) maxW;

  }

  private int getMinWidthOfCol(int col) {

    final LinkedList colCellList = getColCellList( col );

    double minW = 0;

    final Iterator it = colCellList.iterator();

    while( it.hasNext() ) {

      final HtmlDocument doc = (HtmlDocument) it.next();

      final double w = doc.getMinWidth();

      minW = ( minW > w ) ? minW : w;

    }

    return (int) minW;

  }

  private int getMaxHeightOfRow(int row) {

    final LinkedList rowCellList = getRowCellList( row );

    double maxH = 0;

    final Iterator it = rowCellList.iterator();

    while( it.hasNext() ) {

      final HtmlDocument doc = (HtmlDocument) it.next();

      final Rectangle2D area = doc.getArea();

      final double h = area.getHeight();

      maxH = ( maxH > h ) ? maxH : h;

    }

    return (int) maxH;

  }

  private LinkedList getRowCellList( int row ) {

    return (LinkedList) rowDocList.get( row );

  }

  private LinkedList getColCellList( int col ) {

    return (LinkedList) colDocList.get( col );

  }

  private TableCellId getCellId(final int row, final int col) {

    final LinkedList rowDocList = this.rowDocList;

    final LinkedList rowId = (LinkedList) rowDocList.get( row );

    final LinkedList colDocList = this.colDocList;

    final LinkedList colId = (LinkedList) colDocList.get( col );

    return new TableCellId ( rowId, colId );

  }

  private int getRowNum() {

    return rowDocList.size();

  }

  private int getColNum() {

    return colDocList.size();

  }

  public void setSize( double w, double h ) {

    final Rectangle2D preArea = getArea();

    final double wratio = w  / preArea.getWidth(); // 폭 변화율
    final double hratio = h / preArea.getHeight(); // 높이 변화율

//    Utility.debug(this, "WRATIO = " + wratio + ", HRATIO = " + hratio );

    if( wratio == 1 && hratio == 1 ) {

      // 폭과 높이의 변화가 동시에 없으면.....
      // 셀들의 폭과 높이를 조절하지 않는다.

      return;

    }

//    Utility.debug(this, "WRATIO = " + wratio + ", HRATIO = " + hratio );

    final Iterator cellDocList = getImageElementsList().iterator();

    while( cellDocList.hasNext() ) {

      final HtmlDocument cellDoc = (HtmlDocument) cellDocList.next();

      cellDoc.setRequestNewDocument( false ); // requestNewDocument를 디스에이블 하게 만든다.

      final Rectangle2D cellDocArea = cellDoc.getArea();

      cellDoc.setSize( cellDocArea.getWidth()*wratio, cellDocArea.getHeight()*hratio );

      cellDoc.setRequestNewDocument( true ); // requestNewDocument를 에이블하게 한다.

    }

    requestNewHtmlDocView();

  }

  public void requestNewHtmlDocView() {

    if( ! requestNewHtmlDocument ) {

      return;

    }

    checkCellAreaValidity();

    super.requestNewHtmlDocView();

  }

  public int getMinWidth() {

    Insets scanMargin = getScanMargin();

    final int colNum = getColNum();

    int minW = scanMargin.right + scanMargin.left;

    for(int col = 0; col < colNum; col ++ ) {

      minW += getMinWidthOfCol( col );

    }

    return minW;

  }

  public void moveCell(int top, HtmlDocument cellDoc, MouseEvent to) {

    final TableCellId cellId = cellDoc.getTableCellId();

    if( top == BOTTOM_BOUNDARY ) {

      final int rowIndex = rowDocList.indexOf( cellId.getRowId() );

      moveCellVertically( rowIndex, to );

    }  else if( top == LEFT_BOUNDARY ) { // 왽쪽 바운더리

      final int colIndex = colDocList.indexOf( cellId.getColId() ) - 1 ;
      // 칼럼 인덱스를 1 만큼 뺀다. 왼쪽 쪽 바운더리에서 작업하므로....

      moveCellHorizontally( colIndex, to );

    } else if( top == RIGHT_BOUNDARY ) { // 오른 쪽 바운더리

      final int colIndex = colDocList.indexOf( cellId.getColId() );

      moveCellHorizontally( colIndex, to );

    }

  }

  private void moveCellVertically(final int rowIndex, final MouseEvent to) {

      Utility.debug(this, "ROW INDEX = " + rowIndex );

      setRequestNewDocument( false );

      final int y = to.getY();

      final LinkedList rowCellList = getRowCellList( rowIndex );

      final Iterator it = rowCellList.iterator();

      while( it.hasNext() ) {

	final HtmlDocument cellDoc = (HtmlDocument) it.next();

	cellDoc.setRequestNewDocument( false );

	final Rectangle2D absArea = cellDoc.getAbsoluteArea();

	final double w = absArea.getWidth();

	double h = y - absArea.getY();

	final double minH = cellDoc.getMinHeight();

	h = ( h > minH ) ? h : minH;

	cellDoc.setSize( w, h );

	cellDoc.setRequestNewDocument( true );

      }

      setRequestNewDocument( true );

  }

  private void moveCellHorizontally(final int colIndex, final MouseEvent to) {

      Utility.debug(this, "COL INDEX = " + colIndex );

      setRequestNewDocument( false );

      final int x = to.getX();

      double width, leftMinWidth, rightMinWidth;

      final int colNum = getColNum();

      if( colIndex == -1 ) {

	 leftMinWidth = 0;
	 rightMinWidth = getMinWidthOfCol( 0 );
	 width = getMaxWidthOfCol( 0 );

      } else if( colIndex == colNum - 1 ) {

	 leftMinWidth = getMinWidthOfCol( colIndex );
	 rightMinWidth = 0;
	 width = getMaxWidthOfCol( colIndex );

      } else {

	 leftMinWidth = getMinWidthOfCol( colIndex );
	 rightMinWidth = getMinWidthOfCol( colIndex + 1 );
	 width = getMaxWidthOfCol( colIndex ) + getMaxWidthOfCol( colIndex + 1 );

      }

      if( -1 < colIndex ) {

	 final LinkedList currColCellList = getColCellList( colIndex );

	 final HtmlDocument refCellDoc = (HtmlDocument) currColCellList.get( 0 ); // reference cell document

	 final Rectangle2D refAbsArea = refCellDoc.getAbsoluteArea(); // referenced absolute area

	 double w = x - refAbsArea.getX();

	 w = ( w > leftMinWidth ) ? w : leftMinWidth;

	 final Iterator it = currColCellList.iterator();

	 while( it.hasNext() ) {

	     final HtmlDocument cellDoc = (HtmlDocument) it.next();

	     cellDoc.setRequestNewDocument( false );

	     final Rectangle2D cellDocArea = cellDoc.getArea();

	     cellDoc.setSize( w, cellDocArea.getHeight() );

	     cellDoc.setRequestNewDocument( true );

	 }

      }

      final int nextColIndex = colIndex + 1;

      if( nextColIndex < colNum ) {

	 final LinkedList nextColCellList = getColCellList( nextColIndex );

	 final HtmlDocument refCellDoc = (HtmlDocument) nextColCellList.get( 0 ); // reference cell document

	 final Rectangle2D refAbsArea = refCellDoc.getAbsoluteArea(); // referenced absolute area

	 double w = refAbsArea.getX() + refAbsArea.getWidth() - x ;

	 w = ( w > rightMinWidth ) ? w : rightMinWidth;

	 final Iterator it = nextColCellList.iterator();

	 while( it.hasNext() ) {

	     final HtmlDocument cellDoc = (HtmlDocument) it.next();

	     cellDoc.setRequestNewDocument( false );

	     final Rectangle2D cellDocArea = cellDoc.getArea();

	     cellDoc.setSize( w, cellDocArea.getHeight() );

	     cellDoc.setRequestNewDocument( true );

	 }

      }

      if( colIndex == - 1 ) { // 테이블의 제일 좌측에서 마우스를 끌었을 때...

	 final Point2D absLoc = getAbsoluteLocation();

	 final double currWidth = getMaxWidthOfCol( 0 );

	 final Rectangle2D absBounds = getAbsoluteBounds();

	 double newX = x;

	 newX = ( newX > absBounds.getX() ) ? newX : absBounds.getX();

	 double maxX = absLoc.getX() + currWidth - rightMinWidth;

	 newX = ( newX < maxX ) ? newX : maxX;

	 final Point2D loc = getLocation();

	 final double mx = newX - absLoc.getX();

	 setLocation( loc.getX() + mx, loc.getY() );

      }

      setRequestNewDocument( true );

  }

  public String getAdditionalDivTag() {

      return " row: " + getRowNum() + "; col: " + getColNum() + ";";

  }

  public Object clone(final HtmlDocument parentDoc) {

      final int rowNum = getRowNum();
      final int colNum = getColNum();

      final Rectangle2D initTableArea = new Rectangle2D.Double(
					   0, 0,
					   AppRegistry.MINIMUN_DOCUMENT_WIDTH,
					   AppRegistry.MINIMUN_DOCUMENT_HEIGHT
				       );

      // 테이블 크기는 알아서 내부 셀의 크기에 의해서 자동 조절 되므로,
      // 파일에서 읽어 들일 때는 초기에 충분히 작게 잡아서
      // 셀을 읽어 들인 후에 자동으로 크기를 조절하게 한다.

      final TableDocument tableDoc = new TableDocument( parentDoc, rowNum, colNum, initTableArea, this );

      final Insets scanMargin = parentDoc.getScanMargin();

      // 클론된 도큐먼트의 로케이션 보정
      // 아버지 도큐먼트의 스캔마진의 두배 만큼 x, y를 잡아서...
      // 실제 복사시에.....멋있게 복사되도록 한다.

      tableDoc.setLocation( 2*scanMargin.left, 2*scanMargin.top );

      // 끝. 클론 도큐먼트 로케이션 보정.

      // 도큐먼트 색상 콜로닝

      tableDoc.setFillColor( getFillColor() );
      tableDoc.setBorderColor( getBorderColor() );
      tableDoc.setBorderWidth( getBorderWidth() );

      // 끝. 도큐먼트 속성 클로닝

      HtmlLayer.initHtmlDocumentCloned( tableDoc );

      // 끝. 콜론된 도큐먼트 초기화

      return tableDoc;

  }

}