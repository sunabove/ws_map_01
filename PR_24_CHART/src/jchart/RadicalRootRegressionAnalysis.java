package jchart;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class RadicalRootRegressionAnalysis extends RegressionAnalysis {

  public RadicalRootRegressionAnalysis() {
  }

  public RegressionResult getRegressionAnalysisResult( double [] x, double y [] ) {

    x = makeLogArray( x );
    y = makeLogArray( y );

    RegressionResult result = super.getLinearRegressionAnalysisResult( x, y );

    double [] ab = result.ab;
    double a = ab[ 0 ];
    double b = ab[ 1 ];

    return new RegressionResult( new double [] { Math.exp( b ), a } );

  }

  public double getYHat( double x, RegressionResult result ) {

    double [] ab = result.ab;

    return ab[0]*Math.pow(x, ab[1] );

  }

  public String getNumericExpression( RegressionResult result ) {

    double [] ab = result.ab;

    double a = ab[0];
    double b = ab[1];

    return "(" + a + ")(x)^(" + b + ")";

  }

}