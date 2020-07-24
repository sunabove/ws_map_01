package gmlviewer.gis.util;

import java.awt.*;
import java.awt.font.*;
import java.util.*;
import javax.swing.*;
import java.awt.geom.AffineTransform;

public class FontManager {

  private static Hashtable fonts = new Hashtable();

  public static Font getFont(String family, int type, int size ) {

       String key = family + type + size;

       if( fonts.contains( key ) ) {

	   return (Font) fonts.get( key );

       } else {

	   Font font = new Font( family, type, size );

	   fonts.put( key, font );

	   return font;

       }

  }

  public static Font getDefaultFont() {

     return getFont( "Serif", Font.PLAIN, 14 );

  }

  public static Font getDrawingFont(Font font, Rectangle currentBounds, Rectangle originalBounds) {

      double sx = currentBounds.width / ( originalBounds.width + 0.0);
      double sy = currentBounds.height/ ( originalBounds.height + 0.0);

      AffineTransform at = AffineTransform.getScaleInstance( sx, sy );

      return font.deriveFont( at );

  }


}
