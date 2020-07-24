
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

  // ÀÚ±â ÀÚ½ÅÀ» ¾Æ¹öÁö·Î ÇÏ´ÂÀÚ½Ä µµÅ¥¸ÕÆ®¸¦ »ý¼ºÇÏ´Â ÇÔ¼öÀÌ´Ù.

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

   // ¸¶¿ì½º ÀÌº¥Æ®°¡ ³Î °ªÀÌ¸é Å°º¸µå¿¡¼­ ³Ñ°ÜÁÖ´Â ÀÌº¥Æ® ÀÌ´Ù.
   // ¸¶¿ì½º ÀÌº¥Æ®¿Í Å°º¸µå ÀÌº¥Æ®´Â È£È¯ µÇÁö ¾ÊÀ¸¹Ç·Î

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
   * ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ®¿Í ÅäÆú¸®Áö ½ºÆ®·°ÃÄ
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

    for(int k = 2; k > - 1; k -- ) { // ½ºÅ¸ÀÏÀÇ ÀÎµ¦½º°¡ ³ôÀº °Í ºÎÅÍ °Ë»öÇÑ´Ù. z - index°¡ ³ôÀ» ¼ö·Ï ½ºÅ¸ÀÏÀÇ °ªµµ ³ô´Ù.
      // ½ºÅ¸ÀÏÀº ¹Ýµå½Ã 0, 1, 2 ÀÇ °ªÀÌ¾î¾ß ÇÑ´Ù.

       final Iterator it = getImageElementsList( doc, k ).iterator();

       while( it.hasNext() ) {

	   final ImageElement ie = (ImageElement) it.next();

	   if( ie instanceof HtmlDocument ) {

	     // µµÅ¥ ¸ÕÆ® ÀÏ °Ü¿ì´Â ÇÏÀ§ µµÅ¥¸ÕÆ®¿Í ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ® ±îÁö Àç±ÍÀûÀ¸·Î È£ÃâÇÏ¿©
	     // ÃÖ´ë ÅäÆú·ÎÁö¸¦ °¡Áø ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ®¸¦ Ã£¾Æ³½´Ù.

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
	    // ½¦ÀÙ ¿¤¸®¸ÕÆ®ÀÎ °æ¿ì

	    return new ImageElementAndTopology( ie, getTopology( ie, e ) );

	  } else { // ¼ø¼öÇÑ ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ® ÀÏ °æ¿ì

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

       // ¼±ÅÃµÈ ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ®ÀÇ µµÅ¥¸ÕÆ®
       // ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ®°¡ HtmlDocument Å¸ÀÔ ÀÏ °æ¿ì¿¡´Â ÀÚ½ÅÀÌ µÇ¾îÁö°í,
       // ¼ø¼ö ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ®ÀÌ¸é ¾Æ¹öÁö µµÅ¥¸ÕÆ®°¡ µÇ¾îÁø´Ù.

       final HtmlDocument sieDoc = (SIE instanceof HtmlDocument) ? ((HtmlDocument) SIE) : SIE.getParentDocument();

       // ¸¶¿ì½º°¡ ¾È ´­·¯Áø ¸¶¿ì½º ÀÌº¥Æ® ÀÏ °æ¿ì¿¡´Â.....
       // ¹«Àü°Ç ¼±ÅÃµÈ ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ® µµÅ¥¸ÕÆ®¸¦ ¹ÝÈ¯ÇÑ´Ù.
       // ¿Ö³Ä¸é, ´­·¯Áú ¶§ ¿öµå ¹Ú½º Ãß°¡ ¸ðµå¿¡¼­
       // ¼±ÅÃµÈ ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ®¸¦ ¹Ýµå½Ã µµÅ¥¸ÕÆ® Å¬·¡½º·Î ¼³Á¤ÇÏ±â ¶§¹®ÀÌ´Ù.

       if( id != e.MOUSE_PRESSED ) {

	  return sieDoc;

       }

       // ¸¶¿ì½º°¡ ´­·¯Áö¸é...

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

    // ¿öµå ¹Ú½º°¡ ¼±ÅÃµÇ¾úÀ» °æ¿ì, ¼±ÅÃµÈ ¿öµå ¹Ú½º¿¡¼­ Å¸°Ù ¿¤¸®¸ÕÆ®¸¦ ±¸ÇÑ´Ù.
    // ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ®³ª, ³»ºÎ ¿öµå ¹Ú½º°¡ ¼±ÅÃ µÇ¾î Áú ¼ö µµ ÀÖ´Ù.
    // Àç±ÍÀûÀ¸·Î ¿¤¸®¸ÕÆ®µéÀ» °Ë»öÇÏ¿©, ¿µ¿ª¹Û(OUT_OF_AREA)ÀÇ °ªÀÌ ¾Æ´Ñ ¿¤¸®¸ÕÆ®°¡
    // °Ë»öµÇ¸é ±× ¿¤¸®¸ÕÆ®°¡ Å¸ÄÏ ¿¤¸®¸ÕÆ®°¡ µÇ¾îÁø´Ù.

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

    // ³¡. ¼±ÅÃµÈ ¿öµå ¹Ú½º¿¡¼­ Å¸°Ù ¿¤¸®¸ÕÆ® ±¸ÇÏ±â.

    final ImageElementAndTopology ieNmt = getImageElementOfMaxTopology( doc, e ); // image element that has max topology

    return ieNmt.imageElement;

  }

  // ¿ä ÇÔ¼ö´Â ½ºÅÂÆ½ÇÏ°Ô ÃÖ»óÀ§ µµÅ¥¸ÕÆ®¿¡¼­ ÇÑ ¹ø ¸¸ ÄÝÇÑ´Ù.
  // ÀÏ¹Ý ¿öµå ¹Ú½º¿¡¼­´Â ÄÝÇÏÁö ¾Ê´Â´Ù. Àß¸øÇÏ¸é ¹«ÇÑ ·çÇÁ¿¡ ºüÁú ¼ö ÀÖÀ¸¹Ç·Î....

  public boolean processMouseEvent(MouseEvent e) {

    int id = e.getID();

    final HtmlEditorPane editor = (HtmlEditorPane) e.getSource();

    final ImageElement target = getTargetImageElement( this, e );

    if( id == e.MOUSE_PRESSED && ( target instanceof ShapeElement ) ) {

      // ½¦ÀÙ ¿¤¸®¸ÕÆ®°¡ ¸¶¿ì½º¸¦ ´­·¶À» ¶§ ¼±ÅÃµÇ¾îÁö°í,
      // ½¦ÀÙ ¿¤¸®¸ÕÆ®°¡ ¾Ö·Î¿ì Å¸ÀÔ ÀÏ ¶§,
      // ½¦ÀÙ ¿¤¸®¸ÕÆ® ÀÎµ¦½º¸¦ ¼³Á¤ÇÑ´Ù.

      final ShapeElement shapeElement = (ShapeElement) target;

      if( shapeElement.isArrowShapeElement() ) {

	  ShapeElement.MOUSE_PRESSED_POINT_INDEX = shapeElement.getPointIndex( e.getX(), e.getY() );

      } else {

	  ShapeElement.MOUSE_PRESSED_POINT_INDEX = - 1;

      }

    }

//    Utility.debug(this, "TARGET = " + target );

    if( id == e.MOUSE_PRESSED ) {

      // ÀÌÀü ¼±ÅÃµÈ ºí·ÏÅ· ¿µ¿ª Å¬¸®¾î ÇÏ´Â ¹®Á¦....

      final ImageElement PSIE = ImageElement.SEL_IMG_ELEM; // ÀÌÀü ¼±ÅÃµÈ ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ®....

      // ÀÌÀü ÀÛ¾÷ÇÑ ¿¤¸®¸ÕÆ®ÀÇ µµÅ¥¸ÕÆ®
      final HtmlDocument psDoc = (PSIE instanceof HtmlDocument) ?
				   ( (HtmlDocument) PSIE )
				   : ( PSIE == null ? null : PSIE.getParentDocument() );

      // ÇöÀç ÀÛ¾÷ÇÒ ¿¤¸®¸ÕÆ®ÀÇ µµÅ¥¸ÕÆ®

      final HtmlDocument currDoc = (target instanceof HtmlDocument) ? (HtmlDocument) target : target.getParentDocument();

      if( psDoc != null && psDoc != currDoc ) {

	  // ÀÌÀü ¼±ÅÃµÈ µµÅ¥¸ÕÆ®¿Í ÇöÀç ¼±ÅÃÇÒ µµÅ¥¸ÕÆ®°¡ ´Ù¸¦ °æ¿ì¿¡¸¸....
	  // ÀÌÀü ºí·ÎÅ·À» ±ú²ýÇÏ°Ô(Å¬¸®¾îÇÏ°Ô) ÇÑ´Ù.

	  psDoc.syncCaretStringElement( true ); // ÀÌÀü¿¡ ¼±ÅÃµÈ ºí¶ôÅ·À» ¾ø¾Ø´Ù.

      }

      final boolean isRightButton = Utility.isRightMouseButton( e );

      if( ! isRightButton ) {

	  // ¿ÞÂÊ ¸¶¿ì½º°¡ ´­·¯Áö¸é ¹«Á¶°Ç ¿¡µðÅÍÀÇ ÅØ½ºÆ® ÀÎÇ²À» ÃÊ±âÈ­ ÇÑ´Ù.

	  editor.initTextInput();

	  // ÇöÀç ÀÛ¾÷ÇÒ µµÅ¥¸ÕÆ®ÀÇ Ä³¸´ ¿¤¸®¸ÕÆ®¸¦ µ¿±âÈ­ÇÑ´Ù.

	  if( currDoc != null ) {

	    currDoc.syncCaretStringElement( true );

	  }

      }

      // ³¡. ÀÌÀü ºí·ÎÅ· ¿µ¿ª ±ú²ýÇÏ°Ô º¸ÀÌ°Ô ÇÏ±â.

      // ¸¶¿ì½º ÀÌº¥Æ®¸¦ Ã³¸®ÇÒ ¸ñÀû ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ®¸¦ ¼³Á¤ÇÑ´Ù.
      // ¸¶¿ì½º¸¦ ´©¸¦ ¶§¸¸......

      setSelectedImageElement( target, e );

      // ¸¶¿ì½º¸¦ ´©¸¦ ¶§¸¸ ÀÛ¾÷ ¸ðµå¸¦ ¼³Á¤ÇÑ´Ù.
      // ±×¿Ü´Â ÀÛ¾÷À» ÇÑ´Ù. ¸ðµå¿¡ µû¶ó¼­.......

      mode.setMode( getMode( target, e ) );

      // ³¡. ÀÛ¾÷ ¸ðµå ¼³Á¤

      // ¸¶¿ì½º¸¦ ´©¸¦ ¶§ ¸ðµå ÀÛ¾÷¿¡ ÇÊ¿äÇÑ ¸¶¿ì½º ÅäÆú·ÎÁö¸¦ ¼³Á¤ÇÑ´Ù.
      // ¸¶¿ì½º¸¦ ´©¸¦ ¶§¸¸ »õ·Î ¼³Á¤ÇÑ´Ù.

      MOUSE_TOPOLOGY = getTopology( target, e );

      // ³¡. ¸¶¿ì½º ÅäÆú·ÎÁö ¼³Á¤

//      Utility.debug(this, "TARGET = " + target + ", MODE = " + mode.getMode() + ", MOUSE TOP = " + MOUSE_TOPOLOGY );

    }

    if( id != e.MOUSE_DRAGGED && id != e.MOUSE_RELEASED ) {

      setCursor( getTopology( target, e ), target, (HtmlEditorPane) e.getSource() );

    }

    // ¸¶¿ì½º µå·¡±ë½Ã ³¡´Ü¿¡¼­ ½ºÅ©·Ñ¸µ

    if( id == e.MOUSE_DRAGGED && mode.isMode( Mode.EDIT ) ) {

	final int x = e.getX();
	final int y = e.getY();

	final Rectangle visiRect = editor.getVisibleRect();

	final Dimension editorSize = editor.getSize();

	final int margin = visiRect.height/10;

	if(     ( y + margin > visiRect.y + visiRect.height )
	     && ( visiRect.y + visiRect.height < editorSize.height ) ) {

	  editor.scrollBy( 0, margin + 5 );

//	  return true; // ³¡´Ü¿¡¼­ ½ºÅ©·ÑÀÌ ³¡³ª¸é ¸¶¿ì½º ÀÌº¥Æ®ÀÇ Ã³¸®´Â Á¤»óÀûÀ¸·Î ÀÏ´Ü ³¡³½´Ù.

	} else if(     ( y - margin < visiRect.y )
		    && ( visiRect.y > margin ) ) {

	  editor.scrollBy( 0, - (margin + 5 ) );

	}

    }

    if( target instanceof HtmlDocument ) { // µµÅ¥¸ÕÆ® ¿¤¸®¸ÕÆ® ÀÏ °æ¿ì

       ((HtmlDocument) target).processDocumentMouseEvent( e );

    } else if( target != null ) { // ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ® ÀÏ °æ¿ì....

       // ¸¶¿ì½º¸¦ ´­·¶À» ¶§ ¼³Á¤µÈ ¸¶¿ì½º ÅäÆú·ÎÁö¿¡ ÀÇÇØ¼­ ÀÌ¹ÌÁö ¸¶¿ì½º ÀÌº¥Æ® Ã³¸®¸¦ ÇÑ´Ù.

       target.processImageMouseEvent( MOUSE_TOPOLOGY, e );

    }

    if( id == e.MOUSE_RELEASED ) {

      setCursor( getTopology( target, e ), target, (HtmlEditorPane) e.getSource() );

      // ¸¶¿ì½º°¡ ¶¼Áö¸é ´Ù½Ã ÀÛ¾÷ ¸ðµå¸¦ ¼³Á¤ÇÑ´Ù.
      // ¿¡µðÆÃ ¸ðµå·Î ÀüÈ¯ÇÒ °æ¿ì°¡ ´ëºÎºÐÀÌ´Ù.

      mode.setMode( getMode( target, e ) );

      // ¸¶¿ì½º°¡ ¶¼Áö¸é ¹«Á¶°Ç ¸¶¿ì½º ÅäÆú·ÎÁö¸¦ ÃÊ±â°ª(¿µ¿ª¹Ù±ù)À¸·Î ¼³Á¤ÇÑ´Ù.
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

       return true;                     // ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ® ÀÌº¥Æ® Ã³¸®°¡ ¿Ï·á µÇ¸é
					// µµÅ¥¸ÕÆ® ¿¤¸®¸ÕÆ® ÀÌº¥Æ® Ã³¸®´Â ÇÏÁö ¾Ê´Â´Ù.

    }

					// »óÀ§·Î ³Ñ±æ ¸¶¿ì½º ÅäÆú·ÎÁö´Â ¸¶¿ì½º¸¦ ´©¸¦ ¶§ ¼³Á¤ÇØÁØ...
					// ¸¶¿ì½º ÅäÆú·ÎÁö °ªÀÌ´Ù.
					// ÀÛ¾÷ ¼öÇà½Ã¿¡(¸¶À¸½º¸¦ µå·¡±ë ÇÒ ¶§....)
					// ¿¡µðÅÍ¿¡¼­ ÇÊ¿äÇÑ ¸¶¿ì½º ÅäÆú·ÎÁöÀÇ °ªÀÌ º¯ÇÏÁö ¾Ê´Â´Ù.
					// ¹«Á¶°Ç ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ® ¹æ½ÄÀÇ ¸¶¿ì½º Ã³¸®¸¦ ÇØÁØ´Ù.
					// ³ª¸ÓÁö µµÅ¥¸ÕÆ®¿¡ Á¾¼ÓÀûÀÎ ÀÌº¥Æ® Ã³¸®¸¸ ¾Æ·¡¿¡¼­ ÇØÁØ´Ù.
					// ¿¹¸¦ µé¸é.... ¹®¼­ÀÇ ¼±ÅÃ ÀÎµ¦½º ¼³Á¤ÀÌ¶óµç Áö....
					// ¿öµå ¹Ú½ºÀÇ »ðÀÔÀÌ¶óµç Áö.....
					// ÆË¾÷ ¸Þ´º °¨Ãß±â¶ó µç Áö.......

//    Utility.debug( this, "PROCESS DOCUMENT MOUSE EVENT ....." );

    final int id = e.getID();

    final int x = e.getX();
    final int y = e.getY();

    // ¿¡µðÅÍ º¯¼ö...... ¸¶¿ì½º ÀÌº¥Æ®ÀÇ ¼Ò½º ÄÄÆ÷³ÍÆ®´Â ¹Ýµå½Ã HtmlEditorPane ÀÌ¾î¾ß ÇÑ´Ù.
    final HtmlEditorPane editor = (HtmlEditorPane) e.getSource();

    // ¿À¸¥ ÂÊ ¸¶¿ì½º ´­·¯ Áá´Â °¡ ÆÇº°ÇÏ´Â º¯¼ö
    final boolean isRightButton = Utility.isRightMouseButton( e );

    if( isAddWordBoxMode() && id == e.MOUSE_RELEASED ) {

       // ¿öµå ¹Ú½º ¸ðµå¿¡¼­´Â ¸¶¿ì½º°¡ ¸±¸®Áî µÇ´Â °æ¿ì¸¸ ÀÛ¾÷ÇÑ´Ù.

	final HtmlDocument wordBox = new HtmlDocument( this  );

	// ¿öµå ¹Ú½º ¿µ¿ªÀº Àý´ë ÁÂÇ¥¸¦ ±âÁØÀ¸·ÎÇÑ µµÇüÀÌ ³Ñ¾î¿Â´Ù.
	// Àý´ë ±âÁØÀÇ µå·¡±ë ½¦ÀÙÀ¸·Î ºÎÅÍ ¿öµå ¹Ú½º ¿µ¿ªÀ» ±¸ÇÑ´Ù.

	final Rectangle2D wordBoxArea = this.getDraggedShape( MOUSE_TOPOLOGY, LAST_MOUSE_EVENT, e ).getBounds2D();

	// Ãß°¡ ÇÒ ¿öµå ¹Ú½ºÀÇ Å©±â¸¦ ¸ñÀû µµÅ¥¸ÕÆ®ÀÇ »ó´ë À§Ä¡·Î Àâ¾ÆÁØ´Ù.

	/**@todo ¿öµå ¹Ú½º »ðÀÔ½Ã ÁÂÇ¥ º¸Á¤
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

	mode.setMode( Mode.EDIT ); // ¿öµå ¹Ú½º Ãß°¡°¡ ³¡³ª¸é ¿¡µðÅÍ¸¦ ¿¡µðÆÃ ¸ðµå·Î ÀüÈ¯ ½ÃÅ²´Ù.

	return true; // ¿öµå ¹Ú½º¸¦ Ãß°¡ ÇßÀ¸¸é ¸¶¿ì½º ÀÌº¥Æ® Ã³¸®¸¦ ±×³É ³¡³½´Ù.

    } else if( mode.isMode( Mode.ADD_TABLE ) && id == e.MOUSE_RELEASED ) {

       // ¿öµå ¹Ú½º ¿µ¿ªÀº Àý´ë ÁÂÇ¥¸¦ ±âÁØÀ¸·ÎÇÑ µµÇüÀÌ ³Ñ¾î¿Â´Ù.
	// Àý´ë ±âÁØÀÇ µå·¡±ë ½¦ÀÙÀ¸·Î ºÎÅÍ ¿öµå ¹Ú½º ¿µ¿ªÀ» ±¸ÇÑ´Ù.

	final Rectangle2D tableArea = this.getDraggedShape( MOUSE_TOPOLOGY, LAST_MOUSE_EVENT, e ).getBounds2D();

	// Ãß°¡ ÇÒ ¿öµå ¹Ú½ºÀÇ Å©±â¸¦ ¸ñÀû µµÅ¥¸ÕÆ®ÀÇ »ó´ë À§Ä¡·Î Àâ¾ÆÁØ´Ù.

	final Point2D absLoc = this.getAbsoluteLocation();

	 // Å×ÀÌºí Ãß°¡ ¸ðµå¿¡¼­´Â ¸¶¿ì½º°¡ ¸±¸®Áî µÇ´Â °æ¿ì¸¸ ÀÛ¾÷ÇÑ´Ù.

	tableArea.setRect(

			  tableArea.getX() - absLoc.getX(),
			  tableArea.getY() - absLoc.getY(),
			  tableArea.getWidth(),
			  tableArea.getHeight()

			);

	final int [] rowCol = this.getInitialRowCol( tableArea );

	final TableDocument table = new TableDocument( this, rowCol[0], rowCol[1], tableArea  );

	this.addImageElement( table );

	mode.setMode( Mode.EDIT ); // ¿öµå ¹Ú½º Ãß°¡°¡ ³¡³ª¸é ¿¡µðÅÍ¸¦ ¿¡µðÆÃ ¸ðµå·Î ÀüÈ¯ ½ÃÅ²´Ù.

	return true; // ¿öµå ¹Ú½º¸¦ Ãß°¡ ÇßÀ¸¸é ¸¶¿ì½º ÀÌº¥Æ® Ã³¸®¸¦ ±×³É ³¡³½´Ù.

    } else if( isAddWordBoxMode() || mode.isMode( Mode.ADD_TABLE ) ) {

	// ¿öµå ¹Ú½º ¸ðµå¿¡¼­´Â ¸¶¿ì½º°¡ ¸±¸®Áî µÇ´Â °æ¿ì¸¸ ÀÛ¾÷ÇÑ´Ù.
	// ±×·¡¼­, ¸¶¿ì½º°¡ ´­·¯Áö´Â °æ¿ì°¡ ¾Æ´Ï¸é ±×³É....±×³É...¾Æ¹« ¾×¼Çµµ ÃëÇÏÁö ¾Ê´Â´Ù....

	return true;

    } else if( id == e.MOUSE_PRESSED && isRightButton ) {

	  // ¿À¸¥ ÂÊ ¸¶¿ì½º¸¦ ´©¸£¸é ÆË¾÷ ¸Þ´º°¡ ³ªÅ¸³­´Ù. Â¯ÇÏ°í...¸ÚÀÖ°Ô...

	 editor.initTextInput();

	 editor.showEditPopupMenu( e );

	 return true;

    } else if( id == e.MOUSE_PRESSED ) {

	 // ¿ÞÂÊ ¸¶¿ì½º°¡ ´­·¯Áö´Â °æ¿ì¿¡´Â ÅØ½ºÆ® ÀÎÇ²À» ÃÊ±âÈ­ÇÏ°í,
	 // ¿¡µðÆÃ ÆË¾÷ ¸Þ´º°¡ ³ªÅ¸³ª ÀÖÀ¸¸é, ÀÚµ¿À¸·Î ¼û±ä´Ù.

	 // ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ®¿¡¼­ ÇØÁØ´Ù. ±×·¯³ª ¿©±â¼­ ¶Ç ÇÑ ¹ø È®½ÇÈ÷.....

	 editor.initTextInput();

	 // ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ®¿¡¼­ ÇØÁØ´Ù. ±×·¯³ª ¿©±â¼­ ¶Ç ÇÑ ¹ø È®½ÇÈ÷.....
	 // ¿ä ÇÔ¼ö´Â ¿¡µðÆÃ ÆË¾÷ ¸Þ´º°¡ ³ªÅ¸³ª ÀÖ´Â Áö¸¦ °¨º°ÇÑ´ÙÀ½, ³ªÅ¸³ª ÀÖÀ» °æ¿ì¿¡¸¸
	 // ¿¡µðÆÃ ÆË¾÷ ¸Þ´º¸¦ °¨Ãá´Ù. ½Ã¾ß¿¡¼­...

	 editor.hideEditPopupMenu();

    }

    if( editor.isEditPopupMenuShowing() ) { // ¿¡µðÆÃ ÆË¾÷ ¸Þ´º°¡ ³ªÅ¸³ª ÀÖÀ» °Ü¿ì¿¡´Â ¾Æ¹« ÀÏµµ ÇÏÁö ¾Ê´Â´Ù.
					    // ±×³É... ÀÌº¥Æ® Ã³¸®¸¦ Á¾·áÇÑ´Ù.

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

	editor.repaint(); // ºí¶ôÅ·ÀÇ ½Ç½Ã°£ ·»´õ¸µÀ» À§ÇØ¼­

	return true;

    }

    return false;

  }

  public final static int getTableCellTopology( final HtmlDocument cellDoc, final MouseEvent e ) {

      final Point2D absParLoc = cellDoc.getAbsoluteParentLocation();

      final double x = e.getX() - absParLoc.getX();
      final double y = e.getY() - absParLoc.getY();

      // Å×ÀÌºí ¼¿ÀÏ °æ¿ì¿¡´Â OUT_OF_AREA ³ª
      // INSIDE_AREA °ªÀÇ À§»ó¸¸ °¡Áø´Ù.

      final Rectangle2D area = cellDoc.getArea();

      final double margin = 3;

      final double ax = area.getX();
      final double ay = area.getY();
      final double aw = area.getWidth();
      final double ah = area.getHeight();

      final Rectangle2D cellArea = new Rectangle2D.Double( ax, ay, aw, ah + 3 );
      // ¼¿ ¿¡¾î¸®¾î´Â ÇÏ´Ü ÆøÀº ¸¶Áø ¸¸Å­ ´õÇØ¼­ Àâ´Â´Ù.
      // ±×·¡¾ß¸¸ TOP_BOUNDARY ÅäÆú·ÎÁö¸¦ ¾ø¾Ø´Ù.

      if( ! cellArea.contains( x, y ) ) {
	// ¸¶¿ì½º ÁÂÇ¥°¡ ¼¿ ¿¡¾î¸®¾î ³»ºÎ¿¡ µé¾î¿ÀÁö ¾ÊÀ¸¸é, ¿µ¿ª¹Û ÅäÆú·ÎÁö¸¦ ¹ÝÈ¯ÇÑ´Ù.

	return OUT_OF_AREA;

      }

      // ¸¶¿ì½º ÁÂÇ¥°¡ ¿µ¿ª¾ÈÀ¸·Î µé¾î¿ÔÀ½ÀÌ È®ÀÎ µÇ¾úÀ¸¹Ç·Î...
      // ¾Æ·¡ ÄÚµå ºÎÅÍ´Â ¸¶¿ì½º ÁÂÇ¥°¡ ¿µ¿ª¾È¿¡ µé¾î¿Í ÀÖ´Â Áö´Â
      // Ã¼Å©ÇÏÁö ¾Ê´Â´Ù.

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

	  // ÃÖ»óÀ§ µµÅ¥¸ÕÆ®ÀÌ¸é ¹«Á¶°Ç ¸¶¿ì½º ÅäÆú·ÎÁö¸¦ 9 (³»ºÎ¿¡ ÀÖÀ½À» ÀÇ¹Ì)¸¦ ¹ÝÈ¯ÇÑ´Ù.

	  return INSIDE_AREA;

      } else if( doc != null && ie.getDocumentBoundary().contains( x, y ) ) { // ¹Ù¿î´õ¸®¿¡ ÀÖÀ¸¸é....

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

	} else if( isAddWordBoxMode() || mode.isMode( Mode.ADD_TABLE) ) { // ¿öµå ¹Ú½º³ª Å×ÀÌºí Ãß°¡ ¸ðµå ÀÏ °æ¿ì´Â ¹«Á¶°Ç ÇöÀç ¸ðµå(¿öµå ¹Ú½º ¸ðµå)¸¦ ¹ÝÈ¯ÇÑ´Ù.

	      return mode.getMode(); // ÇöÀç ¸ðµå ¹ÝÈ¯

	} else if( ( target instanceof HtmlDocument ) && id == e.MOUSE_RELEASED && top == INSIDE_AREA ) {

	    // ¿öµå ¹Ú½º ³»ºÎ °æ°è¿¡¼­ ¸¶¿ì½º°¡ ¶¼¾î Áö´Â °æ¿ì´Â ¿¡µðÆÃ ¸ðµå°¡ ¼³Á¤µÈ´Ù.

	    return Mode.EDIT;

	} else if( id == e.MOUSE_PRESSED || id == e.MOUSE_RELEASED ) { // ¸¶¿ì½º°¡ ´­·¯Áö°Å³ª ¶¼¾îÁö´Â °æ¿ì¿¡¸¸ ´Ù¾çÇÑ ¸ðµå¸¦ ¹ÝÈ¯ÇÑ´Ù.

	    if( target instanceof HtmlDocument && top == INSIDE_AREA ) {

		return Mode.EDIT;

	    } else if( top > -1 && top <= INSIDE_AREA ) {

		return Mode.RESHAPE;

	    }

	    return Mode.EDIT;

	} else { // ¸¶¿ì½º°¡ Å¬¸¯µÇ´Â ÀÌ ¿ÜÀÇ °æ¿ì´Â ¹«Á¶°Ç ÇöÀç ¸ðµå¸¦ ¹ÝÈ¯ÇÑ´Ù.

	    return mode.getMode() ; // ÇöÀç ¸ðµå¸¦ ¸®ÅÏÇÑ´Ù.

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


	  case ON_THE_BOUNDARY: // ¹Ù¿î´õ¸®¿¡ ÀÖÀ¸¸é....

		 ct = Cursor.MOVE_CURSOR;

		 break;

	  case INSIDE_AREA : // ³»ºÎ¿¡ ÀÖÀ¸¸é.....

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

	    // ¼±ÅÃ µÇÁö ¾ÊÀº »óÅÂ¿¡¼­´Â ¼Õ Ä¿¼­¸¦ ¼³Á¤ÇÑ´Ù.

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
      // ¼±ÅÃµÈ °´Ã¼°¡ ¼ø¼ö ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ®ÀÌ°í,
      // ÄÁÆ®·Ñ Å°°¡ ´­·¯Áö¸é...... ¾Æ¹öÁö µµÅ¥¸ÕÆ®¿¡¼­ Å° ÀÌº¥Æ®¸¦ Ã³¸®ÇÏ¿©
      // Ä«ÇÇ ÆäÀÌ½ºÆ® ÀÛ¾÷À» ÇÑ´Ù.

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

	    // ¹æÇâÅ°°¡ ´­·¯ÁöÁö ¾ÊÀ¸¸é ......
	    // ¹æÇâÅ° È÷½ºÅä¸® Á¤º¸¸¦ ÃÊ±âÈ­ ÇÑ´Ù.
	    this.rowCaretX = -1;

      }

//      Utility.debug(this, "KEY = " + e.getKeyChar() );

      final boolean ctrlDown = e.isControlDown();
      final boolean shiftDown = e.isShiftDown();

      final boolean isEmptyText = editor.getPreText().equals( "" );

      if( key == e.VK_ENTER || key == e.VK_SPACE ) { // ¿£ÅÍ Å°³ª ½ºÆäÀÌ½º Å°

	 editor.initTextInput();

	 this.syncCaretStringElement( true );

	 final String text = "" + ((char) key);

	 this.processText( text );

	 this.syncCaretStringElement( true );

      } else if( key == e.VK_TAB && AppRegistry.TAB_KEY_PROCESSING ) {

	// ÅÇ Å°°¡ µé¾î ¿Ã ¶§

	 // ÅÇ ¹®ÀÚ Ã³¸® ¿©ºÎ.....

	 editor.initTextInput();

	 this.syncCaretStringElement( true );

	 final String text = "" + ((char) key);

	 this.processText( text );

	 this.syncCaretStringElement( true );

      } else if( key == e.VK_LEFT ) {

	 // ¿Þ ÂÊ È­»ìÇ¥ Å°°¡ µé¾î ¿Ã ¶§......

	  editor.initTextInput();

	  this.synchIndex();

	  this.moveCaretBackward();

      } else if( key == e.VK_RIGHT ) {

	  // ¿À¸¥ ÂÊ È­»ìÇ¥ Å°°¡ µé¾î ¿Ã ¶§......

	  editor.initTextInput();

	  this.synchIndex();

	  this.moveCaretForward();

      } else if( key == e.VK_UP ) {

	  // À­ ÂÊ È­»ìÇ¥ Å°°¡ µé¾î ¿Ã ¶§......

	  editor.initTextInput();

	  this.synchIndex();

	  this.goUp( e );

      } else if( key == e.VK_DOWN ) {

	  // ¾Æ·§ ÂÊ È­»ìÇ¥ Å°°¡ µé¾î ¿Ã ¶§.......

	  editor.initTextInput();

	  this.synchIndex();

	  this.goDown( e );

      } else if( key == e.VK_PAGE_UP ) {

	  // ÆäÀÌÁö ¾÷ Å°°¡ µé¾î ¿Ã ¶§.......

	  editor.initTextInput();

	  this.synchIndex();

	  this.goPageUp( e );

      }  else if( key == e.VK_PAGE_DOWN ) {

	  // ÆäÀÌÁö ´Ù¿î Å°°¡ µé¾î ¿Ã ¶§......

	  editor.initTextInput();

	  this.synchIndex();

	  this.goPageDown( e );

      } else if( (key == 'A' || key == 'a' ) && ctrlDown ) {  // select all processing

	  // Ctrl-A ¸¦ ´©¸¦ ¶§.......

	  this.selectAll();

      } else if( ctrlDown && ( key == 'C' || key == 'c' ) ) {

	  // Ctrl-C ¸¦ ´©¸¦ ¶§.......

	  editor.initTextInput();

	  editor.showEditPopupMenu( e );

//	  copyToBuffer();

      } else if( ctrlDown && ( key == 'X' || key == 'x' ) ) {

	  // Ctrl-X ¸¦ ´©¸¦ ¶§.......

	  editor.initTextInput();

	  editor.showEditPopupMenu( e );

//	  cutToBuffer();

      } else if( ctrlDown && ( key == 'V' || key == 'v' ) ) {

	  // Ctrl-V ¸¦ ´©¸¦ ¶§......

	  editor.initTextInput();

	  editor.showEditPopupMenu( e );

//	  pasteFromBuffer();

      } else if( key == e.VK_HOME || key == e.VK_END ) {

	  // È¨ Å°³ª ¿£µå Å°¸¦ ´©¸¦ ¶§.......

	  editor.initTextInput();

	  this.syncCaretStringElement( true );

	  if( key == e.VK_HOME ) {

	    this.goToHome( e );

	  } else {

	    this.goToEnd( e );

	  }

      } else if( key == e.VK_BACK_SPACE ) {

	// ¹é ½ºÆäÀÌ½º Å°¸¦ ´©¸¦ ¶§.......

	if( isEmptyText ) {

	  this.deletePrevious();

	  this.requestNewHtmlDocView();

	}

      } else if( key == e.VK_DELETE ) {

	// µô¸´Æ® Å°¸¦ ´©¸¦ ¶§.......

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

	    // ¹æÇâ Å°¿Í ÆäÀÌÁö ¾÷ ´Ù¿î Å°°¡ ´­·Á ÁøÈÄ,
	    // ÇöÀç ÆùÆ® Á¤º¸¸¦ UI »ó¿¡ Ç¥½ÃÇÑ´Ù.

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

      // Ã¹ ¹ø Â° °ªÀº ÇöÀçÇàÀÇ Ã¹ ¹ø Â° ÀÎµ¦½º, µÎ ¹øÂ° °ªÀº ÇöÀç ÇàÀÇ ±æÀÌ, ¼¼ ¹ø Â°´Â ÁÙ¿¡ ´ëÇÑ Ä³¸´ ÀÎµ¦½ºÀÌ´Ù.

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

    // µµÅ¥¸ÕÆ®ÀÇ ½ÃÀÛ ÀÎµ¦½º ¼³Á¤½Ã¿¡´Â ³¡ ÀÎµ¦½ºµµ µ¿±âÈ­ ½ÃÅ²´Ù.

    this.setStartIndex( index );
    this.setEndIndex( index );

  }

  private void setEndIndex(final int x, final int y, final MouseEvent e) {

       final int refIndex = this.refIndex;

       final StringElementAndMouseIndex seNmi = this.getIndex( x, y, e ); // string element and mouse index

       // µµÅ¥¸ÕÆ®ÀÇ ³¡ ÀÎµ¦½º ¼³Á¤ ½Ã¿¡´Â ±âÁØ ÀÎµ¦½º¿Í ºñ±³ÇÏ¿©....
       // ÀÛÀº °ªÀ» ½ÃÀÛ ÀÎµ¦½º·Î..... Å« °ªÀ» ³¡ ÀÎµ¦½º·Î ¼³Á¤ÇÑ´Ù.
       // ±âÁØ ÀÎµ¦½º´Â ½ÃÀÛ ÀÎµ¦½º ¼³Á¤½Ã¿¡ ½ÃÀÛ ÀÎµ¦½º¿Í °°Àº °ªÀ¸·Î ÀâÈù´Ù.
       // ÀÌ·¸°Ô ÇÔÀ¸·Î¼­ ¸¶¿ì½º¸¦ ¿ª¹æÇâÀ¸·Î ²ø ¶§µµ ÀÎµ¦½ºÀÇ ½ÃÀÛ °ª°ú ³¡ °ªÀÇ
       // ³í¸® ¹«°á¼ºÀÌ È®º¸µÈ´Ù.

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

    // µµÅ¥¸ÕÆ®ÀÇ À§Â÷ °ª ¸¸Å­ x, y °ªÀ» º¸Á¤ÇÑ´Ù.
    x -= (int) ( absLoc.getX() );
    y -= (int) ( absLoc.getY() );

    final HtmlDocView docView = this.getHtmlDocView();

    return docView.getIndex( x, y, e );

  }

  final public boolean contains(final ImageElement ie) {

    return this.imageElements.contains( ie );

  }

  /**
   * ¸Ç ¾ÕÀ¸·Î °¡Á®¿À±â, ¸Ç µÚ·Î º¸³»±â,
   * ¾ÕÀ¸·Î °¡Á®¿À±â, µÚ·Î º¸³»±â ÇÔ¼öÀÌ´Ù.
   */
   // i = Integer.MIN_VALUE : ¸ÇµÚ·Î º¸³»±â
   // i = Integer.MAX_VALUE : ¸Ç ¾ÕÀ¸·Î °¡Á®¿À±â
   // i = -1 : µÚ·Î º¸³»±â
   // i = 1 : ¾ÕÀ¸·Î º¸³»±â

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

    if( index > 0 ) { // ¾ÕÀ¸·Î º¸³»±â

	if( refIndex == size -1 ) {

	  // Do nothing ! ÀÎµ¦½º¸¦ ¼Õ ´î ÇÊ¿ä°¡ ¾øÀ¸¹Ç·Î, ¿Ö³Ä¸é ¸Ç ¾Õ¿¡ °¡ ÀÖÀ¸¹Ç·Î....

	  return;

	}

	imageElements.remove( target );

	// ·çÇÁ¸¦ ¸¶Á÷¸· ¿¤¸®¸ÕÆ®¸¦ Á¦¿ÜÇÑ °÷ ±îÁö ¾ÆÀÌÅÍ·¹ÀÌ¼Ç ÇÑ´Ù.
	// Áï ÃÖÁ¾ ¾ÆÀÌÅÍ·¹ÀÌ¼ÇÀÎ size-1 ÀÌ´Ù.
	// ¸¶Áö¸·¿¡ ³Ö¾î¾ß ÇÒ °æ¿ì¿¡ Á» ·çÆ¾ÀÌ º¹ÀâÇØ¼­....
	// ¾ÆÀÌÅÍ·¹ÀÌ¼ÇÀ» size -1 ±îÁö ÇÑ ´ÙÀ½¿¡....
	// Áß°£¿¡ ¸¸Á·ÇÏ´Â ÀÎµ¦½º¸¦ ¸¸³ª¸é.....
	// ÀÎµ¦½º ¼³Á¤À» ÇÑ ´ÙÀ½ ÇÔ¼ö¸¦ Á¾·áÇÑ´Ù.

	for(int i = refIndex; i < size - 1 ; i ++ ) {

	   final ImageElement ie = (ImageElement) imageElements.get( i );

	   if( refStyle == ie.getStyle() ) {

	       imageElements.add( i + 1, target );

	       return; // ¸¸Á·ÇÑ ÀÎµ¦½º¿¡ ¼³Á¤ÇßÀ¸¹Ç·Î, ÀÛ¾÷ ¼öÇàÀ» Á¾·áÇÑ´Ù.

	   }

	}

	// Áß°£¿¡ ¸¸Á·ÇÏ´Â ÀÎµ¦½º¸¦ ¸¸³ªÁö ¸øÇÞÀ¸¸é...
	// ¹«Á¶°Ç ¸¶Áö¸· ÀÎµ¦½º¿¡ Áý¾î ³Ö´Â´Ù.

	imageElements.addLast( target );

    } else {

	if( refIndex == 0 ) {

	  // Do nothing ! ÀÎµ¦½º¸¦ ¼Õ ´î ÇÊ¿ä°¡ ¾øÀ¸¹Ç·Î, ¿Ö³Ä¸é ¸Ç µÚ¿¡ ÀÖÀ¸¹Ç·Î.....

	  return;

	}

	imageElements.remove( target );

	// ¾ÆÀÌÅ×·¹ÀÌ¼ÇÀ» 1 ±îÁö¸¸ ¼öÇàÇÑ´Ù.
	// Áß°£¿¡ ¸¸Á·ÇÏ´Â ÀÎµ¦½º¸¦ ¸¸³ª¸é....
	// ÀÎµ¦½º ¼³Á¤À» ÇÑ´ÙÀ½ ÇÔ¼ö¸¦ Á¾·áÇÑ´Ù.

	for(int i = refIndex -1 ; i > 0 ; i -- ) {

	   final ImageElement ie = (ImageElement) imageElements.get( i );

	   if( refStyle == ie.getStyle() ) {

	       imageElements.add( i , target );

	       return; // ¸¸Á·ÇÏ´Â ÀÎµ¦½º¿¡ ¼³Á¤À» ÇßÀ¸¹Ç·Î, ÇÔ¼ö¸¦ Á¾·áÇÑ´Ù.

	   }

	}

	// ±×·¸Áö ¸øÇÏ¸é....
	// ¹«Á¶°Ç ¸Ç Ã³À½¿¡ ³Ö¾îÁØ´Ù.

	imageElements.addFirst( target );

    }

  }

  final public HtmlDocView getHtmlDocView() {

    if( this.docView == null ) {

      this.createView();

    }

    return this.docView;

  }

  // µµÅ¥¸ÕÆ®ÀÇ ¿µ¿ªÀº ºäÀÇ ¿µ¿ª°ú µ¿±âÈ­ÇÑ´Ù.
  // ±×·¡¾ß¸¸..... ÀÚµ¿ °³ÇàÀÌ µÇ¾î Áø´Ù.

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

    // 1 ¸¸Å­ ¿©À¯ ÀÖ°Ô º¸Á¤

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

  // »ç¿ëÀÚ°¡ ¸¶¿ì½º·Î ²ø¾î¼­ ¼³Á¤ÇÑ Å©±âÀÌ´Ù.
  // ÃÖ¼Ò Å©±â·Î ¼³Á¤µÈ´Ù.

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

    // ½ºÆ®¸µ ¿¤¸®¸ÕÆ® ÅØ½ºÆ® ÇÕÄ¡±â

    while( it.hasNext() ) {

      final StringElement se = (StringElement) it.next();

      buffer.append( se.getText() + " " );

    }

    // ³¡. ½ºÆ®¸µ ¿¤¸®¸ÕÆ® ÅØ½ºÆ® ÇÕÄ¡±â

    // ¿öµå ¹Ú½º ÅØ½ºÆ® ÇÕÄ¡±â

    it = this.imageElements.iterator();

    while( it.hasNext() ) {

	 final Object obj = it.next();

	 if( obj instanceof HtmlDocument ) {

	     ((HtmlDocument) obj).getOnlyTexts( buffer );

	 }

    }

    // ³¡. ¿öµå ¹Ú½º ÅØ½ºÆ® ÇÕÄ¡±â

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

      // ´Ù Áß ¼± ÅÃ ½Ã ÄÚ µå
      // Ä¿Æ® ÇÑ ÈÄ¿¡ ¼±ÅÃµÈ °´Ã¼´Â ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ®¿¡¼­ »èÁ¦ÇÔ.

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

	 if( lastIndex < startIndex ) { // ºí·Ï¿¡¼­ ÁÂÃø¿¡ ÀÖÀ» ¶§...

	     continue;

	 } else if( endIndex <= firstIndex ) { // ¿£µå ÀÎµ¦½º´Â ½ÇÁ¦ Æ÷ÇÔ¿µ¿ª¿¡´Â µé¾î°¡Áö ¾ÊÀ¸¹Ç·Î
					       // °°°Å³ª Å¬¶§ÀÇ °æ¿ì....
					       // ºí·Ï¿¡¼­ ¿ìÃø¿¡ ÀÖÀ» ¶§.....

	     continue;

	 } else if( firstIndex <= startIndex && endIndex <= lastIndex ) {

	     // ¿µ¿ªÀ» Æ÷ÇÔÇÒ °æ¿ì....
	     // ½ºÅ¸Æ® ÀÎµ¦½º´Â ¿µ¿ª¿¡ µé¾î°¡¹Ç·Î ¯˜°Å³ª ÀÛÀ» ¶§ÀÌ´Ù.
	     // ¿£µå ÀÎµ¦½º´Â ¿µ¿ª¿¡ ¾È µé¾î°¡¹Ç·Î ¶ó½ºÆ® ÀÎµ¦½º¿Í °°À» °æ¿ì´Â
	     // Æ÷ÇÔÇÏ´Â °æ¿ìÀÌ´Ù.

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

	     // ¿ÏÀüÈ÷ Æ÷ÇÔ µÇ´Â °æ¿ì...

	     final StringElement seClone = (StringElement) se.clone( cloneDoc );

	     seClone.synchIndex( -1, false );

	     copiedStringElements.add( seClone );

	     continue;

	 } else if( firstIndex <= startIndex && startIndex <= lastIndex && lastIndex < endIndex ) {

	     // ÁÂÃø¿¡¼­ °ãÄ¥ °æ¿ì...
	     // ½ºÅ¸Æ® ÀÎµ¦½º´Â ¿µ¿ª¿¡ µé¾î°¡¹Ç·Î ¯˜°Å³ª ÀÛÀ» ¶§ÀÌ´Ù.
	     // ¿£µå ÀÎµ¦½º´Â ¿µ¿ª¿¡ ¾È µé¾î°¡¹Ç·Î ÀÛÀ» ¶§ÀÌ´Ù.

	     final String seText = se.getText();

	     final int fromIndex = startIndex - firstIndex;

	     final String text = seText.substring( fromIndex );

	     final StringElement seClone = (StringElement) se.clone( cloneDoc );

	     seClone.setText( text );

	     seClone.synchIndex( -1, false );

	     copiedStringElements.add( seClone );

	     continue;

	 } else if( firstIndex < endIndex && endIndex < lastIndex ) {

	     // ¿ìÃø¿¡¼­ °ãÄ¥ °æ¿ì...

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

	    imageElements.addLast( srcClone ); // ¸¶Áö¸·¿¡ Ãß°¡ÇØ¾ß z index °¡ ³í¸®Àû ÀÏ°ü¼ºÀ» À¯ÁöÇÑ´Ù.

	 }

	 final Rectangle2D area = srcClone.getArea();

	 srcClone.setArea( area.getX() + 10*(pasteNumX + 1), area.getY() + 10*(pasteNumY + 1), area.getWidth(), area.getHeight() );

	 if( srcClone instanceof HtmlDocument ) {

	    // µµÅ¥¸ÕÆ® Ãß°¡ÈÄ¿¡ ¼±ÅÃµÈ °Í Ã³·³ º¸ÀÌµµ·Ï ÇÏ±â À§ÇØ¼­
	    // µµÅ¥¸ÕÆ®ÀÇ ºÙ¿© ³Ö±â ÈÄ¿¡´Â µµÅ¥¸ÕÆ®¸¦ ¾àÇÑ ¼±ÅÃµÈ °´Ã¼·Î ¼³Á¤ÇÑ´Ù.

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
	// ¼ø¼ö ÀÌ¹ÌÁö °´Ã¼¸¦ ¹ß°ßÇÏ¿© ÇÏÀÌÆÛ¸µÅ©¸¦ Àû¿ëÇÏ¿´À¸¸é,
	// ÅØ½ºÆ®¿¡´Â ÇÏÀÌÆÛ ¸µÅ©¸¦ Àû¿ëÇÏÁö ¾Ê°í,
	// ÇÔ¼ö¸¦ Á¾·áÇÑ´Ù.

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

      // ¸µÅ© »èÁ¦ Ã¼Å© ¹Ú½º°¡ ¼±ÅÃµÇ¸é ³Î °ªÀ» ¸®ÅÏÇÑ´Ù.

      if( removeLinkCB.isSelected() ) {

	return new String [] { "", "" };

      }

      // ³¡. ¸µÅ© »èÁ§ Ã¼Å© ¹Ú½º ¼±ÅÃ ¿©ºÎ Á¶»ç.

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

	this.endIndex -= 1; // ÀÏ ¸¸Å­ ÀÎµ¦½º¸¦ °¨¼Ò ½ÃÅ²´Ù. ÇÏÀÌÆÛ ¸µÅ© ¼³Á¤½Ã´Â ¸¶Áö¸·
			    // ¹®ÀÚ°¡ °³Çà ¹®ÀÚÀÌ¸é ÀÌ¸¦ Á¦¿Ü ½ÃÅ²´Ù.

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

     // ÀÌ¹ÌÁö°¡ Ãß°¡ µÇ¸é ÀÚµ¿À¸·Î ¼±ÅÃµÈ °´Ã¼·Î ÀÎ½ÄÇÑ´Ù.
     // ¸¶¿ì½º ÀÌº¥Æ®¸¦ ³Î·Î ³Ñ±â¸é ¹«Á¶°Ç ¼±ÅÃµÈ °´Ã¼·Î ¼³Á¤µÈ´Ù.
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

  // ÀÌ ÇÔ¼ö´Â View¸¦ »ý¼º ÇÒ ¶§¸¸ »ç¿ëÇÑ´Ù.
  // ´Ù¸¥ ÇÔ¼ö¿¡¼­´Â »ç¿ëÇÏ¸é ¾È µÈ´Ù.

  public double getDocumentWidth() {

      // ¹«ÇÑ ·çÇÁ ºüÁö´Â °ÍÀ» ¹æÁöÇÏ±â À§ÇØ¼­ ¹Ýµå½Ã ½´ÆÛ getSize() ÇÔ¼ö¸¦ »ç¿ëÇÑ´Ù.
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

     // ÃÖ»óÀ§ µµÅ¥¸ÕÆ®ÀÇ ºä°¡ Àç »ý¼ºµÇ¸é ¿¡µðÅÍ¿¡°Ô ¹ë¸®µ¥ÀÌ¼ÇÀ» ¿ä±¸ÇÑ´Ù.
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

    // ¼±ÅÃµÈ ¿µ¿ªÀÌ ÀÖÀ¸¸é ¸ÕÀú Áö¿î´Ù.

    this.rowCaretX = -1; // ¹æÇâÅ° È÷½ºÅä¸® Á¤º¸¸¦ -1 °ªÀ¸·Î ¼³Á¤ÇÏ¿© ¹æÇâÅ° È÷½ºÅä¸®¸¦ »èÁ¦ÇÑ´Ù.

    mode.setMode( Mode.EDIT ); // ÆíÁý¿ë ÅØ½ºÆ®°¡ ÀÔ·ÂµÇ¸é ÀÚµ¿À¸·Î ¿¡µðÆÃ ¸ðµå·Î ÀüÈ¯µÈ´Ù.

    int si = this.startIndex;
    int ei = this.endIndex;

    if( si < ei ) {

	this.deleteSelectedCharacters();

    }

    // ³¡. ¼±ÅÃµÈ ¿µ¿ª Áö¿ì±â

    // ÅÇ ¹®ÀÚ¸¦ ½ºÆäÀÌ½º ¹®ÀÚ ¸î °³·Î Ä¡È¯ÇÑ´Ù.

    int tabIndex = text.indexOf( tab );

    String tabStr = AppRegistry.TAB_STRING;

    while( tabIndex > -1 ) {

       int textLen = text.length();

       text = text.substring( 0, tabIndex ) + tabStr
	      + ( tabIndex < textLen - 1 ? text.substring( tabIndex + 1 ) : "" ) ;

       tabIndex = text.indexOf( tab );

    }

    // ³¡. ÅÇ ¹®ÀÚ¸¦ ½ºÆäÀÌ½º ¹®ÀÚ ¸î °³·Î Ä¡È¯ ÇÏ±â.

    // ÀÔ·ÂµÈ ¹®ÀÚ¿­À» Ã³¸®ÇÑ´Ù.

    StringElement se = this.caretElement;

    se.processText( text );

    this.requestNewHtmlDocView();

    // ³¡. ÀÔ·ÂµÈ ¹®ÀÚ¿­ Ã³¸®.

    // ¹º°¡°¡ ¿¡µðÅÍ µÇ¾ú´Ù´Â °ÍÀ» ¾Ë¸°´Ù.
    // ±×·¡¼­ ÇÁ·Î±×·¥À» Á¾·áÇÏ°Å³ª »õ ¹®¼­¸¦ ¿­¶§ ÀúÀåÇÒ °ÇÁö ¸» °ÇÁö¸¦ ¹°¾îº¸°Ô ÇÑ´Ù.

    HtmlFreeEditorPane.setHasSavedAsFile( false );

    // ³¡. ¿¡µðÆÃ µÈ °Í ¾Ë¸®±â.

  }

  public void requestNewHtmlDocView() {

    this.docView = null;

    if( ! this.requestNewHtmlDocument ) {

      return;

    }

    if( this.parentDoc != null ) { // ÀÏ¹Ý ¿öµå ¹Ú½ºÀÌ¸é »óÀ§ µµÅ¥¸ÕÆ®¿¡°Ô »õ ¹®¼­ »ý¼ºÀ» ¿ä±¸ÇÑ´Ù.

      this.parentDoc.requestNewHtmlDocView();

    } else { // ÃÖ»óÀ§ µµÅ¥¸ÕÆ® ÀÌ¸é ¹ë¸® µ¥ÀÌ¼ÇÀ» ¿ä±¸ÇÑ´Ù.

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

  // µµÅ¥¸ÕÆ®¿¡ »ó´ëÀûÀÎ Ä³¸´ ·ÎÄÉÀÌ¼ÇÀ» ¹ÝÈ¯ÇÑ´Ù.
  final private Point getRelativeCaretLocation() {

    // Ä¿¼­ °ªÀº Ç×»ó »ó´ëÀûÀ¸·Î ÀâÈù´Ù. Àý´ë ÁÂÇ¥ °ªÀÌ ¾Æ´Ï´Ù.

     return new Point(  this.cursorRect.x, this.cursorRect.y );

  }

  final public void setCursorRect( final Rectangle cursorRect ) {

      this.cursorRect = cursorRect;

  }

  // ÃÖ»óÀ§ µµÅ¥¸ÕÆ®¸¦ ±âÁØÀ¸·Î Àý´ë ÁÂÇ¥ °ªÀÇ Ä¿¼­ µµÇüÀ» ¸®ÅÏÇÑ´Ù.

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

      // µµÅ¥¸ÕÆ® ¿µ¿ª Å¬·Î´×
      doc.setArea( (Rectangle2D) ( this.getArea().clone() )  );
      // ³¡. µµÅ¥¸ÕÆ® ¿µ¿ª Å¬·Î´×.

      final Insets scanMargin = parentDoc.getScanMargin();

      // Å¬·ÐµÈ µµÅ¥¸ÕÆ®ÀÇ ·ÎÄÉÀÌ¼Ç º¸Á¤
      // ¾Æ¹öÁö µµÅ¥¸ÕÆ®ÀÇ ½ºÄµ¸¶ÁøÀÇ µÎ¹è ¸¸Å­ x, y¸¦ Àâ¾Æ¼­...
      // ½ÇÁ¦ º¹»ç½Ã¿¡.....¸ÚÀÖ°Ô º¹»çµÇµµ·Ï ÇÑ´Ù.

      doc.setLocation( 2*scanMargin.left, 2*scanMargin.top );

      // ³¡. Å¬·Ð µµÅ¥¸ÕÆ® ·ÎÄÉÀÌ¼Ç º¸Á¤.

      // µµÅ¥¸ÕÆ® »ö»ó ÄÝ·Î´×

      doc.setFillColor( this.getFillColor() );
      doc.setBorderColor( this.getBorderColor() );
      doc.setBorderWidth( this.getBorderWidth() );

      // ³¡. µµÅ¥¸ÕÆ® ¼Ó¼º Å¬·Î´×

      // ½ºÆ®¸µ ¿¤¸®¸ÕÆ® Å¬·Ð´×

      final LinkedList ses = this.stringElements;

      final LinkedList sesClone = new LinkedList();

      Iterator it = ses.iterator();

      while( it.hasNext() ) {

	StringElement se = (StringElement) it.next();

	sesClone.add( se.clone( doc ) );

      }

      doc.stringElements = sesClone;

      // ³¡. ½ºÆ®¸µ ¿¤¸®¸ÕÆ® Å¬·Î´×

      // ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ® Å¬·Î´×

      final LinkedList ies = this.imageElements;

      final LinkedList iesClone = new LinkedList();

      it = ies.iterator();

      while( it.hasNext() ) {

	ImageElement ie = (ImageElement) it.next();

	iesClone.add( ie.clone( doc ) );

      }

      doc.imageElements = iesClone;

      // ³¡. ÀÌ¹ÌÁö ¿¤¸®¸ÕÆ® Å¬·Î´×

      // Å¬·ÐµÈ µµÅ¥¸ÕÆ® ÃÊ±âÈ­
      // ·¹ÀÌ¾î¿¡¼­ ÀÐ¾î µéÀÌ´Â ÇÔ¼ö¸¦ ÀÌ¿ëÇÑ´Ù.
      // Àü¿¡ ÇØ ³õÀº ÀÏ¿¡ ´ëÇÑ °ª¾îÄ¡¸¦ ´À³¤´Ù.

      HtmlLayer.initHtmlDocumentCloned( doc );

      // ³¡. ÄÝ·ÐµÈ µµÅ¥¸ÕÆ® ÃÊ±âÈ­

      return doc;

  }

  public void addShapeElement( final ShapeElement shapeElement ) {

//      this.shapeElements.addLast( shapeElement );

      this.imageElements.addLast( shapeElement );

      this.requestNewHtmlDocView();

  }

  // docView ¸¦ °­Á¦ÀûÀ¸·Î ³Î °ªÀ¸·Î ¼³Á¤ÇÑ´Ù.
  // ÆÄÀÏÀ» ÀÐ¾î µéÀÎ ÈÄ, ÅØ½ºÆ®°¡ Á¦´ë·Î ÆäÀÎÆ® µÇÁö ¾Ê´Â ¹ö±×¸¦
  // ÇØ°áÇÏ±â À§ÇÏ¿© HtmlLayer¿¡¼­ Å×ÀÌºí°ú Å×ÀÌºí ¼¿ÀÇ docView¸¦
  // °­Á¦ÀûÀ¸·Î ³Î °ªÀ¸·Î ¼³Á¤ÇÑ ´ÙÀ½¿¡,
  // ½ÇÁ¦ÀûÀ¸·Î ÆäÀÎÆ® ½Ã¿¡ ºä¸¦ ´Ù½Ã »ý¼ºÇÏµµ·Ï ÇÑ´Ù.

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
