
/**
 * Title:        Test Publishing System<p>
 * Description:  Internet Based Test Publishing System V1.0<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      JCOSMOS DEVELOPMENT<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package htmleditor;

import java.util.*;
import java.awt.*;
import java.net.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import javax.swing.*;
import java.awt.datatransfer.*;
import jcosmos.*;

public class HtmlDocument extends ImageElement implements CharacterUtility {

  // static members

  private final static Vector copiedHtmlElements = new Vector();

  private final static LinkedList selectedImageElements = new LinkedList();

  private static int pasteNumX, pasteNumY;

  private static String defaultUrl = AppRegistry.DEFAULT_URL;

  private static final Insets WORD_BOX_SCAN_MARGIN = new Insets( 10, 10, 10, 10 );

  private static final Insets TABLE_CELL_SCAN_MARGIN = new Insets( 5, 10, 5, 10 );

  private static final Insets TABLE_SCAN_MARGIN = new Insets( 0, 0, 0, 0 );

  protected static final Insets TOP_DOC_SCAN_MARGIN = new Insets( 30, 30, 30, 30 );

  private static int MOUSE_TOPOLOGY = OUT_OF_AREA;

  // end of static members

  private int startIndex = 0, endIndex = 0; // selection index

  private int refIndex = -1; // index used temporary for setting start and end index while mouse dragging

  private StringElement caretElement;

  private Color borderColor = Color.black;
  private Color fillColor = Color.white;
  private Color textColor = Color.black;
  private int borderWidth = AppRegistry.DEFAULT_DOCUMENT_BORDER_WIDTH ;

  private Insets scanMargin;

  protected HtmlDocView docView;

  private LinkedList stringElements = new LinkedList();
  private LinkedList imageElements = new LinkedList();
//  private LinkedList shapeElements = new LinkedList();

  private Rectangle cursorRect;

  private double rowCaretX = -1;
  private int refRowLen = 0;

  private HtmlEditorPane editor; // top most editor

  private TableCellId cellId;

  private Font defaultFont;

  protected boolean requestNewHtmlDocument = true;

  public HtmlDocument( HtmlEditorPane editor ) {

     this.editor = editor;

     this.initDocument( null );

  }

  protected HtmlDocument( HtmlDocument parentDoc ) {

     this.initDocument( parentDoc );

  }

  protected Font getDefaultFont() {

    return this.defaultFont;

  }

  public HtmlEditorPane getDocumentEditor() {

    return ( this.editor == null ) ? this.getParentDocument().getDocumentEditor() : this.editor ;

  }

  // 자기 자신을 아버지로 하는자식 도큐먼트를 생성하는 함수이다.

  public HtmlDocument createChildHtmlDocument() {

     return new HtmlDocument( this );

  }

  protected void setRequestNewDocument(boolean b) {

    this.requestNewHtmlDocument = b;

  }

  private void initDocument( HtmlDocument parentDoc ) {

     this.parentDoc = parentDoc;

     this.style = super.RECT;

     if( parentDoc == null ) {

	this.defaultFont = FontManager.getFont( AppRegistry.DEFAULT_FONT_NAME,
						Font.PLAIN,
						AppRegistry.DEFAULT_FONT_SIZE );

	this.scanMargin = TOP_DOC_SCAN_MARGIN;

     } else if( this instanceof TableDocument ) {

	this.defaultFont = FontManager.getFont( AppRegistry.DEFAULT_FONT_NAME,
						Font.PLAIN,
						AppRegistry.DEFAULT_TABLE_CELL_FONT_SIZE );

	this.scanMargin = TABLE_SCAN_MARGIN;

     } else if( this.isTableCell() ) {

	this.defaultFont = FontManager.getFont( AppRegistry.DEFAULT_FONT_NAME,
						Font.PLAIN,
						AppRegistry.DEFAULT_TABLE_CELL_FONT_SIZE );

	this.scanMargin = TABLE_CELL_SCAN_MARGIN;

     } else {

	this.defaultFont = FontManager.getFont( AppRegistry.DEFAULT_FONT_NAME,
						Font.PLAIN,
						AppRegistry.DEFAULT_FONT_SIZE );

	this.scanMargin = WORD_BOX_SCAN_MARGIN;

     }

     this.initDocument();

  }

  public void setTableCellId(TableCellId cellId) {

    this.cellId = cellId;

  }

  public TableCellId getTableCellId() {

    return this.cellId;

  }

  final public Insets getInsets() {

    return this.scanMargin;

  }

  public void setBorderColor(Color borderColor) {

    this.borderColor = borderColor;

  }

  public void setFillColor(Color fillColor) {
    this.fillColor = fillColor;
  }

  public Color getBorderColor() {

    return this.borderColor;

  }

  public Color getFillColor() {

    return this.fillColor;

  }

  public Color getTextColor() {

    return this.textColor;

  }

  public int getBorderWidth() {

    return this.borderWidth;

  }

  public void setBorderWidth(int borderWidth ) {

    this.borderWidth = borderWidth;

    this.requestNewHtmlDocView();

  }

  public static boolean isSelected(ImageElement ie) {

    return ( SEL_IMG_ELEM == ie );

  }

  public static void setSelectedImageElement(ImageElement ie, MouseEvent e) {

   // 마우스 이벤트가 널 값이면 키보드에서 넘겨주는 이벤트 이다.
   // 마우스 이벤트와 키보드 이벤트는 호환 되지 않으므로

    if( e != null && e.getID() == MouseEvent.MOUSE_MOVED ) {

      return;

    }

    if( ie != null ) {

      LinkedList selectedImageElements = HtmlDocument.selectedImageElements;

      for(int i = 0, len = selectedImageElements.size(); i < len; i ++ ) {
	 selectedImageElements.remove( i );
      }

      selectedImageElements.add( ie );

    }

    SEL_IMG_ELEM = ie;

  }

  /**
   * 이미지 엘리먼트와 토폴리지 스트럭쳐
   */
  private static class ImageElementAndTopology {

     public ImageElementAndTopology(ImageElement imageElement, int topology) {

	this.imageElement = imageElement;
	this.topology = topology;

     }

     ImageElement imageElement;
     int topology;

  }

  final private static LinkedList getImageElementsList(final HtmlDocument doc, final int refStyle) {

    final LinkedList list = new LinkedList();

    final ImageElement [] ies = doc.getImageElementsArray();

    for(int i = 0, len = ies.length; i < len; i ++ ) {

      final ImageElement ie = ies[i];

      if( ie.getStyle() == refStyle ) {

	list.addLast( ie );

      }

    }

    return list;

  }

  private static ImageElementAndTopology getImageElementOfMaxTopology(final HtmlDocument doc, final MouseEvent e) {

    int maxTop = OUT_OF_AREA;

    ImageElement target = doc;

    for(int k = 2; k > - 1; k -- ) { // 스타일의 인덱스가 높은 것 부터 검색한다. z - index가 높을 수록 스타일의 값도 높다.
      // 스타일은 반드시 0, 1, 2 의 값이어야 한다.

       final Iterator it = getImageElementsList( doc, k ).iterator();

       while( it.hasNext() ) {

	   final ImageElement ie = (ImageElement) it.next();

	   if( ie instanceof HtmlDocument ) {

	     // 도큐 먼트 일 겨우는 하위 도큐먼트와 이미지 엘리먼트 까지 재귀적으로 호출하여
	     // 최대 토폴로지를 가진 이미지 엘리먼트를 찾아낸다.

	     final HtmlDocument ieDoc = (HtmlDocument) ie;

	     final ImageElementAndTopology ieNmt = getImageElementOfMaxTopology( ieDoc, e ); // image element and max topology

	     final int top = ieNmt.topology;

	     if( ieNmt.imageElement instanceof ShapeElement && top != ImageElement.OUT_OF_AREA ) {

		return ieNmt;

	     }

	     if( top >= maxTop ) {

		 maxTop = top;

		 target = ieNmt.imageElement;

	     }

	  } else if( ie instanceof ShapeElement && getTopology( ie, e) != OUT_OF_AREA ) {
	    // 쉐잎 엘리먼트인 경우

	    return new ImageElementAndTopology( ie, getTopology( ie, e ) );

	  } else { // 순수한 이미지 엘리먼트 일 경우

	     int top =  getTopology( ie, e );

	     if( top >= maxTop ) {

	       maxTop = top;

	       target = ie;

	     }

	   }

	   if( maxTop == INSIDE_AREA ) {

	     return new ImageElementAndTopology( target, maxTop );

	   }

       }

    }

    if( maxTop != OUT_OF_AREA ) {

       return new ImageElementAndTopology( target, maxTop );

    }

    return new ImageElementAndTopology( doc, getTopology( doc, e ) );

  }

  final private static ImageElement getTargetImageElement(final HtmlDocument doc, final MouseEvent e) {

    final ImageElement SIE = ImageElement.SEL_IMG_ELEM;

    final int id = e.getID();

//    final boolean isAddWordBoxMode = isAddWordBoxMode();

    if( mode.isMode( Mode.ADD_SHAPE ) ) {

      return doc;

    } else if( ( mode.isMode( Mode.ADD_WORD_BOX ) || mode.isMode( Mode.ADD_TABLE) ) && SIE != null ) {

       // 선택된 이미지 엘리먼트의 도큐먼트
       // 이미지 엘리먼트가 HtmlDocument 타입 일 경우에는 자신이 되어지고,
       // 순수 이미지 엘리먼트이면 아버지 도큐먼트가 되어진다.

       final HtmlDocument sieDoc = (SIE instanceof HtmlDocument) ? ((HtmlDocument) SIE) : SIE.getParentDocument();

       // 마우스가 안 눌러진 마우스 이벤트 일 경우에는.....
       // 무전건 선택된 이미지 엘리먼트 도큐먼트를 반환한다.
       // 왜냐면, 눌러질 때 워드 박스 추가 모드에서
       // 선택된 이미지 엘리먼트를 반드시 도큐먼트 클래스로 설정하기 때문이다.

       if( id != e.MOUSE_PRESSED ) {

	  return sieDoc;

       }

       // 마우스가 눌러지면...

       final int sieDocTop = getTopology( sieDoc, e );

       if( sieDocTop != OUT_OF_AREA ) {

	  return sieDoc;

       }

       return getImageElementOfMaxTopology( doc, e ).imageElement;

    }

    if( id == e.MOUSE_DRAGGED ) {

       if( SIE != null ) {

	  return SIE;

       }

    } else if( id == e.MOUSE_RELEASED && mode.isMode( Mode.RESHAPE ) ) {

       if( SIE != null ) {

	  return SIE;

       }

    }

    // 워드 박스가 선택되었을 경우, 선택된 워드 박스에서 타겟 엘리먼트를 구한다.
    // 이미지 엘리먼트나, 내부 워드 박스가 선택 되어 질 수 도 있다.
    // 재귀적으로 엘리먼트들을 검색하여, 영역밖(OUT_OF_AREA)의 값이 아닌 엘리먼트가
    // 검색되면 그 엘리먼트가 타켓 엘리먼트가 되어진다.

    if( id != e.MOUSE_DRAGGED && SIE != null && SIE.getParentDocument() != null ) {

      if( SIE instanceof HtmlDocument ) {

	 final HtmlDocument sieDoc = (HtmlDocument) SIE;

	 final ImageElementAndTopology ieNmt=  getImageElementOfMaxTopology( sieDoc, e );

	 if( ieNmt.topology != OUT_OF_AREA ) {

	    return ieNmt.imageElement;

	 }

      }

//      else if( getTopology( SIE, e ) != OUT_OF_AREA ) {
//
//	  return SIE;
//
//      }

    }

    // 끝. 선택된 워드 박스에서 타겟 엘리먼트 구하기.

    final ImageElementAndTopology ieNmt = getImageElementOfMaxTopology( doc, e ); // image element that has max topology

    return ieNmt.imageElement;

  }

  // 요 함수는 스태틱하게 최상위 도큐먼트에서 한 번 만 콜한다.
  // 일반 워드 박스에서는 콜하지 않는다. 잘못하면 무한 루프에 빠질 수 있으므로....

  public boolean processMouseEvent(MouseEvent e) {

    int id = e.getID();

    final HtmlEditorPane editor = (HtmlEditorPane) e.getSource();

    final ImageElement target = getTargetImageElement( this, e );

    if( id == e.MOUSE_PRESSED && ( target instanceof ShapeElement ) ) {

      // 쉐잎 엘리먼트가 마우스를 눌렀을 때 선택되어지고,
      // 쉐잎 엘리먼트가 애로우 타입 일 때,
      // 쉐잎 엘리먼트 인덱스를 설정한다.

      final ShapeElement shapeElement = (ShapeElement) target;

      if( shapeElement.isArrowShapeElement() ) {

	  ShapeElement.MOUSE_PRESSED_POINT_INDEX = shapeElement.getPointIndex( e.getX(), e.getY() );

      } else {

	  ShapeElement.MOUSE_PRESSED_POINT_INDEX = - 1;

      }

    }

//    Utility.debug(this, "TARGET = " + target );

    if( id == e.MOUSE_PRESSED ) {

      // 이전 선택된 블록킹 영역 클리어 하는 문제....

      final ImageElement PSIE = ImageElement.SEL_IMG_ELEM; // 이전 선택된 이미지 엘리먼트....

      // 이전 작업한 엘리먼트의 도큐먼트
      final HtmlDocument psDoc = (PSIE instanceof HtmlDocument) ?
				   ( (HtmlDocument) PSIE )
				   : ( PSIE == null ? null : PSIE.getParentDocument() );

      // 현재 작업할 엘리먼트의 도큐먼트

      final HtmlDocument currDoc = (target instanceof HtmlDocument) ? (HtmlDocument) target : target.getParentDocument();

      if( psDoc != null && psDoc != currDoc ) {

	  // 이전 선택된 도큐먼트와 현재 선택할 도큐먼트가 다를 경우에만....
	  // 이전 블로킹을 깨끗하게(클리어하게) 한다.

	  psDoc.syncCaretStringElement( true ); // 이전에 선택된 블락킹을 없앤다.

      }

      final boolean isRightButton = Utility.isRightMouseButton( e );

      if( ! isRightButton ) {

	  // 왼쪽 마우스가 눌러지면 무조건 에디터의 텍스트 인풋을 초기화 한다.

	  editor.initTextInput();

	  // 현재 작업할 도큐먼트의 캐릿 엘리먼트를 동기화한다.

	  if( currDoc != null ) {

	    currDoc.syncCaretStringElement( true );

	  }

      }

      // 끝. 이전 블로킹 영역 깨끗하게 보이게 하기.

      // 마우스 이벤트를 처리할 목적 이미지 엘리먼트를 설정한다.
      // 마우스를 누를 때만......

      setSelectedImageElement( target, e );

      // 마우스를 누를 때만 작업 모드를 설정한다.
      // 그외는 작업을 한다. 모드에 따라서.......

      mode.setMode( getMode( target, e ) );

      // 끝. 작업 모드 설정

      // 마우스를 누를 때 모드 작업에 필요한 마우스 토폴로지를 설정한다.
      // 마우스를 누를 때만 새로 설정한다.

      MOUSE_TOPOLOGY = getTopology( target, e );

      // 끝. 마우스 토폴로지 설정

//      Utility.debug(this, "TARGET = " + target + ", MODE = " + mode.getMode() + ", MOUSE TOP = " + MOUSE_TOPOLOGY );

    }

    if( id != e.MOUSE_DRAGGED && id != e.MOUSE_RELEASED ) {

      setCursor( getTopology( target, e ), target, (HtmlEditorPane) e.getSource() );

    }

    // 마우스 드래깅시 끝단에서 스크롤링

    if( id == e.MOUSE_DRAGGED && mode.isMode( Mode.EDIT ) ) {

	final int x = e.getX();
	final int y = e.getY();

	final Rectangle visiRect = editor.getVisibleRect();

	final Dimension editorSize = editor.getSize();

	final int margin = visiRect.height/10;

	if(     ( y + margin > visiRect.y + visiRect.height )
	     && ( visiRect.y + visiRect.height < editorSize.height ) ) {

	  editor.scrollBy( 0, margin + 5 );

//	  return true; // 끝단에서 스크롤이 끝나면 마우스 이벤트의 처리는 정상적으로 일단 끝낸다.

	} else if(     ( y - margin < visiRect.y )
		    && ( visiRect.y > margin ) ) {

	  editor.scrollBy( 0, - (margin + 5 ) );

	}

    }

    if( target instanceof HtmlDocument ) { // 도큐먼트 엘리먼트 일 경우

       ((HtmlDocument) target).processDocumentMouseEvent( e );

    } else if( target != null ) { // 이미지 엘리먼트 일 경우....

       // 마우스를 눌렀을 때 설정된 마우스 토폴로지에 의해서 이미지 마우스 이벤트 처리를 한다.

       target.processImageMouseEvent( MOUSE_TOPOLOGY, e );

    }

    if( id == e.MOUSE_RELEASED ) {

      setCursor( getTopology( target, e ), target, (HtmlEditorPane) e.getSource() );

      // 마우스가 떼지면 다시 작업 모드를 설정한다.
      // 에디팅 모드로 전환할 경우가 대부분이다.

      mode.setMode( getMode( target, e ) );

      // 마우스가 떼지면 무조건 마우스 토폴로지를 초기값(영역바깥)으로 설정한다.
      MOUSE_TOPOLOGY = OUT_OF_AREA;

    }

    if( id == e.MOUSE_PRESSED ) {

      if( target instanceof HtmlDocument ) {

	this.showDocumentInfoVisually( (HtmlDocument) target );

      } else if( target instanceof ShapeElement ) {

	this.showDocumentInfoVisually( target.getParentDocument() );

      }

    }

    return true;

  }

  private boolean processDocumentMouseEvent(final MouseEvent e) {

    if( super.processImageMouseEvent( MOUSE_TOPOLOGY, e ) ) {

       return true;                     // 이미지 엘리먼트 이벤트 처리가 완료 되면
					// 도큐먼트 엘리먼트 이벤트 처리는 하지 않는다.

    }

					// 상위로 넘길 마우스 토폴로지는 마우스를 누를 때 설정해준...
					// 마우스 토폴로지 값이다.
					// 작업 수행시에(마으스를 드래깅 할 때....)
					// 에디터에서 필요한 마우스 토폴로지의 값이 변하지 않는다.
					// 무조건 이미지 엘리먼트 방식의 마우스 처리를 해준다.
					// 나머지 도큐먼트에 종속적인 이벤트 처리만 아래에서 해준다.
					// 예를 들면.... 문서의 선택 인덱스 설정이라든 지....
					// 워드 박스의 삽입이라든 지.....
					// 팝업 메뉴 감추기라 든 지.......

//    Utility.debug( this, "PROCESS DOCUMENT MOUSE EVENT ....." );

    final int id = e.getID();

    final int x = e.getX();
    final int y = e.getY();

    // 에디터 변수...... 마우스 이벤트의 소스 컴포넌트는 반드시 HtmlEditorPane 이어야 한다.
    final HtmlEditorPane editor = (HtmlEditorPane) e.getSource();

    // 오른 쪽 마우스 눌러 줬는 가 판별하는 변수
    final boolean isRightButton = Utility.isRightMouseButton( e );

    if( isAddWordBoxMode() && id == e.MOUSE_RELEASED ) {

       // 워드 박스 모드에서는 마우스가 릴리즈 되는 경우만 작업한다.

	final HtmlDocument wordBox = new HtmlDocument( this  );

	// 워드 박스 영역은 절대 좌표를 기준으로한 도형이 넘어온다.
	// 절대 기준의 드래깅 쉐잎으로 부터 워드 박스 영역을 구한다.

	final Rectangle2D wordBoxArea = this.getDraggedShape( MOUSE_TOPOLOGY, LAST_MOUSE_EVENT, e ).getBounds2D();

	// 추가 할 워드 박스의 크기를 목적 도큐먼트의 상대 위치로 잡아준다.

	/**@todo 워드 박스 삽입시 좌표 보정
	 *
	 */

	final Point2D absLoc = this.getAbsoluteLocation();

	wordBox.setArea(
			  wordBoxArea.getX() - absLoc.getX(), wordBoxArea.getY() - absLoc.getY(),
			  wordBoxArea.getWidth(),
			  wordBoxArea.getHeight()

			);

	this.addImageElement( wordBox );

//	Utility.debug( this, "MOUSE WORD BOX AREA = " + wordBoxArea );
//	Utility.debug( this, "ADDED WORD BOX AREA = " + wordBox.getArea() );

	mode.setMode( Mode.EDIT ); // 워드 박스 추가가 끝나면 에디터를 에디팅 모드로 전환 시킨다.

	return true; // 워드 박스를 추가 했으면 마우스 이벤트 처리를 그냥 끝낸다.

    } else if( mode.isMode( Mode.ADD_TABLE ) && id == e.MOUSE_RELEASED ) {

       // 워드 박스 영역은 절대 좌표를 기준으로한 도형이 넘어온다.
	// 절대 기준의 드래깅 쉐잎으로 부터 워드 박스 영역을 구한다.

	final Rectangle2D tableArea = this.getDraggedShape( MOUSE_TOPOLOGY, LAST_MOUSE_EVENT, e ).getBounds2D();

	// 추가 할 워드 박스의 크기를 목적 도큐먼트의 상대 위치로 잡아준다.

	final Point2D absLoc = this.getAbsoluteLocation();

	 // 테이블 추가 모드에서는 마우스가 릴리즈 되는 경우만 작업한다.

	tableArea.setRect(

			  tableArea.getX() - absLoc.getX(),
			  tableArea.getY() - absLoc.getY(),
			  tableArea.getWidth(),
			  tableArea.getHeight()

			);

	final int [] rowCol = this.getInitialRowCol( tableArea );

	final TableDocument table = new TableDocument( this, rowCol[0], rowCol[1], tableArea  );

	this.addImageElement( table );

	mode.setMode( Mode.EDIT ); // 워드 박스 추가가 끝나면 에디터를 에디팅 모드로 전환 시킨다.

	return true; // 워드 박스를 추가 했으면 마우스 이벤트 처리를 그냥 끝낸다.

    } else if( isAddWordBoxMode() || mode.isMode( Mode.ADD_TABLE ) ) {

	// 워드 박스 모드에서는 마우스가 릴리즈 되는 경우만 작업한다.
	// 그래서, 마우스가 눌러지는 경우가 아니면 그냥....그냥...아무 액션도 취하지 않는다....

	return true;

    } else if( id == e.MOUSE_PRESSED && isRightButton ) {

	  // 오른 쪽 마우스를 누르면 팝업 메뉴가 나타난다. 짱하고...멋있게...

	 editor.initTextInput();

	 editor.showEditPopupMenu( e );

	 return true;

    } else if( id == e.MOUSE_PRESSED ) {

	 // 왼쪽 마우스가 눌러지는 경우에는 텍스트 인풋을 초기화하고,
	 // 에디팅 팝업 메뉴가 나타나 있으면, 자동으로 숨긴다.

	 // 이미지 엘리먼트에서 해준다. 그러나 여기서 또 한 번 확실히.....

	 editor.initTextInput();

	 // 이미지 엘리먼트에서 해준다. 그러나 여기서 또 한 번 확실히.....
	 // 요 함수는 에디팅 팝업 메뉴가 나타나 있는 지를 감별한다음, 나타나 있을 경우에만
	 // 에디팅 팝업 메뉴를 감춘다. 시야에서...

	 editor.hideEditPopupMenu();

    }

    if( editor.isEditPopupMenuShowing() ) { // 에디팅 팝업 메뉴가 나타나 있을 겨우에는 아무 일도 하지 않는다.
					    // 그냥... 이벤트 처리를 종료한다.

      return true;

    }

    if( isRightButton ) {

      // Do nothing !

    } else if( mode.isMode( Mode.EDIT ) && id == e.MOUSE_PRESSED ) {

	this.setStartIndex( x, y, e );

	return true;

    } else if( mode.isMode( Mode.EDIT ) && ( id == e.MOUSE_DRAGGED ) ) {

	this.setEndIndex( x, y, e );

	Utility.debug(this, "SI = " + this.startIndex + ", EI = " + this.endIndex );

	editor.repaint(); // 블락킹의 실시간 렌더링을 위해서

	return true;

    }

    return false;

  }

  public final static int getTableCellTopology( final HtmlDocument cellDoc, final MouseEvent e ) {

      final Point2D absParLoc = cellDoc.getAbsoluteParentLocation();

      final double x = e.getX() - absParLoc.getX();
      final double y = e.getY() - absParLoc.getY();

      // 테이블 셀일 경우에는 OUT_OF_AREA 나
      // INSIDE_AREA 값의 위상만 가진다.

      final Rectangle2D area = cellDoc.getArea();

      final double margin = 3;

      final double ax = area.getX();
      final double ay = area.getY();
      final double aw = area.getWidth();
      final double ah = area.getHeight();

      final Rectangle2D cellArea = new Rectangle2D.Double( ax, ay, aw, ah + 3 );
      // 셀 에어리어는 하단 폭은 마진 만큼 더해서 잡는다.
      // 그래야만 TOP_BOUNDARY 토폴로지를 없앤다.

      if( ! cellArea.contains( x, y ) ) {
	// 마우스 좌표가 셀 에어리어 내부에 들어오지 않으면, 영역밖 토폴로지를 반환한다.

	return OUT_OF_AREA;

      }

      // 마우스 좌표가 영역안으로 들어왔음이 확인 되었으므로...
      // 아래 코드 부터는 마우스 좌표가 영역안에 들어와 있는 지는
      // 체크하지 않는다.

      if( ay + ah - margin <= y ) {

	return BOTTOM_BOUNDARY;

      } else if( x < ax + margin ) {

	return LEFT_BOUNDARY;

      } else if( ax + aw - margin <= x ) {

	return RIGHT_BOUNDARY;

      }

      return INSIDE_AREA;

  }

  public final static int getTopology(final ImageElement ie, final MouseEvent e ) {

      if( ie.isTableCell() ) {

	  return getTableCellTopology( ( (HtmlDocument) ie ), e );

      } else if( ie instanceof ShapeElement ) {

	  return getShapeElementTopology( (ShapeElement) ie, e );

      }

      final Point2D absParLoc = ie.getAbsoluteParentLocation();

      final double x = e.getX() - absParLoc.getX();
      final double y = e.getY() - absParLoc.getY();

      final Shape [] rects = ie.getCornerRects();

      for( int i = 0, len = rects.length; i < len; i ++ ) {

	  if( rects[i] != null && rects[i].contains( x, y ) ) {

	      return i;

	  }

      }

      final HtmlDocument doc = ( ie instanceof HtmlDocument ) ? ((HtmlDocument) ie) : null;

      if( doc != null && doc.isTopMostDocument() ) {

	  // 최상위 도큐먼트이면 무조건 마우스 토폴로지를 9 (내부에 있음을 의미)를 반환한다.

	  return INSIDE_AREA;

      } else if( doc != null && ie.getDocumentBoundary().contains( x, y ) ) { // 바운더리에 있으면....

	  return ON_THE_BOUNDARY;

      } else if( ie.getArea().contains( x, y) ) {

	  return INSIDE_AREA;

      }

      return OUT_OF_AREA;

  }

  public final static int getShapeElementTopology(final ShapeElement shapeElement, final MouseEvent e ) {

      final Point2D absParLoc = shapeElement.getAbsoluteParentLocation();

      final double x = e.getX() - absParLoc.getX();
      final double y = e.getY() - absParLoc.getY();

      final Shape [] rects = shapeElement.getCornerRects();

      for( int i = 0, len = rects.length; i < len; i ++ ) {

	  if( rects[i] != null && rects[i].contains( x, y ) ) {

	      return i;

	  }

      }

      final Shape area = ( shapeElement.isArrowShapeElement() ) ?
			   shapeElement.getBoundaryShape() : shapeElement.getArea() ;

      if( area.contains( x, y) ) {

	  return INSIDE_AREA;

      }

      return OUT_OF_AREA;

  }

