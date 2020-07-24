package com.ynhenc.comm.util;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

public class StyleUtil {

  public static Paint createTexturePaint(final  int [][] pixels, final Color refColor ) {

      final int h = pixels.length;
      final int w = pixels[0].length;

      final BufferedImage image = new BufferedImage( w, h, BufferedImage.TYPE_INT_RGB );

      final Graphics g = image.getGraphics();

      for(int i = 0; i < h ; i ++ ) {

        for(int k = 0; k < w ; k ++ ) {

            g.setColor( ( pixels[i][k] == 0 ) ? Color.white : refColor );
            g.drawRect( k, i, 1, 1 );

        }

      }

      final Rectangle2D tr = new Rectangle2D.Double( 0, 0, w, h);

      return new TexturePaint( image , tr );

  }

}
