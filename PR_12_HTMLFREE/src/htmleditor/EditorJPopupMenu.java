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

public class EditorJPopupMenu extends JPopupMenu {

    final private JMenuItem copy = new JMenuItem( "복사", Utility.getResourceImageIcon("copy.gif") );
    final private JMenuItem cut = new JMenuItem( "잘라내기", Utility.getResourceImageIcon("cut.gif") );
    final private JMenuItem paste = new JMenuItem( "붙여넣기", Utility.getResourceImageIcon("paste.gif") );
    final private JMenuItem systemPaste = new JMenuItem( "외부 붙여넣기", Utility.getResourceImageIcon("paste.gif") );

    // separator

    final private JMenu zIndex = new JMenu( "정렬" );

    final private JMenuItem topMost = new JMenuItem ( "맨 앞으로 가져오기", Utility.getResourceImageIcon("top_most.gif" ) );

    final private JMenuItem bottomMost = new JMenuItem ( "맨 뒤로 보내기", Utility.getResourceImageIcon("bottom_most.gif" ) );

    final private JMenuItem toFront = new JMenuItem ( "앞으로 가져오기", Utility.getResourceImageIcon("to_back.gif" ) );

    final private JMenuItem toBack = new JMenuItem ( "뒤로 보내기", Utility.getResourceImageIcon("to_front.gif" ) );

    // separator

    final private JMenuItem imageStyle = new JMenuItem ( "그림 서식", Utility.getResourceImageIcon("image_style.gif" ) );

    // separator

    final private JMenuItem hyperLink = new JMenuItem ( "하이퍼링크", Utility.getResourceImageIcon("html_link.gif" ) );

    HtmlEditorPane editor;

    public EditorJPopupMenu(final HtmlEditorPane editor) {

      this.editor = editor;

      add( copy );
      add( cut );
      add( paste );
      add( systemPaste );

      addSeparator();

      add( zIndex );

      zIndex.add( topMost );
      zIndex.add( bottomMost );
      zIndex.add( toFront );
      zIndex.add( toBack );

      addSeparator();

      add( imageStyle );

      addSeparator();

      add( hyperLink );

      final Font font = AppRegistry.EDITOR_POPUP_MENU_FONT;

      copy.setFont( font );
      cut.setFont( font );
      paste.setFont( font );
      systemPaste.setFont( font );

      zIndex.setFont( font );

      topMost.setFont( font );
      bottomMost.setFont( font );
      toFront.setFont( font );
      toBack.setFont( font );

      imageStyle.setFont( font );
      hyperLink.setFont( font );

      pack();

      copy.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent e) {

	  editor.copyToBuffer();

	}

      });

      cut.addActionListener(new ActionListener() {

	  public void actionPerformed(ActionEvent e) {

	    editor.cutToBuffer();

	  }

      });

      paste.addActionListener(new ActionListener() {

	  public void actionPerformed(ActionEvent e) {

	    editor.pasteFromBuffer();
	  }

      });

      systemPaste.addActionListener(new ActionListener() {

	  public void actionPerformed(ActionEvent e) {

	    editor.pasteSystemClipboard();

	  }

      });

      imageStyle.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent e) {

	  editor.editImageStyle();

	}

      });

      hyperLink.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent e) {

	  editor.setHyperLink( hyperLink );

	}

      });

      topMost.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
	  editor.setMaxIndex();
	}
      });

      bottomMost.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
	  editor.setMinIndex();
	}
      });

      toFront.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
	  editor.setLargeIndex();
	}
      });

      toBack.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
	  editor.setSmallIndex();
	}
      });

    }

    public void show(MouseEvent e) {

	final ImageElement target = ImageElement.SEL_IMG_ELEM;

	if( target instanceof HtmlDocument ) {

	    final HtmlDocument doc = (HtmlDocument) target;

	    final int top = HtmlDocument.getTopology( doc, e );

	    if( top == ImageElement.INSIDE_AREA ) {

	      showAsDocumentPopopMenu( e );

	    } else {

	      showAsImagePopopMenu( e, target );

	    }

	} else if( target != null ) {

	    showAsImagePopopMenu( e, target );

	}

    }

    private void showAsImagePopopMenu(MouseEvent e, ImageElement target) {

	final HtmlDocument doc = editor.getTargetDocument();

	final boolean copiable = true;

	final boolean pastable = ( doc.getCopiedHtmlElements().size() > 0 ) ;

	copy.setEnabled( copiable );
	cut.setEnabled( copiable );
	paste.setEnabled( pastable );

	zIndex.setEnabled( true );

	imageStyle.setEnabled( true );

	hyperLink.setEnabled( ! ( target instanceof HtmlDocument ));

	super.show( editor, e.getX(), e.getY() );

  }

   private void showAsDocumentPopopMenu(MouseEvent e) {

	final boolean copiable = editor.isCopialbe();

	final boolean pastable = editor.isPastable();

	copy.setEnabled( copiable );
	cut.setEnabled( copiable );
	paste.setEnabled( pastable );

	zIndex.setEnabled( false );

	imageStyle.setEnabled( false );

	super.show( editor, e.getX(), e.getY() );

  }

}