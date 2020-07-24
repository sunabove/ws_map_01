
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
import jcosmos.*;

public class StringView extends HtmlView implements HtmlComment {

//  private static final Shape NEW_LINE_SHAPE = createNewLineShape();

  private StringElement stringElement;

  private int refIndex = 0; // reference index to the string element

  private String text = "";
  private FontRenderContext frc;
  private LineMetrics lm;
  private GlyphVector gv;

  private Rectangle2D area = new Rectangle2D.Double(0, 0, 1, 1);

  private static Image nlImg = AppRegistry.NEW_LINE_IMG;

  public StringView(StringElement se, String text, int refIndex) {

     this.stringElement = se;
     this.refIndex = refIndex;

     setText( text );

  }

  public StringElement getStringElement() {

     return stringElement;

  }

  public int getStartIndex() {

     return this.stringElement.getFirstIndex() + refIndex;

  }

  public int getEndIndex() {

     int textLen = text.length();

     textLen = (textLen == 0 ) ? textLen : textLen - 1;

     return getStartIndex() + textLen;

  }

  public int getRefIndex() {

     return refIndex;

  }

  public Rectangle2D getArea() {
     return area;
  }

  public static String calibrateFontToKoreanCharacters(String text, Font font) {

     String fontName = font.getName().toUpperCase();

     String caliFontName = null; // calibrated font name to koread characters

     if( fontName.equalsIgnoreCase( "Serif" ) ) {

	 caliFontName = "바탕";

     } else if( fontName.equalsIgnoreCase( "Dialog" ) ){

	 caliFontName = "굴림";

     } else if( fontName.equalsIgnoreCase( "Monospaced" ) ) {

	 caliFontName = "굴림체";

     }

     if( caliFontName != null ) {

	String caliText = ""; // calibrated text

	boolean needCali = false; // need calibration

	for(int i = 0, len = text.length(); i < len; i ++ ) {

	    char c = text.charAt( i );

	    if( c > 127 ) {  // in case of korean characters

	       if( ! needCali ) {

		  caliText += "<font face = \"" + caliFontName + "\" >";

	       }

	       caliText += c;

	       needCali = true;

	    } else {  // in case of english characters

	       if( needCali ) {

		  caliText += "</font>";

	       }

	       caliText += c;

	       needCali = false;

	    }


	}

	if( needCali ) {

	   caliText += "</font>";

	}

	return caliText;

     }

     return text;

  }

  public String tag(final int zindex) {

     String text = this.text;

     String tag = toHtmlText( text );

     tag = calibrateFontToKoreanCharacters( tag, getFont() );

     tag = "<p style = \"" + getFontStyleTag() +  "\">" + tag + "</p>";

     tag = hrefTag( boldic(italic(underline( tag )) ) );

     Rectangle2D area = getArea();

     int x = (int) area.getX(), y = (int) area.getY();

     y += AppRegistry.IMAGE_HTML_VERICAL_CALIBRATION;

//     tag = "<div style=\"position:absolute; left:" + x + "px; top:" + y + "px; " +  "z-index:" + zindex + "\">" + tag + "</div>" ;

     int w = (int) area.getWidth() + 150, h = (int) area.getHeight();

     tag = "<div style=\"position:absolute; left:" + x + "px; top:" + y + "px; width:" + w + "px; height:" + h + "px; " +  "z-index:" + zindex + "\">" + tag + "</div>" ;

     tag = HtmlElement.comment( tag, this );

     return tag;

  }

  public static String toHtmlText(String text) {

     if( text.endsWith( "\n" ) ) {
	text = text.substring( 0, text.length() - 1 ) + nl;
     }

     // replace "<" as "&lt"
     int i = text.indexOf( '<' );

     while( i > -1 ) {
	String pre = text.substring(0, i);
	String post = "";
	if( i < text.length() - 1 ) {
	   post = text.substring( i + 1 );
	}

	text = pre + "&lt;" + post;

	i = text.indexOf( '<' );
     }

     // replace ">" as "&gt"
     i = text.indexOf( '>' );

     while( i > -1 ) {
	String pre = text.substring(0, i);
	String post = "";
	if( i < text.length() - 1 ) {
	   post = text.substring( i + 1 );
	}

	text = pre + "&gt;" + post;

	i = text.indexOf( '>' );
     }

     // replace " " as "&nbsp"

     i = text.indexOf( ' ' );

     while( i > -1 ) {
	String pre = text.substring(0, i);
	String post = "";
	if( i < text.length() - 1 ) {
	   post = text.substring( i + 1 );
	}

	text = pre + "&nbsp;" + post;

	i = text.indexOf( ' ' );
     }

     return text;

  }

