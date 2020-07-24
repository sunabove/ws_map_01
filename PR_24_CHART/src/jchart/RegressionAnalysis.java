package jchart;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import jcosmos.*;

public abstract class RegressionAnalysis {

  public RegressionAnalysis() {
  }

  public RegressionResult getLinearRegressionAnalysisResult( double [] x, double y [] ) {

    int N = x.length;

    double xySum = getXYSum( x, y );
    double xSum = getSum( x );
    double ySum = getSum( y );
    double xxSum = getXYSum( x, x );

    double sol []  = solveEquation( N, xSum, ySum , xSum, xxSum, xySum );

    double a = sol[ 1 ];

    double b = sol[ 0 ];

    Utility.debug( this, "a = " + a + ", b = " + b );

    return new RegressionResult( new double [] { a, b } );

  }

  // c is ÀýÆí
  public RegressionResult getRegressionAnalysisResult( double [] x, double y [], double c ) {

    double yy [] = getArrayAfterSum( y, -c );

    return getRegressionAnalysisResult( x, yy );

  }

  public double getR2( double [] x, double [] y, RegressionResult result ) {

    double yAvg = getAvg( y );

    double yHat [] = getYHatArray( x, result );

    double yHatAvg = getAvg( yHat );

    double SSR = getSquareSumAfterSum( yHat, - yHatAvg );
    double SST = getSquareSumAfterSum( y, - yAvg );

    return SSR/SST;

  }

  public double [] getArrayAfterSum( double [] array, double v) {

    int len = array.length;

    double newArray [] = new double[ len ];

    for( int i = 0; i < len; i ++ ) {

      newArray[ i ] = array[ i ] + v;

    }

    return newArray;

  }

  public double [] getYHatArray( double [] x, RegressionResult result ) {

    int len = x.length;

    double [] yHat = new double[ len ];

    for( int i = 0; i < len; i ++ ) {

      yHat[ i ] = getYHat( x[ i ], result );

    }

    return yHat;

  }

  public double [] solveEquation(double a, double b, double c, double d, double e, double f) {

    return new double [] { ( c*e - b*f)/(a*e - b*d), (a*f - c*d)/( a*e - b*d) };

  }

  public double getSquareSumAfterSum( double [] v, double y ) {

    double sum = 0;

    for( int i = 0, len = v.length; i < len; i ++ ) {

      sum += ( ( v[i] + y )*( v[i] + y ) );

    }

    return sum;

  }

  public double getSum(double [] v) {

    double sum = 0;

    for( int i = 0, len = v.length ; i < len ; i ++ ) {

      sum += v[ i ];

    }

    return sum;

  }

  public double getXYAvg( double [] x, double [] y ) {

    return getXYSum( x, y )/x.length;

  }

  public double getXYSum( double [] x, double y [] ) {

    int len = x.length;

    double sum = 0;

    for( int i = 0; i < len; i ++ ) {

      sum += ( x[i]*y[i] );

    }

    return sum;

  }

  public double [] makeLogArray(double x [] ) {

    int len = x.length;

    double [] v = new double[ len ];

    for( int i = 0; i < len; i ++ ) {

      v[ i ] = Math.log( x[i] );

    }

    return v;

  }

  private double [] makePowerArray( double [] x, int n ) {

    int len = x.length;

    double [] v = new double[ len ];

    for( int i = 0; i < len; i ++ ) {

      v[ i ] = Math.pow( x[i], n );

    }

    return v;

  }

  public double getAvg( double [] x ) {

    double v = 0;

    int len = x.length;

    for( int i = 0; i < len; i ++ ) {

      v += ( x[i]/len );

    }

    return v;

  }

  public double getAvg( double [] x, double [] y ) {

    double v = 0;

    int len = x.length;

    for( int i = 0; i < len; i ++ ) {

      v += ( x[i]*y[i]/len );

    }

    return v;

  }

  public abstract RegressionResult getRegressionAnalysisResult( double [] x, double y [] );

  public abstract double getYHat( double x, RegressionResult result );

  public abstract String getNumericExpression( RegressionResult result );

}