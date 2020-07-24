package jchart;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class ExponentialRegressionAnalysis extends RegressionAnalysis {

  public ExponentialRegressionAnalysis() {
  }

  public RegressionResult getRegressionAnalysisResult( double [] x, double y [] ) {

    y = makeLogArray( y );

    RegressionResult result = super.getLinearRegressionAnalysisResult( x, y );

    double [] ab = result.ab;
    double a = ab[ 0 ];
    double b = ab[ 1 ];

    return new RegressionResult( new double [] { Math.exp( b ), a } );

  }

  public double getYHat( double x, RegressionResult result ) {

    double [] ab = result.ab;

    return ab[0]*Math.exp( ab[1]*x );

  }

  public String getNumericExpression( RegressionResult result ) {

    double [] ab = result.ab;

    double a = ab[0];
    double b = ab[1];

    return "(" + a + ")E^(" + b + "x)";

  }

}