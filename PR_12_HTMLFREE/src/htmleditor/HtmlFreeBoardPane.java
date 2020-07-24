package htmleditor;

/**
 * Title:        HTML FREE EDITOR
 * Description:  Html Free Editor v2.0
 * Copyright:    Copyright (c) 2001
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import jcosmos.*;
import java.net.*;
import java.io.*;
import java.util.*;
//import HtmlFreeEditorApplet;

public class HtmlFreeBoardPane extends JPanel implements GetParameterInterface, Runnable {

  private JPanel contentPane;
  private HtmlFreeEditorMenuPane htmlFreeMenuPane;

  private Font northPaneFont = AppRegistry.BOARD_NORTH_PANE_FONT;

  private Color northPaneColor = StringView.fromHtmlColor( "#E6E6D2" );

  private JPanel  northPane;
  private JPanel      northFirstRowPane;
  private JTextField      titleTF;
  private JPanel      northSndRowPane;
  private JTextField      eMailTF;
  private JPanel          nickNamePane;
  private JTextField      nickNameTF;
  private JPanel      northThrdRowPane;
  private JRadioButton       noIconCB, agreeCB, denyCB, thxCB;
  private int             selectedJCheckBoxIndex = 1;
  private JTextField stockCodeTF;

  private JPanel southPane;
  private JPanel      southFirstRowPane;
  private JTextField      fileAttachTFOne;
  private JButton         fileAttachBTNOne;
  private JPanel      southSndRowPane;
  private JTextField      fileAttachTFTwo;
  private JButton         fileAttachBTNTwo;
  private JPanel      southThrdRowPane;
  private JButton         registerBTN, cancelBTN;
  private JFileChooser fileChooser = new JFileChooser();

  private String [] args;

  private static final int STANDARD_BOARD_MODE = 0, MODIFY_BOARD_MODE = 1, REPLY_BOARD_MODE = 2;
  private int boardMode= STANDARD_BOARD_MODE ;
  private String title = "";
  private String eMail = "";
  private int subLimit = 48;
  private int emLimit = 30;
  private File attachFileOne;
  private File attachFileTwo;

  private CgiVariableArgs cgiVariableArgs = new CgiVariableArgs();
  private String saveCgiBinUrlText;
  private String userCookieText;
  private String modifiedHfzUrlText;
  private File downLoadedSerZipFile;
  private File tempHtmlFile;

  private boolean inited;
  private String hiddenMenuParamText;
  private String paxNickNameText;

  private boolean isRegistering = false;
  private Thread registerThread;

  private String stockCodeCgiText = "http://stock.paxnet.co.kr/cgi-bin/Analysis/getStockCodeMagic.cgi?stockCode=";

  public HtmlFreeBoardPane( String [] args, final int width, final int height ) {

    args = readArgsFile( args );

    this.args = args;

//    for( int i = 0, len = args.length ; i < len; i ++ ) {
//
//      Utility.debug( this, "ARGS[" + i + "]=" + args[ i ] );
//
//    }

    // 보드 모드 설정

    if( isGoodArgs( args, 2 ) ) {

      setBoardMode( args[ 2 ] );

    }

    // 끝. 보드 모드 설정

    // set hidden menu parameter

    setHiddenMenuParameter( args );

    HtmlFreeEditorApplet.setMenuButtonVisibilities( this );

    // end of setting hidden menu parameter


    initUI( width, height );

  }

  private HtmlEditorPane getEditor() {

    final HtmlFreeEditorPane htmlFreeEditorPane = getHtmlFreeEditorPane();

    if( htmlFreeEditorPane == null ) {

      return null;

    }

    return htmlFreeEditorPane.getEditor();

  }

  private String [] readArgsFile( final String [] args ) {

    if( args == null || args.length < 1 ) {
       return args;
    }

    showStatus( AppRegistry.READING_ARGUMENT_FILE_MSG );

    final File file = AppRegistry.BOARD_ARGS_FILE;

    final LinkedList argsList = new LinkedList();

    argsList.addLast( args[0] );
    argsList.addLast( args[1] );

    try {

      final BufferedReader in = new BufferedReader( new InputStreamReader( new FileInputStream( file ) ) );

      String lineText = in.readLine();

      while( lineText != null ) {

	argsList.addLast( getBase64DecodedText( lineText ) );

	lineText = in.readLine();

      }

      final int size = argsList.size();

      final String [] newArgs = new String [ size ];

      for( int i = 0; i < size; i ++ ) {

	newArgs[ i ] = (String) ( argsList.get( i ) );

      }

      return newArgs;

    } catch (FileNotFoundException e ) {

      JOptionPane.showMessageDialog( this, AppRegistry.CANNOT_FIND_CASH_FILE_MSG );

      AppRegistry.systemExit( 0 );

      return args;

    } catch (IOException e) {

      JOptionPane.showMessageDialog( this, AppRegistry.CANNOT_READ_CASH_FILE_MSG );

      AppRegistry.systemExit( 0 );

      return args;

    }

  }

  private void parseArgs( final String [] args ) {

    if( args == null || args.length < 1 ) {

      return;

    }

    showStatus( AppRegistry.PROCESSING_ARGUMENT_INFO_MSG );

    final int len = args.length; // 아규먼트 크기

//    // 보드 모드 설정
//
//    if( isGoodArgs( args, 2 ) ) {
//
//      setBoardMode( args[ 2 ] );
//
//    }
//
//    // 끝. 보드 모드 설정

    // 입력 CGI 호출

    if( isGoodArgs( args, 3 ) ) {

      callInputCGI( args[ 3 ] );

    }

    // 끝. 입력 CGI 호출

    // 세이브 CGI URL 설정

    if( isGoodArgs( args, 4 ) ) {

      setSaveCgiBinUrlText( args[ 4 ] );

    }

    // 끝. 세이브 CGI URL 설정

    // 쿠기 설정

    if( isGoodArgs( args, 5 ) ) {

      setUserCookie( args[ 5 ] );

      cgiVariableArgs.processCookie( args[ 5 ] );

    }

    // 끝. 쿠기 설정

    // 팩스 닉 네임 설정

    final CgiVariable paxNickNameCgiVar = cgiVariableArgs.getCgiVariableOfCookieName( "paxNick" );

    if( paxNickNameCgiVar != null ) {

      this.paxNickNameText = paxNickNameCgiVar.getValue();

    } else {

      this.paxNickNameText = null;

    }

    cgiVariableArgs.print();

    // 제목과 이메일 주소 값 UI 설정

    setUiTextData();

    // 끝. 제목과 이메일 주소 값 UI 설정

    // 수정 HFZ URL 설정

    if( ( this.boardMode == this.MODIFY_BOARD_MODE ) && isGoodArgs( args, 6 ) ) {

      this.modifiedHfzUrlText = args[ 6 ];

      final File downLoadedSerZipFile = getDownLoadedServerZipFile( modifiedHfzUrlText );

      if( downLoadedSerZipFile == null ) {

	Utility.messageDialog( this, AppRegistry.CANNOT_READ_HTML_FILE_FROM_SVR_MSG, "SERVER ZIP FILE COPY ERROR" );

	AppRegistry.systemExit( 0 );

      }

      this.downLoadedSerZipFile = downLoadedSerZipFile;

      final File tempHtmlFile = this.getTempHtmlFile( downLoadedSerZipFile );

      if( tempHtmlFile == null ) {

	AppRegistry.systemExit( 0 );

      }

      this.tempHtmlFile = tempHtmlFile;

    }

    // 끝. 수정 HFZ URL 설정

    // set help site

    setHelpSite( args );

    // end of setting help site

//    // set hidden menu parameter
//
//    setHiddenMenuParameter( args );
//
//    HtmlFreeEditorApplet.setMenuButtonVisibilities( this );
//
//    // end of setting hidden menu parameter

    // stock code cgi text setting

    setStockCodeCgiText( args );

    // end of setting stock code cgi text

  }

  // 숨겨진 메뉴를 설정할 때는 FALSE 값을 리턴한다.

  public String getParameter( String name ) {

    final String hiddenMenuParamText = this.hiddenMenuParamText;

    if( hiddenMenuParamText != null && hiddenMenuParamText.indexOf( name ) > -1 ) {

      return "FALSE";

    }

    return null;

  }

  private void setHiddenMenuParameter( final String [] args ) {

    if( args == null ) {

      return;

    }

    for( int i = 0, len = args.length; i < len; i ++ ) {

      if( args[ i ].equalsIgnoreCase( "hidden_menu" ) && i < len -1 ) {

	  this.hiddenMenuParamText = args[ i + 1 ];

	  return;

      }

    }

  }

  private void setHelpSite( final String [] args ) {

    if( args == null ) {

      return;

    }

    for( int i = 0, len = args.length; i < len; i ++ ) {

      if( args[ i ].equalsIgnoreCase( "help_site" ) && i < len -1 ) {

	  AppRegistry.HELP_SITE = args[ i + 1 ];

	  return;

      }

    }

  }

  private void setStockCodeCgiText( final String [] args ) {

    if( args == null ) {

      return;

    }

    for( int i = 0, len = args.length; i < len; i ++ ) {

      if( args[ i ].equalsIgnoreCase( "stock_code_cgi" ) && i < len -1 ) {

	  this.stockCodeCgiText = args[ i + 1 ].trim();

	  return;

      }

    }

  }

  private void openNormalHtmlFileBackground( final HtmlFreeEditorPane htmlFreeEditorPane,
					     final File htmlFile ) {

    SwingUtilities.invokeLater(

      new Runnable () {

	  public void run() {

	      try {

		htmlFreeEditorPane.openNormalHtmlFile( tempHtmlFile );

		showStatus( null );

		repaint();

	      } catch (IOException e) {

	      }
	  }

      }

    );

  }

  public void paint( Graphics g ) {

    super.paint( g );

    if( ! inited ) {

      inited = true;

      initHtmlFreeBoardBackground();

    }

  }

  private void initHtmlFreeBoardBackground( ) {

    new Thread( this ).start();

  }

  public void run() {

    final HtmlFreeEditorPane htmlFreeEditorPane = getHtmlFreeEditorPane();

    parseArgs( this.args );

    if( htmlFreeEditorPane != null && this.tempHtmlFile != null ) {

	showStatus( AppRegistry.HTML_FILE_READING_MSG );

	openNormalHtmlFileBackground( htmlFreeEditorPane, this.tempHtmlFile );

    } else {

	showStatus( null );

	repaint();

    }

  }

  final public File getDownLoadedServerZipFile(final String urlText ) {

      String fileName = null;

      try {

	fileName = urlText.substring( urlText.lastIndexOf( "/") + 1 );

      } catch (Exception e) {

	fileName = "temp." + AppRegistry.HTML_FREE_ZIP_FILE_EXTENSION;

	Utility.debug( e );

      }

      File tempDir = null;

      try {

	tempDir = HtmlEditorPane.getTempDir( this );

      } catch ( IOException e ) {

	Utility.messageDialog( this, "CANNOT MAKE TEMP FOLDER WHILE READING SERVER ZIP HTML FILE", "TEMP FOLDER ERROR" );
	Utility.debug( e );

	return null;

      }

      final File tempFile = new File( tempDir, fileName );

      try {

	showStatus( AppRegistry.HTML_FILE_DOWLOAD_MSG );

	new WebCopy( getEditor() ).copy( urlText, tempFile );

      } catch (IOException e) {

//	Utility.messageDialog( this, "CANNOT COPY SERVER ZIP HTML FILE TO LOCAL FOLDER", "SERVER ZIP FILE COPY ERROR" );
	Utility.debug( e );

	return null;

      }

      return tempFile;

  }

  final private File getTempHtmlFile(final File tempZipFile ) {

      File normalHtmlFile = null;

      try {

	  try {

	     File tempDir = HtmlEditorPane.getTempDir( this );

	     final int tempDirIdx = Math.abs( (int)(System.currentTimeMillis() ) );

	     tempDir = new File( tempDir, "tmp" + tempDirIdx );

	     tempDir.mkdir();

	     showStatus( AppRegistry.UNZIPPING_HTML_FILE_MSG );

	     final UnZip unzip = new UnZip( getEditor() );

	     final Vector fileList = unzip.unzip( tempZipFile, tempDir );

	     final Enumeration enumIt = fileList.elements();

	     while( enumIt.hasMoreElements() ) {

		final File fileInZip = (File) enumIt.nextElement();

//                Utility.debug(this, "ZIP = " + fileInZip );

		final String name = fileInZip.getName().trim().toUpperCase();

		if( name.endsWith(".HTML" ) || name.endsWith( ".HTM" ) ) {

		     normalHtmlFile = fileInZip;

		     break;

		}

	     }

	  } catch (Exception e) {

	     Utility.debug(  e );
	     Utility.warningDialog( this, "Cannot decompress zipped html file [" + tempZipFile.getCanonicalPath() + "]", "CANNOT UNZIP" );

	  }

      } catch (Exception e) {

	  Utility.debug(  e );

	  Utility.warningDialog( this, "Cannot copy zipped html file to temporary directory", "CANNOT COPY ZIPPED HTML FILE" );

      }

      return normalHtmlFile;

  }

  private void setUserCookie( final String userCookieText ) {

    this.userCookieText = userCookieText;

  }

  private void setSaveCgiBinUrlText( final String cgiBinUrlText ) {

    this.saveCgiBinUrlText = cgiBinUrlText;

    Utility.debug( this, "SAVE CGI-BIN = " + this.saveCgiBinUrlText );

  }

  private void callInputCGI( final String cgiUrlText ) {

    URL url = null;

    showStatus( AppRegistry.CONNECTING_SERVER_MSG );

    try {

      Utility.debug( this, "CALL CGI = " + cgiUrlText );

      url = new URL( cgiUrlText );

      final BufferedReader in = new BufferedReader( new InputStreamReader( url.openStream() ) );

      processCGIInput( in, this.cgiVariableArgs );

    } catch ( MalformedURLException e ) {

      Utility.debug( e );

      Utility.warningDialog( this, "잘못된 CGI 아규먼트 입니다.", "잘못된 CGI 아규먼트!" );

    } catch ( IOException e ) {

      Utility.debug( e );

      JOptionPane.showMessageDialog( this,
	  new String [] {
	    "서버 접속(CGI 호출) 에러입니다.",
	    "매직 글쓰기를 다시 실행하십시오.",
	    cgiUrlText
	  },
	  "CGI 호출 에러",
	  JOptionPane.OK_OPTION
      );

//      Utility.warningDialog( this, "CGI 호출 에러입니다.", "CGI 호출 에러" );

    }

  }

  private void processCGIInput( final BufferedReader in, final CgiVariableArgs cgiVariableArgs ) throws IOException {

      String cgiVariableName = in.readLine() ;

      if( cgiVariableName.equalsIgnoreCase( "ERROR" ) ) {

	showErrorMessage( in, AppRegistry.INIT_APP_ERROR_MESSAGE );

	AppRegistry.systemExit( 0 );

	return;

      }

      String cgiVariableValue = in.readLine();

      showStatus( AppRegistry.RECEIVING_ENVIRONMENT_INFO_FROM_SVR_MSG );

      while( cgiVariableName != null ) {

	final CgiVariable cgiVariable = new CgiVariable( cgiVariableName, cgiVariableValue );

	cgiVariableArgs.addCgiVariable( cgiVariable );

	cgiVariableName = in.readLine();
	cgiVariableValue = in.readLine();

      }

  }

  private void setUiTextData() {

      // 타이틀 데이터 설정

      CgiVariable cgiVariable = cgiVariableArgs.getCgiVariable( "subject" );

      if( cgiVariable != null ) {

	this.title = cgiVariable.getValue();

	if( this.titleTF != null ) {

	  this.titleTF.setText( this.title );

	}

      }

      cgiVariable = cgiVariableArgs.getCgiVariable( "subLimit" );

      if( cgiVariable != null ) {

	try {

	  this.subLimit = new Integer( cgiVariable.getValue() ).intValue();

	} catch (Exception e) {

	  Utility.debug( e );
	}

      }

      // 끝. 타이틀 데이터 설정

      // 메일 데이터 설정

      cgiVariable = cgiVariableArgs.getCgiVariable( "email" );

      if( cgiVariable != null ) {

	this.eMail = cgiVariable.getValue();

	if( this.eMailTF != null ) {

	  this.eMailTF.setText( eMail );

	}

      }

      cgiVariable = cgiVariableArgs.getCgiVariable( "emLimit" );

      if( cgiVariable != null ) {

	try {

	  this.emLimit = new Integer( cgiVariable.getValue() ).intValue();

	} catch (Exception e) {

	  Utility.debug( e );
	}

      }

      // 끝. 메일 데이터 설정

      // 스탁 코드 데이터 설정

      cgiVariable = cgiVariableArgs.getCgiVariable( "itemcode" );

      if( cgiVariable != null ) {

	try {

	  if( this.stockCodeTF != null && cgiVariable.getValue() != null ) {

	    this.stockCodeTF.setText( cgiVariable.getValue() );

	  }

	} catch (Exception e) {

	  Utility.debug( e );
	}

      }

      // 끝. 스탁 코드 데이터 설정.

      // 필명 UI 와 필명 값 설정.

      setNickNameUiAndData();

      // 끝. 필명 UI 와 필명 값 설정.

  }

  private void setNickNameUiAndData() {

    Utility.debug( this, "NICKNAME UI SETTING ......" );

    if( this.boardMode != this.STANDARD_BOARD_MODE ) {

      return;

    }

    final CgiVariable paxLevelVar = cgiVariableArgs.getCgiVariable( "paxLevel" );

    if( paxLevelVar == null ) {

      return;

    }

    final String paxLevelText = paxLevelVar.getValue();

    if( paxLevelText == null ) {

      return;

    }

    if( ! paxLevelText.equalsIgnoreCase( "999" ) ) {

      return;

    }

    String nickNameText = "";

    final CgiVariable nickNameVar = cgiVariableArgs.getCgiVariable( "senderName" );

    if( nickNameVar != null && nickNameVar.getValue() != null ) {

       nickNameText = nickNameVar.getValue();

    }

    final JPanel nickNamePane = this.nickNamePane;

    final JPanel eastPane = new JBorderLayoutPanel();

    final JPanel westPane = new JBorderLayoutPanel();

    final JLabel nickNameLB = new JLabel( "  필명  " );

    nickNameLB.setFont( northPaneFont );
    nickNameLB.setBackground( northPaneColor );

    this.nickNameTF = new JTextField( nickNameText, 10 );

    westPane.setBackground( northPaneColor );

    westPane.add( nickNameLB, BorderLayout.WEST );
    westPane.add( nickNameTF, BorderLayout.CENTER );

    nickNamePane.add( eastPane, BorderLayout.EAST );
    nickNamePane.add( westPane, BorderLayout.WEST );

    nickNamePane.validate();

    Utility.debug( this, "NICKNAME UI HAS ADDED." );

  }

  private boolean isGoodArgs( final String [] args, final int num ) {

    return ( args != null ) && ( args.length > num ) && ( args[ num ] != null ) ;
  }

  private void setBoardMode( final String modeText ) {

    if( modeText.equalsIgnoreCase( AppRegistry.STANDARD_BOARD_MODE_TEXT ) ) {

      this.boardMode = this.STANDARD_BOARD_MODE;

    } else if( modeText.equalsIgnoreCase( AppRegistry.MODIFY_BOARD_MODE_TEXT ) ) {

      this.boardMode = this.MODIFY_BOARD_MODE;

    } else if( modeText.equalsIgnoreCase( AppRegistry.REPLY_BOARD_MODE_TEXT ) ) {

      this.boardMode = this.REPLY_BOARD_MODE;

    }

    Utility.debug( this, "MODE = " + this.boardMode );

  }

  private void showStatus( final String text ) {

    final ProgressInterface progressInterface = getEditor();

    if( progressInterface != null ) {

      progressInterface.setString( text );

    }

  }

  private void initUI( final int width, final int height ) {

    contentPane = this;

    showStatus( AppRegistry.INITIALIZING_USER_INTERFACE );

    contentPane.setLayout( new BorderLayout( 0, 0 ) );

    // 북쪽 컴포넌트 설정

    final Dimension northRowPaneDimension = new Dimension( width, 30 );

    northFirstRowPane = new JBorderLayoutPanel( 1, 1 );

	northFirstRowPane.setPreferredSize( northRowPaneDimension );

	northFirstRowPane.setBackground( northPaneColor );

	titleTF = new JTextField( title );

	final JLabel titleJL = new JLabel( "제목", JLabel.CENTER );

	titleJL.setFont( northPaneFont );

	final Dimension labelSize = titleJL.getPreferredSize();

	final int labelWidth = 65;

	final int titleTFWidth = width/3;

	titleJL.setPreferredSize( new Dimension( labelWidth, labelSize.height ) );

	titleTF.setPreferredSize( new Dimension( titleTFWidth, labelSize.height ) );

	northFirstRowPane.add( titleJL, BorderLayout.WEST );

	northFirstRowPane.add( titleTF, BorderLayout.CENTER );

	// 종목 코드 찾기 UI 설정

	JPanel stockCodePane = new JPanel() {

	  public Dimension getPreferredSize() {

	    Dimension size = super.getPreferredSize();

	    Dimension contentPaneSize = contentPane.getSize();

	    return new Dimension(
				  contentPaneSize.width/2,
				  labelSize.height
				 );

	  }

	};;

	    stockCodePane.setLayout( new FlowLayout( FlowLayout.RIGHT) );
	    stockCodePane.setBackground( northPaneColor );

	    JLabel stockCodeLabel = new JLabel( "종목 코드", JLabel.RIGHT );
//	    stockCodeLabel.setPreferredSize( new Dimension( 200, labelSize.height ) );
	    stockCodeLabel.setFont( northPaneFont );

	    this.stockCodeTF = new JTextField( 10 );
	    stockCodeTF.setFont( northPaneFont );

	    JButton stockCodeFindBtn = new JButton( Utility.getResourceImageIcon( "bbs_code_search.gif" ) );

	    stockCodeFindBtn.setBackground( northPaneColor );
	    stockCodeFindBtn.setFont( northPaneFont );
	    stockCodeFindBtn.setBorder( BorderFactory.createEmptyBorder() );

	    stockCodePane.add( stockCodeLabel );
	    stockCodePane.add( stockCodeTF );
	    stockCodePane.add( stockCodeFindBtn  );

	    stockCodeTF.setEditable( false );

	    stockCodeFindBtn.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {

		findStockCode( stockCodeTF );

	      }
	    });

//	stockCodePane.setPreferredSize( new Dimension( width - labelWidth - titleTFWidth, labelSize.height ) );

	if( false && boardMode == this.STANDARD_BOARD_MODE ) { // 글쓰기 모드 일 경우에만 종목 코드 찾기 UI를 사용한다.
//	if( boardMode == this.STANDARD_BOARD_MODE ) { // 글쓰기 모드 일 경우에만 종목 코드 찾기 UI를 사용한다.

	    northFirstRowPane.add( stockCodePane, BorderLayout.EAST );

	} else {

	    JLabel emptyLabel = getJLabel( 0.5, contentPane, labelSize.height );

	    emptyLabel.setPreferredSize( new Dimension( width - labelWidth - titleTFWidth, labelSize.height ) );

	    northFirstRowPane.add( emptyLabel, BorderLayout.EAST );

	}

	// 끝. 종목 코드 찾기 UI 설정

    northSndRowPane = new JBorderLayoutPanel( 0, 0 );

	northSndRowPane.setPreferredSize( northRowPaneDimension );

	northSndRowPane.setBackground( northPaneColor );

	int eMailTFWidth = width/4;

	eMailTF = new JTextField( eMail );

	final JLabel mailJL = new JLabel("Email", JLabel.CENTER );

	mailJL.setFont( northPaneFont );

	mailJL.setPreferredSize( new Dimension( labelWidth, labelSize.height ) );

	eMailTF.setFont( northPaneFont );

	eMailTF.setPreferredSize( new Dimension( eMailTFWidth, labelSize.height ) );

	nickNamePane = getJPane( 0.5, contentPane, labelSize.height );

	nickNamePane.setBackground( northPaneColor );

	nickNamePane.setLayout( new BorderLayout() );

	northSndRowPane.add( nickNamePane, BorderLayout.EAST );

	northSndRowPane.add( eMailTF, BorderLayout.CENTER );

	northSndRowPane.add( mailJL, BorderLayout.WEST );

    northThrdRowPane = new JPanel();

	northThrdRowPane.setPreferredSize( northRowPaneDimension );

	northThrdRowPane.setBackground( northPaneColor );

	northThrdRowPane.setLayout( new FlowLayout( FlowLayout.CENTER ) );

	noIconCB = getJRadioButton( 1 );
	agreeCB  = getJRadioButton( 2 );
	denyCB   = getJRadioButton( 3 );
	thxCB    = getJRadioButton( 4 );

	final JRadioButton [] replyCheckBoxes = new JRadioButton [ 4 ];

	replyCheckBoxes[ 0 ] = noIconCB;
	replyCheckBoxes[ 1 ] = agreeCB;
	replyCheckBoxes[ 2 ] = denyCB;
	replyCheckBoxes[ 3 ] = thxCB;

	final CgiVariable positionCgiVariable = this.cgiVariableArgs.getCgiVariable( "position" );

	if( positionCgiVariable != null ) {

	  final String positionText = positionCgiVariable.getValue().trim();

	  final int position = new Integer( positionText ).intValue();

	  this.selectedJCheckBoxIndex = position;

	}

	for( int i = 0, len = replyCheckBoxes.length; i < len ; i ++ ) {

	  final JRadioButton checkBox = replyCheckBoxes[ i ];

	  checkBox.setBackground( northPaneColor );

	  final int index = i + 1;

	  checkBox.addMouseListener( new MouseAdapter() {

	    public void mouseReleased(MouseEvent e) {

	      selectedJCheckBoxIndex = index;
	      northThrdRowPane.repaint();

	    }

	  });

	}

	noIconCB.setFont( northPaneFont );
	agreeCB.setFont( northPaneFont );
	denyCB.setFont( northPaneFont );
	thxCB.setFont( northPaneFont );

	final int align = JLabel.LEFT;

	northThrdRowPane.add( noIconCB );
	JLabel iconLBL = new JLabel( "아이콘 없음   ", align );
	northThrdRowPane.add( iconLBL );
	iconLBL = new JLabel( "찬성 답변   ", Utility.getResourceImageIcon( "bbs_yes_reply.gif" ), align );
	northThrdRowPane.add( agreeCB );
	northThrdRowPane.add( iconLBL );
	iconLBL = new JLabel( "반대 답변   ", Utility.getResourceImageIcon( "bbs_no_reply.gif" ), align );
	northThrdRowPane.add( denyCB );
	northThrdRowPane.add( iconLBL );
	iconLBL = new JLabel( "감사 답변   ", Utility.getResourceImageIcon( "bbs_thanks_reply.gif" ), align );
	northThrdRowPane.add( thxCB );
	northThrdRowPane.add( iconLBL );


    northPane = new JPanel();

    northPane.setBackground( northPaneColor );

    northPane.setLayout( new GridLayout( 0, 1 ) );

    northPane.add( northFirstRowPane );

    northPane.add( northSndRowPane );

    if( this.boardMode == this.REPLY_BOARD_MODE ) {

	northPane.add( northThrdRowPane );

    }

    final int northRowNum = northPane.getComponentCount();

    northPane.setPreferredSize( new Dimension( northRowPaneDimension.width, northRowPaneDimension.height*northRowNum ) );

    Utility.debug( this, "NORTH ROW NUM = " +  northRowNum );

    northPane.setBorder( BorderFactory.createEtchedBorder( 1 ) );

    // 끝. 북쪽 컴포넌트 설정

    // 남쪽 컴포넌트 설정

    final Font southPaneFont = AppRegistry.BOARD_SOUTH_PANE_FONT;

    final Dimension southRowPaneDimension = new Dimension( 100, 25 );

    final Color southPaneColor = StringView.fromHtmlColor( "#F6F6F6" );

    southPane = new JPanel();

	southPane.setLayout( new GridLayout( 3, 1 ) );
	southPane.setBorder( BorderFactory.createEtchedBorder( 1 ) );
	southPane.setBackground( southPaneColor );

    southFirstRowPane = new JBorderLayoutPanel( 1, 1 );

	// 첫번째 파일 첨부

	JPanel southFirstRowWestPane = new JBorderLayoutPanel( 0, 0 );
	southFirstRowPane.setBackground( southPaneColor );
	southFirstRowWestPane.setBackground( southPaneColor );
	southFirstRowPane.add( southFirstRowWestPane, BorderLayout.WEST );

	  JPanel emptyPane = new JPanel();
	  emptyPane.setBackground( southPaneColor );

	southFirstRowPane.add( emptyPane, BorderLayout.EAST );

	southFirstRowPane.setPreferredSize( southRowPaneDimension );

	JLabel fileAttachLBL = new JLabel( " 파일 첨부 ", JLabel.CENTER );
	fileAttachLBL.setFont( southPaneFont );
	final Dimension fileAttachLBLSize = fileAttachLBL.getPreferredSize();
	final int fileAttachLBLWidth = 80;
	fileAttachLBL.setPreferredSize( new Dimension( fileAttachLBLWidth, southRowPaneDimension.height ) );
	fileAttachTFOne = new JTextField( 30 );
	fileAttachTFOne.addKeyListener(new KeyAdapter() {
	  public void keyTyped(KeyEvent e) {
//	    Utility.debug( this, fileAttachTFOne.getText( ));
	    if( fileAttachTFOne.getText().length() < 1 ) {
	      attachFileOne = null;
	    }
	  }
	});
	fileAttachTFOne.setFont( southPaneFont );
	JButton fileAttachBTN = new JButton( " 찾아 보기 " );
	fileAttachBTN.setFont( southPaneFont );
	fileAttachBTN.setBackground( southPaneColor );
	southFirstRowWestPane.add( fileAttachLBL, BorderLayout.WEST );
	southFirstRowWestPane.add( fileAttachTFOne, BorderLayout.CENTER );
	southFirstRowWestPane.add( fileAttachBTN, BorderLayout.EAST );

	// 파일 어태치 버튼 리스너 추가

	fileAttachBTN.addActionListener(new ActionListener() {
	  public void actionPerformed(ActionEvent e) {

	    final int option = fileChooser.showDialog( HtmlFreeBoardPane.this, "선택" );
	    if( option == JFileChooser.CANCEL_OPTION ) {
	      return;
	    }

	    final File file = fileChooser.getSelectedFile();

	    if( checkAttachFileSize( attachFileTwo, file ) ) {

	      attachFileOne = file;

	      fileAttachTFOne.setText( file.getAbsolutePath() );

	    }

	  }
	});

	// 끝. 파일 어태치 버튼 리스터 추가

	// 끝. 첫번째 파일 첨부

	// 두번째 파일 첨부

    southSndRowPane = new JBorderLayoutPanel( 1, 1 );

	JPanel southSndRowWestPane = new JBorderLayoutPanel( 0, 0 );
	southSndRowPane.setBackground( southPaneColor );
	southSndRowWestPane.setBackground( southPaneColor );
	southSndRowPane.add( southSndRowWestPane, BorderLayout.WEST );

	  emptyPane = new JPanel();
	  emptyPane.setBackground( southPaneColor );

	southSndRowPane.add( emptyPane, BorderLayout.EAST );

	southSndRowPane.setPreferredSize( southRowPaneDimension );

	fileAttachLBL = new JLabel( " 파일 첨부 ", JLabel.CENTER );
	fileAttachLBL.setFont( southPaneFont );
	fileAttachLBL.setPreferredSize( new Dimension( fileAttachLBLWidth, southRowPaneDimension.height ) );
	fileAttachTFTwo = new JTextField( 30 );
	fileAttachTFTwo.addKeyListener(new KeyAdapter() {
	  public void keyTyped(KeyEvent e) {
	    if( fileAttachTFTwo.getText().length() < 1 ) {
	      attachFileTwo = null;
	    }
	  }
	});
	fileAttachTFTwo.setFont( southPaneFont );
	fileAttachBTN = new JButton( " 찾아 보기 " );
	fileAttachBTN.setFont( southPaneFont );
	fileAttachBTN.setBackground( southPaneColor );
	southSndRowWestPane.add( fileAttachLBL, BorderLayout.WEST );
	southSndRowWestPane.add( fileAttachTFTwo, BorderLayout.CENTER );
	southSndRowWestPane.add( fileAttachBTN, BorderLayout.EAST );

	// 파일 어태치 버튼 리스너 추가

	fileAttachBTN.addActionListener(new ActionListener() {
	  public void actionPerformed(ActionEvent e) {

	    final int option = fileChooser.showDialog( HtmlFreeBoardPane.this, "선택" );

	    if( option == JFileChooser.CANCEL_OPTION ) {

	      return;

	    }

	    final File file = fileChooser.getSelectedFile();

	    if( checkAttachFileSize( attachFileOne, file ) ) {

	      attachFileTwo = file;

	      fileAttachTFTwo.setText( file.getAbsolutePath() );

	    }

	  }
	});

	// 끝. 파일 어태치 버튼 리스터 추가

	// 끝. 두번째 파일 첨부

    southThrdRowPane = new JPanel();

	southThrdRowPane.setLayout( new FlowLayout( FlowLayout.CENTER ) );
	southThrdRowPane.setPreferredSize( southRowPaneDimension );
	southThrdRowPane.setBackground( northPaneColor );

	// 등록, 취소 버튼

	registerBTN = new JButton( Utility.getResourceImageIcon( "bbs_register.gif" ) );
	cancelBTN = new JButton( Utility.getResourceImageIcon( "bbs_cancel.gif" ) );

	registerBTN.setFont( southPaneFont );
	registerBTN.setBackground( northPaneColor );
	registerBTN.setBorder( BorderFactory.createEmptyBorder() );
	cancelBTN.setFont( southPaneFont );
	cancelBTN.setBackground( northPaneColor );
	cancelBTN.setBorder( BorderFactory.createEmptyBorder() );

	registerBTN.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {

		registerOnBulletinAsThread();

	    }

	});

	cancelBTN.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {

		cancelRegisteringOnBulletin();

	    }
	});

	southThrdRowPane.add( registerBTN );
	southThrdRowPane.add( cancelBTN );

	// 끝. 등록, 취소 버튼

    southPane.add( southFirstRowPane );

    southPane.add( southSndRowPane );

    southPane.add( southThrdRowPane );

    southPane.setPreferredSize(
       new Dimension(
			southRowPaneDimension.width,
			southFirstRowPane.getPreferredSize().height +
			southSndRowPane.getPreferredSize().height +
			southThrdRowPane.getPreferredSize().height + 6
		     ) );


    // 끝. 남쪽 컴포넌트 설정

    // 가운데 컴포넌트 설정

    showStatus( AppRegistry.INITIALIZING_EDITOR_INTERFACE );

    htmlFreeMenuPane = new HtmlFreeEditorMenuPane();

    contentPane.add( southPane, BorderLayout.SOUTH );

    contentPane.add( northPane, BorderLayout.NORTH );

    contentPane.add( htmlFreeMenuPane, BorderLayout.CENTER );

    // 끝. 가운데 컴포넌트 설정

  }

  private boolean isValidEmailAddress( String eMailText ) {

    if( eMailText == null ) {

      return false;

    }

    eMailText = eMailText.trim();

    final int atIndex = eMailText.indexOf( "@" );

    if( atIndex < 0 ) {

      return false;

    }

    final int dotIndex = eMailText.indexOf( ".", atIndex );

    if( dotIndex < 0 ) {

      return false;

    }

    return true;

  }

  private void cancelRegisteringOnBulletin() {

    if( isRegistering ) {

      final int option = JOptionPane.showConfirmDialog( HtmlFreeBoardPane.this,
			"게시판 등록을 취소하시겠습니까?",
			"게시판 등록 취소", JOptionPane.YES_NO_OPTION
		      );

      if( option == JOptionPane.YES_OPTION ) {

	this.isRegistering = false;

	Thread t = this.registerThread;

	try {

	  if( t != null && t.isAlive() ) {

	    t.stop();

	  }

	} catch (Exception e) {

	  Utility.debug( e );

	}

	final HtmlEditorPane progressBar = getEditor();

	progressBar.hideProgress();

	progressBar.setEditable( true );

	progressBar.repaint();

      }

    }

    final int option = JOptionPane.showConfirmDialog( HtmlFreeBoardPane.this,
			AppRegistry.CANCEL_EDITING_FORCED_MSG,
			"글 쓰기 종료", JOptionPane.YES_NO_OPTION
		      );

    if( option == JOptionPane.YES_OPTION ) {

      AppRegistry.systemExit( 0 );

    }

  }

  private void registerOnBulletinAsThread() {

    if( isRegistering ) {

      return;

    }

    final Thread t = new Thread() {

      public void run() {

	isRegistering = true;

	String regiState = "REGISTERING THREAD";

	final HtmlEditorPane progressBar = getEditor();

	try {

	  progressBar.setEditable( false );

	  regiState = registerOnBulletin( progressBar );

	} catch (Exception e ) {

	  Utility.debug( e );

	} catch (Error e) {

	  Utility.debug( e );

	} finally {

	  isRegistering = false;

	  progressBar.setEditable( true );

	  progressBar.hideProgress();

	  progressBar.repaint();

	  Utility.debug( this, "REGISTATE = " + regiState );

	}

      }

    };

    this.registerThread = t;

    t.start();

  }

  private String registerOnBulletin( final ProgressInterface progressBar ) {

    String regiState = "REGISTERING";

    final int subLimit = this.subLimit;

    final int emLimit = this.emLimit;

    // 타이틀 입력 여부 체크

    final String title = this.titleTF.getText();

    if( title == null || title.length() < 1 ) {

      JOptionPane.showMessageDialog( this, "제목을 입력하세요!" );

      return regiState;

    } else if( title != null && ( title.getBytes().length > subLimit ) ) {

      JOptionPane.showMessageDialog( this,
	  "제목의 글자수가 너무 많습니다.(최대 영문 " + subLimit + "자, 한글 " + (subLimit/2) + "자)!" );

      return regiState;

    }

    // 끝. 타이틀 입력 여부 체크

    // 이메일 주소 입력 여부 체크

    final String eMail = this.eMailTF.getText().trim();

    if( eMail != null && eMail.length() < 1 ) {

      // Do nothing!
      // 이 메일 주소 삭게 가능 하도록 함.

    } else if( eMail != null && ( eMail.getBytes().length > emLimit ) ) {

      JOptionPane.showMessageDialog( this,
	  "E-Mail 주소의 글자수가 너무 많습니다.(최대 영문 " + emLimit + "자)!" );

      return regiState;

    } else if( ! isValidEmailAddress( eMail ) ) {

      JOptionPane.showMessageDialog( this, "올바른 E-Mail 주소가 아닙니다." );

      return regiState;

    }

    // 끝. 이메일 주소 입력 여부 체크

    /**@todo 프로그레시브바 대체
     *
     */

    final ProgressInterface msgDialog = progressBar;

    final int boardMode = this.boardMode;

    final String baseDirUrl = null;

    final CgiVariableArgs cgiVariableArgs = this.cgiVariableArgs;

    final CgiVariable fileEncodeCgiVar = cgiVariableArgs.getCgiVariable( "file_encode" );

    boolean fileEncode = ( fileEncodeCgiVar == null
			    ||
			   fileEncodeCgiVar.getValue().equalsIgnoreCase( "TRUE" )
			  );

    regiState = "HTML 파일을 생성중입니다.";

    msgDialog.setString( regiState );

    byte [] zipContentsBytes = getByteZipContent( baseDirUrl );

    if( zipContentsBytes == null ) {

      msgDialog.hideProgress();

      JOptionPane.showMessageDialog( this, "업로드할 HTML 파일을 생성할 수 없습니다." );

      return regiState;

    }

    final int zipContentsBytesLen = zipContentsBytes.length;

    final long upFileMaxSize = getUpFileMaxSize();

    if( upFileMaxSize > -1 && ( zipContentsBytesLen > upFileMaxSize ) ) {

      JOptionPane.showMessageDialog( this,
	      "컨텐트 파일 사이즈 최대 용량("
	      + ( (int)(upFileMaxSize/1000000) )
	      + " M)을 초과하였습니다." );

      return regiState;

    }

    if( fileEncode ) {

      regiState = "HTML 파일을 인코딩중입니다.";

      msgDialog.setString( regiState );

      cgiVariableArgs.setValue( "zip_file", Base64Encoder.encode( zipContentsBytes ) );

    } else {

      cgiVariableArgs.setValue( "zip_file", zipContentsBytes );

    }

    cgiVariableArgs.setValue( "subject", getBase64EncodedText( this.titleTF.getText() )  );
    cgiVariableArgs.setValue( "email", getBase64EncodedText( this.eMailTF.getText() ) );

    final CgiVariable textFileCgiVar = cgiVariableArgs.getCgiVariable( "text_file" );

    if( textFileCgiVar != null ) {

      textFileCgiVar.setValue( getBase64EncodedText( this.get_only_texts() ) );

    }

    if( ! isRegistering ) {

      return regiState;

    }

    final File attachFileOne = this.attachFileOne;

    final File attachFileTwo = this.attachFileTwo;

    // 업로드 파일 사이즈 체크

    regiState = "업로드할 파일 크기를 체크하고 있습니다.";

    msgDialog.setString( regiState );

    // 첫 번째 첨부 파일 크기 구하기

    final long attachFileOneSize = FileUtilities.getFileLength( attachFileOne );

    // 끝. 첫 번째 첨부 파일 크기 구하기

    if( ! isRegistering ) {

      return regiState;

    }

    // 두 번째 첨부 파일 크기 구하기

    final long attachFileTwoSize = FileUtilities.getFileLength( attachFileTwo );

    // 끝. 두 번째 첨부 파일 크기 구하기

    if( ! isRegistering ) {

      return regiState;

    }

    final long currUpFileSize =  ( attachFileOneSize )
			      + ( attachFileTwoSize )
			      + ( zipContentsBytesLen );

    if( upFileMaxSize > -1 && ( currUpFileSize > upFileMaxSize ) ) {

      cgiVariableArgs.setValue( "zip_file", "" );

      cgiVariableArgs.setValue( "upfile1", "" );

      cgiVariableArgs.setValue( "upFile1Name", "" );

      cgiVariableArgs.setValue( "upfile2", "" );

      cgiVariableArgs.setValue( "upFile2Name", "" );

      JOptionPane.showMessageDialog( this,
	      "첨부 및 컨텐트 파일 사이즈가 최대 용량("
	      + ( (int)(upFileMaxSize/1000000) )
	      + " M)을 초과하였습니다." );

      return regiState;

    }

    // 끝. 업로드 파일 사이즈 체크

    regiState = "첨부할 파일을 읽고 있습니다.";

    msgDialog.setString( regiState );

    byte [] attachFileOneBytes = null;

    if( attachFileOne != null ) {

       regiState = "첨부할 첫번째 파일을 읽고 있습니다.";

       msgDialog.setString( regiState );

       attachFileOneBytes = FileUtilities.readBytes( attachFileOne );

    }

    byte [] attachFileTwoBytes = null;

    if( ! isRegistering ) {

      return regiState;

    }

    if( attachFileTwo != null ) {

       regiState = "첨부할 두번째 파일을 읽고 있습니다.";

       msgDialog.setString( regiState );

       attachFileTwoBytes = FileUtilities.readBytes( attachFileTwo );

    }

    if( ! isRegistering ) {

      return regiState;

    }

    if( attachFileOne != null && attachFileOneBytes != null ) {

	  if( fileEncode ) {

	    regiState = "첨부할 첫번째 파일을 인코딩중입니다.";

	    msgDialog.setString( regiState );

	    cgiVariableArgs.setValue( "upfile1",
				    Base64Encoder.encode( attachFileOneBytes )
				  );

	  } else {

	    cgiVariableArgs.setValue( "upfile1",
				    attachFileOneBytes
				  );

	  }

	  cgiVariableArgs.setValue( "upFile1Name", getBase64EncodedText( attachFileOne.getName() ) );

    }

    if( ! isRegistering ) {

      return regiState;

    }

    if( attachFileTwo != null && attachFileTwoBytes != null ) {

	  if( fileEncode ) {

	    regiState = "첨부할 두번째 파일을 인코딩중입니다.";

	    msgDialog.setString( regiState );

	    cgiVariableArgs.setValue( "upfile2",
				      Base64Encoder.encode( attachFileTwoBytes )
				   );

	  } else {

	    cgiVariableArgs.setValue( "upfile2",
				      attachFileTwoBytes
				   );

	  }

	  cgiVariableArgs.setValue( "upFile2Name", getBase64EncodedText( attachFileTwo.getName() ) );

    }

    if( ! isRegistering ) {

      return regiState;

    }

    if( boardMode == this.REPLY_BOARD_MODE && this.selectedJCheckBoxIndex > 0  ) {

      cgiVariableArgs.setValue( "position", "" + this.selectedJCheckBoxIndex );

    }

    // 닉 네임을 Base64로 인코딩하여 보냄.

    final CgiVariable paxNickCgiVar = cgiVariableArgs.getCgiVariableOfCookieName( "paxNick" );

    if( paxNickCgiVar != null ) {

      final String paxNickNameText = this.paxNickNameText;

      if( paxNickNameText != null ) {

	paxNickCgiVar.setValue( getBase64EncodedText( paxNickNameText ) );

      }

    }

    // 끝. 닉 네임 Base64 인코딩 하기.

    // 종목 코드 설정

    final CgiVariable stockCodeCgiVar = cgiVariableArgs.getCgiVariable( "itemcode" );

    if( (stockCodeCgiVar != null) && (stockCodeTF != null) ) {

      stockCodeCgiVar.setValue( getBase64EncodedText( this.stockCodeTF.getText().trim() ) );

    }

    // 끝. 종목 코드 설정

    // 필명 설정

    final CgiVariable nickNameVar = cgiVariableArgs.getCgiVariable( "senderName" );

    if( nickNameVar != null && this.nickNameTF != null ) {

       nickNameVar.setValue( getBase64EncodedText( this.nickNameTF.getText().trim() ) );

    }

    // 끝. 필명 설정

