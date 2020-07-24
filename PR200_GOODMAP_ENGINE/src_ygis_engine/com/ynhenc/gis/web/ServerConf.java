package com.ynhenc.gis.web;

public class ServerConf {

  public static String

   BROWSER_CHARSET                   = "utf-8"
  // BROWSER_CHARSET            = System.getProperty( "file.encoding", "MS949" )
  //"UTF8" //"LATIN1" // "MS949" // "EUC-KR"
  , SERVER_CHARSET           = System.getProperty( "file.encoding" )
  , MYSQL_CREATE_TABLE_OPTION  = ""
  , ORACLE_DB_CHARSET = "EUC-KR"
  //, ORACLE_DB_CHARSET = "utf-9"

  ;

}
