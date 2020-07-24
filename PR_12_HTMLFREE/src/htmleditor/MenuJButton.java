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
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import jcosmos.*;

public class MenuJButton extends JButton implements MouseMotionListener, MouseListener {

  private boolean mouseIn = false;
  private boolean mousePressed = false;

  private static MenuJButton preBtn;  // 이전 마우스 오버 버튼

  public MenuJButton(String toolTip, Border border, String iconName, MouseListener listener ) {

    setToolTipText( toolTip );
    setBorder(border);
    setIcon( Utility.getResourceImageIcon(iconName) );
    addMouseListener( listener );
    addMouseListener( this );
    addMouseMotionListener( this );

  }

  public void mousePressed(MouseEvent e) {

    mousePressed = true;

  }

  public void mouseReleased(MouseEvent e) {

    mousePressed = false;

  }

  public void mouseClicked(MouseEvent e) {
  }

  public void mouseMoved(MouseEvent e) {

      if( mouseIn ) {
	 // Do nothing!
      } else {

	 if( preBtn != null && preBtn != this ) {

	    preBtn.mouseIn = false;

	    preBtn.repaint();

	    preBtn = this;

	 } else if( preBtn == null){
	    preBtn = this;
	 }

	 mouseIn = true;

	 repaint();

      }

  }

  public void mouseDragged(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
      mouseIn = false;
      mousePressed = false;
      repaint();
  }

  public void mouseEntered(MouseEvent e) {

      mouseIn = true;
      mousePressed = false;

      repaint();

  }

  public void paintComponent(Graphics g) {

     super.paintComponent( g );

     if( mouseIn ) {

//        Utility.debug(this, "Drawing Mouse In Effect ...." );

	Color orgColor = g.getColor();

	Dimension size = getSize();

	Insets insets = getInsets();

	int x = insets.left, y = insets.top,
	    w = size.width - 1 - insets.left - insets.right,
	    h = size.height -1 - insets.top - insets.bottom;

	Color leftTop = mousePressed ? Color.gray : Color.white;
	Color rightBottom = mousePressed ? Color.white : Color.gray;

	for(int i = 0; i < 1 ; i ++ ) {

	   g.setColor( leftTop );

	   g.drawLine( x, y, x + w, y );

	   g.drawLine( x, y, x, y + h );

	   g.setColor( rightBottom );

	   g.drawLine( x + w, y, x + w, y + h );

	   g.drawLine( x, y + h, x + w, y + h );

	   x ++;
	   y ++;

	   w --;
	   h --;

	}

	g.setColor( orgColor );

     }

  }

}