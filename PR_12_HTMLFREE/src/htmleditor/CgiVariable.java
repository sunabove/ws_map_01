package htmleditor;

/**
 * Title:        HTML FREE EDITOR
 * Description:  Html Free Editor v2.0
 * Copyright:    Copyright (c) 2001
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.io.*;

public class CgiVariable {

  private String name;
  private String cookieName;
  private String value;
  private char [] chars;
  private byte [] bytes;

  public CgiVariable(String name, String value) {

    int colonIdx = name.indexOf( ':' );

    if( colonIdx > -1 ) {

      this.cookieName = name.substring( 0, colonIdx ).trim();
      this.name = name.substring( colonIdx + 1 ).trim();

    } else {

      this.name = name.trim();

    }

    this.value = value.trim();

  }

  public String getCookieName() {

    return cookieName;

  }

  public String getName() {

    return name;

  }

  public String getValue() {

    return value;

  }

  public void setValue( String value ) {

    this.value = value;

    this.chars = null;

    this.bytes = null;

  }

  public void setValue( char [] chars ) {

    this.value = null;

    this.chars = chars;

    this.bytes = null;

  }

  public void setValue( byte [] bytes ) {

    this.value = null;

    this.chars = null;

    this.bytes = bytes;

  }

  public int getLength() {

    if( bytes != null ) {

      return bytes.length;

    }

    if( chars != null ) {

      return chars.length*2;

    }

    if( value != null ) {

      return value.length()*2;

    }

    return 0;

  }

  public long write( final OutputStream out, long currLen,
			    final ProgressInterface progressBar,
			    final long contentLength ) throws IOException {

    final byte [] nameBytes = ( getName() + "=" ).getBytes();

    out.write( nameBytes );

    currLen += nameBytes.length;

    final long denom = (256*4);

    if( value != null ) {

      final byte [] valueBytes = value.getBytes();

      for( int i = 0, len = valueBytes.length ; i < len ; i ++ ) {

	out.write( valueBytes[ i ] );

	currLen += 1;

	if( ( currLen % denom ) == 0 ) {

	      progressBar.setValue( (int) ( 100.0*( (double) currLen )/( (double) contentLength ) ) );

	}

      }

    } else if( chars != null ) {

      final char [] chars = this.chars;

      for( int i = 0, len = chars.length; i < len; i ++ ) {

	out.write( ("" + chars[i]).getBytes() );

	currLen += 2;

	if( ( currLen % denom ) == 0 ) {

	      progressBar.setValue( (int) ( 100.0*( (double) currLen )/( (double) contentLength ) ) );

	}

      }

    } else if( bytes != null ) {

      byte [] bytes = this.bytes;

      for( int i = 0, len = bytes.length ; i < len ; i ++ ) {

	out.write( bytes[ i ] );

	currLen += 1;

	if( ( currLen % denom ) == 0 ) {

	      progressBar.setValue( (int) ( 100.0*( (double) currLen )/( (double) contentLength ) ) );

	}

      }

    }

    return currLen;

  }

  public String toString() {

    if( cookieName != null ) {

      return "CGI : " + getName() + "(" + cookieName + ") = " + getValue();

    }

    return "CGI : " + getName() + " = " + getValue();

  }

}