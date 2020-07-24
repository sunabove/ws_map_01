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

    // ���� ��� ����

    if( isGoodArgs( args, 2 ) ) {

      setBoardMode( args[ 2 ] );

    }

    // ��. ���� ��� ����

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

    final int len = args.length; // �ƱԸ�Ʈ ũ��

//    // ���� ��� ����
//
//    if( isGoodArgs( args, 2 ) ) {
//
//      setBoardMode( args[ 2 ] );
//
//    }
//
//    // ��. ���� ��� ����

    // �Է� CGI ȣ��

    if( isGoodArgs( args, 3 ) ) {

      callInputCGI( args[ 3 ] );

    }

    // ��. �Է� CGI ȣ��

    // ���̺� CGI URL ����

    if( isGoodArgs( args, 4 ) ) {

      setSaveCgiBinUrlText( args[ 4 ] );

    }

    // ��. ���̺� CGI URL ����

    // ��� ����

    if( isGoodArgs( args, 5 ) ) {

      setUserCookie( args[ 5 ] );

      cgiVariableArgs.processCookie( args[ 5 ] );

    }

    // ��. ��� ����

    // �ѽ� �� ���� ����

    final CgiVariable paxNickNameCgiVar = cgiVariableArgs.getCgiVariableOfCookieName( "paxNick" );

    if( paxNickNameCgiVar != null ) {

      this.paxNickNameText = paxNickNameCgiVar.getValue();

    } else {

      this.paxNickNameText = null;

    }

    cgiVariableArgs.print();

    // ����� �̸��� �ּ� �� UI ����

    setUiTextData();

    // ��. ����� �̸��� �ּ� �� UI ����

    // ���� HFZ URL ����

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

    // ��. ���� HFZ URL ����

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

  // ������ �޴��� ������ ���� FALSE ���� �����Ѵ�.

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

      Utility.warningDialog( this, "�߸��� CGI �ƱԸ�Ʈ �Դϴ�.", "�߸��� CGI �ƱԸ�Ʈ!" );

    } catch ( IOException e ) {

      Utility.debug( e );

      JOptionPane.showMessageDialog( this,
	  new String [] {
	    "���� ����(CGI ȣ��) �����Դϴ�.",
	    "���� �۾��⸦ �ٽ� �����Ͻʽÿ�.",
	    cgiUrlText
	  },
	  "CGI ȣ�� ����",
	  JOptionPane.OK_OPTION
      );

//      Utility.warningDialog( this, "CGI ȣ�� �����Դϴ�.", "CGI ȣ�� ����" );

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

      // Ÿ��Ʋ ������ ����

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

      // ��. Ÿ��Ʋ ������ ����

      // ���� ������ ����

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

      // ��. ���� ������ ����

      // ��Ź �ڵ� ������ ����

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

      // ��. ��Ź �ڵ� ������ ����.

      // �ʸ� UI �� �ʸ� �� ����.

      setNickNameUiAndData();

      // ��. �ʸ� UI �� �ʸ� �� ����.

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

    final JLabel nickNameLB = new JLabel( "  �ʸ�  " );

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

    // ���� ������Ʈ ����

    final Dimension northRowPaneDimension = new Dimension( width, 30 );

    northFirstRowPane = new JBorderLayoutPanel( 1, 1 );

	northFirstRowPane.setPreferredSize( northRowPaneDimension );

	northFirstRowPane.setBackground( northPaneColor );

	titleTF = new JTextField( title );

	final JLabel titleJL = new JLabel( "����", JLabel.CENTER );

	titleJL.setFont( northPaneFont );

	final Dimension labelSize = titleJL.getPreferredSize();

	final int labelWidth = 65;

	final int titleTFWidth = width/3;

	titleJL.setPreferredSize( new Dimension( labelWidth, labelSize.height ) );

	titleTF.setPreferredSize( new Dimension( titleTFWidth, labelSize.height ) );

	northFirstRowPane.add( titleJL, BorderLayout.WEST );

	northFirstRowPane.add( titleTF, BorderLayout.CENTER );

	// ���� �ڵ� ã�� UI ����

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

	    JLabel stockCodeLabel = new JLabel( "���� �ڵ�", JLabel.RIGHT );
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

	if( false && boardMode == this.STANDARD_BOARD_MODE ) { // �۾��� ��� �� ��쿡�� ���� �ڵ� ã�� UI�� ����Ѵ�.
//	if( boardMode == this.STANDARD_BOARD_MODE ) { // �۾��� ��� �� ��쿡�� ���� �ڵ� ã�� UI�� ����Ѵ�.

	    northFirstRowPane.add( stockCodePane, BorderLayout.EAST );

	} else {

	    JLabel emptyLabel = getJLabel( 0.5, contentPane, labelSize.height );

	    emptyLabel.setPreferredSize( new Dimension( width - labelWidth - titleTFWidth, labelSize.height ) );

	    northFirstRowPane.add( emptyLabel, BorderLayout.EAST );

	}

	// ��. ���� �ڵ� ã�� UI ����

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
	JLabel iconLBL = new JLabel( "������ ����   ", align );
	northThrdRowPane.add( iconLBL );
	iconLBL = new JLabel( "���� �亯   ", Utility.getResourceImageIcon( "bbs_yes_reply.gif" ), align );
	northThrdRowPane.add( agreeCB );
	northThrdRowPane.add( iconLBL );
	iconLBL = new JLabel( "�ݴ� �亯   ", Utility.getResourceImageIcon( "bbs_no_reply.gif" ), align );
	northThrdRowPane.add( denyCB );
	northThrdRowPane.add( iconLBL );
	iconLBL = new JLabel( "���� �亯   ", Utility.getResourceImageIcon( "bbs_thanks_reply.gif" ), align );
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

    // ��. ���� ������Ʈ ����

    // ���� ������Ʈ ����

    final Font southPaneFont = AppRegistry.BOARD_SOUTH_PANE_FONT;

    final Dimension southRowPaneDimension = new Dimension( 100, 25 );

    final Color southPaneColor = StringView.fromHtmlColor( "#F6F6F6" );

    southPane = new JPanel();

	southPane.setLayout( new GridLayout( 3, 1 ) );
	southPane.setBorder( BorderFactory.createEtchedBorder( 1 ) );
	southPane.setBackground( southPaneColor );

    southFirstRowPane = new JBorderLayoutPanel( 1, 1 );

	// ù��° ���� ÷��

	JPanel southFirstRowWestPane = new JBorderLayoutPanel( 0, 0 );
	southFirstRowPane.setBackground( southPaneColor );
	southFirstRowWestPane.setBackground( southPaneColor );
	southFirstRowPane.add( southFirstRowWestPane, BorderLayout.WEST );

	  JPanel emptyPane = new JPanel();
	  emptyPane.setBackground( southPaneColor );

	southFirstRowPane.add( emptyPane, BorderLayout.EAST );

	southFirstRowPane.setPreferredSize( southRowPaneDimension );

	JLabel fileAttachLBL = new JLabel( " ���� ÷�� ", JLabel.CENTER );
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
	JButton fileAttachBTN = new JButton( " ã�� ���� " );
	fileAttachBTN.setFont( southPaneFont );
	fileAttachBTN.setBackground( southPaneColor );
	southFirstRowWestPane.add( fileAttachLBL, BorderLayout.WEST );
	southFirstRowWestPane.add( fileAttachTFOne, BorderLayout.CENTER );
	southFirstRowWestPane.add( fileAttachBTN, BorderLayout.EAST );

	// ���� ����ġ ��ư ������ �߰�

	fileAttachBTN.addActionListener(new ActionListener() {
	  public void actionPerformed(ActionEvent e) {

	    final int option = fileChooser.showDialog( HtmlFreeBoardPane.this, "����" );
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

	// ��. ���� ����ġ ��ư ������ �߰�

	// ��. ù��° ���� ÷��

	// �ι�° ���� ÷��

    southSndRowPane = new JBorderLayoutPanel( 1, 1 );

	JPanel southSndRowWestPane = new JBorderLayoutPanel( 0, 0 );
	southSndRowPane.setBackground( southPaneColor );
	southSndRowWestPane.setBackground( southPaneColor );
	southSndRowPane.add( southSndRowWestPane, BorderLayout.WEST );

	  emptyPane = new JPanel();
	  emptyPane.setBackground( southPaneColor );

	southSndRowPane.add( emptyPane, BorderLayout.EAST );

	southSndRowPane.setPreferredSize( southRowPaneDimension );

	fileAttachLBL = new JLabel( " ���� ÷�� ", JLabel.CENTER );
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
	fileAttachBTN = new JButton( " ã�� ���� " );
	fileAttachBTN.setFont( southPaneFont );
	fileAttachBTN.setBackground( southPaneColor );
	southSndRowWestPane.add( fileAttachLBL, BorderLayout.WEST );
	southSndRowWestPane.add( fileAttachTFTwo, BorderLayout.CENTER );
	southSndRowWestPane.add( fileAttachBTN, BorderLayout.EAST );

	// ���� ����ġ ��ư ������ �߰�

	fileAttachBTN.addActionListener(new ActionListener() {
	  public void actionPerformed(ActionEvent e) {

	    final int option = fileChooser.showDialog( HtmlFreeBoardPane.this, "����" );

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

	// ��. ���� ����ġ ��ư ������ �߰�

	// ��. �ι�° ���� ÷��

    southThrdRowPane = new JPanel();

	southThrdRowPane.setLayout( new FlowLayout( FlowLayout.CENTER ) );
	southThrdRowPane.setPreferredSize( southRowPaneDimension );
	southThrdRowPane.setBackground( northPaneColor );

	// ���, ��� ��ư

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

	// ��. ���, ��� ��ư

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


    // ��. ���� ������Ʈ ����

    // ��� ������Ʈ ����

    showStatus( AppRegistry.INITIALIZING_EDITOR_INTERFACE );

    htmlFreeMenuPane = new HtmlFreeEditorMenuPane();

    contentPane.add( southPane, BorderLayout.SOUTH );

    contentPane.add( northPane, BorderLayout.NORTH );

    contentPane.add( htmlFreeMenuPane, BorderLayout.CENTER );

    // ��. ��� ������Ʈ ����

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
			"�Խ��� ����� ����Ͻðڽ��ϱ�?",
			"�Խ��� ��� ���", JOptionPane.YES_NO_OPTION
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
			"�� ���� ����", JOptionPane.YES_NO_OPTION
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

    // Ÿ��Ʋ �Է� ���� üũ

    final String title = this.titleTF.getText();

    if( title == null || title.length() < 1 ) {

      JOptionPane.showMessageDialog( this, "������ �Է��ϼ���!" );

      return regiState;

    } else if( title != null && ( title.getBytes().length > subLimit ) ) {

      JOptionPane.showMessageDialog( this,
	  "������ ���ڼ��� �ʹ� �����ϴ�.(�ִ� ���� " + subLimit + "��, �ѱ� " + (subLimit/2) + "��)!" );

      return regiState;

    }

    // ��. Ÿ��Ʋ �Է� ���� üũ

    // �̸��� �ּ� �Է� ���� üũ

    final String eMail = this.eMailTF.getText().trim();

    if( eMail != null && eMail.length() < 1 ) {

      // Do nothing!
      // �� ���� �ּ� ��� ���� �ϵ��� ��.

    } else if( eMail != null && ( eMail.getBytes().length > emLimit ) ) {

      JOptionPane.showMessageDialog( this,
	  "E-Mail �ּ��� ���ڼ��� �ʹ� �����ϴ�.(�ִ� ���� " + emLimit + "��)!" );

      return regiState;

    } else if( ! isValidEmailAddress( eMail ) ) {

      JOptionPane.showMessageDialog( this, "�ùٸ� E-Mail �ּҰ� �ƴմϴ�." );

      return regiState;

    }

    // ��. �̸��� �ּ� �Է� ���� üũ

    /**@todo ���α׷��ú�� ��ü
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

    regiState = "HTML ������ �������Դϴ�.";

    msgDialog.setString( regiState );

    byte [] zipContentsBytes = getByteZipContent( baseDirUrl );

    if( zipContentsBytes == null ) {

      msgDialog.hideProgress();

      JOptionPane.showMessageDialog( this, "���ε��� HTML ������ ������ �� �����ϴ�." );

      return regiState;

    }

    final int zipContentsBytesLen = zipContentsBytes.length;

    final long upFileMaxSize = getUpFileMaxSize();

    if( upFileMaxSize > -1 && ( zipContentsBytesLen > upFileMaxSize ) ) {

      JOptionPane.showMessageDialog( this,
	      "����Ʈ ���� ������ �ִ� �뷮("
	      + ( (int)(upFileMaxSize/1000000) )
	      + " M)�� �ʰ��Ͽ����ϴ�." );

      return regiState;

    }

    if( fileEncode ) {

      regiState = "HTML ������ ���ڵ����Դϴ�.";

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

    // ���ε� ���� ������ üũ

    regiState = "���ε��� ���� ũ�⸦ üũ�ϰ� �ֽ��ϴ�.";

    msgDialog.setString( regiState );

    // ù ��° ÷�� ���� ũ�� ���ϱ�

    final long attachFileOneSize = FileUtilities.getFileLength( attachFileOne );

    // ��. ù ��° ÷�� ���� ũ�� ���ϱ�

    if( ! isRegistering ) {

      return regiState;

    }

    // �� ��° ÷�� ���� ũ�� ���ϱ�

    final long attachFileTwoSize = FileUtilities.getFileLength( attachFileTwo );

    // ��. �� ��° ÷�� ���� ũ�� ���ϱ�

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
	      "÷�� �� ����Ʈ ���� ����� �ִ� �뷮("
	      + ( (int)(upFileMaxSize/1000000) )
	      + " M)�� �ʰ��Ͽ����ϴ�." );

      return regiState;

    }

    // ��. ���ε� ���� ������ üũ

    regiState = "÷���� ������ �а� �ֽ��ϴ�.";

    msgDialog.setString( regiState );

    byte [] attachFileOneBytes = null;

    if( attachFileOne != null ) {

       regiState = "÷���� ù��° ������ �а� �ֽ��ϴ�.";

       msgDialog.setString( regiState );

       attachFileOneBytes = FileUtilities.readBytes( attachFileOne );

    }

    byte [] attachFileTwoBytes = null;

    if( ! isRegistering ) {

      return regiState;

    }

    if( attachFileTwo != null ) {

       regiState = "÷���� �ι�° ������ �а� �ֽ��ϴ�.";

       msgDialog.setString( regiState );

       attachFileTwoBytes = FileUtilities.readBytes( attachFileTwo );

    }

    if( ! isRegistering ) {

      return regiState;

    }

    if( attachFileOne != null && attachFileOneBytes != null ) {

	  if( fileEncode ) {

	    regiState = "÷���� ù��° ������ ���ڵ����Դϴ�.";

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

	    regiState = "÷���� �ι�° ������ ���ڵ����Դϴ�.";

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

    // �� ������ Base64�� ���ڵ��Ͽ� ����.

    final CgiVariable paxNickCgiVar = cgiVariableArgs.getCgiVariableOfCookieName( "paxNick" );

    if( paxNickCgiVar != null ) {

      final String paxNickNameText = this.paxNickNameText;

      if( paxNickNameText != null ) {

	paxNickCgiVar.setValue( getBase64EncodedText( paxNickNameText ) );

      }

    }

    // ��. �� ���� Base64 ���ڵ� �ϱ�.

    // ���� �ڵ� ����

    final CgiVariable stockCodeCgiVar = cgiVariableArgs.getCgiVariable( "itemcode" );

    if( (stockCodeCgiVar != null) && (stockCodeTF != null) ) {

      stockCodeCgiVar.setValue( getBase64EncodedText( this.stockCodeTF.getText().trim() ) );

    }

    // ��. ���� �ڵ� ����

    // �ʸ� ����

    final CgiVariable nickNameVar = cgiVariableArgs.getCgiVariable( "senderName" );

    if( nickNameVar != null && this.nickNameTF != null ) {

       nickNameVar.setValue( getBase64EncodedText( this.nickNameTF.getText().trim() ) );

    }

    // ��. �ʸ� ����

//    cgiVariableArgs.print();

    regiState = "������ ������Դϴ�.";

    msgDialog.setString( regiState );

    InputStream resultIn = cgiVariableArgs.callPost( this.saveCgiBinUrlText, msgDialog );

    if( resultIn == null ) {

      JOptionPane.showMessageDialog( this, "���� ���� �����Դϴ�." );

      return regiState;

    }

    regiState = "������ ��ٸ��� �ֽ��ϴ�.";

    msgDialog.setString( regiState );

    try {

      BufferedReader in = new BufferedReader( new InputStreamReader( resultIn ) );

      String lineText = in.readLine(); // ù ° ���� ���� ���� ���� �ؽ�Ʈ�� �ѱ�.

      boolean successRegistration =
		( ( lineText == null ) ? false : lineText.equalsIgnoreCase( "SUCCESS" ) );

//      boolean failedRegistration = lineText == null ? false : lineText.equalsIgnoreCase( "ERROR" );

      if( successRegistration ) {

	  regiState = "�Խ��� ��� ����";

	  final String [] options = {
	    "Ȯ��"
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

	  regiState = "�Խ��� ��� ����";

	  showErrorMessage( in, "�Խ��� ��� ����" );

	  return regiState;

      }

    } catch ( IOException e) {

      Utility.debug( e );

      JOptionPane.showMessageDialog( this, AppRegistry.SERVER_COMM_FAIL_MSG, "���� ��� ����", JOptionPane.YES_OPTION );

    } catch ( OutOfMemoryError e ) {

      regiState = "�޸� ����";

      JOptionPane.showMessageDialog( this,
				      new String [] {
					    "÷�� ������ �ʹ� ũ�ų� ���� ������ �ʹ� Ů�ϴ�." ,
					    "�ٸ� ���α׷��� �����Ͻ� �� �ٽ� �õ��ϼ���."
				      },
				      "�޷θ� ����", JOptionPane.YES_OPTION );

    } catch ( Error e ) {

      regiState = "�� �� ���� ����";

      JOptionPane.showMessageDialog( this, new String [] { "�����ڿ��� ������ �ּ���.", "���� ���� :", e.getMessage() },
				      "�˼� ���� ����", JOptionPane.YES_OPTION );

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
		      "��ſ� �����ϰ� �u���߽��ϴ�.",
		      "������ ������ �ֽñ� �ٶ��ϴ�."
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
      // 0 ��°�� HTML ������.
      // 1 ��°�� zip ������.

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
	    new StockCodeSearchJDialog( frame, "�����ڵ� ����",
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
	      "÷�� �� ����Ʈ ���� ������(�ִ�: "
	      + ( (int)(upFileMaxSize/1000000) )
	      + " M)�� �ʰ��Ͽ����ϴ�." );

    }

    return result;

  }

}