//  public final static int getShapeElementTopology(final ShapeElement shapeElement, final MouseEvent e ) {
//
//      final Point2D absParLoc = shapeElement.getAbsoluteParentLocation();
//
//      final double x = e.getX() - absParLoc.getX();
//      final double y = e.getY() - absParLoc.getY();
//
//      final boolean selected = shapeElement.isSelected();
//
//      if( selected ) {
//
//	final Shape [] rects = shapeElement.getCornerRects();
//
//	for( int i = 0, len = rects.length; i < len; i ++ ) {
//
//	    if( rects[i] != null && rects[i].contains( x, y ) ) {
//
//		return i;
//
//	    }
//
//	}
//
//      }
//
//      final Shape area = ( selected && ( ! shapeElement.isArrowShapeElement() ) ) ?
//			  shapeElement.getArea() : shapeElement.getBoundaryShape() ;
//
//      if( area.contains( x, y) ) {
//
//	  return INSIDE_AREA;
//
//      }
//
//      return OUT_OF_AREA;
//
//  }

  protected static final int getMouseTopology() {

      return MOUSE_TOPOLOGY;

  }

  protected final static int getMode(final ImageElement target, final MouseEvent e ) {

	final int top = getTopology( target, e ) ;

	final int id = e.getID();

	if( mode.isMode( Mode.ADD_SHAPE ) ) {

	      return mode.getMode();

	} else if( isAddWordBoxMode() || mode.isMode( Mode.ADD_TABLE) ) { // 워드 박스나 테이블 추가 모드 일 경우는 무조건 현재 모드(워드 박스 모드)를 반환한다.

	      return mode.getMode(); // 현재 모드 반환

	} else if( ( target instanceof HtmlDocument ) && id == e.MOUSE_RELEASED && top == INSIDE_AREA ) {

	    // 워드 박스 내부 경계에서 마우스가 떼어 지는 경우는 에디팅 모드가 설정된다.

	    return Mode.EDIT;

	} else if( id == e.MOUSE_PRESSED || id == e.MOUSE_RELEASED ) { // 마우스가 눌러지거나 떼어지는 경우에만 다양한 모드를 반환한다.

	    if( target instanceof HtmlDocument && top == INSIDE_AREA ) {

		return Mode.EDIT;

	    } else if( top > -1 && top <= INSIDE_AREA ) {

		return Mode.RESHAPE;

	    }

	    return Mode.EDIT;

	} else { // 마우스가 클릭되는 이 외의 경우는 무조건 현재 모드를 반환한다.

	    return mode.getMode() ; // 현재 모드를 리턴한다.

	}

  }

  public static void setCursor(final  int top, final ImageElement target, final HtmlEditorPane editor ) {

       int ct = Cursor.TEXT_CURSOR; // cursor type

       switch( top ) {

	  case 0:
		 ct = Cursor.NW_RESIZE_CURSOR;
		 break;
	  case 1:
		 ct = Cursor.N_RESIZE_CURSOR;
		 break;
	  case 2:
		 ct = Cursor.NE_RESIZE_CURSOR;
		 break;


	  case 3:
		 ct = Cursor.W_RESIZE_CURSOR;
		 break;

	  case 4:
		 ct = Cursor.E_RESIZE_CURSOR;
		 break;


	  case 5:
		 ct = Cursor.SW_RESIZE_CURSOR;
		 break;

	  case 6:
		 ct = Cursor.S_RESIZE_CURSOR;
		 break;

	  case 7:
		 ct = Cursor.SE_RESIZE_CURSOR;
		 break;


	  case ON_THE_BOUNDARY: // 바운더리에 있으면....

		 ct = Cursor.MOVE_CURSOR;

		 break;

	  case INSIDE_AREA : // 내부에 있으면.....

		 ct = ( target instanceof HtmlDocument) ? Cursor.TEXT_CURSOR : ( target.isSelected() ? Cursor.MOVE_CURSOR : Cursor.HAND_CURSOR ) ;

		 break;

	  case BOTTOM_BOUNDARY :

		 ct = Cursor.S_RESIZE_CURSOR;
		 break;

	  case LEFT_BOUNDARY :

		 ct = Cursor.W_RESIZE_CURSOR;
		 break;

	  case RIGHT_BOUNDARY :

		 ct = Cursor.E_RESIZE_CURSOR;
		 break;

       }

       if( mode.isMode( Mode.ADD_SHAPE ) ) {

	    ct = Cursor.CROSSHAIR_CURSOR;

       } else if( mode.isFileOpenning() ) {

	    ct = Cursor.WAIT_CURSOR;

       } else if( target.isTableCell()
		 || ( ImageElement.SEL_IMG_ELEM != null
		      && ImageElement.SEL_IMG_ELEM.getParentDocument() == target
		     )
		) {

	    // Do nothing!

       } else if( ( -1 < top && top < INSIDE_AREA ) && ( ! target.isSelected() ) ) {

	    // 선택 되지 않은 상태에서는 손 커서를 설정한다.

	    ct = Cursor.HAND_CURSOR;

       } else if( isAddWordBoxMode() ) {

	    ct = Cursor.CROSSHAIR_CURSOR;

       } else if( mode.isMode( Mode.ADD_TABLE ) ) {

	    ct = Cursor.CROSSHAIR_CURSOR;

       }

       editor.setCursor( Cursor.getPredefinedCursor(ct) );

  }

  public boolean processKeyEvent(final KeyEvent e) {

    final ImageElement sie = ImageElement.SEL_IMG_ELEM; // selected image element

    if( sie instanceof HtmlDocument ) {

      return ((HtmlDocument) sie).processDocumentKeyEvent( e );

    } else if( ( ! ( sie instanceof HtmlDocument) ) && ( e.isControlDown() ) ) {
      // 선택된 객체가 순수 이미지 엘리먼트이고,
      // 컨트롤 키가 눌러지면...... 아버지 도큐먼트에서 키 이벤트를 처리하여
      // 카피 페이스트 작업을 한다.

      final HtmlDocument pdoc = sie.getParentDocument();

      pdoc.processDocumentKeyEvent( e );

      return true;

    } else {

      return sie.processImageKeyEvent( e );

    }

  }

  private boolean processDocumentKeyEvent(final KeyEvent e) {

      if( super.processImageKeyEvent( e ) ) {

	return true;

      }

      final HtmlEditorPane editor = (HtmlEditorPane) e.getSource();

      final int key = e.getKeyCode();

      if( ! ( key == e.VK_UP || key == e.VK_DOWN || key == e.VK_PAGE_DOWN || key == e.VK_PAGE_UP ) ) {

	    // 방향키가 눌러지지 않으면 ......
	    // 방향키 히스토리 정보를 초기화 한다.
	    this.rowCaretX = -1;

      }

//      Utility.debug(this, "KEY = " + e.getKeyChar() );

      final boolean ctrlDown = e.isControlDown();
      final boolean shiftDown = e.isShiftDown();

      final boolean isEmptyText = editor.getPreText().equals( "" );

      if( key == e.VK_ENTER || key == e.VK_SPACE ) { // 엔터 키나 스페이스 키

	 editor.initTextInput();

	 this.syncCaretStringElement( true );

	 final String text = "" + ((char) key);

	 this.processText( text );

	 this.syncCaretStringElement( true );

      } else if( key == e.VK_TAB && AppRegistry.TAB_KEY_PROCESSING ) {

	// 탭 키가 들어 올 때

	 // 탭 문자 처리 여부.....

	 editor.initTextInput();

	 this.syncCaretStringElement( true );

	 final String text = "" + ((char) key);

	 this.processText( text );

	 this.syncCaretStringElement( true );

      } else if( key == e.VK_LEFT ) {

	 // 왼 쪽 화살표 키가 들어 올 때......

	  editor.initTextInput();

	  this.synchIndex();

	  this.moveCaretBackward();

      } else if( key == e.VK_RIGHT ) {

	  // 오른 쪽 화살표 키가 들어 올 때......

	  editor.initTextInput();

	  this.synchIndex();

	  this.moveCaretForward();

      } else if( key == e.VK_UP ) {

	  // 윗 쪽 화살표 키가 들어 올 때......

	  editor.initTextInput();

	  this.synchIndex();

	  this.goUp( e );

      } else if( key == e.VK_DOWN ) {

	  // 아랫 쪽 화살표 키가 들어 올 때.......

	  editor.initTextInput();

	  this.synchIndex();

	  this.goDown( e );

      } else if( key == e.VK_PAGE_UP ) {

	  // 페이지 업 키가 들어 올 때.......

	  editor.initTextInput();

	  this.synchIndex();

	  this.goPageUp( e );

      }  else if( key == e.VK_PAGE_DOWN ) {

	  // 페이지 다운 키가 들어 올 때......

	  editor.initTextInput();

	  this.synchIndex();

	  this.goPageDown( e );

      } else if( (key == 'A' || key == 'a' ) && ctrlDown ) {  // select all processing

	  // Ctrl-A 를 누를 때.......

	  this.selectAll();

      } else if( ctrlDown && ( key == 'C' || key == 'c' ) ) {

	  // Ctrl-C 를 누를 때.......

	  editor.initTextInput();

	  editor.showEditPopupMenu( e );

//	  copyToBuffer();

      } else if( ctrlDown && ( key == 'X' || key == 'x' ) ) {

	  // Ctrl-X 를 누를 때.......

	  editor.initTextInput();

	  editor.showEditPopupMenu( e );

//	  cutToBuffer();

      } else if( ctrlDown && ( key == 'V' || key == 'v' ) ) {

	  // Ctrl-V 를 누를 때......

	  editor.initTextInput();

	  editor.showEditPopupMenu( e );

//	  pasteFromBuffer();

      } else if( key == e.VK_HOME || key == e.VK_END ) {

	  // 홈 키나 엔드 키를 누를 때.......

	  editor.initTextInput();

	  this.syncCaretStringElement( true );

	  if( key == e.VK_HOME ) {

	    this.goToHome( e );

	  } else {

	    this.goToEnd( e );

	  }

      } else if( key == e.VK_BACK_SPACE ) {

	// 백 스페이스 키를 누를 때.......

	if( isEmptyText ) {

	  this.deletePrevious();

	  this.requestNewHtmlDocView();

	}

      } else if( key == e.VK_DELETE ) {

	// 딜릿트 키를 누를 때.......

	if( ! isEmptyText ) {

	  editor.initTextInput();

	  this.syncCaretStringElement( true );

	}

	this.deleteNext();

	this.requestNewHtmlDocView();

      }

      if(    key == e.VK_UP || key == e.VK_DOWN || key == e.VK_LEFT || key == e.VK_RIGHT
	  || key == e.VK_PAGE_DOWN || key == e.VK_PAGE_UP )
      {

	    // 방향 키와 페이지 업 다운 키가 눌려 진후,
	    // 현재 폰트 정보를 UI 상에 표시한다.

	    this.showDocumentInfoVisually( this );

      }

      return true;

  }

  private void goToHome( KeyEvent e ) {

//      Utility.debug( this, "GO HOME!!!" );

      this.goTo( 0, this.getStartIndex(), e, false );

  }

  private void goToEnd( KeyEvent e ) {

//      Utility.debug( this, "GO END!!!" );

      this.goTo( 0, this.getStartIndex(), e, false );

  }

  private void goDown( KeyEvent e ) {

      this.goTo( 1, this.getStartIndex(), e, false );

  }

  private void goUp( KeyEvent e ) {

      this.goTo( -1, this.getStartIndex(), e, false );

  }

  private void goPageUp( KeyEvent e ) {

      this.goTo( - 1 , this.getStartIndex(), e, true );

  }

  private void goPageDown( KeyEvent e ) {

      this.goTo(   1 , this.getStartIndex(), e, true );

  }

  private void goTo( final int lineNumToSkip, final int currIdx, final KeyEvent e, boolean pgUpDown ) {

//      Utility.debug( this, "CURR INDEX = " + currIdx );

      final StringElement ce = this.caretElement;

      if( ce == null ) {

	return;

      }

      // row view list
      final Vector rowViewList = this.getHtmlDocView().getRowViewList();

      final Enumeration rowViewEnum = rowViewList.elements();

      RowView currRowView = null;

      Utility.debug(this, "SEARCHING TARGET ROW VIEW ......" );

      while( currRowView == null && rowViewEnum.hasMoreElements() ) {

	 final RowView rowView = (RowView) rowViewEnum.nextElement();

	 // string view list
	 final Enumeration svList = rowView.elements();

	 while( currRowView == null && svList.hasMoreElements() ) {

	     final Object obj = svList.nextElement();

	     if( obj instanceof EmptyStringView ) {

		 continue;

	     }

	     final StringView sv = (StringView) obj;

	     final StringElement se = sv.getStringElement();

	     final int svRefIdx = sv.getRefIndex();
	     final int svEndIdx = sv.getEndIndex();

	     if( svRefIdx <= currIdx && currIdx <= svEndIdx ) {

		currRowView = rowView;

		break;

	     }

//	     if( true ) { //se == ce ) {
//
//		 final int svRefIdx = sv.getRefIndex();
//		 final int svEndIdx = sv.getEndIndex();
//
//		 if( svRefIdx <= currIdx && currIdx <= svEndIdx ) {
//
//		    currRowView = rowView;
//
//		    break;
//
//		 }
//
//	     }

	 }

      }

      Utility.debug(this, "SEARCHED ROW VIEW = " + currRowView );

      if( currRowView == null ) {

	return;

      }

      int targetIdx = rowViewList.indexOf( currRowView ) + lineNumToSkip;

      targetIdx = targetIdx < 0 ? 0 : targetIdx;

      targetIdx = targetIdx > rowViewList.size() -1 ? rowViewList.size() - 1 : targetIdx;

      final RowView targetRowView = (RowView) ( rowViewList.get( targetIdx ) ) ;

      if( lineNumToSkip == 0 ) {

	  this.setHomeOrEndIndex( targetRowView, e );

      } else if( pgUpDown ) {

	  this.setPageUpDownIndex( targetRowView, currRowView , e , ( lineNumToSkip < 0 ));

      } else {

	  this.setKeyUpDownIndex( targetRowView, currRowView, e );

      }

  }

  private void setHomeOrEndIndex(final RowView rowView, final KeyEvent e ) {

      final boolean goToHome = ( e.getKeyCode() == e.VK_HOME );

      final Vector svList = rowView.getHtmlViews();

      final StringView sv = ( goToHome ) ? (StringView) svList.firstElement() : (StringView) svList.lastElement();

      final int currIdx = ( goToHome ) ? sv.getStartIndex() : sv.getEndIndex() ;

//      Utility.debug( this, "GO HOME = " + goToHome + ", PRE INDEX = " + this.startIndex + ", NEW INDEX = " + currIdx );

      final StringElement se = sv.getStringElement();

      this.setCaretElement( se );

      se.synchIndex( currIdx - se.getFirstIndex(), true );

  }

  private int getRowCaretIndex(final RowView rowView, final int currIdx) {

      final Vector svList  =  rowView.getHtmlViews();

      final StringView  first  = (StringView) svList.firstElement();
      final StringView  last   = (StringView) svList.lastElement();

      final int si = first.getRefIndex() ; // start index and end index

      // 첫 번 째 값은 현재행의 첫 번 째 인덱스, 두 번째 값은 현재 행의 길이, 세 번 째는 줄에 대한 캐릿 인덱스이다.

      return ( currIdx - si ) ;

  }

  private void setPageUpDownIndex(final RowView targetRowView, final RowView currRowView,
				  final KeyEvent e, boolean pgUp ) {

      double rowCaretX = this.rowCaretX;

      if( rowCaretX < 0 ) {

	  final Rectangle2D cursorRect = this.cursorRect;

	  rowCaretX = cursorRect.getX();

	  this.rowCaretX = rowCaretX;

      }

      Rectangle2D targetArea = targetRowView.getArea();

      final HtmlDocView docView = this.getHtmlDocView();

      final HtmlEditorPane editor = (HtmlEditorPane) e.getSource();

      final Rectangle viewRect = editor.getVisibleRect();

      final double pageHeight = 0.33 * viewRect.height * ( pgUp ? - 1.0 : 1.0 );

      final double y = targetArea.getY() + targetArea.getHeight()/2.0 + pageHeight ;

      final StringElementAndMouseIndex seNmi = docView.getIndex( (int) rowCaretX, (int) y, null );

      final StringElement se = seNmi.stringElement;

      this.setCaretElement( se );

      final int index = seNmi.index;

      final int seFirstIndex = se.getFirstIndex();

      se.synchIndex( index - seFirstIndex, true );

  }

  private void setKeyUpDownIndex(final RowView targetRowView, final RowView currRowView,
				 final KeyEvent e ) {

      double rowCaretX = this.rowCaretX;

      if( rowCaretX < 0 ) {

	  final Rectangle2D cursorRect = this.cursorRect;

	  rowCaretX = cursorRect.getX();

	  this.rowCaretX = rowCaretX;

      }

      Rectangle2D targetArea = targetRowView.getArea();

      final HtmlDocView docView = this.getHtmlDocView();

      final double y = targetArea.getY() + targetArea.getHeight()/2.0;

      final StringElementAndMouseIndex seNmi = docView.getIndex( (int) rowCaretX, (int) y, null );

      final StringElement se = seNmi.stringElement;

      this.setCaretElement( se );

      final int index = seNmi.index;

      final int seFirstIndex = se.getFirstIndex();

      se.synchIndex( index - seFirstIndex, true );

  }

  @Override
