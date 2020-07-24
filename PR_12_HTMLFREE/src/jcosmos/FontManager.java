
/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      JCOSMOS DEVELOPMENT<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package jcosmos;

import java.awt.*;
import java.awt.font.*;
import java.util.*;
import javax.swing.*;
import htmleditor.*;

public class FontManager {

  private static Hashtable fonts = new Hashtable();

  public static Font getFont(String family, int type, int size ) {

       family = forwardFontName( family );

       String key = family + type + size;

       if( fonts.contains( key ) ) {

	   return (Font) fonts.get( key );

       } else {

	   Font font = new Font( family, type, size );

	   fonts.put( key, font );

	   return font;

       }

  }

  public static String forwardFontName(String family ) {

      if( family.equalsIgnoreCase( "Times New Roman" ) ) {
	  return "Serif";
      } else if( family.equalsIgnoreCase( "Arial" ) ) {
	  return "Dialog";
      }

      return family;
  }

  public static String backwardFontName(String family) {

      if( family.equalsIgnoreCase( "Serif" ) ) {

	  return "Times New Roman";

      } else if( family.equalsIgnoreCase( "Dialog" ) ) {

	  return "Arial";

      }

      return family;

  }

  public static Font getDefaultFont() {

     return getFont( AppRegistry.DEFAULT_FONT_NAME, Font.PLAIN, AppRegistry.DEFAULT_FONT_SIZE );

  }

}