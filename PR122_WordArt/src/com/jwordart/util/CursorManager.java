
/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.util;


import java.awt.*;
import java.awt.image.*;

import com.jwordart.ui.resource.Resource_WordArt;

public class CursorManager {
   public static Cursor rotateReadyCursor;
   public static Cursor rotateActionCursor;

   static {
      try {
        Frame f = new Frame();
        f.addNotify();
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image readyImage = Resource_WordArt.getImage( "wordart" , "rotate_ready_cursor.gif") ;
        int w = readyImage.getWidth(f)/4, h = readyImage.getHeight(f)/4;
        BufferedImage newReadyImage = new BufferedImage( w, h, BufferedImage.TYPE_INT_RGB );
        Graphics g2 = newReadyImage.getGraphics();
        g2.drawImage(readyImage, 0, 0, w, h, f);
        rotateReadyCursor = tk.createCustomCursor( newReadyImage, new Point(0, 0), "rotateReadyCursor");
        Image actionImage = Resource_WordArt.getImage( "wordart" , "rotate_action_cursor.gif") ;
        rotateActionCursor = tk.createCustomCursor( actionImage, new Point(6, 8), "rotateActionCursor");
      } catch (Exception e) {
        e.printStackTrace();
      }
   }
}