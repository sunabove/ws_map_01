
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
import java.io.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import jcosmos.*;
//import HtmlFreeEditorApplet;

public class HtmlFreeEditorMenuPane extends JPanel {

  private FontManager fm = new FontManager(); // just do it, please

  private boolean fontMenuClicked; //sets whether mouse clicked on font menu

  private JPanel contentPane;

  private JPanel menuPanel = new JPanel() {

      public void paint(Graphics g) {

	  super.paint( g );

	  Dimension size = getPreferredSize();

	  Component comps [] = getComponents();

	  int w = 0, h = 0;

	  for( int i = 0, len = comps.length ; i < len; i ++ ) {

	     Rectangle bounds = comps[i].getBounds();

	     int lw = bounds.x + bounds.width;
	     int lh = bounds.y + bounds.height;

	     w = ( lw > w ) ? lw : w;
	     h = ( lh > h ) ? lh : h;

	  }

	  // calibrate by insets

	  Insets insets = getInsets();

	  w += insets.right ;
	  h += insets.bottom ;

	  // end of calibrating by insets

	  if( w < 1 || h < 1 ) {

	     return;

	  }

	  if( size.width != w || size.height != h ) {

	    setPreferredSize( new Dimension(w, h) );

	    setSize( w, h );

	    Container parent = getParent();

	    parent.validate();

	  }

      }

  };

  private HtmlFreeEditorPane htmlFreeEditorPane = new HtmlFreeEditorPane();

  int defaultFontSizeIndex = 2;
  int defaultFontNameIndex = 1;

  private boolean isShowingDocumentInfoVisually;

  JComboBox fontSizeComboBox = new JComboBox( new String [] { "8", "10", "12", "14", "18", "24", "28", "36", "48", "72" } );
  JComboBox fontFamilyComboBox = new JComboBox(new String [] { "Times New Roman", "Arial", "Monospaced" } );
//  JComboBox fontFamilyComboBox = new JComboBox(new String [] { "Times New Roman", "Arial", "Monospaced", "HY������M", "HY�ü�B", "HY�Ÿ���", "HY�׷���M", "HY����L", "MS Gothic" } );

  JComboBox borderWidthComboBox = new JComboBox( new String [] { "1", "2", "3", "4", "5" } );

  JComboBox arrowTypeComboBox = new JComboBox(
					new Object [] {
					    Utility.getResourceImageIcon("non_arrow.gif"),
					    Utility.getResourceImageIcon("right_arrow.gif"),
					    Utility.getResourceImageIcon("left_arrow.gif"),
					    Utility.getResourceImageIcon("double_arrow.gif")
					}
				    );

  JButton   openBtn, newBtn, saveBtn
	  , boldicBtn, italicBtn, underLineBtn
	  , changeTextColorBtn, changeLineColorBtn, changeFillColorBtn
	  , tableBtn, wordBoxBtn
	  , insertImageBtn, linkImageBtn, insertMMBtn, insertChartBtn
	  , setHrefBtn, screenCaptureBtn, voiceRecordBtn
	  , simpleLineBtn, rectBtn, circleBtn, oneArrowLineBtn
	  , multiLineBtn, freeLineBtn, helpBtn;

  // The Else //

  JButton barBtn [] = new JButton [9] ;

