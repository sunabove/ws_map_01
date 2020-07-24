
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
import java.awt.event.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import java.awt.image.*;
import java.net.*;
import jcosmos.*;

public class HtmlEditorPane extends JTextField
			    implements ModeInterface, Runnable, CharacterUtility,
				       KeyListener, MouseListener, MouseMotionListener,
				       DropTargetListener, ProgressInterface {

  // static member definition

  private static int chartFileNameIndex;

  final private Color tickColor = Color.lightGray;

  private String preText = "";

  private HtmlDocument doc;

  private String title = AppRegistry.HTML_FREE_EDITOR_TITLE;

  private String defaultUrl = AppRegistry.DEFAULT_URL;

  final private EditorBorder editorBorder = new EditorBorder( this, HtmlDocument.TOP_DOC_SCAN_MARGIN );

  final private EditorJPopupMenu editPopupMenu = new EditorJPopupMenu( this );

  public static JFileChooser htmlOpenFileChooser, htmlSaveFileChooser, imageFileChooser, multiMediaFileChooser;

  private static JColorChooser colorChooser = new JColorChooser();

  private static Color initialColor = Color.black;

  private static int LAST_EDITOR_ID = 0;

  final private int editorId = ( LAST_EDITOR_ID ++ );

  private boolean inited;

  private boolean isPainting = false;

  private JHtmlEditorScrollPane scroller;

  private boolean autoScroll;

  final private Thread cursorBlinker = new Thread( this );

  private static final String WORDS =

    "JCOSMOS DEVLOPMENT Coorporation. e-mail: sbmoon@jcosmos.com One man has died for you. He is Jesus Christ.";

  // 드래그 앤 드랍 관련 멤버 데이타

  private DropTarget dropTarget;
  private boolean acceptableType;	// Indicates whether data is acceptable

  // 끝. 드래그 앤 드랍 관련 멤버 데이타

  // 음성 녹음 ( HTML 저장 시에 체크하여 파일만 zip 파일에 추가한다.)
  private File voiceRecordFile;
  // 끝. 음성 녹음 파일

  private MouseEvent lastMoveMouseEvent;

  // 프로그레스 데이터

//  private final String initStatusMessage = new String( AppRegistry.APP_NAME );

  private String statusMessage = AppRegistry.APP_NAME;

  private int status = -1;

  private long statusPaintTime = System.currentTimeMillis();

  private Dimension messageTextSize = AppRegistry.LOGO_MESSAGE_TEXT_SIZE;

  private Image logoImage = AppRegistry.STARTUP_LOGO_IMAGE;

  private long paintStatusTime = System.currentTimeMillis();

//  private boolean showDefaultStatusMessage = true;

  // 끝. 프로그레스 데이터

  public static void initOpenFileChoosers() {

     final File root = new File( "C:\\" );

     // init html open file chooser

     htmlOpenFileChooser = new JFileChooser();
     htmlOpenFileChooser.addChoosableFileFilter( new SimpleFileFilter( new String [] {".hfz" },
		   "Zipped Html Files (*.hfz)") );
     htmlOpenFileChooser.addChoosableFileFilter( new SimpleFileFilter( new String [] {".htm", ".html" },
		   "Html Files (*.htm, *.html)") );
     htmlOpenFileChooser.setMultiSelectionEnabled( false );
     htmlOpenFileChooser.setDialogTitle("Select an HTML file to open");

     htmlOpenFileChooser.setCurrentDirectory( root );

     // end of initiation html open file chooser

  }

  public static void initSaveFileChooser() {

     final File root = new File( "C:\\" );

     // init html file save chooser

     htmlSaveFileChooser = new JFileChooser();
     htmlSaveFileChooser.addChoosableFileFilter( new SimpleFileFilter( new String [] {".htm", ".html" },
		   "Html Files (*.htm, *.html)") );
     htmlSaveFileChooser.setMultiSelectionEnabled( false );
     htmlSaveFileChooser.setDialogTitle("Select Or Enter an HTML file name to save");

     htmlSaveFileChooser.setCurrentDirectory( root );

     // end of initiation html file save chooser

  }

  public static void initImageFileChooser() {

     final File root = new File( "C:\\" );

     // init image file chooser

     imageFileChooser = new JFileChooser();
     imageFileChooser.setDialogTitle("Slect an image file to insert, please!");
     imageFileChooser.addChoosableFileFilter( new SimpleFileFilter( FileManager.IMAGE_EXTENSIONS,
		   "Images (*.gif, *.jpg, *.jpeg)") );
     imageFileChooser.setMultiSelectionEnabled( false );

     imageFileChooser.setCurrentDirectory( root );

     // end of initiating image file chooser

  }

  public void initMultiMediaFileChooser() {

    final File root = new File( "C:\\" );

     // init video file chooser

     multiMediaFileChooser = new JFileChooser();
     multiMediaFileChooser.setDialogTitle("Slect a multi media file to insert, please!");

     SimpleFileFilter defaultFileFilter = new SimpleFileFilter( new String [] {
		    ".cda",
		    ".mid", ".rmi", ".midi",
		    ".asf", ".wm", ".wma", ".wmv",
		    ".mpeg", ".mpg", ".m1v", ".mp2", ".mp3",
		    ".asx", ".wax", ".m3u", ".wvx", ".wmx",
		    ".avi", ".wmv",
		    ".wav", ".snd", ".au", ".aif", ".aifc", ".aiff"
		   },
		   "미디어 파일(모든 유형)");

     multiMediaFileChooser.addChoosableFileFilter( defaultFileFilter );

     multiMediaFileChooser.addChoosableFileFilter( new SimpleFileFilter( new String [] { ".cda" },
		   "CD 오디오 트랙 (*.cda)") );
     multiMediaFileChooser.addChoosableFileFilter( new SimpleFileFilter( new String [] { ".mid", ".rmi", ".midi" },
		   "MIDI 파일 (*.mid;*.rmi;*.midi)") );
     multiMediaFileChooser.addChoosableFileFilter( new SimpleFileFilter( new String [] { ".asf", ".wm", ".wma", ".wmv" },
		   "Windows Media 파일(*.asf;*.wm;*.wma;*.wmv)") );
     multiMediaFileChooser.addChoosableFileFilter( new SimpleFileFilter( new String [] { ".mpeg", ".mpg", ".m1v", ".mp2", ".mp3" },
		   "동영상 파일(MPEG) (*.mpeg;*.mpg;*.m1v;*.mp2;*.mp3)") );
     multiMediaFileChooser.addChoosableFileFilter( new SimpleFileFilter( new String [] { ".asx", ".wax", ".m3u", ".wvx", ".wmx" },
		   "미디어 재생 목록 (*.asx;*.wax;*.m3u;*.wvx;*.wmx)") );
     multiMediaFileChooser.addChoosableFileFilter( new SimpleFileFilter( new String [] { ".avi", ".wmv" },
		   "비디오 파일 (*.avi;*.wmv)") );
     multiMediaFileChooser.addChoosableFileFilter( new SimpleFileFilter( new String [] { ".wav", ".snd", ".au", ".aif", ".aifc", ".aiff" },
		   "오디오 파일 (*.wav;*.snd;*.au;*.aif;*.aifc;*.aiff)") );

     multiMediaFileChooser.setMultiSelectionEnabled( false );

     multiMediaFileChooser.setFileFilter( defaultFileFilter );

     multiMediaFileChooser.setCurrentDirectory( root );

     // end of initiation video file chooser

  }

  public HtmlEditorPane() {

      // 커스 블링킹 쓰레드 출발

      cursorBlinker.start();

      // 끝. 커서 블링킹 쓰레드 출발.

      // 드래그 앤 드랍 관련 코드

      dropTarget = new DropTarget( this, DnDConstants.ACTION_COPY_OR_MOVE, this, true, null);

      // 끝. 드래그 앤 드랍 관련 코드

  }

  protected void setAutoScroll(final boolean b) {

     this.autoScroll = b;

  }

  protected boolean isAutoScroll() {

     return autoScroll;

  }

  final public JHtmlEditorScrollPane getScroller() {

      if( scroller == null ) { // 빠른 연산을 하기 위해서
			       // 왜냐하면 에디터가 이리 저리 스크롤페인을 이동할 일은
			       // 없어야 하기 때문이다.
			       // 만약에 스클롤러 사이를 이동한다면, 버그가 발생한다.

	scroller = (JHtmlEditorScrollPane) ( Utility.getAncestorOfClass( JHtmlEditorScrollPane.class, this ) );

      }

      return scroller;

  }

  final public EditorBorder getEditorBorder() {

      return editorBorder;

  }

  final public void initEditor() {

     initDocument();

     setDoubleBuffered( true );
     setBorder( editorBorder );

     final Caret caret = super.getCaret();
     caret.setBlinkRate(0);

     setText( "" );

     addKeyListener( this );
     addMouseListener( this );
     addMouseMotionListener( this );

     requestFocus();

  }

  final private void initDocument() {

     doc = new HtmlDocument( this );

     // 디폴트 선택으로 설정한다.
     doc.setSelectedImageElement( doc, null );

     // 에디터 폭과 도큐먼트 폭 최적화

     doc.setDocumentWidth( getSize().width );

     // 끝. 에디터 폭과 도큐먼트 폭 최적화

     // 도큐먼트 위치 동기화

     doc.setLocation( 0, 0 );

     // 끝. 도큐먼트 위치 동기회

     // 도큐먼트 전경색, 배경색 동기화 (기준은 에디터 전경색, 배경색)

     doc.setBorderColor( getBackground() );
     doc.setFillColor( getBackground() );

     // 끝. 도큐먼트 전경색, 배경색 동기화

  }

  final public void setNewDocument() {

     if( ( ! HtmlFreeEditorPane.hasSavedAsFile() ) && ( ! AppRegistry.FORCED_NEW_DOCUMENT ) ) {

	final int option = JOptionPane.showConfirmDialog( getScroller(), AppRegistry.NEW_DOCUMENT_MSG, AppRegistry.APP_NAME, JOptionPane.YES_NO_OPTION );

	if( option == JOptionPane.NO_OPTION ) {

	   requestFocus();

	   return;

	}

     }

     setNewDocumentTruly();

  }

  private void setNewDocumentTruly() {

     initTextInput();

     initDocument();

     this.voiceRecordFile = null; // 음성 녹음 파일을 초기화( 널 값으로 ) 한다.

     repaint();

     HtmlFreeEditorPane.setHasSavedAsFile( false );

     requestFocus();

  }

  final public void saveHtml( final File file, final Hashtable zipList ) {

     // saves as html file
     try {

	// add to zip list

	zipList.put( file, file );

	// end of adding to zip list

	String htmlCodes = "";

	final FileOutputStream out = new FileOutputStream( file );

	// saves start body tag

	final String charSet = AppRegistry.CHARSET;

	final String metaTag = "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=" + charSet + "\">";

	final String startBodyTag = "<html>" + nl +
		  "<head>" + nl +
		  metaTag + nl +
		  "<title>" + nl +
		  title + nl +
		  "</title>" + nl +
		  "<style>" + nl +
		  "<!--" + nl +
		  "    p { font-size : " + AppRegistry.FONT_SIZE_FACTOR + "% }" + nl +
		  "-->" + nl +
		  "</style>" + nl +
		  "</head>" + nl +
		  "<body " + AppRegistry.HTML_BODY_ADDITIONAL_TAG +
		  ">" + nl;;

	htmlCodes += startBodyTag;

	// end of saving start body tag

	// saves each html editor tag as layer

	final HtmlDocument doc = this.doc;

	final String body = doc.tag( 1 );

	saveImages( file.getParentFile(), zipList );

	htmlCodes += body;

	// end of saving each html eidtor tag as layer

	// saves end body tag

	final String endBodyTag = "</body>" + nl + "</html>" + nl;

	htmlCodes += endBodyTag;

	byte [] tag = null;

	try {

	  tag = htmlCodes.getBytes( charSet );

	} catch (Exception e) {

	  tag = htmlCodes.getBytes();

	  Utility.debug( e);

	}

	if( CryptoGraph.FILE_CRYPT ) {
	    tag = CryptoGraph.encrypt( tag ) ;
	}

	out.write( tag );

	// end of saving end body tag

	// flush and closes

	out.flush();
	out.close();

	// end of flushing and closing

	Utility.debug(this, "File " + file + "[" + htmlCodes.length() +" bytes] has saved." );

     } catch (Exception e) {

	e.printStackTrace();

     }

     // end of saving as html file

  }

  final public void saveHtml() throws IOException {

     JFileChooser fileChooser = HtmlEditorPane.htmlSaveFileChooser;

     if( fileChooser == null ) {

	HtmlEditorPane.initSaveFileChooser();

	fileChooser = HtmlEditorPane.htmlSaveFileChooser;

     }

     // selects a file to save

     File htmlFile = null;

     int option = fileChooser.showSaveDialog( getScroller() );

     if( option == JFileChooser.APPROVE_OPTION ) {

	 htmlFile = fileChooser.getSelectedFile();

     } else {

	 htmlFile = null;

	 requestFocus();

	 return;   // cancels saving as html file.

     }

     // end of selecting a file to save.

     // checking html file extension
     // if it does not exist, append html file extension automatically.
     final String fn = htmlFile.getName().toUpperCase(); // file name

     if( fn.endsWith("HTML") || fn.endsWith( "HTM" ) ) {
     } else {

	htmlFile = new File( htmlFile.getParent(), htmlFile.getName() + ".html" );

     }

     // end of html file extension checking.

     // ask whether or not to overwrite if the file exists.

     if( htmlFile == null ) {

	 requestFocus();

	 return;

     } else if( htmlFile.exists() ) {

	option = JOptionPane.showConfirmDialog(getScroller(), htmlFile.getName() + "을 덮어 쓸까요?", "덮어 쓰기?", JOptionPane.YES_NO_OPTION );

	if( option != JOptionPane.OK_OPTION ) {   // cancels saving as html file

	    requestFocus();

	    return;

	}

     }

     saveHtml( htmlFile );

     requestFocus();

  }

  final public File [] saveHtml(final String dirName, final String fileName ) throws IOException {

    final File dirFile = new File( dirName );

    // 디렉토리 체크

    if( ! dirFile.exists() ) {

      dirFile.mkdir();

    }

    // 끝. 디렉토리 체크

    final File htmlFile = new File( dirFile, fileName );

    return saveHtml( htmlFile );

  }

  final private File [] saveHtml(final File htmlFile ) throws IOException {

     final Hashtable zipList = new Hashtable(); // zip file list

     saveHtml( htmlFile, zipList );

     // 음성 녹음 파일 zip List 에 추가한다.

     final File voiceRecordFile = this.voiceRecordFile;

     if( voiceRecordFile != null ) {

	zipList.put( voiceRecordFile, voiceRecordFile );

     }

     // 끝. 음성 녹음 파일 zip List 에 추가하기

     HtmlFreeEditorPane.setHasSavedAsFile( true );

     // end of saving file

     // saves as zipped file

     final File zippedHtmlFile = saveAsZippedFile( htmlFile, zipList );

     // end of saving as zipped file

     return new File [] { htmlFile, zippedHtmlFile }; // 영번째가 HTML 파일. 일번째가 ZIP 파일.

  }

  final public File [] saveAsTemporaryFile( ) {

     try {

	final File tempDir = HtmlEditorPane.getTempDir( getScroller() );

	if( tempDir == null ) {

	    return null;

	}

	final File tempFile = new File( tempDir, AppRegistry.TEMP_HTML_FILE_NAME );

	return this.saveHtml( tempFile );

     } catch (Exception e) {

	e.printStackTrace();

	Utility.debug( e );

     }

     return null;

  }

  final public File saveAsZippedFile( final File htmlFile, final Hashtable zipList ) throws IOException {

      String zipFileName = htmlFile.getName();

      // remove .html file name extension of html file name

      final int dotIdx = zipFileName.lastIndexOf( "." );

      if( dotIdx > -1 ) {

	 zipFileName = zipFileName.substring( 0, dotIdx );

      }

      // end of removal .html file name extension

      // append .hfz htmlfree zip file name extension

      zipFileName += "." + AppRegistry.HTML_FREE_ZIP_FILE_EXTENSION;

      // end of appeding .hfz htmlfree zip file name extension

      final File zipFile = new File( htmlFile.getParentFile(), zipFileName );

      // delete old zip file
//
//      if( zipFile.exists() ) {
//
//          zipFile.delete();
//
//      }

      // end of deleting old zip file

      final int len = zipList.size();

      final File out [] = new File[ len ];

      final Enumeration enumIt = zipList.elements();

      for(int i = 0; i < len; i ++ ) {

	 out[ i ] = (File) enumIt.nextElement();

      }

      final Zip zip = new Zip();

      zip.zip( zipFile, out, false );

      return zipFile;

  }


  final public void setHtmlDocument(HtmlDocument doc) {

     this.doc = doc;

     repaint();

     HtmlFreeEditorPane.setHasSavedAsFile( true );

  }

  final public HtmlDocument getHtmlDocument() {

     return this.doc;

  }

  final public String getOnlyTexts() {

    StringBuffer buffer = new StringBuffer();

    this.doc.getOnlyTexts( buffer );

    return buffer.toString();

  }

  final public void applyFont(String fontFamily, int type, String fontSizeText ) {

//     Utility.debug(this, "APPLY FONT " );

     // 무조건 에디팅 모드로 바뀐다.

     mode.setMode( Mode.EDIT );

     final HtmlDocument doc = this.getTargetDocument();

     if( doc == null ) {

	requestFocus();

	return;

     }

     int size = AppRegistry.DEFAULT_FONT_SIZE;

     try {

	size = new Integer( fontSizeText ).intValue();

     } catch (Exception e) {

	 requestFocus();

	 return;

     }

     doc.applyFont( fontFamily, type, size );

     repaint();

     requestFocus();

  }

  final void changeFillColor()  {

       final ImageElement sie = ImageElement.SEL_IMG_ELEM; // selected image element

       if( sie instanceof ShapeElement ) {

	  changeShapeElementFillColor( (ShapeElement) sie );

       } else {

	  changeWordBoxFillColor();

       }

  }

  final void changeShapeElementFillColor( final ShapeElement shapeElement ) {

       final Color initialColor = shapeElement.getFillColor();

       final Color color = colorChooser.showDialog( getScroller(), "Select a shape fill color, please!", initialColor );

       if( color == null ) {

	   requestFocus();

	   return;

       }

       shapeElement.setFillColor( color );

       repaint();

       requestFocus();

  }

  final void changeWordBoxFillColor() {

       final Color color = colorChooser.showDialog( getScroller(), "Select a word box color, please!", initialColor );

       if( color == null ) {

	   requestFocus();

	   return;

       }

       changeWordBoxFillColor(color);

       repaint();

       requestFocus();

  }

  final public void applyBorderWidth( final int borderWidth ) {

     // 무조건 에디팅 모드로 바뀐다.

     mode.setMode( Mode.EDIT );

     final ImageElement sie = ImageElement.SEL_IMG_ELEM; // selected image element

     if( sie instanceof ShapeElement ) {

	applyShapeElementThickness( (ShapeElement) sie, borderWidth );

     } else if( sie instanceof HtmlDocument ) {

	applyWordBoxBorderWidth( (HtmlDocument) sie, borderWidth );

     }

  }

  final private void applyShapeElementThickness( final ShapeElement shapeElement, final int thickness ) {

      shapeElement.setThickness( thickness );

      repaint();

      requestFocus();

  }

  final private void applyWordBoxBorderWidth( final HtmlDocument doc, final int borderWidth ) {

     // 선택된 이미지 엘리먼트가 도큐먼트 타입 일 경우에만 보더 크기를 설정한다.
     // 선택된 객체는 에디터에서 단 하나이다.

     if( doc == null ) {

	requestFocus();

	return;

     }

     doc.setBorderWidth( borderWidth );

     repaint();

     requestFocus();

  }

  final public void applyTextColor() {

       final Color color = colorChooser.showDialog( getScroller(), "Select text color, please!", initialColor );

       if( color != null ) {

	  this.initialColor = color;

	  applyTextColor( color );

       }

       requestFocus();

  }

  final private void applyTextColor(Color color) {

     // 무조건 에디팅 모드로 바뀐다.

     mode.setMode( Mode.EDIT );

     final HtmlDocument doc = this.getTargetDocument();

     if( doc == null ) {

	return;

     }

     doc.applyTextColor( color );

     requestFocus();

     repaint();

  }

  final private void changeWordBoxFillColor(Color color) {

     final HtmlDocument doc = this.getTargetDocument();

     if( doc == null ) {

	return;

     }

     doc.setFillColor( color );

  }

  final private void changeWordBoxLineColor(Color color) {

     final HtmlDocument doc = this.getTargetDocument();

     if( doc == null ) {

	return;

     }

     doc.setBorderColor( color );

  }

  final void changeLineColor()  {

       final ImageElement sie = ImageElement.SEL_IMG_ELEM; // selected image element

       if( sie instanceof ShapeElement ) {

	  changeShapeElementLineColor( (ShapeElement) sie );

       } else {

	  changeWordBoxLineColor();

       }

  }

  final void changeShapeElementLineColor( final ShapeElement shapeElement ) {

       final Color initialColor = shapeElement.getLineColor();

       final Color color = colorChooser.showDialog( getScroller(), "Select a line color, please!", initialColor );

       if( color == null ) {

	   requestFocus();

	   return;
       }

       shapeElement.setLineColor( color );

       repaint();

       requestFocus();

  }

  final void changeWordBoxLineColor() {

       final Color color = colorChooser.showDialog( getScroller(), "Select a word box line color, please!", initialColor );

       if( color == null ) {

	   requestFocus();

	   return;
       }

       changeWordBoxLineColor(color);

       repaint();

       requestFocus();

  }

  final public void applyUnderLine() {

     mode.setMode( Mode.EDIT );

     final HtmlDocument doc = this.getTargetDocument();

     if( doc == null ) {

	requestFocus();

	return;

     }

     doc.applyUnderLine( );

     requestFocus();

     repaint();

  }

  final public void applyBoldic() {

     mode.setMode( Mode.EDIT );

     final HtmlDocument doc = this.getTargetDocument();

     if( doc == null ) {

	requestFocus();

	return;

     }

     doc.applyBoldic();

     requestFocus();

     repaint();

  }

  final public void applyItalic() {

     mode.setMode( Mode.EDIT );

     final HtmlDocument doc = this.getTargetDocument();

     if( doc == null ) {

	requestFocus();

	return;

     }

     doc.applyItalic();

     requestFocus();

     repaint();

  }

  final public void addLinkImage() {

      URL url = getImageLinkURL( "Enter a image link URL!", "http://www." );

      Utility.debug( this, "IMAGE URL = " + url );

      if( url == null ) {

	requestFocus();

	return;

      }

      addImage( url );

      requestFocus();

  }

  final public void editLinkImage(final ImageElement ie) {

      final URL url = getImageLinkURL( "Enter a image link URL to edit!", "" + ie.getLinkURL() );

      if( url == null ) {

	return;

      }

      ie.setLinkURL( url );

      ie.getParentDocument().requestNewHtmlDocView();

  }

  final private URL getImageLinkURL( String message, String refURL ) {

//       Object obj = JOptionPane.showInputDialog( getScroller(), "Enter a link image URL!", "Link ImageURL", JOptionPane.PLAIN_MESSAGE );

       Object obj = JOptionPane.showInputDialog( getScroller(), message, "Image Link URL", JOptionPane.PLAIN_MESSAGE, null, null, refURL );

       if( obj == null ) {

	  return null;

       }

       String urlText = "" + obj;

       Utility.debug( this, "URL TEXT = " + urlText );

       try {

	  return new URL( urlText );

       } catch (Exception e) {

	  Utility.warningDialog( getScroller(), "Invalid URL !", "Invalid URL" );

//	  Utility.debug( e );

	  return null;

       }

  }

  final public void addImage() {

       mode.setMode( Mode.EDIT );

       if( imageFileChooser == null ) {

	   initImageFileChooser();

       }

       final Component parent = getScroller();

       final int option = imageFileChooser.showOpenDialog( parent );

       if( option == JFileChooser.APPROVE_OPTION ) {

	   final File file = imageFileChooser.getSelectedFile();

	   if( file != null ) {

	       final String fileName = file.getName();

	       final int dotIdx = fileName.indexOf( '.' );

	       final String fileExtension = dotIdx < 0 ? null : fileName.substring( dotIdx ).toUpperCase();

	       if(      fileExtension.equalsIgnoreCase( ".JPG" )
		    ||  fileExtension.equalsIgnoreCase( ".JPEG" )
		    ||  fileExtension.equalsIgnoreCase( ".GIF" )
		) {

		   addImage( file );

		   repaint();

	       } else {

		   Utility.warningDialog( parent, "올바른 이미지 파일이 아닙니다.", "이미지 파일이 아님" );

	       }

	   } else {

	       Utility.debug(this, "Selected file is null!");

	   }

       } else {

	   Utility.debug(this, "Canceled adding an image!");

       }

       requestFocus();

  }

  final public void addTable() {

      mode.setMode( Mode.ADD_TABLE );

      final HtmlDocument doc = this.getTargetDocument();

      if( doc != null ) {

	doc.syncCaretStringElement( true );

	initTextInput();

      }

      requestFocus();

  }

  final public void addWordBox() {

      mode.setMode( Mode.ADD_WORD_BOX );

      final HtmlDocument doc = this.getTargetDocument();

      if( doc != null ) {

	doc.syncCaretStringElement( true );

	initTextInput();

      }

      requestFocus();

  }

  final protected void addVoiceRecordFile(File file) {

    this.voiceRecordFile = file;

  }

  final protected void addImage(File file ) {

     final HtmlDocument doc = this.getTargetDocument();

     if( doc == null ) {

	return;

     }

     doc.addImage( file );

     HtmlFreeEditorPane.setHasSavedAsFile( false );

  }

  final private void addImage( URL url ) {

     final HtmlDocument doc = this.getTargetDocument();

     if( doc == null ) {

	return;

     }

     doc.addImage( url );

     HtmlFreeEditorPane.setHasSavedAsFile( false );

     repaint();

  }

  final private void addChart(ChartData data) {

     final HtmlDocument doc = this.getTargetDocument();

     if( doc == null ) {

	return;

     }

     doc.addChart( data );

     HtmlFreeEditorPane.setHasSavedAsFile( false );

  }

  final private void addAudio(File file ) {

     final HtmlDocument doc = this.getTargetDocument();

     if( doc == null ) {

	return;

     }

     doc.addAudio( file );

     HtmlFreeEditorPane.setHasSavedAsFile( false );

  }

  // end of inserting audio file

  // inserting video file

  final public void addMultiMemia() {

       final Component parent = getScroller();

       if( multiMediaFileChooser == null ) {

	   initMultiMediaFileChooser();

       }

       final int option = multiMediaFileChooser.showOpenDialog( parent );

       if( option == JFileChooser.APPROVE_OPTION ) {

	   final File file = multiMediaFileChooser.getSelectedFile();

	   if( file != null ) {

	       if( FileManager.isAudioFileExtension( file ) ) {

		  addAudio( file );

	       } else {

		  addVideo( file );

	       }

	       repaint();

	   } else {
	       Utility.debug(this, "Selected file is null!");
	   }

       } else {

	   Utility.debug(this, "Canceled adding an image!");

       }

       requestFocus();

  }

  // insert chart

  final public void addChart( ) {

    ImageElement sie = ImageElement.SEL_IMG_ELEM;

    if( sie instanceof ChartElement ) {

      // 선택된 객체가 챠트 엘리먼트 일 경우에는....
      // 챠트 삽입 버튼을 누르면 차트를 편집한다.

      editChart( (ChartElement) sie );

      requestFocus();

      return;

    }

    final Dimension size = new Dimension( 400, 180 );

    final ChartData tm = new ChartData( ChartData.BAR_TYPE, "Comparing Apples and Oranges", "Year", "Tons Consumed", size );

    if( editChartData( tm ) == 1 ) { // 차트 추가가 취소할 경우....

      requestFocus();

      return;

    }

    addChart( tm );

    repaint();

    requestFocus();

  }

  final public void editChart(final ChartElement element) {

    final ChartData cd = element.getChartData().cloneChartData();

    if( editChartData( cd ) == 1 ) { // if action canceld, 캔슬 버튼일 눌러졌을 경우에.....
				      // 아무런 작업도 하지 않고 끝낸다.

      requestFocus();

      return;

    }

    element.setChartData( cd );

    repaint();

    requestFocus();

  }

  final private int editChartData(final ChartData tm) {

      final String enterUrlText = "<html><font color=blue> Please edit</font> <font color=red> chart data </font> <font color = blue>! </font> </html>";

      final JPanel chartPanel = new JPanel();

      chartPanel.setLayout( new BorderLayout(1,1) );

      final JTextField mainTitleTF = new JTextField( tm.getTitle() );

      final JTextField xTitleTF = new JTextField( tm.getXTitle() );

      final JTextField yTitleTF = new JTextField( tm.getYTtitle() );

      final JComboBox typeCB = new JComboBox();

      final JPanel chart = new JPanel() {

	public void paint(Graphics g) {

	  super.paint( g );

	  tm.setSize( this.getSize() );

	  tm.setTitle( mainTitleTF.getText() );

	  tm.setXTitle( xTitleTF.getText() );

	  tm.setYTitle( yTitleTF.getText() );

	  ChartUtilities.paint( g, tm );

	}

      };

      final SpreadSheet sheet = new SpreadSheet( tm );

      tm.addTableModelListener(new TableModelListener() {
	public void tableChanged(TableModelEvent e) {
	  chart.repaint();
	}
      });

      final JScrollPane sheetSp = new JScrollPane( sheet );

      chartPanel.add( sheetSp, BorderLayout.NORTH );

      chart.setBackground( Color.orange );

      chartPanel.add( chart, BorderLayout.CENTER );

      final Dimension tmSize = tm.getSize();

      final Dimension size = new Dimension( 420, 440 );

      sheetSp.setPreferredSize( new Dimension( size.width, 100 ) );

      chartPanel.setPreferredSize( size );

      typeCB.addItem( "Bar" );
      typeCB.addItem( "Pie" );
      typeCB.addItem( "Line" );

      typeCB.setSelectedIndex( tm.getType() );

      final JPanel typePnl = new JPanel();

      typePnl.setLayout( new BorderLayout() );

      typePnl.add( new JLabel(" Type : "), BorderLayout.WEST );
      typePnl.add( typeCB, BorderLayout.CENTER );

      final JPanel mainTitlePnl = new JPanel();

      mainTitlePnl.setLayout( new BorderLayout() );
      mainTitlePnl.add( new JLabel( " Main Title : " ), BorderLayout.WEST );
      mainTitlePnl.add( mainTitleTF, BorderLayout.CENTER );

      final JPanel xTitlePnl = new JPanel();

      xTitlePnl.setLayout( new BorderLayout() );
      xTitlePnl.add( new JLabel( " X Title : " ), BorderLayout.WEST );
      xTitlePnl.add( xTitleTF, BorderLayout.CENTER );

      final JPanel yTitlePnl = new JPanel();

      yTitlePnl.setLayout( new BorderLayout() );
      yTitlePnl.add( new JLabel( " Y Title : " ), BorderLayout.WEST );
      yTitlePnl.add( yTitleTF, BorderLayout.CENTER );

      typeCB.addItemListener(new ItemListener() {

	public void itemStateChanged(ItemEvent e) {

	   tm.setType( typeCB.getSelectedIndex() );
	   chart.repaint();

	}

      });

      final JPanel menuPanel = new JPanel();

      menuPanel.setLayout( new GridLayout( 4, 1 ) );

      menuPanel.add( typePnl );
      menuPanel.add( mainTitlePnl );
      menuPanel.add( xTitlePnl );
      menuPanel.add( yTitlePnl );

      chartPanel.add( menuPanel, BorderLayout.SOUTH );

      mainTitleTF.addKeyListener(new KeyAdapter() {

	public void keyPressed(KeyEvent e) {

//	  tm.setTitle( mainTitleTF.getText() );
	  chart.repaint( 1000 );

	}

      });

      xTitleTF.addKeyListener(new KeyAdapter() {

	public void keyPressed(KeyEvent e) {

//	  tm.setXTitle( xTitleTF.getText() );
	  chart.repaint( 1000 );

	}

      });

      yTitleTF.addKeyListener(new KeyAdapter() {

	public void keyPressed(KeyEvent e) {

//	  tm.setYTitle( yTitleTF.getText() );
	  chart.repaint( 1000 );

	}

      });

      final String[] options = {
	"OK",
	"Cancel",
      };

      final int result = JOptionPane.showOptionDialog(
			      getScroller(),
			      new Object [] { enterUrlText, chartPanel },
			      "Chart",
			      JOptionPane.DEFAULT_OPTION,
			      JOptionPane.INFORMATION_MESSAGE,
			      null,
			      options,
			      options[0]
			  );

      tm.setSize( tmSize );

      return result;

  }

  // insert video from a file

  final private void addVideo(final File file ) {

     final HtmlDocument doc = this.getTargetDocument();

     if( doc == null ) {

	return;

     }

     doc.addVideo( file );

     HtmlFreeEditorPane.setHasSavedAsFile( false );

  }

  private String getSytemClipboardBufferText() {

    Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();

    Transferable content = cb.getContents( this );

    try {

       return "" + content.getTransferData( DataFlavor.stringFlavor );

    } catch (Exception ex ) {

//      Utility.debug( ex );

      return null;

    }

  }

  final public boolean isCopialbe() {

    final HtmlDocument doc = getTargetDocument();

    final int startIndex = doc.getStartIndex();
    final int endIndex = doc.getEndIndex();

    return (startIndex != endIndex );

  }

  final public boolean isPastable() {

    final HtmlDocument doc = getTargetDocument();

    if( doc.getCopiedHtmlElements().size() > 0 ) {

      return true;

    }

    if( getSytemClipboardBufferText() != null ) {

      return true;

    }

    return false;

  }

  protected boolean pasteSystemClipboard() {

//    // 키 이벤트 객체가 널 값이면 항상 시스템 클립보드의 데이터를 페이스팅 할 수 있다.
//    if( e != null && e.getID() != e.KEY_PRESSED ) {
//
//      // 키가 눌러졌을 경우에만 시스템 클립보드의 데이터를 페이스팅 할 수 있다.
//      // 실제 할 지 않 할지는 아래서 좀 더 엄격히 체크한다.
//
//      return true;
//
//    }

//    if( HtmlDocument.getCopiedHtmlElements().size() > 0 ) {
//      // 에디터의 순위를 높이 잡는다. 자체 클립보드에 내용이 있으면 이를 우선한다.
//
//      return false; // 자체 클립보드의 내용을 처리해야 한다는 것을, 폴스 값을 리턴하여 알려준다.
//
//    }

    String data = getSytemClipboardBufferText();

    if( data == null ) {

      Utility.debug(this, "Clipboard content is null" );

    }

    HtmlDocument doc = getTargetDocument();

    if( doc == null ) {

      return false;

    }

//    Utility.debug(this, "CLIPBOARD DATA = " + data );

    final int si = doc.getStartIndex();
    final int ei = doc.getEndIndex();

    if( si != ei ) {

      doc.deleteSelectedCharacters();

    }

    doc.synchIndex();

    doc.processText( "" + data );

    doc.synchIndex();

    repaint(); // 에디터의 화면 리페인팅을 요청한다.

    return true; // 시스템 클립보드의 내용을 도큐먼트에 처리했음을 알려준다.
		  // 자체 클립보드의 내용이 없을 경우에만 가능하다.

  }


  final public void processKeyEvent(final KeyEvent e) {

       if( ! hasFocus() ) {

	  requestFocus();

       }

       if( ! isEditable() ) {

	  super.processKeyEvent( e );

	  return;

       }

       if( true ) { // 모든 키 이 벤트 타입을 다 검사한다.
		    // 키 인벤트의 필터링은 keyPressed(KeEvent e) 함수에서 한다.

	   final int key = e.getKeyCode();

	   final boolean isCtrlDown = e.isControlDown();

	   if( isCtrlDown && key == KeyEvent.VK_V ) {

	       keyPressed( e );

	       return;

	   } else if( isCtrlDown ) {
		// 컨트롤 키의 처리는 모두 상위 스윙 컴포넌트로 넘기지 않나다.

	       keyPressed( e );

	       if( e.getID() == e.KEY_PRESSED && e.isAltDown() && e.isShiftDown() ) {

		  if( key == e.VK_LEFT ) {

		      AppRegistry.DEBUG_FLAG = ! AppRegistry.DEBUG_FLAG;

		  } else if( key == e.VK_DELETE ) {

		      this.initTextInput();

		      this.doc.processText( WORDS );

		      this.doc.syncCaretStringElement( true );

		  }

	       }

	       return;

	   }

	   if( key == e.VK_ENTER || key == e.VK_SPACE || key == e.VK_TAB ) {
	       // 엔터 키, 탭 키, 스페이스 키 만 따로 처리함. 상위 스윙 컴포넌트로 넘겨 주지 않는다.

	       keyPressed( e );

	       return;

	   }

	   if(    key == e.VK_UP || key == e.VK_DOWN
	       || key == e.VK_PAGE_DOWN || key == e.VK_PAGE_UP
	       || key == e.VK_HOME || key == e.VK_END ) {

	       keyPressed( e );

	       return;

	   }

       }

       super.processKeyEvent( e );

  }

  // key listener methods

  final public void keyTyped(KeyEvent e) {

//     repaint();

  }

  final public void keyPressed(KeyEvent e) {

     if( ! isEditable() ) {

      return;

    }

    final HtmlDocument doc = this.getTargetDocument();

     if( doc == null ) {

	return;

     }

     if( e.getID() != e.KEY_PRESSED ) {

	// 눌러진 키 이벤트가 아니면 함수를 종료한다. 여기서.....

	return;

     }

     doc.processKeyEvent( e );

     setAutoScroll( true ); // 키보드가 눌러지면 항상 자동 스크롤 상태이다.

     int key = e.getKeyCode();

     if( key == e.VK_CONTROL || key == e.VK_SHIFT || key == e.VK_ALT
	 || key == e.VK_CAPS_LOCK || key == 0 || key == 263 )
     {
	// 컨트롤 키나 쉽트 키, 알트 키, 캡스 락 키가 입력되면 다시 리페인팅 하지 않는다.
	// 에디터 페이팅 문제 최적화이다.

	Utility.debug(this, "KEY = " + e.getKeyCode() );

	Utility.debug(this, "CTL, SHIFT, CAPS LOCK, WINDOW, CHINESE OR ALT KEY ONLY");

	return;

     }

//     Utility.debug(this, "KEY = " + e.getKeyCode() );

     repaint();

  }

  final public void keyReleased(final KeyEvent e) {
  }

  // End of key listener methods

  public Color getBackground() {

    if( this.doc != null ) {

      return this.doc.getFillColor();

    }

    return super.getBackground();

  }

  final public void requestValidation() {

      // this.getSize() 와 super.getSize()를 동기화 하여
      // 에디터의 전체 밸리데이션 문제를 해결한다.

      if( ! super.getSize().equals( this.getSize() ) ) {

	 super.setSize( this.getSize() );

      }

      getScroller().validate();

  }

  public void update(Graphics g) {

    paintComponent( g );

  }

  public void paintComponent(final Graphics g) {

    if( g == null ) {

	return;

     }

     if( isPainting ) {

	if( ! inited ) {

	  super.paintComponent( g );

	}

	return;

     }

     try {

	isPainting = true;

//	final Dimension ps = getSize(); // previouse size

//	super.paint( g );

	if( ! inited ) {

	    super.paintComponent( g );

	    initEditor();

	}

	paintEditor( g );

	if( ! inited ) {

	    inited = true;

	}

     } catch ( Exception e ) {

	Utility.debug( e );

     } finally {

	isPainting = false;

     }

  }

  final public boolean paintEditor(final Graphics g) {

     Utility.debug(this, "PAINTING EDITOR ......" );

     final HtmlDocument doc = this.doc; // top document

     final String text = this.getText();

     boolean emptyText = text.equals( "" );

     if( ! emptyText ){

	getTargetDocument().processText( text );

     }

//     Utility.debug( this, "Repainting DOC VIEW ....." );

     // 에디터 크기와 도큐 먼트 크기 최적화 (인셋 만큼 보정.....)

     final Dimension editorSize = getSize();

     justifyDocumentWidth( editorSize );

     // 끝. 에디터 크기와 도큐 먼트 크기 최적화

     // 에디터의 크기와 아버지 클래스인 텍스트 컴포넌트와의 크기를 동기화한다.

     final Dimension newEditorSize = this.getSize();

     if( ! super.getSize().equals( newEditorSize ) ) {

	super.setSize( newEditorSize );

     }

     // 끝. 상위 텍스트 컴포넌트와 크기 동기화.....

     final Graphics2D g2 = (Graphics2D) g;

     final Color color = g2.getColor();

     final Color bg = getBackground();

     g2.setColor( bg );

     g2.fillRect( 0, 0, newEditorSize.width, newEditorSize.height );

     g2.setColor( color );

     // 끝. 더블 버퍼링 준비

     // 최상위 도큐먼트 그리기

     // 도큐먼트 뷰를 먼저 페인팅한다.
     // 그래야만 희끗해지는 현상이 사라진다.

     doc.getHtmlDocView().paint( g2 );

     // 끝. 최상위 도큐먼트 그리기

     final EditorBorder border = this.editorBorder;

     border.paintTicks( g2 );

     g2.setColor( color );

     this.preText = text;

     // 작업 중인 XOR 도형 그리기...

     final Shape xorShape = ImageElement.XOR_AREA_SHAPE;

     final Color xorColor = ImageElement.XOR_COLOR;

     if( xorShape != null ) {

	 g2.setXORMode( xorColor );

	 g2.draw( xorShape );

	 g2.setPaintMode();

     }

     // 끝. 작업 중인 XOR 도형 그리기

     // 작업 중인 라인 그리기 도형 그리기

     final ShapeElement mouseWorkingShapeElement = ShapeElement.getMouseShapeElement();

     if( mouseWorkingShapeElement != null ) {

	g2.setXORMode( xorColor );

	mouseWorkingShapeElement.paint( g2 );

	g2.setPaintMode();

     }

     // 끝. 작업 중인 라인 그리기 도형 그리기

     // 커서 그리기

     // 도큐먼트에서 리턴하는 커서 칼라는 배경색이므로....
     // 초기에 커서를 그리기 위해서는 반드시 보색을 이용해서...
     // 커서를 페인팅 하여야 한다.

     paintCursor( g2, getTargetDocument(), true );

     // 끝. 커서 그리기.

     // 그 다음에 스크롤을 한다.

     autoScroll( getAbsoluteCursor() );

     // 스크롤 하기....

     // 스테이터스 메시지 출력하기

     paintStatus( this, g2 );

     // 끝. 스테이터스 메시지 출력하기

     return true;

  }

  final void autoScroll(final Shape absCursor) {

     if( ( ! isAutoScroll() ) || absCursor == null ) {
      // 스크롤바를 통해서 ..... 스크롤을 하거나...(즉, 사용자가 직접 스크롤하여 문서를 탐색하면...)
      // 커서 도형이 널이면 자동 스크롤을 하지 않는다.

      return;

     }

     final JHtmlEditorScrollPane scroller = getScroller();

     final Rectangle visRect = getVisibleRect();

//     Utility.debug(this, "CURR VISIBLE RECT = " + visRect );

     final HtmlDocument doc = this.doc;

     final Integer verticalScrollValue = getVerticalScrollValue( scroller, visRect, doc, absCursor );

     if( verticalScrollValue != null ) {

	final JScrollBar vBar = scroller.getVerticalScrollBar();

	vBar.setValue( verticalScrollValue.intValue() );

     }

     final Integer horizontalScrollValue = getHorizontalScrollValue( scroller, visRect, doc, absCursor );

     if( horizontalScrollValue != null ) {

	final JScrollBar hBar = scroller.getHorizontalScrollBar();

	hBar.setValue( horizontalScrollValue.intValue() );

     }

  }

  public void scrollBy(int w, int h ) {

     final JHtmlEditorScrollPane scroller = getScroller();

     if( w != 0 ) {

	final JScrollBar hBar = scroller.getHorizontalScrollBar();

	hBar.setValue( hBar.getValue() + w );

     }

     if( h != 0 ) {

	final JScrollBar vBar = scroller.getVerticalScrollBar();

	vBar.setValue( vBar.getValue() + h );

     }

  }

  final Integer getVerticalScrollValue( final JHtmlEditorScrollPane scroller,
				    final Rectangle visRect,
				    final HtmlDocument doc,
				    final Shape absCursor ) {

     final double topInset = doc.getScanMargin().top;

     final double bottomInset = AppRegistry.DOCUMENT_ADDITIONAL_BOTTOM_INSET;

     final Rectangle2D cursorBounds = absCursor.getBounds2D();

     final double cursorY = cursorBounds.getY();

     final double cursorH = cursorBounds.getHeight();

     final double visiH = visRect.height;

     double visiY = visRect.y;

     final double preVisiY = visiY;

     visiY = ( cursorY - topInset < visiY ) ? ( cursorY - topInset ) : visiY ;

     visiY = ( visiY + visiH < cursorY + cursorH + bottomInset )
	      ?    ( cursorY + cursorH + bottomInset - visiH + 2 ) // 무한 루프를 방지하기 위해서
		 : visiY ;                                           // 2 만큼 더하여 보정한다.

     visiY = visiY < 0 ? 0 : visiY; // 비쥬얼 수직 값은 양수이다.

     if( visiY == preVisiY ) {

	// 스크롤 할 필요가 없으면 함수를 종료한다. 여기서....

	return null;

     }

     return new Integer( (int) visiY );

  }

  final Integer getHorizontalScrollValue( final JHtmlEditorScrollPane scroller,
				    final Rectangle visRect,
				    final HtmlDocument doc,
				    final Shape absCursor ) {

     final double leftInset = 0;

     final double rightInset = 0;

     final Rectangle2D cursorBounds = absCursor.getBounds2D();

     final double cursorX = cursorBounds.getX();

     final double cursorW = cursorBounds.getWidth();

     final double visiW = visRect.width;

     double visiX = visRect.x;

     final double preVisiX = visiX;

     visiX = ( cursorX - leftInset < visiX ) ? ( cursorX - leftInset ) : visiX ;

     visiX = ( visiX + visiW < cursorX + cursorW + rightInset )
	      ?    ( cursorX + cursorW + rightInset - visiW + 2 ) // 무한 루프를 방지하기 위해서
		 : visiX ;                                           // 2 만큼 더하여 보정한다.

     visiX = visiX < 0 ? 0 : visiX; // 비쥬얼 수직 값은 양수이다.

     if( visiX == preVisiX ) {

	// 스크롤 할 필요가 없으면 함수를 종료한다. 여기서....

	return null;

     }

     return new Integer( (int) visiX );

  }

  // 현재 타겟 도큐먼트의 커서를 최상위 도큐먼트를 기준으로 한 커서 도형를 구한다.

  final Shape getAbsoluteCursor(){

     final HtmlDocument doc = getTargetDocument();

     if( doc == null ) {

	return null;

     }

     // 최상위 도큐먼트를 기준으로 한 커서를 구한다.

     return doc.getAbsoluteCursor();

  }

  final private void justifyDocumentWidth( final Dimension editorSize ) {

     this.doc.setDocumentWidth( editorSize.width );

  }

  // 에디터의 폭은 패런트 컨테이너의 비쥬얼 폭에 기준하여 맞춘다.
  // 이렇게 하면 꼭 끼어서 에디터가 페인트 되어진다.

//  private int getEditorWidth() {
//
//     JPanel con = (JPanel) getParent();
//
//     return con.getVisibleRect().width + 1;
//
//     // 수평 스크롤바가 멋있게 나타나도록 (파랗게 액티브하게) 하기 위해서 일만틈 보정한다.
//
//  }

  // 에디터의 폭은 스크롤페인의 폭에 기준하여 맞춘다. (스크롤 바 크기 만큼은 뺀다.)
  final private int getEditorWidth() {

     final JHtmlEditorScrollPane scroller = getScroller();

     final JScrollBar vBar = scroller.getVerticalScrollBar();

     final int calWidth = 2;

     // 조금더 작게 나타나서 수평 스크롤바가 꽉 끼여서 보이도록 calWidth 만큼 보정한다.

     return scroller.getSize().width - vBar.getSize().width - calWidth;

  }

  final private double getEditorHeight() {

     final double h = doc.getArea().getHeight();

     final JHtmlEditorScrollPane scroller = getScroller();

     final JScrollBar hBar = scroller.getHorizontalScrollBar();

     final int calHeight = 2;

     // 조금더 작게 나타나서 수직 스크롤바가 꽉 끼여서 보이도록 calHeight 만큼 보정한다.

     final double minH = scroller.getSize().height - hBar.getSize().height - calHeight;

     return ( h > minH ) ? h : minH;

  }

  final public void saveImages(final File rootDir, final Hashtable zipList ) {

     if( ! rootDir.exists() ) {

	rootDir.mkdir();

     }

     saveImagesOfHtmlDocument( this.doc, rootDir, zipList );

  }

  final private void saveImagesOfHtmlDocument(HtmlDocument doc, final File rootDir, final Hashtable zipList ) {

     final ImageElement [] ies = doc.getImageElementsArray();

     for(int i = 0, len = ies.length; i < len; i ++ ) {

	 final ImageElement ie = ies[ i ];

	 if( ie instanceof HtmlDocument ) {

	    final HtmlDocument ieDoc = (HtmlDocument) ie;

	    saveImagesOfHtmlDocument( ieDoc, rootDir, zipList );

	    continue;

	 }

	 if( ie.isLinkImage() ) {

	    continue;

	 }

	 final File src = ie.getFile();

	 final String name = src.getName();

	 final File out = new File( rootDir, name );

	 zipList.put( out, out );

	 String rootName = rootDir.getParent();

	 if( rootName == null ) {
	    rootName = "";
	 }

	 Utility.debug(this, "ROOTNAME = " + rootName );

	 if( rootName.endsWith( AppRegistry.FOLDER_DELIMITER) ) {

	     rootName += rootDir.getName();

	 } else {

	     rootName += AppRegistry.FOLDER_DELIMITER + rootDir.getName();

	 }

	 if( ! rootName.endsWith( AppRegistry.FOLDER_DELIMITER ) ) {

	    rootName += AppRegistry.FOLDER_DELIMITER;

	 }

	 String srcParentName = src.getParent();

	 if( srcParentName.endsWith( AppRegistry.FOLDER_DELIMITER + "." ) ) {

	    srcParentName = srcParentName.substring(0, srcParentName.length() - 1 );

	 }

	 if( ! srcParentName.endsWith( AppRegistry.FOLDER_DELIMITER ) ) {

	    srcParentName += AppRegistry.FOLDER_DELIMITER;

	 }

	 Utility.debug(this, "SRC PARENT = " + srcParentName + ", OUT ROOT = " + rootName );

	 if( srcParentName.endsWith( rootName ) && out.exists() ) {

	     Utility.debug(this, "SRC PARENT = " + src.getParent() + ", OUT ROOT = " + rootName );

	     continue;

	 }

	 // write file

	 Utility.debug(this, "SRC DIR = " + src.getPath() + ", OUT DIR = " + out.getPath() );

	 try {

	    new WebCopy( null ).copy( src, out );

	 } catch (IOException e) {

	    e.printStackTrace();

	 }

	 // end of file wrting
     }

  }

  final public Rectangle2D getArea() {

    final Dimension size = getSize();
    final Point loc = getLocation();

    return new Rectangle2D.Double( loc.x, loc.y, size.width, size.height );

  }

  final public String tag( final int zindex) {

      return this.doc.tag( zindex );

  }

  final public Color getTickColor() {

      return tickColor;

  }

  final private String [] getHrefAndTarget(final Component refComp, String refHref, String refTarget) {

      refHref = refHref == null ? "" : refHref;

      final String enterUrlText = "<html><font color=blue> Please enter an</font> <font color=red> URL </font> <font color = blue>to link! </font> </html>";

      final JTextField urlTF = new JTextField( refHref );

      final String defaultTarget = AppRegistry.DEFAULT_LINK_TARGET;

      final JTextField targetTF = new JTextField( );

      final String targetText = "<html><p align = center> <font color = blue> target </font> </p> </html>";

      final JComboBox targetCB = new JComboBox();

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
						 new Object [] { enterUrlText, urlTF, targetCB, targetText, targetTF },
						 "HTML LINK",
						 JOptionPane.DEFAULT_OPTION,
						 JOptionPane.INFORMATION_MESSAGE,
						 null,
						 options,
						 options[1]
						);

      if( result == 1 ) {

	return null;

      }

      final int selTargetCBIdx = targetCB.getSelectedIndex();

      final String target = selTargetCBIdx == 2 ? null : targetTF.getText();

      return new String [] { urlTF.getText(), target };

  }

  // Ctrl - C
  final public void copyToBuffer() {

     final HtmlDocument doc = this.getTargetDocument();

     if( doc == null ) {

	return;

     }

     doc.copyToBuffer();

//     bufferSourceEditor = this;

     repaint();

  }

  // Ctrl - X

  final public void cutToBuffer() {

     final HtmlDocument doc = this.getTargetDocument();

     if( doc == null ) {

	return;

     }

     int startIndex = doc.getStartIndex();
     int endIndex = doc.getEndIndex();

     Utility.debug(this, "CUT DOC = " + doc + ", SI = " + startIndex + ", EI = " + endIndex );
     Utility.debug(this, "SEL IMG = " + ImageElement.SEL_IMG_ELEM );

     final int mouseTopology = HtmlDocument.getMouseTopology();

     if( doc.isTableCell() && ( startIndex != endIndex )) {

	doc.cutToBuffer();

     } else if( doc.isTableCell() && ( startIndex == endIndex ) ) {

	// Do nothing!

     } else if(    doc.isWordBox()
		&& ( mouseTopology != ImageElement.INSIDE_AREA )
		&& ( mouseTopology != ImageElement.OUT_OF_AREA )
	      ) {

	// sie 와 타켓 도큐먼트가 같고 마우스 토폴로지가 영역안 값과 영역밖 값이 아닌 경우는
	// 워드 박스 잘라내기를 할 때이다.
	// 워드 박스를 잘라 내기 할때는
	// 아버지 워드 도큐먼트에서 잘라 내기를 해 주어야 한다.
	// 당근이지만....

//	Utility.debug( this, "CUTTING WORD BOX" );
//	Utility.debug( this, "MOUSE TOP = " + mouseTopology );

	doc.getParentDocument().cutToBuffer();

     } else {
     // 그 외의 이미지를 잘라내기 할 때......

	 doc.cutToBuffer();

     }

     // 에디터에서 커트를 하고 난 다음에는 선택된 이미지 객체를 널로 설정한다.
     // 에이터에서 커팅은 선택된 객체를 대상으로 이루어 지고,
     // 그 선택된 객체가 커팅되면 당연히 에디터 상에서 사라지므로.....

     ImageElement.SEL_IMG_ELEM = null;

     repaint();

  }

  // Ctrl - B
  final public void pasteFromBuffer() {

     final HtmlDocument doc = this.getTargetDocument();

     if( doc == null ) {

	return;

     }

     if( doc.getCopiedHtmlElements().size() > 0 ) {

	// 자체 클립 보드를 우선하여 페이스팅한다.

	doc.pasteFromBuffer();

	requestFocus();
	repaint();

	return;

	// 자체 클립 보드의 내용을 페이스팅 했으면 .....
	// 시스템 버퍼는 페이스팅 하지 않는다.

     }

  }

  // Ctrl - X
  final public void cut() {

  }

  public static File getTempDir(final Component comp) throws IOException{

      final File tempDir = AppRegistry.TEMP_DIR;

      if( ! tempDir.exists() ) {

	  tempDir.mkdir();

	  if( ! tempDir.exists() ) {

	      Utility.warningDialog( comp, "Cannot create temporary directory [" + tempDir.getCanonicalPath() + "]!", "CANNOT CREATE TEMP DIR" );

	      return null;

	  }

      }

      return tempDir;

  }

  final public String toString() {

    return "" + editorId;

  }

  final public Dimension getSize() {

    if( ! inited ) {

      return getParent().getSize();

    }

    return new Dimension( getEditorWidth(), (int) getEditorHeight() );

  }

  final public Dimension getPreferredSize() {

    return getSize();

  }

  final public void setHyperLink(Component comp) {

    final ImageElement sie = HtmlDocument.SEL_IMG_ELEM;

    // 도형이 선택되어져 있으면 도형은 하이퍼 링크를 걸지 않는다.

    if( sie != null && sie instanceof ShapeElement ) {

      return;

    }

    // 끝. 도형 하이퍼링크 안 걸기.

    HtmlDocument doc = null;

    if( sie instanceof HtmlDocument ) {

      doc = (HtmlDocument) sie;

    } else if( sie != null ) {

      doc = sie.getParentDocument();

    }

    if( doc == null ) {

      requestFocus();

      return;

    }

    doc.setHyperLink( comp );

    repaint();

    requestFocus();

  }

  /**
   * 맨 앞으로 가져오기, 맨 뒤로 보내기,
   * 앞으로 가져오기, 뒤로 보내기 함수이다.
   */
   // i = Integer.MIN_VALUE : 맨뒤로 보내기
   // i = Integer.MAX_VALUE : 맨 앞으로 가져오기
   // i = -1 : 뒤로 보내기
   // i = 1 : 앞으로 보내기

  final public void setMaxIndex() {

      setZindex( Integer.MAX_VALUE );

  }

  final public void setMinIndex() {

      setZindex( Integer.MIN_VALUE );

  }

  final public void setLargeIndex() {

     setZindex( 1 );

  }

  final public void setSmallIndex() {

    setZindex( -1 );

  }

  /**
   * 맨 앞으로 가져오기, 맨 뒤로 보내기,
   * 앞으로 가져오기, 뒤로 보내기 함수이다.
   */
   // i = Integer.MIN_VALUE : 맨뒤로 보내기
   // i = Integer.MAX_VALUE : 맨 앞으로 가져오기
   // i = -1 : 뒤로 보내기
   // i = 1 : 앞으로 보내기

   final private void setZindex(final int i) {

      ImageElement sie = ImageElement.SEL_IMG_ELEM ; // selected image element

      if( sie == null ) {

	  requestFocus();
	  return;

      }

      HtmlDocument pdoc = sie.getParentDocument() ; // parent document

      if( pdoc == null ) {

	  requestFocus();
	  return;

      }

      pdoc.setZindex( sie, i );

      pdoc.requestNewHtmlDocView();

      repaint();

      requestFocus();

  }

  public boolean editImageStyle( ) {

	final ImageElement sie = ImageElement.SEL_IMG_ELEM;

	if( sie == this.doc ) {

	    return true; // 선택된 객체가 에디터의 최상위 객체이면...
			 // 스타일 에디팅을 하지 않는다.
			 // 이벤트 처리가 정상적으로 종료된다.

	} else if( sie instanceof HtmlDocument ) {

	   final HtmlDocument doc = (HtmlDocument) sie;

	   if( doc.isTableCell() ) { // 테이블 셀인 경우에는 이미지 스타일 형식을 편집하지 않는다.

	      return true;

	   }

	}

	final String styleEditorTitle = sie instanceof HtmlDocument ? "텍스트 상자 서식" : "그림 서식";

	int style = StyleEditorPane.getStyle( getScroller(), styleEditorTitle, sie.getStyle() );

	if( style == -1 || style == sie.getStyle() ) {

	   // 사용자가 스타일 에디터 다이얼로그에서 취소 버튼을 눌렀을 때.....
	   // -1 의 값이 넘어 오므로 스타일 설정을 하지 않는다.
	   // 혹, 현재 스타일의 값과 같을 경우에는 ....
	   // 스타일 설정을 하지 않는다.

	   requestFocus();

	   return true;

	   // 마우스 이벤트 처리가 완료 되어 도큐먼트 엘리먼트에서는
	   // 더 이상 마우스 처리를 햐여 주지 않는다.
	   // 참 값을 넘기면 도큐먼트 엘리먼트에서는 마우스 이벤트 처리를
	   // 하지 않는다는 신호로 받아 들인다.
	   // 그러므로, 참을 넘길지 거짓을 넘길 지는 주의하여 판단하여야 한다.

	}

	sie.setStyle( style );

	sie.getParentDocument().requestNewHtmlDocView();

	repaint();

	requestFocus();

	return true; // 마우스 이벤트 처리가 완료 되어 도큐먼트 엘리먼트에서는
			      // 더 이상 마우스 처리를 햐여 주지 않는다.
			      // 참 값을 넘기면 도큐먼트 엘리먼트에서는 마우스 이벤트 처리를
			      // 하지 않는다는 신호로 받아 들인다.
			      // 그러므로, 참을 넘길지 거짓을 넘길 지는 주의하여 판단하여야 한다.


  }

  final public void mouseClicked(MouseEvent e) {
  }

  final public void mousePressed(MouseEvent e) {

    if( ! isEditable() ) {

      return;

    }

    final int mode = this.mode.getMode();

    doc.processMouseEvent( e );

    ImageElement.LAST_MOUSE_EVENT = e;

    if( mode == Mode.ADD_SHAPE || mode == Mode.ADD_WORD_BOX || mode == Mode.ADD_TABLE ) {

      Utility.debug( this, "ADD SHAPE(WORD BOX, TABLE) MODE" );

      setAutoScroll( false );

      // Do nothing !

    } else {

      setAutoScroll( true ); // 에디터 내에서 마우스를 클릭하면 자동 스크롤을 가능하게 한다.

    }

    repaint();

  }

  final public void mouseReleased(MouseEvent e) {

    if( ! isEditable() ) {

      return;

    }

    doc.processMouseEvent( e );

    ImageElement.LAST_MOUSE_EVENT = null;

    repaint();

  }

  final public void mouseEntered(MouseEvent e) {
  }

  final public void mouseExited(MouseEvent e) {
  }

  final public void mouseDragged(MouseEvent e) {

    if( ! isEditable() ) {

      return;

    }

    doc.processMouseEvent( e );

  }

  final public void mouseMoved(MouseEvent e) {

    if( ! isEditable() ) {

      return;

    }

    lastMoveMouseEvent = e;

    doc.processMouseEvent( e );

  }

  final public void initTextInput() {

    setText( "" );

    preText = "";

  }

  final public void hideEditPopupMenu() {

    if( editPopupMenu.isShowing() ) {

      editPopupMenu.setVisible( false );

    }

  }

  final public void showEditPopupMenu( final KeyEvent e ) {

    MouseEvent me = this.lastMoveMouseEvent;

    if( me == null ) {

      me = new MouseEvent( this, 0, 0, 0, 100, 100, 1, true );

    }

    showEditPopupMenu( me );

  }

  final public void showEditPopupMenu( final MouseEvent e ) {

    editPopupMenu.show( e );

  }

  final public boolean isEditPopupMenuShowing() {

    return editPopupMenu.isShowing();

  }

  final public String getPreText() {

    return preText;

  }

  final protected HtmlDocument getTargetDocument() {

    final ImageElement sie = ImageElement.SEL_IMG_ELEM;

    return ( sie instanceof HtmlDocument ) ? ( (HtmlDocument) sie ) : this.doc;

  }

   // thread running method for cursor blinker

  final public void run() {

    cursorBlinker.setPriority( Thread.MIN_PRIORITY );

    final int blinkTime = 500;

    // 포커스를 가질 경우에만.... 커서를 블링킹한다.
    final HtmlEditorPane editor = this;

    final Thread cursorBlinker = this.cursorBlinker;

    boolean complementaryColor = true;

    for( ; ; ) {

      try {

	  cursorBlinker.sleep( blinkTime );

	  complementaryColor = ! complementaryColor;

	  final JHtmlEditorScrollPane scroller = getScroller();

	  final Graphics2D g2 = (Graphics2D) editor.getGraphics();

	  if( g2 == null || scroller == null || isPainting ) {

	    continue;

	  }

	  final HtmlDocument doc = getTargetDocument();

	  // 에디터가 포커스가 잡히고....
	  // 타겟 도큐먼트가 널이 아니고...
	  // 모드가 에디터이고
	  // 캐릿 칼라가 널이 아닐 경우에만...
	  // 커서를 블링킹한다.

//	  Utility.debug(this, "FOCUS = " + editor.hasFocus() );

	  if( ( ! editor.hasFocus() ) || doc == null || ( ! mode.isMode( Mode.EDIT ) ) ) {

	      continue;

	  }

	  // 최상위 도큐먼트를 기준으로 한 절대 좌표의 커서 도형을 구한다.

	  if( ! paintCursor( g2, doc, complementaryColor ) ) {

	       continue;

	  }

      } catch (Exception e) {

	Utility.debug( e );

      }

    }

  }

  private boolean paintCursor(final Graphics2D g2, final HtmlDocument doc,
			      final boolean complementaryColor ) {

     final Shape cursor = doc.getAbsoluteCursor();

     final Rectangle editorViewRect = getVisibleRect();

     Color color = doc.getCaretColor();

     if( color == null || editorViewRect == null || cursor == null || ! editorViewRect.contains( cursor.getBounds2D() ) ) {

	 return false;

     }

     color = complementaryColor ? Utility.getComplemetaryColor( color ) : color;

     final Color pc = g2.getColor();

     g2.setColor( color );

     g2.fill( cursor );

     g2.setColor( pc );

     return true;

  }

  // 드래그 앤드 드랍 코드
  // 요 밑에서 조오기 까지는 베타 쓰리에 들어가는 드래그 앤드 드랍 코드이다.


	// Implementation of the DropTargetListener interface
	public void dragEnter(DropTargetDragEvent dtde) {
		DnDUtils.debugPrintln("dragEnter, drop action = "
						+ DnDUtils.showActions(dtde.getDropAction()));

		// Get the type of object being transferred and determine
		// whether it is appropriate.
		checkTransferType(dtde);

		// Accept or reject the drag.
		acceptOrRejectDrag(dtde);
	}

	public void dragExit(DropTargetEvent dte) {
		DnDUtils.debugPrintln("DropTarget dragExit");
	}

	public void dragOver(DropTargetDragEvent dtde) {
		DnDUtils.debugPrintln("DropTarget dragOver, drop action = "
						+ DnDUtils.showActions(dtde.getDropAction()));

		// Accept or reject the drag
		acceptOrRejectDrag(dtde);
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
		DnDUtils.debugPrintln("DropTarget dropActionChanged, drop action = "
						+ DnDUtils.showActions(dtde.getDropAction()));

		// Accept or reject the drag
		acceptOrRejectDrag(dtde);
	}

	public void drop(DropTargetDropEvent dtde) {
		DnDUtils.debugPrintln("DropTarget drop, drop action = "
						+ DnDUtils.showActions(dtde.getDropAction()));

		// Check the drop action
		if ((dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0) {
			// Accept the drop and get the transfer data
			dtde.acceptDrop(dtde.getDropAction());
			Transferable transferable = dtde.getTransferable();

			try {
				boolean result = dropFile(transferable);

				dtde.dropComplete(result);
				DnDUtils.debugPrintln("Drop completed, success: " + result);
			} catch (Exception e) {
				DnDUtils.debugPrintln("Exception while handling drop " + e);
				dtde.dropComplete(false);
			}
		} else {
			DnDUtils.debugPrintln("Drop target rejected drop");
			dtde.rejectDrop();
		}
	}

	// Internal methods start here

	protected boolean acceptOrRejectDrag(DropTargetDragEvent dtde) {
		int dropAction = dtde.getDropAction();
		int sourceActions = dtde.getSourceActions();
		boolean acceptedDrag = false;

		DnDUtils.debugPrintln("\tSource actions are " +
							DnDUtils.showActions(sourceActions) +
							", drop action is " +
							DnDUtils.showActions(dropAction));

		// Reject if the object being transferred
		// or the operations available are not acceptable.
		if (!acceptableType ||
			(sourceActions & DnDConstants.ACTION_COPY_OR_MOVE) == 0) {
			DnDUtils.debugPrintln("Drop target rejecting drag");
			dtde.rejectDrag();
		} else if ((dropAction & DnDConstants.ACTION_COPY_OR_MOVE) == 0) {
			// Not offering copy or move - suggest a copy
			DnDUtils.debugPrintln("Drop target offering COPY");
			dtde.acceptDrag(DnDConstants.ACTION_COPY);
			acceptedDrag = true;
		} else {
			// Offering an acceptable operation: accept
			DnDUtils.debugPrintln("Drop target accepting drag");
			dtde.acceptDrag(dropAction);
			acceptedDrag = true;
		}

		return acceptedDrag;
	}

	protected void checkTransferType(DropTargetDragEvent dtde) {
		// Only accept a list of files
		acceptableType = dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor);

		DnDUtils.debugPrintln("File type acceptable - " + acceptableType);

	}

	// This method handles a drop for a list of files
	protected boolean dropFile(Transferable transferable)
			throws IOException, UnsupportedFlavorException,
			       MalformedURLException {

		if( ! isEditable() ) {

		    return true;

		}

		java.util.List fileList = (java.util.List)transferable.getTransferData(
								DataFlavor.javaFileListFlavor
								);

		final File transferFile = (File)fileList.get(0);
		final URL transferURL = transferFile.toURL();
		DnDUtils.debugPrintln("File URL is " + transferURL);

		final String fileName = transferFile.getName();

		if( fileName == null ) {

		  // Do nothing!

		} else if( fileName.endsWith( ".TXT" ) || fileName.endsWith( ".txt"  ) ){

		  prepareEditorForDragAndDrop();

		  final String text = FileUtilities.readString( transferFile );

		  this.doc.processText( text );

		} else if( FileManager.isImageFileExtension( transferFile ) ) {

		  prepareEditorForDragAndDrop();

		  this.doc.addImage( transferFile );

		} else if( FileManager.isVideoFileExtension( transferFile ) ) {

		  prepareEditorForDragAndDrop();

		  this.doc.addVideo( transferFile );

		} else if( FileManager.isAudioFileExtension( transferFile ) ) {

		  prepareEditorForDragAndDrop();

		  this.doc.addAudio( transferFile );

		}

		this.repaint();

		return true;

	}

	// 드랙 앤 드랍 전에 에디터를 준비 시킴

	private void prepareEditorForDragAndDrop() {

		  initTextInput();

		  final HtmlDocument targetDoc = getTargetDocument();

		  targetDoc.syncCaretStringElement( true );

		  this.doc.syncCaretStringElement( true );

	}

  // 끝. 드래그 앤드 드랍 코드.

  // 라인 그리는 함수들

  public void addShape( final int shapeType ) {

    setAutoScroll( false );

    mode.setMode( Mode.ADD_SHAPE );

    ShapeElement.MOUSE_SHAPE_TYPE = shapeType;

    ShapeElement.removeAllMousePoints();

    requestFocus();

  }

  public void applyArrowType( final int arrowType ) {

    final ImageElement sie = ImageElement.SEL_IMG_ELEM;

    if( ! ( sie instanceof ShapeElement ) ) {

      return;

    }

    final ShapeElement shapeElement = (ShapeElement) sie;

    if( ! shapeElement.isArrowShapeElement() ) {

      return;

    }

    int trueArrowType;

    switch( arrowType ) {
      case 0 :
	trueArrowType = ShapeElement.ZERO_ARROW_LINE;
	break;
      case 1:
	trueArrowType = ShapeElement.FOREWARD_ONE_ARROW_LINE;
	break;
      case 2:
	trueArrowType = ShapeElement.BACKWARD_ONE_ARROW_LINE;
	break;
      case 3:
	trueArrowType = ShapeElement.TWO_ARROW_LINE;
	break;
      default:
	trueArrowType = ShapeElement.ZERO_ARROW_LINE;
	break;
    }

    shapeElement.setShapeType( trueArrowType );

    requestFocus();

    repaint();

  }

  public void addShapeElement( final ShapeElement shapeElement) {

    final HtmlDocument doc = this.doc;

    final ShapeElement cloneShape = new ShapeElement( doc,
						      shapeElement.getPointList(),
						      shapeElement.getShapeType(),
						      shapeElement.getFillColor(),
						      shapeElement.getLineColor(),
						      shapeElement.getThickness()
						     );

    doc.addShapeElement( cloneShape );

  }

  // 끝. 라인 그리는 함수들

  final protected void showDocumentInfoVisually( final HtmlDocument doc) {

    if( doc == null ) {

      return;

    }

    final HtmlFreeEditorMenuPane menuPane = getMenuPane();

    if( menuPane == null ) {
      return;
    }

    menuPane.showDocumentInfoVisually( doc );

  }

  private HtmlFreeEditorMenuPane getMenuPane() {

    return (HtmlFreeEditorMenuPane) Utility.getAncestorOfClass( HtmlFreeEditorMenuPane.class, this );

  }

  private void paintStatusSmoothly() {

    final long then = this.paintStatusTime;

    final long now = System.currentTimeMillis();

    if( Math.abs( now - then ) < 1000 ) {

      return;

    }

    paintStatus();

  }

  private void paintStatus( ) {

    final Component comp = this;

    paintStatus( comp, (Graphics2D) comp.getGraphics() );

  }

  private void paintStatus( final Component comp, final Graphics2D g2 ) {

    if( g2 == null ) {

      return;

    }

    if( ! isProgressActive() ) {

      return;

    }

    this.paintStatusTime = System.currentTimeMillis();

//    final JScrollPane scroller = this.getScroller();

    final HtmlEditorPane editor = this;

    final Rectangle visiRect = editor.getVisibleRect();

//    final Dimension size = editor.getSize();
//
//    final Point cp = new Point( size.width/2, size.height/2 );

    final Point cp = new Point(
				visiRect.x + visiRect.width/2,
				visiRect.y + visiRect.height/2
			      );

    paintLogoImage( g2, cp );

    paintProgressiveBar( g2, cp );

    paintStatusMessage( g2, cp );

  }

  private void paintLogoImage( final Graphics2D g2, final Point cp ) {

    final Image logoImage = this.logoImage;

    final int w = logoImage.getWidth( this );

    final int h = logoImage.getHeight( this );

    g2.drawImage( logoImage, cp.x - w/2, cp.y - h/2, this );

    g2.setColor( Color.black );

    g2.drawRect( cp.x - w/2, cp.y - h/2, w - 1, h -1 );


  }

  private void paintProgressiveBar( final Graphics2D g2, final Point cp ) {

    Dimension mts = this.messageTextSize;

    final int status = this.status;

    if( status < 0 ) {

      return;

    }

    g2.setColor( Color.white );

    g2.fillRect( cp.x - mts.width/2, cp.y - mts.height/2, mts.width, mts.height );

    int w = (int)( (mts.width + 0.0)*(status + 0.0)/100.0 );

    g2.setColor( Color.blue );

    g2.fillRect( cp.x - mts.width/2, cp.y - mts.height/2, w, mts.height );

    g2.drawRect( cp.x - mts.width/2, cp.y - mts.height/2, mts.width, mts.height );

  }

  private void paintStatusMessage( final Graphics2D g2, final Point cp ) {

    String statusMessage = this.statusMessage;

    if( statusMessage == null ) {

      statusMessage = AppRegistry.APP_NAME;

    }

    Font font = AppRegistry.LOGO_MESSAGE_FONT;

    g2.setFont( font );

    FontMetrics fm = g2.getFontMetrics( font );

    int fa = fm.getAscent();

    g2.setColor( Color.black );

    final Dimension messageTextSize = this.messageTextSize;

    g2.drawString(

	 statusMessage,
	 cp.x - fm.stringWidth( statusMessage )/2,
	 cp.y + fa/2

    );

  }

  public void setValue( int i ) {

    this.status = i;

//    Utility.debug( this, "VAL = " + i );

    paintStatusSmoothly();

  }

  public void setString( String text ) {

    this.statusMessage = text;

//    Utility.debug( this, "STATUS = " + text );

    paintStatusSmoothly();

  }

  public void hideProgress() {

    this.status = -1;

    this.statusMessage = null;

    repaint( 1 );

  }

  public boolean isProgressActive() {

    return ( statusMessage != null ) || ( status > -1 );

  }

}
