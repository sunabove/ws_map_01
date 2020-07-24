package htmleditor;

/**
 * Title:        Test Publishing System
 * Description:  Internet Based Test Publishing System V1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.io.*;
import jcosmos.*;

public class HtmlParser implements CharacterUtility {

  protected static String readFile(File file) throws FileNotFoundException, IOException {

       FileInputStream in = new FileInputStream( file );

       byte [] bytes = new byte[ in.available() ];

       in.read( bytes );

       String charSet = AppRegistry.CHARSET;

       try {

	    return new String( bytes, charSet );

       } catch (Exception e ) {

	    Utility.debug( HtmlParser.class , "CHARTSET = " + charSet );

	    Utility.debug( e );

	    return new String( bytes );

       }

  }

  protected static String getHtmlBody(String html) {
      String body = readFromToLast( html, "<body", "</body>" );

      body = body.substring( body.indexOf( ">") + 1 );

      return body.trim();
  }

  protected static String readFromToLast(String text, String from, String to) {
      int i = text.indexOf( from );
      int j = text.lastIndexOf( to );

      if( i < 0 || j < 0 ) {
	  return null;
      }

      return text.substring( i  + from.length() , j );
  }

   protected static String readFromToFirst(String text, String from, String to) {

      int i = text.indexOf( from );

      int k = text.indexOf( to, i + from.length() );

//      Utility.message( HtmlParser.class, "FROM INDEX = " + i + ", TO INDEX = " + k);

      if( i < 0 || k < 0 ) {
	  return null;
      }

      return text.substring( i + from.length() , k );

  }

  protected static String readFromToMatched(final String text, final String from, final String to) {

//      Utility.debug( HtmlParser.class , "INPUT = " + text );

//       Utility.debug( HtmlParser.class, "FROM = " + from );
//       Utility.debug( HtmlParser.class, "TO = " + to );

      final int fromLen = from.length();
      final int toLen = to.length();

      final int i = text.indexOf( from ) + fromLen ; // start index matched

      int k = 0;

      // 일단 프롬을 찾았다.

      String content = text;

      int match = 0;

      do {

//	   Utility.debug( HtmlParser.class, "STEP CONTENT = " + content );

	   int fi = content.indexOf( from ); // from index
	   int ti = content.indexOf( to );  // to index

	   if( fi < 0 ) {

	      match --;

	      content = content.substring( ti + toLen );

	      k += (ti + toLen );

	   } else if( fi < ti ) {

	      match ++;

	      content = content.substring( fi + fromLen );

	      k += (fi + fromLen);

	   } else if( ti < fi ) {

	      match --;

	      content = content.substring( ti + toLen );

	      k += (ti + toLen);

	   }

//	   Utility.debug( HtmlParser.class, "MATCH NUM = " + match );

      } while( match > 0 );

      k -= toLen;

      return text.substring( i, k );

  }

}