//    cgiVariableArgs.print();

    regiState = "서버에 등록중입니다.";

    msgDialog.setString( regiState );

    InputStream resultIn = cgiVariableArgs.callPost( this.saveCgiBinUrlText, msgDialog );

    if( resultIn == null ) {

      JOptionPane.showMessageDialog( this, "서버 연결 오류입니다." );

      return regiState;

    }

    regiState = "응답을 기다리고 있습니다.";

    msgDialog.setString( regiState );

    try {

      BufferedReader in = new BufferedReader( new InputStreamReader( resultIn ) );

      String lineText = in.readLine(); // 첫 째 줄은 성공 실패 값을 텍스트로 넘김.

      boolean successRegistration =
		( ( lineText == null ) ? false : lineText.equalsIgnoreCase( "SUCCESS" ) );

//      boolean failedRegistration = lineText == null ? false : lineText.equalsIgnoreCase( "ERROR" );

      if( successRegistration ) {

	  regiState = "게시판 등록 성공";

	  final String [] options = {
	    "확인"
	  };

	  final Font font = AppRegistry.BOARD_REGI_SUCCESS_PANE_FONT;

	  final JLabel refreshListLb01 = new JLabel( AppRegistry.REFRESH_WEB_BROWSER_1_MSG );
	  final JLabel refreshListLb02 = new JLabel( AppRegistry.REFRESH_WEB_BROWSER_2_MSG );

	  refreshListLb01.setFont( font );

	  refreshListLb02.setFont( font );

	  JOptionPane.showOptionDialog(
			this,
			new Object [] { refreshListLb01, refreshListLb02 },
			AppRegistry.BOARD_REGISTER_OK_MSG,
			JOptionPane.DEFAULT_OPTION,
			JOptionPane.INFORMATION_MESSAGE,
			null,
			options,
			options[0]
		    );

	  AppRegistry.systemExit( 0 );

      } else {

	  regiState = "게시판 등록 실패";

	  showErrorMessage( in, "게시판 등록 실패" );

	  return regiState;

      }

    } catch ( IOException e) {

      Utility.debug( e );

      JOptionPane.showMessageDialog( this, AppRegistry.SERVER_COMM_FAIL_MSG, "서버 통신 에러", JOptionPane.YES_OPTION );

    } catch ( OutOfMemoryError e ) {

      regiState = "메모리 부족";

      JOptionPane.showMessageDialog( this,
				      new String [] {
					    "첨부 파일이 너무 크거나 편집 내용이 너무 큽니다." ,
					    "다른 프로그램을 종료하신 후 다시 시도하세요."
				      },
				      "메로리 부족", JOptionPane.YES_OPTION );

    } catch ( Error e ) {

      regiState = "알 수 없는 에러";

      JOptionPane.showMessageDialog( this, new String [] { "관리자에게 문의해 주세요.", "에러 내용 :", e.getMessage() },
				      "알수 없는 에러", JOptionPane.YES_OPTION );

    }

    return regiState;

  }

  private long getUpFileMaxSize() {

    final CgiVariable upFileSizeCgiVariable = cgiVariableArgs.getCgiVariable( "upFileSize" );

    final String upFileMaxSizeText = upFileSizeCgiVariable == null ? "1" : upFileSizeCgiVariable.getValue();

    Utility.debug( this, "UP FILE MAX SIZE(M) = " + upFileMaxSizeText );

    long upFileMaxSize = -1;

    final long MEGA = 1000000L;

    try {

	upFileMaxSize = new Integer( upFileMaxSizeText ).intValue()*MEGA;

    } catch (Exception e ) {

	Utility.debug( e );

    }

    return upFileMaxSize;

  }

  private String [] getTexts( final BufferedReader in ) {

    Vector textList = new Vector();

    String lineText;

    try {

      lineText = in.readLine();

      while( lineText != null ) {

	textList.add( lineText );

	lineText = in.readLine();

      }

    } catch ( IOException e ) {

      Utility.debug( e );

    }

    final int size = textList.size();

    final String [] texts = new String [ size ];

    System.arraycopy( textList.toArray(), 0, texts, 0, size );

    return texts;

  }

  private void showErrorMessage( final BufferedReader in, final String messageText ) {

      String [] texts = getTexts( in );

      if( texts.length < 1 ) {

	texts = new String [] {
		      "통신에 과부하가 뱔생했습니다.",
		      "수초후 재등록해 주시기 바랍니다."
		};

      }

      final int size = texts.length;

      final String [] failedMsgTexts = new String [ size + 1 ];

      failedMsgTexts[ 0 ] = AppRegistry.BOARD_REGISTER_FAIL_MSG;

      System.arraycopy( texts, 0, failedMsgTexts, 1, size );

      JOptionPane.showMessageDialog( this,
	    failedMsgTexts,
	    messageText, JOptionPane.YES_OPTION );

  }

  private byte [] getByteZipContent(String baseDirUrl ) {

      HtmlFreeEditorPane editorPane = getHtmlFreeEditorPane();

      if( editorPane == null ) {

	return null;

      }

      File [] contentFiles = editorPane.saveAsTemporaryFile();

      AppRegistry.RSC_BASE_DIR_URL = null;

      if( contentFiles == null ) {

	  return null;

      }

      return FileUtilities.readBytes( contentFiles[ 1 ] );
      // 0 번째가 HTML 파일임.
      // 1 번째가 zip 파일임.

  }

  private HtmlFreeEditorPane getHtmlFreeEditorPane() {

      if( htmlFreeMenuPane == null ) {

	return null;

      }

      return htmlFreeMenuPane.getHtmlFreeEditorPane();

  }

  public String get_only_texts() {

    return getHtmlFreeEditorPane().getOnlyTexts();

  }

  public static void main(String [] args) {

    int width = 950, height = 700;

    if( args.length > 2 ) {

      try {

	width = new Integer( args[ 0 ] ).intValue();

      } catch (Exception e ) {
      }

      try {

	height = new Integer( args[ 1 ] ).intValue();

      } catch (Exception e ) {
      }

    }

    final JFrame f = new JFrame( AppRegistry.APP_NAME + " " + AppRegistry.BOARD_APP_TYPE + " " + AppRegistry.BOARD_APP_VERSION );

    AppRegistry.MAIN_APP_JFRAME = f;

    final Container con = f.getContentPane();

    con.setLayout( new BorderLayout( 1, 1 ) );

    final HtmlFreeBoardPane htmlFreeBoardPane = new HtmlFreeBoardPane( args, width, height );

    AppRegistry.MAIN_APP_CLASS = HtmlFreeBoardPane.class;

    con.add( htmlFreeBoardPane, BorderLayout.CENTER );

    f.setSize( width, height );

    f.setVisible( true );

    f.addWindowListener(new WindowAdapter() {

      public void windowClosing(WindowEvent e) {

	AppRegistry.systemExit( 0 );

      }

    });

  }

  private String getBase64EncodedText(String text ) {

    return new String( Base64Encoder.encode( text.getBytes() ) );

  }

  private String getBase64DecodedText( String base64 ) {

    return new String( Base64Encoder.decode( base64.toCharArray() ) );

  }

  private JLabel getJLabel( final double widthRatio,
			    final Container contentPane,
			    final int height ) {

	return new JLabel( "" ) {

	    public Dimension getPreferredSize() {

	      Dimension size = super.getPreferredSize();

	      Dimension contentPaneSize = contentPane.getSize();

	      return new Dimension(
				    (int)(contentPaneSize.width*widthRatio),
				    height
				   );

	    }

	};

  }

  private JPanel getJPane( final double widthRatio,
			    final Container contentPane,
			    final int height ) {

	return new JPanel( ) {

	    public Dimension getPreferredSize() {

	      Dimension size = super.getPreferredSize();

	      Dimension contentPaneSize = contentPane.getSize();

	      return new Dimension(
				    (int)(contentPaneSize.width*widthRatio),
				    height
				   );

	    }

	};

  }

  private JRadioButton getJRadioButton( final int index ) {

    return new JRadioButton( ) {

	  public void paint(Graphics g) {

	    super.paint( g );

	    if( selectedJCheckBoxIndex == index ) {

	      if( ! isSelected() ) {

		setSelected( true );

	      }

	    } else if( isSelected() ) {

	      setSelected( false );

	    }

	  }

	};

  }

  private void findStockCode( final JTextField textField ) {

      final JFrame frame = Utility.getJFrame( this );

      final StockCodeSearchJDialog stockCodeSearchJDialog =
	    new StockCodeSearchJDialog( frame, "종목코드 선택",
					stockCodeCgiText,
					AppRegistry.STOCK_CODE_FIND_WINDOW_SIZE );

      stockCodeSearchJDialog.setLocationRelativeTo( frame );

      stockCodeSearchJDialog.setVisible( true );

      final StockCode stockCode = stockCodeSearchJDialog.getStockCode();

      if( stockCode == null ) {

	// Do nothing!

      } else {

	textField.setText( stockCode.code );

	final JTextField titleTF = this.titleTF;

	if( titleTF != null ) {

	  titleTF.setText( "<b>/" + stockCode.name + "/</b>" + titleTF.getText() );

	}

      }

  }

  private boolean checkAttachFileSize( final File one, final File two ) {

    final long upFileMaxSize = getUpFileMaxSize();

    final long currFileSize = FileUtilities.getFileLength( one ) +
			      FileUtilities.getFileLength( two );

    boolean result = currFileSize < upFileMaxSize;

    if( ! result ) {

      JOptionPane.showMessageDialog( this,
	      "첨부 및 컨텐트 파일 사이즈(최대: "
	      + ( (int)(upFileMaxSize/1000000) )
	      + " M)를 초과하였습니다." );

    }

    return result;

  }

}