  public static String fromHtmlText(String text) {

     // replace "&lt" as "<"
     int i = text.indexOf( "&lt;" );

     while( i > -1 ) {
	String pre = text.substring(0, i);
	String post = "";

	i += 4;

	if( i < text.length() - 1 ) {
	   post = text.substring( i );
	}

	text = pre + "<" + post;

	i = text.indexOf( "&lt;" );
     }

     // replace ">" as "&gt"
     i = text.indexOf( "&gt;" );

     while( i > -1 ) {
	String pre = text.substring(0, i);
	String post = "";

	i += 4;

	if( i < text.length() - 1 ) {
	   post = text.substring( i );
	}

	text = pre + ">" + post;

	i = text.indexOf( "&gt;" );
     }

     // replace " " as "&nbsp"

     i = text.indexOf( "&nbsp;" );

     while( i > -1 ) {

	text = text.substring( 0, i ) + " " + text.substring( i + 6 );

	i = text.indexOf( "&nbsp;" );

     }

     final String nl = CharacterUtility.nl;

     i = text.indexOf( nl );

     while( i > -1 ) {

      text = text.substring( 0, i ) + "\n" + text.substring( i + 2 );

      i = text.indexOf( nl );

     }

     return text;

  }

  private String italic(String tag) {
     Font font = getFont();
     if( font.isItalic() ) {
       return "<i>" + tag + "</i>";
     } else {
	return tag;
     }
  }

  private String boldic(String tag) {
     if( getFont().isBold() ) {
       return "<b>" + tag + "</b>";
     } else {
	return tag;
     }
  }

  private String underline(String tag) {
     if( this.stringElement.isUnderLine() ) {
	return "<u>" + tag + "</u>";
     } else {
	return tag;
     }
  }

  public String fontTag(String text ) {
     Font font = getFont();

     String ft = "<font"; // font tag
     ft += " face = " + sc + font.getName() + sc;
     ft += " size = " + sc + toHtmlSize( font.getSize() ) + sc;
     ft += " color = " + sc + toHtmlColor( this.stringElement.getColor() ) + sc;
     ft += " >";

     Utility.debug(this, "FONT = " + font );

     return ft + text + "</font>";
  }

  public String getFontStyleTag() {

     Font font = getFont();

     String fontName = FontManager.backwardFontName(font.getName());

     String ft = "font-family:" + fontName +"; "
	       + "font-size:" + font.getSize() +"; "
	       + "color:" + toHtmlColor( this.stringElement.getColor() ) + ";";

     return ft;

  }

  protected String hrefTag(String htmlTag ) {

     String href = this.stringElement.getHref();
     String target = this.stringElement.getTarget();

     return hrefTag( htmlTag, href, target );

  }

  public static String hrefTag(String htmlTag, String href, String target ) {

     if( href == null || href.trim().length() < 1 ) {

	return htmlTag;

     } else {

	String targetTag = "";

	if( target != null && target.trim().length() > 1 ) {

	  targetTag = "target = \"" + target + "\"";

	}

//        Utility.debug(this, "HREF " + href + " TARGET = " + targetTag);

	String tag = "<a href = \"" + href.trim() + "\" " + targetTag + ">" + htmlTag + "</a>";

//        Utility.debug(this, "SV TAG = " + tag);

	return tag;

     }

  }

  final public static String toHtmlColor(Color color) {

     final String [] rgb = {
	Integer.toHexString( color.getRed() ),
	Integer.toHexString( color.getGreen() ),
	Integer.toHexString( color.getBlue() )
     };

     for( int i = 0; i < 3; i ++ ) {

	if( rgb[i].length() == 1 ) {

	   rgb[i] = "0" + rgb[i];

	}

     }

     return "#" + rgb[0] + rgb[1] + rgb[2] ;

  }