public String tag(final int zindex) {

      final boolean isTableCellDoc = this.isTableCell();

      final Rectangle2D area = this.getArea();

      final int x = isTableCellDoc ? (int) area.getX() - 1 : (int) area.getX();

      final int y = isTableCellDoc ? (int) area.getY() - 1 : (int) area.getY();

      int width = (int) area.getWidth(), height = (int) area.getHeight();

      if( isTableCellDoc ) {

	width += 1;
	height += 1;

      }

      if( this.isTopMostDocument() ) {

	  final HtmlEditorPane editor = this.getDocumentEditor();

	  final Dimension editorSize = editor.getSize();

	  width = editorSize.width;
	  height = editorSize.height;

      }

      final int borderWidth = this.getBorderWidth();

      final String borderStyle = "border-style: solid; border-width:" + borderWidth + "px; border-color: " + StringView.toHtmlColor( this.borderColor ) + "; ";

      String tagText = "<div style=\"position:absolute; "
			+ "left:" + x + "px; top:" + y + "px; "
			+ "width:" + width + "px; height:" + height + "px; "
			+ "z-index:" + zindex
			+ "; background-color: " + StringView.toHtmlColor( this.fillColor ) + ";"
			+ this.getAdditionalDivTag()
			+ " " + borderStyle
			+ "\">" + nl;

      final HtmlDocView docView = this.getHtmlDocView();

      tagText += docView.tag( zindex );

      tagText += nl + "</div>" + nl;

      tagText = HtmlElement.comment( tagText, this );

      return tagText;

  }

  public String getAdditionalDivTag() {

      return "";

  }

  private void checkStringElementsIndexValidity() {

     final Iterator sesList = this.getStringElements().iterator();

     while( sesList.hasNext() ) {

	  final StringElement se = (StringElement) sesList.next();

	  se.synchIndex( -1, true );

     }

  }

  private void setStartIndex( final int x, final int y, final MouseEvent e ) {

    this.checkStringElementsIndexValidity();

    final StringElementAndMouseIndex seNmi = this.getIndex( x, y, e ); // string element and mouse index

    final int index = seNmi.index;

    final StringElement ce = seNmi.stringElement;

    this.setCaretElement( ce );

    final int caretIdx = index - ce.getFirstIndex();

    ce.synchIndex( caretIdx, true );

    Utility.debug(this, "SI = " + index );

    this.refIndex = seNmi.index;

    // 도큐먼트의 시작 인덱스 설정시에는 끝 인덱스도 동기화 시킨다.

    this.setStartIndex( index );
    this.setEndIndex( index );

  }

  private void setEndIndex(final int x, final int y, final MouseEvent e) {

       final int refIndex = this.refIndex;

       final StringElementAndMouseIndex seNmi = this.getIndex( x, y, e ); // string element and mouse index

       // 도큐먼트의 끝 인덱스 설정 시에는 기준 인덱스와 비교하여....
       // 작은 값을 시작 인덱스로..... 큰 값을 끝 인덱스로 설정한다.
       // 기준 인덱스는 시작 인덱스 설정시에 시작 인덱스와 같은 값으로 잡힌다.
       // 이렇게 함으로서 마우스를 역방향으로 끌 때도 인덱스의 시작 값과 끝 값의
       // 논리 무결성이 확보된다.

       final int startIndex  =  ( seNmi.index < refIndex ) ? seNmi.index : refIndex ;
       final int endIndex    =  ( seNmi.index > refIndex ) ? seNmi.index : refIndex ;

       final StringElement ce = seNmi.stringElement;

       this.setCaretElement( ce );

       int caretIdx = endIndex - ce.getFirstIndex();

       ce.synchIndex( caretIdx, false );

       final int docEndIndex = this.getDocLength();

       Utility.debug( this, "DOC END INDEX = " + docEndIndex );

       this.setStartIndex( startIndex );

       if( startIndex == endIndex -1 && startIndex == docEndIndex ) {

	  caretIdx = caretIdx -1 ;

	  caretIdx = caretIdx < 0 ? 0 : caretIdx;

	  ce.synchIndex( caretIdx, false );

	  this.setEndIndex( startIndex );

       } else {

	  this.setEndIndex( endIndex );

       }

  }

  final private int getDocLength() {

    final StringElement seLast = (StringElement) this.getStringElements().getLast();

    return seLast.getLastIndex();

  }

  final private StringElementAndMouseIndex getIndex( int x, int y, final MouseEvent e ) {

    final Point2D absLoc = this.getAbsoluteLocation(); // absolute location

    // 도큐먼트의 위차 값 만큼 x, y 값을 보정한다.
    x -= (int) ( absLoc.getX() );
    y -= (int) ( absLoc.getY() );

    final HtmlDocView docView = this.getHtmlDocView();

    return docView.getIndex( x, y, e );

  }

  final public boolean contains(final ImageElement ie) {

    return this.imageElements.contains( ie );

  }

  /**
   * 맨 앞으로 가져오기, 맨 뒤로 보내기,
   * 앞으로 가져오기, 뒤로 보내기 함수이다.
   */
   // i = Integer.MIN_VALUE : 맨뒤로 보내기
   // i = Integer.MAX_VALUE : 맨 앞으로 가져오기
   // i = -1 : 뒤로 보내기
   // i = 1 : 앞으로 보내기

  final public void setZindex(final ImageElement target, final int index ) {

    final LinkedList imageElements = this.imageElements;

    if( index == Integer.MAX_VALUE ) {

       imageElements.remove( target );

       imageElements.addLast( target );

    } else if( index == Integer.MIN_VALUE ) {

       imageElements.remove( target );

       imageElements.addFirst( target );

    }

    final int refIndex = imageElements.indexOf( target );

    final int refStyle = target.getStyle();

    final int size = imageElements.size();

    if( index > 0 ) { // 앞으로 보내기

	if( refIndex == size -1 ) {

	  // Do nothing ! 인덱스를 손 댈 필요가 없으므로, 왜냐면 맨 앞에 가 있으므로....

	  return;

	}

	imageElements.remove( target );

	// 루프를 마직막 엘리먼트를 제외한 곳 까지 아이터레이션 한다.
	// 즉 최종 아이터레이션인 size-1 이다.
	// 마지막에 넣어야 할 경우에 좀 루틴이 복잡해서....
	// 아이터레이션을 size -1 까지 한 다음에....
	// 중간에 만족하는 인덱스를 만나면.....
	// 인덱스 설정을 한 다음 함수를 종료한다.

	for(int i = refIndex; i < size - 1 ; i ++ ) {

	   final ImageElement ie = (ImageElement) imageElements.get( i );

	   if( refStyle == ie.getStyle() ) {

	       imageElements.add( i + 1, target );

	       return; // 만족한 인덱스에 설정했으므로, 작업 수행을 종료한다.

	   }

	}

	// 중간에 만족하는 인덱스를 만나지 못햇으면...
	// 무조건 마지막 인덱스에 집어 넣는다.

	imageElements.addLast( target );

    } else {

	if( refIndex == 0 ) {

	  // Do nothing ! 인덱스를 손 댈 필요가 없으므로, 왜냐면 맨 뒤에 있으므로.....

	  return;

	}

	imageElements.remove( target );

	// 아이테레이션을 1 까지만 수행한다.
	// 중간에 만족하는 인덱스를 만나면....
	// 인덱스 설정을 한다음 함수를 종료한다.

	for(int i = refIndex -1 ; i > 0 ; i -- ) {

	   final ImageElement ie = (ImageElement) imageElements.get( i );

	   if( refStyle == ie.getStyle() ) {

	       imageElements.add( i , target );

	       return; // 만족하는 인덱스에 설정을 했으므로, 함수를 종료한다.

	   }

	}

	// 그렇지 못하면....
	// 무조건 맨 처음에 넣어준다.

	imageElements.addFirst( target );

    }

  }

  final public HtmlDocView getHtmlDocView() {

    if( this.docView == null ) {

      this.createView();

    }

    return this.docView;

  }

  // 도큐먼트의 영역은 뷰의 영역과 동기화한다.
  // 그래야만..... 자동 개행이 되어 진다.

  @Override
