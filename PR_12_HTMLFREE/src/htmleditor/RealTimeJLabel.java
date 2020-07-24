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
import javax.swing.*;

public class RealTimeJLabel extends JLabel {

  public RealTimeJLabel() {
  }

  public void repaint() {

      Graphics g = getGraphics();

      if( g == null ) {

	return;

      }

      Dimension size = getSize();

      Color color = getBackground();

      g.setColor( color );

      g.fillRect( 0, 0, size.width, size.height );

      paint( g );

   }

}