package htmleditor;

/**
 * Title:        Test Publishing System
 * Description:  Internet Based Test Publishing System V1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.applet.*;
import jcosmos.*;
//import HtmlFreeEditorApplet;

public class HtmlFreeEditorPane extends JPanel implements CharacterUtility, ModeInterface {

  // static members

  private static boolean hasSavedAsFile = false;

  // end of static members

  // ui components

  private HtmlEditorPane htmlEditorPane = new HtmlEditorPane();

  private JPanel contentPane;
  private JHtmlEditorScrollPane scrollPane;

  // End of ui components declaration

  // Data

  // end of data

  /**Construct the frame*/
  public HtmlFreeEditorPane() {

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

  public HtmlEditorPane getEditor() {

    return this.htmlEditorPane;

  }

  /**Component initialization*/
  private void jbInit() throws Exception  {

    // setting main components

    contentPane = (JPanel) this;

//    contentPane = (JPanel) getContentPane() ;

    contentPane.setLayout( new BorderLayout() );

    final JPanel editorPane = new JPanel();

    editorPane.setLayout( new BorderLayout() );

    editorPane.add( htmlEditorPane, BorderLayout.CENTER );

    scrollPane = new JHtmlEditorScrollPane( editorPane, htmlEditorPane );

    contentPane.add( scrollPane, BorderLayout.CENTER );

    // end of main components setting

  }

  public static boolean hasSavedAsFile() {

     return hasSavedAsFile;

  }

  final public static void setHasSavedAsFile(final boolean b) {

      hasSavedAsFile = b;

  }

  final public void openFile() {

    openFile( this );

    showStatus( null );

  }

  final private void openFile(final Component comp ) {

     JFileChooser fileChooser = HtmlEditorPane.htmlOpenFileChooser;

     if( fileChooser == null ) {

	 HtmlEditorPane.initOpenFileChoosers();

	 fileChooser = HtmlEditorPane.htmlOpenFileChooser;

     }

     // selects a file to save
     File file = null;

     final int option = fileChooser.showOpenDialog( comp );

     if( option == JFileChooser.APPROVE_OPTION ) {

	 file = fileChooser.getSelectedFile();

     } else {

	 return;   // cancels saving as html file.

     }

     // end of selecting a file to save.

     final String fileName = file.getName().trim().toUpperCase();

     if( fileName.endsWith( ".HTML" ) || fileName.endsWith(".HTM") ) {

       // Do nothing!

     } else if( fileName.endsWith( ".HFZ" ) ) {

       // Do nothing!

     } else {

	file = new File( file.getParent(), fileName + ".HTML" );

	if( ! file.exists() ) {

	  JOptionPane.showMessageDialog( this, "HTML 문서가 아닙니다." );

	  this.repaint();

	  return;

	}

     }

     try {

	 openFile( file );

     } catch (Exception e) {

	 Utility.debug( e );

	 JOptionPane.showMessageDialog (comp, file.getName() + "을 읽기 에러" );

	 this.repaint();

     } finally {

	  mode.setMode( Mode.EDIT );

     }

  }

  final private void openFile(final File file ) throws Exception {

      final String fileName = file.getName().toUpperCase().trim();

      if( fileName.endsWith( ".HTML") || fileName.endsWith( ".HTM" ) ) {

	 openNormalHtmlFile( file );

      } else {

	 openZippedHtmlFile( file );

      }

  }

  final private void openZippedHtmlFile(final File file ) throws Exception {

      final File tempDir = HtmlEditorPane.getTempDir( this );

      if( tempDir == null ) {

	  return;

      }

      final File tempFile = new File( tempDir, file.getName() );

      new WebCopy( null ).copy( file, tempFile );

      openTempZippedHtmlFile( tempFile );

  }

  final public void openServerZipFile(final String urlText ) {

      final Cursor cursor = getCursor();

      setCursor( Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );

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
	return;

      }

      final File tempFile = new File( tempDir, fileName );

      try {

	showStatus( AppRegistry.HTML_FILE_DOWLOAD_MSG );

	new WebCopy( null ).copy( urlText, tempFile );

      } catch (IOException e) {

	Utility.messageDialog( this, "CANNOT COPY SERVER ZIP HTML FILE TO LOCAL FOLDER", "SERVER ZIP FILE COPY ERROR" );
	Utility.debug( e );
	return;

      }

      openTempZippedHtmlFile( tempFile );

      setCursor( cursor );

  }

  final private void openTempZippedHtmlFile(final File tempFile ) {

      File normalHtmlFile = null;

      try {

	  try {

	     showStatus( AppRegistry.UNZIPPING_HTML_FILE_MSG );

	     File tempDir = HtmlEditorPane.getTempDir( this );

	     final int tempDirIdx = Math.abs( (int)(System.currentTimeMillis() ) );

	     tempDir = new File( tempDir, "tmp" + tempDirIdx );

	     tempDir.mkdir();

	     final UnZip unzip = new UnZip( null );

	     final Vector fileList = unzip.unzip( tempFile, tempDir );

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
	     Utility.warningDialog( this, "Cannot decompress zipped html file [" + tempFile.getCanonicalPath() + "]", "CANNOT UNZIP" );

	  }

      } catch (Exception e) {

	  Utility.debug(  e );

	  Utility.warningDialog( this, "Cannot copy zipped html file to temporary directory", "CANNOT COPY ZIPPED HTML FILE" );

	  return;

      }

      if( normalHtmlFile != null ) {

	  try {

	     openNormalHtmlFile( normalHtmlFile );

	  } catch (Exception e) {

	      Utility.debug(  e );

	      Utility.warningDialog( this, "Cannot open html file normally", "INVALID HTML FREE FORMAT FILE" );

	  }

      } else {

	 Utility.warningDialog( this, "Cannot find a html file in the zipped html file", "CANNOT FIND HTML FILE" );

	 return;

      }

  }

  final public void openNormalHtmlFile(final File file ) throws IOException {

      final HtmlEditorPane editor = this.htmlEditorPane;

      final Cursor cursor = editor.getCursor();

      editor.setCursor( Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );

      showStatus( AppRegistry.HTML_FILE_READING_MSG );

      final HtmlLayer layer = new HtmlLayer( file );

      layer.realize( this.htmlEditorPane );

      HtmlFreeEditorPane.setHasSavedAsFile( true );

      editor.setCursor( cursor );

      showStatus( null );

      Utility.debug(this, "Done OPEN" );

  }

  /**@todo get only texts
   *
   */
  final public String getOnlyTexts() {

    final HtmlEditorPane editor = this.htmlEditorPane;

    return editor.getOnlyTexts();

  }

  final public void setHyperLink() {

     final HtmlEditorPane editor = this.htmlEditorPane;

     editor.setHyperLink( this );

  }

  final public void applyTextColor() {

     final HtmlEditorPane editor = this.htmlEditorPane;

     editor.applyTextColor( );

  }

  final public void changeFillColor()  {

       final HtmlEditorPane editor = this.htmlEditorPane;

       editor.changeFillColor();

  }

  final public void changeLineColor()  {

       final HtmlEditorPane editor = this.htmlEditorPane;

       editor.changeLineColor();

  }

  final public void applyBoldic() {

      final HtmlEditorPane editor = this.htmlEditorPane;

      editor.applyBoldic();

  }

  final public void applyItalic() {

      final HtmlEditorPane editor = this.htmlEditorPane;

      editor.applyItalic();

  }

  final public void applyUnderLine() {

      final HtmlEditorPane editor = this.htmlEditorPane;

      editor.applyUnderLine();

  }

  final public void addTable() {

      final HtmlEditorPane editor = this.htmlEditorPane;

      editor.addTable();

  }

  final public void addWordBox() {

      final HtmlEditorPane editor = this.htmlEditorPane;

      editor.addWordBox();

  }

  final public void addImage() {

      final HtmlEditorPane editor = this.htmlEditorPane;

      editor.addImage();

  }

  final public void addLinkImage() {

      final HtmlEditorPane editor = this.htmlEditorPane;

      editor.addLinkImage();

  }

  final public void addMultiMemia() {

      final HtmlEditorPane editor = this.htmlEditorPane;

      editor.addMultiMemia();

  }

  final public void addChart() {

      final HtmlEditorPane editor = this.htmlEditorPane;

      editor.addChart();

  }

  final public void saveHtml( final String dirName, final String fileName) throws IOException {

    final HtmlEditorPane editor = getEditor();

      if( editor == null ) {

	return;

      }

      editor.saveHtml( dirName, fileName );

  }

  final public void saveHtml() throws IOException {

      final HtmlEditorPane editor = getEditor();

      if( editor == null ) {

	return;

      }

      editor.saveHtml( );

  }

  final public File [] saveAsTemporaryFile() {

      final HtmlEditorPane editor = getEditor();

      if( editor == null ) {

	return null;

      }

      return editor.saveAsTemporaryFile();

  }

  final public void setNewDocument( ) {

      final HtmlEditorPane editor = this.htmlEditorPane;

      editor.setNewDocument( );

  }

  final public void applyFont(String fontFamily, int type, String fontSizeText ) {

      final HtmlEditorPane editor = this.htmlEditorPane;

      if( editor == null ) {

	  return;

      }

      editor.applyFont( fontFamily, type, fontSizeText );

  }

  public void applyBorderWidth(final String borderWidthText) {

     final HtmlEditorPane editor = this.htmlEditorPane;

     if( editor == null ) {

	 return;

     }

     int borderWidth = 1;

     try {

	 borderWidth = new Integer( borderWidthText ).intValue();

     } catch (Exception e) {

	 editor.requestFocus();

	 return;

     }

     editor.applyBorderWidth( borderWidth );

  }

  public void addShape( final int shapeType) {

    this.htmlEditorPane.addShape( shapeType );

  }

  public void applyArrowType( final int arrowType) {

    this.htmlEditorPane.applyArrowType( arrowType );

  }

  public void help(MouseEvent e) {
      // help button 이 눌러 졌을 때......

      HtmlFreeEditorApplet applet = (HtmlFreeEditorApplet) ( Utility.getAncestorOfClass( HtmlFreeEditorApplet.class, this ) );

      if( applet != null ) {

	applet.callHelpJScript();

      }

      if( applet == null && AppRegistry.HELP_SITE != null ) {

	new Thread() {

	  public void run() {

	    try {

		final String helpCommand = "explorer " + AppRegistry.HELP_SITE;

		java.lang.Runtime.getRuntime().exec( helpCommand );

	    } catch(IOException e) {

		Utility.debug( e );

	    }

	  }

	}.start();

      }

      this.htmlEditorPane.requestFocus();

  }

  private void showStatus( String text ) {

    final HtmlEditorPane editor = getEditor();

    if( editor == null ) {

      return;

    }

    editor.setString( text );

  }

}