final public Rectangle2D getArea() {

    return this.getHtmlDocView().getArea();

  }

  public int getMinHeight() {

    return (int) ( AppRegistry.MINIMUN_DOCUMENT_HEIGHT ) ;

  }

  public int getMinWidth() {

    Insets scanMargin = this.getScanMargin();

    int textMin = this.getStringElementsMinWidth() + scanMargin.left + scanMargin.right + 1;

    // 1 만큼 여유 있게 보정

    int imgMin = this.getMinImageElementsWidth();

    return ( textMin > imgMin ) ? textMin : imgMin;

  }

  final private int getMinImageElementsWidth() {

    double min = 0;

    final ImageElement ies [] = this.getImageElementsArray();

    for(int i = 0, len = ies.length ; i < len; i ++ ) {

      final ImageElement ie = ies[i];

      final Rectangle2D ieArea = ie.getArea();

      final double ieRight = ieArea.getX() + ieArea.getWidth();

      min = ( ieRight > min ) ? ieRight : min ;

    }

    final Insets insets = this.getScanMargin();

    return (int) ( min + insets.right ) ;

  }

  final private int getStringElementsMinWidth() {

    int min = AppRegistry.MIN_GLYPH_WIDTH;

    final Iterator seList = this.getStringElements().iterator(); // string elements list

    while( seList.hasNext() ) {

	final StringElement se = (StringElement) seList.next();

	final int sew = se.getMinWidth();

	min = ( sew > min ) ? sew : min;

    }

    return min;

  }

  // 사용자가 마우스로 끌어서 설정한 크기이다.
  // 최소 크기로 설정된다.

  final public Rectangle2D getUserSpecifiedArea() {

    return super.getArea(); //

  }

  final public Rectangle2D getTopMostDocumentArea() {

    final HtmlEditorPane editor = this.getDocumentEditor();

    return editor.getBounds();

  }

  final public static Vector getCopiedHtmlElements() {

    return copiedHtmlElements;

  }

  final void getOnlyTexts( final StringBuffer buffer) {

    Iterator it = this.stringElements.iterator();

    // 스트링 엘리먼트 텍스트 합치기

    while( it.hasNext() ) {

      final StringElement se = (StringElement) it.next();

      buffer.append( se.getText() + " " );

    }

    // 끝. 스트링 엘리먼트 텍스트 합치기

    // 워드 박스 텍스트 합치기

    it = this.imageElements.iterator();

    while( it.hasNext() ) {

	 final Object obj = it.next();

	 if( obj instanceof HtmlDocument ) {

	     ((HtmlDocument) obj).getOnlyTexts( buffer );

	 }

    }

    // 끝. 워드 박스 텍스트 합치기

  }

  final public void addToCopiedHtmlElements(final StringElement stringElement) {

     this.copiedHtmlElements.addElement( stringElement );

  }

  final public LinkedList getStringElements() {

     return this.stringElements;

  }

  final public Rectangle2D getRectangleIntersects(final Rectangle2D glyph ) {

     final Iterator it = this.imageElements.iterator();  // iterator

     while( it.hasNext() ) {

	  final ImageElement ie = (ImageElement) it.next();

	  if( ! ie.isRectStyle() ) {
	      continue;
	  }

	  final Rectangle2D area = ie.getArea();

	  if( area.intersects( glyph ) || glyph.intersects( area ) || area.contains( glyph ) || glyph.contains( area) ) {

	     return area;

	  }

     }

     return null;

  }

  final private void applyFontToCurrentWord(final Font font) {

      final StringElement ce = this.caretElement;

      final String text = ce.getText();

      final int ci = ce.getCaretIndex(); // caret index

      final int [] wordIndex = this.getWordIndex( text, ci );

      Utility.debug(this, "WORD INDEX = " + wordIndex );

      final int from = ( wordIndex != null ? wordIndex[ 0 ] : ci );
      final int to = ( wordIndex != null ? wordIndex[ 1 ] : ci ) ;

      Utility.debug(this, "FROM WI = " + from + ", TO WI = " + to );

      String first = null, second = null, third = null;

      final char fromKey = ( wordIndex != null ? (char) wordIndex[2] : ' ' );
      final char toKey = ( wordIndex != null ? (char) wordIndex[3] : ' ' );

      if( from < 0 && to < 0 ) {

	  ce.setFont( font );

	  return;

      } else if( from < 0 ) {

	  second = text.substring( 0, to );
	  third = text.substring( to );

      } else if( to < 0 ) {

	  first = text.substring( 0, from ) + fromKey;
	  second = text.substring( from + 1 );

      } else if( wordIndex != null ){

	  first = text.substring(0, from ) + fromKey;
	  second = text.substring( from + 1, to );
	  third = text.substring( to );

      } else {

	  first = text.substring(0, ci );
	  second = "" + text.charAt( ci );

	  if( ci < text.length() - 1 ) {

	     third = text.substring( ci + 1 );

	  }

      }

      final LinkedList ses = this.stringElements;

      final Color color = ce.getColor();
      final boolean underLine = ce.getUnderLine();
      final String href = ce.getHref();

      if( first != null ) {

	  final int index = ses.indexOf( ce );

	  final StringElement se = new StringElement( this, first, ce.getFont(), color, underLine, href );

	  if( index < 0 ) {

	    ses.add( se );

	  } else {

	    ses.add( index, se );

	  }

      }

      StringElement nce = null; // new caret string element

      if( second != null ) {

	  final int index = ses.indexOf( ce );

	  final StringElement se = new StringElement( this, second, font, color, underLine, href );

	  if( index < 0 ) {
	    ses.add( se );
	  } else {
	    ses.add( index, se );
	  }

	  nce = se;

      }

      if( third != null ) {

	  final int index = ses.indexOf( ce );

	  final StringElement se = new StringElement( this, third, ce.getFont(), color, underLine, href );

	  Utility.debug( this, "INDEX = " + index );

	  ses.add( index, se );

      }

      ses.remove( ce );

      this.setCaretElement( nce );

      final int index = ci - ( first == null ? 0 : first.length() );

      nce.synchIndex( index, true );

  }

  final private int [] getWordIndex(final String text, final int caretIndex) {

      final char caretChar = text.charAt( caretIndex );

//      final int textLen = text.length();
//
//      if( caretChar == '\n' ) {
//
//	if( caretIndex < textLen - 1 ) {
//
//	  final char nextCaretChar = text.charAt( caretIndex + 1 );
//
//
//	}
//
//      }

      if( caretChar == ' ' || caretChar == '\n' ) {

	  return null;

      }

      final String pre = text.substring( 0, caretIndex );
      final String post = text.substring( caretIndex );

      final int fromSpc = pre.lastIndexOf( " ");
      final int toSpc = post.indexOf( " " );

      final int fromEnter = pre.lastIndexOf( "\n" );
      final int toEnter = post.indexOf( "\n" );

      final int from = ( fromSpc > fromEnter ) ? fromSpc : fromEnter;
      int to = ( toSpc < toEnter ) ? toSpc : toEnter;

      final char fromKey = ( fromSpc > fromEnter ) ? ' ' : '\n';
      char toKey;

      if( to < 0 && toEnter > -1 ) {

	 to = toEnter;

	 toKey = '\n';

      } else {

	 toKey = ' ';

      }

      to = ( to < 0 ) ? to : pre.length() + to;

      return new int [] { from, to, fromKey, toKey } ;

  }

  final public void applyFont(final String family, final int type, final int size ) {

     final Object [] ses = this.stringElements.toArray();

     final int sesSize = ses.length;

     if( sesSize == 1 ) {

	final StringElement se = (StringElement) ses[ 0 ];

	final int seTextLen = se.getText().length();

	Utility.debug( this, "ONE SES TEXT LEN = " + seTextLen );

	if( seTextLen < 2 ) {

	  return;

	}

     }

     this.getDocumentEditor().initTextInput();

     final int startIndex = this.startIndex;
     final int endIndex = this.endIndex;

     this.syncCaretStringElement( true );

     this.setStartIndex( startIndex );
     this.setEndIndex( endIndex );

     final Font font = FontManager.getFont( family, type, size );

     if( startIndex == endIndex ) {

	this.applyFontToCurrentWord( font );

	this.requestNewHtmlDocView();

	return;

     }

     for(int i = 0; i < sesSize; i ++ ) {

	 ((StringElement) ses[i]).applyFont( font );

     }

     this.requestNewHtmlDocView();

  }

  final public void applyTextColor(final Color color) {

     final Object [] ses = this.stringElements.toArray();

     final int sesSize = ses.length;

     for(int i = 0; i < sesSize; i ++ ) {
	 ((StringElement) ses[i]).applyTextColor( color );
     }

     this.requestNewHtmlDocView();

  }

  final public void applyUnderLine() {

     if( this.caretElement == null ) {
	return;
     }

     final Object [] ses = this.stringElements.toArray();
     final int sesSize = ses.length;

     final boolean underLine = ! this.caretElement.getUnderLine();

     for(int i = 0; i < sesSize; i ++ ) {
	 ((StringElement) ses[i]).applyUnderLine( underLine );
     }

     this.requestNewHtmlDocView();

  }

  final public LinkedList getSelectedImageElements() {

    return HtmlDocument.selectedImageElements;

  }

  final public void copyToBuffer() {

     pasteNumX = pasteNumY = 0;

     if( this.startIndex != this.endIndex ) {

	this.copyStringElementsToBuffer();

     } else if( this.getSelectedImageElements().size() > 0 ) {

	this.copyImageElementsToBuffer();

     }

     this.transferToSystemBuffer();

  }

  final public void transferToSystemBuffer() {

  }

  final public void cutToBuffer() {

     pasteNumX = pasteNumY = 0;

     this.setRequestNewDocument( true );

     if( this.startIndex != this.endIndex ) {

	Utility.debug(this, "CUT TEXT" );

	this.cutStringElementsToBuffer();

	this.requestNewHtmlDocView();

     } else if( this.getSelectedImageElements().size() > 0 ) {

	Utility.debug(this, "CUT IMAGE OR WORD BOX" );

	this.cutImageElementsToBuffer();

//	requestNewHtmlDocView();

     }

  }

  final private void copyImageElementsToBuffer() {

      final Vector copiedHtmlElements = this.copiedHtmlElements;

      copiedHtmlElements.removeAllElements();

      final LinkedList ses = this.getSelectedImageElements();

      final Iterator it = ses.iterator();

      while( it.hasNext() ) {

	  final ImageElement ie = (ImageElement) it.next();

	  Utility.debug( this, "SRC AREA = " + ie.getArea() );

	  final Object cloneIe = ie.clone( this );

	  Utility.debug( this, "CLONE AREA = " + ((ImageElement) cloneIe).getArea() );

	  copiedHtmlElements.add( cloneIe );

      }

  }

  final private void cutImageElementsToBuffer() {

      final Vector copiedHtmlElements = this.copiedHtmlElements;

      copiedHtmlElements.removeAllElements();

      final LinkedList ses = this.getSelectedImageElements();

      final Object [] sesArray = ses.toArray();

      for( int i = 0, len = sesArray.length; i < len; i ++ ) {

	  final ImageElement ie = (ImageElement) sesArray[ i ];

	  Utility.debug(this, "CUT IE = " + ie );

	  final Object cloneIe = ie.clone( this );

	  copiedHtmlElements.add( cloneIe );

      }

      // 다 중 선 택 시 코 드
      // 커트 한 후에 선택된 객체는 이미지 엘리먼트에서 삭제함.

      Utility.debug( this, "REMOVING SEL IMG OR WORD BOX" );

      for( int i = 0, len = sesArray.length; i < len; i ++ ) {

	final ImageElement selImg = (ImageElement) sesArray[ i ];

	Utility.debug( this, "SEL IMG = " + selImg );

	final HtmlDocument parDoc = selImg.getParentDocument();

	Utility.debug( this, "PAR DOC = " + parDoc );

	if( parDoc == null ) {

	  continue;

	}

	parDoc.getImageElementsList().remove( selImg );

	parDoc.requestNewHtmlDocView();

      }

  }

  final private void copyStringElementsToBuffer() {

     final Vector copiedStringElements = this.copiedHtmlElements;

     copiedHtmlElements.removeAllElements();

     final StringElement caretElement = this.caretElement;

     if( caretElement == null ) {

	return;

     }

     final int startIndex = this.startIndex;
     final int endIndex = this.endIndex;

     final Iterator sesList = this.getStringElements().iterator();

     final HtmlDocument cloneDoc = this;

     while( sesList.hasNext() ) {

	 final StringElement se = (StringElement) sesList.next();

	 final int firstIndex = se.getFirstIndex();
	 final int lastIndex = se.getLastIndex();

	 if( lastIndex < startIndex ) { // 블록에서 좌측에 있을 때...

	     continue;

	 } else if( endIndex <= firstIndex ) { // 엔드 인덱스는 실제 포함영역에는 들어가지 않으므로
					       // 같거나 클때의 경우....
					       // 블록에서 우측에 있을 때.....

	     continue;

	 } else if( firstIndex <= startIndex && endIndex <= lastIndex ) {

	     // 영역을 포함할 경우....
	     // 스타트 인덱스는 영역에 들어가므로 캍거나 작을 때이다.
	     // 엔드 인덱스는 영역에 안 들어가므로 라스트 인덱스와 같을 경우는
	     // 포함하는 경우이다.

	     final String seText = se.getText();

	     final int fromIndex = startIndex - firstIndex;

	     final int toIndex = endIndex - firstIndex;

	     final String text = seText.substring( fromIndex, toIndex );

	     final StringElement seClone = (StringElement) se.clone( cloneDoc );

	     seClone.setText( text );

	     seClone.synchIndex( -1, false );

	     copiedStringElements.add( seClone );

	     continue;

	 } else if( startIndex <= firstIndex && lastIndex < endIndex ) {

	     // 완전히 포함 되는 경우...

	     final StringElement seClone = (StringElement) se.clone( cloneDoc );

	     seClone.synchIndex( -1, false );

	     copiedStringElements.add( seClone );

	     continue;

	 } else if( firstIndex <= startIndex && startIndex <= lastIndex && lastIndex < endIndex ) {

	     // 좌측에서 겹칠 경우...
	     // 스타트 인덱스는 영역에 들어가므로 캍거나 작을 때이다.
	     // 엔드 인덱스는 영역에 안 들어가므로 작을 때이다.

	     final String seText = se.getText();

	     final int fromIndex = startIndex - firstIndex;

	     final String text = seText.substring( fromIndex );

	     final StringElement seClone = (StringElement) se.clone( cloneDoc );

	     seClone.setText( text );

	     seClone.synchIndex( -1, false );

	     copiedStringElements.add( seClone );

	     continue;

	 } else if( firstIndex < endIndex && endIndex < lastIndex ) {

	     // 우측에서 겹칠 경우...

	     final String seText = se.getText();

	     final int toIndex = endIndex - firstIndex;

	     final String text = seText.substring( 0, toIndex );

	     final StringElement seClone = (StringElement) se.clone( cloneDoc );

	     seClone.setText( text );

	     seClone.synchIndex( -1, false );

	     copiedStringElements.add( seClone );

	     continue;

	 } else {

	     Utility.debug( this, "Unknown index matching case ..... Should be coded again!" );

	 }

     }

  }

  final private void cutStringElementsToBuffer() {

     this.copiedHtmlElements.removeAllElements();

     if( this.caretElement == null ) {

	return;

     }

     final Object [] ses = this.stringElements.toArray();

     final int sesSize = ses.length;

     final int startIndex = this.startIndex;
     final int endIndex = this.endIndex;

     final LinkedList stringElements = this.stringElements;

     final LinkedList cutList = new LinkedList();

     for(int i = 0; i < sesSize; i ++ ) {

	 final StringElement se = (StringElement) ses[i];

	 final StringElement cutSe = se.cut(); // string element cut

	 if( cutSe != null ) {

	    cutList.add( cutSe );

	 }

     }

     final Iterator it = cutList.iterator();

     int lastRemovedSeIdx = -1 ;

     while( it.hasNext() ) {

	final StringElement se = (StringElement) it.next();

	lastRemovedSeIdx = stringElements.indexOf( se );

	stringElements.remove( se );

     }

     final StringElement caretElement = lastRemovedSeIdx < 0 ? null : (StringElement) ( stringElements.get( lastRemovedSeIdx ) );

     if( caretElement != null ) {

	this.setCaretElement( caretElement );

	caretElement.synchIndex( 0, true );

     }

  }

  final public void pasteFromBuffer() {

      final Vector copiedHtmlElements = this.copiedHtmlElements;

      if( copiedHtmlElements.size() < 1 ) {

	  return;

      }

      final Object obj = copiedHtmlElements.elementAt( 0 );

      if( obj instanceof StringElement ) {

	  this.pasteStringElmentsFromBuffer();

	  pasteNumX ++;
	  pasteNumY ++;

      } else if( obj instanceof ImageElement ) {

	  this.pasteImageElementsFromBuffer();

      }

      this.requestNewHtmlDocView();

      HtmlFreeEditorPane.setHasSavedAsFile( false );

  }

  final private void pasteImageElementsFromBuffer() {

      final Enumeration enumIt = this.copiedHtmlElements.elements();

      final LinkedList imageElements = this.imageElements;

      while( enumIt.hasMoreElements() ) {

	 final ImageElement src = (ImageElement) enumIt.nextElement();

	 Utility.debug( this, "SRC AREA = " + src.getArea() );

	 final ImageElement srcClone = (ImageElement) src.clone( this );

	 if( srcClone instanceof ShapeElement ) {

	    addShapeElement( (ShapeElement) srcClone );

	 } else {

	    imageElements.addLast( srcClone ); // 마지막에 추가해야 z index 가 논리적 일관성을 유지한다.

	 }

	 final Rectangle2D area = srcClone.getArea();

	 srcClone.setArea( area.getX() + 10*(pasteNumX + 1), area.getY() + 10*(pasteNumY + 1), area.getWidth(), area.getHeight() );

	 if( srcClone instanceof HtmlDocument ) {

	    // 도큐먼트 추가후에 선택된 것 처럼 보이도록 하기 위해서
	    // 도큐먼트의 붙여 넣기 후에는 도큐먼트를 약한 선택된 객체로 설정한다.

	    ImageElement.WEAK_SEL_IMG_ELEM = srcClone;

	 } else {

	    ImageElement.SEL_IMG_ELEM = srcClone;

	 }

	 Utility.debug( this, "CLONE AREA = " + srcClone.getArea() );

      }

      pasteNumX ++;

      pasteNumY ++;

  }

  final public void setHyperLink(final Component comp) {

       final ImageElement sie = ImageElement.SEL_IMG_ELEM;

       final int startIndex = this.getStartIndex();
       final int endIndex = this.getEndIndex();

//       Utility.debug(this, "SI = " + startIndex + ", EI = " + endIndex );

       if( sie instanceof HtmlDocument && startIndex == endIndex ) {

	  return;

       }

       String defaultUrl;
       String defaultTarget = null;

       if( this.getSelectedImageElements().size() > 0 ) {

	   final HtmlElement element = (HtmlElement) this.getSelectedImageElements().getFirst();

	   defaultUrl = element.getHref();
	   defaultTarget = element.getTarget();

       } else {

	   final StringElement caretElement = this.caretElement;

	   defaultUrl = caretElement.getHref();
	   defaultTarget = caretElement.getTarget();

       }

       if( defaultUrl == null || defaultUrl.length() < 1 ) {

	   defaultUrl = this.defaultUrl;

       }

       if( defaultTarget == null ) {

	    defaultTarget = AppRegistry.DEFAULT_LINK_TARGET;

       }

       final String hrefAndTarget [] = this.getHrefAndTarget( comp, defaultUrl, defaultTarget );

       if( hrefAndTarget == null ) {

	  return;

       }

       final String href =  hrefAndTarget[0];
       final String target = hrefAndTarget[1];

       if( href != null ) {

	  Utility.debug(this, "LINK URL = " + href.toString() + " target = " + target );

	  this.setHyperLink( href , target );

	  defaultUrl = href.toString();

       }

  }

  final private void setHyperLink(final String href, final String target) {

//      Utility.debug(this, "ONE");

      final Iterator it = this.selectedImageElements.iterator();

      boolean encounteredPureImageElement = false;

      while( it.hasNext() ) {

	 final ImageElement ie = (ImageElement) it.next();

	 if( ie instanceof HtmlDocument ) {

	     continue;

	 }

	 ie.setHref( href, target );

	 encounteredPureImageElement = true;

      }

      if( encounteredPureImageElement ) {
	// 순수 이미지 객체를 발견하여 하이퍼링크를 적용하였으면,
	// 텍스트에는 하이퍼 링크를 적용하지 않고,
	// 함수를 종료한다.

	this.requestNewHtmlDocView();

	return;

      }

//      Utility.debug(this, "TWO" );

      this.applyHref( href, target );

      this.requestNewHtmlDocView();

  }

  final private String [] getHrefAndTarget(final Component refComp, String refHref, final String refTarget) {

      refHref = refHref == null ? "" : refHref;

      final String enterUrlText = "<html><font color=blue> Please enter an</font> <font color=red> URL </font> <font color = blue>to link! </font> </html>";

      final JTextField urlTF = new JTextField( refHref );

      final String defaultTarget = AppRegistry.DEFAULT_LINK_TARGET;

      final JTextField targetTF = new JTextField( );

      final String targetText = "<html><p align = center> <font color = blue> target </font> </p> </html>";

      final JComboBox targetCB = new JComboBox();

      final JCheckBox removeLinkCB = new JCheckBox( "Hyper Link Remove" );

      targetCB.addItemListener(new ItemListener() {

	public void itemStateChanged(ItemEvent e) {

	   final int i = targetCB.getSelectedIndex();

	   if( i == 1 ) {

	    targetTF.setEditable( true );

	  } else {

	    targetTF.setEditable( false );

	  }

	  if( i == 0 ) {

	    targetTF.setText( defaultTarget );

	  } else {

	    targetTF.setText( "" );

	  }

	}
      });

      targetCB.addItem( "New Window [ Default ]" );
      targetCB.addItem( "New Window [ Specified ]" );
      targetCB.addItem( "None" );

      if( refTarget == null ) {

	targetCB.setSelectedIndex( 2 );
	targetTF.setText( "" );

      } else if( refTarget.equals( defaultTarget ) ) {

	targetTF.setText( refTarget );
	targetCB.setSelectedIndex( 0 );

      } else {

	targetTF.setText( refTarget );
	targetCB.setSelectedIndex( 1 );

      }

      final String[] options = {
	"OK",
	"Cancel",
      };

      final int result = JOptionPane.showOptionDialog( refComp,
						 new Object [] { enterUrlText, urlTF, targetCB, targetText, targetTF, removeLinkCB },
						 "HTML LINK",
						 JOptionPane.DEFAULT_OPTION,
						 JOptionPane.INFORMATION_MESSAGE,
						 null,
						 options,
						 options[0]
						);

      if( result == 1 ) {

	return null;

      }

      // 링크 삭제 체크 박스가 선택되면 널 값을 리턴한다.

      if( removeLinkCB.isSelected() ) {

	return new String [] { "", "" };

      }

      // 끝. 링크 삭젝 체크 박스 선택 여부 조사.

      final int selTargetCBIdx = targetCB.getSelectedIndex();

      final String target = selTargetCBIdx == 2 ? null : targetTF.getText();

      return new String [] { urlTF.getText(), target };

  }

  private void pasteStringElmentsFromBuffer() {

     // Divide caret element

     LinkedList stringElements = this.stringElements;

     if( this.caretElement == null ) {

	return;

     }

     int si = this.startIndex;
     int ei = this.endIndex;

     if( si == ei ) {

	caretElement.divideCaretStringElement();

	caretElement = this.caretElement;

     } else {

	 deleteSelectedCharacters();

	 syncCaretStringElement( true );

     }

     // End of division caret element

     // paste truly

     StringElement caretElement = this.caretElement;

     int index = stringElements.indexOf( caretElement );

     Enumeration enumIt = this.copiedHtmlElements.elements();

     while( enumIt.hasMoreElements() ) {

	 StringElement src = (StringElement) enumIt.nextElement();

	 StringElement srcClone = (StringElement) src.clone( this );

	 stringElements.add( index, srcClone );

	 srcClone.synchIndex( -1, false );

	 index ++;

     }

     this.startIndex = this.endIndex = caretElement.getFirstIndex();

     // end of pasting truly

  }

  public void applyBoldic() {

     if( this.caretElement == null ) {
	return;
     }

     Object [] ses = this.stringElements.toArray();
     int sesSize = ses.length;

     boolean boldic = ! this.caretElement.getFont().isBold();

     for(int i = 0; i < sesSize; i ++ ) {
	 ((StringElement) ses[i]).applyBoldic( boldic );
     }

     this.requestNewHtmlDocView();

  }

  public void applyItalic() {

     if( this.caretElement == null ) {
	return;
     }

     Object [] ses = this.stringElements.toArray();
     int sesSize = ses.length;

     boolean italic = ! this.caretElement.getFont().isItalic();

     for(int i = 0; i < sesSize; i ++ ) {
	 ((StringElement) ses[i]).applyItalic( italic );
     }

     this.requestNewHtmlDocView();

  }

  public void applyHref(String href, String target) {

      Utility.debug(this, "HREF = " + href + ", TARGET = " + target );

      if( ( this.startIndex == this.endIndex ) && ( this.caretElement != null ) ) {

	this.caretElement.setHref( href, target );

	this.requestNewHtmlDocView();

	return;

      }

      final int orgEndIndex = this.endIndex;

      char charAtEndIndex = this.getChartAt( orgEndIndex -1 );

      if( charAtEndIndex == '\n' ) {

	this.endIndex -= 1; // 일 만큼 인덱스를 감소 시킨다. 하이퍼 링크 설정시는 마지막
			    // 문자가 개행 문자이면 이를 제외 시킨다.

      }

      Object [] ses = this.stringElements.toArray();

      for(int i = 0, sesSize = ses.length; i < sesSize; i ++ ) {

	 ((StringElement) ses[i]).applyHref( href, target );

      }

      this.endIndex = orgEndIndex;

      this.requestNewHtmlDocView();

  }

  public LinkedList getImageElementsList() {

    return this.imageElements;

  }

  public ImageElement [] getImageElementsArray() {

      Object [] src = this.imageElements.toArray();

      int len = src.length;

      ImageElement [] dst = new ImageElement[ len ];

      System.arraycopy( src, 0, dst, 0, len );

      return dst;

  }

  public int getLength() {

      int length = 0;

      Iterator it = this.stringElements.iterator();

      while( it.hasNext() ) {
	  length += ((StringElement) it.next()).getText().length();
      }

      return length;

  }

  public void selectAll() {

      this.startIndex = 0;
      this.endIndex = this.getLength();

      Utility.debug(this, "SELECT ALL" );

      this.requestNewHtmlDocView();

  }

  public void setStartIndex(int index) {

     this.startIndex = index;

  }

  public void setEndIndex(int index) {

     this.endIndex = index;

  }

  public int getStartIndex() {

     return this.startIndex;

  }

  public int getEndIndex() {

     return this.endIndex;

  }

  public void addImage(final File file) {

     this.addImageElement( new ImageElement( this, file, this.getRelativeCaretLocation() ) );

  }

  public void addImage(final URL url) {

     this.addImageElement( new ImageElement(this, url, this.getRelativeCaretLocation() ) );

  }

  public void addChart(ChartData data) {

     this.addImageElement( new ChartElement(this, null, this.getRelativeCaretLocation(), data ) );

  }

  public void addAudio(File file) {

     this.addImageElement( new AudioElement(this, file, this.getRelativeCaretLocation() ) );

  }

  public void addVideo(File file) {

     this.addImageElement( new VideoElement(this, file, this.getRelativeCaretLocation() ) );

  }

  public void addImageElement(ImageElement element) {

     if( element instanceof ShapeElement ) {

       this.addShapeElement( (ShapeElement) element );

     } else {

	this.imageElements.addLast( element );

     }

     // 이미지가 추가 되면 자동으로 선택된 객체로 인식한다.
     // 마우스 이벤트를 널로 넘기면 무조건 선택된 객체로 설정된다.
     setSelectedImageElement( element, null );

     this.requestNewHtmlDocView();

  }

  public void addStringElement(StringElement element) {

     StringElement ce = this.caretElement;

     int idx = this.stringElements.indexOf( ce );

     this.stringElements.add( idx, element );

  }

  public void addText(String text, Font font, Color color, boolean underLine ) {

     StringElement se = new StringElement( this, text, font, color, underLine ) ;

     LinkedList ses = this.stringElements; // string elements

     int ci = ses.indexOf( this.caretElement ); // caret element index

     ses.add( ci, se );

     this.requestNewHtmlDocView();

  }

  public void moveCaretForward( ) {

//     syncCaretStringElement( true );

     StringElement ce = this.caretElement;
     ce.moveCaretForward( );

  }

  public void moveCaretBackward( ) {

//     syncCaretStringElement( true );

     StringElement ce = this.caretElement;
     ce.moveCaretBackward( );

  }

  // 이 함수는 View를 생성 할 때만 사용한다.
  // 다른 함수에서는 사용하면 안 된다.

  public double getDocumentWidth() {

      // 무한 루프 빠지는 것을 방지하기 위해서 반드시 슈퍼 getSize() 함수를 사용한다.
      return super.getArea().getWidth();

  }

