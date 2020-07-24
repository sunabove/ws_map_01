package test;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class DoubleOverflow {

  public DoubleOverflow() {
  }

  public static void main(String args [] ) {

//    double d = Math.pow( Math.pow(6, 6), 3 );
//
//    System.out.println( "D = " + d);

    double m = - Double.MAX_VALUE;

    System.out.print( "MIN = " + m );

  }

}