  Border menuButtonBorder = BorderFactory.createEmptyBorder();
  Border barButtonBorder = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));

  // End of The Else

  //Construct the frame
  public HtmlFreeEditorMenuPane() {

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

  public HtmlFreeEditorPane getHtmlFreeEditorPane() {

    return htmlFreeEditorPane;

  }

  //Component initialization
  private void jbInit() throws Exception  {

    contentPane = this;

    contentPane.setLayout( new BorderLayout() );

    contentPane.add(menuPanel, BorderLayout.NORTH);

    contentPane.add(htmlFreeEditorPane, BorderLayout.CENTER);

    menuPanel.setLayout( new FlowLayout(FlowLayout.LEFT, 2, 0 ) );
//    menuPanel.setBorder( BorderFactory.createEmptyBorder( 2, 2, 2, 2) );

    menuPanel.setBorder( BorderFactory.createEtchedBorder( 1 ) );

    // sets bar buttons

    for(int i = 0, len = barBtn.length ; i < len; i ++ ) {

       barBtn[i] = new JButton();
       barBtn[i].setPreferredSize(new Dimension(4, 25));
       barBtn[i].setBorder(barButtonBorder);

    }

    // end of setting bar buttons

    saveBtn = new MenuJButton( "���� ����", menuButtonBorder, "save.gif",
			      new java.awt.event.MouseAdapter() {

				 public void mouseClicked(MouseEvent e) {
				     saveBtn_mouseClicked(e, true);
				 }
			      }
	      );

    openBtn = new MenuJButton( "���� ����", menuButtonBorder, "open.gif",
    new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
	openBtn_mouseClicked(e);
      }
    });

    newBtn = new MenuJButton( "�� ����", menuButtonBorder, "new.gif",
    new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
	newBtn_mouseClicked(e);
      }
    });

    italicBtn = new MenuJButton( "����Ӳ�", menuButtonBorder, "italic.gif",
    new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
	italicBtn_mouseClicked(e);
      }
    });

    boldicBtn = new MenuJButton( "���ϰ�", menuButtonBorder, "boldic.gif",
    new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
	boldicBtn_mouseClicked(e);
      }
    });

    underLineBtn = new MenuJButton( "����", menuButtonBorder, "under_line.gif",
    new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
	underLineBtn_mouseClicked(e);
      }
    });

    insertImageBtn = new MenuJButton( "�̹��� ����", menuButtonBorder, "insert_image.gif",
    new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
	insertImageBtn_mouseClicked(e);
      }
    });

    linkImageBtn = new MenuJButton( "��ũ �̹��� ����", menuButtonBorder, "insert_link_image.gif",
    new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
	linkImageBtn_mouseClicked(e);
      }
    });

    changeTextColorBtn = new MenuJButton( "�� ����", menuButtonBorder, "text_color.gif",
    new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
	changeTextColorBtn_mouseClicked(e);
      }
    });

    fontFamilyComboBox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
	fontFamilyComboBox_itemStateChanged(e);
      }
    });

    fontSizeComboBox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
	fontSizeComboBox_itemStateChanged(e);
      }
    });

    borderWidthComboBox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
	borderWidthComboBox_itemStateChanged(e);
      }
    });

    insertMMBtn = new MenuJButton( "��Ƽ�̵�� ����", menuButtonBorder, "insert_video.gif",
    new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
	insertMMBtn_mouseClicked(e);
      }
    });

    insertChartBtn = new MenuJButton( "��Ʈ ����", menuButtonBorder, "insert_chart.gif",
    new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
	insertChartBtn_mouseClicked(e);
      }
    });

    wordBoxBtn = new MenuJButton( "�� ���� ����", menuButtonBorder, "insert_word_box.gif",
    new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
	wordBoxBtn_mouseClicked(e);
      }
    });

    changeFillColorBtn = new MenuJButton( "ä��� ����", menuButtonBorder, "fill_color.gif",
    new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
	changeFillColorBtn_mouseClicked(e);
      }
    });


    changeLineColorBtn = new MenuJButton( "�� ����", menuButtonBorder, "line_color.gif",
    new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
	changeLineColorBtn_mouseClicked(e);
      }
    });

    setHrefBtn = new MenuJButton( "HTML ��ũ", menuButtonBorder, "html_link.gif",
    new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
	setHrefBtn_mouseClicked(e);
      }
    });

    tableBtn = new MenuJButton( "���̺� ����", menuButtonBorder, "insert_table.gif",
    new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
	tableBtn_mouseClicked(e);
      }
    });

    screenCaptureBtn = new MenuJButton( "ȭ�� ĸ��", menuButtonBorder, "screen_capture.gif",
    new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
	screenCaptureBtn_mouseClicked(e);
      }
    });

    voiceRecordBtn = new MenuJButton( "���� ����", menuButtonBorder, "voice_record.gif",
    new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
	voiceRecordBtn_mouseClicked(e);
      }
    });

    simpleLineBtn = new MenuJButton( "����", menuButtonBorder, "simple_line.gif",
    new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
	shapeBtn_mouseClicked(e, ShapeElement.ZERO_ARROW_LINE );
      }
    });

    rectBtn = new MenuJButton( "�簢��", menuButtonBorder, "rectangle.gif",
    new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
	shapeBtn_mouseClicked(e, ShapeElement.RECTANGLE );
      }
    });

    circleBtn = new MenuJButton( "Ÿ��", menuButtonBorder, "circle.gif",
    new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
	shapeBtn_mouseClicked(e, ShapeElement.CIRCLE );
      }
    });

    oneArrowLineBtn = new MenuJButton( "ȭ�� ����", menuButtonBorder, "arrow_line.gif",
    new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
	shapeBtn_mouseClicked(e, ShapeElement.FOREWARD_ONE_ARROW_LINE );
      }
    });

    multiLineBtn = new MenuJButton( "���� ����", menuButtonBorder, "multi_line.gif",
    new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
	shapeBtn_mouseClicked(e, ShapeElement.MULTI_LINE );
      }
    });

    freeLineBtn = new MenuJButton( "���� �", menuButtonBorder, "free_line.gif",
    new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
	shapeBtn_mouseClicked(e, ShapeElement.FREE_LINE );
      }
    });

    helpBtn = new MenuJButton( "����", menuButtonBorder, "help.gif",
    new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
	helpBtn_mouseClicked( e );
      }
    });

    menuPanel.add(barBtn[0]);

    if( AppRegistry.OPEN ) {

      menuPanel.add(openBtn);

    }

    if( AppRegistry.NEW ) {

      menuPanel.add(newBtn);

    }

    if( AppRegistry.SAVE ) {

      menuPanel.add(saveBtn);

    }

    menuPanel.add(barBtn[1]);

    menuPanel.add(fontFamilyComboBox);
    menuPanel.add(fontSizeComboBox);

    menuPanel.add(barBtn[2]);

    menuPanel.add(italicBtn);
    menuPanel.add(underLineBtn);

    menuPanel.add(barBtn[3]);

    menuPanel.add(changeTextColorBtn);
    menuPanel.add(changeFillColorBtn);
    menuPanel.add(changeLineColorBtn);

    menuPanel.add(barBtn[4]);

    if( AppRegistry.TABLE ) {

      menuPanel.add(tableBtn);

    }

    if( AppRegistry.WORD_BOX ) {

      menuPanel.add(wordBoxBtn);

    }

    menuPanel.add(barBtn[5]);

    if( AppRegistry.IMAGE ) {

      menuPanel.add(insertImageBtn);

    }

    if( AppRegistry.LINK_IMAGE ) {

      menuPanel.add(linkImageBtn);

    }

    if( AppRegistry.MULTI_MEDIA ) {

      menuPanel.add(insertMMBtn);

    }

    if( AppRegistry.CHART ) {

      menuPanel.add( insertChartBtn );

    }

    menuPanel.add( setHrefBtn );

    if( AppRegistry.CAPTURE ) {

      menuPanel.add( screenCaptureBtn );

    }

    if( AppRegistry.RECORD ) {

      menuPanel.add( voiceRecordBtn );

    }

    menuPanel.add( barBtn[6] );

    if( AppRegistry.SHAPES ) {

      menuPanel.add( simpleLineBtn );

      menuPanel.add( oneArrowLineBtn );

      menuPanel.add( rectBtn );

      menuPanel.add( circleBtn );

      menuPanel.add( multiLineBtn );

      menuPanel.add( freeLineBtn );

      menuPanel.add( arrowTypeComboBox );

    }

    menuPanel.add( borderWidthComboBox );

    menuPanel.add( barBtn[ 7 ] );

    if( AppRegistry.HELP ) {

      menuPanel.add( helpBtn );

      menuPanel.add( barBtn[ 8 ] );

    }

    this.fontSizeComboBox.setSelectedIndex( this.defaultFontSizeIndex );
    this.fontFamilyComboBox.setSelectedIndex( this.defaultFontNameIndex );

    JPanel statusPanel = new JPanel();

    statusPanel.setLayout( new GridLayout(1, 2, 3, 0 ) );

    statusPanel.setBorder( BorderFactory.createEmptyBorder( 2, 5, 2, 5 ) );

    // arrow type combo box size setting

    final JComboBox arrowTypeComboBox = this.arrowTypeComboBox;

    final Dimension size = arrowTypeComboBox.getPreferredSize();

    size.setSize( 70, size.height );

    arrowTypeComboBox.setPreferredSize( size );

    arrowTypeComboBox.setSize( size );

    // end of arrwo type combox box size setting

    arrowTypeComboBox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
	arrowTypeComboBox_itemStateChanged(e);
      }
    });

    // Code by sbmoon.

  }

  void fontFamilyComboBox_itemStateChanged(ItemEvent e) {

     applyFont();

  }

  void fontSizeComboBox_itemStateChanged(ItemEvent e) {

     applyFont();

  }

  void borderWidthComboBox_itemStateChanged(ItemEvent e) {

     applyBorderWidth();

  }

  void changeTextColorBtn_mouseClicked(MouseEvent e) {
     this.htmlFreeEditorPane.applyTextColor();
  }

  void underLineBtn_mouseClicked(MouseEvent e) {
      this.htmlFreeEditorPane.applyUnderLine();
  }

  void boldicBtn_mouseClicked(MouseEvent e) {
      this.htmlFreeEditorPane.applyBoldic();
  }

  void italicBtn_mouseClicked(MouseEvent e) {
      this.htmlFreeEditorPane.applyItalic();
  }

  public void saveBtn_mouseClicked(MouseEvent e, boolean upLoad) {

      try {

	  this.htmlFreeEditorPane.saveHtml();

      } catch (Exception ex) {

	  Utility.debug( ex );

      }

  }

  void insertImageBtn_mouseClicked(MouseEvent e) {

     this.htmlFreeEditorPane.addImage();

  }

  void linkImageBtn_mouseClicked(MouseEvent e) {

     this.htmlFreeEditorPane.addLinkImage();

  }

  void insertMMBtn_mouseClicked(MouseEvent e) {

      this.htmlFreeEditorPane.addMultiMemia();

  }

  void insertChartBtn_mouseClicked(MouseEvent e) {

      this.htmlFreeEditorPane.addChart();

  }

  public void applyFont() {

     if( isShowingDocumentInfoVisually() ) {

	// HtmlEditorPane ���� ���콺�� ��������,
	// ��Ʈ ������ �޴��� ���������� ��Ÿ����.
	// ��Ʈ ������ ���������� ���������� ��Ÿ�� ��쿡,
	// �̺�Ʈ�� ������ �ݺ� �߻��Ǵ� ���� �����ϱ� ���Ͽ�,
	// ��, ���� ������ ���� ������ ���ؼ�,
	// if ���� �߰��Ѵ�.

	return;

     }

     final String ffn = "" + this.fontFamilyComboBox.getSelectedItem(); // font family name
     final String size = "" + this.fontSizeComboBox.getSelectedItem();  // font size

     final int type = Font.PLAIN;   // font type

     this.htmlFreeEditorPane.applyFont( ffn, type, size );

  }

  public void applyBorderWidth() {

      if( isShowingDocumentInfoVisually() ) {

	return;

      }

      String borderWidthText = "" + borderWidthComboBox.getSelectedItem();  // borderSize size

      this.htmlFreeEditorPane.applyBorderWidth( borderWidthText );

  }

  void tableBtn_mouseClicked(MouseEvent e) {

     this.htmlFreeEditorPane.addTable();

  }

  void wordBoxBtn_mouseClicked(MouseEvent e) {

      this.htmlFreeEditorPane.addWordBox();

  }

  void changeLineColorBtn_mouseClicked(MouseEvent e) {

    this.htmlFreeEditorPane.changeLineColor();

  }

  void changeFillColorBtn_mouseClicked(MouseEvent e) {

      this.htmlFreeEditorPane.changeFillColor();

  }

  void openBtn_mouseClicked(MouseEvent e) {

      this.htmlFreeEditorPane.openFile();

  }

  public void newBtn_mouseClicked(MouseEvent e) {

      this.htmlFreeEditorPane.setNewDocument();

  }

  // ��ũ�� ĸ�� ���α׷� ���� �� ĸ�� ȭ���� �̹����� �ٲ� ��(����� �� ���α׷� ����
  // �� �ִ� �κ�) �����Ϳ� �����ϱ�.

  public void screenCaptureBtn_mouseClicked(MouseEvent e) {

    new Thread() {

      public void run() {

	captureScreen();

      }

    }.start();

  }

  private void captureScreen() {

      final String command = AppRegistry.SCREEN_CAPTURE_COMMAND;

      try {

	Utility.debug( this, "CAPTURE PROGRAM = " + command );

	final Process process = java.lang.Runtime.getRuntime().exec( command );

	final int result = process.waitFor();

	Utility.debug(this, "RESULT = " + result );

	Utility.debug(this, "CAPTURE PROGRAM HAS EXECUTED WELL." );

	final File captureFile = new File( AppRegistry.CAPTURE_DIR,
					AppRegistry.SCREEN_CAPUTER_FILE_NAME );

	if( captureFile != null && captureFile.exists() ) {

	  final HtmlEditorPane editor = this.getHtmlFreeEditorPane().getEditor();

	  editor.addImage( captureFile );

	}

      } catch (Exception ex ) {

	Utility.debug( ex );

      }

  }

  public void voiceRecordBtn_mouseClicked(MouseEvent e) {

    new Thread() {

      public void run() {

	recordVoice();

      }

    }.start();

  }

  private void recordVoice() {

      final String command = AppRegistry.VOICE_RECORD_COMMAND;

      try {

	Utility.debug( this, "VOICE RECORD PROGRAM = " + command );

	final Process process = java.lang.Runtime.getRuntime().exec( command );

	final int result = process.waitFor();

	Utility.debug(this, "RESULT = " + result );

	Utility.debug(this, "VOICE RECORD PROGRAM HAS EXECUTED WELL." );

	final File voiceRecordFile = new File( AppRegistry.VOICE_RECORD_DIR,
					    AppRegistry.VOICE_RECORD_FILE_NAME );

	if( voiceRecordFile != null ) {

	  final HtmlEditorPane editor = this.getHtmlFreeEditorPane().getEditor();

	  editor.addVoiceRecordFile( voiceRecordFile );

	}

      } catch (Exception ex ) {

	Utility.debug( ex );

      }

  }

  public void shapeBtn_mouseClicked(MouseEvent e, int shapeType) {

    this.htmlFreeEditorPane.addShape( shapeType );

  }

  // ��. ��ũ�� ĸ�� �� �̹��� �����ϱ�

  public void openServerZipFile( String urlText ) {

      this.htmlFreeEditorPane.openServerZipFile( urlText );

  }

  public String getOnlyTexts() {

    return this.htmlFreeEditorPane.getOnlyTexts();

  }

  void setHrefBtn_mouseClicked(MouseEvent e) {

      this.htmlFreeEditorPane.setHyperLink();

  }

  void helpBtn_mouseClicked(MouseEvent e) {
      // help button �� ���� ���� ��......

      this.htmlFreeEditorPane.help( e );

  }

  void arrowTypeComboBox_itemStateChanged(ItemEvent e) {

     if( isShowingDocumentInfoVisually() ) {

	return;

     }

     this.htmlFreeEditorPane.applyArrowType( arrowTypeComboBox.getSelectedIndex() );

  }

  final protected void showDocumentInfoVisually( final HtmlDocument doc ) {

    if( doc == null ) {

      setShowingDocumentInfoVisually( false );

      return;

    }

    try {

      setShowingDocumentInfoVisually( true );

      final StringElement ce = doc.getCaretElement();

      if( ce == null ) {

	setShowingDocumentInfoVisually( false );

	return;

      }

      final Font font = ce.getFont();

      if( font == null ) {

	setShowingDocumentInfoVisually( false );

	return;

      }

      final int fontSize = font.getSize();

      final String fontName = FontManager.backwardFontName( font.getName() );

      // UI �� ��Ÿ���� ��Ʈ�� ���� ��Ʈ�� �ٸ���.
      // �ڹ� ��Ʈ�� IE �������� ��Ʈ�� �ٸ��� ������,
      // �ڹ� ��Ʈ�� IE �������� ��Ʈ ����� ������,
      // backwardFontName( ... ) �� ����Ͽ�,
      // UI�� ��Ÿ�� �־�� �� ��Ʈ�� ��Ȯ�� ����Ѵ�.

      final JComboBox fontSizeCB = this.fontSizeComboBox;
      final JComboBox fontNameCB = this.fontFamilyComboBox;

      setJComboBoxSelected( fontSizeCB, "" + fontSize );

      setJComboBoxSelected( fontNameCB, fontName );

      final ImageElement sie = ImageElement.SEL_IMG_ELEM; // selected image element

      if( sie != null && sie instanceof ShapeElement ) {

	final ShapeElement shapeElement = (ShapeElement) sie;

	final int thickness = shapeElement.getThickness();

	final JComboBox thicknessJCB = this.borderWidthComboBox;

	setJComboBoxSelected( thicknessJCB, "" + thickness );

	final JComboBox arrowTypeComboBox = this.arrowTypeComboBox;

	int arrowType = shapeElement.getShapeType();

	if( arrowType > arrowTypeComboBox.getItemCount() - 1 ) {

	  arrowType = 0;

	}

	if( arrowTypeComboBox.getSelectedIndex() != arrowType ) {

	  arrowTypeComboBox.setSelectedIndex( arrowType );

	}

      }

    } finally {

      setShowingDocumentInfoVisually( false );

    }

  }

  private void setJComboBoxSelected(final JComboBox comboBox, final Object obj ) {

    if( comboBox.getSelectedItem().equals( obj ) ) {

      // ���� ���õ� ���� ������ ���� ������ �Լ��� �����Ѵ�.

      return;

    }

    for( int i = 0, len = comboBox.getItemCount(); i < len; i ++ ) {

      if( comboBox.getItemAt( i ).equals( obj ) ) {

	comboBox.setSelectedIndex( i );

	return;

      }

    }

  }

  final private void setShowingDocumentInfoVisually(boolean b) {

    this.isShowingDocumentInfoVisually = b;

  }

  final private boolean isShowingDocumentInfoVisually() {

    return isShowingDocumentInfoVisually;

  }

  public static void main(String [] args) {

    final JFrame f = new JFrame( AppRegistry.APP_NAME );

    final Container con = f.getContentPane();

    con.setLayout( new BorderLayout( 1, 1 ) );

    final HtmlFreeEditorMenuPane htmlFreeEditorMenuPane = new HtmlFreeEditorMenuPane();

    AppRegistry.MAIN_APP_CLASS = HtmlFreeEditorMenuPane.class;

    con.add( htmlFreeEditorMenuPane, BorderLayout.CENTER );

    f.addWindowListener(new WindowAdapter() {

      public void windowClosing(WindowEvent e) {

	AppRegistry.systemExit( 0 );

      }

    });

    f.setSize( 670, 500 );

    f.setVisible( true );

  }

}
