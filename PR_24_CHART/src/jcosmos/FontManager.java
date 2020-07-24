
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
import java.util.*;
import javax.swing.*;

public class FontManager {
  public static final int defaultFontIndex = 5;
  public static final int defaultSizeIndex = 11;
  public static String [] fontFamilyNames;
  public static String [] preferredFontSizes =
    {
      "8", "9", "10", "12", "14", "16", "18", "20", "24", "28", "32", "36",
      "40", "44", "48", "54", "60", "66", "72", "80", "96"
    };

  public static ImageIcon iconFontNames [] ;
  public static ImageIcon iconFontSizes [] ;

  static {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    fontFamilyNames = ge.getAvailableFontFamilyNames(java.util.Locale.KOREA);


    // Set iconifiedFontNames;
    final int fontNum = fontFamilyNames.length;
    iconFontNames = new ImageIcon[ fontNum ];

    for(int i = 0; i < fontNum; i ++ ) {
	try {
	  iconFontNames[i] = new ImageIcon(Utility.getResource("font_icon.gif"));
	  iconFontNames[i].setDescription(fontFamilyNames[i]);
	  Utility.debug( FontManager.class, "Font family = " + fontFamilyNames[i] );
	} catch (Exception e) {
	  Utility.debug( e );
	}
    }

    // Set iconFontSizes;
    final int preferredFontSizedNum = preferredFontSizes.length;
    iconFontSizes = new ImageIcon[ preferredFontSizedNum ];

    for(int i = 0; i < preferredFontSizedNum; i ++ ) {
	try {
	  iconFontSizes[i] = new ImageIcon(Utility.getResource("null.gif"));
	  iconFontSizes[i].setDescription(preferredFontSizes[i]);
	} catch (Exception e) {
	  Utility.debug( e );
	}
    }

  }

  public static void loadAvaiableFonts( Component c ) {

    Font cachedFont;

    long time;

    String [] fontFamilyNames = FontManager.fontFamilyNames;

    Class cls = FontManager.class;

    final int fontNum = fontFamilyNames.length;

    Font font;

    for(int i = 0; i < fontNum; i ++ ) {

       time = System.currentTimeMillis();

       System.out.print( "Activating font " + fontFamilyNames[ i] + " ...... " );

       try {

	cachedFont = new Font( fontFamilyNames[ fontNum - i - 1 ], Font.PLAIN, 8 );

	font = cachedFont;

	showSampleFontText( c, font );

//	font = cachedFont.deriveFont( Font.BOLD );

//	showSampleFontText( c, font );

	jcosmos.Utility.debug(cls, "Font " + fontFamilyNames[ fontNum - i - 1]
	  + " has activated for " + (System.currentTimeMillis() - time)/1000 + " seconds." );

       } catch (Exception e) {
	 jcosmos.Utility.debug(cls, e.getMessage() );
       }

    }

    jcosmos.Utility.debug(cls, "Done activating many kinds of fonts.");

  }

  public static void showSampleFontText(Component c, Font font) {

    c.setFont( font );

    c.paint( c.getGraphics() );

  }

  public static void main(String args [] ) {

    Frame f = new Frame("Font Loading Tester");

    f.setLayout( new BorderLayout() );

    Canvas c = new Canvas() {

      public void paint(Graphics g) {

	super.paint( g );

	g.setColor( Color.black );

	Dimension size = getSize();

	FontMetrics fm = g.getFontMetrics();

	int h = (size.height - fm.getHeight())/2;

	g.drawString( "¾È³çÇÏ¼¼¿ä?", 10, h );

      }

    };

    f.add( c, BorderLayout.CENTER );

    f.setSize( 300, 300 );

    f.setVisible( true );

    FontManager.loadAvaiableFonts( c );
  }

  public static Font getDefaultFont() {
//     return getFont( "µ¸¿ò", Font.PLAIN, 14 );
    return getFont( "Serif", Font.PLAIN, 14);
  }

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

  public static void setFontFamilyMenuItem(JComboBox menu) {
    menu.removeAllItems();
    int count = fontFamilyNames.length;
    for(int i = 0; i < count; i ++ ) {
      menu.add( new JLabel( fontFamilyNames[i] ) );
    }
    menu.validate();
  }
}