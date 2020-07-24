
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

  // �ڱ� �ڽ��� �ƹ����� �ϴ��ڽ� ��ť��Ʈ�� �����ϴ� �Լ��̴�.

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

   // ���콺 �̺�Ʈ�� �� ���̸� Ű���忡�� �Ѱ��ִ� �̺�Ʈ �̴�.
   // ���콺 �̺�Ʈ�� Ű���� �̺�Ʈ�� ȣȯ ���� �����Ƿ�

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
   * �̹��� ������Ʈ�� �������� ��Ʈ����
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

    for(int k = 2; k > - 1; k -- ) { // ��Ÿ���� �ε����� ���� �� ���� �˻��Ѵ�. z - index�� ���� ���� ��Ÿ���� ���� ����.
      // ��Ÿ���� �ݵ�� 0, 1, 2 �� ���̾�� �Ѵ�.

       final Iterator it = getImageElementsList( doc, k ).iterator();

       while( it.hasNext() ) {

	   final ImageElement ie = (ImageElement) it.next();

	   if( ie instanceof HtmlDocument ) {

	     // ��ť ��Ʈ �� �ܿ�� ���� ��ť��Ʈ�� �̹��� ������Ʈ ���� ��������� ȣ���Ͽ�
	     // �ִ� ���������� ���� �̹��� ������Ʈ�� ã�Ƴ���.

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
	    // ���� ������Ʈ�� ���

	    return new ImageElementAndTopology( ie, getTopology( ie, e ) );

	  } else { // ������ �̹��� ������Ʈ �� ���

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

       // ���õ� �̹��� ������Ʈ�� ��ť��Ʈ
       // �̹��� ������Ʈ�� HtmlDocument Ÿ�� �� ��쿡�� �ڽ��� �Ǿ�����,
       // ���� �̹��� ������Ʈ�̸� �ƹ��� ��ť��Ʈ�� �Ǿ�����.

       final HtmlDocument sieDoc = (SIE instanceof HtmlDocument) ? ((HtmlDocument) SIE) : SIE.getParentDocument();

       // ���콺�� �� ������ ���콺 �̺�Ʈ �� ��쿡��.....
       // ������ ���õ� �̹��� ������Ʈ ��ť��Ʈ�� ��ȯ�Ѵ�.
       // �ֳĸ�, ������ �� ���� �ڽ� �߰� ��忡��
       // ���õ� �̹��� ������Ʈ�� �ݵ�� ��ť��Ʈ Ŭ������ �����ϱ� �����̴�.

       if( id != e.MOUSE_PRESSED ) {

	  return sieDoc;

       }

       // ���콺�� ��������...

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

    // ���� �ڽ��� ���õǾ��� ���, ���õ� ���� �ڽ����� Ÿ�� ������Ʈ�� ���Ѵ�.
    // �̹��� ������Ʈ��, ���� ���� �ڽ��� ���� �Ǿ� �� �� �� �ִ�.
    // ��������� ������Ʈ���� �˻��Ͽ�, ������(OUT_OF_AREA)�� ���� �ƴ� ������Ʈ��
    // �˻��Ǹ� �� ������Ʈ�� Ÿ�� ������Ʈ�� �Ǿ�����.

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

    // ��. ���õ� ���� �ڽ����� Ÿ�� ������Ʈ ���ϱ�.

    final ImageElementAndTopology ieNmt = getImageElementOfMaxTopology( doc, e ); // image element that has max topology

    return ieNmt.imageElement;

  }

  // �� �Լ��� ����ƽ�ϰ� �ֻ��� ��ť��Ʈ���� �� �� �� ���Ѵ�.
  // �Ϲ� ���� �ڽ������� ������ �ʴ´�. �߸��ϸ� ���� ������ ���� �� �����Ƿ�....

  public boolean processMouseEvent(MouseEvent e) {

    int id = e.getID();

    final HtmlEditorPane editor = (HtmlEditorPane) e.getSource();

    final ImageElement target = getTargetImageElement( this, e );

    if( id == e.MOUSE_PRESSED && ( target instanceof ShapeElement ) ) {

      // ���� ������Ʈ�� ���콺�� ������ �� ���õǾ�����,
      // ���� ������Ʈ�� �ַο� Ÿ�� �� ��,
      // ���� ������Ʈ �ε����� �����Ѵ�.

      final ShapeElement shapeElement = (ShapeElement) target;

      if( shapeElement.isArrowShapeElement() ) {

	  ShapeElement.MOUSE_PRESSED_POINT_INDEX = shapeElement.getPointIndex( e.getX(), e.getY() );

      } else {

	  ShapeElement.MOUSE_PRESSED_POINT_INDEX = - 1;

      }

    }

//    Utility.debug(this, "TARGET = " + target );

    if( id == e.MOUSE_PRESSED ) {

      // ���� ���õ� ���ŷ ���� Ŭ���� �ϴ� ����....

      final ImageElement PSIE = ImageElement.SEL_IMG_ELEM; // ���� ���õ� �̹��� ������Ʈ....

      // ���� �۾��� ������Ʈ�� ��ť��Ʈ
      final HtmlDocument psDoc = (PSIE instanceof HtmlDocument) ?
				   ( (HtmlDocument) PSIE )
				   : ( PSIE == null ? null : PSIE.getParentDocument() );

      // ���� �۾��� ������Ʈ�� ��ť��Ʈ

      final HtmlDocument currDoc = (target instanceof HtmlDocument) ? (HtmlDocument) target : target.getParentDocument();

      if( psDoc != null && psDoc != currDoc ) {

	  // ���� ���õ� ��ť��Ʈ�� ���� ������ ��ť��Ʈ�� �ٸ� ��쿡��....
	  // ���� ���ŷ�� �����ϰ�(Ŭ�����ϰ�) �Ѵ�.

	  psDoc.syncCaretStringElement( true ); // ������ ���õ� ���ŷ�� ���ش�.

      }

      final boolean isRightButton = Utility.isRightMouseButton( e );

      if( ! isRightButton ) {

	  // ���� ���콺�� �������� ������ �������� �ؽ�Ʈ ��ǲ�� �ʱ�ȭ �Ѵ�.

	  editor.initTextInput();

	  // ���� �۾��� ��ť��Ʈ�� ĳ�� ������Ʈ�� ����ȭ�Ѵ�.

	  if( currDoc != null ) {

	    currDoc.syncCaretStringElement( true );

	  }

      }

      // ��. ���� ���ŷ ���� �����ϰ� ���̰� �ϱ�.

      // ���콺 �̺�Ʈ�� ó���� ���� �̹��� ������Ʈ�� �����Ѵ�.
      // ���콺�� ���� ����......

      setSelectedImageElement( target, e );

      // ���콺�� ���� ���� �۾� ��带 �����Ѵ�.
      // �׿ܴ� �۾��� �Ѵ�. ��忡 ����.......

      mode.setMode( getMode( target, e ) );

      // ��. �۾� ��� ����

      // ���콺�� ���� �� ��� �۾��� �ʿ��� ���콺 ���������� �����Ѵ�.
      // ���콺�� ���� ���� ���� �����Ѵ�.

      MOUSE_TOPOLOGY = getTopology( target, e );

      // ��. ���콺 �������� ����

//      Utility.debug(this, "TARGET = " + target + ", MODE = " + mode.getMode() + ", MOUSE TOP = " + MOUSE_TOPOLOGY );

    }

    if( id != e.MOUSE_DRAGGED && id != e.MOUSE_RELEASED ) {

      setCursor( getTopology( target, e ), target, (HtmlEditorPane) e.getSource() );

    }

    // ���콺 �巡��� ���ܿ��� ��ũ�Ѹ�

    if( id == e.MOUSE_DRAGGED && mode.isMode( Mode.EDIT ) ) {

	final int x = e.getX();
	final int y = e.getY();

	final Rectangle visiRect = editor.getVisibleRect();

	final Dimension editorSize = editor.getSize();

	final int margin = visiRect.height/10;

	if(     ( y + margin > visiRect.y + visiRect.height )
	     && ( visiRect.y + visiRect.height < editorSize.height ) ) {

	  editor.scrollBy( 0, margin + 5 );

//	  return true; // ���ܿ��� ��ũ���� ������ ���콺 �̺�Ʈ�� ó���� ���������� �ϴ� ������.

	} else if(     ( y - margin < visiRect.y )
		    && ( visiRect.y > margin ) ) {

	  editor.scrollBy( 0, - (margin + 5 ) );

	}

    }

    if( target instanceof HtmlDocument ) { // ��ť��Ʈ ������Ʈ �� ���

       ((HtmlDocument) target).processDocumentMouseEvent( e );

    } else if( target != null ) { // �̹��� ������Ʈ �� ���....

       // ���콺�� ������ �� ������ ���콺 ���������� ���ؼ� �̹��� ���콺 �̺�Ʈ ó���� �Ѵ�.

       target.processImageMouseEvent( MOUSE_TOPOLOGY, e );

    }

    if( id == e.MOUSE_RELEASED ) {

      setCursor( getTopology( target, e ), target, (HtmlEditorPane) e.getSource() );

      // ���콺�� ������ �ٽ� �۾� ��带 �����Ѵ�.
      // ������ ���� ��ȯ�� ��찡 ��κ��̴�.

      mode.setMode( getMode( target, e ) );

      // ���콺�� ������ ������ ���콺 ���������� �ʱⰪ(�����ٱ�)���� �����Ѵ�.
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

       return true;                     // �̹��� ������Ʈ �̺�Ʈ ó���� �Ϸ� �Ǹ�
					// ��ť��Ʈ ������Ʈ �̺�Ʈ ó���� ���� �ʴ´�.

    }

					// ������ �ѱ� ���콺 ���������� ���콺�� ���� �� ��������...
					// ���콺 �������� ���̴�.
					// �۾� ����ÿ�(�������� �巡�� �� ��....)
					// �����Ϳ��� �ʿ��� ���콺 ���������� ���� ������ �ʴ´�.
					// ������ �̹��� ������Ʈ ����� ���콺 ó���� ���ش�.
					// ������ ��ť��Ʈ�� �������� �̺�Ʈ ó���� �Ʒ����� ���ش�.
					// ���� ���.... ������ ���� �ε��� �����̶�� ��....
					// ���� �ڽ��� �����̶�� ��.....
					// �˾� �޴� ���߱�� �� ��.......

//    Utility.debug( this, "PROCESS DOCUMENT MOUSE EVENT ....." );

    final int id = e.getID();

    final int x = e.getX();
    final int y = e.getY();

    // ������ ����...... ���콺 �̺�Ʈ�� �ҽ� ������Ʈ�� �ݵ�� HtmlEditorPane �̾�� �Ѵ�.
    final HtmlEditorPane editor = (HtmlEditorPane) e.getSource();

    // ���� �� ���콺 ���� ��� �� �Ǻ��ϴ� ����
    final boolean isRightButton = Utility.isRightMouseButton( e );

    if( isAddWordBoxMode() && id == e.MOUSE_RELEASED ) {

       // ���� �ڽ� ��忡���� ���콺�� ������ �Ǵ� ��츸 �۾��Ѵ�.

	final HtmlDocument wordBox = new HtmlDocument( this  );

	// ���� �ڽ� ������ ���� ��ǥ�� ���������� ������ �Ѿ�´�.
	// ���� ������ �巡�� �������� ���� ���� �ڽ� ������ ���Ѵ�.

	final Rectangle2D wordBoxArea = this.getDraggedShape( MOUSE_TOPOLOGY, LAST_MOUSE_EVENT, e ).getBounds2D();

	// �߰� �� ���� �ڽ��� ũ�⸦ ���� ��ť��Ʈ�� ��� ��ġ�� ����ش�.

	/**@todo ���� �ڽ� ���Խ� ��ǥ ����
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

	mode.setMode( Mode.EDIT ); // ���� �ڽ� �߰��� ������ �����͸� ������ ���� ��ȯ ��Ų��.

	return true; // ���� �ڽ��� �߰� ������ ���콺 �̺�Ʈ ó���� �׳� ������.

    } else if( mode.isMode( Mode.ADD_TABLE ) && id == e.MOUSE_RELEASED ) {

       // ���� �ڽ� ������ ���� ��ǥ�� ���������� ������ �Ѿ�´�.
	// ���� ������ �巡�� �������� ���� ���� �ڽ� ������ ���Ѵ�.

	final Rectangle2D tableArea = this.getDraggedShape( MOUSE_TOPOLOGY, LAST_MOUSE_EVENT, e ).getBounds2D();

	// �߰� �� ���� �ڽ��� ũ�⸦ ���� ��ť��Ʈ�� ��� ��ġ�� ����ش�.

	final Point2D absLoc = this.getAbsoluteLocation();

	 // ���̺� �߰� ��忡���� ���콺�� ������ �Ǵ� ��츸 �۾��Ѵ�.

	tableArea.setRect(

			  tableArea.getX() - absLoc.getX(),
			  tableArea.getY() - absLoc.getY(),
			  tableArea.getWidth(),
			  tableArea.getHeight()

			);

	final int [] rowCol = this.getInitialRowCol( tableArea );

	final TableDocument table = new TableDocument( this, rowCol[0], rowCol[1], tableArea  );

	this.addImageElement( table );

	mode.setMode( Mode.EDIT ); // ���� �ڽ� �߰��� ������ �����͸� ������ ���� ��ȯ ��Ų��.

	return true; // ���� �ڽ��� �߰� ������ ���콺 �̺�Ʈ ó���� �׳� ������.

    } else if( isAddWordBoxMode() || mode.isMode( Mode.ADD_TABLE ) ) {

	// ���� �ڽ� ��忡���� ���콺�� ������ �Ǵ� ��츸 �۾��Ѵ�.
	// �׷���, ���콺�� �������� ��찡 �ƴϸ� �׳�....�׳�...�ƹ� �׼ǵ� ������ �ʴ´�....

	return true;

    } else if( id == e.MOUSE_PRESSED && isRightButton ) {

	  // ���� �� ���콺�� ������ �˾� �޴��� ��Ÿ����. ¯�ϰ�...���ְ�...

	 editor.initTextInput();

	 editor.showEditPopupMenu( e );

	 return true;

    } else if( id == e.MOUSE_PRESSED ) {

	 // ���� ���콺�� �������� ��쿡�� �ؽ�Ʈ ��ǲ�� �ʱ�ȭ�ϰ�,
	 // ������ �˾� �޴��� ��Ÿ�� ������, �ڵ����� �����.

	 // �̹��� ������Ʈ���� ���ش�. �׷��� ���⼭ �� �� �� Ȯ����.....

	 editor.initTextInput();

	 // �̹��� ������Ʈ���� ���ش�. �׷��� ���⼭ �� �� �� Ȯ����.....
	 // �� �Լ��� ������ �˾� �޴��� ��Ÿ�� �ִ� ���� �����Ѵ���, ��Ÿ�� ���� ��쿡��
	 // ������ �˾� �޴��� �����. �þ߿���...

	 editor.hideEditPopupMenu();

    }

    if( editor.isEditPopupMenuShowing() ) { // ������ �˾� �޴��� ��Ÿ�� ���� �ܿ쿡�� �ƹ� �ϵ� ���� �ʴ´�.
					    // �׳�... �̺�Ʈ ó���� �����Ѵ�.

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

	editor.repaint(); // ���ŷ�� �ǽð� �������� ���ؼ�

	return true;

    }

    return false;

  }

  public final static int getTableCellTopology( final HtmlDocument cellDoc, final MouseEvent e ) {

      final Point2D absParLoc = cellDoc.getAbsoluteParentLocation();

      final double x = e.getX() - absParLoc.getX();
      final double y = e.getY() - absParLoc.getY();

      // ���̺� ���� ��쿡�� OUT_OF_AREA ��
      // INSIDE_AREA ���� ���� ������.

      final Rectangle2D area = cellDoc.getArea();

      final double margin = 3;

      final double ax = area.getX();
      final double ay = area.getY();
      final double aw = area.getWidth();
      final double ah = area.getHeight();

      final Rectangle2D cellArea = new Rectangle2D.Double( ax, ay, aw, ah + 3 );
      // �� ������ �ϴ� ���� ���� ��ŭ ���ؼ� ��´�.
      // �׷��߸� TOP_BOUNDARY ���������� ���ش�.

      if( ! cellArea.contains( x, y ) ) {
	// ���콺 ��ǥ�� �� ����� ���ο� ������ ������, ������ ���������� ��ȯ�Ѵ�.

	return OUT_OF_AREA;

      }

      // ���콺 ��ǥ�� ���������� �������� Ȯ�� �Ǿ����Ƿ�...
      // �Ʒ� �ڵ� ���ʹ� ���콺 ��ǥ�� �����ȿ� ���� �ִ� ����
      // üũ���� �ʴ´�.

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

	  // �ֻ��� ��ť��Ʈ�̸� ������ ���콺 ���������� 9 (���ο� ������ �ǹ�)�� ��ȯ�Ѵ�.

	  return INSIDE_AREA;

      } else if( doc != null && ie.getDocumentBoundary().contains( x, y ) ) { // �ٿ������ ������....

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

	} else if( isAddWordBoxMode() || mode.isMode( Mode.ADD_TABLE) ) { // ���� �ڽ��� ���̺� �߰� ��� �� ���� ������ ���� ���(���� �ڽ� ���)�� ��ȯ�Ѵ�.

	      return mode.getMode(); // ���� ��� ��ȯ

	} else if( ( target instanceof HtmlDocument ) && id == e.MOUSE_RELEASED && top == INSIDE_AREA ) {

	    // ���� �ڽ� ���� ��迡�� ���콺�� ���� ���� ���� ������ ��尡 �����ȴ�.

	    return Mode.EDIT;

	} else if( id == e.MOUSE_PRESSED || id == e.MOUSE_RELEASED ) { // ���콺�� �������ų� �������� ��쿡�� �پ��� ��带 ��ȯ�Ѵ�.

	    if( target instanceof HtmlDocument && top == INSIDE_AREA ) {

		return Mode.EDIT;

	    } else if( top > -1 && top <= INSIDE_AREA ) {

		return Mode.RESHAPE;

	    }

	    return Mode.EDIT;

	} else { // ���콺�� Ŭ���Ǵ� �� ���� ���� ������ ���� ��带 ��ȯ�Ѵ�.

	    return mode.getMode() ; // ���� ��带 �����Ѵ�.

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


	  case ON_THE_BOUNDARY: // �ٿ������ ������....

		 ct = Cursor.MOVE_CURSOR;

		 break;

	  case INSIDE_AREA : // ���ο� ������.....

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

	    // ���� ���� ���� ���¿����� �� Ŀ���� �����Ѵ�.

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
      // ���õ� ��ü�� ���� �̹��� ������Ʈ�̰�,
      // ��Ʈ�� Ű�� ��������...... �ƹ��� ��ť��Ʈ���� Ű �̺�Ʈ�� ó���Ͽ�
      // ī�� ���̽�Ʈ �۾��� �Ѵ�.

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

	    // ����Ű�� �������� ������ ......
	    // ����Ű �����丮 ������ �ʱ�ȭ �Ѵ�.
	    this.rowCaretX = -1;

      }

//      Utility.debug(this, "KEY = " + e.getKeyChar() );

      final boolean ctrlDown = e.isControlDown();
      final boolean shiftDown = e.isShiftDown();

      final boolean isEmptyText = editor.getPreText().equals( "" );

      if( key == e.VK_ENTER || key == e.VK_SPACE ) { // ���� Ű�� �����̽� Ű

	 editor.initTextInput();

	 this.syncCaretStringElement( true );

	 final String text = "" + ((char) key);

	 this.processText( text );

	 this.syncCaretStringElement( true );

      } else if( key == e.VK_TAB && AppRegistry.TAB_KEY_PROCESSING ) {

	// �� Ű�� ��� �� ��

	 // �� ���� ó�� ����.....

	 editor.initTextInput();

	 this.syncCaretStringElement( true );

	 final String text = "" + ((char) key);

	 this.processText( text );

	 this.syncCaretStringElement( true );

      } else if( key == e.VK_LEFT ) {

	 // �� �� ȭ��ǥ Ű�� ��� �� ��......

	  editor.initTextInput();

	  this.synchIndex();

	  this.moveCaretBackward();

      } else if( key == e.VK_RIGHT ) {

	  // ���� �� ȭ��ǥ Ű�� ��� �� ��......

	  editor.initTextInput();

	  this.synchIndex();

	  this.moveCaretForward();

      } else if( key == e.VK_UP ) {

	  // �� �� ȭ��ǥ Ű�� ��� �� ��......

	  editor.initTextInput();

	  this.synchIndex();

	  this.goUp( e );

      } else if( key == e.VK_DOWN ) {

	  // �Ʒ� �� ȭ��ǥ Ű�� ��� �� ��.......

	  editor.initTextInput();

	  this.synchIndex();

	  this.goDown( e );

      } else if( key == e.VK_PAGE_UP ) {

	  // ������ �� Ű�� ��� �� ��.......

	  editor.initTextInput();

	  this.synchIndex();

	  this.goPageUp( e );

      }  else if( key == e.VK_PAGE_DOWN ) {

	  // ������ �ٿ� Ű�� ��� �� ��......

	  editor.initTextInput();

	  this.synchIndex();

	  this.goPageDown( e );

      } else if( (key == 'A' || key == 'a' ) && ctrlDown ) {  // select all processing

	  // Ctrl-A �� ���� ��.......

	  this.selectAll();

      } else if( ctrlDown && ( key == 'C' || key == 'c' ) ) {

	  // Ctrl-C �� ���� ��.......

	  editor.initTextInput();

	  editor.showEditPopupMenu( e );

//	  copyToBuffer();

      } else if( ctrlDown && ( key == 'X' || key == 'x' ) ) {

	  // Ctrl-X �� ���� ��.......

	  editor.initTextInput();

	  editor.showEditPopupMenu( e );

//	  cutToBuffer();

      } else if( ctrlDown && ( key == 'V' || key == 'v' ) ) {

	  // Ctrl-V �� ���� ��......

	  editor.initTextInput();

	  editor.showEditPopupMenu( e );

//	  pasteFromBuffer();

      } else if( key == e.VK_HOME || key == e.VK_END ) {

	  // Ȩ Ű�� ���� Ű�� ���� ��.......

	  editor.initTextInput();

	  this.syncCaretStringElement( true );

	  if( key == e.VK_HOME ) {

	    this.goToHome( e );

	  } else {

	    this.goToEnd( e );

	  }

      } else if( key == e.VK_BACK_SPACE ) {

	// �� �����̽� Ű�� ���� ��.......

	if( isEmptyText ) {

	  this.deletePrevious();

	  this.requestNewHtmlDocView();

	}

      } else if( key == e.VK_DELETE ) {

	// ����Ʈ Ű�� ���� ��.......

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

	    // ���� Ű�� ������ �� �ٿ� Ű�� ���� ����,
	    // ���� ��Ʈ ������ UI �� ǥ���Ѵ�.

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

      // ù �� ° ���� �������� ù �� ° �ε���, �� ��° ���� ���� ���� ����, �� �� °�� �ٿ� ���� ĳ�� �ε����̴�.

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

    // ��ť��Ʈ�� ���� �ε��� �����ÿ��� �� �ε����� ����ȭ ��Ų��.

    this.setStartIndex( index );
    this.setEndIndex( index );

  }

  private void setEndIndex(final int x, final int y, final MouseEvent e) {

       final int refIndex = this.refIndex;

       final StringElementAndMouseIndex seNmi = this.getIndex( x, y, e ); // string element and mouse index

       // ��ť��Ʈ�� �� �ε��� ���� �ÿ��� ���� �ε����� ���Ͽ�....
       // ���� ���� ���� �ε�����..... ū ���� �� �ε����� �����Ѵ�.
       // ���� �ε����� ���� �ε��� �����ÿ� ���� �ε����� ���� ������ ������.
       // �̷��� �����μ� ���콺�� ���������� �� ���� �ε����� ���� ���� �� ����
       // �� ���Ἲ�� Ȯ���ȴ�.

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

    // ��ť��Ʈ�� ���� �� ��ŭ x, y ���� �����Ѵ�.
    x -= (int) ( absLoc.getX() );
    y -= (int) ( absLoc.getY() );

    final HtmlDocView docView = this.getHtmlDocView();

    return docView.getIndex( x, y, e );

  }

  final public boolean contains(final ImageElement ie) {

    return this.imageElements.contains( ie );

  }

  /**
   * �� ������ ��������, �� �ڷ� ������,
   * ������ ��������, �ڷ� ������ �Լ��̴�.
   */
   // i = Integer.MIN_VALUE : �ǵڷ� ������
   // i = Integer.MAX_VALUE : �� ������ ��������
   // i = -1 : �ڷ� ������
   // i = 1 : ������ ������

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

    if( index > 0 ) { // ������ ������

	if( refIndex == size -1 ) {

	  // Do nothing ! �ε����� �� �� �ʿ䰡 �����Ƿ�, �ֳĸ� �� �տ� �� �����Ƿ�....

	  return;

	}

	imageElements.remove( target );

	// ������ ������ ������Ʈ�� ������ �� ���� �����ͷ��̼� �Ѵ�.
	// �� ���� �����ͷ��̼��� size-1 �̴�.
	// �������� �־�� �� ��쿡 �� ��ƾ�� �����ؼ�....
	// �����ͷ��̼��� size -1 ���� �� ������....
	// �߰��� �����ϴ� �ε����� ������.....
	// �ε��� ������ �� ���� �Լ��� �����Ѵ�.

	for(int i = refIndex; i < size - 1 ; i ++ ) {

	   final ImageElement ie = (ImageElement) imageElements.get( i );

	   if( refStyle == ie.getStyle() ) {

	       imageElements.add( i + 1, target );

	       return; // ������ �ε����� ���������Ƿ�, �۾� ������ �����Ѵ�.

	   }

	}

	// �߰��� �����ϴ� �ε����� ������ ��������...
	// ������ ������ �ε����� ���� �ִ´�.

	imageElements.addLast( target );

    } else {

	if( refIndex == 0 ) {

	  // Do nothing ! �ε����� �� �� �ʿ䰡 �����Ƿ�, �ֳĸ� �� �ڿ� �����Ƿ�.....

	  return;

	}

	imageElements.remove( target );

	// �����׷��̼��� 1 ������ �����Ѵ�.
	// �߰��� �����ϴ� �ε����� ������....
	// �ε��� ������ �Ѵ��� �Լ��� �����Ѵ�.

	for(int i = refIndex -1 ; i > 0 ; i -- ) {

	   final ImageElement ie = (ImageElement) imageElements.get( i );

	   if( refStyle == ie.getStyle() ) {

	       imageElements.add( i , target );

	       return; // �����ϴ� �ε����� ������ �����Ƿ�, �Լ��� �����Ѵ�.

	   }

	}

	// �׷��� ���ϸ�....
	// ������ �� ó���� �־��ش�.

	imageElements.addFirst( target );

    }

  }

  final public HtmlDocView getHtmlDocView() {

    if( this.docView == null ) {

      this.createView();

    }

    return this.docView;

  }

  // ��ť��Ʈ�� ������ ���� ������ ����ȭ�Ѵ�.
  // �׷��߸�..... �ڵ� ������ �Ǿ� ����.

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

    // 1 ��ŭ ���� �ְ� ����

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

  // ����ڰ� ���콺�� ��� ������ ũ���̴�.
  // �ּ� ũ��� �����ȴ�.

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

    // ��Ʈ�� ������Ʈ �ؽ�Ʈ ��ġ��

    while( it.hasNext() ) {

      final StringElement se = (StringElement) it.next();

      buffer.append( se.getText() + " " );

    }

    // ��. ��Ʈ�� ������Ʈ �ؽ�Ʈ ��ġ��

    // ���� �ڽ� �ؽ�Ʈ ��ġ��

    it = this.imageElements.iterator();

    while( it.hasNext() ) {

	 final Object obj = it.next();

	 if( obj instanceof HtmlDocument ) {

	     ((HtmlDocument) obj).getOnlyTexts( buffer );

	 }

    }

    // ��. ���� �ڽ� �ؽ�Ʈ ��ġ��

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

      // �� �� �� �� �� �� ��
      // ĿƮ �� �Ŀ� ���õ� ��ü�� �̹��� ������Ʈ���� ������.

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

	 if( lastIndex < startIndex ) { // ��Ͽ��� ������ ���� ��...

	     continue;

	 } else if( endIndex <= firstIndex ) { // ���� �ε����� ���� ���Կ������� ���� �����Ƿ�
					       // ���ų� Ŭ���� ���....
					       // ��Ͽ��� ������ ���� ��.....

	     continue;

	 } else if( firstIndex <= startIndex && endIndex <= lastIndex ) {

	     // ������ ������ ���....
	     // ��ŸƮ �ε����� ������ ���Ƿ� ���ų� ���� ���̴�.
	     // ���� �ε����� ������ �� ���Ƿ� ��Ʈ �ε����� ���� ����
	     // �����ϴ� ����̴�.

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

	     // ������ ���� �Ǵ� ���...

	     final StringElement seClone = (StringElement) se.clone( cloneDoc );

	     seClone.synchIndex( -1, false );

	     copiedStringElements.add( seClone );

	     continue;

	 } else if( firstIndex <= startIndex && startIndex <= lastIndex && lastIndex < endIndex ) {

	     // �������� ��ĥ ���...
	     // ��ŸƮ �ε����� ������ ���Ƿ� ���ų� ���� ���̴�.
	     // ���� �ε����� ������ �� ���Ƿ� ���� ���̴�.

	     final String seText = se.getText();

	     final int fromIndex = startIndex - firstIndex;

	     final String text = seText.substring( fromIndex );

	     final StringElement seClone = (StringElement) se.clone( cloneDoc );

	     seClone.setText( text );

	     seClone.synchIndex( -1, false );

	     copiedStringElements.add( seClone );

	     continue;

	 } else if( firstIndex < endIndex && endIndex < lastIndex ) {

	     // �������� ��ĥ ���...

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

	    imageElements.addLast( srcClone ); // �������� �߰��ؾ� z index �� ���� �ϰ����� �����Ѵ�.

	 }

	 final Rectangle2D area = srcClone.getArea();

	 srcClone.setArea( area.getX() + 10*(pasteNumX + 1), area.getY() + 10*(pasteNumY + 1), area.getWidth(), area.getHeight() );

	 if( srcClone instanceof HtmlDocument ) {

	    // ��ť��Ʈ �߰��Ŀ� ���õ� �� ó�� ���̵��� �ϱ� ���ؼ�
	    // ��ť��Ʈ�� �ٿ� �ֱ� �Ŀ��� ��ť��Ʈ�� ���� ���õ� ��ü�� �����Ѵ�.

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
	// ���� �̹��� ��ü�� �߰��Ͽ� �����۸�ũ�� �����Ͽ�����,
	// �ؽ�Ʈ���� ������ ��ũ�� �������� �ʰ�,
	// �Լ��� �����Ѵ�.

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

      // ��ũ ���� üũ �ڽ��� ���õǸ� �� ���� �����Ѵ�.

      if( removeLinkCB.isSelected() ) {

	return new String [] { "", "" };

      }

      // ��. ��ũ ���� üũ �ڽ� ���� ���� ����.

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

	this.endIndex -= 1; // �� ��ŭ �ε����� ���� ��Ų��. ������ ��ũ �����ô� ������
			    // ���ڰ� ���� �����̸� �̸� ���� ��Ų��.

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

     // �̹����� �߰� �Ǹ� �ڵ����� ���õ� ��ü�� �ν��Ѵ�.
     // ���콺 �̺�Ʈ�� �η� �ѱ�� ������ ���õ� ��ü�� �����ȴ�.
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

  // �� �Լ��� View�� ���� �� ���� ����Ѵ�.
  // �ٸ� �Լ������� ����ϸ� �� �ȴ�.

  public double getDocumentWidth() {

      // ���� ���� ������ ���� �����ϱ� ���ؼ� �ݵ�� ���� getSize() �Լ��� ����Ѵ�.
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

     // �ֻ��� ��ť��Ʈ�� �䰡 �� �����Ǹ� �����Ϳ��� �븮���̼��� �䱸�Ѵ�.
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

    // ���õ� ������ ������ ���� �����.

    this.rowCaretX = -1; // ����Ű �����丮 ������ -1 ������ �����Ͽ� ����Ű �����丮�� �����Ѵ�.

    mode.setMode( Mode.EDIT ); // ������ �ؽ�Ʈ�� �ԷµǸ� �ڵ����� ������ ���� ��ȯ�ȴ�.

    int si = this.startIndex;
    int ei = this.endIndex;

    if( si < ei ) {

	this.deleteSelectedCharacters();

    }

    // ��. ���õ� ���� �����

    // �� ���ڸ� �����̽� ���� �� ���� ġȯ�Ѵ�.

    int tabIndex = text.indexOf( tab );

    String tabStr = AppRegistry.TAB_STRING;

    while( tabIndex > -1 ) {

       int textLen = text.length();

       text = text.substring( 0, tabIndex ) + tabStr
	      + ( tabIndex < textLen - 1 ? text.substring( tabIndex + 1 ) : "" ) ;

       tabIndex = text.indexOf( tab );

    }

    // ��. �� ���ڸ� �����̽� ���� �� ���� ġȯ �ϱ�.

    // �Էµ� ���ڿ��� ó���Ѵ�.

    StringElement se = this.caretElement;

    se.processText( text );

    this.requestNewHtmlDocView();

    // ��. �Էµ� ���ڿ� ó��.

    // ������ ������ �Ǿ��ٴ� ���� �˸���.
    // �׷��� ���α׷��� �����ϰų� �� ������ ���� ������ ���� �� ������ ����� �Ѵ�.

    HtmlFreeEditorPane.setHasSavedAsFile( false );

    // ��. ������ �� �� �˸���.

  }

  public void requestNewHtmlDocView() {

    this.docView = null;

    if( ! this.requestNewHtmlDocument ) {

      return;

    }

    if( this.parentDoc != null ) { // �Ϲ� ���� �ڽ��̸� ���� ��ť��Ʈ���� �� ���� ������ �䱸�Ѵ�.

      this.parentDoc.requestNewHtmlDocView();

    } else { // �ֻ��� ��ť��Ʈ �̸� �븮 ���̼��� �䱸�Ѵ�.

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

  // ��ť��Ʈ�� ������� ĳ�� �����̼��� ��ȯ�Ѵ�.
  final private Point getRelativeCaretLocation() {

    // Ŀ�� ���� �׻� ��������� ������. ���� ��ǥ ���� �ƴϴ�.

     return new Point(  this.cursorRect.x, this.cursorRect.y );

  }

  final public void setCursorRect( final Rectangle cursorRect ) {

      this.cursorRect = cursorRect;

  }

  // �ֻ��� ��ť��Ʈ�� �������� ���� ��ǥ ���� Ŀ�� ������ �����Ѵ�.

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

      // ��ť��Ʈ ���� Ŭ�δ�
      doc.setArea( (Rectangle2D) ( this.getArea().clone() )  );
      // ��. ��ť��Ʈ ���� Ŭ�δ�.

      final Insets scanMargin = parentDoc.getScanMargin();

      // Ŭ�е� ��ť��Ʈ�� �����̼� ����
      // �ƹ��� ��ť��Ʈ�� ��ĵ������ �ι� ��ŭ x, y�� ��Ƽ�...
      // ���� ����ÿ�.....���ְ� ����ǵ��� �Ѵ�.

      doc.setLocation( 2*scanMargin.left, 2*scanMargin.top );

      // ��. Ŭ�� ��ť��Ʈ �����̼� ����.

      // ��ť��Ʈ ���� �ݷδ�

      doc.setFillColor( this.getFillColor() );
      doc.setBorderColor( this.getBorderColor() );
      doc.setBorderWidth( this.getBorderWidth() );

      // ��. ��ť��Ʈ �Ӽ� Ŭ�δ�

      // ��Ʈ�� ������Ʈ Ŭ�д�

      final LinkedList ses = this.stringElements;

      final LinkedList sesClone = new LinkedList();

      Iterator it = ses.iterator();

      while( it.hasNext() ) {

	StringElement se = (StringElement) it.next();

	sesClone.add( se.clone( doc ) );

      }

      doc.stringElements = sesClone;

      // ��. ��Ʈ�� ������Ʈ Ŭ�δ�

      // �̹��� ������Ʈ Ŭ�δ�

      final LinkedList ies = this.imageElements;

      final LinkedList iesClone = new LinkedList();

      it = ies.iterator();

      while( it.hasNext() ) {

	ImageElement ie = (ImageElement) it.next();

	iesClone.add( ie.clone( doc ) );

      }

      doc.imageElements = iesClone;

      // ��. �̹��� ������Ʈ Ŭ�δ�

      // Ŭ�е� ��ť��Ʈ �ʱ�ȭ
      // ���̾�� �о� ���̴� �Լ��� �̿��Ѵ�.
      // ���� �� ���� �Ͽ� ���� ����ġ�� ������.

      HtmlLayer.initHtmlDocumentCloned( doc );

      // ��. �ݷе� ��ť��Ʈ �ʱ�ȭ

      return doc;

  }

  public void addShapeElement( final ShapeElement shapeElement ) {

//      this.shapeElements.addLast( shapeElement );

      this.imageElements.addLast( shapeElement );

      this.requestNewHtmlDocView();

  }

  // docView �� ���������� �� ������ �����Ѵ�.
  // ������ �о� ���� ��, �ؽ�Ʈ�� ����� ����Ʈ ���� �ʴ� ���׸�
  // �ذ��ϱ� ���Ͽ� HtmlLayer���� ���̺�� ���̺� ���� docView��
  // ���������� �� ������ ������ ������,
  // ���������� ����Ʈ �ÿ� �並 �ٽ� �����ϵ��� �Ѵ�.

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
