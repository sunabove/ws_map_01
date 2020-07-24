package htmleditor;

/**
 * Title:        HTML FREE EDITOR
 * Description:  Html Free Editor v2.0
 * Copyright:    Copyright (c) 2001
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;
import jcosmos.*;

public class EditorBorder extends EmptyBorder {

  HtmlEditorPane editor;

  public EditorBorder( HtmlEditorPane editor, Insets insets) {

    super( insets );

    this.editor = editor;

  }

  public Shape [] getTicks(int x, int y, int w, int h) {

    Shape [] ticks = new Shape[8];

    int top = this.top;
    int left = this.left;

    int tickRate = 3;

    int tw = left/tickRate; // tick width
    int th =  top/tickRate; // tick height


    // top left
    ticks[ 0 ] = new Line2D.Double( x     ,  y - th ,         x,             y );
    ticks[ 1 ] = new Line2D.Double( x - tw,        y,         x,             y );

    // top right
    ticks[ 2 ] = new Line2D.Double(  x + w,  y - th,       x + w,            y );
    ticks[ 3 ] = new Line2D.Double(  x + w,       y,  x + w + tw,            y );

    // bottom left
    ticks[ 4 ] = new Line2D.Double( x - tw,   y + h,            x,       y + h );
    ticks[ 5 ] = new Line2D.Double(      x,   y + h,            x,  y + h + th );

    // bottom right
    ticks[ 6 ] = new Line2D.Double(  x + w,   y + h,   x + w + tw,       y + h );
    ticks[ 7 ] = new Line2D.Double(  x + w,   y + h,        x + w,  y + h + th);


    return ticks;

  }

  public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {

    super.paintBorder( c, g, x, y, w, h );

    Graphics2D g2 = (Graphics2D) g;

    paintTicks( g2 );

  }

  public void paintTicks(Graphics2D g2) {

    Color tickColor = editor.getTickColor();

    g2.setColor( tickColor );

    Dimension size = editor.getSize();

    int x = left;
    int y = top;
    int w = size.width - left - right;
    int h = size.height - top - bottom;

    Shape ticks [] = getTicks( x, y, w, h );

    for( int i = 0, len = ticks.length; i < len; i ++ ) {

      g2.draw( ticks[ i ] );

    }

  }

}