//  public int getDocumentTotalHeight() {
//
//    return getHtmlDocView().getViewTotalSize().height;
//
//  }

  public void setDocumentWidth(int width) {

    Rectangle2D area = this.getArea();

    if( area.getWidth() == width ) {

      return;

    }

    this.setArea( area.getX(), area.getY(), width, area.getHeight() );

  }

  public void deletePrevious() {

     Utility.debug(this, "delete previous" );

     StringElement ce = this.caretElement;

     ce.deletePrevious();

     this.requestNewHtmlDocView();

  }

  public void deleteNext() {

     int si = this.startIndex;
     int ei = this.endIndex;

     if( si == ei ) {

	this.deleteOneCharacter();

     } else {

	this.deleteSelectedCharacters();

     }

     this.requestNewHtmlDocView();

  }

  public void deleteImageElement(ImageElement ie, MouseEvent e) {

     this.imageElements.remove( ie );

     if( ie.isSelected() ) {

	setSelectedImageElement( ie.getParentDocument(), e );

     }

     this.requestNewHtmlDocView();

  }

  protected void deleteSelectedCharacters() {

     final LinkedList stringElements = this.stringElements;

     final StringElement caretElement = this.caretElement;

     final Object [] ses = stringElements.toArray();

     final int sesSize = ses.length;

     final LinkedList seToRemoveList = new LinkedList();

     for(int i = 0; i < sesSize; i ++ ) {

	 StringElement [] ase = ((StringElement) ses[i]).getAppliedStringElements();

	 if( ase[1] != null ) {

	    seToRemoveList.add( ase[1] );

	 }

     }

     final Iterator it = seToRemoveList.iterator();

     int caretIdx = -1;

     while( it.hasNext() ) {

	StringElement se = (StringElement) it.next();

	caretIdx = stringElements.indexOf( se );

	stringElements.remove( se );

     }

     if( caretIdx < 0 || stringElements.size() < 1 ) {

	final StringElement nce = new StringElement( this, "\n", caretElement.getFont(), caretElement.getColor(), false );

	stringElements.addLast( nce );

	this.setCaretElement( nce );

	nce.synchIndex(0, true );

     } else {

	StringElement ce = (StringElement) stringElements.get( caretIdx );

	this.setCaretElement( ce );

	ce.synchIndex( 0, true );

     }

  }

  private void deleteOneCharacter() {

     Utility.debug(this, "delete next" );

     StringElement ce = this.caretElement;

     ce.deleteNext();

     this.requestNewHtmlDocView();

  }

  public void synchIndex() {

     StringElement se = this.caretElement;
     se.synchIndex( true );

  }


  /**
   * preprocess string elements to remove zero text length elements
   * and combine the same attribute's string elements that contacts.
   */

  private void preprocessStringElement() {

     // removes zero-length string elements

     LinkedList list = this.stringElements;  // string elements linked list

     Iterator it = list.iterator();   // string elements iterator

     Vector waste = new Vector();

     while( it.hasNext() ) {

	 StringElement se = (StringElement) it.next();  // string element

	 if( se.getText().length() == 0 ) {   // remove string element if the text-length is zero.

	    waste.addElement( se );
//            Utility.debug(this, "Encountered zero-length string element and removed it!" );
	 }

     }

     // remove target elements truly
     it = waste.iterator();

     while( it.hasNext() ) {

	list.remove( it.next() );

     }

     // end of removal zero-length string elements

     // combines the same attribute's string elements that contacts

     list = this.stringElements;

     waste = new Vector();

     it = list.iterator();

     StringElement curr = null, next = null; // current and next string element

     while( it.hasNext() ) {

	 if( curr == null ) {

	    curr = (StringElement) it.next();

	 }

	 if( it.hasNext() ) {

	    next = (StringElement) it.next();

	 }

	 if( curr != null && next != null ) {

	    boolean combined = curr.hasCombined( next );

	    if( combined ) {

	       waste.addElement( next );

	    } else {

	       curr = next;

	    }

	 }

     }

     // remove target elements truly
     it = waste.iterator();

     while( it.hasNext() ) {

	list.remove( it.next() );

     }

     // end of combining the same attribute's string elements
  }

  @Override