  public static Color fromHtmlColor(String text) {

     text = text.substring( 1 ); // 맨 앞에 있는 #을 일단 짤라 낸다.

//     Utility.debug(StringView.class, "HTML COLOR TEXT = " + text );

     final String [] rgb = {
	text.substring(0, 2),
	text.substring(2, 4),
	text.substring(4)
     };

     final int r = parseHexaString( rgb[0] ),
	       g = parseHexaString( rgb[1] ),
	       b = parseHexaString( rgb[2] );

     return new Color( r, g, b );

  }

  final public static int parseHexaString(String hexa) {

     int value = 0;

     for(int i = 0, len = hexa.length(); i < len; i ++ ) {

	final char c = hexa.charAt( i );

	int digit = 0;

	switch ( c ) {
	   case '1':
	      digit = 1;
	      break;
	   case '2':
	      digit = 2;
	      break;
	   case '3':
	      digit = 3;
	      break;
	   case '4':
	      digit = 4;
	      break;
	   case '5':
	      digit = 5;
	      break;
	   case '6':
	      digit = 6;
	      break;
	   case '7':
	      digit = 7;
	      break;
	   case '8':
	      digit = 8;
	      break;
	   case '9':
	      digit = 9;
	      break;
	   case 'a':
	      digit = 10;
	      break;
	   case 'b':
	      digit = 11;
	      break;
	   case 'c':
	      digit = 12;
	      break;
	   case 'd':
	      digit = 13;
	      break;
	   case 'e':
	      digit = 14;
	      break;
	   case 'f':
	      digit = 15;
	      break;
	   case 'A':
	      digit = 10;
	      break;
	   case 'B':
	      digit = 11;
	      break;
	   case 'C':
	      digit = 12;
	      break;
	   case 'D':
	      digit = 13;
	      break;
	   case 'E':
	      digit = 14;
	      break;
	   case 'F':
	      digit = 15;
	      break;
	}

	value = value*16 + digit;

     }

     return value;
  }

  final public static int toHtmlSize(int size) {

     int hs = 1;  // html font size

     switch( size ) {
	case 8:
	    hs = 1;
	    break;
	case 10:
	    hs = 2;
	    break;
	case 12:
	    hs = 3;
	    break;
	case 14:
	    hs = 4;
	    break;
	case 18:
	    hs = 5;
	    break;
	case 24:
	    hs = 6;
	    break;
	case 36:
	    hs = 7;
	    break;
     }

     return hs;

  }

  final public static int fromHtmlSize(int size) {

     int hs = 1;  // html font size

     switch( size ) {
	case 1:
	    hs = 8;
	    break;
	case 2:
	    hs = 10;
	    break;
	case 3:
	    hs = 12;
	    break;
	case 4:
	    hs = 14;
	    break;
	case 5:
	    hs = 18;
	    break;
	case 6:
	    hs = 24;
	    break;
	case 7:
	    hs = 36;
	    break;
     }

     return hs;

  }

  final public int getIndex( int x ) {

     final Rectangle2D area = this.area;

     final double lx = area.getX(), w = area.getWidth() ;

     if( x <= lx + w ) {

	 int index = this.stringElement.getFirstIndex() + this.refIndex;

	 if( frc == null ) {

	     this.setGlyphInformation();

	 }

	 final GlyphVector gv = this.gv;

	 //Utility.debug(this, "get Index: first index = " + this.stringElement.getFirstIndex() + ", ref index = " + this.refIndex );

	 final int textLen = this.text.length();

	 Rectangle currBounds = null, nextBounds = null;

	 for(int i = 0; i < textLen ; i ++ ) {

	     if( nextBounds == null ) {

		currBounds = gv.getGlyphLogicalBounds( i ).getBounds();

	     } else {

		currBounds = nextBounds;

	     }

	     if( i < textLen - 1 ) {

		nextBounds = gv.getGlyphLogicalBounds( i + 1 ).getBounds();

	     } else {

		nextBounds = null;

	     }

	     double x0 = lx + currBounds.x, x1 = 0;

	     if( nextBounds != null ) {

		 x1 = lx + nextBounds.x;

	     } else {

		 x1 = lx + currBounds.x + currBounds.width;

	     }

	     if( x < x1 ) {

		if( x > (x0 + x1)/2.0 && i < textLen -1 ) {    // scanning index on the half-divided glyph width
		   i ++;
		}

		index += i;

		this.stringElement.synchIndex( this.refIndex + i, false );
		this.stringElement.getDocument().setCaretElement( this.stringElement );

		return index;

	     }

	 }

	 return index;

     }

     return -1;

  }

