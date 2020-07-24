package com.jwordart.util;

import java.awt.*;

public class HtmlUtil extends ColorUtil {

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

    final public String selected( String a , String b ) {
    	return ( a != null && b != null && a.equals( b ) ) ? "selected" : "" ;
    }

}
