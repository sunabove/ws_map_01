package com.ynhenc.comm.util;

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class DateUtil {

  public static Date getNowDate() {

    return new Date( System.currentTimeMillis() );

  }

  public static String toSqlStyleValue( Date date ) {

    return df.format( (Date)(date) );

//    return
//          ""
//        + (1900 + date.getYear())
//        + '-' + date.getMonth()
//        + '-' + date.getDay()
//        + ' ' + date.getHours()
//        + ':' + date.getMinutes()
//        + ':' + date.getSeconds()
//        ;

  }

  private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");;

}