  private Graphics2D getGraphics2D() {

      return this.stringElement.getGraphics2D();
  }

  final public synchronized void setGlyphInformation() {

    final String text = this.text;

    final Font font = getFont();

    final Graphics2D g2 = getGraphics2D();

    g2.setFont( font );

    final FontRenderContext frc = g2.getFontRenderContext();

    final GlyphVector gv = font.createGlyphVector( frc, text );

    this.frc = frc;
    this.gv = gv;
    this.lm = font.getLineMetrics(text, frc);

  }

  final public void setText(final String text) {

     this.text = text;
     setSize();

  }

  final public String getText() {

      return this.text;

  }

  final private Font getFont() {

      return this.stringElement.getFont();

  }

  final public void setSize() {

     final Graphics2D g2 = getGraphics2D() ;
     setSize( g2 );

  }

  final public void setSize(final Graphics2D g2) {

    if( g2 == null ) { return ; }

    final String text = this.text;

    final Font font = getFont();

    final FontRenderContext frc = g2.getFontRenderContext();
    final GlyphVector gv = font.createGlyphVector( frc, text );

    final Rectangle2D bound = font.getStringBounds(text, frc);

    final double width = bound.getWidth(), height = bound.getHeight();

    final Rectangle2D area = this.area;

    area.setRect( area.getX(), area.getY(), width, height );

    this.frc = frc;
    this.gv = gv;
    this.lm = font.getLineMetrics(text, frc);

  }

  final public int getDescent() {

     if( this.lm == null ) {
	return 0;
     }

     return (int) this.lm.getDescent();

  }

  final public void paint(Graphics2D g2, int x, int y) {
       // Do nothing.
  }

