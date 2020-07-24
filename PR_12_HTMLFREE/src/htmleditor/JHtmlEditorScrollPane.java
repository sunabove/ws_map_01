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
import jcosmos.*;

public class JHtmlEditorScrollPane extends JScrollPane {

  public JHtmlEditorScrollPane(final Component view, final HtmlEditorPane editor) {

    super( view );

    // 수직 수평 스크롤 바를 항상 나타내게 한다.

    setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
    setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );

    // 끝. 수직 수평바 항상 나타내기.

    // 수직, 수평 스크롤 바 리스너 추가

    addListeners( editor );

    // 끝. 수직, 수평 스크롤 바 리스너 추가

  }

  private void addListeners(final HtmlEditorPane editor) {

    MouseListener listener = new MouseAdapter() {

      public void mousePressed(MouseEvent e) {

	editor.setAutoScroll( false );

      }

      public void mouseReleased(MouseEvent e) {

	editor.requestFocus();

      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {

	editor.requestFocus();

      }

    };

    getVerticalScrollBar().addMouseListener( listener );
    getHorizontalScrollBar().addMouseListener( listener );

  }

  public void processKeyEvent(KeyEvent e) {

    char c = e.getKeyChar();

    Utility.debug(this, "SCROLL PANE KEY = " + c );

    super.processKeyEvent( e );

  }

}