public Vector createViews(Point scanPoint, Insets margin) {

      Vector views = new Vector();

      views.addElement( this.getHtmlDocView() );

      return views;

  }

  protected Insets getScanMargin() {

    return this.scanMargin;

  }

  private void createView() {

     // preprocess string elements to remove zero-length string elements
     // and combine the same attribute's string elements that contacts

     Utility.debug(this, "CREATING DOC VIEW " );

     this.preprocessStringElement();

     // end of preprocessing string elements.


     HtmlDocView docView = new HtmlDocView( this );

     // Creation string views
     LinkedList seList = this.stringElements; // string elements

     int slSize = seList.size(); // string elements size

     // init scan indexes of string elements

     for(int i = 0; i < slSize; i ++ ) {

	 StringElement se = (StringElement) seList.get( i );
	 se.setScanIndex( -1 );

     }

//     Utility.debug(this, "string elements list size = " + slSize );

     Point scanPoint = new Point(0, 0);

     Insets scanMargin = getScanMargin();

     for(int i = 0; i < slSize; i ++ ) {

	 StringElement se = (StringElement) seList.get(i);

	 Vector rowViews = se.createViews( scanPoint, scanMargin );

	 Enumeration enumIt = rowViews.elements();

	 while( enumIt.hasMoreElements() ) {

	     docView.addElement( enumIt.nextElement() );

	 }

     }

     // End of creation string views

     // Creation image views

     LinkedList ieList = this.imageElements; // image elements;

     int ilSize = ieList.size(); // image elements size

     for(int i = 0; i < ilSize; i ++ ) {

	 ImageElement ie = (ImageElement) ieList.get(i);

	 Vector imgViews = ie.createViews( null, null );

	 Enumeration enumIt = imgViews.elements();

	 while( enumIt.hasMoreElements() ) {

	     docView.addElement( enumIt.nextElement() );

	 }

     }

     // End of creation image view

     this.docView = docView;

     // 최상위 도큐먼트의 뷰가 재 생성되면 에디터에게 밸리데이션을 요구한다.
     if( isTopMostDocument() ) {

	requestValidation();

     }

//     Utility.debug(this, "DONE CREATING DOC VIEW " );

  }

  public void requestValidation() {

      if( mode.isFileOpenning() ) {

	return;

      }

      HtmlEditorPane editor = this.getDocumentEditor();

      if( editor != null ) {

	 editor.requestValidation();

      }

  }

  private void initDocument() {

     // remove all stringelements
     LinkedList stringElements = this.stringElements;

     for(int i = 0, len = stringElements.size(); i < len; i ++ ) {
	 stringElements.remove( i );
     }
     // end of removal all stringelements

     // add new caret element

     StringElement se = new StringElement(this, "\n" );
     stringElements.add( se );

     this.caretElement = se;

     se.setCaretIndex(0);
     se.setEditIndex(0);

     // End of adding new caret element
  }

  public void setCaretElement(StringElement elem) {

     StringElement ce = this.caretElement;

     if( ce != null && ce != elem ) {

	 ce.synchIndex( -1, false );
     }

     this.caretElement = elem;

  }

  private String getFilteredText( String text ) {

    final String rnText = CharacterUtility.nl;

    int index = text.indexOf( rnText );

    while( index > -1 ) {

      text = text.substring( 0, index ) + "\n" + text.substring( index + 2 );
      index = text.indexOf( rnText );

    }

    return text;


  }

  public void processText( String text ) {

//    Utility.debug(this, "PROCESSING TEXT = " + text );

    text = this.getFilteredText( text );

    // 선택된 영역이 있으면 먼저 지운다.

    this.rowCaretX = -1; // 방향키 히스토리 정보를 -1 값으로 설정하여 방향키 히스토리를 삭제한다.

    mode.setMode( Mode.EDIT ); // 편집용 텍스트가 입력되면 자동으로 에디팅 모드로 전환된다.

    int si = this.startIndex;
    int ei = this.endIndex;

    if( si < ei ) {

	this.deleteSelectedCharacters();

    }

    // 끝. 선택된 영역 지우기

    // 탭 문자를 스페이스 문자 몇 개로 치환한다.

    int tabIndex = text.indexOf( tab );

    String tabStr = AppRegistry.TAB_STRING;

    while( tabIndex > -1 ) {

       int textLen = text.length();

       text = text.substring( 0, tabIndex ) + tabStr
	      + ( tabIndex < textLen - 1 ? text.substring( tabIndex + 1 ) : "" ) ;

       tabIndex = text.indexOf( tab );

    }

    // 끝. 탭 문자를 스페이스 문자 몇 개로 치환 하기.

    // 입력된 문자열을 처리한다.

    StringElement se = this.caretElement;

    se.processText( text );

    this.requestNewHtmlDocView();

    // 끝. 입력된 문자열 처리.

    // 뭔가가 에디터 되었다는 것을 알린다.
    // 그래서 프로그램을 종료하거나 새 문서를 열때 저장할 건지 말 건지를 물어보게 한다.

    HtmlFreeEditorPane.setHasSavedAsFile( false );

    // 끝. 에디팅 된 것 알리기.

  }

  public void requestNewHtmlDocView() {

    this.docView = null;

    if( ! this.requestNewHtmlDocument ) {

      return;

    }

    if( this.parentDoc != null ) { // 일반 워드 박스이면 상위 도큐먼트에게 새 문서 생성을 요구한다.

      this.parentDoc.requestNewHtmlDocView();

    } else { // 최상위 도큐먼트 이면 밸리 데이션을 요구한다.

      this.requestValidation();

    }

  }

  public void syncCaretStringElement(boolean synchSelection) {

     StringElement se = this.caretElement;

     if( se != null ) {

	se.synchIndex( synchSelection );

     }

  }

  public StringElement getPreviousElement(StringElement elem) {
     int ei = this.stringElements.indexOf( elem );
     if( ei > 0 ) {
	return (StringElement) this.stringElements.get( ei - 1);
     } else {
	return null;
     }
  }

  public StringElement getNextElement(StringElement elem) {

     int ei = this.stringElements.indexOf( elem );

     if( ei > -1 && ei < this.stringElements.size() - 1 ) {

	return (StringElement) this.stringElements.get( ei + 1);

     } else {

	return null;

     }

  }

  public StringElement getCaretElement() {
     return this.caretElement;
  }

  public boolean isWordBox() {

    return ( this.parentDoc != null ) ;

  }

  final public boolean isTopMostDocument() {

    return ! this.isWordBox();

  }

  final protected Color getCaretColor() {

      return ( super.SEL_IMG_ELEM == this && ( this.startIndex == this.endIndex ) && ( ! ( this instanceof TableDocument ) ) )
	     ? this.getFillColor() : null;

  }

  // 도큐먼트에 상대적인 캐릿 로케이션을 반환한다.
  final private Point getRelativeCaretLocation() {

    // 커서 값은 항상 상대적으로 잡힌다. 절대 좌표 값이 아니다.

     return new Point(  this.cursorRect.x, this.cursorRect.y );

  }

  final public void setCursorRect( final Rectangle cursorRect ) {

      this.cursorRect = cursorRect;

  }

  // 최상위 도큐먼트를 기준으로 절대 좌표 값의 커서 도형을 리턴한다.

  final public Shape getAbsoluteCursor() {

      if ( super.SEL_IMG_ELEM != this || this instanceof TableDocument ) {

	  return null;

      }

      final Shape cursorRect = this.cursorRect;

      final Point2D absLoc = this.getAbsoluteLocation();

      return AffineTransform.getTranslateInstance( absLoc.getX(), absLoc.getY() ).createTransformedShape( cursorRect );

  }

  @Override
