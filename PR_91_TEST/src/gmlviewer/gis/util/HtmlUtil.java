package gmlviewer.gis.util;

import java.awt.*;

public class HtmlUtil {

  public static String toHtmlColor(Color color) {

     final String [] rgb = {
        Integer.toHexString( color.getRed() ).toUpperCase() ,
        Integer.toHexString( color.getGreen() ).toUpperCase(),
        Integer.toHexString( color.getBlue() ).toUpperCase()
     };

     for( int i = 0; i < 3; i ++ ) {
        if( rgb[i].length() == 1 ) {
           rgb[i] = "0" + rgb[i];
        }
     }

     return "#" + rgb[0] + rgb[1] + rgb[2] ;

  }

  public static Color fromHtmlColor(String text) {

     if( text == null ) {
         return null;
     }
     else if( text.equalsIgnoreCase( "NULL" ) || text.length() < 6 ) {
         return null;
     }

     if( text.startsWith( "#" ) ) {
       text = text.substring(1); // 맨 앞에 있는 #을 일단 짤라 낸다.
     }

     int r = parseHexaString( text.substring(0, 2) ),
         g = parseHexaString( text.substring(2, 4) ),
         b = parseHexaString( text.substring(4) );

     return new Color( r, g, b );

  }

  public static int parseHexaString(String hexa) {

    int value = 0;
    for (int i = 0, len = hexa.length(); i < len; i++) {
      final char c = hexa.charAt(i);
      int digit = 0;
      switch (c) {
        case '1':
          digit = 1;
          break;
        case '2':
          digit = 2;
          break;
        case '3':
          digit = 3;
          break;
        case '4':
          digit = 4;
          break;
        case '5':
          digit = 5;
          break;
        case '6':
          digit = 6;
          break;
        case '7':
          digit = 7;
          break;
        case '8':
          digit = 8;
          break;
        case '9':
          digit = 9;
          break;
        case 'a':
          digit = 10;
          break;
        case 'b':
          digit = 11;
          break;
        case 'c':
          digit = 12;
          break;
        case 'd':
          digit = 13;
          break;
        case 'e':
          digit = 14;
          break;
        case 'f':
          digit = 15;
          break;
        case 'A':
          digit = 10;
          break;
        case 'B':
          digit = 11;
          break;
        case 'C':
          digit = 12;
          break;
        case 'D':
          digit = 13;
          break;
        case 'E':
          digit = 14;
          break;
        case 'F':
          digit = 15;
          break;
      }
      value = value * 16 + digit;
    }

    return value;

  }


    public static String toHtmlText(String text) {

       // replace "<" as "&lt"
       int i = text.indexOf( '<' );

       while( i > -1 ) {
          String pre = text.substring(0, i);
          String post = "";
          if( i < text.length() - 1 ) {
             post = text.substring( i + 1 );
          }

          text = pre + "&lt;" + post;

          i = text.indexOf( '<' );
       }

       // replace ">" as "&gt"
       i = text.indexOf( '>' );

       while( i > -1 ) {
          String pre = text.substring(0, i);
          String post = "";
          if( i < text.length() - 1 ) {
             post = text.substring( i + 1 );
          }

          text = pre + "&gt;" + post;

          i = text.indexOf( '>' );
       }

       // replace " " as "&nbsp"

       if( false ) {
           i = text.indexOf(' ');

           while (i > -1) {
               String pre = text.substring(0, i);
               String post = "";
               if (i < text.length() - 1) {
                   post = text.substring(i + 1);
               }

               text = pre + "&nbsp;" + post;

               i = text.indexOf(' ');
           }
       }
       return text;

    }

    public static String fromHtmlText(String text) {

       // replace "&lt" as "<"
       int i = text.indexOf( "&lt;" );

       while( i > -1 ) {
          String pre = text.substring(0, i);
          String post = "";

          i += 4;

          if( i < text.length() - 1 ) {
             post = text.substring( i );
          }

          text = pre + "<" + post;

          i = text.indexOf( "&lt;" );
       }

       // replace ">" as "&gt"
       i = text.indexOf( "&gt;" );

       while( i > -1 ) {
          String pre = text.substring(0, i);
          String post = "";

          i += 4;

          if( i < text.length() - 1 ) {
             post = text.substring( i );
          }

          text = pre + ">" + post;

          i = text.indexOf( "&gt;" );
       }

       // replace " " as "&nbsp"

       i = text.indexOf( "&nbsp;" );

       while( i > -1 ) {

          text = text.substring( 0, i ) + " " + text.substring( i + 6 );

          i = text.indexOf( "&nbsp;" );

       }

       final String nl = "\r\n";

       i = text.indexOf( nl );

       while( i > -1 ) {

        text = text.substring( 0, i ) + "\n" + text.substring( i + 2 );

        i = text.indexOf( nl );

       }

       return text;

    }

    final public static int toHtmlSize(int size) {

       int hs = 1;  // html font size

       switch( size ) {
          case 8:
              hs = 1;
              break;
          case 10:
              hs = 2;
              break;
          case 12:
              hs = 3;
              break;
          case 14:
              hs = 4;
              break;
          case 18:
              hs = 5;
              break;
          case 24:
              hs = 6;
              break;
          case 36:
              hs = 7;
              break;
       }

       return hs;

    }

    final public static int fromHtmlSize(int size) {

       int hs = 1;  // html font size

       switch( size ) {
          case 1:
              hs = 8;
              break;
          case 2:
              hs = 10;
              break;
          case 3:
              hs = 12;
              break;
          case 4:
              hs = 14;
              break;
          case 5:
              hs = 18;
              break;
          case 6:
              hs = 24;
              break;
          case 7:
              hs = 36;
              break;
       }

       return hs;

    }





}
