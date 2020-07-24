package htmleditor;


/**
 * Title:        Test Publishing System
 * Description:  Internet Based Test Publishing System V1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import jcosmos.*;


public class HtmlFreeEditorApplet extends JApplet implements GetParameterInterface{

  private HtmlFreeEditorPane htmlFreeEditorPane;

  private boolean inited;
  private String serverZip;

  public HtmlFreeEditorApplet() {

      Container con = this.getContentPane();

      con.setBackground( Color.white );

      con.setLayout( new BorderLayout(1, 1) );

      con.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) ) ;

  }

  private HtmlFreeEditorPane getHtmlFreeEditorPane() {

      return this.htmlFreeEditorPane;

  }

  @Override
public void paint(Graphics g) {

     if( ! this.inited ) {

	super.paint( g );

	this.initUI();

	this.inited = true;

	if( this.serverZip != null ) {

	  this.open_server_doc();

	}

	this.getContentPane().setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) ) ;

     }

     super.paint( g );

  }

  // SWING MENU MODE

  private void initUI() {

      Utility.debug(this, "INIT UI" );

      HtmlFreeEditorMenuPane htmlFeeEditorMenuPane = new HtmlFreeEditorMenuPane();

      this.htmlFreeEditorPane = htmlFeeEditorMenuPane.getHtmlFreeEditorPane();

      super.getContentPane().add( htmlFeeEditorMenuPane, BorderLayout.CENTER )  ;

      this.validate();

      this.showStatus( AppRegistry.HTML_FREE_INIT_MSG );

  }

  @Override
public void stop() {

     super.stop();

  }

  private void setBaseDirUrl( String baseDirUrl ) {

      if( baseDirUrl == null || baseDirUrl.length() < 1 ) {

	 // Do nothing!

      } else if( ! baseDirUrl.endsWith("/") ) {

	 // 폴더 딜리미터 체크

	 baseDirUrl += "/";

	 AppRegistry.RSC_BASE_DIR_URL = baseDirUrl;

	 // 끝. 폴더 딜리미터 체크

      }

  }

  // content accessor methods

  public String get_base64_html_contents( String baseDirUrl ) {

    return new String( Base64Encoder.encode( this.getByteHtmlContent(baseDirUrl) ) );

  }

  public String get_html_contents( String baseDirUrl ) {

    return new String( this.getByteHtmlContent( baseDirUrl ) );

  }

  public String get_base64_zip_contents(String baseDirUrl ) {

     return new String( Base64Encoder.encode( this.getByteZipContent(baseDirUrl) ) );
  }

  public String get_zip_contents(String baseDirUrl) {

    return new String( this.getByteZipContent( baseDirUrl ) );

  }

  // end of content accessor method

  private byte [] getByteHtmlContent( String baseDirUrl ) {

      HtmlFreeEditorPane editorPane = this.getHtmlFreeEditorPane();

      if( editorPane == null ) {

	return new byte[] { };

      }

      // 베이스 URL 설정
      this.setBaseDirUrl( baseDirUrl );
      // 끝. 베이스 URL 설정

      File [] contentFiles = editorPane.saveAsTemporaryFile();

      AppRegistry.RSC_BASE_DIR_URL = null;

      if( contentFiles == null ) {

	  return new byte [] { };

      }

      return FileUtilities.readBytes( contentFiles[ 0 ] ); // 영번째가 HTML 파일임.

  }

  private byte [] getByteZipContent(String baseDirUrl ) {

      HtmlFreeEditorPane editorPane = this.getHtmlFreeEditorPane();

      if( editorPane == null ) {

	return new byte[] { };

      }

      File [] contentFiles = editorPane.saveAsTemporaryFile();

      AppRegistry.RSC_BASE_DIR_URL = null;

      if( contentFiles == null ) {

	  return new byte [] { };

      }

      return FileUtilities.readBytes( contentFiles[ 1 ] ); // 일번째가 HTML 파일임.

  }

  public void open( ) {

//	  System.out.println( "NEW DOCUMENT" );

	 SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.openFile();

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void set_new( ) {

//	  System.out.println( "NEW DOCUMENT" );

	 SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.setNewDocument();

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void save( ) {

     SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.saveHtml( );

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void save( final String dirName, final String fileName ) {

     SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.saveHtml( dirName, fileName );

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void italic( ) {

     SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.applyItalic();

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void under_line( ) {

     SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.applyUnderLine();

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void text_color( ) {

     SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.applyTextColor();

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void fill_color( ) {

     SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.changeFillColor();

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void line_color( ) {

     SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.changeLineColor();

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void insert_table( ) {

     SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.addTable();

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void insert_word_box( ) {

     SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.addWordBox();

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void insert_image( ) {

     SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.addImage();

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void insert_link_image( ) {

     SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.addLinkImage();

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void insert_multi_media( ) {

     SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.addMultiMemia();

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void insert_chart( ) {

     SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.addChart();

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void html_link( ) {

     SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.setHyperLink();

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void set_font( final String fontNameText, final String fontSizeText ) {

     SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.applyFont( fontNameText, Font.PLAIN, fontSizeText );

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void set_border_width( final String borderWidthText ) {

     SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.applyBorderWidth( borderWidthText );

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public void open_server_doc( final String urlText ) {

	 Utility.debug( this, "SERVER ZIP FILE = " + urlText );

	 SwingUtilities.invokeLater(

	 new Runnable() {

	    public void run() {

		try {

		    HtmlFreeEditorPane.setHasSavedAsFile( true );

		    HtmlFreeEditorPane editorPane = HtmlFreeEditorApplet.this.getHtmlFreeEditorPane();

		    if( editorPane == null ) {

			return;

		    }

		    editorPane.openServerZipFile( urlText );

		} catch (Exception e) {

		    Utility.debug(e );

		}

	    }
	 }

     );

  }

  public String get_only_texts() {

    return this.htmlFreeEditorPane.getOnlyTexts();

  }

  @Override
public void init() {

//      System.out.println( "APPLET INIT" );

      super.init();

      System.out.println( AppRegistry.APP_NAME );

      System.out.print( "INITIATING FILE CHOOSER......." );

      htmleditor.HtmlEditorPane.initImageFileChooser();

      System.out.println( " DONE!" );

      AppRegistry.APPLET_MODE = true;
      AppRegistry.HTML_APPLET = this;

      // sets debug flag from parameter

      final String debugFlag = this.getParameter( "debug" );

      if( debugFlag != null && debugFlag.equalsIgnoreCase( "TRUE" ) ) {

	 AppRegistry.DEBUG_FLAG = true;

      } else {

	 AppRegistry.DEBUG_FLAG = false;

      }

      // end of setting debug flag

      // sets character set

      final String charSet = this.getParameter("charset" );

      if( charSet != null ) {

	AppRegistry.CHARSET = charSet;

//	System.out.println( "CHARSET = " + charSet );

      }

      // end of setting character set

      // sets server zip document

      final String serverZip = this.getParameter( "doc" );

      if( serverZip != null ) {

	this.serverZip = serverZip;

      }

      // end of setting server zip document

      // sets additional body tag

      final String additionalBodyTag = this.getParameter( "bodytag" );

      if( additionalBodyTag != null ) {

	AppRegistry.HTML_BODY_ADDITIONAL_TAG = additionalBodyTag;

	Utility.debug(this, "ADDIONAL BODY TAG = " + additionalBodyTag );

      }

      // end fo setting additional body tag

      // document margin

      final String docMargin = this.getParameter( "doc_margin" );

      if( docMargin != null ) {

	String marginTxt [] = Utility.parseString( docMargin, "," );

	try {

	  int margin [] = new int[ 4 ];

	  for(int i = 0; i < 4; i ++ ) {

	    margin[ i ] = new Integer( marginTxt[ i ].trim() ).intValue();

	  }

	  AppRegistry.EDITOR_MARGIN = new Insets( margin[0], margin[1], margin[2], margin[3] );


	} catch (Exception e) {

	  Utility.debug( e );

	}

      }

      // end of document margin

      // sets forced new document

      final String forcedNewDocument = this.getParameter( "forced_new_document" );

      if( forcedNewDocument != null && forcedNewDocument.equalsIgnoreCase( "TRUE" ) ) {

	AppRegistry.FORCED_NEW_DOCUMENT = true;

	Utility.debug(this, "forced_new_document = " + forcedNewDocument );

      } else if( forcedNewDocument != null && forcedNewDocument.equalsIgnoreCase( "FALSE" ) ) {

	AppRegistry.FORCED_NEW_DOCUMENT = false;

	Utility.debug(this, "forced_new_document = " + forcedNewDocument );

      }

      // end fo setting forced new document

      // set menu buttons' visibility

      setMenuButtonVisibilities( this );

      // end of setting menu buttons' visibility

      // default font name setting

      final String defaultFontName = this.getParameter( "default_font_name" );

      if( defaultFontName != null && defaultFontName.length() > 0 ) {

	AppRegistry.DEFAULT_FONT_NAME = defaultFontName;

	Utility.debug(this, "default font name = " + defaultFontName );

      }

      // end of setting default font name

      // default font size setting

      final String defaultFontSize = this.getParameter( "default_font_size" );

      if( defaultFontSize != null ) {

	try {

	  AppRegistry.DEFAULT_FONT_SIZE = new Integer( defaultFontSize ).intValue();

	  Utility.debug(this, "default font size = " + AppRegistry.DEFAULT_FONT_SIZE );

	} catch (Exception e) {

	  Utility.debug( e );
	}

      }

      // end of setting default font size

      // default font size setting

      final String defaultTableFontSize = this.getParameter( "default_table_font_size" );

      if( defaultTableFontSize != null ) {

	try {

	  AppRegistry.DEFAULT_TABLE_CELL_FONT_SIZE = new Integer( defaultTableFontSize ).intValue();

	  Utility.debug(this, "default table font size = " + AppRegistry.DEFAULT_TABLE_CELL_FONT_SIZE );

	} catch (Exception e) {

	  Utility.debug( e );

	}

      }

      // end of setting default font size

  }

  public static void setMenuButtonVisibilities( GetParameterInterface obj ) {

      final String openParam = obj.getParameter( "open" );

      if( openParam != null && openParam.equalsIgnoreCase( "FALSE" ) ) {

	AppRegistry.OPEN = false;

      }

      final String newParam = obj.getParameter( "new" );

      if( newParam != null && newParam.equalsIgnoreCase( "FALSE" ) ) {

	AppRegistry.NEW = false;

      }

      final String saveParam = obj.getParameter( "save" );

      if( saveParam != null && saveParam.equalsIgnoreCase( "FALSE" ) ) {

	AppRegistry.SAVE = false;

      }

      final String tableParam = obj.getParameter( "table" );

      if( tableParam != null && tableParam.equalsIgnoreCase( "FALSE" ) ) {

	AppRegistry.TABLE = false;

      }

      final String wordBoxParam = obj.getParameter( "word_box" );

      if( wordBoxParam != null && wordBoxParam.equalsIgnoreCase( "FALSE" ) ) {

	AppRegistry.WORD_BOX = false;

      }

      final String imageParam = obj.getParameter( "image" );

      if( imageParam != null && imageParam.equalsIgnoreCase( "FALSE" ) ) {

	AppRegistry.IMAGE = false;

      }

      final String chartParam = obj.getParameter( "chart" );

      if( chartParam != null && chartParam.equalsIgnoreCase( "FALSE" ) ) {

	AppRegistry.CHART = false;

      }

      final String multiMediaParam = obj.getParameter( "multi_media" );

      if( multiMediaParam != null && multiMediaParam.equalsIgnoreCase( "FALSE" ) ) {

	AppRegistry.MULTI_MEDIA = false;

      }

      final String linkImageParam = obj.getParameter( "link_image" );

      if( linkImageParam != null && linkImageParam.equalsIgnoreCase( "FALSE" ) ) {

	AppRegistry.LINK_IMAGE = false;

      }

      final String captureParam = obj.getParameter( "capture" );

      if( captureParam != null && captureParam.equalsIgnoreCase( "FALSE" ) ) {

	AppRegistry.CAPTURE = false;

      }

      final String recordParam = obj.getParameter( "record" );

      if( recordParam != null && recordParam.equalsIgnoreCase( "FALSE" ) ) {

	AppRegistry.RECORD = false;

      }

      final String shapesParam = obj.getParameter( "shapes" );

      if( shapesParam != null && shapesParam.equalsIgnoreCase( "FALSE" ) ) {

	AppRegistry.SHAPES = false;

      }

      final String helpParam = obj.getParameter( "help" );

      if( helpParam != null && helpParam.equalsIgnoreCase( "HELP" ) ) {

	AppRegistry.HELP = false;

      }

  }

  private void open_server_doc() {

    new Thread() {

      @Override
	public void run() {

	HtmlFreeEditorApplet.this.open_server_doc( HtmlFreeEditorApplet.this.serverZip );

      }

    }.start();

  }

  @Override
public void start() {

     // 애플릿이 다시 시작할 때 디버그 모드로 들어가는 것을
     // 확실하게 막기 위해서 폴스 값으로 설정한다.
     AppRegistry.DEBUG_FLAG = false;

     this.repaint();

  }

  public static void main(String [] args) {

    String appType = null ;

    Utility.debug( HtmlFreeEditorApplet.class , "ARGS LEN = " + args.length );

    HtmlFreeBoardPane.main( args );

//    if( args.length > 0 ) {
//
//      appType = args[ 0 ];
//
//    } else {
//
//      appType = AppRegistry.BOARD_APP_TYPE;
//
//    }
//
//    if( appType == null || appType.equalsIgnoreCase( AppRegistry.EDITOR_APP_TYPE ) ) {
//
//      HtmlFreeEditorMenuPane.main( args );
//
//    } else {
//
//      HtmlFreeBoardPane.main( args );
//
//    }

  }

  public void callHelpJScript() {



  }

}
