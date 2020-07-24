
/**
 * Title:        Test Publishing System<p>
 * Description:  Internet Based Test Publishing System V1.0<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      JCOSMOS DEVELOPMENT<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package htmleditor;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;
import jcosmos.*;

public class StringElement extends HtmlElement {

  private String text = "";

  private Font font;
  private Color color;
  private boolean underLine = false;

  private FontRenderContext frc;
  private LineMetrics lm;
  private GlyphVector gv;

  private int editIndex = -1;
  private int caretIndex = -1;

  private int scanIndex = -1; // scanning index for creating view

  public StringElement(HtmlDocument parentDoc) {

     this( parentDoc, "" );

  }

  public StringElement(HtmlDocument parentDoc, String text ) {

     this( parentDoc, text, parentDoc.getDefaultFont() );

  }

  public StringElement(HtmlDocument parentDoc, String text, Font font) {

     this( parentDoc, text, font, parentDoc.getTextColor(), false );

  }

  public StringElement(HtmlDocument parentDoc, String text, Font font, Color color, boolean underLine) {

     this( parentDoc, text, font ,color, underLine, null ) ;

  }

  public StringElement(HtmlDocument parentDoc, String text, Font font, Color color, boolean underLine, String href) {

     this.parentDoc = parentDoc;
     this.font = font;
     this.color = color;
     this.underLine = underLine;
     this.href = href;

     setText( text );

  }

  public Object clone(HtmlDocument parentDoc) {

     return new StringElement( parentDoc, text, font, color, underLine, href );

  }

  public boolean isUnderLine() {

     return underLine;

  }

  public boolean isSameAttribute(StringElement se) {

      if( ( this.font.equals( se.getFont() ) ) &&
	  ( this.color.getRGB() == se.getColor().getRGB() ) &&
	  ( isUnderLine() == se.isUnderLine() ) &&
	  ( this.href != null && this.href.equals( se.href ) ) ) {

	  return true;

      } else {

	 return false;

      }

  }

  public boolean hasCombined(StringElement se) {

      if( isSameAttribute( se ) ) {

	 StringElement ce = this.parentDoc.getCaretElement(); // caret element

	 if( ce == se ) {

	    int ei = se.getEditIndex();
	    int ci = se.getCaretIndex();

	    this.parentDoc.setCaretElement( this );

	    int textLen = this.text.length();

	    ei += textLen;
	    ci += textLen;

	    this.setCaretIndex( ci );
	    this.setEditIndex( ei );

	 }

	 this.text += se.getText();

	 this.setGlyphInformation();

	 return true;

      } else {

	 return false;

      }

  }

  public boolean getUnderLine() {

     return this.underLine;

  }

  public void setUnderLine(boolean underLine) {

     this.underLine = underLine;

  }

  public Color getColor() {

     return this.color;

  }

  public void setColor(Color color) {

     this.color = color;

  }

  public int getScanIndex() {

     return this.scanIndex;

  }

  public void setScanIndex(int index) {
     this.scanIndex = index;
  }

  public Font getFont() {
      return this.font;
  }

  public HtmlDocument getDocument() {
     return this.parentDoc;
  }

  public void applyFont(Font font) {
     if( this.font == font ) {    // when font applied is null
	return;
     }

     StringElement [] ses = getAppliedStringElements();
     if( ses[1] != null ) {
	ses[1].setFont( font );
     }
  }

  public void applyHref(String href, String target) {

     Utility.debug(this, "HREF = " + href + ", TARGET = " + target );

     if( this.href != null && this.href.equals( href )
	&& this.target != null && this.target.equals( target ) ) {
	return;
     }

     StringElement [] ses = getAppliedStringElements();

     if( ses[1] != null ) {
	ses[1].setHref( href, target );
     }

  }

  public void applyTextColor(Color color) {
     if( this.color == color ) {
	return;
     }

     StringElement [] ses = getAppliedStringElements();

     if( ses[1] != null ) {
	ses[1].setColor( color );
     }
  }

  public void applyUnderLine(boolean underLine) {
     if( this.underLine == underLine ) {
	return;
     }

     StringElement [] ses = getAppliedStringElements();

     if( ses[1] != null ) {
	ses[1].setUnderLine( underLine );
     }
  }

  public StringElement cut() {

     StringElement [] ses = getAppliedStringElements();

     if( ses[1] != null ) {

	this.parentDoc.addToCopiedHtmlElements( (StringElement) ( ses[1].clone( this.parentDoc ) ) );

	return ses[ 1 ];

     }

     return null;

  }

  public void applyBoldic(boolean boldic) {

     Font font = this.font;

     int type = boldic ? Font.BOLD : Font.PLAIN;

     boolean italic = font.isItalic();

     if( italic ) {
	type = type | Font.ITALIC;
     }

     font = font.deriveFont( type );

//     font = FontManager.getFont( font.getFamily(), type, font.getSize() );

     StringElement [] ses = getAppliedStringElements();

     if( ses[1] != null ) {

	ses[1].setFont( font );

     }

  }

  public void applyItalic(boolean italic) {
     Font font = this.font;

     int type = italic ? Font.ITALIC : Font.PLAIN;

     boolean boldic = font.isBold();

     if( boldic ) {
	type = type | Font.BOLD;
     }

     font = font.deriveFont( type );

     StringElement [] ses = getAppliedStringElements();

     if( ses[1] != null ) {
	ses[1].setFont( font );
     }
  }

  public StringElement [] getAppliedStringElements() {

     StringElement [] ses = { null, null, null };

     HtmlDocument parentDoc = this.parentDoc;  // html document
     LinkedList seList = parentDoc.getStringElements();   // string elements list

     int lsi = this.getFirstIndex();  // local first index
     int lei = this.getLastIndex(); // local end index

     int si = parentDoc.getStartIndex(); // document start index
     int ei = parentDoc.getEndIndex(); // document end index

     if( si == ei ) {

	return ses;

     }

     ei -- ; // does not include the last character into the selected area

     if( ei < lsi || lei < si ) {

	return ses;

     }

     String text = this.text;

     int textLen = text.length();

     int i = 0, j = textLen - 1 ;   // first and second index

     // check first division index

     if( si < lsi ) {

	i = 0;  // same as do nothing

     } else {

	i = si - lsi;

     }

     // end of checing first division index

     // check secon division index

     if( ei > lei ) {

	// Do nothing

     } else {

	j -= ( lei - ei );

     }

     if( j < 0 ) {

       j = 0;

     }

     // end of checking second division index

     String div [] = { null , null, null };

     div[0] = text.substring(0, i);

     if( j < textLen -1 ) {

	div[1] = text.substring( i, j + 1 );
	div[2] = text.substring( j + 1 );

     } else {

	div[1] = text.substring( i );
	div[2] = null;

     }

//     Utility.debug(this, "0= [" + div[0] + "], 1= [" + div[1] + "], 2 = [" + div[2] + "]" );

     // end of checking second division index

     String href = this.href;

     if( div[0].length() > 0 ) {

	StringElement se = new StringElement( parentDoc, div[0], this.font, this.color, this.underLine, href );
	int index = seList.indexOf( this );
	seList.add( index, se );

     }

     if( true ) {   // Do whenever

	StringElement se = new StringElement( parentDoc, div[1], this.font, this.color, this.underLine, href );

	int index = seList.indexOf( this );
	seList.add( index, se );

	int syi = div[1].length(); // synch index
	syi = (syi < 0 ) ? 0 : syi;
	parentDoc.setCaretElement( se );
	se.synchIndex( syi, false );

	ses[1] = se;

     }

     if( div[2] != null && div[2].length() > 0 ) {

	StringElement se = new StringElement( parentDoc, div[2], this.font, this.color, this.underLine, href );
	int index = seList.indexOf( this );
	seList.add( index, se );

     }

     seList.remove( this );

     return ses;

  }

  public void divideCaretStringElement() {

     HtmlDocument parentDoc = this.parentDoc;  // html document

     LinkedList seList = parentDoc.getStringElements();   // string elements list

     int lsi = this.getFirstIndex();  // local first index
     int lei = this.getLastIndex(); // local end index

     String text = this.text;

     int textLen = text.length();

     int caretIndex = this.caretIndex;

     if( textLen == 0 ) {

	return ;

     } else  if( caretIndex < 0 || caretIndex > textLen -1 ) {

	return ;

     }

     String preText = text.substring( 0, caretIndex );
     String postText = text.substring( caretIndex );

     StringElement preSE = new StringElement( parentDoc, preText, this.font, this.color, this.underLine, this.href );
     StringElement postSE = new StringElement( parentDoc, postText, this.font, this.color, this.underLine, this.href );

     int index = seList.indexOf( this );

     Utility.debug(this, "INDEX = " + index );

     if( index < 0 ) {

	index = 0;

     }

     // 뒤에 것 먼저 넣기
     seList.add( index, postSE );
     // 끝. 뒤에 것 먼저 넣기

     // 앞에 것 나중에 넣기
     seList.add( index, preSE );
     // 끝. 앞에 것 나중에 넣기

     postSE.synchIndex( 0, false );
     parentDoc.setCaretElement( postSE );

     seList.remove( this );

     return ;

  }

  public void moveCaretForward() {
     int ci = this.caretIndex;  // caret index

     int li = this.text.length() -1; // last index

     if( ci == li ) {

	StringElement nse = this.parentDoc.getNextElement( this );  // next string element

	while( nse != null ) {

	   int textLen = nse.getText().length();

	   if( textLen > 0 ) {

	      nse.synchIndex( 0, true );

	      this.parentDoc.setCaretElement( nse );

	      nse = null;

	   } else {

	      StringElement delse = nse; // deleting string element

	      nse = this.parentDoc.getNextElement( nse );

	      Utility.debug(this, "Encountered zero length string element! Deleting it now! " );

	      this.parentDoc.getStringElements().remove( delse );
	   }
	}

     } else {
	ci ++;

	this.synchIndex( ci, true );

     }
  }

  public void moveCaretBackward() {

     int ci = this.caretIndex;

     if( ci == 0 ) {

       StringElement pse = this.parentDoc.getPreviousElement( this ); // previous string element

       while( pse != null ) {

	   int textLen = pse.getText().length(); // previous text length;

	   if( textLen > 0 ) {

	      pse.synchIndex( textLen -1, true );

	      this.parentDoc.setCaretElement( pse );

	      pse = null;

	   } else {

	      StringElement delse = pse;

	      pse = this.parentDoc.getPreviousElement( pse );

	      Utility.debug(this, "Encountered zero length string element! Deleting it now! " );

	      this.parentDoc.getStringElements().remove( delse );
	   }

       }

     } else {

       ci --;

       this.synchIndex( ci, true );

     }
  }

  public void deletePrevious() {
     int ci = this.caretIndex;

     if( ci == 0 ) {

	StringElement pse = this.parentDoc.getPreviousElement( this );   // previous string element

	while( pse != null ) {

	    String pseText = pse.getText();

	    int textLen = pseText.length(); // previous text length;

	    if( textLen > 0 ) {
		pse.setText( pseText.substring(0, textLen -1 ) );

		pse = null;
	    } else {
		StringElement delse = pse;

		pse = this.parentDoc.getPreviousElement( pse );

		Utility.debug(this, "Encountered zero length string element! Deleting it now! " );

		this.parentDoc.getStringElements().remove( delse );
	    }

	}

     } else {

	 String text = this.text;

	 int tl = text.length(); // text length

	 String pre = text.substring(0, ci - 1 );

	 String post = text.substring( ci );

	 this.text = pre + post;

	 this.setGlyphInformation();

	 this.synchIndex( ci -1, true );
     }
  }

  public void deleteNext() {

     int ci = this.caretIndex;

     String text = this.text;

     String pre = text.substring(0, ci);

     String post = "";

     int textLen = text.length();

     StringElement nse = this.parentDoc.getNextElement( this );

     if( nse == null && ci == textLen - 1 ) {

	post = "\n";

     } else if( ci < textLen - 1 ) {

	post = text.substring( ci + 1 );

     }

     this.text = pre + post;

     if( post.length() == 0 ) {

	int nci = ci - 1; // new caret index

	nci = ( nci < 0 ) ? 0 : nci;

	this.synchIndex( nci, true );

     } else {

	this.synchIndex( ci , true );

     }

     this.setGlyphInformation();

     if( this.text.length() == 0 ) {

	nse = this.parentDoc.getNextElement( this );   // next string element

	if( nse != null ) {

	    nse.synchIndex(0, true );

	    this.parentDoc.setCaretElement( nse );

	} else {

	    StringElement pse = this.parentDoc.getPreviousElement( this );   // previous string element

	    if( pse != null ) {

		pse.synchIndex( pse.getText().length() -1, true );

		this.parentDoc.setCaretElement( pse );

	    } else {

		this.processText( "\n" );

	    }

	}

     } else if( ci == textLen - 1 ) {

	nse = this.parentDoc.getNextElement( this );   // next string element

	while( nse != null ) {

	    int nseTextLen = nse.getText().length(); // next text length;

	    if( nseTextLen > 0 ) {

		nse.synchIndex( 0, true );

		this.parentDoc.setCaretElement( nse );

		nse = null;

	    } else {

		StringElement delse = nse;

		nse = this.parentDoc.getPreviousElement( nse );

		Utility.debug(this, "Encountered zero length string element! Deleting it now! " );

		this.parentDoc.getStringElements().remove( delse );

	    }

	}

     }

  }

  public int getFirstIndex() {

     StringElement pe = this.parentDoc.getPreviousElement( this );   // previous string element

     int textLen = this.text.length(); // current index

     if( pe != null ) {

	return pe.getLastIndex() + ((textLen > 0 ) ? 1 : 0);

     } else {

	return 0;

     }

  }

  public int getLastIndex() {

     StringElement se = this.parentDoc.getPreviousElement( this );

     int textLen = this.text.length(); // current index

     if( se != null ) {

	return se.getLastIndex() + textLen;

     } else {

	if( textLen > 0 ) {
	  textLen --;
	}

	return textLen;

     }

  }

  protected Graphics2D getGraphics2D() {

      return (Graphics2D) ( getParentDocument().getDocumentEditor().getGraphics() );

  }

  public void setGlyphInformation() {

    String text = this.text;

    Font font = this.font;

    Graphics2D g2 = getGraphics2D();

    g2.setFont( font );

    FontRenderContext frc = g2.getFontRenderContext();

    GlyphVector gv = font.createGlyphVector( frc, text );

    this.frc = frc;
    this.gv = gv;
    this.lm = font.getLineMetrics(text, frc);

  }

  public int getMinWidth() {

    if( gv == null ) {

      setGlyphInformation();

    }

    int min = AppRegistry.MIN_GLYPH_WIDTH ;

    final GlyphVector gv = this.gv;

    final int glyphNum = gv.getNumGlyphs();

    for(int i = 0; i < glyphNum; i ++ ) {

      Rectangle gb = gv.getGlyphLogicalBounds( i ).getBounds();

      int gw = gb.width + 1; // 여유잇게 2 만큼 보정

      min = ( gw > min ) ? gw : min;

    }

    return min;

  }

  public Vector createViews(Point scanPoint, Insets margin ) {

     Vector views = new Vector();

     RowView rowView = null;
     HtmlView strView = null;

     // 이 부분에서는 도큐먼트의 크기를 가져오기 위해서는
     // 반드시 getDocumentWidth() 를 사용한다.
     // 무한 루프 빠지는 것을 방지 하기 위해서 이다.

     double docW = this.parentDoc.getDocumentWidth() - margin.right;

     if( this.frc == null ) {

	setGlyphInformation();

	if( this.frc == null ) {

	    Utility.debug( this, "CANCELED CREATION STRING VIEW" );

	   return new Vector();

	}

     }

     FontRenderContext frc = this.frc;
     GlyphVector gv = this.gv;
     LineMetrics lm = this.lm;

     String text = this.text;
     int gi = 0; // glyph index
     int textLen = text.length(); // text length

     double scanX = ( scanPoint.x > margin.left ) ? scanPoint.x : margin.left,
	    scanY = ( scanPoint.y > margin.top ) ? scanPoint.y : margin.top ;

     int startIndex = 0;   // start index

     String c;

     int si = this.scanIndex; // scan index

     if( si > -1 ) {

	gi = si + 1;
	startIndex = gi;

     }

     final double minGw = docW - margin.left - margin.right; // minimun glyph width

     double gw, gh = lm.getHeight();

     while( gi < textLen ) {

//	 Utility.debug( this, "GI = " + gi + ", TL = " + textLen );

	 boolean newRow = false; // detect creation of new row view

	 c = "" + text.charAt( gi );

	 if( gi < textLen - 1 ) {

	    Point2D gp = gv.getGlyphPosition( gi );

	    Point2D ngp = gv.getGlyphPosition( gi + 1 );

	    gw = ngp.getX() - gp.getX();

	    Rectangle2D gb = gv.getGlyphLogicalBounds( gi ).getBounds2D();

	 } else  {

	    Rectangle2D gb = gv.getGlyphLogicalBounds( gi ).getBounds2D();

	    gw = gb.getWidth();

	 }

	 if( gw == 0 ) {
	    Utility.debug(this, "GW is zero!" );
	 }

	 if( gh == 0 ) {
	    Utility.debug(this, "GH is zero!" );
	 }

	 Rectangle2D gr = new Rectangle2D.Double( scanX, scanY, gw*2, gh ); // glyph rectangle

	 double preScanX = scanX;

	 scanX += gw;

	 Rectangle2D oir = parentDoc.getRectangleIntersects( gr ); // overlapped image rectangle

	 if( oir != null ) {

//	    Utility.debug(this, "encountered overlapped image." );

	    strView = new StringView( this, text.substring( startIndex, gi), startIndex );

	    startIndex = gi;

	    if( rowView == null ) {
	       rowView = new RowView () ;
	       views.addElement( rowView );
	    }

	    rowView.addElement( strView );

	    HtmlView addedView = rowView;

	    strView = null;

	    scanX = oir.getX() + oir.getWidth() + gw;

	    double w = scanX - preScanX,
		   h = gh; // empty string view width and height

	    if( scanX >= docW ) {

		w = docW - preScanX;

		scanX = margin.left;

		scanY += (addedView.getArea().getHeight() + 1);

		newRow = true;

	    }

	    strView = new EmptyStringView( w, h );

	 } else if( c.equals( "\n" ) || c.equals( "\r\n" ) ) {

//	    Utility.debug(this, "NEW CARRIAGE RETURN" );

	    strView = new StringView( this, text.substring( startIndex, gi) + c, startIndex );

	    gi ++;

	    startIndex = gi;
	    scanX = margin.left;

	    double sh = strView.getArea().getHeight() + 1;
	    double rh = rowView != null ? rowView.getArea().getHeight() + 1 : 0 ;

	    scanY += ( sh > rh ) ? sh : rh;

	    newRow = true;

	 } else if( gw >= minGw ) {

//	    Utility.debug(this, "SCANX IS LARGER THAN DOCW" );

	    gi ++ ;

	    strView = new StringView( this, text.substring( startIndex, gi), startIndex );
	    startIndex = gi;

	    scanX = margin.left;

	    double sh = strView.getArea().getHeight() + 1;
	    double rh = rowView != null ? rowView.getArea().getHeight() + 1 : 0 ;

	    scanY += ( sh > rh ) ? sh : rh;

	    newRow = true;

	 } else if( scanX >= docW ) {

//	    Utility.debug(this, "SCANX IS LARGER THAN DOCW" );

	    strView = new StringView( this, text.substring( startIndex, gi), startIndex );
	    startIndex = gi;

	    scanX = margin.left;

	    double sh = strView.getArea().getHeight() + 1;
	    double rh = rowView != null ? rowView.getArea().getHeight() + 1 : 0 ;

	    scanY += ( sh > rh ) ? sh : rh;

	    newRow = true;

	 } else if( gi == textLen -1 ) {

//	    Utility.debug(this, "GI == TEXTLEN - 1" );

	    strView = new StringView( this, text.substring( startIndex ), startIndex );

	    gi ++;

	 } else {

//	    Utility.debug(this, "GI++" );

	    gi ++;

	 }

	 if( strView != null ) {

	     if( rowView == null ) {

		rowView = new RowView();
		views.addElement( rowView );

	     }

	     rowView.addElement( strView );

	     strView = null;

	     if( newRow ) {
		rowView = null;
		newRow = false;
	     }

	 }

     }

     // scanning next StringElement
     StringElement ce = this; // current stringElement

     while( ce != null ) {

	 StringElement nse = this.parentDoc.getNextElement( ce );

	 if( nse == null ) {
	      ce = null;
	      break;
	 }

	 int ngi = 0;

	 GlyphVector ngv = nse.getGlyphVector();
	 LineMetrics nlm = nse.getLineMetrics();

	 String nText = nse.getText();
	 int nTextLen = nText.length();

	 startIndex = 0;

	 int nsi = nse.getScanIndex(); // next string element's scan index

	 if( nsi > -1 ) {
	    ngi = nsi + 1;
	    startIndex = ngi;
	 }

	 double ngw, ngh;

	 if( nlm != null ) {

	   ngh = nlm.getHeight();

	 } else {

	   FontMetrics fm = getGraphics2D().getFontMetrics();
	   ngh = fm.getHeight();

	 }

	 while( ngi < nTextLen ) {

	    c = "" + nText.charAt( ngi );

	    if( ngi < nTextLen - 1 ) {

	       Point2D gp = ngv.getGlyphPosition(  ngi );

	       Point2D ngp = ngv.getGlyphPosition( ngi + 1 );

	       ngw = ngp.getX() - gp.getX();

	       Rectangle2D gb = ngv.getGlyphLogicalBounds( ngi ).getBounds2D();

	    } else  {
	       Rectangle2D gb = ngv.getGlyphLogicalBounds( ngi ).getBounds2D();

	       ngw = gb.getWidth();

	    }

	    if( ngw == 0 ) {
	       Utility.debug(this, "NGW is zero!" );
	    }

	    Rectangle2D ngr = new Rectangle2D.Double( scanX, scanY, ngw*2, ngh ); // next glyph rectangle

	    double preScanX = scanX;

	    scanX += ngw;

	    Rectangle2D noir = parentDoc.getRectangleIntersects( ngr ); // overlapped image rectangle

	    if( noir != null ) {

	       //Utility.debug(this, "encountered overlapped image." );
	       strView = new StringView( nse, nText.substring( startIndex, ngi), startIndex );
	       startIndex = ngi;

	       nse.setScanIndex( ngi - 1 );

	       if( rowView == null ) {
		  rowView = new RowView () ;
		  views.addElement( rowView );
	       }

	       rowView.addElement( strView );

	       HtmlView addedView = rowView;

	       strView = null;

//	       scanX += noir.getWidth() ;

	       scanX = noir.getX() + noir.getWidth() + ngw;

	       double w = scanX - preScanX,
		   h = ngh; // empty string view width and height

	       if( scanX >= docW ) {

		   w = docW - preScanX;
		   scanX = margin.left;
		   scanY += (addedView.getArea().getHeight() + 1);

		   nse = null;

		   ngi = nTextLen;

	       } else {

	       }

	       strView = new EmptyStringView( w, h );

	    } else if( c.equals( "\n" ) || c.equals( "\r\n" ) ) {

	       strView = new StringView( nse, nText.substring( startIndex, ngi) + c, startIndex );

	       nse.setScanIndex( ngi );

	       scanX = margin.left;

	       double sh = strView.getArea().getHeight() + 1;
	       double rh = rowView != null ? rowView.getArea().getHeight() + 1 : 0 ;

	       scanY += ( sh > rh ) ? sh : rh;

	       nse = null;
	       ngi = nTextLen;

	    } else if( ngw >= minGw ) {

	       ngi ++;

	       strView = new StringView( nse, nText.substring( startIndex, ngi), startIndex );

	       nse.setScanIndex( ngi - 1 );

	       scanX = margin.left;

	       double sh = strView.getArea().getHeight() + 1;
	       double rh = rowView != null ? rowView.getArea().getHeight() + 1 : 0 ;

	       scanY += ( sh > rh ) ? sh : rh;

	       nse = null;
	       ngi = nTextLen;

	    } else if( scanX >= docW ) {

	       strView = new StringView( nse, nText.substring( startIndex, ngi), startIndex );

	       nse.setScanIndex( ngi - 1 );

	       scanX = margin.left;

	       double sh = strView.getArea().getHeight() + 1;
	       double rh = rowView != null ? rowView.getArea().getHeight() + 1 : 0 ;

	       scanY += ( sh > rh ) ? sh : rh;

	       nse = null;
	       ngi = nTextLen;

	    } else if( ngi == nTextLen -1 ) {

	       strView = new StringView( nse, nText.substring( startIndex ), startIndex );

	       nse.setScanIndex( ngi );

	       ngi ++;

	    } else {

	       ngi ++;

	    }

	    if( strView != null ) {

	       if( rowView == null ) {

		  rowView = new RowView();
		  views.add( rowView );

	       }

	       rowView.addElement( strView );
	       strView = null;

	    }
	 }

	 ce = nse;
     }
     // End of scanning next string element

     scanPoint.x = (int) scanX; scanPoint.y = (int) scanY;   // for the following scanning

     return views;

  }

  public GlyphVector getGlyphVector() {
     if( this.gv == null ) {
	this.setGlyphInformation();
     }
     return this.gv;
  }

  public LineMetrics getLineMetrics() {
     if( this.gv == null ) {
	this.setGlyphInformation();
     }
     return this.lm;
  }

  public void setText(String text) {
     this.text = text;
     this.setGlyphInformation();
  }

  public String getText() {
      return this.text;
  }

  public void setFont(Font font) {
     if( this.font != font ) {
	this.font = font;
	this.setGlyphInformation();
     }
  }

  public void setCaretIndex(int index) {
     this.caretIndex = index;
  }

  public void setEditIndex(int index) {
     this.editIndex = index;
  }

  final public void synchIndex(final boolean synchSelection) {

     synchIndex( this.caretIndex, synchSelection );

  }

  final public void synchIndex(final int index, final boolean synchSelection) {

     // synchronize current string element editing indexes
     this.caretIndex = index;
     this.editIndex = index;
     // end of synchronizing editing indexes.

     if( index < 0 || (! synchSelection) ) {
	return;  // does not synchronize document selection indexes
     }

     // synchronize start and end index of documents
     final int si = this.getFirstIndex() + index ; // start index

     this.parentDoc.setStartIndex( si );
     this.parentDoc.setEndIndex( si );
     // end of synchronizing of selection indexes.

  }

  public int getCaretIndex() {
     return this.caretIndex;
  }

  public int getEditIndex() {
     return this.editIndex;
  }

  public void processText(String input ) {

     if( input.length() == 0 ) {
	 return;
     }

     int ei = this.editIndex; // edit index
     int ci = this.caretIndex; // caret index

     String text = this.text;

     int iLen = input.length();

     String pre = text.substring(0, ei);
     String post = text.substring( ci );

     text = pre + input + post;

     ci = ei + iLen;

     this.caretIndex = ci;
     this.text = text;

     this.setGlyphInformation();

//     Utility.debug(this, "ei = " + ei + " ci = " + ci);
  }

}