public Object clone(final HtmlDocument parentDoc) {

      final HtmlDocument doc = new HtmlDocument( parentDoc );

      // 도큐먼트 영역 클로닝
      doc.setArea( (Rectangle2D) ( this.getArea().clone() )  );
      // 끝. 도큐먼트 영역 클로닝.

      final Insets scanMargin = parentDoc.getScanMargin();

      // 클론된 도큐먼트의 로케이션 보정
      // 아버지 도큐먼트의 스캔마진의 두배 만큼 x, y를 잡아서...
      // 실제 복사시에.....멋있게 복사되도록 한다.

      doc.setLocation( 2*scanMargin.left, 2*scanMargin.top );

      // 끝. 클론 도큐먼트 로케이션 보정.

      // 도큐먼트 색상 콜로닝

      doc.setFillColor( this.getFillColor() );
      doc.setBorderColor( this.getBorderColor() );
      doc.setBorderWidth( this.getBorderWidth() );

      // 끝. 도큐먼트 속성 클로닝

      // 스트링 엘리먼트 클론닝

      final LinkedList ses = this.stringElements;

      final LinkedList sesClone = new LinkedList();

      Iterator it = ses.iterator();

      while( it.hasNext() ) {

	StringElement se = (StringElement) it.next();

	sesClone.add( se.clone( doc ) );

      }

      doc.stringElements = sesClone;

      // 끝. 스트링 엘리먼트 클로닝

      // 이미지 엘리먼트 클로닝

      final LinkedList ies = this.imageElements;

      final LinkedList iesClone = new LinkedList();

      it = ies.iterator();

      while( it.hasNext() ) {

	ImageElement ie = (ImageElement) it.next();

	iesClone.add( ie.clone( doc ) );

      }

      doc.imageElements = iesClone;

      // 끝. 이미지 엘리먼트 클로닝

      // 클론된 도큐먼트 초기화
      // 레이어에서 읽어 들이는 함수를 이용한다.
      // 전에 해 놓은 일에 대한 값어치를 느낀다.

      HtmlLayer.initHtmlDocumentCloned( doc );

      // 끝. 콜론된 도큐먼트 초기화

      return doc;

  }

  public void addShapeElement( final ShapeElement shapeElement ) {

//      this.shapeElements.addLast( shapeElement );

      this.imageElements.addLast( shapeElement );

      this.requestNewHtmlDocView();

  }

  // docView 를 강제적으로 널 값으로 설정한다.
  // 파일을 읽어 들인 후, 텍스트가 제대로 페인트 되지 않는 버그를
  // 해결하기 위하여 HtmlLayer에서 테이블과 테이블 셀의 docView를
  // 강제적으로 널 값으로 설정한 다음에,
  // 실제적으로 페인트 시에 뷰를 다시 생성하도록 한다.

  public void setHtmlDocViewAsNull() {

    this.docView = null;

  }

  private void showDocumentInfoVisually( final HtmlDocument doc) {

    if( doc == null ) {

      return;

    }

    final HtmlEditorPane editor = doc.getDocumentEditor();

    editor.showDocumentInfoVisually( doc );

  }

  private char getChartAt( final int index ) {

    if( index < 0 ) {

      return (char) -1;

    }

    final LinkedList sesList = this.getStringElements();

    String text;

    int scanIndex = 0;
    int textLen;

    for( int i = 0, sesLen = sesList.size(); i < sesLen; i ++ ) {

      text = ( (StringElement) sesList.get( i ) ).getText();

      textLen = text.length();

      if( ( scanIndex <= index ) && ( index <= ( scanIndex + textLen ) ) ) {

	return text.charAt( index - scanIndex );

      }

      scanIndex += textLen;

    }

    return (char) -1 ;

  }

}
