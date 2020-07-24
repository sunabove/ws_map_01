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

public class LogRegressionAnalysis extends RegressionAnalysis {

  public LogRegressionAnalysis() {
  }

  public RegressionResult getRegressionAnalysisResult( double [] x, double y [] ) {

    int N = x.length;

    double [] logX = makeLogArray( x );

    double logXSum = getSum( logX );
    double logXlogXSum = getXYSum( logX, logX );
    double yLogXSum = getXYSum( y, logX );
    double ySum = getSum( y );

    double sol []  = solveEquation( logXlogXSum, logXSum, yLogXSum,
				    logXSum, N, ySum );

    double a = sol[ 0 ];

    double b = sol[ 1 ];

    Utility.debug( this, "a = " + a + ", b = " + b );

    return new RegressionResult( new double [] { a, b } );

  }

  public double getYHat( double x, RegressionResult result ) {

    double [] ab = result.ab;

    return ab[0]*Math.log( x ) + ab[ 1 ];

  }

  public String getNumericExpression( RegressionResult result ) {

    double [] ab = result.ab;

    double a = ab[0];
    double b = ab[1];

    return "" + a + "Ln(" + b + "x)";

  }

}