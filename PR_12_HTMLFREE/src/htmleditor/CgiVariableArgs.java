package htmleditor;

/**
 * Title:        HTML FREE EDITOR
 * Description:  Html Free Editor v2.0
 * Copyright:    Copyright (c) 2001
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.util.*;
import java.io.*;
import java.net.*;
import jcosmos.*;

public class CgiVariableArgs {

  private Vector variableList = new Vector();

  public CgiVariableArgs() {
  }

  public void addCgiVariable( CgiVariable cgiVariable ) {

    variableList.addElement( cgiVariable );

  }

  public void processCookie( String cookieText ) {

    if( ! cookieText.endsWith( ";" ) ) {

      cookieText += ";";

    }

    final Vector variableList = this.variableList;

    for( int i = 0, len = variableList.size(); i < len; i ++ ) {

      final CgiVariable cgiVariable = (CgiVariable) variableList.elementAt( i );

      final String cookieName = cgiVariable.getCookieName();

      if( cookieName == null ) {

	continue;

      }

      final int cookieNameIdx = cookieText.indexOf( cookieName );

      if( cookieNameIdx < 0 ) {

	continue;

      }

      final int startIdx = cookieText.indexOf( "=", cookieNameIdx );

      if( startIdx < 0 ) {

	continue;

      }

      final int endIdx = cookieText.indexOf( ";", startIdx );

      final String cookieValueText = cookieText.substring( startIdx + 1, endIdx ).trim();

      cgiVariable.setValue( cookieValueText );

    }

  }

  private int getContentLength() {

    int contentLength = 0;

    final Vector variableList = this.variableList;

    final int len = variableList.size();

    for( int i = 0; i < len; i ++ ) {

      contentLength += ((CgiVariable) variableList.elementAt( i )).getLength();

    }

    return contentLength + len*4;

  }

//  private String getHttpCgiBinText() {
//
//    String httpCgiBinText = "";
//
//    final Vector variableList = this.variableList;
//
//    for( int i = 0, len = variableList.size(); i < len; i ++ ) {
//
//      final CgiVariable cgiVariable = (CgiVariable) variableList.elementAt( i );
//
//      httpCgiBinText += cgiVariable.getName() + "=" + cgiVariable.getValue()
//			+  ( ( i != len - 1 ) ? "&" : "\r\n"  );
//
//			// 반드시 맨 마지막에는 개행문자가 들어가야 한다.
//			// HTTP POST 방식의 프로토콜 때문이다.
//
//    }
//
//    return httpCgiBinText;
//
//  }

  public CgiVariable getCgiVariable( String variableName ) {

    variableName = variableName.trim();

    final Vector variableList = this.variableList;

    for( int i = 0, len = variableList.size(); i < len; i ++ ) {

      final CgiVariable cgiVariable = (CgiVariable) variableList.elementAt( i );

      if( cgiVariable.getName().equalsIgnoreCase( variableName ) ) {

	return cgiVariable;

      }

    }

    return null;

  }

  public CgiVariable getCgiVariableOfCookieName( String cookieName ) {

    cookieName = cookieName.trim();

    final Vector variableList = this.variableList;

    CgiVariable cgiVariable;

    String cookieNameComp;

    for( int i = 0, len = variableList.size(); i < len; i ++ ) {

      cgiVariable = (CgiVariable) variableList.elementAt( i );

      cookieNameComp = cgiVariable.getCookieName();

      if( cookieNameComp != null && cookieNameComp.equalsIgnoreCase( cookieName ) ) {

	return cgiVariable;

      }

    }

    return null;

  }

  public void print( ) {

    final Vector variableList = this.variableList;

    for( int i = 0, len = variableList.size(); i < len; i ++ ) {

      final CgiVariable cgiVariable = (CgiVariable) variableList.elementAt( i );

      System.out.println( "" + cgiVariable );

    }

  }

  // 텍스트 데이터 설정

  public void setValue( String variableName, String variableValue ) {

    CgiVariable cgiVariable = getCgiVariable( variableName );

    if( cgiVariable == null ) {

      cgiVariable = new CgiVariable( variableName, "" );

      addCgiVariable( cgiVariable );

    }

    cgiVariable.setValue( variableValue );

  }

  // 캐릭터 데이터 설정

  public void setValue( String variableName, char [] chars ) {

    CgiVariable cgiVariable = getCgiVariable( variableName );

    if( cgiVariable == null ) {

      cgiVariable = new CgiVariable( variableName, "" );

      addCgiVariable( cgiVariable );

    }

    cgiVariable.setValue( chars );

  }

  // 바이너리 데이터 설정

  public void setValue( String variableName, byte [] bytes ) {

    CgiVariable cgiVariable = getCgiVariable( variableName );

    if( cgiVariable == null ) {

      cgiVariable = new CgiVariable( variableName, "" );

      addCgiVariable( cgiVariable );

    }

    cgiVariable.setValue( bytes );

  }

  public void removeVariable( CgiVariable variable ) {

    variableList.remove( variable );

  }

  public void removeVariable( String variableName ) {

    final Vector variableList = this.variableList;

    for( int i = 0, len = variableList.size(); i < len; i ++ ) {

      final CgiVariable cgiVariable = (CgiVariable) variableList.elementAt( i );

      if( cgiVariable.getName().equalsIgnoreCase( variableName ) ) {

	variableList.remove( i );

	return;

      }

    }

  }

  public InputStream callPost( final String cgiBinUrlText, final ProgressInterface progressBar ) {

      URL url = null;

      try {

	  url = new URL( cgiBinUrlText );

	  Utility.debug( this, "CGI-BIN = " + cgiBinUrlText );

      } catch ( MalformedURLException e ) {

	  Utility.warningDialog(  AppRegistry.MAIN_APP_JFRAME,
				  "잘못된 저장 CGI-BIN URL",
				  "잘못된 저장 CGI-BIN URL"
				);

      }

      try {

	  URLConnection urlC = url.openConnection();

	  urlC.setDoInput( true );

	  urlC.setDoOutput( true );

	  urlC.setUseCaches( false );

	  urlC.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

//	  DataOutputStream dos = new DataOutputStream( urlC.getOutputStream() );
//	  dos.writeBytes( getHttpCgiBinText() );

	  OutputStream out = urlC.getOutputStream();

	  final int contentLength = getContentLength();

	  final Vector variableList = this.variableList;

	  CgiVariable cgiVariable;

	  long currLen = 0;

	  for( int i = 0, len = variableList.size(); i < len; i ++ ) {

	    cgiVariable = (CgiVariable) variableList.elementAt( i );

	    currLen = cgiVariable.write( out, currLen, progressBar, contentLength );

	    if( i < len -1 ) {

	      out.write( "&".getBytes() );

	      currLen += 2;

	    }

//	    System.out.println( currLen + " / " + contentLength + " = " + status );

	  }

	  out.write( "\r\n".getBytes() );

	  // 반드시 맨 마지막에는 개행문자가 들어가야 한다.
	  // HTTP POST 방식의 프로토콜 때문이다.

//	  out.write( -1 );

	  out.flush();

	  out.close();

	  return urlC.getInputStream();

      } catch ( IOException e ) {

	  Utility.warningDialog( AppRegistry.MAIN_APP_JFRAME, "저장 CGI-BIN URL 호출 에러", "저장 CGI-BIN URL 호출 에러"  );

	  return null;

      }

  }

}