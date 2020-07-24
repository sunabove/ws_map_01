package com.ynhenc.comm.util;

public class Parser {

    public Parser(String line) {
      this( line, "," );
    }

    public Parser(String line, String delimiter) {
      this.line = line;
      this.lineLen = line.length();
      this.delimiter = delimiter;
      this.comma = -1;
    }

    public String readNext() {
      String data = readNextInternal().trim() ;
      int len = data.length();
      if( data.startsWith( "\"" ) && data.endsWith( "\"" ) ) {
        if( len > 2 ) {
          return data.substring( 1, len -1 );
        } else {
          return "";
        }
      } else {
        return data;
      }
    }

    private String readNextInternal() {
      if( comma < lineLen -1 ) {
        int nextComma = line.indexOf( this.delimiter, comma + 1 );
        if (nextComma > -1) {
          String data = line.substring( comma + 1, nextComma );
          this.comma = nextComma;
          return data;
        } else {
          if (comma > -1 ) {
            String data = line.substring( comma + 1 );
            this.comma = lineLen + 1;
            return data;
          }
          else {
            this.comma = lineLen + 1;
            return line;
          }
        }
      } else {
        return "";
      }
    }

    // member
    private String delimiter;
    private int comma;
    private int lineLen;
    private String line;
    // end of member

  }
