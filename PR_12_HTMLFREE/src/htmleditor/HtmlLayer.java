package htmleditor;

/**
 * Title:        Test Publishing System
 * Description:  Internet Based Test Publishing System V1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import jcosmos.*;

public class HtmlLayer extends HtmlParser implements ModeInterface {

  private String type;
  private String style;
  private String attribute;

  public static final String TEXT = "T",
		     RECT_IMAGE = "R",
		     FRONT_TEXT = "F",
		     BACK_TEXT = "B",
		     WORD_BOX = "W",
		     TABLE = "A";

  private final Vector layers = new Vector();

  private File dir;

  public HtmlLayer(File file) throws FileNotFoundException, IOException{

      // read html codes from a file

      this.dir = file.getParentFile();

      String html = readFile( file );

//      Utility.debug(this, "HTML = " + html );

      // end of reading html codes from a file

      // extract body tag

      int bodyIdx = html.indexOf( "<body" );

      if( bodyIdx > -1 ) {
	html = html.substring( bodyIdx );
      }

      // end of extraction body tag

      // parse body tag

      parse( html );

      // end of parsing body tag

  }

  private HtmlLayer(File dir, String html) {

      this.dir = dir;
      parse( html );

  }

  private void parse(String html) {

     extractAttribute( extractStyle( extractType( html) ) );

  }

  final private String extractType(final String html) {

     final String type = readFromToFirst( html, "<!--", "-->" ).trim();

     this.type = type;

//     String from = "<!-- " + type + " -->" ;
//
//     String to = "<!-- END OF " + type + " -->" ;

     final String from = HtmlElement.startComment( type );

     final String to = HtmlElement.endComment( type );

//     Utility.debug(this, "TYPE = " + from );

     final String content = readFromToMatched( html, from, to );

//     Utility.debug(this, "CONTENT = " + content );

     return content;

  }

  final private String extractStyle(final String html) {

     final String styleHtml = readFromToFirst( html, "<div", ">" );

     this.style = styleHtml.trim();

//     Utility.debug(this, "STYLE = " + style );

     int i = html.indexOf( style );

     final String from = "<div";
     final String to = "</div>";

     final String content = readFromToMatched( html, from, to );

     i = content.indexOf( ">" );

     return content.substring( i + 1 );

  }

  final private void extractAttribute(String html) {

	html = html.trim();

//	Utility.debug(this, "Attribute = " + html );

	while( html != null && html.startsWith( "<!--" ) ) {

	   final String typeHtml = readFromToFirst( html, "<!--", "-->" ).trim();

	   final String from = HtmlElement.startComment( typeHtml );

	   final String to = HtmlElement.endComment( typeHtml );

//	   String from = "<!-- " + typeHtml + " -->";
//	   String to = "<!-- END OF " + typeHtml + " -->";

//	   Utility.debug( this, "TYPE HTML = " + typeHtml );

	   String text = readFromToMatched( html, from, to );

	   if( text == null || text.length() < 1 ) {
	      break;
	   }

	   text = from + text + to;

	   final HtmlLayer layer = new HtmlLayer( dir, text );

	   this.layers.addElement( layer );

	   final int idx = html.indexOf( text ) + text.length();

	   if( idx < html.length() ) {

	      html = html.substring( idx ).trim();

	   } else {

	      html = null;

	   }

	}

	if( html != null && ! html.startsWith( "<!--" ) ) {

	   this.attribute = html;

	}

//	Utility.debug(this, "ATTRIBUTE = " + this.attribute );

  }

  final private String getHref() {

      final String href = readAttribute( "a href" );

      return (href == null) ? "" : href;

  }

  final private String getTarget() {

      final String target = readAttribute( "target" );

      return (target == null) ? "" : target;

  }

  final private String clearCalibratedFontTags(String text) {

      String clearedText = "";


      final String OPEN_FONT = "<font";
      final String CLOSING_FONT = "</font>";

      final int OPEN_LEN = OPEN_FONT.length();
      final int CLOSING_LEN = CLOSING_FONT.length();

      int openIndex = text.indexOf( OPEN_FONT );

      if( openIndex < 0 ) {

	 return text;

      }


      while( openIndex > -1 ) {

	  int textLen = text.length();

	  clearedText += text.substring( 0, openIndex );

	  int middleIndex = text.indexOf( ">", openIndex );

	  int closingIndex = text.indexOf( CLOSING_FONT, middleIndex );

	  clearedText += text.substring( middleIndex + 1, closingIndex );

	  text = text.substring( closingIndex + CLOSING_LEN );

	  openIndex = text.indexOf( OPEN_FONT );

      }

      clearedText += text;

      return clearedText;

  }

  final private StringElement getStringElement(final HtmlDocument doc) {

      final Font font = getFont();

//      Utility.debug(this, "SE FONT = " + font);

      final String attribute = this.attribute;

      String text = readFromToLast( attribute, "<p", "/p>");

//      Utility.debug(this, "SE TEXT = " + text);

      text = readFromToLast( text, ">", "<");

      text = clearCalibratedFontTags( text );

//      Utility.debug(this, "SE TEXT = " + text);

//      if( text.endsWith( nl ) ) {
//	  text = text.substring( 0, text.lastIndexOf( nl ) ) + "\n" ;
//      }

      text = StringView.fromHtmlText( text );

//      Utility.debug(this, "SE text = " + text );

      final Color color = getColor();

//      Utility.debug(this, "SE COLOR = " + color);

      final boolean underLine = isUnderLine();

      final String href = getHref();
      final String target = getTarget();

      final StringElement element = new StringElement( doc, text, font, color, underLine );

      element.setHref( href, target );

      return element;

  }

  final private Color getStyleBackground() {

      final String color = readFromToFirst( style, "background-color:", ";" );

      if( color == null ) {

	 return null;

      }

      return StringView.fromHtmlColor( color.trim() );

  }

  final private int getStyleBorderWidth() {

      return readInt( style, "border-width:", "px");

  }

  final private Color getStyleBorderColor() {

      final String color = readFromToFirst( style, "border-color:", ";" );

      if( color == null ) {

	 return null;

      }

      return StringView.fromHtmlColor( color.trim() );

  }

  final private int getStyleRowNum() {

      try {

	return  new Integer( readFromToFirst( style, "row:", ";" ).trim() ).intValue() ;

      } catch (Exception e) {

	  Utility.debug( e );

	  return 1;

      }

  }

  final private int getStyleColNum() {

      try {

	return  new Integer( readFromToFirst( style, "col:", ";" ).trim() ).intValue() ;

      } catch (Exception e) {

	  Utility.debug( e );

	  return 1;

      }

  }

  final private ImageElement getImageElement(final HtmlDocument doc, final int imageType) {

      final String name = getName();

//      Utility.debug(this, "IE NAME = " + name );

      final String srcText = getSourceText();

      final File src = getSource();

      final String srcName = src.getName().toLowerCase();

      Utility.debug( this, "SRC TEXT = " + srcText );

      final Rectangle area = getSourceArea();

      ImageElement element = null;

      if( name != null && name.equalsIgnoreCase("chart") ) {

	 final int type = new Integer( readAttribute("type") ).intValue();

	 final String title = readAttribute( "title" );
	 final String xTitle = readAttribute( "xtitle" );
	 final String yTitle = readAttribute( "ytitle" );

	 final Dimension size = new Dimension( area.width, area.height );

	 final String dataText = readAttribute( "data" );

	 final String rows [] = Utility.parseString( dataText, "\\\\" );

	 final String [][] data = new String [ rows.length ] [];

	 for( int i = 0, len = data.length ; i < len; i ++ ) {

	     data [ i ] = Utility.parseString( rows[i], "\\" );

	 }

	 final ChartData chartData = new ChartData( data, type, title, xTitle, yTitle, size );

	 element = new ChartElement( doc, src, area, chartData );

      } else if( name != null && name.equalsIgnoreCase("shape") ) {

	 final int shapeType = new Integer( readAttribute("type") ).intValue();

	 final int thickness = new Integer( readAttribute("thickness") ).intValue();

	 final int fillRGB = new Integer( readAttribute("fill_color") ).intValue();

	 final Color fillColor = ( fillRGB == -1 ) ? null : new Color( fillRGB );

	 final int lineRGB = new Integer( readAttribute("line_color") ).intValue();

	 final Color lineColor = ( lineRGB == -1 ) ? null : new Color( lineRGB );

	 final String dataText = readAttribute( "data" );

	 final String data [] = Utility.parseString( dataText, "," );

	 final LinkedList pointList = new LinkedList();

	 for( int i = 0, len = data.length; i < len; i += 2 ) {

	    final String xText = data[ i ];
	    final String yText = data[ i + 1 ];

	    final int x = new Integer( xText ).intValue();
	    final int y = new Integer( yText ).intValue();

	    pointList.addLast( new Point( x, y ) );

	 }

	 element = new ShapeElement( doc, pointList, shapeType, fillColor, lineColor, thickness );

      } else if( srcText != null && srcText.startsWith( "http://") ) { // 링크 이미지인 경우

	  try {

	    element = new ImageElement( doc, new URL( srcText ), area );

	  } catch (Exception e) {

	    Utility.debug( e );

	  }

      } else if( FileManager.isImageFileExtension( srcName ) ) { // 보통 이미지

	 element = new ImageElement( doc, src, area );

      } else if( FileManager.isAudioFileExtension( srcName ) ) { // 오디오 파일

	 element = new AudioElement( doc, src, area );

      } else if( FileManager.isVideoFileExtension( srcName ) ) { // 비디오 파일

	 element = new VideoElement( doc, src, area );

      }

      if( element == null ) {

	element = new ImageElement( doc, src, area );

      }

      element.setStyle( imageType );

      element.setHref( getHref(), getTarget() );

      return element;

  }

  final private boolean isUnderLine() {

      final String attribute = this.attribute;

      final int i = attribute.indexOf( "<u>" );
      final int k = attribute.indexOf( "</u>" );

      return ! ( i < 0 || k < 0 );

  }

  final private boolean isBoldic() {

      final String attribute = this.attribute;

      final int i = attribute.indexOf( "<b>" );
      final int k = attribute.indexOf( "</b>" );

      return ( i > -1 && k > -1 );

  }

  final private boolean isItalic() {

      final String attribute = this.attribute;

      final int i = attribute.indexOf( "<i>" );
      final int k = attribute.indexOf( "</i>" );

      return ( i > -1 && k > -1 );

  }

  final private Color getColor() {

      final String color = readFromToFirst( attribute, "color:", ";").trim();

      return StringView.fromHtmlColor( color );

  }

  final private String getSourceText() {

      return readAttribute( "src" );

  }

  final private File getSource() {

      final String src = readAttribute( "src" );

      return new File( dir, src );

  }

  final private String getName() {

      return readAttribute( "name" );

  }

  final private Dimension getSourceDimension() {

     final String width = readAttribute( "width" );
     final String height = readAttribute( "height" );

     return new Dimension( new Integer(width).intValue(), new Integer(height).intValue() );

  }

  final private Rectangle getSourceArea() {

     final Rectangle area = getStyleArea();
     final Dimension size = getSourceDimension();

     return new Rectangle( area.x, area.y, size.width, size.height );

  }

  final private Font getFont() {

      final String attribute = this.attribute;

      final String face = readFromToFirst( attribute, "font-family:", ";").trim();

      final String sizeText = readFromToFirst( attribute, "font-size:", ";").trim();

      final int size = new Integer( sizeText ).intValue();

      int style = Font.PLAIN;

      if( isBoldic() ) {
	 style = style | Font.BOLD;
      }

      if( isItalic() ) {
	 style = style | Font.ITALIC;
      }

      return FontManager.getFont( face, style, size );

  }

  final private String readAttribute(final String attributeName ) {

      String attribute = this.attribute;

      final int i = attribute.indexOf( attributeName );

      if( i < 0 ) {

	 return null;

      }

      attribute = attribute.substring( i + attributeName.length() ).trim();

      if( attribute == null ) {
	return "";
      }

      attribute = readFromToFirst( attribute, "\"", "\"" );

      if( attribute == null ) {
	return "";
      }

      return attribute.trim();

  }

  final private Rectangle getStyleArea() {

      String style = this.style;

      if( style == null || style.length() < 1 ) {
	  return null;
      }

      style = style.toLowerCase();

      final int x = readInt( style, "left:", "px"),
		y = readInt( style, "top:", "px"),
		w = readInt( style, "width:", "px"),
		h = readInt( style, "height:", "px");

      return new Rectangle( x, y, w, h );

  }

  final private int readInt(final String text, final String from, final String to) {

      final String data = readFromToFirst( text, from, to);

      if( data == null ) {

	 return 0;

      }

      try {

	 return new Integer( data.trim() ).intValue();

      } catch (Exception e) {

	 Utility.debug( e );

	 return 0;

      }

  }

  final private void realize(final  HtmlDocument doc ) {

      final String type = this.type;

//      Utility.debug(this, "REAL TYPE = " + type );

      if( type.equalsIgnoreCase( TEXT ) ) {  // in case of string element

	 realizeText( doc );

      } else if( type.equalsIgnoreCase( RECT_IMAGE ) ) {

	 realizeImage( doc, ImageElement.RECT );

      } else if( type.equalsIgnoreCase( FRONT_TEXT ) ) {

	 realizeImage( doc, ImageElement.FRONT_TEXT );

      } else if( type.equalsIgnoreCase( BACK_TEXT ) ) {

	 realizeImage( doc, ImageElement.BACK_TEXT );

      } else if( type.equalsIgnoreCase( WORD_BOX ) ) {

	 realizeWordBox( doc, null );

      } else if( type.equalsIgnoreCase( TABLE ) ) {

	 realizeTable( doc );

      }

//      Utility.debug(this, "DONE REAL TYPE = " + type );

  }

  final private void realizeImage(final HtmlDocument doc, final int imageType ) {
//      Utility.debug(this, "Realizing image layer (" + imageType + ") ......" );

      doc.addImageElement( getImageElement(doc, imageType) );

  }

  final private void realizeText(final HtmlDocument doc) {
//      Utility.debug(this, "Realizing text layer ......" );

      doc.addStringElement( getStringElement( doc ) );

  }

  final public void realize(final  HtmlEditorPane editor ) {

      mode.setFileOpen( true );

      final Rectangle area = getStyleArea();

      final Color borderColor = getStyleBorderColor();

      final Color bgColor = getStyleBackground();

      final int borderWidth = getStyleBorderWidth();

      // 기존에 입력되던 문자 흔적을 지운다. 텍스트에리어에 남아 있던 버프를 없앤다.
      // 그래야 새로 입력 될 때 버그가 안 난다.

      editor.initTextInput();

      // 텍스트 에리어의 기존 입력 문자 버퍼 지우기.

      final HtmlDocument doc = new HtmlDocument( editor );

      editor.setHtmlDocument( doc );

      doc.setFillColor( bgColor );
      doc.setBorderColor( borderColor );
      doc.setArea( area );
      doc.setBorderWidth( borderWidth );

//      Utility.debug(this, "HtmlEditor TYPE = " + type +", BG = " + bgColor + ", BC = " + borderColor + ", AREA = " + area);

      final HtmlLayer [] layers = getLayers();

      for( int i = 0, len = layers.length; i < len; i ++ ) {

	layers[i].realize( doc );

      }

      HtmlDocument.setSelectedImageElement( doc, null );

      mode.setFileOpen( false );

      removeLastStringElementToBeautifyInsertedDocument( doc );

      initHtmlDocumentCloned( doc );

      editor.repaint();

      editor.requestFocus();

  }

  // 문서를 읽어 들인 후에, 문서의 마지막에 개행 문자가 두 개 생기므로
  // 멋진 페인팅을 위해서 마지막 스트링 엘리먼트를 삭제한다.
  // 문서의 초기화 시에 개행 문자가 하나 기본적으로 들어가 있으므로 생기는 현상이다.
  // 그러므로, 문서 파일 읽기 후에는 기본적으로 들어가 있는 스트링 엘리먼트를 삭제하여야 한다.
  // 문서를 읽어 들일때 기본 스트링 엘리먼트는 하나 씩 뒤로 밀려서 맨 마지막으로 위치하게 된다.

  protected static void removeLastStringElementToBeautifyInsertedDocument(final HtmlDocument doc) {

      // 초기화된 개행 문자를 없애준다. 파일 읽기 후에 페인팅 최적화를 위해서......
      final LinkedList stringElements = doc.getStringElements();

      stringElements.removeLast();

  }

  final protected static void initHtmlDocumentCloned(final HtmlDocument doc ) {

      // 파일 오픈 후에 깜빡 깜빡 거리도록 하기 위해서...
      // 캐릿 엘리먼트를 정확히 설정해 준다.

      final StringElement caretElement = (StringElement) doc.getStringElements().getFirst();

      doc.setCaretElement( caretElement );

      doc.syncCaretStringElement( true );

      caretElement.synchIndex( 0, true );

      // 끝. 캐릿 엘리먼트 재정의

      doc.requestNewHtmlDocView();

  }

  final private void realizeWordBox(final  HtmlDocument parentDoc, HtmlDocument childDoc ) {

//      Utility.debug(this, "Realizing word box layer ......" );

      final Rectangle area = getStyleArea();

      final Color borderColor = getStyleBorderColor();

      final Color bgColor = getStyleBackground();

      final int borderWidth = getStyleBorderWidth();

      // 파라미터로 넘어온 차이들 도큐먼트가 널인 경우에는
      // 차일드 도큐먼트를 새로 생성한다.

      final HtmlDocument doc = ( childDoc != null ) ?
			       childDoc : ( parentDoc.createChildHtmlDocument() );

      doc.setFillColor( bgColor );
      doc.setBorderColor( borderColor );
      doc.setArea( area );
      doc.setBorderWidth( borderWidth );

      if( childDoc == null ) {
	  // 자식 도큐먼트를 새로 생성 했을 경우에만
	  // 아버지 도큐먼트에 추가한다.

	  parentDoc.addImageElement( doc );

      }

      final HtmlLayer [] layers = getLayers();

      for( int i = 0, len = layers.length; i < len; i ++ ) {

	layers[i].realize( doc );

      }

      // 초기화된 개행 문자를 없애준다. 파일 읽기 후에 페인팅 최적화를 위해서......
      removeLastStringElementToBeautifyInsertedDocument( doc );

  }

  final private void realizeTable(final  HtmlDocument parentDoc ) {

//      Utility.debug(this, "Realizing word box layer ......" );

      final Color borderColor = getStyleBorderColor();

      final Color bgColor = getStyleBackground();

      final int borderWidth = getStyleBorderWidth();

      final int rowNum = getStyleRowNum();

      final int colNum = getStyleColNum();

      final Rectangle area = getStyleArea();

//      final Rectangle2D initTableArea = new Rectangle2D.Double(
//					   0, 0,
//					   rowNum*AppRegistry.MINIMUN_DOCUMENT_WIDTH,
//					   colNum*AppRegistry.MINIMUN_DOCUMENT_HEIGHT
//				       );

      // 테이블 크기는 알아서 내부 셀의 크기에 의해서 자동 조절 되므로,
      // 파일에서 읽어 들일 때는 초기에 충분히 작게 잡아서
      // 셀을 읽어 들인 후에 자동으로 크기를 조절하게 한다.

      final TableDocument table = new TableDocument( parentDoc, rowNum, colNum, area );

      table.setFillColor( bgColor );
      table.setBorderColor( borderColor );
      table.setBorderWidth( borderWidth );

      parentDoc.addImageElement( table );

      final HtmlLayer [] layers = getLayers(); // 워드 박스 레이어들

      final ImageElement cellDocs []= table.getImageElementsArray(); // 테이블 셀 도큐먼트들

      for( int i = 0, len = layers.length; i < len; i ++ ) {

	layers[i].realizeWordBox( table, ( (HtmlDocument) cellDocs[ i ] ) );

      }

      table.checkCellAreaValidity();

      table.setHtmlDocViewAsNull();

//      table.setArea( area );

//      // 초기화된 개행 문자를 없애준다. 파일 읽기 후에 페인팅 최적화를 위해서......
//      removeLastStringElementToBeautifyInsertedDocument( table );

  }

  final private HtmlLayer [] getLayers() {

      final Vector layers = this.layers;

      final int len = layers.size();

      final HtmlLayer value [] = new HtmlLayer[ len ];

      final Enumeration enumIt = layers.elements();

      int i = 0;

      while( enumIt.hasMoreElements() ) {
	  value[ i ] = (HtmlLayer) enumIt.nextElement();
	  i ++ ;
      }

      return value;
  }

}