  final public void paint(Graphics2D g2, int x, int y, int descent) { // x, y means bottom line

     // Utility.debug(this, "string view text = " + this.text );

     final Color pc = g2.getColor(); // previous color

     final Rectangle2D area = this.area;

     final LineMetrics lm = this.lm;

     final double h = lm.getAscent() + lm.getLeading() + descent;

     final double w = gv.getLogicalBounds().getWidth();

     // Setting view location
     area.setRect( x, y - h , w , h );
     // End of setting view location

     final boolean debugFlag = false;

     if( debugFlag ) {  // debug flag

	g2.setColor( Color.black );
	g2.draw( area );

     }

     final String text = this.text;

     if( text.length() == 0 ) {
//	Utility.debug(this, "Encountered zero-length text string view and skipped painting it!" );
	return;
     }

     // Drawing selection area

     final StringElement stringElement = this.stringElement;   // reference string element

     final HtmlDocument doc = stringElement.getDocument();  // html document

     final int si = doc.getStartIndex(); // document start index
     final int ei = doc.getEndIndex();   // document end index

     final int textLen = text.length();   // text length

     final int svfi = stringElement.getFirstIndex() + this.refIndex; // string view first index
     final int svli = svfi + textLen; // string view last index

     if( si > svli || ei < svfi ) {

	// Do nothing!

     } else {

	int lsi = 0;  // local start index
	int lei = 0; // local end index

	int df = si - svfi;   // difference of first indexes

	if( df < 0 ) {
	    // lsi = 0;
	} else {
	    lsi = df;
	}

	int dl = ei - svli;   // difference of last indexes

	if( dl > -1 ) {  // 도큐먼트 마지막 인덱스가 로컬 인덱스를 넘어 가면.

	    lei = textLen - 1;

	} else {    // 도큐먼트 마지막 인덱스가 로컬 인데스 내에 있을 때.

	    lei = textLen - 1 + dl;

	}

//	Utility.debug(this, "lsi = " + lsi + " lei = " + lei );

	boolean selected = true;

	if ( lsi < 0 ) {
	   lsi = 0 ;
	} else if( lsi > textLen - 1 ) {
	   selected = false;
	}

	if( selected && lei > textLen - 1 ) {

	    lei = textLen -1;

	} else if( selected && lei < 0 ) {

	    selected = false;

	}

	if( selected && this.frc == null ) {

	   this.setGlyphInformation();

	}

	if( selected && (lsi <= lei) ) {

	   GlyphVector gv = this.gv;

	   int sx0 = x + gv.getGlyphLogicalBounds( lsi ).getBounds().x;
	   Rectangle sx1Bounds = gv.getGlyphLogicalBounds( lei ).getBounds();
	   int sx1 = x + sx1Bounds.x + sx1Bounds.width;
	   int ah = (int)(area.getHeight());

	   g2.setColor( Color.green );

	   g2.fillRect( sx0, y - ah, sx1 - sx0 + 1, ah);

	}

     }

     // End of drawing area

     // Drawing text

     String drawingText = text;

     if( text.indexOf( '\n' ) > -1 ) {
	 drawingText = text.replace( '\n', ' ');
     }

     if( text.endsWith( nl ) ) {
	 drawingText = text.substring( 0, text.length() - 1 ) + ' ';
     }

     final int gx = x, gy = y - descent;

     // Setting Font

     final Font of = g2.getFont(); // original font

     final Font font = getFont();

//     Utility.debug(this, "SV FONT = " + font);

     if( of != font ) {
	 g2.setFont( font );
     }
     // Enf of settting font

     g2.setColor( stringElement.getColor() );

     g2.drawString( drawingText, gx, gy);

     if( of != font ) {
	 g2.setFont( of );
     }

     // End of drawing text

     // Drawing underline

     if( stringElement.getUnderLine() ) {

	 final GlyphVector gv = this.gv;

	 final int li = ( textLen - 1 < 0 )? 0 : ( textLen - 1 ); // last text index

//	 li = (li < 0 ) ? 0 : li;   // negative index occurs when text length is zeron.

	 final Rectangle lgb = gv.getGlyphLogicalBounds( li ).getBounds(); // last glyph bounds

	 final int ux0 = gx, uy0 = y, ux1 = ux0 + lgb.x + lgb.width, uy1 = uy0;   // under line position

	 g2.setColor( stringElement.getColor() );

	 g2.drawLine(  ux0, uy0, ux1, uy1 );

     }

     // End of drawing underline

     // draw hyper link under line

     final String href = stringElement.getHref();

     if( href != null && href.length() > 0 ) {

	 final GlyphVector gv = this.gv;

	 final int li = (textLen - 1 < 0 ) ? 0 : (textLen - 1) ; // last text index

	 final Rectangle lgb = gv.getGlyphLogicalBounds( li ).getBounds(); // last glyph bounds

	 final int ux0 = gx, uy0 = y + 2, ux1 = ux0 + lgb.x + lgb.width, uy1 = uy0;   // under line position

	 g2.setColor( AppRegistry.HTML_LINK_COLOR );

	 g2.drawLine(  ux0, uy0, ux1, uy1 );

     }

     // End of drawing hyper link under line

     // Drawing new line character graphically

     int nli = text.indexOf( '\n' );

     if( nli < 0 ) {

       nli = text.indexOf( nl );

     }

     if( nli > -1 ) {

	final GlyphVector gv = this.gv;

	final Point2D gp = gv.getGlyphPosition( nli );

	final int ix = (int)( x + gp.getX() );
	final int iy = (int)( gy - this.nlImg.getHeight( doc.getDocumentEditor() ) );

	g2.drawImage( this.nlImg, ix, iy, doc.getDocumentEditor() );

     }

     // End of drawing new line character graphically

     // Drawing caret

     final StringElement se = this.stringElement;

     final Color cc = doc.getCaretColor(); // caret color

     final int ci = se.getCaretIndex(); // caret index
     final int ri = this.refIndex; // reference index to the string element
     final int lci = ci - ri; // local caret index

     if( cc != null && ( -1 < lci && lci < textLen ) ) {

	 final GlyphVector gv = this.gv;
	 final Point2D gp = gv.getGlyphPosition( lci );

	 final int cx = (int)( x + gp.getX() -1 );   // caret x
	 final int cy = y;                      // caret y
	 final int cw = 2;                      // caret width
	 int ch = (int) area.getHeight();   // caret heigh

//	 g2.setColor( Utility.getComplemetaryColor( cc ) );

	 ch -= (ch/6); // caret height calibration

	 final Rectangle cursor = new Rectangle( cx, cy - ch - 1, cw, ch);

	 se.getDocument().setCursorRect( cursor );

//	 g2.fill( cursor );

     }

     // End of drawing caret

     g2.setColor( pc ); // restore color as previous color

  }

  final public String commentText() {

     return HtmlLayer.TEXT;

  }

}